package fr.shinigota.game;

import fr.shinigota.engine.graphic.texture.Texture;
import fr.shinigota.engine.graphic.texture.TextureSheet;

import java.io.IOException;

public class GameTexture {
    private static final int TEXTURE_WIDTH = 32;
    private static final int TEXTURE_HEIGHT = 32;

    public final Texture grass;
    public final Texture dirt;
    public final Texture sky;

    private final TextureSheet textureSheet;

    public GameTexture() throws IOException {
        textureSheet = new TextureSheet("/textures/spritesheet_low.png");

        grass = new Texture(textureSheet,64, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        dirt = new Texture(textureSheet,0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT);
        sky = new Texture(textureSheet,0, 64, TEXTURE_WIDTH, TEXTURE_HEIGHT);
    }

    public void cleanup() {
        textureSheet.cleanup();
    }
}
