package fr.shinigota.engine.graphic.mesh.comparator;

import fr.shinigota.engine.graphic.entity.Entity;
import fr.shinigota.engine.graphic.entity.MeshEntity;
import org.joml.Vector3f;

import java.util.Comparator;

public class EntityDistanceComparator implements Comparator<Entity> {
    private final Vector3f origin;

    public EntityDistanceComparator(Vector3f origin) {
        this.origin = origin;
    }

    @Override
    public int compare(Entity m1, Entity m2) {
        // distanceSquared to ensure testing with absolute values
        return Float.compare(origin.distanceSquared(m2.getPosition()), origin.distanceSquared(m1.getPosition()));
    }
}
