package fr.shinigota.engine.graphic.mesh;

import fr.shinigota.engine.graphic.texture.Texture;
import fr.shinigota.game.world.chunk.block.BlockType;

public class InstancedCubeMesh {
    private final BlockType type;
    private final InstancedMesh up;
    private final InstancedMesh down;
    private final InstancedMesh front;
    private final InstancedMesh back;
    private final InstancedMesh right;
    private final InstancedMesh left;

    public InstancedCubeMesh(BlockType type, Texture texture) {
        this.type = type;

        up = InstancedFaceMesh.up(type.getCubeTexture(texture).up());
        down = InstancedFaceMesh.down(type.getCubeTexture(texture).down());
        front = InstancedFaceMesh.front(type.getCubeTexture(texture).side());
        back = InstancedFaceMesh.back(type.getCubeTexture(texture).side());
        right = InstancedFaceMesh.right(type.getCubeTexture(texture).side());
        left = InstancedFaceMesh.left(type.getCubeTexture(texture).side());
    }

    public InstancedMesh up() {
        return up;
    }

    public InstancedMesh down() {
        return down;
    }

    public InstancedMesh front() {
        return front;
    }

    public InstancedMesh back() {
        return back;
    }

    public InstancedMesh right() {
        return right;
    }

    public InstancedMesh left() {
        return left;
    }

    public void cleanup() {
        up.cleanup();
        down.cleanup();
        front.cleanup();
        back.cleanup();
        right.cleanup();
        left.cleanup();
    }
}
