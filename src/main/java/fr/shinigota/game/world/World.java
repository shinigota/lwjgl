package fr.shinigota.game.world;

import fr.shinigota.engine.graphic.entity.Entity;
import fr.shinigota.engine.graphic.mesh.InstancedMesh;
import fr.shinigota.engine.graphic.mesh.Mesh;
import fr.shinigota.engine.graphic.texture.Texture;
import fr.shinigota.game.world.chunk.Chunk;
import fr.shinigota.game.world.chunk.generator.IChunkGenerator;
import fr.shinigota.game.world.chunk.generator.PerlinGenerator;
import org.joml.Vector2i;
import org.joml.Vector2ic;

import java.io.IOException;
import java.util.*;

public class World {
    private final Map<Vector2ic, Chunk> chunks;

    private final Map<Mesh, List<Entity>> opaqueMeshes;
    private final Map<Mesh, List<Entity>> transparentMeshes;

    private final Map<InstancedMesh, List<Entity>> instancedOpaqueMeshes;
    private final Map<InstancedMesh, List<Entity>> instancedTransparentMeshes;

    private final Texture blockTextures;
    private final BlockMeshLoader blockMeshLoader;

    public World() throws IOException {
        blockTextures = new Texture("/textures/texturepack.png");
        blockMeshLoader = new BlockMeshLoader(blockTextures);

        chunks = new HashMap<>();
        int seed = new Random().nextInt();
        addChunk(-1, 0, new PerlinGenerator(seed));
        addChunk(0, 0, new PerlinGenerator(seed));
        addChunk(1, 0, new PerlinGenerator(seed));
        addChunk(-1, 1, new PerlinGenerator(seed));
        addChunk(0, 1, new PerlinGenerator(seed));
        addChunk(1, 1, new PerlinGenerator(seed));
        addChunk(-1, 2, new PerlinGenerator(seed));
        addChunk(0, 2, new PerlinGenerator(seed));
        addChunk(1, 2, new PerlinGenerator(seed));


        opaqueMeshes = new HashMap<>();
        transparentMeshes = new HashMap<>();
        instancedOpaqueMeshes = new HashMap<>();
        instancedTransparentMeshes = new HashMap<>();
    }

    private void addChunk(int x, int z, IChunkGenerator generator) {
        Vector2i chunkPos = new Vector2i(x, z);
        Chunk chunk = new Chunk(x, z, generator, blockMeshLoader);
        chunks.put(chunkPos, chunk);

        setNeighbours(chunk);
    }

    private void setNeighbours(Chunk chunk) {
        Vector2ic front = new Vector2i(chunk.getX(), chunk.getZ() + 1);
        if(chunks.containsKey(front)) {
            chunk.getController().setFront(chunks.get(front));
        }

        Vector2ic back = new Vector2i(chunk.getX(), chunk.getZ() - 1);
        if(chunks.containsKey(back)) {
            chunk.getController().setBack(chunks.get(back));
        }

        Vector2ic left = new Vector2i(chunk.getX() - 1, chunk.getZ());
        if(chunks.containsKey(left)) {
            chunk.getController().setLeft(chunks.get(left));
        }

        Vector2ic right = new Vector2i(chunk.getX() + 1,  chunk.getZ());
        if(chunks.containsKey(right)) {
            chunk.getController().setRight(chunks.get(right));
        }
    }

    public void generate() {
        for(Chunk chunk : chunks.values()) {
            chunk.generate();
        }
    }

    public Map<Mesh, List<Entity>>  getOpaqueMeshes() {
        if (!opaqueMeshes.isEmpty()) {
            return opaqueMeshes;
        }
        for(Chunk chunk : chunks.values()) {
            for(Map.Entry<Mesh, List<Entity>> entry : chunk.getOpaqueMeshes().entrySet()) {
                opaqueMeshes.putIfAbsent(entry.getKey(), new ArrayList<>());
                opaqueMeshes.get(entry.getKey()).addAll(entry.getValue());
            }
        }
        return opaqueMeshes;
    }

    public Map<Mesh, List<Entity>> getTransparentMeshes() {
        if (!transparentMeshes.isEmpty()) {
            return transparentMeshes;
        }
        for(Chunk chunk : chunks.values()) {
            for(Map.Entry<Mesh, List<Entity>> entry : chunk.getTransparentMeshes().entrySet()) {
                transparentMeshes.putIfAbsent(entry.getKey(), new ArrayList<>());
                transparentMeshes.get(entry.getKey()).addAll(entry.getValue());
            }
        }
        return transparentMeshes;
    }

    public Map<InstancedMesh, List<Entity>>  getInstancedOpaqueMeshes() {
        if (!instancedOpaqueMeshes.isEmpty()) {
            return instancedOpaqueMeshes;
        }
        for(Chunk chunk : chunks.values()) {
            for(Map.Entry<InstancedMesh, List<Entity>> entry : chunk.getInstancedOpaqueMeshes().entrySet()) {
                instancedOpaqueMeshes.putIfAbsent(entry.getKey(), new ArrayList<>());
                instancedOpaqueMeshes.get(entry.getKey()).addAll(entry.getValue());
            }
        }
        return instancedOpaqueMeshes;
    }

    public Map<InstancedMesh, List<Entity>> getInstancedTransparentMeshes() {
        if (!instancedTransparentMeshes.isEmpty()) {
            return instancedTransparentMeshes;
        }
        for(Chunk chunk : chunks.values()) {
            for(Map.Entry<InstancedMesh, List<Entity>> entry : chunk.getInstancedTransparentMeshes().entrySet()) {
                instancedTransparentMeshes.putIfAbsent(entry.getKey(), new ArrayList<>());
                instancedTransparentMeshes.get(entry.getKey()).addAll(entry.getValue());
            }
        }
        return instancedTransparentMeshes;
    }

    public void cleanup() {
        blockTextures.cleanup();
        blockMeshLoader.cleanup();
    }
}
