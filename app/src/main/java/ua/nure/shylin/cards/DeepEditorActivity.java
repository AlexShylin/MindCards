package ua.nure.shylin.cards;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

public class DeepEditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deep_editor);

        long id=0;
        long tests_id=0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getLong("id");
            tests_id = extras.getLong("tests_id");
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, PlaceholderFragment.newInstance(id, tests_id))
                    .commit();
        }
    }

    public static class PlaceholderFragment extends Fragment {

        EditText quesBox;
        EditText answerBox;
        Button delButton;
        Button saveButton;

        DatabaseHelper sqlHelper;
        SQLiteDatabase db;
        Cursor userCursor;

        public static PlaceholderFragment newInstance(long id, long tests_id) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args=new Bundle();
            args.putLong("id", id);
            args.putLong("tests_id", tests_id);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);

            sqlHelper = new DatabaseHelper(getActivity());
        }
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_user_deep, container, false);
            quesBox = (EditText) rootView.findViewById(R.id.question);
            answerBox = (EditText) rootView.findViewById(R.id.answer);
            delButton = (Button) rootView.findViewById(R.id.delete);
            saveButton = (Button) rootView.findViewById(R.id.save);

            final long tests_id = getArguments().getLong("tests_id");
            final long id = getArguments().size()>1 ? getArguments().getLong("id") : 0;

            db = sqlHelper.getWritableDatabase();
            // кнопка удаления
            delButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    db.execSQL("DELETE FROM cards where _id = " + String.valueOf(id) + " and tests_id = " + String.valueOf(tests_id));
                    goHome();
                }
            });

            // кнопка сохранения
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (id > 0) {
                        db.execSQL("UPDATE cards SET " +
                                " question = '" + quesBox.getText().toString() + "', " +
                                " answer = '" + answerBox.getText().toString() + "' " +
                                "WHERE _id = " + String.valueOf(id) + " and tests_id = " + String.valueOf(tests_id));
                    } else {
                        //TODO исправить ошибку при добавлении нового вопроса
                        db.execSQL("INSERT INTO cards (question,answer,tests_id) VALUES ('" +
                                quesBox.getText().toString() + "','" + answerBox.getText().toString() + "'," + String.valueOf(tests_id)+ ");");
                    }
                    goHome();
                }
            });

            // если 0, то добавление
            if (id > 0) {
                // получаем элемент по id из бд
                userCursor = db.rawQuery("select * from cards where _id=?", new String[]{String.valueOf(id)});
                userCursor.moveToFirst();
                quesBox.setText(userCursor.getString(1));
                answerBox.setText(String.valueOf(userCursor.getString(2)));
                userCursor.close();
            } else {
                // скрываем кнопку удаления
                delButton.setVisibility(View.GONE);
            }
            return rootView;
        }

        public void goHome(){
            // закрываем подключение
            db.close();
            // переход к главной activity
            Intent intent = new Intent(getActivity(), EditQActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
    }
}
