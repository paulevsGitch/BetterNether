package paulevs.betternether.registry;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.SignType;

public class SignTypesRegistry
{
	public static final List<SignType> VALUES = new ArrayList<SignType>();
	
	public static final SignType STALAGNATE = register(new SignTypeCustom("stalagnate"));
	
	public static void register() {}
	
	private static SignType register(SignType type)
	{
		VALUES.add(type);
		return type;
	}
	
	protected static class SignTypeCustom extends SignType
	{
		protected SignTypeCustom(String name)
		{
			super(name);
		}
	}
}