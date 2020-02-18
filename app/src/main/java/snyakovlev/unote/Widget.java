package snyakovlev.unote;

/**
 * Created by Катя on 11.11.2016.
 */
import java.util.Arrays;
import java.util.Calendar;

import android.Manifest;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class Widget extends AppWidgetProvider {

     static int id_wid;

   static final String LOG_TAG = "myLogs";

    static final String prefics="com.yakovlev.sergey.android.notebook.";

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d(LOG_TAG, "onEnabled");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        Log.d(LOG_TAG,"onUpdate");

     //   context.startService(new Intent(context,UpdateService.class));

        for (int appWidgetId : appWidgetIds) {
            updateAll(context, appWidgetId);
        }

    }

      void updateAll(Context context,int appWidgetId)

     {
         Log.d("rrrttt","update");


         RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

         PendingIntent pIntent;



         Intent photo = new Intent(context, Widget.class);

         photo.setAction(prefics+"q_photo");

         pIntent = PendingIntent.getBroadcast(context, 0, photo, 0);

         views.setOnClickPendingIntent(R.id.q_photo, pIntent);



         Intent paint = new Intent(context, Widget.class);

         paint.setAction(prefics+"q_paint");


         pIntent = PendingIntent.getBroadcast(context, 0, paint, 0);

         views.setOnClickPendingIntent(R.id.q_paint, pIntent);



         Intent text = new Intent(context, Widget.class);

         text.setAction(prefics+"q_text");

         pIntent = PendingIntent.getBroadcast(context, 0, text, 0);

         views.setOnClickPendingIntent(R.id.q_text, pIntent);




         Intent mickr = new Intent(context, Widget.class);

         mickr.setAction(prefics+"q_mickr");

         pIntent = PendingIntent.getBroadcast(context,0, mickr, 0);

         views.setOnClickPendingIntent(R.id.q_mickr, pIntent);

         ComponentName thisWidget = new ComponentName(context, Widget.class);
         AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
         if (appWidgetId == -1) {
             appWidgetManager.updateAppWidget(thisWidget, views);
         } else {
             appWidgetManager.updateAppWidget(appWidgetId, views);
         }

     }




     @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.d(LOG_TAG, "onDeleted " + Arrays.toString(appWidgetIds));
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.d(LOG_TAG, "onDisabled");
    }

    boolean upd;

    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

             Log.d(LOG_TAG,"onReceive");

        if (AppWidgetManager.getInstance(context).getAppWidgetIds(
                new ComponentName(context, this.getClass())).length == 0) return;



        if (intent.getAction().equalsIgnoreCase(prefics+"q_photo"))

        {

             Intent i=new Intent(context,Glavnoe_Activity.class);
                i.putExtra("create","photo");
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
              Log.d(LOG_TAG, "q_photo");
        }

        if (intent.getAction().equalsIgnoreCase(prefics+"q_paint"))

        {

                Intent i=new Intent(context,Glavnoe_Activity.class);
                i.putExtra("create","paint");
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

                Log.d(LOG_TAG, "q_paint");

        }

        if (intent.getAction().equalsIgnoreCase(prefics+"q_text"))

        {

                Intent i=new Intent(context,Glavnoe_Activity.class);
                i.putExtra("create","text");
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

                Log.d(LOG_TAG, "q_text");



        }

        if (intent.getAction().equalsIgnoreCase(prefics+"q_qs"))

        {



                Intent i=new Intent(context,Glavnoe_Activity.class);
                i.putExtra("create","qs");
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

                Log.d(LOG_TAG, "q_qs");



        }

        if (intent.getAction().equalsIgnoreCase(prefics+"q_mickr"))

        {


                Log.d(LOG_TAG,"q_mickr");
            if (!isMyServiceRunning(Dickt.class,context)) {
              if  (ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.RECORD_AUDIO)== PackageManager.PERMISSION_GRANTED)

              {
                  RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
                  views.setImageViewResource(R.id.q_mickr, R.drawable.nomic);
                  ComponentName thisWidget = new ComponentName(context, Widget.class);
                  AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                  appWidgetManager.updateAppWidget(thisWidget, views);


                  Intent i = new Intent(context, Dickt.class);
                  i.putExtra("stop", "start");
                  context.startService(i);
              }
              else
              {
                Toast.makeText(context,context.getResources().getString(R.string.w1),Toast.LENGTH_LONG).show();
                  Intent i=new Intent(context,Glavnoe_Activity.class);
                  i.putExtra("create","mickr0");
                  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  context.startActivity(i);
              }
            }

            else
            {
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
                views.setImageViewResource(R.id.q_mickr, R.drawable.mic);
                ComponentName thisWidget = new ComponentName(context, Widget.class);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                appWidgetManager.updateAppWidget(thisWidget, views);
                Intent i = new Intent(context, Dickt.class);
                i.putExtra("stop", "stop");
                context.stopService(i);
            }




        }

        if (intent.getAction().equalsIgnoreCase(prefics+"q_mickr0"))
        {
           String filename= intent.getStringExtra("filename");

           mickr(context,filename);
        }



    }
    DataBaseInterface db;
    AllSheets allSheets;
    void mickr(Context ctx,String filename)
    {


       db=new DataBaseInterface(ctx);

        db.getWritableDatabase();

       allSheets = new AllSheets(ctx, db);

       createQS();

        CurrSave currsave;

        currsave = new CurrSave(db, allSheets.getUuid_sheet());

        currsave.setUuid_save(new CreateUID(ctx).creatingUID() + "_save");

        currsave.setDatetime(formatDateTime());

        currsave.setValue("");

        currsave.setFilename(filename);//getIntent().getStringExtra("filename")

        currsave.addDataBase();

        Toast.makeText(ctx,ctx.getResources().getString(R.string.w2),Toast.LENGTH_LONG).show();



    }

    void createQS()
    {
        if (search()==null) {

            allSheets.setNametablesheet("QuickStart");

            allSheets.setPassword("admin");

            allSheets.addDataBase(false);
        }

        else

        {
            allSheets.setUuid_sheet(search());

            allSheets.setTempltable(search2());

            allSheets.setTabletempltable(search3());

            allSheets.setNametablesheet("QuickStart");
        }
    }

    String search()
    {

        String ret=db.queryMainTable();

        return ret;
    }

    String search2()
    {

        String ret=db.queryMainTable2();

        return ret;
    }

    String search3()
    {

        String ret=db.queryMainTable3();

        return ret;
    }
    String formatDateTime() {

        Calendar cal = Calendar.getInstance();

        String mont = "0";

        int tmont = cal.get(Calendar.MONTH) + 1;

        mont = tmont + "";

        if (tmont < 10) mont = "0" + tmont;

        String min = "0";

        int tmin = cal.get(Calendar.MINUTE);

        min = tmin + "";

        if (tmin < 10) min = "0" + tmin;

        return cal.get(Calendar.DAY_OF_MONTH) + "." + mont + "." + cal.get(Calendar.YEAR) + "  " + cal.get(Calendar.HOUR_OF_DAY) + ":" + min;

    }




    private boolean isMyServiceRunning(Class<?> serviceClass,Context cont) {

        ActivityManager manager = (ActivityManager) cont.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }






    public  static class UpdateService extends Service
    {

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {

            Log.d(Widget.LOG_TAG,"onstart");

            RemoteViews rws=buildRemoteView(this);

            pushUpdate(rws);

            stopSelf();

            return super.onStartCommand(intent, flags, startId);




        }

        public RemoteViews buildRemoteView(Context context)
    {
        RemoteViews updateView=null;

        updateView=new RemoteViews(context.getPackageName(), R.layout.widget);

        return  updateView;
    }
        @Override
        public  void onConfigurationChanged(Configuration newConfig)
        {
            int oldOrient=this.getResources().getConfiguration().orientation;

            if (newConfig.orientation!=oldOrient)
            {
                RemoteViews rws=buildRemoteView(this);

                pushUpdate(rws);
            }

        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        private  void pushUpdate(RemoteViews rws)
        {
            ComponentName myw=new ComponentName(this,Widget.class);
            AppWidgetManager man=AppWidgetManager.getInstance(this);
            man.updateAppWidget(myw,rws);
        }
    }


    }



