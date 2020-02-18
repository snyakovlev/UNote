package snyakovlev.unote;


import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;

import android.media.MediaFormat;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AlertDialog;

import android.util.Log;

import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAuthIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by Катя on 05.09.2018.
 */



public class GoogleDrive  {

    volatile String pa0="nbook";
    volatile String pa1="photo";
    volatile String pa2="image";
    volatile String pa3="audio";
    volatile String pa4="backup";
    volatile String pa5 = "table";
    volatile String pa6="mini";

    Glavnoe_Activity glav;
    private static final int REQ_ACCPICK = 1;
    private static final int REQ_CONNECT = 2;
    private static boolean mBusy;

    public GoogleDrive(Glavnoe_Activity glav)
    {
            this.glav=glav;


            UT.init(glav);

                try {
                    Log.v("asas","google drive create");
                    glav.startActivityForResult(AccountPicker.newChooseAccountIntent(null,
                            null, new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE}, true, null, null, null, null),
                            REQ_ACCPICK);
                }
                catch (ActivityNotFoundException e)
                {
                    Toast.makeText(glav, "SYNC ERROR!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                catch (Exception e)
                {
                    Toast.makeText(glav, "SYNC ERROR!!!", Toast.LENGTH_SHORT).show();
                    return;
                }




    }










    boolean stop=false;

    boolean search=false;

    DataBaseInterface db=new DataBaseInterface(glav);

    ProgressBar pb;

    int progres=0;

    AlertDialog alert;

    boolean err;

    public void upload(final String titl) {


        pb=new ProgressBar(glav, null, android.R.attr.progressBarStyleSmall);
        pb.setIndeterminate(false);
        pb.setMax(100);

        Log.v("qqaassdd","upload");
        if (titl != null && !mBusy) {
            //mDispTxt.setText("UPLOADING\n");
            AlertDialog.Builder builder = new AlertDialog.Builder(glav);
            builder.setTitle(glav.getResources().getString(R.string.gd1))
                    .setMessage("create backup...")
                    .setView(pb)
                    .setMessage("")
                    .setCancelable(false)
                    .setNegativeButton(glav.getResources().getString(R.string.cansel),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                   // if (err) upload(titl);
                                    stop=true;
                                    return;
                                }
                            });
             alert = builder.create();
            alert.show();
            alert.setMessage("create backup...");


            new AsyncTask<Void, String, Void>() {
                private String findOrCreateFolder(String prnt, String titl){
                    ArrayList<ContentValues> cvs = REST.search(prnt, titl, UT.MIME_FLDR);
                    String id, txt="";

                    if (cvs.size()==0)
                    {
                        Log.v("asas","no folder");
                        REST.createFolder(prnt,titl);
                        cvs = REST.search(prnt, titl, UT.MIME_FLDR);
                    }
                    else
                    {

                        REST.trash(cvs.get(0).getAsString(UT.GDID));
                        REST.createFolder(prnt,titl);
                        cvs = REST.search(prnt, titl, UT.MIME_FLDR);
                        Log.v("asas","yes folder "+cvs.size());
                    }

                        id =  cvs.get(0).getAsString(UT.GDID);

                    if (id == null) err=true;

                    return id;
                }



                @Override
                protected Void doInBackground(Void... params) {
                    mBusy = true;
                    try {
                        Zip(getDirNBook().getAbsolutePath());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String rsids = findOrCreateFolder("root", UT.MYROOT);


                    publishProgress("");
                    if (rsids != null) {


                        File fl = new File(getDirRoott() + File.separator + "backup.zip");

                        String id = null;

                        if (fl != null) {
                            id = REST.createFile(rsids, "backup.zip", "application/zip", fl);
                            if (id == null) err = true;

                        }


                        publishProgress("");
                    }



                     //   String rsid1 = findOrCreateFolder(rsids, "photo");
                     //   if (rsid1 != null) {
                      //      File fl = getDirPhoto();


                       //     String id = null;

                        //    File[] fphoto = fl.listFiles();
                        //    if (fl != null) {
                         //       int iph = 0;



                          //      for (File f : fphoto) {


                         //              id = REST.createFile(rsid1, f.getName(), UT.MIME_JPEG, fphoto[iph]);
                        //               if (id == null) err = true;
                        //               iph++;
                         //          }
                        //            }
                       //         }
                       //     }

                    //    publishProgress("");



                     //   String rsid2 = findOrCreateFolder(rsids, "image");
                     ////   if (rsid2 != null) {
                     //       File fl = getDirImage();
                            // File fl = new File(to);

                     //       String id = null;

                    //        File[] fphoto = fl.listFiles();
                      //      if (fl != null) {
                      //          int iph = 0;



                       //         for (File f : fphoto) {


                      //              id = REST.createFile(rsid2, f.getName(), UT.MIME_PNG, fphoto[iph]);
                      //              if (id == null) err = true;
                      //              iph++;
                      //          }

                   //         }
                   //     }

                   //     publishProgress("");


                   //     String rsid3 = findOrCreateFolder(rsids, "audio");
                  //      if (rsid3 != null) {
                   //         File fl = getDirAudio();


                  //          String id = null;

                   //         File[] fphoto = fl.listFiles();
                  //          if (fl != null) {
                  //              int iph = 0;



                   //             for (File f : fphoto) {


                   //                 id = REST.createFile(rsid3, f.getName(), UT.MIME_AMR, fphoto[iph]);
                   //                 if (id == null) err = true;
                   //                 iph++;
                   //             }
                 //           }

                  //      }
                   //     publishProgress("");


                  //      String rsid4 = findOrCreateFolder(rsids, "table");
                   //     if (rsid4 != null) {
                   //         File fl = getDirTable();


                   //         String id = null;

                   //         File[] fphoto = fl.listFiles();
                   //         if (fl != null) {
                   //             int iph = 0;



                    //            for (File f : fphoto) {


                    //                id = REST.createFile(rsid4, f.getName(), UT.MIME_XML, fphoto[iph]);
                    //                if (id == null) err = true;
                    //                iph++;
                     //           }
                     //       }

                     //   }

                    //    String rsid5 = findOrCreateFolder(rsids, "mini");
                    //    if (rsid4 != null) {
                    //        File fl = getDirTable();


                     //       String id = null;

                    //        File[] fphoto = fl.listFiles();
                    //        if (fl != null) {
                     //           int iph = 0;



                     //           for (File f : fphoto) {


                     //               id = REST.createFile(rsid5, f.getName(), UT.MIME_JPEG, fphoto[iph]);
                     //               if (id == null) err = true;
                     //               iph++;
                      //          }
                      //      }

                   //     }





                //    publishProgress(glav.getResources().getString(R.string.gd5));

                    mBusy = false;
                    return null;
                }




                @Override
                protected void onProgressUpdate(String... strings) { super.onProgressUpdate(strings);

                    alert.setMessage(glav.getResources().getString(R.string.sy));
                    pb.incrementProgressBy(15);
                  //  Toast.makeText(glav, strings[0], Toast.LENGTH_SHORT).show();

                }
                @Override
                protected void onPostExecute(Void nada) {
                    Log.v("qqwweerr","Done!");
                    pb.setProgress(100);
                    super.onPostExecute(nada);
                    if (err)
                    {
                        alert.setMessage(glav.getResources().getString(R.string.err));

                    }
                    else {
                        pb.setVisibility(View.GONE);
                        alert.setMessage(glav.getResources().getString(R.string.gd5));

                    }

                    mBusy = false;
                }
            }.execute();
        }
    }

     void download() {

         err=false;
         pb=new ProgressBar(glav, null, android.R.attr.progressBarStyleSmall);
         pb.setIndeterminate(false);
         pb.setMax(100);
        if (!mBusy) {
            stop=false;
         Log.v("qazasd","DOWNLOADING\n");
            AlertDialog.Builder builder = new AlertDialog.Builder(glav);
            builder.setTitle(glav.getResources().getString(R.string.gd1))
                    .setView(pb)
                    .setMessage("")
                    .setCancelable(false)
                    .setNegativeButton(glav.getResources().getString(R.string.cansel),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    if (err) download();
                                    stop=true;
                                    return;
                                }
                            });
            alert = builder.create();

            alert.show();

            new AsyncTask<Void, String, Void>() {
                String ff=null;File fg=null;
                private void iterate(ContentValues gfParent) {
                    ArrayList<ContentValues> cvs = REST.search(gfParent.getAsString(UT.GDID), null, null);

                    if (cvs != null)

                        for (ContentValues cv : cvs) {

                       // if (stop) break;

                        String gdid = cv.getAsString(UT.GDID);

                        if (gdid==null) {err=true;}

                        String titl = cv.getAsString(UT.TITL);

                        Log.v("ertyy",titl);

                       // if (REST.isFolder(cv)) {
                        //    publishProgress(titl);
                          //  if (titl.equals("mini"))  ff=getDirPhoto().getAbsolutePath();
                           // if (titl.equals("photo"))  ff=getDirPhoto().getAbsolutePath();
                          //  if (titl.equals("image")) ff=getDirImage().getAbsolutePath();
                          //  if (titl.equals("audio")) ff=getDirAudio().getAbsolutePath();
                         //   if (titl.equals("table")) ff=getDirTable().getAbsolutePath();
                        //    if (titl.equals("nbook")) ff=getDirRoott() ;
                        //    Log.v("ertyy","ff="+ff);
                        //    iterate(cv);
                      //  } else {
                            if (titl.equals("backup.zip")) ff=getDirRoott() ;
                            byte[] buf = REST.read(gdid);
                            if (buf == null) err=true;
                            try {
                                 fg=new File(ff+ File.separator + "backup.zip");
                                Log.v("erty","fg="+fg.getAbsolutePath());
                                fg.createNewFile();
                                UT.str2File(buf,fg );
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                     //   }
                    }

                    if (!err) {
                        try {
                            unzip(fg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                protected Void doInBackground(Void... params) {
                    mBusy = true;
                    ArrayList<ContentValues> gfMyRoot = REST.search("root", UT.MYROOT, null);
                    if (gfMyRoot != null && gfMyRoot.size() == 1 ){
                        publishProgress(gfMyRoot.get(0).getAsString(UT.TITL));
                        Log.v("ertyy",gfMyRoot.get(0).getAsString(UT.TITL));
                        iterate(gfMyRoot.get(0));

                    }
                    return null;
                }

                @Override
                protected void onProgressUpdate(String... strings) {
                    super.onProgressUpdate(strings);

                    alert.setMessage(glav.getResources().getString(R.string.sy));
                    pb.incrementProgressBy(15);
                }

                @Override
                protected void onPostExecute(Void nada) {
                    super.onPostExecute(nada);
                    pb.setProgress(100);
                    if (err)
                    {
                        alert.setMessage(glav.getResources().getString(R.string.err));

                    }
                    else {
                        pb.setVisibility(View.GONE);
                        alert.setMessage(glav.getResources().getString(R.string.gd5));

                    }
                    alert.cancel();
                    mBusy = false;
                    glav.toDB();
                }
            }.execute();
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




    File getDirNbookBD()
    {
        DataBaseInterface db=new DataBaseInterface(glav);
        return db.dbPath;
    }

    File getDirPhoto()
    {
        return new File(getDirRoot() + File.separator + pa0+File.separator+pa1 );
    }

    File getDirBackup()
    {
        return new File(getDirRoot() + File.separator + pa0+File.separator+pa4+File.separator+"backup.zip" );
    }

    File getDirBackup0()
    {
        return new File(getDirRoot() + File.separator + pa0+File.separator+pa4);
    }

    File getDirImage()
    {
        return new File(getDirRoot() + File.separator + pa0+File.separator+pa2);
    }

    File getDirAudio()
    {
        return new File(getDirRoot() + File.separator + pa0+File.separator+pa3);
    }

    File getDirTable()
    {
        return new File(getDirRoot() + File.separator + pa0+File.separator+pa5);
    }

    File getDirNBook(){return new File(getDirRoot() + File.separator + pa0);}

    private void deleteTree() {
        if (!mBusy) {
          //  mDispTxt.setText("DELETING\n");
            new AsyncTask<Void, String, Void>() {

                private void iterate(ContentValues gfParent) {
                    ArrayList<ContentValues> cvs = REST.search(gfParent.getAsString(UT.GDID), null, null);
                    if (cvs != null) for (ContentValues cv : cvs) {
                        String titl = cv.getAsString(UT.TITL);
                        String gdid = cv.getAsString(UT.GDID);
                        if (REST.isFolder(cv))
                            iterate(cv);
                        publishProgress("  " + titl + (REST.trash(gdid) ? " OK" : " FAIL"));
                    }
                }

                @Override
                protected Void doInBackground(Void... params) {
                    mBusy = true;
                    ArrayList<ContentValues> gfMyRoot = REST.search("root", UT.MYROOT, null);
                    if (gfMyRoot != null && gfMyRoot.size() == 1 ){
                        ContentValues cv = gfMyRoot.get(0);
                        iterate(cv);
                        String titl = cv.getAsString(UT.TITL);
                        String gdid = cv.getAsString(UT.GDID);
                        publishProgress("  " + titl + (REST.trash(gdid) ? " OK" : " FAIL"));
                    }
                    return null;
                }

                @Override
                protected void onProgressUpdate(String... strings) {
                    super.onProgressUpdate(strings);
                  //  mDispTxt.append("\n" + strings[0]);
                }

                @Override
                protected void onPostExecute(Void nada) {
                    super.onPostExecute(nada);
                 //   mDispTxt.append("\n\nDONE");
                    mBusy = false;
                }
            }.execute();
        }
    }

    String getDirRoott()

    {
        File root=null;

        if (isAvailable() || !isReadOnly()) {

            root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        }
        return root.getAbsolutePath();
    }

    public  void unzip(File zipFile) throws IOException {

        File targetDirectory=new File(getDirRoot());
        ZipInputStream zis = new ZipInputStream(
                new BufferedInputStream(new FileInputStream(zipFile)));
        try {
            ZipEntry ze;
            int count;
            byte[] buffer = new byte[8192];
            while ((ze = zis.getNextEntry()) != null) {
                File file = new File(targetDirectory, ze.getName());
                File dir = ze.isDirectory() ? file : file.getParentFile();
                if (!dir.isDirectory() && !dir.mkdirs())
                    throw new FileNotFoundException("Failed to ensure directory: " +
                            dir.getAbsolutePath());
                if (ze.isDirectory())
                    continue;
                FileOutputStream fout = new FileOutputStream(file);
                try {
                    while ((count = zis.read(buffer)) != -1)
                        fout.write(buffer, 0, count);
                } finally {
                    fout.close();
                }
            /* if time should be restored as well
            long time = ze.getTime();
            if (time > 0)
                file.setLastModified(time);
            */
            }
        } finally {
            zis.close();
        }
    }



    private void Zip(String source_dir) throws Exception
    {
        String zip_file=getDirRoott()+File.separator+"backup.zip" ;
        // Cоздание объекта ZipOutputStream из FileOutputStream
        FileOutputStream fout = new FileOutputStream(zip_file);
        ZipOutputStream zout = new ZipOutputStream(fout);
        // Определение кодировки

        // Создание объекта File object архивируемой директории
        File fileSource = new File(source_dir);

        Log.v("aqwe",fileSource.getPath());


        addDirectory(zout, fileSource);

        // Закрываем ZipOutputStream
        zout.close();

       Log.v("asas","Zip файл создан!");
    }
    private void addDirectory(ZipOutputStream zout, File fileSource)
            throws Exception
    {
        File[] files = fileSource.listFiles();
        Log.v("asas","Добавление директории <" + fileSource.getName() + ">");
        for(int i = 0; i < files.length; i++) {
            // Если file является директорией, то рекурсивно вызываем
            // метод addDirectory
            if(files[i].isDirectory()) {
                addDirectory(zout, files[i]);
                continue;
            }
            Log.v("asas","Добавление файла <" + files[i].getName() + ">");

            FileInputStream fis = new FileInputStream(files[i]);

            zout.putNextEntry(new ZipEntry(files[i].getPath().substring(
                    getDirNBook().getPath().lastIndexOf("/") + 1)));

            byte[] buffer = new byte[4048];
            int length;
            while((length = fis.read(buffer)) > 0)
                zout.write(buffer, 0, length);
            // Закрываем ZipOutputStream и InputStream
            zout.closeEntry();
            fis.close();
        }
    }
}
