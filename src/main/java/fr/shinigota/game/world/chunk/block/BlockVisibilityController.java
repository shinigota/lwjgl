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

    private boolean visibleAndDifferent(Block block) {
        // self side tested is visible if block is null
        if (block == null) {
            return true;
        }

        // both block solids, merge them, contact side not visible
        if ( self.getBlockType().isOpaque() && block.getBlockType().isOpaque() ) {
            return false;
        }

        // both block liquids, merge them, contact side not visible
        if ( self.getBlockType().isLiquid() && block.getBlockType().isLiquid() ) {
            return false;
        }

        // self is liquid, other is solid, hide self face
        if ( self.getBlockType().isSemiTransparent() && block.getBlockType().isOpaque() ) {
            return false;
        }

        return true;
    }

    public boolean topVisible () {
        return visibleAndDifferent(top);
    }

    public void setTop(Block top) {
        top.getVisibilityController().bottom = self;
        this.top = top;
    }

    public boolean bottomVisible () {
        return visibleAndDifferent(bottom);
    }

    public void setBottom(Block bottom) {
        bottom.getVisibilityController().top = self;
        this.bottom = bottom;
    }

    public boolean frontVisible () {
        return visibleAndDifferent(front);
    }

    public void setFront(Block front) {
        front.getVisibilityController().back = self;
        this.front = front;
    }

    public boolean backVisible () {
        return visibleAndDifferent(back);
    }

    public void setBack(Block back) {
        back.getVisibilityController().front = self;
        this.back = back;
    }

    public boolean rightVisible () {
        return visibleAndDifferent(right);
    }

    public void setRight(Block right) {
        right.getVisibilityController().left = self;
        this.right = right;
    }

    public boolean leftVisible () {
        return visibleAndDifferent(left);
    }

    public void setLeft(Block left) {
        left.getVisibilityController().right = self;
        this.left = left;
    }
}
