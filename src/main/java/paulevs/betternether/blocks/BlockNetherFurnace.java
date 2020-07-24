package paulevs.betternether.blocks;

import java.util.List;
import java.util.Random;
import java.util.function.ToIntFunction;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import paulevs.betternether.blockentities.BlockEntityFurnace;

public class BlockNetherFurnace extends AbstractFurnaceBlock
{
	public BlockNetherFurnace(Block source)
	{
		super(FabricBlockSettings.copyOf(source).requiresTool().lightLevel(getLuminance()));
	}
	
	private static ToIntFunction<BlockState> getLuminance()
	{
		return (blockState) -> {
			return (Boolean)blockState.get(Properties.LIT) ? 13 : 0;
		};
	}
	
	public BlockEntity createBlockEntity(BlockView view)
	{
		return new BlockEntityFurnace();
	}

	@Override
	protected void openScreen(World world, BlockPos pos, PlayerEntity player)
	{
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof BlockEntityFurnace)
		{
			player.openHandledScreen((NamedScreenHandlerFactory) blockEntity);
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
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder)
	{
		List<ItemStack> drop = super.getDroppedStacks(state, builder);
		drop.add(new ItemStack(this.asItem()));
		return drop;
	}
}
