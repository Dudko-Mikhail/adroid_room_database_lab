package by.bsu.room.dao;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import by.bsu.room.entity.Person;

public class PersonRepository {
    private static PersonRepository instance;
    private final PersonDao personDao;
    private final LiveData<List<Person>> allPersons;

    public PersonRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        personDao = db.personDao();
        allPersons = personDao.getAll();
    }

    public void insert(Person person) {
        AppDatabase.databaseActionExecutors.execute(() -> personDao.insert(person));
    }

    public void deleteAll() {
        AppDatabase.databaseActionExecutors.execute(personDao::deleteAll);
    }

    public LiveData<List<Person>> getAllPersons() {
        return allPersons;
    }

    public LiveData<List<Person>> getPersonsWhoseNameOrSurnameStartsWithGivenText(String text) {
        return personDao.getPersonsWhoseNameOrSurnameStartsWithGivenText(text);
    }

    public static PersonRepository getInstance(Application application) {
        if (instance == null) {
            instance = new PersonRepository(application);
        }
        return instance;
    }
}
