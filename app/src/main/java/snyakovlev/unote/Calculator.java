package snyakovlev.unote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Катя on 25.11.2016.
 */
public class Calculator extends Fragment implements View.OnClickListener, View.OnLongClickListener {


    int op=0;

    double o1,o2,r;

    PaintCreater pc;
    boolean opf;
    ImageView operat,ravno2;
    TextView result;
    LinearLayout telo;
    boolean nulevoy=false;
    Box b;
    boolean start=false;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
         b= Box.getInstance();
        nulevoy=true;
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)

    {
        View root = inflater.inflate(R.layout.calculator, container, false);

        root.findViewById(R.id.plus).setOnClickListener(this);
        root.findViewById(R.id.minus).setOnClickListener(this);
        root.findViewById(R.id.umnog).setOnClickListener(this);
        root.findViewById(R.id.razdel).setOnClickListener(this);
        telo=(LinearLayout)root.findViewById(R.id.telo);

        root.findViewById(R.id.plus).setOnLongClickListener(this);
        root.findViewById(R.id.minus).setOnLongClickListener(this);
        root.findViewById(R.id.umnog).setOnLongClickListener(this);
        root.findViewById(R.id.razdel).setOnLongClickListener(this);

        ImageView ppp=(ImageView)root.findViewById(R.id.plus);
        ImageView mmm=(ImageView) root.findViewById(R.id.minus);
        ImageView uuu=(ImageView) root.findViewById(R.id.umnog);
        ImageView rrr=(ImageView) root.findViewById(R.id.razdel);

        if (operfixplus){ppp.setAlpha(0.5f);}
            if (operfixminus){mmm.setAlpha(0.5f);}
                if (operfixumnog){uuu.setAlpha(0.5f);}
                    if (operfixrazdel){rrr.setAlpha(0.5f);}

        root.findViewById(R.id.ravno).setOnClickListener(this);
        root.findViewById(R.id.vstavka).setOnClickListener(this);
        root.findViewById(R.id.reset).setOnClickListener(this);
        ravno2=(ImageView) root.findViewById(R.id.ravno2);
        result=(TextView)root.findViewById(R.id.result);

        valid=true;
        et = (EditTable) getActivity().getSupportFragmentManager().findFragmentById(R.id.header);
     if (savedInstanceState!=null) {

         initTelo();
         if (ravnor) ravno2.setVisibility(View.VISIBLE);
         result.setText(savedInstanceState.getString("rrrr",""));


     }
     else
     {
         opers=new ArrayList<>();
     }


        return root;

    }
    EditTable et;

    ArrayList<Oper> opers;


    static  double okrugl(double chislo)
    {
       double d=chislo*1000;
        int i=(int)Math.round(d);
        return  (double)i/1000;
    }


    @Override
   public void onSaveInstanceState(Bundle outState) {

        outState.putString("rrrr",result.getText().toString());


        super.onSaveInstanceState(outState);

    }

    boolean ravnor;
    Oper oper;
    boolean valid=true;


    void table2oper(String o)
    {
        if (!ravnor) {


          if (valid)  oper=new Oper(this);

                start = true;
                oper.setOperand(o, nulevoy);



                if (operfixplus) {
                    Log.v("qazxcc",operfixplus+"");
                    nulevoy=false;
                     crPlus();
                }
                if (operfixminus) {

                    nulevoy=false;
                     crMinus();
                }
                if (operfixumnog) {
                    nulevoy=false;
                    crUmnog();
                }
                if (operfixrazdel) {
                    nulevoy=false;
                     crRazdel();
                }

                oper.setOperand(o, nulevoy);



                start = true;



            if (opf) nulevoy = false;

          if (valid) {
              opers.add(oper);
              int max = opers.size();
            if (telo.getChildAt(telo.getChildCount()-1)!=opers.get(max-1).getOperand_et())  telo.addView(opers.get(max - 1).getOperand_et());
          }

         if (!operfixminus && !operfixplus && !operfixumnog && !operfixrazdel) {

             valid = false;
         }
         else {
             int max=opers.size();
             Log.v("lololol","max="+max);
             telo.addView(opers.get(max-1).getOperator_iv());
         }
        }
    }



   static int createID()
    {
        Log.i("hjklkj",(Calendar.getInstance().get(Calendar.SECOND)*1000+Calendar.getInstance().get(Calendar.MILLISECOND))+"");
       return ((Calendar.getInstance().get(Calendar.SECOND)*1000+Calendar.getInstance().get(Calendar.MILLISECOND)));

    }

   void  initTelo()
   {


       for (int i=0;i<opers.size();i++)
       {

           if (opers.get(i).getOperand_et()!=null) {
               if (opers.get(i).getOperand_et().getParent() != null)
                   ((ViewGroup) opers.get(i).getOperand_et().getParent()).removeView(opers.get(i).getOperand_et());
               telo.addView(opers.get(i).getOperand_et());
           }
           if (opers.get(i).getOperator_iv()!=null) {
               if (opers.get(i).getOperator_iv().getParent() != null)
                   ((ViewGroup) opers.get(i).getOperator_iv().getParent()).removeView(opers.get(i).getOperator_iv());
               telo.addView(opers.get(i).getOperator_iv());
           }
       }

   }



   void calcul ()
   {


       float n=0,m=0;
       try {
           if(opers.size()==0) return;
          n=Float.parseFloat(opers.get(0).getOperand().toString());
           Log.v("qazsx",n+"");
           for (int i=1;i<opers.size();i++) {
                     m=Float.parseFloat(opers.get(i).getOperand().toString());
                     Log.v("qazsx",m+"");
                     if (opers.get(i-1).getOperator()!=null && opers.get(i-1).getOperator().equals("p")) n = n + m;
                     if (opers.get(i-1).getOperator()!=null && opers.get(i-1).getOperator().equals("m")) n = n - m;
                     if (opers.get(i-1).getOperator()!=null && opers.get(i-1).getOperator().equals("u")) n = n * m;

                     if (opers.get(i-1).getOperator()!=null && m == 0 & opers.get(i-1).getOperator().equals("r")){Toast.makeText(getActivity(),getResources().getString(R.string.c1),Toast.LENGTH_LONG).show();return;}
                    if (opers.get(i-1).getOperator()!=null && m != 0 & opers.get(i-1).getOperator().equals("r"))   { n = n / m;}



           }
       }
       catch (NumberFormatException e)
       {
           Log.e("errors",e.toString());
       }



       result.setText(n+"");
   }


    @Override
    public void onClick(View v) {



            switch (v.getId()) {

                case R.id.plus: {Log.v("lololol","plus");
                    if (!opf) {
                        if (start && !ravnor) {
                            nulevoy = false;
                            crPlus();
                            int max=opers.size();
                            telo.addView(opers.get(max-1).getOperator_iv());
                            valid=true;
                            table2oper("0");
                        }
                    }
                }
                break;
                case R.id.minus: {
                    if (!opf) {
                        if (start && !ravnor) {
                            nulevoy = false;
                            crMinus();
                            int max=opers.size();
                            telo.addView(opers.get(max-1).getOperator_iv());
                            valid=true;
                            table2oper("0");
                        }
                    }
                }
                break;
                case R.id.umnog: {
                    if (!opf) {
                        if (start && !ravnor) {
                            nulevoy = false;
                            crUmnog();
                            int max=opers.size();
                            telo.addView(opers.get(max-1).getOperator_iv());
                            valid=true;
                            table2oper("0");
                        }
                    }
                }
                break;
                case R.id.razdel: {
                    if (!opf) {
                        if (start && !ravnor) {
                            nulevoy = false;
                            crRazdel();
                            int max=opers.size();
                            telo.addView(opers.get(max-1).getOperator_iv());
                            valid=true;
                            table2oper("0");
                        }
                    }
                }
                break;
                case R.id.ravno: {


                    et.numoper = 2;
                    nulevoy=true;
                    ravno2.setVisibility(View.VISIBLE);
                    ravno2.setImageDrawable(getResources().getDrawable(R.drawable.ravno));
                    calcul();
                    ravnor = true;
                    et.update(-1);

                }
                break;
                case R.id.vstavka: {
                    et.calcul2table(result.getText().toString());
                }
                break;
                case R.id.reset: {
                    ravnor = false;
                    ravno2.setVisibility(View.INVISIBLE);
                    et.numoper = 0;
                    et.update(-1);
                    et.editing = false;
                    et.initLayout(false);
                    telo.removeAllViews();
                    opers.clear();
                    start=false;
                    result.setText("");
                    nulevoy = true;
                    valid = true;


                }


            }


        }


    @Override
    public boolean onLongClick(View v) {

        switch (v.getId()) {
            case R.id.plus: {
        if (!operfixminus & !operfixrazdel & !operfixumnog) {
            v.setAlpha(0.5f);

            if (!operfixplus) {

                operfixplus = true;
                opf=true;
                v.setAlpha(0.5f);
            } else {
                operfixplus = false;

                opf=false;
                v.setAlpha(1.0f);
            }
        }

            }
            break;
            case R.id.minus: {
                if (!operfixplus & !operfixrazdel & !operfixumnog) {
                    v.setAlpha(0.5f);

                    if (!operfixminus) {

                        operfixminus = true;
                        opf=true;
                        v.setAlpha(0.5f);
                    } else {
                        operfixminus = false;
                        opf=false;
                        v.setAlpha(1.0f);
                    }
                }

            }
            break;
            case R.id.umnog: {
                if (!operfixminus & !operfixrazdel & !operfixplus) {
                    v.setAlpha(0.5f);

                    if (!operfixumnog) {

                        operfixumnog = true;
                        opf=true;
                        v.setAlpha(0.5f);
                    } else {
                        operfixumnog = false;

                        opf=false;
                        v.setAlpha(1.0f);
                    }
                }

            }
            break;
            case R.id.razdel: {
                if (!operfixminus & !operfixplus & !operfixumnog) {
                    v.setAlpha(0.5f);

                    if (!operfixrazdel) {

                        operfixrazdel = true;
                        opf=true;
                        v.setAlpha(0.5f);
                    } else {
                        operfixrazdel = false;

                        opf=false;
                        v.setAlpha(1.0f);
                    }
                }

            }
            break;

        }
        return false;
    }
    boolean operfixplus,operfixminus,operfixumnog,operfixrazdel;

    void crPlus()
    {
        Log.v("lololol","crPlus");

        valid=true;

       // oper=new Oper(this);

        oper.setOperator("p",nulevoy);



    }

    void crMinus () {
        valid=true;

      //  oper=new Oper(this);

        oper.setOperator("m",nulevoy);


    }
    void crUmnog() {

        valid=true;

       // oper=new Oper(this);

        oper.setOperator("u",nulevoy);

    }



    void crRazdel() {

        valid=true;

       // oper=new Oper(this);

        oper.setOperator("r",nulevoy);

    }

    class Oper
    {
        private String operand=null;
        private String operator=null;
        private boolean promval=false;
        private boolean resval=false;
        private EditText operand_et=null;
        private ImageView operator_iv=null;
        private Calculator cal;
        private boolean nulevoy=false;

        public Oper(Calculator cal) {
            this.cal=cal;

        }

        public String getOperand() {
            return operand;
        }

        public void setOperand(final String operand, boolean nulevoy) {

            Log.v("lololol","setOperand");


               this.nulevoy=nulevoy;

               this.operand = operand;

               this.promval=true;

               if (this.operand_et==null) {

                   this.operand_et = new EditText(this.cal.getActivity());

                   this.operand_et.setId(createID());

                   this.operand_et.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));

                   this.operand_et.setGravity(Gravity.BOTTOM);

                   this.operand_et.setPadding(0, 4, 0, 0);
               }

               this.operand_et.setText(this.operand);

               this.operand_et.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                  setOp(s.toString());
                }
            });

          //  else
         //  {
         //       this.promval=false;
         //  }
         //   if (this.operand!=null) this.resval=true;

        }

        void setOp(String oper)
        {
            this.operand=oper;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator,boolean nulevoy) {


            Log.v("lololol","setOperator");

                if (!nulevoy) {

                    this.operator = operator;

                        this.operator_iv = new ImageView(getActivity());

                        this.operator_iv.setId(createID());

                        this.operator_iv.setPadding(0, 4, 0, 0);

                    if (this.operator.equals("p")) {
                        this.operator_iv.setImageDrawable(getResources().getDrawable(R.drawable.plusic2));
                    }
                    if (this.operator.equals("m")) {
                        this.operator_iv.setImageDrawable(getResources().getDrawable(R.drawable.minus));
                    }
                    if (this.operator.equals("u")) {
                        this.operator_iv.setImageDrawable(getResources().getDrawable(R.drawable.umnog));
                    }
                    if (this.operator.equals("r")) {
                        this.operator_iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_razdel));
                    }
                }

        }

        public EditText getOperand_et() {
            return operand_et;
        }


        public ImageView getOperator_iv() {
            return operator_iv;
        }

    }

}
