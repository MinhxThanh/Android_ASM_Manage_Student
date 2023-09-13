package pro.edu.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pro.edu.model.Classes;

public class ClassesDao {
    private SQLiteDatabase db;

    public ClassesDao(Context context) {
        DbHelper helper = new DbHelper(context);

        this.db = helper.getWritableDatabase();
    }

    private List<Classes> get(String sql, String...selectArgs) {
        List<Classes> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectArgs);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") Classes classes = new Classes(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("code")),
                    cursor.getString(cursor.getColumnIndex("name"))
            );
            list.add(classes);
        }
        return list;
    }

    public Long insert(Classes classes){
        ContentValues values = new ContentValues();
        values.put("id", classes.getId());
        values.put("code", classes.getCode());
        values.put("name", classes.getName());

        return db.insert("classes", null, values);
    }

    public List<Classes> getAll() {
        String query = "select * from classes";
        return get(query);
    }

    public Classes getById(String id) {
        String query = "select * from classes where id = ?";
        List<Classes> classes = get(query, id);
        return classes.get(0);
    }

    public int delete(Integer id) {
        return db.delete("classes", "id = ?", new String[]{String.valueOf(id)});
    }
}

