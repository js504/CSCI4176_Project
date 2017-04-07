package in.geobullet.csci_4176_project;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;
import android.util.Log;

// Asynchronous receiver for the Geofence Intent service.
// Provides an interface for the MapsActivity to implement
public class GeofenceResultReceiver extends ResultReceiver {
    public static String TAG = "GeofenceReceiver";
    private GeofenceReceiver receiver;
    public GeofenceResultReceiver(Handler h){
        // Retrieve from any handler
        super(h);
        Log.d(TAG, "result receiver being inited");
    }

    public interface GeofenceReceiver {
        void onReceiveResult(int resultCode, Bundle resultData);
    }

    public void setGFReceiver(GeofenceReceiver r) {
        receiver = r;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if(receiver != null){
            receiver.onReceiveResult(resultCode, resultData);
        }

    }
}
