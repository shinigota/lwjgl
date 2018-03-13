package fr.shinigota.engine.graphic.mesh;

import fr.shinigota.engine.graphic.Transformation;
import fr.shinigota.engine.graphic.entity.Entity;
import fr.shinigota.engine.graphic.texture.Texture;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL33.*;

public class InstancedMesh extends Mesh {
    public static  final int DEFAULT_INSTANCES_NB = 200;
    private static final int VECTOR4F_SIZE_BYTES = 4 * 4;
    private static final int MATRIX_SIZE_BYTES = 4 * VECTOR4F_SIZE_BYTES;
    private static final int MATRIX_SIZE_FLOATS = 4 * 4;

    private final int instancesNumber;
    private final int modelViewVBO;
    private FloatBuffer modelViewBuffer;

    public InstancedMesh(float[] positions, int[] indices, float[] textCoords, Texture texture, int instancesNumber) {
        super(positions, indices, textCoords, texture);
        this.instancesNumber = instancesNumber;
        glBindVertexArray(vaoId);

        // Model View Matrix
        modelViewVBO = glGenBuffers();
        vboIds.add(modelViewVBO);
        this.modelViewBuffer = MemoryUtil.memAllocFloat(instancesNumber * MATRIX_SIZE_FLOATS);
        glBindBuffer(GL_ARRAY_BUFFER, modelViewVBO);

        int start = 2;
        for (int i = 0; i < 4; i++) {
            glVertexAttribPointer(start, 4, GL_FLOAT, false, MATRIX_SIZE_BYTES, i * VECTOR4F_SIZE_BYTES);
            glVertexAttribDivisor(start, 1);
            start++;
        }


        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public InstancedMesh(float[] positions, int[] indices, float[] textCoords, Texture texture) {
        this(positions, indices, textCoords, texture, DEFAULT_INSTANCES_NB);
    }

    @Override
    protected void initRender() {
        super.initRender();
        int start = 2;
        int numElements = 4;

        for (int i = 0; i < numElements; i++) {
            glEnableVertexAttribArray(start + i);
        }


    }

    public void renderListInstanced(List<Entity> entities, Transformation transformation, Matrix4f viewMatrix) {
        initRender();

        int chunkSize = instancesNumber;
        int length = entities.size();
        for (int i = 0; i < length; i += chunkSize) {
            int end = Math.min(length, i + chunkSize);
            List<Entity> subList = entities.subList(i, end);
            renderChunkInstanced(subList, transformation, viewMatrix);
        }

        endRender();
    }

    private void renderChunkInstanced(List<Entity> entities, Transformation transformation, Matrix4f viewMatrix) {
        this.modelViewBuffer.clear();
        int i = 0;
        for (Entity entity : entities) {
            Matrix4f modelViewMatrix = transformation.buildModelViewMatrix(entity, viewMatrix);
            modelViewMatrix.get(MATRIX_SIZE_FLOATS * i, modelViewBuffer);

            i++;
        }

        glBindBuffer(GL_ARRAY_BUFFER, modelViewVBO);
        glBufferData(GL_ARRAY_BUFFER, modelViewBuffer, GL_DYNAMIC_DRAW);

        glDrawElementsInstanced(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0, entities.size());

        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
    @Override
    protected void endRender() {
        int start = 3;
        int numElements = 4;
        for (int i = 0; i < numElements; i++) {
            glDisableVertexAttribArray(start + i);
        }
        super.endRender();
    }

    @Override
    public void cleanup() {
        super.cleanup();
        if (this.modelViewBuffer != null) {
            MemoryUtil.memFree(this.modelViewBuffer);
            this.modelViewBuffer = null;
        }
    }

    public int getInstancesNumber() {
        return instancesNumber;
    }
}
