package fr.shinigota.game.world;

import fr.shinigota.engine.graphic.entity.Entity;
import fr.shinigota.engine.graphic.mesh.InstancedMesh;
import fr.shinigota.engine.graphic.mesh.Mesh;
import fr.shinigota.engine.graphic.texture.Texture;
import fr.shinigota.game.world.chunk.Chunk;
import fr.shinigota.game.world.chunk.generator.IChunkGenerator;
import fr.shinigota.game.world.chunk.generator.PerlinGenerator;
import fr.shinigota.game.world.renderer.Renderable;
import fr.shinigota.game.world.renderer.Scene;
import org.joml.Vector2i;
import org.joml.Vector2ic;

import java.io.IOException;
import java.util.*;

public class World {
    private final Map<Vector2ic, Chunk> chunks;

    private final Texture blockTextures;
    private final BlockMeshLoader blockMeshLoader;

    private final Renderable renderer;

    public World() throws IOException {
        chunks = new HashMap<>();

        blockTextures = new Texture("/textures/texturepack.png");
        blockMeshLoader = new BlockMeshLoader(blockTextures);

        renderer = new Scene(this, blockMeshLoader);

        int seed = new Random().nextInt();

        int size = 10;

        for(int x = 0; x < size; x++) {
            for (int z = 0; z < size; z++) {
                addChunk(x - size / 2, z - size / 2, new PerlinGenerator(seed));
            }
        }
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

    public void cleanup() {
        blockTextures.cleanup();
        renderer.cleanup();
        for(Chunk chunk : chunks.values()) {
            chunk.cleanup();
        }
    }

    public Map<Vector2ic, Chunk> getChunks() {
        return chunks;
    }

    public Renderable getRenderer() {
        return renderer;
    }
}
