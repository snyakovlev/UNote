package snyakovlev.unote;

import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Катя on 19.09.2016.
 */
public class CreateTemplate extends android.support.v4.app.DialogFragment {


    DataBaseInterface db;

    SharedPreferences sh;

    EditText v;

    String nametable;

    Glavnoe_Activity glav;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View ll = getActivity().getLayoutInflater().inflate(R.layout.create_sheet, null);

        v = (EditText) ll.findViewById(R.id.et_namesheet);

        db = new DataBaseInterface(getActivity());

        glav=(Glavnoe_Activity)getActivity();

        return new AlertDialog.Builder(getActivity())

                .setView(ll)

                .setTitle(getResources().getString(R.string.createtemplate))

                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        nametable=glav.allSheets.getTempltable();

                        ContentValues cv=new ContentValues();

                        cv.put("template",v.getText().toString());

                        db.insertDataBase(nametable,cv);

                        sendResultEditData();
                    }
                })

                .create();


    }

    private void sendResultEditData() {

        IInitTamplate iil = (IInitTamplate) getActivity().getSupportFragmentManager().findFragmentById(R.id.header);

        iil.initTemplate();


    }
}
