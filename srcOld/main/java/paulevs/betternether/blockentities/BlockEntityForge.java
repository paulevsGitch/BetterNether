package paulevs.betternether.blockentities;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import paulevs.betternether.registry.BlockEntitiesRegistry;

public class BlockEntityForge extends AbstractFurnaceBlockEntity {
	public BlockEntityForge() {
		super(BlockEntitiesRegistry.CINCINNASITE_FORGE, RecipeType.SMELTING);
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
	protected int getCookTime() {
		return super.getCookTime() / 2;
	}
}
