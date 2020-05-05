package paulevs.betternether.blocks;

import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockEyeball extends BlockEyeBase
{
	public BlockEyeball()
	{
		super(FabricBlockSettings.of(Material.WOOD)
				.materialColor(MaterialColor.BROWN)
				.sounds(BlockSoundGroup.SLIME)
				.hardness(0.5F)
				.resistance(0.5F));
	}

	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
	{
		if (random.nextInt(5) == 0)
		{
			double x = pos.getX() + random.nextDouble();
			double y = pos.getY() + random.nextDouble() * 0.3;
			double z = pos.getZ() + random.nextDouble();
			world.addParticle(ParticleTypes.DRIPPING_WATER, x, y, z, 0, 0, 0);
		}
	}
}
