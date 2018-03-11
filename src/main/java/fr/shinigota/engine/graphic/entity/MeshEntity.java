package fr.shinigota.engine.graphic.entity;

import fr.shinigota.engine.graphic.mesh.Mesh;
import org.joml.Vector3f;

public class MeshEntity extends Entity {
    private Mesh mesh;

    public MeshEntity() {
        super();
    }

    public MeshEntity(Mesh mesh) {
        this(mesh, 0, 0, 0);
    }

    public MeshEntity(Mesh mesh, float x, float y, float z) {
        super(x, y, z);
        this.mesh = mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void cleanup() {
        mesh.cleanup();
    }
}
