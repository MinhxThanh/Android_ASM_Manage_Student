package pro.edu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import pro.edu.activity.ListClassActivity;
import pro.edu.activity.LoginActivity;
import pro.edu.activity.ManageStudents;
import pro.edu.dialog.CreateNewClassDialog;

public class MainActivity extends AppCompatActivity {
    private Button btnCreateClass, btnListClass, btnManageStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.onClickButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itChangePassword) {
            Toast.makeText(this, "Change password in development!", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.itSignOut) {
            this.clearAutoLogin();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }

    private void onClickButton () {
//        button open dialog create new class
        findViewById(R.id.btnCreateClass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateNewClassDialog dialog = new CreateNewClassDialog(MainActivity.this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

//        button go to list class
        findViewById(R.id.btnListClass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListClassActivity.class);
                startActivity(intent);
            }
        });

//        button go to management students
        findViewById(R.id.btnManageStudents).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ManageStudents.class);
                startActivity(intent);
            }
        });
    }

    private void clearAutoLogin() {
        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.clear();

        editor.commit();
    }
}