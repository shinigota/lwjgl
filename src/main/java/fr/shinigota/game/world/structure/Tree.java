package fr.shinigota.game.world.structure;

import fr.shinigota.game.world.chunk.block.Block;
import fr.shinigota.game.world.chunk.block.BlockType;

public class Tree extends AbstractStructure{
    public Tree(int x, int y, int z) {
        super(x, y, z);
    }

    @Override
    public void generateStructure(int x, int y, int z) {
        blocks.add(new Block(BlockType.TRUNK, x, y, z));
        blocks.add(new Block(BlockType.TRUNK, x, y + 1, z));
        blocks.add(new Block(BlockType.TRUNK, x, y + 2, z));
        blocks.add(new Block(BlockType.TRUNK, x, y + 3, z));
        blocks.add(new Block(BlockType.LEAVES, x - 1, y + 3, z - 1));
        blocks.add(new Block(BlockType.LEAVES, x - 1, y + 3, z));
        blocks.add(new Block(BlockType.LEAVES, x - 1, y + 3, z + 1));
        blocks.add(new Block(BlockType.LEAVES, x, y + 3, z - 1));
        blocks.add(new Block(BlockType.LEAVES, x, y + 3, z + 1));
        blocks.add(new Block(BlockType.LEAVES, x + 1, y + 3, z - 1));
        blocks.add(new Block(BlockType.LEAVES, x + 1, y + 3, z));
        blocks.add(new Block(BlockType.LEAVES, x + 1, y + 3, z + 1));
        blocks.add(new Block(BlockType.LEAVES, x, y + 4, z));
    }


}
