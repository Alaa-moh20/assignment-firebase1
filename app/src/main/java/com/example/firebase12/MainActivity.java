package com.example.firebase12;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button button1;
    Button button2;
    private ProgressDialog dialog;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.btn1);
        button2 = findViewById(R.id.btn2);
        textView = findViewById(R.id.tv);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
        getItems();
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItems("dv2sIDTLqx1PebEHvkuM");
            }
        });
    }

    private void getItems() {
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                String data = "";
                if (task.isSuccessful()) {
                    if (task.getResult().isEmpty()) {
                        textView.setText("There is No Data click ADD USER button");
                    } else {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("ttt", document.getId() + " => " + document.getData());
                            data += document.getData().toString() + "\n \n";
                        }
                        textView.setText(data);
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                } else {
                    Log.d("ttt", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    private void deleteItems(String id) {
        db.collection("users").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "DocumentSnapshot successfully deleted!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("TAG", "Error deleting document", e);
            }
        });
    }
}