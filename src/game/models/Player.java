/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.models;
import game.view.GamePanel;
import game.view.TileMap;
import java.awt.*;

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
    
    private boolean topLeft;
    private boolean topRight;
    private boolean bottomLeft;
    private boolean bottomRight;
    
    public Player(TileMap tm){
            
       tileMap = tm;
       
       width = 40;
       height = 40;
       
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
    
    private void calculateCorners(double x, double y){
        
        int leftTile = tileMap.getColTile((int) (x - width / 2));
        int rightTile = tileMap.getColTile((int) (x + width / 2) - 1); //-1 for out of bounds
        int topTile = tileMap.getRowTile((int) (y - height / 2));
        int bottomTile = tileMap.getRowTile((int) (y + height / 2) - 1);
        topLeft = tileMap.isBlocked(topTile, leftTile);
        topRight = tileMap.isBlocked(topTile, rightTile);
        bottomLeft = tileMap.isBlocked(bottomTile, leftTile);
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
      
    }
    
    public void draw(Graphics2D g){
        
        int tx = tileMap.getx();
        int ty = tileMap.gety();
        
        g.setColor(Color.BLUE);
        g.fillRect(
                (int) (tx + x - width / 2),
                (int) (ty + y - height / 2),
                width, height
        );
    }
    
    
}
