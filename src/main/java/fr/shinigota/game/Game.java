package fr.shinigota.game;

import fr.shinigota.engine.IGameLogic;
import fr.shinigota.engine.Window;
import fr.shinigota.engine.entity.GameItem;
import fr.shinigota.engine.graphic.Camera;
import fr.shinigota.engine.graphic.Mesh;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class Game implements IGameLogic {
    private static final float MOUSE_SENSITIVITY = .3f;
    public static final int HEIGHT = 720;
    public static final int WIDTH = 1280;
    private final Renderer renderer;
    private final Camera camera;
    private final List<GameItem> gameItems;
    private final Controller controller;
    private GameTexture gameTexture;

    public Game(Renderer renderer, Controller controller) throws IOException {
        this.renderer = renderer;
        camera = new Camera();
        gameItems = new ArrayList<>();
        this.controller = controller;
        controller.setCamera(camera);

    }

    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);
        gameTexture = new GameTexture();

        Mesh cubeMesh = Mesh.FACTORY.cubeMesh(gameTexture.GRASS);
        Mesh cubeMesh2 = Mesh.FACTORY.cubeMesh(gameTexture.DIRT);

        GameItem gameItem = new GameItem(cubeMesh2);
        gameItem.setPosition(0, 0, -2f);
        gameItems.add(gameItem);

        GameItem gameItem2 = new GameItem(cubeMesh2);
        gameItem2.setPosition(-1, 0, -2f);
        gameItems.add(gameItem2);

        GameItem gameItem6 = new GameItem(cubeMesh);
        gameItem6.setPosition(0, 0, -3f);
        gameItems.add(gameItem6);

        GameItem gameItem5 = new GameItem(cubeMesh);
        gameItem5.setPosition(-1, 0, -3f);
        gameItems.add(gameItem5);

        GameItem gameItem3 = new GameItem(cubeMesh2);
        gameItem3.setPosition(0, 1, -3f);
        gameItems.add(gameItem3);

        GameItem gameItem4 = new GameItem(cubeMesh2);
        gameItem4.setPosition(-1, 1, -3f);
        gameItems.add(gameItem4);

    }

    @Override
    public void update(float interval) {
        if (controller.isKeyPressed(GLFW_KEY_W)) {
            camera.moveToDirection(Camera.Direction.FORWARD);
        }
        if (controller.isKeyPressed(GLFW_KEY_S)) {
            camera.moveToDirection(Camera.Direction.BACKWARD);
        }
        if (controller.isKeyPressed(GLFW_KEY_A)) {
            camera.moveToDirection(Camera.Direction.LEFT);
        } if (controller.isKeyPressed(GLFW_KEY_D)) {
            camera.moveToDirection(Camera.Direction.RIGHT);
        }
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
