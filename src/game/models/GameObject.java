/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.models;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Graphics2D;
/**
 *
 * @author pepek
 */

public class GameObject {
    private int x;
    private int y;
    private BufferedImage obj;
    private int distance;
    private boolean left = true;
    private int pixel = 100;
    public GameObject(int x,int y,String s){
      this.x = x;
      this.y = y;
      this.distance =  x - (x-300); 
        try{
           obj = ImageIO.read(new File(s));
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public void draw(Graphics2D g){
        g.drawImage(obj, x, y, null);
    }
    public BufferedImage getImage(){
        return this.obj;
    }
    public void move(){
        if(left){
            x = x-1;
            pixel--;
            if(pixel == 0)
                left = false;
        }
        else if(!left){
            x = x+1;
            pixel++;
            if(pixel == 100)
                left = true;
        }
    }
    
}
