package ro.pub.cs.systems.eim.practicaltest01var02;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01Var02MainActivity extends AppCompatActivity {

    Button plus, minus, changeActivity;
    EditText editText1, editText2, editText3;

    private IntentFilter intentFilter = new IntentFilter();
    private boolean serviceRun = false;

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("wow", intent.getStringExtra("message"));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var02_main);

        intentFilter.addAction("1");

        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        changeActivity = findViewById(R.id.changeActivity);
        editText1 = findViewById(R.id.editext1);
        editText2 = findViewById(R.id.editext2);
        editText3 = findViewById(R.id.editext3);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeOperation("+");
                startService1();
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeOperation("-");
                startService1();
            }
        });

        changeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PracticalTest01Var02MainActivity.this, PracticalTest01Var02SecondaryActivity.class);
                intent.putExtra("rez", editText3.getText().toString()); //Optional parameters
                PracticalTest01Var02MainActivity.this.startActivity(intent);
            }
        });

        Intent intent = this.getIntent();
        if (intent != null) {
            String result = intent.getStringExtra("rez");
            if (result != null) {
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void startService1() {
        if (!serviceRun) {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01Var02Service.class);
            intent.putExtra("val1", editText1.getText().toString());
            intent.putExtra("val2", editText2.getText().toString());
            getApplicationContext().startService(intent);
            serviceRun = true;
        }
    }

    public void makeOperation(String operation) {
        if (editText1.getText() == null || editText2.getText() == null || editText2.getText().toString().isEmpty() || editText1.getText().toString().isEmpty()) {
            Toast.makeText(this, "invalid numbers", Toast.LENGTH_SHORT).show();
        } else {
            int val1 = Integer.valueOf(editText1.getText().toString());
            int val2 = Integer.valueOf(editText2.getText().toString());
            if (operation.equals("+")) {
                int sum = val1 + val2;
                editText3.setText(val1 + operation + val2 + "=" + sum);
            } else if (operation.equals("-")) {
                int dif = val1 - val2;
                editText3.setText(val1 + operation + val2 + "=" + dif);
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("edit1", editText1.getText().toString());
        outState.putString("edit2", editText2.getText().toString());
        outState.putString("edit3", editText3.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            String edit1 = savedInstanceState.getString("edit1");
            String edit2 = savedInstanceState.getString("edit2");
            String edit3 = savedInstanceState.getString("edit3");
            if (edit1 != null && edit2 != null && edit3 != null) {
                editText1.setText(edit1);
                editText2.setText(edit2);
                editText3.setText(edit3);
            }

        }
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
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var02Service.class);
        stopService(intent);
        super.onDestroy();
    }
}
