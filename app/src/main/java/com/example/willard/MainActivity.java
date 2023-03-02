package com.example.willard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    Button buttonINC,buttonDEC,button;
    TextView tvDisplay;
    FirebaseDatabase database;
    DatabaseReference reference;
    Member member;

    int maxid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonINC = findViewById(R.id.btn_inc_id);
        buttonDEC = findViewById(R.id.btn_dec_id);
        tvDisplay = findViewById(R.id.tv_output_id);
        button = findViewById(R.id.btn);

        member = new Member();
        reference = FirebaseDatabase.getInstance().getReference().child("Testing increment");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    maxid = (int) snapshot.getChildrenCount();
                } else  {
                    ///
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        buttonINC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentValue = tvDisplay.getText().toString();
                int value = Integer.parseInt(currentValue);
                value++;
                tvDisplay.setText(String.valueOf(value));
            }
        });

        buttonDEC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentValue = tvDisplay.getText().toString();
                int value = Integer.parseInt(currentValue);
                value--;
                tvDisplay.setText(String.valueOf(value));
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                member.setTvDisplay(tvDisplay.getText().toString());

                // Get a reference to the child node you want to update
                DatabaseReference childRef = reference.child(String.valueOf(maxid + 1));

                // Update the value of the child node
                childRef.setValue(member);
            }
        });
    }
}
