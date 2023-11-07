package jsr381.demo;

import javax.visrec.ml.classification.NeuralNetImageClassifier;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.visrec.ml.model.ModelCreationException;

/**
 * This standalone program uses the model created by TrainModel and uses 6
 * images of spaceships to be classified.
 *
 */
public class UseSheltieModel {

    /**
     * Instantiate a classifier from the training model and test images
     *
     * @param args
     * @throws javax.visrec.ml.model.ModelCreationException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws ModelCreationException, IOException {
        var classifier = NeuralNetImageClassifier.builder()
                .inputClass(BufferedImage.class)
                .imageHeight(50)
                .imageWidth(50)
                .importModel(Paths.get("datasets/sheltie_model.dnet"))
                .build();

        // recognize image with a train model
        BufferedImage image = ImageIO.read(new File("datasets/sheltie_02.jpg"));
//        BufferedImage image = ImageIO.read(new File("datasets/sheltie_05.jpg"));
        Map<String, Float> results = classifier.classify(image);
        System.out.println(results);
        
    }
    
    
}
