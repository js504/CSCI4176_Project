package in.geobullet.csci_4176_project;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.ResultReceiver;
import android.util.Log;

/**
 * Created by rkett on 4/4/2017.
 */

public class GeofenceResultReceiver extends ResultReceiver {
    public static String TAG = "GeofenceReceiver";
    private GeofenceReceiver receiver;
    public GeofenceResultReceiver(Handler h){
        // Retrieve from any handler
        super(h);
        Log.d(TAG, "result receiver being inited");
    }

    public interface GeofenceReceiver {
        public void onReceiveResult(int resultCode, Bundle resultData);
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
