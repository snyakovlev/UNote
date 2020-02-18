package snyakovlev.unote;

import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Катя on 21.04.2017.
 */
public class Box {
   ArrayList<String> operands=new ArrayList<>();
   ArrayList<EditText> operandset=new ArrayList<>();
   ArrayList<ImageView> operatorsiv=new ArrayList<>();
   ArrayList<String> operators=new ArrayList<>();
    boolean operatorr;
    private static Box ourInstance = new Box();

    public static Box getInstance() {
        return ourInstance;
    }

    private Box() {
    }

}
