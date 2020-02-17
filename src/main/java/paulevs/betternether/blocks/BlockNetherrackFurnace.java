package paulevs.betternether.blocks;

import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.container.NameableContainerFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import paulevs.betternether.blockentities.BlockEntityFurnace;

public class BlockNetherrackFurnace extends AbstractFurnaceBlock
{
	public BlockNetherrackFurnace()
	{
		super(FabricBlockSettings.copy(Blocks.NETHER_BRICKS).build());
	}
	
	public BlockEntity createBlockEntity(BlockView view)
	{
		return new BlockEntityFurnace();
	}

	protected void openContainer(World world, BlockPos pos, PlayerEntity player)
	{
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof BlockEntityFurnace)
		{
			player.openContainer((NameableContainerFactory) blockEntity);
			player.incrementStat(Stats.INTERACT_WITH_FURNACE);
		}
	}

	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
	{
		if ((Boolean) state.get(LIT))
		{
			double d = (double) pos.getX() + 0.5D;
			double e = (double) pos.getY();
			double f = (double) pos.getZ() + 0.5D;
			if (random.nextDouble() < 0.1D)
			{
				world.playSound(d, e, f, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
			}

			Direction direction = (Direction) state.get(FACING);
			Direction.Axis axis = direction.getAxis();
			double h = random.nextDouble() * 0.6D - 0.3D;
			double i = axis == Direction.Axis.X ? (double) direction.getOffsetX() * 0.52D : h;
			double j = random.nextDouble() * 6.0D / 16.0D;
			double k = axis == Direction.Axis.Z ? (double) direction.getOffsetZ() * 0.52D : h;
			world.addParticle(ParticleTypes.SMOKE, d + i, e + j, f + k, 0.0D, 0.0D, 0.0D);
			world.addParticle(ParticleTypes.FLAME, d + i, e + j, f + k, 0.0D, 0.0D, 0.0D);
		}
	}

	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack)
	{
		if (itemStack.hasCustomName())
		{
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof BlockEntityFurnace)
			{
				((BlockEntityFurnace) blockEntity).setCustomName(itemStack.getName());
			}
		}

	}

	public void onBlockRemoved(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved)
	{
		if (state.getBlock() != newState.getBlock())
		{
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof BlockEntityFurnace)
			{
				ItemScatterer.spawn(world, (BlockPos) pos, (Inventory) ((BlockEntityFurnace) blockEntity));
				world.updateHorizontalAdjacent(pos, this);
			}

			super.onBlockRemoved(state, world, pos, newState, moved);
		}
	}
}
