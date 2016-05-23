package ua.nure.shylin.cards;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class GoTestingActivity extends AppCompatActivity {
    Cursor testsCursor;
    //SimpleCursorAdapter quesAdapter;
    //SimpleCursorAdapter answerAdapter;
    ArrayList<String> ques = new ArrayList<String>();
    ArrayList<String> ans = new ArrayList<String>();
    boolean isQuestion = true;
    int currentQuestion = 0;
    int prevQuestion = 0;
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    public static long tests_id = 0;

    Button text;
    Button next;
    TextView tw;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_testing);

        Bundle extras = getIntent().getExtras();
        long extra = 0;
        extra = extras.getLong("tests_id");
        tests_id = extra;

        text = (Button) findViewById(R.id.button_text);
        next = (Button) findViewById(R.id.button_next);
        tw = (TextView) findViewById(R.id.textView_status);

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isQuestion = !isQuestion;
                if (isQuestion)
                    tw.setText("Вопрос");
                else tw.setText("Ответ");
                if (isQuestion) {
                    text.setText(ques.get(currentQuestion));
                } else {
                    text.setText(ans.get(currentQuestion));
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isQuestion = true;
                TextView tw = (TextView) findViewById(R.id.textView_status);
                tw.setText("Вопрос");

                prevQuestion = currentQuestion;
                if (ques.size() > 1) {
                    while (currentQuestion == prevQuestion) {
                        Random r = new Random();
                        currentQuestion = r.nextInt(ques.size());
                    }
                } else currentQuestion = 0;

                if (isQuestion) {
                    text.setText(ques.get(currentQuestion));
                } else {
                    text.setText(ans.get(currentQuestion));
                }
            }
        });

        sqlHelper = new DatabaseHelper(getApplicationContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = sqlHelper.getReadableDatabase();

        //получаем данные из бд
        testsCursor = db.rawQuery("select * from cards where tests_id = " + tests_id, null);
        testsCursor.moveToFirst();
        while (!testsCursor.isAfterLast()) {
            ques.add(testsCursor.getString(testsCursor.getColumnIndex("question"))); //add the item
            ans.add(testsCursor.getString(testsCursor.getColumnIndex("answer"))); //add the item
            testsCursor.moveToNext();
        }
        if (ques.size() == 0) {
            onDestroy();
        } else {
            goTesting(true);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключения
        db.close();
        testsCursor.close();
    }

    private void goTesting(boolean rnd) {
        if (rnd) {
            if (ques.size() > 1) {
                Random r = new Random();
                currentQuestion = r.nextInt(ques.size());
            } else currentQuestion = 0;
        }

        if (isQuestion) {
            Button b = (Button) findViewById(R.id.button_text);
            b.setText(ques.get(currentQuestion));
        } else {
            Button b = (Button) findViewById(R.id.button_text);
            b.setText(ans.get(currentQuestion));
        }
    }
}
