package paulevs.betternether.registers;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleEffect.Factory;
import net.minecraft.particle.ParticleType;

public class PublicParticleType<T extends ParticleEffect> extends ParticleType<T>
{
	public PublicParticleType(boolean shouldAlwaysShow, Factory<T> parametersFactory)
	{
		super(shouldAlwaysShow, parametersFactory);
	}
}
