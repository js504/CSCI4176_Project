package in.geobullet.csci_4176_project;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



public class Poster_Look extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poster_look);
        String passed_postername = null;
        String poster_details = null;
        String poster_address = null;
        String poster_sdate = null;
        String poster_edate = null;
        String poster_cost = null;

        Bundle bundle = getIntent().getExtras();
        passed_postername = bundle.getString("postername");
        poster_details = bundle.getString("posterdetails");
        poster_address = bundle.getString("posteraddress");
        poster_sdate = bundle.getString("postersdate");
        poster_edate = bundle.getString("posteredate");
        poster_cost = bundle.getString("postercost");

        ImageView iv = new ImageView(this);
        TextView details = new TextView(this);
        TextView address = new TextView(this);
        TextView startdate = new TextView(this);
        TextView enddate = new TextView(this);
        TextView cost = new TextView(this);

        LinearLayout LinLayout = (LinearLayout) findViewById(R.id.lin_in_scroll);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        int resID = this.getResources().getIdentifier(passed_postername, "drawable", this.getPackageName());
        iv.setImageResource(resID);

        params.setMargins(0, 0, 0, 0);
        iv.setLayoutParams(params);
        LinLayout.addView(iv);

        details.setText("Details: " + poster_details);
        params.setMargins(10, 0, 0, 0);
        details.setLayoutParams(params);
        details.setTextSize(20);
        details.setTypeface(null, Typeface.BOLD_ITALIC);
        LinLayout.addView(details);

        address.setText("Address: " + poster_address);
        params.setMargins(10, 20, 0, 0);
        address.setLayoutParams(params);
        address.setTextSize(20);
        address.setTypeface(null, Typeface.BOLD_ITALIC);
        LinLayout.addView(address);

        startdate.setText("Start Date/Time: " + poster_sdate);
        params.setMargins(10, 20, 0, 0);
        startdate.setLayoutParams(params);
        startdate.setTextSize(20);
        startdate.setTypeface(null, Typeface.BOLD_ITALIC);
        LinLayout.addView(startdate);

        enddate.setText("End Date/Time: " + poster_edate);
        params.setMargins(10, 20, 0, 0);
        enddate.setLayoutParams(params);
        enddate.setTextSize(20);
        enddate.setTypeface(null, Typeface.BOLD_ITALIC);
        LinLayout.addView(enddate);

        cost.setText("Cost: " + poster_cost);
        params.setMargins(10, 20, 0, 0);
        cost.setLayoutParams(params);
        cost.setTextSize(20);
        cost.setTypeface(null, Typeface.BOLD_ITALIC);
        LinLayout.addView(cost);

    }
}
