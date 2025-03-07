package snakegame;

import javax.swing.*;

public class SnakeGame extends JFrame {
    
    SnakeGame() {
        super("Snake Game");
        add(new Board());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();  // Automatically sizes the window based on the Board's preferred size
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SnakeGame());
    }
}
