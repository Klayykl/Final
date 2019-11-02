package com.example.afinal;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends Activity implements OnClickListener {

    private Button textbtn, imgbtn, videobtn;
    private ListView lv;
    private Intent i;
    private adapter adapter;
    private NotesDB notesDB;
    private SQLiteDatabase dbReader;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {
        lv = (ListView) findViewById(R.id.list);
        textbtn = (Button) findViewById(R.id.text);
        imgbtn = (Button) findViewById(R.id.img);
        videobtn = (Button) findViewById(R.id.video);
//显示数据到控件
        textbtn.setOnClickListener(this);
        imgbtn.setOnClickListener(this);
        videobtn.setOnClickListener(this);


        notesDB = new NotesDB(this);
        dbReader = notesDB.getReadableDatabase();

        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                cursor.moveToPosition(position);
                Intent i = new Intent(MainActivity.this, Select.class);
                i.putExtra(NotesDB.ID,
                        cursor.getInt(cursor.getColumnIndex(NotesDB.ID)));
                i.putExtra(NotesDB.CONTENT, cursor.getString(cursor
                        .getColumnIndex(NotesDB.CONTENT)));
                i.putExtra(NotesDB.TIME,
                        cursor.getString(cursor.getColumnIndex(NotesDB.TIME)));
                i.putExtra(NotesDB.PATH,
                        cursor.getString(cursor.getColumnIndex(NotesDB.PATH)));
                i.putExtra(NotesDB.VIDEO,
                        cursor.getString(cursor.getColumnIndex(NotesDB.VIDEO)));
                startActivity(i);
            }
        });

    }

    /**
     * 点击添加数据
     */
    @Override
    public void onClick(View v) {
        i = new Intent(this, show.class);
        switch (v.getId()) {
            case R.id.text:
                i.putExtra("flag", "1");
                startActivity(i);
                break;

            case R.id.img:
                i.putExtra("flag", "2");
                startActivity(i);
                break;

            case R.id.video:
                i.putExtra("flag", "3");
                startActivity(i);
                break;
        }
    }

    /**
     * listview的数据源
     */
    public void selectDB() {
        cursor = dbReader.query(NotesDB.TABLE_NAME, null, null, null, null,
                null, null);
        adapter = new adapter(this, cursor);
        lv.setAdapter(adapter);
    }

    //在点击保存数据 回到主界面后调用
    @Override
    protected void onResume() {
        super.onResume();
        selectDB();
    }


    public static class NotesDB extends SQLiteOpenHelper {

        public static final String TABLE_NAME = "notes";
        public static final String CONTENT = "content";
        public static final String PATH = "path";
        public static final String VIDEO = "video";
        public static final String ID = "_id";
        public static final String TIME = "time";

        public NotesDB(Context context) {
            super(context, "notes", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT," + CONTENT
                    + " TEXT NOT NULL," + PATH + " TEXT NOT NULL," + VIDEO
                    + " TEXT NOT NULL," + TIME + " TEXT NOT NULL)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    }
}