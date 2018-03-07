package fr.shinigota.game.world.chunk;

public class ChunkController {
    private final Chunk self;

    private Chunk front;
    private Chunk back;
    private Chunk right;
    private Chunk left;

    public ChunkController(Chunk self) {
        this.self = self;
    }

    public void setFront(Chunk front) {
        front.getController().back = self;
        this.front = front;
    }

    public void setBack(Chunk back) {
        back.getController().front = self;
        this.back = back;
    }

    public void setRight(Chunk right) {
        right.getController().left = self;
        this.right = right;
    }

    public void setLeft(Chunk left) {
        left.getController().right = self;
        this.left = left;
    }

    public Chunk getFront() {
        return front;
    }

    public Chunk getBack() {
        return back;
    }

    public Chunk getRight() {
        return right;
    }

    public Chunk getLeft() {
        return left;
    }
}
