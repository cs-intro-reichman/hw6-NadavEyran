import java.awt.Color;

/**
 * Demonstrates the morphing operation featured by Runigram.java. 
 * The program recieves three command-line arguments: a string representing the name
 * of the PPM file of a source image, a string representing the name of the PPM file
 * of a target image, and the number of morphing steps (an int). 
 * For example, to morph the cake into ironman in 50 steps, use:
 * java Editor3 cake.ppm ironman.ppm 50
 * Note: There is no need to scale the target image to the size of the source
 * image, since Runigram.morph performs this action.
 */
public class Editor3 {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java Editor3 <sourceFile> <targetFile> <steps>");
            return;
        }

        String sourceFile = args[0];
        String targetFile = args[1];
        int steps;

        try {
            steps = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.out.println("The number of steps must be an integer.");
            return;
        }

        Color[][] sourceImage = Runigram.read(sourceFile);
        if (sourceImage == null) {
            System.out.println("Failed to load source image: " + sourceFile);
            return;
        }

        Color[][] targetImage = Runigram.read(targetFile);
        if (targetImage == null) {
            System.out.println("Failed to load target image: " + targetFile);
            return;
        }

        System.out.println("Morphing from " + sourceFile + " to " + targetFile + " in " + steps + " steps...");
        Runigram.setCanvas(sourceImage);
        Runigram.morph(sourceImage, targetImage, steps);
    }
}
