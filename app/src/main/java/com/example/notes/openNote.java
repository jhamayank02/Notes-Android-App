package com.example.notes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class openNote extends AppCompatActivity {
    TextView titleDisplay;
    TextView dateDisplay;
    TextView descriptionDisplay;
    ImageView edit;
    ImageView delete;

//    Function to edit an existing note
    public void editFunc(View view){
        Intent intent2 = getIntent();

        Intent intent = new Intent(this, editNote.class);
        intent.putExtra("title", intent2.getStringExtra("title"));
        intent.putExtra("description", intent2.getStringExtra("description"));
        intent.putExtra("id", intent2.getStringExtra("id"));
        intent.putExtra("date", intent2.getStringExtra("date"));
        startActivityForResult(intent, 1);
    }

//    Get the edited note back and update the titleDisplay and descriptionDisplay fields in current acivity(openNote)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            titleDisplay = findViewById(R.id.titleDisplay);
            descriptionDisplay = findViewById(R.id.descriptionDisplay);

            titleDisplay.setText(data.getStringExtra("updatedTitle"));
            descriptionDisplay.setText(data.getStringExtra("updatedDescription"));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_note);

        // Changing the color of header
        // Define ActionBar object
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#F02929"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        titleDisplay = findViewById(R.id.titleDisplay);
        dateDisplay = findViewById(R.id.dateDisplay);
        descriptionDisplay = findViewById(R.id.descriptionDisplay);
        edit = findViewById(R.id.edit);
        delete = findViewById(R.id.delete);

        Intent intent = getIntent();
        titleDisplay.setText(intent.getStringExtra("title"));
        dateDisplay.setText(intent.getStringExtra("date"));
        descriptionDisplay.setText(intent.getStringExtra("description"));

//            If name(title) or description field is blank then hide it
        if(titleDisplay.getText().toString().length() == 0){
            titleDisplay.setVisibility(TextView.INVISIBLE);
        }
        if(descriptionDisplay.getText().toString().length() == 0){
            descriptionDisplay.setVisibility(TextView.GONE);
        }

        delete.setOnClickListener(new View.OnClickListener() {
//            Show alert when user clicks on delete icon
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setCancelable(true);
                builder.setTitle("");
                builder.setMessage("Are you sure you want to delete this?");

//                If user confirms the deletion
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sp = getSharedPreferences("MyNotes", MODE_PRIVATE);
                                SharedPreferences.Editor ed = sp.edit();

                                ed.remove(intent.getStringExtra("id"));
                                ed.apply();
                                finish();
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }
}