package com.smoko;

public class Snake {
    private Block head;
    private Block tail;
    private PlayField playField;

    public Snake(Block head, Block tail, PlayField playField) {
        this.head = head;
        this.tail = tail;
        this.playField = playField;
    }

    public Block getHead() {
        return head;
    }

    public void setHead(Block head) {
        this.head = head;
    }

    public Block getTail() {
        return tail;
    }

    public void setTail(Block tail) {
        this.tail = tail;
    }

    public void moveDown() {
        moveBody();
        this.head.getPoint().setY(this.head.getPoint().getY() + 1);
        keepSnakeInPlayfield();
    }

    public void moveUp() {
        moveBody();
        this.head.getPoint().setY(this.head.getPoint().getY() - 1);
        keepSnakeInPlayfield();
    }

    public void moveLeft() {
        moveBody();
        this.head.getPoint().setX(this.head.getPoint().getX() - 1);
        keepSnakeInPlayfield();
    }

    public void moveRight() {
        moveBody();
        this.head.getPoint().setX(this.head.getPoint().getX() + 1);
        keepSnakeInPlayfield();
    }

    public void keepSnakeInPlayfield() {
        if (this.head.getPoint().getX() == playField.getHBlockCount()) {
            this.head.getPoint().setX(0);
        } else if (this.head.getPoint().getX() == -1) {
            this.head.getPoint().setX(playField.getHBlockCount() - 1);
        }

        if (this.head.getPoint().getY() == playField.getVBlockCount()) {
            this.head.getPoint().setY(0);
        } else if (this.head.getPoint().getY() == -1) {
            this.head.getPoint().setY(playField.getVBlockCount() - 1);
        }
    }

    public void moveBody() {
        Block blockToMove = tail;

        do {
            Block nextBlock = blockToMove.getNextBlock();
            if (nextBlock != null) {

                blockToMove.getPoint().setX(nextBlock.getPoint().getX());
                blockToMove.getPoint().setY(nextBlock.getPoint().getY());
                blockToMove = nextBlock;
            } else {
                break;
            }
        } while (blockToMove != null);
    }
}
