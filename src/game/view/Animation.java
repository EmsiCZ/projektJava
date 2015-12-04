/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.view;
import java.awt.image.*;

/**
 *
 * @author Luboš
 */
public class Animation {
    
    private BufferedImage[] frames;
    private int currentFrame;
    
    private long startTime;
    private long delay;
    
    public Animation(){}
    
    public void setFrames(BufferedImage[] images, boolean loop){
        frames = images;
        if(currentFrame >= frames.length){
            if(loop){
                currentFrame = 0;
            }
            else{
                currentFrame = frames.length - 1;
            }
        }
    }
    
    public void setDelay(long d){
        delay = d;
    }
    
    public void update(boolean loop){
        if(delay == -1) return;
        
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if(elapsed > delay){
            currentFrame++;
            startTime = System.nanoTime();
        }
        if(currentFrame == frames.length){
            if(loop){
               currentFrame = 0;  
            }
            else{
               currentFrame = frames.length - 1;
            }
            
        }
    }
    
    public BufferedImage getImage(){
        return frames[currentFrame];
    }
    
    public boolean isLastFrame(){
        return currentFrame == frames.length - 1;
    }
    
    
}
