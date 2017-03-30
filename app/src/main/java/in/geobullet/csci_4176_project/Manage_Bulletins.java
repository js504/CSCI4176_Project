package in.geobullet.csci_4176_project;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import in.geobullet.csci_4176_project.db.DatabaseHandler;

public class Manage_Bulletins extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__bulletins);


        DatabaseHandler db = new DatabaseHandler(this);

    }
}
