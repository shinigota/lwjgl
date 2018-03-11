package fr.shinigota.game.world.chunk.generator;

import com.flowpowered.noise.module.source.Perlin;
import fr.shinigota.game.world.chunk.Chunk;
import fr.shinigota.game.world.chunk.block.Block;
import fr.shinigota.game.world.chunk.block.BlockType;
import org.joml.Vector3i;

public class PerlinGenerator implements IChunkGenerator {
    private static final double FACTOR = 0.05;
    private final int seed;
    private final Perlin perlin;

    public PerlinGenerator(int seed) {
        this.seed = seed;
        perlin = new Perlin();
        perlin.setSeed(seed);
        perlin.setOctaveCount(1);
        perlin.setFrequency(0.1);
    }

    @Override
    public void generate(Chunk chunk) {

        for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
            for (int z = 0; z < Chunk.CHUNK_SIZE; z++) {
                int noise = (int) (perlin.getValue( (chunk.getRealX() + x) * FACTOR, 0, (chunk.getRealZ() + z) * FACTOR) *
                        Chunk.CHUNK_HEIGHT / 2);
                int actualHeight = Chunk.CHUNK_HEIGHT / 2 + noise;
                for (int y = 0; y <= Chunk.CHUNK_HEIGHT; y++) {

                    BlockType blockType = BlockType.AIR;

                    if (y  <= actualHeight) {
                        blockType = BlockType.GRASS;

                        if(y < actualHeight) {
                            blockType = BlockType.DIRT;
                        }

                        if(y <= Chunk.SEA_LEVEL) {
                            blockType = BlockType.SAND;
                        }
                    }

                    Block block = new Block(blockType, chunk.getRealX() + x, y, chunk.getRealZ() + z);
                    chunk.getBlocksMap().put(new Vector3i(chunk.getRealX() + x, y, chunk.getRealZ() + z), block);
                    chunk.putBlockByType(blockType, block);

                    chunk.setNeighbours(block);
                }
            }
        }

        fillWater(chunk);
        chunk.requestUpdate();
    }

    public void fillWater(Chunk chunk) {
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

                        chunk.setNeighbours(block);
                    }
                }
            }
        }
    }
}
