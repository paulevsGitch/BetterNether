package paulevs.betternether.items;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import paulevs.betternether.BlocksHelper;
import paulevs.betternether.blocks.BlockStalagnateBowl;
import paulevs.betternether.blocks.shapes.FoodShape;
import paulevs.betternether.registry.BlocksRegistry;
import paulevs.betternether.registry.ItemsRegistry;
import paulevs.betternether.tab.CreativeTab;

public class ItemBowlFood extends Item
{
	private FoodShape bowlFood;
	
	public ItemBowlFood(FoodComponent component, FoodShape food)
	{
		super(new Item.Settings().group(CreativeTab.BN_TAB).food(component).maxCount(16));
		food.setItem(this);
		this.bowlFood = food;
	}
	
	public ActionResult useOnBlock(ItemUsageContext context)
	{
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos().offset(context.getSide());
		if (context.getPlayer().isSneaking() && world.isAir(pos) && BlocksRegistry.STALAGNATE_BOWL.canPlaceAt(world.getBlockState(pos), world, pos))
		{
			if (!world.isClient())
			{
				BlockState state = BlocksRegistry.STALAGNATE_BOWL.getDefaultState().with(BlockStalagnateBowl.FOOD, bowlFood);
				BlocksHelper.setWithoutUpdate((ServerWorld) world, pos, state);
			}
			if (!context.getPlayer().isCreative())
			{
				context.getPlayer().getMainHandStack().decrement(1);
			}
			world.playSound(
					pos.getX() + 0.5,
					pos.getY() + 0.25,
					pos.getZ() + 0.5,
					SoundEvents.BLOCK_WOOD_PLACE,
					SoundCategory.BLOCKS,
					0.8F,
					1.0F,
					true);
			return ActionResult.CONSUME;
		}
		return super.useOnBlock(context);
	}
	
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user)
	{
		if (stack.getCount() == 1)
		{
			super.finishUsing(stack, world, user);
			return new ItemStack(ItemsRegistry.STALAGNATE_BOWL, stack.getCount());
		}
		else
		{
			if (user instanceof PlayerEntity)
			{
				PlayerEntity player = (PlayerEntity) user;
				if (!player.isCreative())
					player.giveItemStack(new ItemStack(ItemsRegistry.STALAGNATE_BOWL));
			}
			return super.finishUsing(stack, world, user);
		}
	}
}