package snyakovlev.unote;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.File;





public class ViewF extends AppCompatActivity {


    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ColorTheme.setColor(this);


        setContentView(R.layout.activity_view_f);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MobileAds.initialize(this, "ca-app-pub-9657610086378132~3203303757");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9657610086378132/7248228650");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


          String filef=getIntent().getStringExtra("filef");

          String s=WorkAdapterDlg.getFileExtension(new File(filef));

          if (s.equals("png") || s.equals("jpg"))
          {
               Log.v("aqwe",filef);
              getSupportFragmentManager().beginTransaction().add(R.id.framef,VImage.newInstance(filef,"png")).commit();
          }

          else

          if (s.equals("pdf"))
          {
              getSupportFragmentManager().beginTransaction().add(R.id.framef,PdfViewF.newInstance(filef)).commit();
          }

          else

              if (s.equals("xls"))

          {
              getSupportFragmentManager().beginTransaction().add(R.id.framef,ViewTable.newInstance(filef)).commit();
          }

              else
              {
                  getSupportFragmentManager().beginTransaction().add(R.id.framef,ViewText.newInstance(filef)).commit();
              }

    }


    @Override
    protected void onDestroy() {

        if (mInterstitialAd.isLoaded()) {
      //  mInterstitialAd.show();
    } else {
        Log.d("TAG", "The interstitial wasn't loaded yet.");
    }

        super.onDestroy();
    }
}
