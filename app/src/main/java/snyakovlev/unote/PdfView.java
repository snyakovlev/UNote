package snyakovlev.unote;



import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.io.File;
import java.util.ArrayList;


public class PdfView extends android.support.v4.app.Fragment implements View.OnTouchListener {

    int w,h;

    DataBaseInterface db;


    Glavnoe_Activity glav;

    CurrSave currsave;

    AllSheets allsheets;

    static  boolean _quick;

   TextView comm;

    ViewFlipper flip;

     boolean   photo=false;


    public static PdfView newInstance(boolean quick) {

        _quick=quick;

        PdfView pv = new PdfView();

        return pv;
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


        flip.setBackgroundColor(Color.WHITE);


        glav = (Glavnoe_Activity) getActivity();

        comm=(TextView)ll.findViewById(R.id.comm);

        comm.setVisibility(View.GONE);

        allsheets = ((Glavnoe_Activity) getActivity()).allSheets;

        currsave=((Glavnoe_Activity) getActivity()).currsave;


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

    int indexphoto;



    ArrayList<String> cms=new ArrayList<>();
    ArrayList<String> cph=new ArrayList<>();

    int currpos=0;

    String currphoto="";

    boolean v;

    boolean zoom=false;


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

          final String fn = currsave.filenamephoto1;

          outFile1 = new File(my_dir, fn + ".pdf");




        PDFAdapter pa=null;
        Bitmap bit=null;

        if (outFile1.exists())
    {
        pa=new PDFAdapter(getActivity(),outFile1.getAbsolutePath());
    }

        else
        {
            return;
        }

            cms.clear();
            cph.clear();

            for (int i=0;i<pa.openPdfRenderer();i++) {

                bit=pa.Pdf2Bitmap(i);

                String value = "";
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
                                    } else return false;


                                default:
                                    break;
                            }
                            return false;
                        }
                    });

                    im.setDisplayType(ImageViewTouchBase.DisplayType.FIT_IF_BIGGER);


                 im.setImageBitmap(bit);
                    im.invalidate();
                    flip.addView(im);


            }
            currpos=0;
            int j=0;

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
        inflater.inflate(R.menu.menu_main55, menu);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();




        if (id == R.id.savepdf) {


            save();
        }



        if (id==R.id.share_pdf)
        {
            glav.permStorage();
            glav.share(currphoto,"pdf",null);
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




