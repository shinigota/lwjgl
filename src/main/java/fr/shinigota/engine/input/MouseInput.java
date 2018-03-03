package fr.shinigota.engine.input;

import fr.shinigota.engine.Window;
import fr.shinigota.game.Controller;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

import java.nio.DoubleBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class MouseInput {

    private IInputProcessor inputProcessor;
    private Window window;

    public void init(Window window) {
        this.window = window;
        glfwSetCursorPos(window.getWindowHandle(), window.getWidth() / 2, window.getHeight() / 2);
        inputProcessor = window.getInputProcessor();

        glfwSetCursorPosCallback(window.getWindowHandle(),(windowHandle, xpos, ypos) -> {
            if (inputProcessor != null) {
                MouseInput.this.mouseMoved(xpos, ypos);
            }
        });

        glfwSetCursorEnterCallback(window.getWindowHandle(), (windowHandle, entered) -> {
            if (MouseInput.this.inputProcessor == null) {
                return;
            }

            if (entered) {
                MouseInput.this.mouseEntered();
            } else {
                MouseInput.this.mouseLeaved();
            }
        });

        glfwSetMouseButtonCallback(window.getWindowHandle(), (windowHandle, button, action, mode) -> {
            if (MouseInput.this.inputProcessor == null) {
                return;
            }
            MouseInput.this.mouseClick(button, action);
        });
    }

    public void update() {


    }

    private void mouseMoved(double x, double y) {
        inputProcessor.mouseMoved(x, y);
    }

    private void mouseEntered() {
        inputProcessor.mouseEntered();
    }

    private void mouseLeaved() {
        inputProcessor.mouseLeaved();
    }

    private void mouseClick(int button, int type) {
        inputProcessor.mouseClick(button, type);
    }
}
