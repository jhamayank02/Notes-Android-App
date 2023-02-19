package com.example.notes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class createNote extends AppCompatActivity {
    EditText enterName;
    EditText enterDescription;

//    When user clicks on save note button
    public void saveNote(View view) throws JSONException {

        enterName = findViewById(R.id.enterName);
        enterDescription = findViewById(R.id.enterDescription);

        if(enterName.getText().toString().length() == 0 && enterDescription.getText().toString().length() == 0){
            Toast.makeText(this, "A note must have a title or description", Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();

//        Generating a random number
        Random rand = new Random();
        int upperBound = 100000;
        int randomNum = rand.nextInt(upperBound);

        JSONObject note = new JSONObject();
//        Concatenate the title and a random number to create a unique id
        note.put("id", enterName.getText().toString() + randomNum);
        note.put("title", enterName.getText().toString());
        note.put("description", enterDescription.getText().toString());
        note.put("date", formatter.format(date));

        SharedPreferences sp = getSharedPreferences("MyNotes", MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(enterName.getText().toString() + randomNum, note.toString());

        ed.apply();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

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
    }
}