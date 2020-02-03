package paulevs.betternether.registers;

import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import paulevs.betternether.BetterNether;
import paulevs.betternether.entity.EntityChair;
import paulevs.betternether.entity.EntityFirefly;

public class EntityRegister
{
	public static final EntityType<EntityFirefly> FIREFLY = FabricEntityTypeBuilder.create(EntityCategory.AMBIENT, EntityFirefly::new).size(EntityDimensions.fixed(0.5F, 0.5F)).setImmuneToFire().build();
	public static final EntityType<EntityChair> CHAIR = FabricEntityTypeBuilder.create(EntityCategory.MISC, EntityChair::new).size(EntityDimensions.fixed(0.0F, 0.0F)).setImmuneToFire().build();
	
	public static void register()
	{
		registerEntity("firefly", FIREFLY);
		registerEntity("chair", CHAIR);
	}
	
	public static void registerEntity(String name, EntityType<?> entity)
	{
		Registry.register(Registry.ENTITY_TYPE, new Identifier(BetterNether.MOD_ID, name), entity);
	}
}
