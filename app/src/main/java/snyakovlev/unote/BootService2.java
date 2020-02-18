package snyakovlev.unote;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;


public  class BootService2 extends Service
{


 @Override
 public void onCreate() {
     super.onCreate();


 }

 @Override
 public int onStartCommand(Intent intent, int flags, int startId) {

     this.startForeground(startId,(new not(getApplication()).getNot()));

     if (intent!=null && intent.getStringExtra("str") instanceof String)

     {


         if (intent.getStringExtra("str").equals("start")) {
             Log.v("notic4","yes");

                 AlarmFrag.saveAlarm(getApplicationContext());
                 this.stopForeground(true);
                 this.stopSelf();


         }
     }



     return super.onStartCommand(intent, flags, startId);
 }

 @Nullable
 @Override
 public IBinder onBind(Intent intent) {
     return null;
 }



}
class not
{
    Notification notification;
     not(Context ctx) {
        NotificationManager manager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder nb = new Notification.Builder(ctx)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentText(ctx.getResources().getString(R.string.bs2));
        notification = nb.build();
        manager.notify(100, notification);
    }

    Notification getNot()
    {
        return notification;
    }
}
