package com.smoko;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameEngine extends JFrame implements KeyListener {
    private String moveDirection;
    private PlayField playField;
    private Snake snake;
    private Long lastMoveTime;
    private int score;

    public GameEngine(int xSize, int ySize) {
        super();
        this.score = 0;
        this.playField = new PlayField(xSize, ySize);
        this.initializeScreen();
        this.moveDirection = "R";
    }

    private void initializeScreen() {
        int screenWidth = 2 * Constants.BORDER_WIDTH
                + playField.getHBlockCount() * Constants.BLOCK_SIZE
                + playField.getHBlockCount() * 1
                + Constants.FRAME_SIZE * 2
                + 1;

        int screenHeight = 2 * Constants.BORDER_WIDTH
                + playField.getVBlockCount() * Constants.BLOCK_SIZE
                + playField.getVBlockCount() * 1
                + Constants.BAR_HEIGHT
                + 4;

        this.setSize(screenWidth, screenHeight);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addKeyListener(this);
        this.setLocationRelativeTo(null);// center the dialog in the screen
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(Constants.BORDER_WIDTH,
                Constants.BORDER_WIDTH, Constants.BORDER_WIDTH, Constants.BORDER_WIDTH, Color.RED));
    }

    public void run() {
        List<Block> blocks = new ArrayList<>();
        for (int i = 0; i < Constants.START_SNAKE_SIZE; i++) {
            if (i == 0) {
                Block block = new Block(playField.getStartPoint());
                blocks.add(block);
            } else {
                Block nextBlock = blocks.get(i - 1);
                Block block = new Block(nextBlock.getPoint().getX() - 1, nextBlock.getPoint().getY(), nextBlock);
                blocks.add(block);
            }
        }

        snake = new Snake(blocks.get(0), blocks.get(blocks.size() - 1), playField);
        playField.setBlocks(blocks);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(this::runLoop, 0, 16, TimeUnit.MILLISECONDS);
    }

    public void runLoop() {
        this.repaint();

        if (hasMoveTimeElapsed()) {
            switch (moveDirection) {
                case "R":
                    snake.moveRight();
                    break;
                case "L":
                    snake.moveLeft();
                    break;
                case "D":
                    snake.moveDown();
                    break;
                case "U":
                    snake.moveUp();
                    break;
            }

            lastMoveTime = System.currentTimeMillis();
        }
    }

    private boolean hasMoveTimeElapsed() {
        if (lastMoveTime == null || System.currentTimeMillis() - lastMoveTime > 200) {
            return true;
        }
        return false;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (Block block : playField.getBlocks()) {
            int x = Constants.FRAME_SIZE + Constants.BORDER_WIDTH + 1 + block.getPoint().getX() * (Constants.BLOCK_SIZE + 1);
            int y = Constants.BAR_HEIGHT + Constants.BORDER_WIDTH + 1 + block.getPoint().getY() * (Constants.BLOCK_SIZE + 1);
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
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (snake != null && this.canCurrentShapeMoveRight()) {
                this.moveDirection = "R";
            }
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (snake != null && this.canCurrentShapeMoveLeft()) {
                this.moveDirection = "L";
            }
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (snake != null && this.canCurrentShapeMoveDown()) {
                this.moveDirection = "D";
            }
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (snake != null && this.canCurrentShapeMoveDown()) {
                this.moveDirection = "U";
            }
        }
    }

    private boolean canCurrentShapeMoveDown() {
        //TODO implement later
        return true;
    }

    private boolean canCurrentShapeMoveLeft() {
        //TODO implement later
        return true;
    }

    private boolean canCurrentShapeMoveRight() {
        //TODO implement later
        return true;
    }
}