package snyakovlev.unote;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by Катя on 14.12.2016.
 */

public class AudioPlayer {

    public MediaPlayer mediaPlayer;

    boolean playing=false;

    File outFile;

    String filename;

   WorkAdapter.MyViewHolder2 vh=null;

    WorkList wl;

    Activity activ;

    AudioPlayer(String filename,Activity activity,WorkAdapter.MyViewHolder2 vh,WorkList wl)
    {
        this.vh=vh;

        this.wl=wl;

        this.activ=activity;

        File root;

        if (isAvailable() || !isReadOnly()) {

            root = Environment.getExternalStorageDirectory();

            if (root == null) {

                root = activity.getFilesDir();
            }
        }

        else
        {
            root=activity.getFilesDir();
        }

        String root_str=root.getAbsolutePath();

        String my_dir=root_str+File.separator+"nbook"+File.separator+"audio";


        outFile = new File(my_dir,filename+".3gp");

        if (!outFile.exists()) outFile=new File(my_dir,filename+".amr");

        this.filename=outFile.getAbsolutePath();

        Boolean filecreated=outFile.mkdirs();

        Log.v("mmm", outFile.toString());

    }

    private static boolean isReadOnly() {
        String storageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(storageState);
    }

    // проверяем есть ли доступ к внешнему хранилищу
    private static boolean isAvailable() {
        String storageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(storageState);
    }

    public void playStart() {
        try {

            Log.v("gg", "play_start"+outFile.toString());
            playing=true;
            releasePlayer(null);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    vh.sb.setProgress(0);
                    releasePlayer(vh);
                }
            });
            mediaPlayer.setDataSource(String.valueOf(outFile));
            mediaPlayer.setLooping(false);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void releasePlayer(WorkAdapter.MyViewHolder2 vh) {
        if (mediaPlayer != null) {



            if (playing)
                try {
                    mediaPlayer.stop();
                }
                catch (Exception e)
                {
                    return;
                }
            playing=false;
            mediaPlayer.release();
           if (vh!=null) wl.adapter.audiostop(vh);
            mediaPlayer = null;
        }
    }
}
