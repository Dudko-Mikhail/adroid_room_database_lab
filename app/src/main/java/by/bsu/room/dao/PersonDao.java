package by.bsu.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import by.bsu.room.entity.Person;

@Dao
public interface PersonDao {
    @Query("SELECT * FROM Person")
    LiveData<List<Person>> getAll();

    @Query("SELECT * FROM Person p WHERE p.name LIKE :text || '%' OR p.surname LIKE :text || '%'")
    LiveData<List<Person>> getPersonsWhoseNameOrSurnameStartsWithGivenText(String text);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Person person);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Person...person);

    @Query("DELETE FROM Person")
    void deleteAll();
}