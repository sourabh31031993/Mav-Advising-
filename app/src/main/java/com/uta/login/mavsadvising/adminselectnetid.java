package com.uta.login.mavsadvising;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class adminselectnetid extends AppCompatActivity {
protected TextView selectNetID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminselectnetid);

        selectNetID = (TextView) findViewById(R.id.selectNetID);
        Button submit_Adm = (Button) findViewById(R.id.submit_btn);
        submit_Adm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(adminselectnetid.this, viewDelAdvisor.class);
                //i.putExtra("NetID", "sajib123");
                i.putExtra("NetID", selectNetID.getText().toString());
                startActivity(i);
            }
        });
    }
}
