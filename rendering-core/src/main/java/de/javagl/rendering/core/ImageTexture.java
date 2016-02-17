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
 * Interface for a {@link Texture} that is backed by {@link ImageData}
 */
public interface ImageTexture extends Texture
{
    /**
     * Returns the {@link ImageData} backing this image texture    
     * 
     * @return The {@link ImageData}
     */
    ImageData getImageData();

//    /**
//     * Will inform all registered {@link ImageTextureListener
//     * ImageTextureListeners} that the specified region of
//     * this texture has to be updated.
//     * 
//     * @param x The x position of the region 
//     * @param y The y position of the region
//     * @param width The width of the region
//     * @param height The height of the region
//     */
//    void requestUpdate(int x, int y, int width, int height);
//    
//    /**
//     * Add the given listener to this texture
//     * 
//     * @param imageTextureListener The listener to add
//     */
//    void addImageTextureListener(
//        ImageTextureListener imageTextureListener);
//    
//    /**
//     * Remove the given listener from this texture
//     * 
//     * @param imageTextureListener The listener to remove
//     */
//    void removeImageTextureListener(
//        ImageTextureListener imageTextureListener);
//    
}