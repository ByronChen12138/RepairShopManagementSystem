package ca.mcgill.ecse321.repairshopmanagementsystem.dto;

import java.util.List;

public class ServiceDto {
    private String serviceType;
    private List<AssistantDto> assistant;
    private AppointmentDto appointment;

    public ServiceDto() {
    }

    public ServiceDto(String serviceType) {
        this.serviceType = serviceType;
    }

    public ServiceDto(String serviceType, List<AssistantDto> assistant, AppointmentDto appointment) {
        this.serviceType = serviceType;
        this.assistant = assistant;
        this.appointment = appointment;
    }

    public ServiceDto(String serviceType, List<AssistantDto> assistant) {
        this.serviceType = serviceType;
        this.assistant = assistant;

    }

    public String getServiceType() {
        return this.serviceType;
    }

    public List<AssistantDto> getAssistant() {
        return this.assistant;
    }

    public AppointmentDto getAppointment() {
        return this.appointment;
    }
}
