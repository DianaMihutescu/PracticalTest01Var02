package ro.pub.cs.systems.eim.practicaltest01var02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PracticalTest01Var02SecondaryActivity extends AppCompatActivity {

    EditText text1;
    Button correct, incorrect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var02_secondary);

        text1 = findViewById(R.id.text1);
        correct = findViewById(R.id.correct);
        incorrect = findViewById(R.id.incorrect);

        Intent intent = this.getIntent();
        String rezult = intent.getStringExtra("rez");
        text1.setText(rezult);

        correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PracticalTest01Var02SecondaryActivity.this, PracticalTest01Var02MainActivity.class);
                intent.putExtra("rez", "Correct"); //Optional parameters
                PracticalTest01Var02SecondaryActivity.this.startActivity(intent);
            }
        });

        incorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PracticalTest01Var02SecondaryActivity.this, PracticalTest01Var02MainActivity.class);
                intent.putExtra("rez", "InCorrect"); //Optional parameters
                PracticalTest01Var02SecondaryActivity.this.startActivity(intent);
            }
        });
    }
}
