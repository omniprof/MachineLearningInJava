package jsr381.demo;

import javax.visrec.ml.classification.NeuralNetImageClassifier;
import java.awt.image.BufferedImage;
import java.nio.file.Paths;
import javax.visrec.ml.model.ModelCreationException;

/**
 * This is the standalone training program for StarWars
 *
 */
public class StarWarsTrainModel {

	/**
	 * Where it all begins
	 *
	 * @param args
	 * @throws javax.visrec.ml.model.ModelCreationException
	 */
	public static void main(String[] args) throws ModelCreationException {
		// The classifier to use
		NeuralNetImageClassifier.builder()

				// What type of class is being classified
				.inputClass(BufferedImage.class)

				// Image will be scaled to this height
				.imageHeight(50)

				// Image will be scaled to this width
				.imageWidth(50)

				// The category name of the data to be classified
				.labelsFile(Paths.get("datasets/starwars/labels.txt"))

				// The path to the files to be used in training along with matching label
				.trainingFile(Paths.get("datasets/starwars/train.txt"))

				// The ML engine configuration file
				.networkArchitecture(Paths.get("datasets/starwars/arch.json"))

				// The name of the file to output the trained data to.
				.exportModel(Paths.get("datasets/starwars/trained_model.dnet"))

				// Stops training once it reaches the error rate of the training
				.maxError(0.03f)

				// Maximum iterations before ending the training propcess
				.maxEpochs(25)

				// Speed at which you wish to train the model, smaller is faster
				.learningRate(0.01f).build();
	}
}
