/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package dogepong;

import javax.swing.JFrame;

public class DogePong {
    
    
    
    public static void main(String[] args) throws InterruptedException {
       
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        DogePanel dPanel = new DogePanel("/Users/Ivan/Documents/NetBeans"
                + "Projects/DogePong/media/south_park_elementary.png");
        
        dPanel.setFocusable(true);
        dPanel.requestFocusInWindow();
        
        frame.getContentPane().add(dPanel);
        frame.pack();
        frame.setVisible(true);
        
        
    }
    
}