package com.uta.login.mavsadvising;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class viewDelAdvisor extends AppCompatActivity {
    private ListView listview_adv;
    DataModel_Advisor[] modelItems;
    DataModel_Advisor selectedItem;
    TextView advisor_banner;
    MyAdapter_Adv adapter;
    Button delete_advisorSchedule;
    Button logout_advisor;
    RequestQueue queue;
    String NetId;
    String WeekOfTheDay = "Monday";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_del_advisor);
        NetId = getIntent().getStringExtra("NetID");
        advisor_banner = (TextView) findViewById(R.id.advisor_banner_textview);
        String banner = NetId +"'s Schedule";
        advisor_banner.setText(banner);
        listview_adv=(ListView) findViewById(R.id.view_adv_listview);
        logout_advisor = (Button) findViewById(R.id.logout_advisor_btn);
        logout_advisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
        delete_advisorSchedule = (Button) findViewById(R.id.del_advisor_btn);
        delete_advisorSchedule.setVisibility(View.VISIBLE);
        delete_advisorSchedule.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Code to delete the Advisor schedule

                deleteData();
            }
        });
        queue = Volley.newRequestQueue(this);
        populateData();
    }

    private void deleteData()
    {
        String URL = "http://omega.uta.edu/~oap8293/delete_advisor_schedule.php";
        Log.e("inside delete", "Deleted data");
        StringRequest stringReq = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Assign the response to an TextView
                try{
                    if(response.equals("Success"))
                    {
                        adapter.notifyDataSetChanged();
                        Toast msg = Toast.makeText(getApplicationContext(),"Delete Button clicked", Toast.LENGTH_LONG);
                        msg.show();

                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log.d(TAG , error.toString());
            }
        })
        {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> parameters =new HashMap<String, String>();
                if(NetId!= null) parameters.put("NetID", "Sajib");
                else parameters.put("NetID", NetId);
                parameters.put("WeekOfTheDay", WeekOfTheDay);
                return parameters;
            }
        };
        queue.add(stringReq);
    }

    private void populateData()
    {
        String URL = "http://omega.uta.edu/~oap8293/view_advisor_schedule.php";
        StringRequest stringReq = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Assign the response to an TextView
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray res = jsonObject.getJSONArray("result");

                    int limit = res.length();
                    modelItems = new DataModel_Advisor[limit];
                    for(int i=0;i< res.length();i++)
                    {
                        String NetID = res.getJSONObject(i).getString("NetID");
                        String DayOfTheWeek = res.getJSONObject(i).getString("DayOfWeek");
                        String StartDate = res.getJSONObject(i).getString("StartDate");
                        String EndDate = res.getJSONObject(i).getString("EndDate");
                        String StartTime = res.getJSONObject(i).getString("StartTime");
                        String EndTime = res.getJSONObject(i).getString("EndTime");
                        modelItems[i] = new DataModel_Advisor(NetID,DayOfTheWeek,StartDate,EndDate,StartTime,EndTime);
                    }
                    adapter = new MyAdapter_Adv(viewDelAdvisor.this, modelItems);
                    listview_adv.setAdapter(adapter);
                    listview_adv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log.d(TAG , error.toString());
            }
        })
        {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> parameters =new HashMap<String, String>();
                if(NetId!= null) parameters.put("NetID", NetId);
                else parameters.put("NetID", "Sajib");
                return parameters;
            }
        };
        queue.add(stringReq);

        listview_adv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // When clicked, show a toast with the TextView text
                selectedItem = (DataModel_Advisor) parent.getItemAtPosition(position);
                adapter.setSelectedIndex(position);
                adapter.notifyDataSetChanged();

                if (selectedItem != null) {
                    delete_advisorSchedule.setVisibility(View.VISIBLE);
                    WeekOfTheDay = selectedItem.getDayOfTheWeek();
                } else {
                    delete_advisorSchedule.setVisibility(View.GONE);
                }
                Log.e("Selected clicked", selectedItem.toString());
            }
        });
    }
}
