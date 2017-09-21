package com.example.di7.test;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class BlankFragment extends Fragment {

    private static final String TAG = "--------";
    private static final String SAVED_EMAIL = "email";
    private static final String SAVED_PASSWORD = "password";
    private static final String SAVED_ID = "id";
    private View v;
    private EditText etPassqword, etMail, etName;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Switch riderOrDriverSwitch;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser user;
    private String userID;

    public BlankFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_blank, container, false);

        mAuth = FirebaseAuth.getInstance();

        etMail = (EditText) v.findViewById(R.id.etMail);
        etPassqword = (EditText) v.findViewById(R.id.etPassword);
        etName = (EditText) v.findViewById(R.id.etName);

        riderOrDriverSwitch = (Switch) v.findViewById(R.id.riderOrDriverSwitch);

        database = FirebaseDatabase.getInstance();

        loadPref();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //redirectUser(user);
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
        ((Button)v.findViewById(R.id.btnAuth)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sighIn();
            }
        });

        ((Button)v.findViewById(R.id.btnReg)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create();
            }
        });

        return v;
    }

    private void create() {
        mAuth.createUserWithEmailAndPassword(etMail.getText().toString(), etPassqword.getText().toString())
                .addOnCompleteListener((Activity) v.getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        User u = new User(etName.getText().toString(), etMail.getText().toString(), riderOrDriverSwitch.isChecked());
                        userID = mAuth.getCurrentUser().getUid();
                        savePref();
                        myRef = database.getReference("users").child(userID);
                        myRef.child("name").setValue(etName.getText().toString());
                        myRef.child("email").setValue(etMail.getText().toString());
                        myRef.child("longitude").setValue(0);
                        myRef.child("latitude").setValue(0);
                        myRef.child("riderOrDriver").setValue(riderOrDriverSwitch.isChecked());
                        myRef.child("lastTime").setValue(0);



                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(v.getContext(), "reg Field",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void redirectUser() {
        if(riderOrDriverSwitch.isChecked()) {
            Intent i = new Intent(v.getContext(), MapsActivity.class);
            startActivity(i);
        } else {
            Intent i = new Intent(v.getContext(), MapsActivityDriver.class);
            i.putExtra("userID", userID);
            startActivity(i);

        }
    }

    private void sighIn() {
        mAuth.signInWithEmailAndPassword(etMail.getText().toString(), etPassqword.getText().toString())
                .addOnCompleteListener((Activity) v.getContext(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            redirectUser();
                        } else {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(v.getContext(), "auth field",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void savePref() {
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPref.edit();
        ed.putString(SAVED_EMAIL, etMail.getText().toString());
        ed.putString(SAVED_PASSWORD, etPassqword.getText().toString());
        ed.putString(SAVED_ID, userID);
        ed.commit();
    }

    private void loadPref() {
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        etMail.setText(sharedPref.getString(SAVED_EMAIL, ""));
        etPassqword.setText(sharedPref.getString(SAVED_PASSWORD, ""));
    }




}
