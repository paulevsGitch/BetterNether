package paulevs.betternether.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import paulevs.betternether.registry.NetherParticles;

class BNDripHangParticle extends DripParticle.DripHangParticle{
	public BNDripHangParticle(ClientLevel clientLevel, double d, double e, double f, Fluid fluid, ParticleOptions particleOptions) {
		super(clientLevel, d, e, f, fluid, particleOptions);
	}
	
	public void setup(boolean isGlowing, float gravityFactor, int lifetime){
		this.isGlowing = isGlowing;
		this.gravity *= gravityFactor;
		this.lifetime = lifetime;
	}
}

class BNFallAndLandParticle extends  DripParticle.FallAndLandParticle{
	public BNFallAndLandParticle(ClientLevel clientLevel, double d, double e, double f, Fluid fluid, ParticleOptions particleOptions) {
		super(clientLevel, d, e, f, fluid, particleOptions);
	}
	
	public void setup(boolean isGlowing, float gravity){
		this.isGlowing = isGlowing;
		this.gravity = gravity;
	}
}

class BNDripLandParticle extends DripParticle.DripLandParticle{
	public BNDripLandParticle(ClientLevel clientLevel, double d, double e, double f, Fluid fluid) {
		super(clientLevel, d, e, f, fluid);
	}
	public void setup(boolean isGlowing, int lifetime){
		this.isGlowing = isGlowing;
		this.lifetime = lifetime;
	}
}

public class BNParticleProvider<S extends ParticleType<SimpleParticleType>> {
	private static final float BLUE_DRIP_R =  0x12/255.0f;
	private static final float BLUE_DRIP_G =  0x5a/255.0f;
	private static final float BLUE_DRIP_B =  0xf9/255.0f;
	@Environment(EnvType.CLIENT)
	public static class ObsidianTearLandProvider implements net.minecraft.client.particle.ParticleProvider<SimpleParticleType> {
		protected final SpriteSet sprite;
		
		public ObsidianTearLandProvider(SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}
		
		public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			BNDripLandParticle dripParticle = new BNDripLandParticle(clientLevel, d, e, f, Fluids.EMPTY);
			dripParticle.setup(true, (int)(28.0D / (Math.random() * 0.8D + 0.2D)));
			dripParticle.setColor(BLUE_DRIP_R, BLUE_DRIP_G, BLUE_DRIP_B);
			dripParticle.pickSprite(this.sprite);
			return dripParticle;
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static class ObsidianWeepLandProvider implements net.minecraft.client.particle.ParticleProvider<SimpleParticleType> {
		protected final SpriteSet sprite;
		
		public ObsidianWeepLandProvider(SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}
		
		public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			BNDripLandParticle dripParticle = new BNDripLandParticle(clientLevel, d, e, f, Fluids.EMPTY);
			dripParticle.setup(true, (int)(Math.random() * 10) + 1);
			dripParticle.setColor(BLUE_DRIP_R, BLUE_DRIP_G, BLUE_DRIP_B);
			dripParticle.pickSprite(this.sprite);
			return dripParticle;
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static class ObsidianTearFallProvider implements net.minecraft.client.particle.ParticleProvider<SimpleParticleType> {
		protected final SpriteSet sprite;
		
		public ObsidianTearFallProvider(SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}
		
		public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			BNFallAndLandParticle dripParticle = new BNFallAndLandParticle(clientLevel, d, e, f, Fluids.EMPTY, NetherParticles.BLUE_LANDING_OBSIDIAN_TEAR);
			dripParticle.setup(true, 0.01F);
			dripParticle.setColor(BLUE_DRIP_R, BLUE_DRIP_G, BLUE_DRIP_B);
			dripParticle.pickSprite(this.sprite);
			return dripParticle;
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static class ObsidianWeepFallProvider implements net.minecraft.client.particle.ParticleProvider<SimpleParticleType> {
		protected final SpriteSet sprite;
		
		public ObsidianWeepFallProvider(SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}
		
		public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			BNFallAndLandParticle dripParticle = new BNFallAndLandParticle(clientLevel, d, e, f, Fluids.EMPTY, NetherParticles.BLUE_LANDING_OBSIDIAN_WEEP);
			dripParticle.setup(true, 0.01F);
			dripParticle.setColor(BLUE_DRIP_R, BLUE_DRIP_G, BLUE_DRIP_B);
			dripParticle.pickSprite(this.sprite);
			return dripParticle;
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static class ObsidianTearHangProvider implements net.minecraft.client.particle.ParticleProvider<SimpleParticleType> {
		protected final SpriteSet sprite;
		
		public ObsidianTearHangProvider(SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}
		
		public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			BNDripHangParticle dripHangParticle = new BNDripHangParticle(clientLevel, d, e, f, Fluids.EMPTY, NetherParticles.BLUE_FALLING_OBSIDIAN_TEAR);
			dripHangParticle.setup(true, 0.01F, 100);
			dripHangParticle.setColor(BLUE_DRIP_R, BLUE_DRIP_G, BLUE_DRIP_B);
			dripHangParticle.pickSprite(this.sprite);
			return dripHangParticle;
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static class ObsidianWeepHangProvider implements net.minecraft.client.particle.ParticleProvider<SimpleParticleType> {
		protected final SpriteSet sprite;
		
		public ObsidianWeepHangProvider(SpriteSet spriteSet) {
			this.sprite = spriteSet;
		}
		
		public Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
			BNDripHangParticle dripHangParticle = new BNDripHangParticle(clientLevel, d, e, f, Fluids.EMPTY, NetherParticles.BLUE_FALLING_OBSIDIAN_WEEP);
			dripHangParticle.setup(true, 0.01F, 5 + (int)(Math.random()*10));
			dripHangParticle.setColor(BLUE_DRIP_R, BLUE_DRIP_G, BLUE_DRIP_B);
			dripHangParticle.pickSprite(this.sprite);
			return dripHangParticle;
		}
	}
}
