package snyakovlev.unote;

/**
 * Created by Катя on 11.11.2016.
 */

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Widget2 extends AppWidgetProvider {

    static int id_wid;

    static final String LOG_TAG = "myLogs";

    public static final String ACTION_ON_ITEM_CLICK = "ON_MORE_CLICK";

    public static final String COMMAND = "COMMAND";
    public static final String TEXT = "TEXT";
    public static final String CONNECT = "CONNECT";
    public static final String CHECKED = "CHECKED";
    public static final String ITEM = "ITEM";

    static final String prefics = "com.yakovlev.sergey.android.notebook.";

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.d(LOG_TAG, "onEnabled");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int widgetId : appWidgetIds) {
            updateWidget(context, appWidgetManager, widgetId);
        }
    }

    private void updateWidget(Context context, AppWidgetManager appWidgetManager, int widgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget2);
        setList(views, context, widgetId);
        final Intent onItemClick = new Intent(context, Widget2.class);
        onItemClick.setAction(ACTION_ON_ITEM_CLICK);
        onItemClick.setData(Uri.parse(onItemClick.toUri(Intent.URI_INTENT_SCHEME)));
        final PendingIntent onClickPendingIntent =
                PendingIntent.getBroadcast(context, 0, onItemClick, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widgetList, onClickPendingIntent);

        appWidgetManager.updateAppWidget(widgetId, views);
    }

    private void setList(RemoteViews views, Context context, int widgetId) {
        Intent intent = new Intent(context, WidgetRemoteViewsService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        views.setRemoteAdapter(R.id.widgetList, intent);
    }

    public static void sendRefreshBroadcast(Context context) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(context, Widget2.class));
        context.sendBroadcast(intent);
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



            final String action = intent.getAction();



            if (!TextUtils.isEmpty(action)) {

                if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {

                    AppWidgetManager manager = AppWidgetManager.getInstance(context);
                    ComponentName cn = new ComponentName(context, Widget2.class);
                    manager.notifyAppWidgetViewDataChanged(manager.getAppWidgetIds(cn), R.id.widgetList);
                }

                if (action.equals(ACTION_ON_ITEM_CLICK)) {
                    parseItemClick(context, intent.getExtras());
                    AppWidgetManager manager = AppWidgetManager.getInstance(context);
                    ComponentName cn = new ComponentName(context, Widget2.class);
                    manager.notifyAppWidgetViewDataChanged(manager.getAppWidgetIds(cn), R.id.widgetList);
                }
            }
            super.onReceive(context, intent);

    }

    private void parseItemClick(Context context, Bundle bundle) {
        if (bundle != null) {
            ArrayList<String> ar=getTextArray(context);
            setUuid_table_table(context);
            String command = bundle.getString(COMMAND);
            if (!TextUtils.isEmpty(command)) {

                if (command.equals(Widget2.CONNECT)) {

                    File root;

                    if (isAvailable() || !isReadOnly()) {

                        root = Environment.getExternalStorageDirectory();
                    } else {
                        root = context.getFilesDir();
                    }

                    String root_str = root.getAbsolutePath();

                    String my_dir = root_str + File.separator + "nbook" + File.separator + "table";


                    boolean ss = false;
                    XmlSerializer s = Xml.newSerializer();
                    FileWriter writer = null;
                    try {
                        File fil = new File(my_dir, uuid_table_table + ".html");
                        fil.getParentFile().mkdirs();
                        fil.createNewFile();
                        writer = new FileWriter(fil);
                        s.setOutput(writer);
                        s.startDocument("UTF-8", true);
                        Log.v("qqqwww","ii="+ar.size()+"");

                        for (int i = 0; i < ar.size(); i++) {

                            Log.v("qqqwww",bundle.getInt(ITEM)+"");
                            Log.v("qqqwww",bundle.getBoolean(CHECKED)+"");

                            s.startTag("", "check");
                            if (ar.get(i).split("_")[0].equals("0") && i != bundle.getInt(ITEM)) {
                                Log.v("qqqwww","checked=0");
                                s.attribute("", "checked", "0");
                            }
                             if (ar.get(i).split("_")[0].equals("1") && i != bundle.getInt(ITEM))
                            {
                                Log.v("qqqwww","checked=1");
                                s.attribute("", "checked", "1");
                            }

                            if (i == bundle.getInt(ITEM) && bundle.getBoolean(CHECKED)) {
                                s.attribute("", "checked", "1");
                                Log.v("qqqwww","checked=1");
                            }
                             if (i == bundle.getInt(ITEM) && !bundle.getBoolean(CHECKED)) {
                                s.attribute("", "checked", "0");
                                Log.v("qqqwww","checked=0");
                            }

                            s.attribute("", "text", (ar.get(i).split("_")[1]));
                            s.endTag("", "check");
                        }

                        s.endDocument();

                    } catch (Exception e) {
                        throw new RuntimeException(e);

                    }
                }

            }
        }


    }

   static DataBaseInterface db;
    AllSheets allSheets;
  static   String uuid_table_table;

  static   void setUuid_table_table(Context context)
    {

        uuid_table_table= PreferenceManager.getDefaultSharedPreferences(context).getString("uuid_table_table", "");
        Log.v("qqqwww",uuid_table_table);
    }

  static String getText(Context context)
    {
        Log.v("qqqwww","getText");
        String idsheet= PreferenceManager.getDefaultSharedPreferences(context).getString("idsheet", "");
       String idcurrsave= PreferenceManager.getDefaultSharedPreferences(context).getString("idcurrsave", "");

        db=new DataBaseInterface(context);
        db.getWritableDatabase();


        int  _count = db.getCountDataBaseAll(idsheet);
        String text="";

        for (int i=0;i<_count;i++) {
           if (idcurrsave.equals(db.queryDataBaseAll(idsheet, "uuid_save").get(i) ))
            {
                  text=db.queryDataBaseAll(idsheet, "value").get(i);
                Log.v("qqqwww",text);
                   return text;
            }


        }
           return "";
    }




  static  ArrayList<String> getTextArray(Context ctx)
    {
        Log.v("qqqwww","getTextArray");

        ArrayList<String> larr=getLArr(ctx);



        return  larr;
    }

   static ArrayList<String> getLArr(Context ctx)
    {
        Log.v("wsde","getLArr");
        ArrayList<String> res=new ArrayList<>();

        XmlPullParser xml=getParser(ctx);

        if (xml!=null) {
           String ll="";
            boolean c = false;
            try {
                int j=0;
                while (xml.getEventType() != XmlPullParser.END_DOCUMENT)

                {

                    if (xml.getEventType() == XmlPullParser.START_TAG && xml.getName().equals("check")) {


                        String t1 = null, t2 = null;

                        t2 = xml.getAttributeValue(1);
                        Log.v("wsde", xml.toString());
                        Log.v("wsde", t2);
                        if (ll != null) {
                            if (t2 != null) {
                                ll = t2;

                            } else {
                                ll = ctx.getResources().getString(R.string.wa4);

                            }

                        }
                        t1 = xml.getAttributeValue(0);

                        Log.v("wsde", t1);
                        if (t1 != null) {
                            if (t1.equals("0")) {

                              if (ll.split("_").length>1) ll="";
                                ll = "0_" + t2;

                            }
                            if (t1.equals("1")) {
                                if (ll.split("_").length>1) ll="";
                                ll = "1_" + t2;

                            }


                            res.add(ll);

                    }
                    }


                    xml.next();
                }


            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return res;

    }
    private static boolean isReadOnly() {
        String storageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(storageState);
    }

    // проверяем есть ли доступ к внешнему хранилищу
    private static boolean isAvailable() {
        String storageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(storageState);
    }

  static  XmlPullParser getParser(Context glav)
    {
        File root;

        if (isAvailable() || !isReadOnly()) {

            root = Environment.getExternalStorageDirectory();
        } else {
            root = glav.getFilesDir();
        }

        String root_str = root.getAbsolutePath();

        String my_dir = root_str + File.separator + "nbook" + File.separator + "table";

        setUuid_table_table(glav);

        File f=new File(my_dir+"/"+uuid_table_table+".html");

        if (!f.exists()) return null;
        XmlPullParserFactory fac=null;
        XmlPullParser parcer=null;
        FileInputStream fis=null;
        try {
            fac=XmlPullParserFactory.newInstance();
        } catch (XmlPullParserException e) {
            e.printStackTrace();

        }
        try {
            parcer=fac.newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        try {
            fis=new FileInputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (fac!=null && parcer!=null && fis!=null)

        {
            try {
                parcer.setInput(new InputStreamReader(fis));
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
        }

        return parcer;
    }

    public  static class UpdateService extends Service
    {

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {

            Log.d(Widget2.LOG_TAG,"onstart");

            RemoteViews rws=buildRemoteView(this);

            pushUpdate(rws);

            stopSelf();

            return super.onStartCommand(intent, flags, startId);




        }

        public RemoteViews buildRemoteView(Context context)
        {
            RemoteViews updateView=null;

            updateView=new RemoteViews(context.getPackageName(),R.layout.widget2);

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
            ComponentName myw=new ComponentName(this,Widget2.class);
            AppWidgetManager man=AppWidgetManager.getInstance(this);
            man.updateAppWidget(myw,rws);
        }
    }

    }



