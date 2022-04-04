package BrickBreaker;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false;
    private int score = 0;
    private int totalBricks = 30;
    private Timer timer;
    private int delay = 10;
    private int playerX = 310;
    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    private BricksMap bricksMap;

    public Gameplay() {
        bricksMap = new BricksMap(5, 6);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics graphic) {

        // Background
        graphic.setColor(Color.black);
        graphic.fillRect(1, 1, 692, 592);

        // Paddle
        graphic.setColor(Color.green);
        graphic.fillRect(playerX, 550, 100, 8);

        // Ball
        graphic.setColor(Color.yellow);
        graphic.fillOval(ballPosX, ballPosY, 20, 20);

        // Score
        graphic.setColor(Color.white);
        graphic.setFont(new Font("serif", Font.BOLD, 25));
        graphic.drawString("" + score, 590, 30);

        // Draw bircks map
        bricksMap.draw((Graphics2D) graphic);

        // Win Condition
        if (totalBricks <= 0) {

            this.play = false;
            this.ballXdir = 0;
            this.ballYdir = 0;

            graphic.setColor(Color.RED);
            graphic.setFont(new Font("serif", Font.BOLD, 30));
            graphic.drawString("You Won", 270, 300);

            graphic.setColor(Color.RED);
            graphic.setFont(new Font("serif", Font.BOLD, 30));
            graphic.drawString("Press (Enter) to Restart", 190, 350);

        }

        // Lose condition
        if (ballPosY > 570) {

            this.play = false;
            this.ballXdir = 0;
            this.ballYdir = 0;
            graphic.setColor(Color.RED);
            graphic.setFont(new Font("serif", Font.BOLD, 30));
            graphic.drawString("Game Over, Scores: " + score, 190, 300);

            graphic.setColor(Color.RED);
            graphic.setFont(new Font("serif", Font.BOLD, 30));
            graphic.drawString("Press (Enter) to Restart", 190, 350);

        }

        graphic.dispose();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        // Right key
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                play = true;
                playerX += 20;
            }
        }

        // Left key
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                play = true;
                playerX -= 20;
            }
        }

        // Enter key
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                this.play = true;
                this.ballPosX = 120;
                this.ballPosY = 350;
                this.ballXdir = -1;
                this.ballYdir = -2;
                this.playerX = 310;
                this.score = 0;
                this.totalBricks = 30;
                this.bricksMap = new BricksMap(5, 6);
                repaint();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        timer.start();
        if (play) {
            if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 30, 8))) {
                this.ballXdir = -2;
                this.ballYdir = -ballYdir;
            } else if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX + 70, 550, 30, 8))) {
                this.ballXdir = ballXdir + 1;
                this.ballYdir = -ballYdir;
            } else if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX + 30, 550, 40, 8))) {
                this.ballYdir = -ballYdir;
            }

            // Collision of bricks map and ball
            A: for (int i = 0; i < bricksMap.map.length; i++) {
                for (int j = 0; j < bricksMap.map[0].length; j++) {
                    if (bricksMap.map[i][j] > 0) {
                        // Scores++;
                        int brickX = j * bricksMap.brickWidth + 80;
                        int brickY = i * bricksMap.brickHeight + 50;
                        int brickWidth = bricksMap.brickWidth;
                        int brickHeight = bricksMap.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)) {
                            bricksMap.setBrickValue(0, i, j);
                            score += 10;
                            totalBricks--;

                            // Ball hits right or left of brick
                            if (ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            }
                            // Ball hits top or bottom of brick
                            else {
                                ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }

            ballPosX += ballXdir;
            ballPosY += ballYdir;

            if (ballPosX < 0) {
                ballXdir = -ballXdir;
            }

            if (ballPosY < 0) {
                ballYdir = -ballYdir;
            }
            if (ballPosX > 670) {
                ballXdir = -ballXdir;
            }
            repaint();
        }
    }
}

// Comment