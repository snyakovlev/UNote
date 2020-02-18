package snyakovlev.unote;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VImage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VImage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageViewTouch imt;


    public VImage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VImage.
     */
    // TODO: Rename and change types and number of parameters
    public static VImage newInstance(String param1, String param2) {
        VImage fragment = new VImage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_vimage, container, false);

        getActivity().setTitle("ImageView");

        imt=root.findViewById(R.id.vim);

        imt.setDisplayType(ImageViewTouchBase.DisplayType.FIT_IF_BIGGER);

        Picasso pic=Picasso.with(getActivity());

        if ( mParam2.equals("png") || mParam2.equals("jpg")) {




            if (mParam1 != null) {


                Uri f = null;

                if (Build.VERSION.SDK_INT >= 24) {
                    f = FileProvider.getUriForFile(getActivity(), "snyakovlev.unote.provider", new File(mParam1));
                } else {
                    f = Uri.fromFile(new File(mParam1));
                }

               // imt.setImageURI(f);
                Log.v("aqwew", f.toString());




                 pic.load(new File(mParam1)).fit().centerInside().into(imt);

                 pic.invalidate(new File(mParam1));
            }
        }

        if ( mParam2.equals("jpg")) {

            Bitmap b= BitmapFactory.decodeFile(mParam1);

            imt.setImageBitmap(b);

        }


        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_main_vimage, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.close) {
            getActivity().finish();
        }

        return true;
    }



}
