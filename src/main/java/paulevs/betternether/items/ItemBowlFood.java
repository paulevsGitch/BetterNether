package paulevs.betternether.items;

import net.minecraft.block.BlockState;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
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
import paulevs.betternether.registers.BlocksRegister;
import paulevs.betternether.tab.CreativeTab;

public class ItemBowlFood extends Item
{
	private FoodShape bowlFood;
	
	public ItemBowlFood(int hunger, FoodShape food)
	{
		super(new Item.Settings()
				.group(CreativeTab.BN_TAB)
				.food(hunger > 0 ? new FoodComponent
						.Builder()
						.hunger(hunger)
						.saturationModifier(0.5F)
						.build() : null));
		food.setItem(this);
		this.bowlFood = food;
	}

	public ActionResult useOnBlock(ItemUsageContext context)
	{
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos().offset(context.getSide());
		if (world.isAir(pos) && BlocksRegister.BLOCK_STALAGNATE_BOWL.canPlaceAt(world.getBlockState(pos), world, pos))
		{
			if (!world.isClient())
			{
				BlockState state = BlocksRegister.BLOCK_STALAGNATE_BOWL.getDefaultState().with(BlockStalagnateBowl.FOOD, bowlFood);
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
		return ActionResult.PASS;
	}
}