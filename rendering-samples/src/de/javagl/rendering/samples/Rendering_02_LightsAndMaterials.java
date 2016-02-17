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
import de.javagl.rendering.core.light.Light;
import de.javagl.rendering.core.light.LightSetup;
import de.javagl.rendering.core.light.LightSetups;
import de.javagl.rendering.core.light.Lights;
import de.javagl.rendering.core.material.Material;
import de.javagl.rendering.core.material.Materials;
import de.javagl.rendering.core.utils.MatrixUtils;
import de.javagl.rendering.desktop.ImageTextures;
import de.javagl.rendering.desktop.RenderingEnvironments;
import de.javagl.rendering.interaction.Control;
import de.javagl.rendering.interaction.camera.CameraControls;


/**
 * A sample demonstrating the JavaGL rendering classes by rendering a 
 * textured cube with lights and materials
 */
public class Rendering_02_LightsAndMaterials
{
    /**
     * Entry point of this sample
     * 
     * @param args Not used
     */
    public static void main(String args[])
    {
        System.setProperty("sun.awt.noerasebackground", "true");
        SwingUtilities.invokeLater(Rendering_02_LightsAndMaterials::new);
    }
    /**
     * Creates the sample instance
     */
    private Rendering_02_LightsAndMaterials()
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
        // Create the textured cube and pass it to the RenderingEnvironment
        // (See the basic samples for details)
        GraphicsObject cube = GraphicsObjects.createCube();
        Texture texture = ImageTextures.createImageTextureFromFileUnchecked(
            "images/lena512color.png");
        Program program = Programs.createFixedFunctionProgram();
        RenderedObject texturedCube = 
            RenderedObjects.create(cube, program)
                .connect(Parameters.VERTEX_POSITION, Attributes.VERTICES)
                .connect(Parameters.VERTEX_NORMAL, Attributes.NORMALS)
                .connect(Parameters.VERTEX_TEXCOORD0, Attributes.TEXCOORDS)
                .connect(Parameters.TEXTURE0, texture)
                .build();
        renderingEnvironment.handleRenderedObject(texturedCube);
        
        // Create the light setup
        LightSetup lightSetup = LightSetups.create();

        // Create a blue spotlight pointing at the center of the 
        // front side of the cube
        Light light0 = Lights.createSpotLight(
            0.5f, 0.5f, 2, 0, 0, -1, 60, 10);
        light0.setDiffuseColor(0, 0, 1, 0);
        light0.setConstantAttenuation(0.5f);
        lightSetup.addLight(light0);

        // Create a yellow point light at the upper right
        Light light1 = Lights.createPointLight(2, 2, 0.5f);
        light1.setDiffuseColor(1, 1, 0, 0);
        lightSetup.addLight(light1);
        
        // Create a shiny material that emits a green light,
        // and whose specular reflection is red
        Material material = Materials.create();
        material.setShininess(120);
        material.setEmissionColor(0, 0.2f, 0, 1);
        material.setSpecularColor(1, 0, 0, 1);
        
        // Create a list of Commands that will cause the object to be rendered
        // (See the basic samples for details)
        CommandList commandList = new CommandList();
        commandList.addCommands(
            Commands.setDefaultMatrices(
                program, MatrixUtils.identity(), 
                renderingEnvironment.getView()),

            // Apply the light setup
            Commands.setLightSetup(program, lightSetup, 
                renderingEnvironment.getView()),

            // Set the material for the object
            Commands.setMaterial(program, material),
                
            // Finally, render the textured cube
            Commands.setNumTextures(program, 1),
            Commands.render(texturedCube)
        );
        renderingEnvironment.addCommandSupplier(commandList);
    }
    

}
