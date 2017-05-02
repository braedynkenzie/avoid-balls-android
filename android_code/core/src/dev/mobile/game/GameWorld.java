package dev.mobile.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.LinkedList;
import java.util.List;
import dev.mobile.gameobjects.Ball;
import dev.mobile.gameobjects.DangerBall;
import dev.mobile.gameobjects.Projectile;
import dev.mobile.helpers.AssetLoader;

/**
 * Created by Braedyn Kenzie on 2017-02-28.
 *
 */

public class GameWorld {

    public enum GameState { MENU, RUNNINGEASY, RUNNINGMED, RUNNINGHARD, GAMEOVER }
    private GameState currentGameState = GameState.MENU;

    private int gameWidth;
    private int gameHeight;

    // Menu Objects
    public Ball easy;
    public Ball med;
    public Ball hard;

    // Game Objects
    public Ball mainBall;
    public List<Projectile> projectiles = new LinkedList<Projectile>();
    public List<Projectile> dangerProjectiles = new LinkedList<Projectile>();
    public List<DangerBall> dangerBalls = new LinkedList<DangerBall>();

    // Misc. game variables
    private boolean easyBallsInit = false;
    private boolean medBallsInit = false;
    private boolean hardBallsInit = false;
    public float countDown = 0;


    public GameWorld(int gameWidth, int gameHeight){

        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;

        // Initialize menu objects
        easy = new Ball(this, gameWidth/2, gameHeight/2, 0, 0);
        easy.setBoundingCircleRadius(easy.getRadius() + 10);
        med  = new Ball(this, gameWidth/3, gameHeight/3, 0, 0);
        med.setBoundingCircleRadius(med.getRadius() + 10);
        hard = new Ball(this, gameWidth*2/3, gameHeight/3, 0, 0);
        hard.setBoundingCircleRadius(hard.getRadius() + 10);

        mainBall = new Ball(this, gameWidth/4, (gameHeight*7/8), 0, 0);

    }

    public void update(float delta){

        if(countDown > 0){
            countDown -= delta;
            if(countDown < 0){
                countDown = 0;
            }
        }

        if(Gdx.input.isTouched()){
            mainBall.onTouchAt(Gdx.input.getX(), Gdx.input.getY());
        }
        mainBall.update(delta);
        handleEdgeCollisions();

        switch (currentGameState){
            case MENU:
                updateMenu(delta);
                handleMenuCollisions();
                break;
            case RUNNINGEASY:
                updateRunningEasy(delta);
                break;
            case RUNNINGMED:
                updateRunningMed(delta);
                break;
            case RUNNINGHARD:
                updateRunningHard(delta);
                break;
            case GAMEOVER:
                if(Gdx.input.isTouched() && countDown == 0){
                    mainBall.reset();
                    projectiles.clear();
                    dangerBalls.clear();
                    currentGameState = GameState.MENU;
                }

                break;
        }
    }

    private void updateMenu(float delta){
        Circle easy = new Circle();
        easy.setPosition(gameWidth/3, gameHeight/6);
    }

    private void updateRunningEasy(float delta){
        if(!easyBallsInit){
            initDangerBallsEasy();
            easyBallsInit = true;
            // Freeze the dangerBalls at the start of the game for designated time
            for (DangerBall dangerBall: dangerBalls) {
                dangerBall.freeze(5/2);
            }
        }
        updateDangerBalls(mainBall.getX(), mainBall.getY(), delta);
        handleBallCollisions();
        handleProjectileCollisions();
        updateProjectiles(delta);
        updateDangerProjectiles(delta);


    }

    private void updateRunningMed(float delta){
        if(!medBallsInit){
            initDangerBallsMed();
            medBallsInit = true;
            // Freeze the dangerBalls at the start of the game for designated time
            int time = 2;
            for (DangerBall dangerBall: dangerBalls) {
                dangerBall.freeze(time++);
            }
        }
        updateDangerBalls(mainBall.getX(), mainBall.getY(), delta);
        handleBallCollisions();
        handleProjectileCollisions();
        updateProjectiles(delta);
        updateDangerProjectiles(delta);

    }

    private void updateRunningHard(float delta){
        if(!hardBallsInit){
            initDangerBallsHard();
            hardBallsInit = true;
            // Freeze the dangerBalls at the start of the game for designated time
            int time = 2;
            for (DangerBall dangerBall: dangerBalls) {
                dangerBall.freeze(time++);
            }
        }
        updateDangerBalls(mainBall.getX(), mainBall.getY(), delta);
        handleBallCollisions();
        handleProjectileCollisions();
        updateProjectiles(delta);
        updateDangerProjectiles(delta);

    }

    private void initDangerBallsEasy(){
        //Initialize danger balls for easy game mode
        DangerBall dangerBall_1 = new DangerBall(this, gameWidth*3/4, gameHeight/8, 0, 0);
        dangerBalls.add(dangerBall_1);
    }

    private void initDangerBallsMed(){
        //Initialize danger balls for medium difficulty game mode
        DangerBall dangerBall_1 = new DangerBall(this, gameWidth*3/4, gameHeight/8, 0, 0);
        dangerBalls.add(dangerBall_1);
        DangerBall dangerBall_2 = new DangerBall(this, gameWidth*1/4, gameHeight/8, 0, 0);
        dangerBalls.add(dangerBall_2);
    }

    private void initDangerBallsHard(){
        //Initialize danger balls for hard game mode
        DangerBall dangerBall_1 = new DangerBall(this, gameWidth*3/4, gameHeight/8, 0, 0);
        dangerBalls.add(dangerBall_1);
        DangerBall dangerBall_2 = new DangerBall(this, gameWidth*1/4, gameHeight/8, 0, 0);
        dangerBalls.add(dangerBall_2);
        DangerBall dangerBall_3 = new DangerBall(this, gameWidth*2/4, gameHeight/8, 0, 0);
        dangerBalls.add(dangerBall_3);
    }

    private void handleMenuCollisions(){
        if(Intersector.overlaps(mainBall.getBoundingCircle(), easy.getBoundingCircle())){
            currentGameState = GameState.RUNNINGEASY;
            mainBall.reset();
            projectiles.clear();
        } else if(Intersector.overlaps(mainBall.getBoundingCircle(), med.getBoundingCircle())){
            currentGameState = GameState.RUNNINGMED;
            mainBall.reset();
            projectiles.clear();
        } else if(Intersector.overlaps(mainBall.getBoundingCircle(), hard.getBoundingCircle())){
            currentGameState = GameState.RUNNINGHARD;
            mainBall.reset();
            projectiles.clear();
        }
    }

    private void handleProjectileCollisions() {
        if(projectiles.isEmpty()){ return; }

        DangerBall[] dangerBallArray = new DangerBall[dangerBalls.size()];
        // Add all items in dangerBall list to an array
        int index = 0;
        for (DangerBall db: dangerBalls) {
            dangerBallArray[index] = db;
            index++;
        }

        for(Projectile proj: projectiles){
            // Check if proj is colliding with any dangerballs
            for(DangerBall currentDangerBall: dangerBalls){
                // TODO: only check intersector.overlaps if collision is possible
                if(Intersector.overlaps(proj.getBoundingCircle(), currentDangerBall.getBoundingCircle())){
                    projectileCollision(proj, currentDangerBall);
                }
            }


        }



}

    private void projectileCollision(Projectile proj, DangerBall dangerBall) {
        // Freeze dangerBall when hit with a projectile
        if(!dangerBall.isFrozen()){
            dangerBall.freeze(1);
            // TODO: play freeze sound effect here
        }

        // TODO:(?)change the dangerBalls velocity corresponding to getting hit with a projectile
//        dangerBall.setXVel((int)(dangerBall.getXVel() + 100*proj.getXVel()));
//        dangerBall.setYVel((int)(dangerBall.getYVel() + 100*proj.getYVel()));


    }

    private void handleBallCollisions(){
        if(dangerBalls.isEmpty()){ return; }

        DangerBall[] dangerBallArray = new DangerBall[dangerBalls.size()];

        // Add all items in dangerBall list to an array
        int index = 0;
        for (DangerBall db: dangerBalls) {
            dangerBallArray[index] = db;
            index++;
        }

        for(int i = 0; i < dangerBallArray.length; i++){
            DangerBall currentDangerBall = dangerBallArray[i];

            // Check collision with mainBall:
            if(Intersector.overlaps(mainBall.getBoundingCircle(), currentDangerBall.getBoundingCircle())){
                this.gameOver();
            }

            // Check collisions with other dangerBalls:
            for(int n = i + 1; n < dangerBallArray.length; n++){

                // TODO: only check intersector.overlaps if collision is possible

                if(Intersector.overlaps(currentDangerBall.getBoundingCircle(),
                                        dangerBallArray[n].getBoundingCircle())){
                    ballCollision(currentDangerBall, dangerBallArray[n]);
                    AssetLoader.ballCollide.play();
                }
            }
        }
    }

    private void ballCollision(Ball ball_1, Ball ball_2){

        if(ball_1.getXVel() > 0 && ball_2.getXVel() > 0){
            Ball left;
            Ball right;
            if(ball_1.getX() >= ball_2.getX()){
                right = ball_1;
                left  = ball_2;
            } else {
                left  = ball_1;
                right = ball_2;
            }

            int lXVel = (int) left.getXVel();
            int rXVel = (int) right.getXVel();

            right.setXVel((int) (rXVel + 2*lXVel));
            left.setXVel((int) (lXVel / 2));
        }

        if(ball_1.getXVel() < 0 && ball_2.getXVel() < 0){
            Ball left;
            Ball right;
            if(ball_1.getX() >= ball_2.getX()){
                right = ball_1;
                left  = ball_2;
            } else {
                left  = ball_1;
                right = ball_2;
            }

            int lXVel = (int) left.getXVel();
            int rXVel = (int) right.getXVel();

            right.setXVel((int) (rXVel/ 2));
            left.setXVel((int) (lXVel + 2*rXVel));
        }

        if(ball_1.getYVel() > 0 && ball_2.getYVel() > 0){
            Ball above;
            Ball below;
            if(ball_1.getY() >= ball_2.getY()){
                below = ball_1;
                above  = ball_2;
            } else {
                above  = ball_1;
                below = ball_2;
            }

            int aYVel = (int) above.getYVel();
            int bYVel = (int) below.getYVel();

            above.setYVel((int) (aYVel / 2));
            below.setYVel((int) (bYVel + 2*aYVel));
        }

        if(ball_1.getYVel() < 0 && ball_2.getYVel() < 0){
            Ball above;
            Ball below;
            if(ball_1.getY() >= ball_2.getY()){
                below = ball_1;
                above  = ball_2;
            } else {
                above  = ball_1;
                below = ball_2;
            }

            int aYVel = (int) above.getYVel();
            int bYVel = (int) below.getYVel();

            above.setYVel((int) (aYVel + 2*bYVel));
            below.setYVel((int) (bYVel / 2));
        }

        if((ball_1.getXVel() > 0 && ball_2.getXVel() < 0) | (ball_1.getXVel() > 0 && ball_2.getXVel() < 0)){
            Ball left;
            Ball right;
            if(ball_1.getX() >= ball_2.getX()){
                right = ball_1;
                left  = ball_2;
            } else {
                left  = ball_1;
                right = ball_2;
            }

            int lXVel = (int) left.getXVel();
            int rXVel = (int) right.getXVel();

            left.setXVel((int) (lXVel * (-2) + rXVel));
            right.setXVel((int) (rXVel * (-2) + lXVel));
        }

        if((ball_1.getYVel() > 0 && ball_2.getYVel() < 0) | (ball_1.getYVel() > 0 && ball_2.getYVel() < 0)){
            Ball above;
            Ball below;
            if(ball_1.getY() >= ball_2.getY()){
                below = ball_1;
                above  = ball_2;
            } else {
                above  = ball_1;
                below = ball_2;
            }

            int aYVel = (int) above.getYVel();
            int bYVel = (int) below.getYVel();

            below.setXVel((int) (bYVel * (-2) + aYVel));
            above.setXVel((int) (aYVel * (-2) + bYVel));
        }
    }

    private void handleEdgeCollisions(){
        if(mainBall.getX() + mainBall.getRadius() >= gameWidth ){
            mainBall.invertX();
            mainBall.setX(gameWidth - mainBall.getRadius() - 1);
        }
        if(mainBall.getY() + mainBall.getRadius() >= gameHeight){
            mainBall.invertY();
            mainBall.setY(gameHeight - mainBall.getRadius() - 1);
        }
        if(mainBall.getX() - mainBall.getRadius() <= 0 ){
            mainBall.invertX();
            mainBall.setX(mainBall.getRadius() + 1);
        }
        if(mainBall.getY() - mainBall.getRadius() <= 0 ){
            mainBall.invertY();
            mainBall.setY(mainBall.getRadius() + 1);
        }
    }

    private void updateDangerBalls(float mainX, float mainY, float delta){
        for (DangerBall dangerBall: dangerBalls) {

            dangerBall.onMainBallAt(mainX, mainY);
            dangerBall.update(delta);

        }
    }

    public void addProjectile(Projectile newProjectile){
        projectiles.add(newProjectile);
    }

    public void addDangerProjectile(Projectile newProjectile){
        dangerProjectiles.add(newProjectile);
    }

    public void updateProjectiles(float delta){
        for (Projectile projectile: projectiles) {
            projectile.update(delta);

            float deathTimer = projectile.getDeathTimer();
            if(deathTimer <= 0){
                projectiles.remove(projectile);
            }
        }

            // todo: get rid of all the projectiles properly
//            // Check if the projectile is off the screen
//            if(projectile.getX() - projectile.getRadius() > gameWidth){
//                projectiles.remove(projectile);
//            }
//            if(projectile.getX() + projectile.getRadius() < 0){
//                projectiles.remove(projectile);
//            }
//            if(projectile.getY() - projectile.getRadius() > gameHeight){
//                projectiles.remove(projectile);
//            }
//            if(projectile.getY() + projectile.getRadius() < 0){
//                projectiles.remove(projectile);
//            }
    }

    public void updateDangerProjectiles(float delta){
        for (Projectile dangerProjectile: dangerProjectiles) {
            dangerProjectile.updateDangerProjectile(delta, mainBall);

            float deathTimer = dangerProjectile.getDeathTimer();
            if(deathTimer <= 0){
                dangerProjectiles.remove(dangerProjectile);
            }
        }
    }

    public GameState getCurrentGameState(){
        return currentGameState;
    }

    public void gameOver(){
        currentGameState = GameState.GAMEOVER;
        mainBall.setXVel(0);
        mainBall.setYVel(0);
        for (DangerBall db: dangerBalls){
            db.setXVel(0);
            db.setYVel(0);
        }

        // Might be able to reduce this to a single variable..
        easyBallsInit = false;
        medBallsInit = false;
        hardBallsInit = false;

        // Set delay for touch input
        countDown += 1.2;



    }

}
