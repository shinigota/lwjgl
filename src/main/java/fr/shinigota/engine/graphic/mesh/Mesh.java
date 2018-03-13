package fr.shinigota.engine.graphic.mesh;

import fr.shinigota.engine.graphic.entity.Entity;
import fr.shinigota.engine.graphic.texture.Texture;
import fr.shinigota.engine.graphic.texture.TextureRegion;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Mesh {
    public static final MeshFactory FACTORY = new MeshFactory();

    private final float[] positions;
    private final float[] textCoords;
    private final int[] indicies;

    /**
     * Verticies arrays object
     */
    protected final int vaoId;

    private final int vertexCount;

    private Texture texture;

    protected final List<Integer> vboIds;

    public Mesh(float[] positions, int[] indices, float[] textCoords) {

        this.positions = positions;
        this.textCoords = textCoords;
        this.indicies = indices;

        vboIds = new ArrayList<>();
        FloatBuffer posBuffer = null;
        IntBuffer indicesBuffer = null;
        FloatBuffer textCoordsBuffer = null;

        try {
            vertexCount = indices.length;

            vaoId = glGenVertexArrays();
            glBindVertexArray(vaoId);

            // Position VBO M
            int vboId = glGenBuffers();
            vboIds.add(vboId);
            posBuffer = MemoryUtil.memAllocFloat(positions.length);
            posBuffer.put(positions).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

            // Index VBO M
            vboId = glGenBuffers();
            vboIds.add(vboId);
            indicesBuffer = MemoryUtil.memAllocInt(indices.length);
            indicesBuffer.put(indices).flip();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

            // Colour VBO M
            vboId = glGenBuffers();
            vboIds.add(vboId);
            textCoordsBuffer = MemoryUtil.memAllocFloat(textCoords.length);
            textCoordsBuffer.put(textCoords).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, textCoordsBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        } finally {
            if (posBuffer != null) {
                MemoryUtil.memFree(posBuffer);
            }
            if (indicesBuffer != null) {
                MemoryUtil.memFree(indicesBuffer);
            }
            if (textCoordsBuffer != null) {
                MemoryUtil.memFree(textCoordsBuffer);
            }
        }
    }

    public Mesh(float[] positions, int[] indices, float[] textCoords, Texture texture) {
        this(positions, indices, textCoords);
        this.texture = texture;
    }

    public int getVaoId() {
        return vaoId;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void cleanup() {
        glDisableVertexAttribArray(0);

        // Delete the VBOs
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        for (int vboId : vboIds) {
            glDeleteBuffers(vboId);
        }

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }

    protected void initRender() {
        // Activate first texture unit
        glActiveTexture(GL_TEXTURE0);
        // Bind the texture
        glBindTexture(GL_TEXTURE_2D, texture.getId());

        // Draw the mesh
        glBindVertexArray(vaoId);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
    }

    protected void endRender() {

        // Restore state
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
    }

    public void render() {
        initRender();

        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);

        endRender();
    }

    public void renderList(List<Entity> entities, Consumer<Entity> consumer) {
        initRender();

        for(Entity entity : entities) {
            consumer.accept(entity);
            glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
        }

        endRender();
    }

    public float[] getPositions() {
        return positions;
    }

    public int[] getIndicies() {
        return indicies;
    }

    public float[] getTextCoords() {
        return textCoords;
    }

    @Override
    public int hashCode() {
        return Objects.hash(texture, positions, textCoords, indicies);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if ( ! (obj instanceof  Mesh) ) {
            return false;
        }

        Mesh other = (Mesh) obj;

        if (!texture.equals(other.texture)) {
            return false;
        }

        if (positions != other.positions) {
            return false;
        }

        if (textCoords != other.textCoords) {
            return false;
        }

        if (indicies != other.indicies) {
            return false;
        }

        return true;
    }
}
