package fr.shinigota.engine.graphic.texture;

import de.matthiasmann.twl.utils.PNGDecoder;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texture {
    private final int textureId;
    private final int width;
    private final int height;
    private final String fileName;

    public Texture(String fileName) throws IOException {
        this.fileName = fileName;

        // Decode the texture
        PNGDecoder decoder = new PNGDecoder(TextureRegion.class.getResourceAsStream(fileName));
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

        // Disable filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        // Upload decoded PNG in the texture
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL_RGBA,
                GL_UNSIGNED_BYTE, buffer);

        // Scale the texture
        glGenerateMipmap(GL_TEXTURE_2D);
    }

    public int getId() {
        return textureId;
    }

    public void cleanup() {
        glDeleteTextures(textureId);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public TextureRegion getTextureAt(int x, int y, int width, int height) {
        return new TextureRegion(this, x, y, width, height);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textureId, width, height, fileName);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (! (obj instanceof  Texture) ) {
            return false;
        }

        Texture other = (Texture) obj;
        if (fileName.equals(((Texture) obj).fileName)) {
            return false;
        }

        if (textureId != other.textureId) {
            return false;
        }

        return true;
    }
}
