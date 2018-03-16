package fr.shinigota.engine.graphic.entity;

import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Entity {
    private final Vector3f position;
    private final Quaternionf rotation;
    private float scale;
    private boolean insideFrustum;

    public Entity() {
        this( 0, 0, 0);
    }

    public Entity(float x, float y, float z) {
        position = new Vector3f(x, y, z);
        rotation = new Quaternionf();
        scale = 1;
        insideFrustum = false;

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

    public Quaternionf getRotation() {
        return rotation;
    }

    public void setRotation(Quaternionf q) {
        rotation.set(q);
    }

    @Override
    public String toString() {
        return "Entity{" +
                "x=" + position.x +
                ", y=" + position.y +
                ", z=" + position.z +
                '}';
    }

    public void setInsideFrustum(boolean insideFrustum) {
        this.insideFrustum = insideFrustum;
    }

    public boolean isInsideFrustum() {
        return insideFrustum;
    }
}
