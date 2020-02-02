package com.smoko;

public class Block {
    private Point point;
    private Block nextBlock;

    public Block() {

    }

    public Block(int x, int y, Block nextBlock) {
        this(new Point(x, y), nextBlock);
    }

    public Block(Point point) {
        this(point, null);
    }

    public Block(int x, int y) {
        this(new Point(x, y), null);
    }

    public Block(Point point, Block nextBlock) {
        this.point = point;
        this.nextBlock = nextBlock;
    }

    public Block getNextBlock() {
        return nextBlock;
    }

    public void setNextBlock(Block nextBlock) {
        this.nextBlock = nextBlock;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
