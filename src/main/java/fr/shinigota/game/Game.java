package fr.shinigota.game;

import fr.shinigota.engine.IGameLogic;
import fr.shinigota.engine.Window;
import fr.shinigota.engine.graphic.texture.CubeTexture;
import fr.shinigota.engine.graphic.texture.Texture;
import fr.shinigota.engine.graphic.Camera;
import fr.shinigota.engine.graphic.Skybox;
import fr.shinigota.game.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;


import static org.lwjgl.glfw.GLFW.*;

public class Game implements IGameLogic {
    public static final boolean DEBUG = true;

    private static final Logger LOGGER = LogManager.getLogger(Game.class);

    public static final int HEIGHT = 720;
    public static final int WIDTH = 1280;

    private final Renderer renderer;
    private final Camera camera;

    private final Controller controller;

    private World world;

    private Texture skyboxTexture;
    private Skybox skybox;

    public Game(Renderer renderer, Controller controller) throws IOException {
        this.renderer = renderer;
//        camera = new Camera(8, 9, 5);
        camera = new Camera(8, 60, 30);
//        camera = new Camera(8, 4, 5);
        this.controller = controller;

    }

    @Override
    public void init(Window window) throws Exception {
        world = new World();
        world.generate();

        renderer.init(window);

        skyboxTexture = new Texture("/textures/skybox.png");

        CubeTexture skyboxCube = new CubeTexture
                .CubeTextureBuilder(skyboxTexture, 32, 32, 0)
                .down(32, 64, 0)
                .up(32, 0, 0)
                .build();

        skybox = new Skybox(skyboxCube);

    }

    @Override
    public void update(float interval) {
        camera.moveRotation(controller.consumeCameraPitch(), controller.consumeCameraYaw());

        if (controller.isKeyPressed(GLFW_KEY_W)) {
            camera.moveToDirection(Camera.Direction.FORWARD);
        }
        if (controller.isKeyPressed(GLFW_KEY_S)) {
            camera.moveToDirection(Camera.Direction.BACKWARD);
        }
        if (controller.isKeyPressed(GLFW_KEY_A)) {
            camera.moveToDirection(Camera.Direction.LEFT);
        }
        if (controller.isKeyPressed(GLFW_KEY_D)) {
            camera.moveToDirection(Camera.Direction.RIGHT);
        }

        if (Game.DEBUG) {
            LOGGER.info(camera);
        }
    }

    @Override
    public void render(Window window) {
        renderer.render(window, camera, world.getRenderer(), skybox);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();

        world.cleanup();

        if (skyboxTexture != null) {
            skyboxTexture.cleanup();
        }

        world.cleanup();
    }

    @Override
    public void resize(Window window) throws Exception {
        renderer.resize(window);
    }
}
