package snyakovlev.unote;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Катя on 18.09.2018.
 */

class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private List<String> list;

    private int mWidgetId;

    public WidgetRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override public void onCreate() {

        list=new ArrayList<>();

       // if ( PreferenceManager.getDefaultSharedPreferences(mContext).getString("tcurrsave", "t").equals("t"))
       // {
       //     list.add(Widget2.getText(mContext));
      //  }
       // else {

      //    list=Widget2.getTextArray(mContext);
      //  }


    }

    @Override public void onDataSetChanged() {
        list.clear();

        if ( PreferenceManager.getDefaultSharedPreferences(mContext).getString("tcurrsave", "0").equals("t"))
        {
            list.add(Widget2.getText(mContext));
        }
        else {

            list=Widget2.getTextArray(mContext);
        }
    }

    @Override public void onDestroy() {
    }

    @Override public int getCount() {
        return list.size();
    }

    @Override public RemoteViews getViewAt(int i) {
        String str = list.get(i);
        Log.v("qqqwww",str);
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
        if (str.split("_").length==1)
        {
            Log.v("qqqwww","text="+str);
            rv.setTextViewText(R.id.tw1, str);
            rv.setViewVisibility(R.id.im1, View.GONE);
        }
        else
            {
                Log.v("qqqwww","text_array="+str);
            Widget2.setUuid_table_table(mContext);
            String str1 = str.split("_")[0];
            String str2 = str.split("_")[1];
            rv.setTextViewText(R.id.tw1, str2);
           boolean chk=false;
            if (str1.equals("0")) {
                Log.v("qqqwww","str1="+str1);
                rv.setImageViewResource(R.id.im1,R.drawable.blue);
                chk=true;
            } else {
                Log.v("qqqwww","str1="+str1);
                 rv.setImageViewResource( R.id.im1,R.drawable.red);
                chk=false;
            }
            rv.setOnClickFillInIntent(R.id.tw1, createIntent(Widget2.CONNECT, i,str2,chk));
        }



        return rv;
    }

    private Intent createIntent(String cmd,int i,String text,boolean checked ) {
        Intent intent = new Intent();
        intent.setAction(Widget2.ACTION_ON_ITEM_CLICK);
        Bundle bundle = new Bundle();
        bundle.putString(Widget2.COMMAND, cmd);
        bundle.putInt(Widget2.ITEM, i);
        bundle.putString(Widget2.TEXT,text);
        bundle.putBoolean(Widget2.CHECKED,checked);
        intent.putExtras(bundle);
        return intent;
    }

    @Override public RemoteViews getLoadingView() {
        return null;
    }

    @Override public int getViewTypeCount() {
        return 1;
    }

    @Override public long getItemId(int i) {
        return i;
    }

    @Override public boolean hasStableIds() {
        return true;
    }


}
