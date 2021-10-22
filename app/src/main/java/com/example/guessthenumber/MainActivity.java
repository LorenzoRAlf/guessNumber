package com.example.guessthenumber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generateRandomNumber();

    }

    @Override
    protected void onResume() {
        super.onResume();
        generateRandomNumber();
        TextView textGuesses = findViewById(R.id.guesses);
        textGuesses.setText("Guesses: 0");
    }

    protected int randomNumber;
    private int guesses = 0;
    private CharSequence text;
    private CharSequence guessText;
    //private static ArrayList<Record> records = new ArrayList<Record>();
    private Hashtable<String, Integer> playerRecords = new Hashtable<String, Integer>();

    public void guess(View v) {
        guesses++;
        text = "";
        guessText = "Guesses: " + guesses;

        EditText editText = findViewById(R.id.numberEdit);
        TextView textGuesses = findViewById(R.id.guesses);

        textGuesses.setText(guessText);
        String temp = editText.getText().toString();
        editText.setText("");

        int value = Integer.parseInt(temp);

        if (value == randomNumber) {
            text = "You got it right!!!" + randomNumber;
            showDialog();
        }
        else if (value > randomNumber) {
            text = "Go lower!"+ randomNumber;
        }
        else  {
            text = "Go higher!"+ randomNumber;
        }

        Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
    }

    private void showDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("You got it!");
        alert.setMessage("Enter your name:");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String name = input.getText().toString();
                playerRecords.put(name, guesses);
                guesses = 0;
                switchToRecord();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                TextView textGuesses = findViewById(R.id.guesses);
                textGuesses.setText("Guesses: 0");
                generateRandomNumber();
                // Canceled.
            }
        });

        alert.show();
    }

    private void switchToRecord() {
        Intent goToRecord = new Intent(this, RecordActivity.class);
        goToRecord = goToRecord.putExtra("records", playerRecords);
        startActivity(goToRecord);
    }


    public void generateRandomNumber() {
        randomNumber = (int)Math.floor(Math.random()*100+1);
    }
}
