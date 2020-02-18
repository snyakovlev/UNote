package snyakovlev.unote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.pdf.PdfRenderer;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Cache;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


/**
 * Created by Катя on 04.09.2015.
 */
public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.MyViewHolder2> {

    private Context contextt;

    boolean editing;

     ArrayList<CurrSave> values;

    Glavnoe_Activity glav;

   CurrSave currsave;

      class MyViewHolder2  extends RecyclerView.ViewHolder {
        TextView infdate,nameList,openList;

        TextView textView1,topent;

        WebView table;


        boolean pdf;


       int pos = 0;

          boolean panel_photo;

        boolean play=false;

        boolean record=false;

        boolean playing=false;

        boolean recording=false;

          ImageButton audio_record,audio_play,share_audio;
Button fon,text;

          ImageView photo1_panel,photo2_panel,photo3_panel,image1_panel;

          SeekBar sb;

        AudioPlayer auplay;

        AudioRecorder aurec;

        CurrSave currsave;

        String fph4,ffph1,ffph2,ffph3;

        String filename;


        CheckBox cbox;

        Context context;

       int i=0;

          LinearLayout glav_panel,audio_panel,fontext,pp;

          RelativeLayout cv2;

          LinearLayout photo_panel,image_panel;

          RelativeLayout ppanel;

          private Handler durationHandler = new Handler();


          LinearLayout text_panel,table_panel,list_panel,list_add;



          boolean error;
           boolean com=false;

          private double timeElapsed = 0;

          boolean per=true;



        MyViewHolder2(View rowView,Context context) {

            super(rowView);


            cv2=(RelativeLayout)rowView.findViewById(R.id.ll3);
            
            photo_panel= rowView.findViewById(R.id.photo_panel);

            topent=(TextView)rowView.findViewById(R.id.topent);

            image_panel= rowView.findViewById(R.id.image_panel);

            ppanel=(RelativeLayout) rowView.findViewById(R.id.ppanel);

            audio_panel=(LinearLayout) rowView.findViewById(R.id.audio_panel);

            text_panel=(LinearLayout)rowView.findViewById(R.id.text_panel);

            table_panel=(LinearLayout)rowView.findViewById(R.id.table_panel);

            list_panel=(LinearLayout)rowView.findViewById(R.id.list_panel);

            sb=(SeekBar)rowView.findViewById(R.id.sb);







            pp=(LinearLayout)rowView.findViewById(R.id.pp);

            glav_panel=(LinearLayout)rowView.findViewById(R.id.glav_panel);

            audio_record=(ImageButton)rowView.findViewById(R.id.audio_record);

            share_audio=(ImageButton)rowView.findViewById(R.id.share_audio);

            audio_play=(ImageButton)rowView.findViewById(R.id.audio_play);

            photo1_panel=(ImageView)rowView.findViewById(R.id.photo1_panel);


            image1_panel=(ImageView)rowView.findViewById(R.id.image1_panel);



            cbox=(CheckBox)rowView.findViewById(R.id.cbox);


            text=(Button)rowView.findViewById(R.id.text);




            textView1 = (TextView) rowView.findViewById(R.id.textView1);

            nameList = (TextView) rowView.findViewById(R.id.nameList);

            openList = (TextView) rowView.findViewById(R.id.openList);

            table=(WebView)rowView.findViewById(R.id.table);

            infdate = (TextView) rowView.findViewById(R.id.infdate);

            error=false;

        }

          void runn(final AudioPlayer auplay)
          {
              Log.v("wwsseedd","runn");
              durationHandler.postDelayed(new Runnable() {

                  public void run() {

                      if (auplay.mediaPlayer!=null) {

                          timeElapsed = auplay.mediaPlayer.getCurrentPosition();


                          sb.setProgress((int) timeElapsed);

                      if (auplay.mediaPlayer.isPlaying())  {durationHandler.postDelayed(this, 100);}
                      }



                  }

              }, 100);
          }

    }




    @Override
    public MyViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listxml, parent, false);


        MyViewHolder2 vh = new MyViewHolder2(v,this.glav);



        return vh;
    }

    WorkList wl;

    boolean checs[];


    void seekChange(View v)
    {

    }


    public WorkAdapter(Context context, ArrayList<CurrSave> values,WorkList wl,boolean move) {


        Log.v("dfgbn",values.size()+"");

          checs=new boolean[values.size()];

        this.wl=wl;

        this.contextt=context;

        this.wl=wl;

        this.glav=(Glavnoe_Activity)context;

        this.values = values;

        updListFile();

        WorkList.save_for_move.clear();
    }




    String s;
     int posit;
     float size=0;

     void update()
     {
         wl.notifList(true);
     }


     class ListL
     {
         boolean check=false;
         String text="";
     }

     boolean comm(CurrSave c)
     {
         if (c.getFilenamephoto1()!=null && !c.getFilenamephoto1().equals(""))
             return true;
         if (c.getFilenameimage()!=null && !c.getFilenameimage().equals("") )
             return true;
         if (c.getFilename()!=null && !c.getFilename().equals(""))
             return true;

         if (c.getName_table_table()!=null && !c.getName_table_table().equals(""))
             return true;

         return false;
     }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        // если в имени файла есть точка и она не является первым символом в названии файла
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            // то вырезаем все знаки после последней точки в названии файла, то есть ХХХХХ.txt -> txt
            return fileName.substring(fileName.lastIndexOf(".")+1);
            // в противном случае возвращаем заглушку, то есть расширение не найдено
        else return "";
    }

    String pa0="nbook";
    String pa1="photo";
    String pa2="image";




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


    File getDirPhoto()
    {
        return new File(getDirRoot() + File.separator + pa0+File.separator+pa1 );
    }


    File getDirImage()
    {
        return new File(getDirRoot() + File.separator + pa0+File.separator+pa2);
    }


    @Override
    public void onBindViewHolder( MyViewHolder2 vh,  int position) {


        vh.text_panel.setVisibility(View.GONE);
        vh.table_panel.setVisibility(View.GONE);
        vh.image_panel.setVisibility(View.GONE);
        vh.photo_panel.setVisibility(View.GONE);
        vh.audio_panel.setVisibility(View.GONE);
        vh.list_panel.setVisibility(View.GONE);
        vh.nameList.setVisibility(View.GONE);
        vh.openList.setVisibility(View.GONE);

        vh.currsave=values.get(position);

        vh.pos=position;

int color=PreferenceManager.getDefaultSharedPreferences(contextt).
        getInt("fon_col_"+vh.currsave.getUuid_save(),Color.BLACK);
        int r=Color.red(color);
        int g=Color.green(color);
        int b=Color.blue(color);
        int col=0;
        if (r>g & r>b) col=Color.rgb(r,0,0);
        else
        if (g>r & g>b) col=Color.rgb(0,g,0);
        else
        if (b>g & b>r) col=Color.rgb(0,0,b);
        else
            col=Color.rgb(0,0,0);
if (color==Color.BLACK)
{ vh.pp.setBackgroundColor(color);}
else {
    vh.pp.setBackgroundColor(col);
}
        vh.ppanel.setBackgroundColor(PreferenceManager.getDefaultSharedPreferences(contextt).
                getInt("fon_col_"+vh.currsave.getUuid_save(),Color.WHITE));
        vh.textView1.setTextColor(PreferenceManager.getDefaultSharedPreferences(contextt).
                getInt("text_col_"+vh.currsave.getUuid_save(),Color.BLACK));

        vh.infdate.setTextColor(PreferenceManager.getDefaultSharedPreferences(contextt).
                getInt("text_col_"+vh.currsave.getUuid_save(),Color.BLACK));


        final  MyViewHolder2 vhh;
            vhh=vh;

            vh.ppanel.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    wl.fab2.setEnabled(false);
                    wl.hidefabs();
                    wl.FAbstatus=false;
                    wl.move=true;
                    wl.m_m1();
                    update();
                    return true;
                }
            });

            vh.photo_panel.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    wl.fab2.setEnabled(false);
                    wl.hidefabs();
                    wl.FAbstatus=false;
                    wl.move=true;
                    wl.m_m1();
                    update();
                    return true;
                }
            });

            vh.audio_panel.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    wl.fab2.setEnabled(false);
                    wl.hidefabs();
                    wl.FAbstatus=false;
                    wl.move=true;
                    wl.m_m1();
                    update();
                    return true;
                }
            });

            vh.image1_panel.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    wl.fab2.setEnabled(false);
                    wl.hidefabs();
                    wl.FAbstatus=false;
                    wl.move=true;
                    wl.m_m1();
                    update();
                    return true;
                }
            });

            vh.textView1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    wl.fab2.setEnabled(false);
                    wl.hidefabs();
                    wl.FAbstatus=false;
                    wl.move=true;
                    wl.m_m1();
                    update();
                    return true;
                }
            });

            vh.table_panel.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    wl.fab2.setEnabled(false);
                    wl.hidefabs();
                    wl.FAbstatus=false;
                    wl.move=true;
                    wl.m_m1();
                    update();
                    return true;
                }
            });

            vh.list_panel.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    wl.fab2.setEnabled(false);
                    wl.hidefabs();
                    wl.FAbstatus=false;
                    wl.move=true;
                    wl.m_m1();
                    update();
                    return true;
                }
            });

         if (wl.move)   {vh.cbox.setVisibility(View.VISIBLE); }
         else
         {
             vh.cbox.setVisibility(View.GONE);

         }





         Log.v("qwedfg",checs[position]+ " == "+position);

            WorkList.save_for_move.trimToSize();


        vh.cbox.setChecked(checs[position]);


        s = values.get(position).getFilename();

        if (s==null) s="";

        final boolean com=false;

        if (comm(values.get(vh.pos)))
        {
            vh.textView1.setTextSize(contextt.getResources().getDimension(R.dimen.mintext));

            vh.textView1.setTypeface(Typeface.DEFAULT);

            vh.table_panel.setGravity(Gravity.CENTER);

            vh.com=true;
        }

        else
        {
            if (PreferenceManager.getDefaultSharedPreferences(wl.glav).getBoolean("link",true))
            {
                vh.textView1.setLinksClickable(true);

                Linkify.addLinks(vh.textView1,Linkify.ALL);

                Log.v("linkyfi",true+"");
            }

            else
            {
                vh.textView1.setLinksClickable(false);

                Log.v("linkyfi",false+"");
            }


            vh.textView1.setTypeface(Typeface.createFromAsset(Fonts.getAss(contextt),Fonts.fontWl));

            vh.textView1.setTextSize(Float.parseFloat(Fonts.fontsizeWL));

            vh.text_panel.setVisibility(View.VISIBLE);

            vh.com=false;
        }


        if (!(values.get(vh.pos).getFilenamephoto1()+"").equals("") && values.get(vh.pos).getFilenamephoto1()!=null) {

            vh.photo1_panel.setVisibility(View.VISIBLE);

            vh.photo_panel.setVisibility(View.VISIBLE);

            String ff=getDirPhoto().getAbsolutePath();

            File fg = new File(ff + File.separator + vh.currsave.getFilenamephoto1() + ".pdf");



            if (fg.exists())
            {
                Log.v("qwert",fg.getAbsolutePath());

                vh.pdf=true;

                PDFAdapter pa = new PDFAdapter(contextt, fg.getAbsolutePath());

                Bitmap bm = pa.Pdf2Bitmap(0);

                vh.photo1_panel.setImageBitmap(bm);

                vh.text_panel.setVisibility(View.VISIBLE);

                vh.textView1.setText(vh.currsave.getFilenamephoto1());
            }

            else

            if(getFilePhoto(vh.currsave.getFilenamephoto1())!=null) {


                vh.pdf=false;

                final BitmapFactory.Options options = new BitmapFactory.Options();

                options.inJustDecodeBounds = true;

                BitmapFactory.decodeFile(getFilePhoto(vh.currsave.getFilenamephoto1()).getAbsolutePath(), options);

               int h= options.outHeight;

               int w=options.outWidth;

               boolean port=false;

               int ww=0,hh=0;

               double k=1;

                if (h > w)
                {k = (double) h / w;port=true;}

                if (h < w)
                {k = (double) w / h;
                    port=false;}

                if (h==w) k=1;

                int height=0;

                if (port) {
                    height = (int) (wl.wp * k);
                }
                else {
                    height = (int) (wl.wp /k);
                }

               LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(wl.wp,height);

                 vh.photo1_panel.setLayoutParams(lp);

                glav.picasso.invalidate(getFilePhoto(vh.currsave.getFilenamephoto1()));

                glav.picasso
                        .load(getFilePhoto(vh.currsave.getFilenamephoto1()))
                        .fit()
                        .centerCrop()
                        .into(vh.photo1_panel);

                Log.v("qwert",vh.currsave.getFilenamephoto1()+"  "+(getFilePhoto(vh.currsave.getFilenamephoto1())).length()+"");
                vh.photo1_panel.invalidate();




//

                // init(vh.currsave,vh);

                vh.photo1_panel.setVisibility(View.VISIBLE);

                vh.text_panel.setVisibility(View.VISIBLE);

                vh.textView1.setText("");
            }

        }

        if (!(values.get(vh.pos).getFilenameimage()+"").equals("") && values.get(vh.pos).getFilenameimage()!=null) {

            vh.image_panel.setVisibility(View.VISIBLE);
            Log.v("qwert","image");


                final BitmapFactory.Options options = new BitmapFactory.Options();

                options.inJustDecodeBounds = true;

                BitmapFactory.decodeFile(getFileImage(vh.currsave.getFilenameimage()).getAbsolutePath(), options);

                int h= options.outWidth;

                int w=options.outHeight;

                double k=1;

                if (h>w) k=(double)h/w;

                if (h<w) k=(double)w/h;

                if (h==w) k=1;


                LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(wl.wp,(int)(wl.wp*k));

                vh.image1_panel.setLayoutParams(lp);

                    glav.picasso

                            .load(getFileImage(vh.currsave.getFilenameimage()))
                            .fit()
                            .centerCrop()
                            .into(vh.image1_panel);

                    glav.picasso.invalidate(getFileImage(vh.currsave.getFilenameimage()));


                vh.text_panel.setVisibility(View.VISIBLE);

                vh.textView1.setText("");




        }

        if (!(values.get(vh.pos).getFilename()+"").equals("") && values.get(vh.pos).getFilename()!=null) {

            vh.audio_panel.setVisibility(View.VISIBLE);

            vh.text_panel.setVisibility(View.VISIBLE);

            vh.textView1.setText("");



        }
        if (getType(vh)!=null) {

            if (!(values.get(vh.pos).getName_table_table() + "").equals("") && values.get(vh.pos).getName_table_table() != null && getType(vh).equals("1")) {

                //=============Таблица============

                vh.table_panel.setVisibility(View.VISIBLE);

                vh.table.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.v("rews", "click");
                    }
                });

                LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Math.abs(glav.getResources().getDisplayMetrics().heightPixels/2));

                vh.table_panel.setLayoutParams(lp);

                vh.table.setWebViewClient(new WebViewClient() {

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);

                        Log.v("edcxz", view.getTitle() + "");
                        vhh.topent.setText(contextt.getResources().getString(R.string.wa1));


                    }


                });


                File root;

                if (isAvailable() || !isReadOnly()) {

                    root = Environment.getExternalStorageDirectory();
                } else {
                    root = glav.getFilesDir();
                }

                String root_str = root.getAbsolutePath();

                String my_dir = root_str + File.separator + "nbook" + File.separator + "table";

                File f = new File(my_dir + "/" + values.get(vh.pos).uuid_table_table + ".html");

                if (f.exists()) {

                    Log.v("rews", "true");
                    vh.table.loadUrl("file:///" + my_dir + "/" + values.get(vh.pos).uuid_table_table + ".html");
                } else {
                    Log.v("rews", "false");
                    vh.table.setVisibility(View.GONE);
                }

            }
        }

        if (getType(vh)!=null) {

            if (!(values.get(vh.pos).getName_table_table() + "").equals("")
                    && values.get(vh.pos).getName_table_table() !=null
                    && getType(vh).equals("0")) {

                    //=============Список============
                vh.list_panel.setVisibility(View.VISIBLE);
                vh.nameList.setVisibility(View.VISIBLE);
                vh.openList.setVisibility(View.VISIBLE);
                vh.list_panel.removeAllViews();
                if (vh.list_panel.getChildCount()==0) {


                vh.nameList.setText(vh.currsave.getName_table_table());

                vh.nameList.setTextColor(PreferenceManager.getDefaultSharedPreferences(contextt).
                        getInt("text_col_"+vh.currsave.getUuid_save(),Color.BLACK));

                    final  ArrayList<ListL> larr  = getLArr(vh);

                vh.openList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (larr.size()==0)
                        {
                            if (!wl.move ) wl.itemClickTable(vhh.pos,false);
                        }
                        else {
                            if (!wl.move) wl.itemClickTable(vhh.pos, true);
                        }
                    }
                });

                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                for (ListL l : larr) {
                    Log.v("qazxc", l.text);

                    CheckBox cbx = new CheckBox(contextt);

                    cbx.setTextColor(PreferenceManager.getDefaultSharedPreferences(contextt).
                            getInt("text_col_"+vh.currsave.getUuid_save(),Color.BLACK));

                    cbx.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            saveList(vhh);
                        }
                    });

                    cbx.setChecked(l.check);

                    TextView tv = new TextView(contextt);

                    tv.setText(l.text);

                    tv.setTextColor(PreferenceManager.getDefaultSharedPreferences(contextt).
                            getInt("text_col_"+vh.currsave.getUuid_save(),Color.BLACK));

                  //  tv.setLayoutParams(lp);

                    LinearLayout lili = new LinearLayout(contextt);

                    lili.setLayoutParams(lp);

                    lili.setOrientation(LinearLayout.HORIZONTAL);

                    lili.addView(cbx);

                    lili.addView(tv);

                 vh.list_panel.addView(lili);
                }
                }

            }
        }


        if (!vh.com && !values.get(vh.pos).getValue().equals("")) {

            vh.textView1.setText(values.get(vh.pos).getValue());

        }

        if (vh.com && !values.get(vh.pos).getValue().equals("")) {


            vh.textView1.setMaxHeight(Math.abs(glav.getResources().getDisplayMetrics().heightPixels/2));

            vh.textView1.setGravity(Gravity.CENTER);

            vh.textView1.setTypeface(Typeface.DEFAULT);

            vh.textView1.setTextSize(contextt.getResources().getDimension(R.dimen.mintext));

            vh.textView1.setText(values.get(vh.pos).getValue());

        }

        if (vh.com && values.get(vh.pos).getValue()!=null && values.get(vh.pos).getValue().equals(""))
        {

            vh.textView1.setTypeface(Typeface.DEFAULT);

            vh.textView1.setTextSize(contextt.getResources().getDimension(R.dimen.mintext));

            vh.textView1.setHint(contextt.getResources().getString(R.string.wa2));

        }
        if (!vh.com && values.get(vh.pos).getValue()!=null && values.get(vh.pos).getValue().equals(""))
        {
            vh.textView1.setHint(contextt.getResources().getString(R.string.wa3));
        }






    vh.infdate.setText(values.get(position).getDatetime());




        vh.play=false;



        glav.isPotok=false;




        if (!s.equals("")) {

            vh.play=true;

            vh.audio_play.setAlpha(1.0f);

        }
        else
        {
            vh.play=false;

            vh.audio_play.setAlpha(0.5f);


        }






//-------------------photo----------------------------------------------------------------------------------------------




        vh.photo_panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!wl.move) {
                    if (vhh.auplay != null) {
                        if (vhh.auplay.mediaPlayer != null) {
                            if (vhh.auplay.mediaPlayer.isPlaying()) {
                                vhh.auplay.mediaPlayer.stop();
                            }
                        }
                    }
                    if (vhh.aurec != null) {
                        if (vhh.recording) {
                            vhh.aurec.recordStop();
                        }
                    }

                    if (vhh.pdf)
                    {
                        glav.currsave = vhh.currsave;

                        IPdfView iv = (IPdfView) contextt;

                        iv.pdfView(false);
                    }
                    else {
                        glav.currsave = vhh.currsave;

                        IPhotoView iv = (IPhotoView) contextt;

                        iv.photoView(false);
                    }


                }
            }
        });




  //-------------------------------------image-----------------------------------------------------


        vh.image1_panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!wl.move) {
                    if (vhh.auplay != null && vhh.auplay.mediaPlayer!=null) {
                        if (vhh.auplay.mediaPlayer.isPlaying())

                        {
                            vhh.auplay.mediaPlayer.stop();
                        }
                    }

                    if (vhh.aurec != null) {
                        if (vhh.recording) {
                            vhh.aurec.recordStop();
                        }
                    }
                    glav.currsave = vhh.currsave;
                    ICreaterPaint ipv = (ICreaterPaint) contextt;
                    ipv.showPaintCreator(vhh.currsave.getFilenameimage(),false);
                    Log.v("ghy", "image=" + vhh.fph4);


                }
            }
        });



        //-----------------------------------------------------------------------

        vh.cbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                glav.selectall2=false;
                if (vhh.cbox.isChecked()) {
                    Log.v("qwedc",vhh.pos+"");
                    checs[vhh.pos]=true;
                    WorkList.save_for_move.add(values.get(vhh.pos));
                }

                if (!vhh.cbox.isChecked())
                {
                    checs[vhh.pos]=false;
                    int i = 0;
                    int j=0;
                    for (CurrSave s : WorkList.save_for_move) {

                        if (s.equals(vhh.currsave)) {j=i;}

                        i++;
                    }
                    Log.v("asdf","j="+j);
                    remove(j);
                }
            }
        });

//-------------------------------------audio------------------------------------------------


        vh.sb.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {



                if (!vhh.playing) {

                   if (vhh.auplay != null && vhh.auplay.mediaPlayer != null)
                        vhh.auplay.mediaPlayer.release();

                    vhh.audio_play.setImageDrawable(contextt.getResources().getDrawable(R.drawable.stop2));


                    glav.isPotok = true;
                   vhh.auplay = new AudioPlayer(vhh.currsave.getFilename(), (Activity) contextt, vhh, wl);


                    try {
                        vhh.auplay.playStart();

                        vhh.auplay.mediaPlayer.stop();

                       vhh.sb.setMax(vhh.auplay.mediaPlayer.getDuration());

                        vhh.auplay.mediaPlayer.seekTo((int)vhh.sb.getProgress());

                        vhh.auplay.mediaPlayer.start();

                       vhh.runn(vhh.auplay);


                    } catch (Exception e) {

                    }


                    glav.currsave = vhh.currsave;

                    vhh.playing = true;
                }

            }
        });

        vh.audio_play.setOnClickListener(new View.OnClickListener() {


                                         @Override
                                         public void onClick(View v) {

                                             if (!wl.move)

                                             {

                                                 Log.v("gg", "ps_is_potok=" + glav.isPotok);
                                                 if (!glav.isPotok & vhh.play) {

                                                     Log.v("gg", "play_start");
                                                     vhh.audio_play.setImageDrawable(contextt.getResources().getDrawable(R.drawable.stop2));


                                                     glav.isPotok = true;

                                                     vhh.auplay = new AudioPlayer(vhh.currsave.getFilename(), (Activity) contextt,vhh, wl);


                                                     try {
                                                         vhh.auplay.playStart();

                                                         vhh.sb.setMax(vhh.auplay.mediaPlayer.getDuration());

                                                         vhh.auplay.mediaPlayer.seekTo(vhh.sb.getProgress());

                                                         vhh.runn(vhh.auplay);


                                                     }
                                                     catch (Exception e)
                                                     {
                                                         return;
                                                     }

                                                     glav.currsave = vhh.currsave;

                                                     vhh.playing = true;


                                                 } else if (vhh.play & glav.isPotok & vhh.playing)

                                                 {

                                                     Log.v("gg", "play_stop");
                                                     glav.isPotok = false;

                                                     glav.currsave = vhh.currsave;

                                                 //    vhh.audio_play.setImageDrawable(contextt.getResources().getDrawable(R.drawable.play2));

                                                     vhh.auplay.releasePlayer(vhh);

                                                     vhh.playing = false;


                                                 }


                                             }

                                         }


                                     }

        );

        vh.audio_record.setOnClickListener(new View.OnClickListener() {


                                        @Override
                                        public void onClick(View v)

                                        {

                                            if (!wl.move)

                                            { LockOrientation.getInstance(glav).lock();

                                                Log.v("gg", "rs_is_potok=" + glav.isPotok);
                                                if (vhh.i == 3) vhh.i = 1;

                                                if (!glav.isPotok & vhh.i == 1) {

                                                    Log.v("gg", "rec_start");

                                                    vhh.audio_play.setAlpha(0.5f);

                                                    glav.currsave = vhh.currsave;

                                                    vhh.aurec = new AudioRecorder((Activity) contextt, vhh.currsave);

                                                    vhh.audio_record.setImageDrawable(contextt.getResources().getDrawable(R.drawable.stop2));

                                                    try {

                                                        vhh.aurec.recordStart();
                                                    }

                                                    catch (Exception e)
                                                    {
                                                        vhh.audio_record.setImageDrawable(contextt.getResources().getDrawable(R.drawable.mic2));

                                                   return;
                                                    }

                                                    glav.isPotok = true;

                                                    vhh.recording = true;
                                                }


                                                if (vhh.i == 2 & vhh.recording) {

                                                    LockOrientation.getInstance(glav).unlock();

                                                    Log.v("gg", "rec_stop");
                                                    vhh.audio_play.setImageDrawable(contextt.getResources().getDrawable(R.drawable.play2));

                                                    vhh.audio_play.setAlpha(1.0f);

                                                    vhh.audio_record.setImageDrawable(contextt.getResources().getDrawable(R.drawable.mic2));

                                                    glav.isPotok = false;

                                                    glav.currsave = vhh.currsave;

                                                    vhh.play = true;

                                                    glav.currsave.setFilename(vhh.aurec.filename);

                                                    glav.currsave.updateDataBase();

                                                    vhh.aurec.recordStop();

                                                    vhh.currsave = glav.currsave;

                                                    vhh.recording = false;
                                                }


                                                vhh.i++;


                                            }
                                        }
                                    }
            );

        vh.share_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!wl.move) {

                    if (vhh.auplay!=null && vhh.auplay.mediaPlayer != null) {
                        if (vhh.auplay.mediaPlayer.isPlaying())

                        {
                            glav.isPotok = false;

                            glav.currsave = vhh.currsave;

                            //    vhh.audio_play.setImageDrawable(contextt.getResources().getDrawable(R.drawable.play2));

                            vhh.auplay.releasePlayer(vhh);

                            vhh.playing = false;
                        }
                    }

                    if (vhh.aurec != null) {
                        if (vhh.recording) {
                            vhh.audio_play.setImageDrawable(contextt.getResources().getDrawable(R.drawable.play2));

                            vhh.audio_play.setAlpha(1.0f);

                            vhh.audio_record.setImageDrawable(contextt.getResources().getDrawable(R.drawable.mic2));

                            glav.isPotok = false;

                            glav.currsave = vhh.currsave;

                            vhh.play = true;

                            glav.currsave.setFilename(vhh.aurec.filename);

                            glav.currsave.updateDataBase();

                            vhh.aurec.recordStop();

                            vhh.currsave = glav.currsave;

                            vhh.recording = false;
                        }
                    }
                    glav.share(vhh.currsave.getFilename(),"audio",null);
                }
            }
        });

        //---------------------------------------------------------------------------

        vh.textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!wl.move) {

                    if (vhh.com) {
                       final  EditText et=new EditText(contextt);
                       et.setText(vhh.currsave.getValue());
                        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        lp.setMargins(16,16,16,16);
                        et.setLayoutParams(lp);
                        LinearLayout ll=new LinearLayout(contextt);
                        ll.setGravity(Gravity.CENTER);
                        et.setEms(15);
                        ll.addView(et);
                        AlertDialog.Builder ab=new AlertDialog.Builder(contextt);
                        ab.setView(ll)
                                .setTitle(contextt.getResources().getString(R.string.wa2))
                                .setPositiveButton(contextt.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        editDB(et.getText().toString(),vhh.currsave);

                                    }
                                })
                                .show();
                    }else {
                        vhh.currsave.setEditing(true);
                        wl.itemClick(vhh.pos);
                    }

                }

            }
        });

        vh.topent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              if (!wl.move ) wl.itemClickTable(vhh.pos,false);

            }
        });





    }

    void editDB(String str,CurrSave c)
    {
      c.setValue(str);
      c.updateDataBase();
      notifyDataSetChanged();
    }

    ArrayList<ListL> getLArr(MyViewHolder2 vh)
    {
        Log.v("wsde","getLArr");
      ArrayList<ListL> res=new ArrayList<>();

        XmlPullParser xml=lf.get(vh.pos).getParser(vh.currsave);

        if (xml!=null) {
            ListL ll;
            boolean c = false;
            try {
                while (xml.getEventType() != XmlPullParser.END_DOCUMENT)

                {


                    ll = new ListL();
                    if (xml.getEventType() == XmlPullParser.START_TAG && xml.getName().equals("check")) {


                        String t1 = null;

                        t1 = xml.getAttributeValue(0);

                        Log.v("wsde", t1);
                        if (t1 != null) {
                            if (t1.equals("0")) {

                                ll.check = false;
                            }
                            if (t1.equals("1")) {
                                ll.check = true;
                            }
                        } else {
                            ll.check = false;

                        }


                        String t2 = null;

                        t2 = xml.getAttributeValue(1);

                        Log.v("wsde", t2);
                        if (ll != null) {
                            if (t2 != null) {
                                ll.text = t2;

                            } else {
                                ll.text = contextt.getResources().getString(R.string.wa4);

                            }

                        }

                        res.add(ll);
                    }


                    xml.next();

                }


            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return res;

    }

    void saveList(MyViewHolder2 vh)
    {
        File root;

        if (isAvailable() || !isReadOnly()) {

            root = Environment.getExternalStorageDirectory();
        } else {
            root = glav.getFilesDir();
        }

        String root_str = root.getAbsolutePath();

        String my_dir = root_str + File.separator + "nbook" + File.separator + "table";



            boolean ss=false;
            XmlSerializer s= Xml.newSerializer();
            FileWriter writer= null;
            try {
                File fil=new File(my_dir,vh.currsave.uuid_table_table+".html");
                fil.getParentFile().mkdirs();
                fil.createNewFile();
                writer = new FileWriter(fil);
                s.setOutput(writer);
                s.startDocument("UTF-8", true);

                 for (int i=0;i<vh.list_panel.getChildCount();i++)
                {
                    LinearLayout l=(LinearLayout)vh.list_panel.getChildAt(i);
                    s.startTag("", "check");
                      if (((CheckBox)l.getChildAt(0)).isChecked()) {
                    s.attribute("","checked","1");
                }
                else {
                          s.attribute("","checked","0");
                }

                    s.attribute("", "text",((TextView)l.getChildAt(1)).getText().toString());
                    s.endTag("", "check");
                }

            s.endDocument();

        }
        catch (Exception e)
        {
            throw new RuntimeException(e);

        }

        Widget2.sendRefreshBroadcast(contextt);
        }




    String getType(MyViewHolder2 vh)
    {
        String res="0";

        int i=0;

        XmlPullParser xml=lf.get(vh.pos).getParser(vh.currsave);

        if (xml!=null) {

            try {
                while (xml.getEventType() != XmlPullParser.END_DOCUMENT) {

                    if (xml.getEventType() == XmlPullParser.START_TAG && xml.getName().equals("td")) {
                        i++;
                    }

                    try {
                        xml.next();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                if (i == 1) {
                    res = "2";
                } else if (i == 0) {
                    res = "0";
                } else {
                    res = "1";
                }
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
        }


        return  res;
    }


    void audiostop(MyViewHolder2 vh)
    {
        Log.i("poiu","ai=udiostop");

        glav.isPotok = false;

        glav.currsave = vh.currsave;

        vh.playing = false;

        vh.audio_play.setImageDrawable(contextt.getResources().getDrawable(R.drawable.play2));
    }

    void initImage(MyViewHolder2 vh,CurrSave currsave)
    {

        vh.image1_panel.setImageBitmap(lf.get(vh.pos).image);
    }




    @Override
    public int getItemCount() {
        Log.v("edfg","item_count "+values.size());
        return values.size();
    }

void remove(int j){
  if (WorkList.save_for_move.size()!=0 && j<WorkList.save_for_move.size())  WorkList.save_for_move.remove(j);
}




    boolean init(CurrSave currsave,MyViewHolder2 vh) {



    if (lf.get(vh.pos).photo==null) {

        vh.photo1_panel.setImageDrawable(contextt.getResources().getDrawable(R.drawable.photo));
    }

              else {

        vh.photo1_panel.setImageBitmap(lf.get(vh.pos).photo);
    }



        this.glav.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);

        return true;
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
    public void  updListFile()
    {
        try {
            lf= new DLI().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    List<ListFile> lf;

    private  class DLI extends AsyncTask<Void,Void,List<ListFile>>
    {

        @Override
        protected List<ListFile> doInBackground(Void... voids) {
            final  List<ListFile> list =new ArrayList<ListFile>();
            for (int i=0;i<values.size();i++)
            {
                list.add(new ListFile(values.get(i),i));
            }
            return list;
        }



    }

    File getFilePhoto(String file)
    {
        File outFile=null;

        File root;

        if (isAvailable() || !isReadOnly()) {

            root = Environment.getExternalStorageDirectory();
        } else {
            root = contextt.getFilesDir();
        }

        String root_str = root.getAbsolutePath();

        String my_dir = null;

        my_dir = root_str + File.separator + "nbook" + File.separator + "mini";

        outFile = new File(my_dir, file + ".jpg");

        if (outFile.exists())
        {return outFile;}
        else {

            my_dir = root_str + File.separator + "nbook" + File.separator + "photo";

            outFile = new File(my_dir, file + ".jpg");


            return outFile.exists() ? outFile : null;
        }

    }

    File getFileImage(String file)
    {
        File outFile=null;

        File root;

        if (isAvailable() || !isReadOnly()) {

            root = Environment.getExternalStorageDirectory();
        } else {
            root = contextt.getFilesDir();
        }

        String root_str = root.getAbsolutePath();

        String my_dir = null;

        my_dir = root_str + File.separator + "nbook" + File.separator + "image";

        outFile = new File(my_dir, file + ".png");



        return outFile.exists() ?outFile:null;

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


        private  Bitmap dLI(final String file)
    {
        File outFile=null;

        File root;

        if (isAvailable() || !isReadOnly()) {

            root = Environment.getExternalStorageDirectory();
        } else {
            root = contextt.getFilesDir();
        }

        String root_str = root.getAbsolutePath();

        String my_dir = null;

        my_dir = root_str + File.separator + "nbook" + File.separator + "photo";

        if (file!=null && !file.equals("")) {

            outFile = new File(my_dir, file + ".jpg");
        }

       Log.v("fff","file="+file);
      return file.length()==0 | file==null ? null:  WorkAdapter.decodeSampledBitmapFromResource(outFile.getAbsolutePath(),148,148);

    }




        private  Bitmap dLI2(final String file)
        {
            File outFile=null;

            File root;

            if (isAvailable() || !isReadOnly()) {

                root = Environment.getExternalStorageDirectory();
            } else {
                root = contextt.getFilesDir();
            }

            String root_str = root.getAbsolutePath();

            String my_dir = null;

            my_dir = root_str + File.separator + "nbook" + File.separator + "image";

            if (file!=null && !file.equals("")) {

                outFile = new File(my_dir, file + ".png");
            }

            Log.v("fff","file="+file);
            return file.length()==0 | file==null ? null:  WorkAdapter.decodeSampledBitmapFromResource(outFile.getAbsolutePath(),148,148);

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
