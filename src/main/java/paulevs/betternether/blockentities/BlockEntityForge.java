package paulevs.betternether.blockentities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import paulevs.betternether.registry.BlockEntitiesRegistry;

public class BlockEntityForge extends AbstractFurnaceBlockEntity implements ChangebleCookTime {
	public BlockEntityForge(BlockPos pos, BlockState state) {
		super(BlockEntitiesRegistry.CINCINNASITE_FORGE, pos, state, RecipeType.SMELTING);
	}

	@Override
	protected Text getContainerName() {
		return new TranslatableText("container.forge", new Object[0]);
	}

	@Override
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
		return new FurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
	}

	@Override
	protected int getFuelTime(ItemStack fuel) {
		return super.getFuelTime(fuel) / 2;
	}

	@Override
	public int changeCookTime(int cookTime) {
		return cookTime/2;
	}
}
