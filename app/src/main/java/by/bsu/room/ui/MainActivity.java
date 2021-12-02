package by.bsu.room.ui;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;

import by.bsu.room.R;
import by.bsu.room.dao.PersonRepository;
import by.bsu.room.entity.Person;

public class MainActivity extends AppCompatActivity {
    private Button addPersonBtn;
    private TextView totalRecords;
    private TextView foundRecords;
    private TextInputEditText searchField;
    private PersonRepository personRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addPersonBtn = findViewById(R.id.addPersonButton);
        totalRecords = findViewById(R.id.recordsTotal);
        foundRecords = findViewById(R.id.foundRecords);
        searchField = findViewById(R.id.searchField);
        personRepository = PersonRepository.getInstance(getApplication());
        personRepository.getAllPersons().observe(this, people -> setTotalRecords(people.size()));
        personRepository.getPersonsWhoseNameOrSurnameStartsWithGivenText("").observe(this, people -> setFoundRecords(people.size()));
        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Person newPerson = (Person) data.getSerializableExtra("NEW_PERSON");
                        personRepository.insert(newPerson);
                    }
                });

        addPersonBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddPersonActivity.class);
            resultLauncher.launch(intent);
        });

        searchField.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}

                    @Override
                    public void afterTextChanged(Editable s) {
                        personRepository.getPersonsWhoseNameOrSurnameStartsWithGivenText(s.toString())
                                .observe(MainActivity.this,
                                        persons -> setFoundRecords(persons.size())
                                );
                    }
                }
        );
    }

    private void setTotalRecords(int amount) {
        totalRecords.setText(String.format(Locale.getDefault(), "%s %d", getText(R.string.records_total), amount));
    }

    private void setFoundRecords(int amount) {
        foundRecords.setText(String.format(Locale.getDefault(), "%s %d", getText(R.string.found_records), amount));
    }
}