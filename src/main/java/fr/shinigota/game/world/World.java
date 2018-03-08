package fr.shinigota.game.world;

import fr.shinigota.engine.graphic.entity.MeshEntity;
import fr.shinigota.engine.graphic.texture.Texture;
import fr.shinigota.game.world.chunk.Chunk;
import fr.shinigota.game.world.chunk.block.Block;
import fr.shinigota.game.world.chunk.generator.*;
import org.joml.Vector2i;
import org.joml.Vector2ic;

import java.io.IOException;
import java.util.*;

public class World {
    private final Map<Vector2ic, Chunk> chunks;
    private final List<Block> blocks;
    private final List<MeshEntity> meshes;
    private final List<MeshEntity> transparentMeshes;
    private final List<MeshEntity> allMeshes;
    private final Texture blockTextures;

    public World() throws IOException {
        blockTextures = new Texture("/textures/texturepack.png");

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


        blocks = new ArrayList<>();
        meshes = new ArrayList<>();
        transparentMeshes = new ArrayList<>();
        allMeshes = new ArrayList<>();
    }

    private void addChunk(int x, int z, IChunkGenerator generator) {
        Vector2i chunkPos = new Vector2i(x, z);
        Chunk chunk = new Chunk(x, z, generator);
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

    public List<MeshEntity> getMeshes() {
        if (!meshes.isEmpty()) {

            return meshes;
        }
        System.out.println(System.currentTimeMillis() + "Copy");
        for(Chunk chunk : chunks.values()) {
            meshes.addAll(chunk.getMeshes(blockTextures));
        }
        return meshes;
    }

    public List<MeshEntity> getTransparentMeshes() {
        if (!transparentMeshes.isEmpty()) {

            return transparentMeshes;
        }
        transparentMeshes.clear();
        for(Chunk chunk : chunks.values()) {
            transparentMeshes.addAll(chunk.getTransparentMeshes(blockTextures));
        }
        return transparentMeshes;
    }

    public void cleanup() {
        if (blockTextures != null) {
            blockTextures.cleanup();
        }
    }
}
