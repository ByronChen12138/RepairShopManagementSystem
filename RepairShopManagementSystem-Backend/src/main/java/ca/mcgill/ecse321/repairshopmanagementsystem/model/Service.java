package ca.mcgill.ecse321.repairshopmanagementsystem.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Service {
    private String serviceType;

    public void setServiceType(String value) {
        this.serviceType = value;
    }

   
    public String getServiceType() {
        return this.serviceType;
    }

    private Integer serviceId;

    public void setServiceId(Integer value) {
        this.serviceId = value;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getServiceId() {
        return this.serviceId;
    }

    private Set<Assistant> assistant;

    @ManyToMany(mappedBy = "service")
    public Set<Assistant> getAssistant() {
        return this.assistant;
    }

    public void setAssistant(Set<Assistant> assistants) {
        this.assistant = assistants;
    }

    private Appointment appointment;

    @OneToOne(optional = false)
    public Appointment getAppointment() {
        return this.appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

}
