package ca.mcgill.ecse321.repairshopmanagementsystem.dto;

public class BillDto {
    private Integer billNo;
    private Integer price;
    private AppointmentDto appointment;
    private Boolean isPaid;

    public BillDto() {

    }

    public BillDto(Integer price, AppointmentDto appointment) {
        this.billNo = (price + appointment.hashCode()) % Integer.MAX_VALUE;
        this.price = price;
        this.appointment = appointment;
        this.isPaid = false;
    }

    public BillDto(Integer billNo, Integer price, AppointmentDto appointment, Boolean isPaid) {
        this.billNo = billNo;
        this.price = price;
        this.appointment = appointment;
        this.isPaid = isPaid;
    }
    public BillDto(Integer billNo, Integer price, Boolean isPaid) {
        this.billNo = billNo;
        this.price = price;

        this.isPaid = isPaid;
    }

    public Integer getBillNo() {
        return billNo;
    }

    public Integer getPrice() {
        return price;
    }

    public AppointmentDto getAppointment() {
        return appointment;
    }

    public Boolean getPaid() {
        return isPaid;
    }
}
