package fr.shinigota.engine.graphic.entity;

import fr.shinigota.engine.graphic.mesh.Mesh;
import org.joml.Vector3f;

public class Entity {
    private final Vector3f position;
    private final Vector3f rotation;
    private float scale;

    public Entity() {
        this( 0, 0, 0);
    }

    public Entity(float x, float y, float z) {
        scale = 1;
        position = new Vector3f(x, y, z);
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
}
