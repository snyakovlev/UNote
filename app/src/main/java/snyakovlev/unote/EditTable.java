package snyakovlev.unote;


import android.*;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
;

import static snyakovlev.unote.R.attr.colorPrimaryDark;

/**
 * Created by user on 27.05.2015.
 */
public class EditTable extends Fragment  {

    boolean pr=false;

    boolean editing = true;

    String _valtmp = null;

    String _texttmp = null;

    String _filenametmp = null;

    String _photofiletmp1 = null;

    String _photofiletmp2 = null;

    String _photofiletmp3 = null;

    String _filenameimagetmp = null;

    String _value = null;

    String _text = null;

    LinearLayout linl;


    String _datetime = null;

    String _photofile1 = "";

    String _photofile2 = "";

    String _photofile3 = "";

    String _filenameimage = "";

    String nametable;

    String _filename = "";

    AudioRecorder aur;

    String _temptable = "";

    String _table = "";

    String _tabletempltable;

    Glavnoe_Activity glav;

    CurrSave currsave;

  //  FloatingActionButton fab233;

    AllSheets allsheets;

    ScrollView scroll;

    Table tt;

    boolean created=false;


    TableRow tr0;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        setHasOptionsMenu(true);

        pr=true;

        _value = getArguments().getString("value");

        _text = getArguments().getString("text");

        _table = getArguments().getString("table");

        _filenameimage = getArguments().getString("filenameimage");

        editing = getArguments().getBoolean("edit");

        _valtmp = _value;

        _texttmp = _text;

        _filename = getArguments().getString("filename");

        _photofile1 = getArguments().getString("photofile1");

        _photofile2 = getArguments().getString("photofile2");

        _photofile3 = getArguments().getString("photofile3");

        _filenametmp = _filename;

        _photofiletmp1 = _photofile1;

        _photofiletmp2 = _photofile2;

        _photofiletmp3 = _photofile3;

        _filenameimagetmp = _filenameimage;

        editing = getArguments().getBoolean("edit");

        glav = (Glavnoe_Activity) getActivity();



    }



    static EditTable newInstance(CurrSave currsave,boolean list) {

        EditTable edata = new EditTable();

        edata.currsave = currsave;

        Bundle args = new Bundle();

        args.putString("value", currsave.getValue());

        args.putString("text", currsave.getText());

        args.putBoolean("edit", currsave.isEditing());

        args.putString("table", currsave.getUuid_table_table());

        args.putString("filename", currsave.getFilename());

        args.putString("photofile1", currsave.getFilenamephoto1());

        args.putString("photofile2", currsave.getFilenamephoto2());

        args.putString("photofile3", currsave.getFilenamephoto3());

        args.putString("filenameimage", currsave.getFilenameimage());

        args.putBoolean("list",list);




        edata.setArguments(args);

        return edata;
    }

    CoordinatorLayout maincontent;

    Bundle sis=null;

    @Override
    public void onResume() {

        super.onResume();
        Log.v("wwssdd","resume");
            for (i = 0; i > tl.getChildCount(); i++) {
                tl.clearChildFocus(tl.getChildAt(i));
            }

            if (created) {
                update(-1);

                if (showc) {
                    initLayout(false);
                } else {
                    initLayout(true);
                }
            }


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)

    {
        Log.v("wwssdd","oncreateview");

        sis=savedInstanceState;
        list=getArguments().getBoolean("list",false);
        start=false;
        glav = (Glavnoe_Activity) getActivity();
        if (savedInstanceState==null) {
            nametable = glav.allSheets.getUuid_sheet();

            _temptable = glav.allSheets.getTempltable();

            _tabletempltable = glav.allSheets.getTabletempltable();

            glav.setTitle(glav.currsave.getName_table_table());



            c=new Calculator();

            cer=new Colrowedit();
        }
        else
        {
            _table=savedInstanceState.getString("table");

            nametable = savedInstanceState.getString("getUuid_sheet");

            _temptable = savedInstanceState.getString("getTempltable");

            _tabletempltable = savedInstanceState.getString("getTabletempltable");

            glav.setTitle(savedInstanceState.getString("gst"));




        }

        boolean edit=true;

        editing=true;



        allsheets = ((Glavnoe_Activity) getActivity()).allSheets;


        ListSheetAdapter2 adapter2 = new ListSheetAdapter2(getActivity(), initSheets());

        glav.lw.setAdapter(adapter2);

        arret.clear();

        arrtev.clear();


        View root = inflater.inflate(R.layout.edit_table_layout, container, false);

        linl=(LinearLayout)root.findViewById(R.id.linvert);

        tr0=(TableRow) root.findViewById(R.id.tr0);

        tr0.setScrollContainer(false);

        tr0.setVerticalScrollBarEnabled(false);

        glav = (Glavnoe_Activity) getActivity();

        this.currsave=glav.currsave;

        tl = (TableLayout) root.findViewById(R.id.tl);

        tl.setMinimumWidth((int) (Math.round(getResources().getDisplayMetrics().widthPixels / 1.3)));


        if (savedInstanceState != null && savedInstanceState.getBoolean("calc")) {
            pr=false;
            edit=false;
            editing=false;
            tl.clearChildFocus(getFocusView());
            showc = true;
        }

        glav.drawerlay.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        list=getArguments().getBoolean("list");

        if (_table.equals("")) {

                created=false;

                IShowFragmentCT is = (IShowFragmentCT) getActivity();

                is.showFragmentCT(glav.currsave, glav, list);
        }

        if (!_table.equals("")) {

            if (savedInstanceState!=null)

            { cuid= savedInstanceState.getString("cuid");}
            else
            {
                 showeditcolrow();
                cuid=currsave.uuid_table_table;
            }

            initLayout(edit);

            created=true;
        }

        return root;

    }







boolean fvis;
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("calc",showc);
        tl.clearChildFocus(getFocusView());
        update(-1);
        if (glav!=null && glav.allSheets!=null) {
            outState.putString("table",_table);
            outState.putString("getUuid_sheet", glav.allSheets.getUuid_sheet());
            outState.putString("getTempltable", glav.allSheets.getTempltable());
            outState.putString("getTabletempltable", glav.allSheets.getTabletempltable());
        }
        if (glav!=null && glav.currsave!=null) {
            outState.putString("gst", glav.currsave.getName_table_table());
        }
        if (currsave!=null && currsave.uuid_table_table!=null) {
            outState.putString("cuid", currsave.uuid_table_table);
        }
        super.onSaveInstanceState(outState);
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

        }

        allsheetss.removeAll(allremove);

        return allsheetss;
    }





    ArrayList<CustomEditText> et = new ArrayList<>();

    ArrayList<ArrayList<CustomEditText>> arret = new ArrayList<ArrayList<CustomEditText>>();

    ImageButton btdel;


    void addRow() {


        Log.v("ertret","addRow");

        i++;


        TableRow tr = new TableRow(getActivity());

        String idrow= Calendar.getInstance().getTimeInMillis()+"";

        tr.setBackgroundColor(Color.BLACK);

        TableLayout.LayoutParams lp = new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );

       lp.setMargins(1, 1, 1, 1);

        tr.setLayoutParams(lp);

        et = new ArrayList<>();

        for (int i = 0; i < tab.get(0).col.length; i++) {

         final   CustomEditText cet=crEdite(true,idrow);

            TableRow.LayoutParams lpbt = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.1f);

            final int z=this.i;

            et.add(cet);

            tr.addView(et.get(et.size() - 1));


        }

        tl.addView(tr);

        arret.add(et);

        Table t = new Table(tab.get(0).col.length);

        t.setUuidrow(idrow);

        t.setNamerow("");

        for (int i = 0; i < tab.get(0).col.length; i++) {

            t.col[i].setCol("");

        }

        db.inTableTable(cuid, t);

        update(-1);


    }
boolean start=true;


    CustomEditText crEdite(boolean edit,String idrow) {
        CustomEditText ced = new CustomEditText(getActivity(), this,edit,idrow);



        return ced;
    }

    ArrayList<TextView> arrtev = new ArrayList<>();

    DataBaseInterface db;

    TableLayout tl;

    ArrayList<Table> tab=new ArrayList<>();

    int i;

    String cuid;

    boolean list;

    TextView tev;

    int pos=0;


    int inn=0;

    String name_table_t="";

    void initLayout(final boolean edit) {

        Log.v("ertret","initLayout");

        arret.clear();

        arrtev.clear();

        et.clear();

        if (!edit2) {tab.clear();}

        tl.removeAllViewsInLayout();

        tr0.removeAllViews();

        tl.addView(tr0);

        db = new DataBaseInterface(getActivity());

        db.getWritableDatabase();

     if(!edit2)  tab = db.queryTableTableAll(cuid);

      edit2=false;



          TableLayout.LayoutParams lp = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);

          tr0.setLayoutParams(lp);

        TableRow.LayoutParams lp1 = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);

        lp1.setMargins(1, 0, 1, 0);


        if (tab.size() != 0) {


                for ( inn = 0; inn < tab.get(0).col.length; inn++) {

                    tev = new TextView(getActivity());

                    tev.setId(inn);

                    tev.setMaxLines(1);


                    tev.setText(tab.get(0).col[inn].getCol());


                    tev.setBackgroundColor(ContextCompat.getColor(getActivity(), ColorTheme.primaryDark));


                    tev.setLayoutParams(lp1);

                    tev.setTextColor(Color.WHITE);

                    tev.setGravity(Gravity.CENTER);

                    tev.setPadding(4, 0, 4, 0);

                    tev.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {

                            for (TextView t:arrtev)
                            {
                               if (t.getId()==((TextView)v).getId())
                               {
                                   Log.v("wwwwww",t.getId()+"=="+((TextView)v).getId());
                                dialogEdit(t.getId());
                               }
                            }
                            return false;
                        }
                    });

                    arrtev.add(tev);

                    tr0.addView(tev);
                }



                 for (int i = 1; i < tab.size(); i++) {

                    TableRow tr = new TableRow(getActivity());

                    tr.setBackgroundColor(Color.BLACK);


                    TableLayout.LayoutParams lpp = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);

                    lpp.setMargins(1, 1, 1, 1);

                    tr.setLayoutParams(lpp);

                    et = new ArrayList<>();

                    for (int j = 0; j < tab.get(0).col.length; j++)
                    {

                        Log.v("qwsxc","initLayout col="+tab.get(i).col[j].getCol());

                        String uuidrow = tab.get(i).uuidrow;

                      final  CustomEditText cet = crEdite(edit,uuidrow);

                      TableRow.LayoutParams lpbt = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.1f);

                        lpbt.setMargins(0, 0, 0, 0);


                           cet.setText(tab.get(i).col[j].getCol());

                        et.add(cet);


                        Log.v("qwqwe",tab.get(i).col[j].getCol()+"  "+j+"  "+i);

                        if (!tab.get(0).col[j].getCol().equals("") ) {
                            tr.addView(et.get(j));
                        }
                    }

                    arret.add(et);


                    if (!vall) {
                        if (!tab.get(i).col[0].getCol().equals("")) {tl.addView(tr);}
                    } else {
                        tl.addView(tr);
                    }


                }

            }

    }

    boolean edit2=false;


void dialogEdit(final int pos)
{
    View v=getActivity().getLayoutInflater().inflate(R.layout.edit_col,null);
    final   EditText et1=(EditText)v.findViewById(R.id.et_namecol);
    et1.setText(tab.get(0).col[pos].getCol());
    final   EditText et2=(EditText)v.findViewById(R.id.et_poscol);
    et2.setText((pos+1)+"");
    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
    builder.setTitle(R.string.et4)
            //  .setMessage(R.string.message)
            .setView(v)
            .setCancelable(false)
            .setPositiveButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            update(-1);

                            tab = db.queryTableTableAll(cuid);

                            tab.get(0).col[pos].setCol(et1.getText().toString());

                            int pos2=(Integer.parseInt(et2.getText().toString())-1);

                           String temp="";

                           if (pos2>tab.get(0).col.length-1)
                           {
                               Toast.makeText(glav, (tab.size()-1)+" column", Toast.LENGTH_SHORT).show();
                               return;
                           }

                            if (!et2.getText().equals(pos+""))
                            {
                               for (int i=0;i<tab.size();i++)
                               {
                                  temp=tab.get(i).col[pos].getCol();
                                  tab.get(i).col[pos].setCol(tab.get(i).col[pos2].getCol());
                                  tab.get(i).col[pos2].setCol(temp);

                                  Log.v("zzzz",tab.get(i).col[pos].getCol()+"  "+tab.get(i).col[pos2].getCol());
                               }
                            }

                            for (int ii = 0; ii < arret.size(); ii++) {

                                Table t = new Table(tab.get(0).col.length);

                                int ij=0;

                                for (int i = 0; i < arret.get(0).size(); i++) {

                                            t.col[ij].setCol(tab.get(ii+1).col[i].getCol());

                                            t.setUuidrow(arret.get(ii).get(i).idrow);
                                            ij++;

                                            Log.v("sddf", ij + "__" + i + "___" + arret.get(ii).get(i).getText().toString());

                                }
                                String col= tab.get(0).col[pos].getCol();

                                db.urowpos(cuid,"0",et1.getText().toString(),pos2);

                                db.urowpos(cuid,"0",col,pos);

                                db.urow(cuid, t.getUuidrow(), t);


                            }

                            edit2=true;

                            initLayout(true);
                        }
                    })
            .setNegativeButton(R.string.otmena, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    return;
                }
            });

    android.support.v7.app.AlertDialog alert = builder.create();
    alert.show();
}





    boolean update(int jj) {



        for (int ii = 0; ii < arret.size(); ii++) {

            Table t = new Table(tab.get(0).col.length);
            int ij=0;

            for (int i = 0; i < arret.get(0).size(); i++) {

                    if (i!=jj) {

                        t.col[ij].setCol(arret.get(ii).get(i).getText().toString());
                        t.setUuidrow(arret.get(ii).get(i).idrow);
                        ij++;

                        Log.v("sddf", ij + "__" + i + "___" + arret.get(ii).get(i).getText().toString());
                    }

            }

            db.urow(cuid, t.getUuidrow(), t);

        }

        if (ContextCompat.checkSelfPermission(glav, Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)   createfilehtml0();

return true;
    }




    EditText getFocusView() {
        View v = getActivity().getCurrentFocus();

        if (v != null) {

            if (v.getClass() == CustomEditText.class) return (EditText) v;
        }

        return null;
    }


    void closeTableData() {

      if   (update(-1)) {

          IShowFragmentWL isfwl = (IShowFragmentWL) getActivity();


          isfwl.showFragmentWL();
      }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main24, menu);

    }

    boolean showcr;

    boolean ww=false;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id==R.id.rename)
        {
            rentab();
        }

        if (id==R.id.saveshablon)
        {
            saveshablon();
        }



        if (id == R.id.gl) {


            update(-1);

            hidecalc();

            hideeditcolrow();

            save();
        }
        if (id == R.id.tdate) {

            bool = true;

            setDate();

            return true;
        }

        if (id == R.id.ttime) {

            bool = true;

            setTime();

            return true;
        }

        if (id== R.id.addrow)
        {
           addr(item);
        }

        if (id == R.id.shcalc) {

            if (showc) {
                Box b=Box.getInstance();

                b.operands.clear();
                b.operandset.clear();
                b.operatorsiv.clear();
                b.operators.clear();
                b.operatorr=false;

                item.setTitle(getResources().getString(R.string.men1));


                hidecalc();// TODO

                update(-1);

                editing=true;

                initLayout(editing);



                showc = false;
            }

            else

            {
                update(-1);

                editing=false;

                initLayout(editing);

                item.setTitle(getResources().getString(R.string.men1)+"("+getResources().getString(R.string.skr)+")");

                showcalc();

                showc = true;




            }

            return true;
        }



        if (id== R.id.share_table)
        {
            update(-1);
            initLayout(editing);
            sharetable(true);
        }

        if (id== R.id.view_table)
        {
            update(-1);
            initLayout(editing);
            sharetable(false);
        }


        return true;
    }

    void addr(MenuItem item)
    {
        if (!_table.equals(""))

        {
            Box b=Box.getInstance();

            b.operands.clear();
            b.operandset.clear();
            b.operatorsiv.clear();
            b.operators.clear();
            b.operatorr=false;

          if (item!=null)  item.setTitle(getResources().getString(R.string.men1));


            hidecalc();// TODO

            update(-1);

            editing=true;

          //  initLayout(editing);

            //   fab233.setVisibility(View.VISIBLE);

            showc = false;
            addRow();
        }
    }

    boolean vall;

    void saveshablon()
    {
        _tabletempltable=glav.allSheets.getTabletempltable();

        ContentValues cv=new ContentValues();

        cv.put(db.param14,glav.currsave.getUuid_table_table());

        cv.put(db.param15,glav.currsave.getName_table_table());

        Log.v("asd","nametable="+_tabletempltable);

        db.insertDataBase(_tabletempltable,cv);
    }

    void rentab()
    {
        View v=getActivity().getLayoutInflater().inflate(R.layout.create_sheet,null);
        final   EditText et3=(EditText)v.findViewById(R.id.et_namesheet);
        et3.setGravity(Gravity.CENTER);
        et3.setMaxEms(8);
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.pp1)
                //  .setMessage(R.string.message)
                .setView(v)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                glav.currsave.setName_table_table(et3.getText().toString());

                                glav.currsave.updateDataBase();

                                glav.setTitle(et3.getText().toString());


                            }
                        })
                .setNegativeButton(R.string.otmena, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        return;
                    }
                });

        android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();


    }



    void sharetable(boolean b)
    {

      createfilehtml();
       sharefilehtml(b);
    }

    void sharefilehtml(boolean b)
    {
        if (b)

        {

            Uri f ;
            Intent intent;
            intent = new Intent(Intent.ACTION_SEND);
            if (Build.VERSION.SDK_INT>=24)
        {
            f= FileProvider.getUriForFile(glav,glav.getApplicationContext().getPackageName()+
                    ".provider",new File(glav.getDirPublic() + File.separator+ currsave.uuid_table_table+ ".html"));
        }

        else
        {
            f=Uri.fromFile(new File(glav.getDirPublic() + File.separator+ currsave.uuid_table_table+ ".html"));
        }


            Log.i("qwertrewq", f.getEncodedPath());

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_STREAM, f);
            intent.setType("text/html");
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(intent, getResources().getString(R.string.et2)));
        }
        else
        {

            MimeTypeMap mime = MimeTypeMap.getSingleton();
            String mimetype  = mime.getMimeTypeFromExtension("html");
            Intent htmlIntent = new Intent(Intent.ACTION_VIEW);
            File ff;
            Uri u;

            if (Build.VERSION.SDK_INT>=24)
            {
                htmlIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                ff=new File(glav.getDirPublic() + File.separator +currsave.uuid_table_table+ ".html");
                ff.getParentFile().mkdirs();
                try {
                    ff.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                    u= FileProvider.getUriForFile(glav,glav.getApplicationContext().getPackageName()+
                            ".provider",ff);
                Log.v("wsxc",u.getPath());

            }

            else
            {
                u=Uri.fromFile(new File(glav.getDirPublic() + File.separator +currsave.uuid_table_table+ ".html"));
                Log.v("wsxc",u.getPath());
            }

            htmlIntent.setDataAndType(u, mimetype);
                    startActivity(Intent.createChooser(htmlIntent,getResources().getString(R.string.et3)));

                }


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

    void createfilehtml0()
    {

        File root;

        if (isAvailable() || !isReadOnly()) {

            root = Environment.getExternalStorageDirectory();
        } else {
            root = glav.getFilesDir();
        }

        String root_str = root.getAbsolutePath();

        String my_dir = root_str + File.separator + "nbook" + File.separator + "table";
        cuid=currsave.uuid_table_table;
        tab = db.queryTableTableAll(cuid);
        int  ss=0;
        XmlSerializer s= Xml.newSerializer();
        FileWriter writer= null;
        try {
            File fil=new File(my_dir,currsave.uuid_table_table+".html");
            fil.getParentFile().mkdirs();
             fil.createNewFile();
            writer = new FileWriter(fil);
            s.setOutput(writer);
            s.startDocument("UTF-8", true);

            if (tab.get(0).col.length>1  && !list) {
                s.startTag("", "table");

                s.attribute("", "style", "font-size:1.0em; margin: auto;border-collapse: collapse;border: 0px;");

                s.startTag("", "div");
                s.startTag("", "caption");
                s.attribute("", "style", "margin-bottom:16px;");
                s.text(currsave.getName_table_table());
                s.endTag("", "caption");
                s.endTag("", "div");
                s.startTag("", "tr");
                s.attribute("", "style", "border: 0px");
                s.startTag("", "td");
                s.attribute("", "style", "border: 0px");
                s.startTag("", "div");
                s.text("     ");
                s.endTag("", "div");
                s.endTag("", "td");
                s.endTag("", "tr");
            }
            for (Table t : tab) {

                Log.v("erdr",tab.size()+"");
                if (list)
                {
                    if (ss>1) {
                        if (!t.col[0].getCol().equals("")) {
                            s.startTag("", "check");
                            s.attribute("","checked","0");
                            s.attribute("","text",t.col[0].getCol());
                            s.endTag("","check");

                        }}

                        ss++;

                }

                else {
                    s.startTag("","tr");
                    for (int i = 0; i < t.col.length; i++) {
                        if (!vall) {
                            if (!t.col[0].getCol().equals("")) {
                                s.startTag("", "td");
                                s.attribute("","style","font-size:1.0em; margin: auto;border-collapse: collapse; border: 1px solid black");
                                s.text(t.col[i].getCol());
                                s.endTag("", "td");
                            }
                        } else {
                            s.startTag("", "td");
                            s.attribute("","style","font-size:1.0em; margin: auto;border-collapse: collapse; border: 1px solid black");
                            s.text(t.col[i].getCol());
                            s.endTag("", "td");
                        }
                    }
                    s.endTag("","tr");
                }

            }

            if (tab.get(0).col.length>1 && !list) {
                s.endTag("", "table");
            }
                s.endDocument();


        }
        catch (Exception e)
        {
            throw new RuntimeException(e);

        }
    }

    void createfilehtml()
    {
        XmlSerializer s= Xml.newSerializer();
        FileWriter writer= null;
        try {
            File fil=new File(glav.getDirPublic()+File.separator+currsave.uuid_table_table+".html");
            if (!fil.exists()) fil.createNewFile();
            writer = new FileWriter(fil);
            s.setOutput(writer);
            s.startDocument("UTF-8", true);
            s.startTag("","table");
            s.attribute("","bordercolor","green");
            s.attribute("","border","1");
            s.attribute("","style","font-size:1.5em");
            s.startTag("","caption");
            s.text(currsave.getName_table_table());
            s.endTag("","caption");
            for (Table t : tab) {
                Log.v("erdr",tab.size()+"");
                s.startTag("","tr");
                for (int i = 0; i<t.col.length;i++)
                {
                    if (!vall) {
                        if (!t.col[0].getCol().equals("")) {
                            s.startTag("", "td");
                            s.text(t.col[i].getCol());
                            s.endTag("", "td");
                        }
                    }
                    else
                    {
                        s.startTag("", "td");
                        s.text(t.col[i].getCol());
                        s.endTag("", "td");
                    }
                }
                s.endTag("","tr");
            }
            s.endTag("","table");
            s.endDocument();

        }
        catch (Exception e)
        {
            throw new RuntimeException(e);

               }
           }





    void hidecalc() {

        if (showc) {

            showc = false;

            getActivity().getSupportFragmentManager().beginTransaction()

                    .remove(c)

                    .commit();
            showeditcolrow();
        }



    }

    void hideeditcolrow()
    {
        if (showcr)
        {
            showcr = false;

            getActivity().getSupportFragmentManager().beginTransaction()

                    .remove(cer)

                    .commit();
        }
    }

    Calculator c ;

    Colrowedit cer;

    void showeditcolrow()
    {
        showcr = true;

        getActivity().getSupportFragmentManager().beginTransaction()

                .add(R.id.footer, cer)

                .commit();
    }

    void showcalc() {

        showc = true;

        getActivity().getSupportFragmentManager().beginTransaction()

                .replace(R.id.footer, c)

                .commit();


    }

    boolean showc;


    GregorianCalendar cale = (GregorianCalendar) Calendar.getInstance();

    boolean bool = true;

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            cale.set(Calendar.YEAR, year);

            cale.set(Calendar.MONTH, monthOfYear);

            cale.set(Calendar.DAY_OF_MONTH, dayOfMonth);


            if (bool) tupdate();

            bool = false;


        }
    };

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {


        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            cale.set(Calendar.HOUR_OF_DAY, hourOfDay);

            cale.set(Calendar.MINUTE, minute);

            if (bool) tupdate2();

            bool = false;


        }
    };


    DatePickerDialog dpd;

    TimePickerDialog tpd;


    public void setDate() {
        dpd = new DatePickerDialog(getActivity(), d,
                cale.get(Calendar.YEAR),
                cale.get(Calendar.MONTH),
                cale.get(Calendar.DAY_OF_MONTH));

        dpd.show();

    }

    public void setTime() {
        tpd = new TimePickerDialog(getActivity(), t, cale.get(Calendar.HOUR_OF_DAY), cale.get(Calendar.MINUTE), true);

        tpd.show();

    }

    int hour_dp, minute_dp;

    void tupdate2() {


        if (getFocusView() != null) {


            hour_dp = cale.get(Calendar.HOUR_OF_DAY);

            minute_dp = cale.get(Calendar.MINUTE);

            String str = "";

            if (minute_dp < 10) str = "0" + minute_dp;

            else str = minute_dp + "";

            String temp = getFocusView().getText().toString();

            Log.v("amm", "t1:" + temp);

            String temp2 = hour_dp + "." + str + " ";

            Log.v("amm", "t2:" + temp2);

            if (temp2.equals(temp)) temp2 = "";

            String text = temp + " " + temp2;

            getFocusView().setText(text);


        }

    }


    void tupdate() {


        if (getFocusView() != null) {


            den_dp = cale.get(Calendar.DAY_OF_MONTH);

            mes_dp = cale.get(Calendar.MONTH);

            year_dp = cale.get(Calendar.YEAR);

            String str = "";

            int mes = mes_dp + 1;

            if ((mes_dp + 1) < 10) str = "0" + mes;

            else str = mes + "";

            String temp = getFocusView().getText().toString();

            Log.v("amm", "t1:" + temp);

            String temp2 = den_dp + "." + str + "." + year_dp + " ";

            Log.v("amm", "t2:" + temp2);

            if (temp2.equals(temp)) temp2 = "";

            String text = temp + " " + temp2;

            getFocusView().setText(text);


        }

    }



    int den_dp = 0, mes_dp = 0, year_dp = 0;


    void save() {
        closeTableData();
    }



    void calcul2table(String res) {
        if (res != null) {
            CustomEditText v = (CustomEditText)getFocusView();

            if (v != null) {
                v.setText(res);
                update(-1);
            }
        }
    }

    EditText ed1000;


    int numoper = 0;

    boolean sowc=false;

    void t2c() {


        Calculator calc = (Calculator) getActivity().getSupportFragmentManager().findFragmentById(R.id.footer);

            if (ed1000 != null) calc.table2oper(ed1000.getText().toString());



    }

    int id=0;

    void plusColumn()
    {

        View v=getActivity().getLayoutInflater().inflate(R.layout.create_sheet,null);
      final   EditText et=(EditText)v.findViewById(R.id.et_namesheet);
      et.setGravity(Gravity.CENTER);
      et.setMaxEms(8);
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.et4)
                //  .setMessage(R.string.message)
                .setView(v)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                db.addCol(cuid,"col"+tab.get(0).col.length,et.getText().toString());

                                tab = db.queryTableTableAll(cuid);

                                update(-1);

                                initLayout(true);
                            }
                        })
                .setNegativeButton(R.string.otmena, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        return;
                    }
                });

        android.support.v7.app.AlertDialog alert = builder.create();
        alert.show();

    }
    void minusColumn()
    {


        EditText v = getFocusView();
        int j=0;
        for (ArrayList<CustomEditText> ar:arret)
        {
            int ind=0;
           for (CustomEditText cs:ar)
           {
               if (cs.equals(v)) j=ind;
               ind++;
           }
        }

        if (tab.get(0).col.length<3)
        {
            Toast.makeText(glav, "Error (2 col.)", Toast.LENGTH_SHORT).show();
            return;
        }

        db.delCol(currsave.getUuid_table_table(),tab.get(0).col.length,j);

        tab = db.queryTableTableAll(cuid);

        update(j);

        initLayout(true);
    }
    void plusRow()
    {
      addRow();
    }

    void minusRowList(String j)
    {

      Log.v("qqqaaa",arret.size()+"");
        if (arret.size()>2) {
            this.i--;

            db.deleterow(cuid, j);

            tab = db.queryTableTableAll(cuid);

            update(-1);

            initLayout(true);
        }
    }

    void minusRow()
    {
        EditText v = getFocusView();
        String j="";
        int ind=0;
        for (ArrayList<CustomEditText> ar:arret)
        {

            for (CustomEditText cs:ar)
            {
                if (cs.equals(v)) j=cs.idrow;

            }
            ind++;
        }
      db.deleterow(cuid,j);

        tab = db.queryTableTableAll(cuid);

        update(-1);

        initLayout(true);
    }

}

 class CustomEditText extends android.support.v7.widget.AppCompatEditText implements View.OnFocusChangeListener, View.OnClickListener {

    EditTable et;
    String idrow;
    boolean focus;

    public CustomEditText(Activity context,EditTable et,boolean edit,String id) {
        super(context);
        if (edit) {

            this.idrow=id;
            this.et = et;
            TableRow.LayoutParams lp = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
            lp.setMargins(1, 0, 1, 0);
            this.setTextSize(Float.parseFloat(Fonts.fontsizeTable));
            this.setLayoutParams(lp);
            this.setBackgroundColor(Color.WHITE);
            this.setOnFocusChangeListener(this);
            this.setGravity(Gravity.CENTER);

        }
        else
        {
            this.idrow=id;
            this.setCursorVisible(false);
            this.setTextSize(Float.parseFloat(Fonts.fontsizeTable));
            this.setBackgroundColor(Color.TRANSPARENT);
            this.setKeyListener(null);
            this.et = et;
            TableRow.LayoutParams lp = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
            lp.setMargins(1, 0, 1, 0);
            this.setLayoutParams(lp);
            this.setBackgroundColor(Color.WHITE);
            this.setOnFocusChangeListener(this);
            this.setOnClickListener(this);
            this.setGravity(Gravity.CENTER);
        }

    }






    @Override
    public void onFocusChange(View v, boolean hasFocus) {

  //   if (hasFocus)  { Log.v("hjk","focussss!!!: "+v.getId());}


        if (et.showc && et.sowc && hasFocus) {

            this.setTypeface(null,Typeface.NORMAL);

            Log.v("uiopo","hasFocus"+"  "+et.pr);

            if (et.pr)

            {
                this.setBackgroundDrawable(getResources().getDrawable(R.color.colorPrimaryRed));

                et.ed1000 = this;

                et.t2c();
            }

        }

        if (et.showc && et.sowc && !hasFocus)
        {
           et.pr=true;

            this.setBackgroundDrawable(getResources().getDrawable(android.R.color.white));
        }

        et.sowc=true;

    }

    @Override
    public void onClick(View v) {

        Log.v("uiopo","onClick"+"  "+et.pr);

        this.setTypeface(null,Typeface.NORMAL);

        if (et.pr && et.showc && et.sowc) {

            Log.v("ttop","yes");
            this.setBackgroundDrawable(getResources().getDrawable(R.color.colorPrimaryRed));

            et.ed1000 = this;

            et.t2c();
            et.pr = true;
        }
    }
}