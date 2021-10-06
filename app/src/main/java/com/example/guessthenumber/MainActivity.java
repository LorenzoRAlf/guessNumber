package com.example.guessthenumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    int randomNumber = (int)Math.floor(Math.random()*100+1);
    int guesses = 0;
    CharSequence text;
    CharSequence guessText;

    public void guess(View v) {
        guesses++;
        text = "";
        guessText = "Guesses: " + guesses;

        EditText editText = findViewById(R.id.numberEdit);
        TextView textGuesses = findViewById(R.id.guesses);
        textGuesses.setText(guessText);
        String temp = editText.getText().toString();

        int value = Integer.parseInt(temp);

        if (value == randomNumber) {
            text = "You got it right!!!" + randomNumber;
        }
        else if (value > randomNumber) {
            text = "Go lower!"+ randomNumber;
        }
        else  {
            text = "Go higher!"+ randomNumber;
        }

        Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
    }

}