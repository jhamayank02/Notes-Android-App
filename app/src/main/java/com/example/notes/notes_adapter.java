package com.example.notes;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class notes_adapter extends ArrayAdapter<String> {
    private String []arr;

    public notes_adapter(@NonNull Context context, int resource, @NonNull String[] arr) {
        super(context, resource, arr);
        this.arr = arr;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return arr[position];
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        JSONObject jsonObj = null;
        try {
//            Convert the string value to a json object
            jsonObj = new JSONObject(getItem(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        convertView = LayoutInflater.from(getContext()).inflate(R.layout.notes_adapter, parent, false);

        TextView date = convertView.findViewById(R.id.date);
        TextView name = convertView.findViewById(R.id.name);
        TextView description = convertView.findViewById(R.id.description);

        final String titleVal, dateVal, descriptionVal, id;

        try {
            titleVal = jsonObj.get("title").toString();
            dateVal = jsonObj.get("date").toString();
            descriptionVal = jsonObj.get("description").toString();
            id = jsonObj.get("id").toString();

            date.setText(dateVal);
            name.setText(titleVal.length() > 19 ? titleVal.substring(0, 20) + "..." : titleVal);
            description.setText(descriptionVal.length() > 19 ? descriptionVal.substring(0, 40) + "..." : descriptionVal);

//            If name(title) or description field is blank then hide it
            if(name.getText().toString().length() == 0){
                name.setVisibility(TextView.GONE);
            }
            if(description.getText().toString().length() == 0){
                description.setVisibility(TextView.GONE);
            }


            convertView.setOnClickListener(new View.OnClickListener() {
//                When user clicks to open the note
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), openNote.class);
                    intent.putExtra("title", titleVal);
                    intent.putExtra("description", descriptionVal);
                    intent.putExtra("date", dateVal);
                    intent.putExtra("id", id);
                    view.getContext().startActivity(intent);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return convertView;
    }
}
