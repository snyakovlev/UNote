package snyakovlev.unote;


import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DLG#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DLG extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    Glavnoe_Activity glav;
    WorkAdapterDlg adapter;
    ArrayList<File> files;
public static ArrayList<File> save_for_move=new ArrayList<>();
    double koa;
    int hp;
    int wp;
    int t=0;
    int reglay=0;
    DataBaseInterface db;
    AllSheets allsheets;
    String uuidcurrsheet;



    // TODO: Rename and change types of parameters

    RecyclerView list;


    public DLG() {
        // Required empty public constructor
    }


    public static DLG newInstance() {
        DLG fragment = new DLG();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.worklistdlg, container, false);

        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());

        allsheets = ((Glavnoe_Activity) getActivity()).allSheets;

        uuidcurrsheet = allsheets.getUuid_sheet();

         files=listFilesWithSubFolders(dir);

         Log.v("qwer",files.size()+"");

         db=new DataBaseInterface(getActivity());

        db.getWritableDatabase();

        list = (RecyclerView) root.findViewById(R.id.worklistlistdlg);

        list.setItemViewCacheSize(0);

        list.setDrawingCacheEnabled(true);

        glav=(Glavnoe_Activity)getActivity();

        adapter = new WorkAdapterDlg(glav, files,this,false);

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



            LinearLayoutManager lm = new LinearLayoutManager(getActivity());
            list.setLayoutManager(lm);


        list.setAdapter(adapter);

        return root;
    }

    ArrayList<String> as=new ArrayList<>();
    ArrayList<String> us=new ArrayList<>();
    int wh;

    void move()
    {
        if (save_for_move.size()!=0) {
            as.clear();
            us.clear();

            for (AllSheets sht : db.queryMainTableAll2(db.nameMainTable, db.tab_param1, db.tab_param2, db.tab_param3, db.tab_param5, db.tab_param6, glav)) {
                Log.d("tty", "namets: " + sht.getNametablesheet() + " " + sht.getUuid_sheet());

                if (!sht.getUuid_sheet().equals("dlg0") && !sht.getUuid_sheet().equals("rec0")) {
                    as.add(sht.getNametablesheet());
                    us.add(sht.getUuid_sheet());


                }
            }
                String[] shs = new String[as.size()];
                int inn = 0;
                for (String s : as) {

                    shs[inn] = s;
                    inn++;
                }

                if (shs.length == 0) {
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
                } else if (shs.length != 0) {

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


            }else
            {
                Toast.makeText(glav, getResources().getString(R.string.wl10), Toast.LENGTH_SHORT).show();
            }

    }

    void move2()
    {

        for (File csformove:save_for_move) {

            String filename=new CreateUID(getActivity()).creatingUID();

            copyFile(csformove,filename);

            ContentValues cv = new ContentValues();

            cv.put("uuid_save", new CreateUID(getActivity()).creatingUID());

            if (WorkAdapterDlg.getFileExtension(csformove).equals("xls") || (WorkAdapterDlg.getFileExtension(csformove).equals("xlms"))) {

                cv.put("name_table_table",csformove.getName());

                String ntt=new CreateUID(getActivity()).creatingUID();

                cv.put("uuid_table_table",ntt);


                ExcelleAdapter ea=new ExcelleAdapter();

                ArrayList<Table> at=ea.Excelle2Table(glav,csformove.getAbsolutePath());

                db.createTableTable(ntt,ea.max_count_cells);

                for (int i=0;i<at.size();i++)
                {
                    Log.v("qaswe","col="+at.get(i).col.length+"");
                    db.inTableTable(ntt,at.get(i));
                }

            }
            else {

                cv.put("name_table_table", "");

                cv.put("uuid_table_table", "");
            }

         if (WorkAdapterDlg.getFileExtension(csformove).equals("jpg") || (WorkAdapterDlg.getFileExtension(csformove).equals("pdf"))) {
             cv.put("filenamephoto1", filename);
         }
         else {
             cv.put("filenamephoto1", "");
         }

            cv.put("filenamephoto2", "");

            cv.put("filenamephoto3", "");

            if (WorkAdapterDlg.getFileExtension(csformove).equals("png") ) {
                cv.put("filenameimage", filename);
            }
            else
            {
                cv.put("filenameimage", "");
            }

            cv.put("filename", "");

            if (WorkAdapterDlg.getFileExtension(csformove).equals("txt")||WorkAdapterDlg.getFileExtension(csformove).equals("xml")||WorkAdapterDlg.getFileExtension(csformove).equals("html")) {

                StringBuilder text = new StringBuilder();

                  try {
                     BufferedReader br = new BufferedReader(new FileReader(csformove),50);
                    br.mark(5);
                  String line;
                    int i=0;
                  while ((line = br.readLine()) != null && i<5) {
                       text.append(line);
                      text.append('\n');
                      i++;
                  }



                   br.close();

                 } catch (IOException e) {

                  }
                cv.put("value", text.toString());
            }
            else {
                cv.put("value", "");
            }

            cv.put("text", "");

            cv.put("datetime","");

            db.insertDataBase(us.get(wh), cv);

           // db.delSave(uuidcurrsheet, csformove.getUuid_save());
        }

        save_for_move.clear();

    }

    public static void copy(File src, File dst) throws IOException {

        Log.v("helperrr","copy");

        Log.v("helperrr" , src.length()+"");

        InputStream in = new FileInputStream(src);
        try {
            OutputStream out = new FileOutputStream(dst);
            try {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();

                Log.v("helperr",e.toString());
            }

            finally {
                out.close();
            }
        } finally {
            in.close();
        }



        Log.v("helperrr" , dst.length()+"");
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


    String getDirRoot()
    {
        File root;

        if (isAvailable() || !isReadOnly()) {

            root = Environment.getExternalStorageDirectory();

        }
        else
        {
            root = glav.getFilesDir();
        }

        return  root.getAbsolutePath();
    }

     String pa0="nbook";
     String pa1="photo";
     String pa2="image";

    File getDirPhoto()
    {
        return new File(getDirRoot() + File.separator + pa0+File.separator+pa1 );
    }


    File getDirImage()
    {
        return new File(getDirRoot() + File.separator + pa0+File.separator+pa2);
    }


    void copyFile(File file,String filename)
    {
        if(WorkAdapterDlg.getFileExtension(file).equals("png") )
        {

           String ff=getDirImage().getAbsolutePath();
            File fg=null;

               fg = new File(ff + File.separator + filename + ".png");



            try {
                fg.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                copy(file,fg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(WorkAdapterDlg.getFileExtension(file).equals("jpg")|| WorkAdapterDlg.getFileExtension(file).equals("pdf"))
        {
            String ff=getDirPhoto().getAbsolutePath();
            File fg=null;
            if(WorkAdapterDlg.getFileExtension(file).equals("jpg")) {
                fg = new File(ff + File.separator + filename + ".jpg");
            }

            if(WorkAdapterDlg.getFileExtension(file).equals("pdf")) {
                fg = new File(ff + File.separator + filename + ".pdf");
            }
            try {
                fg.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                copy(file,fg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    Menu menu1;

   MenuInflater minfl;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.


        menu1=menu;

        minfl=inflater;

        inflater.inflate(R.menu.menu_main2dlg, menu);






    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();





        if (id == R.id.wlmovedlg) {

            move();

            return false;
        }


        if (id==R.id.doc)
        {
            File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());

            files=listFilesWithSubFoldersDoc(dir);

            notifList(true);
        }

        if (id==R.id.ima)
        {
            File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());

            files=listFilesWithSubFoldersImg(dir);

            notifList(true);
        }

        if (id==R.id.all)
        {
            File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());

            files=listFilesWithSubFolders(dir);

            notifList(true);
        }


        return true;
    }


    public ArrayList<File> listFilesWithSubFolders(File dir) {
        ArrayList<File> files = new ArrayList<File>();
        for (File file : dir.listFiles()) {
            if (!file.isDirectory())
                files.add(file);
        }
        return files;
    }


    public ArrayList<File> listFilesWithSubFoldersDoc(File dir) {
        ArrayList<File> files = new ArrayList<File>();
        for (File file : dir.listFiles()) {
            if (!file.isDirectory())
            {

                if (WorkAdapterDlg.getFileExtension(file).equals("pdf") ||
                        WorkAdapterDlg.getFileExtension(file).equals("xls") ||
                        WorkAdapterDlg.getFileExtension(file).equals("txt") ||
            WorkAdapterDlg.getFileExtension(file).equals("xml") ||
                    WorkAdapterDlg.getFileExtension(file).equals("html")) {
                    files.add(file);
                }
                }
        }
        return files;
    }

    public ArrayList<File> listFilesWithSubFoldersImg(File dir) {
        ArrayList<File> files = new ArrayList<File>();
        for (File file : dir.listFiles()) {
            if (!file.isDirectory()) {
                if (WorkAdapterDlg.getFileExtension(file).equals("jpg") ||
                        WorkAdapterDlg.getFileExtension(file).equals("png")) {
                    files.add(file);
                }
            }
        }
        return files;
    }

    void notifList(boolean izm)
    {

        adapter.values=files;

        if (izm)  adapter.checs=new boolean[files.size()];

        list.getAdapter().notifyDataSetChanged();
    }

}
