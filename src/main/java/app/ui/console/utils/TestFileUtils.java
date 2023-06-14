package app.ui.console.utils;

import app.mappers.dto.ClientDTO;
import app.mappers.dto.TestFileDTO;

import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestFileUtils {

    private List<String> dataLabels;
    
    public TestFileUtils (){
        dataLabels = new ArrayList<>();
    }
    public List<TestFileDTO> getTestsDataToDto(String filePath){
        File csvFile = new File(filePath);
        List<TestFileDTO> processedListData = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFile))) {
            String line = bufferedReader.readLine();
            dataLabels = Arrays.asList(line.split(";"));
            line = bufferedReader.readLine();
            while(line != null){
                String [] attributes = line.split(";");
                processedListData.add(attributesToDto(attributes));
                line = bufferedReader.readLine();
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return processedListData;
    }

    private  TestFileDTO attributesToDto(String[] testData) throws ParseException {
        return new TestFileDTO(clientToDto(testData), testData[dataLabels.indexOf("NHS_Code")],
                testData[dataLabels.indexOf("TestType")], getParameterCodes(testData), getParameterResults(testData),
                getDateOfString(testData[dataLabels.indexOf("Test_Reg_DateHour")]), getDateOfString(testData[dataLabels.indexOf("Test_Chemical_DateHour")]),
                getDateOfString(testData[dataLabels.indexOf("Test_Doctor_DateHour")]),getDateOfString(testData[dataLabels.indexOf("Test_Validation_DateHour")]), testData[dataLabels.indexOf("Lab_ID")]);
    }

    private  Date getDateOfString(String stringFormatDate){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            return sdf.parse(stringFormatDate);
        } catch(ParseException p){
            return null;
        }
    }


    private  ClientDTO clientToDto(String[] data) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String citizenCardNum = String.format("%016d", Integer.parseInt(data[dataLabels.indexOf("CitizenCard_Number")]));
        String nhsNum = data[dataLabels.indexOf("NHS_Number")];
        String tin = data[dataLabels.indexOf("TIN")];
        Date date = df.parse(data[dataLabels.indexOf("BirthDay")]);
        String phoneNum = data[dataLabels.indexOf("PhoneNumber")];
        String name = data[dataLabels.indexOf("Name")];
        String email = data[dataLabels.indexOf("E-mail ")];
        return new ClientDTO(citizenCardNum, nhsNum, date, tin, email,name, phoneNum);
    }

    public  List<String> getParameterCodes(String [] arrayData){
        List<String> parameterCodes = new ArrayList<>();
        int indexOfTestType = dataLabels.indexOf("TestType") + 1;
        int indexOfFirstDate = dataLabels.indexOf("Test_Reg_DateHour");
        for (int i=indexOfTestType; i < indexOfFirstDate; i++){
            if(!arrayData[i].equals("NA") && !dataLabels.get(i).equals("Category")){
                parameterCodes.add(dataLabels.get(i));
            }
        }
        return parameterCodes;
    }

    public  List<Double> getParameterResults(String [] arrayData) throws ParseException {
        List<String> parameterCodes = getParameterCodes(arrayData);
        NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
        List<Double> parameterResults = new ArrayList<>();
        int indexOfCode;
        for(String code : parameterCodes){
            indexOfCode = dataLabels.indexOf(code);
            parameterResults.add(format.parse(arrayData[indexOfCode]).doubleValue());
        }
        return parameterResults;
    }

}
