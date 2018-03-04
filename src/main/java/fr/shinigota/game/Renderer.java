package fr.shinigota.game;

import fr.shinigota.engine.Utils;
import fr.shinigota.engine.Window;
import fr.shinigota.engine.entity.GameItem;
import fr.shinigota.engine.graphic.Camera;
import fr.shinigota.engine.graphic.ShaderProgram;
import fr.shinigota.engine.graphic.Skybox;
import fr.shinigota.engine.graphic.Transformation;
import org.joml.Matrix4f;

import java.util.List;

import static org.lwjgl.opengl.EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT;
import static org.lwjgl.opengl.GL11.*;

public class Renderer {
    private static final float FOV = (float) Math.toRadians(70.0f);
    private static final float Z_NEAR = 0.01f;
    private static final float Z_FAR = 1000.f;
    private final Transformation transformation;
    private final Controller controller;

    private ShaderProgram shaderProgram;


    public Renderer(Controller controller) {
        transformation = new Transformation();
        this.controller = controller;
    }


    public void init(Window window) throws Exception {
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Utils.loadResource("/shaders/vertex.vs"));
        shaderProgram.createFragmentShader(Utils.loadResource("/shaders/fragment.fs"));
        shaderProgram.link();

        shaderProgram.createUniform("projectionMatrix");
        shaderProgram.createUniform("texture_sampler");
        shaderProgram.createUniform("modelWorldMatrix");

        window.setInputProcessor(controller);
    }

    public void render(Window window, Camera camera, List<GameItem> gameItems, Skybox skybox) {
        clear();

        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        shaderProgram.bind();

        shaderProgram.setUniform("texture_sampler", 0);

        renderSkybox(skybox);
        // Update projection Matrix
        Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
        shaderProgram.setUniform("projectionMatrix", projectionMatrix);

        Matrix4f viewMatrix = transformation.getViewMatrix(camera);
        // Render each item
        for (GameItem gameItem : gameItems) {
            // Set world matrix for this item
            Matrix4f modelViewMatrix =
                    transformation.getModelView(gameItem, viewMatrix);
            shaderProgram.setUniform("modelWorldMatrix", modelViewMatrix);
            // Render the mes for this game item
            gameItem.getMesh().render();
        }

        shaderProgram.unbind();
    }

    private void renderSkybox(Skybox skybox) {
        Matrix4f tmpProjectionMatrix = transformation.getProjectionMatrix();
        shaderProgram.setUniform("projectionMatrix", tmpProjectionMatrix);
        Matrix4f tmpViewMatrix = transformation.getViewMatrix();
        tmpViewMatrix.m30(0);
        tmpViewMatrix.m31(0);
        tmpViewMatrix.m32(0);
        Matrix4f tmpModelViewMatrix = transformation.getModelView(skybox, tmpViewMatrix);
        shaderProgram.setUniform("modelWorldMatrix", tmpModelViewMatrix);
        skybox.getMesh().render();

    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void cleanup() {
        if (shaderProgram != null) {
            shaderProgram.cleanup();
        }
    }

    public void resize(Window window) throws Exception {
        if (shaderProgram != null) {
            float aspectRatio = (float) window.getWidth() / window.getHeight();
            Matrix4f projectionMatrix = new Matrix4f().perspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
            shaderProgram.setUniform("projectionMatrix", projectionMatrix);
        }
    }
}
