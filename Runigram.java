import java.awt.Color;

/** A library of image processing functions. */
public class Runigram {

	public static void main(String[] args) {
	    
		//// Hide / change / add to the testing code below, as needed.
		
		// Tests the reading and printing of an image:	
		Color[][] tinypic = read("tinypic.ppm");
		print(tinypic);

		// Creates an image which will be the result of various 
		// image processing operations:
		Color[][] image;

		// Tests the horizontal flipping of an image:
		image = flippedHorizontally(tinypic);
		System.out.println();
		print(image);
		
		//// Write here whatever code you need in order to test your work.
		//// You can continue using the image array.
	}

	/** Returns a 2D array of Color values, representing the image data
	 * stored in the given PPM file. */
	public static Color[][] read(String fileName) {
	    In in = new In(fileName);

	    String format = in.readString();
	    if (!format.equals("P3")) {
	        System.out.println("Unsupported PPM format: " + format);
	        return null;
	    }

	    int numCols = in.readInt();
	    int numRows = in.readInt();
	    int maxColorValue = in.readInt();
	    if (maxColorValue != 255) {
	        System.out.println("Unsupported max color value: " + maxColorValue);
	        return null;
	    }

    // Initializes the 2D array to store Color values
    Color[][] image = new Color[numRows][numCols];

    // Reads the RGB values for each pixel and populates the array
    for (int i = 0; i < numRows; i++) {
        for (int j = 0; j < numCols; j++) {
            int r = in.readInt(); // Reads red component
            int g = in.readInt(); // Reads green component
            int b = in.readInt(); // Reads blue component
            image[i][j] = new Color(r, g, b); // Creates Color object and assigns it to the array
        }
    }

    // Returns the populated image array
    return image;
}


    // Prints the RGB values of a given color.
	private static void print(Color c) {
	    System.out.print("(");
		System.out.printf("%3s,", c.getRed());   // Prints the red component
		System.out.printf("%3s,", c.getGreen()); // Prints the green component
        System.out.printf("%3s",  c.getBlue());  // Prints the blue component
        System.out.print(")  ");
	}

	// Prints the pixels of the given image.
	// Each pixel is printed as a triplet of (r,g,b) values.
	// This function is used for debugging purposes.
	// For example, to check that some image processing function works correctly,
	// we can apply the function and then use this function to print the resulting image.
	public static void print(Color[][] image) {
		//// Replace this comment with your code
		//// Notice that all you have to so is print every element (i,j) of the array using the print(Color) function.
		for (int i = 0 ; i < image.length ; i++ ) {
			for (int j = 0 ; j < image[i].length ; j++ ) {
				print(image[i][j]);
			}
			System.out.println();
		}
	}

	
	/**
	 * Returns an image which is the horizontally flipped version of the given image. 
	 */
	public static Color[][] flippedHorizontally(Color[][] image) {
	    int rows = image.length;
	    int cols = image[0].length;
	    Color[][] flippedImage = new Color[rows][cols];

	    for (int i = 0; i < rows; i++) {
	        for (int j = 0; j < cols; j++) {
	            flippedImage[i][cols - j - 1] = image[i][j];
	        }
	    }

	    return flippedImage;
	}

	
	/**
	 * Returns an image which is the vertically flipped version of the given image. 
	 */
	public static Color[][] flippedVertically(Color[][] image) {
	    int rows = image.length;
	    int cols = image[0].length;
	    Color[][] verticallyImage = new Color[rows][cols];

	    for (int i = 0; i < rows; i++) {
	        for (int j = 0; j < cols; j++) {
	            verticallyImage[rows - i - 1][j] = image[i][j];
	        }
	    }

	    return verticallyImage;
	}
		
	// Computes the luminance of the RGB values of the given pixel, using the formula 
	// lum = 0.299 * r + 0.587 * g + 0.114 * b, and returns a Color object consisting
	// the three values r = lum, g = lum, b = lum.
	public static Color luminance(Color pixel) {
	    int r = pixel.getRed();
	    int g = pixel.getGreen();
	    int b = pixel.getBlue();

	    int lum = (int) (0.299 * r + 0.587 * g + 0.114 * b);

	    return new Color(lum, lum, lum);
	}
	
	/**
	 * Returns an image which is the grayscaled version of the given image.
	 */
	public static Color[][] grayScaled(Color[][] image) {
	    int rows = image.length;
	    int cols = image[0].length;
	    Color[][] grayScaled = new Color[rows][cols];

	    for (int i = 0; i < rows; i++) {
	        for (int j = 0; j < cols; j++) {
	            grayScaled[i][j] = luminance(image[i][j]);
	        }
	    }

	    return grayScaled;
	}	
	
	/**
	 * Returns an image which is the scaled version of the given image. 
	 * The image is scaled (resized) to have the given width and height.
	 */
	public static Color[][] scaled(Color[][] image, int width, int height) {
	    int originalHeight = image.length;
	    int originalWidth = image[0].length;
	    Color[][] newImage = new Color[height][width];

	    for (int i = 0; i < height; i++) {
	        for (int j = 0; j < width; j++) {

	            int originalRow = i * originalHeight / height;
	            int originalCol = j * originalWidth / width;

	            newImage[i][j] = image[originalRow][originalCol];
	        }
	    }

	    return newImage;
	}
	
	/**
	 * Computes and returns a blended color which is a linear combination of the two given
	 * colors. Each r, g, b, value v in the returned color is calculated using the formula 
	 * v = alpha * v1 + (1 - alpha) * v2, where v1 and v2 are the corresponding r, g, b
	 * values in the two input color.
	 */
	public static Color blend(Color c1, Color c2, double alpha) {
	    int r1 = c1.getRed();
	    int g1 = c1.getGreen();
	    int b1 = c1.getBlue();

	    int r2 = c2.getRed();
	    int g2 = c2.getGreen();
	    int b2 = c2.getBlue();

	    int r = (int) (alpha * r1 + (1 - alpha) * r2);
	    int g = (int) (alpha * g1 + (1 - alpha) * g2);
	    int b = (int) (alpha * b1 + (1 - alpha) * b2);

	    r = Math.min(255, Math.max(0, r));
	    g = Math.min(255, Math.max(0, g));
	    b = Math.min(255, Math.max(0, b));

	    return new Color(r, g, b);
	}
	
	/**
	 * Cosntructs and returns an image which is the blending of the two given images.
	 * The blended image is the linear combination of (alpha) part of the first image
	 * and (1 - alpha) part the second image.
	 * The two images must have the same dimensions.
	 */
	public static Color[][] blend(Color[][] image1, Color[][] image2, double alpha) {
	    int rows = image1.length;
	    int cols = image1[0].length;

	    if (image2.length != rows || image2[0].length != cols) {
	        throw new IllegalArgumentException("The two images must have the same dimensions.");
	    }

	    Color[][] blendedImage = new Color[rows][cols];

	    for (int i = 0; i < rows; i++) {
	        for (int j = 0; j < cols; j++) {

	            blendedImage[i][j] = blend(image1[i][j], image2[i][j], alpha);
	        }
	    }

	    return blendedImage;
	}

	/**
	 * Morphs the source image into the target image, gradually, in n steps.
	 * Animates the morphing process by displaying the morphed image in each step.
	 * Before starting the process, scales the target image to the dimensions
	 * of the source image.
	 */
	public static void morph(Color[][] source, Color[][] target, int n) {
	    int rows = source.length;
	    int cols = source[0].length;

	    Color[][] scaledTarget = scaled(target, cols, rows);

	    for (int i = 0; i <= n; i++) {

	        double alpha = (double) (n - i) / n;

	        Color[][] morphedImage = blend(source, scaledTarget, alpha);

	        display(morphedImage);

	        StdDraw.pause(500);
	    }
	}
	
	/** Creates a canvas for the given image. */
	public static void setCanvas(Color[][] image) {
		StdDraw.setTitle("Runigram 2023");
		int height = image.length;
		int width = image[0].length;
		StdDraw.setCanvasSize(width, height);
		StdDraw.setXscale(0, width);
		StdDraw.setYscale(0, height);
        // Enables drawing graphics in memory and showing it on the screen only when
		// the StdDraw.show function is called.
		StdDraw.enableDoubleBuffering();
	}

	/** Displays the given image on the current canvas. */
	public static void display(Color[][] image) {
		int height = image.length;
		int width = image[0].length;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Sets the pen color to the pixel color
				StdDraw.setPenColor( image[i][j].getRed(),
					                 image[i][j].getGreen(),
					                 image[i][j].getBlue() );
				// Draws the pixel as a filled square of size 1
				StdDraw.filledSquare(j + 0.5, height - i - 0.5, 0.5);
			}
		}
		StdDraw.show();
	}
}

