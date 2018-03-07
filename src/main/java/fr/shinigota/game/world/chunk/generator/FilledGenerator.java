package fr.shinigota.game.world.chunk.generator;

import fr.shinigota.game.world.chunk.Chunk;
import fr.shinigota.game.world.chunk.block.Block;
import fr.shinigota.game.world.chunk.block.BlockType;
import fr.shinigota.game.world.structure.AbstractStructure;
import fr.shinigota.game.world.structure.Tree;
import org.joml.Vector3i;

public class FilledGenerator implements IChunkGenerator {
    @Override
    public void generate(Chunk chunk) {
        for (int y = 0; y <= Chunk.CHUNK_HEIGHT; y++) {
            for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
                for (int z = 0; z < Chunk.CHUNK_SIZE; z++) {
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

                    Block block = new Block(blockType,chunk.getRealX() + x, y, chunk.getRealZ() + z);
                    chunk.getBlocksMap().put(new Vector3i(chunk.getRealX() + x, y, chunk.getRealZ() + z), block);

                    chunk.setNeighbours(block);
                }
            }
        }

        AbstractStructure tree = new Tree(chunk.getRealX() + 3, Chunk.SEA_LEVEL + 1, chunk.getRealZ() + 3 );
        tree.generate(chunk);
        chunk.requestUpdate();
    }
}
