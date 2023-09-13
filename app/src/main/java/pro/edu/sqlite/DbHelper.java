package pro.edu.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "eduSys";
    public static final int DB_VERSION = 1;

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String queryStringClass = "CREATE TABLE classes(" +
                "id integer primary key autoincrement," +
                "code text not null," +
                "name text not null)";
        String queryStringStudent = "CREATE TABLE students(" +
                "id text primary key," +
                "name text not null," +
                "birth_date text not null," +
                "class_id integer," +
                "FOREIGN KEY (class_id) REFERENCES classes(id)) ";
        sqLiteDatabase.execSQL(queryStringClass);
        sqLiteDatabase.execSQL(queryStringStudent);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String queryStringStudent = "DROP TABLE IF EXISTS classes";
        String queryStringClass = "DROP TABLE IF EXISTS students";

        sqLiteDatabase.execSQL(queryStringClass);
        sqLiteDatabase.execSQL(queryStringStudent);

        this.onCreate(sqLiteDatabase);
    }
}
