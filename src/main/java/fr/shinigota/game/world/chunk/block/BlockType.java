package fr.shinigota.game.world.chunk.block;

import fr.shinigota.engine.graphic.texture.CubeTexture;
import fr.shinigota.engine.graphic.texture.Texture;

public enum BlockType {
    AIR(MaterialType.NONE, BlockAlpha.TRANSPARENT),
    WATER(MaterialType.LIQUID, BlockAlpha.SEMI_TRANSPARENT, 2, 1),

    DIRT(MaterialType.SOLID, BlockAlpha.OPAQUE, 1, 0),
    GRASS(MaterialType.SOLID, BlockAlpha.OPAQUE, 0, 0, 1, 0, 2, 0),
    SAND(MaterialType.SOLID, BlockAlpha.OPAQUE, 1, 1),
    STONE(MaterialType.SOLID, BlockAlpha.OPAQUE, 0, 1),
    TRUNK(MaterialType.SOLID, BlockAlpha.OPAQUE, 1, 2, 0, 2),
    LEAVES(MaterialType.SOLID, BlockAlpha.SEMI_TRANSPARENT, 2, 2);


    public static final int TEXTURE_DIMENSION = 16;
    public MaterialType materialType;
    public BlockFace up;
    public BlockFace down;
    public BlockFace side;
    public BlockAlpha type;

    BlockType(MaterialType materialType, BlockAlpha type) {
        this.materialType = materialType;
        this.type = type;
    }

    BlockType(MaterialType materialType, BlockAlpha type, int x, int y) {
        this(materialType, type, x, y, x, y);
    }

    BlockType(MaterialType materialType, BlockAlpha type, int xUp, int yUp, int xSide, int ySide) {
        this(materialType, type, xUp, yUp, xUp, yUp, xSide, ySide);
    }

    BlockType(MaterialType materialType, BlockAlpha type, int xUp, int yUp, int xDown, int yDown, int xSide, int ySide) {
        this.materialType = materialType;
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

    public boolean isSolid() {
        return materialType == MaterialType.SOLID;
    }

    public boolean isLiquid() {
        return materialType == MaterialType.LIQUID;
    }
}
