package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.registers.BlocksRegister;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.plants.StructureBlackBush;
import paulevs.betternether.structures.plants.StructureBlackVine;
import paulevs.betternether.structures.plants.StructureReeds;
import paulevs.betternether.structures.plants.StructureSmoker;
import paulevs.betternether.structures.plants.StructureSoulVein;
import paulevs.betternether.structures.plants.StructureSwampGrass;
import paulevs.betternether.structures.plants.StructureWallBrownMushroom;
import paulevs.betternether.structures.plants.StructureWallMoss;
import paulevs.betternether.structures.plants.StructureWallRedMushroom;
import paulevs.betternether.structures.plants.StructureWillow;

public class NetherSwampland extends NetherBiome
{
	public NetherSwampland(String name)
	{
		super(new BiomeDefenition(name)
				.setColor(126, 32, 57)
				.setLoop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
				.setAdditions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
				.setMood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD));
		addStructure("willow", new StructureWillow(), StructureType.FLOOR, 0.1F, false);
		addStructure("nether_reed", new StructureReeds(), StructureType.FLOOR, 0.8F, false);
		addStructure("soul_vein", new StructureSoulVein(), StructureType.FLOOR, 0.5F, false);
		addStructure("smoker", new StructureSmoker(), StructureType.FLOOR, 0.1F, false);
		addStructure("black_bush", new StructureBlackBush(), StructureType.FLOOR, 0.02F, false);
		addStructure("nether_grass", new StructureSwampGrass(), StructureType.FLOOR, 0.4F, false);
		addStructure("black_vine", new StructureBlackVine(), StructureType.CEIL, 0.4F, false);
		addStructure("wall_moss", new StructureWallMoss(), StructureType.WALL, 0.8F, true);
		addStructure("wall_red_mushroom", new StructureWallRedMushroom(), StructureType.WALL, 0.8F, true);
		addStructure("wall_brown_mushroom", new StructureWallBrownMushroom(), StructureType.WALL, 0.8F, true);
	}
	
	@Override
	public void genSurfColumn(IWorld world, BlockPos pos, Random random)
	{
		switch (random.nextInt(4))
		{
		case 0:
			if (validWall(world, pos.down()) && validWall(world, pos.north()) && validWall(world, pos.south()) && validWall(world, pos.east()) && validWall(world, pos.west()))
				BlocksHelper.setWithoutUpdate(world, pos, Blocks.LAVA.getDefaultState());
			else
				BlocksHelper.setWithoutUpdate(world, pos, Blocks.SOUL_SAND.getDefaultState());
			break;
		case 1:
			BlocksHelper.setWithoutUpdate(world, pos, Blocks.SOUL_SAND.getDefaultState());
		case 2:
			BlocksHelper.setWithoutUpdate(world, pos, BlocksRegister.NETHERRACK_MOSS.getDefaultState());
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
	
	protected boolean validWall(IWorld world, BlockPos pos)
	{
		BlockState state = world.getBlockState(pos);
		return BlocksHelper.isLava(state) || BlocksHelper.isNetherGround(state);
	}
}
