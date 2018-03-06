package fr.shinigota.game.world.chunk;

import fr.shinigota.engine.graphic.entity.MeshEntity;
import fr.shinigota.engine.graphic.mesh.FaceMesh;
import fr.shinigota.engine.graphic.mesh.Mesh;
import fr.shinigota.engine.graphic.texture.Texture;
import fr.shinigota.game.world.chunk.block.Block;
import fr.shinigota.game.world.chunk.block.BlockType;
import fr.shinigota.game.world.chunk.generator.FilledGenerator;
import fr.shinigota.game.world.chunk.generator.IChunkGenerator;
import org.joml.Vector3i;
import org.joml.Vector3ic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chunk {
    public static final int CHUNK_SIZE = 16;
    public static final int CHUNK_HEIGHT = 16;
    public static final int SEA_LEVEL = 8;

    private final int x;
    private final int z;

    /**
     * ID : Read-only 3D vector of int (position of the block)
     * Value : block
     */
    private final Map<Vector3ic, Block> blocks;
    private final IChunkGenerator generator;

    private boolean updateRequired;

    private final List<MeshEntity> meshes;


    public Chunk(int x, int z, IChunkGenerator generator) {
        this.x = x*CHUNK_SIZE;
        this.z = z*CHUNK_SIZE;
        blocks = new HashMap<>();
        updateRequired = false;
        meshes = new ArrayList<>();
        this.generator = generator;
    }

    public void generate() {
        generator.generate(this);
    }

    public List<MeshEntity> getMeshes(Texture texture) {
        if (!updateRequired) {
            return meshes;
        }

        meshes.clear();

        for(Block block : blocks.values()) {
            if (block.getBlockType().isTransparent()) {
                continue;
            }


            if (block.getVisibilityController().topVisible()) {
                meshes.add(new MeshEntity(FaceMesh.up(block.getBlockType().getCubeTexture(texture).getUp()), block
                        .getX(), block.getY(), block.getZ()));
            }
            if (block.getVisibilityController().bottomVisible()) {
                meshes.add(new MeshEntity(FaceMesh.down(block.getBlockType().getCubeTexture(texture).getDown()), block
                        .getX(), block.getY(), block.getZ()));
            }
            if (block.getVisibilityController().frontVisible()) {
                meshes.add(new MeshEntity(FaceMesh.front(block.getBlockType().getCubeTexture(texture).getSide()), block
                        .getX(), block.getY(), block.getZ()));
            }
            if (block.getVisibilityController().backVisible()) {
                meshes.add(new MeshEntity(FaceMesh.back(block.getBlockType().getCubeTexture(texture).getSide()), block
                        .getX(), block.getY(), block.getZ()));
            }
            if (block.getVisibilityController().rightVisible()) {
                meshes.add(new MeshEntity(FaceMesh.right(block.getBlockType().getCubeTexture(texture).getSide()), block
                        .getX(), block.getY(), block.getZ()));
            }
            if (block.getVisibilityController().leftVisible()) {
                meshes.add(new MeshEntity(FaceMesh.left(block.getBlockType().getCubeTexture(texture).getSide()), block
                        .getX(), block.getY(), block.getZ()));
            }
        }

        return meshes;
    }

    public List<Block> getBlocks() {
        return (List<Block>) blocks.values();
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public void setNeighbours(Block center) {
        Vector3ic top = new Vector3i(center.getX(), center.getY() + 1, center.getZ());
        if(blocks.containsKey(top)) {
            center.getVisibilityController().setTop(blocks.get(top));
        }

        Vector3ic bottom = new Vector3i(center.getX(), center.getY() - 1, center.getZ());
        if(blocks.containsKey(bottom)) {
            center.getVisibilityController().setBottom(blocks.get(bottom));
        }

        Vector3ic front = new Vector3i(center.getX(), center.getY(), center.getZ() + 1);
        if(blocks.containsKey(front)) {
            center.getVisibilityController().setFront(blocks.get(front));
        }

        Vector3ic back = new Vector3i(center.getX(), center.getY(), center.getZ() - 1);
        if(blocks.containsKey(back)) {
            center.getVisibilityController().setBack(blocks.get(back));
        }

        Vector3ic left = new Vector3i(center.getX() - 1, center.getY(), center.getZ());
        if(blocks.containsKey(left)) {
            center.getVisibilityController().setLeft(blocks.get(left));
        }

        Vector3ic right = new Vector3i(center.getX() + 1, center.getY(), center.getZ());
        if(blocks.containsKey(right)) {
            center.getVisibilityController().setRight(blocks.get(right));
        }
    }

    @Override
    public String toString() {
        return "Chunk{ { x:" + x + "; z:" + z + "} { x+w:" + (x + CHUNK_SIZE) + "; z+w :" + (z + CHUNK_SIZE) + "} }";
    }

    public Map<Vector3ic, Block> getBlocksMap() {
        return this.blocks;
    }

    public void requestUpdate() {
        updateRequired = true;
    }
}
