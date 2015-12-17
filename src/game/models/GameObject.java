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
    private boolean left;
    
    public GameObject(int x,int y, boolean left, String s){
      this.x = x;
      this.y = y;
      this.left = left;
        
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
    public boolean isLeft(){
        return left;
    }
    public void setX(int move){
        x+=move;
    }
    
}
