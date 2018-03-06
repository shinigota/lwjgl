package fr.shinigota.game.world;

import fr.shinigota.engine.graphic.entity.MeshEntity;
import fr.shinigota.engine.graphic.texture.Texture;
import fr.shinigota.game.world.chunk.Chunk;
import fr.shinigota.game.world.chunk.block.Block;
import fr.shinigota.game.world.chunk.block.BlockMesh;
import fr.shinigota.game.world.chunk.generator.CheckerboardGenerator;
import fr.shinigota.game.world.chunk.generator.DiagonalStaircaseGenerator;
import fr.shinigota.game.world.chunk.generator.FilledGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class World {
    private final List<Chunk> chunks;
    private final List<Block> blocks;
    private final List<MeshEntity> meshes;
//    private final BlockMesh blockMesh;
    private final Texture blockTextures;

    public World() throws IOException {
        blockTextures = new Texture("/textures/texturepack.png");

        chunks = new ArrayList<>();

        chunks.add(new Chunk(-1, 0, new DiagonalStaircaseGenerator()));
        chunks.add(new Chunk(0, 0, new FilledGenerator()));
        chunks.add(new Chunk(1, 0, new CheckerboardGenerator()));

        blocks = new ArrayList<>();
        meshes = new ArrayList<>();
    }

    public void generate() {
        for(Chunk chunk : chunks) {
            chunk.generate();
        }
    }
    public List<Block> getBlocks () {
        if (!blocks.isEmpty()) {
            return blocks;
        }
        blocks.clear();
        for(Chunk chunk : chunks) {
            blocks.addAll(chunk.getBlocks());
        }
        return blocks;
    }

    public List<MeshEntity> getMeshes() {
        if (!meshes.isEmpty()) {
            return meshes;
        }
        meshes.clear();
        for(Chunk chunk : chunks) {
            meshes.addAll(chunk.getMeshes(blockTextures));
        }
        return meshes;
    }

    public void cleanup() {
        if (blockTextures != null) {
            blockTextures.cleanup();
        }
    }
}
