package com.uta.login.mavsadvising;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by karthyvr on 6/28/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MAVSADVISING.db";
    private static final String LOGIN_TABLE_NAME = "Login";
    private static final String APPOINTMENT_TABLE_NAME = "AppointmentDetails";
    private static final String ADVISOR_TABLE_NAME = "AdvisorSchedule";
    SQLiteDatabase db = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        System.out.println("In DatabaseHelper constructor");
        this.db = super.getWritableDatabase();
        System.out.println("this.db ==" + this.db);

    }

    @Override
    //this method gets called only once during the DB creation.  All table creation scripts and static test Data go here
    public void onCreate(SQLiteDatabase db) {
        System.out.println("In DatabaseHelper onCreate");
        System.out.println("oncreate db ===" + db);

//        karthy
//        Login holds the login information
        db.execSQL("DROP TABLE IF EXISTS Login");
        db.execSQL(
                "create table Login " +
                        "(netid text primary key, password text,role text,FName text,LName text)"
        );

//        karthy
//        AdvisorSchedule holds the schedule information for advisor
        db.execSQL("DROP TABLE IF EXISTS AdvisorSchedule");
        db.execSQL(
                "create table AdvisorSchedule " +
                        "(netid text primary key, DayOfWeek text,Startdate date,Enddate date,Starttime text,Endtime text)"
        );


//karthy
// APPOINTMENT_DETAILS holds the actual appointment details with status, reason, student net id , advisor net id date and time of appointment

        db.execSQL("DROP TABLE IF EXISTS AppointmentDetails");
        db.execSQL(
                "create table AppointmentDetails " +
                        "(StudentNetId text , AdvisorNetid text ,AptDate date,AptStartTime text,AptEndTime text,AptStatus text,AptReason text,AptComment text, PRIMARY KEY (StudentNetId,AdvisorNetid,AptDate))"
        );

        System.out.println("db ==" + db);

        // Karthy
        //----------------------One time Creation Table Test Data for Login---------------------------
        insertUserDetails(db);
        insertAdvisorSchedule(db);

    }
//karthy
// Static Testdata for login

    public boolean insertUserDetails(SQLiteDatabase db) {

        System.out.println("insertUserDetails db ===" + db);
        ContentValues contentValues = new ContentValues();
        contentValues.put("netid", "karthy");
        contentValues.put("password", "test");
        contentValues.put("role", "student");
        contentValues.put("FName", "Karthy");
        contentValues.put("LName", "VR");
        db.insert("Login", null, contentValues);

        contentValues.put("netid", "sourabh");
        contentValues.put("password", "test");
        contentValues.put("role", "student");
        contentValues.put("FName", "sourabh");
        contentValues.put("LName", "sri");
        db.insert("Login", null, contentValues);

        contentValues.put("netid", "roopesh");
        contentValues.put("password", "test");
        contentValues.put("role", "student");
        contentValues.put("FName", "roopesh");
        contentValues.put("LName", "gowda");
        db.insert("Login", null, contentValues);

        contentValues.put("netid", "sajib123");
        contentValues.put("password", "test");
        contentValues.put("role", "advisor");
        contentValues.put("FName", "sajib");
        contentValues.put("LName", "dutta");
        db.insert("Login", null, contentValues);

        contentValues.put("netid", "elmasri123");
        contentValues.put("password", "test");
        contentValues.put("role", "advisor");
        contentValues.put("FName", "elmasri");
        contentValues.put("LName", "ramez");
        db.insert("Login", null, contentValues);

        contentValues.put("netid", "khalili123");
        contentValues.put("password", "test");
        contentValues.put("role", "advisor");
        contentValues.put("FName", "khalili");
        contentValues.put("LName", "b");
        db.insert("Login", null, contentValues);

        contentValues.put("netid", "super_user");
        contentValues.put("password", "test");
        contentValues.put("role", "admin");
        contentValues.put("FName", "super");
        contentValues.put("LName", "user");
        db.insert("Login", null, contentValues);


        return true;
    }

//karthy
// Static Testdata for Advisorschedule

    public boolean insertAdvisorSchedule(SQLiteDatabase db) {

        System.out.println("insertAdvisorSchedule db ===" + db);
        ContentValues contentValues = new ContentValues();
        contentValues.put("NetID", "sajib123");
        contentValues.put("DayOfWeek", "Tuesday");
        contentValues.put("Startdate", "2017-01-01");
        contentValues.put("Enddate", "2017-12-01");
        contentValues.put("Starttime", "10:30:00");
        contentValues.put("Endtime", "11:30:00");
        db.insert("AdvisorSchedule", null, contentValues);

        contentValues.put("NetID", "elmasri123");
        contentValues.put("DayOfWeek", "Tuesday");
        contentValues.put("Startdate", "2017-01-01");
        contentValues.put("Enddate", "2017-12-01");
        contentValues.put("Starttime", "09:30:00");
        contentValues.put("Endtime", "11:40:00");
        db.insert("AdvisorSchedule", null, contentValues);

        contentValues.put("NetID", "khalili123");
        contentValues.put("DayOfWeek", "Wednesday");
        contentValues.put("Startdate", "2017-01-01");
        contentValues.put("Enddate", "2017-12-01");
        contentValues.put("Starttime", "09:30:00");
        contentValues.put("Endtime", "11:40:00");
        db.insert("AdvisorSchedule", null, contentValues);

        return true;
    }

//karthy
//student schedule appointment in AppointmentDetails table

    public long insertAppointmentDetails(String advNetid, String stuNetid, String aptstartTime, String aptendTime, String aptStatus, String aptReason, String aptComment) {
        long id;

        ContentValues contentValues = new ContentValues();
        contentValues.put("AdvisorNetid", advNetid);
        contentValues.put("StudentNetId", stuNetid);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        contentValues.put("AptDate", dateFormat.format(new Date(System.currentTimeMillis())));
        System.out.println("aptstartTime = " + aptstartTime);
        System.out.println("aptstartTime = " + aptendTime);
        contentValues.put("AptStartTime", aptstartTime);
        contentValues.put("AptEndTime", aptendTime);
        contentValues.put("AptStatus", aptStatus);
        contentValues.put("AptReason", aptReason);
        contentValues.put("AptComment", aptComment);


        id = db.insert("AppointmentDetails", null, contentValues);

        System.out.println("the id is --->" + id);
        return id;

    }
    //karthy
//student Cancel Student Appointment

    public long CancelStudentAppointment(String stuNetid, String advNetid) {
        String can = "Cancelled";
        long test = -1;
        String strSQL = "UPDATE AppointmentDetails SET AptStatus = '" + can + "' WHERE AdvisorNetid = '" + advNetid + "' AND StudentNetId = '" + stuNetid + "'";
        db.execSQL(strSQL);
        return test;
    }

//karthy
// function to retrieve the role for the user based on the net id and password and redirect them to their home screens

    public String fetchUserRole(String inputnetid, String password) {
        String query = "select netid, password,role from Login";
        Cursor cursor = db.rawQuery(query, null);
        String role;
        role = "error";
        if (cursor != null) {
            //cursor.moveToFirst();
            boolean isUserFound = false;
            while (!isUserFound && cursor.moveToNext()) {
                if (inputnetid.equals(cursor.getString(0))) {
                    System.out.println("user found");
                    isUserFound = true;
                    if (password.equals(cursor.getString(1))) {
                        System.out.println("pwd matches");
                        role = cursor.getString(2);
                        System.out.println("role = " + role);
                    }
                }
            }
        }
        System.out.println("role ===" + role);
        return role;
    }

// Karthy
// Function to Fetch the Advisor Name from database for Student Schedule Appointment

    public List<String> getAdvisorNames() {
        List<String> advisorNames = new ArrayList<String>();

        String ipday = null;
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        System.out.println("Day of the week is ==" + dayOfWeek);
        if (dayOfWeek == 1) {
            ipday = "Sunday";
        } else if (dayOfWeek == 2) {
            ipday = "Monday";
        } else if (dayOfWeek == 3) {
            ipday = "Tuesday";
        } else if (dayOfWeek == 4) {
            ipday = "Wednesday";
        } else if (dayOfWeek == 5) {
            ipday = "Thursday";
        } else if (dayOfWeek == 6) {
            ipday = "Friday";
        } else if (dayOfWeek == 7) {
            ipday = "Saturday";
        }

        System.out.println("Day of the week is ==" + ipday);

        // Select All Query
        String query = "select FName from Login INNER JOIN AdvisorSchedule on Login.NetID = AdvisorSchedule.NetID AND AdvisorSchedule.DayOfWeek ='" + ipday + "'";
        Cursor cursor = db.rawQuery(query, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                advisorNames.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // returning Advisor Names List
        return advisorNames;
    }


    // Karthy
// Function to Fetch the start time  from advisorschedule/Login for Student Schedule Appointment

    public String getAdvisorstartTime(String advFName) {
        String advisorstartTime = null;

        String query = "select a.Starttime from AdvisorSchedule a where a.NetID IN (select l.NetID from Login l where l.FName = '" + advFName + "')";

        Cursor cursor = db.rawQuery(query, null);

        // looping through all rows and adding to list
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            advisorstartTime = cursor.getString(0);

        }

        // returning Advisor start time
        return advisorstartTime;
    }


    // Karthy
// Function to Fetch the endtime from advisorschedule/Login for Student Schedule Appointment

    public String getAdvisorendTime(String advFName) {
        String advisorendTime = null;

        String query = "select a.Endtime from AdvisorSchedule a where a.NetID IN (select l.NetID from Login l where l.FName ='" + advFName + "')";
        Cursor cursor = db.rawQuery(query, null);

        // looping through all rows and adding to list
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            advisorendTime = cursor.getString(0);

        }

        // returning Advisor end time
        return advisorendTime;
    }
    // Karthy
// Function to Fetch the netid of the advisor from Login Table using the Firstname

    public String getAdvisorNetId(String advName) {
        String advisorNetId = null;

        String query = "select NetID  from Login where FName ='" + advName + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            advisorNetId = cursor.getString(0);

        }

        // returning Advisor netId
        return advisorNetId;
    }

    // Karthy
// Function to Fetch the AdvisorName  from Login Table using the AdvisorNetid

    public String getAdvisorName(String advNetId) {
        String advisorName = null;

        String query = "select FName from Login where NetID ='" + advNetId + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            advisorName = cursor.getString(0);

        }

        // returning Advisor name
        return advisorName;
    }
    // Karthy
// Function to Fetch the AdvisorAppointment count for the day from Appointmentdetails Table using the AdvisorNetid

    public int getAdvisorAppointmentCountForDay(String advNetId) {
        int count = 0;

        String query = "select * from AppointmentDetails where AdvisorNetid ='" + advNetId + "'";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                count++;
            } while (cursor.moveToNext());
        }

        // returning the TotalCount of appointment for the advisor
        return count;
    }


    // Karthy
    // Function to Fetch the Appointmentdetails for the student using the model class AppointmentDetailsDomain

    public List<AppointmentDetailsDomain> getStudentAppointmentDetails(String studentnetid) {
        String act = "ActionRequired";
        String app = "Approved";
        String can = "Cancelled";
        List<AppointmentDetailsDomain> aptDetails = new ArrayList<AppointmentDetailsDomain>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        String selectQuery = "SELECT * FROM AppointmentDetails where StudentNetId ='" + studentnetid + "' AND AptStatus !='" + can + "'AND AptDate ='" + dateFormat.format(new Date(System.currentTimeMillis())) + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                AppointmentDetailsDomain aptDtl = new AppointmentDetailsDomain();
                aptDtl.setAdvisorName(cursor.getString(cursor.getColumnIndex("AdvisorNetid")));
                aptDtl.setStartTime(cursor.getString(cursor.getColumnIndex("AptStartTime")));
                aptDtl.setEndTime(cursor.getString(cursor.getColumnIndex("AptEndTime")));
                aptDtl.setStatus(cursor.getString(cursor.getColumnIndex("AptStatus")));
                aptDtl.setAptDate(cursor.getString(cursor.getColumnIndex("AptDate")));
                aptDtl.setReason(cursor.getString(cursor.getColumnIndex("AptReason")));
                aptDtl.setComment(cursor.getString(cursor.getColumnIndex("AptComment")));
                aptDetails.add(aptDtl);
            } while (cursor.moveToNext());
        }

        return aptDetails;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
