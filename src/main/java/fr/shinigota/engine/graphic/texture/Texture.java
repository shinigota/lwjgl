package fr.shinigota.engine.graphic.texture;

import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class Texture {
    private final TextureSheet textureSheet;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public Texture(TextureSheet textureSheet, int x, int y, int width, int height) throws IOException {
        this.textureSheet = textureSheet;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    public float getX() {
        return (float) x / textureSheet.getWidth();
    }

    public float getY() {
        return (float) y / textureSheet.getWidth();
    }

    public float getWidth() {
        return (float) width / textureSheet.getWidth();
    }

    public float getHeight() {
        return (float) height / textureSheet.getHeight();
    }

    public int getId() {
        return textureSheet.getId();
    }
}
