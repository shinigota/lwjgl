package fr.shinigota.game.world.renderer;

import fr.shinigota.engine.graphic.entity.Entity;
import fr.shinigota.engine.graphic.entity.MeshEntity;
import fr.shinigota.engine.graphic.mesh.CubeMesh;
import fr.shinigota.engine.graphic.mesh.InstancedCubeMesh;
import fr.shinigota.engine.graphic.mesh.InstancedMesh;
import fr.shinigota.engine.graphic.mesh.Mesh;
import fr.shinigota.engine.graphic.mesh.comparator.EntityDistanceComparator;
import fr.shinigota.game.world.BlockMeshLoader;
import fr.shinigota.game.world.chunk.Chunk;
import fr.shinigota.game.world.chunk.block.Block;
import fr.shinigota.game.world.chunk.block.BlockType;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.*;

public class ChunkRenderer implements Renderable {
    private final Chunk chunk;

    private final BlockMeshLoader blockMeshLoader;

    private final Map<Mesh, List<Entity>> opaqueMeshes;
    private final Map<Mesh, List<Entity>> transparentMeshes;

    private final Map<InstancedMesh, List<Entity>> instancedOpaqueMeshes;
    private final Map<InstancedMesh, List<Entity>> instancedTransparentMeshes;

    private final List<MeshEntity> sortedTransparentMeshes;

    public ChunkRenderer(Chunk chunk, BlockMeshLoader blockMeshLoader) {
        this.chunk = chunk;
        this.blockMeshLoader = blockMeshLoader;

        opaqueMeshes = new HashMap<>();
        transparentMeshes = new HashMap<>();

        instancedOpaqueMeshes = new HashMap<>();
        instancedTransparentMeshes = new HashMap<>();

        sortedTransparentMeshes = new ArrayList<>();
    }

    @Override
    public Map<Mesh, List<Entity>> getOpaqueMeshes() {
        if (!chunk.isUpdateRequired()) {
            return opaqueMeshes;
        }

        opaqueMeshes.clear();

        for(Map.Entry<BlockType, List<Block>> blocksEntry : chunk.getBlocksByTypes().entrySet()) {
            if (!blocksEntry.getKey().isOpaque()) {
                continue;
            }

            List<Block> blocks = blocksEntry.getValue();
            if (blocks.size() >= InstancedMesh.DEFAULT_INSTANCES_NB){
                continue;
            }

            for (Block block : blocks) {
                CubeMesh blockMesh = blockMeshLoader.getOpaqueMeshList().get(block.getBlockType());
                getBlockMeshes(block, blockMesh, opaqueMeshes);
            }
        }

        return opaqueMeshes;
    }

    @Override
    public Map<Mesh, List<Entity>> getTransparentMeshes() {
        if (!chunk.isUpdateRequired()) {
            return transparentMeshes;
        }

        transparentMeshes.clear();

        for(Map.Entry<BlockType, List<Block>> blocksEntry : chunk.getBlocksByTypes().entrySet()) {
            if (!blocksEntry.getKey().isSemiTransparent()) {
                continue;
            }

            List<Block> blocks = blocksEntry.getValue();
//            if (blocks.size() >= InstancedMesh.DEFAULT_INSTANCES_NB){
//                continue;
//            }

            for (Block block : blocks) {
                CubeMesh blockMesh = blockMeshLoader.getTransparentMeshList().get(block.getBlockType());
                getBlockMeshes(block, blockMesh, transparentMeshes);
            }
        }

        return transparentMeshes;
    }

    @Override
    public Map<InstancedMesh, List<Entity>> getInstancedOpaqueMeshes() {
        if (!chunk.isUpdateRequired()) {
            return instancedOpaqueMeshes;
        }

        instancedOpaqueMeshes.clear();

        for(Map.Entry<BlockType, List<Block>> blocksEntry : chunk.getBlocksByTypes().entrySet()) {
            if (!blocksEntry.getKey().isOpaque()) {
                continue;
            }

            List<Block> blocks = blocksEntry.getValue();
            if (blocks.size() < InstancedMesh.DEFAULT_INSTANCES_NB){
                continue;
            }

            for (Block block : blocks) {
                InstancedCubeMesh blockMesh = blockMeshLoader.getInstancedOpaqueMesh().get(block.getBlockType());
                getBlockInstancedMeshes(block, blockMesh, instancedOpaqueMeshes);
            }
        }

        return instancedOpaqueMeshes;
    }

    @Override
    public Map<InstancedMesh, List<Entity>> getInstancedTransparentMeshes() {
        if (!chunk.isUpdateRequired()) {
            return instancedTransparentMeshes;
        }

        instancedTransparentMeshes.clear();

        for(Map.Entry<BlockType, List<Block>> blocksEntry : chunk.getBlocksByTypes().entrySet()) {
            if (!blocksEntry.getKey().isSemiTransparent()) {
                continue;
            }

            List<Block> blocks = blocksEntry.getValue();
            if (blocks.size() < InstancedMesh.DEFAULT_INSTANCES_NB){
                continue;
            }

            for (Block block : blocks) {
                InstancedCubeMesh blockMesh = blockMeshLoader.getInstancedTransparentMesh().get(block.getBlockType());
                getBlockInstancedMeshes(block, blockMesh, instancedTransparentMeshes);
            }
        }

        return instancedTransparentMeshes;
    }

    @Override
    public List<MeshEntity> getSortedTransparentMeshes(Vector3f origin) {
        sortedTransparentMeshes.clear();

        for(Map.Entry<BlockType, List<Block>> blocksEntry : chunk.getBlocksByTypes().entrySet()) {
            if (!blocksEntry.getKey().isSemiTransparent()) {
                continue;
            }

            List<Block> blocks = blocksEntry.getValue();
//            if (blocks.size() >= InstancedMesh.DEFAULT_INSTANCES_NB){
//                continue;
//            }

            for (Block block : blocks) {
                CubeMesh blockMesh = blockMeshLoader.getTransparentMeshList().get(block.getBlockType());
                getBlockMeshes(block, blockMesh, sortedTransparentMeshes);
            }
        }
        sortedTransparentMeshes.sort(new EntityDistanceComparator(origin));

        return sortedTransparentMeshes;
    }

    private void getBlockMeshes(Block block, CubeMesh blockMesh, Map<Mesh, List<Entity>> targetMap) {
        Entity entity = new Entity(block.getX(), block.getY(), block.getZ());

        if (block.getVisibilityController().topVisible()) {
            targetMap.putIfAbsent(blockMesh.up(), new ArrayList<>());
            targetMap.get(blockMesh.up()).add(entity);
        }
        if (block.getVisibilityController().bottomVisible()) {
            targetMap.putIfAbsent(blockMesh.down(), new ArrayList<>());
            targetMap.get(blockMesh.down()).add(entity);
        }
        if (block.getVisibilityController().frontVisible()) {
            targetMap.putIfAbsent(blockMesh.front(), new ArrayList<>());
            targetMap.get(blockMesh.front()).add(entity);
        }
        if (block.getVisibilityController().backVisible()) {
            targetMap.putIfAbsent(blockMesh.back(), new ArrayList<>());
            targetMap.get(blockMesh.back()).add(entity);
        }
        if (block.getVisibilityController().rightVisible()) {
            targetMap.putIfAbsent(blockMesh.right(), new ArrayList<>());
            targetMap.get(blockMesh.right()).add(entity);
        }
        if (block.getVisibilityController().leftVisible()) {
            targetMap.putIfAbsent(blockMesh.left(), new ArrayList<>());
            targetMap.get(blockMesh.left()).add(entity);
        }
    }

    private void getBlockMeshes(Block block, CubeMesh blockMesh, Collection<MeshEntity> targetCol) {
        Entity entity = new Entity(block.getX(), block.getY(), block.getZ());

        if (block.getVisibilityController().topVisible()) {
            targetCol.add(new MeshEntity(blockMesh.up(), entity));
        }
        if (block.getVisibilityController().bottomVisible()) {
            targetCol.add(new MeshEntity(blockMesh.down(), entity));
        }
        if (block.getVisibilityController().frontVisible()) {
            targetCol.add(new MeshEntity(blockMesh.front(), entity));
        }
        if (block.getVisibilityController().backVisible()) {
            targetCol.add(new MeshEntity(blockMesh.back(), entity));
        }
        if (block.getVisibilityController().rightVisible()) {
            targetCol.add(new MeshEntity(blockMesh.right(), entity));
        }
        if (block.getVisibilityController().leftVisible()) {
            targetCol.add(new MeshEntity(blockMesh.left(), entity));
        }
    }

    private void getBlockInstancedMeshes(Block block, InstancedCubeMesh blockMesh, Map<InstancedMesh, List<Entity>>
            targetMap) {
        Entity entity = new Entity(block.getX(), block.getY(), block.getZ());

        if (block.getVisibilityController().topVisible()) {
            targetMap.putIfAbsent(blockMesh.up(), new ArrayList<>());
            targetMap.get(blockMesh.up()).add(entity);
        }
        if (block.getVisibilityController().bottomVisible()) {
            targetMap.putIfAbsent(blockMesh.down(), new ArrayList<>());
            targetMap.get(blockMesh.down()).add(entity);
        }
        if (block.getVisibilityController().frontVisible()) {
            targetMap.putIfAbsent(blockMesh.front(), new ArrayList<>());
            targetMap.get(blockMesh.front()).add(entity);
        }
        if (block.getVisibilityController().backVisible()) {
            targetMap.putIfAbsent(blockMesh.back(), new ArrayList<>());
            targetMap.get(blockMesh.back()).add(entity);
        }
        if (block.getVisibilityController().rightVisible()) {
            targetMap.putIfAbsent(blockMesh.right(), new ArrayList<>());
            targetMap.get(blockMesh.right()).add(entity);
        }
        if (block.getVisibilityController().leftVisible()) {
            targetMap.putIfAbsent(blockMesh.left(), new ArrayList<>());
            targetMap.get(blockMesh.left()).add(entity);
        }
    }

    @Override
    public void cleanup() {

    }
}
