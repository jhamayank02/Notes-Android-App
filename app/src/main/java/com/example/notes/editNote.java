package com.example.notes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

public class editNote extends AppCompatActivity {
    EditText enterName;
    EditText enterDescription;
    FloatingActionButton editBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

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

        enterName = findViewById(R.id.enterName);
        enterDescription = findViewById(R.id.enterDescription);
        editBtn = findViewById(R.id.editBtn);

        String title, description, id, date;

//      Take the previous note data
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        description = intent.getStringExtra("description");
        id = intent.getStringExtra("id");
        date = intent.getStringExtra("date");

//        Set the previous text in title and description
        enterName.setText(title);
        enterDescription.setText(description);

//        User will click on edit button after the changes in previous notes
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(enterName.getText().toString().length() == 0 && enterDescription.getText().toString().length() == 0){
                    Toast.makeText(editNote.this, "A note must have a title or description", Toast.LENGTH_SHORT).show();
                    return;
                }

                JSONObject note = new JSONObject();
                try {
                    note.put("id", id);
                    note.put("title", enterName.getText().toString());
                    note.put("description", enterDescription.getText().toString());
                    note.put("date", date);

                    SharedPreferences sp = getSharedPreferences("MyNotes", MODE_PRIVATE);
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putString(id, note.toString());

                    ed.apply();

                    Intent data = new Intent();
                    data.putExtra("updatedTitle", enterName.getText().toString());
                    data.putExtra("updatedDescription",enterDescription.getText().toString());
                    setResult(Activity.RESULT_OK, data);

                } catch (JSONException e) {
                    Toast.makeText(editNote.this, "Some error occured", Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        });
    }
}