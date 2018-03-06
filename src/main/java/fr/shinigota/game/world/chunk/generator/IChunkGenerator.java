package fr.shinigota.game.world.chunk.generator;

import fr.shinigota.game.world.chunk.Chunk;
import fr.shinigota.game.world.chunk.block.Block;

import java.util.List;

public interface IChunkGenerator {
    void generate(Chunk chunk);
}
