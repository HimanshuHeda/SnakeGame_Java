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
    
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    
    private int dots;
    private Timer timer;
    
    Board(){
        addKeyListener(new TAdepter());
        
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
        
        if(leftDirection) {
            x[0] = x[0] - DOT_SIZE;
        }
        if(rightDirection) {
            x[0] = x[0] + DOT_SIZE;
        }
        if(upDirection) {
            y[0] = y[0] - DOT_SIZE;
        }
        if(downDirection) {
            y[0] = y[0] + DOT_SIZE;
        }
    }
    
    public void actionPerformed(ActionEvent ae){
        move();
        
        repaint();
    }
    
    public class TAdepter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            
            if (key == KeyEvent.VK_LEFT && (!rightDirection)){
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            
            if (key == KeyEvent.VK_RIGHT && (!leftDirection)){
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            
            if (key == KeyEvent.VK_UP && (!downDirection)){
                upDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
            
            if (key == KeyEvent.VK_DOWN && (!upDirection)){
                downDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
        }
    }
}
