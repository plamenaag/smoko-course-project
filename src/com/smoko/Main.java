package com.smoko;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        GameEngine gameEngine = new GameEngine(50,30);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gameEngine.run();
            }
        });
    }
}
