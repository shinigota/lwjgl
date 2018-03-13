package fr.shinigota.engine.graphic;

import fr.shinigota.engine.graphic.entity.Entity;
import fr.shinigota.engine.graphic.mesh.Mesh;
import fr.shinigota.engine.graphic.texture.CubeTexture;
import fr.shinigota.engine.graphic.entity.MeshEntity;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Skybox extends Entity {
    private Mesh mesh;


    public Skybox(CubeTexture skyboxTexture) {
        Mesh skyboxMesh = Mesh.FACTORY.cubeMesh(skyboxTexture);
        setPosition(0, 0, 0);
        setScale(1000f);

        setMesh(skyboxMesh);
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
