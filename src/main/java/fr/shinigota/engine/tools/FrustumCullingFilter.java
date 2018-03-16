package fr.shinigota.engine.tools;

import fr.shinigota.engine.graphic.entity.Entity;
import fr.shinigota.engine.graphic.entity.MeshEntity;
import fr.shinigota.engine.graphic.mesh.Mesh;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;
import java.util.Map;

public class FrustumCullingFilter {
    private static final int NUM_PLANES = 6;

    private final Matrix4f prjViewMatrix;
    private final Vector4f[] frustrumPlanes;

    public FrustumCullingFilter() {
        prjViewMatrix = new Matrix4f();
        frustrumPlanes = new Vector4f[NUM_PLANES];

        for (int i = 0; i < NUM_PLANES; i++) {
            frustrumPlanes[i] = new Vector4f();
        }
    }

    public void update(Matrix4f projMatrix, Matrix4f viewMatrix) {
        // Calculate projection view matrix
        prjViewMatrix.set(projMatrix);
        prjViewMatrix.mul(viewMatrix);

        // Get frustrum planes
        for (int i = 0; i < NUM_PLANES; i++) {
            prjViewMatrix.frustumPlane(i, frustrumPlanes[i]);
        }
    }

    public boolean isInside(float x, float y, float z, float radius) {
        for (int i = 0; i < NUM_PLANES; i++) {
            Vector4f plane = frustrumPlanes[i];
            if (plane.x * x + plane.y * y + plane.z * z + plane.w <= -radius) {
                return false;
            }
        }

        return true;
    }

    public void filter(List<Entity> entities, float meshBoudingRadius) {
        float boundingRadius;
        Vector3f pos;
        for (Entity entity : entities) {
            boundingRadius = entity.getScale() * meshBoudingRadius;
            pos = entity.getPosition();
            entity.setInsideFrustum(isInside(pos.x, pos.y, pos.z, boundingRadius));
        }
    }

    public void filter(Map<? extends Mesh, List<Entity>> mapEntity) {
        for (Map.Entry<? extends Mesh, List<Entity>> entry : mapEntity.entrySet()) {
            filter(entry.getValue(), entry.getKey().getBoundingRadius());
        }
    }

    public void filter(List<MeshEntity> transparentMeshes) {
        float boundingRadius;
        Vector3f pos;
        for (MeshEntity meshEntity : transparentMeshes) {
            boundingRadius = meshEntity.entity.getScale() * meshEntity.mesh.getBoundingRadius();
            pos = meshEntity.entity.getPosition();
            meshEntity.entity.setInsideFrustum(isInside(pos.x, pos.y, pos.z, boundingRadius));
        }
    }
}
