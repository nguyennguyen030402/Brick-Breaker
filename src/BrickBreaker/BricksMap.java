package BrickBreaker;

import java.awt.*;

public class BricksMap {
    
    public int map[][];
    public int brickWidth;
    public int brickHeight;

    public BricksMap(int row, int col) {
        map = new int[row][col];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = 1;
            }
        }
        brickWidth = 540 / col;
        brickHeight = 150 / row;
    }

    // Draw bricks map  
    public void draw(Graphics2D graphic) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    graphic.setColor(Color.white);
                    graphic.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                    // Boders of brick
                    graphic.setStroke(new BasicStroke(3));
                    graphic.setColor(Color.black);
                    graphic.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    public void setBrickValue(int value, int row, int col) {
        map[row][col] = value;
    }

}
