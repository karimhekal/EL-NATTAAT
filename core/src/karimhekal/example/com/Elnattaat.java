package karimhekal.example.com;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.Button;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
public class Elnattaat extends ApplicationAdapter {
    SpriteBatch batch;
    Texture boy, tube, bg,land,cloud,gameover;


    Rectangle kid;
    ShapeRenderer shapeRenderer;
    Rectangle[] TUBE;

    float boyY;
    float velocity = 0;
    boolean game=false;
    float upSpeed = 0;
    int acc;
    boolean FirstTime;
    float gravity = 3.2f;
    int up=400;
    float temp;
    int inc=85;
    int cloudinc;
    float line;
    boolean touched;
    int numOfTubes = 2;
    int numOfClouds=2;
    float DistanceBetweenClouds;
    float distanceBetweenTubes;
    boolean happened;
    public float tubeVelocity[] = new float[numOfTubes];
    public float cloudVelocity[]=new float[numOfClouds];
    @Override
    public void create() {
        try {

            batch = new SpriteBatch();
            boy = new Texture("KIDDO4.png");
            gameover= new Texture("GameOver.png");
            tube = new Texture("TUBE4.png");
            bg = new Texture("BACKGROUND5.png");
            cloud = new Texture("CLOUDS3.png");
            land = new Texture("LAND8.png");
            line = Gdx.graphics.getHeight() / 3.8f; //the line that the land begins from and the kiddo stands on position
            boyY = line;
            touched = true;
            temp = 0;
            game=false;
            FirstTime=true;
            distanceBetweenTubes = Gdx.graphics.getWidth() / 2;
            DistanceBetweenClouds = Gdx.graphics.getWidth() / 2;

            kid = new Rectangle();
            shapeRenderer = new ShapeRenderer();
            TUBE = new Rectangle[numOfTubes];


            setDistance();
            setClouds();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        }

    @Override
    public void render() {

        batch.begin();

        if (game==true) {


            if (Gdx.input.justTouched()) { // when the screen is touched
                if (boyY == line) {
                    touched = true;
                    velocity -= 30;

                    temp = boyY;
                    acc = 35; // acceleration starts , if you increase it -> the kid will go far when it jumps
                    game = true;
                }
                //upSpeed=0;
            }


            if ((boyY > line || velocity < 0)) { // if the boy is top of the ground then calculate speed and make gravity  //$$$$


                if (touched) {
                    if (boyY >= temp + 550) { //(temp + X) ; x is the scale of the jump , so if you increase x then the jump distance will increase // ALSO boyY is the current position of the object , and temp is a snap of the object before jumping to make sure that he jumped x distance above
                        touched = false;
                        upSpeed = 100;                // you should also consider that gravity pulls it down so when you increase gravity , the jump power will decrease
                    }
                    upSpeed -= 1;
                    velocity = 0;

                    acc -= 1.1; // acceleration decreases

                    boyY += acc;
                    if (boyY < line) // for some reason 'boyY' decreases to be less than 0 , so when that happens the object goes a few pixels under the screen
                    {
                        boyY = line;
                    }
                }

                velocity = velocity + gravity;
                //velocity+=1.5;
                boyY -= velocity;
            }
            if (boyY < line)// for some reason 'boyY' decreases to be less than 0 , so when that happens the object goes a few pixels under the screen
            {
                boyY = line;
            }


            batch.draw(bg, 0, line);


            for (int i = 0; i < numOfClouds; i++) {
                if (cloudVelocity[i] < 0 - (cloud.getWidth())) // if the position of the tube is out of scope to the left and gets out of the screen then make the position of the tube at the start again in the right
                {

                    cloudVelocity[i] += numOfClouds * DistanceBetweenClouds + cloud.getWidth() + i * inc;


                }
                float s = cloudVelocity[i];
                float w = cloudVelocity[1];
                if ((cloudVelocity[0] > cloudVelocity[1]) && cloudVelocity[0] > 0 && cloudVelocity[1] > 0) {
                    if (cloudVelocity[0] - cloudVelocity[1] < 690) // if the distance between 2 tubes is less than 690 , then 'inc' *the space that's gonna be added between the next 2 tubes is gonna be negative so the distanec decreases
                    {
                        cloudinc = -85;
                    } else if ((cloudVelocity[0] - cloudVelocity[1] > 1300)) { // when the distance dicreases too much then make it go back like normal and the distance shrinks again
                        cloudinc = 85;
                    }
                }
                cloudVelocity[i] -= 1f; //15 // speed of moving clouds :D
                batch.draw(cloud, cloudVelocity[i], line + up, cloud.getWidth(), cloud.getHeight());
                if (up == 400) {
                    up = 600;
                } else if (up == 600) {
                    up = 400;
                }

            }

            batch.draw(land, 0, line - land.getHeight(), Gdx.graphics.getWidth(), land.getHeight());

            for (int i = 0; i < numOfTubes; i++) {
                if (tubeVelocity[i] < 0 - (tube.getWidth())) // if the position of the tube is out of scope to the left and gets out of the screen then make the position of the tube at the start again in the right
                {

                    tubeVelocity[i] += numOfTubes * distanceBetweenTubes + tube.getWidth() + i * inc;


                }
                float s = tubeVelocity[i];
                float w = tubeVelocity[1];
                if ((tubeVelocity[0] > tubeVelocity[1]) && tubeVelocity[0] > 0 && tubeVelocity[1] > 0) {
                    if (tubeVelocity[0] - tubeVelocity[1] < 690) // if the distance between 2 tubes is less than 690 , then 'inc' *the space that's gonna be added between the next 2 tubes is gonna be negative so the distanec decreases
                    {
                        inc = -85;
                    } else if ((tubeVelocity[0] - tubeVelocity[1] > 1300)) { // when the distance dicreases too much then make it go back like normal and the distance shrinks again
                        inc = 85;
                    }
                }
                tubeVelocity[i] -= 15; //15 // speed of moving tubes :D
                batch.draw(tube, tubeVelocity[i], line, tube.getWidth() * 1.3f, tube.getHeight() * 1.3f);

                TUBE[i] = new Rectangle(tubeVelocity[i], line, tube.getWidth() * 0.9f, tube.getHeight() * 0.9f);
            }

            batch.draw(boy, Gdx.graphics.getWidth() / 2, boyY, boy.getWidth() * 0.9f, boy.getHeight() * 0.9f);

            // shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            // shapeRenderer.setColor(Color.RED);
            kid = new Rectangle(Gdx.graphics.getWidth() / 2, boyY, boy.getWidth() * 0.9f, boy.getHeight() * 0.9f);
            // shapeRenderer.rect(Gdx.graphics.getWidth() / 2, boyY, boy.getWidth() * 0.9f, boy.getHeight() * 0.9f);


            for (int i = 0; i < numOfTubes; i++) {
                // shapeRenderer.rect(tubeVelocity[i], line, tube.getWidth()*0.9f, tube.getHeight() * 0.9f);
                if (Intersector.overlaps(kid, TUBE[i])) {
                    Gdx.app.log("Collision", "yes");
                    //touched = false;
                    //  boyY=line;
                    setDistance();
                    FirstTime=false; //
                    game = false;


                }
            }
        }
        else if (game==false )
        {

            if (FirstTime==false) {
                batch.draw(gameover, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            }
            if (Gdx.input.justTouched()) { // when the screen is touched
                if (boyY == line||boyY>line) {
                    touched = true;
                    velocity -= 30;

                    temp = boyY;
                    acc = 35; // acceleration starts , if you increase it -> the kid will go far when it jumps
                    game = true;
                }
                //upSpeed=0;
            }

        }

       // shapeRenderer.end();
        batch.end();
    }


    void setDistance() { // to generate tubes
        for (int i = 0; i < numOfTubes; i++) {
            tubeVelocity[i] = (Gdx.graphics.getWidth()) + (tube.getWidth()) + (i * distanceBetweenTubes);
            TUBE[i]=new Rectangle();
        }
    }

    void setClouds() { // to generate clouds
        for (int i = 0; i < numOfClouds; i++) {
            cloudVelocity[i] = (Gdx.graphics.getWidth()) + (cloud.getWidth()) + (i * DistanceBetweenClouds);
        }
    }


    public void dispose() {
        batch.dispose();

    }
}
