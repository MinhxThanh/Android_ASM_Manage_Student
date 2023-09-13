package pro.edu.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import pro.edu.MainActivity;
import pro.edu.R;

public class SplashPageActivity extends AppCompatActivity {
    private int percent = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set fullscreen mode for activity
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Set content view to the splash screen layout
        setContentView(R.layout.activity_splash_page);

        // Get the references to the views in the layout.
        ConstraintLayout clSplashPage = (ConstraintLayout) findViewById(R.id.clSplashPage);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.pbLoading);
        TextView tvProgress = (TextView) findViewById(R.id.tvProgress);
        ImageView ivLogo = (ImageView) findViewById(R.id.ivLogo);

        // Create a handler and a runnable.
        // Handler for repeating progress update
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // If the progress is less than 100, increment the progress and update the UI.
                if (percent < 100) {
                    percent += 1;
                    progressBar.setProgress(percent);
                    tvProgress.setText(percent + "%");
                    // Repeat every 10ms
                    handler.postDelayed(this, 8);
                } else if (percent == 100) {
                    // If the progress is 100, hide the progress bar and text view, show the logo.
                    try {
                        progressBar.setVisibility(View.INVISIBLE);
                        tvProgress.setVisibility(View.INVISIBLE);
                        Thread.sleep(200);
                        ivLogo.setVisibility(View.VISIBLE);
                        // Animate fade out splash screen
                        ivLogo.setAnimation(getAnimationFadeOut(100, 1000));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        };
        // Start progress bar animation
        handler.post(runnable);

        // Switch to main activity after 3 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent startLandingPageActivity = new Intent(SplashPageActivity.this, LoginActivity.class);
                startActivity(startLandingPageActivity);
                finish();
            }
        }, 3000);
    }

    @NonNull
    private static Animation getAnimationFadeOut(int startOffset, int endOffset) {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(startOffset);
        fadeOut.setDuration(endOffset);
        return fadeOut;
    }
}