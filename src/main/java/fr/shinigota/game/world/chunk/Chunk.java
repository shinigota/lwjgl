package fr.shinigota.game.world.chunk;

import fr.shinigota.engine.graphic.entity.Entity;
import fr.shinigota.engine.graphic.mesh.CubeMesh;
import fr.shinigota.engine.graphic.mesh.InstancedCubeMesh;
import fr.shinigota.engine.graphic.mesh.InstancedMesh;
import fr.shinigota.engine.graphic.mesh.Mesh;
import fr.shinigota.game.world.BlockMeshLoader;
import fr.shinigota.game.world.chunk.block.Block;
import fr.shinigota.game.world.chunk.block.BlockType;
import fr.shinigota.game.world.chunk.generator.IChunkGenerator;
import org.joml.Vector3i;
import org.joml.Vector3ic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chunk {
    public static final int CHUNK_SIZE = 32;
    public static final int CHUNK_HEIGHT = 64;
    public static final int SEA_LEVEL = 25;


    /**
     * ID : Read-only 3D vector of int (position of the block)
     * Value : block
     */
    private final Map<Vector3ic, Block> blocks;
    private final Map<BlockType, List<Block>> blocksByTypes;

    private final IChunkGenerator generator;
    private final ChunkController chunkController;

    private final Map<Mesh, List<Entity>> opaqueMeshes;
    private final Map<Mesh, List<Entity>> transparentMeshes;

    private final Map<InstancedMesh, List<Entity>> instancedOpaqueMeshes;
    private final Map<InstancedMesh, List<Entity>> instancedTransparentMeshes;

    private final int x;
    private final int z;
    private final BlockMeshLoader blockMeshLoader;

    private boolean updateRequired;

    public Chunk(int x, int z, IChunkGenerator generator, BlockMeshLoader blockMeshLoader) {
        this.x = x;
        this.z = z;
        blocks = new HashMap<>();
        blocksByTypes = new HashMap<>();
        updateRequired = false;
        opaqueMeshes = new HashMap<>();
        transparentMeshes = new HashMap<>();
        instancedOpaqueMeshes = new HashMap<>();
        instancedTransparentMeshes = new HashMap<>();
        this.blockMeshLoader = blockMeshLoader;
        this.generator = generator;
        chunkController = new ChunkController(this);
    }

    public void generate() {
        generator.generate(this);
    }

    public Map<Mesh, List<Entity>> getOpaqueMeshes() {
        if (!updateRequired) {
            return opaqueMeshes;
        }

        opaqueMeshes.clear();

        for(Map.Entry<BlockType, List<Block>> blocksEntry : blocksByTypes.entrySet()) {
            if (!blocksEntry.getKey().isOpaque()) {
                continue;
            }

            List<Block> blocks = blocksEntry.getValue();
            if(blocks.size() < 10){
                continue;
            }

            for (Block block : blocks) {
                CubeMesh blockMesh = blockMeshLoader.getOpaqueMeshList().get(block.getBlockType());
                Entity entity = new Entity(block.getX(), block.getY(), block.getZ());

                if (block.getVisibilityController().topVisible()) {
                    opaqueMeshes.putIfAbsent(blockMesh.up(), new ArrayList<>());
                    opaqueMeshes.get(blockMesh.up()).add(entity);
                }
                if (block.getVisibilityController().bottomVisible()) {
                    opaqueMeshes.putIfAbsent(blockMesh.down(), new ArrayList<>());
                    opaqueMeshes.get(blockMesh.down()).add(entity);
                }
                if (block.getVisibilityController().frontVisible()) {
                    opaqueMeshes.putIfAbsent(blockMesh.front(), new ArrayList<>());
                    opaqueMeshes.get(blockMesh.front()).add(entity);
                }
                if (block.getVisibilityController().backVisible()) {
                    opaqueMeshes.putIfAbsent(blockMesh.back(), new ArrayList<>());
                    opaqueMeshes.get(blockMesh.back()).add(entity);
                }
                if (block.getVisibilityController().rightVisible()) {
                    opaqueMeshes.putIfAbsent(blockMesh.right(), new ArrayList<>());
                    opaqueMeshes.get(blockMesh.right()).add(entity);
                }
                if (block.getVisibilityController().leftVisible()) {
                    opaqueMeshes.putIfAbsent(blockMesh.left(), new ArrayList<>());
                    opaqueMeshes.get(blockMesh.left()).add(entity);
                }
            }
        }

        return opaqueMeshes;
    }

    public Map<InstancedMesh, List<Entity>> getInstancedOpaqueMeshes() {
        if (!updateRequired) {
            return instancedOpaqueMeshes;
        }

        instancedOpaqueMeshes.clear();

        for(Map.Entry<BlockType, List<Block>> blocksEntry : blocksByTypes.entrySet()) {
            if (!blocksEntry.getKey().isOpaque()) {
                continue;
            }

            List<Block> blocks = blocksEntry.getValue();
            if(blocks.size() < 10){
                continue;
            }

            for (Block block : blocks) {

                InstancedCubeMesh blockMesh = blockMeshLoader.getInstancedOpaqueMesh().get(block.getBlockType());
                Entity entity = new Entity(block.getX(), block.getY(), block.getZ());

                if (block.getVisibilityController().topVisible()) {
                    instancedOpaqueMeshes.putIfAbsent(blockMesh.up(), new ArrayList<>());
                    instancedOpaqueMeshes.get(blockMesh.up()).add(entity);
                }
                if (block.getVisibilityController().bottomVisible()) {
                    instancedOpaqueMeshes.putIfAbsent(blockMesh.down(), new ArrayList<>());
                    instancedOpaqueMeshes.get(blockMesh.down()).add(entity);
                }
                if (block.getVisibilityController().frontVisible()) {
                    instancedOpaqueMeshes.putIfAbsent(blockMesh.front(), new ArrayList<>());
                    instancedOpaqueMeshes.get(blockMesh.front()).add(entity);
                }
                if (block.getVisibilityController().backVisible()) {
                    instancedOpaqueMeshes.putIfAbsent(blockMesh.back(), new ArrayList<>());
                    instancedOpaqueMeshes.get(blockMesh.back()).add(entity);
                }
                if (block.getVisibilityController().rightVisible()) {
                    instancedOpaqueMeshes.putIfAbsent(blockMesh.right(), new ArrayList<>());
                    instancedOpaqueMeshes.get(blockMesh.right()).add(entity);
                }
                if (block.getVisibilityController().leftVisible()) {
                    instancedOpaqueMeshes.putIfAbsent(blockMesh.left(), new ArrayList<>());
                    instancedOpaqueMeshes.get(blockMesh.left()).add(entity);
                }
            }
        }

        return instancedOpaqueMeshes;
    }

    public Map<Mesh, List<Entity>> getTransparentMeshes() {
        if (!updateRequired) {
            return transparentMeshes;
        }

        transparentMeshes.clear();

        for(Map.Entry<BlockType, List<Block>> blocksEntry : blocksByTypes.entrySet()) {
            if (!blocksEntry.getKey().isSemiTransparent()) {
                continue;
            }

            List<Block> blocks = blocksEntry.getValue();
            if(blocks.size() < 10){
                continue;
            }

            for (Block block : blocks) {

                CubeMesh blockMesh = blockMeshLoader.getTransparentMeshList().get(block.getBlockType());
                Entity entity = new Entity(block.getX(), block.getY(), block.getZ());

                if (block.getVisibilityController().topVisible()) {
                    transparentMeshes.putIfAbsent(blockMesh.up(), new ArrayList<>());
                    transparentMeshes.get(blockMesh.up()).add(entity);
                }
                if (block.getVisibilityController().bottomVisible()) {
                    transparentMeshes.putIfAbsent(blockMesh.down(), new ArrayList<>());
                    transparentMeshes.get(blockMesh.down()).add(entity);
                }
                if (block.getVisibilityController().frontVisible()) {
                    transparentMeshes.putIfAbsent(blockMesh.front(), new ArrayList<>());
                    transparentMeshes.get(blockMesh.front()).add(entity);
                }
                if (block.getVisibilityController().backVisible()) {
                    transparentMeshes.putIfAbsent(blockMesh.back(), new ArrayList<>());
                    transparentMeshes.get(blockMesh.back()).add(entity);
                }
                if (block.getVisibilityController().rightVisible()) {
                    transparentMeshes.putIfAbsent(blockMesh.right(), new ArrayList<>());
                    transparentMeshes.get(blockMesh.right()).add(entity);
                }
                if (block.getVisibilityController().leftVisible()) {
                    transparentMeshes.putIfAbsent(blockMesh.left(), new ArrayList<>());
                    transparentMeshes.get(blockMesh.left()).add(entity);
                }
            }
        }

        return transparentMeshes;
    }

    public Map<InstancedMesh, List<Entity>> getInstancedTransparentMeshes() {
        if (!updateRequired) {
            return instancedTransparentMeshes;
        }

        instancedTransparentMeshes.clear();

        for(Map.Entry<BlockType, List<Block>> blocksEntry : blocksByTypes.entrySet()) {
            if (!blocksEntry.getKey().isSemiTransparent()) {
                continue;
            }

            List<Block> blocks = blocksEntry.getValue();
            if(blocks.size() < 10){
                continue;
            }

            for (Block block : blocks) {

                InstancedCubeMesh blockMesh = blockMeshLoader.getInstancedTransparentMesh().get(block.getBlockType());
                Entity entity = new Entity(block.getX(), block.getY(), block.getZ());

                if (block.getVisibilityController().topVisible()) {
                    instancedTransparentMeshes.putIfAbsent(blockMesh.up(), new ArrayList<>());
                    instancedTransparentMeshes.get(blockMesh.up()).add(entity);
                }
                if (block.getVisibilityController().bottomVisible()) {
                    instancedTransparentMeshes.putIfAbsent(blockMesh.down(), new ArrayList<>());
                    instancedTransparentMeshes.get(blockMesh.down()).add(entity);
                }
                if (block.getVisibilityController().frontVisible()) {
                    instancedTransparentMeshes.putIfAbsent(blockMesh.front(), new ArrayList<>());
                    instancedTransparentMeshes.get(blockMesh.front()).add(entity);
                }
                if (block.getVisibilityController().backVisible()) {
                    instancedTransparentMeshes.putIfAbsent(blockMesh.back(), new ArrayList<>());
                    instancedTransparentMeshes.get(blockMesh.back()).add(entity);
                }
                if (block.getVisibilityController().rightVisible()) {
                    instancedTransparentMeshes.putIfAbsent(blockMesh.right(), new ArrayList<>());
                    instancedTransparentMeshes.get(blockMesh.right()).add(entity);
                }
                if (block.getVisibilityController().leftVisible()) {
                    instancedTransparentMeshes.putIfAbsent(blockMesh.left(), new ArrayList<>());
                    instancedTransparentMeshes.get(blockMesh.left()).add(entity);
                }
            }
        }

        return instancedTransparentMeshes;
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

    public Block getBlockAt(int x, int y, int z) {
        return blocks.get(new Vector3i(x, y, z));
    }

    public void putBlockByType(BlockType type, Block block) {
        blocksByTypes.putIfAbsent(type, new ArrayList<>());
        blocksByTypes.get(type).add(block);
    }
}
