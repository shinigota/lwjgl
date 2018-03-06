package fr.shinigota.engine.graphic.texture;

import static org.lwjgl.opengl.GL11.*;

public class TextureRegion {
    private final int width;
    private final int height;

    private Texture texture;
    private int x;
    private int y;

    public TextureRegion(Texture texture, int x, int y, int width, int height) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    public float getX() {
        return (float) x / texture.getWidth();
    }

    public float getY() {
        return (float) y / texture.getWidth();
    }

    public float getWidth() {
        return (float) width / texture.getWidth();
    }

    public float getHeight() {
        return (float) height / texture.getHeight();
    }

    public Texture getTexture() {
        return texture;
    }
}
