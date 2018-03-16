package fr.shinigota.game.world.chunk.generator;

import com.flowpowered.noise.module.source.Perlin;
import fr.shinigota.game.world.chunk.Chunk;
import fr.shinigota.game.world.chunk.block.Block;
import fr.shinigota.game.world.chunk.block.BlockType;
import fr.shinigota.game.world.structure.AbstractStructure;
import fr.shinigota.game.world.structure.Tree;
import org.joml.Vector3i;
import org.joml.Vector3ic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PerlinGenerator implements IChunkGenerator {
    private static final double FACTOR = 0.05;
    private final int seed;
    private final Perlin perlin;

    public PerlinGenerator(int seed) {
        this.seed = seed;
        perlin = new Perlin();
        perlin.setSeed(seed);
        perlin.setOctaveCount(2);
        perlin.setFrequency(0.3);
    }

    @Override
    public void generate(Chunk chunk) {

        for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
            for (int z = 0; z < Chunk.CHUNK_SIZE; z++) {
                int noise = (int) (perlin.getValue( (chunk.getRealX() + x) * FACTOR, 0, (chunk.getRealZ() + z) * FACTOR) *
                        Chunk.CHUNK_HEIGHT / 3);
                int actualHeight = Chunk.CHUNK_HEIGHT / 2 + noise;
                for (int y = 0; y <= Chunk.CHUNK_HEIGHT; y++) {

                    BlockType blockType = BlockType.AIR;

                    if (y  <= actualHeight) {
                        blockType = BlockType.GRASS;

                        if (y == actualHeight && y > Chunk.SEA_LEVEL) {

                        }

                        if(y < actualHeight) {
                            blockType = BlockType.DIRT;
                        }

                        if(y <= Chunk.SEA_LEVEL) {
                            blockType = BlockType.SAND;
                        }
                    }

                    Block block = new Block(blockType, chunk.getRealX() + x, y, chunk.getRealZ() + z);
                    chunk.addBlockAt(block, block.getX(), block.getY(), block.getZ(), y == actualHeight);
                }
            }
        }

        fillWater(chunk);
        populateSurface(chunk);
        chunk.requestUpdate();
    }

    private void populateSurface(Chunk chunk) {
        List<AbstractStructure> structures = new ArrayList<>();

        for(Map.Entry<Vector3ic, Block> entry : chunk.getSurfaceBlocks().entrySet()) {
            if (entry.getValue().getBlockType() == BlockType.GRASS) {
                int rnd = new Random().nextInt(70);
                if (rnd == 1) {
                    structures.add(new Tree(entry.getValue().getX(), entry.getValue().getY() + 1, entry.getValue().getZ
                            ()));
                }
            }
        }

        for (AbstractStructure structure : structures) {
            structure.generate(chunk);
        }

    }

    private void fillWater(Chunk chunk) {
        for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
            for (int z = 0; z < Chunk.CHUNK_SIZE; z++) {
                for (int y = 0; y <= Chunk.SEA_LEVEL; y++) {
                    Block blockAt = chunk.getBlockAt(chunk.getRealX() + x, y, chunk.getRealZ() + z);
                    if (blockAt == null) {
                        continue;
                    }

                    if (blockAt.getBlockType().equals(BlockType.AIR)) {
                        Block block = new Block(BlockType.WATER, chunk.getRealX() + x, y, chunk.getRealZ() + z);
                        chunk.getBlocksMap().put(new Vector3i(chunk.getRealX() + x, y, chunk.getRealZ() + z), block);
                        chunk.putBlockByType(BlockType.WATER, block);
                        chunk.setNeighbours(block);
                    }
                }
            }
        }
    }
}
