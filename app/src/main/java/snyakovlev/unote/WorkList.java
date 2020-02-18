package snyakovlev.unote;

import android.app.SearchManager;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.content.ContentValues;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import  android.support.v7.widget.SearchView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;



/**
 * Created by Катя on 21.08.2015.
 */
public class WorkList extends Fragment implements IDelRowTableDataBase,IShowInfo, SearchView.OnQueryTextListener, SearchView.OnCloseListener, View.OnFocusChangeListener {

  AllSheets allsheets;

    RecyclerView list;

    Boolean editing = false;

    int position = 0;

    String _value = null;

    Glavnoe_Activity glav;

    Context ctx;

    int _count;

   boolean FAbstatus=false;



    String uuidcurrsheet;

    ArrayList<CurrSave> currsavearray=new ArrayList<CurrSave>();


    CurrSave currsave;
    FloatingActionButton fab2;
    FloatingActionButton fab21;
    FloatingActionButton fab22;
    FloatingActionButton fab23;
    FloatingActionButton fab24;
    FloatingActionButton fab25;
    FloatingActionButton fab26;

    TextView tt1,tt2,tt3,tt4,tt5,tt6;

    Animation sf21;
    Animation rot;
    Animation rot2;
    Animation hf21;
    static ArrayList<CurrSave> save_for_move=new ArrayList<>();

    TextView tv201;

 public    static WorkList newInstance() {

     WorkList wol = new WorkList();

     return wol;
 }



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        setHasOptionsMenu(true);

        db=new DataBaseInterface(getActivity());

        ctx = getActivity().getApplicationContext();

        glav=(Glavnoe_Activity)getActivity();

    }
int t=0;

boolean recy=false;
boolean dlg=false;
MenuItem item;
double koa;
int hp;
int wp;
    ArrayList<File> files;


    final String APPLICAITON_ID="5c07715339c82d6f008b4567";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (savedInstanceState!=null) tch=false;

        final View root = inflater.inflate(R.layout.worklist, container, false);


        list = (RecyclerView) root.findViewById(R.id.worklistlist);

        list.setItemViewCacheSize(0);

        list.setDrawingCacheEnabled(true);

        glav=(Glavnoe_Activity)getActivity();

        tv201=(TextView)root.findViewById(R.id.tv201);

        fab2 = (FloatingActionButton) root.findViewById(R.id.fab2);

        Display display=glav.getWindowManager().getDefaultDisplay();

        Point size=new Point();

        display.getSize(size);

        DisplayMetrics m=getResources().getDisplayMetrics();



        float dw=m.widthPixels/m.density;
        float dh=m.heightPixels/m.density;

         koa=(double)m.heightPixels/m.widthPixels;

        Log.v("axxc","koa="+koa);

        t=(int)(dw/160);

         wp=(int)((m.widthPixels/(t))-(m.density*6));

         hp=(int)(wp*koa);

         Log.v("axxc"," wp="+wp);









         if (PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt("reglay", 0)==1)

         {
             reglay = 1;
             LinearLayoutManager lm = new LinearLayoutManager(getActivity());
             list.setLayoutManager(lm);

         }
         else
         {
             reglay=0;
             StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(t,StaggeredGridLayoutManager.VERTICAL);
             list.setLayoutManager(mLayoutManager);
         }

       // StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(t,StaggeredGridLayoutManager.VERTICAL);

      //  list.setLayoutManager(mLayoutManager);

        list.setHasFixedSize(true);

        allsheets = ((Glavnoe_Activity) getActivity()).allSheets;

        uuidcurrsheet = allsheets.getUuid_sheet();

        getActivity().setTitle(allsheets.getNametablesheet());

        tt1=(TextView)root.findViewById(R.id.tt1);
        tt2=(TextView)root.findViewById(R.id.tt2);
        tt3=(TextView)root.findViewById(R.id.tt3);
        tt4=(TextView)root.findViewById(R.id.tt4);
        tt5=(TextView)root.findViewById(R.id.tt5);
        tt6=(TextView)root.findViewById(R.id.tt6);



        initFabs(root);

        if (allsheets.getNametablesheet().equals(getResources().getString(R.string.ga7)) || allsheets.getUuid_sheet().equals("rec0"))
        {
            recy=true;
            FAbstatus=false;
            hidefabs();
            move=true;
            fab2.hide();
        }

        else {
            recy=false;

            ListSheetAdapter2 adapter2 = new ListSheetAdapter2(getActivity(), initSheets());

            glav.lw.setAdapter(adapter2);

            FAbstatus = true;

            expandfabs();
        }

        initList(getActivity());

        setAdapter();



            fab2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.v("qqwer",FAbstatus+" "+move);

                    if (!FAbstatus & !move) {

                        expandfabs();

                        FAbstatus = true;
                    }

                    else
                        if (!move) {
                        hidefabs();

                        FAbstatus = false;
                    }

                }
            });



        return root;
    }



    ArrayList initSheets()
    {
        ArrayList<AllSheets> allsheetss;

        db=new DataBaseInterface(getActivity().getApplicationContext());

        if (glav.isSetPass)
        {
            allsheetss =db.queryMainTableAll2(db.nameMainTable,db.tab_param1,db.tab_param2,db.tab_param3,db.tab_param5,db.tab_param6,glav);

        }

        else

        {

            allsheetss = db.queryMainTableAll(db.nameMainTable, db.tab_param1,db.tab_param2, db.tab_param3,db.tab_param5,db.tab_param6,glav);
        }

        ArrayList<AllSheets> allremove=new ArrayList<>();

        for (int i=0;i<allsheetss.size();i++) {
            Log.v("nnnnnnmm", allsheetss.get(i).getNametablesheet()+"  "+allsheetss.get(i).getUuid_sheet());
            if (allsheetss.get(i).getNametablesheet().equals(getResources().getString(R.string.ga7))) {
                Log.v("nnnnnn", allsheetss.get(i).getNametablesheet());
                allremove.add(allsheetss.get(i));
            }
            else
           if (allsheetss.get(i).getUuid_sheet().equals("rec0"))
            {
                Log.v("nnnnnn", allsheetss.get(i).getNametablesheet());
                allremove.add(allsheetss.get(i));
            }

            if (allsheetss.get(i).getNametablesheet().equals(getResources().getString(R.string.ga18))) {
                Log.v("nnnnnn", allsheetss.get(i).getNametablesheet());
                allremove.add(allsheetss.get(i));
            }
            else
            if (allsheetss.get(i).getUuid_sheet().equals("dlg0"))
            {
                Log.v("nnnnnn", allsheetss.get(i).getNametablesheet());
                allremove.add(allsheetss.get(i));
            }

        }

            allsheetss.removeAll(allremove);


        return allsheetss;
    }


    void createSavePhoto()
    {
       if  (glav.permCamera()) {

          addBolvankaPhoto();

          createPhoto(true);
       }
    }

    void createPhoto(boolean create) {

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            glav.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            glav.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        PhotoCreater ph = new PhotoCreater(glav,currsave);

        ph.createPhoto(0,create);

        initList(getActivity());

        notifList(true);

    }

    AudioRecorder aurec;

    boolean recording,play;

    int i=1;

    void createSaveList()
    {
        glav.permStorage();

        IShowFragmentList ied =(IShowFragmentList)getActivity();

        LockOrientation.getInstance(getActivity()).lock();

        CurrSave currsave=new CurrSave(glav.db,glav.allSheets.getUuid_sheet());

        glav.currsave=currsave;

        if (currsave != null) {

            formatDateTime();

            glav.currsave.setUuid_save(new CreateUID(getActivity()).creatingUID() + "_save");

            glav.currsave.setDatetime(_datetime);

            glav.currsave.setEditing(false);

            glav.currsave.setUuid_table_table("");



            ied.showFragmentList(glav.currsave,true);

            Random rnd=new Random();
            int r=rnd.nextInt(55);
            int g=rnd.nextInt(55);
            int b=rnd.nextInt(55);
            int col= Color.rgb(200+r,200+g,200+b);
            Log.v("qqaass",col+"");
            PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putInt("fon_col_" + glav.currsave.getUuid_save(), col).commit();


        }

    }


    void createSaveRecord()
    {




        if (i==3)i=1;

        if (!glav.isPotok & i==1) {

            LockOrientation.getInstance(glav).lock();

            addBolvanka();

             Log.v("edcvf",currsave.filename);

            aurec = new AudioRecorder(getActivity(),currsave);

            fab23.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.nomic));

            aurec.recordStart();

            glav.isPotok = true;

            recording = true;
        }


        if (i==2 & recording) {

            LockOrientation.getInstance(glav).unlock();

            Log.v("gg","rec_stop");


            fab23.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.mic));

            glav.isPotok = false;

            play = true;



            currsave.setFilename(aurec.filename);

            currsave.updateDataBase();

            aurec.recordStop();

            recording = false;

            initList(getActivity());

            notifList(true);
        }


        i++;


    }

    void createSavePaint()
    {
        glav.permStorage();

        addBolvanka();

        ICreaterPaint ipv = (ICreaterPaint) getActivity();

        ipv.showPaintCreator("",false);

    }

    void createSaveText()
    {
        glav.permStorage();

        IShowFragmentED ied =(IShowFragmentED)getActivity();

        CurrSave currsave=new CurrSave(glav.db,glav.allSheets.getUuid_sheet());
        Random rnd=new Random();
        int r=rnd.nextInt(55);
        int g=rnd.nextInt(55);
        int b=rnd.nextInt(55);
        int col= Color.rgb(200+r,200+g,200+b);
        Log.v("qqaass","uuid="+currsave.getUuid_save());
        PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putInt("fon_col_" + currsave.getUuid_save(), col).commit();


        ied.showFragmentED(currsave);
        glav.currsave=currsave;




    }

    void createSaveTable()
    {

        glav.permStorage();

        IShowFragmentET ied =(IShowFragmentET)getActivity();

        LockOrientation.getInstance(getActivity()).lock();

        CurrSave currsave=new CurrSave(glav.db,glav.allSheets.getUuid_sheet());

        glav.currsave=currsave;

        if (currsave != null) {

            formatDateTime();

            glav.currsave.setUuid_save(new CreateUID(getActivity()).creatingUID() + "_save");

            glav.currsave.setDatetime(_datetime);

            glav.currsave.setEditing(false);

             glav.currsave.setUuid_table_table("");

            Random rnd=new Random();
            int r=rnd.nextInt(55);
            int g=rnd.nextInt(55);
            int b=rnd.nextInt(55);
            int col= Color.rgb(200+r,200+g,200+b);
            Log.v("qqaass",col+"");
            PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putInt("fon_col_" + glav.currsave.getUuid_save(), col).commit();

            ied.showFragmentET(glav.currsave,false);


        }

    }


    void initFabs(View root)
    {

      fab21=(FloatingActionButton)root.findViewById(R.id.fab21);
       fab22=(FloatingActionButton)root.findViewById(R.id.fab22);
       fab23=(FloatingActionButton)root.findViewById(R.id.fab23);
         fab24=(FloatingActionButton)root.findViewById(R.id.fab24);
        fab25=(FloatingActionButton)root.findViewById(R.id.fab25);
        fab26=(FloatingActionButton)root.findViewById(R.id.fab26);



        sf21= AnimationUtils.loadAnimation(glav,R.anim.sf21);
        rot=AnimationUtils.loadAnimation(glav,R.anim.rot);
        rot2=AnimationUtils.loadAnimation(glav,R.anim.rot2);

        hf21= AnimationUtils.loadAnimation(glav,R.anim.hf21);



        fab21.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


         if (FAbstatus)
         {createSavePhoto();}
         else
         {
             fab21.setVisibility(View.GONE);
         }

        }
    });
        fab22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (FAbstatus)   {createSaveText();}

                else
                {
                    fab22.setVisibility(View.GONE);
                }
            }
        });
        fab23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (FAbstatus)  {glav.permRecord();}
                else
                {
                    fab23.setVisibility(View.GONE);
                }

            }
        });

        fab24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (FAbstatus) {createSavePaint();}
                else
                {
                    fab24.setVisibility(View.GONE);
                }

            }
        });

        fab25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (FAbstatus)  { createSaveTable();}
                else
                {
                    fab25.setVisibility(View.GONE);
                }

            }
        });

        fab26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (FAbstatus)    {createSaveList();}
                else
                {
                    fab26.setVisibility(View.GONE);
                }

            }
        });


    }


void expandfabs()
{
  ///  fab21.setEnabled(true);
  //  fab22.setEnabled(true);
  //  fab23.setEnabled(true);
 //   fab24.setEnabled(true);
  //  fab25.setEnabled(true);
  //  fab26.setEnabled(true);



   // fab21.startAnimation(sf21);
   // fab22.startAnimation(sf21);
   // fab23.startAnimation(sf21);
   // fab24.startAnimation(sf21);
   // fab25.startAnimation(sf21);
   // fab26.startAnimation(sf21);

     fab21.show();
    fab22.show();
    fab23.show();
    fab24.show();
    fab25.show();
    fab26.show();

    Log.v("sxccv",PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("viewsign", true)+"");

    if ( PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("viewsign", true)) {

        tt1.setVisibility(View.VISIBLE);
        tt2.setVisibility(View.VISIBLE);
        tt3.setVisibility(View.VISIBLE);
        tt4.setVisibility(View.VISIBLE);
        tt5.setVisibility(View.VISIBLE);
        tt6.setVisibility(View.VISIBLE);
    }

    fab2.invalidate();

    fab2.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.krestic2));

}

    void hidefabs()
    {



            if (tt1 != null) {
                tt1.setVisibility(View.GONE);
                tt2.setVisibility(View.GONE);
                tt3.setVisibility(View.GONE);
                tt4.setVisibility(View.GONE);
                tt5.setVisibility(View.GONE);
                tt6.setVisibility(View.GONE);
            }


        fab21.hide();
        fab22.hide();
        fab23.hide();
        fab24.hide();
        fab25.hide();
        fab26.hide();

        fab2.invalidate();

        fab2.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.plusic));

    }



  public   void initList(Context ctx) {

        glav.permStorage();

        glav.setTitle(allsheets.getNametablesheet());


      _count=db.getCountDataBaseAll(allsheets.getUuid_sheet());


        currsavearray.clear();

        for (int i=_count-1;i>-1;i--) {

            currsave = new CurrSave(glav.db,allsheets.getUuid_sheet());

            currsave.setUuid_save(db.queryDataBaseAll(allsheets.getUuid_sheet(), "uuid_save").get(i));

            currsave.setValue(db.queryDataBaseAll(allsheets.getUuid_sheet(), "value").get(i));

            currsave.setText(db.queryDataBaseAll(allsheets.getUuid_sheet(), "text").get(i));

            currsave.setFilename(db.queryDataBaseAll(allsheets.getUuid_sheet(), "filename").get(i));

            Log.v("dfgh","WL.initList:"+db.queryDataBaseAll(allsheets.getUuid_sheet(), "filename").get(i));

            currsave.setFilenamephoto1(db.queryDataBaseAll(allsheets.getUuid_sheet(), "filenamephoto1").get(i));

            currsave.setFilenamephoto2(db.queryDataBaseAll(allsheets.getUuid_sheet(), "filenamephoto2").get(i));

            currsave.setFilenamephoto3(db.queryDataBaseAll(allsheets.getUuid_sheet(), "filenamephoto3").get(i));

            currsave.setFilenameimage(db.queryDataBaseAll(allsheets.getUuid_sheet(), "filenameimage").get(i));

            currsave.setDatetime(db.queryDataBaseAll(allsheets.getUuid_sheet(), "datetime").get(i));

            currsave.setUuid_table_table(db.queryDataBaseAll(allsheets.getUuid_sheet(), "uuid_table_table").get(i));

            currsave.setName_table_table(db.queryDataBaseAll(allsheets.getUuid_sheet(), "name_table_table").get(i));

            currsavearray.add(currsave);

            Log.v("wsxcv",currsavearray.size()+"");
        }

            if (ctx == null) {
                ctx = getActivity();
            }

            Log.v("ertgb",FAbstatus+"");

            if (currsavearray.size()==0 & !recy )

            {
                if (!FAbstatus) {
                    tv201.setVisibility(View.VISIBLE);
                    list.setVisibility(View.GONE);
                    if (fab2 != null) fab2.show();
                }
            }

            else
            {
                if (FAbstatus) {
                    tv201.setVisibility(View.GONE);
                    list.setVisibility(View.VISIBLE);
                }
            }

        if (currsavearray.size()==0 & recy )
        {
            list.setVisibility(View.GONE);
            tv201.setVisibility(View.VISIBLE);
            tv201.setText(getResources().getString(R.string.wl1));

        }

        if (recy | dlg) glav.drawerlay.closeDrawers();





    }

    void notifList(boolean izm)
    {

        adapter.values=currsavearray;

        adapter.updListFile();

      if (izm)  adapter.checs=new boolean[currsavearray.size()];

        list.getAdapter().notifyDataSetChanged();
    }

    void notifList2(ArrayList<CurrSave> cs,boolean izm)
    {

        adapter.values=cs;

        adapter.updListFile();

        if (izm)  adapter.checs=new boolean[cs.size()];

        list.getAdapter().notifyDataSetChanged();
    }


    void setAdapter()
    {


        adapter = new WorkAdapter(glav, currsavearray,this,move);


        list.setAdapter(adapter);

    }
    WorkAdapter  adapter;
    Menu menu1;

   public WorkList(){}

   SearchView search;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.


        menu1=menu;

        minfl=inflater;

        inflater.inflate(R.menu.menu_main2, menu);

      if (reglay==1)  menu1.getItem(3).setIcon(R.drawable.grid);
        if (reglay==0)  menu1.getItem(3).setIcon(R.drawable.line);

        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem=menu.findItem(R.id.search);
        search=(SearchView) searchItem.getActionView();
        search.setSearchableInfo( searchManager.getSearchableInfo(getActivity().getComponentName()));
        search.setOnQueryTextListener(this);
        search.setOnQueryTextFocusChangeListener(this);

     if (!recy)   m1_m();

    //    WorkAdapter.save_for_move.clear();

     //   glav.selectall2=false;

     //   glav.move=false;

     //   initList(getActivity());



    }

    int reglay=0;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.lock) {
            showFragmentLocking();
        }

        if (id == R.id.im_home) {

            if (!tch) {

                getActivity().setTitle(getResources().getString(R.string.app_name));

                IShowFragmentLS isfls = (IShowFragmentLS) getActivity();

                isfls.showFragmentLS();
            }

        }

        if (id==R.id.lay)
        {
            if (reglay==0)
            {
                reglay=1;
                LinearLayoutManager lm=new LinearLayoutManager(getActivity());
                list.setLayoutManager(lm);
                item.setIcon(getResources().getDrawable(R.drawable.grid));
                PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putInt("reglay", 1).commit();
            }

            else
            {
                reglay=0;
                StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(t,StaggeredGridLayoutManager.VERTICAL);
                list.setLayoutManager(mLayoutManager);
                item.setIcon(getResources().getDrawable(R.drawable.line));
                PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putInt("reglay", 0).commit();
            }
        }


        if (id == R.id.wlmove) {

            delete=false;
            move();


            return false;
        }

        if (id == R.id.wldel) {
          if (recy)  del();
            delete=true;
          if (!recy) move();


            return false;
        }

        if (id == R.id.all2) {

            if (!s)
            {
                s=true;
                all();
            }
            else
            {
                s=false;
                noall();
            }

        }

        if (id==R.id.colfon)
        {

            adapter.dialColor1();

        }

        if (id==R.id.coltext)
        {

            adapter.dialColor2();

        }

        return true;
    }

    boolean s;
    boolean move;

    void back()
    {
        move=false;

        save_for_move.clear();

        glav.selectall2=false;



        iclick();
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }

    void noall()
    {


        if (glav.selectall2) {glav.selectall2=false;}

        int ik=0;
        save_for_move.clear();
        for (CurrSave c:currsavearray)
        {
            adapter.checs[ik]=false;

            ik++;
        }

        notifList(false);
    }


    void all()
    {


       if (!glav.selectall2) {glav.selectall2=true;}
       int ik=0;
       for (CurrSave c:currsavearray)
       {
           adapter.checs[ik]=true;
           save_for_move.add(c);
           ik++;
       }

       // initList(getActivity());
       notifList(false);

    }





boolean selectedsave=false;
    ImageView image;



    public void itemClick( int position) {

        if (!glav.isPotok) {

            selectedsave = true;

            this.position = position;

            editing = true;

            info = currsavearray.get(position).getDatetime();

            glav.currsave = currsavearray.get(position);

            this.currsave = currsavearray.get(position);

           // glav.currsave.setEditing(true);

            edite_sheet();
        }
    }

    public void itemClickTable( int position,boolean list) {

        if (!glav.isPotok) {

            selectedsave = true;

            this.position = position;

            editing = true;

            info = currsavearray.get(position).getDatetime();

            glav.currsave = currsavearray.get(position);

            this.currsave = currsavearray.get(position);

            glav.currsave.setEditing(true);



            edite_table(list);
        }
    }




    @Override
    public void delRow() {


            if (glav.selectall2)
            {
                save_for_move.clear();
                for (int i=0;i<currsavearray.size();i++)
                {
                    save_for_move.add(currsavearray.get(i));
                }
            }


            for (CurrSave ufd : save_for_move)

            {

                db.deleteDataBase(uuidcurrsheet, ufd.getUuid_save());

                File root = Environment.getExternalStorageDirectory();

                String root_str = root.getAbsolutePath();

                String my_dir = root_str + File.separator + "nbook" + File.separator + "audio";

                File outFile1 = new File(my_dir, ufd.getFilename() + ".amr");

                if (outFile1.exists()) {
                    outFile1.delete();
                }
                my_dir = root_str + File.separator + "nbook" + File.separator + "photo";

                File outFile2 = new File(my_dir, ufd.getFilenamephoto1() + ".jpg");

                if (outFile2.exists()) {
                    outFile2.delete();
                }

                File outFile3 = new File(my_dir, ufd.getFilenamephoto2() + ".jpg");

                if (outFile3.exists()) {
                    outFile3.delete();
                }

                File outFile4 = new File(my_dir, ufd.getFilenamephoto3() + ".jpg");

                if (outFile4.exists()) {
                    outFile4.delete();
                }
                my_dir = root_str + File.separator + "nbook" + File.separator + "image";

                File outFile5 = new File(my_dir, ufd.getFilenameimage() + ".png");

                if (outFile5.exists()) {
                    outFile5.delete();
                }

                my_dir = root_str + File.separator + "nbook" + File.separator + "table";

                File outFile6 = new File(my_dir, ufd.getUuid_table_table() + ".html");

                if (outFile6.exists()) {
                    outFile6.delete();
                }
            }

            Toast.makeText(getActivity(), getResources().getString(R.string.wl2), Toast.LENGTH_SHORT).show();

            selectedsave = false;

           glav.selectall2=false;

            move = false;

            save_for_move.clear();

            FAbstatus=false;

      fab2.setEnabled(true);

         if (!recy)   m1_m();

           initList(getActivity());


           notifList(true);

    }



   void  showFragmentLocking()
    {

        IShowLocking isl=(IShowLocking)getActivity();

        isl.showLocking(uuidcurrsheet);
    }
    String info;
    @Override
    public void showInfo() {

        if (_value!=null)

        {

            String mess = info;

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.create_worklist)
                    .setCancelable(false)
                    .setMessage(mess)
                    .setPositiveButton(getResources().getString(R.string.ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else
        {
            Toast.makeText(getActivity(), getResources().getString(R.string.wl3), Toast.LENGTH_SHORT).show();
        }



    }

    private void edite_sheet() {

        IShowFragmentED ied = (IShowFragmentED) getActivity();


        if (currsave != null) {

            glav.currsave.setEditing(true);

            ied.showFragmentED(glav.currsave);


        }
        else
        {

        }
    }

    private void edite_table(boolean list) {
        if (list)
        {
            IShowFragmentList ied = (IShowFragmentList) getActivity();


            if (currsave != null) {

                glav.currsave.setEditing(true);

                ied.showFragmentList(glav.currsave, list);

            } else {

            }
        }
        else {


            IShowFragmentET ied = (IShowFragmentET) getActivity();


            if (currsave != null) {

                glav.currsave.setEditing(true);

                ied.showFragmentET(glav.currsave, list);

            } else {

            }
        }
    }








    public void iclick()
    {
      if (!delete && !recy)
      {
          fab2.setEnabled(true);

        move=false;

       initList(getActivity());

       notifList(true);

        m1_m();}
    }

    MenuInflater minfl;


    void m_m1()
    {


        menu1.getItem(4).setVisible(true);
        menu1.getItem(5).setVisible(true);
        menu1.getItem(6).setVisible(true);
        menu1.getItem(7).setVisible(true);
        menu1.getItem(8).setVisible(true);

    }



    void m1_m()
    {

        menu1.getItem(4).setVisible(false);
        menu1.getItem(5).setVisible(false);
        menu1.getItem(6).setVisible(false);
        menu1.getItem(7).setVisible(false);
        menu1.getItem(8).setVisible(false);

    }


    void del()
    {
        if (save_for_move.size()!=0) {
            String mess = getResources().getString(R.string.wl4);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("")
                    .setCancelable(false)
                    .setMessage(mess)
                    .setPositiveButton(getResources().getString(R.string.yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    delRow();
                                }
                            })
                    .setNegativeButton(getResources().getString(R.string.no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else
        {
            Toast.makeText(glav, getResources().getString(R.string.wl5), Toast.LENGTH_SHORT).show();
        }


    }

    ArrayList<String> as=new ArrayList<>();
    ArrayList<String> us=new ArrayList<>();

    int wh;
    boolean delete;
    int kor;

    void move()
    {
       if (save_for_move.size()!=0) {
           as.clear();
           us.clear();

           for (AllSheets sht : db.queryMainTableAll2(db.nameMainTable, db.tab_param1, db.tab_param2, db.tab_param3, db.tab_param5, db.tab_param6, glav)) {
               Log.d("tty", "namets: " + sht.getNametablesheet()+" "+sht.getUuid_sheet());

               if (!uuidcurrsheet.equals(sht.getUuid_sheet()) && !sht.getUuid_sheet().equals("dlg0")) {
                   as.add(sht.getNametablesheet());
                   us.add(sht.getUuid_sheet());
               }


           }
           String[] shs = new String[as.size()];
           int inn = 0;
           for (String s : as) {
               Log.v("ttytt", s);
               if (s.equals(getResources().getString(R.string.ga7))) kor=inn;
               shs[inn] = s;
               inn++;
           }

           if (shs.length == 1 && !delete) {
               AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
               builder.setMessage(getResources().getString(R.string.wl6))
                       .setPositiveButton(getResources().getString(R.string.wl7), new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               glav.lsheet = false;
                               glav.createSheet(glav.lsheet);

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
           } else if (shs.length!=1 && !delete)

           {

               AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
               builder.setTitle(getResources().getString(R.string.wl8))
                       .setItems(shs, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               wh = which;
                               move2();
                           }
                       })
                       .setPositiveButton(getResources().getString(R.string.wl7), new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               glav.createSheet(false);

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

           if (delete)
           {
               Toast.makeText(glav, getResources().getString(R.string.wl9), Toast.LENGTH_SHORT).show();
               wh = kor;
               move2();
           }
       }else
       {
           Toast.makeText(glav, getResources().getString(R.string.wl10), Toast.LENGTH_SHORT).show();
       }
    }

    void move2()
    {

        for (CurrSave csformove:save_for_move) {

            ContentValues cv = new ContentValues();

            cv.put("uuid_save", csformove.getUuid_save());

            cv.put("name_table_table",csformove.getName_table_table());

            cv.put("uuid_table_table", csformove.getUuid_table_table());

            cv.put("filenamephoto1", csformove.getFilenamephoto1());

            cv.put("filenamephoto2", csformove.getFilenamephoto2());

            cv.put("filenamephoto3", csformove.getFilenamephoto3());

            cv.put("filenameimage", csformove.getFilenameimage());

            cv.put("filename", csformove.getFilename());

            cv.put("value", csformove.getValue());

            cv.put("text", csformove.getText());

            cv.put("datetime", csformove.getDatetime());

            db.insertDataBase(us.get(wh), cv);

            db.delSave(uuidcurrsheet, csformove.getUuid_save());
        }

        move=false;

        save_for_move.clear();

        m1_m();

        initList(getActivity());

        notifList(true);

        fab2.show();

        fab2.setEnabled(true);

    }

    public void addBolvanka() {

        glav.currsave=new CurrSave(glav.db,glav.allSheets.getUuid_sheet());

        currsave=glav.currsave;

      String  _value = "";
      String  _text="";
        String _filename="";
        String _photofile1="";
        String _photofile2="";
        String _photofile3="";
        String _filenameimage="";

        Calendar cal = Calendar.getInstance();

            formatDateTime();
            glav.currsave.setUuid_save(new CreateUID(getActivity()).creatingUID() + "_save");
            glav.currsave.setDatetime(_datetime);
            glav.currsave.setValue(_value);
            glav.currsave.setText(_text);
            glav.currsave.setFilename(_filename);
            glav.currsave.setFilenamephoto1(_photofile1);
            glav.currsave.setFilenamephoto2(_photofile2);
            glav.currsave.setFilenamephoto3(_photofile3);
            glav.currsave.setFilenameimage(_filenameimage);
        Random rnd=new Random();
        int r=rnd.nextInt(25);
        int g=rnd.nextInt(25);
        int b=rnd.nextInt(25);
        int col= Color.rgb(230+r,230+g,230+b);
        Log.v("qqaass",col+"");
        PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putInt("fon_col_" + glav.currsave.getUuid_save(), col).commit();
            Log.v("qqaass",col+"");
            glav.currsave.addDataBase();

            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.wl11), Toast.LENGTH_SHORT).show();



    }

    public void addBolvankaPhoto() {

        glav.currsave=new CurrSave(glav.db,glav.allSheets.getUuid_sheet());

        currsave=glav.currsave;

        String  _value = "";
        String  _text="";
        String _filename="";
        String _photofile1="pusto";
        String _photofile2="";
        String _photofile3="";
        String _filenameimage="";

        Calendar cal = Calendar.getInstance();

        formatDateTime();
        glav.currsave.setUuid_save(new CreateUID(getActivity()).creatingUID() + "_save");
        glav.currsave.setDatetime(_datetime);
        glav.currsave.setValue(_value);
        glav.currsave.setText(_text);
        glav.currsave.setFilename(_filename);
        glav.currsave.setFilenamephoto1(_photofile1);
        glav.currsave.setFilenamephoto2(_photofile2);
        glav.currsave.setFilenamephoto3(_photofile3);
        glav.currsave.setFilenameimage(_filenameimage);
        Random rnd=new Random();
        int r=rnd.nextInt(25);
        int g=rnd.nextInt(25);
        int b=rnd.nextInt(25);
        int col= Color.rgb(230+r,230+g,230+b);
        Log.v("qqaass",col+"");
        PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putInt("fon_col_" + glav.currsave.getUuid_save(), col).commit();






    }


    String _datetime;

    void formatDateTime() {
        Calendar cal = Calendar.getInstance();

        String mont = "0";

        int tmont = cal.get(Calendar.MONTH) + 1;

        mont = tmont + "";

        if (tmont < 10) mont = "0" + tmont;

        String min = "0";

        int tmin = cal.get(Calendar.MINUTE);

        min = tmin + "";

        if (tmin < 10) min = "0" + tmin;

        _datetime = cal.get(Calendar.DAY_OF_MONTH) + "." + mont + "." + cal.get(Calendar.YEAR) + "  " + cal.get(Calendar.HOUR_OF_DAY) + ":" + min;

    }

    DataBaseInterface db;

    @Override
    public void onResume() {

        db=new DataBaseInterface(getActivity());
        super.onResume();
    }

    boolean tch=false;

    @Override
    public boolean onQueryTextSubmit(String query) {

        db=new DataBaseInterface(getActivity());
        db.getWritableDatabase();
         ArrayList<String> s= db.querySearch(allsheets.getUuid_sheet(),query);

        tch=false;

        if (s==null)
        {

            if (glav.isPotok) return false;
            initList(getActivity());
            notifList(false);
            return false;
        }
         ArrayList<CurrSave> arrs=new ArrayList<>();

         for (String str:s)
        {

            for (CurrSave c:currsavearray) {
                if (c.getUuid_save().equals(str))
                    arrs.add(c);
            }
        }
        Log.v("qazxsw",arrs.size()+"");

        currsavearray=arrs;

        if (glav.isPotok) return false;

         notifList2(arrs,false);
        return false;
    }



    @Override
    public boolean onQueryTextChange(String newText) {

        db.getWritableDatabase();
        ArrayList<String> s= db.querySearch(allsheets.getUuid_sheet(),newText);

         tch=true;
        if (s==null)
        {

            if (glav.isPotok) return false;



            initList(getActivity());
            notifList(false);

            return false;
        }
        ArrayList<CurrSave> arrs=new ArrayList<>();

        for (String str:s)
        {

            for (CurrSave c:currsavearray) {
                if (c.getUuid_save().equals(str))
                    arrs.add(c);
            }
        }
        Log.v("qazxsw",arrs.size()+"");

        currsavearray=arrs;

        if (glav.isPotok) return false;

        notifList2(arrs,false);
        return false;
    }


    @Override
    public boolean onClose() {
        Log.v("qqqqq","Close");
        tch=false;
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Log.v("qqqqq","hasFocus"+ hasFocus);
        tch=hasFocus;
    }
}
