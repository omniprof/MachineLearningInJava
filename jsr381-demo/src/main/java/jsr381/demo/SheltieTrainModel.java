package jsr381.demo;

import javax.visrec.ml.classification.NeuralNetImageClassifier;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;
import javax.visrec.ml.model.ModelCreationException;

/**
 * Modified code to train for Shelties
 * The training data was too large to include so you cannot run this program.
 * A trained model, sheltie_model.dnet in the datasets folder can be used
 *
 * @author Zoran Sevarac <zoran.sevarac@deepnetts.com>
 */
public class SheltieTrainModel {

    //
    // Cannot run as Sheltie training data is not included. 
    // Use StarWars if you wish to demo training
    //
    
    public static void main(String[] args) throws IOException, ModelCreationException {
        
        // Configure and train ML model to classify images
        NeuralNetImageClassifier.builder()
                .inputClass(BufferedImage.class)
                .imageHeight(128)
                .imageWidth(128)
                .labelsFile(Paths.get("C:/dev/Dogs/labels.txt")) // category labels
                .trainingFile(Paths.get("C:/dev/Dogs/training_4.txt")) // list of images
                .networkArchitecture(Paths.get("C:/dev/Dogs/sheltienotsheltie_arch.json"))
                .exportModel(Paths.get("C:/dev/Dogs/sheltie.dnet"))
                .maxError(0.05f)
                .maxEpochs(500)
                .learningRate(0.01f)
                .build();
    }
}
