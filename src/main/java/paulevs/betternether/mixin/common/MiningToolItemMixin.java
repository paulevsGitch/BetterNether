package paulevs.betternether.mixin.common;

import java.util.Set;

import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tag.Tag;
import paulevs.betternether.blocks.BNObsidian;
import paulevs.betternether.blocks.BlockObsidianGlass;
import paulevs.betternether.registry.BlocksRegistry;

@Mixin(MiningToolItem.class)
public abstract class MiningToolItemMixin extends ToolItem implements Vanishable {
	protected MiningToolItemMixin(float attackDamage, float attackSpeed, ToolMaterial material, Tag<Block> effectiveBlocks, Settings settings) {
		super(material, settings);
	}

	@Inject(method = "isSuitableFor", at = @At(value = "HEAD"), cancellable = true)
	private void effectiveOn(BlockState state, CallbackInfoReturnable<Boolean> info) {
		int level = this.getMaterial().getMiningLevel();
		if (state.getBlock() == BlocksRegistry.CINCINNASITE_ORE) {
			info.setReturnValue(level >= 1);
			info.cancel();
		}
		else if (state.getBlock() == BlocksRegistry.NETHER_RUBY_ORE) {
			info.setReturnValue(level >= 2);
			info.cancel();
		}
		else if (state.getBlock() instanceof BNObsidian || state.getBlock() instanceof BlockObsidianGlass) {
			info.setReturnValue(level >= 3);
			info.cancel();
		}
	}
}
