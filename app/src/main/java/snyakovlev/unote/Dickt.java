package snyakovlev.unote;

import android.app.Notification;
import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Dickt extends JobService {

    static boolean in=false;

    MediaRecorder mr;
    Notification not;
    File outFile;
    String filename;
    boolean start;
   static String tel;
    static final String prefics="com.yakovlev.sergey.android.notebook.";

    public Dickt() {
    }



    @Override
    public boolean onStartJob(JobParameters intent) {


        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("dfgh","serv_created");
        mr=new MediaRecorder();
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Notification.Builder nb = new Notification.Builder(getApplicationContext())
//NotificationCompat.Builder nb = new NotificationBuilder(context) //для версии Android > 3.0
                .setSmallIcon(R.drawable.mic) //иконка уведомления
                .setAutoCancel(true) //уведомление закроется по клику на него
                .setTicker("UNotes") //текст, который отобразится вверху статус-бара при создании уведомления
                .setContentText(getResources().getString(R.string.d1)) // Основной текст уведомления
                .setWhen(System.currentTimeMillis())
                .setContentTitle("UNotes"); //отображаемое время уведомления
        Notification notification = nb.getNotification();

        if (intent != null && intent.getStringExtra("stop").equals("start")) {
            start = true;
            if (intent.getStringExtra("tel")==null)
            {tel="";}
            else {
                tel = intent.getStringExtra("tel");
            }
            initMR();
            recordStart();
            startForeground(startId,notification);
            //  crnot();
            Log.v("dfghhg", "onStartCommand");

        }

        else
        {
            this.stopSelf(startId);
        }

        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {

        recordStop();

        Intent i=new Intent(this,Phone.class);
        // i.putExtra("create","mickr");

        i.putExtra("filename",filename);

        i.setAction(prefics+"q_mickr0");

        i.putExtra("tel",tel);

      //  i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        this.sendBroadcast(i);

        super.onDestroy();
    }

    public  String crUID()
    {
        String tS=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        return  "table_"+tS;
    }

    void initMR()
    {


        String filename = crUID();

        File root;

        if (isAvailable() || !isReadOnly()) {

            root = Environment.getExternalStorageDirectory();

            if (root == null) {

                root = this.getFilesDir();
            }
        } else {
            root = this.getFilesDir();
        }

        String root_str = root.getAbsolutePath();

        String my_dir = root_str + File.separator + "nbook" + File.separator + "audio";


        outFile = new File(my_dir, filename + ".3gp");

        this.filename = filename;

        Boolean filecreated = outFile.mkdirs();

        Log.v("gg", outFile.toString());
    }

    private static boolean isReadOnly() {
        String storageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(storageState);
    }

    private static boolean isAvailable() {
        String storageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(storageState);
    }

    public void recordStart() {
        try {
            releaseRecorder();


            if (outFile.exists()) {
                outFile.delete();
            }
            mr = new MediaRecorder();
            if (tel.equals("")) {
                mr.setAudioSource(MediaRecorder.AudioSource.MIC);
            }
            else
            {
                mr.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
            }
            mr.setAudioSamplingRate(8000);
            mr.setAudioEncodingBitRate(12200);
            mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mr.setOutputFile(String.valueOf(outFile));
            mr.prepare();
            mr.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void releaseRecorder() {
        if (mr != null) {
            mr.release();
            mr = null;
        }
    }

    public void recordStop() {
        if (mr != null) {

            try {
                mr.stop();
            }
            catch (IllegalStateException e)
            {

            }
        }

        releaseRecorder();
    }



}
