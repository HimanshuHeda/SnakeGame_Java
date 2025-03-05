package snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener {
    
    private Image apple;
    private Image dot;
    private Image head;
    
    private final int ALL_DOTS = 900;
    private final int DOT_SIZE = 10;
    private final int RANDOM_POSITION = 29;
    
    private int apple_x;
    private int apple_y;
    
    private final int x[] = new int[ALL_DOTS];
    private final int y[] = new int[ALL_DOTS];
    
    private int dots;
    private Timer timer;
    
    Board(){
        setBackground(Color.BLACK);
        setFocusable(true);
        
        loadImages();
        initGame();
    }
    
    public void loadImages(){
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("snakeGame/icons/apple.png"));
        apple = i1.getImage();
        
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("snakeGame/icons/dot.png"));
        dot = i2.getImage();
        
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("snakeGame/icons/head.png"));
        head = i3.getImage();
    }
    
    public void initGame(){
        dots = 3;
        
        for (int i = 0 ; i < dots ; i++){
            y[i] = 50;
            x[i] = 50 - i * DOT_SIZE;
            
        }
        
        locateApple();
        
        timer = new Timer(140, this);
        timer.start();
        
    }
    
    public void locateApple() {
        int r = (int)(Math.random() * RANDOM_POSITION);
        apple_x = r * DOT_SIZE;
                
        r = (int)(Math.random() * RANDOM_POSITION);
        apple_y = r * DOT_SIZE;
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        draw(g);
    }
    public void draw(Graphics g){
        g.drawImage(apple, apple_x, apple_y, this);
        
        for (int i = 0; i < dots; i++){
            if (i == 0) {
                g.drawImage(head, x[i], y[i], this);
            }
            else {
                g.drawImage(dot, x[i], y[i], this);
            }
        }
        
        // Initialize it with Default Toolkit 
        Toolkit.getDefaultToolkit().sync();
    }
    
    public void move(){
        for (int i = dots; i > 0; i--){
            x[i] = x[i - 1];
            y[i] = y[i - 1];
            
        }
        
        x[0] += DOT_SIZE;
        y[0] += DOT_SIZE;
    }
    
    public void actionPerformed(ActionEvent ae){
        move();
        
        repaint();
    }
}
