package snyakovlev.unote;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.media.AudioManager;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import static android.content.Context.AUDIO_SERVICE;

/**
 * Created by Катя on 07.11.2018.
 */

public class Phone extends BroadcastReceiver {

    static final String prefics="com.yakovlev.sergey.android.notebook.";
    DataBaseInterface db;
    AllSheets allSheets;
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

    void mickr(Context ctx,String filename,String text)
    {


        db=new DataBaseInterface(ctx);

        db.getWritableDatabase();

        allSheets = new AllSheets(ctx, db);

       if (text.equals(""))
       {
           Toast.makeText(ctx,ctx.getResources().getString(R.string.w2),Toast.LENGTH_LONG).show();
           createQS();
       }
       else
       {
           Toast.makeText(ctx,ctx.getResources().getString(R.string.w4),Toast.LENGTH_LONG).show();
           createFolder(ctx);
       }

        CurrSave currsave;

        currsave = new CurrSave(db, allSheets.getUuid_sheet());

        currsave.setUuid_save(new CreateUID(ctx).creatingUID() + "_save");

        currsave.setDatetime(formatDateTime());

        currsave.setValue(text);

        currsave.setFilename(filename);//getIntent().getStringExtra("filename")

        currsave.addDataBase();



        Random rnd=new Random();
        int r=rnd.nextInt(25);
        int g=rnd.nextInt(25);
        int b=rnd.nextInt(25);
        int col= Color.rgb(230+r,230+g,230+b);
        Log.v("qqaass",col+"");
        PreferenceManager.getDefaultSharedPreferences(ctx.getApplicationContext()).edit().putInt("fon_col_" + currsave.getUuid_save(), col).commit();



    }



    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(prefics+"q_mickr0"))
        {

            String text="";

            if (intent.getStringExtra("tel")!=null) {

              text = intent.getStringExtra("tel");
            }

            if (text!=null && !text.equals("")) {text=context.getResources().getString(R.string.w5)+" "+text;}

            String filename= intent.getStringExtra("filename");

            mickr(context.getApplicationContext(),filename,text);
        }

      //  else {

         //   if (PreferenceManager.getDefaultSharedPreferences(context.
             //       getApplicationContext()).getBoolean("phone", false)) {

             //   if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED

             //           && ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {


              //      String phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

               //     if (phoneState.equals(TelephonyManager.EXTRA_STATE_RINGING))
               //     {
                //        Dickt.in=true;
                //        AudioManager audiomanager = (AudioManager) context.getSystemService(AUDIO_SERVICE);
                //        audiomanager.setMode(AudioManager.MODE_IN_CALL);
                //        Intent i = new Intent(context, Dickt.class);
                //        i.putExtra("stop", "start");
                //        init(context);
                 //       String tel = "";
                 //       String tels = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                 //       if (tels == null) tel = context.getResources().getString(R.string.w6);
                 //       if (tels != null && tels.equals(""))
                 //           tel = context.getResources().getString(R.string.w6);
                 //       if (tels != null && !tels.equals("")) tel = tels;
                  //      String text = "";
                 //       for (TelBookDBI t : tbs) {
                 //           if (t.tel.equals(tel)) text = t.name;
                 //       }

                  //      if (text.equals("")) text = tel;
                  //      i.putExtra("tel", text);
                  //      context.startService(i);
                  //  }

                  //  if (phoneState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                   //     if (!Dickt.in) {
                    //        Dickt.in=true;
                    //        Intent i = new Intent(context, Dickt.class);
                     //       i.putExtra("stop", "start");
                     //       init(context);
                      //      String tel = "";
                      //      String tels = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                      //      if (tels == null) tel = context.getResources().getString(R.string.w6);
                      //      if (tels != null && tels.equals(""))
                      //          tel = context.getResources().getString(R.string.w6);
                      //      if (tels != null && !tels.equals("")) tel = tels;
                       //     String text = "";
                       //     for (TelBookDBI t : tbs) {
                      //          if (t.tel.equals(tel)) text = t.name;
                      //      }

                       //     if (text.equals("")) text = tel;
                       //     i.putExtra("tel", text);
                       //     context.startService(i);
                     //   }
                 //   }
                 //   if (phoneState.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                  //      if (Dickt.in) {
                   //         Dickt.in=false;
                    //        AudioManager audiomanager = (AudioManager) context.getSystemService(AUDIO_SERVICE);
                    //        audiomanager.setMode(AudioManager.MODE_NORMAL);
                     //       Intent i = new Intent(context, Dickt.class);
                     //       i.putExtra("stop", "stop");

                      //      context.stopService(i);
                   //     }
                 //   }
              //  }
         //   }
      //  }
    }

    ArrayList<TelBookDBI> tbs=new ArrayList<>();
    String phone_cont_id=ContactsContract.CommonDataKinds.Phone.CONTACT_ID;

    void init(Context ctx) {


        tbs.clear();

        ContentResolver cres = ctx.getContentResolver();

        Cursor cursor = cres.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        Calendar c = Calendar.getInstance();

        int id = 0;

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String tel = "";

                String cont_id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));

                int hpn = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));

                if (hpn > 0) {
                    Cursor phonec = ctx.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER,
                                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},
                            phone_cont_id + "=" + cont_id,
                            null,
                            null);


                    while (phonec.moveToNext()) {
                        tel = phonec.getString(0);
                        name = phonec.getString(1);
                    }

                    phonec.close();

                }
                TelBookDBI tbi = new TelBookDBI();
                tbi.id = c.getTimeInMillis() + "_" + id;
                tbi.tel = tel;
                tbi.name = name;
                tbs.add(tbi);
                id++;

            }
        }
    }

    void createFolder(Context ctx)
    {

        if (searchPhone(ctx) == null) {

            allSheets.setNametablesheet(ctx.getResources().getString(R.string.ga17));

            allSheets.setUuid_sheet("phone0");

            allSheets.setPassword("admin");

            allSheets.addDataBase(false);
        }
        else

        {

            allSheets.setUuid_sheet(searchPhone(ctx));

            allSheets.setNametablesheet(ctx.getResources().getString(R.string.ga17));
        }
    }

    String searchPhone(Context ctx) {

        String ret = db.queryMainTablePhone(ctx);

        return ret;
    }


}

class TelBookDBI
{
    String id,tel,name;
}