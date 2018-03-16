package fr.shinigota.engine.graphic.entity;

import fr.shinigota.engine.graphic.mesh.Mesh;
import org.joml.Vector3f;

public class MeshEntity {
    public final Mesh mesh;
    public final Entity entity;

    public MeshEntity(Mesh mesh, Entity entity) {
        this.mesh = mesh;
        this.entity = entity;
    }
}
