package com.ayuan.loadbigimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loadImage = (Button) findViewById(R.id.btn_loadimage);
        imageView = (ImageView) findViewById(R.id.iv_bitmap);
        //点击按钮加载大图片
        loadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Options:作用：创建一个位图工厂的配置参数
                BitmapFactory.Options options = new BitmapFactory.Options();

                //此参数如果设置为true，这个decode不会真的去解析位图，但是还能够获取图片的宽和高信息
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile("/mnt/sdcard/DCIM/a.bmp", options);

                //获取图片的分辨率
                int outHeight = options.outHeight;
                int outWidth = options.outWidth;

                //获取手机的分辨率  使用WindowManager
                WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
                //获取手机的分辨率
                Display display = windowManager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                int width = point.x;
                int height = point.y;

                //计算缩放比
                int scale = 1;
                int scaleX = outWidth / width;
                int scaleY = outHeight / height;
                if (scaleX >= scaleY && scaleY > scale) {
                    scale = scaleX;
                } else if (scaleY > scaleX && scaleX > scale) {
                    scale = scaleY;
                }

                //按照缩放比进行显示
                options.inSampleSize = scale;
                //按照缩放比进行解析位图
                options.inJustDecodeBounds = false;
                Bitmap bitmap = BitmapFactory.decodeFile("/mnt/sdcard/DCIM/a.bmp", options);
                //把bitmap图片显示到控件中
                imageView.setImageBitmap(bitmap);

            }
        });
    }
}
