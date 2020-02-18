package snyakovlev.unote;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class BootCompl2 extends BroadcastReceiver {

    public  BootCompl2()
    {

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.v("notic4","yes1");

        Intent inte=new Intent(context,BootService2.class);

        inte.putExtra("str","start");

        inte.putExtra("setup","0");

        AlarmManager am=(AlarmManager)context.getSystemService(context.ALARM_SERVICE);

        Calendar c = Calendar.getInstance();


        long time=c.getTimeInMillis();

        PendingIntent pendingIntent = PendingIntent.getService(context, 0
                , inte, PendingIntent.FLAG_CANCEL_CURRENT);

        am.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);

    }
}
