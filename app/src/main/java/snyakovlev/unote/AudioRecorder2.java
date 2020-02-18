package snyakovlev.unote;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Катя on 12.09.2016.
 */
public class AudioRecorder2 {

    final String TAG = "myLogs";



    int myBufferSize = 8192;
    AudioRecord audioRecord;
    boolean isReading = false;
    FileOutputStream fos;
    File outFile;

     AudioRecorder2(String filename) {

         File root = Environment.getExternalStorageDirectory();

         String root_str=root.getAbsolutePath();

         String my_dir=root_str+File.separator+"nbook";


         outFile = new File(my_dir,filename+".amr");
        int sampleRate = 8000;
        int channelConfig = AudioFormat.CHANNEL_IN_MONO;
        int audioFormat = AudioFormat.ENCODING_PCM_16BIT;

        int minInternalBufferSize = AudioRecord.getMinBufferSize(sampleRate,
                channelConfig, audioFormat);
        int internalBufferSize = minInternalBufferSize * 4;
        Log.v("ammm", "minInternalBufferSize = " + minInternalBufferSize
                + ", internalBufferSize = " + internalBufferSize
                + ", myBufferSize = " + myBufferSize);

        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                sampleRate, channelConfig, audioFormat, internalBufferSize);
         try {
              fos=new FileOutputStream(outFile,true);
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         }
     }

    public void recordStart() {
        Log.v("amm", "record start");
        audioRecord.startRecording();
        int recordingState = audioRecord.getRecordingState();
        Log.v("amm", "recordingState = " + recordingState);
    }

    public void recordStop() {
        Log.v("amm", "record stop");
        audioRecord.stop();
    }
    byte[] myBufferr;


    public void readStart() {
        Log.v("amm", "read start");
        isReading = true;
        new Thread(new Runnable() {
          @Override
          public void run() {
              if (audioRecord == null)
                  return;

           byte[]  myBuffer = new byte[myBufferSize];
              int readCount = 0;
              int totalCount = 0;
              while (isReading) {
                  readCount = audioRecord.read(myBuffer, 0, myBufferSize);
                  myBufferr=myBuffer;
                  totalCount += readCount;
                  Log.v("amm", "readCount = " + readCount + ", totalCount = "
                          + totalCount);
              }
          }
      }).start();


    }

    public void readStop() {
        Log.v("amm", "read stop");
        isReading = false;

        try {
            fos.write(myBufferr,0,myBufferSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
public void destroy()
{
    if (audioRecord != null) {
        audioRecord.release();
    }
}

}
