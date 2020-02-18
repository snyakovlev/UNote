package snyakovlev.unote;


import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Locking extends DialogFragment {


    EditText et;

    Button bt;

    String nametable;

    public static Locking newInstance(String nametable)
    {
        Locking locking=new Locking();

        Bundle args=new Bundle();

        args.putString("nametable", nametable);

        locking.setArguments(args);

        return  locking;


    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View ll=getActivity().getLayoutInflater().inflate(R.layout.activity_locking,null);

        et=(EditText)ll.findViewById(R.id.password);

        nametable=getArguments().getString("nametable");

        return new AlertDialog.Builder(getActivity())

                .setView(ll)

                .setTitle(getResources().getString(R.string.setpassword))

                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        setPassword();
                    }
                })
                .create();



    }

 void setPassword()
 {
     DataBaseInterface db=new DataBaseInterface(getActivity());

     Log.i("mmmm", nametable+" "+et.getText());

        db.setPassTableMain(nametable,et.getText().toString());


 }


}
