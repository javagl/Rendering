package de.javagl.rendering.desktop;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import de.javagl.rendering.core.ImageData;
import de.javagl.rendering.core.ImageTexture;
import de.javagl.rendering.core.Textures;

/**
 * Utility methods for creating {@link ImageTexture} instances. 
 * These instances may be created from files and BufferedImages.
 */
public class ImageTextures
{
    /**
     * The logger used in this class
     */
    private static final Logger logger = 
        Logger.getLogger(ImageTextures.class.getName());

    /**
     * Creates a new {@link ImageTexture} from the specified file.
     * If an IO error occurs, an error message will be printed,
     * and a dummy texture containing the error message will be
     * returned.
     * 
     * @param fileName The name of the input file
     * @return The {@link ImageTexture}
     */
    public static ImageTexture createImageTextureFromFileUnchecked(
        String fileName)
    {
        try
        {
            return createImageTexture(fileName, false);
        } 
        catch (IOException e)
        {
            logger.severe(e.getMessage());
            return createErrorTexture(e.getMessage());
        }
    }

    /**
     * Creates a new {@link ImageTexture} from the specified file.
     * If an IO error occurs, an error message will be printed,
     * and a dummy texture containing the error message will be
     * returned.
     * 
     * @param fileName The name of the input file
     * @param flipVertically Whether the image should be flipped vertically
     * @return The {@link ImageTexture}
     */
    public static ImageTexture createImageTextureFromFileUnchecked(
        String fileName, boolean flipVertically)
    {
        try
        {
            return createImageTexture(fileName, flipVertically);
        } 
        catch (IOException e)
        {
            logger.severe(e.getMessage());
            return createErrorTexture(e.getMessage());
        }
    }
    
    /**
     * Creates a new {@link ImageTexture} from the specified file.
     * 
     * @param fileName The name of the input file
     * @return The {@link ImageTexture}
     * @throws IOException If an IO error occurs
     */
    public static ImageTexture createImageTextureFromFile(String fileName)
        throws IOException
    {
        return createImageTexture(fileName, false);
    }
    
    /**
     * Creates a new {@link ImageTexture} from the specified file.
     * 
     * @param fileName The name of the input file
     * @param flipVertically Whether the image should be flipped vertically
     * @return The {@link ImageTexture}
     * @throws IOException If an IO error occurs
     */
    public static ImageTexture createImageTexture(
        String fileName, boolean flipVertically) throws IOException 
    {
        InputStream inputStream = null;
        try
        {
            inputStream = new FileInputStream(fileName);
            ImageTexture result = 
                createImageTexture(inputStream, flipVertically);
            inputStream.close();
            return result;
        }
        finally 
        {
            if (inputStream != null)
            {
                inputStream.close();
            }
        }
    }

    /**
     * Creates a new {@link ImageTexture} from the given stream.
     * The caller is responsible for closing the stream.
     * 
     * @param inputStream The input stream
     * @return The {@link ImageTexture}
     * @throws IOException If an IO error occurs
     */
    public static ImageTexture createImageTexture(InputStream inputStream)
        throws IOException
    {
        return createImageTexture(inputStream, false);
    }        

    /**
     * Creates a new {@link ImageTexture} from the given stream.
     * The caller is responsible for closing the stream.
     * 
     * @param inputStream The input stream
     * @param flipVertically Whether the image should be flipped vertically
     * @return The {@link ImageTexture}
     * @throws IOException If an IO error occurs
     */
    public static ImageTexture createImageTexture(
        InputStream inputStream, boolean flipVertically) throws IOException
    {
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        ImageTexture texture = create(bufferedImage, flipVertically);
        return texture;
    }
    
    
    /**
     * Creates and returns a new {@link ImageTexture} from the 
     * given BufferedImage. 
     * 
     * @param textureImage The input image
     * @return The {@link ImageTexture}
     */
    public static ImageTexture create(BufferedImage textureImage)
    {
        return create(textureImage, false);
    }
    
    /**
     * Creates and returns a new {@link ImageTexture} from the 
     * given BufferedImage. 
     * 
     * @param textureImage The input image
     * @param flipVertically Whether the image should be flipped vertically
     * @return The {@link ImageTexture}
     */
    public static ImageTexture create(
        BufferedImage textureImage, boolean flipVertically)
    {
        int w = textureImage.getWidth();
        int h = textureImage.getHeight();
        BufferedImage image = 
            new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        if (flipVertically)
        {
            g.drawImage(textureImage, 0, h, w, -h, null);
        }
        else
        {
            g.drawImage(textureImage, 0, 0, null);
        }
        g.dispose();
        ImageTexture result = Textures.createImageTexture(w, h);
        write(image, result.getImageData());
        return result;
    }

    
    /**
     * Write the contents of the given image into the given {@link ImageData}
     * object. The given image will become unmanaged by this call.
     * 
     * @param source The source image
     * @param target The target {@link ImageData}
     * @throws IllegalArgumentException If the given image is not
     * backed by a DataBufferInt
     */
    static void write(BufferedImage source, ImageData target)
    {
        IntBuffer sBuffer = getBuffer(source);
        ByteBuffer bBuffer = target.getData();
        bBuffer.order(ByteOrder.nativeOrder());
        IntBuffer tBuffer = bBuffer.asIntBuffer();
        for (int y=0; y<target.getHeight(); y++)
        {
            tBuffer.position(y * target.getWidth());
            sBuffer.limit(y * source.getWidth() + target.getWidth());
            sBuffer.position(y * source.getWidth());
            tBuffer.put(sBuffer);
        }
    }
    
    /**
     * Returns the data buffer of the given image as an IntBuffer.
     * 
     * @param image The image
     * @return The data from the image as an IntBuffer
     * @throws IllegalArgumentException If the given image is not
     * backed by a DataBufferInt
     */
    static IntBuffer getBuffer(BufferedImage image)
    {
        DataBuffer dataBuffer = image.getRaster().getDataBuffer();
        if (!(dataBuffer instanceof DataBufferInt))
        {
            throw new IllegalArgumentException(
                "Invalid buffer type in image, "+
                "only TYPE_INT_* is allowed");
        }
        DataBufferInt dataBufferInt = (DataBufferInt)dataBuffer;
        int data[] = dataBufferInt.getData();
        IntBuffer intBuffer = IntBuffer.wrap(data);
        return intBuffer;
    }
    
    /**
     * Creates a new {@link ImageTexture} containing the given error message.
     * 
     * @param message The error message
     * @return The {@link ImageTexture}
     */
    private static ImageTexture createErrorTexture(String message)
    {
        BufferedImage tempImage = 
            new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
        Graphics2D tempGraphics = tempImage.createGraphics();
        FontMetrics fontMetrics = 
            tempGraphics.getFontMetrics(tempGraphics.getFont());
        int stringWidth = fontMetrics.stringWidth(message);
        int stringHeight = fontMetrics.getHeight();
        tempGraphics.dispose();
        
        int size = stringWidth + 10;
        BufferedImage bufferedImage = 
            new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        int delta = (int)Math.ceil(size / 8.0);
        for (int y=0; y<8; y++)
        {
            for (int x=0; x<8; x++)
            {
                if (((x+y) & 1) == 0)
                {
                    graphics.setColor(Color.LIGHT_GRAY);
                }
                else
                {
                    graphics.setColor(Color.WHITE);
                }
                graphics.fillRect(x*delta, y*delta, delta, delta);
            }
        }
        graphics.setColor(Color.RED);
        graphics.drawLine(0,0,size,size);
        graphics.drawLine(size,0,0,size);
        graphics.setColor(Color.BLACK);
        graphics.drawString(message, 5, stringHeight + 5);
        graphics.dispose();
        ImageTexture texture = create(bufferedImage);
        return texture;
    }
    
    /**
     * Private constructor to prevent instantiation
     */
    private ImageTextures()
    {
        // Private constructor to prevent instantiation
    }
}
