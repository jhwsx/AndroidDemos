package com.wzc.sclockernotificationdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;


public class MainActivity extends AppCompatActivity {

    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification();
            }
        });
    }

    private void showNotification() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(mContext);
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification_view);
        int requestCode = 1000;
        Intent intent = new Intent(mContext, MyReceiver.class);
        intent.setAction(MyReceiver.ACTION_MYRECEIVER);
        PendingIntent pi = PendingIntent.getBroadcast(mContext, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        contentView.setOnClickPendingIntent(R.id.check_right_now,pi);
        int interceptTime = 5;
        String interceptText = mContext.getString(R.string.notification_intercept_attack_time_text_view, String.valueOf(interceptTime));
        contentView.setTextViewText(R.id.notification_intercept_time,getSpannableString(interceptText,String.valueOf(interceptTime)));
        mBuilder.setContent(contentView)
                .setSmallIcon(R.drawable.notification_small_icon)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(false)
                .setOngoing(true);

        Notification notification = mBuilder.build();
        notification.flags = Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;
        mNotificationManager.notify(1, notification);
    }

    public CharSequence getSpannableString(String text, String size) {
        int startIndex = text.indexOf(size);
        if(startIndex == -1)
            return text;

        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.rgb(0x51, 0x79, 0xff));
        StyleSpan styleSpanBold = new StyleSpan(Typeface.BOLD);

        SpannableString amountS = new SpannableString(text);
        amountS.setSpan(redSpan, startIndex, startIndex + size.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        amountS.setSpan(styleSpanBold, startIndex, startIndex + size.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return amountS;
    }
}
//        try {
//            // 获取到SetOnClickPendingIntent对象
//            Class<?> setOnClickPendingIntentClass = Class.forName("android.widget.RemoteViews$SetOnClickPendingIntent");
//            // public SetOnClickPendingIntent(int id, PendingIntent pendingIntent)
//            Constructor<?>[] constructors = setOnClickPendingIntentClass.getConstructors();
//            Constructor<?> constructor = setOnClickPendingIntentClass.getConstructor(RemoteViews.class, int.class, PendingIntent.class);
//            constructor.setAccessible(true);
////            Object setOnClickPendingIntentObj = constructor.newInstance(contentView, R.id.linearlayout1, pi);
//
////            // 代理掉SetOnClickPendingIntent类的apply()方法,获取到root对象,进而获取点击的对象
////            Object hookSetOnClickPendingIntentObj = Proxy.newProxyInstance(setOnClickPendingIntentObj.getClass().getClassLoader(),
////                    setOnClickPendingIntentClass.getInterfaces(), new HookHandler(setOnClickPendingIntentObj));
//            // 获取addAction方法 private void addAction(Action a)
//            Class<?> actionClass = Class.forName("android.widget.RemoteViews$Action");
//            Method addActionMethod = RemoteViews.class.getDeclaredMethod("addAction", actionClass);
//            addActionMethod.setAccessible(true);
////            addActionMethod.invoke(contentView, setOnClickPendingIntentObj);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
