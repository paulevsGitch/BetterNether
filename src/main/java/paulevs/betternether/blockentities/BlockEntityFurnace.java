package paulevs.betternether.blockentities;

import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import paulevs.betternether.registry.BlockEntitiesRegistry;

public class BlockEntityFurnace extends AbstractFurnaceBlockEntity
{
	public BlockEntityFurnace()
	{
		super(BlockEntitiesRegistry.NETHERRACK_FURNACE, RecipeType.SMELTING);
	}

	protected Text getContainerName()
	{
		return new TranslatableText("container.furnace", new Object[0]);
	}

	@Override
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory)
	{
		return new FurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
	}
}
