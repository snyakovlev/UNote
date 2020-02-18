package snyakovlev.unote;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;

/**
 * Created by Катя on 25.11.2016.
 */
public class FragmentFooterMenu1 extends Fragment implements View.OnClickListener {



    PaintCreater pc;
    ImageButton i1,i2,i3,i4,i5,i6,i7,i8,i9,i10,i11,i12,hid;
    SeekBar seekBar;
    LinearLayout ls1;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    int progres=255;
    boolean hidpan=true;
    LinearLayout lp1,lp2,lp3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)

    {
        View root = inflater.inflate(R.layout.fragment_footer_menu_1, container, false);
        ls1=(LinearLayout)root.findViewById(R.id.ls1);
         seekBar=(SeekBar)root.findViewById(R.id.seekBar);
         seekBar.setProgress(255);

         lp1=(LinearLayout)root.findViewById(R.id.lp1);
        lp2=(LinearLayout)root.findViewById(R.id.lp2);
        lp3=(LinearLayout)root.findViewById(R.id.lp3);

        i1=(ImageButton) root.findViewById(R.id.red);
       i1.setOnClickListener(this);
        hid=(ImageButton) root.findViewById(R.id.hid);
        hid.setOnClickListener(this);
       i2= (ImageButton)root.findViewById(R.id.blue);
               i2.setOnClickListener(this);
       i3= (ImageButton)root.findViewById(R.id.green);
       i3.setOnClickListener(this);
       i4= (ImageButton)root.findViewById(R.id.yellow);
       i4.setOnClickListener(this);
       i5= (ImageButton)root.findViewById(R.id.black);
       i5.setOnClickListener(this);
       i6= (ImageButton)root.findViewById(R.id.gray);
       i6.setOnClickListener(this);

       i7=(ImageButton) root.findViewById(R.id.s1);
       i7.setOnClickListener(this);
       i8=(ImageButton) root.findViewById(R.id.s5);
       i8.setOnClickListener(this);
       i9=(ImageButton) root.findViewById(R.id.s10);
               i9.setOnClickListener(this);
       i10=(ImageButton) root.findViewById(R.id.s20);
       i10.setOnClickListener(this);
       i11=(ImageButton) root.findViewById(R.id.s30);
               i11.setOnClickListener(this);
        i12=(ImageButton)root.findViewById(R.id.s15);
        i12.setOnClickListener(this);

       pc=(PaintCreater)getActivity().getSupportFragmentManager().findFragmentById(R.id.header);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pc.appv.paint.setAlpha(progress);
                progres=progress;
                i1.setAlpha(progress);
                i2.setAlpha(progress);
                i3.setAlpha(progress);
                i4.setAlpha(progress);
                i5.setAlpha(progress);
                i6.setAlpha(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return root;
    }



    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.hid:
            {
                if (hidpan) {
                    pc.appv.fulls=true;
                    hidpan=false;
                    lp1.setVisibility(View.GONE);
                    lp3.setVisibility(View.GONE);
                }
                else
                {
                    pc.appv.fulls=true;
                    hidpan=true;
                    lp1.setVisibility(View.VISIBLE);
                    lp3.setVisibility(View.VISIBLE);
                }
            }
            break;

            case R.id.red:
            {

                clearColor(v);
                pc.appv.paint.setColor(Color.RED);
                pc.appv.paint.setAlpha(progres);
                pc.color = Color.RED;
                pc.appv.rt=false;
            }
            break;
            case R.id.blue:
            {
                clearColor(v);
                pc.appv.paint.setColor(Color.BLUE);
                pc.appv.paint.setAlpha(progres);
                pc.color = Color.BLUE;
                pc.appv.rt=false;
            }
            break;
            case R.id.green:
            {

                clearColor(v);
                pc.appv.paint.setColor(Color.GREEN);
                pc.appv.paint.setAlpha(progres);
                pc.color = Color.GREEN;
                pc.appv.rt=false;
            }
            break;
            case R.id.yellow:
            {

                clearColor(v);
                pc.appv.paint.setColor(Color.YELLOW);
                pc.appv.paint.setAlpha(progres);
                pc.color = Color.YELLOW;
                pc.appv.rt=false;
            }
            break;
            case R.id.black:
            {

                clearColor(v);
                pc.appv.paint.setColor(Color.BLACK);
                pc.appv.paint.setAlpha(progres);
                pc.color = Color.BLACK;
                pc.appv.rt=false;
            }
            break;
            case R.id.gray:
            {

                clearColor(v);
                pc.appv.paint.setColor(Color.WHITE);
                pc.appv.paint.setAlpha(progres);
                pc.color = Color.WHITE;
                pc.appv.rt=false;

            }
            break;
            case R.id.s1:
            {

                clearSize(v);
                pc.appv.paint.setStrokeWidth(1);
                pc.appv.rt=false;
            }
            break;
            case R.id.s5:
            {

                clearSize(v);
                pc.appv.paint.setStrokeWidth(5);
                pc.appv.rt=false;
            }
            break;
            case R.id.s10:
            {

                clearSize(v);
               pc.appv.paint.setStrokeWidth(10);
                pc.appv.rt=false;
            }
            break;
            case R.id.s15:
            {

                clearSize(v);
               pc.appv.paint.setStrokeWidth(15);
                pc.appv.rt=false;
            }
            break;
            case R.id.s20:
            {

                clearSize(v);
                pc.appv.paint.setStrokeWidth(20);
                pc.appv.rt=false;
            }
            break;
            case R.id.s30:
            {

                clearSize(v);
                pc.appv.paint.setStrokeWidth(30);
                pc.appv.rt=false;
            }
            break;

        }

    }

    void clearColor(View v)
    {
        i1.setBackground(getResources().getDrawable(R.drawable.button0));
        i2.setBackground(getResources().getDrawable(R.drawable.button0));
        i3.setBackground(getResources().getDrawable(R.drawable.button0));
        i4.setBackground(getResources().getDrawable(R.drawable.button0));
        i5.setBackground(getResources().getDrawable(R.drawable.button0));
        i6.setBackground(getResources().getDrawable(R.drawable.button0));
        v.setBackground(getResources().getDrawable(R.drawable.button));

    }

    void clearSize(View v)
    {
        i7.setBackground(getResources().getDrawable(R.drawable.button0));
        i8.setBackground(getResources().getDrawable(R.drawable.button0));
        i9.setBackground(getResources().getDrawable(R.drawable.button0));
        i10.setBackground(getResources().getDrawable(R.drawable.button0));
        i11.setBackground(getResources().getDrawable(R.drawable.button0));
        i12.setBackground(getResources().getDrawable(R.drawable.button0));
        v.setBackground(getResources().getDrawable(R.drawable.button));
    }
}
