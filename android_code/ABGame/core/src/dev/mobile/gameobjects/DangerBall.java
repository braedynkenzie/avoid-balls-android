package dev.mobile.gameobjects;

import com.badlogic.gdx.math.Circle;

import dev.mobile.game.GameWorld;

/**
 * Created by Braedyn Kenzie on 2017-03-10.
 * 
 */

public class DangerBall extends Ball {

    private int DANGER_RADIUS = super.getRadius() + 5;

    public double dangerNum = 3;
    private Circle boundingDangerCircle = new Circle();

    public DangerBall(GameWorld gw, int x_pos, int y_pos, int x_vel, int y_vel) {
        super(gw, x_pos, y_pos, x_vel, y_vel);

        boundingDangerCircle.setRadius(DANGER_RADIUS);
        boundingDangerCircle.setPosition(x_pos, y_pos);
    }

    public void onMainBallAt(float mainX, float mainY) {
        // Change the velocity depending on distance from ball
        float x_dist = (mainX - super.getX());
        float y_dist = (mainY - super.getY());

        float distance = (float) Math.sqrt(x_dist*x_dist + y_dist*y_dist);

        super.setXVel((int) (super.getXVel() + (x_dist * 250) * 100/Math.sqrt(distance)));
        super.setYVel((int) (super.getYVel() + (y_dist * 250) * 100/Math.sqrt(distance)));
    }

    @Override
    public void update(float delta){
        super.update(delta);

        if(dangerNum > 0){
            dangerNum -= delta;
        } else if(dangerNum < 0){
            dangerNum += 3;
            // Dangerball shoots its own projectile here
            // TODO: set the proper direction of the new dangerProjectile (maybe set to homing towards mainBall?)
            Projectile dangerProjectile = new Projectile(2, (int)this.getX(),(int)this.getY(),0,0);
            this.ballGameWorld.addDangerProjectile(dangerProjectile);

        }


        // Have to also change the different bounding circle when updating dangerBalls:
        boundingDangerCircle.setPosition(super.getX(), super.getY());
    }

    @Override
    public Circle getBoundingCircle(){
        return boundingDangerCircle;
    }

    @Override
    public int getRadius(){
        return DANGER_RADIUS;
    }

}
