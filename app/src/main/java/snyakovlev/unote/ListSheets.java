package snyakovlev.unote;

import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;




import java.io.IOException;
import java.util.ArrayList;




/**
 * Created by Катя on 27.08.2015.
 */
public class ListSheets extends Fragment implements IInitList, View.OnClickListener {



    RecyclerView lw_listshits;

    public LinearLayoutManager mLayoutManager;


    Glavnoe_Activity glav;

    IShowFragmentWL isfwl;

    String nametable;

    ListSheetAdapter madapter;

    DataBaseInterface db;




    boolean setPass=false;

 //   ArrayList<Glavnoe_Activity.AllSheets> allsheets=new ArrayList<>();

 //   TextView listsize0;



   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       Log.v("ee", "createview");

       View root = inflater.inflate(R.layout.listsheet, null);

       glav = (Glavnoe_Activity) getActivity();



       glav.setTitle(getResources().getString(R.string.app_name));

       lw_listshits = (RecyclerView) root.findViewById(R.id.lw_listsheet);

       Display display=glav.getWindowManager().getDefaultDisplay();

       Point size=new Point();

       display.getSize(size);

       DisplayMetrics m=getResources().getDisplayMetrics();

       int t=(int)Math.max(1,(size.x/m.xdpi)/2);

       Log.v("metrics",m+"");

       int col=Math.max(1,size.x/400);

       Log.i("figa","size.x="+size.x+"  "+"col="+col);

       GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(),t);

       lw_listshits.setLayoutManager(mLayoutManager);

       lw_listshits.setOnClickListener(this);


       root.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               IShowCreateSheet iscs = (IShowCreateSheet) getActivity();

               glav.lsheet=true;

               iscs.createSheet(glav.lsheet);

           }
       });



       initList();

       return root;

   }






    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Log.v("ee", "create");

        db=new DataBaseInterface(getActivity());

        setRetainInstance(true);

        setHasOptionsMenu(true);





    }



    void selectItem(int position)
    {

     //  ArrayList<Glavnoe_Activity.AllSheets> sheets =db.queryMainTableAll(db.nameMainTable, db.tab_param1,db.tab_param2, db.tab_param3,db.tab_param5,glav);

       setPass= allsheets.get(position).isSetPass();


        if (glav.isSetPass || setPass)

        {
            glav.allSheets=allsheets.get(position);

            glav.allSheets.setUuid_sheet(allsheets.get(position).getUuid_sheet());

            getActivity().setTitle(allsheets.get(position).getNametablesheet());

            isfwl=(IShowFragmentWL)getActivity();

            isfwl.showFragmentWL();

          //  isfwl.showFragmentFF2();

        }
        else

        {

            glav.allSheets=allsheets.get(position);

            IShowEnterPassword isl=(IShowEnterPassword)getActivity();

            isl.showEnterPassword(glav.allSheets.getUuid_sheet());
        }



    }

    ArrayList<AllSheets> allsheets;






    ArrayList initSheets()
    {

        db=new DataBaseInterface(getActivity().getApplicationContext());

        if (glav.isSetPass)
        {
            allsheets =db.queryMainTableAll2(db.nameMainTable,db.tab_param1,db.tab_param2,db.tab_param3,db.tab_param5,db.tab_param6,glav);

        }

        else

        {

            allsheets = db.queryMainTableAll(db.nameMainTable, db.tab_param1,db.tab_param2, db.tab_param3,db.tab_param5,db.tab_param6,glav);
        }




        return allsheets;
    }

    ListSheetAdapter2 adapter2;

@Override
  public  void initList()
    {


        initSheets();

        ArrayList<AllSheets> allremove=new ArrayList<>();

        for (int i=0;i<allsheets.size();i++) {
            Log.v("nnnnnnmm", allsheets.get(i).getNametablesheet()+"  "+allsheets.get(i).getUuid_sheet());
            if (allsheets.get(i).getNametablesheet().equals(getResources().getString(R.string.ga7))) {
                Log.v("nnnnnn", allsheets.get(i).getNametablesheet());
                allremove.add(allsheets.get(i));
            }
            else
            if (allsheets.get(i).getUuid_sheet().equals("rec0"))
            {
                Log.v("nnnnnn", allsheets.get(i).getNametablesheet());
                allremove.add(allsheets.get(i));
            }

            if (allsheets.get(i).getNametablesheet().equals(getResources().getString(R.string.ga18))) {
                Log.v("nnnnnn", allsheets.get(i).getNametablesheet());
                allremove.add(allsheets.get(i));
            }
            else
            if (allsheets.get(i).getUuid_sheet().equals("dlg0"))
            {
                Log.v("nnnnnn", allsheets.get(i).getNametablesheet());
                allremove.add(allsheets.get(i));
            }

        }

        allsheets.removeAll(allremove);

        madapter=new ListSheetAdapter(getActivity(),allsheets,this);

       adapter2=new ListSheetAdapter2(getActivity(),allsheets);

        lw_listshits.clearOnChildAttachStateChangeListeners();

        glav.lw.clearOnChildAttachStateChangeListeners();

        lw_listshits.setAdapter(madapter);

        glav.lw.setAdapter(adapter2);




    }

    boolean actcreated;






    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        actcreated=true;
    }
    Menu menu;

    Menu smenu;

    MenuInflater minfl;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        this.menu=menu;

        this.minfl=inflater;

        inflater.inflate(R.menu.menu_main10, menu);

        if (glav.shmove)

        {
            menu.clear();
            inflater.inflate(R.menu.menu_main_10, menu);

        }



    }



    @Override
    public void onResume() {

        super.onResume();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        if (item.getItemId()==R.id.sh_del)
        {
          //  glav.shmove=false;
            glav.delsheet();
        }

        if (item.getItemId()==R.id.sh_rename)
        {
         //   glav.shmove=false;
            glav.rnsheet();
        }

        if (item.getItemId()==R.id.sh_all)
        {
           if (!glav.selectall) {glav.selectall=true;} else {glav.selectall=false;}
            initList();

        }


        if (item.getItemId()==R.id.nastr)
        {
            glav.showSettings();

        }


        return true;
    }

    void back()
    {
        glav.selectall=false;
        glav.shmove=false;
        m1_m();
        initList();
    }




    @Override
    public void onClick(View v) {
        initList();
    }


    void shdlgObl()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.ls7))
                .setItems(new String[]{getResources().getString(R.string.ls2),getResources().getString(R.string.ls3)}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which==0){ db2obl();}

                        if (which==1){ obl2db();}
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cansel),new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return ;
                    }
                })
                .setCancelable(false);
        AlertDialog alert = builder.create();
        alert.show();
    }

    void shdlgUstr()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.ls1))
                .setItems(new String[]{getResources().getString(R.string.ls2),getResources().getString(R.string.ls3)}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which==1){ file2db();}

                        if (which==0){ db2file();}
                    }
                })
                .setNegativeButton(getResources().getString(R.string.otmena),new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return ;
                    }
                })
                .setCancelable(false);
        AlertDialog alert = builder.create();
        alert.show();
    }


    void obl2db()
    {
        glav.action=1;
        GoogleDrive gd=new GoogleDrive(glav);
        gd.download();
    }

    void db2obl()
    {
        glav.action=2;
        GoogleDrive gd=new GoogleDrive(glav);
        gd.upload("NBookBD.db");

    }

    void file2db()
    {  try {
        glav.copyDataBase(1);
    }
    catch (IOException e) {
      glav.showToastMessage(getResources().getString(R.string.ls4));
    }

    }

    void db2file()
    {
        try {
            glav.copyDataBase(2);
            glav.showToastMessage(getResources().getString(R.string.ls5));
        } catch (IOException e) {
            glav.showToastMessage(getResources().getString(R.string.ls6));
        }
    }

    void itemLongClick()
    {
        initList();

        m_m1();
    }

    void m_m1()
    {
        menu.clear();
        minfl.inflate(R.menu.menu_main_10,menu);

    }

  public  void m1_m()
    {
        menu.clear();
        minfl.inflate(R.menu.menu_main10,menu);
    }
}

