package in.geobullet.csci_4176_project;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.os.ResultReceiver;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;


/**
 * Created by rkett on 3/23/2017.
 */

public class GeofenceIntentHandler extends IntentService {
    public static final String TAG = "GeofenceHandler";
    ResultReceiver resultReceiver;

    public GeofenceIntentHandler(){
       super(TAG);
    }

    @Override
    public void onCreate(){ super.onCreate(); }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        resultReceiver = intent.getParcelableExtra("ResultReceiver");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent){
        GeofencingEvent event = GeofencingEvent.fromIntent(intent);

        // On the GeoFence being broken, need to:
        // > Delete the current geofence, so it can't be retriggered; and to prevent leaks
        // > Build a new Geofence around the current location


        if(event.hasError()){
            // Handle errors
            return;
        }
        int transition = event.getGeofenceTransition();
        if(transition == Geofence.GEOFENCE_TRANSITION_EXIT){
            // Need to delete the current fence, and create a new one at the current location
            List<Geofence> fenceList = event.getTriggeringGeofences();
            String reqID = fenceList.get(0).getRequestId();
            Bundle b = new Bundle();
            b.putString("reqID", reqID);
            b.putParcelable("Location", event.getTriggeringLocation());
            resultReceiver.send(Activity.RESULT_OK, b);
        }


    }
}
