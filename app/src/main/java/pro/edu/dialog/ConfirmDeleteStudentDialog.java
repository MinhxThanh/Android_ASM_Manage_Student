package pro.edu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import pro.edu.R;
import pro.edu.model.Students;
import pro.edu.sqlite.ClassesDao;
import pro.edu.sqlite.StudentDao;

public class ConfirmDeleteStudentDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private TextView tvConfirmAsk;
    private Students student;

    public ConfirmDeleteStudentDialog(@NonNull Context context, Students student) {
        super(context);
        this.context = context;
        this.student = student;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.confirm_delete_dialog);

        initControl();
    }

    private void initControl() {
        tvConfirmAsk = (TextView) findViewById(R.id.tvConfirmAsk);
        tvConfirmAsk.setText("Do you want delete student '" + student.getId() + "'?");

        findViewById(R.id.btnConfirmYes).setOnClickListener(this);
        findViewById(R.id.btnConfirmNo).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnConfirmYes) {
            new StudentDao(context).delete(student.getId());
            Toast.makeText(context, "Delete student '" + student.getId() + "' successfully!", Toast.LENGTH_SHORT).show();
            dismiss();
        } else if (id == R.id.btnConfirmNo) {
            dismiss();
        }
    }
}
