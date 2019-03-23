package paulevs.betternether.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import paulevs.betternether.blocks.BlocksRegister;
import paulevs.betternether.entities.render.EntityRenderRegister;
import paulevs.betternether.items.ItemsRegister;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
		EntityRenderRegister.register();
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
		BlocksRegister.registerRender();
		ItemsRegister.registerRender();
		//EntityRenderRegister.register();
	}

	@Override
	public void postInit(FMLPostInitializationEvent event)
	{
		super.postInit(event);
	}
}