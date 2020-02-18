package snyakovlev.unote;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class PhotoCreater {

    String photofile;

    final int REQUEST=120;

    CurrSave currsave;

  Glavnoe_Activity activ;

    public PhotoCreater(Glavnoe_Activity activ,CurrSave currsave)
    {
        this.activ=activ;


        this.currsave=currsave;

    }




 public   void createPhoto(int index,boolean create)
    {
        CreateUID cruid=new CreateUID(activ);

        String  filenamephoto = cruid.creatingUID();

        this.currsave.setFilenamephoto1(filenamephoto);

        try {
            createFilePhoto(filenamephoto,index,create);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }




    void createFilePhoto(String filename, int index, boolean create) throws IOException {

        String sss="";

        String ss="";

        switch (index)
        {
            case 0:ss=currsave.getFilenamephoto1();
                break;
            case 1:ss=currsave.getFilenamephoto2();
                break;
            case 2:ss=currsave.getFilenamephoto3();
                break;
        }

        if (ss==null) { ss=sss;}

            if (ss.equals("") || ss.equals("pusto"))

            {
                File outFile;

                File root;

                if (isAvailable() || !isReadOnly()) {

                    root = Environment.getExternalStorageDirectory();
                } else {
                    root = activ.getFilesDir();
                }

                String root_str = root.getAbsolutePath();

                String my_dir = root_str + File.separator + "nbook" + File.separator + "photo";

                outFile = new File(my_dir, filename + ".jpg");

                outFile.getParentFile().mkdirs();

                outFile.createNewFile();

                Uri urifile=null;

                Log.v("qqaass",outFile.getAbsolutePath());

                if (Build.VERSION.SDK_INT>=24)
                {
                    urifile= FileProvider.getUriForFile(activ,activ.getApplicationContext().getPackageName()+".provider",outFile);
                }

                else
                {
                    urifile=Uri.fromFile(outFile);
                }

               if (urifile!=null)
               {
                   this.photofile = filename;

                   dispatchTPI(outFile,urifile, create);
               }
               else{return;}



            }

            else


            {
                filename = ss;

                File outFile;

                File root;

                if (isAvailable() || !isReadOnly()) {

                    root = Environment.getExternalStorageDirectory();
                } else {
                    root = activ.getFilesDir();
                }

                String root_str = root.getAbsolutePath();

                String my_dir = root_str + File.separator + "nbook" + File.separator + "photo";

                outFile = new File(my_dir, filename + ".jpg");

                outFile.getParentFile().mkdirs();

                outFile.createNewFile();

                Uri urifile = null;

                if (Build.VERSION.SDK_INT >= 24) {
                    urifile = FileProvider.getUriForFile(activ, activ.getApplicationContext().getPackageName() + ".provider", outFile);
                } else {
                    urifile = Uri.fromFile(outFile);
                }

                if (urifile != null) {

                    this.photofile = filename;
                    dispatchTPI(outFile, urifile, create);
                } else {
                    return;
                }


            }


    }



    public  void dispatchTPI(File f,Uri outfile,boolean create)
    {
        activ.phfile=f;

        Log.v("fgbv",activ.phfile.getAbsolutePath());

        if (create) {

            Intent tpIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (tpIntent.resolveActivity(activ.getPackageManager()) != null) {

                if (outfile != null) {

                    tpIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    tpIntent.putExtra(MediaStore.EXTRA_OUTPUT, outfile);

                    activ.startActivityForResult(tpIntent, REQUEST);
                }


            }
        }

        else
        {


            Intent tpIntent = new Intent(Intent.ACTION_PICK);

            tpIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

            tpIntent.setType("image/*");

            activ.startActivityForResult(tpIntent, 220);


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



}
