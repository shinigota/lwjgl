package fr.shinigota.game.world.chunk.generator;

import com.flowpowered.noise.module.source.Perlin;
import fr.shinigota.game.world.chunk.Chunk;
import fr.shinigota.game.world.chunk.block.Block;
import fr.shinigota.game.world.chunk.block.BlockType;
import org.joml.Vector3i;

public class FlatGenerator implements IChunkGenerator {
    @Override
    public void generate(Chunk chunk) {

        for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
            for (int z = 0; z < Chunk.CHUNK_SIZE; z++) {
                for (int y = 0; y <= Chunk.CHUNK_HEIGHT; y++) {

                    BlockType blockType = BlockType.DIRT;

                    Block block = new Block(blockType, chunk.getRealX() + x, y, chunk.getRealZ() + z);
                    chunk.getBlocksMap().put(new Vector3i(chunk.getRealX() + x, y, chunk.getRealZ() + z), block);
                    chunk.putBlockByType(blockType, block);
                    chunk.setNeighbours(block);
                }
            }
        }
        chunk.requestUpdate();
    }

}
