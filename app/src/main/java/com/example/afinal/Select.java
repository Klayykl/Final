package com.example.afinal;


import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class Select extends Activity implements OnClickListener {

    private Button s_delete, s_back;
    private ImageView s_img;
    private VideoView s_video;
    private TextView s_tv;
    private MainActivity.NotesDB notesDB;
    private SQLiteDatabase dbWriter;
    /** 点击item 的详情页 对数据进行删除操作*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);

        s_delete = (Button) findViewById(R.id.s_delete);
        s_back = (Button) findViewById(R.id.s_back);
        s_img = (ImageView) findViewById(R.id.s_img);
        s_video = (VideoView) findViewById(R.id.s_video);
        s_tv = (TextView) findViewById(R.id.s_tv);
        /**读写权限*/
        notesDB = new MainActivity.NotesDB(this);
        dbWriter = notesDB.getWritableDatabase();

        s_back.setOnClickListener(this);
        s_delete.setOnClickListener(this);
        //判断是否存在图片
        if (getIntent().getStringExtra(MainActivity.NotesDB.PATH).equals("null")) {
            s_img.setVisibility(View.GONE);
        } else {
            s_img.setVisibility(View.VISIBLE);
        }

        if (getIntent().getStringExtra(MainActivity.NotesDB.VIDEO).equals("null")) {
            s_video.setVisibility(View.GONE);
        } else {
            s_video.setVisibility(View.VISIBLE);
        }
        // 设置需要显示的图片
        s_tv.setText(getIntent().getStringExtra(MainActivity.NotesDB.CONTENT));

        Bitmap bitmap = BitmapFactory.decodeFile(getIntent().getStringExtra(
                MainActivity.NotesDB.PATH));
        s_img.setImageBitmap(bitmap);

        s_video.setVideoURI(Uri
                .parse(getIntent().getStringExtra(MainActivity.NotesDB.VIDEO)));

        s_video.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.s_delete:
                deleteDate();
                finish();
                break;

            case R.id.s_back:
                finish();
                break;
        }
    }
    //数据的删除
    public void deleteDate() {
        dbWriter.delete(MainActivity.NotesDB.TABLE_NAME,
                "_id=" + getIntent().getIntExtra(MainActivity.NotesDB.ID, 0), null);
    }
}
