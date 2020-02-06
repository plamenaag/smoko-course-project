package com.smoko;

import java.util.ArrayList;
import java.util.List;

public class PlayField {
    private final int hBlockCount;
    private final int vBlockCount;
    private List<Block> blocks;

    public PlayField(int hBlockCount, int vBlockCount) {
        this.hBlockCount = hBlockCount;
        this.vBlockCount = vBlockCount;
        this.blocks = new ArrayList<>();
    }

    public int getHBlockCount() {
        return hBlockCount;
    }

    public int getVBlockCount() {
        return vBlockCount;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }

    public Block getBlock(int x, int y) {
        Block block = null;
        for (Block bl : getBlocks()) {
            if (bl.getPoint().getY() == y && bl.getPoint().getX() == x) {
                block = bl;
            }
        }

        return block;
    }

    public List<Block> getBlocksByPosition(int x, int y) {
        List<Block> blockList = new ArrayList<>();

        for (Block bl : getBlocks()) {
            if (bl.getPoint().getY() == y && bl.getPoint().getX() == x) {
                blockList.add(bl);
            }
        }

        return blockList;
    }

    // returns a list of empty points (where there's no blocks)
    public List<Point> getEmptyPoints() {
        // check if at given coords on the field there is a block
        List<Point> points = new ArrayList<>();
        for (int y = 0; y < this.getVBlockCount(); y++) {
            for (int x = 0; x < this.getHBlockCount(); x++) {
                Block block = this.getBlock(x, y);
                if (block == null) {
                    Point point = new Point(x, y);
                    points.add(point);
                }
            }
        }

        return points;
    }

    public Point getStartPoint() {
        Point point = new Point(getStartPointX(), getStartPointY());
        return point;
    }

    public int getStartPointX() {
        int result = this.hBlockCount / 2;
        return result;
    }

    public int getStartPointY() {
        return 0;
    }
}
