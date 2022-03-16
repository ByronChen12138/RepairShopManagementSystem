package ca.mcgill.ecse321.repairshopmanagementsystem.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
public class Shift {
    private Date date;

    public void setDate(Date value) {
        this.date = value;
    }

    public Date getDate() {
        return this.date;
    }

    private Time startTime;

    public void setStartTime(Time value) {
        this.startTime = value;
    }

    public Time getStartTime() {
        return this.startTime;
    }

    private Time endTime;

    public void setEndTime(Time value) {
        this.endTime = value;
    }

    public Time getEndTime() {
        return this.endTime;
    }

    private Appointment appointment;

    @OneToOne(mappedBy = "shift")
    public Appointment getAppointment() {
        return this.appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    private Schedule schedule;

    @ManyToOne(optional = false)
    public Schedule getSchedule() {
        return this.schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    private Assistant assistant;

    @ManyToOne(optional = false)
    public Assistant getAssistant() {
        return this.assistant;
    }

    public void setAssistant(Assistant assistant) {
        this.assistant = assistant;
    }

    private Integer shiftId;

    public void setShiftId(Integer value) {
        this.shiftId = value;
    }

    @Id
    public Integer getShiftId() {
        return this.shiftId;
    }
}
