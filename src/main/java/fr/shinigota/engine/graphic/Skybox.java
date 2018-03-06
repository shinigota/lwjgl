package fr.shinigota.engine.graphic;

import fr.shinigota.engine.graphic.mesh.Mesh;
import fr.shinigota.engine.graphic.texture.CubeTexture;
import fr.shinigota.engine.graphic.entity.MeshEntity;
import org.joml.Vector3f;

public class Skybox extends MeshEntity {
    private final Vector3f position;
    private final Vector3f rotation;
    private float scale;
    private Mesh mesh;


    public Skybox(CubeTexture skyboxTexture) {
        position = new Vector3f(0, 0, 0);
        scale = 1;
        rotation = new Vector3f(0, 0, 0);

        Mesh skyboxMesh = Mesh.FACTORY.cubeMesh(skyboxTexture);
        setPosition(0, 0, 0);
        setScale(50f);

        setMesh(skyboxMesh);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        rotation.x = x;
        rotation.y = y;
        rotation.z = z;
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
