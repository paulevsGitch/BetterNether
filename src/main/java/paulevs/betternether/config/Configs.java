package paulevs.betternether.config;

import paulevs.betternether.BetterNether;
import ru.bclib.config.PathConfig;

public class Configs {
	public static final PathConfig MAIN = new PathConfig(BetterNether.MOD_ID, "main");
	public static final PathConfig GENERATOR = new PathConfig(BetterNether.MOD_ID, "generator");
	public static final PathConfig BLOCKS = new PathConfig(BetterNether.MOD_ID, "blocks");
	public static final PathConfig ITEMS = new PathConfig(BetterNether.MOD_ID, "items");
	public static final PathConfig BIOMES = new PathConfig(BetterNether.MOD_ID, "biomes");
	public static final PathConfig MOBS = new PathConfig(BetterNether.MOD_ID, "mobs");
	public static final PathConfig RECIPES = new PathConfig(BetterNether.MOD_ID, "recipes");

	public static void saveConfigs() {
		MAIN.saveChanges();
		GENERATOR.saveChanges();
		BLOCKS.saveChanges();
		ITEMS.saveChanges();
		MOBS.saveChanges();
		RECIPES.saveChanges();
		BIOMES.saveChanges();
	}
}
