package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.plants.StructureBlackApple;
import paulevs.betternether.structures.plants.StructureBlackBush;
import paulevs.betternether.structures.plants.StructureInkBush;
import paulevs.betternether.structures.plants.StructureMagmaFlower;
import paulevs.betternether.structures.plants.StructureNetherGrass;
import paulevs.betternether.structures.plants.StructureNetherWart;
import paulevs.betternether.structures.plants.StructureReeds;
import paulevs.betternether.structures.plants.StructureSmoker;
import paulevs.betternether.structures.plants.StructureWallBrownMushroom;
import paulevs.betternether.structures.plants.StructureWallMoss;
import paulevs.betternether.structures.plants.StructureWallRedMushroom;
import paulevs.betternether.structures.plants.StructureWartSeed;

public class NetherGrasslands extends NetherBiome 
{
	public NetherGrasslands(String name)
	{
		super(new BiomeDefenition(name)
				.setColor(113, 73, 133)
				.setLoop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
				.setAdditions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
				.setMood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD));
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
	public void genSurfColumn(IWorld world, BlockPos pos, Random random)
	{
		switch(random.nextInt(3))
		{
		case 0:
			BlocksHelper.setWithoutUpdate(world, pos, Blocks.SOUL_SOIL.getDefaultState());
			break;
		case 1:
			BlocksHelper.setWithoutUpdate(world, pos, BlocksRegistry.NETHERRACK_MOSS.getDefaultState());
			break;
		default:
			super.genSurfColumn(world, pos, random);
			break;
		}
		for (int i = 1; i < random.nextInt(3); i++)
		{
			BlockPos down = pos.down(i);
			if (random.nextInt(3) == 0 && BlocksHelper.isNetherGround(world.getBlockState(down)))
			{
				BlocksHelper.setWithoutUpdate(world, down, Blocks.SOUL_SAND.getDefaultState());
			}
		}
	}
}
