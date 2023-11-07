#Use the Force
This example trains and uses a simple machine learning model using JSR-381. It uses a training file that contains images of the Millenium Falcon and X-Wing fighters. Using the training file, a classifier object can instantiated to determine if an image file is an X-Wing or Millenium Falcon. The images you submit must be of either spaceship and not found in the training set. The result will be the percentage certaintity that an image is one or the other.

#Use the Sheltie
There is a second trained model for classifying Shetland Sheepdogs. Unlike StarWars, this model does not work as well. See the last paragraph below for an explanation.


# Required Software
Java 17 or greater
Maven 3.8.6 or greater to work from the command line
IDE or TextEditor

#Setup
As a Maven based project you can load this project into any IDE that supports Maven projects. You can also compile and run the code from the command line using Maven.

#pom file
In the <properties> section you can select which of ther applications you want to run.
<exec.mainClass>jsr381.demo.fx.ClassifierApplication</exec.mainClass>
<!-- <exec.mainClass>jsr381.demo.StarWarsTrainModel</exec.mainClass>-->
<!-- <exec.mainClass>jsr381.demo.UseStarWarsModel</exec.mainClass>-->
<!-- <exec.mainClass>jsr381.demo.SheltieTrainModel</exec.mainClass>-->
<!-- <exec.mainClass>jsr381.demo.UseSheltieModel</exec.mainClass>-->
The remaining steps show the Maven command line, if you prefer. The simplest way to demonstrate this project is to simply open a console/terminal in the root folder of the project and just enter mvn. This assumes that maven can run from a prompt. Whichever mainClass from above that is not commented out will run.

#Use the program
Step 1: Let Maven download all required libraries and compile all Java files.
Command: mvn verify

Step 2: The project includes the training models shelties_model.dnet and starwars_model.dnet therefore you do not need to use the SheltieTrainModel or StarWarsTrainModel programs. If you wish to, then the training data for StarWars is in the datasets folder. The training data for shelties was too large so they are not included. You can run either of the Use programs.

If you do want to create the training file for StarWars the location of the files is hardcoded into the UseStarWarsModel class. 

Command:
If you do not want to edit the pom file you can use:
mvn verify exec:java -Dexec.mainClass="jsr381.demo.StarWarsTrainModel"
Otherwise, uncomment the mainClass from the properties section of the pom file and comment out what you are not using. Open a console/terminal in the root folder of the project and enter mvn.

Step 3: Test the training file.
mvn verify exec:java -Dexec.mainClass="jsr381.demo.UseStarWarsModel"

Step 4: Use the JavaFX program to load a model, load an image to test, and then classify which of the spaceships or shelties it is.

mvn verify exec:java -Dexec.mainClass="jsr381.demo.fx.ClassifierApplication"
Otherwise, uncomment the mainClass from the properties section of the pom file and comment out what you are not using. Open a console/terminal in the root folder of the project and enter mvn.

The StarWars example works quite well. The Shelties example does not. Why? Most of the sample images of the Millenium Falcon and X-Wing have plain backgrounds. All the test images in the datasets folder have a black background. The Sheltie images generally have complex backgrounds having been taken outdoors or inside with a lot of background. I call this background noise. The Sheltie test image sheltie_05.jpeg is not classified as a Sheltie. Use sheltie_02.jpg where the background was removed and the code recognizes that this is a Sheltie. The point to make is that the quality of the training data is critical. 


