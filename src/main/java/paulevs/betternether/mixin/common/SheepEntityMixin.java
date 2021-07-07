package paulevs.betternether.mixin.common;

import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Sheep.class)
public abstract class SheepEntityMixin extends Animal implements Shearable {
	protected SheepEntityMixin(EntityType<? extends Animal> entityType, Level world) {
		super(entityType, world);
	}

	@Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
	private void shear(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> info) {
		ItemStack itemStack = player.getItemInHand(hand);
		if (FabricToolTags.SHEARS.contains(itemStack.getItem())) {
			if (!this.level.isClientSide && this.readyForShearing()) {
				this.shear(SoundSource.PLAYERS);
				itemStack.hurtAndBreak(1, player, ((playerEntity) -> {
					playerEntity.broadcastBreakEvent(hand);
				}));
				info.setReturnValue(InteractionResult.SUCCESS);
			}
			else {
				info.setReturnValue(InteractionResult.CONSUME);
			}
			info.cancel();
		}
	}
}
