package com.uta.login.mavsadvising;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by sourabhsp on 04/08/16.
 */
public class admindashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.admin_dashboard);

        TextView admin_welcome = (TextView) findViewById(R.id.admin_welcome_msg);
        admin_welcome.setText("Welcome " + getIntent().getStringExtra("NetID"));
        Button viewDel_Adv = (Button) findViewById(R.id.viewdelete_btn);
        Button viewDel_Stu = (Button) findViewById(R.id.viewdelstudent_btn);
        viewDel_Adv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(admindashboard.this, adminselectnetid.class);
                //i.putExtra("NetID", "sajib123");
                startActivity(i);
            }
        });
        viewDel_Stu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(admindashboard.this, adminstudent.class);
                //i.putExtra("NetID", "sajib123");
                startActivity(i);


            }
        });
    }
}
