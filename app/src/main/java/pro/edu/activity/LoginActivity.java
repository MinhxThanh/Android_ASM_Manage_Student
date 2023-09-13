package pro.edu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import pro.edu.MainActivity;
import pro.edu.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView ivLogoLoginForm;
    private TextInputEditText tieUsername, tiePassword;
    private CheckBox cbRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initControl();
    }

    private void initControl() {
        tieUsername = (TextInputEditText) findViewById(R.id.tieUsername);
        tiePassword = (TextInputEditText) findViewById(R.id.tiePassword);
        cbRemember = (CheckBox) findViewById(R.id.cbRemember);
        
        ivLogoLoginForm = (ImageView) findViewById(R.id.ivLogoLoginForm);

        findViewById(R.id.btnLogin).setOnClickListener(this);

        Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotation);
        ivLogoLoginForm.startAnimation(rotation);

        this.validateInput();

        this.readAutoLogin();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogin) {
            if (tieUsername.getText().toString().equals("admin") && tiePassword.getText().toString().equals("admin")) {
               if (cbRemember.isChecked()) {
                   this.saveAutoLogin();
               } else {
                   this.clearAutoLogin();
               }
               Intent intent = new Intent(this, MainActivity.class);
               startActivity(intent);
               finish();
            } else {
                Snackbar.make(view, "Please enter username and password correctly!", Toast.LENGTH_SHORT).show();
            }
           
        }
    }

    private void clearAutoLogin() {
        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.clear();

        editor.commit();
    }

    private boolean readAutoLogin() {
        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        String username = preferences.getString("username", "");
        String password = preferences.getString("password", "");

        if (username != null && password != null && !username.equals("") && !password.equals("")) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }

    private void saveAutoLogin() {
        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("username", tieUsername.getText().toString());
        editor.putString("password", tiePassword.getText().toString());

        editor.commit();
    }
    
    private void validateInput() {
        this.validTextChanged(tieUsername, "Username can't be blank!", "Please enter username!");
        this.validTextChanged(tiePassword, "Password can't be blank!", "Please enter password!");
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

}