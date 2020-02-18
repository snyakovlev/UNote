package snyakovlev.unote;


import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EnterPassword extends android.support.v4.app.DialogFragment {

    EditText et;

    Button bt;

    String uuid_sheet;

    String password;

    Boolean setPass;

    Glavnoe_Activity glav;

    public static EnterPassword newInstance(String uuid_sheet)

    {


        EnterPassword epass=new EnterPassword();

        Bundle args=new Bundle();

        args.putString("uuid_sheet", uuid_sheet);

        epass.setArguments(args);

        return  epass;


    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View ll=getActivity().getLayoutInflater().inflate(R.layout.activity_locking,null);

       et=(EditText)ll.findViewById(R.id.password);

        uuid_sheet= getArguments().getString("uuid_sheet");

        glav=(Glavnoe_Activity)getActivity();

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

        password=db.queryDataBaseRowNT("password",uuid_sheet);

        if (et.getText().toString().equals(password))
        {
            glav.isSetPass=true;

            sendResultListSheets();
        }
        else
        {
            glav.isSetPass=false;
        }



    }

    private void sendResultListSheets()
    {


        IShowFragmentWL  isfwl=glav;

        isfwl.showFragmentWL();

    }

}
