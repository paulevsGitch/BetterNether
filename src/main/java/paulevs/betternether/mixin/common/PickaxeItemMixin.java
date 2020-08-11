package paulevs.betternether.mixin.common;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import paulevs.betternether.blocks.BNObsidian;
import paulevs.betternether.blocks.BlockObsidianGlass;
import paulevs.betternether.registry.BlocksRegistry;

@Mixin(PickaxeItem.class)
public abstract class PickaxeItemMixin extends MiningToolItem
{
	protected PickaxeItemMixin(float attackDamage, float attackSpeed, ToolMaterial material, Set<Block> effectiveBlocks, Settings settings)
	{
		super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
	}

	@Inject(method = "isEffectiveOn", at = @At(value = "HEAD"), cancellable = true)
	private void effectiveOn(BlockState state, CallbackInfoReturnable<Boolean> info)
	{
		int level = this.getMaterial().getMiningLevel();
		if (state.getBlock() == BlocksRegistry.CINCINNASITE_ORE)
		{
			info.setReturnValue(level >= 1);
			info.cancel();
		}
		else if (state.getBlock() == BlocksRegistry.NETHER_RUBY_ORE)
		{
			info.setReturnValue(level >= 2);
			info.cancel();
		}
		else if (state.getBlock() instanceof BNObsidian || state.getBlock() instanceof BlockObsidianGlass)
		{
			info.setReturnValue(level >= 3);
			info.cancel();
		}
	}
}
