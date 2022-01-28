package com.dylan773.finalyearproject.render.levels;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.dylan773.finalyearproject.EducationGame;
import com.dylan773.finalyearproject.entities.PlayerTest;
import com.dylan773.finalyearproject.render.screens.GameScene;



//TODO - change the map load directory to sort out names

public class HistoryTest extends GameScene {
    /*
     * Fields
     */
    public TiledMap map = new TmxMapLoader().load("levels/history/history.tmx");
    //private Player player = new Player(40, 40, Assets.KNIGHT_SPRITE);
    private EducationGame game;

    // Box2D
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private PlayerTest playerTest;


    public HistoryTest(EducationGame game) {
        this.game = game;
        world = new World(new Vector2(0, 0), true);
        debugRenderer = new Box2DDebugRenderer();

        getCamera().zoom = 0.7f;

        // Box2D variables
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        //TiledMapObject.parseTiledObjectLayer(world, map.getLayers().get(4).getObjects());

        for (MapObject object : map.getLayers().get("Collision").getObjects()) {
            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set(rectangle.getX() + rectangle.getWidth() / 2, rectangle.getY() + rectangle.getHeight() / 2);

            body = world.createBody(bodyDef);
            shape.setAsBox(rectangle.getWidth() / 2, rectangle.getHeight() / 2);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

            }

            @Override
            public void endContact(Contact contact) {
                System.out.println("contact end");

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }

        });
        // Creation of the player
        playerTest = new PlayerTest(world);
        playerTest.historyMap = this;

        RectangleMapObject spawn = (RectangleMapObject) map.getLayers().get("objects").getObjects().get("spawn");
        playerTest.pos.set(spawn.getRectangle().x, spawn.getRectangle().y);

        createGameLevel(map, playerTest);
        // createGameLevel(map, player);
        //createGameLevelTest(map, playerTest); // abstract method from parent
    }



    @Override
    public void render(float delta) {
        super.render(delta);

        world.step(1 / 60f, 6, 2);
        debugRenderer = new Box2DDebugRenderer();
        debugRenderer.render(world, getCamera().combined);

        //getCamera().position.set(playerTest.pos, 0);
        desiredCamPos.set(playerTest.pos, 0);

        // linear interoperation - creates a delay on the camera position to player.
        getCamera().position.lerp(desiredCamPos, 0.08f);


        //getCamera().zoom -= .1;
//        getCamera().position.x +=1;
//        getCamera().position.y +=1;
    }
}