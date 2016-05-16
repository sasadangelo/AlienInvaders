package org.androidforfun.alieninvaders.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class AlienInvadersWorld {
    // Each cell is a 4x4 matrix of boxes. The reason we need cells is that ship, aliens and
    // projectiles do move across boxes.
    public static final int CELL_WIDTH = 4;
    public static final int CELL_HEIGHT = 4;
    // The dimension of the matrix is 16x20 cells
    public static final int MATRIX_WIDTH = 16;
    public static final int MATRIC_HEIGHT = 20;
    // Alien Invaders world is a matrix of 64x80 boxes
    public static final int WORLD_WIDTH = MATRIX_WIDTH*CELL_WIDTH;
    public static final int WORLD_HEIGHT = MATRIC_HEIGHT*CELL_HEIGHT;

    // the possible game status values
    public enum GameState {
        Ready,
        Running,
        Paused,
        GameOver
    }

    // the game status
    private GameState state = GameState.Ready;

    // the game level
    //private int level = 0;
    // the user score
    private int score;
    private int timer;

    private List<Alien> aliens;
    private List<Projectile> projectiles;
    private List<Shield> shields;

    private Ship ship;

    // the private static instance used to implement the Singleton pattern.
    private static AlienInvadersWorld instance = null;

    public List<Projectile> getProjectiles() {

        return projectiles;
    }
    public List<Shield> getShields() { return shields; }
    public List<Alien> getAliens() { return aliens; }

    public Ship getShip() {
        return ship;
    }

    private AlienInvadersWorld() {
        this.aliens=new ArrayList<Alien>();
        this.projectiles=new ArrayList<Projectile>();
        this.shields=new ArrayList<Shield>();
        this.clear();
    }

    public static AlienInvadersWorld getInstance() {
        if (instance == null) {
            synchronized (AlienInvadersWorld.class) {
                if (instance == null) {
                    instance = new AlienInvadersWorld();
                }
            }
        }
        return instance;
    }

    public void update(float deltaTime) {
        if (state == GameState.GameOver)
            return;

        if (state == GameState.Running ) {
            timer += 1;
            if ((timer % 40) == 0) {
                for (Alien alien: aliens) {
                    alien.move();
                }
            }

            if ((timer % 60) == 0) {
                for (Alien alien: aliens) {
                    alien.increaseSpeed();
                }
            }

            if ((timer % 80) == 0) {
                if (aliens.size()!=0) {
                    invasion();
                }
                for (Alien alien: aliens) {
                    alien.increaseSpeed();
                }
            }

            for (Projectile projectile: projectiles) {
                projectile.move();
                if (getBounds().overlaps(projectile.getBounds())) {
                    projectile.kill();
                }
            }

            for (Iterator<Projectile> itr= projectiles.iterator(); itr.hasNext();) {
                Projectile projectile = itr.next();
                if (projectile.isInactive()) {
                    itr.remove();
                }
            }
            detectCollisions();

            if (!ship.isAlive()) {
                state=GameState.GameOver;
            }
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(0, 0, WORLD_WIDTH, WORLD_HEIGHT);
    }

    public void detectCollisions() {
        for (Iterator<Alien> itr= aliens.iterator(); itr.hasNext();) {
            Alien alien = itr.next();
            if (ship.hit(alien)) {
                ship.kill();
                itr.remove();
                break;
            }
        }

        for (Iterator<Projectile> itrProjectile= projectiles.iterator(); itrProjectile.hasNext();) {
            Projectile projectile = itrProjectile.next();
            if (projectile instanceof ShipProjectile) {
                for (Iterator<Alien> itrAlien= aliens.iterator(); itrAlien.hasNext();) {
                    Alien alien = itrAlien.next();
                    if (alien.hit(projectile)) {
                        // bang
                        score += alien.getScore();
                        itrAlien.remove();
                        itrProjectile.remove();
                        break;
                    }
                }
            }
        }

        for (Iterator<Projectile> itrProjectile= projectiles.iterator(); itrProjectile.hasNext();) {
            Projectile projectile = itrProjectile.next();
            if (projectile instanceof AlienProjectile) {
                if (ship.hit(projectile)) {
                    // bang
                    ship.kill();
                    itrProjectile.remove();
                    break;
                }
            }
        }

        for (Iterator<Projectile> itrProjectile1= projectiles.iterator(); itrProjectile1.hasNext();) {
            Projectile projectile1 = itrProjectile1.next();
            if (projectile1 instanceof AlienProjectile) {
                for (Iterator<Projectile> itrProjectile2= projectiles.iterator(); itrProjectile2.hasNext();) {
                    Projectile projectile2 = itrProjectile2.next();
                    if (projectile2 instanceof ShipProjectile) {
                        if (projectile1.hit(projectile2)) {
                            // bang
                            score +=10;
                            itrProjectile1.remove();
                            itrProjectile2.remove();
                            break;
                        }
                    }
                }
            }
        }

        for (Iterator<Projectile> itrProjectile= projectiles.iterator(); itrProjectile.hasNext();) {
            Projectile projectile = itrProjectile.next();
            if (projectile instanceof AlienProjectile) {
                for (Iterator<Shield> itrShield= shields.iterator(); itrShield.hasNext();) {
                    Shield shield = itrShield.next();
                    if (shield.hit(projectile)) {
                        // bang
                        itrProjectile.remove();
                        shield.reduce();
                        if (!shield.isAlive()) {
                            itrShield.remove();
                        }
                    }
                }
            }
        }

        for (Iterator<Projectile> itrProjectile= projectiles.iterator(); itrProjectile.hasNext();) {
            Projectile projectile = itrProjectile.next();
            if (projectile instanceof ShipProjectile) {
                for (Iterator<Shield> itrShield= shields.iterator(); itrShield.hasNext();) {
                    Shield shield = itrShield.next();
                    if (shield.hit(projectile)) {
                        // bang
                        itrProjectile.remove();
                    }
                }
            }
        }
    }

    public void invasion() {
        Random rnd = new Random(System.currentTimeMillis());
        int i=rnd.nextInt(aliens.size());
        Projectile p = aliens.get(i).shoot();
        if (p != null) projectiles.add(p);
    }

    public void clear() {
        score = 0;
        timer = 0;
        state = GameState.Ready;
        aliens.clear();
        projectiles.clear();;
        shields.clear();
        ship=new Ship();
        for (int i=0; i<10; ++i) {
            aliens.add(new UglyAlien(i*CELL_WIDTH + 3*CELL_WIDTH, 7*CELL_HEIGHT));
            aliens.add(new BadAlien(i*CELL_WIDTH + 3*CELL_WIDTH, 8*CELL_HEIGHT));
            aliens.add(new BadAlien(i*CELL_WIDTH + 3*CELL_WIDTH, 9*CELL_HEIGHT));
            aliens.add(new GoodAlien(i*CELL_WIDTH + 3*CELL_WIDTH, 10*CELL_HEIGHT));
            aliens.add(new GoodAlien(i*CELL_WIDTH + 3*CELL_WIDTH, 11*CELL_HEIGHT));
        }

        shields.add(new Shield(3*CELL_WIDTH, 17*CELL_HEIGHT));
        shields.add(new Shield(6*CELL_WIDTH, 17*CELL_HEIGHT));
        shields.add(new Shield(9*CELL_WIDTH, 17*CELL_HEIGHT));
        shields.add(new Shield(12*CELL_WIDTH, 17*CELL_HEIGHT));
    }

    public GameState getState() {
        return state;
    }
    public void setState(GameState state) {
        this.state = state;
    }

    public int getScore() {
        return score;
    }
}
