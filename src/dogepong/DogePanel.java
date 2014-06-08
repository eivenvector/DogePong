/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package dogepong;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Ivan
 */
public class DogePanel extends JPanel{
    
    private final int WIDTH = 800, HEIGHT = 450;
    private Image img;
    private DogeKeyboardListener listener;
    private DogeActionListener actListener;
    private final Image doge_1 = new ImageIcon("/Users/Ivan/Documents/NetBeans"
            + "Projects/DogePong/media/doge_1.png").getImage();
    private final Image doge_2 = new ImageIcon("/Users/Ivan/Documents/NetBeans"
            + "Projects/DogePong/media/doge_2.png").getImage();
    private final Image coin = new ImageIcon("/Users/Ivan/Documents/NetBeans"
            + "Projects/DogePong/media/coin.png").getImage();
    
    private int doge_1_y = 180, doge_2_y = 180, doge_1_x = 0, doge_2_x = 715;
    private final int coin_x = 365, coin_y = 190;
    private Timer timer;
    private JButton startButton;
    private DogeCoin coin_obj = new DogeCoin(coin_x, coin_y);
    
    
    
    public DogePanel(String img) {
        this(new ImageIcon(img).getImage());
    }
    
    
    public DogePanel(Image img){
        this.img = img;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        actListener = new DogeActionListener();
        startButton = new JButton("Start");
        timer = new Timer(50, actListener);
        
        listener = new DogeKeyboardListener();
        addKeyListener(listener);
        timer.start();
    }
    
    @Override
    public void paintComponent(Graphics page) {
        super.paintComponent(page);
        page.drawImage(img, 0, 0, null);
        page.drawImage(doge_1, doge_1_x, doge_1_y, null);
        page.drawImage(doge_2, doge_2_x, doge_2_y, null);
        page.drawImage(coin, coin_obj.getX(), coin_obj.getY(), null);
    }
    
    private class DogeKeyboardListener implements KeyListener {
        
        @Override
        public void keyTyped(KeyEvent e) {
            
        }
        
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyChar()) {
                case 'a': dogeMoved(1, 2);
                break;
                case 'q': dogeMoved(1, 1);
                break;
                case 'l': dogeMoved(2, 2);
                break;
                case 'o': dogeMoved(2, 1);
                break;
                default:
                    break;
            }
        }
        
        @Override
        public void keyReleased(KeyEvent e) { }
        
        public void dogeMoved(int doge, int direction) {
            if (doge == 1) {
                if (direction == 1) {
                    doge_1_y -= 10;
                }
                else {
                    doge_1_y += 10;
                }
            }
            else {
                if (direction == 1) {
                    doge_2_y -= 10;
                }
                else {
                    doge_2_y += 10;
                }
            }
            repaint();
        }
        
    }
        
    private class DogeActionListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (coin_obj.hasHitXBounds() == 0) {
                if (!coin_obj.hasHitYBounds()) {
                    if (!coin_obj.hasHitDoge(doge_1_x, doge_1_y) && !coin_obj.hasHitDoge(doge_2_x, doge_2_y)) {
                        coin_obj.setX(coin_obj.getX() + (int)coin_obj.getVelocity()[0]);
                        coin_obj.setY(coin_obj.getY() + (int)coin_obj.getVelocity()[1]);
                    } else if (coin_obj.hasHitDoge(doge_1_x, doge_1_y) || coin_obj.hasHitDoge(doge_2_x, doge_2_y)) {
                        coin_obj.wallCollisionVelocityChange();
                        coin_obj.setX(coin_obj.getX() + (int)coin_obj.getVelocity()[0]);
                        coin_obj.setY(coin_obj.getY() + (int)coin_obj.getVelocity()[1]);
                    }
                } else {
                    coin_obj.wallCollisionVelocityChange();
                    coin_obj.setX(coin_obj.getX() + (int)coin_obj.getVelocity()[0]);
                    coin_obj.setY(coin_obj.getY() + (int)coin_obj.getVelocity()[1]);
                }
                
            } else if (coin_obj.hasHitXBounds() == 1) {
                System.out.println("Player 2 WINS!!");
                timer.stop();
            } else {
                System.out.println("Player 1 WINS!!");
                timer.stop();
            }
            
            repaint();
            
            
        }
        
    }
    
    
    
}

