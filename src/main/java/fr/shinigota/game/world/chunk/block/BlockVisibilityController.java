package fr.shinigota.game.world.chunk.block;

public class BlockVisibilityController {
    private final Block self;

    private Block top;
    private Block bottom;
    private Block front;
    private Block back;
    private Block right;
    private Block left;

    public BlockVisibilityController(Block self) {
        this.self = self;
    }

    public boolean topVisible () {
        if(top == null) {
            return true;
        }
        return top.getBlockType().isTransparent();
    }

    public void setTop(Block top) {
        top.getVisibilityController().bottom = self;
        this.top = top;
    }

    public boolean bottomVisible () {
        if(bottom == null) {
            return true;
        }
        return bottom.getBlockType().isTransparent();
    }

    public void setBottom(Block bottom) {
        bottom.getVisibilityController().top = self;
        this.bottom = bottom;
    }

    public boolean frontVisible () {
        if(front == null) {
            return true;
        }
        return front.getBlockType().isTransparent();
    }

    public void setFront(Block front) {
        front.getVisibilityController().back = self;
        this.front = front;
    }

    public boolean backVisible () {
        if(back == null) {
            return true;
        }
        return back.getBlockType().isTransparent();
    }

    public void setBack(Block back) {
        back.getVisibilityController().front = self;
        this.back = back;
    }

    public boolean rightVisible () {
        if(right == null) {
            return true;
        }
        return right.getBlockType().isTransparent();
    }

    public void setRight(Block right) {
        right.getVisibilityController().left = self;
        this.right = right;
    }

    public boolean leftVisible () {
        if(left == null) {
            return true;
        }
        return left.getBlockType().isTransparent();
    }

    public void setLeft(Block left) {
        left.getVisibilityController().right = self;
        this.left = left;
    }
}
