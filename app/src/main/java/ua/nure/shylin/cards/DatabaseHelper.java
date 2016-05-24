package ua.nure.shylin.cards;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.content.ContentValues;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "userstore.db"; // название бд
    private static final int SCHEMA = 1; // версия базы данных

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE `tests` (`_id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, `name` TEXT NOT NULL);");
        db.execSQL("CREATE TABLE `cards` (" +
        "`_id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
        "`question`	TEXT NOT NULL," +
        "`answer`	TEXT NOT NULL," +
        "`tests_id`	INTEGER NOT NULL," +
                "FOREIGN KEY(`tests_id`) REFERENCES `tests`(`_id`)" +
        ");");
        db.execSQL("INSERT INTO tests (name) VALUES ('История');");
        db.execSQL("INSERT INTO cards (question,answer,tests_id) VALUES ('Взятие Бастилии','1789',1);");
        db.execSQL("INSERT INTO cards (question,answer,tests_id) VALUES ('Декларация Независимости США','1776',1);");
        db.execSQL("INSERT INTO cards (question,answer,tests_id) VALUES ('Вторая Мировая','1939',1);");
        db.execSQL("INSERT INTO cards (question,answer,tests_id) VALUES ('Первая Мировая','1914',1);");
        db.execSQL("INSERT INTO cards (question,answer,tests_id) VALUES ('Падение Византийской империи','1453',1);");
        db.execSQL("INSERT INTO cards (question,answer,tests_id) VALUES ('Образование СССР','30 декабря 1922 года',1);");
        db.execSQL("INSERT INTO cards (question,answer,tests_id) VALUES ('Въетнамская война','1964-1973',1);");
        db.execSQL("INSERT INTO cards (question,answer,tests_id) VALUES ('Карибский кризис','1962',1);");
        db.execSQL("INSERT INTO cards (question,answer,tests_id) VALUES ('Убийство Джона Кеннеди','22 ноября 1963 года',1);");
        db.execSQL("INSERT INTO cards (question,answer,tests_id) VALUES ('Коронование Елизаветы II','1952',1);");
        db.execSQL("INSERT INTO cards (question,answer,tests_id) VALUES ('Открытие туннеля под Ла-Маншем','1994',1);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {

    }
}
