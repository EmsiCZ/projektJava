/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.view;
import game.models.Finish;
import game.models.GameObject;
import game.models.Shooter;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
/**
 *
 * @author Luboš
 */
public class TileMap {
    
    private int x;
    private int y;
    
    private int tileSize;
    private int[][] map;
    private int mapWidth;
    private int mapHeight;
    
    private BufferedImage tileset;
    private BufferedImage background;
    
    private Tile tiles[][];
    private Finish finish;
    
    private int minx;
    private int miny;
    private int maxx = 0;
    private int maxy = 0;
    
    private ArrayList<GameObject> spikes;
    private ArrayList<Shooter> shooters;
    private ArrayList<GameObject> shoot;

    private ArrayList<GameObject> monsters;
    private boolean levelFinished = false;
    
    long startTime = System.nanoTime();
    long delay = 2500;
    
    public TileMap(String s, int tileSize){
        
        this.tileSize = tileSize;
        spikes = new ArrayList<>();
        shooters = new ArrayList<>();
        shoot = new ArrayList<>();
        
        monsters = new ArrayList<>();



        finish = new Finish();
        finish.loadFinish();

        
        try{
            BufferedReader br = new BufferedReader(new FileReader(s));
            
            mapWidth = Integer.parseInt(br.readLine());
            mapHeight = Integer.parseInt(br.readLine());
            map = new int[mapHeight][mapWidth];
            
            minx = GamePanel.WIDTH - mapWidth * tileSize;
            miny = GamePanel.HEIGHT - mapHeight * tileSize;
            
            String delimiters = "\\s+"; //space
            for (int row = 0; row < mapHeight; row++) {
                String line = br.readLine();
                String[] tokens = line.split(delimiters);
                for (int col = 0; col < mapWidth; col++) {
                    map[row][col] = Integer.parseInt(tokens[col]);
                    if(map[row][col] == 3){
                        //finish = new Finish(col * tileSize - 5, row * tileSize - 16);
                        finish.setPosition(col * tileSize - 5, row * tileSize - 16);
                    }
                    if(map[row][col] == 4){
                        spikes.add(new GameObject(col * tileSize + 0,row * tileSize + 32,true,"src/game/models/spikes.png"));
                        spikes.add(new GameObject(col * tileSize + 32,row * tileSize + 32,true,"src/game/models/spikes.png"));
                    }
                    if(map[row][col] == 5){

                        spikes.add(new GameObject(col * tileSize + 0,row * tileSize + 32,true,"src/game/models/spikes.png"));

                        spikes.add(new GameObject(col * tileSize + 0,row * tileSize + 32, true,"src/game/models/spikes.png"));
                    }
                    if(map[row][col] == 8){
                        monsters.add( new GameObject(col * tileSize + 0,row * tileSize + 43, true,"src/game/graphics/monster.png"));

                    }
                    if(map[row][col] == 6){
                        shooters.add(new Shooter(col * tileSize, row * tileSize + 16, true, "src/game/graphics/shooter_left.png" ));
                    } 
                    if(map[row][col] == 7){
                        shooters.add(new Shooter(col * tileSize, row * tileSize + 16, false, "src/game/graphics/shooter_right.png" )); 
                    }
                        
               
                }
            }
            
        }
        catch(Exception e){
        }

    }
    
    public void loadTiles(String s){
        
        try{
            tileset = ImageIO.read(new File(s));
            int numTilesAcross = (tileset.getWidth() + 1) / (tileSize + 1);
            tiles = new Tile[2][numTilesAcross];
            
            BufferedImage subimage;
            for (int col = 0; col < numTilesAcross; col++) {
                subimage = tileset.getSubimage(col * tileSize + col, 0, tileSize, tileSize);
                tiles[0][col] = new Tile(subimage, false);
                
                subimage = tileset.getSubimage(col * tileSize + col, tileSize + 1, tileSize, tileSize);
                tiles[1][col] = new Tile(subimage, true);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void loadBackground(String s){
        
        try{
            background = ImageIO.read(new File(s));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public Finish getFinish(){
        return finish;
    }
    
    
    public int getx(){ return x; }
    public int gety(){ return y; }
    
    public int getColTile(int x){ 
        return x / tileSize; 
    }
    public int getRowTile(int y){ 
        return y / tileSize; 
    }
    public int getTile(int row, int col){
        return map[row][col];
    }
    public int getTileSize(){
        return tileSize;
    }
    public int getMapWidht(){
        return mapWidth;
    }
    
    public int getMapHeight(){
        return mapHeight;
    }
    
    public boolean getLevelFinished(){
        return levelFinished;
    }
    
    public boolean isBlocked(int row, int col){
        int rc = map[row][col];
        int r = rc / tiles[0].length;
        int c = rc % tiles[0].length;
        return tiles[r][c].isBlocked();
    }
    
    public void setLevelFinished(boolean x){
        levelFinished = x;
    }
    public void setx(int i){ 
        x = i;
        if(x < minx){
            x = minx;
        }
        else if(x > maxx){
            x = maxx;
        }
    }
    public void sety(int i){ 
        y = i;
        if(y < miny){
            y = miny;
        }
        else if(y > maxy){
            y = maxy;
        }
    }
    public ArrayList<GameObject> getSpikes(){
        return spikes;
    }
    
    public ArrayList<GameObject> getShots(){
        return shoot;
    }
    
    public void clearShooters(){
        shooters.clear();
    }
    
    public void clearShots(){
        shoot.clear();
    }
    
    public void generateShots(){
        if(!shooters.isEmpty()){
            long elapsed = (System.nanoTime() - startTime) / 1000000;
            if(elapsed > delay){
                for (int i = 0; i < shooters.size(); i++){
                    if(shooters.get(i).isLeft()){
                       shoot.add(new GameObject(shooters.get(i).getx()-40, shooters.get(i).gety()+8, true, "src/game/graphics/kunai_left.png")); 
                    }
                    else{
                       shoot.add(new GameObject(shooters.get(i).getx()+19, shooters.get(i).gety()+8, false, "src/game/graphics/kunai.png")); 
                    }
                        
                }
                
                 startTime = System.nanoTime();
            
            }
        }
    }
    
    public void moveShots(){
        if(!shoot.isEmpty()){
            int move = 3;
            for (int i = 0; i < shoot.size(); i++){
                if(shoot.get(i).isLeft()){
                     shoot.get(i).setX(-move);
                }
                else
                    shoot.get(i).setX(move);
               
            }
        }
        
    }
    
    public void deleteShots(){
        int temp;
        
        if(!shoot.isEmpty()){
            Iterator<GameObject> iterator = shoot.iterator();  
                    while(iterator.hasNext()){
                        temp = iterator.next().getX();
                        if( temp > mapWidth * tileSize || temp < 0)
                            iterator.remove();
                    }
        }
        
    }
    
    
     public ArrayList<GameObject> getMonsters(){
        return monsters;
    }
    public void update(){
        generateShots();
        moveShots();
        deleteShots();
        
        if(!monsters.isEmpty())
        for(int i = 0; i < monsters.size(); i++){
            monsters.get(i).move();
        }
    }
    
    public void draw(Graphics2D g){
        
           g.drawImage(background, 0, 0, null);
            for (int row = 0; row < mapHeight; row++) {
                for (int col = 0; col < mapWidth; col++) {
                    int rc = map[row][col];
                
                    int r = rc / tiles[0].length;
                    int c = rc % tiles[0].length;
                
                    g.drawImage(
                    tiles[r][c].getImage(),
                    x + col * tileSize,
                    y + row * tileSize,
                    null
                );
            }
            

        }

            g.drawImage(

                    finish.getImage(),
                    x + finish.getx(),
                    y + finish.gety(),
                    null

                );
        if(!spikes.isEmpty()){
        for (int i = 0; i < spikes.size(); i++) {
          g.drawImage(spikes.get(i).getImage(), x+spikes.get(i).getX(), spikes.get(i).getY()+y, null);  
        }}
        
        if(!shooters.isEmpty()){
        for (int i = 0; i < shooters.size(); i++) {
          g.drawImage(shooters.get(i).getImage(), x+shooters.get(i).getx(), shooters.get(i).gety()+y, null);  
        }}
        
        if(!shoot.isEmpty()){
        for (int i = 0; i < shoot.size(); i++) {
          g.drawImage(shoot.get(i).getImage(), x+shoot.get(i).getX(), shoot.get(i).getY()+y, null);  
        }}
        
        if(!monsters.isEmpty()){
            for (int j = 0; j < monsters.size(); j++) {
            g.drawImage(monsters.get(j).getImage(), x+monsters.get(j).getX(), y+monsters.get(j).getY(), null);
            }
        }
        
    }

               
        }
        


