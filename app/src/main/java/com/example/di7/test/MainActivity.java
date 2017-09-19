package com.example.di7.test;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends Activity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private BlankFragment bf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bf = new BlankFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.container, bf).commit();

       /* database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users").child("ret");

        myRef.setValue("Hello, World!");*/

    }
}
