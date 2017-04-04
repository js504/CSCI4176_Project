package in.geobullet.csci_4176_project;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import in.geobullet.csci_4176_project.Database.Classes.Board;
import in.geobullet.csci_4176_project.Database.DatabaseHandler;
import in.geobullet.csci_4176_project.Shared.SessionData;

public class EditBoard extends AppCompatActivity {


    //Labels of buttons
    private static final String START_DATE_LABEL = "Start Date";
    private static final String END_DATE_LABEL = "End Date";
    private static final String START_TIME_LABEL = "Start Time";
    private static final String END_TIME_LABEL = "End Time";

    private static final String START_DATE = "Start Date:";
    private static final String END_DATE = "End Date:";
    private static final String START_TIME = "Start Time:";
    private static final String END_TIME = "End Time:";

    private TextView board_name;
    private TextView radius;
    private TextView startDate;
    private TextView endDate;
    private TextView errorTv;


    //Layout buttons to activate date and time pickers
    private Button startDateButton;
    private Button endDateButton;
    private Button startTimeButton;
    private Button endTimeButton;
    private Button browsePostersButton;
    private Button submitPosterButton;

    private final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Nov", "Dec"};


    Board current = new Board();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_board);


        final DatabaseHandler db = new DatabaseHandler(this);


        //textview reference
        board_name = (TextView) findViewById(R.id.edit_board_name);
        radius = (TextView) findViewById(R.id.edit_radius);
        startDate = (TextView)findViewById(R.id.start_date_text_view);
        endDate = (TextView)findViewById(R.id.end_date_text_view);


        int boardId = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            boardId = extras.getInt("boardID");
        }


        //populate board data
        current = db.getBoardById(boardId);
        Log.i("boardname",current.getName());
        board_name.setText(current.getName());
        radius.setText(Integer.toString(current.getRadiusInMeters()));
        startDate.setText(current.getCreated().toString());
        endDate.setText(current.getExpirationDate().toString());



        //buttone references
        startDateButton = (Button)findViewById(R.id.select_start_date_button);
        endDateButton = (Button)findViewById(R.id.select_end_date_button);
        submitPosterButton = (Button)findViewById(R.id.submit_poster_button);

        Button update_button = (Button) findViewById(R.id.update_board);
        //update the board information
        update_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                current.setName(board_name.getText().toString());
                current.setRadiusInMeters(Integer.parseInt(radius.getText().toString()));
                if( SessionData.location != null){
                    current.setLatitude(SessionData.location.getLatitude());
                    current.setLongitude(SessionData.location.getLongitude());
                }



                //here todo select date option seems not doing its job
                String startDateStr = startDate.getText().toString();
                String endDateStr = endDate.getText().toString();



                int[] startDateInt = parseDate(startDateStr);
                int[] endDateInt = parseDate(endDateStr);

                Calendar startDateCal = Calendar.getInstance();
                Calendar endDateCal = Calendar.getInstance();

                if(startDateInt != null) {
                    startDateCal.set(Calendar.YEAR, startDateInt[2]);
                    startDateCal.set(Calendar.MONTH, startDateInt[1]);
                    startDateCal.set(Calendar.DATE, startDateInt[0]);
                    current.setCreated(startDateCal.getTime());
                }

                if(startDateInt != null) {
                    endDateCal.set(Calendar.YEAR, endDateInt[2]);
                    endDateCal.set(Calendar.MONTH, endDateInt[1]);
                    endDateCal.set(Calendar.DATE, endDateInt[0]);
                    current.setExpirationDate(endDateCal.getTime());
                }

                db.updateBoard(current);

                Intent intent = new Intent(EditBoard.this, Manage_Bulletins.class);
                finish();
                startActivity(intent);
            }
        });


        Button delete_button = (Button) findViewById(R.id.delete_board);
        //update the board information
        delete_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               //todo need delete board databasehandler function
                //db.deleteBoard();

                Intent intent = new Intent(EditBoard.this, Manage_Bulletins.class);
                finish();
                startActivity(intent);
            }
        });


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
            newFragment = new CreateNewPoster.DatePickerFragment();
        }
        else if(button.getText().equals(END_DATE_LABEL)){

            //Create a  bundle to let the dialog know this is the end date
            bundle.putString(END_DATE_LABEL, END_DATE_LABEL);

            //Create new date picker fragment
            newFragment = new CreateNewPoster.DatePickerFragment();
        }
        else if(button.getText().equals(START_TIME_LABEL)){

            //Create a  bundle to let the dialog know this is the start time
            bundle.putString(START_TIME_LABEL, START_TIME_LABEL);

            //Create new time picker fragment
            newFragment = new CreateNewPoster.TimePickerFragment();
        }
        else if(button.getText().equals(END_TIME_LABEL)){

            //Create a  bundle to let the dialog know this is the end time
            bundle.putString(END_TIME_LABEL, END_TIME_LABEL);

            //Create new time picker fragment
            newFragment = new CreateNewPoster.TimePickerFragment();
        }

        if(newFragment != null) {
            //send bundle to new fragment
            newFragment.setArguments(bundle);

            //show the fragment
            newFragment.show(getSupportFragmentManager(), "Picker");
        }



    }

    private String parseEditDate(String date){



        String parsedDate = "";
        String[] dateSplit = date.split(" ");

        Log.i("DATE", Integer.toString(dateSplit.length));
        Log.i("DATE", date);


        if(dateSplit.length == 6){

            parsedDate = dateSplit[2].trim() + "-";

            for(int i = 0; i < MONTHS.length; i++){
                if(MONTHS[i].equals(dateSplit[1].trim())){
                    parsedDate += (i + 1) + "-";
                    break;
                }
            }

            parsedDate += dateSplit[5].trim();

        }

        return parsedDate;
    }

    private int[] parseDate(String date){

        int[] dateArr = null;

        String[] getDateSplit = date.split(":");

        String[] dateSplit = getDateSplit[1].split("-");

        if(dateSplit.length == 3){
            dateArr = new int[3];

            for(int i = 0; i < 3; i++){
                dateArr[i] = Integer.parseInt(dateSplit[i].trim());
            }
        }

        return dateArr;
    }


    private String parseEditTime(String time){

        String[] split = time.split(" ");

        Log.i("TIME", split[3]);

        String[] timeItems = split[3].split(":");

        int hour = Integer.parseInt(timeItems[0]);

        String amPm = "";

        if(hour >= 12){
            amPm = "pm";
        }
        else{
            amPm = "am";
        }

        if(hour == 0){
            hour = 12;
        } else if (hour > 12) {
            hour -= 12;
        }

        String newTime = Integer.toString(hour) + ":" + timeItems[1]  + amPm;

        return newTime;
    }

    private int[] parseTime(String time){

        int[] timeIntArr = null;



        String[] timeSplit = time.split(":");

        if(timeSplit.length == 2){

            String amPm = timeSplit[1].substring(timeSplit[1].length() - 2);

            if(amPm.equals("am") || amPm.equals("pm")){

                timeIntArr = new int[2];

                try {

                    int hour = Integer.parseInt(timeSplit[0]);

                    if(amPm.equals("pm")){
                        if(hour == 12) {
                            hour = 0;
                        }
                        else{
                            hour += 12;
                        }
                    }

                    timeIntArr[0] = hour;
                    timeIntArr[1] = Integer.parseInt(timeSplit[1].substring(0, timeSplit.length - 2));
                }
                catch(NumberFormatException nfe){
                    Log.i("NFE", timeSplit[1].substring(0, timeSplit.length - 2));
                }
            }
        }

        return timeIntArr;
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
            else{
                Log.i("Bundle NULL", "Bundle Null");
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

            String date =  Integer.toString(day) + "-" + Integer.toString(month + 1) + "-" + Integer.toString(year);
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
