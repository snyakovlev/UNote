package snyakovlev.unote;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import snyakovlev.unote.ColorTheme;

public class preference extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorTheme.setColor(this);
        setContentView(R.layout.activity_preference);
        this.setTitle(getResources().getString(R.string.title_activity_preference));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.setting2);
        setSupportActionBar(toolbar);

        getFragmentManager().beginTransaction()
                .add(R.id.prefs, new FrSettings())
                .commit();
    }

    @Override
   public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_pref, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.closepr)
        {

            fin();
        }
        return true;
    }


    void fin()
    {
        this.setResult(199);
        finish();
    }

        public static class FrSettings extends PreferenceFragment {


        @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.preference);
        }

    }


}

class FontsPref {

 static  final String f1=  "times-new-roman.ttf";
    static  final String f2= "nautiluspompilius.ttf";
    static  final String f3=  "comic.ttf";
    static  final String f4=  "Arkhip_font.otf";
    static  final String f5=  "kremlin.ttf";
}