package ro.pub.cs.systems.eim.practicaltest01var06;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01Var06MainActivity extends AppCompatActivity {

    private Button statusBtn = null;
    private Button checkBtn = null;
    private EditText internetEditText = null;
    private EditText infoEditText = null;
    private Button goSecond = null;
    private final static int SECONDARY_ACTIVITY_REQUEST_CODE = 1;
    int serviceStatus = Constants.SERVICE_STOPPED;
    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private IntentFilter intentFilter = new IntentFilter();
    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("internetAdd"));
        }
    }


    public class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();

            switch (id) {
                case R.id.statusButton:
                    if (statusBtn.getText().toString().equals("Less details")) {
                        findViewById(R.id.less_showInterface).setVisibility(View.VISIBLE);
                        statusBtn.setText("More details");
                    } else {
                        findViewById(R.id.less_showInterface).setVisibility(View.GONE);
                        statusBtn.setText("Less details");
                    }
                    break;
                case R.id.goButton:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01Var06SecondaryActivity.class);
                    intent.putExtra("internet", internetEditText.getText().toString());
                    intent.putExtra("status",infoEditText.getText().toString());
                    startActivityForResult(intent, SECONDARY_ACTIVITY_REQUEST_CODE);
                    break;
            }

        }
    }

    private TextListener textListener = new TextListener();
    public class TextListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start,
                                      int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {
            if (s.toString().startsWith("http://")) {
                //checkBtn.setBackground(getApplicationContext(), getDrawable(R.color.green));
                checkBtn.setBackground(getApplicationContext().getResources().getDrawable(R.color.green));
                checkBtn.setText("Pass");
            } else {
                checkBtn.setBackground(getApplicationContext().getResources().getDrawable(R.color.red));
                checkBtn.setText("Fail");
            }

            if (checkBtn.getText().toString().equals("Pass")
                    && serviceStatus == Constants.SERVICE_STOPPED) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Var06Service.class);
                intent.putExtra("internetAdd", internetEditText.getText().toString());
                getApplicationContext().startService(intent);
                serviceStatus = Constants.SERVICE_STARTED;
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var06_main);
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

        statusBtn = (Button)findViewById(R.id.lessButton);
        statusBtn.setOnClickListener(buttonClickListener);

        internetEditText = (EditText)findViewById(R.id.secondEditText);
        internetEditText.addTextChangedListener(textListener);

        checkBtn = (Button)findViewById(R.id.statusButton);

        infoEditText = (EditText)findViewById(R.id.firstEditText);


        goSecond = (Button)findViewById(R.id.goButton);
        goSecond.setOnClickListener(buttonClickListener);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("firstText")) {
                infoEditText.setText(savedInstanceState.getString("firstText"));
            } else {
                infoEditText.setText("");
            }
            if (savedInstanceState.containsKey("secondText")) {
                internetEditText.setText(savedInstanceState.getString("secondText"));
            } else {
                internetEditText.setText("");
            }
            String message = savedInstanceState.getString("firstText") + "," + savedInstanceState.getString("secondText");
            Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
        }


        intentFilter.addAction(Constants.actionTypes[0]);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("firstText", infoEditText.getText().toString());
        savedInstanceState.putString("secondText", internetEditText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey("firstText")) {
            infoEditText.setText(savedInstanceState.getString("firstText"));
        } else {
            infoEditText.setText("");
        }
        if (savedInstanceState.containsKey("secondText")) {
            internetEditText.setText(savedInstanceState.getString("secondText"));
        } else {
            internetEditText.setText("");
        }
    }


    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var06Service.class);
        stopService(intent);
        super.onDestroy();
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_practical_test01_var06_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
