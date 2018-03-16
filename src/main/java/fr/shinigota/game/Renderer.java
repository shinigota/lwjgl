package fr.shinigota.game;

import fr.shinigota.engine.Utils;
import fr.shinigota.engine.Window;
import fr.shinigota.engine.graphic.entity.Entity;
import fr.shinigota.engine.graphic.Camera;
import fr.shinigota.engine.graphic.ShaderProgram;
import fr.shinigota.engine.graphic.Skybox;
import fr.shinigota.engine.graphic.Transformation;
import fr.shinigota.engine.graphic.entity.MeshEntity;
import fr.shinigota.engine.graphic.mesh.InstancedMesh;
import fr.shinigota.engine.graphic.mesh.Mesh;
import fr.shinigota.engine.tools.FrustumCullingFilter;
import fr.shinigota.game.world.renderer.Renderable;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
    private static final float FOV = (float) Math.toRadians(70.0f);
    private static final float Z_NEAR = 0.01f;
    private static final float Z_FAR = 1000.f;
    private final Transformation transformation;
    private final Controller controller;

    private ShaderProgram shaderProgram;

    private final FrustumCullingFilter frustumFilter;

    private final List<Entity> filteredItems;

    public Renderer(Controller controller) {
        transformation = new Transformation();
        this.controller = controller;
        frustumFilter = new FrustumCullingFilter();
        filteredItems = new ArrayList<>();
    }


    public void init(Window window) throws Exception {
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Utils.loadResource("/shaders/vertex.vs"));
        shaderProgram.createFragmentShader(Utils.loadResource("/shaders/fragment.fs"));
        shaderProgram.link();

        shaderProgram.createUniform("projectionMatrix");
        shaderProgram.createUniform("texture_sampler");
        shaderProgram.createUniform("modelWorldNonInstancedMatrix");
        shaderProgram.createUniform("isInstanced");

        window.setInputProcessor(controller);
    }

    public void render(Window window, Camera camera, Renderable scene, Skybox skybox) {
        clear();

        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }



        shaderProgram.bind();

        shaderProgram.setUniform("texture_sampler", 0);



        renderSkybox(skybox);
//         Update projection Matrix
        Matrix4f projectionMatrix = transformation.updateProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);

        shaderProgram.setUniform("projectionMatrix", projectionMatrix);

        Matrix4f viewMatrix = transformation.updateViewMatrix(camera);

        List<MeshEntity> sortedTransparentMeshes = scene.getSortedTransparentMeshes(camera.getPosition());

        frustumFilter.update(projectionMatrix, viewMatrix);
        frustumFilter.filter(scene.getInstancedOpaqueMeshes());
        frustumFilter.filter(scene.getOpaqueMeshes());
        frustumFilter.filter(scene.getInstancedTransparentMeshes());
        frustumFilter.filter(scene.getTransparentMeshes());
        frustumFilter.filter(sortedTransparentMeshes);

        renderInstancedMeshEntityList(scene.getInstancedOpaqueMeshes(), viewMatrix);
        renderMeshEntityList(scene.getOpaqueMeshes(), viewMatrix);
//        transparentMeshes.sort(new EntityDistanceComparator(camera.getPosition()));
//        renderInstancedMeshEntityList(scene.getInstancedTransparentMeshes(), viewMatrix);
        renderMeshEntityList(sortedTransparentMeshes, viewMatrix);

        shaderProgram.unbind();

    }

    private void renderMeshEntityList(Map<Mesh, List<Entity>> meshes, Matrix4f viewMatrix) {
        // Render each item
        shaderProgram.setUniform("isInstanced", 0);
        for (Map.Entry<Mesh, List<Entity>> mesh : meshes.entrySet()) {
            mesh.getKey().renderList(mesh.getValue(), (Entity entity) -> {
                Matrix4f modelViewMatrix = transformation.buildModelViewMatrix(entity, viewMatrix);
                shaderProgram.setUniform("modelWorldNonInstancedMatrix", modelViewMatrix);
            });
        }
    }

    private void renderMeshEntityList(List<MeshEntity> meshes, Matrix4f viewMatrix) {
        // Render each item
        shaderProgram.setUniform("isInstanced", 0);
        for (MeshEntity meshEntity : meshes) {
            if (meshEntity.entity.isInsideFrustum()) {
                shaderProgram.setUniform("modelWorldNonInstancedMatrix", transformation.buildModelViewMatrix(meshEntity.entity,
                        viewMatrix));
                meshEntity.mesh.render();
            }
        }
    }

    private void renderInstancedMeshEntityList(Map<InstancedMesh, List<Entity>> meshes, Matrix4f viewMatrix) {
        // Render each item
        shaderProgram.setUniform("isInstanced", 1);
        for (Map.Entry<InstancedMesh, List<Entity>> mesh : meshes.entrySet()) {
            filteredItems.clear();

            for(Entity entity : mesh.getValue()) {
                if (entity.isInsideFrustum()) {
                    filteredItems.add(entity);
                }
            }

            mesh.getKey().renderListInstanced(filteredItems, transformation, viewMatrix);
        }
    }

    private void renderSkybox(Skybox skybox) {
        Matrix4f tmpProjectionMatrix = transformation.getProjectionMatrix();
        shaderProgram.setUniform("isInstanced", 0);
        shaderProgram.setUniform("projectionMatrix", tmpProjectionMatrix);
        Matrix4f tmpViewMatrix = transformation.getViewMatrix();
        tmpViewMatrix.m30(0);
        tmpViewMatrix.m31(0);
        tmpViewMatrix.m32(0);
        Matrix4f tmpModelViewMatrix = transformation.buildModelViewMatrix(skybox, tmpViewMatrix);
        shaderProgram.setUniform("modelWorldNonInstancedMatrix", tmpModelViewMatrix);
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
