/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.models;
import game.view.Animation;
import game.view.GamePanel;
import static game.view.GamePanel.WIDTH;
import game.view.TileMap;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author Luboš
 */
public class Player {
    
    private double x;
    private double y;
    private double dx;
    private double dy;
    
    private int width;
    private int height;
    
    private boolean left;
    private boolean right;
    private boolean jumping;
    private boolean falling;
    
    private double moveSpeed;
    private double maxSpeed;
    private double maxFallingSpeed;
    private double stopSpeed;
    private double jumpStart;
    private double gravity;
    
    private TileMap tileMap;
   // private BufferedImage idle;
    
    private boolean topLeft;
    private boolean topRight;
    private boolean bottomLeft;
    private boolean bottomRight;
    
    private Animation animation;
    private BufferedImage[] idleNinja;
    private BufferedImage[] runningNinja;
    private BufferedImage[] jumpingNinja;
    private BufferedImage[] fallingNinja;
    private boolean facingRight;
    
    
    public Player(TileMap tm){
            
       tileMap = tm;
       
       width = 26;
       height = 50;
       
       /*moveSpeed = 0.6;
       maxSpeed = 4.2;
       maxFallingSpeed = 12;
       stopSpeed = 0.3;
       jumpStart = -11.0;
       gravity = 0.64;*/
       
       moveSpeed = 0.6;
       maxSpeed = 4.2;
       maxFallingSpeed = 12;
       stopSpeed = 0.3;
       jumpStart = -16.0;
       gravity = 0.64;
       
       try{
           
           idleNinja = new BufferedImage[10];
           jumpingNinja = new BufferedImage[1];
           fallingNinja = new BufferedImage[1];
           runningNinja = new BufferedImage[10];
           
           
           width = 41;
           height = 55;
           jumpingNinja[0] = ImageIO.read(new File("src/game/graphics/player/falling.png"));
           fallingNinja[0] = ImageIO.read(new File("src/game/graphics/player/falling.png"));
           
           BufferedImage image = ImageIO.read(new File("src/game/graphics/player/idle.png"));
           width = 26;
           height = 50;
           for (int i = 0; i < runningNinja.length; i++) {
               idleNinja[i] = image.getSubimage(i * width + i, 0, width, height);
           }
           
           image = ImageIO.read(new File("src/game/graphics/player/running.png"));
           width = 41;
           height = 52;
           for (int i = 0; i < runningNinja.length; i++) {
               runningNinja[i] = image.getSubimage(i * width + i, 0, width, height);
           }
           
           
       }
       catch(Exception e){
           e.printStackTrace();
       }
       
       animation = new Animation();
       facingRight = true;
       
    }
    public void setx(int i){ x = i; }
    public void sety(int i){ y = i; }
    
    public void setLeft(boolean b){ left = b; }
    public void setRight(boolean b){ right = b; }
    public void setJumping(boolean b){
        if(!falling){
            jumping = true;
        }
    }
    
    public void loadPlayer(String s){
        
        try{
            //idle = ImageIO.read(new File(s));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void calculateCorners(double x, double y){
        
        int leftTile = tileMap.getColTile((int) (x - width / 2));
        int rightTile = tileMap.getColTile((int) (x + width / 2) - 1); //-1 for out of bounds
        int topTile = tileMap.getRowTile((int) (y - height / 2));
        int bottomTile = tileMap.getRowTile((int) (y + height / 2) - 1);
        topLeft = tileMap.isBlocked(topTile, leftTile) || x < 0;
        topRight = tileMap.isBlocked(topTile, rightTile);
        bottomLeft = tileMap.isBlocked(bottomTile, leftTile) || x < 0;
        bottomRight = tileMap.isBlocked(bottomTile, rightTile);
        
    }
    ////////////////////////////////////////////
    
    public void update(){
        
        //determine next position
        if(left){
            dx -= moveSpeed;
            if(dx < -maxSpeed){
                dx = -maxSpeed;
            }
        }
        else if(right){
            dx += moveSpeed;
            if(dx > maxSpeed){
                dx = maxSpeed;
            }
        }
        else{
            if(dx > 0){
                dx -= stopSpeed;
                if(dx < 0){
                    dx = 0;
                }
            }
            else if(dx < 0){
                dx += stopSpeed;
                if(dx > 0){
                    dx = 0;
                }
            }
        }
        
        if(jumping){
           dy = jumpStart;
           falling = true;
           jumping = false;
        }
        
        if(falling){
            dy += gravity;
            if(dy > maxFallingSpeed){
                dy = maxFallingSpeed;
            }
        }
        else{
            dy = 0;
        }
        
        //check collisions
        int currCol = tileMap.getColTile((int) x);
        int currRow = tileMap.getRowTile((int) y);
        
        double tox = x + dx;
        double toy = y + dy;
        double tempx = x;
        double tempy = y;
        
        calculateCorners(x, toy);
        if(dy < 0){
            if(topLeft || topRight){
                dy = 0;
                tempy = currRow * tileMap.getTileSize() + height / 2;
            }
            else{
                tempy += dy;
            }
        }
        if(dy > 0){
            if(bottomLeft || bottomRight){
                dy = 0;
                falling = false;
                tempy = (currRow + 1) * tileMap.getTileSize() - height / 2;
            }
            else{
                tempy += dy;
            }
        }
        
        calculateCorners(tox, y);
        if(dx < 0){
            if(bottomLeft || topLeft){
                dx = 0;
                tempx = currCol * tileMap.getTileSize() + width / 2;
            }
            else{
                tempx += dx;
            }
        }
        if(dx > 0){
            if(bottomRight || topRight){
                dx = 0;
                tempx = (currCol + 1) * tileMap.getTileSize() - width / 2;
            }
            else{
                tempx += dx;
            }
        }
        
        if(!falling){ 
            calculateCorners(x, y + 1); //check cliff 1 pixel bellow
            if(!bottomLeft && !bottomRight){
                falling = true;
            }
        }
        
        x = tempx;
        y = tempy;
        
        //move the map
        tileMap.setx((int) (GamePanel.WIDTH / 2 - x));
        tileMap.sety((int) (GamePanel.HEIGHT / 2 - y));
        
        //create animations
        if(left || right){
            animation.setFrames(runningNinja);
            animation.setDelay(50);
        }
        else{
            animation.setFrames(idleNinja);
            animation.setDelay(50);
        }
        if(dy < 0){
            animation.setFrames(jumpingNinja);
            animation.setDelay(-1);
        }
        if(dy > 0){
            animation.setFrames(fallingNinja);
            animation.setDelay(-1);
        }
        animation.update();
        
        if(dx > 0){
            facingRight = true;
        }
        else if(dx < 0){
            facingRight = false;
        }
      
    }
    
    public void draw(Graphics2D g){
        
        int tx = tileMap.getx();
        int ty = tileMap.gety();
        
        if(facingRight){
            g.drawImage(
                    animation.getImage(),
                    (int) (tx + x - animation.getImage().getWidth() / 2),
                    (int) (ty + y - animation.getImage().getHeight() / 2),
                    null);
        }
        else{
            g.drawImage(
                    animation.getImage(),
                    (int) (tx + x - animation.getImage().getWidth() / 2 + animation.getImage().getWidth()),
                    (int) (ty + y - animation.getImage().getHeight() / 2),
                    -animation.getImage().getWidth(),
                    animation.getImage().getHeight(),
                    null);
        }
        /*g.setColor(Color.BLUE);
        g.fillRect(
                (int) (tx + x - width / 2),
                (int) (ty + y - height / 2),
                width, height
        );*/
    }
    
    
}
