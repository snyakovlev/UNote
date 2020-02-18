package snyakovlev.unote;

import android.content.Context;


import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.*;

/**
 * Created by Катя on 04.09.2015.
 */
public class ListSheetAdapter extends Adapter<ListSheetAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<AllSheets> values;
    static ColorStateList coltext;
    static int colback;
    ListSheets ls;

    public class MyViewHolder extends RecyclerView.ViewHolder {
      final  ColorStateList color;
        CardView cv15;
        TextView textview;
        boolean em, edmenuret;


        //   ImageView imageview;
        int position;

        MyViewHolder(View v) {
            super(v);

            cv15 = (CardView) v.findViewById(R.id.cv15);


            textview = (TextView) v.findViewById(R.id.tv_sheet);



            color = textview.getTextColors();
            em = false;
            edmenuret = true;
            //     imageview = (ImageView)v.findViewById(R.id.iv_lock);

        }
    }


    boolean[] checs;


    public ListSheetAdapter(Context context, ArrayList<AllSheets> values, ListSheets ls) {

        checs=new boolean[values.size()];
        this.ls = ls;
        this.context = context;
        this.values = values;

    }


    int position = 0;


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_sheet_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);


        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        this.position = position;
        Glavnoe_Activity glav = ((Glavnoe_Activity) context);
        holder.em=glav.selectall;
        holder.textview.setTypeface(Typeface.createFromAsset(Fonts.getAss(context),Fonts.fontLs));
        holder.textview.setTextSize(Float.parseFloat(Fonts.fontsizeLS));
        holder.textview.setText(values.get(position).getNametablesheet());

        String s = values.get(position).getPassword();


        if (s.startsWith("admin")) {
            holder.textview.setAlpha(1.0f);
        } else {
            holder.textview.setAlpha(0.5f);
        }
        coltext=holder.textview.getTextColors();
        if (glav.shmove & holder.em) {

            holder.textview.setTextColor(Color.WHITE);
            holder.textview.setBackgroundDrawable(context.getResources().getDrawable(ColorTheme.accent));
            sheetsformove.add(values.get(position));
        }

        else
        {
            holder.textview.setTextColor(coltext);
            holder.textview.setBackgroundDrawable(context.getResources().getDrawable(android.R.color.white));
            sheetsformove.clear();
        }

        final AllSheets alls = values.get(position);
        if (glav.shmove)
        {
            for (AllSheets a:sheetsformove)
            {
                if (a.getUuid_sheet().equals(values.get(position).getUuid_sheet()))
                {
                    holder.textview.setTextColor(Color.WHITE);
                    holder.textview.setBackgroundDrawable(context.getResources().getDrawable(ColorTheme.accent));
                }
            }
        }

        holder.textview.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Glavnoe_Activity glav = ((Glavnoe_Activity) context);

                if (glav.shmove) {
                    if (holder.em) {
                        holder.em = false;
                    } else {
                        holder.em = true;
                    }
                }

                if (glav.shmove & holder.em) {
                    glav.sel=true;
                    holder.textview.setTextColor(Color.WHITE);
                    holder.textview.setBackgroundDrawable(context.getResources().getDrawable(ColorTheme.accent));
                    sheetsformove.add(values.get(position));
                }
                else
                if (!holder.em & !glav.shmove) {

                    if (alls.setPass)

                    {


                        glav.allSheets = alls;

                        glav.allSheets.setUuid_sheet(alls.getUuid_sheet());

                        glav.setTitle(alls.getNametablesheet());

                        IShowFragmentWL isfwl = (IShowFragmentWL) glav;

                        isfwl.showFragmentWL();

                        holder.edmenuret = false;


                        //   isfwl.showFragmentFF2();

                    } else

                    {

                        glav.allSheets = alls;

                        IShowEnterPassword isl = (IShowEnterPassword) glav;

                        isl.showEnterPassword(glav.allSheets.getUuid_sheet());

                        holder.edmenuret = false;

                    }


                }

                else if (glav.shmove & !holder.em) {
                    glav.sel=false;
                    holder.textview.setTextColor(holder.color);
                    holder.textview.setBackgroundDrawable(context.getResources().getDrawable(R.color.white));
                    int i = 0;
                    int j=0;
                    for (AllSheets s : sheetsformove) {

                    if (s.equals(alls)) {j=i;}

                        i++;
                    }
                    Log.v("asdf","j="+j);
                    remove(j);
                }

            }

        });

        holder.textview.setOnLongClickListener(
                new OnLongClickListener() {
                    Glavnoe_Activity glav = ((Glavnoe_Activity) context);

                    @Override
                    public boolean onLongClick(View v) {

                        sheetsformove.clear();

                        glav.shmove = true;

                        ls.itemLongClick();

                        holder.textview.setTextColor(Color.GREEN);


                        return false;
                    }
                });
    }

    int pos;
    boolean editing;

    @Override
    public int getItemCount() {
        return values.size();
    }

    static ArrayList<AllSheets> sheetsformove = new ArrayList<>();

    void remove(int i) {
        sheetsformove.remove(i);
    }
}