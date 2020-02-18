package snyakovlev.unote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * Created by Катя on 04.09.2015.
 */
public class WorkAdapterDlg extends RecyclerView.Adapter<WorkAdapterDlg.MyViewHolder2> {

    private Context contextt;

    boolean editing;

     ArrayList<File> values;

    Glavnoe_Activity glav;

   CurrSave currsave;

      class MyViewHolder2  extends RecyclerView.ViewHolder {


        TextView textView1;




       int pos = 0;







        File currsave;

        String filename;


        CheckBox cbox;

        Context context;

       int i=0;


          RelativeLayout ppanel;



          boolean error;
           boolean com=false;





        MyViewHolder2(View rowView,Context context) {

            super(rowView);



            cbox=(CheckBox)rowView.findViewById(R.id.cbox);

            textView1 = (TextView) rowView.findViewById(R.id.textView1);

            error=false;

        }




    }




    @Override
    public MyViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listxmldlg, parent, false);


        MyViewHolder2 vh = new MyViewHolder2(v,this.glav);



        return vh;
    }

    DLG wl;

    boolean checs[];


    void seekChange(View v)
    {

    }


    public WorkAdapterDlg(Context context, ArrayList<File> values, DLG wl, boolean move) {


        Log.v("dfgbn",values.size()+"");

          checs=new boolean[values.size()];

        this.wl=wl;

        this.contextt=context;

        this.wl=wl;

        this.glav=(Glavnoe_Activity)context;

        this.values = values;

    }




    String s;
     int posit;
     float size=0;

     void update()
     {
         wl.notifList(true);
     }






    static String getFileExtension(File file) {
        String fileName = file.getName();
        // если в имени файла есть точка и она не является первым символом в названии файла
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            // то вырезаем все знаки после последней точки в названии файла, то есть ХХХХХ.txt -> txt
            return fileName.substring(fileName.lastIndexOf(".")+1);
            // в противном случае возвращаем заглушку, то есть расширение не найдено
        else return "";
    }


    @Override
    public void onBindViewHolder( MyViewHolder2 vh,  int position) {



        vh.currsave = values.get(position);

        Log.v("qwer", vh.currsave.getAbsolutePath());

        vh.pos = position;


        final MyViewHolder2 vhh;

        vhh = vh;

        WorkList.save_for_move.trimToSize();


        vh.cbox.setChecked(checs[position]);


        vh.textView1.setText(values.get(vh.pos).getName());

        vh.textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(glav,ViewF.class);
                i.putExtra("filef",vhh.currsave.getAbsolutePath());
                glav.startActivity(i);
            }
        });



        vh.cbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                glav.selectall2 = false;
                if (vhh.cbox.isChecked()) {
                    Log.v("qwedc", vhh.pos + "");
                    checs[vhh.pos] = true;
                    DLG.save_for_move.add(values.get(vhh.pos));
                }

                if (!vhh.cbox.isChecked()) {
                    checs[vhh.pos] = false;
                    int i = 0;
                    int j = 0;
                    for (CurrSave s : WorkList.save_for_move) {

                        if (s.equals(vhh.currsave)) {
                            j = i;
                        }

                        i++;
                    }

                    remove(j);
                    Log.v("asdf", "j=" + j);
                }
            }
        });


    }

    void remove(int j){
        if (DLG.save_for_move.size()!=0 && j<DLG.save_for_move.size())  DLG.save_for_move.remove(j);
    }

    @Override
    public int getItemCount() {
        Log.v("edfg","item_count "+values.size());
        return values.size();
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






    class ListFile
    {
        Bitmap photo;
        XmlPullParser parser;
        Bitmap image;


        ListFile(CurrSave curs,int pos)
        {
          //  image=dLI2(curs.filenameimage);
          //  photo=dLI(curs.filenamephoto1);
            parser=getParser(curs);

        }




        XmlPullParser getParser(CurrSave currs)
        {
            File root;

            if (isAvailable() || !isReadOnly()) {

                root = Environment.getExternalStorageDirectory();
            } else {
                root = glav.getFilesDir();
            }

            String root_str = root.getAbsolutePath();

            String my_dir = root_str + File.separator + "nbook" + File.separator + "table";

            File f=new File(my_dir+"/"+currs.uuid_table_table+".html");
            if (!f.exists()) return null;
            XmlPullParserFactory fac=null;
            XmlPullParser parcer=null;
            FileInputStream fis=null;
            try {
                fac=XmlPullParserFactory.newInstance();
            } catch (XmlPullParserException e) {
                e.printStackTrace();

            }
            try {
                parcer=fac.newPullParser();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            try {
                fis=new FileInputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (fac!=null && parcer!=null && fis!=null)

            {
                try {
                    parcer.setInput(new InputStreamReader(fis));
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }

            return parcer;
        }
    }






    public static Bitmap decodeSampledBitmapFromResource(String file, int reqWidth, int reqHeight) {

        Log.v("fff","file2="+file);

        final BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(file, options);
        // Подсчитываем inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;

        Bitmap bf = BitmapFactory.decodeFile(file,options);

        Log.v("fff", bf.getByteCount()+"");

        Bitmap bm = Bitmap.createScaledBitmap(bf, reqWidth, reqHeight, true);

        // Bitmap mutableBitma        p = bf.copy(Bitmap.Config.ARGB_8888, true);

        return bm;
    }

    void dialColor1() {
        new AmbilWarnaDialog(contextt, Color.WHITE,false, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int col) {

                for (CurrSave c:WorkList.save_for_move) {
                    PreferenceManager.getDefaultSharedPreferences(contextt).edit().putInt("fon_col_" + c.getUuid_save(), col).commit();
                }

                wl.notifList(true);
                WorkList.save_for_move.clear();
            }


        }).show();
    }

    void dialColor2() {
        new AmbilWarnaDialog(contextt, Color.WHITE, false, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog ambilWarnaDialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog ambilWarnaDialog, int col) {

                for (CurrSave c:WorkList.save_for_move) {
                    PreferenceManager.getDefaultSharedPreferences(contextt).edit().putInt("text_col_" + c.getUuid_save(), col).commit();
                }

                wl.notifList(true);
                WorkList.save_for_move.clear();
            }


        }).show();
    }






}
