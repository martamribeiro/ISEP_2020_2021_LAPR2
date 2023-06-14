package app.mappers.dto;


public class SpecialistDoctorDTO extends EmployeeDTO {
    private String doctorIndexNumber;

    public SpecialistDoctorDTO(String roleDesignation,
                            String name,
                            String address,
                            String phoneNumber,
                            String email,
                            String socCode,
                            String doctorIndexNumber) {
        super(roleDesignation, name, address, phoneNumber, email, socCode);
        this.doctorIndexNumber = doctorIndexNumber;
    }

    public String getDoctorIndexNumber() {
        return doctorIndexNumber;
    }
}
