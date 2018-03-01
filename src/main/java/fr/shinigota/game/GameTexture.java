package fr.shinigota.game;

import fr.shinigota.engine.graphic.texture.Texture;
import fr.shinigota.engine.graphic.texture.TextureSheet;

import java.io.IOException;

public class GameTexture {
    private static final int TEXTURE_WIDTH = 512;
    private static final int TEXTURE_HEIGHT = 512;

    public final Texture GRASS;
    public final Texture DIRT;

    private final TextureSheet textureSheet;

    public GameTexture() throws IOException {
        textureSheet = new TextureSheet("/textures/grassblock.png");

        GRASS = new Texture(textureSheet,1024, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        DIRT = new Texture(textureSheet,0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT);
    }

    public void cleanup() {
        textureSheet.cleanup();
    }
}
