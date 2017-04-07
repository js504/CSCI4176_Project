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
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        // Initialize the Result Receiver at startup of the service
        resultReceiver = intent.getParcelableExtra(GeofenceResultReceiver.TAG);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent){
        // Get the geofencing event
        GeofencingEvent event = GeofencingEvent.fromIntent(intent);

        if(event.hasError()){
            // Handle errors
            Log.d(TAG, "Errors encountered");
            return;
        }
        int transition = event.getGeofenceTransition();
        // Send off the transition to the result receiver
        resultReceiver.send(transition, null);
    }
}
