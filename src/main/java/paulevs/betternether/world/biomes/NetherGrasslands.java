package paulevs.betternether.world.biomes;

import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import paulevs.betternether.MHelper;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.world.structures.StructureType;
import paulevs.betternether.world.structures.plants.StructureBlackApple;
import paulevs.betternether.world.structures.plants.StructureBlackBush;
import paulevs.betternether.world.structures.plants.StructureInkBush;
import paulevs.betternether.world.structures.plants.StructureMagmaFlower;
import paulevs.betternether.world.structures.plants.StructureNetherGrass;
import paulevs.betternether.world.structures.plants.StructureNetherWart;
import paulevs.betternether.world.structures.plants.StructureReeds;
import paulevs.betternether.world.structures.plants.StructureSmoker;
import paulevs.betternether.world.structures.plants.StructureWallBrownMushroom;
import paulevs.betternether.world.structures.plants.StructureWallMoss;
import paulevs.betternether.world.structures.plants.StructureWallRedMushroom;
import paulevs.betternether.world.structures.plants.StructureWartSeed;
import ru.bclib.api.biomes.BCLBiomeBuilder;
import ru.bclib.api.surface.rules.SwitchRuleSource;

public class NetherGrasslands extends NetherBiome {
	private static final SurfaceRules.RuleSource SOUL_SOIL = SurfaceRules.state(Blocks.SOUL_SOIL.defaultBlockState());
	private static final SurfaceRules.RuleSource MOSS = SurfaceRules.state(NetherBlocks.NETHERRACK_MOSS.defaultBlockState());
	private static final SurfaceRules.RuleSource NETHERRACK = SurfaceRules.state(Blocks.NETHERRACK.defaultBlockState());
	
	private static final SurfaceRules.RuleSource BLUE = SurfaceRules.state(Blocks.BLUE_CONCRETE.defaultBlockState());
	private static final SurfaceRules.RuleSource LIGHT_BLUE = SurfaceRules.state(Blocks.LIGHT_BLUE_CONCRETE.defaultBlockState());
	private static final SurfaceRules.RuleSource CYAN = SurfaceRules.state(Blocks.CYAN_CONCRETE.defaultBlockState());
	private static final SurfaceRules.RuleSource GREEN = SurfaceRules.state(Blocks.GREEN_CONCRETE.defaultBlockState());
	private static final SurfaceRules.RuleSource LIME_GREEN = SurfaceRules.state(Blocks.LIME_CONCRETE.defaultBlockState());
	private static final SurfaceRules.RuleSource YELLOW = SurfaceRules.state(Blocks.YELLOW_CONCRETE.defaultBlockState());
	private static final SurfaceRules.RuleSource ORANGE = SurfaceRules.state(Blocks.ORANGE_CONCRETE.defaultBlockState());
	private static final SurfaceRules.RuleSource RED = SurfaceRules.state(Blocks.RED_CONCRETE.defaultBlockState());
	private static final SurfaceRules.RuleSource PINK = SurfaceRules.state(Blocks.PINK_CONCRETE.defaultBlockState());
	private static final SurfaceRules.RuleSource PURPLE = SurfaceRules.state(Blocks.PURPLE_CONCRETE.defaultBlockState());
	private static final SurfaceRules.RuleSource MAGENTA = SurfaceRules.state(Blocks.MAGENTA_CONCRETE.defaultBlockState());
	private static final SurfaceRules.RuleSource BLACK = SurfaceRules.state(Blocks.BLACK_CONCRETE.defaultBlockState());
	//List.of(BLUE, LIGHT_BLUE, CYAN, GREEN, LIME_GREEN, YELLOW, ORANGE, RED, PINK, MAGENTA, PURPLE, BLACK)
	
	
	public static class Config extends NetherBiomeConfig {
		public Config(String name) {
			super(name);
		}
		
		@Override
		protected void addCustomBuildData(BCLBiomeBuilder builder) {
			builder.fogColor(113, 73, 133)
				   .loop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
				   .additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
				   .mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
				   .music(SoundEvents.MUSIC_BIOME_NETHER_WASTES)
				   .structure(NetherBiomeBuilder.VANILLA_STRUCTURES.getBASTION_REMNANT())
				   .surface(new SwitchRuleSource(ctx -> {
					   final int depth = ctx.getStoneDepthAbove();
					   if (depth<=1) return MHelper.RANDOM.nextInt(3);
					   if (depth<=MHelper.RANDOM.nextInt(3)+1) return 0;
					   return 2;
				   }, List.of(SOUL_SOIL, MOSS, NETHERRACK) ))
			;
		}
		
		@Override
		public BiFunction<ResourceLocation, Biome, NetherBiome> getSupplier() {
			return NetherGrasslands::new;
		}
	}
	
	public NetherGrasslands(ResourceLocation biomeID, Biome biome) {
		super(biomeID, biome);
		
		addStructure("nether_reed", new StructureReeds(), StructureType.FLOOR, 0.5F, false);
		addStructure("nether_wart", new StructureNetherWart(), StructureType.FLOOR, 0.05F, true);
		addStructure("magma_flower", new StructureMagmaFlower(), StructureType.FLOOR, 0.5F, true);
		addStructure("smoker", new StructureSmoker(), StructureType.FLOOR, 0.05F, true);
		addStructure("ink_bush", new StructureInkBush(), StructureType.FLOOR, 0.05F, true);
		addStructure("black_apple", new StructureBlackApple(), StructureType.FLOOR, 0.01F, true);
		addStructure("black_bush", new StructureBlackBush(), StructureType.FLOOR, 0.02F, true);
		addStructure("wart_seed", new StructureWartSeed(), StructureType.FLOOR, 0.02F, true);
		addStructure("nether_grass", new StructureNetherGrass(), StructureType.FLOOR, 0.4F, true);
		addStructure("wall_moss", new StructureWallMoss(), StructureType.WALL, 0.8F, true);
		addStructure("wall_red_mushroom", new StructureWallRedMushroom(), StructureType.WALL, 0.8F, true);
		addStructure("wall_brown_mushroom", new StructureWallBrownMushroom(), StructureType.WALL, 0.8F, true);
	}

	@Override
	public void genSurfColumn(LevelAccessor world, BlockPos pos, Random random) {
//		switch (random.nextInt(3)) {
//			case 0 -> BlocksHelper.setWithoutUpdate(world, pos, Blocks.SOUL_SOIL.defaultBlockState());
//			case 1 -> BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.NETHERRACK_MOSS.defaultBlockState());
//			default -> super.genSurfColumn(world, pos, random);
//		}
//
//		for (int i = 1; i < random.nextInt(3); i++) {
//			BlockPos down = pos.below(i);
//			if (random.nextInt(3) == 0 && BlocksHelper.isNetherGround(world.getBlockState(down))) {
//				BlocksHelper.setWithoutUpdate(world, down, Blocks.SOUL_SAND.defaultBlockState());
//			}
//		}
	}
}
