package paulevs.betternether.blockentities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import paulevs.betternether.registry.BlockEntitiesRegistry;

public class BlockEntityFurnace extends AbstractFurnaceBlockEntity {
	public BlockEntityFurnace(BlockPos pos, BlockState state) {
		super(BlockEntitiesRegistry.NETHERRACK_FURNACE, pos, state, RecipeType.SMELTING);
	}

	protected Text getContainerName() {
		return new TranslatableText("container.furnace", new Object[0]);
	}

	@Override
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
		return new FurnaceScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
	}
}
