package fr.shinigota.engine.graphic.mesh;

import fr.shinigota.engine.graphic.Transformation;
import fr.shinigota.engine.graphic.entity.Entity;
import fr.shinigota.engine.graphic.texture.Texture;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL33.*;

public class InstancedMesh extends Mesh {
    private static final int VECTOR4F_SIZE_BYTES = 4 * 4;
    private static final int MATRIX_SIZE_BYTES = 4 * VECTOR4F_SIZE_BYTES;
    private static final int MATRIX_SIZE_FLOATS = 4 * 4;

    private final int instances;
    private final int modelViewVBO;
    private FloatBuffer modelViewBuffer;

    public InstancedMesh(float[] positions, int[] indices, float[] textCoords, Texture texture, int instances) {
        super(positions, indices, textCoords, texture);
        instances = 10;
        this.instances = instances;

        modelViewVBO = glGenBuffers();
        vboIds.add(modelViewVBO);
        modelViewBuffer = MemoryUtil.memAllocFloat(instances * MATRIX_SIZE_FLOATS);

        glBindBuffer(GL_ARRAY_BUFFER, modelViewVBO);

        int start = 3;
        for (int i = 0; i < 4; i++) {
            glVertexAttribPointer(start, 4, GL_FLOAT, false, MATRIX_SIZE_BYTES, i * VECTOR4F_SIZE_BYTES);
            glVertexAttribDivisor(start, 1);
            start++;
        }

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

    }

    @Override
    protected void initRender() {
        super.initRender();
        int start = 3;
        int numElements = 1;

        for (int i = 0; i < numElements; i++) {
            glEnableVertexAttribArray(start + i);
        }


    }

    public void renderListInstanced(List<Entity> entities, Transformation transformation, Matrix4f viewMatrix) {
        initRender();

        int length = entities.size();
        for (int i = 0; i < length; i += instances) {
            int chunkEnd = Math.min(length, i + instances);
            List<Entity> subList = entities.subList(i, chunkEnd);
            renderChunkInstanced(subList, transformation, viewMatrix);
        }
        endRender();
    }
    private void renderChunkInstanced(List<Entity> entities, Transformation transformation, Matrix4f viewMatrix) {
        this.modelViewBuffer.clear();
        int i = 0;
        for (Entity entity : entities) {
            Matrix4f modelViewMatrix = transformation.getModelView(entity, viewMatrix);
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
        int numElements = 2 * 2;
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
}
