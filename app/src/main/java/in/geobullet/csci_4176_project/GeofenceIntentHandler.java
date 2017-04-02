package in.geobullet.csci_4176_project;

import android.app.IntentService;
import android.content.Intent;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

/**
 * Created by rkett on 3/23/2017.
 */

public class GeofenceIntentHandler extends IntentService {
    public static final String TAG = "GeofenceHandler";
    public GeofenceIntentHandler(){
       super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent){
        GeofencingEvent event = GeofencingEvent.fromIntent(intent);
        if(event.hasError()){
            // Handle errors
            return;
        }
        int transition = event.getGeofenceTransition();
        if(transition == Geofence.GEOFENCE_TRANSITION_EXIT){
            // Need to delete the current fence, and create anew one at the current location
        }

    }
}
