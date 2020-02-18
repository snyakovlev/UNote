package snyakovlev.unote;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class Colrowedit extends Fragment {

    EditTable et;
    Button pcol, mcol, prow, mrow;

    public Colrowedit() {
        // Required empty public constructor
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        et = (EditTable) getActivity().getSupportFragmentManager().findFragmentById(R.id.header);
        View root = inflater.inflate(R.layout.col_row_edit, container, false);

        pcol = (Button) root.findViewById(R.id.pcol);
        pcol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.plusColumn();
            }
        });
        mcol = (Button) root.findViewById(R.id.mcol);
        mcol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.minusColumn();
            }
        });
        prow = (Button) root.findViewById(R.id.prow);
        prow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                et.plusRow();
            }
        });
        mrow = (Button) root.findViewById(R.id.mrow);
        mrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.minusRow();
            }
        });


        return root;
    }

}
