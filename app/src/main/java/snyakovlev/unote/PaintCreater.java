package snyakovlev.unote;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Катя on 28.09.2016.
 */
public class PaintCreater extends Fragment  {

    private static final int FADE_MSG = 1;


    private static final int CLEAR_ID = Menu.FIRST;

    private static final int FADE_ID = Menu.FIRST+1;


    private static final int FADE_DELAY = 100;

    boolean mFading;

    Bitmap img = null;

    Glavnoe_Activity glav;

    CurrSave currsave;

    String filename = null;

    int color;

    ScratchOutView appv;

    boolean photo;



    public static PaintCreater newInstance(String filename, boolean photo) {


        PaintCreater pv = new PaintCreater();

        Bundle args = new Bundle();

        args.putBoolean("photo",photo);

        args.putString("filename", filename);

        pv.setArguments(args);

        return pv;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putString("fi", currsave.getFilename());
        outState.putBoolean("photo",photo);
        outState.putString("old",oldfilename);
        outState.putBoolean("setl",setl);
        outState.putBoolean("al",appv.l);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        filename = getArguments().getString("filename");

        photo=getArguments().getBoolean("photo");

        setHasOptionsMenu(true);

        setRetainInstance(true);


    }

    Menu menu;
    static float density;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main4, menu);
        this.menu = menu;


    }

    String fi="";

    @Override
    public void onPause() {

            photo=false;

            try {
                createFileImage(filename,true);
            } catch (IOException e) {
                e.printStackTrace();
            }


        super.onPause();
    }

    boolean oldl;


    void clear(int mode)
    {
        Display currentDisplay = getActivity().getWindowManager().getDefaultDisplay();


        if (mode==1) {
            oldl=appv.l;
            appv.l=false ;
            appv.rt=true;
        }

        if (mode==2)
        {
           if (appv.image!=null)
           {appv.f.drawBitmap(appv.src,0,0,null);}
           else {
               appv.f.drawColor(colorback);
           }
        }

    }

    boolean setl;
    boolean sett;

    int ts=30;
    String text="";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();




        if (id==R.id.setl)
        {
            if (setl)
            {
                item.setTitle(getResources().getString(R.string.pc1));
                appv.l=false;
                oldl=false;
                setl=false;
            }
            else
            {
                item.setTitle(getResources().getString(R.string.pc2));
                appv.l=true;
                oldl=true;
                setl=true;
            }

        }

        if (id==R.id.clear)
        {
            clear(1);
        }

        if (id==R.id.clear2)
        {
            clear(2);
        }

        if (id==R.id.setcolor)
        {
            appv.l=oldl;
            dialogColor();
        }

        if (id==R.id.setcolorback)
        {
            dialog1();
        }




        if (id==R.id.share_image)
        {
            CreateUID cruid = new CreateUID(getActivity());

            String filenameimage = cruid.creatingUID();

            try {
                createFileImage(filenameimage,true);
            } catch (IOException e) {
                e.printStackTrace();


            }
            glav.share(currsave.getFilenameimage(),"image",null);

        }

        if (id == R.id.create_pic) {


            try {
                createFileImage(filename,true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            back();
        }

        if (id==R.id.full)
        {
            fullscreen();
        }

        return true;
    }

File outFile=null;







    void openGallery() throws IOException {

        glav.paintfile=filename;

        Intent tpIntent = new Intent(Intent.ACTION_PICK);

        tpIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

        tpIntent.setType("image/*");

        getActivity().startActivityForResult(tpIntent, 230);
    }





    void dialogColor()
    {
        new AmbilWarnaDialog(getActivity(),color, true,new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int col) {

                appv.paint.setColor(col);
                color=col;
                appv.rt=false;

            }


        }).show();
    }

    int colorback=Color.WHITE;

    void dialogColorBack()
    {
        new AmbilWarnaDialog(getActivity(),colorback, true,new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int col) {
                appv.f.drawColor(col);
                try {
                    createFileImage(filename,true);
                } catch (IOException e) {
                    e.printStackTrace();

                }
                    appv.rt=false;
                    appv.invalidate();

            }


        }).show();
    }


    void createPic()
    {
        CreateUID cruid = new CreateUID(getActivity());

        String filenameimage = cruid.creatingUID();


        if (!fi.equals("")) filenameimage=fi;

        try {
            createFileImage(filenameimage,true);
        } catch (IOException e) {
            e.printStackTrace();

        }

        filename=filenameimage;



        appv.filename=filename;

    }



    void createFileImage(String filename, boolean create) throws IOException {
        File outFile;

        File root;

        if (isAvailable() || !isReadOnly()) {

            root = Environment.getExternalStorageDirectory();
        } else {
            root = getActivity().getFilesDir();
        }

        String root_str = root.getAbsolutePath();

        String my_dir = root_str + File.separator + "nbook" + File.separator + "image";

        outFile = new File(my_dir, filename + ".png");


        this.filename = filename;

        Log.v("jjjj","xzxz "+filename);

        glav.currsave.setFilenameimage(filename);

        glav.currsave.updateDataBase();

        outFile.getParentFile().mkdirs();

        this.outFile=outFile;

        Picasso.with(getActivity()).invalidate(outFile);

        if (outFile.exists())
        {
            outFile.delete();
        }

        outFile.createNewFile();


            try {
                FileOutputStream fos = new FileOutputStream(outFile);

                appv.image.compress(Bitmap.CompressFormat.PNG, 85, fos);

                fos.flush();

                fos.close();
            } catch (Exception e) {

            }



    }

 boolean fulls;
    String oldfilename="";
     int vv=0,hh=0;
    LinearLayout layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       View root = inflater.inflate(R.layout.paint_creator, container, false);


        glav = (Glavnoe_Activity) getActivity();

        glav.drawerlay.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        glav.setTitle(getString(R.string.nnn));

        currsave = glav.currsave;



        if (savedInstanceState==null) {

            fi=currsave.getFilenameimage();
        }
        else
        {
            setl=savedInstanceState.getBoolean("setl",false);

            appv.l=savedInstanceState.getBoolean("al",false);

            fi=savedInstanceState.getString("fi");

            filename=fi;
        }

        color=Color.BLACK;

        ScratchOutView.filename=filename;

        ScratchOutView.filename2=filename;

        ScratchOutView.photo=photo;

        appv=(ScratchOutView)root.findViewById(R.id.scretch);

        appv.filename=filename;

        appv.rt=false;

        ViewTreeObserver vto=appv.getViewTreeObserver();

        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                if (photo) oldfilename=filename;

                if (filename.equals("") )

                {createPic();}

                else if (!filename.equals("") && photo)

                {createPic();}

                    appv.getViewTreeObserver().removeOnPreDrawListener(this);

                    appv.photo=photo;

                    appv.col=colorback;



                    appv.filename=oldfilename;

                    appv.inv();

                    try {
                    createFileImage(filename,true);
                } catch (IOException e) {
                    e.printStackTrace();

                }
                appv.invalidate();

                 return false;
            }
        });


        img = appv.image;

        return  root;

    }

    public  void dialog1()
    {
        new AlertDialog.Builder(getActivity())
                .setTitle(getResources().getString(R.string.pc3))
                .setPositiveButton(getResources().getString(R.string.pc4), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogColorBack();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.pc5), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            openGallery();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .show();
    }

 public void fullscreen()
 {
     menu.getItem(4).setIcon(R.drawable.text);

     appv.rt = false;
     sett = false;

     fulls=true;
     appv.fulls=true;

     glav.getSupportActionBar().hide();

     FragmentManager fm= getActivity().getSupportFragmentManager();

     Fragment ff1=fm.findFragmentById(R.id.footer);

     if (ff1!=null) {

         fm.beginTransaction()

                 .detach(ff1)
                 .commit();
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

  public  void back()
    {


        if (fulls)
        {
            glav.getSupportActionBar().show();

            FragmentManager fm= getActivity().getSupportFragmentManager();

                fm.beginTransaction()

                        .add(R.id.footer,new FragmentFooterMenu1())

                        .commit();

               fulls=false;

        }

        else {

            glav.drawerlay.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

            try {
                createFileImage(filename,true);
            } catch (IOException e) {
                e.printStackTrace();
            }

            glav.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);

            FragmentManager fm = getActivity().getSupportFragmentManager();

            Fragment ff1 =  fm.findFragmentById(R.id.footer);

            if (ff1 != null) {

                fm.beginTransaction()

                        .remove(ff1)

                        .commit();
            }

            WorkList wl = WorkList.newInstance();

            getActivity().getSupportFragmentManager().beginTransaction()

                    .replace(R.id.header, wl)

                    .commit();

        }


    }



}
