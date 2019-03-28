package com.example.admin.notificationtest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button sendNotice;

    /**
     *
     * 从Android O开始 每个通知必须为其设置对应的channel **
     * channel的构造函数有三个参数 channel的id,name，和优先级。 创建完channel后通过 NotificationManager的createNotificationChannel(channel)方法添加chanel，并在在notification里设置setChannelId。
     * 通常 通知会在广播接收器(broadcastReciver)或者服务(Server)中创建，不在活动中创建，因为app给我们发送通知的时候并不是运行在前台。
     * 作者：Limmerence
     * 来源：CSDN
     * 原文：https://blog.csdn.net/cyyyy_da/article/details/82938613
     * 版权声明：本文为博主原创文章，转载请附上博文链接！
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendNotice = (Button) findViewById(R.id.send_notice);
        sendNotice.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.send_notice:
                        String CHANNEL_ID = "my_channel_01";
                        CharSequence name = getString(R.string.channel_01);
                        String description = "你好世界";
                        int importance = NotificationManager.IMPORTANCE_LOW;

                        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        //Android 8.0开始需要为通知设置channel
                        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                        channel.setDescription(description);
                        channel.enableLights(true);
                        channel.setLightColor(Color.RED);
                        channel.enableVibration(true);
                        channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

                        manager.createNotificationChannel(channel);
                        Notification notification = new NotificationCompat.Builder(MainActivity.this).
                                setContentTitle("This is content Title").
                                setContentText("This is content text").
                                setWhen(System.currentTimeMillis()).
                                setSmallIcon(R.mipmap.ic_launcher).
                                setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)).
                                setChannelId(CHANNEL_ID).
                                build();
                        manager.notify(1, notification);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
