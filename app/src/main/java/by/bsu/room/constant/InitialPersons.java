package by.bsu.room.constant;

import by.bsu.room.entity.Person;

public class InitialPersons {
    private static final Person[] persons = {
            new Person("Misha", "Dudko", "myComment"),
            new Person("Ivan", "Ivanov", "some text"),
            new Person("Sidorov", "Peter", "another text"),
            new Person("Gleb", "Petrov", "Interesting comment"),
    };

    public static Person[] getPersons() {
        return persons;
    }
}
