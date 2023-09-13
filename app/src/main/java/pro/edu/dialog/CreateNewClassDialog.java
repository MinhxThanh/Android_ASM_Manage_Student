package pro.edu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;

import pro.edu.R;
import pro.edu.model.Classes;
import pro.edu.sqlite.ClassesDao;

public class CreateNewClassDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private TextInputEditText tieId, tieCode, tieName;

    public CreateNewClassDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_create_new_class);

        tieId = (TextInputEditText) findViewById(R.id.tieId);
        tieCode = (TextInputEditText) findViewById(R.id.tieCode);
        tieName = (TextInputEditText) findViewById(R.id.tieName);

        findViewById(R.id.btnSave).setOnClickListener(this);
        findViewById(R.id.btnExit).setOnClickListener(this);

        this.validateInput();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnSave) {
            Classes classes = new Classes();

            classes.setCode(tieCode.getText().toString());
            classes.setName(tieName.getText().toString());

            new ClassesDao(context).insert(classes);

            Toast.makeText(context, "New class is added!", Toast.LENGTH_SHORT).show();

            dismiss();
        } else if (id == R.id.btnExit) {
            dismiss();
        }

    }

    private void validateInput() {
//        validate id input
        tieId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    tieId.setError("Class id cannot be blank!");
                } else {
                    tieId.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().trim().isEmpty()) {
                    tieId.setError("Looks like class id is empty");
                } else {
                    tieId.setError(null);
                }
            }
        });
//        validate code input
        tieCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    tieCode.setError("Class code cannot be blank!");
                } else {
                    tieCode.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().trim().isEmpty()) {
                    tieCode.setError("Looks like class code is empty");
                } else {
                    tieCode.setError(null);
                }
            }
        });

//        validate name input
        tieName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().isEmpty()) {
                    tieName.setError("Class name cannot be blank!");
                } else {
                    tieName.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().trim().isEmpty()) {
                    tieName.setError("Looks like class name is empty");
                } else {
                    tieName.setError(null);
                }
            }
        });
    }
}
