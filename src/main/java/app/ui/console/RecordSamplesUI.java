package app.ui.console;

import app.controller.RecordSamplesController;
import app.mappers.dto.TestDTO;
import app.ui.console.utils.Utils;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.output.OutputException;

import java.io.IOException;
import java.util.List;

public class RecordSamplesUI implements Runnable {
    private RecordSamplesController ctrl;
    private String laboratoryID;

    public RecordSamplesUI(String laboratoryID) {
        ctrl = new RecordSamplesController();
        this.laboratoryID = laboratoryID;
    }

    @Override
    public void run() {
        boolean success;
        do {
            success = recordSamples();
        } while (!success);
    }

    private boolean recordSamples() {
        boolean success = false;

        try {
            List<TestDTO> listTestsNoSamplesDto = ctrl.getTestsNoSamples(laboratoryID);
            TestDTO selectedTest = (TestDTO) Utils.showAndSelectOne(listTestsNoSamplesDto,
                    "To record the Samples collected of a Test, please selected a Test from the list:\n");

            if(selectedTest != null) {
                System.out.println("How many Samples are to be collected?");
                int numSamples = Utils.readIntegerFromConsole("Number of Samples: ");

                boolean confirm = Utils.confirm(String.format("%d Samples will be added to the selected Test. (type `s` if its correct, `n` if it is not)", numSamples, selectedTest.getCode()));

                if(confirm) {
                    for (int i = 0; i < numSamples; i++) {
                        ctrl.createSample(); //ClassNotFoundException, InstantiationException, IllegalAccessException, Barcode Exception
                        boolean addSampleToTest = ctrl.addSample(selectedTest.getCode());
                        if(addSampleToTest)
                            ctrl.saveImageBarcode(selectedTest.getCode()); //IOException, Output Exception (Barbecue)
                    }
                    ctrl.addSampleCollectionDateToTest();
                    success = true;
                    System.out.println("\nSamples successfully recorded and added to the Test!\n" +
                            "You can find the Samples Barcodes in the subfolder \"Test-Code" + selectedTest.getCode() + "\" in the folder \"Samples Barcodes\".");
                } else {
                    System.out.println("\nOperation canceled!");
                    success = true;
                }

            } else {
                success = true;
            }
        }
        catch(ClassNotFoundException cnde) {
            System.out.println("The API Adapter Class was not found!\n");
        }
        catch(InstantiationException ie) {
            System.out.println("The specified class of the External API cannot be instantiated!\n");
        }
        catch(BarcodeException be) {
            System.out.println("The barcode number should be 12 digits and the length of the generated number should be 11!\n");
        }
        catch(IllegalAccessException iae) {
            System.out.println("The method invoked to be executed from the Class of the API Adapter defined doesn't have access to that Class!\n");
        }
        catch (OutputException oe) {
            System.out.println(oe.getMessage());
        }
        catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        catch(IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
        }
        catch(UnsupportedOperationException uoe) {
            System.out.println(uoe.getMessage());
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return success;

    }

}


