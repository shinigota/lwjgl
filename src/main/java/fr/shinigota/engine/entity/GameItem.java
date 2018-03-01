package fr.shinigota.engine.entity;

import fr.shinigota.engine.graphic.Mesh;
import org.joml.Vector3f;

public class GameItem {
    private final Mesh mesh;
    private final Vector3f position;
    private final Vector3f rotation;
    private float scale;

    public GameItem(Mesh mesh) {
        this.mesh = mesh;
        position = new Vector3f(0, 0, 0);
        scale = 1;
        rotation = new Vector3f(0, 0, 0);
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

    public Mesh getMesh() {
        return mesh;
    }

    public void cleanup() {
        mesh.cleanup();
    }
}
