package fr.shinigota.game.world.chunk.block;

public class Block {
    private final BlockType blockType;
    private final BlockVisibilityController visibilityController;
    private final int x;
    private final int y;
    private final int z;

    public Block(BlockType blockType, int x, int y, int z) {
        this.blockType = blockType;
        visibilityController = new BlockVisibilityController(this);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public BlockVisibilityController getVisibilityController() {
        return visibilityController;
    }
}
