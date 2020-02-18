package snyakovlev.unote;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.preference.PreferenceManager;

/**
 * Created by Катя on 17.03.2017.
 */

public class Fonts {

    static  String fwls,fls;

  static   String fontLs;

   static String fontWl;

    static String fontsizeLS;

    static String fontsizeWL;

    static String fontsizeTable;

    static AssetManager getAss(Context act)
    {
        return act.getAssets();
    }


   static void setFontLS(Activity act)
    {
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(act);

        fls=sh.getString("fontLS",FontsPref.f3);



        String[] flss=act.getResources().getStringArray(R.array.fontLS);
        fontLs="times-new-roman.ttf";
        if (fls.equals(flss[0])) fontLs=FontsPref.f1;
        if (fls.equals(flss[1])) fontLs=FontsPref.f2;
        if (fls.equals(flss[2])) fontLs=FontsPref.f3;
        if (fls.equals(flss[3])) fontLs=FontsPref.f4;
        if (fls.equals(flss[4])) fontLs=FontsPref.f5;




    }

  static  void setFontWl(Activity act)
    {
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(act);


        fwls=sh.getString("fontWL",FontsPref.f2);


        String[] flss=act.getResources().getStringArray(R.array.fontLS);

        fontWl="times-new-roman.ttf";

        if (fwls.equals(flss[0])) fontWl=FontsPref.f1;
        if (fwls.equals(flss[1])) fontWl=FontsPref.f2;
        if (fwls.equals(flss[2])) fontWl=FontsPref.f3;
        if (fwls.equals(flss[3])) fontWl=FontsPref.f4;
        if (fwls.equals(flss[4])) fontWl=FontsPref.f5;

    }


    static  void setSizeFontLS(Activity act)
    {

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(act);


        fontsizeLS=sh.getString("fontsizels","18");


    }

    static  void setSizeFontWL(Activity act)
    {
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(act);


        fontsizeWL=sh.getString("fontsizewl","18");

    }

    static  void setSizeFontTable(Activity act)
    {
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(act);


        fontsizeTable=sh.getString("fontsizetable","18");

    }
}
