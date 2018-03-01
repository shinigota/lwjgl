package fr.shinigota.engine.graphic;

import fr.shinigota.engine.graphic.texture.Texture;

public class MeshFactory {

    public Mesh cubeMesh(Texture texture) {
        float tx = texture.getX();
        float ty = texture.getY();
        float tw = texture.getWidth();
        float th = texture.getHeight();

        // Create the Mesh
        float[] positions = new float[] {
                // V0
                -0.5f, 0.5f, 0.5f,
                // V1
                -0.5f, -0.5f, 0.5f,
                // V2
                0.5f, -0.5f, 0.5f,
                // V3
                0.5f, 0.5f, 0.5f,
                // V4
                -0.5f, 0.5f, -0.5f,
                // V5
                0.5f, 0.5f, -0.5f,
                // V6
                -0.5f, -0.5f, -0.5f,
                // V7
                0.5f, -0.5f, -0.5f,

                // For text coords in top face
                // V8: V4 repeated
                -0.5f, 0.5f, -0.5f,
                // V9: V5 repeated
                0.5f, 0.5f, -0.5f,
                // V10: V0 repeated
                -0.5f, 0.5f, 0.5f,
                // V11: V3 repeated
                0.5f, 0.5f, 0.5f,

                // For text coords in right face
                // V12: V3 repeated
                0.5f, 0.5f, 0.5f,
                // V13: V2 repeated
                0.5f, -0.5f, 0.5f,

                // For text coords in left face
                // V14: V0 repeated
                -0.5f, 0.5f, 0.5f,
                // V15: V1 repeated
                -0.5f, -0.5f, 0.5f,

                // For text coords in bottom face
                // V16: V6 repeated
                -0.5f, -0.5f, -0.5f,
                // V17: V7 repeated
                0.5f, -0.5f, -0.5f,
                // V18: V1 repeated
                -0.5f, -0.5f, 0.5f,
                // V19: V2 repeated
                0.5f, -0.5f, 0.5f,
        };
        float[] textCoords = new float[]{
                tx,         ty,
                tx,         ty + th,
                tx + tw,    ty + th,
                tx + tw,    ty,

                tx,         ty,
                tx + tw,    ty,
                tx,         ty + th,
                tx + tw,    ty + th,

                // For text coords in top face
                tx,         ty + th,
                tx + tw,    ty + th,
                tx,         ty + 2*th,
                tx + tw,    ty + 2*th,

                // For text coords in right face
                tx,         ty,
                tx,         ty + th,

                // For text coords in left face
                tx + tw,    ty,
                tx + tw,    ty + th,

                // For text coords in bottom face
                tx + tw,    ty,
                tx + 2*tw,  ty,
                tx + tw,    ty + th,
                tx + 2*tw,  ty + th,
        };

        int[] indices = new int[]{
                // Front face
                0, 1, 3, 3, 1, 2,
                // Top Face
                8, 10, 11, 9, 8, 11,
                // Right face
                12, 13, 7, 5, 12, 7,
                // Left face
                14, 15, 6, 4, 14, 6,
                // Bottom face
                16, 18, 19, 17, 16, 19,
                // Back face
                4, 6, 7, 5, 4, 7,
        };
        return new Mesh(positions, indices, textCoords, texture);
    }
}
