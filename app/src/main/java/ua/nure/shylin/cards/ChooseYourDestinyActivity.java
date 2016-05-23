package ua.nure.shylin.cards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ChooseYourDestinyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_your_destiny);
    }

    public void goTesting(View view){
        // Обработка нажатия кнопки "Проверить знания"
        Intent intent = new Intent(this, ChooseTestActivity.class);
        startActivity(intent);
    }

    public void startEdit(View view){
        // Обработка нажатия кнопки "Редактировать задания"
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }
}
