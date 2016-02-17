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

package de.javagl.rendering.core.handling;

import de.javagl.rendering.core.Texture;

/**
 * Abstract base implementation of a {@link TextureHandler}.
 * 
 * @param <U> The type of the internal representation of the
 * objects handled by this class  
 */
public abstract class AbstractTextureHandler<U> 
    extends AbstractReferenceHandler<Texture, U> 
    implements TextureHandler<U>//, ImageTextureListener
    
{
    /**
     * The manager for dirty rectangles of textures
     */
    //private DirtyRectangleManager<Texture> dirtyRectangleManager;
    
    /**
     * Creates a new AbstractTextureHandler
     */
    protected AbstractTextureHandler()
    {
        //dirtyRectangleManager = new DirtyRectangleManager<Texture>();
    }
    
    @Override
    protected final void handleChildren(Texture t)
    {
        // No children
    }

    @Override
    protected final void releaseChildren(Texture t)
    {
        // No children
    }

//    @Override
//    public void updateRequested(ImageTexture texture, int x, int y, int width, int height)
//    {
//        dirtyRectangleManager.addDirtyRectangle(texture, x, y, width, height);
//    }
//    
//    /**
//     * Validate the given texture. If an update has been requested
//     * for the given texture, then this method will call 
//     * {@link #updateImageTexture(ImageTexture, int, int, int, int)}
//     * with the respective region. 
//     *  
//     * @param texture The texture to validate
//     */
//    protected void validate(ImageTexture texture)
//    {
//        Rectangle region = dirtyRectangleManager.getDirtyRectangle(texture);
//        if (region != null)
//        {
//            updateImageTexture(
//                texture, region.x, region.y, region.width, region.height);
//            dirtyRectangleManager.clearDirtyRectangle(texture);
//        }
//    }
    
}
