package app.domain.shared;

import app.domain.model.TestType;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulo Maio <pam@isep.ipp.pt>
 */
public class Constants {
    public static final String SERILIAZTION_FILE_NAME = "app.dat";
    public static final String ROLE_ADMIN = "ADMINISTRATOR";
    public static final String ROLE_RECEPTIONIST = "RECEPTIONIST";
    public static final String ROLE_MED_LAB_TECHNICIAN = "MED LAB TECHNICIAN";
    public static final String ROLE_CLINICAL_CHEM_TECHNOLOGIST = "C CHEM TECH";
    public static final String ROLE_SPECIALIST_DOCTOR = "SPEC DOCTOR";
    public static final String ROLE_LAB_COORDINATOR = "LAB COORDINATOR";
    public static final String ROLE_CLIENT = "CLIENT";

    public static final String TIN_COMPARATOR_ID = "TIN";

    public static final String PARAMS_FILENAME = "config.properties";
    public static final String PARAMS_COMPANY_DESIGNATION = "Company.Designation";

    public static final String CLASS_SORT_ALGORITHM = "app.domain.adapters.BubbleSortAdapter";
    //created because it's handy for the tests
    public static final String CLASS_BARCODE_API = "app.domain.adapters.BarbecueAdapter";
    public static final String CLASS_SIMPLE_REGRESSION_MODEL = "app.domain.adapters.SimpleLinearRegressionAdapter";
    public static final String CLASS_MULTIPLE_REGRESSION_MODEL = "app.domain.adapters.MultipleLinearRegressionAdapter";
    public static final String DATE_INTERVAL = "28/05/2021-7/06/2021";
    public static final String HISTORICAL_POINTS = "15";

    public static final String CONFIDENCE_LEVEL = "0.95";
    public static final String SIGNIFICANCE_LEVEL = "0.05";

    public static final String DAY_DATA = "Day";
    public static final String WEEK_DATA = "Week";
    public static final String TEST_VARIABLE = "Covid-19 Tests Realized";
    public static final String CHOSEN_REG_COEFFICIENT_A = "a";
    public static final String CHOSEN_REG_COEFFICIENT_B = "b";
    public static final int MINIMUM_ALLOWED_WEEK_DAYS = 13;

    public static final String BLOOD_MODULE_1_NAME = "BloodModule1 (for blood tests, requires a key access)";
    public static final String BLOOD_MODULE_2_NAME = "BloodModule2 (for Blood tests, no access key)";
    public static final String COVID_MODULE_NAME = "CovidModule (for Covid 19 Tests)";

    public static final String BLOOD_EXTERNAL_ADAPTER_2 = "app.domain.adapters.ExternalModule2APIAdapter";
    public static final String BLOOD_EXTERNAL_ADAPTER_3 = "app.domain.adapters.ExternalModule3APIAdapter";
    public static final String COVID_EXTERNAL_ADAPTER = "app.domain.adapters.CovidReferenceValues1APIAdapter";

    public static final String BENCHMARK_ALGORITHM_ADAPTER = "app.domain.adapters.BenchmarkAlgorithmAdapter";
    public static final String BRUTEFORCE_ALGORITHM_ADAPTER = "app.domain.adapters.BruteForceAlgorithmAdapter";

    //Mathematics
    //-> Simple Linear Regression
    public static final double A0 = 0;
    public static final double B0 = 0;


}
