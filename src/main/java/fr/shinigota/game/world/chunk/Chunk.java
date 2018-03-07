package fr.shinigota.game.world.chunk;

import fr.shinigota.engine.graphic.entity.MeshEntity;
import fr.shinigota.engine.graphic.mesh.FaceMesh;
import fr.shinigota.engine.graphic.texture.Texture;
import fr.shinigota.game.world.chunk.block.Block;
import fr.shinigota.game.world.chunk.generator.IChunkGenerator;
import org.joml.Vector3i;
import org.joml.Vector3ic;

import java.util.*;

public class Chunk {
    public static final int CHUNK_SIZE = 16;
    public static final int CHUNK_HEIGHT = 16;
    public static final int SEA_LEVEL = 8;


    /**
     * ID : Read-only 3D vector of int (position of the block)
     * Value : block
     */
    private final Map<Vector3ic, Block> blocks;
    private final IChunkGenerator generator;
    private final ChunkController chunkController;
    private final List<MeshEntity> meshes;
    private final List<MeshEntity> transparentMeshes;
    private final int x;
    private final int z;

    private boolean updateRequired;

    public Chunk(int x, int z, IChunkGenerator generator) {
        this.x = x;
        this.z = z;
        blocks = new HashMap<>();
        updateRequired = false;
        meshes = new ArrayList<>();
        transparentMeshes = new ArrayList<>();
        this.generator = generator;
        chunkController = new ChunkController(this);
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
            if (!block.getBlockType().isOpaque()) {
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

    public List<MeshEntity> getTransparentMeshes(Texture texture) {
        if (!updateRequired) {
            return meshes;
        }

        meshes.clear();

        for(Block block : blocks.values()) {
            if (!block.getBlockType().isSemiTransparent()) {
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

    public int getRealX() {
        return x * CHUNK_SIZE;
    }

    public int getRealZ() {
        return z * CHUNK_SIZE;
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
        if (isInside(front)) {
            if (blocks.containsKey(front)) {
                center.getVisibilityController().setFront(blocks.get(front));
            }
        }
        else {
            if (chunkController.getFront() != null && chunkController.getFront().blocks.containsKey(front)) {
                center.getVisibilityController().setFront(chunkController.getFront().blocks.get(front));
            }
        }

        Vector3ic back = new Vector3i(center.getX(), center.getY(), center.getZ() - 1);
        if (isInside(back)) {
            if (blocks.containsKey(back)) {
                center.getVisibilityController().setBack(blocks.get(back));
            }
        } else {
            if (chunkController.getBack() != null && chunkController.getBack().blocks.containsKey(back)) {
                center.getVisibilityController().setBack(chunkController.getBack().blocks.get(back));
            }
        }

        Vector3ic left = new Vector3i(center.getX() - 1, center.getY(), center.getZ());
        if (isInside(left)) {
            if (blocks.containsKey(left)) {
                center.getVisibilityController().setLeft(blocks.get(left));
            }
        } else {
            if (chunkController.getLeft() != null && chunkController.getLeft().blocks.containsKey(left)) {
                center.getVisibilityController().setLeft(chunkController.getLeft().blocks.get(left));
            }
        }

        Vector3ic right = new Vector3i(center.getX() + 1, center.getY(), center.getZ());
        if (isInside(right)) {
            if (blocks.containsKey(right)) {
                center.getVisibilityController().setRight(blocks.get(right));
            }
        } else {
            if (chunkController.getRight() != null && chunkController.getRight().blocks.containsKey(right)) {
                center.getVisibilityController().setRight(chunkController.getRight().blocks.get(right));
            }
        }
    }

    public boolean isInside(Vector3ic position) {
        return      (getRealX() <= position.x() && position.x() < CHUNK_SIZE + getRealX() )
                &&  (getRealZ() <= position.z() && position.z() < CHUNK_SIZE + getRealZ() );

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

    public ChunkController getController() {
        return chunkController;
    }
}
