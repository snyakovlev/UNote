package snyakovlev.unote;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewTable#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewTable extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    TableLayout tbl;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ViewTable() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.

     * @return A new instance of fragment ViewTable.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewTable newInstance(String param1) {
        ViewTable fragment = new ViewTable();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

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

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_view_table, container, false);

        tbl=root.findViewById(R.id.table_fvt);

        parse(mParam1);

        return root;
    }


    public void parse( String fileName) {

        ArrayList<Table> arrt=new ArrayList<>();


        ArrayList<ArrayList<String>> rows=new ArrayList<>();

        //инициализируем потоки
        String result = "";
        InputStream inputStream = null;
        HSSFWorkbook workBook = null;
        try {
            inputStream = new FileInputStream(fileName);
            workBook = new HSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //разбираем первый лист входного файла на объектную модель
        Sheet sheet = workBook.getSheetAt(0);


        // Определение граничных строк обработки
        int rowStart = Math.min(  0, sheet.getFirstRowNum());
        int rowEnd   = Math.max(100, sheet.getLastRowNum ());
        TableRow.LayoutParams lp = new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
        lp.setMargins(1, 0, 1, 0);
        TableLayout.LayoutParams lpp = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);

        lpp.setMargins(1, 1, 1, 1);
        for (int rw = rowStart; rw < rowEnd; rw++) {



            Row row = sheet.getRow(rw);
            if (row == null) {
                // System.out.println(
                //      "row '" + rw + "' is not created");
                continue;
            }
            short minCol = row.getFirstCellNum();
            short maxCol = row.getLastCellNum();

            ArrayList<String>  cellss=new ArrayList<>();
            for(short col = 0; col < maxCol; col++) {

                Cell cell = row.getCell(col);
                if (cell == null) {
                    cellss.add("--");
                    continue;
                }
                switch (cell.getCellTypeEnum()) {

                    case STRING:
                        result = cell.getStringCellValue();
                        cellss.add(result);
                        break;
                    case NUMERIC:
                        result = cell.getNumericCellValue() + "";
                        cellss.add(result);
                        break;

                    case FORMULA:
                        result = "--";
                        cellss.add(result);;
                        break;

                    case BLANK:
                        result = "--";
                        cellss.add(result);
                        break;
                    case BOOLEAN:
                        result = cell.getBooleanCellValue()+"";
                        cellss.add(result);
                        break;

                    case _NONE:
                        result = "--";
                        cellss.add(result);;
                        break;

                    case ERROR:
                        result = "--";
                        cellss.add(result);

                        break;
                    default:
                        result = "--";
                        cellss.add(result);
                        break;
                }




            }

            rows.add(cellss);

            Log.v("qqaass",result);

        }


       int max_count_cells=rows.get(0).size();




        for (int i=1;i<rows.size();i++)
        {
            max_count_cells=Math.max(max_count_cells,rows.get(i).size());
        }

        int z=0;

        boolean stop=false;
        for (int j=0;j<rows.size();j++) {

            for (int i = 0; i < max_count_cells; i++) {

                if (i>=rows.get(j).size())
                {
                    rows.get(j).add("");
                }


                if (!rows.get(j).get(i).equals(""))
                {
                    z=j;
                    stop=true;
                }


            }

            if (stop) break;
        }



        for (int j=z;j<rows.size();j++) {

            TableRow tr=new TableRow(getActivity());
            tr.setLayoutParams(lpp);
            tbl.addView(tr);




            for (int i = 0; i < max_count_cells; i++) {




                if (i>=rows.get(j).size())
                {
                    rows.get(j).add("--");


                }

                if (rows.get(j).get(i).equals("")) rows.get(j).set(i,"--");


              TextView tv=new TextView(getActivity());

              tv.setBackgroundColor(Color.WHITE);

              tv.setLayoutParams(lp);

              tv.setText(rows.get(j).get(i));

              tr.addView(tv);

                Log.v("qqaass",j+ "___"+rows.get(j).get(i));
            }


        }


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
