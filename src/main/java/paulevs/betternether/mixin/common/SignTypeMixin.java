package paulevs.betternether.mixin.common;

import java.util.Set;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import net.minecraft.util.SignType;
import paulevs.betternether.registry.SignTypesRegistry;

@Mixin(SignType.class)
public class SignTypeMixin
{
	@Shadow
	@Final
	private static Set<SignType> VALUES;
	
	static
	{
		SignTypesRegistry.register();
		VALUES.addAll(SignTypesRegistry.VALUES);
	}
}
