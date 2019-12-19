package paulevs.betternether;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import paulevs.betternether.proxy.CommonProxy;
import paulevs.betternether.tab.CreativeTab;

@Mod(modid = BetterNether.MODID, name = BetterNether.NAME, version = BetterNether.VERSION)
public class BetterNether
{
	public static final String MODID = "betternether";
	public static final String NAME = "Better Nether";
	public static final String VERSION = "0.1.8.5";
	
	public static final CreativeTabs BN_TAB = new CreativeTab();
	
	@SidedProxy(clientSide = "paulevs.betternether.proxy.ClientProxy", serverSide = "paulevs.betternether.proxy.CommonProxy")
	public static CommonProxy proxy;
	private static Object mod;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		mod = this;
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
	    proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
	    proxy.postInit(event);
	}

	public static Object getMod()
	{
		return mod;
	}
}
