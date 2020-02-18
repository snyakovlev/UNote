package snyakovlev.unote;

/**
 * Created by Катя on 06.09.2016.
 */


import java.io.File;
import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import snyakovlev.unote.CurrSave;


public class AudioRecorder
    {

        File outFile;

        private MediaRecorder mediaRecorder;

       public  String filename=null;

        AudioRecorder(Activity activity,CurrSave currsave)
        {
            Log.v("gg",currsave.getFilename()+"sds");

            if (currsave.getFilename().equals("") | currsave.getFilename()==null) {

                CreateUID cruid = new CreateUID(activity);

                String filename = cruid.creatingUID();

                File root;

                if (isAvailable() || !isReadOnly()) {

                    root = Environment.getExternalStorageDirectory();

                    if (root == null) {

                        root = activity.getFilesDir();
                    }
                } else {
                    root = activity.getFilesDir();
                }

                String root_str = root.getAbsolutePath();

                String my_dir = root_str + File.separator + "nbook" + File.separator + "audio";


                outFile = new File(my_dir, filename + ".3gp");

                this.filename = filename;

                Boolean filecreated = outFile.mkdirs();

                Log.v("gg", outFile.toString());
            }

            else

            {

                filename=currsave.getFilename();

                File root;

                if (isAvailable() || !isReadOnly()) {

                    root = Environment.getExternalStorageDirectory();

                    if (root == null) {

                        root = activity.getFilesDir();
                    }
                } else {
                    root = activity.getFilesDir();
                }

                String root_str = root.getAbsolutePath();

                String my_dir = root_str + File.separator + "nbook" + File.separator + "audio";


                outFile = new File(my_dir, filename + ".3gp");

                this.filename = filename;
            }

        }
        public void recordStart() {
            try {
                releaseRecorder();


                if (outFile.exists()) {
                    outFile.delete();
                }
                mediaRecorder = new MediaRecorder();
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setAudioSamplingRate(8000);
                mediaRecorder.setAudioEncodingBitRate(12200);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mediaRecorder.setOutputFile(String.valueOf(outFile));
                mediaRecorder.prepare();
                mediaRecorder.start();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        public void recordStop() {
            if (mediaRecorder != null) {
                mediaRecorder.stop();



            }

            releaseRecorder();
        }






        private void releaseRecorder() {
            if (mediaRecorder != null) {
                mediaRecorder.release();
                mediaRecorder = null;
            }
        }





        private static boolean isReadOnly() {
            String storageState = Environment.getExternalStorageState();
            return Environment.MEDIA_MOUNTED_READ_ONLY.equals(storageState);
        }

        private static boolean isAvailable() {
            String storageState = Environment.getExternalStorageState();
            return Environment.MEDIA_MOUNTED.equals(storageState);
        }

    }




