package com.example.storage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class EditPage extends AppCompatActivity {
    private static final String FILE_NAME = "USERFILE";
    EditText editBlock;
    Button save;
    Button load;
    Button clear;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_page);

        // Initialize all variables
        editBlock = findViewById(R.id.editBlock);
        save = findViewById(R.id.save);
        load = findViewById(R.id.load);
        clear = findViewById(R.id.clear);

        // load Button onClick Listener
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileContent = loadFileFromLocal();
                // Fail to read file
                if (fileContent == null) {
                    Toast.makeText(v.getContext(), "Fail to load file.", Toast.LENGTH_SHORT).show();
                }
                else {
                    editBlock.setText(fileContent);
                    Toast.makeText(v.getContext(), "Save successfully.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // save Button onClick Listener
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSaved = saveFileToLocal();
                if (isSaved) {
                    Toast.makeText(v.getContext(), "Save successfully.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(v.getContext(), "Fail to save.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // clear Button onClick Listner
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // clear the editText
                editBlock.setText("");
            }
        });
    }

    private String loadFileFromLocal() {
        try (FileInputStream fileInputStream = openFileInput(FILE_NAME)) {
            byte[] contents = new byte[fileInputStream.available()];
            fileInputStream.read(contents);
            return new String(contents);
        } catch (IOException ex) {
            Log.e("TAG", "Fail to read file");
            return null;
        }
    }

    private  boolean saveFileToLocal() {
        try (FileOutputStream fileOutputStream = openFileOutput(FILE_NAME, MODE_PRIVATE)) {
            fileOutputStream.write(editBlock.getText().toString().getBytes());
            Log.i("TAG", "Successfully saved file.");
            return true;
        } catch (IOException ex) {
            Log.e("TAG", "Fail to save file.");
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }
}
