package ua.nure.shylin.cards;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.ListView;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

public class StartActivity extends ActionBarActivity {

    ListView mList;
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mList = (ListView)findViewById(R.id.list);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
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
        userCursor =  db.rawQuery("select * from tests", null);
        String[] headers = new String[] {"name"};
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, userCursor, headers, new int[]{android.R.id.text1});
        mList.setAdapter(userAdapter);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключения
        db.close();
        userCursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(0        // Группа
                , 1        // id
                , 0        //порядок
                , "Добавить");  // заголовок
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == 1) {

            Intent intent = new Intent(getApplicationContext(), EditActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
