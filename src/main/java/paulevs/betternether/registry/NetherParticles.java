package paulevs.betternether.registry;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import paulevs.betternether.BetterNether;
import paulevs.betternether.config.Configs;
import paulevs.betternether.particles.BNSimpleParticleType;
import paulevs.betternether.particles.BNParticleProvider;

public class NetherParticles {
	public static SimpleParticleType BLUE_DRIPPING_OBSIDIAN_TEAR;
	public static SimpleParticleType BLUE_FALLING_OBSIDIAN_TEAR;
	public static SimpleParticleType BLUE_LANDING_OBSIDIAN_TEAR;
	
	public static SimpleParticleType BLUE_DRIPPING_OBSIDIAN_WEEP;
	public static SimpleParticleType BLUE_FALLING_OBSIDIAN_WEEP;
	public static SimpleParticleType BLUE_LANDING_OBSIDIAN_WEEP;
	
	public static SimpleParticleType DRIPPING_OBSIDIAN_WEEP;
	public static SimpleParticleType FALLING_OBSIDIAN_WEEP;
	public static SimpleParticleType LANDING_OBSIDIAN_WEEP;
	
	public static void register(){
		BLUE_DRIPPING_OBSIDIAN_TEAR = Registry.register(Registry.PARTICLE_TYPE, BetterNether.makeID( "blue_dripping_obsidian_tear"), new BNSimpleParticleType(false));
		ParticleFactoryRegistry.getInstance().register(BLUE_DRIPPING_OBSIDIAN_TEAR, BNParticleProvider.ObsidianTearHangProvider::new);
		
		BLUE_FALLING_OBSIDIAN_TEAR = Registry.register(Registry.PARTICLE_TYPE, BetterNether.makeID( "blue_falling_obsidian_tear"), new BNSimpleParticleType(false));
		ParticleFactoryRegistry.getInstance().register(BLUE_FALLING_OBSIDIAN_TEAR, BNParticleProvider.ObsidianTearFallProvider::new);
		
		BLUE_LANDING_OBSIDIAN_TEAR = Registry.register(Registry.PARTICLE_TYPE, BetterNether.makeID( "blue_landing_obsidian_tear"), new BNSimpleParticleType(false));
		ParticleFactoryRegistry.getInstance().register(BLUE_LANDING_OBSIDIAN_TEAR, BNParticleProvider.ObsidianTearLandProvider::new);
		
		
		if (Configs.MAIN.getBoolean("particles", "weeping", true)) {
			BLUE_DRIPPING_OBSIDIAN_WEEP = Registry.register(Registry.PARTICLE_TYPE, BetterNether.makeID("blue_dripping_obsidian_weep"), new BNSimpleParticleType(false));
			ParticleFactoryRegistry.getInstance()
								   .register(BLUE_DRIPPING_OBSIDIAN_WEEP, BNParticleProvider.ObsidianWeepHangProvider::new);
			
			BLUE_FALLING_OBSIDIAN_WEEP = Registry.register(Registry.PARTICLE_TYPE, BetterNether.makeID("blue_falling_obsidian_weep"), new BNSimpleParticleType(false));
			ParticleFactoryRegistry.getInstance()
								   .register(BLUE_FALLING_OBSIDIAN_WEEP, BNParticleProvider.ObsidianWeepFallProvider::new);
			
			BLUE_LANDING_OBSIDIAN_WEEP = Registry.register(Registry.PARTICLE_TYPE, BetterNether.makeID("blue_landing_obsidian_weep"), new BNSimpleParticleType(false));
			ParticleFactoryRegistry.getInstance()
								   .register(BLUE_LANDING_OBSIDIAN_WEEP, BNParticleProvider. ObsidianWeepLandProvider::new);
			
			
			DRIPPING_OBSIDIAN_WEEP = Registry.register(Registry.PARTICLE_TYPE, BetterNether.makeID("dripping_obsidian_weep"), new BNSimpleParticleType(false));
			ParticleFactoryRegistry.getInstance()
								   .register(DRIPPING_OBSIDIAN_WEEP, BNParticleProvider.ObsidianVanillaWeepHangProvider::new);
			
			FALLING_OBSIDIAN_WEEP = Registry.register(Registry.PARTICLE_TYPE, BetterNether.makeID("falling_obsidian_weep"), new BNSimpleParticleType(false));
			ParticleFactoryRegistry.getInstance()
								   .register(FALLING_OBSIDIAN_WEEP, BNParticleProvider.ObsidianVanillaWeepFallProvider::new);
			
			LANDING_OBSIDIAN_WEEP = Registry.register(Registry.PARTICLE_TYPE, BetterNether.makeID("landing_obsidian_weep"), new BNSimpleParticleType(false));
			ParticleFactoryRegistry.getInstance()
								   .register(LANDING_OBSIDIAN_WEEP, BNParticleProvider.ObsidianVanillaWeepLandProvider::new);
		} else {
			BLUE_DRIPPING_OBSIDIAN_WEEP = BLUE_DRIPPING_OBSIDIAN_TEAR;
			DRIPPING_OBSIDIAN_WEEP = BLUE_DRIPPING_OBSIDIAN_TEAR;
			
			BLUE_FALLING_OBSIDIAN_WEEP = BLUE_FALLING_OBSIDIAN_TEAR;
			FALLING_OBSIDIAN_WEEP = BLUE_FALLING_OBSIDIAN_TEAR;
			
			BLUE_LANDING_OBSIDIAN_WEEP = BLUE_LANDING_OBSIDIAN_TEAR;
			LANDING_OBSIDIAN_WEEP = BLUE_LANDING_OBSIDIAN_TEAR;
		}
	}
}
