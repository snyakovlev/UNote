package snyakovlev.unote;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.widget.Toast;
import java.util.List;

 class VoiceRecognition {

    Activity cont;

    String message;

    private  static  final int VOICE_RECOGNITION_REQUEST_CODE = 121;

   public VoiceRecognition(Activity cont)
    {
        this.cont=cont;
    }




    public  boolean  CheckVoiceRecognition(){
        PackageManager pm = cont.getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH),0);
        if (activities.size()==0){

            message=cont.getResources().getString(R.string.error_voice_recognition);


            Toast.makeText(cont, message, Toast.LENGTH_LONG).show();

            return false;
        }

        return true;
    }

    public void speak(){

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getClass().getPackage().getName());

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);

        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);

        cont.startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }





}
