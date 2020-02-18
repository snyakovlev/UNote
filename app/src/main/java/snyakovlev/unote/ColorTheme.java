package snyakovlev.unote;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by Катя on 17.03.2017.
 */

public class ColorTheme {

    static String color;

    static int primary, primaryDark, accent;

    static void setColor(Activity act) {

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(act);

        String col="";

         col=sh.getString("theme",act.getResources().getStringArray(R.array.theme)[1]);

        String[] cs=act.getResources().getStringArray(R.array.theme);
        Log.v("hjkl",col+"="+cs[0]);
        if (col.equals(cs[0]))

        {
            color=cs[0];

            act.setTheme(R.style.Green_Base);
            primary = R.color.colorPrimaryGreen;
            primaryDark = R.color.colorPrimaryDarkGreen;
            accent = R.color.colorAccentGreen;
        }
        if (col.equals(cs[1])) {
            color=cs[1];
            act.setTheme(R.style.Blue_Base);
            primary = R.color.colorPrimaryBlue;
            primaryDark = R.color.colorPrimaryDarkBlue;
            accent = R.color.colorAccentBlue;
        }

        if (col.equals(cs[2])) {
            color=cs[2];
            act.setTheme(R.style.Red_Base);
            primary=R.color.colorPrimaryRed;
            primaryDark= R.color.colorPrimaryDarkRed;
            accent = R.color.colorAccentRed;
        }
        if (col.equals(cs[3])) {
            color=cs[3];
            act.setTheme(R.style.Yellow_Base);
            primary = R.color.colorPrimaryYellow;
            primaryDark = R.color.colorPrimaryDarkYellow;
            accent = R.color.colorAccentYellow;
        }

        if (col.equals(cs[4])) {
            color=cs[4];
            act.setTheme(R.style.DayNight_Base);
            primary = R.color.gray;
            primaryDark = R.color.darkgray;
            accent = R.color.acgray;
        }

    }


}
