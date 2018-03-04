package fr.shinigota.engine.graphic.texture;

import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class Texture {
    private final int width;
    private final int height;

    private TextureSheet textureSheet;
    private int x;
    private int y;

    private int textureId;

    public Texture(TextureSheet textureSheet, int x, int y, int width, int height) {
        this.textureSheet = textureSheet;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Texture(String fileName) throws IOException {
        // Decode the texture
        PNGDecoder decoder = new PNGDecoder(Texture.class.getResourceAsStream(fileName));
        ByteBuffer buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
        decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
        buffer.flip();

        width = decoder.getWidth();
        height = decoder.getHeight();

        // Create the OpenGL texture
        textureId = glGenTextures();

        // Bind the texture
        glBindTexture(GL_TEXTURE_2D, textureId);

        // Unpack the texture
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

//        // Texture filters
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        // Upload decoded PNG in the texture
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA,
                GL_UNSIGNED_BYTE, buffer);

        // Scale the texture
        glGenerateMipmap(GL_TEXTURE_2D);
    }

    public float getX() {
        if (textureSheet == null) {
            return 0;
        }
        return (float) x / textureSheet.getWidth();
    }

    public float getY() {
        if (textureSheet == null) {
            return 0;
        }
        return (float) y / textureSheet.getWidth();
    }

    public float getWidth() {
        if (textureSheet == null) {
            return width;
        }
        return (float) width / textureSheet.getWidth();
    }

    public float getHeight() {
        if (textureSheet == null) {
            return height;
        }
        return (float) height / textureSheet.getHeight();
    }

    public int getId() {
        if (textureSheet == null) {
            return textureId;
        }
        return textureSheet.getId();
    }

    public void cleanup()  {
        if (textureSheet == null) {
            return;
        }
        glDeleteTextures(textureId);
    }
}
