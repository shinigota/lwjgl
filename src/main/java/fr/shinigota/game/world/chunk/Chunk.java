package fr.shinigota.game.world.chunk;

import fr.shinigota.game.world.BlockMeshLoader;
import fr.shinigota.game.world.renderer.ChunkRenderer;
import fr.shinigota.game.world.renderer.Renderable;
import fr.shinigota.game.world.chunk.block.Block;
import fr.shinigota.game.world.chunk.block.BlockType;
import fr.shinigota.game.world.chunk.generator.IChunkGenerator;
import org.joml.Vector3i;
import org.joml.Vector3ic;

import java.util.*;

public class Chunk {
    public static final int CHUNK_SIZE = 32;
    public static final int CHUNK_HEIGHT = 64;
    public static final int SEA_LEVEL = 25;

    private final int x;
    private final int z;

    /**
     * ID : Read-only 3D vector of int (position of the block)
     * Value : block
     */
    private final Map<Vector3ic, Block> blocks;
    private final Map<Vector3ic, Block> surfaceBlocks;
    private final Map<BlockType, List<Block>> blocksByTypes;

    private final IChunkGenerator generator;
    private final ChunkController chunkController;

    private boolean updateRequired;

    private final Renderable chunkRenderer;

    public Chunk(int x, int z, IChunkGenerator generator, BlockMeshLoader blockMeshLoader) {
        this.x = x;
        this.z = z;

        blocks = new HashMap<>();
        surfaceBlocks = new HashMap<>();
        blocksByTypes = new EnumMap<>(BlockType.class);

        this.generator = generator;
        chunkController = new ChunkController(this);

        updateRequired = false;

        chunkRenderer = new ChunkRenderer(this, blockMeshLoader);
    }

    public void generate() {
        generator.generate(this);
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

    public void addBlockAt(Block block, int x, int y, int z, boolean surface) {
        if(blocks.containsKey(new Vector3i(x, y, z))) {
            System.out.println("CONTAINS : " +  blocks.get(new Vector3i(x, y, z)).getBlockType() + " TO " + block
                    .getBlockType());
        }
        blocks.put(new Vector3i(x, y, z), block);

        if (surface) {
            surfaceBlocks.put(new Vector3i(x, y, z), block);
        }

        setNeighbours(block);
        if (block.getBlockType() == BlockType.AIR) {
            return;
        }
        putBlockByType(block.getBlockType(), block);
    }

    public void putBlockByType(BlockType type, Block block) {
        blocksByTypes.putIfAbsent(type, new ArrayList<>());
        blocksByTypes.get(type).add(block);
    }

    public Map<BlockType, List<Block>> getBlocksByTypes() {
        return blocksByTypes;
    }

    public boolean isUpdateRequired() {
        return updateRequired;
    }

    public Renderable getRenderer() {
        return chunkRenderer;
    }

    public void cleanup() {
        chunkRenderer.cleanup();
    }

    public Map<Vector3ic, Block> getSurfaceBlocks() {
        return surfaceBlocks;
    }
}
