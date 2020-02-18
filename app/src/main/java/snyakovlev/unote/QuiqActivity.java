package snyakovlev.unote;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


public class QuiqActivity extends AppCompatActivity {
    ImageButton bqphoto,bqpaint,bqaudio,bqtext,bqnull;
    boolean d;
    void permRecord2()
    {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED)

        {
            dialogPerm();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10001);
        }

    }

    void permPhoto()
    {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED)

        {
            dialogPerm();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 20000);
        }
        else
        {
            startPhoto();
        }

    }

    void dialogPerm()
    {
        Toast.makeText(this,"Для корректной работы приложения необходимо получить разрешения на работу " +
                "с некоторыми функциями телефона (планшета), таковы требования ОС \"Android\"," +
                " если Вы не " +
                "дадите разрешение, то к сожалению, приложение завершится",Toast.LENGTH_LONG).show();

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 10001) {

            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                startDic();

            } else

            {
                Toast.makeText(this, "Разрешения не получены.", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Приложение завершено", Toast.LENGTH_SHORT).show();
                this.finish();
            }


        }

        if (requestCode == 20000) {

            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                startPhoto();

            } else

            {
                Toast.makeText(this, "Разрешения не получены.", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Приложение завершено", Toast.LENGTH_SHORT).show();
                this.finish();
            }


        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        overridePendingTransition(R.anim.scale, R.anim.scale2);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quiq);

        bqphoto=(ImageButton)findViewById(R.id.bqphoto);

        bqphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              qphoto();
            }
        });

        findViewById(R.id.bqpaint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qpaint();
            }
        });

        findViewById(R.id.bqtext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qtext();
            }
        });

        bqaudio=(ImageButton)findViewById(R.id.bqmickr);

        bqaudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!d) {

                    sdic();

                }
                else
                {
                    stopDic();
                    d=false;
                }

            }
        });


    }

    void sdic()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)!=PackageManager.PERMISSION_GRANTED) {
            permRecord2();
        }
        else
        {
            startDic();
        }
        d=true;
    }

    void startDic()
    {
        bqaudio.setImageDrawable(getResources().getDrawable(R.drawable.nomic));
        Intent i = new Intent(this, Dickt.class);
        i.putExtra("stop", "start");
        this.startService(i);
    }

    void stopDic()
    {
        bqaudio.setImageDrawable(getResources().getDrawable(R.drawable.mic));
        Intent i = new Intent(this, Dickt.class);
        i.putExtra("stop", "stop");
        this.stopService(i);
    }

    @Override
    protected void onPause() {
        overridePendingTransition(R.anim.scale, R.anim.scale2);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        overridePendingTransition(R.anim.scale, R.anim.scale2);
        super.onDestroy();
    }

    void qphoto()
    {
        permPhoto();

    }

    void startPhoto()
    {
        Intent i=new Intent(this,Glavnoe_Activity.class);
        i.putExtra("create","photo");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        this.finish();
    }
    void qpaint()
    {
        Intent i=new Intent(this,Glavnoe_Activity.class);
        i.putExtra("create","paint");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        this.finish();
    }

    void qtext()
    {
        Intent i=new Intent(this,Glavnoe_Activity.class);
        i.putExtra("create","text");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        this.finish();
    }

    void qnull()
    {
        Intent i=new Intent(this,Glavnoe_Activity.class);
        i.putExtra("create","mickr");
        i.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        startActivity(i);
        this.finish();
    }

}
