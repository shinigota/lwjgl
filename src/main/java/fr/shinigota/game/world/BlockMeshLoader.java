package fr.shinigota.game.world;

import fr.shinigota.engine.graphic.mesh.CubeMesh;
import fr.shinigota.engine.graphic.mesh.InstancedCubeMesh;
import fr.shinigota.engine.graphic.texture.Texture;
import fr.shinigota.game.world.chunk.block.BlockType;

import java.util.HashMap;
import java.util.Map;

public class BlockMeshLoader {
    private final Map<BlockType, CubeMesh> opaqueMesh;
    private final Map<BlockType, CubeMesh> transparentMesh;

    private final Map<BlockType, InstancedCubeMesh> instancedOpaqueMesh;
    private final Map<BlockType, InstancedCubeMesh> instancedTransparentMesh;

    public BlockMeshLoader(Texture texture) {
        opaqueMesh = new HashMap<>();
        transparentMesh = new HashMap<>();
        instancedOpaqueMesh = new HashMap<>();
        instancedTransparentMesh = new HashMap<>();

        for (BlockType blockType : BlockType.values()) {
            if(BlockType.AIR.equals(blockType)) {
                continue;
            }
            Map<BlockType, CubeMesh> listToAdd = blockType.isOpaque() ? opaqueMesh : transparentMesh;
            Map<BlockType, InstancedCubeMesh> instancedListToAdd = blockType.isOpaque() ? instancedOpaqueMesh : instancedTransparentMesh;
            listToAdd.put(blockType, new CubeMesh(blockType, texture));
            instancedListToAdd.put(blockType, new InstancedCubeMesh(blockType, texture));
        }
    }

    public void cleanup() {
        for(CubeMesh mesh : opaqueMesh.values()) {
            mesh.cleanup();
        }

        for(CubeMesh mesh : transparentMesh.values()) {
            mesh.cleanup();
        }

        for(InstancedCubeMesh mesh : instancedOpaqueMesh.values()) {
            mesh.cleanup();
        }

        for(InstancedCubeMesh mesh : instancedTransparentMesh.values()) {
            mesh.cleanup();
        }
    }

    public Map<BlockType, CubeMesh> getOpaqueMeshList() {
        return opaqueMesh;
    }

    public Map<BlockType, CubeMesh> getTransparentMeshList() {
        return transparentMesh;
    }

    public Map<BlockType, InstancedCubeMesh> getInstancedOpaqueMesh() {
        return instancedOpaqueMesh;
    }

    public Map<BlockType, InstancedCubeMesh> getInstancedTransparentMesh() {
        return instancedTransparentMesh;
    }

}
