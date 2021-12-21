package com.example.guessthenumber;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    protected int randomNumber;
    private int guesses = 0;
    private String name;
    private String fileName;
    static int counter = 0;

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
        guesses = 0;
        TextView textGuesses = findViewById(R.id.guesses);
        textGuesses.setText("Guesses: 0");
    }


    public void guess(View v) {
        guesses++;
        CharSequence text;
        CharSequence guessText = "Guesses: " + guesses;

        EditText editText = findViewById(R.id.numberEdit);
        TextView textGuesses = findViewById(R.id.guesses);

        textGuesses.setText(guessText);
        String temp = editText.getText().toString();

        int value = Integer.parseInt(temp);

        if (value == randomNumber) {
            text = "You got it right!!!" + randomNumber;
            showDialog();
        } else if (value > randomNumber) {
            text = "Go lower!" + randomNumber;

        } else {
            text = "Go higher!" + randomNumber;

        }
        editText.setText("");
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
    }

    private void showDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("You got it!");
        alert.setMessage("Enter your name:");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", (dialog, whichButton) -> {
            name = input.getText().toString();

            if (name.isEmpty()) {
                showDialog();
            } else {
                counter++;
                fileName = counter + String.valueOf(randomNumber) +
                        "pic.jpg";
                dispatchTakePictureIntent();

            }
        });

        alert.setNegativeButton("Cancel", (dialog, whichButton) -> {
            TextView textGuesses = findViewById(R.id.guesses);
            textGuesses.setText("Guesses: 0");
            generateRandomNumber();
            // Canceled.
        });

        alert.show();
    }

    protected File getFile() {
        File path = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(path, fileName);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create the File where the photo should go
        File photoFile = getFile();
        // Continue only if the File was successfully created
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.example.guessthenumber",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent i = new Intent(getApplicationContext(), RecordActivity.class);
        i.putExtra("recordName", name);
        i.putExtra("recordGuesses", guesses);
        i.putExtra("fileName", fileName);
        startActivity(i);

    }

    public void generateRandomNumber() {
        randomNumber = (int)Math.floor(Math.random()*100+1);
    }
}
