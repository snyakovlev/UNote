package snyakovlev.unote;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Катя on 04.09.2015.
 */



    public class ListSheetAdapter2 extends RecyclerView.Adapter<ListSheetAdapter2.MyViewHolder> {
        private static  Context context;
        private  ArrayList<AllSheets> values;
    boolean move;

        public static  class MyViewHolder  extends RecyclerView.ViewHolder {

            TextView textview;
            LinearLayout lin333;

            int position;

            MyViewHolder(View v) {
                super(v);
                textview = (TextView)v.findViewById(R.id.tv_sheet2);
lin333=(LinearLayout)v.findViewById(R.id.lin333);



            }
        }


        public ListSheetAdapter2(Context context,ArrayList<AllSheets> values)
        {

            this.context=context;
            this.values=values;


        }


        int position=0;


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_sheet_item2, parent, false);
            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            this.position=position;

            holder. textview.setTypeface(Typeface.createFromAsset(Fonts.getAss(context),Fonts.fontLs));
            holder.textview.setTextSize(Float.parseFloat(Fonts.fontsizeLS));
            holder.textview.setText(values.get(position).getNametablesheet());

            final AllSheets alls= values.get(position);

            holder.textview.setOnClickListener(new View.OnClickListener()
            {

                @Override
                public void onClick(View v) {

                    Glavnoe_Activity glav=((Glavnoe_Activity)context);

                    glav.drawerlay.closeDrawer(GravityCompat.START,false);

                    WorkList.save_for_move.clear();

                    glav.selectall2=false;



                    if (alls.setPass)

                    {
                        glav.allSheets=alls;

                        glav.allSheets.setUuid_sheet(alls.getUuid_sheet());

                        glav.setTitle(alls.getNametablesheet());

                        IShowFragmentWL  isfwl=glav;

                        isfwl.showFragmentWL();


                    }
                    else

                    {

                        glav.allSheets=alls;

                        IShowEnterPassword isl=glav;

                        isl.showEnterPassword(glav.allSheets.getUuid_sheet());
                    }



                }
            });




        }




        @Override
        public int getItemCount() {
            return values.size();
        }

    void goAbout()
    {
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://androidappl.esy.es"));

        context.startActivity(intent);

    }



}
