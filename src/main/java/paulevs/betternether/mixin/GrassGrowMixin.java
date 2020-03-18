package paulevs.betternether.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.World;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.biomes.NetherBiome;
import paulevs.betternether.biomes.NetherSoulPlain;
import paulevs.betternether.biomes.NetherSwampland;
import paulevs.betternether.registers.BlocksRegister;
import paulevs.betternether.world.BNWorldGenerator;

@Mixin(BoneMealItem.class)
public class GrassGrowMixin
{
	private static final Mutable POS = new Mutable();
	
	@Inject(method = "useOnBlock", at = @At("HEAD"))
	private void onUse(ItemUsageContext context, CallbackInfoReturnable<ActionResult> info)
	{
		World world = context.getWorld();
		BlockPos blockPos = context.getBlockPos();
		if (!world.isClient)
		{
			if (BlocksHelper.isNetherrack(world.getBlockState(blockPos)))
			{
				growGrass(world, blockPos);
				world.playLevelEvent(2005, blockPos, 0);
				if (!context.getPlayer().isCreative())
					context.getStack().decrement(1);
				info.setReturnValue(ActionResult.SUCCESS);
			}
			else if (BlocksHelper.isSoulSand(world.getBlockState(blockPos)))
			{
				growGrass(world, blockPos);
				world.playLevelEvent(2005, blockPos, 0);
				if (!context.getPlayer().isCreative())
					context.getStack().decrement(1);
				info.setReturnValue(ActionResult.SUCCESS);
			}
		}
	}
	
	private void growGrass(World world, BlockPos pos)
	{
		int y1 = pos.getY() + 3;
		int y2 = pos.getY() - 3;
		BlockState grass = getGrassState(pos);
		for (int i = 0; i < 64; i++)
		{
			int x = (int) (pos.getX() + world.random.nextGaussian() * 2);
			int z = (int) (pos.getZ() + world.random.nextGaussian() * 2);
			POS.setX(x);
			POS.setZ(z);
			for (int y = y1; y >= y2; y--)
			{
				POS.setY(y);
				if (world.isAir(POS) && BlocksHelper.isNetherGround(world.getBlockState(POS.down())))
				{
					BlocksHelper.setWithoutUpdate(world, POS, grass);
					break;
				}
			}
		}
	}
	
	private BlockState getGrassState(BlockPos pos)
	{
		NetherBiome biome = BNWorldGenerator.getBiome(pos.getX(), pos.getY(), pos.getZ());
		if (biome instanceof NetherSwampland)
			return BlocksRegister.SWAMP_GRASS.getDefaultState();
		else if (biome instanceof NetherSoulPlain)
			return BlocksRegister.SOUL_GRASS.getDefaultState();
		else
			return BlocksRegister.NETHER_GRASS.getDefaultState();
	}
}