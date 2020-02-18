package snyakovlev.unote;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v4.widget.DrawerLayout;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.support.v7.widget.AppCompatEditText;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import snyakovlev.unote.AllSheets;
import snyakovlev.unote.CurrSave;

import java.util.ArrayList;
import java.util.UUID;


/**
 * Created by user on 27.05.2015.
 */
public class CreateTable extends Fragment  {

   String  _tabletempltable;

    AppCompatEditText et_name_table;

    FloatingActionButton table_fab;

    Glavnoe_Activity glav;

    DataBaseInterface db;



    ArrayList<EditText> arret=new ArrayList<>();

    CurrSave currsave;

    LinearLayout table_ll;

  static   LockOrientation lock;

  AllSheets al;

   static int or;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        setHasOptionsMenu(true);

        glav=(Glavnoe_Activity)getActivity();

        glav.drawerlay.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);


        glav.setTitle(getResources().getString(R.string.ct1));

        al=glav.allSheets;


    }

    static CreateTable newInstance(CurrSave currsave,Glavnoe_Activity glav,boolean list) {

        CreateTable etable = new CreateTable();

        //lock=LockOrientation.getInstance(glav);
        etable.list=list;

        etable.currsave=currsave;

        return etable;
    }

    @Override
    public void onAttach(Context context) {


        super.onAttach(context);

      //  lock.lock();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    if (al!=null)  {outState.putString("getTabletempltable",al.getTabletempltable());}
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)

    {
        lock=LockOrientation.getInstance(glav);

        lock.lock();

        if (savedInstanceState!=null)
        {
            _tabletempltable=savedInstanceState.getString("getTabletempltable");
        }

        else {

            _tabletempltable = al.getTabletempltable();
        }

        db=new DataBaseInterface(getActivity());

        db.getWritableDatabase();

        View root = inflater.inflate(R.layout.table, container, false);

        table_ll=(LinearLayout)root.findViewById(R.id.table_ll);

        et_name_table =  root.findViewById(R.id.et_name_table);

        et_name_table.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                updateStyle(et_name_table, Typeface.NORMAL);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()==0) updateStyle(et_name_table,Typeface.ITALIC);
            }
        });



        addStartEditText("");

        return root;

    }

    int iii=0;

    void addStartEditText(String text)
    {

        Log.v("wsxcvb","addSTET");
            iii++;

            final  AppCompatEditText et = new AppCompatEditText(getActivity());

            et.setHint(getResources().getString(R.string.ct2)+iii);

            if (et.getText().length()==0) updateStyle(et,Typeface.ITALIC);


            et.setText(text);

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                updateStyle(et, Typeface.NORMAL);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()==0) updateStyle(et,Typeface.ITALIC);
            }
        });


            Log.v("hjkl", iii + "");

            if (text != null) {

                arret.add(et);

                table_ll.addView(et);
            }

    }

    void addEditText(String text)
    {
        Log.v("wsxcvb","addET");

        iii++;

       final AppCompatEditText et=new AppCompatEditText(getActivity());

        et.setId(Calculator.createID());

        et.setHint(getResources().getString(R.string.ct2)+iii);

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                updateStyle(et, Typeface.NORMAL);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()==0) updateStyle(et,Typeface.ITALIC);
            }
        });

        et.setText(text);

        if (list) et.setVisibility(View.GONE);

        Log.v("hjkl",iii+"");

        if (text!=null) {

            arret.add(et);


            table_ll.addView(et);
        }
    }


    void updateStyle(AppCompatEditText cet,int style )
    {
        cet.setTypeface(null,style);
    }

    AlertDialog alert;

    void showDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(glav);
        builder.setTitle(glav.getResources().getString(R.string.ct8))
                .setMessage("")
                .setCancelable(false)
                .setNegativeButton(glav.getResources().getString(R.string.cansel),new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .setPositiveButton(glav.getResources().getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                createTTable();
                                return;
                            }
                        });
        alert = builder.create();
        alert.show();

    }


    void closeCreateTable()
    {
        showDialog();

     //   glav.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);

        InputMethodManager imm=(InputMethodManager)glav.getSystemService(Context.INPUT_METHOD_SERVICE);

      if (imm!=null){  imm.hideSoftInputFromWindow(this.et_name_table.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);}

        IShowFragmentET isfwl=(IShowFragmentET)getActivity();

        isfwl.showFragmentET(glav.currsave,list);
    }


    Table t;



  //  public void editeDataInDataBase() {





    //   if (et_name_table.getText().toString().equals("")){Toast.makeText(getActivity(), getResources().getString(R.string.ct3), Toast.LENGTH_LONG).show();
     //      return;}

      //     if (!list) {
       //        if (varret.size() < 2) {
        //           Toast.makeText(getActivity(), getResources().getString(R.string.ct4), Toast.LENGTH_LONG).show();
         ///          return;
         //      }
         //  }

       //    Log.v("wsxcvb","edit");
      //  t=new Table(varret.size());
      //  t.setUuidrow("0");



     //   for (int i=0;i<varret.size();i++) {

     //           t.col[i].setCol(varret.get(i).getText().toString());
     //   }


      //     glav.currsave.setName_table_table(et_name_table.getText().toString());
//
       //    glav.currsave.setUuid_table_table(new CreateUID(getActivity()).creatingUID());

       //    glav.currsave.updateDataBase();

      //     db.createTableTable(glav.currsave.getUuid_table_table(),varret.size());
//    db.inTableTable(glav.currsave.getUuid_table_table(),t);

      //  Toast.makeText(getActivity(),getResources().getString(R.string.ct5), Toast.LENGTH_SHORT).show();

      //  closeCreateTable();
    //   }
boolean list;

    public void addDataInDataBase() {


        if (et_name_table.getText().toString().equals("")){Toast.makeText(getActivity(), getResources().getString(R.string.ct3), Toast.LENGTH_LONG).show();
            return;}

            if (!list) {
                if (varret.size() < 2) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.ct4), Toast.LENGTH_LONG).show();
                    return;
                }
            }

        Log.v("wsxcvb","add");
        t=new Table(varret.size());

        t.setUuidrow("0");

        for (int i=0;i<varret.size();i++) {

                t.col[i].setCol(varret.get(i).getText().toString());

        }

        glav.currsave.setName_table_table(et_name_table.getText().toString());

        glav.currsave.setUuid_table_table(new CreateUID(getActivity()).creatingUID());

        glav.currsave.addDataBase();

        db.createTableTable(glav.currsave.getUuid_table_table(),varret.size());

        db.inTableTable(glav.currsave.getUuid_table_table(),t);

        Toast.makeText(getActivity(),getResources().getString(R.string.ct5), Toast.LENGTH_SHORT).show();

        closeCreateTable();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main25, menu);

      if (list)  menu.getItem(0).setVisible(false);

    }

    int w=0;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        final String[] templTables;


        if (id==R.id.temp)
        {
            if (glav.allSheets.getTabletempltable().equals(""))
            {
                glav.allSheets.setTabletempltable(UUID.randomUUID().toString());
                glav.allSheets.updateDataBase();
            }
            ArrayList<String> items = glav.db.queryDataBaseAll(glav.allSheets.getTabletempltable(), DataBaseInterface.param15);
            ArrayList<String> items2 = glav.db.queryDataBaseAll(glav.allSheets.getTabletempltable(), DataBaseInterface.param14);

            int size=items.size();

            templNameTable =new String[size];
            templUUIDTable= new String[size];

            int i=0;

            for (i=0;i<items.size();i++)
            {
                templNameTable[i]=items.get(i).toString();
                templUUIDTable[i]=items2.get(i).toString();

            }



            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getResources().getString(R.string.ct6))
                    .setSingleChoiceItems(templNameTable, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           w=which;
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.otmena),new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return ;
                        }
                    })
                    .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                           if (templUUIDTable.length>0)   { createTable("", templUUIDTable[w]);}

                        }
                    })
                    .setNeutralButton(getResources().getString(R.string.ct7), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            if (templUUIDTable.length>0)  {delshabl(templUUIDTable[w]);}


                        }
                    })
                    .setCancelable(false);

            AlertDialog alert = builder.create();
            alert.show();

        }

        if (id==R.id.add)
        {
           addEditText("");
        }


        if (id==R.id.okey)
        {
            valid();

            if (currsave.editing) {
             //   editeDataInDataBase();
            }
            else
            {
                addDataInDataBase();
            }



        }


        return true;
    }


    ArrayList<EditText> varret=new ArrayList<>();

    void delshabl(String uuidtable)
    {


        db.deleteShablonTable(uuidtable,glav.allSheets.getTabletempltable());

    }

void createTable(String nameTable,String uuidTable)
{
    int k=table_ll.getChildCount();
    for (int i=1;i<k;i++)
    {
        table_ll.removeView(table_ll.getChildAt(i));
    }
 Table nns=db.queryTable(uuidTable);
 addEditText(nns.col[0].getCol());
 for (int i=1;i<nns.col.length;i++) {
     addEditText(nns.col[i].getCol());
 }


}


    void valid()
    {
        varret.clear();

        int i=0;

        for (EditText et:arret) {
            if (!et.getText().toString().equals(""))

            {

                if (et.getText() != null)
                {
                varret.add(et);
                    Log.v("weryt",i+" --- "+et.getText().toString());
            }
        }


               i++;

               Log.v("wert",i+" --- "+et.getText().toString());
        }

    }

    void cancel()
    {
        InputMethodManager imm=(InputMethodManager)glav.getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

        IShowFragmentWL isfwl=(IShowFragmentWL)getActivity();

        //   isfwl.showFragmentFF2();

        isfwl.showFragmentWL();
    }

    String[] templNameTable;

    String[] templUUIDTable;
    int j=0;

    ArrayList<String> uuidsdel=new ArrayList<>();

    void update4(String uuid)
    {
       uuidsdel.add(uuid);
    }






    void createTTable()
    {
        _tabletempltable=glav.allSheets.getTabletempltable();

        ContentValues cv=new ContentValues();

        cv.put(db.param14,glav.currsave.getUuid_table_table());

        cv.put(db.param15,glav.currsave.getName_table_table());

        Log.v("asd","nametable="+_tabletempltable);

        db.insertDataBase(_tabletempltable,cv);

    }



    }

    class LockOrientation
    {
        Activity act;

        static LockOrientation getInstance(Activity act)
        {
            return  new LockOrientation(act);
        }
        private LockOrientation(Activity act) {
            this.act=act;
        }

        void unlock()
        {
            act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
        }

        void lock()
        {
            Log.v("wertyu",act.getResources().getConfiguration().orientation+"");

            if (act.getResources().getConfiguration().orientation==ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {

                    act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                Log.v("wertyu", "landscape");
            }

            if (act.getResources().getConfiguration().orientation==ActivityInfo.SCREEN_ORIENTATION_USER) {

                    act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                Log.v("wertyu", "user");
            }

            if (act.getResources().getConfiguration().orientation==ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {

                    act.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                Log.v("wertyu", "portrait");
            }
        }





}