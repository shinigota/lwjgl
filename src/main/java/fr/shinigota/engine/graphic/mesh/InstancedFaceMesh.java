package fr.shinigota.engine.graphic.mesh;

import fr.shinigota.engine.graphic.texture.TextureRegion;

public class InstancedFaceMesh {
    public static InstancedMesh up(TextureRegion textureRegion) {
        // Up properties
        float ux = textureRegion.getX();
        float uy = textureRegion.getY();
        float uw = textureRegion.getWidth();
        float uh = textureRegion.getHeight();

        // Create the Mesh
        float[] positions = new float[] {
                // Verticies
                -0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
        };
        float[] textCoords = new float[]{
                // top
                ux,         uy,        // 8
                ux + uw,    uy,        // 9
                ux,         uy + uh,      // 10
                ux + uw,    uy + uh,      // 11
        };

        int[] indices = new int[]{
                0, 1, 3, 0, 2, 3,
        };
        return new InstancedMesh(positions, indices, textCoords, textureRegion.getTexture());
    }

    public static InstancedMesh down(TextureRegion textureRegion) {
        // Down properties
        float dx = textureRegion.getX();
        float dy = textureRegion.getY();
        float dw = textureRegion.getWidth();
        float dh = textureRegion.getHeight();

        // Create the Mesh
        float[] positions = new float[] {
                // Verticies
                -0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
        };
        float[] textCoords = new float[]{
                // top
                // bottom
                dx,         dy,
                dx + dw,    dy,
                dx,         dy + dh,
                dx + dw,    dy + dh,
        };

        int[] indices = new int[]{
                0, 2, 3, 1, 0, 3,
        };
        return new InstancedMesh(positions, indices, textCoords, textureRegion.getTexture());
    }

    public static InstancedMesh back(TextureRegion textureRegion) {
        // Back properties
        float bx = textureRegion.getX();
        float by = textureRegion.getY();
        float bw = textureRegion.getWidth();
        float bh = textureRegion.getHeight();

        // Create the Mesh
        float[] positions = new float[] {
                // Verticies
                -0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
        };
        float[] textCoords = new float[]{
                // back
                bx,         by,
                bx + bw,    by,
                bx,         by + bh,
                bx + bw,    by + bh,
        };

        int[] indices = new int[]{
                0, 2, 3, 1, 0, 3,
        };
        return new InstancedMesh(positions, indices, textCoords, textureRegion.getTexture());
    }

    public static InstancedMesh right(TextureRegion textureRegion) {
        // Right properties
        float rx = textureRegion.getX();
        float ry = textureRegion.getY();
        float rw = textureRegion.getWidth();
        float rh = textureRegion.getHeight();

        // Create the Mesh
        float[] positions = new float[] {
                // Verticies
                0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, -0.5f,
                0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, -0.5f,
        };
        float[] textCoords = new float[]{
                // right
                rx,         ry,
                rx + rw,    ry,
                rx,         ry + rh,
                rx + rw,    ry + rh,
        };

        int[] indices = new int[]{
                0, 2, 3, 1, 0, 3,
        };
        return new InstancedMesh(positions, indices, textCoords, textureRegion.getTexture());
    }

    public static InstancedMesh front(TextureRegion textureRegion) {
        // Front properties
        float fx = textureRegion.getX();
        float fy = textureRegion.getY();
        float fw = textureRegion.getWidth();
        float fh = textureRegion.getHeight();

        // Create the Mesh
        float[] positions = new float[] {
                // Verticies
                -0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                -0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
        };
        float[] textCoords = new float[]{
                // front
                fx,         fy,
                fx + fw,    fy,
                fx,         fy + fh,
                fx + fw,    fy + fh,
        };

        int[] indices = new int[]{
                0, 2, 3, 1, 0, 3,
        };
        return new InstancedMesh(positions, indices, textCoords, textureRegion.getTexture());
    }

    public static InstancedMesh left(TextureRegion textureRegion) {
        // Left properties
        float lx = textureRegion.getX();
        float ly = textureRegion.getY();
        float lw = textureRegion.getWidth();
        float lh = textureRegion.getHeight();

        // Create the Mesh
        float[] positions = new float[] {
                // Verticies
                -0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, -0.5f,
                -0.5f, -0.5f, 0.5f,
                -0.5f, -0.5f, -0.5f,
        };
        float[] textCoords = new float[]{
                // left
                lx,         ly,
                lx + lw,    ly,
                lx,         ly + lh,
                lx + lw,    ly + lh,
        };

        int[] indices = new int[]{
                0, 2, 3, 1, 0, 3,
        };
        return new InstancedMesh(positions, indices, textCoords, textureRegion.getTexture());
    }
}
