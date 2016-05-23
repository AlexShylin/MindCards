package ua.nure.shylin.cards;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        long userId=0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, PlaceholderFragment.newInstance(userId))
                    .commit();
        }
    }

    public static class PlaceholderFragment extends Fragment {

        EditText nameBox;
        Button delButton;
        Button saveButton;
        Button editQuesButton;

        DatabaseHelper sqlHelper;
        SQLiteDatabase db;
        Cursor userCursor;

        public static PlaceholderFragment newInstance(long id) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args=new Bundle();
            args.putLong("id", id);
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
            View rootView = inflater.inflate(R.layout.fragment_user, container, false);
            nameBox = (EditText) rootView.findViewById(R.id.name);
            delButton = (Button) rootView.findViewById(R.id.delete);
            saveButton = (Button) rootView.findViewById(R.id.save);
            editQuesButton = (Button) rootView.findViewById(R.id.edit_question);

            final long id = getArguments() != null ? getArguments().getLong("id") : 0;

            db = sqlHelper.getWritableDatabase();
            // кнопка удаления
            delButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    db.delete("tests", "_id = ?", new String[]{String.valueOf(id)});
                    goHome();
                }
            });

            // кнопка сохранения
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContentValues cv = new ContentValues();
                    cv.put("name", nameBox.getText().toString());

                    if (id > 0) {
                        db.update("tests", cv, "_id" + "=" + String.valueOf(id), null);
                    } else {
                        db.insert("tests", null, cv);
                    }
                    goHome();
                }
            });

            // кнопка редактирования вопросов
            editQuesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), EditQActivity.class);
                    intent.putExtra("tests_id", id);
                    startActivity(intent);
                }
            });

            // если 0, то добавление
            if (id > 0) {
                // получаем элемент по id из бд
                userCursor = db.rawQuery("select * from " + "tests" + " where " +
                        "_id" + "=?", new String[]{String.valueOf(id)});
                userCursor.moveToFirst();
                nameBox.setText(userCursor.getString(1));
                userCursor.close();
            } else {
                // скрываем кнопку удаления
                delButton.setVisibility(View.GONE);
                editQuesButton.setVisibility(View.GONE);
            }
            return rootView;
        }

        public void goHome(){
            // закрываем подключение
            db.close();
            // переход к главной activity
            Intent intent = new Intent(getActivity(), StartActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
    }
}
