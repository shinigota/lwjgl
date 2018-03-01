package fr.shinigota.engine.input;

import fr.shinigota.engine.Window;
import fr.shinigota.game.Controller;

import static org.lwjgl.glfw.GLFW.*;

public class MouseInput {

    private IInputProcessor inputProcessor;

    public void init(Window window) {
        inputProcessor = window.getInputProcessor();
        glfwSetCursorPosCallback(window.getWindowHandle(), (windowHandle, xpos, ypos) -> {
            if (inputProcessor != null) {
                MouseInput.this.mouseMoved(xpos, ypos);
            }
        });
        glfwSetCursorEnterCallback(window.getWindowHandle(), (windowHandle, entered) -> {
            if (inputProcessor != null) {
                if (entered) {
                    MouseInput.this.mouseEntered();
                } else {
                    MouseInput.this.mouseLeaved();
                }
            }
        });

        glfwSetMouseButtonCallback(window.getWindowHandle(), (windowHandle, button, action, mode) -> {
            if (inputProcessor != null) {
                MouseInput.this.mouseClick(button, action);
            }
        });
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
