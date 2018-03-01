package fr.shinigota.game;

import fr.shinigota.engine.input.IInputProcessor;
import org.joml.Vector2d;
import org.joml.Vector2f;

import static org.lwjgl.glfw.GLFW.*;

public class Controller implements IInputProcessor {
    private final Vector2d previousPos;
    private final Vector2d currentPos;
    private final Vector2f displVec;

    private boolean inWindow = false;
    private boolean leftButtonPressed = false;
    private boolean rightButtonPressed = false;

    public Controller() {
        previousPos = new Vector2d(-1, -1);
        currentPos = new Vector2d(0, 0);
        displVec = new Vector2f();
    }

    @Override
    public void mouseMoved(double x, double y) {
        currentPos.x = x;
        currentPos.y = y;

        displVec.x = 0;
        displVec.y = 0;
        if (previousPos.x > 0 && previousPos.y > 0) {
            double deltax = currentPos.x - previousPos.x;
            double deltay = currentPos.y - previousPos.y;
            boolean rotateX = deltax != 0;
            boolean rotateY = deltay != 0;
            if (rotateX) {
                displVec.y = (float) deltax;
            }
            if (rotateY) {
                displVec.x = (float) deltay;
            }
        }
        previousPos.x = currentPos.x;
        previousPos.y = currentPos.y;
    }

    @Override
    public void mouseEntered() {
        inWindow = true;
    }

    @Override
    public void mouseLeaved() {
        inWindow = false;
    }

    @Override
    public void mouseClick(int button, int action) {
        leftButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
        rightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
    }

    public Vector2f getDisplVec() {
        return displVec;
    }

    public boolean isLeftButtonPressed() {
        return leftButtonPressed;
    }

    public boolean isRightButtonPressed() {
        return rightButtonPressed;
    }
}
