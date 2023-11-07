package jsr381.demo.fx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.visrec.ml.model.ModelCreationException;
import javax.visrec.ml.classification.ImageClassifier;
import javax.visrec.ml.classification.NeuralNetImageClassifier;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.stage.Screen;
import jsr381.demo.results.Result;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Every JavaFX fxml file needs a controller behind it. This controller responds
 * to choices made by the user.
 *
 */
public class ClassifierController {

    // Each of these are controls or containers in the fxml file
    @FXML
    private ImageView image;

    @FXML
    private TextArea consoleText;

    @FXML
    private Button imageBtn;

    @FXML
    private Button classifyBtn;

    @FXML
    private ResourceBundle resources;

    // Instance variables
    private ImageClassifier<BufferedImage> classifier;
    private File imageFile;

    // Declare a private or protected Logger object initialized with the 
    // class's name
    private static final Logger LOG = LogManager.getLogger(ClassifierController.class);

    /**
     * This method is called after each of the @FXML variables from the fxml
     * file are injected.
     */
    @FXML
    private void initialize() {
        double height = Screen.getPrimary().getBounds().getHeight() * 0.5;
        double width = Screen.getPrimary().getBounds().getWidth() * 0.9;

        // Until a model is loaded disable the Image & Classify buttons
        imageBtn.setDisable(true);
        classifyBtn.setDisable(true);
        
        image.setFitHeight(height);
        image.setFitWidth(width);
    }

    /**
     * Event handler for menu item Open Model. Opens the trained Model file and
     * instantiate a classifier from it
     *
     * @param event All event handlers are required to receive an object of type
     * ActionEvent. It is not necessary to use this object.
     */
    @FXML
    private void openModel(ActionEvent event) {
        // Open FileChooser dialog to load model
        var fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("datasets"));
        fileChooser.setTitle(resources.getString("openM"));
        File file = fileChooser.showOpenDialog(null);
        try {
            classifier = NeuralNetImageClassifier.builder()
                    .inputClass(BufferedImage.class)
                    .importModel(file.toPath())
                    .build();
            imageBtn.setDisable(false);
            LOG.info("Loaded model: " + file.toPath());
            consoleText.appendText(resources.getString("loadM") + " " + file.toPath() + "\n");

        } catch (ModelCreationException e) {
            LOG.error(e);
            consoleText.appendText("\nModelCreationException\n" + e.getMessage() + "\n");
            imageBtn.setDisable(true);
        } finally {
            imageFile = null;
            image.setImage(null);
            classifyBtn.setDisable(true);
        }
        consoleText.setScrollTop(Double.MAX_VALUE);
    }

    /**
     * Using the FileChooser control, open an image file to test against the
     * model.
     *
     * @param event All event handlers are required to receive an object of type
     * ActionEvent. It is not necessary to use this object.
     */
    @FXML
    private void openImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("datasets"));
        fileChooser.setTitle(resources.getString("openI"));
        imageFile = fileChooser.showOpenDialog(null);
        image.setImage(new Image(imageFile.toURI().toString()));
        centerImage();
        classifyBtn.setDisable(false);
        LOG.info("Loaded image: " + imageFile.toPath());
        consoleText.appendText(resources.getString("loadI") + " " + imageFile.toPath() + "\n");
    }

    /**
     * Using the classifier created when the model was loaded, use it to
     * determine if images are one of the two spaceships. The results are
     * written to a Results record.
     *
     * @param event All event handlers are required to receive an object of type
     * ActionEvent. It is not necessary to use this object.
     */
    @FXML
    private void classifyImage(ActionEvent event) {

        var results = classifier.classify(Paths.get(imageFile.getPath()));

        var sortedResults = results.entrySet().stream()
                .map(e -> new Result(e.getKey(), e.getValue()))
                .sorted((v1, v2) -> v1.accuracy() > v2.accuracy() ? 1 : -1)
                .collect(Collectors.toList());

        var contentTextBuilder = new StringBuilder();
        sortedResults.forEach(e
                -> contentTextBuilder.append(e.name())
                        .append(" : ")
                        .append(String.format("%.2f", e.accuracy() * 100))
                        .append("%")
                        .append("\n")
        );
        LOG.info(contentTextBuilder);
        consoleText.appendText("\n" + contentTextBuilder + "\n");
        consoleText.setScrollTop(Double.MAX_VALUE);
    }

    /**
     * The official way to exit a JavaFX application, use Platform.exit();
     *
     * @param event
     */
    @FXML
    private void doExit(ActionEvent event) {
       LOG.debug("doExit");         
       Platform.exit();
    }

    /**
     * This will center the image in the panel
     */
    private void centerImage() {
        Image img = image.getImage();
        if (img != null) {
            double ratioX = image.getFitWidth() / img.getWidth();
            double ratioY = image.getFitHeight() / img.getHeight();

            double reducCoeff;
            if (ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            double w = img.getWidth() * reducCoeff;
            double h = img.getHeight() * reducCoeff;

            image.setX((image.getFitWidth() - w) / 2);
            image.setY((image.getFitHeight() - h) / 2);
        }
    }
}
