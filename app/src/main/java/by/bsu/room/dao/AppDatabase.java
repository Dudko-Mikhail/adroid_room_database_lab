package by.bsu.room.dao;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import by.bsu.room.constant.InitialPersons;
import by.bsu.room.entity.Person;

@Database(entities = {Person.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    static final ExecutorService databaseActionExecutors;
    private static final int NUMBER_OF_THREADS = 4;
    private static AppDatabase instance;

    static  {
        databaseActionExecutors = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    }

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "app-database")
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            databaseActionExecutors.execute(() -> {
                                PersonDao dao = instance.personDao();
                                dao.deleteAll();
                                dao.insertAll(InitialPersons.getPersons());
                            });
                        }
                    })
                    .build();
        }
        return instance;
    }

    public abstract PersonDao personDao();

    protected AppDatabase() {}
}
