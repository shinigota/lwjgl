package fr.shinigota.game.world.chunk.block;

import fr.shinigota.engine.graphic.texture.CubeTexture;
import fr.shinigota.engine.graphic.texture.Texture;

public enum BlockType {
    AIR(BlockAlpha.TRANSPARENT),
    WATER(BlockAlpha.SEMI_TRANSPARENT, 2, 1),

    DIRT(BlockAlpha.OPAQUE, 1, 0),
    GRASS(BlockAlpha.OPAQUE, 0, 0, 1, 0, 2, 0),
    SAND(BlockAlpha.OPAQUE, 1, 1),
    STONE(BlockAlpha.OPAQUE, 0, 1);


    public static final int TEXTURE_DIMENSION = 16;
    public BlockFace up;
    public BlockFace down;
    public BlockFace side;
    public BlockAlpha type;

    BlockType(BlockAlpha type) {
        this.type = type;
    }

    BlockType(BlockAlpha type, int x, int y) {
        this(type, x, y, x, y);
    }

    BlockType(BlockAlpha type, int xUp, int yUp, int xSide, int ySide) {
        this(type, xUp, yUp, xSide, ySide, xUp, yUp);
    }

    BlockType(BlockAlpha type, int xUp, int yUp, int xDown, int yDown, int xSide, int ySide) {
        this.type = type;
        up = new BlockFace(xUp, yUp);
        down = new BlockFace(xDown, yDown);
        side = new BlockFace(xSide, ySide);
    }

    public CubeTexture getCubeTexture(Texture texture) {
        //  TODO : Throw exception if BlockFace is null (AIR block)
        return new CubeTexture
                .CubeTextureBuilder(texture, TEXTURE_DIMENSION, side.x * TEXTURE_DIMENSION, side.y *
                        TEXTURE_DIMENSION)
                .up(TEXTURE_DIMENSION, up.x * TEXTURE_DIMENSION, up.y * TEXTURE_DIMENSION)
                .down(TEXTURE_DIMENSION, down.x * TEXTURE_DIMENSION, down.y * TEXTURE_DIMENSION)
                .build();
    }

    public boolean isVisible()  {
        return type == BlockAlpha.TRANSPARENT || type == BlockAlpha.SEMI_TRANSPARENT;
    }

    public boolean isTransparent()  {
        return type == BlockAlpha.TRANSPARENT;
    }

    public boolean isSemiTransparent()  {
        return type == BlockAlpha.SEMI_TRANSPARENT;
    }

    public boolean isOpaque()  {
        return type == BlockAlpha.OPAQUE;
    }
}
