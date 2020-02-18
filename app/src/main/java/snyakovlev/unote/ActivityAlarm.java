package snyakovlev.unote;

import android.content.Context;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ActivityAlarm extends AppCompatActivity {

    TextView alarm_text;
    ImageButton alarm_cancel;
    Ringtone rr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        setTitle(getResources().getString(R.string.aa1));
        String text="";
        Log.v("provrka3",getIntent().getStringExtra("mess"));
       text =getIntent().getStringExtra("mess");
        alarm_text=(TextView)findViewById(R.id.alarm_text);
        alarm_cancel=(ImageButton)findViewById(R.id.alarm_cancel);

        AudioManager aum = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        this.setVolumeControlStream(AudioManager.STREAM_RING);
        int max = aum.getStreamMaxVolume(AudioManager.STREAM_RING);

        aum.setStreamVolume(AudioManager.STREAM_RING, max, AudioManager.FLAG_PLAY_SOUND);
        Uri notify = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notify);
        rr=r;
        alarm_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r.stop();

                finish();
            }
        });

        try {
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
       alarm_text.setText(text);


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (rr!=null && rr.isPlaying())
        {
            rr.stop();
        }
    }
}
