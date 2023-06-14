package app.domain.shared;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulo Maio <pam@isep.ipp.pt>
 */
public class Constants {
    public static final String ROLE_ADMIN = "ADMINISTRATOR";
    public static final String ROLE_RECEPTIONIST = "RECEPTIONIST";
    public static final String ROLE_MED_LAB_TECHNICIAN = "MED LAB TECHNICIAN";
    public static final String ROLE_CLINICAL_CHEM_TECHNOLOGIST = "C CHEM TECH";
    public static final String ROLE_SPECIALIST_DOCTOR = "SPEC DOCTOR";
    public static final String ROLE_LAB_COORDINATOR = "LAB COORDINATOR";
    public static final String ROLE_CLIENT = "CLIENT";


    public static final String PARAMS_FILENAME = "config.properties";
    public static final String PARAMS_COMPANY_DESIGNATION = "Company.Designation";

    public static final String BLOOD_MODULE_1_NAME = "BloodModule1 (for blood tests, requires a key access)";
    public static final String BLOOD_MODULE_2_NAME = "BloodModule2 (for Blood tests, no access key)";
    public static final String COVID_MODULE_NAME = "CovidModule (for Covid 19 Tests)";

    public static final String BLOOD_EXTERNAL_ADAPTER_2 = "app.domain.adapters.ExternalModule2APIAdapter";
    public static final String BLOOD_EXTERNAL_ADAPTER_3 = "app.domain.adapters.ExternalModule3APIAdapter";
    public static final String COVID_EXTERNAL_ADAPTER = "app.domain.adapters.CovidReferenceValues1APIAdapter";

}
