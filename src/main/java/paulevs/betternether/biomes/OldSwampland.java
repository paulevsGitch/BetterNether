package paulevs.betternether.biomes;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.noise.OpenSimplexNoise;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.registry.SoundsRegistry;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.plants.StructureBlackBush;
import paulevs.betternether.structures.plants.StructureBlackVine;
import paulevs.betternether.structures.plants.StructureFeatherFern;
import paulevs.betternether.structures.plants.StructureJellyfishMushroom;
import paulevs.betternether.structures.plants.StructureOldWillow;
import paulevs.betternether.structures.plants.StructureReeds;
import paulevs.betternether.structures.plants.StructureSmoker;
import paulevs.betternether.structures.plants.StructureSoulVein;
import paulevs.betternether.structures.plants.StructureSwampGrass;
import paulevs.betternether.structures.plants.StructureWallBrownMushroom;
import paulevs.betternether.structures.plants.StructureWallMoss;
import paulevs.betternether.structures.plants.StructureWallRedMushroom;
import paulevs.betternether.structures.plants.StructureWillow;
import paulevs.betternether.structures.plants.StructureWillowBush;

public class OldSwampland extends NetherBiomeData {
	protected static final OpenSimplexNoise TERRAIN = new OpenSimplexNoise(523);

	public OldSwampland(String name) {
		super(new BiomeDefinition(name)
				.setFogColor(137, 19, 78)
				.setLoop(SoundsRegistry.AMBIENT_SWAMPLAND)
				.setAdditions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
				.setMood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
				.setMusic(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST)
				.setDefaultMobs(false)
				.addMobSpawn(EntityType.STRIDER, 40, 2, 4)
				.addMobSpawn(EntityType.MAGMA_CUBE, 40, 2, 4));
		addStructure("old_willow", new StructureOldWillow(), StructureType.FLOOR, 0.02F, false);
		addStructure("willow", new StructureWillow(), StructureType.FLOOR, 0.02F, false);
		addStructure("willow_bush", new StructureWillowBush(), StructureType.FLOOR, 0.1F, true);
		addStructure("feather_fern", new StructureFeatherFern(), StructureType.FLOOR, 0.05F, true);
		addStructure("nether_reed", new StructureReeds(), StructureType.FLOOR, 0.8F, false);
		addStructure("soul_vein", new StructureSoulVein(), StructureType.FLOOR, 0.5F, false);
		addStructure("smoker", new StructureSmoker(), StructureType.FLOOR, 0.05F, false);
		addStructure("jellyfish_mushroom", new StructureJellyfishMushroom(), StructureType.FLOOR, 0.03F, true);
		addStructure("black_bush", new StructureBlackBush(), StructureType.FLOOR, 0.01F, false);
		addStructure("swamp_grass", new StructureSwampGrass(), StructureType.FLOOR, 0.4F, false);
		addStructure("black_vine", new StructureBlackVine(), StructureType.CEIL, 0.4F, true);
		addStructure("wall_moss", new StructureWallMoss(), StructureType.WALL, 0.8F, true);
		addStructure("wall_red_mushroom", new StructureWallRedMushroom(), StructureType.WALL, 0.8F, true);
		addStructure("wall_brown_mushroom", new StructureWallBrownMushroom(), StructureType.WALL, 0.8F, true);
	}

	@Override
	public void genSurfColumn(LevelAccessor world, BlockPos pos, Random random) {
		double value = TERRAIN.eval(pos.getX() * 0.2, pos.getY() * 0.2, pos.getZ() * 0.2);
		if (value > 0.3 && validWalls(world, pos))
			BlocksHelper.setWithoutUpdate(world, pos, Blocks.LAVA.defaultBlockState());
		else if (value > -0.3)
			BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.SWAMPLAND_GRASS.defaultBlockState());
		else {
			value = TERRAIN.eval(pos.getX() * 0.5, pos.getZ() * 0.5);
			BlocksHelper.setWithoutUpdate(world, pos, value > 0 ? Blocks.SOUL_SAND.defaultBlockState() : Blocks.SOUL_SOIL.defaultBlockState());
		}
	}

	protected boolean validWalls(LevelAccessor world, BlockPos pos) {
		return validWall(world, pos.below())
				&& validWall(world, pos.north())
				&& validWall(world, pos.south())
				&& validWall(world, pos.east())
				&& validWall(world, pos.west());
	}

	protected boolean validWall(LevelAccessor world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		return BlocksHelper.isLava(state) || BlocksHelper.isNetherGround(state);
	}
}