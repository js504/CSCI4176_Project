package in.geobullet.csci_4176_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import in.geobullet.csci_4176_project.db.Classes.User;
import in.geobullet.csci_4176_project.db.DatabaseHandler;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_login_gui);


        final DatabaseHandler db = new DatabaseHandler(this);

        //adding image and animation to the weather
        ImageView iv = (ImageView) findViewById(R.id.earth);
        Animation an = AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate);

        iv.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation animation){

            }
            @Override
            public void onAnimationEnd(Animation animation){
                //onRestart();
            }
            @Override
            public void onAnimationRepeat(Animation animation){

            }
        });

        Button login = (Button) findViewById(R.id.login_btn);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText email = (EditText) findViewById(R.id.username_input);
                EditText pwd = (EditText) findViewById(R.id.pwd_input);

                String email_input = email.getText().toString();
                String pwd_input = pwd.getText().toString();

                User usr = db.getUserByEmailPass(email_input,pwd_input);
                db.close();

                if(!email_input.equals("")&& !pwd_input.equals("")) {
                    if (email_input.equals(usr.getEmail()) && pwd_input.equals(usr.getPassword())) {
                        Intent i = new Intent(Login.this, Account_info.class);
                        finish();
                        i.putExtra("user", usr.getId());
                        Log.i("idxx",Integer.toString(usr.getId()));
                        startActivity(i);
                    }
                }else {
                    Toast.makeText(getBaseContext(), "Email or password not valid.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
