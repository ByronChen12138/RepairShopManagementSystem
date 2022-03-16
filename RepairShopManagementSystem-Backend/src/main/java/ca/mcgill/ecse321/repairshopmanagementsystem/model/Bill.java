package ca.mcgill.ecse321.repairshopmanagementsystem.model;

import javax.persistence.*;

@Entity
public class Bill {
    private Integer billNo;

    public void setBillNo(Integer value) {
        this.billNo = value;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getBillNo() {
        return this.billNo;
    }

    private Integer price;

    public void setPrice(Integer value) {
        this.price = value;
    }

    public Integer getPrice() {
        return this.price;
    }

    private Appointment appointment;

    @ManyToOne(optional = false)
    public Appointment getAppointment() {
        return this.appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    private Boolean isPaid;

    public void setIsPaid(Boolean value) {
        this.isPaid = value;
    }

    public Boolean getIsPaid() {
        return this.isPaid;
    }
}
