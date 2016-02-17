/**
 * The main classes of the JavaGL Rendering Core.
 * 
 * <h2>Structure</h2>
 * 
 * The interfaces in this package are abstract descriptions of the
 * entities that are associated with the low-level rendering process, like
 * data buffers, graphically represented objects or shader programs. <br>
 * <br>
 * Instances of classes implementing the core interfaces can be created 
 * using the static factory methods, which are located in classes dedicated 
 * to the respective interfaces:
 * <ul>
 *  <li>
 *    {@link de.javagl.rendering.core.RenderedObject RenderedObject} 
 *    instances may be created with the 
 *    {@link de.javagl.rendering.core.RenderedObjects RenderedObjects} class
 *  </li>
 *  <li>
 *    {@link de.javagl.rendering.core.Program Program} 
 *    instances may be created with the  
 *    {@link de.javagl.rendering.core.Programs Programs} class
 *  </li>
 *  <li>
 *    {@link de.javagl.rendering.core.GraphicsObject GraphicsObject} 
 *    instances may be created with the
 *    {@link de.javagl.rendering.core.GraphicsObjects GraphicsObjects} class
 *  </li>
 *  <li>
 *    {@link de.javagl.rendering.core.DataBuffer DataBuffer} 
 *    instances may be created with the  
 *    {@link de.javagl.rendering.core.DataBuffers DataBuffers} class
 *  </li>
 *  <li>
 *    {@link de.javagl.rendering.core.Texture Texture} 
 *    instances may be created with the  
 *    {@link de.javagl.rendering.core.Textures Textures} class
 *  </li>
 *  <li>
 *    {@link de.javagl.rendering.core.FrameBuffer FrameBuffer} 
 *    instances may be created with the  
 *    {@link de.javagl.rendering.core.FrameBuffers FrameBuffers} class
 *  </li>
 * </ul>

 * <br>
 * <h2>Concept</h2>
 * 
 * The conceptual root of the rendering infrastructure is the
 * {@link de.javagl.rendering.core.RenderingEnvironment RenderingEnvironment} 
 * interface. It summarizes the elements that are required for rendering in a 
 * very general way. It contains, for example, a 
 * {@link de.javagl.rendering.core.view.View View} that 
 * defines the viewport and {@link de.javagl.rendering.core.view.Camera Camera} 
 * position, a rendering component, helper methods for maintaining the 
 * {@link de.javagl.rendering.core.RenderedObject RenderedObject} instances, 
 * and, most importantly, a {@link de.javagl.rendering.core.Renderer Renderer}.
 * <br>
 * <br>
 * A Renderer offers a set of specific 
 * {@link de.javagl.rendering.core.handling.Handler Handler} 
 * instances for different classes associated with rendering:
 * <ul>
 *  <li> 
 *    A {@link de.javagl.rendering.core.handling.RenderedObjectHandler 
 *    RenderedObjectHandler} for 
 *    {@link de.javagl.rendering.core.RenderedObject RenderedObject} 
 *    instances
 *  </li>
 *  <li> 
 *    A {@link de.javagl.rendering.core.handling.ProgramHandler 
 *    ProgramHandler} for 
 *    {@link de.javagl.rendering.core.Program Program} 
 *    instances
 *  </li>
 *  <li> 
 *    A {@link de.javagl.rendering.core.handling.GraphicsObjectHandler
 *    GraphicsObjectHandler} for 
 *    {@link de.javagl.rendering.core.GraphicsObject GraphicsObject} 
 *    instances
 *  </li>
 *  <li> 
 *    A {@link de.javagl.rendering.core.handling.DataBufferHandler
 *    DataBufferHandler} for 
 *    {@link de.javagl.rendering.core.DataBuffer DataBuffer} 
 *    instances
 *  </li>
 *  <li> 
 *    A {@link de.javagl.rendering.core.handling.TextureHandler 
 *    TextureHandler} for 
 *    {@link de.javagl.rendering.core.Texture Texture}
 *    instances
 *  </li>
 *  <li> 
 *    A {@link de.javagl.rendering.core.handling.FrameBufferHandler
 *    FrameBufferHandler} for 
 *    {@link de.javagl.rendering.core.FrameBuffer FrameBuffer}
 *    instances.
 *  </li>
 * </ul>
 * Each of these handlers offer methods for handling instances of the 
 * respective classes, and for performing specific operations on these 
 * instances. For example, the 
 * {@link de.javagl.rendering.core.handling.ProgramHandler ProgramHandler} 
 * allows setting parameters for a 
 * {@link de.javagl.rendering.core.Program Program}.<br>
 * <br>
 * For each handled object, the respective Handler class maintains a reference 
 * counter. When the handler is told to handle an object, its reference counter 
 * is increased. If the object was not yet handled, internal data structures 
 * may be initialized and associated with the object. When it is told to 
 * release an object, its reference counter is decreased. When the reference 
 * counter reaches zero, the internal data structures for the  object are 
 * deleted. <br>
 * <br>
 * This behavior is especially important because the objects form
 * a hierarchy - and so to the handlers. When a  
 * {@link de.javagl.rendering.core.RenderedObject RenderedObject} is passed to
 * a {@link de.javagl.rendering.core.handling.RenderedObjectHandler
 * RenderedObjectHandler}, the handler will automatically pass the  
 * {@link de.javagl.rendering.core.GraphicsObject GraphicsObject} to the
 * {@link de.javagl.rendering.core.handling.GraphicsObjectHandler
 * GraphicsObjectHandler}, which in turn will pass all 
 * {@link de.javagl.rendering.core.DataBuffer DataBuffer} instances to the
 * {@link de.javagl.rendering.core.handling.DataBufferHandler 
 * DataBufferHandler} and so on.
 * <br>
 * <br>
 * 
 * 
 * <h2>Handling of classes which have a GL representation</h2>
 * 
 * The most obvious implementations of renderers are backed by OpenGL.
 * It is not a coincidence that several interfaces describe objects 
 * that have a direct representation in OpenGL:
 * <ul>
 *  <li> A {@link de.javagl.rendering.core.Program Program}
 *       corresponds to an OpenGL program
 *  </li>
 *  <li> A {@link de.javagl.rendering.core.GraphicsObject GraphicsObject}
 *       corresponds to a Vertex Array Object (VAO)
 *  </li>
 *  <li> A {@link de.javagl.rendering.core.DataBuffer DataBuffer}
 *       corresponds to a Vertex Buffer Object (VBO) 
 *  </li>
 *  <li> A {@link de.javagl.rendering.core.Texture Texture}
 *       corresponds to an OpenGL texture 
 *  </li>
 *  <li> A {@link de.javagl.rendering.core.FrameBuffer FrameBuffer}
 *       corresponds to an OpenGL frame buffer
 *  </li>
 * </ul>
 * Finally, a {@link de.javagl.rendering.core.RenderedObject RenderedObject}
 * describes the process of rendering a specific object with
 * a specific program and textures. <br>
 * <br>
 * For each of these classes, there are corresponding GL classes. 
 * These classes are the internal data structures that are 
 * maintained by the handlers. They usually only store the 
 * <code>int</code> identifiers for the GL objects. 
 * 
 * 
 * <h3> The {@link de.javagl.rendering.core.handling.ProgramHandler 
 * ProgramHandler} </h3>
 * <br>
 * Handling a {@link de.javagl.rendering.core.Program Program} will 
 * usually consist of the following steps:<br>
 * <ul>
 *  <li>Create the GL program ID</li>
 *  <li>
 *    For each {@link de.javagl.rendering.core.Shader Shader}
 *    of the {@link de.javagl.rendering.core.Program Program}:
 *    <ul>
 *      <li>Create a GL shader ID</li>
 *      <li>Set the source of the GL shader to be the source 
 *          of the {@link de.javagl.rendering.core.Shader Shader}
 *      </li>
 *      <li>Compile the GL shader</li>
 *      <li>Attach the GL shader to the GL program</li>
 *      <li>Delete the GL shader ID</li>
 *    </ul>
 *  </li>      
 *  <li>Link the GL program</li>
 *  <li>Validate the GL program</li>
 * </ul>
 * <br>
 * <br>
 * 
 * 
 * 
 * <h3> The {@link de.javagl.rendering.core.handling.DataBufferHandler
 * DataBufferHandler} </h3>
 * <br>
 * Handling a {@link de.javagl.rendering.core.DataBuffer DataBuffer} will 
 * usually consist of the following steps:<br>
 * <ul>
 *  <li>Create the VBO</li>
 *  <li>Bind the VBO (as GL_ARRAY_BUFFER)</li>
 *  <li>Buffer the data from the DataBuffer</li>
 *  <li>Unbind the VBO</li>
 * </ul>
 * <br>
 * Updating a {@link de.javagl.rendering.core.DataBuffer DataBuffer} will 
 * usually consist of the following steps:<br>
 * <ul>
 *   <li>Bind the VBO</li>
 *   <li>Map the memory of the GL buffer</li>
 *   <li>Copy the data from the DataBuffer to the mapped buffer</li>
 *  <li>Unbind the VBO</li>
 * </ul>
 * <br>
 * <br>
 * 
 * <h3> The {@link de.javagl.rendering.core.handling.GraphicsObjectHandler
 * GraphicsObjectHandler} </h3>
 * <br>
 * Handling a {@link de.javagl.rendering.core.GraphicsObject GraphicsObject} 
 * will usually consist of the following steps:<br>
 * <ul>
 *   <li>
 *     Handle each {@link de.javagl.rendering.core.DataBuffer DataBuffer}
 *     of the {@link de.javagl.rendering.core.GraphicsObject GraphicsObject} 
 *     by passing them to the 
 *     {@link de.javagl.rendering.core.handling.DataBufferHandler 
 *     DataBufferHandler}
 *   </li>
 *   <li>If the given {@link de.javagl.rendering.core.GraphicsObject 
 *   GraphicsObject} is not yet handled:
 *     <ul>
 *       <li>Create the VBO for the indices</li> 
 *       <li>
 *         Bind the VBO for the indices 
 *         (as <code>GL_ELEMENT_ARRAY_BUFFER</code>)
 *        </li>
 *       <li>
 *         Buffer the indices data from the given 
 *         {@link de.javagl.rendering.core.GraphicsObject GraphicsObject}
 *       </li>
 *       <li>Unbind the VBO</li>
 *     </ul>
 *   </li>  
 * </ul>
 * <br>
 * <br>
 * 
 * 
 * <h3> The {@link de.javagl.rendering.core.handling.TextureHandler
 * TextureHandler} </h3>
 * <br>
 * Handling a {@link de.javagl.rendering.core.Texture Texture} will 
 * usually consist of the following steps:<br>
 * <ul>
 *   <li>Generate the GL texture ID</li>
 *   <li>Generate the PBO</li>
 *   <li>Bind the PBO</li>
 *   <li>Buffer the PBO data (empty, only for allocation)</li>
 *   <li>Bind the GL texture ID</li>
 *   <li>Set texture parameters</li>
 *   <li>Unbind the GL texture ID</li>
 *   <li>Unbind the PBO</li>
 * </ul>
 * <br>
 * Updating a {@link de.javagl.rendering.core.Texture Texture} will
 * usually consist of the following steps:<br>
 * <ul>
 *   <li>Bind the PBO</li>
 *   <li>Bind the GL texture ID</li>
 *   <li>Map the PBO buffer</li>
 *   <li>Copy the {@link de.javagl.rendering.core.ImageData ImageData} 
 *       to the mapped buffer 
 *   </li>  
 *   <li>Unmap the PBO buffer</li>
 *   <li>Define the PBO as TexImage2D</li>
 *   <li>Unbind the GL texture ID</li>
 *   <li>Unbind the PBO</li>
 * </ul> 
 * <br>
 * <br>
 * 
 * <h3> The {@link de.javagl.rendering.core.handling.RenderedObjectHandler
 * RenderedObjectHandler} </h3>
 * <br>
 * Handling a {@link de.javagl.rendering.core.RenderedObject RenderedObject} 
 * will usually consist of the following steps:<br>
 * <ul>
 *   <li>
 *     Handle the {@link de.javagl.rendering.core.Program Program} of the 
 *     {@link de.javagl.rendering.core.RenderedObject RenderedObject} 
 *     by passing it to the 
 *     {@link de.javagl.rendering.core.handling.ProgramHandler ProgramHandler}
 *   </li>
 *   <li>
 *     Handle the {@link de.javagl.rendering.core.GraphicsObject
 *     GraphicsObject} of the {@link de.javagl.rendering.core.RenderedObject
 *     RenderedObject} by passing it to the 
 *     {@link de.javagl.rendering.core.handling.GraphicsObjectHandler
 *     GraphicsObjectHandler}
 *   </li>
 *   <li>
 *     Handle each {@link de.javagl.rendering.core.Texture Texture} of the 
 *     {@link de.javagl.rendering.core.RenderedObject RenderedObject} 
 *     by passing it to the 
 *     {@link de.javagl.rendering.core.handling.TextureHandler TextureHandler}
 *   </li>
 *   <li>
 *     If the {@link de.javagl.rendering.core.RenderedObject RenderedObject} 
 *     is not yet handled:
 *     <ul>
 *       <li>Generate the VAO</li>
 *       <li>Bind the VAO</li>
 *       <li>
 *         Bind each {@link de.javagl.rendering.core.Attribute Attribute} 
 *         of the {@link de.javagl.rendering.core.RenderedObject RenderedObject} 
 *       </li> 
 *     <li>Unbind the VAO</li>
 *   </ul>
 * </ul>
 * <br>
 * <br>
 * Rendering a {@link de.javagl.rendering.core.RenderedObject
 * RenderedObject} will usually consist of the following steps:<br>
 * <ul>
 *   <li>
 *     Obtain the {@link de.javagl.rendering.core.Mapping Mapping} from the
 *     {@link de.javagl.rendering.core.RenderedObject RenderedObject} that maps
 *     the {@link de.javagl.rendering.core.Parameter Parameter} instances
 *     of the {@link de.javagl.rendering.core.Program Program} to the
 *     {@link de.javagl.rendering.core.Texture Texture} instances that 
 *     are used for the program input. 
 *   </li>
 *   <li>
 *     For each {@link de.javagl.rendering.core.Texture Texture}:
 *     <ul>
 *       <li>Activate the corresponding GL_TEXTUREn</li>
 *       <li>Bind the GL texture ID</li>
 *       <li>Use the <code>GL_TEXTUREn</code> as the program input</li>
 *     </ul>
 *   </li>
 *   <li>
 *     Activate the GL program that corresponds to the 
 *     {@link de.javagl.rendering.core.Program Program}
 *   </li>
 *   <li>
 *     Bind the VAO that corresponds to the 
 *     {@link de.javagl.rendering.core.RenderedObject RenderedObject}
 *   </li>
 *   <li>
 *     Bind the VBO that corresponds to the indices of the 
 *     {@link de.javagl.rendering.core.GraphicsObject GraphicsObject}
 *   </li>
 *   <li>
 *     Finally, draw the elements
 *   </li>
 *   <li>
 *     Unbind all previously bound buffers
 *   </li>
 * </ul>
 * <br>
 * <br>
 */
package de.javagl.rendering.core;


