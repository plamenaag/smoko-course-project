package com.smoko;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Food extends Block {
    private boolean isDynamic;
    private PlayField playField;

    public Food(Point point, PlayField playField) {
        super(point);
        isDynamic = false;
        this.playField = playField;
    }

    public boolean isDynamic() {
        return isDynamic;
    }

    public void setDynamic(boolean dynamic) {
        isDynamic = dynamic;
    }

    public void keepFoodInPlayfield() {
        if (this.getPoint().getX() == playField.getHBlockCount()) {
            this.getPoint().setX(0);
        } else if (this.getPoint().getX() == -1) {
            this.getPoint().setX(playField.getHBlockCount() - 1);
        }

        if (this.getPoint().getY() == playField.getVBlockCount()) {
            this.getPoint().setY(0);
        } else if (this.getPoint().getY() == -1) {
            this.getPoint().setY(playField.getVBlockCount() - 1);
        }
    }

    public void move() {
        if (this.isDynamic) {
            List<Point> possiblePointsToMove = new ArrayList<>();
            possiblePointsToMove.add(new Point(this.getPoint().getX(), this.getPoint().getY() - 1));
            possiblePointsToMove.add(new Point(this.getPoint().getX(), this.getPoint().getY() + 1));
            possiblePointsToMove.add(new Point(this.getPoint().getX() + 1, this.getPoint().getY()));
            possiblePointsToMove.add(new Point(this.getPoint().getX() - 1, this.getPoint().getY()));

            for (int i = possiblePointsToMove.size() - 1; i >= 0; i--) {
                // check if there is a block on the current point coordinates
                if (playField.getBlock(possiblePointsToMove.get(i).getX(), possiblePointsToMove.get(i).getY()) != null) {
                    possiblePointsToMove.remove(i);
                }
            }

            // if the list is not empty we choose random position for the dynamic food block
            if (!possiblePointsToMove.isEmpty()) {
                Random r = new Random();
                int randomPointIndex = r.nextInt(possiblePointsToMove.size());
                Point selectedPoint = possiblePointsToMove.get(randomPointIndex);
                this.setPoint(selectedPoint);
                keepFoodInPlayfield();
            }
        }
    }
}
