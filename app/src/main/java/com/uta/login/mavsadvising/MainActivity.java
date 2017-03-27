package com.uta.login.mavsadvising;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DatabaseHelper(this);

    }

    public void onButtonClick(View v) {
        if (v.getId() == R.id.Blogin) {
            EditText netid = (EditText) findViewById(R.id.TFnetid);
            String inputnetid = netid.getText().toString().toLowerCase();

            EditText pass = (EditText) findViewById(R.id.TFpassword);
            String inputpassword = pass.getText().toString();

            String role = dbHelper.fetchUserRole(inputnetid, inputpassword);

            if (!role.equals("error")) {
                System.out.println("input net id is " + inputnetid);
                if (role.equals("advisor")) {
            /* Commented by Roopesh : Calling the intent to display Advisor dashboard activity
            setContentView(R.layout.advisor_dashboard);

            TextView a = (TextView) findViewById(R.id.advisor_welcome_msg);
            if (a != null) {
                a.setText("Welcome " + usernetid);
            }*/
                    Intent i = new Intent(MainActivity.this, AdvisorDashBoardActivity.class);
                    i.putExtra("AdvisorNetId" , inputnetid);
                    i.putExtra("role", role);
                    startActivityForResult(i, 0);
                }
                else {
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    i.putExtra("Usernetid", inputnetid);
                    i.putExtra("role", role);

                    startActivityForResult(i, 0);
                }
            } else {
                Toast failedmessage = Toast.makeText(MainActivity.this, "Invalid details.. Please try again..", Toast.LENGTH_LONG);
                failedmessage.show();

            }
        } else if (v.getId() == R.id.BTNForgotPassword){
            //Forgot Password logic happens here


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
