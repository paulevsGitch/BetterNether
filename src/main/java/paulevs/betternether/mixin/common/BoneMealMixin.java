package paulevs.betternether.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.World;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.biomes.NetherSwampland;
import paulevs.betternether.registry.BlocksRegistry;

@Mixin(BoneMealItem.class)
public class BoneMealMixin
{
	private static final Mutable POS = new Mutable();
	
	@Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
	private void onUse(ItemUsageContext context, CallbackInfoReturnable<ActionResult> info)
	{
		World world = context.getWorld();
		BlockPos blockPos = context.getBlockPos();
		if (!world.isClient)
		{
			if (BlocksHelper.isNetherrack(world.getBlockState(blockPos)))
			{
				BlockState nylium = getNylium(world, blockPos);
				boolean consume = true;
				if (nylium != null && world.getBlockState(blockPos).getBlock() == Blocks.NETHERRACK)
				{
					BlocksHelper.setWithoutUpdate(world, blockPos, nylium);
				}
				else
				{
					consume = growGrass(world, blockPos);
				}
				if (consume)
				{
					if (!context.getPlayer().isCreative())
						context.getStack().decrement(1);
					world.syncWorldEvent(2005, blockPos, 0);
					info.setReturnValue(ActionResult.SUCCESS);
					info.cancel();
				}
			}
			else if (BlocksHelper.isSoulSand(world.getBlockState(blockPos)))
			{
				if (growGrass(world, blockPos))
				{
					world.syncWorldEvent(2005, blockPos, 0);
					if (!context.getPlayer().isCreative())
						context.getStack().decrement(1);
					info.setReturnValue(ActionResult.SUCCESS);
					info.cancel();
				}
			}
		}
	}
	
	private boolean growGrass(World world, BlockPos pos)
	{
		int y1 = pos.getY() + 3;
		int y2 = pos.getY() - 3;
		boolean result = false;
		for (int i = 0; i < 64; i++)
		{
			int x = (int) (pos.getX() + world.random.nextGaussian() * 2);
			int z = (int) (pos.getZ() + world.random.nextGaussian() * 2);
			POS.setX(x);
			POS.setZ(z);
			for (int y = y1; y >= y2; y--)
			{
				POS.setY(y);
				BlockPos down = POS.down();
				if (world.isAir(POS) && !world.isAir(down))
				{
					BlockState grass = getGrassState(world, down);
					if (grass != null)
					{
						BlocksHelper.setWithoutUpdate(world, POS, grass);
						if (world.random.nextInt(3) == 0 && world.getBlockState(down).getBlock() == Blocks.NETHERRACK)
							BlocksHelper.setWithoutUpdate(world, down, BlocksRegistry.NETHERRACK_MOSS.getDefaultState());
						result = true;
					}
					break;
				}
			}
		}
		return result;
	}
	
	private BlockState getGrassState(World world, BlockPos pos)
	{
		BlockState state = world.getBlockState(pos);
		if (state.getBlock() == BlocksRegistry.JUNGLE_GRASS)
			return BlocksRegistry.JUNGLE_PLANT.getDefaultState();
		else if (BlocksHelper.isSoulSand(state))
			return BlocksRegistry.SOUL_GRASS.getDefaultState();
		else if (state.getBlock() == BlocksRegistry.MUSHROOM_GRASS)
			return BlocksRegistry.BONE_GRASS.getDefaultState();
		else if (BlocksHelper.isNetherrack(state) && !BlocksHelper.isNylium(state))
			return world.getBiome(pos) instanceof NetherSwampland ?
					BlocksRegistry.SWAMP_GRASS.getDefaultState() :
						BlocksRegistry.NETHER_GRASS.getDefaultState();
		return null;
	}
	
	private BlockState getNylium(World world, BlockPos pos)
	{
		BlockState state = world.getBlockState(pos.north());
		if (BlocksHelper.isNylium(state))
			return state;
		
		state = world.getBlockState(pos.south());
		if (BlocksHelper.isNylium(state))
			return state;
		
		state = world.getBlockState(pos.east());
		if (BlocksHelper.isNylium(state))
			return state;
		
		state = world.getBlockState(pos.west());
		if (BlocksHelper.isNylium(state))
			return state;
		
		return null;
	}
}