package paulevs.betternether.biomes;

import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.IWorld;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.structures.StructureType;
import paulevs.betternether.structures.plants.StructureBlackBush;
import paulevs.betternether.structures.plants.StructureSoulGrass;
import paulevs.betternether.structures.plants.StructureSoulVein;

public class NetherSoulPlain extends NetherBiome
{
	private static final Mutable POS = new Mutable();
	
	public NetherSoulPlain(String name)
	{
		super(new BiomeDefenition(name)
				.setColor(196, 113, 239)
				.setLoop(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
				.setAdditions(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS)
				.setMood(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD));
		addStructure("soul_vein", new StructureSoulVein(), StructureType.FLOOR, 0.5F, true);
		addStructure("black_bush", new StructureBlackBush(), StructureType.FLOOR, 0.02F, false);
		addStructure("soul_grass", new StructureSoulGrass(), StructureType.FLOOR, 0.3F, false);
	}
	
	@Override
	public void genSurfColumn(IWorld world, BlockPos pos, Random random)
	{
		POS.set(pos);
		for (int i = 0; i < random.nextInt(3) + 1; i++)
		{
			POS.setY(pos.getY() - i);
			if (BlocksHelper.isNetherGround(world.getBlockState(POS)))
				BlocksHelper.setWithoutUpdate(world, POS, Blocks.SOUL_SAND.getDefaultState());
		}
	}
}