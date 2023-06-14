package app.mappers.dto;

public class EmployeeDTO {
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String socCode;
    private String roleDesignation;

    public EmployeeDTO(String roleDesignation,
                        String name,
                        String address,
                        String phoneNumber,
                        String email,
                        String socCode) {
        this.roleDesignation = roleDesignation;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.socCode = socCode;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getSocCode() {
        return socCode;
    }

    public String getRoleDesignation() {
        return roleDesignation;
    }
}

