package de.javagl.rendering.interaction.camera;

import java.awt.Point;

/**
 * Interface for a basic camera behavior. Usually this class will be 
 * controlled by a MouseListener/MouseMotionListener/MouseWheelListener
 * (particularly, by a {@link CameraControl}) by calling the methods 
 * accordingly:
 * <ul>
 *   <li> 
 *     {@link #startRotate(Point)}
 *      when the mouse is pressed to start a rotation
 *   </li>
 *   <li> 
 *      {@link #doRotate(Point)}
 *      when the mouse is dragged to rotate the camera
 *   </li>
 *   <li> 
 *      {@link #startMovement(Point)}
 *      when the mouse is pressed to start a movement
 *   </li>
 *   <li> 
 *     {@link #startRotate(Point)}
 *      when the mouse is pressed to start a rotation
 *   </li>
 *   <li> 
 *      {@link #doMovement(Point)}
 *      when the mouse is moved to move the camera
 *   </li>
 *   <li> 
 *      {@link #zoom(float)}
 *      when the mouse wheel is rotated to zoom
 *   </li>
 *   <li> 
 *      {@link #translateZ(float)}
 *      when the mouse wheel is rotated while holding SHIFT to 
 *      translate the camera along the view direction
 *   </li>
 * </ul>
 * However, different applications of these methods may be implemented
 * by the client. <br>
 */
interface CameraBehavior
{
    /**
     * Reset the camera to its initial configuration.
     */
    void reset();

    /**
     * Called when the rotation starts at the given point
     * 
     * @param point The point where the rotation starts, 
     * in screen coordinates
     */
    void startRotate(Point point);

    /**
     * Called when the rotation continues to the given point
     * 
     * @param point The current mouse position, in screen coordinates
     */
    void doRotate(Point point);

    /**
     * Called when the movement starts at the given point
     * 
     * @param point The point where the movement starts, in screen coordinates
     */
    void startMovement(Point point);

    /**
     * Called when the movement continues to the given point
     * 
     * @param point The current mouse position, in screen coordinates
     */
    void doMovement(Point point);

    /**
     * Zoom by the given amount. This will move the eye position
     * towards or away from the view position.
     * 
     * @param amount The zoom amount
     */
    void zoom(float amount);

    /**
     * Translate the camera along the viewing direction, by the given amount.
     * 
     * @param amount The amount to translate by
     */
    void translateZ(float amount);

}