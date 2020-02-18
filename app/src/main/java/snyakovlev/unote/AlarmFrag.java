package snyakovlev.unote;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import static snyakovlev.unote.DataBaseInterface.ap0;
import static snyakovlev.unote.DataBaseInterface.ap1;
import static snyakovlev.unote.DataBaseInterface.ap11;
import static snyakovlev.unote.DataBaseInterface.ap2;
import static snyakovlev.unote.DataBaseInterface.ap3;
import static snyakovlev.unote.DataBaseInterface.ap5;
import static snyakovlev.unote.DataBaseInterface.ap8;
import static snyakovlev.unote.DataBaseInterface.ap9;

/**
 * Created by Катя on 08.08.2018.
 */

public class AlarmFrag extends View implements View.OnClickListener {

    TextView tdate,ttime;
     Switch vkl;
    EditData ed;
    boolean upd;
    String ida;

    public AlarmFrag(Context context) {

            this(context, (AttributeSet) null);
        }

       public void setEditData(EditData ed)
        {
            this.ed=ed;
        }


       public AlarmFrag (Context context, AttributeSet attrs) {

           super(context, attrs);
           napom = false;
           DataBaseInterface db = new DataBaseInterface(context);
           db.getWritableDatabase();
           ArrayList<Alarm> alls = new ArrayList<>();
           alls = db.qAlarmTabel();
           tdate = (TextView)this.findViewById(R.id.tdate);
           tdate.setOnClickListener(this);
           ttime = (TextView)this.findViewById(R.id.ttime);
           ttime.setOnClickListener(this);
           vkl =(Switch) this.findViewById(R.id.vkl);
           vkl.setOnClickListener(this);


           for (Alarm a : alls) {

               if (a.id_currsave.equals(ed.currsave.getUuid_save())) {
                   datenoform = a.datenoform;
                   timenoform = a.timenoform;
                   tdate.setText(a.date);
                   ttime.setText(a.time);
                   if (a.ok.equals("ok")) {
                       vkl.setChecked(true);
                   } else {
                       vkl.setChecked(false);
                   }
                   upd = true;
                   ida = a.id;
               }
           }

       }

    int vv=0;

    @Override
    public void onClick(View v){

        if (v.getId()==R.id.tdate)
        {
            vkl.setChecked(false);
setDate();
        }
        if (v.getId()==R.id.ttime)
        {
            vkl.setChecked(false);
setTime();
        }
        if (v.getId()==R.id.vkl)
        {
            if (vv!=0){upd=true;}
            vv++;
            if (!ttime.getText().equals("") && !tdate.getText().equals("")) {

                if (vkl.isChecked()) {
                    s2db(ed);
                }
                else
                {
                    Toast.makeText(getContext(), getResources().getString(R.string.af1), Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                vkl.setChecked(false);
                Toast.makeText(getContext(), getResources().getString(R.string.af2), Toast.LENGTH_SHORT).show();
            }
        }

    }

    GregorianCalendar cale = (GregorianCalendar) Calendar.getInstance();


    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int yearr, int monthOfYear, int dayOfMonth) {

            cale.set(Calendar.YEAR, yearr);

            cale.set(Calendar.MONTH, monthOfYear);

            cale.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            tdate.setText(dayOfMonth+"."+update(monthOfYear)+"."+yearr);

            datenoform=dayOfMonth+"."+monthOfYear+"."+yearr;

        }
    };

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {


        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int min) {

            cale.set(Calendar.HOUR_OF_DAY, hourOfDay);

            cale.set(Calendar.MINUTE, min);

             ttime.setText(hourOfDay+":"+update2(min));

             timenoform=hourOfDay+":"+min;

        }
    };

    int iii = 0;

    DatePickerDialog dpd;

    TimePickerDialog tpd;

    public void setDate() {
        dpd = new DatePickerDialog(getContext(), d,
                cale.get(Calendar.YEAR),
                cale.get(Calendar.MONTH),
                cale.get(Calendar.DAY_OF_MONTH));

        dpd.show();

    }

    public void setTime() {
        tpd = new TimePickerDialog(getContext(), t, cale.get(Calendar.HOUR_OF_DAY), cale.get(Calendar.MINUTE), true);

        tpd.show();

    }

    int hour, minute;

    String timenoform="";

    String datenoform="";


    String update2(int min) {

                String str="";

                if (min < 10)
                {str = "0" + min;}
                else {str = min + "";}

    return str;

    }

    int den,mes,year;


    String update(int mes) {



                String str = "";

                int mess = mes + 1;

                if (mess < 10)
                {str = "0" + mess;}
                else
                {str = mess + "";}

            return str;

        }


void s2db(EditData ed)
{
    Log.v("proverka",upd+"");

    DataBaseInterface db=new DataBaseInterface(getContext());

    db.getWritableDatabase();

    ContentValues cv=new ContentValues();

   if (!upd)

   {
       ida=(new CreateUID(getContext())).creatingUID() + "";
       cv.put(ap0, ida);
   }

    cv.put(ap1,ed.currsave.getValue());

    cv.put(ap3,datenoform);

    cv.put(ap8,timenoform);

    cv.put(ap5,tdate.getText().toString());

    cv.put(ap9,ttime.getText().toString());

    cv.put(ap11,ed.currsave.getUuid_save());

    String s="";

    if (vkl.isChecked())
    {
        s="ok";
    }
    else
    {
        s="non";
    }

    cv.put(ap2,s);

    if (!upd) {

        if (vkl.isChecked()) {
            db.iAlarmTable(cv);
            saveAlarm(getContext());
        }
    }
    else
    {
        if (vkl.isChecked()) {
            db.uAlarmTable(cv, ida);
            saveAlarm(getContext());
        }
        else
        {
            db.dAlarmTable(ida);
        }
    }

}

static boolean napom=false;

static void saveAlarm(Context context)
{
 String text="";

  AlarmManager  am= (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

    DataBaseInterface db=new DataBaseInterface(context);

    db.getWritableDatabase();

    ArrayList<Alarm> alls=db.qAlarmTabel();

    Log.v("proverka",alls.size()+"");

    long time1=0;
    long time2=0;
    long time=0;


    Calendar c=Calendar.getInstance();
    Calendar cc=Calendar.getInstance();


    for (int i=0;i<alls.size();i++)

    {

       int hour=str2hour(alls.get(i).timenoform);
       int min=str2min(alls.get(i).timenoform);
            time2=str2long2(alls.get(i).date);
            c.setTimeInMillis(time2);
            c.set(Calendar.HOUR_OF_DAY,hour);
            c.set(Calendar.MINUTE,min);

            time=c.getTimeInMillis();

            cc.setTimeInMillis(time);
        Log.v("proverka",cc.get(Calendar.HOUR_OF_DAY)+":"+cc.get(Calendar.MINUTE)+"   "+
                cc.get(Calendar.DAY_OF_MONTH)+"."+cc.get(Calendar.MONTH)+"."+cc.get(Calendar.YEAR));
            if (time<Calendar.getInstance().getTimeInMillis()) {
              //  Toast.makeText(context, "Время напоминания меньше текущего. Напоминание не поставлено.", Toast.LENGTH_SHORT).show();

            }
            else {
                napom=true;

                text = alls.get(i).text;


                Intent intent = new Intent(context, ActivityAlarm.class);

                intent.setAction(alls.get(i).id_currsave);

                intent.putExtra("mess", text);

                PendingIntent pendingIntent = PendingIntent.getActivity(context, i, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                 am.cancel(pendingIntent);


                if (alls.get(i).ok.equals("ok")) {
                    Log.v("proverka", "ok!");
                    if (Build.VERSION.SDK_INT >= 19) {
                        am.setWindow(AlarmManager.RTC_WAKEUP, time, 0, pendingIntent);
                    } else {
                        am.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
                    }
                } else {

                    Log.v("proverka", "nook!");
                    intent = new Intent(context, ActivityAlarm.class);

                    intent.setAction(alls.get(i).id_currsave);

                    pendingIntent = PendingIntent.getBroadcast(context, i, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                    am.cancel(pendingIntent);

                    pendingIntent.cancel();
                }
            }

    }




}

  static  int str2hour(String str)
    {



        SimpleDateFormat df = new SimpleDateFormat("H:mm");

        df.setTimeZone(TimeZone.getDefault());

        int vremya=0;

        try {
            Date d = df.parse(str);

            vremya = d.getHours();


        } catch (ParseException e) {
            e.printStackTrace();
        }


        return vremya;

    }

    static  int str2min(String str)
    {



        SimpleDateFormat df = new SimpleDateFormat("H:mm");

        df.setTimeZone(TimeZone.getDefault());

        int vremya=0;

        try {
            Date d = df.parse(str);

            vremya = d.getMinutes();


        } catch (ParseException e) {
            e.printStackTrace();
        }


        return vremya;

    }

    static  long str2long2(String str)
    {

        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        long vremya=0;

        try {
            Date d = df.parse(str);



            vremya = d.getTime();


        } catch (ParseException e) {
            e.printStackTrace();
        }


        return vremya;

    }
}


