package snyakovlev.unote;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.provider.BaseColumns;
import android.support.design.widget.TabLayout;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by user on 26.05.2015.
 */
public class DataBaseInterface  extends SQLiteOpenHelper implements BaseColumns {

    Context context;

    static String nametable = "";

    static String nameMainTable = "mainTable";

    static final String tab_param1 = "uuid_sheet";

    static final String tab_param2 = "nametablesheet";

    static final String tab_param3 = "password";

    static final String tab_param4 = "datetime";

    static final String tab_param5 = "templtable";

    static final String tab_param6="tabletempltable";

    static final String param0 = "uuid_save";

    static final String param1 = "nametablesave";

    static final String param2 = "value";

    static final String param3 = "text";

    static final String param4 = "datetime";

    static final String param5 = "filename";

    static final String param6 = "filenamephoto1";

    static final String param7 = "filenamephoto2";

    static final String param8 = "filenamephoto3";

    static final String param9 = "filenameimage";

    static final String param12="uuid_table_table";

    static final String param13="name_table_table";

    static final String param10 = "uuid_templtable";

    static final String param11 = "template";

    static final String param14 = "uuid_table_table";

    static final String param15 = "table_template";


    static final String CREATE_TABLE = "CREATE TABLE " + nameMainTable + " (_id INTEGER PRIMARY KEY AUTOINCREMENT," + tab_param1 + " TEXT," + tab_param2 + " TEXT," + tab_param3 + " TEXT," + tab_param4 + " TEXT," + tab_param5 + " TEXT,"+tab_param6+" TEXT)";


    public static File dbPath;

    public DataBaseInterface(Context context) {

        super(context, "NBookBD.db", null, 11);

        this.context = context;

       if (context!=null)   dbPath = context.getDatabasePath("NBookBD.db");

       Log.v("ddccvv",dbPath+"");


    }


    @Override
    public void onCreate(SQLiteDatabase sqldb) {
        sqldb.execSQL(CREATE_TABLE);
        createAlarmTable(sqldb);

    }

    static  final String ap0="id_alarm";

    static final String ap1="text_alarm";

    static final String ap2="regim_alarm";

    static final String ap3="date1_alarm";

    static final String ap4="povtor2_alarm";

    static final String ap5="date2_alarm";

    static final String ap6="chislo2_alarm";

    static final String ap7="denned2_alarm";

    static final String ap8="vremya1_alarm";

    static final String ap9="vremya2_alarm";

    static final String ap10="povtor3_alarm";

    static final String ap11="uuid_currsave";


    public void createAlarmTable(SQLiteDatabase sql)
    {

        sql.execSQL("DROP TABLE IF EXISTS 'AlarmTable'");

        sql.execSQL("CREATE TABLE 'AlarmTable' (_id INTEGER PRIMARY KEY AUTOINCREMENT,"+ ap0 + " TEXT," + ap1 + " TEXT," + ap2 + " TEXT," +
                ap3 + " TEXT," + ap4 + " TEXT," + ap5 + " TEXT," + ap6 + " TEXT," + ap7 + " TEXT," + ap8 + " TEXT," + ap9 +
                " TEXT," + ap10+" TEXT,"+ap11 + " TEXT)");


    }

    public void iAlarmTable(ContentValues cv)
    {
        SQLiteDatabase sql=this.getWritableDatabase();

        sql.insert("AlarmTable",null,cv);

        sql.close();
    }


    public void uAlarmTable(ContentValues cv,String ida)
    {
        SQLiteDatabase sql=this.getWritableDatabase();

        sql.update("AlarmTable",cv,"id_alarm=?",new String[]{ida});

        sql.close();
    }

    public  void dAlarmTable(String currsave)
    {
        SQLiteDatabase sql=this.getWritableDatabase();

        sql.delete("AlarmTable","id_alarm=?",new String[]{currsave});

        sql.close();
    }

    public ArrayList<Alarm> qAlarmTabel() {
        ArrayList<Alarm> alss = new ArrayList<>();

        SQLiteDatabase sql = this.getWritableDatabase();

        Cursor c = sql.query("AlarmTable", null, null, null, null, null, null);

        int i1 = c.getColumnIndex(ap0);

        int i2 = c.getColumnIndex(ap1);

        int i3 = c.getColumnIndex(ap3);

        int i4 = c.getColumnIndex(ap8);

        int i5 = c.getColumnIndex(ap2);

        int i6 = c.getColumnIndex(ap11);

        int i7 = c.getColumnIndex(ap5);

        int i8 = c.getColumnIndex(ap9);

        if (c.getCount() != 0) {

            while (c.moveToNext()) {
                Alarm als = new Alarm();

                als.id = c.getString(i1);

                als.id_currsave = c.getString(i6);

                als.datenoform = c.getString(i3);

                als.timenoform = c.getString(i4);

                als.ok=c.getString(i5);

                als.text=c.getString(i2);

                als.date=c.getString(i7);

                als.time=c.getString(i8);

                alss.add(als);
            }


        }

        return alss;
    }



public ArrayList<String> querySearch(String nameTable,String search)
{
    ArrayList<String> retvals = new ArrayList<>();

    SQLiteDatabase sql = this.getWritableDatabase();

  //  if (!search.equals(""))  sql.execSQL("SELECT * FROM "+nameTable+" WHERE value LIKE "+search);
    Cursor c=null;
    if (!search.equals(""))

    {
       c = sql.query(nameTable, null,
                "value LIKE ?", new String[]{"%" + search + "%"}, null, null, null);
    }
    else
    {
       //  c = sql.query(nameTable, null, null,null, null, null, null);

        return null;
    }

    if (c==null || c.getColumnCount()==0) return null;

    int indexcolumn = c.getColumnIndex("uuid_save");

    while (c.moveToNext()) {

        retvals.add(c.getString(indexcolumn));
    }



    sql.close();

    return retvals;
}



    public void insertDataBase(String uuid_sheet, ContentValues cv) {
        //       DataBaseInterface dbi=new DataBaseInterface(cont);

        SQLiteDatabase sql = this.getWritableDatabase();

        sql.insert(uuid_sheet, null, cv);

        sql.close();

    }

    public void deleteDataBase(String uuid_sheet, String uuid_save) {
        SQLiteDatabase sql = this.getWritableDatabase();

        sql.delete(uuid_sheet, "uuid_save=?", new String[]{uuid_save});

        sql.close();
    }

    public ArrayList<AllSheets> queryMainTableAll(String nameMainTable, String column1, String column2, String column3, String column5,String column6, Activity act) {
        ArrayList<AllSheets> allsheets = new ArrayList<>();

        SQLiteDatabase sql = this.getWritableDatabase();

        Cursor c = sql.query(nameMainTable, null, null, null, null, null, null);

        int indexcolumn1 = c.getColumnIndex(column1);

        int indexcolumn2 = c.getColumnIndex(column2);

        int indexcolumn3 = c.getColumnIndex(column3);

        int indexcolumn5 = c.getColumnIndex(column5);

        int indexcolumn6 = c.getColumnIndex(column6);

        if (c.getCount() != 0) {

            while (c.moveToNext()) {
                AllSheets als = new AllSheets(act, this);

                als.setUuid_sheet(c.getString(indexcolumn1));

                als.setNametablesheet(c.getString(indexcolumn2));

                als.setPassword(c.getString(indexcolumn3));

                als.setTempltable(c.getString(indexcolumn5));

                als.setTabletempltable(c.getString(indexcolumn6));

                allsheets.add(als);

            }
        }

        sql.close();

        return allsheets;
    }

    public ArrayList<AllSheets> queryMainTableAll2(String nameTable, String column1, String column2, String column3, String column5, String column6, Activity act) {

        ArrayList<AllSheets> allsheets = new ArrayList<>();

        SQLiteDatabase sql = this.getWritableDatabase();

        Cursor c = sql.query(nameTable, null, null, null, null, null, null);

        int indexcolumn1 = c.getColumnIndex(column1);

        int indexcolumn2 = c.getColumnIndex(column2);

        int indexcolumn3 = c.getColumnIndex(column3);

        int indexcolumn5 = c.getColumnIndex(column5);

        int indexcolumn6 = c.getColumnIndex(column6);

        while (c.moveToNext()) {
            AllSheets als = new AllSheets(act, this);

            als.setUuid_sheet(c.getString(indexcolumn1));

            als.setNametablesheet(c.getString(indexcolumn2));

            als.setPassword("admin");

            als.setTempltable(c.getString(indexcolumn5));

            als.setTabletempltable(c.getString(indexcolumn6));

            allsheets.add(als);

        }

        sql.close();

        return allsheets;
    }

    public Table queryTable(String nameTable) {

        SQLiteDatabase sql = this.getWritableDatabase();

        Cursor c = sql.query(nameTable, null, null, null, null, null, null);

        int count_col=c.getColumnCount();

        int index[] = new int[count_col];

        Log.v("ttyy",count_col+"");


        for (int i=0;i<count_col-4;i++) {

             index[i]=c.getColumnIndex("col" + i);

        }

        c.moveToFirst();

        Table t=new Table(count_col-4);

        for (int i=0;i<count_col-4;i++) {

            t.col[i].setCol(c.getString(index[i]));


        }




        sql.close();

        return t;
    }

    public void deleteShablon(ArrayList<String> phrase,String uuid)
    {
        SQLiteDatabase sql=this.getWritableDatabase();

        for (String p:phrase) {

            sql.delete(uuid, "template=?", new String[]{p});
        }
    }

    public void deleteShablonTable(String uuid1,String uuid2)
    {
        SQLiteDatabase sql=this.getWritableDatabase();

            sql.delete(uuid2, param14+"=?", new String[]{uuid1});

    }

    public ArrayList<String> queryDataBaseAll(String nameTable, String column) {
        ArrayList<String> retvals = new ArrayList<>();

        SQLiteDatabase sql = this.getWritableDatabase();

        Cursor c = sql.query(nameTable, null, null, null, null, null, null);

        if (c.getColumnCount()==0) return null;

        int indexcolumn = c.getColumnIndex(column);

        while (c.moveToNext()) {

            retvals.add(c.getString(indexcolumn));
        }

        sql.close();

        return retvals;
    }

    public String queryMainTable() {

        SQLiteDatabase sql = this.getWritableDatabase();

        Cursor c = sql.query("mainTable", null, null, null, null, null, null);

        String val=null;

        if (c.getColumnCount()==0) return null;

        int indexcolumn = c.getColumnIndex("nametablesheet");

        int ic2=c.getColumnIndex("uuid_sheet");

        while (c.moveToNext()) {

            if (c.getString(indexcolumn).equals("QuickStart"))
            {
                val=c.getString(ic2);
            }
        }

        sql.close();

        return val;
    }

       public String queryMainTableRec(Activity act) {

        SQLiteDatabase sql = this.getWritableDatabase();

        Cursor c = sql.query("mainTable", null, null, null, null, null, null);

        String val=null;

        if (c.getColumnCount()==0) return null;

        int indexcolumn = c.getColumnIndex("nametablesheet");

        int ic2=c.getColumnIndex("uuid_sheet");

        while (c.moveToNext()) {

            if (c.getString(indexcolumn).equals(act.getResources().getString(R.string.ga7)))
            {
                val=c.getString(ic2);
            }
        }

        sql.close();

        return val;
    }

    public String queryMainTableDlg(Activity act) {

        SQLiteDatabase sql = this.getWritableDatabase();

        Cursor c = sql.query("mainTable", null, null, null, null, null, null);

        String val=null;

        if (c.getColumnCount()==0) return null;

        int indexcolumn = c.getColumnIndex("nametablesheet");

        int ic2=c.getColumnIndex("uuid_sheet");

        while (c.moveToNext()) {

            if (c.getString(indexcolumn).equals(act.getResources().getString(R.string.ga18)))
            {
                val=c.getString(ic2);
            }
        }

        sql.close();

        return val;
    }



    public String queryMainTablePhone(Context act) {

        SQLiteDatabase sql = this.getWritableDatabase();

        Cursor c = sql.query("mainTable", null, null, null, null, null, null);

        String val=null;

        if (c.getColumnCount()==0) return null;

        int indexcolumn = c.getColumnIndex("nametablesheet");

        int ic2=c.getColumnIndex("uuid_sheet");

        while (c.moveToNext()) {

            if (c.getString(indexcolumn).equals(act.getResources().getString(R.string.ga17)))
            {
                val=c.getString(ic2);
            }
        }

        sql.close();

        return val;
    }


    public String queryMainTable2() {

        SQLiteDatabase sql = this.getWritableDatabase();

        Cursor c = sql.query("mainTable", null, null, null, null, null, null);

        String val=null;

        if (c.getColumnCount()==0) return null;

        int indexcolumn = c.getColumnIndex("nametablesheet");

        int ic2=c.getColumnIndex("templtable");



        while (c.moveToNext()) {

            if (c.getString(indexcolumn).equals("QuickStart"))
            {
                val=c.getString(ic2);
            }
        }

        sql.close();

        return val;
    }

    public String queryMainTable3() {

        SQLiteDatabase sql = this.getWritableDatabase();

        Cursor c = sql.query("mainTable", null, null, null, null, null, null);

        String val=null;

        if (c.getColumnCount()==0) return null;

        int indexcolumn = c.getColumnIndex("nametablesheet");

        int ic2=c.getColumnIndex("tabletempltable");


        while (c.moveToNext()) {

            if (c.getString(indexcolumn).equals("QuickStart"))
            {
                val=c.getString(ic2);
            }
        }

        sql.close();

        return val;
    }

    public void delSave(String uuid_sheet, String uuid_save)

    {
        SQLiteDatabase sql=this.getWritableDatabase();

        sql.delete(uuid_sheet,"uuid_save=?",new String[]{uuid_save});
    }

    public int getCountDataBaseAll(String nameTable)
    {

        SQLiteDatabase sql=this.getWritableDatabase();

        Cursor c= sql.query(nameTable, null, null, null, null, null, null);

        int count=c.getCount();

        sql.close();

        return count;
    }

    public String queryDataBaseRow(String uuid_sheet,String uuid_save,String column)
    {
        String retval=null;

        SQLiteDatabase sql=this.getWritableDatabase();

        Cursor c= sql.query(uuid_sheet,null,"uuid_save=?",new String[]{uuid_save},null,null,null);

        int indexcolumn= c.getColumnIndex(column);

      if (c.moveToFirst())

      {retval=c.getString(indexcolumn);}

        sql.close();


        return retval;
    }

    public String queryDataBaseRowNT(String column,String uuid_sheet)
    {
        String retval=null;

        SQLiteDatabase sql=this.getWritableDatabase();

        Cursor c= sql.query(this.nameMainTable,null,"uuid_sheet=?",new String[]{uuid_sheet},null,null,null);

        int indexcolumn= c.getColumnIndex(column);

        if (c.moveToFirst())

        {retval=c.getString(indexcolumn);}

        sql.close();


        return retval;
    }

    public String queryDataBaseRowNT2(String column,String nametable)
    {
        String retval=null;

        SQLiteDatabase sql=this.getWritableDatabase();

        Cursor c= sql.query(this.nameMainTable,null,"nametable=?",new String[]{nametable},null,null,null);

        int indexcolumn= c.getColumnIndex(column);

        if (c.moveToFirst())

        {retval=c.getString(indexcolumn);}

        sql.close();


        return retval;
    }

    public void insDB(String uuid_sheet,ContentValues cv)
    {
        SQLiteDatabase sql=this.getWritableDatabase();

        sql.insert(uuid_sheet,null,cv);

        sql.close();
    }





    public void updDB(String uuid_sheet,String  nametablesheet)
    {
        ContentValues cv=new ContentValues();

        cv.put(tab_param2,nametablesheet);

        SQLiteDatabase sql=this.getWritableDatabase();

        sql.update(nameMainTable,cv,"uuid_sheet=?",new String[]{uuid_sheet});

        sql.close();
    }



    public void updateDataBase(String uuid_sheet,String uuid_save,ContentValues cv)
    {
        SQLiteDatabase sql=this.getWritableDatabase();

        sql.update(uuid_sheet,cv,"uuid_save=?",new String[]{uuid_save});

        sql.close();
    }

    public void setPassTableMain(String uuid_sheet,String password)
    {
        SQLiteDatabase sql=this.getWritableDatabase();

        ContentValues cv=new ContentValues();

        cv.put("password",password);

        Log.i("nn",nametable+" "+password);

        sql.update(nameMainTable,cv,"uuid_sheet=?",new String[]{uuid_sheet});

        sql.close();
    }

    public void createTable(String uuid_sheet,String namesheet,String templtable,String tabletempltable,String datetime)
    {

        SQLiteDatabase sql=this.getWritableDatabase();

        ContentValues cv= new ContentValues();

        cv.put(tab_param2, namesheet);

        cv.put(tab_param1, uuid_sheet);

        cv.put(tab_param3, "admin");

        cv.put(tab_param4, datetime);

        cv.put(tab_param5, templtable);

        cv.put(tab_param6, tabletempltable);



        sql.insert("mainTable", null, cv);

        sql.execSQL("DROP TABLE IF EXISTS " + uuid_sheet);

        sql.execSQL("CREATE TABLE " + uuid_sheet+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT,"+ param0 + " TEXT," + param1 + " TEXT," + param2 + " TEXT," + param3 + " TEXT," + param4 + " TEXT," + param5 + " TEXT,"+ param6 + " TEXT,"+param7+" TEXT,"+ param8 +" TEXT,"+ param9 +" TEXT,"+param12 +" TEXT,"+param13 +" TEXT)");

        sql.close();
    }

    public void createTemplTable(String uuid_sheet_templ)
    {
        SQLiteDatabase sql=this.getWritableDatabase();

        sql.execSQL("DROP TABLE IF EXISTS " + uuid_sheet_templ);

        sql.execSQL("CREATE TABLE "  + uuid_sheet_templ + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,"+param10 + " TEXT," + param11 + " TEXT)");

        sql.close();
    }

    public void createTemplTableTable(String uuid_sheet_table_templ)
    {
        SQLiteDatabase sql=this.getWritableDatabase();

        sql.execSQL("DROP TABLE IF EXISTS " + uuid_sheet_table_templ);

        sql.execSQL("CREATE TABLE "  + uuid_sheet_table_templ + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,"+param14 + " TEXT," + param15 + " TEXT)");

        sql.close();
    }

    public void createTableTable(String uuid_table_table,int count_col)
    {
        SQLiteDatabase sql=this.getWritableDatabase();

        sql.execSQL("DROP TABLE IF EXISTS " + uuid_table_table);

        String str,strok="";

        for (int i=0;i<count_col;i++) {

           str=",col"+i+" TEXT";

            strok=strok+str;

        }

        Log.v("ffjj","CREATE TABLE " + uuid_table_table + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,uuidrow INTEGER,namerow TEXT,count_col TEXT"+strok+")");

        sql.execSQL("CREATE TABLE " + uuid_table_table + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,uuidrow INTEGER,namerow TEXT,count_col TEXT"+strok+")");

        sql.close();
    }

    public  void delrow(String uuid_table_table,String namerow)
    {
        SQLiteDatabase sql=this.getWritableDatabase();

        sql.delete(uuid_table_table,"namerow=?",new String[]{namerow});
    }

    public  void deleterow(String uuid_table_table,String namerow)
    {
        SQLiteDatabase sql=this.getWritableDatabase();

        sql.delete(uuid_table_table,"uuidrow=?",new String[]{namerow});
    }

    public void urow(String uuid_table_table,String uuidrow,Table t)
    {
        SQLiteDatabase sql=this.getWritableDatabase();

        ContentValues cv=new ContentValues();

        cv.put("namerow",t.getNamerow());

        for (int i=0;i<t.col.length;i++) {

            cv.put("col" + i, t.col[i].getCol());
            Log.v("eeddff",i+"  "+t.col[i].getCol());
        }

        sql.update(uuid_table_table,cv,"uuidrow=?",new String[]{uuidrow});

        sql.close();
    }

    public void urowpos(String uuid_table_table,String uuidrow,String text,int pos)
    {
        SQLiteDatabase sql=this.getWritableDatabase();

        ContentValues cv=new ContentValues();

        cv.put("namerow",text);


        cv.put("col" + pos, text);


        sql.update(uuid_table_table,cv,"uuidrow=?",new String[]{uuidrow});

        sql.close();
    }



    public void inTableTable(String uuid_table_tabe,Table t )
    {

        SQLiteDatabase sql=this.getWritableDatabase();

        ContentValues cv=new ContentValues();

        cv.put("uuidrow",t.getUuidrow());

        cv.put("namerow",t.getNamerow());

        Log.v("kkll","namerow:"+t.getNamerow());

        for (int i=0;i<t.col.length;i++) {

            cv.put("col" + i, t.col[i].getCol());

            Log.v("kkll","col"+i+":"+t.col[i].getCol());
        }


        sql.insert(uuid_table_tabe,null,cv);

        sql.close();
    }


    public ArrayList<Table> queryTableTableAll(String TableTable) {

        ArrayList<Table> table = new ArrayList<>();

        SQLiteDatabase sql = this.getWritableDatabase();

        Cursor c = sql.query(TableTable, null, null, null, null, null, null);

        int indexuuidrow = c.getColumnIndex("uuidrow");

        int indexnamerow = c.getColumnIndex("namerow");

        int count_col=c.getColumnCount();

      //  Log.v("datasdfg",count_col+"");

        int[] index=new int[count_col-4];

        for (int i=0;i<count_col-4;i++) {

            index[i] = c.getColumnIndex("col"+i);

            Log.v("datasdfg","col"+i);
        }


        if (c.getCount() != 0) {

            while (c.moveToNext()) {

                Table t = new Table(count_col-4);

                for (int i=0;i<index.length;i++) {

                    t.col[i].setCol(c.getString(index[i]));
                }



                t.setUuidrow(c.getString(indexuuidrow));

                t.setNamerow(c.getString(indexnamerow));

                table.add(t);

            }
        }

        sql.close();

        return table;
    }

    public String queryDataBaseRowTT(String uuid_table_table,String uuid_row,String col)
    {
        String retval=null;

        SQLiteDatabase sql=this.getWritableDatabase();

        Cursor c= sql.query(uuid_table_table,null,"uuidrow=?",new String[]{uuid_row},null,null,null);

        int indexcolumn= c.getColumnIndex(col);

        if (c.moveToFirst())

        {retval=c.getString(indexcolumn);}

        sql.close();


        return retval;
    }


    public void deleteTable(String uuid_sheet)
    {
        SQLiteDatabase sql=getWritableDatabase();

        for (AllSheets s:ListSheetAdapter.sheetsformove) {

            sql.execSQL("DROP TABLE IF EXISTS " + s.getUuid_sheet());

            sql.delete("mainTable", "uuid_sheet=?", new String[]{s.getUuid_sheet()});
        }

        sql.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
      //  db.execSQL("DROP TABLE IF EXISTS " + nameMainTable);

       // db.execSQL(CREATE_TABLE);

        createAlarmTable(db);
    }

    SQLiteDatabase myDataBase;

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    private void copyDataBase() throws IOException {

        File fdb=new File(getDirNbookBD().getAbsolutePath());

        FileInputStream input=new FileInputStream(fdb);

        String outFileName = DB_PATH + DB_NAME;

        OutputStream output = new FileOutputStream(outFileName);

        byte[] buffer = new byte[8192];
        int length;
        while ((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }
        output.flush();
        output.close();
        input.close();
    }

     static String DB_PATH = "/data/data/snyakovlev.unote/databases/";

    static String DB_NAME = "NBookBD.db";


    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
           Log.i("fff","ничего не делаем");
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            //файл базы данных отсутствует
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    String getDirRoot()
    {
        File root;

        if (isAvailable() || !isReadOnly()) {

            root = Environment.getExternalStorageDirectory();
        } else {
            root = context.getFilesDir();
        }

        return  root.getAbsolutePath();
    }


    File getDirNbookBD()
    {
        return new File(getDirRoot() + File.separator + "nbook" + File.separator+"NBookBD.db");
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

    public void openDataBase() throws SQLException {
        String path = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
    }

   void delCol(String uuid,int count_col,int del_col)

   {
       String strok="",str="";
       boolean perv=false;
       Log.v("qwerf",del_col+"");
       if (count_col<3)
       {
           return;
       }
       for (int i=0;i<count_col;i++) {
           if (i != del_col) {

           if (i==0)
           {
               perv=true;
               strok = "col0";
           }

               if (i>0) {
               if (perv) {
                   str = ",col" + i;

                   strok = strok + str;
               }
               else
               {
                   str = "col" + i;

                   strok = strok + str;

                   perv=true;
               }
               }
               }




       }
       Log.v("qwerf",strok);
       perv=false;
       uuid.toString();
       String g=uuid.replace("'","");
       String t=g.concat("temp");
       Log.v("qazxsw",count_col+"");
       createTableTable("'"+t+"'",count_col-1);
       SQLiteDatabase sql=getWritableDatabase();
       sql.execSQL("insert into '"+t+"'   SELECT _id,uuidrow,namerow,count_col,"+strok+" FROM "+uuid);
       sql.execSQL("DROP TABLE IF EXISTS "+uuid);
       sql.execSQL("ALTER TABLE '"+t+"'  RENAME TO "+uuid);
         sql.execSQL("DROP TABLE IF EXISTS '"+t+"'");
       sql.close();

    }

    void addCol(String uuid,String col,String name)
    {
        SQLiteDatabase sql=getWritableDatabase();
        sql.execSQL("ALTER TABLE "+uuid+" ADD COLUMN "+col+" STRING DEFAULT '"+name+"'");
    }




}