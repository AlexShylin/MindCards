package ua.nure.shylin.cards;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class InputTestNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_test_name);
    }

    public void addTest(View view){
        //TODO доделать запись в БД и передачу Id в следующую активити
    }
}
