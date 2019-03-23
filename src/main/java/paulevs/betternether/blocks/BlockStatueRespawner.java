package paulevs.betternether.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockStatueRespawner extends BlockStatue
{
	public BlockStatueRespawner(String name)
	{
		super(name);
		this.setLightLevel(1F);
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (player.getHeldItemMainhand().getItem() == Items.GLOWSTONE_DUST)
		{
			if (!player.isCreative())
				player.getHeldItemMainhand().shrink(1);
			for (int i = 0; i < 100; i++)
				world.spawnParticle(EnumParticleTypes.REDSTONE,
						pos.getX() + world.rand.nextFloat(),
						pos.getY() + 1.5,
						pos.getZ() + world.rand.nextFloat(), 0, 0, 0);
			player.sendStatusMessage(new TextComponentTranslation("message.spawn_set", new Object[0]), true);
			player.setSpawnDimension(player.dimension);
			player.setSpawnPoint(pos.offset(((EnumFacing)state.getValue(FACING))), true);
			player.playSound(SoundEvents.ITEM_TOTEM_USE, 0.7F, 1.0F);
			return true;
		}
		player.sendStatusMessage(new TextComponentTranslation("message.spawn_help", new Object[0]), true);
		return false;
	}
}
