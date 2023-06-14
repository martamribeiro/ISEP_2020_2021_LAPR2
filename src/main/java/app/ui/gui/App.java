package app.ui.gui;

import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;


import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App extends Application {

    private Stage stage;
    private final String TITLE =  "Many Labs Application";
    private final double MINIMUM_WINDOW_WIDTH = 500.0;
    private final double MINIMUM_WINDOW_HEIGHT = 300.0;
    private final double SCENE_WIDTH = 693;
    private final double SCENE_HEIGHT = 495;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setTitle(TITLE);
        //stage.setMinWidth(MINIMUM_WINDOW_WIDTH); Prof
        //stage.setMinHeight(MINIMUM_WINDOW_HEIGHT); Prof
        stage.setMinHeight(495);
        stage.setMinWidth(693);

        toMainScene();
        this.stage.show();
    }


    public Stage getStage() {
        return this.stage;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void toMainScene() {
        try {
            MainUI mainUI = (MainUI) replaceSceneContent("/fxml/Login.fxml");
            mainUI.setMainApp(this);
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = App.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(App.class.getResource(fxml));
        Pane page;
        try {
            page = (Pane) loader.load(in);
        } finally {
            in.close();
        }
        Scene scene = new Scene(page, SCENE_WIDTH, SCENE_HEIGHT);
        scene.getStylesheets().add("/styles/Styles.css");
        this.stage.setScene(scene);
        this.stage.sizeToScene();
        if(fxml.equals("/fxml/Login.fxml") && !stage.getStyle().equals(StageStyle.UNDECORATED))
            stage.initStyle(StageStyle.UNDECORATED);
        return (Initializable) loader.getController();
    }

    public void exitSave(){
        app.controller.App.getInstance().storeUserSection();
        System.exit(0);
    }


    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
