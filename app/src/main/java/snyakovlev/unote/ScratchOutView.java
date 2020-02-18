package snyakovlev.unote;

/**
 * Created by Катя on 23.04.2018.
 */


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

//import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class ScratchOutView extends View {


    public Bitmap image;
    public Path path;
    public Context context;
    public File outFile1,root;
    static public String filename="";
    public Paint paint=new Paint();
    public Paint paint2=new Paint();
    PaintCreater pc;
    static boolean photo;
     static String filename2="";
    int col;

    Path pt;

    static void instance(Context context,boolean photo,String filename2,String filename)
    {
        ScratchOutView.filename=filename;

        ScratchOutView.photo=photo;

        ScratchOutView.filename2=filename2;

        new ScratchOutView(context);
    }

    public ScratchOutView(Context context) {
        this(context, (AttributeSet) null);
    }



    public ScratchOutView(Context ctx, AttributeSet attrs) {


        super(ctx, attrs);
        this.pc=(PaintCreater)((Glavnoe_Activity)ctx).getSupportFragmentManager().findFragmentById(R.id.header);

        this.pc=pc;
        this.context=ctx;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1.0f);
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
         pt=new Path();
      //  init();
    }


    String getCurrImage(String fph1) {

        if (!fph1.equals("")) {

            if (isAvailable() || !isReadOnly()) {

                root = Environment.getExternalStorageDirectory();
            } else {
                root = context.getFilesDir();
            }

            String root_str = root.getAbsolutePath();

            String my_dir = null;


            my_dir = root_str + File.separator + "nbook" + File.separator + "image";

            outFile1 = new File(my_dir, fph1 + ".png");

            Log.v("lkl", "file: " + outFile1.getAbsolutePath());


            return outFile1.getAbsolutePath();
        }else
            return null;


    }

    String getCurrPhoto(String fph1) {

        if (!fph1.equals("")) {

            if (isAvailable() || !isReadOnly()) {

                root = Environment.getExternalStorageDirectory();
            } else {
                root = context.getFilesDir();
            }

            String root_str = root.getAbsolutePath();

            String my_dir = null;


            my_dir = root_str + File.separator + "nbook" + File.separator + "photo";

            outFile1 = new File(my_dir, fph1 + ".jpg");

            Log.v("lkl", "file: " + outFile1.getAbsolutePath());


            return outFile1.getAbsolutePath();
        }else
            return null;


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




      int w,h;

    boolean ph;
    String fn;
    Bitmap src;



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


    private static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float)maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float)maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;
        } else {
            return image;
        }
    }

    boolean back;
    boolean up;
    int ind;
    Bitmap e;
    Canvas f;

    void inv()
    {
        invalidate();
    }

    public void onSizeChanged(int w, int h, int oldw, int oldh) {

        ph=photo;

        fn=filename2;

        Glavnoe_Activity ga = (Glavnoe_Activity) context;

        Display currentDisplay = ga.getWindowManager().getDefaultDisplay();


        w = currentDisplay.getWidth();
        h = currentDisplay.getHeight();







        ga.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Log.v("qwew","f2="+filename2);

        if (!filename2.equals("")) {


            final BitmapFactory.Options options = new BitmapFactory.Options();

            options.inJustDecodeBounds = false;

            Bitmap b= BitmapFactory.decodeFile(getCurrImage(filename),options);

            options.inSampleSize = calculateInSampleSize(options, w, h);

            options.inPreferredConfig = Config.RGB_565;

            options.inJustDecodeBounds = false;

            if (!photo && !fulls) {

                image = BitmapFactory.decodeFile(getCurrImage(filename2), options);

                src=BitmapFactory.decodeFile(getCurrImage(filename2), options);

            if (image!=null)    image = image.copy(Config.ARGB_8888, true);

            }

            if (photo && !filename2.equals("") && !fulls)
            {
                image = BitmapFactory.decodeFile(getCurrPhoto(filename2), options);

                src=BitmapFactory.decodeFile(getCurrPhoto(filename2), options);

                image = image.copy(Config.ARGB_8888, true);

            }


            if (image != null)

            {

                Bitmap mutableBitmap = image.copy(Config.ARGB_8888, true);

                Bitmap srcmb=src.copy(Config.ARGB_8888, true);

                image = Bitmap.createBitmap(mutableBitmap, 0, 0, mutableBitmap.getWidth(), mutableBitmap.getHeight());

                image=resize(image,w,h);

                src = Bitmap.createBitmap(srcmb, 0, 0, src.getWidth(), src.getHeight());

                src=resize(src,w,h);

                this.f = new Canvas(image);

            }

        }

        else if (!fulls){

            Log.v("jjjj", "ff=");

            image = Bitmap.createBitmap(currentDisplay.getWidth(), currentDisplay.getHeight(), Config.ARGB_4444);



            this.f = new Canvas(image);

            this.f.drawColor(Color.WHITE);

            src = image.copy(Config.ARGB_8888, true);
        }



        path = new Path();

        invalidate();


    }


    boolean fulls;
    @Override
    public void onDraw(Canvas canvass) {

        Log.v("aazzxx","ondraw "+up+" "+back);



       if (image!=null)
       {canvass.drawBitmap(image, 0, 0, paint2);

       paint.setStyle(Paint.Style.STROKE);

        canvass.drawPath(pt,paint);}


    }

ArrayList<Pointt> apoint=new ArrayList<>();

    Paint pp =new Paint();
    float ox,oy,ax,ay,otx=-1;
    boolean tt=false;
    boolean l=false;
    boolean rt=false;
    int ts=30;
    Paint pat=new Paint();
    @Override
    public boolean onTouchEvent(MotionEvent me) {
        int c=paint.getColor();
        int red= Color.red(c);
        int green= Color.green(c);
        int blue= Color.blue(c);
        int alpha= Color.alpha(c);
        paint.setARGB(alpha,red,green,blue);



        switch (me.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = me.getX();
                float y = me.getY();
                if (tt && otx!=-1)  copy((int) otx, (int) oy, 12, 12);
                ox=x;oy=y;
                ax=x;ay=y;
              if (!rt) {


                  paint.setStyle(Paint.Style.FILL);
                  f.drawCircle(x,y,paint.getStrokeWidth()/2,paint);
                  paint.setStyle(Paint.Style.STROKE);
                    pt.moveTo(x, y);


                }

              else
              {

                     copy((int) x, (int) y, 40, 40);

              }

                break;
            case MotionEvent.ACTION_MOVE:
                 x = me.getX();
                 y = me.getY();
                ax=x;ay=y;
                if (!rt) {
                    if (l) {
                        float razx = Math.abs(x - ox);
                        float razy = Math.abs(y - oy);
                        if (razx > razy) y = oy;
                        if (razy > razx) x = ox;
                        ox = x;
                        oy = y;
                    }
                    else
                    {
                    float abs = Math.abs((ox - x));
                    float abs2 = Math.abs((oy - y));
                    if (abs >= 10.0f || abs2 >= 10.0f) {
                        pt.quadTo(ox, oy, (ox + x) / 2.0f, (oy + y) / 2.0f);
                        ox = x;
                        oy = y;
                    }
                    }


                }
                else
                {

                        copy((int) x, (int) y, 40, 40);

                }

                break;
            case MotionEvent.ACTION_UP:

                 x = me.getX();
                y = me.getY();
                ox=x;oy=y;otx=x-5;
                if (!rt) {
                    if (l) {
                        float razx = Math.abs(x - ox);
                        float razy = Math.abs(y - oy);
                        if (razx > razy) y = oy;
                        if (razy > razx) x = ox;
                        pt.lineTo(x, y);
                        f.drawPath(pt, paint);
                    }
                   if (!l) pt.lineTo(x, y);
                    paint.setStyle(Paint.Style.FILL);
                   if(!l) f.drawCircle(x,y,paint.getStrokeWidth()/2,paint);
                    paint.setStyle(Paint.Style.STROKE);
                    f.drawPath(pt, paint);
                    pt.reset();

                }

                else
                {
                    if (tt) {f.drawCircle(otx,y,5,paint);}
                    else {
                        copy((int) x, (int) y, 40, 40);
                    }
                }

                break;
        }

        invalidate();
        return true;
    }

    void copy2(int x,int y,int w,int h)
    {
        if (src!=null && y>0 && x>0 && (y+h)<src.getHeight() && (x+w)<src.getWidth()) {
            Bitmap t = Bitmap.createBitmap(src, (int) (x), (int) (y), w, h);
            f.drawBitmap(t, (int) (x), (int) (y), new Paint());
            invalidate();
        }
    }


   void copy(int x,int y,int w,int h)
   {
       Log.v("asasd","copy");

       if (src!=null && y-h/2>0 && x-w/2>0 && (y+h/2)<src.getHeight() && (x+w/2)<src.getWidth()) {
           Bitmap t = Bitmap.createBitmap(src, (int) (x-w/2), (int) (y-h/2), w, h);
           f.drawBitmap(t, (int) (x-w/2), (int) (y-h/2), new Paint());
           invalidate();
       }
   }




}

