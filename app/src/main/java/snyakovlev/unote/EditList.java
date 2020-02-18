package snyakovlev.unote;


import android.*;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
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
import android.widget.Button;
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

/**
 * Created by user on 27.05.2015.
 */
public class EditList extends Fragment  {

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

    EditText et_name_list;

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



    static EditList newInstance(CurrSave currsave,boolean list) {

        EditList edata = new EditList();

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

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)

    {

        glav = (Glavnoe_Activity) getActivity();

        this.currsave=glav.currsave;

        glav.setTitle(getActivity().getResources().getString(R.string.ct9));
        if (savedInstanceState==null) {
            nametable = glav.allSheets.getUuid_sheet();

            _temptable = glav.allSheets.getTempltable();

            _table=currsave.uuid_table_table;

        }
        else
        {
            _table=savedInstanceState.getString("table");

            nametable = savedInstanceState.getString("getUuid_sheet");

            _temptable = savedInstanceState.getString("getTempltable");

        }

        boolean edit=true;

        editing=true;

        allsheets = ((Glavnoe_Activity) getActivity()).allSheets;

        ListSheetAdapter2 adapter2 = new ListSheetAdapter2(getActivity(), initSheets());

        glav.lw.setAdapter(adapter2);

        View root = inflater.inflate(R.layout.edit_list_layout, container, false);

        linl=(LinearLayout)root.findViewById(R.id.linvert);

        tr0=(TableRow) root.findViewById(R.id.tr0);

        et_name_list=(EditText)root.findViewById(R.id.et_name_list);



        tl = (TableLayout) root.findViewById(R.id.tl);

        tl.setMinimumWidth((int) (Math.round(getResources().getDisplayMetrics().widthPixels / 1.3)));


        glav.drawerlay.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);


        if (!_table.equals("")) {

            if (savedInstanceState!=null)

            { cuid= savedInstanceState.getString("cuid");}
            else
            {
                cuid=currsave.uuid_table_table;
            }

            created=true;
        }



        initLayout();

        return root;

    }


    void createTable(String nametable)
    {
        glav.currsave.setUuid_table_table(new CreateUID(getActivity()).creatingUID());

        cuid=currsave.getUuid_table_table();

        glav.currsave.setName_table_table(nametable);

        db.createTableTable(glav.currsave.getUuid_table_table(),1);
    }






    boolean fvis;
    @Override
    public void onSaveInstanceState(Bundle outState) {
        tl.clearChildFocus(getFocusView());
        update();
        if (glav!=null && glav.allSheets!=null) {
            outState.putString("table",_table);
            outState.putString("getUuid_sheet", glav.allSheets.getUuid_sheet());

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





    ArrayList<CustomEditTextList> et = new ArrayList<>();

    CustomButton btdel;

    void addRow(boolean del) {

        TableRow tr = new TableRow(getActivity());

        String idrow= Calendar.getInstance().getTimeInMillis()+"";


        tr.setBackgroundColor(Color.BLACK);

        tr.setBackgroundColor(Color.WHITE);

        TableLayout.LayoutParams lp = new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );

        tr.setLayoutParams(lp);


            final CustomEditTextList cet = crEdite(idrow);

            TableRow.LayoutParams lpbt = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.1f);


            final int z = this.i;


            lpbt.setMargins(0, 0, 0, 0);

            btdel = new CustomButton(getActivity(),idrow);

            btdel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    minusRowList(((CustomButton)v).idrow );
                }
            });
            cet.setTypeface(null, Typeface.ITALIC);

            cet.setHint(getResources().getString(R.string.et1));

            cet.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    updateStyle(cet, Typeface.NORMAL);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 0) updateStyle(cet, Typeface.ITALIC);
                }
            });


            et.add(cet);

            tr.addView(cet);

          if (del)  tr.addView(btdel);

            tl.addView(tr);

        Log.v("lll","addrow="+idrow);


            inTable(idrow);

    }

    boolean start=true;

    void inTable(String idrow)
    {

        Log.v("lll","intabel="+idrow);

        Table t = new Table(1);

        t.setUuidrow(idrow);

        t.setNamerow("");

        t.col[0].setCol("");

        db.inTableTable(cuid, t);

    }


    CustomEditTextList crEdite(String idrow) {
        CustomEditTextList ced = new CustomEditTextList(getActivity(), this,idrow);



        return ced;
    }


    DataBaseInterface db;

    TableLayout tl;

    ArrayList<Table> tab=new ArrayList<>();

    int i;

    String cuid;

    boolean list;

    TextView tev;


    String name_table_t="";



    void initLayout() {

        Log.v("ertret", "initLayout");


        et.clear();

        tl.removeAllViewsInLayout();

        tr0.removeAllViews();

        db = new DataBaseInterface(getActivity());

        db.getWritableDatabase();

        et_name_list.setText(currsave.getName_table_table());

        if (cuid != null) {
            tab = db.queryTableTableAll(cuid);
        } else {
            createNewList();
            return;
        }


        TableLayout.LayoutParams lp = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);

        tr0.setLayoutParams(lp);

        TableRow.LayoutParams lp1 = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);

        lp1.setMargins(0, 0, 0, 0);


        if (tab.size() != 0) {


            for (int i = 0; i < tab.size(); i++) {

                TableRow tr = new TableRow(getActivity());

                TableLayout.LayoutParams lpp = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);

                lpp.setMargins(0, 0, 0, 0);

                tr.setLayoutParams(lpp);

                String uuidrow = tab.get(i).uuidrow;

                final CustomEditTextList cet = crEdite(uuidrow);

                TableRow.LayoutParams lpbt = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.1f);

                lpbt.setMargins(0, 0, 0, 0);

                CustomButton btdel = new CustomButton(getActivity(),uuidrow);

                btdel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        minusRowList(((CustomButton)v).idrow);
                    }
                });

                cet.setHint(getResources().getString(R.string.et1));

                cet.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        Log.v("ffffff", s.toString());

                        updateStyle(cet, Typeface.NORMAL);


                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.length() == 0) updateStyle(cet, Typeface.ITALIC);

                    }
                });


                if (tab.get(i).col[0].getCol().length() == 0) {
                    cet.setTypeface(null, Typeface.ITALIC);
                } else {
                    cet.setTypeface(null, Typeface.NORMAL);
                }

                cet.setText(tab.get(i).col[0].getCol());

                et.add(cet);

                tr.addView(cet);

                if (i > 0) tr.addView(btdel);

                tl.addView(tr);


            }

        }
    }




    boolean edit2=false;


    void createNewList()
    {
        createTable("");

        addRow(false);
    }




    void updateStyle(CustomEditTextList cet,int style )
    {
        cet.setTypeface(null,style);
    }


    boolean update() {


         currsave.setName_table_table(et_name_list.getText().toString());

         if (et_name_list.getText().length()==0)
         {
             currsave.setName_table_table(getResources().getString(R.string.et4));
         }



        for (int ii = 0; ii < et.size(); ii++) {

            Table t = new Table(1);

                    t.col[0].setCol(et.get(ii).getText().toString());
                    t.setUuidrow(et.get(ii).idrow);
                    db.urow(cuid, t.getUuidrow(), t);
                }



        if (ContextCompat.checkSelfPermission(glav, Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)   createfilehtml0();

        currsave.updateDataBase();

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

        if   (update()) {

            Log.v("aaaabb","close");

           if (tab.size()>1 && et_name_list.getText().length()>0 && !currsave.isEditing()) glav.currsave.addDataBase();

            IShowFragmentWL isfwl = (IShowFragmentWL) getActivity();

            //   isfwl.showFragmentFF2();

            isfwl.showFragmentWL();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main24list, menu);

    }

    boolean showcr;

    boolean ww=true;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.gll) {

            if (ww) Widget2.sendRefreshBroadcast(getActivity());

            save();
        }

        if (id== R.id.addrowl)
        {
            addRow(true);
        }



        if (id== R.id.widget2)
        {


                Intent i = new Intent(getActivity(), Widget2.class);
                PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putString("uuid_table_table", currsave.getUuid_table_table()).commit();
                PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putString("idcurrsave", currsave.getUuid_save()).commit();
                PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putString("tcurrsave", "l").commit();
                Widget2.sendRefreshBroadcast(getActivity());
                ww=true;

        }

        return true;
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

            for (Table t : tab) {

                            s.startTag("", "check");
                            s.attribute("","checked","0");
                            s.attribute("","text",t.col[0].getCol());
                            s.endTag("","check");

                }

            s.endDocument();


        }
        catch (Exception e)
        {
            throw new RuntimeException(e);

        }
    }




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


    int id=0;

    void minusRowList(String j)
    {
        Log.v("lll","minus="+j);

            update();

            db.deleterow(cuid, j);

            tab = db.queryTableTableAll(cuid);

            initLayout();

    }


}
class CustomButton extends android.support.v7.widget.AppCompatImageButton
{
    String idrow;

    public CustomButton(Context context,String idrow) {

        super(context);

        TableRow.LayoutParams lpbt = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0.1f);

        this.setLayoutParams(lpbt);

        this.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_delete));

        this.setBackgroundColor(Color.WHITE);

        this.idrow=idrow;
    }
}

class CustomEditTextList extends android.support.v7.widget.AppCompatEditText  {

    EditList et;
    String idrow;
    boolean focus;

    public CustomEditTextList(Activity context, EditList et,  String id) {
        super(context);


            this.idrow=id;
            this.et = et;
            TableRow.LayoutParams lp = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
            lp.setMargins(1, 0, 1, 0);
            this.setTextSize(Float.parseFloat(Fonts.fontsizeTable));
            this.setLayoutParams(lp);
            this.setBackgroundColor(Color.WHITE);
            this.setGravity(Gravity.CENTER);


    }

}