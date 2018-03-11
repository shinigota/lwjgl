package fr.shinigota.engine.graphic.mesh;

import fr.shinigota.engine.graphic.texture.CubeTexture;

public class MeshFactory {

    public static Mesh cubeMesh(CubeTexture cubeTexture) {

        // Side properties
        float sx = cubeTexture.side().getX();
        float sy = cubeTexture.side().getY();
        float sw = cubeTexture.side().getWidth();
        float sh = cubeTexture.side().getHeight();

        // Up properties
        float ux = cubeTexture.up().getX();
        float uy = cubeTexture.up().getY();
        float uw = cubeTexture.up().getWidth();
        float uh = cubeTexture.up().getHeight();

        // Down properties
        float dx = cubeTexture.down().getX();
        float dy = cubeTexture.down().getY();
        float dw = cubeTexture.down().getWidth();
        float dh = cubeTexture.down().getHeight();

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
                // front
                sx,         sy,             // 0
                sx,         sy + sh,        // 1
                sx + sw,    sy + sh,        // 2
                sx + sw,    sy,             // 3

                // back
                sx,         sy,             // 4
                sx + sw,    sy,             // 5
                sx,         sy + sh,        // 6
                sx + sw,    sy + sh,        // 7

                // top
                ux,         uy,        // 8
                ux + uw,    uy,        // 9
                ux,         uy + uh,      // 10
                ux + uw,    uy + uh,      // 11

                // right
                sx,         sy,             // 12
                                            // 5
                sx,         sy + sh,        // 13
                                            // 7

                // left
                                            // 4
                sx + sw,    sy,             // 14
                                            // 6
                sx + sw,    sy + sh,        // 15

                // bottom
                dx,         dy,             // 16
                dx + dw,    dy,             // 17
                dx,         dy + dh,        // 18
                dx + dw,    dy + dh,        // 19
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
        Mesh mesh = new Mesh(positions, indices, textCoords);
        mesh.setTexture(cubeTexture.getTexture());

        return mesh;
    }
}
