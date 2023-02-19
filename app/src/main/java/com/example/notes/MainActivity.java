package com.example.notes;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView listView;

//    Function to create a new note
    public void createNote(View view){
        Intent intent = new Intent(this, createNote.class);
        startActivity(intent);
    }

//    Function to get all saved notes
    public void fetchNotes(){
        listView = findViewById(R.id.listView);

        SharedPreferences sp = getSharedPreferences("MyNotes", MODE_PRIVATE);
        Map<String, ?> noteData = sp.getAll();

        JSONArray noteArray = new JSONArray();

//        Add all the values of noteData(Map) into noteArray(JsonArray)
        for (Map.Entry<String, ?> entry : noteData.entrySet()) {
//            final String key = entry.getKey();
            final Object value = entry.getValue();
            noteArray.put(value);
        }

        String []arr = new String[noteArray.length()];

//        Add all the values of noteArray(JsonArray) into a normal array arr
        for(int i = 0; i < noteArray.length(); i++){
            try {
                arr[i] = noteArray.getString(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        notes_adapter notesAdapter = new notes_adapter(this, R.layout.notes_adapter, arr);
        listView.setAdapter(notesAdapter);
    }

//    Fetch the notes again on coming back from another activity as there is a possibility of getting a new note which has been created few moments ago
    @Override
    protected void onResume()
    {
        super.onResume();
        fetchNotes();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        fetchNotes();
    }
}