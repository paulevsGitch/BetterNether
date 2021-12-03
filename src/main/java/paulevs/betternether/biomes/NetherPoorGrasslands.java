package paulevs.betternether.biomes;

import java.util.Random;
import java.util.function.BiFunction;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.plants.StructureBlackApple;
import paulevs.betternether.structures.plants.StructureBlackBush;
import paulevs.betternether.structures.plants.StructureInkBush;
import paulevs.betternether.structures.plants.StructureMagmaFlower;
import paulevs.betternether.structures.plants.StructureNetherGrass;
import paulevs.betternether.structures.plants.StructureNetherWart;
import paulevs.betternether.structures.plants.StructureReeds;
import paulevs.betternether.structures.plants.StructureSmoker;
import paulevs.betternether.structures.plants.StructureWartSeed;
import ru.bclib.api.biomes.BCLBiomeBuilder;
import ru.bclib.world.biomes.BCLBiome;

public class NetherPoorGrasslands extends NetherBiome {
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
				   .structure(NetherBiomeBuilder.VANILLA_STRUCTURES.getBASTION_REMNANT());;
		}
		
		@Override
		public BiFunction<ResourceLocation, Biome, NetherBiome> getSupplier() {
			return NetherPoorGrasslands::new;
		}
	}
	
	public NetherPoorGrasslands(ResourceLocation biomeID, Biome biome) {
		super(biomeID, biome);
		
		addStructure("nether_reed", new StructureReeds(), StructureType.FLOOR, 0.05F, false);
		addStructure("nether_wart", new StructureNetherWart(), StructureType.FLOOR, 0.005F, true);
		addStructure("magma_flower", new StructureMagmaFlower(), StructureType.FLOOR, 0.05F, true);
		addStructure("smoker", new StructureSmoker(), StructureType.FLOOR, 0.005F, true);
		addStructure("ink_bush", new StructureInkBush(), StructureType.FLOOR, 0.005F, true);
		addStructure("black_apple", new StructureBlackApple(), StructureType.FLOOR, 0.001F, true);
		addStructure("black_bush", new StructureBlackBush(), StructureType.FLOOR, 0.002F, true);
		addStructure("wart_seed", new StructureWartSeed(), StructureType.FLOOR, 0.002F, true);
		addStructure("nether_grass", new StructureNetherGrass(), StructureType.FLOOR, 0.04F, true);
	}

	@Override
	public void genSurfColumn(LevelAccessor world, BlockPos pos, Random random) {
		switch (random.nextInt(3)) {
			case 0:
				BlocksHelper.setWithoutUpdate(world, pos, Blocks.SOUL_SOIL.defaultBlockState());
				break;
			case 1:
				BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.NETHERRACK_MOSS.defaultBlockState());
				break;
			default:
				super.genSurfColumn(world, pos, random);
				break;
		}
		for (int i = 1; i < random.nextInt(3); i++) {
			BlockPos down = pos.below(i);
			if (random.nextInt(3) == 0 && BlocksHelper.isNetherGround(world.getBlockState(down))) {
				BlocksHelper.setWithoutUpdate(world, down, Blocks.SOUL_SAND.defaultBlockState());
			}
		}
	}
}