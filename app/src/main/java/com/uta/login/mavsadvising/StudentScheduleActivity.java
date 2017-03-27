package com.uta.login.mavsadvising;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

/**
 * Created by karthyvr on 6/28/16.
 */
public class StudentScheduleActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String role = getIntent().getStringExtra("role");
        setContentView(R.layout.student_scheduleappointment);
        // drop down menu for the Reason
        Spinner dropdown = (Spinner) findViewById(R.id.spinner);
        String[] items = new String[]{"OPT/CPT", "PRE-REQUISITE WAIVER", "COURSE ENROLLMENT", "GENERAL ADVISING", "OTHERS"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dbHelper = new DatabaseHelper(this);


        // drop down menu for the Advisor names
        List<String> AdvNames = dbHelper.getAdvisorNames();

        // Creating adapter for spinner
        final Spinner dropdown1 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, AdvNames);
        dropdown1.setAdapter(dataAdapter);
        final EditText starttime = (EditText) findViewById(R.id.ETstarttime);
        final EditText endtime = (EditText) findViewById(R.id.ETendtime);
        dropdown1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                Object item = arg0.getItemAtPosition(arg2);
                if (item != null) {

                    String strtdt = dbHelper.getAdvisorstartTime(item.toString());
                    starttime.setText(strtdt);
                    String enddt = dbHelper.getAdvisorendTime(item.toString());
                    endtime.setText(enddt);
                }
                Toast.makeText(StudentScheduleActivity.this, "Selected",
                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


    }

    public void loadSpinnerData() {


        List<String> AdvNames = dbHelper.getAdvisorNames();

        // Creating adapter for spinner
        Spinner dropdown = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, AdvNames);
        dropdown.setAdapter(dataAdapter);


    }

    public void onButtonClick(View v) {
        int appointmentCount = 0;
        if (v.getId() == R.id.BTNRequestAppointment) {
            long msg;
            System.out.println("karthyyyyyy");
            String aptStatus;

            String studentNetId = getIntent().getStringExtra("Usernetid");

            EditText studentComment = (EditText) findViewById(R.id.selectNetID);
            String aptComment = studentComment.getText().toString();

            Spinner spinner = (Spinner) findViewById(R.id.spinner);
            String aptReason = spinner.getSelectedItem().toString();
            if (aptReason.equals("OPT/CPT") || aptReason.equals("PRE-REQUISITE WAIVER")) {
                aptStatus = "ActionRequired";
            } else {
                aptStatus = "Approved";
            }

            EditText aptstTime = (EditText) findViewById(R.id.ETstarttime);
            String aptstartTime = aptstTime.getText().toString();
            EditText apteTime = (EditText) findViewById(R.id.ETendtime);
            String aptendTime = apteTime.getText().toString();

            Spinner spinner1 = (Spinner) findViewById(R.id.spinner2);
            String advName = spinner1.getSelectedItem().toString();
            System.out.println("the advisor name is ===>" + advName);
            String advisorNetId = dbHelper.getAdvisorNetId(advName);
            // get the total number of students scheduled the appointment with the advisor count allowed is 20 per advisor
            appointmentCount = dbHelper.getAdvisorAppointmentCountForDay(advisorNetId);
            System.out.println("The appointment Count is " + appointmentCount);

            if (appointmentCount < 20) {

                msg = dbHelper.insertAppointmentDetails(advisorNetId, studentNetId, aptstartTime, aptendTime, aptStatus, aptReason, aptComment);

                aptstTime.setText(" ");
                apteTime.setText(" ");
                studentComment.setText(" ");
                System.out.println("the message from the database is ===" + msg);

                if (msg == -1) {
                    Toast sfailedmessage = Toast.makeText(StudentScheduleActivity.this, "Sorry!!! Only ONE Appointment per student-advisor is allowed on same day...", Toast.LENGTH_LONG);
                    sfailedmessage.show();
                } else {
                    Toast successmessage = Toast.makeText(StudentScheduleActivity.this, "Appointment succesfully Created...", Toast.LENGTH_LONG);
                    successmessage.show();
                }

            } else {
                Toast sfailedmessage1 = Toast.makeText(StudentScheduleActivity.this, "Sorry try again tommorow!!! Only 20 Students per Advisor is permitted for the day...", Toast.LENGTH_LONG);
                sfailedmessage1.show();
            }
        } else if (v.getId() == R.id.BTNLogOut) {
            System.out.println("within the logout functionality");
            Intent i = new Intent(StudentScheduleActivity.this, MainActivity.class);
            startActivity(i);

        }
    }
}
