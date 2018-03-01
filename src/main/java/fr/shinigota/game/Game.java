package fr.shinigota.game;

import fr.shinigota.engine.IGameLogic;
import fr.shinigota.engine.Window;
import fr.shinigota.engine.entity.GameItem;
import fr.shinigota.engine.graphic.Camera;
import fr.shinigota.engine.graphic.Mesh;
import fr.shinigota.engine.graphic.texture.Texture;
import fr.shinigota.engine.graphic.texture.TextureSheet;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class Game implements IGameLogic {
    private static final float CAMERA_POS_STEP = .2f;
    private static final float MOUSE_SENSITIVITY = .3f;
    private final Renderer renderer;
    private final Vector3f cameraDelta;
    private final Camera camera;
    private final List<GameItem> gameItems;
    private final Controller controller;
    private GameTexture gameTexture;

    public Game(Controller controller) throws IOException {
        renderer = new Renderer();
        cameraDelta = new Vector3f();
        camera = new Camera();
        gameItems = new ArrayList<>();
        this.controller = controller;

    }

    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);
        gameTexture = new GameTexture();

        Mesh cubeMesh = Mesh.FACTORY.cubeMesh(gameTexture.GRASS);

        GameItem gameItem = new GameItem(cubeMesh);
        gameItem.setPosition(0, 0, -2f);
        gameItems.add(gameItem);

        GameItem gameItem2 = new GameItem(cubeMesh);
        gameItem2.setPosition(-1, 0, -2f);
        gameItems.add(gameItem2);

        GameItem gameItem3 = new GameItem(cubeMesh);
        gameItem3.setPosition(0, 1, -3f);
        gameItems.add(gameItem3);

        GameItem gameItem4 = new GameItem(cubeMesh);
        gameItem4.setPosition(-1, 1, -3f);
        gameItems.add(gameItem4);

    }

    @Override
    public void input(Window window) {
        cameraDelta.set(0, 0, 0);
        if (window.isKeyPressed(GLFW_KEY_W)) {
            cameraDelta.z = -1;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            cameraDelta.z = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            cameraDelta.x = -1;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            cameraDelta.x = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_Z)) {
            cameraDelta.y = -1;
        } else if (window.isKeyPressed(GLFW_KEY_X)) {
            cameraDelta.y = 1;
        }
    }

    @Override
    public void update(float interval) {
        // Update camera position
        camera.movePosition(cameraDelta.x * CAMERA_POS_STEP,
                cameraDelta.y * CAMERA_POS_STEP,
                cameraDelta.z * CAMERA_POS_STEP);
//
//        // Update camera based on mouse
////        if (controller.isRightButtonPressed()) {
//            Vector2f rotVec = controller.getDisplVec();
//            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
//        }

        Vector2f rotVec = controller.getDisplVec();
        camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
        System.out.println();
        controller.getDisplVec().set(0,0);

//        Vector3f camDeltaCpy = new Vector3f(cameraDelta);
////        Vector3f rotVecCpy = new Vector3f(rotVec.x, rotVec.y, 0).normalize();
////        camDeltaCpy.mul(rotVecCpy);
////        camera.movePosition(camDeltaCpy.x * CAMERA_POS_STEP,
////                camDeltaCpy.y * CAMERA_POS_STEP,
////                camDeltaCpy.z * CAMERA_POS_STEP);


    }

    @Override
    public void render(Window window) {
        renderer.render(window, camera, gameItems);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        if (gameItems != null) {
            for (GameItem gameItem : gameItems) {
                gameItem.cleanup();
            }
        }

    }

    @Override
    public void resize(Window window) throws Exception {
        renderer.resize(window);
    }
}
