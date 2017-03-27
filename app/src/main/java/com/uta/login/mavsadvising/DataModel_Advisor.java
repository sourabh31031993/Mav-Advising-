package com.uta.login.mavsadvising;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * Created by Roopesh on 7/20/2016.
 */
public class DataModel_Advisor implements Serializable {
    String NetID;
    String dayOfTheWeek;
    String startDate;
    String endDate;
    String startTime;
    String endTime;

    DataModel_Advisor(String NetID, String dayOfTheWeek, String startDate, String endDate, String startTime, String endTime)
    {
        this.NetID = NetID;
        this.dayOfTheWeek = dayOfTheWeek;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDayOfTheWeek()
    {
        return dayOfTheWeek;
    }
    public String getStartTime()
    {
        return startTime;
    }
    public String getEndTime()
    {
        return endTime;
    }

public String getAdvisorName()
{
    return this.NetID;
}

}
