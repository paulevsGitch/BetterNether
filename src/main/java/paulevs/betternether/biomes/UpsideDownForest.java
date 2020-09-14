package paulevs.betternether.biomes;

import net.minecraft.sound.SoundEvents;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.plants.StructureAnchorTree;
import paulevs.betternether.structures.plants.StructureAnchorTreeBranch;
import paulevs.betternether.structures.plants.StructureAnchorTreeRoot;
import paulevs.betternether.structures.plants.StructureJungleMoss;
import paulevs.betternether.structures.plants.StructureMossCover;
import paulevs.betternether.structures.plants.StructureWallBrownMushroom;
import paulevs.betternether.structures.plants.StructureWallRedMushroom;

public class UpsideDownForest extends NetherBiome
{
	public UpsideDownForest(String name)
	{
		super(new BiomeDefinition(name)
				.setFogColor(111, 188, 111)
				.setLoop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
				.setAdditions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
				.setMood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
				.setMusic(SoundEvents.MUSIC_NETHER_CRIMSON_FOREST)
				.setBNStructures(false));
		this.setNoiseDensity(0.5F);
		addStructure("anchor_tree", new StructureAnchorTree(), StructureType.CEIL, 0.2F, false);
		addStructure("anchor_tree_root", new StructureAnchorTreeRoot(), StructureType.CEIL, 0.03F, false);
		addStructure("anchor_tree_branch", new StructureAnchorTreeBranch(), StructureType.CEIL, 0.02F, true);
		addStructure("moss_cover", new StructureMossCover(), StructureType.FLOOR, 0.6F, false);
		addStructure("jungle_moss", new StructureJungleMoss(), StructureType.WALL, 0.4F, true);
		addStructure("wall_red_mushroom", new StructureWallRedMushroom(), StructureType.WALL, 0.4F, true);
		addStructure("wall_brown_mushroom", new StructureWallBrownMushroom(), StructureType.WALL, 0.4F, true);
	}
}
