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
import net.minecraft.world.biome.Biome;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.biomes.NetherBiomeJungle;
import paulevs.betternether.biomes.NetherSoulPlain;
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
			if (BlocksHelper.isNetherrack(world.getBlockState(blockPos)) && !hasNyliumNear(world, blockPos))
			{
				boolean ground = false;
				if (world.getBlockState(blockPos).getBlock() == Blocks.NETHERRACK)
				{
					ground = setGround(world, blockPos, world.getBlockState(blockPos.north())) ||
							setGround(world, blockPos, world.getBlockState(blockPos.south())) ||
							setGround(world, blockPos, world.getBlockState(blockPos.east())) ||
							setGround(world, blockPos, world.getBlockState(blockPos.west()));
				}
				if (!ground)
					growGrass(world, blockPos);
				if (!context.getPlayer().isCreative())
					context.getStack().decrement(1);
				world.syncWorldEvent(2005, blockPos, 0);
				info.setReturnValue(ActionResult.SUCCESS);
				info.cancel();
			}
			else if (BlocksHelper.isSoulSand(world.getBlockState(blockPos)))
			{
				growGrass(world, blockPos);
				world.syncWorldEvent(2005, blockPos, 0);
				if (!context.getPlayer().isCreative())
					context.getStack().decrement(1);
				info.setReturnValue(ActionResult.SUCCESS);
				info.cancel();
			}
		}
	}
	
	private void growGrass(World world, BlockPos pos)
	{
		int y1 = pos.getY() + 3;
		int y2 = pos.getY() - 3;
		BlockState grass = getGrassState(world, pos);
		for (int i = 0; i < 64; i++)
		{
			int x = (int) (pos.getX() + world.random.nextGaussian() * 2);
			int z = (int) (pos.getZ() + world.random.nextGaussian() * 2);
			POS.setX(x);
			POS.setZ(z);
			for (int y = y1; y >= y2; y--)
			{
				POS.setY(y);
				if (world.isAir(POS) && world.getBlockState(POS.down()).getBlock() != BlocksRegistry.NETHER_MYCELIUM && BlocksHelper.isNetherGround(world.getBlockState(POS.down())))
				{
					BlocksHelper.setWithoutUpdate(world, POS, grass);
					break;
				}
			}
		}
	}
	
	private BlockState getGrassState(World world, BlockPos pos)
	{
		Biome biome = world.getBiome(pos);
		if (biome instanceof NetherSwampland)
			return BlocksRegistry.SWAMP_GRASS.getDefaultState();
		else if (biome instanceof NetherSoulPlain)
			return BlocksRegistry.SOUL_GRASS.getDefaultState();
		else if (biome instanceof NetherBiomeJungle)
			return BlocksRegistry.JUNGLE_PLANT.getDefaultState();
		else
			return BlocksRegistry.NETHER_GRASS.getDefaultState();
	}
	
	private boolean hasNyliumNear(World world, BlockPos pos)
	{
		return  isNylium(world.getBlockState(pos.north())) ||
				isNylium(world.getBlockState(pos.south())) ||
				isNylium(world.getBlockState(pos.east())) ||
				isNylium(world.getBlockState(pos.west()));
	}
	
	private boolean isNylium(BlockState state)
	{
		return state.getBlock() == Blocks.CRIMSON_NYLIUM || state.getBlock() == Blocks.WARPED_NYLIUM;
	}
	
	private boolean setGround(World world, BlockPos pos, BlockState state)
	{
		if (state.getBlock() == BlocksRegistry.NETHER_MYCELIUM)
		{
			BlocksHelper.setWithoutUpdate(world, pos, BlocksRegistry.NETHER_MYCELIUM.getDefaultState());
			return true;
		}
		else if (state.getBlock() == BlocksRegistry.JUNGLE_GRASS)
		{
			BlocksHelper.setWithoutUpdate(world, pos, BlocksRegistry.JUNGLE_GRASS.getDefaultState());
			return true;
		}
		return false;
	}
}