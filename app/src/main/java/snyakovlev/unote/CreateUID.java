package snyakovlev.unote;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Катя on 23.09.2016.
 */
public  class CreateUID
{
   Context context;

    public CreateUID(Context context)
    {
        this.context=context;
    }


  public  String creatingUID()
    {
        String tS=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        return  "'"+UUID.randomUUID().toString().replace("-","0").toString()+"'";
    }


}
