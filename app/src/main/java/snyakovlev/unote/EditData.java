package snyakovlev.unote;


import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.TimeZone;

import static snyakovlev.unote.DataBaseInterface.ap0;
import static snyakovlev.unote.DataBaseInterface.ap1;
import static snyakovlev.unote.DataBaseInterface.ap11;
import static snyakovlev.unote.DataBaseInterface.ap2;
import static snyakovlev.unote.DataBaseInterface.ap3;
import static snyakovlev.unote.DataBaseInterface.ap5;
import static snyakovlev.unote.DataBaseInterface.ap8;
import static snyakovlev.unote.DataBaseInterface.ap9;

/**
 * Created by user on 27.05.2015.
 */
public class EditData extends Fragment implements IAddDataBase,ISetType,IInitTamplate, View.OnFocusChangeListener,IAddWorkList, View.OnClickListener {


    EditText addValue;

    EditText addText;

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


    String _datetime = null;

    String _photofile1 = "";

    String _photofile2 = "";

    String _photofile3 = "";

    String _filenameimage = "";

    String nametable;

    String _filename = "";

    AudioRecorder aur;

   String _temptable;

    Glavnoe_Activity glav;

    CurrSave currsave;


    TextView tdate,ttime;
    Switch vkl;
    boolean upd;
    String ida;



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        setHasOptionsMenu(true);

        glav=(Glavnoe_Activity)getActivity();

        _value = getArguments().getString("value");

        _text = getArguments().getString("text");

        _filenameimage = getArguments().getString("filenameimage");

        editing=getArguments().getBoolean("edit");

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

        editing=getArguments().getBoolean("edit");

    }

    static EditData newInstance(CurrSave currsave) {

        EditData edata = new EditData();

        edata.currsave=currsave;

        Bundle args = new Bundle();

        args.putString("value", currsave.getValue());

        args.putString("text", currsave.getText());

        args.putBoolean("edit", currsave.isEditing());

        args.putString("filename", currsave.getFilename());

        args.putString("photofile1", currsave.getFilenamephoto1());

        args.putString("photofile2", currsave.getFilenamephoto2());

        args.putString("photofile3", currsave.getFilenamephoto3());

        args.putString("filenameimage", currsave.getFilenameimage());


        edata.setArguments(args);

        return edata;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.v("wedsxc","saveInstance ed="+ed+"");
        outState.putString("getUuid_sheet",glav.allSheets.getUuid_sheet());
        outState.putString("getTempltable",glav.allSheets.getTempltable());
        outState.putBoolean("ed",ed);
        outState.putBoolean("saving",saving);
        super.onSaveInstanceState(outState);
    }

    boolean ed;

    boolean saving;

    @Override
    public void onPause() {


            if (ed)


            {
                editDataInDataBase();
            }
            else
                {

                addDataInDataBase();

            }




        super.onPause();
    }

    boolean view=true;

    boolean created=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)

    {



        created=true;

        if (savedInstanceState==null) {
            nametable = glav.allSheets.getUuid_sheet();

            _temptable = glav.allSheets.getTempltable();

            ed=currsave.isEditing();
        }
        else
        {

            nametable=savedInstanceState.getString("getUuid_sheet");
            _temptable =savedInstanceState.getString("getTempltable");
            ed=savedInstanceState.getBoolean("ed");
            saving=savedInstanceState.getBoolean("saving");

        }



     //   getActivity().getSupportFragmentManager().beginTransaction()
       //         .add(R.id.footer, af)
       //         .commit();

        View root = inflater.inflate(R.layout.edit_data_layout, container, false);

        napom = false;
        DataBaseInterface db = new DataBaseInterface(getActivity());
        db.getWritableDatabase();
        ArrayList<Alarm> alls = new ArrayList<>();
        alls = db.qAlarmTabel();
        tdate = (TextView)root.findViewById(R.id.tdate);
        tdate.setOnClickListener(this);
        ttime = (TextView)root.findViewById(R.id.ttime);
        ttime.setOnClickListener(this);
        vkl =(Switch) root.findViewById(R.id.vkl);
        vkl.setOnClickListener(this);


        for (Alarm a : alls) {

            if (a.id_currsave.equals(currsave.getUuid_save())) {
                datenoform = a.datenoform;
                timenoform = a.timenoform;
                tdate.setText(a.date);
                ttime.setText(a.time);
                if (a.ok.equals("ok")) {
                    vkl.setChecked(true);
                } else {
                    vkl.setChecked(false);
                }
                upd = true;
                ida = a.id;
            }
        }

        formatDateTime();

        currsave=glav.currsave;

        currsave.setEditing(ed);

         if (!ed)   glav.currsave.setUuid_save(new CreateUID(getActivity()).creatingUID() + "_save");

        Random rnd=new Random();
        int r=rnd.nextInt(55);
        int g=rnd.nextInt(55);
        int b=rnd.nextInt(55);
        int col= Color.rgb(200+r,200+g,200+b);


         if (ed) view=true;

        addValue = (EditText) root.findViewById(R.id.addValue);

        addValue.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() ==R.id.addValue) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction()&MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

       if (ed) toTextView();



        Log.v("qqaass","uuid="+currsave.getUuid_save());

        addValue.setBackgroundColor( PreferenceManager.getDefaultSharedPreferences(getActivity())
            .getInt("fon_col_" + currsave.getUuid_save(),PreferenceManager.getDefaultSharedPreferences(getActivity())
                    .getInt("fon_col_um",Color.WHITE )));

        addValue.setTextColor( PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getInt("text_col_" + currsave.getUuid_save(), PreferenceManager.getDefaultSharedPreferences(getActivity())
                        .getInt("text_col_um",Color.BLACK )));

        addValue.setHintTextColor( PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getInt("text_col_" + currsave.getUuid_save(), PreferenceManager.getDefaultSharedPreferences(getActivity())
                        .getInt("text_col_um",Color.BLACK )));

     if (!ed) {  PreferenceManager.getDefaultSharedPreferences(getActivity()).edit()
                .putInt("text_col_" + currsave.getUuid_save(),  PreferenceManager.getDefaultSharedPreferences(getActivity())
                        .getInt("text_col_um",Color.BLACK )).commit();}

    if (!ed)  {  PreferenceManager.getDefaultSharedPreferences(getActivity()).edit()
                .putInt("fon_col_" + currsave.getUuid_save(),  PreferenceManager.getDefaultSharedPreferences(getActivity())
                        .getInt("fon_col_um",Color.WHITE )).commit();}

        if (!ed)    {PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putInt("fon_col_" + glav.currsave.getUuid_save(), col).commit();}

        glav=(Glavnoe_Activity)getActivity();



        ListSheetAdapter2 adapter2 = new ListSheetAdapter2(getActivity(), initSheets());

        glav.lw.setAdapter(adapter2);


        addValue.setText(_value);

        addValue.setTypeface(Typeface.createFromAsset(Fonts.getAss(getActivity()),Fonts.fontWl));

        addValue.setTextSize(Float.parseFloat(Fonts.fontsizeWL));



        addValue.setOnFocusChangeListener(this);

       Log.d("jhg","eddata " +glav.allSheets.getNametablesheet());

        glav.setTitle(glav.allSheets.getNametablesheet());



        return root;

    }

    KeyListener kl;


    void toTextView()
    {
        addValue.setEnabled(false);
        addValue.setCursorVisible(false);
        kl=addValue.getKeyListener();
        addValue.setKeyListener(null);
        addValue.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
        addValue.setVerticalScrollBarEnabled(true);
    }

    void toEditText()
    {
        addValue.setEnabled(true);
        addValue.setCursorVisible(true);
        addValue.setKeyListener(kl);
        addValue.setLongClickable(true);
        addValue.setInputType(InputType.TYPE_CLASS_TEXT);
        addValue.setSingleLine(false);
    }

    ArrayList initSheets()
    {
        ArrayList<AllSheets> allsheetss;

      DataBaseInterface  db=new DataBaseInterface(getActivity().getApplicationContext());

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

    void cancel()
    {
          InputMethodManager imm=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
          imm.hideSoftInputFromWindow(addValue.getWindowToken(),0);



            if (ed)

            {
                editDataInDataBase();

                closeEditeData();
            }
            else
                {

                addDataInDataBase();

                closeEditeData();
            }



        AlarmFrag.napom=false;

        s2db();

        if (AlarmFrag.napom)
        {
            Toast.makeText(getActivity(), getResources().getString(R.string.ed4), Toast.LENGTH_SHORT).show();
        }

        currsave=null;
    }

    void closeEditeData()
    {
        IShowFragmentWL isfwl=(IShowFragmentWL)getActivity();

        isfwl.showFragmentWL();
    }


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

    @Override
    public void addDataInDataBase() {



        ed=true;

        glav=(Glavnoe_Activity)getActivity();


        _value = addValue.getText().toString();



        Calendar cal = Calendar.getInstance();

     //   glav.currsave=new Glavnoe_Activity.CurrSave(glav.db,glav.allSheets.getUuid_sheet());


       if ((_value.equals("") & photo_array[0].equals("")) & (_filename.equals("") & _text.equals("")) & _filenameimage.equals("")) {
            Toast.makeText(getActivity(), getResources().getString(R.string.ed5), Toast.LENGTH_SHORT).show();


       } else {
           formatDateTime();

           glav.currsave.setDatetime(_datetime);
           glav.currsave.setValue(_value);
           glav.currsave.setText(_text);
           glav.currsave.setFilename(_filename);
           glav.currsave.setFilenamephoto1(_photofile1);
           glav.currsave.setFilenamephoto2(_photofile2);
           glav.currsave.setFilenamephoto3(_photofile3);
           glav.currsave.setFilenameimage(_filenameimage);

           glav.currsave.addDataBase();


           Toast.makeText(getActivity(), getResources().getString(R.string.ed6), Toast.LENGTH_SHORT).show();
       }


    }

    @Override

    public void editDataInDataBase() {



        glav=(Glavnoe_Activity)getActivity();

        _value = addValue.getText().toString();




        if ((_value.equals("") & photo_array[0].equals("")) && (_filename.equals("") & _text.equals("")) & _filenameimage.equals("")) {

            Toast.makeText(getActivity(),getResources().getString(R.string.ed7) , Toast.LENGTH_SHORT).show();

        }
        else
        {
            Log.v("ggg", "sdsd" + _photofiletmp1);


            if (photo_array[0].equals("")) {
                photo_array[0] = _photofiletmp1;
            }

            Log.v("ggg", "sds2" + photo_array[0]);

            if (photo_array[1].equals("")) {
                photo_array[1] = _photofiletmp2;
            }

            if (photo_array[2].equals("")) {
                photo_array[2] = _photofiletmp3;
            }

            if (_value.equals(_valtmp)) _value = _valtmp;

            if (_text.equals(_texttmp)) _text = _texttmp;

            if (_filename.equals(_filenametmp)) _filename = _filenametmp;

            if (_filenameimage.equals(_filenameimagetmp)) _filenameimage = _filenameimagetmp;

            glav.currsave.setValue(_value);
            glav.currsave.setText(_text);
            glav.currsave.setFilename(_filename);
            glav.currsave.setFilenamephoto1(_photofile1);
            glav.currsave.setFilenamephoto2(_photofile2);
            glav.currsave.setFilenamephoto3(_photofile3);
            glav.currsave.setFilenameimage(_filenameimage);

            glav.currsave.updateDataBase();


            Toast.makeText(getActivity(), getResources().getString(R.string.ed7), Toast.LENGTH_SHORT).show();
        }


    }


    public void setType(int type) {
        switch (type) {

            case 1: {
                String s=addValue.getText().toString();
                addValue.setInputType(InputType.TYPE_CLASS_TEXT);


                addValue.setSelection(addValue.getText().length());
                addValue.setSingleLine(false);
            }
            break;

            case 2: {
                String s=addValue.getText().toString();
                addValue.setInputType(InputType.TYPE_CLASS_NUMBER);
                addValue.setSingleLine(false);
                addValue.setSelection(addValue.getText().length());



            }
            break;
        }
    }

    Menu menu;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main3, menu);
        this.menu = menu;

    }

    boolean recording = false;

    boolean clav;

    boolean pw=false;

    boolean ett=true;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id==R.id.widget)
        {
            pw=true;
            Intent i=new Intent(getActivity(),Widget2.class);
            PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putString("idsheet", glav.allSheets.getUuid_sheet()).commit();
            PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putString("idcurrsave", currsave.getUuid_save()).commit();
            PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putString("tcurrsave", "t").commit();
            Widget2.sendRefreshBroadcast(getActivity());
        }

        if (id==R.id.edit)
        {
            if (ett) {
                item.setIcon(getResources().getDrawable(R.drawable.view));
                toEditText();
                ett = false;
            }
            else
            {
                item.setIcon(getResources().getDrawable(R.drawable.edit));
                toTextView();
                ett=true;
            }
        }

        if (id==R.id.typeclav)
        {
            if (!clav)
            {
                setType(2);
                clav=true;
            }
            else
            {
                setType(1);
                clav=false;
            }
        }

        if (id==R.id.colfon2)
        {
           dialColor1();
        }

        if (id==R.id.coltext2)
        {
            dialColor2();
        }


        if (id==R.id.share_text)
        {
            if (ed)
        {
            editDataInDataBase();
        }

        else {
            addDataInDataBase();
        }
            glav.share(null,"text",currsave.getValue());
        }

        if (id==R.id.cancel)
        {
          if (pw)  Widget2.sendRefreshBroadcast(getActivity());
            cancel();
        }



        if (id == R.id.date) {
            iii = 1;

            setDate();

            return true;
        }

        if (id == R.id.time) {
            iii = 1;

            setTime();

            return true;
        }

        if (id == R.id.temp_phrase) {
            createTemplatePhrase();


            return true;
        }


        return true;
    }










    void dialColor1() {
        new AmbilWarnaDialog(getActivity(), Color.WHITE,false, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int col) {

              final int colo=col;
                    PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putInt("fon_col_" + currsave.getUuid_save(), col).commit();

                addValue.setBackgroundColor(col);

                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
                builder.setTitle("")
                        .setMessage(getResources().getString(R.string.ed8))
                        .setCancelable(false)
                        .setNegativeButton(getResources().getString(R.string.no),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                })
                        .setPositiveButton(getResources().getString(R.string.yes),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putInt("fon_col_um", colo).commit();
                                    }
                                });
                android.support.v7.app.AlertDialog alert = builder.create();
                alert.show();

            }


        }).show();
    }

    void dialColor2() {
        new AmbilWarnaDialog(getActivity(), Color.BLACK, false, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int col) {

                final int colo=col;
                    PreferenceManager.getDefaultSharedPreferences(getActivity()).edit()
                            .putInt("text_col_" + currsave.getUuid_save(), col).commit();


                addValue.setTextColor(col);

                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
                builder.setTitle("")
                        .setMessage(getResources().getString(R.string.ed9))
                        .setCancelable(false)
                        .setNegativeButton(getResources().getString(R.string.no),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                })
                        .setPositiveButton(getResources().getString(R.string.yes),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putInt("text_col_um", colo).commit();
                                    }
                                });
                android.support.v7.app.AlertDialog alert = builder.create();
                alert.show();

            }


        }).show();
    }





    void setRecogn(String text)
    {
        final String str=text;
        String mess="";
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(mess)
                .setCancelable(false)
                .setMessage(text)
                .setPositiveButton(getResources().getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                addValue.setText(str);
                            }
                        })

                .setNegativeButton(getResources().getString(R.string.ed1),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                             //   VoiceRecognition vr=new VoiceRecognition(glav);
                             //   if (vr.CheckVoiceRecognition()) {
                              //      vr.speak();
                              //  }
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }








    GregorianCalendar cale = (GregorianCalendar) Calendar.getInstance();


    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            cale.set(Calendar.YEAR, year);

            cale.set(Calendar.MONTH, monthOfYear);

            cale.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            update(iii);

            iii = 0;
        }
    };

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {


        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            cale.set(Calendar.HOUR_OF_DAY, hourOfDay);

            cale.set(Calendar.MINUTE, minute);

            update2(iii);

            iii = 0;
        }
    };

    int iii = 0;

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

    void update2(int ii) {

        switch (ii) {
            case 1:

                hour_dp = cale.get(Calendar.HOUR_OF_DAY);

                minute_dp = cale.get(Calendar.MINUTE);

                String str = "";

                if (minute_dp < 10) str = "0" + minute_dp;
                else str = minute_dp + "";

                if (focus.equals("addvalue")) {

                    String temp = addValue.getText().toString();

                    Log.v("amm", "t1:" + temp);

                    String temp2 = hour_dp + "." + str + " ";

                    Log.v("amm", "t2:" + temp2);

                    if (temp2.equals(temp)) temp2 = "";

                    String text = temp + " " + temp2;

                    addValue.setText(text);

                    addValue.setSelection(addValue.getText().length());
                }

                if (focus.equals("addtext")) {

                    String temp = addText.getText().toString();

                    String temp2 = hour_dp + "." + str + " ";

                    if (temp2.equals(temp)) temp2 = "";

                    String text = temp + " " + temp2;

                    addText.setText(text);
                }

                break;

        }

    }


    void update(int ii) {
        switch (ii) {
            case 1:

                den_dp = cale.get(Calendar.DAY_OF_MONTH);

                mes_dp = cale.get(Calendar.MONTH);

                year_dp = cale.get(Calendar.YEAR);

                String str = "";

                int mes = mes_dp + 1;

                if ((mes_dp + 1) < 10) str = "0" + mes;
                else str = mes + "";

                if (focus.equals("addvalue")) {

                    String temp = addValue.getText().toString();

                    Log.v("amm", "t1:" + temp);

                    String temp2 = den_dp + "." + str + "." + year_dp + " ";

                    Log.v("amm", "t2:" + temp2);

                    if (temp2.equals(temp)) temp2 = "";

                    String text = temp + " " + temp2;

                    addValue.setText(text);

                    addValue.setSelection(addValue.getText().length());
                }

                if (focus.equals("addtext")) {

                    String temp = addText.getText().toString();

                    String temp2 = den_dp + "." + str + "." + year_dp + " ";

                    if (temp2.equals(temp)) temp2 = "";

                    String text = temp + " " + temp2;

                    addText.setText(text);
                }

                break;

        }
    }

    void update3(String text) {


        String str = "";


        if (focus.equals("addvalue")) {

            String temp = addValue.getText().toString();

            String temp2 = text;

            if (temp2.equals(temp)) temp2 = "";

            String strok = temp + " " + temp2;

            addValue.setText(strok);

            addValue.setSelection(addValue.getText().length());
        }

        if (focus.equals("addtext")) {

            String temp = addValue.getText().toString();

            String temp2 = text;

            if (temp2.equals(temp)) temp2 = "";

            String strok = temp + " " + temp2;

            addText.setText(strok);
        }


    }

    int den_dp = 0, mes_dp = 0, year_dp = 0;

    String focus = "addvalue";

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        if (v.getId() == R.id.addValue) {
            focus = "addvalue";
        }

    }




    public void createTPhrase()
    {
        FragmentManager fm = getActivity().getSupportFragmentManager();

        CreateTemplate dialog = new CreateTemplate();

        dialog.show(fm, "addTemplate");
    }


    public void createTemplatePhrase() {


        dialogCTPhrase();



    }

    String[] templPhrases;


   void dialogCTPhrase()
   {
       ArrayList<String> items = glav.db.queryDataBaseAll(glav.allSheets.getTempltable(), "template");

       int size=items.size();

     templPhrases =new String[size];

      int i=0;

       for (Object s:items)
       {
           templPhrases[i]=s.toString();

           i++;
       }

       AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
       builder.setTitle(getResources().getString(R.string.ed2))
               .setItems(templPhrases, new DialogInterface.OnClickListener() {


                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       update3(templPhrases[which]);

                   }
               })
               .setNegativeButton(getResources().getString(R.string.otmena),new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       return ;
                   }
               })
               .setPositiveButton(getResources().getString(R.string.ed3), new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       createTPhrase();
                   }
               })
               .setNeutralButton(getResources().getString(R.string.ct7), new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       showDialogDelete();
                   }
               })
               .setCancelable(false);
       AlertDialog alert = builder.create();
       alert.show();
   }

   void showDialogDelete()
    {
        del_shabl.clear();

        ArrayList<String> items = glav.db.queryDataBaseAll(glav.allSheets.getTempltable(), "template");

       final boolean[] mchis=new boolean[items.size()];

        int size=items.size();

        templPhrases =new String[size];

        int i=0;

        for (Object s:items)
        {
            templPhrases[i]=s.toString();

            i++;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.ed2))
                .setMultiChoiceItems(templPhrases,mchis, new DialogInterface.OnMultiChoiceClickListener() {


                    @Override
                    public void onClick(DialogInterface dialog, int which,boolean ischecked) {
                        mchis[which]=ischecked;
                    }
                })
                .setNegativeButton(getResources().getString(R.string.otmena),new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return ;
                    }
                })
                .setPositiveButton(getResources().getString(R.string.ct7), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        for (int i=0;i<templPhrases.length;i++)
                        {
                            if (mchis[i]) update4(templPhrases[i]);
                        }
                        delshabl();
                    }
                })
                .setCancelable(false);
        AlertDialog alert = builder.create();
        alert.show();

    }

    ArrayList<String> del_shabl =new ArrayList<>();

    void update4(String it)
    {
        del_shabl.add(it);
    }

    void delshabl()
    {
        if (del_shabl.size()!=0) {
            glav.db.deleteShablon(del_shabl, glav.allSheets.getTempltable());

            dialogCTPhrase();
        }
    }




    @Override
    public void initTemplate() {

        Log.v("bmm", "initTemplate");

      createTemplatePhrase();

    }

    int photo_ind = 0;

    String[] photo_array = new String[]{"", "", ""};



    @Override
    public void addWorkList() {
        addDataInDataBase();
    }


    void createImage() {

        ICreaterPaint icp = (ICreaterPaint) getActivity();

        icp.showPaintCreator(_filenameimagetmp,false);


    }

    int vv=0;

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.tdate)
        {
            vkl.setChecked(false);
            setDateAl();
        }
        if (v.getId()==R.id.ttime)
        {
            vkl.setChecked(false);
            setTimeAl();
        }
        if (v.getId()==R.id.vkl)
        {
            if (vv!=0){upd=true;}
            vv++;
            if (!ttime.getText().equals("") && !tdate.getText().equals("")) {

                if (vkl.isChecked()) {
                    s2db();
                }
                else
                {
                    Toast.makeText(getContext(), getResources().getString(R.string.af1), Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                vkl.setChecked(false);
                Toast.makeText(getContext(), getResources().getString(R.string.af2), Toast.LENGTH_SHORT).show();
            }
        }
    }

    GregorianCalendar caleAl = (GregorianCalendar) Calendar.getInstance();


    DatePickerDialog.OnDateSetListener dAl = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int yearr, int monthOfYear, int dayOfMonth) {

            caleAl.set(Calendar.YEAR, yearr);

            caleAl.set(Calendar.MONTH, monthOfYear);

            caleAl.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            tdate.setText(dayOfMonth+"."+updateAl(monthOfYear)+"."+yearr);

            datenoform=dayOfMonth+"."+monthOfYear+"."+yearr;

        }
    };

    TimePickerDialog.OnTimeSetListener tAl = new TimePickerDialog.OnTimeSetListener() {


        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int min) {

            caleAl.set(Calendar.HOUR_OF_DAY, hourOfDay);

            caleAl.set(Calendar.MINUTE, min);

            ttime.setText(hourOfDay+":"+update2Al(min));

            timenoform=hourOfDay+":"+min;

        }
    };

    int iiiAl = 0;

    DatePickerDialog dpdAl;

    TimePickerDialog tpdAl;

    public void setDateAl() {
        dpdAl = new DatePickerDialog(getContext(), dAl,
                caleAl.get(Calendar.YEAR),
                caleAl.get(Calendar.MONTH),
                caleAl.get(Calendar.DAY_OF_MONTH));

        dpdAl.show();

    }

    public void setTimeAl() {
        tpdAl = new TimePickerDialog(getContext(), tAl, caleAl.get(Calendar.HOUR_OF_DAY), caleAl.get(Calendar.MINUTE), true);

        tpdAl.show();

    }

    int hour, minute;

    String timenoform="";

    String datenoform="";


    String update2Al(int min) {

        String str="";

        if (min < 10)
        {str = "0" + min;}
        else {str = min + "";}

        return str;

    }

    int den,mes,year;


    String updateAl(int mes) {



        String str = "";

        int mess = mes + 1;

        if (mess < 10)
        {str = "0" + mess;}
        else
        {str = mess + "";}

        return str;

    }


    void s2db()
    {
        Log.v("proverka",upd+"");

        DataBaseInterface db=new DataBaseInterface(getContext());

        db.getWritableDatabase();

        ContentValues cv=new ContentValues();

        if (!upd)

        {
            ida=(new CreateUID(getContext())).creatingUID() + "";
            cv.put(ap0, ida);
        }

        cv.put(ap1,currsave.getValue());

        cv.put(ap3,datenoform);

        cv.put(ap8,timenoform);

        cv.put(ap5,tdate.getText().toString());

        cv.put(ap9,ttime.getText().toString());

        cv.put(ap11,currsave.getUuid_save());

        String s="";

        if (vkl.isChecked())
        {
            s="ok";
        }
        else
        {
            s="non";
        }

        cv.put(ap2,s);

        if (!upd) {

            if (vkl.isChecked()) {
                db.iAlarmTable(cv);
                saveAlarm(getContext());
            }
        }
        else
        {
            if (vkl.isChecked()) {
                db.uAlarmTable(cv, ida);
                saveAlarm(getContext());
            }
            else
            {
                db.dAlarmTable(ida);
            }
        }

    }

    static boolean napom=false;

    static void saveAlarm(Context context)
    {
        String text="";

        AlarmManager am= (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        DataBaseInterface db=new DataBaseInterface(context);

        db.getWritableDatabase();

        ArrayList<Alarm> alls=db.qAlarmTabel();

        Log.v("proverka",alls.size()+"");

        long time1=0;
        long time2=0;
        long time=0;


        Calendar c=Calendar.getInstance();
        Calendar cc=Calendar.getInstance();


        for (int i=0;i<alls.size();i++)

        {

            int hour=str2hour(alls.get(i).timenoform);
            int min=str2min(alls.get(i).timenoform);
            time2=str2long2(alls.get(i).date);
            c.setTimeInMillis(time2);
            c.set(Calendar.HOUR_OF_DAY,hour);
            c.set(Calendar.MINUTE,min);

            time=c.getTimeInMillis();

            cc.setTimeInMillis(time);
            Log.v("proverka",cc.get(Calendar.HOUR_OF_DAY)+":"+cc.get(Calendar.MINUTE)+"   "+
                    cc.get(Calendar.DAY_OF_MONTH)+"."+cc.get(Calendar.MONTH)+"."+cc.get(Calendar.YEAR));
            if (time<Calendar.getInstance().getTimeInMillis()) {
                //  Toast.makeText(context, "Время напоминания меньше текущего. Напоминание не поставлено.", Toast.LENGTH_SHORT).show();

            }
            else {
                napom=true;

                text = alls.get(i).text;


                Intent intent = new Intent(context, ActivityAlarm.class);

                intent.setAction(alls.get(i).id_currsave);

                intent.putExtra("mess", text);

                PendingIntent pendingIntent = PendingIntent.getActivity(context, i, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                am.cancel(pendingIntent);


                if (alls.get(i).ok.equals("ok")) {
                    Log.v("proverka", "ok!");
                    if (Build.VERSION.SDK_INT >= 19) {
                        am.setWindow(AlarmManager.RTC_WAKEUP, time, 0, pendingIntent);
                    } else {
                        am.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
                    }
                } else {

                    Log.v("proverka", "nook!");
                    intent = new Intent(context, ActivityAlarm.class);

                    intent.setAction(alls.get(i).id_currsave);

                    pendingIntent = PendingIntent.getBroadcast(context, i, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                    am.cancel(pendingIntent);

                    pendingIntent.cancel();
                }
            }

        }




    }

    static  int str2hour(String str)
    {



        SimpleDateFormat df = new SimpleDateFormat("H:mm");

        df.setTimeZone(TimeZone.getDefault());

        int vremya=0;

        try {
            Date d = df.parse(str);

            vremya = d.getHours();


        } catch (ParseException e) {
            e.printStackTrace();
        }


        return vremya;

    }

    static  int str2min(String str)
    {



        SimpleDateFormat df = new SimpleDateFormat("H:mm");

        df.setTimeZone(TimeZone.getDefault());

        int vremya=0;

        try {
            Date d = df.parse(str);

            vremya = d.getMinutes();


        } catch (ParseException e) {
            e.printStackTrace();
        }


        return vremya;

    }

    static  long str2long2(String str)
    {

        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        long vremya=0;

        try {
            Date d = df.parse(str);



            vremya = d.getTime();


        } catch (ParseException e) {
            e.printStackTrace();
        }


        return vremya;

    }



}

 class Alarm
{
    String id;
    String id_currsave;
    String date;
    String time;
    String ok;
    String text;
    String datenoform;
    String timenoform;
}