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
import android.widget.Toast;

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


    private TextView board_name;
    private TextView radius;
    private TextView startDate;
    private TextView endDate;


    //Layout buttons to activate date and time pickers
    private Button delete_button;
    private Button update_button;
    private Button startDateButton;
    private Button endDateButton;
    private Button submitPosterButton;


    private final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Nov", "Dec"};


    Board current = new Board();
    //add boards or edit boards flag
    Boolean addmod = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_board);


        final DatabaseHandler db = new DatabaseHandler(this);


        //textview reference
        board_name = (TextView) findViewById(R.id.edit_board_name);
        radius = (TextView) findViewById(R.id.edit_radius);
        startDate = (TextView) findViewById(R.id.start_date_text_view);
        endDate = (TextView) findViewById(R.id.end_date_text_view);
        //button reference
        update_button = (Button) findViewById(R.id.update_board);
        delete_button = (Button) findViewById(R.id.delete_board);
        startDateButton = (Button) findViewById(R.id.select_start_date_button);
        endDateButton = (Button) findViewById(R.id.select_end_date_button);
        submitPosterButton = (Button) findViewById(R.id.submit_poster_button);


        int boardId = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            boardId = extras.getInt("boardID");
            addmod = extras.getBoolean("addmod");
        }


        //check if its in add mod or edit mod
        if (!addmod) {
            //populate board data
            current = db.getBoardById(boardId);
            Log.i("boardname", current.getName());
            board_name.setText(current.getName());
            radius.setText(Integer.toString(current.getRadiusInMeters()));
            startDate.setText(current.getCreated().toString());
            endDate.setText(current.getExpirationDate().toString());
            getSupportActionBar().setTitle("Edit Bulletin Boards");

        } else {
            current = new Board();
            getSupportActionBar().setTitle("Add Bulletin Boards");
            update_button.setText("Add Boards");
            View button = findViewById(R.id.delete_board);
            button.setVisibility(View.GONE);
        }


        //update the board information
        update_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // initialize variables
                Boolean flag = false;
                int radius_meter = 0;
                String startDateStr;
                String endDateStr;
                int[] startDateInt = null;
                int[] endDateInt = null;
                //check for user input
                try {
                    radius_meter = Integer.parseInt(radius.getText().toString());
                    startDateStr = startDate.getText().toString();
                    endDateStr = endDate.getText().toString();
                    startDateInt = parseDate(startDateStr);
                    endDateInt = parseDate(endDateStr);
                } catch (NumberFormatException e) {
                    flag = true;
                    Toast.makeText(EditBoard.this, "Radius not valid",
                            Toast.LENGTH_LONG).show();
                } catch (IndexOutOfBoundsException e) {
                    flag = true;
                    Toast.makeText(EditBoard.this, "Please select date",
                            Toast.LENGTH_LONG).show();
                }


                if (!flag) {
                    //Bard data
                    if (SessionData.location != null) {
                        current.setLatitude(SessionData.location.getLatitude());
                        current.setLongitude(SessionData.location.getLongitude());
                    }
                    current.setRadiusInMeters(radius_meter);
                    current.setName(board_name.getText().toString());
                    //here todo select date option seems not doing its job


                    Calendar startDateCal = Calendar.getInstance();
                    Calendar endDateCal = Calendar.getInstance();

                    if (startDateInt != null) {
                        startDateCal.set(Calendar.YEAR, startDateInt[2]);
                        startDateCal.set(Calendar.MONTH, startDateInt[1] - 1);
                        startDateCal.set(Calendar.DATE, startDateInt[0]);
                        current.setCreated(startDateCal.getTime());
                    }

                    if (endDateInt != null) {
                        endDateCal.set(Calendar.YEAR, endDateInt[2]);
                        endDateCal.set(Calendar.MONTH, endDateInt[1] - 1);
                        endDateCal.set(Calendar.DATE, endDateInt[0]);
                        current.setExpirationDate(endDateCal.getTime());
                    }
                    if (!addmod) {
                        db.updateBoard(current);
                    } else if (addmod) {
                        db.addBoard(current);
                    }

                    Intent intent = new Intent(EditBoard.this, Manage_Bulletins.class);
                    finish();
                    startActivity(intent);
                }
            }
        });


        //delete the board information
        delete_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db.getBoardById(current.getId());
                Log.i("dsdf", Integer.toString(current.getId()));

                db.removeBoard(current.getId());
                Log.i("dsdfxxx", Integer.toString(current.getId()));
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
     * @param v The button clicked to display a date or time picker dialog
     */
    public void showDialog(View v) {

        Button button = (Button) v;

        Bundle bundle = new Bundle();

        DialogFragment newFragment = null;

        if (button.getText().toString().equals(START_DATE_LABEL)) {

            //Create a  bundle to let the dialog know this is the start date
            bundle.putString(START_DATE_LABEL, START_DATE_LABEL);

            //Create new date picker fragment
            newFragment = new EditBoard.DatePickerFragment();
        } else if (button.getText().equals(END_DATE_LABEL)) {

            //Create a  bundle to let the dialog know this is the end date
            bundle.putString(END_DATE_LABEL, END_DATE_LABEL);

            //Create new date picker fragment
            newFragment = new EditBoard.DatePickerFragment();
        } else if (button.getText().equals(START_TIME_LABEL)) {

            //Create a  bundle to let the dialog know this is the start time
            bundle.putString(START_TIME_LABEL, START_TIME_LABEL);

            //Create new time picker fragment
            newFragment = new EditBoard.DatePickerFragment();
        } else if (button.getText().equals(END_TIME_LABEL)) {

            //Create a  bundle to let the dialog know this is the end time
            bundle.putString(END_TIME_LABEL, END_TIME_LABEL);

            //Create new time picker fragment
            newFragment = new EditBoard.DatePickerFragment();
        }

        if (newFragment != null) {
            //send bundle to new fragment
            newFragment.setArguments(bundle);

            //show the fragment
            newFragment.show(getSupportFragmentManager(), "Picker");
        }


    }


    private int[] parseDate(String date) {

        int[] dateArr = null;

        String[] getDateSplit = date.split(":");

        String[] dateSplit = getDateSplit[1].split("-");

        if (dateSplit.length == 3) {
            dateArr = new int[3];

            for (int i = 0; i < 3; i++) {
                dateArr[i] = Integer.parseInt(dateSplit[i].trim());
            }
        }

        return dateArr;
    }



    /**
     * Fragment for displaying the date picker dialog
     */
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {


        private String key = "";


        /**
         * Called when the dialog is created.  Data is gathered from the bundle and
         * the date is set to the current date in the picker.
         *
         * @param savedInstanceState saved info from last created instance
         * @return a new date picker dialog with the current date already set
         */
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            Bundle bundle = getArguments();


            if (bundle.getString(START_DATE_LABEL) != null) {
                key = bundle.getString(START_DATE_LABEL);
            } else if (bundle.getString(END_DATE_LABEL) != null) {
                key = bundle.getString(END_DATE_LABEL);
            } else {
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
         * @param view  The date picker dialog
         * @param year  The year selected
         * @param month The month selected
         * @param day   The day selected
         */
        public void onDateSet(DatePicker view, int year, int month, int day) {

            String date = Integer.toString(day) + "-" + Integer.toString(month + 1) + "-" + Integer.toString(year);
            //Set the date based on whether its the start or end date
            if (key.equals(START_DATE_LABEL)) {
                TextView startDate = (TextView) getActivity().findViewById(R.id.start_date_text_view);
                date = "Start Date: " + date;
                startDate.setText(date);
            } else if (key.equals(END_DATE_LABEL)) {
                TextView endDate = (TextView) getActivity().findViewById(R.id.end_date_text_view);
                date = "End Date: " + date;
                endDate.setText(date);
            }

        }
    }

    /**
     * Fragment for displaying the time picker dialog
     */
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {


        private String key = "";


        /**
         * Called when the dialog is created.  Data is gathered from the bundle to determin
         * whether its the start or end time
         *
         * @param savedInstanceState saved info from last created instance
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
         * @param view      The time picker dialog
         * @param hourOfDay The hour of the day selected, in 24 hour time
         * @param minute    The minute of the day selected
         */
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


            int noTwntFourHour = hourOfDay;
            String min = Integer.toString(minute);
            String amPm = "am";

            //Check if the time is greater than or equal to 12. if it is subtract 12 to make it
            //in 12 hour time and set am - pm to pm
            if (hourOfDay >= 12) {
                noTwntFourHour = hourOfDay - 12;
                amPm = "pm";
            }

            //If the hour ends up being a 0 set it to 12
            if (hourOfDay == 0 || noTwntFourHour == 0) {
                noTwntFourHour = 12;
            }

            //if the min is less than 10 then add an extra 0 for the proper display
            if (minute < 10) {
                min = "0" + min;
            }


            String time = Integer.toString(noTwntFourHour) + ":" + min + amPm;

            //Set the time based on whether its the start or end time
            if (key.equals(START_TIME_LABEL)) {
                TextView startTime = (TextView) getActivity().findViewById(R.id.start_time_text_view);
                time = "Start Time: " + time;
                startTime.setText(time);
            } else if (key.equals(END_TIME_LABEL)) {
                TextView endTime = (TextView) getActivity().findViewById(R.id.end_time_text_view);

                time = "End Time: " + time;
                endTime.setText(time);
            }
        }
    }
}
