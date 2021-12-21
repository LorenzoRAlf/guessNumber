package com.example.guessthenumber;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;


public class RecordActivity extends AppCompatActivity {


    static class Record {
        public int guesses;
        public String file;
        public String name;

        public Record(String _file, int _guesses, String _name ) {
            file = _file;
            guesses = _guesses;
            name = _name;
        }


    }

    static ArrayList<Record> records = new ArrayList<>();
    ArrayAdapter<Record> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        String name = getIntent().getStringExtra("recordName");
        int guesses = getIntent().getIntExtra("recordGuesses", 0);
        String fileName = getIntent().getStringExtra("fileName");
        records.add(new Record(fileName, guesses, name));

        if (records.size()>1){

            records.sort(Comparator.comparingInt(o -> o.guesses));

        }

        adapter = new ArrayAdapter<Record>( this, R.layout.list_item, records )
        {
            @Override
            public View getView(int pos, View convertView, ViewGroup container)
            {

                if( convertView==null ) {
                    convertView = getLayoutInflater().inflate(R.layout.list_item, container, false);
                }

                String fileName = getItem(pos).file;
                String guessesNumber = Integer.toString(getItem(pos).guesses);
                ((TextView) convertView.findViewById(R.id.name)).setText(getItem(pos).name);
                ((TextView) convertView.findViewById(R.id.guesses)).setText(guessesNumber);
                Uri fileUri = Uri.fromFile(getFile(fileName));
                ((ImageView) convertView.findViewById(R.id.userImage)).setImageURI(fileUri);
                return convertView;
            }

        };

        ListView lv = findViewById(R.id.recordsView);
        lv.setAdapter(adapter);



    }

    public void goBack(View view) {
        finish();
    }

    protected File getFile(String fileName){

        File path = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(path, fileName);
    }

}