/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package dogepong;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Ivan
 */

public class DogePanel extends JPanel{
    
    private final int WIDTH = 800, HEIGHT = 450;
    private Image img;
    private DogeKeyboardListener listener;
    private DogeActionListener actListener;
    private DogeButtonActionListener btnListener;
    private DogeAdventureSoundPlayer sndListener;
    private final Image doge_1 = new ImageIcon("media/doge_1.png").getImage();
    private final Image doge_2 = new ImageIcon("media/doge_2.png").getImage();
    private final Image coin = new ImageIcon("media/coin.png").getImage();
    private final ImageIcon start_button_img = new ImageIcon("media/start_button.png");
    private final ImageIcon resume_button_img = new ImageIcon("media/resume_button.png");
    private final ImageIcon exit_button_img = new ImageIcon("media/exit_button.png");
    private final String advenString = "file:media/doge_adven.wav";
    private int doge_1_y = 180, doge_2_y = 180, doge_1_life = 3, doge_2_life = 3;
    private final int coin_x = 365, coin_y = 190, doge_1_x = 0, doge_2_x = 715;
    private Timer timer, soundTimer;
    private JButton startButton, exitButton;
    private DogeCoin coin_obj = new DogeCoin(coin_x, coin_y);
    private JTextArea ta;
    
    public DogePanel(String img) {
        this(new ImageIcon(img).getImage());
    }
    
    
    public DogePanel(Image img){
        ta = new JTextArea(4, 40);
        ta.setEditable(false);
        ta.setVisible(false);
        ta.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
        ta.setBackground(Color.yellow);
        
        add(ta);
        this.img = img;
        
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        actListener = new DogeActionListener();
        btnListener = new DogeButtonActionListener();
        sndListener = new DogeAdventureSoundPlayer();
        
        startButton = new JButton("Start", start_button_img);
        startButton.setBorder(null);
        startButton.addActionListener(btnListener);
        add(startButton);

        exitButton = new JButton("Exit", exit_button_img);
        exitButton.setBorder(null);
        exitButton.addActionListener(btnListener);
        add(exitButton);
        exitButton.setVisible(false);
        
        timer = new Timer(50, actListener);
        timer.setInitialDelay(2000);
        
        soundTimer = new Timer(5650, sndListener);
        soundTimer.setInitialDelay(0);

        
        listener = new DogeKeyboardListener();
        addKeyListener(listener);        
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
                    if (doge_1_y > 0) {
                    doge_1_y -= 10;
                    } else { }
                }
                else {
                    if (doge_1_y < 360) {
                    doge_1_y += 10;
                    } else { }
                }
            }
            else {
                if (direction == 1) {
                    if (doge_2_y > 0) {
                    doge_2_y -= 10;
                    } else { }
                }
                else {
                    if (doge_2_y < 360) {
                    doge_2_y += 10;
                    } else { }
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
                        coin_obj.setX(coin_obj.getX() + (int)coin_obj.getVelocity()[0]);
                        coin_obj.setY(coin_obj.getY() + (int)coin_obj.getVelocity()[1]);
                    }
                } else {
                    coin_obj.wallCollisionVelocityChange();
                    coin_obj.setX(coin_obj.getX() + (int)coin_obj.getVelocity()[0]);
                    coin_obj.setY(coin_obj.getY() + (int)coin_obj.getVelocity()[1]);
                }
                
            } else if (coin_obj.hasHitXBounds() == 1) {
                doge_1_life --;
                hitXWall(1);
                timer.stop();
            } else {
                doge_2_life --;
                hitXWall(2);
                timer.stop();
            }
            
            repaint();
            
            
        }
        
        private void hitXWall(int side) {
            if (doge_1_life > 0 && doge_2_life > 0) {
                if (side == 1) {
                    String scoreUpdate = "     much Lives Update\n" +
                            "          Doge 1: such " + doge_1_life +
                            "\n          Doge 2: amaze " + doge_2_life +
                            "\n          Press Resume to Play";
                    ta.setText(scoreUpdate);
                    ta.setVisible(true); 
                    startButton.setText("     ");
                    startButton.setActionCommand("Resume");
                    startButton.setIcon(resume_button_img);
                    startButton.setBorder(null);
                    startButton.setVisible(true);
                } else {
                    String scoreUpdate = "     much Lives Update\n" +
                            "          Doge 1: such " + doge_1_life +
                            "\n          Doge 2: amaze " + doge_2_life +
                            "\n          Press Resume to Play";
                    startButton.setText("     ");
                    ta.setText(scoreUpdate);
                    ta.setVisible(true);
                    startButton.setActionCommand("Resume");
                    startButton.setIcon(resume_button_img);
                    startButton.setBorder(null);
                    startButton.setVisible(true);
                }            
            } else {
                if (doge_1_life == 0) {
                    String endingMessage = "  such amaze\n\n     doge 2 such winner"
                            + "\n     such wow"
                            + "\n                   so winner"
                            + "\n such alpha";
                    ta.setText(endingMessage);
                    ta.setVisible(true);
                    exitButton.setVisible(true);
                } else {
                    String endingMessage2 = "  such amaze\n\n     doge 1 such winner"
                            + "\n     such wow"
                            + "\n                   so winner"
                            + "\n such alpha";
                    ta.setText(endingMessage2);
                    ta.setVisible(true);
                    exitButton.setVisible(true);
                }
            }
            
        }
        
    }
    private class DogeButtonActionListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Start")) {
                soundTimer.start();
                startButton.setVisible(false);
                timer.start();
            } else if (e.getActionCommand().equals("Resume")) {
                coin_obj = new DogeCoin(coin_x, coin_y);
                startButton.setVisible(false);
                ta.setVisible(false);
                timer.start();
            } else if (e.getActionCommand().equals("Exit")) {
                System.exit(0);
            }
            
        }
        
        
    }
    
    private class DogeAdventureSoundPlayer implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                DogePlaySound.dogePlay(advenString);
            } catch (InterruptedException ex) {
                System.err.println(ex);
            }
        }
        
    }
    
    
}

