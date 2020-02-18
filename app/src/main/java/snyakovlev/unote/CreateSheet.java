package snyakovlev.unote;


import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;

import java.util.UUID;

import snyakovlev.unote.AllSheets;

public class CreateSheet extends DialogFragment {


    Glavnoe_Activity activ;

    EditText v;

    boolean lsh;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        activ=(Glavnoe_Activity)getActivity();

       View ll = getActivity().getLayoutInflater().inflate(R.layout.create_sheet, null);

        v=(EditText)ll.findViewById(R.id.et_namesheet);


        if (savedInstanceState!=null) activ.lsheet=savedInstanceState.getBoolean("lsh");

        return new AlertDialog.Builder(getActivity())

                .setView(ll)

                .setTitle(getResources().getString(R.string.create_table))

                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        activ.allSheets=new AllSheets(getActivity(),activ.db);

                        activ.allSheets.setTabletempltable(UUID.randomUUID().toString());

                        activ.allSheets.setNametablesheet(v.getText().toString());

                        activ.allSheets.setPassword("admin");

                        activ.allSheets.addDataBase(false);

                        sendResultListSheets(activ.lsheet);
                    }
                })

                        .create();


            }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("lsh",activ.lsheet);


    }

    private void sendResultListSheets(boolean ls)
{

    if (ls) {


        IInitList iil = (IInitList) getActivity().getSupportFragmentManager().findFragmentById(R.id.header);

        iil.initList();
    }

    else {

        WorkList wl = (WorkList) getActivity().getSupportFragmentManager().findFragmentById(R.id.header);

        wl.move();
    }

}





}
