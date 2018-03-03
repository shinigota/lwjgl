package fr.shinigota.game;

import fr.shinigota.engine.graphic.Camera;
import fr.shinigota.engine.input.IInputProcessor;
import org.joml.Vector2d;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class Controller implements IInputProcessor {
    private static final float MOUSE_SENSITIVITY = .1f;

    private final Vector2d previousRotation;
    private final Map<Integer, Boolean> pressedKeys = new HashMap<>();

    private Camera camera;


    public Controller() {
        previousRotation = new Vector2d(Game.WIDTH/2, Game.HEIGHT/2);

        pressedKeys.put(GLFW_KEY_W, false);
        pressedKeys.put(GLFW_KEY_A, false);
        pressedKeys.put(GLFW_KEY_S, false);
        pressedKeys.put(GLFW_KEY_D, false);
    }

    @Override
    public void mouseMoved(double x, double y) {
        float pitch = 0;
        float yaw = 0;

        double deltax = x - previousRotation.x;
        double deltay = y - previousRotation.y;
        boolean doesYaw = deltax != 0;
        boolean doesPitch = deltay != 0;
        if (doesYaw) {
            yaw = (float) deltax;
        }
        if (doesPitch) {
            pitch = (float) deltay;
        }

        previousRotation.x = x;
        previousRotation.y = y;

        if(camera != null) {
            camera.moveRotation(pitch * MOUSE_SENSITIVITY, yaw * MOUSE_SENSITIVITY);
        }
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

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public boolean isKeyPressed(int key) {
        return pressedKeys.get(key);
    }
}
