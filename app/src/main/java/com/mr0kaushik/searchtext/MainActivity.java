package com.mr0kaushik.searchtext;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tvError;
    EditText editText;
    ImageButton btnSearch;

    boolean isPressedTwice = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search Text");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        tvError = findViewById(R.id.tv_error);
        editText = findViewById(R.id.editText);
        btnSearch = findViewById(R.id.btn_search);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length()!=0){
                    tvError.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().trim().length()!=0){
                    /**
                     * Rest of the code fro intent call
                     */
//                    Toast.makeText(MainActivity.this, "Switching Activity", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), OcrActivity.class);
                    Log.i("Search Text Main", "onClick: MainActivity : " + editText.getText().toString());
                    intent.putExtra("search_value", editText.getText().toString());
                    Log.i("MainActivity", "onClick: Switching Activity between MainActivity to Camera Activity.");
                    startActivity(intent);

                } else {
                    tvError.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        if (isPressedTwice) {
            super.onBackPressed();
            return;
        }

        this.isPressedTwice = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isPressedTwice=false;
            }
        }, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.help:
                openHelpActivity();
                break;
            case R.id.about:
                openAboutActivity();
                break;
            case R.id.send_feedback:
                boolean sendReport = sendFeedback();
                if (sendReport){
                    Toast.makeText(this, "Opening Email", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean sendFeedback() {
        Intent Email = new Intent(Intent.ACTION_SEND);
        Email.setType("text/email");
        Email.putExtra(Intent.EXTRA_EMAIL, new String[] { "thor.jane.odinson@gmail.com" });
        Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback for the Search Text app");
        Email.putExtra(Intent.EXTRA_TEXT, "Dear, " + "");
        startActivity(Intent.createChooser(Email, "Send Feedback:"));
        return true;
    }

    private void openHelpActivity() {
        Intent intent = new Intent(getApplicationContext(), HelpActivity.class);
        startActivity(intent);
    }

    public void openAboutActivity(){
        Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(intent);
    }
}
