package paulevs.betternether.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@Mixin(SheepEntity.class)
public abstract class SheepEntityMixin extends AnimalEntity implements Shearable
{
	protected SheepEntityMixin(EntityType<? extends AnimalEntity> entityType, World world)
	{
		super(entityType, world);
	}

	@Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
	private void shear(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> info)
	{
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.getItem().isIn(FabricToolTags.SHEARS))
		{
			if (!this.world.isClient && this.isShearable())
			{
				this.sheared(SoundCategory.PLAYERS);
				itemStack.damage(1, player, ((playerEntity) -> { playerEntity.sendToolBreakStatus(hand); }));
				info.setReturnValue(ActionResult.SUCCESS);
			}
			else
			{
				info.setReturnValue(ActionResult.CONSUME);
			}
			info.cancel();
		}
	}
}
