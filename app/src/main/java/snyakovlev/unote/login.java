package snyakovlev.unote;

import android.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * Created bymКатя on 21.08.2015.
 */
public class login extends Fragment implements View.OnClickListener {

    EditText epass;

    Button password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View root=inflater.inflate(R.layout.login, container, false);

        epass=(EditText)root.findViewById(R.id.epass);

        password=(Button)root.findViewById(R.id.password);

        password.setOnClickListener( this);

        return root;
    }

    interface IshowWorkList
    {
        void showWorkList();
    }

    @Override
    public void onClick(View v) {

        String pass=getActivity().getSharedPreferences("password",getActivity().MODE_PRIVATE).getString("pas", "admin");

        Log.v("mylog", "pass2=" + pass);

        if (epass.getText().toString().equalsIgnoreCase(pass))
        {
          IshowWorkList ishwl=(IshowWorkList)getActivity();

            ishwl.showWorkList();
        }
        else
        {


        }

    }
}
