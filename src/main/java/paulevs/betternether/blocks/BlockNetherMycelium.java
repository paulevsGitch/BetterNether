package paulevs.betternether.blocks;

import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MaterialColor;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockNetherMycelium extends BlockBase
{
	public BlockNetherMycelium()
	{
		super(FabricBlockSettings.copy(Blocks.NETHERRACK).materialColor(MaterialColor.GRAY).build());
	}

	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
	{
		super.randomDisplayTick(state, world, pos, random);
		//if (random.nextInt(10) == 0)
		{
			world.addParticle(ParticleTypes.MYCELIUM, pos.getX() + random.nextDouble(), pos.getY() + 1.1D, pos.getZ() + random.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
	}
}