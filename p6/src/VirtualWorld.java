
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Random;
import edu.calpoly.spritely.AnimationFrame;
import edu.calpoly.spritely.ImageTile;
import edu.calpoly.spritely.Size;
import edu.calpoly.spritely.SolidColorTile;
import edu.calpoly.spritely.SpriteWindow;
import edu.calpoly.spritely.Tile;
import java.lang.Object;
import edu.calpoly.spritely.AnimationController;
import java.util.Collections;
import edu.calpoly.spritely.MouseClickedHandler;

/**
 * A representation of a virtual world, containing various entities
 * that move around a grid.  The data structures representing the
 * current state of the virtual world are split out in a separate
 * model class, called WorldModel.
 */
public final class VirtualWorld
{
    public static final Size TILE_SIZE = new Size(32, 32);
    public static final Size WORLD_SIZE = new Size(40, 30);
	public static int MouseClicked = 0;
    public static final Random rand = new Random();
    // Name, as decided by CSC 203 in Spring 2018:
    public static final String NAME = "Minecraft 2: Electric Boogaloo";
    public static final File IMAGE_DIR = new File("images");

    public static final String[] BACKGROUND = new String[] {
        "                   R                    ",
        "                    R                  R",
        " RR   RR   RR                           ",
        "R  R R  R R  R                          ",
        "   R R  R    R                          ",
        " RR  R  R  RR                           ",
        "R    R  R    R                          ",
        "R    R  R R  R                          ",
        "RRRR  RR   RR                           ",
        "                                        ",
        "                                        ",
        "                                        ",
        "                                        ",
        "                    R                  R",
        "                   R                    ",
        "                    R                  R",
        "                   R                    ",
        "                                        ",
        "                                        ",
        "                                        ",
        "                                        ",
        "                                        ",
        "                                        ",
        "                                        ",
        "                                        ",
        "                                        ",
        "                                        ",
        "                                        ",
        "                   R                    ",
        "                    R                   "
    };


    public static double timeScale;

    public static List<Tile> blacksmithTiles;
    public static List<Tile> blobTiles;
    public static List<Tile> minerTiles;
    public static List<Tile> minerFullTiles;
	public static List<Tile> cookieTiles;
    public static List<Tile> obstacleTiles;
    public static List<Tile> riverTiles;
    public static List<Tile> oreTiles;
    public static List<Tile> quakeTiles;
    public static List<Tile> veinTiles;
	public static List<Tile> wyvernTiles;
	public static List<Tile> freezeTiles;
    public static final Tile grass;
    public static final Tile rocks;


    public static WorldModel model;
    public static EventSchedule eventSchedule;
    public static SpriteWindow window;

    public VirtualWorld(double time) {

        window = new SpriteWindow(NAME, WORLD_SIZE);
        window.setFps(30f);
        window.setTileSize(TILE_SIZE);
        System.out.println(NAME + ".  Press 'q' to quit.");
        window.setKeyTypedHandler((char ch) -> {
            if (ch == 'q' || ch == 'Q') {
                window.stop();
            }
        });
        model = new WorldModel(WORLD_SIZE);
        eventSchedule = new EventSchedule();
        setupBackground();
        createInitialEntities();
        scheduleInitialActions(model, eventSchedule);
        timeScale = time;
    }


    static 
	{
        blacksmithTiles = makeSmiths();
        blobTiles = makeBlobs();
        minerTiles = makeMiners();
        minerFullTiles = makeMinerFull();
		cookieTiles = makeCookie();
        obstacleTiles = makeObstacles();
		wyvernTiles = makeWyvern();
		freezeTiles = makeFreezes();
        oreTiles = makeOres();
        quakeTiles = makeQuakes();
        veinTiles = makeVeins();
        grass = makeGrassTiles();
        rocks = makeRockTiles();
		riverTiles = makeRiver();

    }

    public static List<Tile> makeVeins() {
        List<Tile> newveinTiles = loadImages("vein", "V");
        List<Tile> immutable = Collections.unmodifiableList(newveinTiles);
        return immutable;
    }
    public static List<Tile> makeCookie() {
        List<Tile> newcookieTiles = loadImages("cookie", "C");
        List<Tile> immutable = Collections.unmodifiableList(newcookieTiles);
        return immutable;
    }

    public static List<Tile> makeQuakes() {
        List<Tile> quakeTiles = loadImages("quake", "QqQqQq");
        List<Tile> immutable = Collections.unmodifiableList(quakeTiles);
        return immutable;
    }

    public static List<Tile> makeRiver() {
        List<Tile> riverTiles = loadImages("river", "R");
        List<Tile> immutable = Collections.unmodifiableList(riverTiles);
        return immutable;
    }


    public static List<Tile> makeOres() {
        List<Tile> oreTiles = loadImages("ore", "$");;
        List<Tile> immutable = Collections.unmodifiableList(oreTiles);
        return immutable;
    }

    public static List<Tile> makeObstacles() {
        List<Tile> obsTiles = loadImages("obstacle", "O");
        List<Tile> immutable = Collections.unmodifiableList(obsTiles);
        return immutable;
    }

    public static List<Tile> makeWyvern() {
        List<Tile> wyvTiles = loadImages("wyvern", "wWwWwW");
        List<Tile> immutable = Collections.unmodifiableList(wyvTiles);
        return immutable;
    }

    public static List<Tile> makeFreezes() {
        List<Tile> freTiles = loadImages("freeze", "fFfF");
        List<Tile> immutable = Collections.unmodifiableList(freTiles);
        return immutable;
    }

    public static List<Tile> makeSmiths() {
        List<Tile> bsTiles = loadImages("blacksmith", "B");
        List<Tile> immutable = Collections.unmodifiableList(bsTiles);
        return immutable;
    }

    public static List<Tile> makeBlobs() {
        List<Tile> blobTiles = loadImages("blob", "===*===*=");
        List<Tile> immutable = Collections.unmodifiableList(blobTiles);
        return immutable;
    }

    public static List<Tile> makeMiners() {
        List<Tile> minerTiles = loadImages("miner", "mMmMm");
        List<Tile> immutable = Collections.unmodifiableList(minerTiles);
        return immutable;
    }

    public static List<Tile> makeMinerFull() {
        List<Tile> minerFullTiles = loadImages("miner_full", "mM$mM");
        List<Tile> immutable = Collections.unmodifiableList(minerFullTiles);
        return immutable;
    }


    public static Tile makeGrassTiles() {
        Tile getGrass = getImageTile("grass.png", '.');
        return getGrass;
    }

    public static Tile makeRockTiles() {
        Tile getRock = getImageTile("rocks.png", '=');
        return getRock;
    }



    public static Tile getImageTile(String imageFileName, char text) {
        Tile t = null;
        File f = new File(IMAGE_DIR, imageFileName);
        try {
            t = new ImageTile(f, TILE_SIZE, text);
        } catch (IOException ex) {
            System.out.println("Fatal error:  Image not found in " + f);
            ex.printStackTrace();
            System.exit(1);
        }
        return t;
    }

    public void setupBackground() {
        //Tile grass = getImageTile("grass.png", '.');
        //Tile rocks = getImageTile("rocks.png", '=');
        //char t = 'y';
        //SolidColorTile yellow = new SolidColorTile(yellow.YELLOW, t);
        for (int y = 0; y < WORLD_SIZE.height; y++) {
            for (int x = 0; x < WORLD_SIZE.width; x++) {
                char c = BACKGROUND[y].charAt(x);
                if (c == ' ') {
                    model.background[y][x] = grass;
                } else if (c == 'R') {
                    model.background[y][x] = rocks;
                } else {
                    assert false;
                }
            }
        }
    }


    public static double getTime(){
        return window.getTimeSinceStart();
    }
    // make animation action and then schedule it in schedule events


    public void createInitialEntities() {
        addInitial(new Blacksmith(new Point(0, 11), blacksmithTiles));
        addInitial(new Blacksmith(new Point(0, 29), blacksmithTiles));
        addInitial(new Blacksmith(new Point(19, 14), blacksmithTiles));
        addInitial(new Blacksmith(new Point(19, 29), blacksmithTiles));
        addInitial(new Blacksmith(new Point(20, 0), blacksmithTiles));
        addInitial(new Blacksmith(new Point(39, 0), blacksmithTiles));
        addInitial(new Blacksmith(new Point(39, 14), blacksmithTiles));
        addInitial(new Blacksmith(new Point(39, 29), blacksmithTiles));

        addInitial(new MinerNotFull(new Point(12,23), minerTiles, 954, 300, 2, 0));
        addInitial(new MinerNotFull(new Point(17,22), minerTiles, 982, 310, 2, 0));
        addInitial(new MinerNotFull(new Point(23,6), minerTiles, 777, 320, 2, 0));
        addInitial(new MinerNotFull(new Point(24,26), minerTiles, 851, 90, 2, 0));
        addInitial(new MinerNotFull(new Point(31,15), minerTiles, 933, 95, 2, 0));
        addInitial(new MinerNotFull(new Point(31,26), minerTiles, 734, 87,2, 0));
        addInitial(new MinerNotFull(new Point(37,10), minerTiles, 400, 33,2, 0));
        addInitial(new MinerNotFull(new Point(37,18), minerTiles, 888, 100,2,0));
        addInitial(new MinerNotFull(new Point(37,6), minerTiles, 991, 317,2,0));
        addInitial(new MinerNotFull(new Point(5,6), minerTiles, 992, 318,2,0));
        addInitial(new MinerNotFull(new Point(6,25), minerTiles, 930, 106,2,0));
        addInitial(new MinerNotFull(new Point(6,3), minerTiles, 813, 92,2,0));
        addInitial(new MinerNotFull(new Point(7,13), minerTiles, 913, 97,2,0));

        addInitial(new Obstacle(new Point(10, 23),obstacleTiles));
        addInitial(new Obstacle(new Point(10, 24),obstacleTiles));
        addInitial(new Obstacle(new Point(11, 21),obstacleTiles));
        addInitial(new Obstacle(new Point(11, 24),obstacleTiles));
        addInitial(new Obstacle(new Point(11, 25),obstacleTiles));
        addInitial(new Obstacle(new Point(12, 22),obstacleTiles));
        addInitial(new Obstacle(new Point(12, 25),obstacleTiles));
        addInitial(new Obstacle(new Point(12, 26),obstacleTiles));
        addInitial(new Obstacle(new Point(13, 22),obstacleTiles));
        addInitial(new Obstacle(new Point(13, 26),obstacleTiles));
        addInitial(new Obstacle(new Point(14, 23),obstacleTiles));
        addInitial(new Obstacle(new Point(14, 24),obstacleTiles));
        addInitial(new Obstacle(new Point(26, 26),obstacleTiles));
        addInitial(new Obstacle(new Point(27, 25),obstacleTiles));
        addInitial(new Obstacle(new Point(28, 19),obstacleTiles));
        addInitial(new Obstacle(new Point(28, 25),obstacleTiles));
        addInitial(new Obstacle(new Point(29, 20),obstacleTiles));
        addInitial(new Obstacle(new Point(29, 26),obstacleTiles));
        addInitial(new Obstacle(new Point(30, 21),obstacleTiles));
        addInitial(new Obstacle(new Point(31, 22),obstacleTiles));
        addInitial(new Obstacle(new Point(32, 23),obstacleTiles));
        addInitial(new Obstacle(new Point(5, 20),obstacleTiles));
        addInitial(new Obstacle(new Point(5, 21),obstacleTiles));
        addInitial(new Obstacle(new Point(6, 20),obstacleTiles));
        addInitial(new Obstacle(new Point(6, 21),obstacleTiles));
        addInitial(new Obstacle(new Point(7, 20),obstacleTiles));
        addInitial(new Obstacle(new Point(7, 21),obstacleTiles));
        addInitial(new Obstacle(new Point(8, 21),obstacleTiles));
        addInitial(new Obstacle(new Point(8, 22),obstacleTiles));
        addInitial(new Obstacle(new Point(9, 22),obstacleTiles));
        addInitial(new Obstacle(new Point(9, 23),obstacleTiles));

        addInitial(new Vein(new Point(10, 25), veinTiles, 8366));
        addInitial(new Vein(new Point(14, 22), veinTiles, 8248));
        addInitial(new Vein(new Point(21, 20), veinTiles, 9294));
        addInitial(new Vein(new Point(27, 6), veinTiles, 9456));
        addInitial(new Vein(new Point(28, 23), veinTiles, 13422));
        addInitial(new Vein(new Point(33, 11), veinTiles, 10278));
        addInitial(new Vein(new Point(33, 13), veinTiles, 10865));
        addInitial(new Vein(new Point(33, 3), veinTiles, 11101));
        addInitial(new Vein(new Point(34, 19), veinTiles, 11702));
        addInitial(new Vein(new Point(6, 11), veinTiles, 15026));
        addInitial(new Vein(new Point(7, 11), veinTiles, 9377));
        addInitial(new Vein(new Point(8, 11), veinTiles, 13146));
    }

    public static void addInitial(Entity entity) {
        assert !model.isOccupied(entity.getPosition());
        model.addEntity(entity);
    }

    /**
     * Load a list of images for an entity.  text gives a series of
     * characters that serve as the animation for the text 
     * representation of the entity when in text mode.
     */
    public static List<Tile> loadImages(String fileBasename, String text) {
        int len = text.length();
        List<Tile> result = new ArrayList<Tile>(len);
        if (len == 1) {
            result.add(getImageTile(fileBasename + ".png", text.charAt(0)));
        } else {
            for (int i = 1; i <= len; i++) {
                String name = fileBasename + i + ".png";
                result.add(getImageTile(name, text.charAt(i - 1)));
            }
        }
        return result;
    }

    public void scheduleInitialActions(WorldModel model,
                                              EventSchedule eventSchedule)
    {
        for (Entity entity : model.entities)
        {
	    if ((entity instanceof EntityAction) || (entity instanceof EntityAnimated)){
                 EntityAction ea = (EntityAction)entity;
                 ea.scheduleActions(eventSchedule, model);
            }

        }
        model.daynight.scheduleActions(eventSchedule);
    }

    /**
     * Entry point to run the virtual world simulation.
     */
    public void runSimulation() 
	{
        model.paint(window.getInitialFrame());

		MouseClickedHandler handler = (int x, int y) -> 
		{
			MouseClicked+= 1;
			if (MouseClicked == 1)
			{
				model.addEntity(new River(new Point(x, y), riverTiles)); 
				model.addEntity(new River(new Point(x + 1, y),riverTiles));
				model.addEntity(new River(new Point(x - 1, y),riverTiles));
				model.addEntity(new River(new Point(x, y + 1),riverTiles));
				model.addEntity(new River(new Point(x, y - 1),riverTiles));
				model.addEntity(new River(new Point(x -1, y - 1),riverTiles));
				model.addEntity(new River(new Point(x + 1, y + 1),riverTiles));
				model.addEntity(new River(new Point(x -1, y + 1),riverTiles));
				model.addEntity(new River(new Point(x + 1, y - 1),riverTiles));
			
			}
			else 
			{
				Predator p = new Predator(new Point(x, y), wyvernTiles, 950 , 150 ); 			//50 + rand.nextInt(100)
				model.addEntity(p);
				p.scheduleActions(eventSchedule, model);																					 // make a Predator class
			}
					
		};		//System.out.println("Clicked at " + x + "," + y)
		window.setMouseClickedHandler(handler);
        window.start();
        while (true) {
            AnimationFrame frame = window.waitForNextFrame();
            if (frame == null) {
                break;
            }
            eventSchedule.processEvents(window.getTimeSinceStart() * timeScale);
            model.paint(frame);
            window.showNextFrame();
        }
    }



}
