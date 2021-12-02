package by.bsu.room.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import by.bsu.room.R;
import by.bsu.room.entity.Person;

public class AddPersonActivity extends AppCompatActivity {
    private TextInputEditText name;
    private TextInputEditText surname;
    private TextInputEditText comment;
    private Button saveBtn;
    private Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);
        name = findViewById(R.id.nameEditText);
        surname = findViewById(R.id.surnameEditText);
        comment = findViewById(R.id.commentEditText);
        saveBtn = findViewById(R.id.addPersonSaveButton);
        cancelBtn = findViewById(R.id.addPersonCancelButton);

        saveBtn.setOnClickListener(view -> {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("NEW_PERSON", new Person(name.getText().toString(),
                    surname.getText().toString(), comment.getText().toString()));
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        });
        cancelBtn.setOnClickListener(view -> {
            setResult(Activity.RESULT_CANCELED);
            finish();
        });
    }
}