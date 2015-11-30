/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.view;
import game.models.Player;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Luboš
 */
public class GamePanel extends JPanel implements Runnable, KeyListener{
    
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    
    private Thread thread;
    private boolean running;
    
    private BufferedImage image;
    private Graphics2D g;
    
    private int FPS = 60;
    private int targetTime = 1000/FPS;
    
    private TileMap tileMap;
    private Player player;
    
    public GamePanel(){
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
    }
    
    public void addNotify(){
        super.addNotify();
        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
        addKeyListener(this);
    }
    
    public void run(){
        
        init();
        
        long startTime;
        long urdTime;
        long waitTime;
        
        while(running){
            
            startTime = System.nanoTime();
            
            update();
            render();
            draw();
            
            urdTime = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - urdTime;
            
            try{
                Thread.sleep(waitTime);
            }
            catch(Exception e){
            }
        }
        
    }
    
    public void init(){
        
        running = true;
        
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        
        tileMap = new TileMap("src/game/levels/testMap.txt", 64);
        tileMap.loadBackground("src/game/graphics/BG1280x720.gif");
        tileMap.loadTiles("src/game/graphics/tileset.gif");
        
        player = new Player(tileMap);
        player.loadPlayer("src/game/graphics/player/run_000.png");
        player.setx(80);
        player.sety(80);
    }
    
    ///////////////////////////////////////////////////////
    
    private void update(){
        
        tileMap.update();
        player.update();
    }
    
    private void render(){
        
        /*g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);*/
        
        tileMap.draw(g);
        player.draw(g);
    }
    
    private void draw(){
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        
    }
    
    public void keyTyped(KeyEvent key){}
    public void keyPressed(KeyEvent key){
        
        int code = key.getKeyCode();
        
        if(code == KeyEvent.VK_LEFT){
            player.setLeft(true);
        }
        if(code == KeyEvent.VK_RIGHT){
            player.setRight(true);
        }
        if(code == KeyEvent.VK_SPACE){
            player.setJumping(true);
        }
    }
    public void keyReleased(KeyEvent key){
        
        int code = key.getKeyCode();
        
        if(code == KeyEvent.VK_LEFT){
            player.setLeft(false);
        }
        if(code == KeyEvent.VK_RIGHT){
            player.setRight(false);
        }
    }
}
