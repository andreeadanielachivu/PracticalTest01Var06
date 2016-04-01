package ro.pub.cs.systems.eim.practicaltest01var06;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PracticalTest01Var06SecondaryActivity extends AppCompatActivity {

    private Button okBtn = null;
    private Button cancelBtn = null;
    private EditText internetText = null;
    private EditText statusText = null;


    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.okBtn:
                    setResult(RESULT_OK, null);
                    break;
                case R.id.cancelBtn:
                    setResult(RESULT_CANCELED, null);
                    break;
            }
            finish();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var06_secondary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        okBtn = (Button)findViewById(R.id.okBtn);
        cancelBtn = (Button)findViewById(R.id.cancelBtn);

        internetText = (EditText)findViewById(R.id.internet);
        statusText = (EditText)findViewById(R.id.status);

        //get info from intent

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("internet") && intent.getExtras().containsKey("status")) {
            String address = intent.getStringExtra("internet");
            String statusMsg = intent.getStringExtra("status");
            internetText.setText(address);
            statusText.setText(statusMsg);
        }


        okBtn.setOnClickListener(buttonClickListener);

        cancelBtn.setOnClickListener(buttonClickListener);
    }

}
