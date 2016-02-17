/*
 * www.javagl.de - Rendering
 *
 * Copyright 2010-2016 Marco Hutter - http://www.javagl.de
 */
package de.javagl.rendering.samples;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.function.Supplier;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.vecmath.Matrix4f;

import de.javagl.rendering.core.Attributes;
import de.javagl.rendering.core.CommandList;
import de.javagl.rendering.core.Commands;
import de.javagl.rendering.core.FrameBuffer;
import de.javagl.rendering.core.FrameBuffers;
import de.javagl.rendering.core.GraphicsObject;
import de.javagl.rendering.core.GraphicsObjects;
import de.javagl.rendering.core.Parameters;
import de.javagl.rendering.core.Program;
import de.javagl.rendering.core.Programs;
import de.javagl.rendering.core.RenderedObject;
import de.javagl.rendering.core.RenderedObjects;
import de.javagl.rendering.core.RenderingEnvironment;
import de.javagl.rendering.core.Texture;
import de.javagl.rendering.core.Textures;
import de.javagl.rendering.core.light.LightSetup;
import de.javagl.rendering.core.light.LightSetups;
import de.javagl.rendering.core.material.Material;
import de.javagl.rendering.core.material.Materials;
import de.javagl.rendering.core.utils.MatrixUtils;
import de.javagl.rendering.desktop.ImageTextures;
import de.javagl.rendering.desktop.RenderingEnvironments;
import de.javagl.rendering.interaction.Control;
import de.javagl.rendering.interaction.camera.CameraControls;

/**
 * A sample demonstrating the JavaGL rendering classes by rendering a 
 * cube with multiple textures, of which one is a frame buffer texture
 */
public class Rendering_04_FBO
{
    /**
     * Entry point of this sample
     * 
     * @param args Not used
     */
    public static void main(String args[])
    {
        System.setProperty("sun.awt.noerasebackground", "true");
        SwingUtilities.invokeLater(Rendering_04_FBO::new);
    }
    
    /**
     * The {@link RenderingEnvironment} which provides the rendering
     * component and maintains the {@link RenderedObject} instances
     */
    private RenderingEnvironment<Component> renderingEnvironment = null;
    
    /**
     * Creates the sample instance
     */
    private Rendering_04_FBO()
    {
        // Create the RenderingEnvironment and obtain the rendering component
        renderingEnvironment = 
            RenderingEnvironments.createRenderingEnvironment();
        Component component = renderingEnvironment.getRenderComponent();

        // Create the frame that shows the rendering component
        JFrame frame = new JFrame(
            renderingEnvironment.getClass().getSimpleName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(component, BorderLayout.CENTER);
        
        // Create the contents for the sample scene
        createContents();
        
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
     */
    private void createContents()
    {
        // Create some basic objects
        GraphicsObject cube = GraphicsObjects.createCube();
        GraphicsObject sphere = GraphicsObjects.createSphere(3);

        // Create a texture from an image file
        Texture imageTexture = 
            ImageTextures.createImageTextureFromFileUnchecked(
                "images/lena512color.png");

        // Create a frame buffer 
        FrameBuffer frameBuffer = FrameBuffers.createFrameBuffer(500,500); 
        renderingEnvironment.handleFrameBuffer(frameBuffer);
        
        // Create a texture from the frame buffer
        Texture frameBufferTexture = 
            Textures.createFrameBufferTexture(frameBuffer);
        
        // Create a Program
        Program program = Programs.createFixedFunctionProgram();
        
        // A sphere rendered with colors
        RenderedObject renderedColoredSphere = 
            RenderedObjects.create(sphere, program)
                .connect(Parameters.VERTEX_POSITION, Attributes.VERTICES)
                .connect(Parameters.VERTEX_NORMAL, Attributes.NORMALS)
                .connect(Parameters.VERTEX_COLOR, Attributes.COLORS)
                .build();
        renderingEnvironment.handleRenderedObject(renderedColoredSphere);
        
        // A cube rendered with two textures. The second texture
        // will be the frame buffer
        RenderedObject renderedTexturedCube0 = 
            RenderedObjects.create(cube, program)
                .connect(Parameters.VERTEX_POSITION, Attributes.VERTICES)
                .connect(Parameters.VERTEX_NORMAL, Attributes.NORMALS)
                .connect(Parameters.VERTEX_TEXCOORD0, Attributes.TEXCOORDS)
                .connect(Parameters.VERTEX_TEXCOORD1, Attributes.TEXCOORDS)
                .connect(Parameters.TEXTURE0, imageTexture)
                .connect(Parameters.TEXTURE1, frameBufferTexture)
                .build();
        renderingEnvironment.handleRenderedObject(renderedTexturedCube0);
        
        // Create a default light setup and material
        LightSetup lightSetup = LightSetups.create();
        LightSetups.addDefaultLights(lightSetup);
        Material material = Materials.create();

        // Set up the list of commands for the rendering environment
        CommandList commandList = new CommandList();
        
        commandList.addCommands(

            // Set the light setup and material
            Commands.setLightSetup(program, lightSetup, 
                renderingEnvironment.getView()),
            Commands.setMaterial(program, material),
            
            // Enable the frame buffer
            Commands.setFrameBufferActive(frameBuffer),            

            // Disable all textures, for rendering the colored spheres
            Commands.setNumTextures(program, 0)
        );

        // Create commands to render the colored sphere
        // at different positions, and add them to the
        // list of commands
        int sizeX = 3;
        int sizeY = 3;
        for (int x=0; x<sizeX; x++)
        {
            for (int y=0; y<sizeY; y++)
            {
                float dx = (-sizeX / 2.0f + x + 0.5f) * 2;
                float dy = (-sizeY / 2.0f + y + 0.5f) * 2;
                
                Supplier<Matrix4f> matrixSupplier = () -> 
                {
                    float z = (float)Math.sin(
                        System.currentTimeMillis()/1000.0 + dx + dy);
                    return MatrixUtils.translation(dx, dy, z);
                };
                
                commandList.addCommands(
                    Commands.setDefaultMatrices(
                        program, matrixSupplier, 
                        renderingEnvironment.getView()),
                    Commands.render(renderedColoredSphere)
                );
            }
        }
        
        commandList.addCommands(
            
            // Disable the frame buffer
            Commands.setFrameBufferActive(null),
            
            // Render the cube with its textures (one of them is the 
            // frame buffer texture) 
            Commands.setDefaultMatrices(
                program, MatrixUtils.identity(), 
                renderingEnvironment.getView()),
            Commands.setNumTextures(program, 2),
            Commands.render(renderedTexturedCube0)
        ); 

        renderingEnvironment.addCommandSupplier(commandList);
        
        startRepaintThread(20);
    }
    
    /**
     * Create a thread that triggers a rendering in the given
     * interval.
     * 
     * @param intervalMs The interval, in milliseconds
     */
    private void startRepaintThread(long intervalMs)
    {
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    renderingEnvironment.triggerRendering();
                    try
                    {
                        Thread.sleep(intervalMs);
                    }
                    catch (InterruptedException e)
                    {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

}
