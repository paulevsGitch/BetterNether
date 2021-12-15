package paulevs.betternether.world.biomes;

import java.util.Random;
import java.util.function.BiFunction;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.noise.OpenSimplexNoise;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.registry.SoundsRegistry;
import paulevs.betternether.world.NetherBiome;
import paulevs.betternether.world.NetherBiomeBuilder;
import paulevs.betternether.world.NetherBiomeConfig;
import paulevs.betternether.world.structures.StructureType;
import paulevs.betternether.world.structures.plants.StructureBlackBush;
import paulevs.betternether.world.structures.plants.StructureBlackVine;
import paulevs.betternether.world.structures.plants.StructureFeatherFern;
import paulevs.betternether.world.structures.plants.StructureJellyfishMushroom;
import paulevs.betternether.world.structures.plants.StructureReeds;
import paulevs.betternether.world.structures.plants.StructureSmoker;
import paulevs.betternether.world.structures.plants.StructureSoulVein;
import paulevs.betternether.world.structures.plants.StructureSwampGrass;
import paulevs.betternether.world.structures.plants.StructureWallBrownMushroom;
import paulevs.betternether.world.structures.plants.StructureWallMoss;
import paulevs.betternether.world.structures.plants.StructureWallRedMushroom;
import paulevs.betternether.world.structures.plants.StructureWillow;
import paulevs.betternether.world.structures.plants.StructureWillowBush;
import ru.bclib.api.biomes.BCLBiomeBuilder;

public class NetherSwampland extends NetherBiome {
	public static class Config extends NetherBiomeConfig {
		public Config(String name) {
			super(name);
		}
		
		@Override
		protected void addCustomBuildData(BCLBiomeBuilder builder) {
			builder.fogColor(137, 19, 78)
				   .loop(SoundsRegistry.AMBIENT_SWAMPLAND)
				   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
				   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
				   .music(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST)
				   .spawn(EntityType.STRIDER, 40, 2, 4)
				   .spawn(EntityType.MAGMA_CUBE, 40, 2, 4)
				   .structure(NetherBiomeBuilder.VANILLA_STRUCTURES.getBASTION_REMNANT())
				   .structure(NetherBiomeBuilder.VANILLA_STRUCTURES.getNETHER_BRIDGE());
		}
		
		@Override
		public BiFunction<ResourceLocation, Biome, NetherBiome> getSupplier() {
			return NetherSwampland::new;
		}
		
		@Override
		public boolean spawnVanillaMobs() {
			return false;
		}
	}
	
	protected static final OpenSimplexNoise TERRAIN = new OpenSimplexNoise(523);
	
	public NetherSwampland(ResourceLocation biomeID, Biome biome) {
		super(biomeID, biome);
		
		addStructure("willow", new StructureWillow(), StructureType.FLOOR, 0.05F, false);
		addStructure("willow_bush", new StructureWillowBush(), StructureType.FLOOR, 0.2F, true);
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
