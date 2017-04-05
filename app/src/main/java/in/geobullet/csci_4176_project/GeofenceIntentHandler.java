package in.geobullet.csci_4176_project;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.os.ResultReceiver;
import android.util.Log;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;


public class GeofenceIntentHandler extends IntentService {
    public static final String TAG = "GfHandler";
    ResultReceiver resultReceiver = null;

    public GeofenceIntentHandler(){
        super(TAG);
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        resultReceiver = intent.getParcelableExtra(GeofenceResultReceiver.TAG);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent){
        GeofencingEvent event = GeofencingEvent.fromIntent(intent);
        Log.d(TAG, "Intent handler triggered");

        if(event.hasError()){
            // Handle errors
            Log.d(TAG, "Errors encountered");
            return;
        }
        int transition = event.getGeofenceTransition();
        event = null;
        resultReceiver.send(transition, null);
    }
}