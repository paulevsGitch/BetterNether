package paulevs.betternether.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import paulevs.betternether.registry.EntityRegistry;

@Mixin(DefaultAttributeRegistry.class)
public class DefaultAttributeRegistryMixin
{
	@Inject(method = "get", at = @At("HEAD"), cancellable = true)
	private static void getAttribute(EntityType<? extends LivingEntity> type, CallbackInfoReturnable<DefaultAttributeContainer> info)
	{
		DefaultAttributeContainer container = EntityRegistry.ATTRIBUTES.get(type);
		if (container != null)
		{
			info.setReturnValue(container);
			info.cancel();
		}
	}

	@Inject(method = "hasDefinitionFor", at = @At("HEAD"), cancellable = true)
	private static void hasDefinition(EntityType<?> type, CallbackInfoReturnable<Boolean> info)
	{
		if (EntityRegistry.ATTRIBUTES.containsKey(type))
		{
			info.setReturnValue(true);
			info.cancel();
		}
	}
}