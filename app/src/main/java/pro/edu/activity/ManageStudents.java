package pro.edu.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pro.edu.R;
import pro.edu.adapter.ClassesAdapter;
import pro.edu.adapter.StudentsAdapter;
import pro.edu.dialog.ConfirmDeleteStudentDialog;
import pro.edu.helper.DateTimeHelper;
import pro.edu.model.Classes;
import pro.edu.model.Students;
import pro.edu.sqlite.ClassesDao;
import pro.edu.sqlite.StudentDao;

public class ManageStudents extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText tieId, tieName, tieBirthday;
    private Spinner spClasses;
    private List<Classes> classesList;
    private ListView lvListStudents;
    private StudentsAdapter studentsAdapter;
    private Boolean isEditStudent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_students);

        this.initControl();
    }

    private void initControl() {
        Toolbar toolbar = findViewById(R.id.tbManageStudents);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tieId = (TextInputEditText)  findViewById(R.id.tieId);
        tieName = (TextInputEditText) findViewById(R.id.tieName);
        tieBirthday = (TextInputEditText) findViewById(R.id.tieBirthday);
        spClasses = (Spinner) findViewById(R.id.spClasses);
        lvListStudents = (ListView) findViewById(R.id.lvListStudents);

        findViewById(R.id.btnCreateStudent).setOnClickListener(this);
        findViewById(R.id.btnDeleteStudent).setOnClickListener(this);

        this.fillClassesToSpinner();
        this.validateInput();
        
        registerForContextMenu(lvListStudents);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            onBackPressed(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        StudentDao dao = new StudentDao(this);
        if (id == R.id.btnCreateStudent) {
           try {
               String msg;
               Classes classes = (Classes) spClasses.getSelectedItem();
               Students students = new Students(
                       tieId.getText().toString(),
                       tieName.getText().toString(),
                       DateTimeHelper.toDate(tieBirthday.getText().toString()),
                       classes.getId()
               );
               if (!this.isEditStudent) {
                   dao.insert(students);
                   msg = "Student is saved!";
               } else {
                   dao.update(students);
                   msg = "Student is updated!";
               }
               Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();

               this.isEditStudent = false;
               this.clearForm();
               fillStudentsToListView();
           } catch (Exception e) {
               e.printStackTrace();
               this.clearForm();
               Toast.makeText(this, "Some thing wrong please try again!", Toast.LENGTH_SHORT).show();
           }
        } else if (id == R.id.btnDeleteStudent){
            String studentId = tieId.getText().toString();
            if (studentId != null && !studentId.equals("") && isEditStudent) {
                Students student = dao.getById(studentId);

                ConfirmDeleteStudentDialog dialog = new ConfirmDeleteStudentDialog(this, student);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                this.clearForm();
                this.isEditStudent = false;
            } else {
                Toast.makeText(this, "Please select student want to delete!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu_manage_students, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Students student = (Students) studentsAdapter.getItem(info.position);

        int id = item.getItemId();

        if (id == R.id.oEditStudent) {
            tieId.setText(student.getId());
            tieName.setText(student.getName());
            tieBirthday.setText(DateTimeHelper.toString(student.getBirthDate()));

            this.isEditStudent = true;
        } else if (id == R.id.oDeleteStudent) {
            ConfirmDeleteStudentDialog dialog = new ConfirmDeleteStudentDialog(this, student);

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
        return super.onContextItemSelected(item);
    }

    private void fillStudentsToListView() {
        try {
            Classes cls = (Classes) spClasses.getSelectedItem();
            studentsAdapter = new StudentsAdapter(this, new StudentDao(this).getAllByClassId(cls.getId()));
            lvListStudents.setAdapter(studentsAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillClassesToSpinner() {
        try {
            classesList = new ClassesDao(this).getAll();
            ClassesAdapter adapter = new ClassesAdapter(this, classesList);
            spClasses.setAdapter(adapter);

            spClassesListener();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void spClassesListener() {
        spClasses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fillStudentsToListView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void validateInput() {
        this.validTextChanged(tieId, "Student id cannot be blank!", "Look like student id is empty");
        this.validTextChanged(tieName, "Student name cannot be blank!", "Look like student name is empty");
        this.validateBirthDate(tieBirthday);
    }

    private void validTextChanged(TextInputEditText editText, String errOnChange, String errAfterChange) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    editText.setError(errOnChange);
                } else {
                    editText.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().trim().isEmpty()) {
                    editText.setError(errAfterChange);
                } else {
                    editText.setError(null);
                }
            }
        });
    }

    private void validateBirthDate(TextInputEditText dateEditText) {
        dateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String date = dateEditText.getText().toString();

                if (!isValidDate(date)) {
                    dateEditText.setError("Invalid Date! Birth date have format dd/MM/yyyy and year less than 2020");
                } else {
                    dateEditText.setError(null);
                }
            }
        });
    }

    public boolean isValidDate(String dateString) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        dateFormat.setLenient(false);

        try {
            Date date = dateFormat.parse(dateString);

            // Check if date is before 1/1/2020
            Calendar cal = Calendar.getInstance();
            cal.set(2020, 0, 1);
            Date minDate = cal.getTime();

            if(date.before(minDate)) {
                return true;
            } else {
                return false;
            }

        } catch (ParseException e) {
            return false;
        }

    }

    private void clearForm() {
        tieId.getText().clear();
        tieName.getText().clear();
        tieBirthday.getText().clear();

        tieId.setError(null);
        tieName.setError(null);
        tieBirthday.setError(null);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        this.fillStudentsToListView();
    }
}