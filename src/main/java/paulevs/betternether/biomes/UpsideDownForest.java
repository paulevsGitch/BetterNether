package paulevs.betternether.biomes;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.decorations.StructureForestLitter;
import paulevs.betternether.structures.plants.StructureAnchorTree;
import paulevs.betternether.structures.plants.StructureAnchorTreeBranch;
import paulevs.betternether.structures.plants.StructureAnchorTreeRoot;
import paulevs.betternether.structures.plants.StructureCeilingMushrooms;
import paulevs.betternether.structures.plants.StructureHookMushroom;
import paulevs.betternether.structures.plants.StructureJungleMoss;
import paulevs.betternether.structures.plants.StructureMossCover;
import paulevs.betternether.structures.plants.StructureNeonEquisetum;
import paulevs.betternether.structures.plants.StructureNetherSakura;
import paulevs.betternether.structures.plants.StructureNetherSakuraBush;
import paulevs.betternether.structures.plants.StructureWallBrownMushroom;
import paulevs.betternether.structures.plants.StructureWallRedMushroom;
import paulevs.betternether.structures.plants.StructureWhisperingGourd;
import ru.bclib.api.biomes.BCLBiomeBuilder;

public class UpsideDownForest extends NetherBiomeData {
	@Override
	protected void addCustomBuildData(BCLBiomeBuilder builder){
		builder
			.fogColor(111, 188, 111)
			.loop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
			.additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
			.mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
			.music(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST);
	}
	
	@Override
	public boolean hasStalactites() {
		return false;
	}
	
	@Override
	public boolean hasBNStructures() {
		return false;
	}
	
	public UpsideDownForest(String name) {
		super(name);
		
		this.setNoiseDensity(0.5F);
		
		addStructure("anchor_tree", new StructureAnchorTree(), StructureType.CEIL, 0.2F, false);
		addStructure("anchor_tree_root", new StructureAnchorTreeRoot(), StructureType.CEIL, 0.03F, false);
		addStructure("anchor_tree_branch", new StructureAnchorTreeBranch(), StructureType.CEIL, 0.02F, true);
		addStructure("nether_sakura", new StructureNetherSakura(), StructureType.CEIL, 0.01F, true);
		addStructure("nether_sakura_bush", new StructureNetherSakuraBush(), StructureType.FLOOR, 0.01F, true);
		addStructure("moss_cover", new StructureMossCover(), StructureType.FLOOR, 0.6F, false);
		addStructure("jungle_moss", new StructureJungleMoss(), StructureType.WALL, 0.4F, true);
		addStructure("wall_red_mushroom", new StructureWallRedMushroom(), StructureType.WALL, 0.4F, true);
		addStructure("wall_brown_mushroom", new StructureWallBrownMushroom(), StructureType.WALL, 0.4F, true);
		addStructure("forest_litter", new StructureForestLitter(), StructureType.FLOOR, 0.1F, false);
		addStructure("ceiling_mushrooms", new StructureCeilingMushrooms(), StructureType.CEIL, 1F, false);
		addStructure("neon_equisetum", new StructureNeonEquisetum(), StructureType.CEIL, 0.1F, true);
		addStructure("hook_mushroom", new StructureHookMushroom(), StructureType.CEIL, 0.03F, true);
		addStructure("whispering_gourd", new StructureWhisperingGourd(), StructureType.CEIL, 0.02F, true);
	}

	@Override
	public void genSurfColumn(LevelAccessor world, BlockPos pos, Random random) {
		BlocksHelper.setWithoutUpdate(world, pos, random.nextInt(3) == 0 ? NetherBlocks.NETHERRACK_MOSS.defaultBlockState() : Blocks.NETHERRACK.defaultBlockState());
	}
}
