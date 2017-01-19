package com.ichen.user.fcmtest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;


public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    /*DB Block*/
    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    /*View Block*/
    private Button logoutButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private EditText newEditText;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initID();
        initView();
        initFirebaseDB();
        startAuth();
    }

    private void initID() {
        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.listView);
        logoutButton = (Button) findViewById(R.id.logoutButton);
        // Add items via the Button and EditText at the bottom of the window.
        newEditText = (EditText) findViewById(R.id.todoText);
        addButton = (Button) findViewById(R.id.addButton);

    }

    private void initView() {
        // Create a new Adapter
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1);

        // Assign adapter to ListView
        listView.setAdapter(adapter);
        // Delete items when clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Query myQuery = dbRef.orderByValue().equalTo((String) listView.getItemAtPosition(position));

                myQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                            firstChild.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Create a new child with a auto-generated ID.
                DatabaseReference childRef = dbRef.push();

                // Set the child's data to the value passed in from the newEditText box.
                childRef.setValue(newEditText.getText().toString());

            }
        });

        /*logout Action*/
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), REQUEST_LOGIN);
            }
        });

    }

    private void initFirebaseDB() {
        // Connect to the Firebase database
        database = FirebaseDatabase.getInstance();

        // Get a reference to the todoItems child items it the database
        dbRef = database.getReference("contacts");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Assign a listener to detect changes to the child items
        // of the database reference.
        dbRef.addChildEventListener(new ChildEventListener() {

            // This function is called once for each child that exists
            // when the listener is added. Then it is called
            // each time a new child is added.
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                String value = dataSnapshot.getValue(String.class);
                adapter.add(value);
            }

            // This function is called each time a child item is removed.
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                adapter.remove(value);
            }

            // The following functions are also required in ChildEventListener implementations.
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
            }

            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG:", "Failed to read value.", error.toException());
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        auth.addAuthStateListener(authStateListener);

        Intent intent = getIntent();
        String msg = intent.getStringExtra("msg");
        if (msg != null) {
            Log.d(TAG, "msg:" + msg);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_LOGIN:
                if (resultCode != RESULT_OK) {
                    finish();
                }
                break;
        }
    }

    /**
     * Life Cycle Line
     */
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private final int REQUEST_LOGIN = 0;

    private void startAuth() {
        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null || !user.isEmailVerified()) {
                    startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), REQUEST_LOGIN);
                } else {
                    // TODO after login
                }
            }
        };
    }

}
