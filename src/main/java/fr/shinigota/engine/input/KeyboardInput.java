package fr.shinigota.engine.input;

import fr.shinigota.engine.Window;

import static org.lwjgl.glfw.GLFW.*;

public class KeyboardInput {
    private IInputProcessor inputProcessor;
    private Window window;

    public void init(Window window) {
        this.window = window;
        inputProcessor = window.getInputProcessor();

        glfwSetKeyCallback(window.getWindowHandle(), (window1, key, scancode, action, mods) -> {
            if (KeyboardInput.this.inputProcessor == null) {
                return;
            }

            if( action == GLFW_PRESS) {
                KeyboardInput.this.onKeyPress(key);
            } else if( action == GLFW_RELEASE) {
                KeyboardInput.this.onKeyReleased(key);
            } else if( action == GLFW_REPEAT) {
                KeyboardInput.this.onKeyHold(key);
            }
        });
    }

    private void onKeyHold(int key) {
        inputProcessor.onKeyHold(key);
    }

    private void onKeyReleased(int key) {
        inputProcessor.onKeyRelease(key);
    }

    private void onKeyPress(int key) {
        inputProcessor.onKeyPress(key);
    }
}
