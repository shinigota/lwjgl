package fr.shinigota.game;

import fr.shinigota.engine.input.IInputProcessor;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class Controller implements IInputProcessor {
    private static final float MOUSE_SENSITIVITY = .3f;

    private final Vector2d previousRotation;
    private final Map<Integer, Boolean> pressedKeys = new HashMap<>();

    private float cameraYaw = 0;
    private float cameraPitch = 0;

    public Controller() {
        previousRotation = new Vector2d(Game.WIDTH/2, Game.HEIGHT/2);

        pressedKeys.put(GLFW_KEY_W, false);
        pressedKeys.put(GLFW_KEY_A, false);
        pressedKeys.put(GLFW_KEY_S, false);
        pressedKeys.put(GLFW_KEY_D, false);

        cameraPitch = 0;
        cameraYaw = 0;
    }

    @Override
    public void mouseMoved(double x, double y) {
        double deltax = x - previousRotation.x;
        double deltay = y - previousRotation.y;
        boolean doesYaw = deltax != 0;
        boolean doesPitch = deltay != 0;
        if (doesYaw) {
            cameraYaw = (float) deltax * MOUSE_SENSITIVITY;
        }
        if (doesPitch) {
            cameraPitch = (float) deltay * MOUSE_SENSITIVITY;
        }

        previousRotation.x = x;
        previousRotation.y = y;
    }

    @Override
    public void mouseEntered() {

    }

    @Override
    public void mouseLeaved() {

    }

    @Override
    public void mouseClick(int button, int action) {
    }

    @Override
    public void onKeyPress(int key) {
        if (pressedKeys.containsKey(key)) {
            pressedKeys.put(key, true);
        }
    }

    @Override
    public void onKeyRelease(int key) {
        if (pressedKeys.containsKey(key)) {
            pressedKeys.put(key, false);
        }
    }

    @Override
    public void onKeyHold(int key) {

    }

    public float consumeCameraPitch() {
        float tmp = cameraPitch;
        cameraPitch = 0;
        return tmp;
    }

    public float consumeCameraYaw() {
        float tmp = cameraYaw;
        cameraYaw = 0;
        return tmp;
    }

    public boolean isKeyPressed(int key) {
        return pressedKeys.get(key);
    }
}
