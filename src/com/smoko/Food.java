package com.smoko;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Food extends Block {
    private boolean isDynamic;
    private PlayField playField;

    public Food(Point point, PlayField playField) {
        super(point);
        this.isDynamic = false;
        this.playField = playField;
    }

    public boolean isDynamic() {
        return this.isDynamic;
    }

    public void setDynamic(boolean dynamic) {
        this.isDynamic = dynamic;
    }


    public void move() {
        if (this.isDynamic) {
            List<Point> possiblePointsToMove = new ArrayList<>();

            if (this.getPoint().getY() - 1 >= 0) {
                possiblePointsToMove.add(new Point(this.getPoint().getX(), this.getPoint().getY() - 1));
            }

            if (this.getPoint().getY() + 1 < this.playField.getVBlockCount()) {
                possiblePointsToMove.add(new Point(this.getPoint().getX(), this.getPoint().getY() + 1));
            }

            if (this.getPoint().getX() + 1 < this.playField.getHBlockCount()) {
                possiblePointsToMove.add(new Point(this.getPoint().getX() + 1, this.getPoint().getY()));
            }

            if (this.getPoint().getX() - 1 >= 0) {
                possiblePointsToMove.add(new Point(this.getPoint().getX() - 1, this.getPoint().getY()));
            }

            for (int i = possiblePointsToMove.size() - 1; i >= 0; i--) {
                // check if there is a block on the current point coordinates
                Block blockAtPoint = this.playField.getBlock(possiblePointsToMove.get(i).getX(), possiblePointsToMove.get(i).getY());
                if (blockAtPoint != null) {
                    possiblePointsToMove.remove(i);
                }
            }

            // if the list is not empty we choose random position for the dynamic food block
            if (!possiblePointsToMove.isEmpty()) {
                Random r = new Random();
                int randomPointIndex = r.nextInt(possiblePointsToMove.size());
                Point selectedPoint = possiblePointsToMove.get(randomPointIndex);
                this.setPoint(selectedPoint);
            }
        }
    }
}
