package fr.shinigota.engine.graphic.texture;

public class CubeTexture {
    private final Texture texture;
    private final TextureRegion up;
    private final TextureRegion down;
    private final TextureRegion side;

    private CubeTexture(CubeTextureBuilder cubeTextureBuilder) {
        this.texture = cubeTextureBuilder.texture;
        this.up = cubeTextureBuilder.up;
        this.down = cubeTextureBuilder.down;
        this.side = cubeTextureBuilder.side;
    }

    public TextureRegion up() {
        return up;
    }

    public TextureRegion down() {
        return down;
    }

    public TextureRegion side() {
        return side;
    }

    public Texture getTexture() {
        return texture;
    }

    public static class CubeTextureBuilder {
        private final Texture texture;
        private final TextureRegion side;
        private TextureRegion up;
        private TextureRegion down;

        /**
         * Compulsory fields of a texture
         * @param texture the source texture containing each textureregion of the sides
         * @param dimension the width or height (same values expected) in px of the texture
         * @param x x position of the <b>side</b> texture in the texture sheet, in px, top left corner
         * @param y y position of the <b>side</b> texture in the texture sheet, in px, top left corner
         */
        public CubeTextureBuilder(Texture texture, int dimension, int x, int y) {
            this.texture = texture;
            this.side = texture.getTextureAt(x, y, dimension, dimension);
            this.up = this.side;
            this.down = this.side;
        }

        public CubeTextureBuilder up(int dimension, int x, int y) {
            this.up = texture.getTextureAt(x, y, dimension, dimension);
            return this;
        }

        public CubeTextureBuilder down(int dimension, int x, int y) {
            this.down = texture.getTextureAt(x, y, dimension, dimension);
            return this;
        }

        public CubeTexture build() {
            return new CubeTexture(this);
        }
    }
}
