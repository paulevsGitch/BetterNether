package paulevs.betternether.mixin.common;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import paulevs.betternether.enchantments.RubyFire;
import paulevs.betternether.registry.NetherEnchantments;

import java.util.List;

@Mixin(Block.class)
public class BlockBehaviourMixin {
	
	
	@Inject(method="dropResources(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)V", at=@At("HEAD"), cancellable = true)
	private static void bn_getDrops(BlockState brokenBlock, Level level, BlockPos blockPos, BlockEntity blockEntity, Entity breakingEntity, ItemStack breakingItem, CallbackInfo ci) {
		if ((level instanceof ServerLevel server) && (breakingEntity instanceof Player player)){
			if (RubyFire.getDrops(brokenBlock, server, blockPos, player, breakingItem)){
				ci.cancel();
			}
		}
	}
}
