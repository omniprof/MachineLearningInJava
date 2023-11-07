package jsr381.demo.fx;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Screen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Create the JavaFX user interface that consists of an FXML file and a Java
 * controller class.
 *
 */
public class ClassifierApplication extends Application {

    // Declare a private or protected Logger object initialized with the 
    // class's name
    private static final Logger LOG = LogManager.getLogger(ClassifierApplication.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Set the locale manualy to test i18n - use new Locale("fr","CA") for Canadian French
        ResourceBundle resources = ResourceBundle.getBundle("MessagesBundle", new Locale("en", "CA"));

        double height = Screen.getPrimary().getBounds().getHeight() * 0.8;
        double width = Screen.getPrimary().getBounds().getWidth() * 0.8;

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/classifier.fxml"), resources);
        primaryStage.setTitle(resources.getString("title"));
        primaryStage.setScene(new Scene(root, width, height));
        Platform.setImplicitExit(true);
        
        
        // Have Platform.exit called if the window is closed without the exit button
        primaryStage.setOnCloseRequest(e -> {
            LOG.debug("setOnCloseRequest");
            Platform.exit();
        });
        primaryStage.show();
    }
    
    /**
     * Where it all begins
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
