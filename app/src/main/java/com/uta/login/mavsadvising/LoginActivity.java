package com.uta.login.mavsadvising;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by karthyvr on 6/27/16.
 */
public class LoginActivity extends AppCompatActivity {
    String usernetid;
    String role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usernetid = getIntent().getStringExtra("Usernetid");
        role = getIntent().getStringExtra("role");
        if (role.equals("admin")) {
            //setContentView(R.layout.admin_dashboard);
            Intent i =  new Intent(getApplicationContext(), admindashboard.class);
            i.putExtra("NetID", usernetid);
            startActivity(i);
            //TextView a = (TextView) findViewById(R.id.admin_welcome_msg);
            //if (a != null) {
              //  a.setText("Welcome " + usernetid);
            //}
        } /*else if (role.equals("advisor")) {
            /* Commented by Roopesh : Calling the intent to display Advisor dashboard activity
            setContentView(R.layout.advisor_dashboard);

            TextView a = (TextView) findViewById(R.id.advisor_welcome_msg);
            if (a != null) {
                a.setText("Welcome " + usernetid);
            }
            Intent i = new Intent(LoginActivity.this, AdvisorDashBoardActivity.class);
            i.putExtra("AdvisorNetId" , usernetid);
            i.putExtra("role", role);
            startActivityForResult(i, 0);
        }*/
        if (role.equals("student")) {
            setContentView(R.layout.student_dashboard);
            TextView a = (TextView) findViewById(R.id.student_welcome_msg);
            if (a != null) {
                a.setText("Welcome " + usernetid);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

        public void onButtonClick(View v) {


            if (v.getId() == R.id.BTNStudentSchedule) {

                Intent i = new Intent(LoginActivity.this, StudentScheduleActivity.class);
                i.putExtra("Usernetid", usernetid);
                i.putExtra("role", role);

                startActivity(i);
            }else if (v.getId() == R.id.BTNStudentView){
                System.out.println("within the studentcancel functionality");
                Intent i = new Intent(LoginActivity.this, StudentViewCancelActivity.class);
                i.putExtra("Usernetid", usernetid);
                i.putExtra("role", role);

                startActivity(i);

            }else if (v.getId() == R.id.BTNLogOut){
                System.out.println("within the logout functionality");

                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);

            }
        }

    }

