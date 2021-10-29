package paulevs.betternether.registry;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import paulevs.betternether.BetterNether;
import paulevs.betternether.particles.BNParticleProvider.ObsidianWeepFallProvider;
import paulevs.betternether.particles.BNParticleProvider.ObsidianWeepHangProvider;
import paulevs.betternether.particles.BNParticleProvider.ObsidianWeepLandProvider;
import paulevs.betternether.particles.BNSimpleParticleType;
import paulevs.betternether.particles.BNParticleProvider;

public class NetherParticles {
	public static SimpleParticleType BLUE_DRIPPING_OBSIDIAN_TEAR;
	public static SimpleParticleType BLUE_FALLING_OBSIDIAN_TEAR;
	public static SimpleParticleType BLUE_LANDING_OBSIDIAN_TEAR;
	
	public static SimpleParticleType BLUE_DRIPPING_OBSIDIAN_WEEP;
	public static SimpleParticleType BLUE_FALLING_OBSIDIAN_WEEP;
	public static SimpleParticleType BLUE_LANDING_OBSIDIAN_WEEP;
	
	public static void register(){
		BLUE_DRIPPING_OBSIDIAN_TEAR = Registry.register(Registry.PARTICLE_TYPE, BetterNether.makeID( "blue_dripping_obsidian_tear"), new BNSimpleParticleType(false));
		ParticleFactoryRegistry.getInstance().register(BLUE_DRIPPING_OBSIDIAN_TEAR, BNParticleProvider.ObsidianTearHangProvider::new);
		
		BLUE_FALLING_OBSIDIAN_TEAR = Registry.register(Registry.PARTICLE_TYPE, BetterNether.makeID( "blue_falling_obsidian_tear"), new BNSimpleParticleType(false));
		ParticleFactoryRegistry.getInstance().register(BLUE_FALLING_OBSIDIAN_TEAR, BNParticleProvider.ObsidianTearFallProvider::new);
		
		BLUE_LANDING_OBSIDIAN_TEAR = Registry.register(Registry.PARTICLE_TYPE, BetterNether.makeID( "blue_landing_obsidian_tear"), new BNSimpleParticleType(false));
		ParticleFactoryRegistry.getInstance().register(BLUE_LANDING_OBSIDIAN_TEAR, BNParticleProvider.ObsidianTearLandProvider::new);
		
		
		
		BLUE_DRIPPING_OBSIDIAN_WEEP = Registry.register(Registry.PARTICLE_TYPE, BetterNether.makeID( "blue_dripping_obsidian_weep"), new BNSimpleParticleType(false));
		ParticleFactoryRegistry.getInstance().register(BLUE_DRIPPING_OBSIDIAN_WEEP, ObsidianWeepHangProvider::new);
		
		BLUE_FALLING_OBSIDIAN_WEEP = Registry.register(Registry.PARTICLE_TYPE, BetterNether.makeID( "blue_falling_obsidian_weep"), new BNSimpleParticleType(false));
		ParticleFactoryRegistry.getInstance().register(BLUE_FALLING_OBSIDIAN_WEEP, ObsidianWeepFallProvider::new);
		
		BLUE_LANDING_OBSIDIAN_WEEP = Registry.register(Registry.PARTICLE_TYPE, BetterNether.makeID( "blue_landing_obsidian_weep"), new BNSimpleParticleType(false));
		ParticleFactoryRegistry.getInstance().register(BLUE_LANDING_OBSIDIAN_WEEP, ObsidianWeepLandProvider::new);
	}
}
