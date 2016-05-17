# Rendering

## A rendering library

**Note**: This library is experimental and there may be considerable 
refactorings in the near future. 

### Background

The development of this library started in 2010, shortly after OpenGL 3.3 
was introduced. With the deprecation of the fixed function pipeline, the 
goals and application patterns of OpenGL changed dramatically. With the fixed
function pipeline, OpenGL was an API to describe the vertices of
rendered models (along with the vertex properties), and a matrix stack
to describe the model- and viewing transformations. In newer versions,
OpenGL is an API to maintain rendering data buffers and shader programs 
that are used for rendering these objects, and, most importantly, the 
mapping between the data buffers and the shader program inputs. 
 
### Goals

This library is an approach to provide an abstraction layer for OpenGL.
It is in no way complete, and can not provide the same low-level 
functionality that is offered by plain OpenGL. The main goal was to
create a library that provides the "core" functionality of modern OpenGL,
in a more convenient way (and based on my personal understanding of 
the concepts - which might be wrong). 

The library should allow the user to conveniently maintain graphical objects 
that contain certain attributes, i.e. data buffers with rendering data. 
A rendered object can be created by mapping these attributes to 
input parameters for shader programs. A rendered object can be displayed 
in the rendering environment by executing a sequence of rendering commands.

The translation of this goal into a (somewhat) object-oriented API is
straightforward: 

There are `GraphicsObject`s that contain certain `Attribute`s, i.e. 
`DataBuffers` with rendering data. A `RenderedObject` can be created by 
`Mapping` these `Attribute`s to input `Parameter`s for `Shader` `Programs`. 
A `RenderedObject` can be displayed in the `RenderingEnvironment` by 
executing a sequence of rendering `Command`s.

An illustrative code example, extracted from the samples:

```
// Create a simple GraphicsObject
GraphicsObject cube = GraphicsObjects.createCube();
 
// Create a texture from an image file
Texture texture = ImageTextures.createImageTextureFromFileUnchecked("lena512color.png");
 
// Create a default program that emulates the fixed function pipeline
Program program = Programs.createFixedFunctionProgram();
 
// Create a rendered object by connecting the input parameters of the
// program to the Attributes of the GraphicsObject 
RenderedObject texturedCube =
    RenderedObjects.create(cube, program)
        .connect(Parameters.VERTEX_POSITION, Attributes.VERTICES)
        .connect(Parameters.VERTEX_NORMAL, Attributes.NORMALS)
        .connect(Parameters.VERTEX_TEXCOORD0, Attributes.TEXCOORDS)
        .connect(Parameters.TEXTURE0, texture)
        .build();
 
// Pass the RenderedObject to the RenderingEnvironment,
renderingEnvironment.handleRenderedObject(texturedCube);
 
// Create a list of Commands that will cause the object to be rendered
CommandList commandList = new CommandList();
commandList.addCommands(
    ...
    // Render the textured cube
    Commands.render(texturedCube)
);
renderingEnvironment.addCommandSupplier(commandList);
``` 

It can be seen that this code does not *explicitly* refer to any concepts
that are specific for OpenGL. A similar API could probably be built on
top of other rendering APIs. A secondary goal of this library was to provide
an abstraction of the most important OpenGL bindings for Java: There exist 
backends implementing the core functionality based on both, JOGL and LWJGL. 


## Project structure

This project consists of the following modules:

 * [rendering-core](./rendering-core) : The classes and interfaces in this 
   module are abstract descriptions of the entities that are associated with 
   the low-level rendering process, like data buffers, graphically represented 
   objects or shader programs, but also classes for maintaining these objects, 
   as well as for managing view- and camera configurations. 
 * [rendering-desktop](./rendering-desktop) : Classes for using the rendering
   library on desktop (non-Android) systems. 
 * [rendering-interaction](./rendering-interaction) : Classes and interfaces
   related to interaction like camera controls an picking
 * [rendering-geometry](./rendering-geometry) : Classes and interfaces
   that simplify the handling of geometry data used for rendering
 * [rendering-core-gl](./rendering-core-gl) : Classes and abstract base 
   classes for OpenGL-based rendering backends. 
 * [rendering-core-jogl](./rendering-core-jogl) : An implementation of the
   rendering core classes based on JOGL
 * [rendering-core-lwjgl](./rendering-core-lwjgl) : An implementation of the
   rendering core classes based on LWJGL 2

 
### The `rendering-core` module

This module contains the main classes and interfaces for the rendering
system.

#### Rendering entities

These interfaces describe the building blocks for a rendering setup. For 
example, the `GeometryObject` interface describes an object that stores 
geometric information like triangle indices and vertex coordinates, as 
well as associated `Attribute`s, like texture coordinates. The `Program` 
class describes a shader program that defines the way how an object is 
rendered. Additional interfaces exist for textures, frame buffers or 
shaders. Conceptually, these entities are organized hierarchically,
and the root element of the hierarchy of rendering entities is the 
`RenderedObject`:

![RenderedObject01.png](/documentation/RenderedObject01.png)


#### Handlers

The `Handler` interface is a very basic interface that has the 
functionality of maintaining a set of objects and an internal
representation of these objects. Different flavors of the Handler 
interface exist for the different types of rendering entities. 
For example, a `ProgramHandler` may receive a `Program`, and possibly 
build up an internal representation for this program. When the program 
is released, the internal representation is destroyed. Additionally, 
the specific handler interfaces offer methods related to the handled 
object types. For example, the `ProgramHandler` offers methods for 
setting parameters of programs that will be passed to the respective 
program when it is executed. The handlers in turn form a hierarchy 
which is parallel to the hierarchy formed by the rendering entities.
They maintain the implementation-specific data for the rendering
entities - for example, OpenGL buffer IDs. Thus, they decouple the
rendering entities from the internal GL representation:

![Layers01.png](/documentation/Layers01.png)
 

#### The Renderer and Commands

The `Renderer` maintains the handlers for the rendering entities. 
The `Command` interface is a generic command that can be executed 
on a renderer. Depending on the implementation of the command, 
it may, for example, set program parameters, or trigger the 
rendering of a rendered object.

