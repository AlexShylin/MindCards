package ua.nure.shylin.cards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void startCards(View view){
        // Обработка нажатия кнопки "Начать"
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    public void openInfo(View view){
        // Обработка нажатия кнопки "Справка"
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }

    public void exitApp(View view) {
        // Обработка нажатия кнопки "Выход"
        this.finish();
    }
}
