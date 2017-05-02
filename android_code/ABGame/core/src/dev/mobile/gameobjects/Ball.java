package dev.mobile.gameobjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import dev.mobile.game.GameWorld;
import dev.mobile.helpers.AssetLoader;
import sun.rmi.runtime.Log;

/**
 * Created by Braedyn Kenzie on 2017-02-28.
 *
 */

public class Ball {
    public GameWorld ballGameWorld;

    private Vector2 position = new Vector2();
    private Vector2 velocity = new Vector2();

    private int RADIUS = 30;
    private float DECAY = 95/100f;
    private int MAX_SPEED = 15000000;

    private Circle boundingCircle = new Circle();
    private double countDown = 0;

    // Values for when the ball is reset:
    private int startX;
    private int startY;

    public Ball(GameWorld gameWorld, int x_pos, int y_pos, int x_vel, int y_vel){

        ballGameWorld = gameWorld;

        // Balls starting position -- restarts here when reset
        startX = x_pos;
        startY = y_pos;

        position.x = x_pos;
        position.y = y_pos;
        velocity.x = x_vel;
        velocity.y = y_vel;

        boundingCircle.setRadius(RADIUS);
        boundingCircle.setPosition(x_pos, y_pos);
    }

    public void update(float delta){

        if(countDown > 0){
            if(countDown - delta <=  0){ AssetLoader.ballStartSound.play(); }
            countDown -= delta;
            if(countDown < 0){
                countDown = 0;
            }
        }

        if(countDown == 0){
            position.x += velocity.x * delta/10000;
            position.y += velocity.y * delta/10000;
        }


        // Velocity decay/bounds
        if(velocity.x > 0){
            velocity.x *= DECAY;
        }
        if(velocity.y > 0){
            velocity.y *= DECAY;
        }
        if(velocity.x < 0){
            velocity.x *= DECAY;
        }
        if(velocity.y < 0){
            velocity.y *= DECAY;
        }
        if(velocity.x < -MAX_SPEED){
            velocity.x = -MAX_SPEED;
        }
        if(velocity.x > MAX_SPEED){
            velocity.x = MAX_SPEED;
        }
        if(velocity.y < -MAX_SPEED){
            velocity.y = -MAX_SPEED;
        }
        if(velocity.y > MAX_SPEED){
            velocity.y = MAX_SPEED;
        }

        // Change the position of the bounding circle as well
        boundingCircle.setPosition(position.x, position.y);

    }

    public void freeze(float time){
        countDown += time;
    }

    public float getX(){
        return position.x;
    }

    public float getY(){
        return position.y;
    }

    public float getXVel(){
        return velocity.x;
    }

    public float getYVel(){
        return velocity.y;
    }

    public void setX(int new_x){
        position.x = new_x;
    }

    public void setY(int new_y){
        position.y = new_y;
    }

    public void setXVel(int new_x_vel){
        velocity.x = new_x_vel;
    }

    public void setYVel(int new_y_vel){
        velocity.y = new_y_vel;
    }

    public void invertX(){
        velocity.x = velocity.x *(-1);
    }

    public void invertY(){
        velocity.y = velocity.y *(-1);
    }

    public int getRadius(){
        return this.RADIUS;
    }



    public void onTouchAt(float x, float y){

        // Change the velocity depending on distance from ball

        float x_dist = (x - position.x);
        float y_dist = (y - position.y);

        float distance = (float) Math.sqrt(x_dist*x_dist + y_dist*y_dist);

        velocity.x += (x_dist * 600) * 100/Math.sqrt(distance);
        velocity.y += (y_dist * 600) * 100/Math.sqrt(distance);


    }

    public Circle getBoundingCircle(){
        return boundingCircle;
    }

    public void setBoundingCircleRadius(int radius){
        boundingCircle.setRadius(radius);
    }

    public void reset(){
        position.x = startX;
        position.y = startY;
        velocity.x = 0;
        velocity.y = 0;
    }

    public boolean isFrozen() {
        return countDown > 0;
    }
}
