package app.domain.model;

import app.domain.interfaces.SubMaxSumAlgorithms;
import app.domain.model.Company;
import app.domain.model.Test;
import app.domain.shared.Constants;
import app.domain.store.TestStore;
import org.apache.commons.lang3.time.DateUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Represents the company performance through:
 *
 *
 * @author Marta Ribeiro 1201592
 */
public class CompanyPerformance {

    /**
     * The company associated with the Company Performance.
     */
    private Company company;

    /**
     * Set the company associated with the Company Performance.
     *
     * @param company the company to associate with the Company Performance
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * The beginning date of the interval.
     */
    private Date beginningDate;

    /**
     * The ending date of the interval.
     */
    private Date endingDate;

    /**
     * The chosen algorithm.
     */
    private String chosenAlg;

    /**
     * The number of clients.
     */
    private int clientsNum;

    /**
     * The number os processed tests.
     */
    private int processTestsNum;

    /**
     * The tests info for the days of the interval.
     */
    private ArrayList<int[]> testInfoDay;

    /**
     * The tests info for the weeks of the interval.
     */
    private ArrayList<int[]> testInfoWeek;

    /**
     * The tests info for the months of the interval.
     */
    private ArrayList<int[]> testInfoMonth;

    /**
     * The tests info for the years of the interval.
     */
    private ArrayList<int[]> testInfoYear;

    /**
     * The beginning and end of the contiguous subsequence with maximum sum.
     */
    private Date[] worstSubInt;

    public CompanyPerformance(Date beginningDate, Date endingDate, String chosenAlg, Company company) {
        this.company=company;
        this.beginningDate=beginningDate;
        this.endingDate=endingDate;
        this.chosenAlg=chosenAlg;
        this.clientsNum=getClientsInfoPerInterval(getDays());
        this.processTestsNum=getNumTestsProcessedInterval(getDays());
        this.testInfoDay=getTestInfoPerDay(getDays());
        this.testInfoWeek=getTestInfoPerWeek(getDays());
        this.testInfoMonth=getTestInfoPerMonth(getDays());
        this.testInfoYear=getTestInfoPerYear(getDays());
        this.worstSubInt=findWorstSubIntWithChosenAlgorithm(getDays(),chosenAlg);
    }

    /**
     * Returns the number of clients.
     * @return number of clients
     */
    public int getClientsNum() {
        return clientsNum;
    }

    /**
     * Returns the number os processed tests.
     * @return number of processed tests
     */
    public int getProcessTestsNum() {
        return processTestsNum;
    }

    /**
     * Returns the tests info for the days of the interval.
     * @return tests info
     */
    public ArrayList<int[]> getTestInfoDay() {
        return testInfoDay;
    }

    /**
     * Returns the tests info for the weeks of the interval.
     * @return tests info
     */
    public ArrayList<int[]> getTestInfoWeek() {
        return testInfoWeek;
    }

    /**
     * Returns the tests info for the months of the interval.
     * @return tests info
     */
    public ArrayList<int[]> getTestInfoMonth() {
        return testInfoMonth;
    }

    /**
     * Returns the tests info for the years of the interval.
     * @return tests info
     */
    public ArrayList<int[]> getTestInfoYear() {
        return testInfoYear;
    }

    /**
     * Returns the beginning and end of the contiguous subsequence with maximum sum.
     * @return beginning and end of the contiguous subsequence with maximum sum
     */
    public Date[] getWorstSubInt() {
        return worstSubInt;
    }

    /**
     * Gets the number of clients for an interval
     * @return the number of clients for an interval
     */
    public int getClientsInfoPerInterval(ArrayList<Date> days){
        Date endingDay = new Date(days.get(days.size()-1).getYear(), days.get(days.size()-1).getMonth(), days.get(days.size()-1).getDate(), 20, 0, 0);
        TestStore testStore = this.company.getTestStore();
        ArrayList<String> clientEmails = new ArrayList<>();
        String clientEmail;
        boolean repeated = false;
        String notEmp = "";
        clientEmails.add(notEmp);
        for (Test test : testStore.getTests()){
            if (test.getDateOfTestRegistration().before(endingDay)){
                clientEmail=test.getClient().getEmail();
                for (int i = 0; i < clientEmails.size(); i++) {
                    if (clientEmail.equals(clientEmails.get(i))){
                        repeated=true;
                    }
                }
                if (!repeated){
                    clientEmails.add(clientEmail);
                }
                repeated=false;
            }
        }
        return clientEmails.size()-1;
    }

    /**
     * Gets the number of processed tests for an interval
     * @return the number of processed tests for an interval
     */
    public int getNumTestsProcessedInterval(ArrayList<Date> days){
        Date endingDay = new Date(days.get(days.size()-1).getYear(), days.get(days.size()-1).getMonth(), days.get(days.size()-1).getDate(), 20, 0, 0);
        TestStore testStore = this.company.getTestStore();
        int quant=0;
        for (Test test : testStore.getTests()){
            if (test.getDateOfValidation()!=null && test.getDateOfValidation().before(endingDay)){
                quant++;
            }
        }
        return quant;
    }

    /**
     * Gets an ArrayList with the tests info for the days of the interval
     * @return ArrayList with the tests info for the days of the interval
     */
    public ArrayList<int[]> getTestInfoPerDay(ArrayList<Date> days){
        ArrayList<int[]> testInfoPerDay = new ArrayList<>();
        int[] testInfo = new int[2];
        TestStore testStore = this.company.getTestStore();
        Date beginningDay;
        Date endingDay;
        for (Date day : days) {
            beginningDay = new Date(day.getYear(), day.getMonth(), day.getDate(), 8, 0, 0);
            endingDay = new Date(day.getYear(), day.getMonth(), day.getDate(), 19, 59, 59);
            testInfo[0] = testStore.getNumTestsWaitingForResultsDayOrInterval(beginningDay, endingDay);
            testInfo[1] = testStore.getNumTestsWaitingForDiagnosisDayOrInterval(beginningDay, endingDay);
            testInfoPerDay.add(testInfo.clone());
        }
        return testInfoPerDay;
    }

    /**
     * Gets an ArrayList with the tests info for the weeks of the interval
     * @return ArrayList with the tests info for the weeks of the interval
     */
    public ArrayList<int[]> getTestInfoPerWeek(ArrayList<Date> days){
        ArrayList<int[]> testInfoPerWeek = new ArrayList<>();
        int[] testInfo = new int[2];
        TestStore testStore = this.company.getTestStore();
        ArrayList<ArrayList<Date>> weeks = new ArrayList<>();
        ArrayList<Date> week = new ArrayList<>();
        if (days.get(days.size()-1).getDay()!=6){
            Date lastSat = days.get(days.size()-1);
            do {
                lastSat = DateUtils.addDays(lastSat,-1);
            }while (lastSat.getDay()!=6);
            if (lastSat.before(days.get(0))){
                for (Date date : days){
                    week.add(date);
                }
                weeks.add((ArrayList<Date>) week.clone());
                week.clear();
            } else {
                for (Date date : days) {
                    if (date.getDay() != 0) {
                        week.add(date);
                    }
                    if (date.getDay() == 6) {
                        weeks.add((ArrayList<Date>) week.clone());
                        week.clear();
                    } else if (date==days.get(days.size()-1)) {
                        weeks.add((ArrayList<Date>) week.clone());
                        week.clear();
                    }
                }
            }
        } else {
            for (Date date : days) {
                if (date.getDay() != 0) {
                    week.add(date);
                }
                if (date.getDay() == 6) {
                    weeks.add((ArrayList<Date>) week.clone());
                    week.clear();
                }
            }
        }
        Date beginningDay;
        Date endingDay;
        for (ArrayList<Date> singleWeek : weeks) {
            beginningDay = new Date(singleWeek.get(0).getYear(), singleWeek.get(0).getMonth(), singleWeek.get(0).getDate(), 8, 0, 0);
            endingDay = new Date(singleWeek.get(singleWeek.size()-1).getYear(), singleWeek.get(singleWeek.size()-1).getMonth(), singleWeek.get(singleWeek.size()-1).getDate(), 19, 59, 59);
            testInfo[0] = testStore.getNumTestsWaitingForResultsDayOrInterval(beginningDay, endingDay);
            testInfo[1] = testStore.getNumTestsWaitingForDiagnosisDayOrInterval(beginningDay, endingDay);
            testInfoPerWeek.add(testInfo.clone());
        }
        return testInfoPerWeek;
    }

    /**
     * Gets an ArrayList with the tests info for the months of the interval
     * @return ArrayList with the tests info for the months of the interval
     */
    public ArrayList<int[]> getTestInfoPerMonth(ArrayList<Date> days){
        ArrayList<int[]> testInfoPerMonth = new ArrayList<>();
        int[] testInfo = new int[2];
        TestStore testStore = this.company.getTestStore();
        ArrayList<ArrayList<Date>> months = new ArrayList<>();
        ArrayList<Date> month = new ArrayList<>();
        if (days.get(days.size()-1).getDate()!=31 &&
                (days.get(days.size()-1).getMonth() == Calendar.JANUARY || days.get(days.size()-1).getMonth() == Calendar.MARCH || days.get(days.size()-1).getMonth() == Calendar.MAY ||
                        days.get(days.size()-1).getMonth() == Calendar.JULY || days.get(days.size()-1).getMonth() == Calendar.AUGUST || days.get(days.size()-1).getMonth() == Calendar.OCTOBER || days.get(days.size()-1).getMonth() == Calendar.DECEMBER)) {
            Date lastDayMonth = days.get(days.size() - 1);
            do {
                lastDayMonth = DateUtils.addDays(lastDayMonth, -1);
            } while (lastDayMonth.getDate() != 31);
            if (lastDayMonth.before(days.get(0))) {
                for (Date date : days) {
                    month.add(date);
                }
                months.add((ArrayList<Date>) month.clone());
                month.clear();
            } else {
                for (Date date : days) {
                    month.add(date);
                    if (date.getDate() == 31) {
                        months.add((ArrayList<Date>) month.clone());
                        month.clear();
                    } else if (date == days.get(days.size() - 1)) {
                        months.add((ArrayList<Date>) month.clone());
                        month.clear();
                    }
                }
            }
        }else if(days.get(days.size()-1).getDate()!=30 && (days.get(days.size()-1).getMonth() == Calendar.APRIL || days.get(days.size()-1).getMonth() == Calendar.JUNE || days.get(days.size()-1).getMonth() == Calendar.SEPTEMBER || days.get(days.size()-1).getMonth() == Calendar.NOVEMBER)) {
            Date lastDayMonth = days.get(days.size() - 1);
            do {
                lastDayMonth = DateUtils.addDays(lastDayMonth, -1);
            } while (lastDayMonth.getDate() != 30);
            if (lastDayMonth.before(days.get(0))) {
                for (Date date : days) {
                    month.add(date);
                }
                months.add((ArrayList<Date>) month.clone());
                month.clear();
            } else {
                for (Date date : days) {
                    month.add(date);
                    if (date.getDate() == 30) {
                        months.add((ArrayList<Date>) month.clone());
                        month.clear();
                    } else if (date == days.get(days.size() - 1)) {
                        months.add((ArrayList<Date>) month.clone());
                        month.clear();
                    }
                }
            }
        } else if(days.get(days.size()-1).getDate()!=29 && (days.get(days.size()-1).getMonth() == Calendar.FEBRUARY && (days.get(days.size()-1).getYear() % 400 == 0) || ((days.get(days.size()-1).getYear() % 100) != 0 && (days.get(days.size()-1).getYear() % 4 == 0)))) {
            Date lastDayMonth = days.get(days.size() - 1);
            do {
                lastDayMonth = DateUtils.addDays(lastDayMonth, -1);
            } while (lastDayMonth.getDate() != 29);
            if (lastDayMonth.before(days.get(0))) {
                for (Date date : days) {
                    month.add(date);
                }
                months.add((ArrayList<Date>) month.clone());
                month.clear();
            } else {
                for (Date date : days) {
                    month.add(date);
                    if (date.getDate() == 29) {
                        months.add((ArrayList<Date>) month.clone());
                        month.clear();
                    } else if (date == days.get(days.size() - 1)) {
                        months.add((ArrayList<Date>) month.clone());
                        month.clear();
                    }
                }
            }
        }else if(days.get(days.size()-1).getDate()!=28 && (days.get(days.size()-1).getMonth() == Calendar.FEBRUARY && (days.get(days.size()-1).getYear() % 400 != 0) && ((days.get(days.size()-1).getYear() % 100) == 0 || (days.get(days.size()-1).getYear() % 4 != 0)))){
            Date lastDayMonth = days.get(days.size() - 1);
            do {
                lastDayMonth = DateUtils.addDays(lastDayMonth, -1);
            } while (lastDayMonth.getDate() != 28);
            if (lastDayMonth.before(days.get(0))) {
                for (Date date : days) {
                    month.add(date);
                }
                months.add((ArrayList<Date>) month.clone());
                month.clear();
            } else {
                for (Date date : days) {
                    month.add(date);
                    if (date.getDate() == 28) {
                        months.add((ArrayList<Date>) month.clone());
                        month.clear();
                    } else if (date == days.get(days.size() - 1)) {
                        months.add((ArrayList<Date>) month.clone());
                        month.clear();
                    }
                }
            }
        }else {
            for (Date date : days) {
                if (date.getMonth() == Calendar.JANUARY || date.getMonth() == Calendar.MARCH || date.getMonth() == Calendar.MAY ||
                        date.getMonth() == Calendar.JULY || date.getMonth() == Calendar.AUGUST || date.getMonth() == Calendar.OCTOBER || date.getMonth() == Calendar.DECEMBER) {
                    if (date.getDay() != 0) {
                        month.add(date);
                    }
                    if (date.getDate() == 31) {
                        months.add((ArrayList<Date>) month.clone());
                        month.clear();
                    }
                } else if (date.getMonth() == Calendar.APRIL || date.getMonth() == Calendar.JUNE || date.getMonth() == Calendar.SEPTEMBER || date.getMonth() == Calendar.NOVEMBER) {
                    if (date.getDay() != 0) {
                        month.add(date);
                    }
                    if (date.getDate() == 30) {
                        months.add((ArrayList<Date>) month.clone());
                        month.clear();
                    }
                } else if (date.getMonth() == Calendar.FEBRUARY && (date.getYear() % 400 == 0) || ((date.getYear() % 100) != 0 && (date.getYear() % 4 == 0))) {
                    if (date.getDay() != 0) {
                        month.add(date);
                    }
                    if (date.getDate() == 29) {
                        months.add((ArrayList<Date>) month.clone());
                        month.clear();
                    }
                } else {
                    if (date.getDay() != 0) {
                        month.add(date);
                    }
                    if (date.getDate() == 28) {
                        months.add((ArrayList<Date>) month.clone());
                        month.clear();
                    }
                }
            }
        }
        Date beginningDay;
        Date endingDay;
        for (ArrayList<Date> singleMonth : months) {
            beginningDay = new Date(singleMonth.get(0).getYear(), singleMonth.get(0).getMonth(), singleMonth.get(0).getDate(), 8, 0, 0);
            endingDay = new Date(singleMonth.get(singleMonth.size()-1).getYear(), singleMonth.get(singleMonth.size()-1).getMonth(), singleMonth.get(singleMonth.size()-1).getDate(), 19, 59, 59);
            testInfo[0] = testStore.getNumTestsWaitingForResultsDayOrInterval(beginningDay, endingDay);
            testInfo[1] = testStore.getNumTestsWaitingForDiagnosisDayOrInterval(beginningDay, endingDay);
            testInfoPerMonth.add(testInfo.clone());
        }
        return testInfoPerMonth;
    }

    /**
     * Gets an ArrayList with the tests info for the years of the interval
     * @return ArrayList with the tests info for the years of the interval
     */
    public ArrayList<int[]> getTestInfoPerYear(ArrayList<Date> days){
        ArrayList<int[]> testInfoPerYear = new ArrayList<>();
        int[] testInfo = new int[2];
        TestStore testStore = this.company.getTestStore();
        ArrayList<ArrayList<Date>> years = new ArrayList<>();
        ArrayList<Date> year = new ArrayList<>();
        if (days.get(days.size()-1).getMonth()!=Calendar.DECEMBER && days.get(days.size()-1).getDate()!=31){
            Date lastDayYear = days.get(days.size() - 1);
            do {
                lastDayYear = DateUtils.addDays(lastDayYear, -1);
            } while (lastDayYear.getMonth()!=Calendar.DECEMBER && lastDayYear.getDate()!=31);
            if (lastDayYear.before(days.get(0))) {
                for (Date date : days) {
                    year.add(date);
                }
                years.add((ArrayList<Date>) year.clone());
                year.clear();
            } else {
                for (Date date : days) {
                    year.add(date);
                    if (date.getMonth()==Calendar.DECEMBER && date.getDate()==31) {
                        years.add((ArrayList<Date>) year.clone());
                        year.clear();
                    } else if (date == days.get(days.size() - 1)) {
                        years.add((ArrayList<Date>) year.clone());
                        year.clear();
                    }
                }
            }
        } else {
            for (Date date : days) {
                if (date.getDay() != 0) {
                    year.add(date);
                }
                if ((date.getMonth() == Calendar.DECEMBER && date.getDate() == 31)) {
                    years.add(year);
                    year.clear();
                }
            }
        }
        Date beginningDay;
        Date endingDay;
        for(ArrayList<Date> singleYear : years) {
            beginningDay = new Date(singleYear.get(0).getYear(), singleYear.get(0).getMonth(), singleYear.get(0).getDate(), 8, 0, 0);
            endingDay = new Date(singleYear.get(singleYear.size()-1).getYear(), singleYear.get(singleYear.size()-1).getMonth(), singleYear.get(singleYear.size()-1).getDate(), 19, 59, 59);
            testInfo[0] = testStore.getNumTestsWaitingForResultsDayOrInterval(beginningDay, endingDay);
            testInfo[1] = testStore.getNumTestsWaitingForDiagnosisDayOrInterval(beginningDay, endingDay);
            testInfoPerYear.add(testInfo.clone());
        }
        return testInfoPerYear;
    }

    /**
     * Creates an ArrayList with all the days of the interval
     * @return an ArrayList with all the days of the interval
     */
    public ArrayList<Date> getDays(){
        ArrayList<Date> days = new ArrayList<>();
        Date day = this.beginningDate;
        Date end = new Date(this.endingDate.getYear(), this.endingDate.getMonth(), this.endingDate.getDate(), 8,0,0);
        if (day.getYear()==end.getYear() && day.getMonth()==end.getMonth() && day.getDate()==end.getDate()) {
            days.add(this.endingDate);
        } else {
            if (end.getDay() != 0) {
                do {
                    if (day.getDay() != 0 && (day.getYear() != end.getYear() || day.getMonth() != end.getMonth() || day.getDate() != end.getDate()))
                        days.add(day);
                    day = DateUtils.addDays(day, 1);
                } while (day.before(end));
                end.setHours(19);
                end.setMinutes(59);
                end.setSeconds(59);
                days.add(end);
            } else {
                end = DateUtils.addDays(day, -1);
                do {
                    if (day.getDay() != 0 && (day.getYear() != end.getYear() || day.getMonth() != end.getMonth() || day.getDate() != end.getDate()))
                        days.add(day);
                    day = DateUtils.addDays(day, 1);
                } while (day.before(end));
                end.setHours(19);
                end.setMinutes(59);
                end.setSeconds(59);
                days.add(end);
            }
        }
        return days;
    }

    /**
     * Creates an array with the difference between the number of new tests and the number of results available to the client during each half an hour period
     *
     * @param days days of the interval
     * @return an array with the difference between the number of new tests and the number of results available to the client during each half an hour period
     */
    public int[] makeIntervalArray(ArrayList<Date> days){
        TestStore testStore = this.company.getTestStore();
        ArrayList<Integer> intervalArrayList = new ArrayList<>();
        int numRegistered = 0, numValidated = 0, intToKeep = 0;
        Date finalDate = new Date(days.get(days.size()-1).getYear(),days.get(days.size()-1).getMonth(),days.get(days.size()-1).getDate(),8,0,0);
        days.set(days.size()-1,finalDate);
        for (Date day : days){
            Date date1 = day;
            Date date2 = DateUtils.addMinutes(date1, 30);
            Date finish = new Date(day.getYear(), day.getMonth(), day.getDate(), 20,0,1);
            Date endDay = (Date)date2.clone();
            endDay = DateUtils.addSeconds(endDay,-1);
            do{
                if (date1.getHours()>=8 && date2.getHours()<20) {
                    numRegistered = testStore.getNumberOfTestsByIntervalDateOfTestRegistration(date1, date2);
                    numValidated = testStore.getNumberOfTestsByIntervalDateOfDiagnosis(date1, date2);
                    intToKeep = numRegistered - numValidated;
                    intervalArrayList.add(intToKeep);
                } else if ((date2.getHours()==20 && date2.getMinutes()==0)) {
                    numRegistered = testStore.getNumberOfTestsByIntervalDateOfTestRegistration(date1, endDay);
                    numValidated = testStore.getNumberOfTestsByIntervalDateOfDiagnosis(date1, endDay);
                    intToKeep = numRegistered - numValidated;
                    intervalArrayList.add(intToKeep);
                }
                date1 = DateUtils.addMinutes(date1, 30);
                date2 = DateUtils.addMinutes(date2, 30);
                endDay = DateUtils.addMinutes(endDay, 30);
            } while (date2.before(finish));
        }
        int[] intervalArray = new int[intervalArrayList.size()];
        for (int i = 0; i < intervalArray.length; i++) {
            intervalArray[i] = intervalArrayList.get(i).intValue();
        }
        return intervalArray;
    }

    /**
     * Finds the beginning and the ending dates of the contiguous subsequence with maximum sum of an interval, through the chosen algorithm
     *
     * @param days days of the interval
     * @param chosenAlgorithm the chosen algorithm
     * @return the beginning and the ending dates of the contiguous subsequence with maximum sum
     */
    public Date[] findWorstSubIntWithChosenAlgorithm(ArrayList<Date> days, String chosenAlgorithm) {
        int[] interval = makeIntervalArray(days);
        String algorithmClass = getChosenAlgorithmAdapter(chosenAlgorithm);
        Class<?> oClass = null;
        try {
            oClass = Class.forName(algorithmClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        SubMaxSumAlgorithms subMaxSumAlgorithm = null;
        try {
            subMaxSumAlgorithm = (SubMaxSumAlgorithms) oClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int[] worstSubInt = subMaxSumAlgorithm.findSubMaxSum(interval);
        int num=0, ind, ref=0;
        Date[] limits = new Date[2];
        if (worstSubInt.length!=0) {
            for (int j = 0; j < interval.length; j++) {
                if (num!=worstSubInt.length) {
                    if (interval[j] == worstSubInt[0]) {
                        ind = j;
                        for (int value : worstSubInt) {
                            if (value == interval[ind]) {
                                num++;
                            }
                            ind++;
                        }
                        if (num == worstSubInt.length) {
                            ref = j;
                        } else {
                            num = 0;
                        }
                    }
                }
            }
            int startIndex = ref;
            int endIndex = startIndex + worstSubInt.length - 1;
            Date first = days.get(0);
            limits[0]=(Date)first.clone();
            Date last = DateUtils.addMinutes(first, 30);
            limits[1]=(Date)last.clone();
            Date resultFor0 = (Date)first.clone();
            Date resultFor1 = (Date)last.clone();
            int quant = 0;
            if (quant != startIndex) {
                for (Date day : days) {
                    first = day;
                    last = DateUtils.addMinutes(first, 30);
                    do {
                        if (first.getHours() >= 8 && last.getHours() < 20) {
                            first = DateUtils.addMinutes(first, 30);
                            last = DateUtils.addMinutes(last, 30);
                            quant++;
                            if (quant==startIndex){
                                resultFor0=(Date)first.clone();
                            }
                        } else if (last.getHours()==20 && last.getMinutes()==0){
                            quant++;
                            if (quant==startIndex){
                                resultFor0=(Date)first.clone();
                            }
                        }
                    } while (last.getHours()!=20);
                }
                limits[0] = (Date)resultFor0.clone();
            }
            first = days.get(0);
            last = DateUtils.addMinutes(first, 30);
            quant = 0;
            if (quant != endIndex) {
                for (Date day : days) {
                    first = day;
                    last = DateUtils.addMinutes(first, 30);
                    do {
                        if (first.getHours() >= 8 && last.getHours() < 20) {
                            first = DateUtils.addMinutes(first, 30);
                            last = DateUtils.addMinutes(last, 30);
                            quant++;
                            if (quant==endIndex){
                                resultFor1=(Date)last.clone();
                            }
                        } else if (last.getHours()==20 && last.getMinutes()==0){
                            quant++;
                            if (quant==endIndex){
                                resultFor1=(Date)last.clone();
                            }
                        }
                    } while (last.getHours()!=20);
                }
            }
            limits[1] = (Date)resultFor1.clone();
        }else{
            limits[0]=null;
            limits[1]=null;
        }
        return limits;
    }

    /**
     * Gets the chosen algorithm adapter.
     *
     * @param chosenAlgorithm the chosen algorithm
     * @return the chosen algorithm adapter
     */
    public String getChosenAlgorithmAdapter(String chosenAlgorithm) {
        String chosenAlgorithmAdapter;
        if(chosenAlgorithm.equals("Benchmark Algorithm"))
            chosenAlgorithmAdapter = Constants.BENCHMARK_ALGORITHM_ADAPTER;
        else
            chosenAlgorithmAdapter = Constants.BRUTEFORCE_ALGORITHM_ADAPTER;

        return chosenAlgorithmAdapter;
    }

}
