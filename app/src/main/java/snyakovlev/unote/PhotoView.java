package snyakovlev.unote;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;


public class PhotoView extends android.support.v4.app.Fragment implements View.OnTouchListener {

    int w,h;

    DataBaseInterface db;


    Glavnoe_Activity glav;

    CurrSave currsave;

    AllSheets allsheets;

    static  boolean _quick;

   TextView comm;

    ViewFlipper flip;

     boolean   photo=false;




    public static PhotoView newInstance(boolean quick) {

        _quick=quick;

        PhotoView pv = new PhotoView();

        return pv;
    }

    ArrayList initSheets()
    {
        ArrayList<AllSheets> allsheetss;



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

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        setHasOptionsMenu(true);






    }


    float fromPosition = 0;

    ImageView zoomi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View ll = getActivity().getLayoutInflater().inflate(R.layout.photo_view, null);

        zoomi=(ImageView)ll.findViewById(R.id.zoom);

        zoomi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (zoom)
                {
                    zoomi.setImageDrawable(getResources().getDrawable(R.drawable.zoom));
                    zoom=false;
                }

                else
                {
                    zoomi.setImageDrawable(getResources().getDrawable(R.drawable.flip));
                    zoom=true;
                }

            }
        });

        flip=(ViewFlipper)ll.findViewById(R.id.flip);

        flip.setDrawingCacheEnabled(true);

        flip.setOnTouchListener(this);



        glav = (Glavnoe_Activity) getActivity();

        db=new DataBaseInterface(getActivity().getApplicationContext());

        comm=(TextView)ll.findViewById(R.id.comm);






        allsheets = ((Glavnoe_Activity) getActivity()).allSheets;

        currsave=((Glavnoe_Activity) getActivity()).currsave;

        initList(getActivity());

        cs=currsave;

  comm.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              final EditText et = new EditText(getActivity());
              et.setText(cs.getValue());
              LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
              lp.setMargins(16, 16, 16, 16);
              et.setLayoutParams(lp);
              LinearLayout ll = new LinearLayout(getActivity());
              ll.setGravity(Gravity.CENTER);
              et.setEms(15);
              ll.addView(et);
              AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
              ab.setView(ll)
                      .setTitle(getActivity().getResources().getString(R.string.wa2))
                      .setPositiveButton(getActivity().getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {

                              editDB(et.getText().toString());
                              comm.setText(et.getText().toString());
                              updCom();

                          }
                      })
                      .show();
          }

          });
        ListSheetAdapter2 adapter2 = new ListSheetAdapter2(getActivity(), initSheets());

        glav.lw.setAdapter(adapter2);


       ll.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        glav = (Glavnoe_Activity) getActivity();

        Display currentDisplay = getActivity().getWindowManager().getDefaultDisplay();

        h = currentDisplay.getHeight();

        w = currentDisplay.getWidth();

        init(glav);




        if (_quick){createPhoto(true);_quick=false;}

        return ll;
    }


    CurrSave cs;

        void editDB(String str)
        {

            initList(getActivity());
            cs.setValue(str);
            cs.updateDataBase();

        }

    int indexphoto;



    ArrayList<String> cms=new ArrayList<>();
    ArrayList<String> cph=new ArrayList<>();

    int currpos=0;

    String currphoto="";

    boolean v;

    boolean zoom=false;


    void updCom()
    {
        cs=currsave;

        cms.clear();

        final int  countt=db.getCountDataBaseAll(allsheets.getUuid_sheet());

        for (int i=countt-1;i>-1;i--) {

            final String fn = db.queryDataBaseAll(allsheets.getUuid_sheet(), "filenamephoto1").get(i);

            if (fn != null && !fn.equals("") && !fn.equals("pusto")) {


                String value = db.queryDataBaseAll(allsheets.getUuid_sheet(), "value").get(i);
                cms.add(value);
            }
        }

    }

    ArrayList<CurrSave> currsavearray =new ArrayList<>();

    void initList(Context ctx) {

        glav.permStorage();

        glav.setTitle(allsheets.getNametablesheet());


      int  _count=db.getCountDataBaseAll(allsheets.getUuid_sheet());


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



    }


    void initFlip()
    {
        flip.removeAllViews();
        flip.invalidate();

      final int  count=db.getCountDataBaseAll(allsheets.getUuid_sheet());

        File outFile1;

        File root;

        if (isAvailable() || !isReadOnly()) {

        root = Environment.getExternalStorageDirectory();
    } else {
        root = getActivity().getFilesDir();
    }

        String root_str = root.getAbsolutePath();

        String my_dir = null;

        my_dir = root_str + File.separator + "nbook" + File.separator + "photo";



            cms.clear();
            cph.clear();

            for (int i=count-1;i>-1;i--) {

                final String fn = db.queryDataBaseAll(allsheets.getUuid_sheet(), "filenamephoto1").get(i);

                if (fn != null && !fn.equals("") && !fn.equals("pusto")) {


                    String value = db.queryDataBaseAll(allsheets.getUuid_sheet(), "value").get(i);
                    cms.add(value);
                    cph.add(fn);
                   final ImageViewTouch im = new ImageViewTouch(getActivity(), null);

                    im.setSingleTapListener(new ImageViewTouch.OnImageViewTouchSingleTapListener() {
                        @Override
                        public void onSingleTapConfirmed() {



                        }
                    });

                    im.setDoubleTapListener(new ImageViewTouch.OnImageViewTouchDoubleTapListener() {
                        @Override
                        public void onDoubleTap() {
                            if (zoom==false) {
                                zoom = true;
                                zoomi.setImageDrawable(getResources().getDrawable(R.drawable.flip));
                            }else {
                                zoom = false;
                                zoomi.setImageDrawable(getResources().getDrawable(R.drawable.zoom));
                            }
                        }
                    });


                    im.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN: // Пользователь нажал на экран, т.е. начало движения
                                    // fromPosition - координата по оси X начала выполнения операции
                                    fromPosition = event.getX();
                                    break;
                                case MotionEvent.ACTION_UP: // Пользователь отпустил экран, т.е. окончание движения
                                    float toPosition = event.getX();
                                    if (fromPosition > toPosition + 40 && !zoom) {
                                        flip.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.go_next_in));
                                        flip.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.go_next_out));
                                        flip.showNext();
                                        currpos++;

                                        if (currpos > cms.size() - 1 ) {
                                            currpos = 0;
                                        }

                                        comm.setText(cms.get(currpos));
                                        currphoto = cph.get(currpos);
                                        cs=currsavearray.get(currpos);
                                        Log.v("aazzssxx", currpos + "");


                                    } else if (fromPosition < toPosition - 40 && !zoom) {
                                        flip.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.go_prev_in));
                                        flip.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.go_prev_out));
                                        flip.showPrevious();
                                        currpos--;

                                        if (currpos < 0) {
                                            currpos = cms.size() - 1;
                                        }
                                        Log.v("aazzssxx", currpos + "");
                                        comm.setText(cms.get(currpos));
                                        currphoto = cph.get(currpos);
                                        cs=currsavearray.get(currpos);
                                    } else return false;


                                default:
                                    break;
                            }
                            return false;
                        }
                    });

                    im.setDisplayType(ImageViewTouchBase.DisplayType.FIT_IF_BIGGER);
                    outFile1 = new File(my_dir, fn + ".jpg");
                    glav.picasso.load(outFile1).fit().centerInside().into(im);
                    glav.picasso.invalidate(outFile1);
                    im.invalidate();
                    flip.addView(im);
                }

                else
                {
                    v=true;
                }



            }
            currpos=0;
            int j=0;

                for (int i = 0; i < count; i++) {
                     String fn = db.queryDataBaseAll(allsheets.getUuid_sheet(), "filenamephoto1").get((count - 1) - i);

                    if (fn != null && !fn.equals("") && !fn.equals("pusto")) {

                        if (fn.equals(currsave.getFilenamephoto1())) {

                        currpos = j;

                        cs=currsavearray.get(currpos);


                        comm.setText(cms.get(currpos));

                        currphoto=cph.get(currpos);

                            glav.picasso.invalidate(currphoto);

                        break;
                    }
                    flip.showNext();
                        j++;
                }
            }

    }


    void init(Glavnoe_Activity glav) {

        this.glav = glav;

        currsave = glav.currsave;

        glav.setTitle(glav.allSheets.getNametablesheet());

        this.glav.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);

        initFlip();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main5, menu);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id==R.id.openphoto)
        {
            openphoto();
        }

        if (id==R.id.ph_paint)
        {
            ICreaterPaint ipv = (ICreaterPaint) getActivity();

            ipv.showPaintCreator(currphoto,true);
        }


        if (id == R.id.savephoto) {


            save();
        }

        if (id==R.id.createphoto)
        {
            glav.permCamera();
            createPhoto(true);
        }

        if (id==R.id.share_photo)
        {
            glav.permStorage();
            glav.share(currphoto,"photo",null);
        }

        return true;
    }

    boolean s=false;


    void save()
    {
        Glavnoe_Activity glav=(Glavnoe_Activity)getActivity();

        getActivity().setTitle(glav.allSheets.getNametablesheet());

        IShowFragmentWL isfwl=(IShowFragmentWL)getActivity();

        isfwl.showFragmentWL();
    }

 void   openphoto()
 {
     glav.permCamera();
     createPhoto(false);

 }


    int photo_ind = 0;

    String[] photo_array = new String[]{"", "", ""};



    private static boolean isReadOnly() {
        String storageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(storageState);
    }

    // проверяем есть ли доступ к внешнему хранилищу
    private static boolean isAvailable() {
        String storageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(storageState);
    }


    void createPhoto(boolean create) {

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            glav.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            glav.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        photo_ind=indexphoto;

        PhotoCreater ph = new PhotoCreater(glav,currsave);

        ph.createPhoto(photo_ind,create);

        photo_array[photo_ind] = ph.photofile;



        currsave.setFilenamephoto1(photo_array[0]);


        currsave.updateDataBase();

    }


    static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Начальная высота и ширина изображения
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        Log.v("hh",reqHeight+" "+reqWidth);
        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Рассчитываем наибольшее значение inSampleSize, которое равно степени двойки
            // и сохраняем высоту и ширину, когда они больше необходимых
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(String file, int reqWidth, int reqHeight) {

        // Сначала вызываем декодер с опцией inJustDecodeBounds=true для проверки разрешения
        final BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(file, options);
        // Подсчитываем inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inPreferredConfig = Bitmap.Config.RGB_565;

        // Теперь вызываем декодер с установленной опцией inSampleSize
        options.inJustDecodeBounds = false;

        Bitmap bf = BitmapFactory.decodeFile(file, options);

        Log.v("fff", reqHeight + "  " + reqWidth);

//        Bitmap bm = bf.createScaledBitmap(bf, reqWidth, reqHeight, true);

        // Bitmap mutableBitma        p = bf.copy(Bitmap.Config.ARGB_8888, true);

        return bf;
    }

    public boolean onTouch(View view, MotionEvent event)
    {
        if (view.getId()==R.id.flip) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: // Пользователь нажал на экран, т.е. начало движения
                    // fromPosition - координата по оси X начала выполнения операции
                    fromPosition = event.getX();
                    break;
                case MotionEvent.ACTION_UP: // Пользователь отпустил экран, т.е. окончание движения
                    float toPosition = event.getX();
                    if (fromPosition > toPosition + 20) {
                        flip.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.go_next_in));
                        flip.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.go_next_out));
                        flip.showNext();
                    } else if (fromPosition < toPosition - 20) {
                        flip.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.go_prev_in));
                        flip.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.go_prev_out));
                        flip.showPrevious();
                    }


                default:
                    break;
            }
        }
        return false;
    }


}




