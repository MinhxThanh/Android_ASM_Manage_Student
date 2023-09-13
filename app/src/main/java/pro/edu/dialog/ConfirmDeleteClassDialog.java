package pro.edu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import pro.edu.R;
import pro.edu.activity.ListClassActivity;
import pro.edu.adapter.ClassesAdapter;
import pro.edu.model.Classes;
import pro.edu.sqlite.ClassesDao;

public class ConfirmDeleteClassDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private TextView tvConfirmAsk;
    private Classes classes;
    public ConfirmDeleteClassDialog(@NonNull Context context, Classes classes) {
        super(context);
        this.context = context;
        this.classes = classes;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.confirm_delete_dialog);

        initControl();
    }

    private void initControl() {
        tvConfirmAsk = (TextView) findViewById(R.id.tvConfirmAsk);
        tvConfirmAsk.setText("Do you want delete class '" + classes.getCode() + "'?");

        findViewById(R.id.btnConfirmYes).setOnClickListener(this);
        findViewById(R.id.btnConfirmNo).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnConfirmYes) {
            new ClassesDao(context).delete(classes.getId());
            Toast.makeText(context, "Delete class '" + classes.getName() + "' successfully!", Toast.LENGTH_SHORT).show();
            dismiss();
        } else if (id == R.id.btnConfirmNo) {
            dismiss();
        }
    }
}
