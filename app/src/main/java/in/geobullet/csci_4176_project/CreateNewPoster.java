package in.geobullet.csci_4176_project;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import in.geobullet.csci_4176_project.Shared.SessionData;
import in.geobullet.csci_4176_project.Database.Classes.BoardPosterPair;
import in.geobullet.csci_4176_project.Database.Classes.Poster;
import in.geobullet.csci_4176_project.Database.Classes.PosterType;
import in.geobullet.csci_4176_project.Database.DatabaseHandler;
import in.geobullet.csci_4176_project.Utils.PhotoPicker;

/**
 *
 * This class represents the Add new poster view.  It allows users to enter details about a new event or service
 * and have the poster uploaded to the poll they have selected.
 *
 */

public class CreateNewPoster extends AppCompatActivity {

    //todo Create function for uploading pictures from photo gallery

    //Labels of buttons
    private static final String START_DATE_LABEL = "Start Date";
    private static final String END_DATE_LABEL = "End Date";
    private static final String START_TIME_LABEL = "Start Time";
    private static final String END_TIME_LABEL = "End Time";

    private static final String START_DATE = "Start Date:";
    private static final String END_DATE = "End Date:";
    private static final String START_TIME = "Start Time:";
    private static final String END_TIME = "End Time:";

    //Layout radio buttons
    private RadioButton eventRadioButton;
    private RadioButton serviceRadioButton;

    private EditText title;
    private EditText locationAddress;
    private EditText city;
    private EditText details;

    //Layout textviews for displaying time and date
    private TextView startDate;
    private TextView endDate;
    private TextView startTime;
    private TextView endTime;
    private TextView errorTv;

    //Layout buttons to activate date and time pickers
    private Button startDateButton;
    private Button endDateButton;
    private Button startTimeButton;
    private Button endTimeButton;
    private Button browsePostersButton;
    private Button submitPosterButton;

    private ImageView previewImageView;

    private final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Nov", "Dec"};

    private DatabaseHandler dbHandler = null;

    private int posterId;
    private String imgSrc;
    private String iconSrc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_poster);


        dbHandler = new DatabaseHandler(this);

        posterId = -1;

        imgSrc = "";
        iconSrc = "";

        Bundle extras = this.getIntent().getExtras();


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
        errorTv = (TextView)findViewById(R.id.add_poster_error_msg);

        //get references to buttons
        startDateButton = (Button)findViewById(R.id.select_start_date_button);
        endDateButton = (Button)findViewById(R.id.select_end_date_button);
        startTimeButton = (Button)findViewById(R.id.start_time_button);
        endTimeButton = (Button)findViewById(R.id.end_time_button);
        browsePostersButton = (Button)findViewById(R.id.browse_posters_button);
        submitPosterButton = (Button)findViewById(R.id.submit_poster_button);

        previewImageView = (ImageView)findViewById(R.id.poster_preview);

        //Then we are editing an existing poster
        if(extras != null){
            populateFields(extras);
        }
    }

    /**
     * Populates the fields of the create poster activity with the existing data to be edited.
     *
     * @param extras  The data to populate the fields with
     */
    private void populateFields(Bundle extras){
        posterId = (int)extras.get("ID");
        title.setText((String)extras.get("TITLE"));
        locationAddress.setText((String)extras.get("ADDRESS"));
        city.setText((String)extras.get("CITY"));

        String startDateStr = (String)extras.get("STARTDATE");
        startDateStr = "Start Date: " + parseEditDate(startDateStr);

        String endDateStr = (String)extras.get("ENDDATE");
        endDateStr = "End Date: " + parseEditDate(endDateStr);

        String startTimeStr = (String)extras.get("STARTTIME");
        startTimeStr = "Start Time: " + parseEditTime(startTimeStr);

        String endTimeStr = (String)extras.get("ENDTIME");
        endTimeStr = "End Time: " + parseEditTime(endTimeStr);

        startDate.setText(startDateStr);
        endDate.setText(endDateStr);
        startTime.setText(startTimeStr);
        endTime.setText(endTimeStr);
        details.setText((String)extras.get("DETAILS"));
        imgSrc = (String)extras.get("IMGSRC");
        iconSrc = (String)extras.get("IMGICONSRC");


        imgSrc = imgSrc.substring(0, imgSrc.lastIndexOf("."));
        int id = this.getResources().getIdentifier(imgSrc, "drawable", this.getPackageName());
        previewImageView.setImageResource(id);


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


    /**
     * On click method to the postor picker activity which allows users to select an image for their poster
     *
     * @param view  The button view that was clicked
     */
    public void getImageOnClick(View view){

        Intent intent = new Intent(CreateNewPoster.this, PhotoPicker.class);
        startActivityForResult(intent, 0);
    }

    /**
     * Result of the photo picker activity is grabbed through this call back method
     *
     * @param requestCode   Code of the requesting activity that called the previous
     * @param resultCode    The code as to whether the last activities operation was successful
     * @param data          The data that was returned from the activity
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (0) : {
                if (resultCode == Activity.RESULT_OK) {
                    String tmpImgSrc = data.getStringExtra("IMG_SRC");
                    imgSrc = tmpImgSrc;

                    Log.i("IMG_SRC", imgSrc);

                    int id = this.getResources().getIdentifier(imgSrc, "drawable", this.getPackageName());
                    previewImageView.setImageResource(id);
                }
                break;
            }
        }
    }


    /**
     * Onclick that calls the Submit methods a new poster if all required fields have been added.
     *
     * @param view  Submit button view
     */
    public void addPosterOnClick(View view){


        RadioButton eventRadio = (RadioButton)findViewById(R.id.event_radio_button);


        Poster poster = null;

        Log.i("EVENT POSTER", "CHECKED");

        if(eventRadioButton.isChecked()){
            Log.i("EVENT POSTER", "CHECKED");
            poster = addEventPoster();
        }
        else if(serviceRadioButton.isChecked()){
            Log.i("SERVICE POSTER", "CHECKED");

            poster = addServicePoster();
        }


        if(poster != null){
            errorTv.setVisibility(View.GONE);
            if(posterId != -1){
                editPosterInDb(poster);
            }else{
                addPosterToDb(poster);
            }


        }
        else{
            errorTv.setVisibility(View.VISIBLE);
        }

    }

    /**
     * Update the poster in the db if the poster already existed
     *
     * @param poster  The poster to be updated
     */
    private void editPosterInDb(Poster poster){
        poster.setId(posterId);
        boolean tmp = dbHandler.updatePoster(poster);
        Log.i("UPDATED POSTER", poster.toString());
        if(tmp){
            Toast.makeText(this, "Poster Updated!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Add a new poster to the db if none existed before
     *
     * @param poster  The new poster to be added to the db
     */
    private void addPosterToDb(Poster poster){
        posterId = dbHandler.addPoster(poster);

        poster = dbHandler.getPosterById(posterId);

        Log.i("New Poster: ", poster.toString());

        BoardPosterPair boardPosterPair = new BoardPosterPair();

        boardPosterPair.setBoardId(SessionData.boardId);
        boardPosterPair.setPosterId(posterId);

        int bppId = dbHandler.addBoardPosterPair(boardPosterPair);

        BoardPosterPair bpp = dbHandler.getBoardPosterPairById(bppId);

        Log.d("Seeding", "Added board poster pair: " + bpp.toString());
        resetFields();
        Toast.makeText(this, "Poster Created!", Toast.LENGTH_SHORT).show();
    }


    /**
     * Creates an event poster based on poster type
     *
     * @return  The created poster if all required fields were added, null otherwise
     */
    private Poster addEventPoster(){


        String titleStr = title.getText().toString();
        String locationAddressStr = locationAddress.getText().toString();
        String cityStr = city.getText().toString();
        String detailsStr = details.getText().toString();

        String startDateStr = startDate.getText().toString();
        String endDateStr = endDate.getText().toString();
        String startTimeStr = startTime.getText().toString();
        String endTimeStr = endTime.getText().toString();

        Poster poster = null;

        String error = "";

        if(!titleStr.equals("") &&
                !locationAddressStr.equals("") &&
                !cityStr.equals("") &&
                !detailsStr.equals("") &&
                !startDateStr.equals("") &&
                !endDateStr.equals("") &&
                !startTimeStr.equals("") &&
                !endTimeStr.equals("") &&
                !imgSrc.isEmpty()){

                poster = createNewPoster(PosterType.Event, titleStr, locationAddressStr, cityStr ,detailsStr, startDateStr, endDateStr, startTimeStr, endTimeStr);


        }
        else{
            error = "Please enter the required fields before submitting: \n";

            if(titleStr.isEmpty()){
                error += "\tPoster Title\n";
            }

            if(locationAddressStr.isEmpty()){
                error += "\tLocation Address\n";
            }

            if(cityStr.isEmpty()){
                error += "\tCity\n";
            }


            String[] startDateStrArr = startDateStr.split(":");
            if(startDateStrArr.length <= 1){
                error += "\tStart Date\n";
            }


            String[] endDateStrArr = endDateStr.split(":");
            if(endDateStrArr.length <= 1){
                error += "\tEnd Date\n";
            }

            String[] startTimeStrArr = startTimeStr.split(":");

            if(startTimeStrArr.length <= 1){
                error += "\tStar Time\n";
            }

            String[] endTimeStrArr = endTimeStr.split(":");

            if(endTimeStrArr.length <= 1){
                error += "\tEnd Time\n";
            }

            if(imgSrc.isEmpty()){
                error += "\tPoster Image\n";
            }

        }


        errorTv.setText(error.toString());

        return poster;

    }



    /**
     *  Checks required fields for a service poster then creates one.  If required fields are not filled in
     *  error message is created and displayed.
     *
     * @return  The created service poster, null otherwise.
     */
    private Poster addServicePoster(){

        String titleStr = title.getText().toString();
        String locationAddressStr = locationAddress.getText().toString();
        String cityStr = city.getText().toString();
        String detailsStr = details.getText().toString();

        String startDateStr = startDate.getText().toString();

        Poster poster = null;
        String error = "";


        if(!titleStr.equals("") &&
                !locationAddressStr.equals("") &&
                !cityStr.equals("") &&
                !detailsStr.equals("") &&
                !startDateStr.equals("") &&
                !imgSrc.isEmpty()){

            poster = createNewPoster(PosterType.Service, titleStr, locationAddressStr, cityStr ,detailsStr, startDateStr, "", "", "");


        }
        else{
            error = "Please enter the required fields before submitting: \n";

            if(titleStr.isEmpty()){
                error += "\tPoster Title\n";
            }

            if(locationAddressStr.isEmpty()){
                error += "\tLocation Address\n";
            }

            if(cityStr.isEmpty()){
                error += "\tCity\n";
            }

            String[] startDateArr = startDateStr.split(":");
            if(startDateArr.length <= 1){

                error += "\tStart Date\n";
            }

            if(imgSrc.isEmpty()){
                error += "\tPoster Image\n";
            }

        }

        errorTv.setText(error.toString());

        return poster;

    }

    /**
     * Method creates a new poster based on its poster type.
     *
     * @param posterType    The event type
     * @param title         The event title
     * @param address       The event address / location
     * @param city          The event city
     * @param details       The event details
     * @param startDate     The event start date
     * @param endDate       The event end date (not used for a service)
     * @param startTime     The event start time (not used for a service)
     * @param endTime       The event end time (not used for a service)
     * @return              The created poster
     */
    private Poster createNewPoster(PosterType posterType, String title, String address, String city, String details,
                                   String startDate, String endDate, String startTime, String endTime){
        Poster poster = new Poster();

        poster.setCreated(Calendar.getInstance().getTime());
        poster.setCreatedByUserId(SessionData.currentUser.getId());

        poster.setTitle(title);
        poster.setPosterType(posterType);
        poster.setAddress(address);
        poster.setCity(city);
        poster.setStateProv("");
        poster.setDetails(details);

        int[] startDateInt = parseDate(startDate);

        Calendar startDateCal = Calendar.getInstance();
        Calendar endDateCal = Calendar.getInstance();

        if(startDateInt != null){

            Log.i("start date crated", "");
            startDateCal.set(Calendar.YEAR, startDateInt[2]);
            startDateCal.set(Calendar.MONTH, startDateInt[1]);
            startDateCal.set(Calendar.DATE, startDateInt[0]);

        }


        if(posterType == PosterType.Event) {
            Log.i("event type", "");


            int[] startTimeInt = parseTime(startTime);

            if (startTimeInt != null) {
                Log.i("start time crated", "");

                startDateCal.set(Calendar.HOUR_OF_DAY, startTimeInt[0]);
                startDateCal.set(Calendar.MINUTE, startTimeInt[1]);
                startDateCal.set(Calendar.SECOND, 0);
            }

            int[] endDateInt = parseDate(endDate);

            if(endDateInt != null){
                Log.i("end date crated", "");

                endDateCal.set(Calendar.YEAR, endDateInt[2]);
                endDateCal.set(Calendar.MONTH, endDateInt[1]);
                endDateCal.set(Calendar.DATE, endDateInt[0]);
            }

            int[] endTimeInt = parseTime(endTime);

            if (endTimeInt != null) {
                Log.i("end time crated", "");

                startDateCal.set(Calendar.HOUR_OF_DAY, endTimeInt[0]);
                startDateCal.set(Calendar.MINUTE, endTimeInt[1]);
                startDateCal.set(Calendar.SECOND, 0);
            }



        }
        else{
            startDateCal.set(Calendar.HOUR_OF_DAY, 0);
            startDateCal.set(Calendar.MINUTE, 0);
            startDateCal.set(Calendar.SECOND, 0);
            endDateCal.set(Calendar.YEAR, 0);
            endDateCal.set(Calendar.MONTH, 0);
            endDateCal.set(Calendar.DATE, 0);
            endDateCal.set(Calendar.HOUR_OF_DAY, 0);
            endDateCal.set(Calendar.MINUTE, 0);
            endDateCal.set(Calendar.SECOND, 0);
        }


        poster.setStartDate(startDateCal.getTime());
        poster.setEndDate(endDateCal.getTime());

        poster.setStartTime(startDateCal.getTime());
        poster.setEndTime(endDateCal.getTime());

        //TODO get the photo name

        imgSrc = imgSrc + ".png";
        iconSrc = imgSrc + "_icon.png";

        poster.setPhotoName(imgSrc);
        poster.setIconName(iconSrc);


        //int posterId = dbHandler.addPoster(poster);

        return poster;
    }

    /**
     * Parses the default date displayed when first editing a poster and displays it in its
     * required format
     *
     * @param date  The unformatted date
     * @return      The formatted date
     */
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


    /**
     * Parses the date from the expected format and returns it as numbers for use when creating
     * posters
     *
     * @param date
     * @return
     */
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


    /**
     * Parses the default time displayed when first editing a poster and displays it in its
     * required format
     *
     * @param time  The unformatted time
     * @return      The formatted time
     */
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


    /**
     * Parses the time from the time text fields
     *
     * @param time  The time text fields stext
     * @return      The parsed time for use with creating posters
     */
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
     * Resets the fields to blank after a successful submission
     *
     */
    private void resetFields(){

        title.setText("");
        locationAddress.setText("");
        city.setText("");
        startDate.setText(START_DATE);
        endDate.setText(END_DATE);
        startTime.setText(START_TIME);
        endTime.setText(END_TIME);
        details.setText("");
        imgSrc = "";
        previewImageView.setImageResource(-1);

    }







    /**************** Private static classes start here *****************/

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