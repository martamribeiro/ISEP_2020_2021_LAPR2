package app.ui.gui;

import app.controller.AuthController;
import app.domain.shared.Constants;
import app.ui.console.utils.Utils;
import auth.mappers.dto.UserRoleDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.*;

public class MainUI implements Initializable {

    private App mainApp;

    private AuthController ctrl;

    @FXML
    private ImageView imgTeam;

    @FXML
    private Button sairBtn;

    @FXML
    private TextField email;

    @FXML
    private TextField pwd;

    /**
     * Initializes the UI class.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ctrl = new AuthController();
    }

    public App getMainApp(){
        return this.mainApp;
    }

    public String getEmail(){
        return email.getText();
    }

    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    void exitAction(ActionEvent event) {
        this.mainApp.exitSave();
    }

    @FXML
    private void handleLoginButton(){

        boolean success;
        success = ctrl.doLogin(email.getText(), pwd.getText());
        if (!success)
        {
            AlertUI.createAlert(Alert.AlertType.WARNING, "Many labs", "Wrong credentials", "Wrong email or password. Please try again").show();
        }
        if(success){
            List<UserRoleDTO> roles = this.ctrl.getUserRoles();
            if ( (roles == null) || (roles.isEmpty()) )
            {
                AlertUI.createAlert(Alert.AlertType.WARNING, "Many labs", "No role", "User has not any role assigned.");
            }
            else
            {
                UserRoleDTO role = selectsRole(roles);
                if (!Objects.isNull(role))
                {
                    List<MenuItem> rolesUI = getMenuItemForRoles();
                    this.redirectToRoleUI(rolesUI,role);
                }
                else
                {
                    System.out.println("User did not select a role.");
                }
            }
        }
        this.logout();
    }
    private List<MenuItem> getMenuItemForRoles()
    {
        List<MenuItem> rolesUI = new ArrayList<>();
        rolesUI.add(new MenuItem(Constants.ROLE_ADMIN, new AdminMenuUI()));
        rolesUI.add(new MenuItem(Constants.ROLE_CLIENT, new ClientMenuUI()));
        rolesUI.add(new MenuItem(Constants.ROLE_LAB_COORDINATOR, new LabCoordinatorUI()));
        rolesUI.add(new MenuItem(Constants.ROLE_CLINICAL_CHEM_TECHNOLOGIST, new ChemTechUI()));
        rolesUI.add(new MenuItem(Constants.ROLE_RECEPTIONIST, new ReceptionistMenuUI()));
        rolesUI.add(new MenuItem(Constants.ROLE_MED_LAB_TECHNICIAN, new MedLabTechUI()));
        rolesUI.add(new MenuItem(Constants.ROLE_SPECIALIST_DOCTOR, new SpecDoctorUI()));
        return rolesUI;
    }

    private void redirectToRoleUI(List<MenuItem> rolesUI, UserRoleDTO role)
    {
        boolean found = false;
        Iterator<MenuItem> it = rolesUI.iterator();
        while (it.hasNext() && !found)
        {
            MenuItem item = it.next();
            found = item.hasDescription(role.getDescription());
            if (found)
                try {
                    Menu uiController = (Menu)this.mainApp.replaceSceneContent(item.getUi().getFXML_PATH());
                    uiController.setMainApp(mainApp);
                    if (item.getUi().getFXML_PATH().equals("/fxml/ClientMenu.fxml")){
                        ClientMenuUI clientMenuUI = (ClientMenuUI) uiController;
                        clientMenuUI.setMainUI(this);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
        }
        if (!found)
            System.out.println("There is no UI for users with role '" + role.getDescription() + "'");
    }

    private UserRoleDTO selectsRole(List<UserRoleDTO> roles)
    {
        if (roles.size() == 1)
            return roles.get(0);
        else
            return (UserRoleDTO) Utils.showAndSelectOne(roles, "Select the role you want to adopt in this session:");
    }



    private void logout()
    {
        ctrl.doLogout();
    }

}
