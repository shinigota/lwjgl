package fr.shinigota.engine.graphic;

import fr.shinigota.engine.graphic.entity.Entity;
import fr.shinigota.engine.graphic.entity.MeshEntity;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Transformation {

    private final Matrix4f projectionMatrix;

    private final Matrix4f modelMatrix;

    private final Matrix4f modelViewMatrix;

    private final Matrix4f viewMatrix;

    public Transformation() {
        projectionMatrix = new Matrix4f();
        modelMatrix = new Matrix4f();
        modelViewMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Matrix4f updateProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
        float aspectRatio = width / height;
        return projectionMatrix.setPerspective(fov, aspectRatio, zNear, zFar);
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public Matrix4f updateViewMatrix(Camera camera) {
        return updateGenericViewMatrix(camera.getPosition(), camera.getRotation(), viewMatrix);
    }

    private Matrix4f updateGenericViewMatrix(Vector3f position, Vector3f rotation, Matrix4f matrix) {
        // First do the rotation so camera rotates over its position
        return matrix.rotationX((float)Math.toRadians(rotation.x))
                .rotateY((float)Math.toRadians(rotation.y))
                .translate(-position.x, -position.y, -position.z);
    }

    public Matrix4f buildModelMatrix(Entity entity) {
        Quaternionf rotation = entity.getRotation();
        return modelMatrix.translationRotateScale(
                entity.getPosition().x, entity.getPosition().y, entity.getPosition().z,
                rotation.x, rotation.y, rotation.z, rotation.w,
                entity.getScale(), entity.getScale(), entity.getScale());
    }

    public Matrix4f buildModelViewMatrix(Entity Entity, Matrix4f viewMatrix) {
        return buildModelViewMatrix(buildModelMatrix(Entity), viewMatrix);
    }

    public Matrix4f buildModelViewMatrix(Matrix4f modelMatrix, Matrix4f viewMatrix) {
        return viewMatrix.mulAffine(modelMatrix, modelViewMatrix);
    }
}
