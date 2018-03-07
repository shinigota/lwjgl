package fr.shinigota.game.world.chunk.generator;

import fr.shinigota.game.world.chunk.Chunk;
import fr.shinigota.game.world.chunk.block.Block;
import fr.shinigota.game.world.chunk.block.BlockType;
import org.joml.Vector3i;

public class PoolGenerator implements IChunkGenerator {
    @Override
    public void generate(Chunk chunk) {
        for (int y = 0; y <= Chunk.CHUNK_HEIGHT; y++) {
            for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
                for (int z = 0; z < Chunk.CHUNK_SIZE; z++) {
                    BlockType blockType = BlockType.DIRT;
                    if ( y == Chunk.SEA_LEVEL ) {
                        blockType = BlockType.GRASS;
                    }

                    if ( (0 < x && x < Chunk.CHUNK_SIZE - 1)
                            && (0 < z && z < Chunk.CHUNK_SIZE - 1)
                            && (y >= Chunk.SEA_LEVEL - 3)) {
                        blockType = BlockType.WATER;
                    }

                    if ( y > Chunk.SEA_LEVEL ) {
                        blockType = BlockType.AIR;
                    }


                    Block block = new Block(blockType,chunk.getRealX() + x, y, chunk.getRealZ() + z);
                    chunk.getBlocksMap().put(new Vector3i(chunk.getRealX() + x, y, chunk.getRealZ() + z), block);

                    chunk.setNeighbours(block);
                }
            }
        }

        chunk.requestUpdate();
    }
}
