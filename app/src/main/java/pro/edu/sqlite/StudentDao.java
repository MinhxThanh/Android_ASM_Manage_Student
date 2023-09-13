package pro.edu.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pro.edu.helper.DateTimeHelper;
import pro.edu.model.Students;

public class StudentDao {
    private SQLiteDatabase db;

    public StudentDao(Context context) {
        DbHelper helper = new DbHelper(context);

        this.db = helper.getWritableDatabase();
    }

    private List<Students> get(String sql, String...selectArgs) {
        List<Students> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, selectArgs);
        Log.d("TAG_Cursor", "get: " + cursor.getCount());
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") Students student = new Students(
                        cursor.getString(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        DateTimeHelper.toDate(cursor.getString(cursor.getColumnIndex("birth_date"))),
                        cursor.getInt(cursor.getColumnIndex("class_id"))
                );
                list.add(student);
            }
        }
        return list;
    }

    public List<Students> getAll() {
        String query = "SELECT * FROM students";
        return get(query);
    }

    public Students getById(String id) {
        String query = "SELECT * FROM students WHERE id = ?";
        List<Students> students = get(query, id);
        return students.get(0);
    }

    public List<Students> getAllByClassId(Integer classId) {
        String query = "SELECT * FROM students WHERE class_id = ?";

        return get(query, "" + classId);
    }

    public Long insert(Students student) {
        ContentValues values = new ContentValues();
        values.put("id", student.getId());
        values.put("name", student.getName());
        values.put("birth_date", DateTimeHelper.toString(student.getBirthDate()));
        values.put("class_id", student.getClassId());

        return db.insert("students", null, values);
    }

    public int update(Students student) {
        ContentValues values = new ContentValues();
        values.put("id", student.getId());
        values.put("name", student.getName());
        values.put("birth_date", DateTimeHelper.toString(student.getBirthDate()));
        values.put("class_id", student.getClassId());

        return db.update("students", values, "id = ?", new String[]{student.getId()});
    }

    public int delete(String id) {
        return db.delete("students", "id = ?", new String[]{id});
    }
}
