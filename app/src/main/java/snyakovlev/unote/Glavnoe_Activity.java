package snyakovlev.unote;


//import com.google.android.gms.ads.AdListener;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.InterstitialAd;
//import com.google.android.gms.ads.MobileAds;



import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.ConnectionResult;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAuthIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.accounts.AccountManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.speech.RecognizerIntent;

import android.support.v4.app.FragmentManager;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;


public class Glavnoe_Activity extends AppCompatActivity implements IShowFragmentList, ICreaterPaint, IPhotoView, IShowFragmentLS, IShowFragmentWL, IShowFragmentED,IShowFragmentET ,IShowFragmentCT ,IShowCreateSheet, IShowLocking, IShowEnterPassword, REST.ConnectCBs, IShowFragmentWLDLG, IPdfView {
    private static final String TAG = "MainActivity";

    //  private static final int REQUEST_CODE_CREATOR = NEXT_AVAILABLE_REQUEST_CODE;
    /**
     * Request code for the opener activity.
     */
    //  private static final int REQUEST_CODE_OPENER = NEXT_AVAILABLE_REQUEST_CODE + 1;

    /**
     * Text file mimetype.
     */
    // private static final String MIME_TYPE_TEXT = "text/plain";

    public AllSheets allSheets;

    public CurrSave currsave;

    public boolean isSetPass;

    public boolean isPotok;

    public boolean selectall;

    public boolean selectall2;

    public Picasso picasso;

    DrawerLayout drawerlay;

    ActionBarDrawerToggle abdt;

    DataBaseInterface db;


    Toolbar toolbar;


    CharSequence drawertitle;

    String titleact;


    RecyclerView lw;

    TextView tsetting;

    TextView tpda;

    TextView trecy;

    TextView tdlg;

    TextView talarm;

    boolean mickr;

    boolean shmove, ch;

    LinearLayout ll;

    volatile String pa0 = "nbook";
    volatile String pa1 = "photo";
    volatile String pa2 = "image";
    volatile String pa3 = "audio";
    volatile String pa4 = "backup";
    volatile String pa5 = "table";
    volatile String pa6 = "mini";

    final String titlepa = "NBook";

    EditText etnf;


    View llll;


    static void dialogPerm(Context ctx) {
        Toast.makeText(ctx, ctx.getResources().getString(R.string.ga1) +
                ctx.getResources().getString(R.string.ga2) +
                ctx.getResources().getString(R.string.ga3) +
                ctx.getResources().getString(R.string.ga4), Toast.LENGTH_LONG).show();

    }


    void permRecord() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)

        {
           // dialogPerm(this);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10002);
        } else {
            Fragment pv = getSupportFragmentManager().findFragmentById(R.id.header);
            if (pv != null && pv.getClass().equals(WorkList.class)) {
                ((WorkList) pv).createSaveRecord();
            }

        }
    }

    String _datetime;


    void permRecord2() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)

        {
           // dialogPerm(this);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10001);
        }

    }


    void permStorage() {


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            //dialogPerm(this);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, 10001);

        }

    }


    boolean permCamera() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
          //  dialogPerm(this);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10001);
        } else {
            return true;
        }

        return false;

    }


    void permAccounts() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
           // dialogPerm(this);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET, Manifest.permission.GET_ACCOUNTS, Manifest.permission.ACCESS_NETWORK_STATE}, 10004);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {



        if (requestCode == 10004) {

            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else

            {
                Toast.makeText(this, getResources().getString(R.string.ga5), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, getResources().getString(R.string.ga6), Toast.LENGTH_SHORT).show();
                this.finish();
            }


        }

        if (requestCode == 10002) {
            PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("audio", true).commit();
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Fragment pv = getSupportFragmentManager().findFragmentById(R.id.header);
                if (pv.getClass().equals(WorkList.class)) {

                    ((WorkList) pv).createSaveRecord();
                }
            } else

            {
                Toast.makeText(this, getResources().getString(R.string.ga5), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, getResources().getString(R.string.ga6), Toast.LENGTH_SHORT).show();
                this.finish();
            }


        }

    }

    // private AdView mAdView;

     private InterstitialAd mInterstitialAd;

    TextView tzv;

    private AdView mAdView;

    private AdView mAdView0;

    String APPLICAITON_ID = "5a5adaac9bc01e6f008b4567";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);




        picasso =Picasso.with(this);

        rekl = PreferenceManager.getDefaultSharedPreferences(this).getInt("rekl", 0);

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("audio", true).commit();
        }

        if (Build.VERSION.SDK_INT < 23) {
            PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("audio", true).commit();
        }


        ColorTheme.setColor(this);

        Fonts.setFontLS(this);

        Fonts.setFontWl(this);

        Fonts.setSizeFontLS(this);

        Fonts.setSizeFontWL(this);

        Fonts.setSizeFontTable(this);

        setContentView(R.layout.activity_glavnoe_);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Lookup your LinearLayout assuming it's been given
        // the attribute android:id="@+id/mainLayout"


         MobileAds.initialize(this,
                 "ca-app-pub-9657610086378132~2832475302");

          mInterstitialAd = new InterstitialAd(this);
          mInterstitialAd.setAdUnitId("ca-app-pub-9657610086378132/6853580983");
          mInterstitialAd.loadAd(new AdRequest.Builder().build());



          mInterstitialAd.setAdListener(new AdListener() {
               @Override
             public void onAdLoaded() {
        // Code to be executed when an ad finishes loading.
              }

           @Override
          public void onAdFailedToLoad(int errorCode) {
         //Code to be executed when an ad request fails.
          }

            @Override
            public void onAdOpened() {
        // Code to be executed when the ad is displayed.
            }

            @Override
             public void onAdLeftApplication() {
        //          // Code to be executed when the user has left the app.
              }

              @Override
            public void onAdClosed() {
        //        // Code to be executed when when the interstitial ad is closed.
             }
           });

        String quick = getIntent().getStringExtra("create");


        titleact = getTitle().toString();

        drawertitle = "navig drawer";

        toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);

        drawerlay = (DrawerLayout) findViewById(R.id.drawer_layout);

        ll = (LinearLayout) findViewById(R.id.lheader);

        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });

        lw = (RecyclerView) findViewById(R.id.list_nav_draw);

        ImageView line = (ImageView) findViewById(R.id.line);

        if (ColorTheme.primary == R.color.colorPrimaryBlue)
            line.setImageDrawable(getResources().getDrawable(R.drawable.my_rect_line_blue));
        if (ColorTheme.primary == R.color.colorPrimaryRed)
            line.setImageDrawable(getResources().getDrawable(R.drawable.my_rect_line_red));
        if (ColorTheme.primary == R.color.colorPrimaryGreen)
            line.setImageDrawable(getResources().getDrawable(R.drawable.my_rect_line_green));
        if (ColorTheme.primary == R.color.colorPrimaryYellow)
            line.setImageDrawable(getResources().getDrawable(R.drawable.my_rect_line_yellow));
        if (ColorTheme.primary == R.color.gray)
            line.setImageDrawable(getResources().getDrawable(R.drawable.my_rect_line_dn));

        abdt = new ActionBarDrawerToggle(this, drawerlay, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);// {


        abdt.syncState();

        tsetting = (TextView) findViewById(R.id.tsetting);

        tsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerlay.closeDrawer(GravityCompat.START, false);

                showSettings();

            }
        });

        TextView tback = (TextView) findViewById(R.id.tbackup);

        tback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permAccounts();
                goBackup();
            }
        });

        tdlg = (TextView) findViewById(R.id.tdlg);

        tdlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDlg();
            }
        });

        trecy = (TextView) findViewById(R.id.trecycler);

        trecy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goRecy();
            }
        });


        // tpda=(TextView)findViewById(R.id.tpda);

        // tpda.setOnClickListener(new View.OnClickListener() {
        //   @Override
        //   public void onClick(View v) {
        //      drawerlay.closeDrawer(GravityCompat.START,false);
        //      goPDA();

        //       }
        //  });

        tzv = (TextView) findViewById(R.id.tzv);

        tzv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerlay.closeDrawer(GravityCompat.START, false);
                goTZV();

            }
        });

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);

        lw.setLayoutManager(mLayoutManager);

        drawerlay.addDrawerListener(abdt);

        db = new DataBaseInterface(this);

        db.getWritableDatabase();

        allSheets = new AllSheets(this, db);

        isPotok = false;

        isSetPass = false;

        File root;

        if (isAvailable() || !isReadOnly()) {

            root = Environment.getExternalStorageDirectory();
        } else {
            root = getFilesDir();
        }

        String root_str = root.getAbsolutePath();

        String path = root_str + File.separator + pa0;

        String path1 = root_str + File.separator + pa0 + File.separator + pa1;

        String path2 = root_str + File.separator + pa0 + File.separator + pa2;

        String path3 = root_str + File.separator + pa0 + File.separator + pa3;

        String path4 = root_str + File.separator + pa0 + File.separator + pa4;

        String path5 = root_str + File.separator + pa0 + File.separator + pa5;

        String path6 = root_str + File.separator + pa0 + File.separator + pa6;

        File f = new File(path);
        if (!f.exists()) f.mkdir();

        f = new File(path1);
        if (!f.exists()) f.mkdir();

        f = new File(path2);
        if (!f.exists()) f.mkdir();

        f = new File(path3);
        if (!f.exists()) f.mkdir();

        f = new File(path4);
        if (!f.exists()) f.mkdir();

        f = new File(path5);
        if (!f.exists()) f.mkdir();

        f = new File(path6);
        if (!f.exists()) f.mkdir();

        createDlg();
        createRecycler();
        createQS();

        if (savedInstanceState == null)

        {


            ListSheets ls = new ListSheets();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.header, ls)
                    .commit();

            if (quick != null && quick.equals("photo")) {
                photo();
            }

            if (quick != null && quick.equals("paint")) {
                image();
            }

            if (quick != null && quick.equals("text")) {
                text();
            }

            if (quick != null && quick.equals("qs")) {
                qs();
            }

            if (quick != null && quick.equals("mickr0")) {
                permrec();
            }

            if (quick != null && quick.equals("mickr")) {
                mickr = true;
                mickr();
            }

            if (quick != null && quick.equals("open")) {
                openet();
            }

        } else {

            if (!savedInstanceState.getString("phfile", "").equals(""))
                phfile = new File(savedInstanceState.getString("phfile", ""));

            shmove = savedInstanceState.getBoolean("shmove");

            selectall = savedInstanceState.getBoolean("selectall");

            allSheets.setUuid_sheet(savedInstanceState.getString("uuidsheet"));

            allSheets.setTempltable(savedInstanceState.getString("templtable"));

            allSheets.setTabletempltable(savedInstanceState.getString("tabletempltable"));

            allSheets.setPassword(savedInstanceState.getString("password"));

            allSheets.setNametablesheet(savedInstanceState.getString("nametablesheet"));

            allSheets.setSetPass(savedInstanceState.getBoolean("setpass"));

            lsheet = savedInstanceState.getBoolean("lsheet");

            isPotok = savedInstanceState.getBoolean("ispotok");


            if (savedInstanceState.getBoolean("currsave")) {

                currsave = new CurrSave(db, allSheets.getUuid_sheet());

                currsave.setValue(savedInstanceState.getString("value"));

                currsave.setText(savedInstanceState.getString("text"));

                currsave.setUuid_table_table(savedInstanceState.getString("uuid_table_table"));

                currsave.setName_table_table(savedInstanceState.getString("name_table_table"));

                Log.v("qwerty", "savinst2:" + currsave.getName_table_table());

                currsave.setFilename(savedInstanceState.getString("filename"));

                currsave.setFilenamephoto1(savedInstanceState.getString("filenamephoto1"));

                currsave.setFilenamephoto2(savedInstanceState.getString("filenamephoto2"));

                currsave.setFilenamephoto3(savedInstanceState.getString("filenamephoto3"));

                currsave.setFilenameimage(savedInstanceState.getString("filenameimage"));

                currsave.setUuid_save(savedInstanceState.getString("uuid_save"));

                currsave.setEditing(savedInstanceState.getBoolean("editing"));

                currsave.setIndexphoto(Integer.parseInt(savedInstanceState.getString("indexphoto")));

                //  move=savedInstanceState.getBoolean("move");

                selectall2 = savedInstanceState.getBoolean("selectall2");
            }


        }


        //    AdRequest adRequest = new AdRequest.Builder().build();

        //   mAdView.loadAd(adRequest);
        if (savedInstanceState == null && quick == null) {
            this.setTitle(allSheets.getNametablesheet());

            IShowFragmentWL isfwl = this;

            isfwl.showFragmentWL();
        }


    }

    void goBackup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.ls7))
                .setItems(new String[]{getResources().getString(R.string.ls2), getResources().getString(R.string.ls3)}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            db2obl();
                        }

                        if (which == 1) {
                            obl2db();
                        }
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cansel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .setCancelable(false);
        AlertDialog alert = builder.create();
        alert.show();
    }

    void ustr() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.ls1))
                .setItems(new String[]{getResources().getString(R.string.ls2), getResources().getString(R.string.ls3)}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 1) {
                            file2db();
                        }

                        if (which == 0) {
                            db2file();
                        }
                    }
                })
                .setNegativeButton(getResources().getString(R.string.otmena), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .setCancelable(false);
        AlertDialog alert = builder.create();
        alert.show();
    }

    void obl() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.ls7))
                .setItems(new String[]{getResources().getString(R.string.ls2), getResources().getString(R.string.ls3)}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            db2obl();
                        }

                        if (which == 1) {
                            obl2db();
                        }
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cansel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .setCancelable(false);
        AlertDialog alert = builder.create();
        alert.show();
    }

    void obl2db() {
        action = 1;
        if (hasConnection()) {
            gd = new GoogleDrive(this);
            //  gd.download();
        } else {
            showToastMessage(getResources().getString(R.string.ls8));
            toDB();
        }
    }

    GoogleDrive gd;

    void db2obl() {
        action = 2;
        if (hasConnection()) {
            try {
                copyDataBase2();
            } catch (IOException e) {

            }
            gd = new GoogleDrive(this);
           // gd.upload("NBookBD.db");
        }

        else
        {
            Toast.makeText(this, getResources().getString(R.string.ls8), Toast.LENGTH_SHORT).show();
        }

    }

    void file2db() {
        try {
            copyDataBase(1);
        } catch (IOException e) {
            showToastMessage(getResources().getString(R.string.ls4));
        }

    }

    void db2file() {
        try {
            copyDataBase(2);
            showToastMessage(getResources().getString(R.string.ls5));
        } catch (IOException e) {
            showToastMessage(getResources().getString(R.string.ls6));
        }
    }


    public boolean hasConnection() {
        Context context = this;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        return false;
    }

    // private void showInterstitial() {
    // Show the ad if it's ready. Otherwise toast and restart the game.
    //   if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
    //       mInterstitialAd.show();
    //   } else {
//
//    }

    void showSettings() {
        startActivityForResult(new Intent(this, preference.class), 199);
    }


    @Override
    protected void onDestroy() {


        super.onDestroy();
    }


    @Override
    protected void onPause() {

        REST.disconnect();

        // showInterstitial();

        super.onPause();
    }

    void goRecy() {
        createRecycler();

        this.setTitle(allSheets.getNametablesheet());

        IShowFragmentWL isfwl = this;

        isfwl.showFragmentWL();
    }


    void goDlg() {

        createDlg();

        this.setTitle(allSheets.getNametablesheet());

        IShowFragmentWLDLG isfwl =  this;

        isfwl.showFragmentWLDLG();

        drawerlay.closeDrawers();
    }


    void goPDA() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://andros-app.ru/index.php/ru/?id=45"));

        startActivity(intent);
    }

    void goTZV() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=snyakovlev.unote"));

        startActivity(intent);
    }


    void goHome() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://synote-app.blogspot.com"));

        startActivity(intent);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        abdt.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        abdt.onConfigurationChanged(newConfig);
    }

    void createQS() {
        if (search() == null) {

            allSheets.setNametablesheet("QuickStart");

            allSheets.setTabletempltable(UUID.randomUUID().toString());

            allSheets.setPassword("admin");

            allSheets.addDataBase(false);
        } else

        {
            allSheets.setUuid_sheet(search());
            Log.v("wsxdc", "createQS " + search());

            allSheets.setTempltable(search2());

            allSheets.setTabletempltable(search3());

            allSheets.setNametablesheet("QuickStart");
        }
    }

    void createDlg() {
        if (searchRec() == null) {

            allSheets.setNametablesheet(getResources().getString(R.string.ga18));

            allSheets.setUuid_sheet("dlg0");

            allSheets.setPassword("admin");

            allSheets.addDataBase(true);
        }

        else

        {

            allSheets.setUuid_sheet(searchRec());

            allSheets.setNametablesheet(getResources().getString(R.string.ga18));
        }
    }

    void createRecycler() {
        if (searchRec() == null) {

            allSheets.setNametablesheet(getResources().getString(R.string.ga7));

            allSheets.setUuid_sheet("rec0");

            allSheets.setPassword("admin");

            allSheets.addDataBase(true);
        }
        else

        {

            allSheets.setUuid_sheet(searchRec());

            allSheets.setNametablesheet(getResources().getString(R.string.ga7));
        }
    }

    void quick2(String str) {

        Log.d("jhg", "quiq " + allSheets.getNametablesheet());

        currsave = new CurrSave(db, allSheets.getUuid_sheet());

        currsave.setUuid_save(new CreateUID(this).creatingUID() + "_save");

        currsave.setDatetime(formatDateTime());

        currsave.setValue("");

        currsave.setFilenamephoto1("");

        currsave.setFilenamephoto2("");

        currsave.setFilenamephoto3("");

        currsave.setFilename("");

        currsave.setFilenameimage("");

        Random rnd=new Random();
        int r=rnd.nextInt(10);
        int g=rnd.nextInt(10);
        int b=rnd.nextInt(10);
        int col= Color.rgb(245+r,245+g,245+b);
        Log.v("qqaass",col+"");
        PreferenceManager.getDefaultSharedPreferences(this).edit().putInt("fon_col_" + currsave.getUuid_save(), col).commit();

    }


    void quick(String str) {

        Log.d("jhg", "quiq " + allSheets.getNametablesheet());

        currsave = new CurrSave(db, allSheets.getUuid_sheet());

        currsave.setUuid_save(new CreateUID(this).creatingUID() + "_save");

        currsave.setDatetime(formatDateTime());

        currsave.setValue("");

        currsave.setFilenamephoto1("");

        currsave.setFilenamephoto2("");

        currsave.setFilenamephoto3("");

        currsave.setFilename("");

        currsave.setFilenameimage("");

        currsave.addDataBase();

        Random rnd=new Random();
        int r=rnd.nextInt(25);
        int g=rnd.nextInt(25);
        int b=rnd.nextInt(25);
        int col= Color.rgb(230+r,230+g,230+b);
        Log.v("qqaass",col+"");
        PreferenceManager.getDefaultSharedPreferences(this).edit().putInt("fon_col_" + currsave.getUuid_save(), col).commit();
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

    String search() {

        String ret = db.queryMainTable();

        return ret;
    }


    String searchRec() {

        String ret = db.queryMainTableRec(this);

        return ret;
    }

    String searchDlg() {

        String ret = db.queryMainTableDlg(this);

        return ret;
    }

    String search2() {

        String ret = db.queryMainTable2();

        return ret;
    }

    String search3() {

        String ret = db.queryMainTable3();

        return ret;
    }


    void text() {

        permStorage();

        Log.d("jhg", "text " + allSheets.getNametablesheet());

        currsave = new CurrSave(db, allSheets.getUuid_sheet());

        this.setTitle(allSheets.getNametablesheet());

        IShowFragmentED ied = this;

        ied.showFragmentED(currsave);

    }

    void openet() {
        // String uuidcurrsave=getIntent().getStringExtra("currsave");

//        this.setTitle(allSheets.getNametablesheet());

        //      IShowFragmentED ied =this;

        //    ied.showFragmentED(currsave);


    }

    void qs() {
        IShowFragmentWL isfwl = this;

        isfwl.showFragmentWL();
    }
    boolean q=false;


    boolean photo() {

        if (!permCamera()) return false;

        q=true;

        quick2("");

        this.setTitle(allSheets.getNametablesheet());

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        PhotoCreater ph = new PhotoCreater(this,currsave);

        ph.createPhoto(0,true);

        return true;


    }

    void postpermCamera() {

    }

    void image() {

        permStorage();

        quick("");

        this.setTitle(allSheets.getNametablesheet());

        ICreaterPaint ipv = this;

        ipv.showPaintCreator("", false);


    }

    void permrec() {
        permRecord();
    }

    void mickr() {

        Intent inte = new Intent(this, Dickt.class);

        inte.putExtra("stop", "stop");

        stopService(inte);

        currsave = new CurrSave(db, allSheets.getUuid_sheet());

        currsave.setUuid_save(new CreateUID(this).creatingUID() + "_save");

        currsave.setDatetime(formatDateTime());

        currsave.setValue("");

        currsave.setFilename(getIntent().getStringExtra("filename"));

        Log.v("dfgh", "GlavAct.filename:" + getIntent().getStringExtra("filename"));

        currsave.addDataBase();

        Toast.makeText(this, getResources().getString(R.string.ga8), Toast.LENGTH_LONG).show();

        this.setTitle(allSheets.getNametablesheet());

        IShowFragmentWL isfwl = this;

        isfwl.showFragmentWL();

        this.finish();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putBoolean("shmove", shmove);

        outState.putBoolean("selectall", selectall);

        if (phfile != null) {
            outState.putString("phfile", phfile.getAbsolutePath());
        }

        outState.putString("uuidsheet", allSheets.getUuid_sheet());

        outState.putString("tabletempltable", allSheets.getTabletempltable());

        outState.putString("templtable", allSheets.getTempltable());

        outState.putString("password", allSheets.getPassword());

        outState.putString("nametablesheet", allSheets.getNametablesheet());

        outState.putBoolean("setpass", allSheets.isSetPass());

        outState.putBoolean("ispotok", isPotok);

        outState.putBoolean("lsheet", lsheet);

        if (currsave != null) {

            outState.putString("uuid_table_table", currsave.getUuid_table_table());

            outState.putString("name_table_table", currsave.getName_table_table());

            Log.v("qwerty", "savinst:" + currsave.getName_table_table());

            outState.putBoolean("currsave", true);

            outState.putString("value", currsave.getValue());

            outState.putString("text", currsave.getText());

            outState.putString("filename", currsave.getFilename());

            outState.putString("filenamephoto1", currsave.getFilenamephoto1());

            outState.putString("filenamephoto2", currsave.getFilenamephoto2());

            outState.putString("filenamephoto3", currsave.getFilenamephoto3());

            outState.putString("filenameimage", currsave.getFilenameimage());

            outState.putString("uuid_save", currsave.getUuid_save());

            outState.putBoolean("editing", currsave.isEditing());

            outState.putString("indexphoto", currsave.getIndexphoto() + "");

            //   outState.putBoolean("move", move);

            outState.putBoolean("selectall2", selectall2);


        } else {
            outState.putBoolean("currsave", false);
        }


        super.onSaveInstanceState(outState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main6, menu);
        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (abdt.onOptionsItemSelected(item)) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
//-----------------showListSheet--------------------

    boolean lsheet;

    @Override
    public void showFragmentLS() {

        ListSheets ls = new ListSheets();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.header, ls)
                .commit();

    }


    //-----------------------showWorkList----------------------
    @Override
    public void showFragmentWL() {


        WorkList wl = WorkList.newInstance();

        Fragment ff1 = getSupportFragmentManager().findFragmentById(R.id.footer);

        if (ff1 != null && ff1.getClass().equals(FragmentFooterMenu1.class)) {

            getSupportFragmentManager().beginTransaction()

                    .remove((FragmentFooterMenu1) ff1)

                    .commit();
        }

        if (ff1 != null && ff1.getClass().equals(Calculator.class)) {

            getSupportFragmentManager().beginTransaction()

                    .remove((Calculator) ff1)

                    .commit();
        }

        if (ff1 != null && ff1.getClass().equals(Colrowedit.class)) {

            getSupportFragmentManager().beginTransaction()

                    .remove((Colrowedit) ff1)

                    .commit();
        }


        getSupportFragmentManager().beginTransaction()

                .replace(R.id.header, wl)

                .commit();
    }


    //------------------showEditeData----------------
    @Override
    public void showFragmentED(CurrSave currsave) {

        Log.d("jhg", "showFED " + allSheets.getNametablesheet());


        EditData ed = EditData.newInstance(currsave);

        getSupportFragmentManager().beginTransaction()

                .replace(R.id.header, ed)

                .commit();
    }

    @Override
    public void showFragmentET(CurrSave currsave, boolean list) {

        Log.d("jhg", "showFED " + allSheets.getNametablesheet());


        EditTable et = EditTable.newInstance(currsave, list);

        getSupportFragmentManager().beginTransaction()

                .replace(R.id.header, et)

                .commit();


    }


    @Override

    public void createSheet(boolean ls) {

        lsheet = ls;

        FragmentManager fm = getSupportFragmentManager();

        CreateSheet dialog = new CreateSheet();

        dialog.show(fm, "addsheet");
    }

    @Override
    protected void onResume() {

        // REST.connect();
        // showInterstitial();

        super.onResume();


    }

    @Override
    public void showLocking(String nametable) {
        FragmentManager fm = getSupportFragmentManager();

        Locking dialog = Locking.newInstance(nametable);

        dialog.show(fm, "locking");


    }

    @Override
    public void showEnterPassword(String nametable) {

        FragmentManager fm = getSupportFragmentManager();

        EnterPassword dialog = EnterPassword.newInstance(nametable);

        dialog.show(fm, "enterpassword");

    }


    @Override
    public void onBackPressed() {
        Fragment pv = getSupportFragmentManager().findFragmentById(R.id.header);

        if (pv.getClass().equals(ListSheets.class)) {
            if (this.shmove) {
                ((ListSheets) pv).back();
                return;
            }
        }
        if (pv.getClass().equals(WorkList.class)) {
            if (((WorkList) pv).move && !((WorkList) pv).recy) {
                ((WorkList) pv).back();
                return;
            } else {
                showFragmentLS();
                return;
            }
        }

        if (pv.getClass().equals(DLG.class)) {

                showFragmentLS();
                return;

        }
        if (pv.getClass().equals(CreateTable.class)) {
            drawerlay.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            showFragmentWL();
            return;
        }
        if (pv.getClass().equals(EditTable.class)) {
            ((EditTable) pv).hidecalc();
            ((EditTable) pv).hideeditcolrow();
            showFragmentWL();
            return;
        }

        if (pv.getClass().equals(EditList.class)) {
            showFragmentWL();
            return;
        }
        if (pv.getClass().equals(EditData.class)) {
            ((EditData) pv).cancel();
            showFragmentWL();
            return;
        }
        if (pv.getClass().equals(PhotoView.class)) {
            showFragmentWL();
            return;
        }
        if (pv.getClass().equals(PaintCreater.class)) {
            ((PaintCreater) pv).back();
            return;
        }






      if((bp+1700)>System.currentTimeMillis())

    {
       //if (mInterstitialAd!=null && mInterstitialAd.isLoaded()) mInterstitialAd.show();
      super.onBackPressed();
    }
    else
      {
          Toast.makeText(this, getResources().getString(R.string.gl1), Toast.LENGTH_SHORT).show();
      }
      bp=System.currentTimeMillis();

}

    long bp = 0;

    int rekl = 0;

    void finishh() {
      //  rekl++;
        //PreferenceManager.getDefaultSharedPreferences(this).edit().putInt("rekl", rekl).commit();
        //if (rekl % 3 == 0 && rekl != 0) showInterstitial();
        finish();

    }

    public File phfile = null;

    public String paintfile = null;

    private static final int REQ_ACCPICK = 1;
    private static final int REQ_CONNECT = 2;


    private void suicide(int rid) {
        UT.AM.setEmail(null);
        Toast.makeText(this, rid, Toast.LENGTH_LONG).show();
        // finish();
    }
    Bitmap bitmap=null;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQ_CONNECT:
                Log.v("qwqwqq",data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME));
                if (resultCode == RESULT_OK) {

                    REST.connect(this, UT.AM.getEmail());
                    UT.lg("act result - YES AUTH");
                }
                else {
                    UT.lg("act result - NO AUTH");
                    suicide(R.string.err_auth_nogo);  //---------------------------------->>>
                }
                break;
            case REQ_ACCPICK: {


                if (data != null && data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME) != null) {


                    Log.v("qwqwqq", data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME));
                    UT.AM.setEmail(data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME));
                    REST.init(this);
                    REST.connect(this, UT.AM.getEmail());
                    if (action == 1) {
                        gd.download();
                    }
                    if (action == 2) {
                        gd.upload("NBookBD.db");
                    }
                }

                else

                    return;

            }
                break;
        }


        if (requestCode == 120 && resultCode == RESULT_OK) {

            Fragment pv = getSupportFragmentManager().findFragmentById(R.id.header);



            if (pv.getClass().equals(PhotoView.class)) {


                currsave.updateDataBase();

                ((PhotoView)pv).initFlip();
            }

            if (pv.getClass().equals(WorkList.class)) {

                currsave.addDataBase();

                 String  filename = currsave.getFilenamephoto1();

              final   File outFileMaxi,outFileMini;

                File root;

                if (isAvailable() || !isReadOnly()) {

                    root = Environment.getExternalStorageDirectory();
                } else {
                    root = this.getFilesDir();
                }

                String root_str = root.getAbsolutePath();

                String my_dir_maxi = root_str + File.separator + "nbook" + File.separator + "photo";

                outFileMaxi = new File(my_dir_maxi, filename + ".jpg");

                String my_dir_mini = root_str + File.separator + "nbook" + File.separator + "mini";

                outFileMini = new File(my_dir_mini, filename + ".jpg");

                outFileMini.getParentFile().mkdirs();


                try {
                    outFileMini.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                saveMini(outFileMaxi,outFileMini,pv);

                ((WorkList)pv).initList(this);


            }

            if (pv.getClass().equals(ListSheets.class)) {

                currsave.addDataBase();

                Toast.makeText(this, getResources().getString(R.string.w3), Toast.LENGTH_SHORT).show();

                String  filename = currsave.getFilenamephoto1();

                final   File outFileMaxi,outFileMini;

                File root;

                if (isAvailable() || !isReadOnly()) {

                    root = Environment.getExternalStorageDirectory();
                } else {
                    root = this.getFilesDir();
                }

                String root_str = root.getAbsolutePath();

                String my_dir_maxi = root_str + File.separator + "nbook" + File.separator + "photo";

                outFileMaxi = new File(my_dir_maxi, filename + ".jpg");

                String my_dir_mini = root_str + File.separator + "nbook" + File.separator + "mini";

                outFileMini = new File(my_dir_mini, filename + ".jpg");

                outFileMini.getParentFile().mkdirs();


                try {
                    outFileMini.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }


              saveMini(outFileMaxi,outFileMini,pv);




            }



            // currsave.setPhotocreated(true);
        }

        if (requestCode == 199) {
            Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }

        if (requestCode == 230 && resultCode == RESULT_OK) {
            Uri image = data.getData();


            File outFile;

            File root;

            if (isAvailable() || !isReadOnly()) {

                root = Environment.getExternalStorageDirectory();
            } else {
                root = this.getFilesDir();
            }

            String root_str = root.getAbsolutePath();

            String my_dir = root_str + File.separator + "nbook" + File.separator + "image";

            outFile = new File(my_dir, paintfile + ".png");

            this.currsave.setFilenameimage(paintfile);

            this.currsave.updateDataBase();

            outFile.getParentFile().mkdirs();


            if (outFile.exists()) {
                outFile.delete();
            }

            try {
                outFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }


            Log.v("fgbv", image.getPath());



            try {

                Bitmap img = MediaStore.Images.Media.getBitmap(getContentResolver(), image);

                FileOutputStream fos = new FileOutputStream(outFile);

                img.compress(Bitmap.CompressFormat.PNG, 85, fos);

                fos.flush();

                fos.close();

                PaintCreater pv = (PaintCreater) getSupportFragmentManager().findFragmentById(R.id.header);

                Log.v("qqawe",paintfile);

                Bitmap x=img.copy(Bitmap.Config.ARGB_8888, true);

                pv.appv.f.drawBitmap(x,0,0,null);

            } catch (Exception e) {

                Log.v("fgbv", e.getMessage());

            }



        }

        if (requestCode == 220 && resultCode == RESULT_OK) {

            Uri image = data.getData();


            Log.v("fgbv", image.getPath());

            Bitmap img=null;
            try {

                 img= MediaStore.Images.Media.getBitmap(getContentResolver(), image);

                if (img.getByteCount()==0)
                {
                   img=BitmapFactory.decodeResource(getResources(),R.drawable.photo);

                }

                Log.v("fgbv", phfile.getAbsolutePath());

                FileOutputStream fos = new FileOutputStream(phfile);

                img.compress(Bitmap.CompressFormat.JPEG, 55, fos);

                fos.flush();

                fos.close();
            } catch (Exception e) {

                Log.v("fgbv", e.getMessage());

            }

            currsave.updateDataBase();

            PhotoView pv = (PhotoView) getSupportFragmentManager().findFragmentById(R.id.header);

            pv.initFlip();

            String  filename = currsave.getFilenamephoto1();

            final   File outFileMaxi,outFileMini;

            File root;

            if (isAvailable() || !isReadOnly()) {

                root = Environment.getExternalStorageDirectory();
            } else {
                root = this.getFilesDir();
            }

            String root_str = root.getAbsolutePath();

            outFileMaxi = phfile;

            String my_dir_mini = root_str + File.separator + "nbook" + File.separator + "mini";

            outFileMini = new File(my_dir_mini, filename + ".jpg");

            outFileMini.getParentFile().mkdirs();


            try {
                outFileMini.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            saveMini(outFileMaxi,outFileMini,null);


        }


        if (requestCode == 121 && resultCode == RESULT_OK) {


            ArrayList<String> textMatchlist = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            if (!textMatchlist.isEmpty()) {


                String text = textMatchlist.get(0);

                FragmentManager fm = getSupportFragmentManager();

                EditData ed = (EditData) fm.findFragmentById(R.id.header);

                ed.setRecogn(text);


            }
        } else if (resultCode == RecognizerIntent.RESULT_AUDIO_ERROR) {
            showToastMessage("Audio Error");

        } else if ((resultCode == RecognizerIntent.RESULT_CLIENT_ERROR)) {
            showToastMessage("Client Error");

        } else if (resultCode == RecognizerIntent.RESULT_NETWORK_ERROR) {
            showToastMessage("Network Error");
        } else if (resultCode == RecognizerIntent.RESULT_NO_MATCH) {
            showToastMessage("No Match");
        } else if (resultCode == RecognizerIntent.RESULT_SERVER_ERROR) {
            showToastMessage("Server Error");
        }


        // super.onActivityResult(requestCode, resultCode, data);

    }

    int height=0,width=0;


    void saveMini(final File outFileMaxi,final File outFileMini,final Fragment fr)
    {
        try {

            final BitmapFactory.Options options = new BitmapFactory.Options();

            options.inJustDecodeBounds = true;

            BitmapFactory.decodeFile(outFileMaxi.getAbsolutePath(), options);

            int h = options.outHeight;

            int w = options.outWidth;

            boolean port=false;



            double k = 1;

            if (h > w)
            {k = (double) h / w;port=true;}

            if (h < w)
            {k = (double) w / h;
            port=false;}

            if (h == w) k = 1;

            DisplayMetrics m = getResources().getDisplayMetrics();

            float dw=m.widthPixels/m.density;

            int t=(int)(dw/160);

            int  wp=(int)((m.widthPixels/(t*m.density))-(m.density*6));

             width = wp;
            if (port) {
                  height = (int) (wp * k);
            }
            else {
                 height = (int) (wp /k);
            }
            Log.v("aassw","do "+"h="+h+" w="+w +" width="+width+" height="+height);
            new Thread(new Runnable(){
                @Override
                public void run() {
                    try {

                            bitmap = picasso.load(outFileMaxi).resize(width, height).get();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(outFileMini);


                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                        out.flush();
                        out.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    options.inJustDecodeBounds = true;

                    BitmapFactory.decodeFile(outFileMini.getAbsolutePath(), options);

                    int h = options.outHeight;

                    int w = options.outWidth;

                    Log.v("aassw","posle "+"h="+h+" w="+w +" width="+width+" height="+height);

                }
            }).start();

        }
        catch (Exception e)
        {

        }

        if (fr!=null && fr.getClass().equals(WorkList.class)){
            ((WorkList) fr).initList(this);
            ((WorkList) fr).notifList(true);
        }

            if (fr!=null && fr.getClass().equals(ListSheets.class)) {
            if (q) this.finish();
        }


    }


    void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    @Override
    public void photoView(boolean quick) {

        PhotoView pV = PhotoView.newInstance(quick);

        getSupportFragmentManager().beginTransaction()

                .replace(R.id.header, pV)

                .commit();

    }


    @Override
    public void showPaintCreator(String filename, boolean photo) {


        PaintCreater pC = PaintCreater.newInstance(filename, photo);

        getSupportFragmentManager().beginTransaction()

                .replace(R.id.header, pC)

                .commit();

        FragmentFooterMenu1 ffm1 = new FragmentFooterMenu1();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.footer, ffm1)
                .commit();


    }


    int action = 0;


    final Glavnoe_Activity context = this;
    volatile int index = 0;
    String path4;
    volatile String title;
    FileWriter fstream;
    volatile BufferedWriter out;


    //-------------------------Save------------------------

    volatile ArrayList<Files> fileslist = new ArrayList<>();

    public void filelisting() {

        fileslist.clear();
        Files fs = new Files();
        fs.file = db.dbPath;
        fs.title = db.getDatabaseName();
        fs.mimetype = "application/x-sqlite-3";
        fileslist.add(fs);
        if (getDirPhoto().listFiles() != null)

        {
            for (File file : getDirPhoto().listFiles()) {
                fs = new Files();
                fs.file = file;
                fs.title = file.getName();
                fs.mimetype = "image/jpeg";
                fileslist.add(fs);
            }
        }
        if (getDirImage().listFiles() != null) {
            for (File file : getDirImage().listFiles()) {
                fs = new Files();
                fs.file = file;
                fs.title = file.getName();
                fs.mimetype = "image/png";
                fileslist.add(fs);
            }
        }
        if (getDirAudio().listFiles() != null) {
            for (File file : getDirAudio().listFiles()) {
                fs = new Files();
                fs.file = file;
                fs.title = file.getName();
                fs.mimetype = "audio/amr";
                fileslist.add(fs);
            }
        }


    }


    void log(String s) {
        Log.v("log", s);
    }

    Files temp;

    ArrayList<Files> fy = new ArrayList<>();

    ArrayList<Files> fn = new ArrayList<>();

    String getDirRoot() {
        File root;

        if (isAvailable() || !isReadOnly()) {


            root = Environment.getExternalStorageDirectory();
            Log.v("qqqq","ExternStorage="+root);


        } else {
            Log.v("qqqq","InternalStorage");
            root = getFilesDir();
        }

        return root.getAbsolutePath();
    }

    public String[] getStorageDirectories() {
        String[] storageDirectories;
        String rawSecondaryStoragesStr = System.getenv("SECONDARY_STORAGE");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            List<String> results = new ArrayList<String>();
            File[] externalDirs = getExternalFilesDirs(null);
            for (File file : externalDirs) {
                if (file != null) {
                    String path = file.getPath().split("/Android")[0];
                    if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Environment.isExternalStorageRemovable(file))
                            || rawSecondaryStoragesStr != null && rawSecondaryStoragesStr.contains(path)) {
                        results.add(path);
                        Log.v("asxc", path);
                    }
                }
            }
            storageDirectories = results.toArray(new String[0]);
        } else {
            final Set<String> rv = new HashSet<String>();

            if (!TextUtils.isEmpty(rawSecondaryStoragesStr)) {
                final String[] rawSecondaryStorages = rawSecondaryStoragesStr.split(File.pathSeparator);
                Collections.addAll(rv, rawSecondaryStorages);
            }
            storageDirectories = rv.toArray(new String[rv.size()]);
        }
        return storageDirectories;
    }


    File getDirNbook() {
        return new File(getDirRoot() + File.separator + pa0);
    }

    File getDirPhoto() {
        return new File(getDirRoot() + File.separator + pa0 + File.separator + pa1);
    }

    File getDirImage() {
        return new File(getDirRoot() + File.separator + pa0 + File.separator + pa2);
    }

    File getDirAudio() {
        return new File(getDirRoot() + File.separator + pa0 + File.separator + pa3);
    }

    @Override
    public void showFragmentCT(CurrSave currsave, Glavnoe_Activity glav, boolean list) {

        CreateTable ct = CreateTable.newInstance(currsave, this, list);

        getSupportFragmentManager().beginTransaction()

                .replace(R.id.header, ct)

                .commit();

    }


    public String textAlarm = "";


    void dIshow(String m) {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.ga9))
                .setMessage(m)
                .setCancelable(false)
                .setNegativeButton(getResources().getString(R.string.ga10),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = getBaseContext().getPackageManager()
                                        .getLaunchIntentForPackage(getBaseContext().getPackageName());
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                finish();
                            }
                        })
                .setPositiveButton(getResources().getString(R.string.ga11),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    volatile String sdid;

    static final boolean file2Os(OutputStream os, File file, long size) {
        boolean bOK = false;
        InputStream is = null;
        if (file != null && os != null) try {
            byte[] buf = new byte[(int) size];
            is = new FileInputStream(file);
            int c;
            while ((c = is.read(buf, 0, buf.length)) > 0)
                os.write(buf, 0, c);
            bOK = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                os.flush();
                os.close();
                if (is != null) is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bOK;
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
    //------------------------Retrieve---------------------------


    String getDirPublic()

    {
        File root;

        if (isAvailable() || !isReadOnly()) {

            root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        } else {
            root = getFilesDir();
        }

        return root.getAbsolutePath();
    }


    public void share(String file, String tfile, String text) {//TODO

        namesf = null;
        namesf=file;

        if (text == null) {
            copyfile(file, tfile, text);
        } else
            sendtext(text);

    }

    void sendtext(String text) {
        Intent intent2 = new Intent(Intent.ACTION_SEND);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent2.putExtra(Intent.EXTRA_TEXT, text);
        intent2.setType("text/plain");
        intent2.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(intent2, getResources().getString(R.string.et2)));

    }


    void sendfile(String file, String tfile, String text) {

           Log.v("qqqsss","sendfile "+tfile);
        if (tfile.equals("text")) {
            Uri f=null;
            if (namesf != null) {


                if (Build.VERSION.SDK_INT >= 24) {
                    f = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider",new File(getDirPublic() + File.separator + namesf + ".txt"));
                } else {
                    f = Uri.fromFile(new File(getDirPublic() + File.separator + namesf + ".txt"));
                }
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Intent.EXTRA_STREAM, f);
                intent.setType("text/plain");
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(intent, getResources().getString(R.string.et2)));


            }
        }

        if (tfile.equals("photo")) {

            if (namesf != null) {
                Uri f=null;

                if (Build.VERSION.SDK_INT >= 24) {
                    f = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider",new File(getDirPublic() + File.separator + namesf + ".jpg"));
                } else {
                    f = Uri.fromFile(new File(getDirPublic() + File.separator + namesf + ".jpg"));
                }
                Log.i("qwertrewq", f.getEncodedPath());
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Intent.EXTRA_STREAM, f);
                intent.setType("image/jpeg");
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(intent, getResources().getString(R.string.et2)));
            }

        }

        if (tfile.equals("pdf")) {

            if (namesf != null) {
                Uri f=null;

                if (Build.VERSION.SDK_INT >= 24) {
                    f = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider",new File(getDirPublic() + File.separator + namesf + ".pdf"));
                } else {
                    f = Uri.fromFile(new File(getDirPublic() + File.separator + namesf + ".pdf"));
                }
                Log.i("qwertrewq", f.getEncodedPath());
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Intent.EXTRA_STREAM, f);
                intent.setType("application/pdf");
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(intent, getResources().getString(R.string.et2)));
            }

        }

        if (tfile.equals("image")) {

            Uri f=null;
            if (namesf != null) {
                if (Build.VERSION.SDK_INT >= 24) {
                     f = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider",new File(getDirPublic() + File.separator + namesf + ".png"));
                } else {
                     f = Uri.fromFile(new File(getDirPublic() + File.separator + namesf + ".png"));
                }
                Log.i("qwertrewq", f.getEncodedPath());
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Intent.EXTRA_STREAM, f);
                intent.setType("image/png");
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(intent, getResources().getString(R.string.et2)));
            }

        }

        if (tfile.equals("audio")) {
            if (namesf != null) {
                  Uri f=null;
                if (Build.VERSION.SDK_INT >= 24) {
                    f = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider",new File(getDirPublic() + File.separator + namesf + ".amr"));
                } else {
                    f = Uri.fromFile(new File(getDirPublic() + File.separator + namesf + ".amr"));
                }
                Log.i("qwertrewq", f.getEncodedPath());
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Intent.EXTRA_STREAM, f);
                intent.setType("audio/amr");
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(intent, getResources().getString(R.string.et2)));
            }

        }


    }


    String namesf;


    void copyfile(final String file, final String tfile, final String text) {

        try {
            Log.v("qqqsss",tfile);
            copy(file, tfile);
            sendfile(file, tfile, text);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    void copy(String file, String tfile) throws IOException {
        File to = null, from = null;

        if (tfile.equals("text")) {
            FileWriter fw = new FileWriter(new File(getDirPublic() + File.separator + namesf + ".txt"));
            fw.write(file);
            fw.close();
            return;
        }

        if (tfile.equals("photo")) {
            from = new File(getDirPhoto() + File.separator + file + ".jpg");
            Log.i("ioioi", from.getAbsolutePath());
            to = new File(getDirPublic() + File.separator + namesf + ".jpg");
            Log.i("ioioi", to.getAbsolutePath());
            if (!to.exists()) to.createNewFile();
        }

        if (tfile.equals("pdf")) {
            from = new File(getDirPhoto() + File.separator + file + ".pdf");
            Log.i("ioioi", from.getAbsolutePath());
            to = new File(getDirPublic() + File.separator + namesf + ".pdf");
            Log.i("ioioi", to.getAbsolutePath());
            if (!to.exists()) to.createNewFile();
        }


        if (tfile.equals("image")) {
            from = new File(getDirImage() + File.separator + file + ".png");
            Log.i("ioioi", from.getAbsolutePath());
            to = new File(getDirPublic() + File.separator + namesf + ".png");
            Log.i("ioioi", to.getAbsolutePath());
            if (!to.exists()) to.createNewFile();
        }

        if (tfile.equals("audio")) {
            from = new File(getDirAudio() + File.separator + file + ".3gp");
            Log.i("ioioi", from.getAbsolutePath());
            to = new File(getDirPublic() + File.separator + namesf + ".3gp");
            Log.i("ioioi", to.getAbsolutePath());
            if (!to.exists()) to.createNewFile();
        }


        FileInputStream input = new FileInputStream(from);


        OutputStream output = new FileOutputStream(to);

        byte[] buffer = new byte[8192];
        int length;
        while ((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }

        output.flush();
        output.close();
        input.close();


    }


    ArrayList<String> drids;


    void rnsheet() {
        if (ListSheetAdapter.sheetsformove.size() != 0) {
            FragmentManager fm = getSupportFragmentManager();

            RenameSheet dialog = new RenameSheet();

            dialog.show(fm, "renamesheet");
        } else {
            Toast.makeText(this, getResources().getString(R.string.ga14), Toast.LENGTH_SHORT).show();
        }
    }

    boolean sel = false;

    void delsheet() {

        if (ListSheetAdapter.sheetsformove.size() != 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.quest1))
                    .setCancelable(false)
                    .setNegativeButton(getResources().getString(R.string.cansel),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.cancel();
                                }
                            })
                    .setPositiveButton(getResources().getString(R.string.ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {


                                    Log.v("jklkj", allSheets.uuid_sheet);
                                    db.deleteTable(allSheets.uuid_sheet);
                                    ListSheets ls = (ListSheets) getSupportFragmentManager().findFragmentById(R.id.header);
                                    ls.initList();

                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            Toast.makeText(getApplication(), getResources().getString(R.string.ga15), Toast.LENGTH_SHORT).show();
        }


    }

    File getDirNbookBD(String root) {
        return new File(root + File.separator + pa0 + File.separator + pa4 + File.separator + "NBookBD.db");
    }

    void toDB ()
    {
        try {
        File to = null, from = null;

        String str=null;
        str= getDirRoot();

            from = getDirNbookBD(str);

            if (!from.exists())
            {
                showToastMessage(getResources().getString(R.string.ls4));
            return;
            }
            DataBaseInterface db=new DataBaseInterface(this);
            to = db.dbPath;


        FileInputStream input = null;

            input = new FileInputStream(from);



        OutputStream output = null;

            output = new FileOutputStream(to);


        byte[] buffer = new byte[8192];
        int length;

            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            db.openDataBase();
            dIshow(getResources().getString(R.string.ga16));



            output.flush();
        output.close();
        input.close();

       // if (from.exists()) from.delete();
        }



        catch (IOException e)
        {
            showToastMessage(getResources().getString(R.string.ls4));
    }
    }

    File getDirBackup()
    {
        return new File(getDirRoot() + File.separator + pa0 + File.separator + pa4 + File.separator + "NBookBD.db" );
    }

    void copyDataBase2() throws IOException {


          File  from = new File(db.DB_PATH + db.DB_NAME);
          File  to = getDirBackup();
          Log.v("asas","from="+from.getAbsolutePath()+"  to="+to.getAbsolutePath());
        copyFile(from,to,2);

    }


    void copyDataBase(int i) throws IOException {
        File to = null, from = null;

        String str=null;
        str= getStorageDirectories().length==0 ? getDirRoot():getStorageDirectories()[0];
        if (i == 1) {
            from = getDirNbookBD(str);
            if (!from.exists())
            {
                showToastMessage(getResources().getString(R.string.ls4));
                return;
            }

            to = new File(db.DB_PATH + db.DB_NAME);
        } else if (i == 2) {

            from = new File(db.DB_PATH + db.DB_NAME);
            to = getDirNbookBD(str);


        }


       copyFile(from,to,i);

    }

    void copyFile(File from,File to,int i)
    {
        try {
        FileInputStream input = null;

            input = new FileInputStream(from);



        OutputStream output = new FileOutputStream(to);

        byte[] buffer = new byte[8192];
        int length;
        while ((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }
        if (i == 1) {
            db.openDataBase();
            dIshow(getResources().getString(R.string.ga16));

        }
        output.flush();
        output.close();
        input.close();
        }
        catch
                (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnFail(Exception ex) {
        if (ex == null) {
            UT.lg("connFail - UNSPECD 1");
            suicide(R.string.err_auth_dono);
            return;  //---------------------------------->>>
        }
        if (ex instanceof UserRecoverableAuthIOException) {
            UT.lg("connFail - has res");
            startActivityForResult((((UserRecoverableAuthIOException) ex).getIntent()), REQ_CONNECT);
        } else if (ex instanceof GoogleAuthIOException) {
            UT.lg("connFail - SHA1?");
            if (ex.getMessage() != null) suicide(ex.getMessage());  //--------------------->>>
            else suicide(R.string.err_auth_sha);  //---------------------------------->>>
        } else {
            UT.lg("connFail - UNSPECD 2");
            suicide(R.string.err_auth_dono);  //---------------------------------->>>
        }
    }


    @Override
    public void onConnOK() {
        Toast.makeText(this,"CONNECTED TO: " + UT.AM.getEmail(), Toast.LENGTH_LONG).show();
    }

    private void suicide(String msg) {
        UT.AM.setEmail(null);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        // finish();
    }

    @Override
    public void showFragmentList(CurrSave currsave, boolean list) {

        EditList et = EditList.newInstance(currsave, list);

        getSupportFragmentManager().beginTransaction()

                .replace(R.id.header, et)

                .commit();

    }

    @Override
    public void showFragmentWLDLG() {

        DLG wl = DLG.newInstance();

        Fragment ff1 = getSupportFragmentManager().findFragmentById(R.id.footer);

        if (ff1 != null && ff1.getClass().equals(FragmentFooterMenu1.class)) {

            getSupportFragmentManager().beginTransaction()

                    .remove((FragmentFooterMenu1) ff1)

                    .commit();
        }

        if (ff1 != null && ff1.getClass().equals(Calculator.class)) {

            getSupportFragmentManager().beginTransaction()

                    .remove((Calculator) ff1)

                    .commit();
        }

        if (ff1 != null && ff1.getClass().equals(Colrowedit.class)) {

            getSupportFragmentManager().beginTransaction()

                    .remove((Colrowedit) ff1)

                    .commit();
        }


        getSupportFragmentManager().beginTransaction()

                .replace(R.id.header, wl)

                .commit();
    }

    @Override
    public void pdfView(boolean quick) {

        PdfView pV = PdfView.newInstance(quick);

        getSupportFragmentManager().beginTransaction()

                .replace(R.id.header, pV)

                .commit();
    }


    class Files {
        File file;
        String title;
        //   DriveId driveid;
        String mimetype;
        long filesize;
    }


}






    class AllSheets {

     Context context;

     String password = "";

     String datetime;

     String templtable;



        String tabletempltable="";

     String uuid_sheet;

     String uuid_save;

     boolean setPass;

     DataBaseInterface db;

     public AllSheets(Context context, DataBaseInterface db) {
         this.context = context;

         this.db = db;
     }


        public String getTabletempltable() {
            return tabletempltable;
        }

        public void setTabletempltable(String tabletempltable) {
            this.tabletempltable = tabletempltable;
        }

     public boolean isSetPass() {
         return setPass;
     }

     public void setSetPass(boolean setPass) {
         this.setPass = setPass;
     }


     public String getNametablesheet() {
         return nametablesheet;
     }

     public void setNametablesheet(String nametablesheet) {
         this.nametablesheet = nametablesheet;
     }

     String nametablesheet;

     public String getUuid_save() {
         return uuid_save;
     }

     public void setUuid_save(String uuid_save) {
         this.uuid_save = uuid_save;
     }

     public String getUuid_sheet() {

         return uuid_sheet;
     }

     public void setUuid_sheet(String uuid_sheet) {
         this.uuid_sheet = uuid_sheet;
     }

     public void createUuid_sheet(boolean rec) {

        if (!rec) setUuid_sheet(new CreateUID(context).creatingUID());

         setTempltable(new CreateUID(context).creatingUID());

         setTabletempltable(new CreateUID(context).creatingUID());
     }


     public void setTempltable(String templtable) {
         this.templtable = templtable;
     }

     public void setDatetime(String datetime) {

         this.datetime = datetime;
     }

     public void setPassword(String password) {

         this.password = password;

         if (this.password.equals("admin")) setPass = true;
     }


     public String getTempltable() {

         return templtable;
     }

     public String getDatetime() {

         return datetime;
     }

     public String getPassword() {

         return password;
     }

     public void updateDataBase() {

         db.updDB(getUuid_sheet(),getNametablesheet());

     }

     public void addDataBase(boolean rec) {

         createUuid_sheet(rec);

         Calendar cal = Calendar.getInstance();

         String datetime = (cal.get(Calendar.DAY_OF_MONTH) + "." + cal.get(Calendar.MONTH) + "." + cal.get(Calendar.YEAR) + "  " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));

         Log.v("wsxdc","add "+uuid_sheet);

         db.createTable(uuid_sheet, nametablesheet,templtable,tabletempltable, datetime);

         db.createTemplTable(templtable);

         db.createTemplTableTable(tabletempltable);

     }

 }

     class CurrSave {

         public int getIndexphoto() {
             return indexphoto;
         }

         public void setIndexphoto(int indexphoto) {
             this.indexphoto = indexphoto;
         }

         int indexphoto=0;

         boolean photocreated;

       DataBaseInterface db;

       String uuid_save = "";

       String uuidcurrsheet = "";

       String value = "";

       String text = "";

       String datetime = "";

       String filename = "";

       String filenamephoto1 = "";

       String filenamephoto2 = "";

       String filenamephoto3 = "";

       String filenameimage = "";

       String nametablesave = "";

         String uuid_table_table="";

         String name_table_table="";

       boolean editing;


         public boolean isPhotocreated() {
             return photocreated;
         }

         public void setPhotocreated(boolean photocreated) {
             this.photocreated = photocreated;
         }

       public boolean isEditing() {
           return editing;
       }

       public void setEditing(boolean editing) {
           this.editing = editing;
       }


       public CurrSave(DataBaseInterface db, String uuidcurrsheet) {
           this.db = db;

           this.uuidcurrsheet = uuidcurrsheet;
       }

         public String getUuid_table_table() {
             return uuid_table_table;
         }

         public void setUuid_table_table(String uuid_table_table) {
             this.uuid_table_table = uuid_table_table;
         }
         public String getName_table_table() {
             return name_table_table;
         }

         public void setName_table_table(String name_table_table) {
             this.name_table_table = name_table_table;
         }
       public String getNametablesave() {
           return nametablesave;
       }

       public void setNametablesave(String nametablesave) {
           this.nametablesave = nametablesave;
       }

       public String getUuid_save() {
           return uuid_save;
       }

       public void setUuid_save(String uuid_save) {
           this.uuid_save = uuid_save;
       }


       public String getFilenameimage() {
           return filenameimage;
       }

       public void setFilenameimage(String filenameimage) {
           this.filenameimage = filenameimage;
       }

       public String getFilenamephoto3() {

           return filenamephoto3;
       }

       public void setFilenamephoto3(String filenamephoto3) {
           this.filenamephoto3 = filenamephoto3;
       }

       public String getFilenamephoto2() {

           return filenamephoto2;
       }

       public void setFilenamephoto2(String filenamephoto2) {
           this.filenamephoto2 = filenamephoto2;
       }

       public String getFilenamephoto1() {

           return filenamephoto1;
       }

       public void setFilenamephoto1(String filenamephoto1) {
           this.filenamephoto1 = filenamephoto1;
       }

       public String getFilename() {

           return filename;
       }

       public void setFilename(String filename) {
           this.filename = filename;
       }

       public String getDatetime() {

           return datetime;
       }

       public void setDatetime(String datetime) {
           this.datetime = datetime;
       }

       public String getText() {

           return text;
       }

       public void setText(String text) {
           this.text = text;
       }

       public String getValue() {

           return value;
       }

       public void setValue(String value) {
           this.value = value;
       }


       public void addDataBase() {
           ContentValues cv = new ContentValues();

           cv.put("uuid_save", uuid_save);

           cv.put("filenamephoto1", filenamephoto1);

           cv.put("filenamephoto2", filenamephoto2);

           cv.put("filenamephoto3", filenamephoto3);

           cv.put("filenameimage", filenameimage);

           cv.put("filename", filename);

           cv.put("value", value);

           cv.put("text", text);

           cv.put("datetime", datetime);

           cv.put("uuid_table_table",uuid_table_table);

           cv.put("name_table_table",name_table_table);

           db.insertDataBase(uuidcurrsheet, cv);

       }

       public void updateDataBase() {
           ContentValues cv = new ContentValues();

           cv.put("filenamephoto1", filenamephoto1);

           cv.put("filenamephoto2", filenamephoto2);

           cv.put("filenamephoto3", filenamephoto3);

           cv.put("value", value);

           cv.put("text", text);

           cv.put("filename", filename);

           cv.put("filenameimage", filenameimage);

           cv.put("uuid_table_table",uuid_table_table);

           cv.put("name_table_table",name_table_table);

           db.updateDataBase(uuidcurrsheet, uuid_save, cv);
       }





    }
