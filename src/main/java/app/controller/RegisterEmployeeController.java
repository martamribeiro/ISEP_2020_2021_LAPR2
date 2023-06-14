package app.controller;

import app.domain.model.Company;
import app.domain.model.Employee;
import app.domain.model.OrgRole;
import app.domain.model.SpecialistDoctor;
import app.domain.shared.Constants;
import app.domain.shared.utils.PasswordUtils;
import app.mappers.OrgRoleMapper;
import app.mappers.dto.EmployeeDTO;
import app.mappers.dto.OrgRoleDTO;
import app.mappers.dto.SpecialistDoctorDTO;
import java.io.IOException;
import java.util.List;

/**
 * Controller class for registering a new employee.
 */
public class RegisterEmployeeController {

    /**
     * The company associated to the controller.
     */
    private Company company;

    /**
     * The employee associated to the controller.
     */
    private Employee emp;

    /**
     * The specialist doctor associated to the controller.
     */
    private SpecialistDoctor sd;

    /**
     * The generated password.
     */
    private String generatedPassword;

    /**
     * Builds an empty constructor for having the actual instance of the employee when instantiated.
     */
    public RegisterEmployeeController() {
        this(App.getInstance().getCompany());
    }

    /**
     * Build a Registed Employee's instance receiving the company.
     *
     * @param company company associated to the Controller.
     */
    public RegisterEmployeeController(Company company) {
        this.company = company;
        this.emp = null;
        this.sd = null;
    }

    /**
     * Calling method to create a new employee
     * through a DTO containing all the requested data:
     * the name, address, phone number, email and, in caso of the employee being a specialist doctor, the doctor index number.
     *
     * @param empDTO the employee DTO
     * @return true if the employee was successfully created,
     * otherwise returns false.
     */
    public boolean createEmployee(EmployeeDTO empDTO) {
        if (empDTO.getRoleDesignation().equalsIgnoreCase(Constants.ROLE_SPECIALIST_DOCTOR))
            this.emp = this.company.createSpecialistDoctor((SpecialistDoctorDTO) empDTO);
        else
            this.emp = this.company.createEmployee(empDTO);
        return this.company.validateEmployee(emp);
    }

    /**
     * Calling method to save a new employee.
     *
     * @return true if the new employee was successfully saved,
     * otherwise returns false.
     */
    public boolean saveEmployee(){
        return this.company.saveEmployee(emp);
    }

    /**
     * Calling method to get the list of Roles available in the system.
     * It makes use of a mapper to transform the said list to DTO to reduce coupling.
     *
     * @return true if the list was successfully received,
     * otherwise returns false.
     */
    public List<OrgRoleDTO> getRoles() {
        List<OrgRole> roles = this.company.getRoles();
        OrgRoleMapper mapper = new OrgRoleMapper();
        return mapper.toDTO(roles);

    }

    /**
     * Makes employee a user of the system.
     *
     * @return true if the employee is successfully turned into a user of the system,
     * otherwise returns false.
     */
    public boolean makeEmployeeAUser(){
        this.generatedPassword = PasswordUtils.generateRandomPassword();
        if(this.generatedPassword != null)
            return this.company.makeEmployeeAUser(emp, generatedPassword);
        return false;
    }

    /**
     * Sends the user credentials to a text file.
     *
     * @return true if the credentials are successfully sent to a text file.
     * otherwise returns false.
     *
     * @throws IOException if the credentials aren't successfully sent to the text file.
     */
    public boolean makeEmployeeAnUserAndSendPassword() throws IOException {
        if(!makeEmployeeAUser())
            return false;
        return PasswordUtils.writePassword(generatedPassword, emp.getEmail());
    }
}
