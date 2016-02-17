/*
 * www.javagl.de - Rendering
 * 
 * Copyright 2010-2016 Marco Hutter - http://www.javagl.de
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package de.javagl.rendering.core;


/**
 * Implementation of a {@link Texture} that is backed by {@link ImageData}.<br>
 * <br>
 * TODO This interface may be extended with a listener infrastructure
 */
class DefaultImageTexture implements ImageTexture
{
    /**
     * The {@link ImageData} of this texture.
     */
    private final ImageData imageData;
    
//    /**
//     * The listeners for update requests on this texture
//     */
//    private List<ImageTextureListener> 
//        imageTextureListeners = 
//            new CopyOnWriteArrayList<ImageTextureListener>();
    
//    /**
//     * Creates a new Texture. The texture will contain a <b>new</b> 
//     * image that has the same contents as the given one. 
//     * 
//     * @param textureImage The input image
//     */
//    private DefaultImageTexture(BufferedImage textureImage)
//    {
//        int w = textureImage.getWidth();
//        int h = textureImage.getHeight();
//        
//        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//        
//        Graphics2D g = image.createGraphics();
//        g.drawImage(textureImage, 0, 0, null);
//        g.dispose();
//    }

    /**
     * Creates a new image texture with the given size
     * 
     * @param width The width of the texture
     * @param height The height of the texture
     */
    DefaultImageTexture(int width, int height)
    {
        imageData = new DefaultImageData(width, height);
    }
    

    @Override
    public int getWidth()
    {
        return imageData.getWidth();
    }

    @Override
    public int getHeight()
    {
        return imageData.getHeight();
    }

    @Override
    public ImageData getImageData()
    {
        return imageData;
    }
    
//    @Override
//    public void requestUpdate(int x, int y, int width, int height)
//    {
//        for (ImageTextureListener u : 
//             imageTextureListeners)
//        {
//            u.updateRequested(this, x, y, width, height);
//        }
//    }
//    
//    @Override
//    public void addImageTextureListener(
//        ImageTextureListener imageTextureListener)
//    {
//        imageTextureListeners.add(
//            imageTextureListener);
//    }
//
//    @Override
//    public void removeImageTextureListener(
//        ImageTextureListener imageTextureListener)
//    {
//        imageTextureListeners.remove(
//            imageTextureListener);
//    }
    
    @Override
    public String toString()
    {
        return "DefaultTexture["+
            "imageData="+imageData+"]";
    }


    @Override
    public int hashCode()
    {
        return imageData.hashCode();
    }


    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (object == null)
        {
            return false;
        }
        if (getClass() != object.getClass())
        {
            return false;
        }
        DefaultImageTexture other = (DefaultImageTexture) object;
        if (imageData == null)
        {
            if (other.imageData != null)
            {
                return false;
            }
        } 
        else if (!imageData.equals(other.imageData))
        {
            return false;
        }
        return true;
    }

   
}
