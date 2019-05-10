import com.brad.latchConnect.serialization.data.LCDatabase;
import com.brad.latchConnect.serialization.data.LCField;
import com.brad.latchConnect.serialization.data.LCObject;
import com.brad.latchConnect.serialization.data.LCString;

import java.util.ArrayList;
import java.util.List;

public class Sandbox {

    static class Level {

        private String name;
        private String path;
        private int width, height;
        private List<Entity> entities = new ArrayList<>();

        private Level () {}

        public Level(String path) {
            this.name = "Level One";
            this.path = path;
            width = 64;
            height = 128;
        }

        public void add(Entity e) {
            e.init(this);
            entities.add(e);
        }

        public void update() {

        }

        public void render() {

        }

        public void save(String path) {
            LCDatabase database = new LCDatabase(name);
            LCObject object = new LCObject("LevelData");
            object.addString(LCString.Create("name", name));
            object.addString(LCString.Create("path", this.path));
            object.addField(LCField.Integer("width", width));
            object.addField(LCField.Integer("height", height));
            object.addField(LCField.Integer("entityCount", entities.size()));
            database.addObject(object);
            for (int i = 0; i < entities.size(); i++) {
                Entity e = entities.get(i);
                LCObject entity = new LCObject("E" + i);
                byte type = 0;
                if (e instanceof Player) {
                    type = 1;
                }
                e.serialize(entity);
                entity.addField(LCField.Byte("type", type));
                entity.addField(LCField.Integer("arrayIndex", i));
                database.addObject(entity);
            }
            database.serializeToFile(path);
        }

        public static Level load(String path) {
            LCDatabase database = LCDatabase.DeserializeFromFile(path);
            LCObject levelData = database.findObject("LevelData");

            Level result = new Level();
            result.name = levelData.findString("name").getString();
            result.path = levelData.findString("path").getString();
            result.width = levelData.findField("width").getInt();
            result.height = levelData.findField("height").getInt();
            int entityCount = levelData.findField("entityCount").getInt();

            for (int i = 0; i < entityCount; i++) {
                LCObject entity = database.findObject("E" + i);
                Entity e;
                if (entity.findField("type").getByte() == 1) {
                    e = Player.deserialize(entity);
                } else {
                    e = Entity.deserialize(entity);
                }
                result.add(e);
            }
            return result;
        }
    }

    static class Entity {

        protected int x;
        protected int y;
        protected boolean removed = false;
        protected Level level;

        public void Entity() {
            x = 0;
            y = 0;
        }

        public void init(Level level) {
            this.level = level;
        }

        public void serialize(LCObject object) {
            object.addField(LCField.Integer("x", x));
            object.addField(LCField.Integer("y", y));
            object.addField(LCField.Boolean("removed", removed));
        }

        public static Entity deserialize(LCObject object) {
            Entity result = new Entity();
            result.x = object.findField("x").getInt();
            result.y = object.findField("y").getInt();
            result.removed = object.findField("removed").getBoolean();
            return result;
        }

    }

    static class Player extends Entity {

        private String name;
        private String avatarPath;

        private Player() {}

        public Player(String name, int x, int y) {
            this.x = x;
            this.y = y;
            this.name = name;
            avatarPath = "res/avatar.png";
        }

        public void serialize(LCObject object) {
            super.serialize(object);
            object.addString(LCString.Create("name", name));
            object.addString(LCString.Create("avatarPath", avatarPath));
        }


        public static Player deserialize(LCObject object) {
            Entity e = Entity.deserialize(object);
            Player result = new Player();
            result.x = e.x;
            result.y = e.y;
            result.removed = e.removed;

            result.name = object.findString("name").getString();
            result.avatarPath = object.findString("avatarPath").getString();
            return result;
        }

    }

    public void play() {
        {
            Entity mob = new Entity();
            Player player = new Player("yaBoiBrad", 40, 28);

            Level level = new Level("res/level.lvl");
            level.add(mob);
            level.add(player);


            level.save("level.lcdb");
        }
        {
            Level level = Level.load("level.lcdb");
            System.out.println();
        }
    }
}
