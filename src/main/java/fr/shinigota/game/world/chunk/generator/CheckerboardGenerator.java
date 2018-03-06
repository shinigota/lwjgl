package fr.shinigota.game.world.chunk.generator;

import fr.shinigota.game.world.chunk.Chunk;
import fr.shinigota.game.world.chunk.block.Block;
import fr.shinigota.game.world.chunk.block.BlockType;
import org.joml.Vector3i;

public class CheckerboardGenerator implements IChunkGenerator {
    @Override
    public void generate(Chunk chunk) {
        for (int y = 0; y <= Chunk.CHUNK_HEIGHT; y++) {
            for (int x = y%2; x < Chunk.CHUNK_SIZE; x+=2) {
                for (int z = y%2; z < Chunk.CHUNK_SIZE; z+=2) {
                    BlockType blockType;
                    if (0 <= y && y < Chunk.SEA_LEVEL / 2) {
                        blockType = BlockType.STONE;
                    } else if ( y == Chunk.SEA_LEVEL / 2 ) {
                        blockType = BlockType.SAND;
                    } else if ( Chunk.SEA_LEVEL / 2 < y && y < Chunk.SEA_LEVEL ) {
                        blockType = BlockType.DIRT;
                    } else if ( y == Chunk.SEA_LEVEL ) {
                        blockType = BlockType.GRASS;
                    } else {
                        blockType = BlockType.AIR;
                    }

                    Block block = new Block(blockType,chunk.getX() + x, y, chunk.getZ() + z);
                    chunk.getBlocksMap().put(new Vector3i(block.getX(), block.getY(), block.getZ()), block);

                    chunk.setNeighbours(block);
                }
            }
        }

        chunk.requestUpdate();
    }
}
