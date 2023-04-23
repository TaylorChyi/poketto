package xyz.taylorchyi.poketto.Image.PNGToICO;

import net.coobird.thumbnailator.Thumbnails;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class PngToIcoConverter {

    public static void main(String[] args) {
        File inputFile = new File("src/test/resources/images/png/input.png");
        File outputFile = new File("src/test/resources/images/ico/output.ico");

        try {
            BufferedImage inputImage = Thumbnails.of(inputFile)
                    .size(480, 480)
                    .asBufferedImage();

            BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            outputImage.getGraphics().drawImage(inputImage, 0, 0, null);

            try (FileImageOutputStream outputStream = new FileImageOutputStream(outputFile)) {
                javax.imageio.ImageIO.write(outputImage, "bmp", outputStream);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
