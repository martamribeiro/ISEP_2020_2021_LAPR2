package app.domain.model;

import app.controller.App;
import app.domain.interfaces.ExternalAPI;
import app.domain.store.*;
import app.mappers.dto.EmployeeDTO;
import app.mappers.dto.SpecialistDoctorDTO;
import auth.AuthFacade;
import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulo Maio <pam@isep.ipp.pt> and SRC-Code-23
 */
public class Company {

    /**
     * The company designation.
     */
    private String designation;

    private AuthFacade authFacade;

    /**
     * The parameter category store.
     */
    private ParameterCategoryStore parameterCategoryStore;

    /**
     * The parameter store.
     */
    private ParameterStore parameterStore;

    /**
     * The test store.
     */
    private TestStore testStore;

    /**
     * The client store.
     */
    private ClientStore clientStore;

    /**
     * The test type store.
     */
    private TestTypeStore testTypeStore;

    /**
     * The sample store.
     */
    private SampleStore sampleStore;

    /**
     * The clinical analysis laboratory store.
     */
    private ClinicalAnalysisLaboratoryStore calStore;

    private List<Employee> empList;
    private List<OrgRole> roles;

    public Company(String designation)
    {
        if (StringUtils.isBlank(designation))
            throw new IllegalArgumentException("Designation cannot be blank.");

        this.designation = designation;
        this.authFacade = new AuthFacade();
        this.testTypeStore = new TestTypeStore();
        this.clientStore = new ClientStore();
        this.parameterCategoryStore = new ParameterCategoryStore();
        this.testStore = new TestStore();
        this.empList = new ArrayList<>();
        this.roles = new ArrayList<>();
        this.parameterStore = new ParameterStore();
        OrgRole r1 = new OrgRole("ADMINISTRATOR");
        OrgRole r2 = new OrgRole("RECEPTIONIST");
        OrgRole r3 = new OrgRole("MED LAB TECH");
        OrgRole r4 = new OrgRole("LAB COORDINATOR");
        OrgRole r5 = new OrgRole("SPEC DOCTOR");
        OrgRole r6 = new OrgRole("C CHEM TECH");
        this.roles.add(r1);
        this.roles.add(r2);
        this.roles.add(r3);
        this.roles.add(r4);
        this.roles.add(r5);
        this.roles.add(r6);
        this.sampleStore = new SampleStore();
        this.calStore = new ClinicalAnalysisLaboratoryStore();
    }

    public String getDesignation() {
        return designation;
    }

    public AuthFacade getAuthFacade() {
        return authFacade;
    }

    /**
     * Calling the Parameter Category Store available in the system.
     *
     * @return the parameter category store.
     */
    public ParameterCategoryStore getParameterCategoryStore() {
        return parameterCategoryStore;
    }

    /**
     * Calling the Parameter Store available in the system.
     *
     * @return the parameter store.
     */
    public ParameterStore getParameterStore(){
        return parameterStore;
    }

    /**
     * Calling the Test Type Store available in the system.
     *
     * @return the test type store.
     */
    public TestTypeStore getTestTypeStore() {
        return testTypeStore;
    }

    /**
     * Calling the Client Store available in the system.
     *
     * @return the client store.
     */
    public ClientStore getClientStore(){
        return clientStore;
    }

    /**
     * Calling the Test Store available in the system.
     *
     * @return the test store.
     */
    public TestStore getTestStore(){
        return testStore;
    }

    /**
     * Calling the Sample Store available in the system.
     *
     * @return the sample store.
     */
    public SampleStore getSampleStore() {
        return sampleStore;
    }

    /**
     * Calling the Clinical Analysis Laboratory Store available in the system.
     *
     * @return the clinical analysis laboratory store.
     */
    public ClinicalAnalysisLaboratoryStore getCalStore() {
        return calStore;
    }

    /**
     * Calling the employee list available in the system.
     *
     * @return the employee list.
     */
    public List<Employee> getEmpList() {
        return empList;
    }

    /**
     * Calling the roles list available in the system.
     *
     * @return the roles list.
     */
    public List<OrgRole> getRoles() {
        return new ArrayList<>(roles);
    }

    /**
     * Get Organization Role according to the its description
     * @param roleDescription the role description.
     * @return Organization Role reference
     */
    private OrgRole getRoleByDescription(String roleDescription) {
        for (OrgRole role : roles) {
            if(role.getDescription().equalsIgnoreCase(roleDescription)){
                return role;
            }
        }
        throw new UnsupportedOperationException("Organization Role not found with given description: " + roleDescription);
    }

    /**
     * Called method through the RegisterEmployeeController to create a new employee
     * through a DTO containing all the requested data:
     * the name, address, phone number and email.
     *
     * @param empDTO the employee DTO.
     * @return instance of Employee Class with the information provided by the Dto
     * received by parameter.
     */
    public Employee createEmployee(EmployeeDTO empDTO) {
        String roleDesignation = empDTO.getRoleDesignation();
        OrgRole role = getRoleByDescription(roleDesignation);

        return new Employee(role, empDTO.getName(),
                empDTO.getAddress(), empDTO.getPhoneNumber(), empDTO.getEmail(), empDTO.getSocCode());
    }

    /**
     * Called method through the RegisterEmployeeController to create a new employee
     * through a DTO containing all the requested data:
     * the name, address, phone number, email and doctor index number.
     *
     * @param sdDTO the specialist doctor DTO.
     * @return instance of SpecialistDoctor Class with the information provided by the Dto
     * received by parameter.
     */
    public SpecialistDoctor createSpecialistDoctor(SpecialistDoctorDTO sdDTO) {
        String roleDesignation = sdDTO.getRoleDesignation();
        OrgRole role = getRoleByDescription(roleDesignation);

        return new SpecialistDoctor(role, sdDTO.getName(), sdDTO.getAddress(),
                sdDTO.getPhoneNumber(), sdDTO.getEmail(), sdDTO.getSocCode(), sdDTO.getDoctorIndexNumber());
    }

    /**
     * Called method through the RegisterEmployeeController to validate a new employee.
     *
     * @param emp the employee to be validated.
     * @return true if the employee was successfully validated,
     * otherwise return false.
     */
    public boolean validateEmployee(Employee emp) {
        if(emp == null)
            return false;
        return !this.empList.contains(emp);
    }

    /**
     * Called method through the RegisterEmployeeController to save a new employee.
     *
     * @param emp the employee to be saved.
     * @return true if the employee was successfully saved,
     * otherwise return false.
     */
    public boolean saveEmployee(Employee emp) {
        if(!validateEmployee(emp)) {
            return false;
        }
        return this.empList.add(emp);
    }

    /**
     * Called method through the RegisterEmployeeController to add the employee to the desired role.
     *
     * @param emp the employee ti be added to the role.
     * @return true if the employee was successfully added to the desired role,
     * otherwise return false.
     */
    public boolean addUserRole(Employee emp) {
        return this.getAuthFacade().addUserRole(emp.getRole().getDescription(), emp.getRole().getDescription());
    }

    /**
     * Called method through the RegisterEmployeeController to make the employee a user.
     *
     * @param emp the employee to be turned into a user.
     * @param generatedPassword the employee's password.
     * @return true if the employee was successfully turned into a user,
     * otherwise return false.
     */
    public boolean makeEmployeeAUser(Employee emp, String generatedPassword){
        boolean success = this.authFacade.addUserWithRole(emp.getName(), emp.getEmail(), generatedPassword, emp.getRole().getDescription());
        if(!success){
            addUserRole(emp);
            return this.authFacade.addUserWithRole(emp.getName(), emp.getEmail(), generatedPassword, emp.getRole().getDescription());
        }
        return success;
    }

    /**
     * Called method through the WriteReportController to create a new report
     * through a String containing the report text.
     *
     * @param reportText the report text.
     * @return instance of Report Class with the report text.
     */
    public Report createReport(String reportText){
        return new Report(reportText);
    }

    /**
     * Called method to get the External API, using Java Reflection.
     *
     * @return a instance of the External API to be used.
     *
     * @throws IllegalAccessException if there's a method invoked does not have access to the class representing the API
     * @throws ClassNotFoundException if the class name of the external API is not found
     * @throws InstantiationException if the class object of the external API cannot be instantiated
     */
    public ExternalAPI getExternalAPI() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        String className = App.getInstance().getBarcodeClassNameConfig();
        Class<?> oClass = Class.forName(className);
        return (ExternalAPI) oClass.newInstance();
    }



}