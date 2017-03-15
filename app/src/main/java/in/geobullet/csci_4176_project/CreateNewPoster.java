package in.geobullet.csci_4176_project;


import android.app.DatePickerDialog;
import android.app.Dialog;
import java.util.Calendar;

import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 *
 * This class represents the Add new poster view.  It allows users to enter details about a new event or service
 * and have the poster uploaded to the poll they have selected.
 *
 */

public class CreateNewPoster extends AppCompatActivity {

    //todo Create function for posting the poster and make sure all required fields are populated
    //todo Create function for uploading pictures from photo gallery
    //todo polish up UI with some colour and stuff

    //Labels of buttons
    private static final String START_DATE_LABEL = "Start Date";
    private static final String END_DATE_LABEL = "End Date";
    private static final String START_TIME_LABEL = "Start Time";
    private static final String END_TIME_LABEL = "End Time";


    //Layout radio buttons
    private RadioButton eventRadioButton;
    private RadioButton serviceRadioButton;

    private EditText title;
    private EditText locationAddress;
    private EditText city;
    private EditText details;

    //Layout textviews for displaying time and date
    //Made static to be used with the date and time picker fragments
    private  TextView startDate;
    private  TextView endDate;
    private  TextView startTime;
    private  TextView endTime;

    //Layout buttons to activate date and time pickers
    private Button startDateButton;
    private Button endDateButton;
    private Button startTimeButton;
    private Button endTimeButton;
    private Button browsePostersButton;
    private Button submitPosterButton;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_poster);

        //get reference to radio buttons
        eventRadioButton = (RadioButton)findViewById(R.id.event_radio_button);
        serviceRadioButton = (RadioButton)findViewById(R.id.service_radio_button);

        //Get reference to edit texts
        title = (EditText)findViewById(R.id.title_edit_text);
        locationAddress = (EditText)findViewById(R.id.address_edit_text);
        city = (EditText)findViewById(R.id.city_edit_text);
        details = (EditText)findViewById(R.id.details_text_area);


        //Get references to text views
        startDate = (TextView)findViewById(R.id.start_date_text_view);
        endDate = (TextView)findViewById(R.id.end_date_text_view);
        startTime = (TextView)findViewById(R.id.start_time_text_view);
        endTime = (TextView)findViewById(R.id.end_time_text_view);

        //get references to buttons
        startDateButton = (Button)findViewById(R.id.select_start_date_button);
        endDateButton = (Button)findViewById(R.id.select_end_date_button);
        startTimeButton = (Button)findViewById(R.id.start_time_button);
        endTimeButton = (Button)findViewById(R.id.end_time_button);
        browsePostersButton = (Button)findViewById(R.id.browse_posters_button);
        submitPosterButton = (Button)findViewById(R.id.submit_poster_button);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    /**
     * OnClick function for the event type selection.  Upon selecting a service the
     * time and end date views are removed from the UI.  When selecting an event selecting
     * time and date views are displayed.
     *
     * @param v  The radio button that has been clicked
     */
    public void onEventOrServiceClick(View v){
        RadioButton rb = (RadioButton)v;

        if(rb.getText().toString().equals("Event")){
            serviceRadioButton.setChecked(false);

            //show the end date and time labels
            endDate.setVisibility(View.VISIBLE);
            startTime.setVisibility(View.VISIBLE);
            endTime.setVisibility(View.VISIBLE);

            //show the end date and time buttons
            endDateButton.setVisibility(View.VISIBLE);
            startTimeButton.setVisibility(View.VISIBLE);
            endTimeButton.setVisibility(View.VISIBLE);

        }
        else if(rb.getText().toString().equals("Service"))
        {
            eventRadioButton.setChecked(false);

            //hide the end date and time labels
            endDate.setVisibility(View.GONE);
            startTime.setVisibility(View.GONE);
            endTime.setVisibility(View.GONE);

            //hide the end date and time buttons
            endDateButton.setVisibility(View.GONE);
            startTimeButton.setVisibility(View.GONE);
            endTimeButton.setVisibility(View.GONE);
        }
    }

    /**
     * OnClick function for the set time and date buttons.  Depending on which button is selected
     * either a date picker dialog or time picker dialog is shown.  The selected date and time are
     * then displayed in the corresponding label next to the button.
     *
     * @param v  The button clicked to display a date or time picker dialog
     */
    public void showDialog(View v) {

        Button button = (Button)v;

        Bundle bundle = new Bundle();

        DialogFragment newFragment = null;

        if(button.getText().toString().equals(START_DATE_LABEL)){

            //Create a  bundle to let the dialog know this is the start date
            bundle.putString(START_DATE_LABEL, START_DATE_LABEL);

            //Create new date picker fragment
            newFragment = new DatePickerFragment();
        }
        else if(button.getText().equals(END_DATE_LABEL)){

            //Create a  bundle to let the dialog know this is the end date
            bundle.putString(END_DATE_LABEL, END_DATE_LABEL);

            //Create new date picker fragment
            newFragment = new DatePickerFragment();
        }
        else if(button.getText().equals(START_TIME_LABEL)){

            //Create a  bundle to let the dialog know this is the start time
            bundle.putString(START_TIME_LABEL, START_TIME_LABEL);

            //Create new time picker fragment
            newFragment = new TimePickerFragment();
        }
        else if(button.getText().equals(END_TIME_LABEL)){

            //Create a  bundle to let the dialog know this is the end time
            bundle.putString(END_TIME_LABEL, END_TIME_LABEL);

            //Create new time picker fragment
            newFragment = new TimePickerFragment();
        }

        if(newFragment != null) {
            //send bundle to new fragment
            newFragment.setArguments(bundle);

            //show the fragment
            newFragment.show(getSupportFragmentManager(), "Picker");
        }



    }

    public void Test(){

    }


    /**
     * Fragment for displaying the date picker dialog
     *
     */
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {


        private String key = "";


        /**
         * Called when the dialog is created.  Data is gathered from the bundle and
         * the date is set to the current date in the picker.
         *
         * @param savedInstanceState    saved info from last created instance
         * @return a new date picker dialog with the current date already set
         */
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            Bundle bundle = getArguments();

            if (bundle.getString(START_DATE_LABEL) != null) {
                key = bundle.getString(START_DATE_LABEL);
            } else if (bundle.getString(END_DATE_LABEL) != null) {
                key = bundle.getString(END_DATE_LABEL);
            }

            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        /**
         * Called when the user has selected the date they want
         *
         * @param view      The date picker dialog
         * @param year      The year selected
         * @param month     The month selected
         * @param day       The day selected
         */
        public void onDateSet(DatePicker view, int year, int month, int day) {

            String date =  Integer.toString(day) + "-" + Integer.toString(month) + "-" + Integer.toString(year);
            //Set the date based on whether its the start or end date
            if(key.equals(START_DATE_LABEL)){
                TextView startDate = (TextView)getActivity().findViewById(R.id.start_date_text_view);
                date = "Start Date: " + date;
                startDate.setText(date);
            }
            else if(key.equals(END_DATE_LABEL)){
                TextView endDate = (TextView)getActivity().findViewById(R.id.end_date_text_view);
                date = "End Date: " + date;
                endDate.setText(date);
            }

        }
    }

    /**
     * Fragment for displaying the time picker dialog
     *
     */
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {


        private String key = "";


        /**
         * Called when the dialog is created.  Data is gathered from the bundle to determin
         * whether its the start or end time
         *
         * @param savedInstanceState    saved info from last created instance
         * @return a new time picker dialog shown without 24 hour mode enabled
         */
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            Bundle bundle = getArguments();

            if (bundle.getString(START_TIME_LABEL) != null) {
                key = bundle.getString(START_TIME_LABEL);
            } else if (bundle.getString(END_TIME_LABEL) != null) {
                key = bundle.getString(END_TIME_LABEL);
            }

            return new TimePickerDialog(getActivity(), this, 12, 0, false);
        }

        /**
         * Called when the user has selected the time they wish and converts it from 24 hour
         * to 12 hour view.
         *
         * @param view          The time picker dialog
         * @param hourOfDay     The hour of the day selected, in 24 hour time
         * @param minute        The minute of the day selected
         */
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {



            int noTwntFourHour = hourOfDay;
            String min = Integer.toString(minute);
            String amPm = "am";

            //Check if the time is greater than or equal to 12. if it is subtract 12 to make it
            //in 12 hour time and set am - pm to pm
            if(hourOfDay >= 12){
                noTwntFourHour = hourOfDay - 12;
                amPm = "pm";
            }

            //If the hour ends up being a 0 set it to 12
            if(hourOfDay == 0 || noTwntFourHour == 0){
                noTwntFourHour = 12;
            }

            //if the min is less than 10 then add an extra 0 for the proper display
            if(minute < 10){
                min = "0" + min;
            }


            String time = Integer.toString(noTwntFourHour) + ":" + min + amPm;

            //Set the time based on whether its the start or end time
            if (key.equals(START_TIME_LABEL)) {
                TextView startTime = (TextView)getActivity().findViewById(R.id.start_time_text_view);
                time = "Start Time: " + time;
                startTime.setText(time);
            } else if (key.equals(END_TIME_LABEL)) {
                TextView endTime = (TextView)getActivity().findViewById(R.id.end_time_text_view);

                time = "End Time: " + time;
                endTime.setText(time);
            }
        }
    }
}