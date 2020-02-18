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
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.io.File;
import java.util.ArrayList;


public class PdfViewF extends android.support.v4.app.Fragment implements View.OnTouchListener {

    int w,h;

    DataBaseInterface db;




    CurrSave currsave;

    AllSheets allsheets;

    static  boolean _quick;

   TextView comm;

    ViewFlipper flip;

    String file;

     boolean   photo=false;


    public static PdfViewF newInstance(String file) {


        PdfViewF pv = new PdfViewF();

        Bundle args = new Bundle();
        args.putString("file", file);
        pv.setArguments(args);

        return pv;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        setHasOptionsMenu(true);

        file=getArguments().getString("file");

    }


    float fromPosition = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View ll = getActivity().getLayoutInflater().inflate(R.layout.photo_view, null);

        getActivity().setTitle("PDFView");

        flip=(ViewFlipper)ll.findViewById(R.id.flip);

        comm=(TextView)ll.findViewById(R.id.comm);

        comm.setVisibility(View.GONE);

        flip.setBackgroundColor(Color.WHITE);

        flip.setDrawingCacheEnabled(true);

        flip.setOnTouchListener( this);


        initFlip();

        return ll;
    }






    int currpos=0;

    String currphoto="";

    boolean v;

    boolean zoom=false;


    void initFlip()
    {
        flip.removeAllViews();
        flip.invalidate();



         File outFile1 = new File(file);




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



            for (int i=0;i<pa.openPdfRenderer();i++) {

                bit=pa.Pdf2Bitmap(i);

                String value = "";

                   final ImageViewTouch im = new ImageViewTouch(getActivity(), null);

                    im.setSingleTapListener(new ImageViewTouch.OnImageViewTouchSingleTapListener() {
                        @Override
                        public void onSingleTapConfirmed() {



                        }
                    });

                    im.setDoubleTapListener(new ImageViewTouch.OnImageViewTouchDoubleTapListener() {
                        @Override
                        public void onDoubleTap() {
                            if (zoom==false) zoom=true; else zoom=false;
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






                                    } else if (fromPosition < toPosition - 40 && !zoom) {
                                        flip.setInAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.go_prev_in));
                                        flip.setOutAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.go_prev_out));
                                        flip.showPrevious();
                                        currpos--;


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




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main555, menu);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id==R.id.savepdff)
        {
            getActivity().finish();
        }

        return true;
    }

    boolean s=false;





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




