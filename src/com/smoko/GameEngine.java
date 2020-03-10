package com.smoko;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameEngine extends JFrame implements KeyListener {
    private String moveDirection;
    private PlayField playField;
    private Snake snake;
    private Food food;
    private Long snakeLastMoveTime;
    private Long foodLastMoveTime;
    private int score;
    private ScheduledExecutorService executor;
    private List<Block> obstacleBlocks;
    private boolean isPaused;

    public GameEngine(int xSize, int ySize) {
        super();
        this.score = 0;
        this.playField = new PlayField(xSize, ySize);
        this.initializeScreen();
        this.moveDirection = "R";
        this.isPaused = false;
    }

    private void initializeScreen() {
        int screenWidth = 2 * Constants.BORDER_WIDTH
                + this.playField.getHBlockCount() * Constants.BLOCK_SIZE
                + this.playField.getHBlockCount() * 1
                + Constants.FRAME_SIZE * 2
                + 1;

        int screenHeight = 2 * Constants.BORDER_WIDTH
                + this.playField.getVBlockCount() * Constants.BLOCK_SIZE
                + this.playField.getVBlockCount() * 1
                + Constants.BAR_HEIGHT
                + 1
                + 3 + 60;

        this.setSize(screenWidth, screenHeight);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLayout(null);
        this.addKeyListener(this);
        this.setLocationRelativeTo(null);// center the dialog in the screen
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(Constants.BORDER_WIDTH,
                Constants.BORDER_WIDTH, Constants.BORDER_WIDTH, Constants.BORDER_WIDTH, Color.RED));
    }

    public void run() {
        this.moveDirection = "R";
        this.score = 0;
        this.food = null;
        this.isPaused = false;

        if (executor != null) {
            executor.shutdownNow();
        }

        List<Block> blocks = new ArrayList<>();
        for (int i = 0; i < Constants.START_SNAKE_SIZE; i++) {
            if (i == 0) {
                //create head of snake
                Point startPoint = playField.getStartPoint();
                Block block = new Block(startPoint);
                blocks.add(block);
            } else {
                Block nextBlock = blocks.get(i - 1);
                Block block = new Block(nextBlock.getPoint().getX() - 1, nextBlock.getPoint().getY(), nextBlock);
                blocks.add(block);
            }
        }

        this.snake = new Snake(blocks.get(0), blocks.get(blocks.size() - 1), this.playField);
        this.playField.setBlocks(blocks);


        this.playField.getBlocks().addAll(createObstacleBlocks());

        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(this::runLoop, 0, 100, TimeUnit.MILLISECONDS);
    }

    public void runLoop() {
        this.repaint();

        if (this.isPaused) {
            return;
        }

        if (this.food == null) {
            this.food = createRandomFoodBlock();
            this.playField.getBlocks().add(food);
        }

        if (hasSnakeMoveTimeElapsed()) {
            switch (this.moveDirection) {
                case "R":
                    this.snake.moveRight();
                    break;
                case "L":
                    this.snake.moveLeft();
                    break;
                case "D":
                    this.snake.moveDown();
                    break;
                case "U":
                    this.snake.moveUp();
                    break;
            }

            if (!tryToEatFood()) {
                List<Block> blocks = this.playField.getBlocksByPosition(this.snake.getHead().getPoint().getX(), this.snake.getHead().getPoint().getY());
                blocks.remove(this.snake.getHead());
                if (!blocks.isEmpty()) {
                    executor.shutdownNow();
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "GAME OVER!!! Score: " + this.score);
                    this.repaint();
                    return;
                }

                if (this.food != null && this.food.isDynamic() && hasFoodMoveTimeElapsed()) {
                    this.food.move();
                    this.foodLastMoveTime = System.currentTimeMillis();
                }

                if (this.score >= 300) {
                    executor.shutdown();
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "You are a winner");
                    this.repaint();
                    return;
                }
            }

            this.snakeLastMoveTime = System.currentTimeMillis();
        }
    }

    private List<Block> createObstacleBlocks() {
        this.obstacleBlocks = new ArrayList<>();
        this.obstacleBlocks.add(new Block(30, 25));
        this.obstacleBlocks.add(new Block(30, 24));
        this.obstacleBlocks.add(new Block(30, 23));
        this.obstacleBlocks.add(new Block(30, 22));
        this.obstacleBlocks.add(new Block(30, 21));
        this.obstacleBlocks.add(new Block(30, 20));

        this.obstacleBlocks.add(new Block(4, 3));
        this.obstacleBlocks.add(new Block(5, 3));
        this.obstacleBlocks.add(new Block(6, 3));
        this.obstacleBlocks.add(new Block(7, 3));
        this.obstacleBlocks.add(new Block(8, 3));
        this.obstacleBlocks.add(new Block(9, 3));

        return this.obstacleBlocks;
    }

    private boolean tryToEatFood() {
        if (this.snake.getHead().getPoint().getX() == this.food.getPoint().getX()
                && this.snake.getHead().getPoint().getY() == this.food.getPoint().getY()) {
            this.snake.eat(food);

            if (this.food.isDynamic()) {
                this.score += 15;
            } else {
                this.score += 10;
            }

            this.food = null;
            return true;
        }

        return false;
    }

    private Food createRandomFoodBlock() {
        Random r = new Random();
        List<Point> emptyPoints = this.playField.getEmptyPoints();
        int randomPointIndex = r.nextInt(emptyPoints.size());
        Point point = emptyPoints.get(randomPointIndex);
        Food food = new Food(point, this.playField);
        if (randomPointIndex % 2 == 0) {
            food.setDynamic(true);
        }

        return food;
    }

    private boolean hasSnakeMoveTimeElapsed() {
        if (this.snakeLastMoveTime == null || System.currentTimeMillis() - this.snakeLastMoveTime > 200) {
            return true;
        }
        return false;
    }

    private boolean hasFoodMoveTimeElapsed() {
        if (this.foodLastMoveTime == null || System.currentTimeMillis() - this.foodLastMoveTime > 400) {
            return true;
        }
        return false;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setFont(new Font("arial", Font.PLAIN, 14));
        g.drawString("Scores: " + score, 40, 55);
        if (this.isPaused) {
            g.drawString("Press 'SPACE' to resume", 150, 55);
        } else {
            g.drawString("Press 'SPACE' to pause", 150, 55);
        }

        g.drawString("Press 'R' to restart", 350, 55);

        int lineY = Constants.BAR_HEIGHT + Constants.BORDER_WIDTH + 55;
        g.setColor(Color.red);
        g.fillRect(0, lineY, this.getWidth(), 5);
        g.setColor(Color.BLACK);

        for (Block block : this.playField.getBlocks()) {
            int x = Constants.FRAME_SIZE + Constants.BORDER_WIDTH + 1 + block.getPoint().getX() * (Constants.BLOCK_SIZE + 1);
            int y = Constants.BAR_HEIGHT + Constants.BORDER_WIDTH + 1 + block.getPoint().getY() * (Constants.BLOCK_SIZE + 1) + 60;
            if (block.equals(this.food)) {
                g.setColor(Color.GREEN);
            } else if (this.obstacleBlocks.contains(block)) {
                g.setColor(Color.BLUE);
            } else {
                g.setColor(Color.BLACK);
            }
            g.fillRect(x, y, Constants.BLOCK_SIZE, Constants.BLOCK_SIZE);

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            this.isPaused = !isPaused;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (this.snake != null && this.snake.canSnakeMoveRight()) {
                this.moveDirection = "R";
            }
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (this.snake != null && this.snake.canSnakeMoveLeft()) {
                this.moveDirection = "L";
            }
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (this.snake != null && this.snake.canSnakeMoveDown()) {
                this.moveDirection = "D";
            }
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (this.snake != null && this.snake.canSnakeMoveUp()) {
                this.moveDirection = "U";
            }
        } else if (e.getKeyCode() == KeyEvent.VK_R) {
            run();
        }
    }
}
