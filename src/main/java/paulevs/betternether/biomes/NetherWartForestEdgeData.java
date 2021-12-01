package paulevs.betternether.biomes;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.NetherBlocks;
import paulevs.betternether.registry.EntityRegistry;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.plants.StructureBlackBush;
import paulevs.betternether.structures.plants.StructureNetherWart;
import paulevs.betternether.structures.plants.StructureWartSeed;
import ru.bclib.api.biomes.BCLBiomeBuilder;

public class NetherWartForestEdgeData extends NetherBiomeData {
	@Override
	protected void addCustomBuildData(BCLBiomeBuilder builder){
		builder
			.fogColor(191, 28, 28)
			.loop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
			.additions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
			.mood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD)
			.music(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST)
			.spawn(EntityRegistry.FLYING_PIG, 20, 2, 4);
	}
	
	public NetherWartForestEdgeData(String name) {
		super(name);
		
		addStructure("nether_wart", new StructureNetherWart(), StructureType.FLOOR, 0.02F, false);
		addStructure("wart_seed", new StructureWartSeed(), StructureType.FLOOR, 0.01F, false);
		addStructure("black_bush", new StructureBlackBush(), StructureType.FLOOR, 0.01F, false);
	}

	@Override
	public void genSurfColumn(LevelAccessor world, BlockPos pos, Random random) {
		switch (random.nextInt(3)) {
			case 0:
				super.genSurfColumn(world, pos, random);
				break;
			case 1:
				BlocksHelper.setWithoutUpdate(world, pos, Blocks.SOUL_SAND.defaultBlockState());
				break;
			case 2:
				BlocksHelper.setWithoutUpdate(world, pos, Blocks.SOUL_SOIL.defaultBlockState());
				break;
			case 3:
				BlocksHelper.setWithoutUpdate(world, pos, NetherBlocks.NETHERRACK_MOSS.defaultBlockState());
				break;
		}
		for (int i = 1; i < random.nextInt(3); i++) {
			BlockPos down = pos.below(i);
			if (random.nextInt(3) == 0 && BlocksHelper.isNetherGround(world.getBlockState(down)))
				BlocksHelper.setWithoutUpdate(world, down, Blocks.SOUL_SAND.defaultBlockState());
		}
	}
}