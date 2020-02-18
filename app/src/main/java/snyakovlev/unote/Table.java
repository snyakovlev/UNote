package snyakovlev.unote;

/**
 * Created by Катя on 03.03.2017.
 */

public class Table {

    String uuidrow;

    String namerow;

    Col[] col;

    public Table(int count_col) {
        col=new Col[count_col];
        for (int i=0;i<count_col;i++)
        {
            Col col=new Col();
            this.col[i]=col;
        }
    }



    public String getNamerow() {

        return namerow;
    }

    public void setNamerow(String namerow) {
        this.namerow = namerow;
    }

    public String getUuidrow() {

        return uuidrow;
    }

    public void setUuidrow(String uuidrow) {
        this.uuidrow = uuidrow;
    }




}

class Col {

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    private String col="";

}
