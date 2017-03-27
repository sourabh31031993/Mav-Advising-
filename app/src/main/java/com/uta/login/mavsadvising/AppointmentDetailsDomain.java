package com.uta.login.mavsadvising;

/**
 * Created by karthyvr on 7/17/16.
 */
public class AppointmentDetailsDomain {


    public String studentNetID;
    public String advisorName;
    public String AptDate;
    public String startTime;
    public String endTime;
    public String status;
    public String reason;
    public String comment;

    public String getAptDate() {
        return AptDate;
    }

    public void setAptDate(String aptDate) {
        AptDate = aptDate;
    }


    public String getStudentNetID() {
        return studentNetID;
    }

    public void setStudentNetID(String studentNetID) {
        this.studentNetID = studentNetID;
    }

    public String getAdvisorName() {
        return advisorName;
    }

    public void setAdvisorName(String advisorName) {
        this.advisorName = advisorName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
