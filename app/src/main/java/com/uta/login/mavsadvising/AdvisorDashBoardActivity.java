package com.uta.login.mavsadvising;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdvisorDashBoardActivity extends AppCompatActivity {
    String welcome_Advisor_str;
    String NetID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advisor_dashboard);
        TextView advisor_date_txt_view = (TextView) findViewById(R.id.advisor_date_txt_view);
        String date = new SimpleDateFormat("MM-dd-yyyy").format(new Date());
        advisor_date_txt_view.setText(date);
        Intent i = getIntent();
         welcome_Advisor_str = "Welcome " + i.getStringExtra("AdvisorNetId").toString();
        NetID = i.getStringExtra("AdvisorNetId").toString();
         String role = i.getStringExtra("role").toString();
        TextView welcome_Advisor_TxtView = (TextView) findViewById(R.id.advisor_welcome_msg_text_view);
        welcome_Advisor_TxtView.setText(welcome_Advisor_str);

        Button logout_Advisor = (Button) findViewById(R.id.logout_advisor_btn);
        logout_Advisor.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        Button viewDel_Adv = (Button) findViewById(R.id.view_del_advisor_schedule_btn);
        viewDel_Adv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdvisorDashBoardActivity.this, viewDelAdvisor.class);
                i.putExtra("NetID",NetID );
                startActivityForResult(i,0);
            }
        });


    }

    @Override
    public void onBackPressed() {
        Toast backButtondisable = Toast.makeText(AdvisorDashBoardActivity.this, "Back Button Disabled. Press LogOut instead...", Toast.LENGTH_LONG);
        backButtondisable.show();
    }
}
