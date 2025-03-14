package snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener {
    
    private Image apple, dot, head;
    private final int ALL_DOTS = 900;
    private final int DOT_SIZE = 10;
    private final int RANDOM_POSITION = 29;
    private final int BOARD_WIDTH = 300;
    private final int BOARD_HEIGHT = 300;
    
    private int apple_x, apple_y;
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];
    
    private boolean leftDirection = false, rightDirection = true;
    private boolean upDirection = false, downDirection = false, inGame = true;
    private int dots;
    private Timer timer;
    
    Board() {
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        
        loadImages();
        initGame();
    }
    
    public void loadImages() {
        apple = new ImageIcon(getClass().getResource("/snakegame/icons/apple.png")).getImage();
        dot = new ImageIcon(getClass().getResource("/snakegame/icons/dot.png")).getImage();
        head = new ImageIcon(getClass().getResource("/snakegame/icons/head.png")).getImage();
    }
    
    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            y[i] = 50;
            x[i] = 50 - i * DOT_SIZE;
        }
        
        locateApple();
        timer = new Timer(140, this);
        timer.start();
    }
    
    public void locateApple() {
        apple_x = (int) (Math.random() * RANDOM_POSITION) * DOT_SIZE;
        apple_y = (int) (Math.random() * RANDOM_POSITION) * DOT_SIZE;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (inGame) {
            draw(g);
        } else {
            gameOver(g);
            return;  // Prevents further drawing after Game Over
        }
    }
    
    public void draw(Graphics g) {
        g.drawImage(apple, apple_x, apple_y, this);
        
        for (int i = 0; i < dots; i++) {
            if (i == 0) {
                g.drawImage(head, x[i], y[i], this);
            } else {
                g.drawImage(dot, x[i], y[i], this);
            }
        }
        
        Toolkit.getDefaultToolkit().sync();
    }
    
    public void move() {
        for (int i = dots - 1; i > 0; i--) { 
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        
        if (leftDirection) x[0] -= DOT_SIZE;
        if (rightDirection) x[0] += DOT_SIZE;
        if (upDirection) y[0] -= DOT_SIZE;
        if (downDirection) y[0] += DOT_SIZE;
    }
    
    public void checkApple() {
        if (x[0] == apple_x && y[0] == apple_y) {
            dots++;
            locateApple();
        }
    }
    
    public void checkCollision() {
        for (int i = dots - 1; i > 0; i--) { 
            if (i > 3 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
        }

        if (x[0] >= BOARD_WIDTH - DOT_SIZE || x[0] < 0 || y[0] >= BOARD_HEIGHT - DOT_SIZE || y[0] < 0) {
            inGame = false;
        }

        if (!inGame) {
            timer.stop();
        }
    }
    
    public void gameOver(Graphics g) {
        String msg = "Game Over!";
        Font font = new Font("Helvetica", Font.BOLD, 20);
        FontMetrics metrics = getFontMetrics(font);

        g.setColor(Color.RED);
        g.setFont(font);
        g.drawString(msg, (BOARD_WIDTH - metrics.stringWidth(msg)) / 2, BOARD_HEIGHT / 2);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (inGame) {
            checkApple();
            move();
            checkCollision();
        }
        repaint();
    }
    
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            
            if (key == KeyEvent.VK_LEFT && !rightDirection) {
                leftDirection = true; upDirection = false; downDirection = false;
            }
            if (key == KeyEvent.VK_RIGHT && !leftDirection) {
                rightDirection = true; upDirection = false; downDirection = false;
            }
            if (key == KeyEvent.VK_UP && !downDirection) {
                upDirection = true; leftDirection = false; rightDirection = false;
            }
            if (key == KeyEvent.VK_DOWN && !upDirection) {
                downDirection = true; leftDirection = false; rightDirection = false;
            }
        }
    }
}
