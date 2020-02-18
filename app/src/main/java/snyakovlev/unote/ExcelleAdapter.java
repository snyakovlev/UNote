package snyakovlev.unote;

import android.util.Log;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

class ExcelleAdapter
{
    int col_table;

    public ExcelleAdapter() {
    }

    ArrayList<Table> Excelle2Table(Glavnoe_Activity glav, String fileName)
    {
        return   parse(glav,fileName);
    }

    int col=0;

    int max_count_cells=0;

    public  ArrayList<Table> parse(Glavnoe_Activity glav,String fileName) {

        ArrayList<Table> arrt=new ArrayList<>();


        ArrayList<ArrayList<String>> rows=new ArrayList<>();



        DataBaseInterface db=new DataBaseInterface(glav);

        db.getWritableDatabase();

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



            Log.v("qqaass",result);
            rows.add(cellss);
        }

        max_count_cells=rows.get(0).size();




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

            String idrow=new CreateUID(glav).creatingUID() ;

            Table t = new Table(max_count_cells);

            if (j==z) {

                t.setUuidrow("0");

                t.setNamerow("");
            }

            else if (j>z) {
                t.setUuidrow(idrow);

                t.setNamerow("");
            }



            for (int i = 0; i < max_count_cells; i++) {




                if (i>=rows.get(j).size())
                {
                    rows.get(j).add("--");

                    t.col[i].setCol(rows.get(j).get(i));
                }

                if (rows.get(j).get(i).equals("")) rows.get(j).set(i,"--");


                t.col[i].setCol(rows.get(j).get(i));

                Log.v("qqaass",j+ "___"+rows.get(j).get(i));
            }

            arrt.add(t);



        }


        return arrt ;
    }

    boolean noheader;
    boolean header;
    boolean pusto=true;


    int j=0;
}

