package jsr381.demo;

import javax.visrec.ml.classification.NeuralNetImageClassifier;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;
import java.util.List;
import javax.visrec.ml.model.ModelCreationException;

/**
 * This standalone program uses the model created by TrainModel and uses 6
 * images of spaceships to be classified.
 *
 */
public class UseStarWarsModel {

    /**
     * Instantiate a classifier from the training model and test images
     *
     * @param args
     * @throws javax.visrec.ml.model.ModelCreationException
     */
    public static void main(String[] args) throws ModelCreationException {
        var classifier = NeuralNetImageClassifier.builder()
                .inputClass(BufferedImage.class)
                .imageHeight(50)
                .imageWidth(50)
                .importModel(Paths.get("datasets/starwars/trained_model.dnet"))
                .build();

        /*
         * Create a list of the test images and then invoke the classifier on
         * each one
         */

        // Tie Fighter
        var tieFighters = List.of(
                Paths.get("test_images/tie_fighter/1.jpg"),
                Paths.get("test_images/tie_fighter/2.jpg"),
                Paths.get("test_images/tie_fighter/3.jpg")
        );
        tieFighters.stream()
                .map(classifier::classify)
                .forEach(System.out::println);

        // Millennium Falcon
        var falcons = List.of(
                Paths.get("test_images/millennium_falcon/1.jpg"),
                Paths.get("test_images/millennium_falcon/2.jpg"),
                Paths.get("test_images/millennium_falcon/3.jpg")
        );
        falcons.stream()
                .map(classifier::classify)
                .forEach(System.out::println);
    }
}
