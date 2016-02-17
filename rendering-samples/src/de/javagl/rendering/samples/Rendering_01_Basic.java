/*
 * www.javagl.de - Rendering
 *
 * Copyright 2010-2016 Marco Hutter - http://www.javagl.de
 */
package de.javagl.rendering.samples;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import de.javagl.rendering.core.Attributes;
import de.javagl.rendering.core.CommandList;
import de.javagl.rendering.core.Commands;
import de.javagl.rendering.core.GraphicsObject;
import de.javagl.rendering.core.GraphicsObjects;
import de.javagl.rendering.core.Parameters;
import de.javagl.rendering.core.Program;
import de.javagl.rendering.core.Programs;
import de.javagl.rendering.core.RenderedObject;
import de.javagl.rendering.core.RenderedObjects;
import de.javagl.rendering.core.RenderingEnvironment;
import de.javagl.rendering.core.Texture;
import de.javagl.rendering.core.utils.MatrixUtils;
import de.javagl.rendering.desktop.ImageTextures;
import de.javagl.rendering.desktop.RenderingEnvironments;
import de.javagl.rendering.interaction.Control;
import de.javagl.rendering.interaction.camera.CameraControls;

/**
 * A sample demonstrating the JavaGL rendering classes by rendering a 
 * simple, textured cube.
 */
public class Rendering_01_Basic
{
    /**
     * Entry point of this sample
     * 
     * @param args Not used
     */
    public static void main(String args[])
    {
        System.setProperty("sun.awt.noerasebackground", "true");
        SwingUtilities.invokeLater(Rendering_01_Basic::new);
    }
    /**
     * Creates the sample instance
     */
    private Rendering_01_Basic()
    {
        // Create the RenderingEnvironment and obtain the rendering component
        RenderingEnvironment<Component> renderingEnvironment =
            RenderingEnvironments.createRenderingEnvironment();
        Component component = renderingEnvironment.getRenderComponent();

        // Create the frame that shows the rendering component
        JFrame frame = new JFrame(
            renderingEnvironment.getClass().getSimpleName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(component, BorderLayout.CENTER);
        
        // Create the contents for the sample scene
        createContents(renderingEnvironment);
        
        // Initialize the interaction
        Control cameraControl = 
            CameraControls.createDefaultArcballControl(
                renderingEnvironment.getView());
        cameraControl.attachTo(component);
        
        frame.setSize(800,800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    /**
     * Create the contents of the {@link RenderingEnvironment}
     * 
     * @param renderingEnvironment The {@link RenderingEnvironment}
     */
    private void createContents(
        RenderingEnvironment<Component> renderingEnvironment)
    {
        // Create a simple GraphicsObject. This GraphicsObject contains
        // DataBuffers for several default Attributes. These are at least
        // Attributes.VERTICES, Attributes.TEXCOORDS and Attributes.NORMALS.
        GraphicsObject cube = GraphicsObjects.createCube();
        
        // Create a texture from an image file
        Texture texture = ImageTextures.createImageTextureFromFileUnchecked(
        	"images/lena512color.png");
        
        // Create a default program that emulates the fixed function pipeline
        Program program = Programs.createFixedFunctionProgram();
        
        // Create a rendered object. It will render the GraphicsObject with 
        // the loaded Program. The connections between input Parameters of
        // the Program and the Attributes of the GraphicsObject are 
        // established.
        RenderedObject texturedCube = 
            RenderedObjects.create(cube, program)
                .connect(Parameters.VERTEX_POSITION, Attributes.VERTICES)
                .connect(Parameters.VERTEX_NORMAL, Attributes.NORMALS)
                .connect(Parameters.VERTEX_TEXCOORD0, Attributes.TEXCOORDS)
                .connect(Parameters.TEXTURE0, texture)
                .build();
        
        // Pass the RenderedObject to the RenderingEnvironment,
        // which will initialize all internal data structures
        renderingEnvironment.handleRenderedObject(texturedCube);
        
        // Create a list of Commands that will cause the object to be rendered
        CommandList commandList = new CommandList();
        commandList.addCommands(

            // Create a command to set the model-, view-, projection- and
            // normal-matrix of the program based on the View that is 
            // provided via the RenderingEnvironment, and set the model 
            // matrix to identity
            Commands.setDefaultMatrices(
                program, MatrixUtils.identity(), 
                renderingEnvironment.getView()),
            
            // Set the number of textures 
            Commands.setNumTextures(program, 1),
                
            // Finally, render the textured cube
            Commands.render(texturedCube)
        );
        renderingEnvironment.addCommandSupplier(commandList);
    }
    
    

}
