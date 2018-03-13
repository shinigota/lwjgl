package fr.shinigota.game.world.renderer;

import fr.shinigota.engine.graphic.entity.Entity;
import fr.shinigota.engine.graphic.mesh.InstancedMesh;
import fr.shinigota.engine.graphic.mesh.Mesh;

import java.util.List;
import java.util.Map;

public interface Renderable {

    Map<Mesh, List<Entity>> getOpaqueMeshes();
    Map<Mesh, List<Entity>> getTransparentMeshes();
    Map<InstancedMesh, List<Entity>>  getInstancedOpaqueMeshes();
    Map<InstancedMesh, List<Entity>> getInstancedTransparentMeshes();

    void cleanup();
}
