package com.uta.login.mavsadvising;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by karthyvr on 6/28/16.
 */
public class StudentViewCancelActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DatabaseHelper(this);
        setContentView(R.layout.student_view_cancel_appointments);
        String studentnetid = getIntent().getStringExtra("Usernetid");
        TableLayout tableLayout = (TableLayout) findViewById(R.id.tablelayout);
        // Add header row
        TableRow rowHeader = new TableRow(this);
        rowHeader.setBackgroundColor(Color.parseColor("#c0c0c0"));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText = {"StudentNetId", "ADVISORNAME", "APTDATE","STARTTIME", "ENDTIME", "STATUS", "REASON", "COMMENT"};
        for (String c : headerText) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(11);
            tv.setPadding(1, 5, 1, 5);
            tv.setText(c);
            rowHeader.addView(tv);
        }
        boolean set = true;
        tableLayout.addView(rowHeader);


        // Get data from sqlite database and add them to the table

        List<AppointmentDetailsDomain> aptDetails = dbHelper.getStudentAppointmentDetails(studentnetid);

        if (aptDetails != null && !aptDetails.isEmpty()) {
            for (AppointmentDetailsDomain aptDtl : aptDetails) {

                // Read columns data from the model object AppointmentDetailsDomain aptDtl
                String Advisornetid = aptDtl.getAdvisorName();
                String Advisorname = dbHelper.getAdvisorName(Advisornetid);
                String AppointmentStartTime = aptDtl.getStartTime();
                String AppointmentEndTime = aptDtl.getEndTime();
                String AppointmentStatus = aptDtl.getStatus();
                String AppointmentReason = aptDtl.getReason();
                String AppointmentComment = aptDtl.getComment();
                String AppointmentDate = aptDtl.getAptDate();

                // Table data rows

                final TableRow row = new TableRow(this);
                row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                String[] colText = {studentnetid, Advisorname,AppointmentDate, AppointmentStartTime, AppointmentEndTime, AppointmentStatus, AppointmentReason, AppointmentComment};
                for (String text : colText) {
                    TextView tv = new TextView(this);

                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(11);
                    tv.setPadding(1, 5, 1, 5);
                    tv.setText(text);
                    row.addView(tv);

                }
                Button btn = new Button(this);
                btn.setText("Cancel Appointment");
                btn.setOnClickListener(mListener);
                row.addView(btn);
                tableLayout.addView(row);


            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private View.OnClickListener mListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            long msg;
            TableRow tr = (TableRow) v.getParent();
            // now you have a reference to the row where this Button that was
            // clicked exists
            TextView et = (TextView) tr.getChildAt(1);
            String advisorFName = et.getText().toString();
            String advisorNtId = dbHelper.getAdvisorNetId(advisorFName);
            System.out.println("net id is   " + advisorNtId);
            System.out.println("name  is   " + advisorFName);
            TextView st = (TextView) tr.getChildAt(0);
            String studentNetId = st.getText().toString();
            System.out.println("student net id  is   " + studentNetId);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String today = dateFormat.format(new Date(System.currentTimeMillis()));
            msg = dbHelper.CancelStudentAppointment(studentNetId, advisorNtId);
            if (msg == -1) {
                Toast sfailedmessage = Toast.makeText(StudentViewCancelActivity.this, "Appointment has been Sucessfully Cancelled!!!...", Toast.LENGTH_LONG);
                sfailedmessage.show();
            }
            Intent i = new Intent(StudentViewCancelActivity.this, StudentViewCancelActivity.class);
            i.putExtra("Usernetid", studentNetId);
            startActivity(i);


        }
    };

    //Function for Log Out functionality
    public void onButtonClick(View v) {
        if (v.getId() == R.id.BTNLogOut) {
            System.out.println("within the logout functionality");
            Intent i = new Intent(StudentViewCancelActivity.this, MainActivity.class);
            startActivity(i);

        }
    }
}