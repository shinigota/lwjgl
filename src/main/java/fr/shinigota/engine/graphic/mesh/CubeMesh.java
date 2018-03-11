package fr.shinigota.engine.graphic.mesh;

import fr.shinigota.engine.graphic.texture.Texture;
import fr.shinigota.game.world.chunk.block.BlockType;

public class CubeMesh {
    private final BlockType type;
    private final Mesh up;
    private final Mesh down;
    private final Mesh front;
    private final Mesh back;
    private final Mesh right;
    private final Mesh left;

    public CubeMesh(BlockType type, Texture texture) {
        this.type = type;

        up = FaceMesh.up(type.getCubeTexture(texture).up());
        down = FaceMesh.down(type.getCubeTexture(texture).down());
        front = FaceMesh.front(type.getCubeTexture(texture).side());
        back = FaceMesh.back(type.getCubeTexture(texture).side());
        right = FaceMesh.right(type.getCubeTexture(texture).side());
        left = FaceMesh.left(type.getCubeTexture(texture).side());
    }

    public Mesh up() {
        return up;
    }

    public Mesh down() {
        return down;
    }

    public Mesh front() {
        return front;
    }

    public Mesh back() {
        return back;
    }

    public Mesh right() {
        return right;
    }

    public Mesh left() {
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
