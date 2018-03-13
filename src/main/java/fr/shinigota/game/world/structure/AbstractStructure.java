package fr.shinigota.game.world.structure;

import fr.shinigota.game.world.chunk.Chunk;
import fr.shinigota.game.world.chunk.block.Block;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractStructure {
    public final List<Block> blocks;
    public final int x;
    public final int y;
    public final int z;

    public AbstractStructure(int x, int y, int z) {
        blocks = new ArrayList<>();
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void generate(Chunk chunk) {
        if (!chunk.isInside(new Vector3i(x, y, z))) {
            throw new UnsupportedOperationException("Structure outside of chunk");
        }

        generateStructure(x, y, z);

        for (Block block : blocks) {
            chunk.addBlockAt(block, block.getX(), block.getY(), block.getZ(), true);
        }
    }

    protected abstract void generateStructure(int x, int y, int z);
}

