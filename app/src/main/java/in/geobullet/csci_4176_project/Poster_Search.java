package in.geobullet.csci_4176_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;


public class Poster_Search extends AppCompatActivity {

    String type_selected = "Event";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poster_search);

        NumberPicker poster_type_picker = (NumberPicker) findViewById(R.id.np);
        poster_type_picker.setMinValue(0);
        poster_type_picker.setMaxValue(1);
        poster_type_picker.setDisplayedValues( new String[] { "Event", "Service" } );

        //Get the widgets reference from XML layout
        final TextView tv = (TextView) findViewById(R.id.tv);
        NumberPicker np = (NumberPicker) findViewById(R.id.np);
        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        Button button = (Button) findViewById(R.id.poster_search_btn);

        //Initializing a new string array with elements
        final String[] values= {"Event", "Service"};

        //Specify the NumberPicker data source as array elements
        np.setDisplayedValues(values);

        //Set a value change listener for NumberPicker
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected value from picker
                type_selected =  values[newVal];
            }
        });

        button.setOnClickListener(new OnClickListener(){
            @Override

            //On click function
            public void onClick(View view) {
                //Create the intent to start another activity
                Intent intent = new Intent(Poster_Search.this, Main_GUI.class);
                intent.putExtra("postertype", type_selected);
                startActivity(intent);
            }
        });
    }
}


