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

    public List<Block> getPositionBlockCount(int x, int y) {
        List<Block> blockList = new ArrayList<>();

        for (Block bl : getBlocks()) {
            if (bl.getPoint().getY() == y && bl.getPoint().getX() == x) {
                blockList.add(bl);
            }
        }

        return blockList;
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
        int result = this.vBlockCount / 2;

        return 0;
    }
}
