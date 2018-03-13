package fr.shinigota.game.world.renderer;

import fr.shinigota.engine.graphic.entity.Entity;
import fr.shinigota.engine.graphic.mesh.InstancedMesh;
import fr.shinigota.engine.graphic.mesh.Mesh;
import fr.shinigota.game.world.BlockMeshLoader;
import fr.shinigota.game.world.World;
import fr.shinigota.game.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scene implements Renderable {
    private final World world;

    private final Map<Mesh, List<Entity>> opaqueMeshes;
    private final Map<Mesh, List<Entity>> transparentMeshes;

    private final Map<InstancedMesh, List<Entity>> instancedOpaqueMeshes;
    private final Map<InstancedMesh, List<Entity>> instancedTransparentMeshes;

    private final BlockMeshLoader blockMeshLoader;
    public Scene(World world, BlockMeshLoader blockMeshLoader) {
        this.world = world;
        this.blockMeshLoader = blockMeshLoader;

        opaqueMeshes = new HashMap<>();
        transparentMeshes = new HashMap<>();
        instancedOpaqueMeshes = new HashMap<>();
        instancedTransparentMeshes = new HashMap<>();
    }

    @Override
    public Map<Mesh, List<Entity>> getOpaqueMeshes() {
        if (!opaqueMeshes.isEmpty()) {
            return opaqueMeshes;
        }
        for(Chunk chunk : world.getChunks().values()) {
            for(Map.Entry<Mesh, List<Entity>> entry : chunk.
                    getRenderer()
                    .getOpaqueMeshes()
                    .entrySet()) {
                opaqueMeshes.putIfAbsent(entry.getKey(), new ArrayList<>());
                opaqueMeshes.get(entry.getKey()).addAll(entry.getValue());
            }
        }
        return opaqueMeshes;
    }

    @Override
    public Map<Mesh, List<Entity>> getTransparentMeshes() {
        if (!transparentMeshes.isEmpty()) {
            return transparentMeshes;
        }
        for(Chunk chunk : world.getChunks().values()) {
            for(Map.Entry<Mesh, List<Entity>> entry : chunk.getRenderer().getTransparentMeshes().entrySet()) {
                transparentMeshes.putIfAbsent(entry.getKey(), new ArrayList<>());
                transparentMeshes.get(entry.getKey()).addAll(entry.getValue());
            }
        }
        return transparentMeshes;
    }

    @Override
    public Map<InstancedMesh, List<Entity>> getInstancedOpaqueMeshes() {
        if (!instancedOpaqueMeshes.isEmpty()) {
            return instancedOpaqueMeshes;
        }
        for(Chunk chunk : world.getChunks().values()) {
            for(Map.Entry<InstancedMesh, List<Entity>> entry : chunk
                    .getRenderer()
                    .getInstancedOpaqueMeshes()
                    .entrySet()) {
                instancedOpaqueMeshes.putIfAbsent(entry.getKey(), new ArrayList<>());
                instancedOpaqueMeshes.get(entry.getKey()).addAll(entry.getValue());
            }
        }
        return instancedOpaqueMeshes;
    }

    @Override
    public Map<InstancedMesh, List<Entity>> getInstancedTransparentMeshes() {
        if (!instancedTransparentMeshes.isEmpty()) {
            return instancedTransparentMeshes;
        }
        for(Chunk chunk : world.getChunks().values()) {
            for(Map.Entry<InstancedMesh, List<Entity>> entry : chunk.getRenderer().getInstancedTransparentMeshes().entrySet()) {
                instancedTransparentMeshes.putIfAbsent(entry.getKey(), new ArrayList<>());
                instancedTransparentMeshes.get(entry.getKey()).addAll(entry.getValue());
            }
        }
        return instancedTransparentMeshes;
    }

    @Override
    public void cleanup() {
        blockMeshLoader.cleanup();
    }
}
