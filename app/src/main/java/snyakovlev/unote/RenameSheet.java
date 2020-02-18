package snyakovlev.unote;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;

public class RenameSheet extends DialogFragment {


    Glavnoe_Activity activ;

    EditText v;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        activ=(Glavnoe_Activity)getActivity();

       View ll = getActivity().getLayoutInflater().inflate(R.layout.create_sheet, null);

        v=(EditText)ll.findViewById(R.id.et_namesheet);

        return new AlertDialog.Builder(getActivity())

                .setView(ll)

                .setTitle(getResources().getString(R.string.rs1))

                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        ListSheetAdapter.sheetsformove.get(ListSheetAdapter.sheetsformove.size()-1).setNametablesheet(v.getText().toString());

                        ListSheetAdapter.sheetsformove.get(ListSheetAdapter.sheetsformove.size()-1).updateDataBase();

                        sendResultListSheets();
                    }
                })

                        .create();


            }

private void sendResultListSheets()
{
   IInitList iil=(IInitList)getActivity().getSupportFragmentManager().findFragmentById(R.id.header);

    iil.m1_m();

    iil.initList();


}





}
