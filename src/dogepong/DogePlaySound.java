/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package dogepong;

import java.applet.*;
import java.net.*;

/**
 *
 * @author Ivan
 */
public class DogePlaySound {
    public static void dogePlay(String url) throws InterruptedException
    {
        
        try
        {
            long delay = 0;
            AudioClip clip = Applet.newAudioClip(new URL(url));
            clip.play();
            Thread.sleep(delay);
            
        } catch (MalformedURLException murle) {
            System.out.println(murle);
        }
    }
}
