package com.smoko;

public class Snake {
    private Block head;
    private Block tail;
    private PlayField playField;
    private Block eatenBlock;
    private Block neck;

    public Snake(Block head, Block tail, PlayField playField) {
        this.head = head;
        this.tail = tail;
        this.playField = playField;
        this.neck = tail;

        while (this.neck.getNextBlock() != head) {
            this.neck = this.neck.getNextBlock();
        }
    }

    public Block getHead() {
        return this.head;
    }

    public void setHead(Block head) {
        this.head = head;
    }

    public Block getTail() {
        return this.tail;
    }

    public void setTail(Block tail) {
        this.tail = tail;
    }

    public Block getEatenBlock() {
        return this.eatenBlock;
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

    public boolean canSnakeMoveDown() {
        return this.head.getPoint().getY() + 1 != this.neck.getPoint().getY();
    }

    public boolean canSnakeMoveUp() {
        return this.head.getPoint().getY() - 1 != this.neck.getPoint().getY();
    }

    public boolean canSnakeMoveLeft() {
        return this.head.getPoint().getX() - 1 != this.neck.getPoint().getX();
    }

    public boolean canSnakeMoveRight() {
        return this.head.getPoint().getX() + 1 != this.neck.getPoint().getX();
    }

    // to make sure the snake doesn't disappear somewhere outside the playfield
    public void keepSnakeInPlayfield() {
        if (this.head.getPoint().getX() == this.playField.getHBlockCount()) {
            this.head.getPoint().setX(0);
        } else if (this.head.getPoint().getX() == -1) {
            this.head.getPoint().setX(this.playField.getHBlockCount() - 1);
        }

        if (this.head.getPoint().getY() == this.playField.getVBlockCount()) {
            this.head.getPoint().setY(0);
        } else if (this.head.getPoint().getY() == -1) {
            this.head.getPoint().setY(this.playField.getVBlockCount() - 1);
        }
    }

    public void moveBody() {
        Block blockToMove = this.tail;

        if (this.eatenBlock != null) {
            this.eatenBlock.setPoint(new Point(this.tail.getPoint().getX(), this.tail.getPoint().getY()));
        }
        Block nextBlock;
        do {
            nextBlock = blockToMove.getNextBlock();
            if (nextBlock != null) {
                blockToMove.getPoint().setX(nextBlock.getPoint().getX());
                blockToMove.getPoint().setY(nextBlock.getPoint().getY());
                blockToMove = nextBlock;
            } else {
                break;
            }
        } while (blockToMove != null);

        if (this.eatenBlock != null) {
            this.eatenBlock.setNextBlock(tail);
            this.tail = this.eatenBlock;
            this.eatenBlock = null;
        }
    }

    public void eat(Block block) {
        this.eatenBlock = block;
    }
}
