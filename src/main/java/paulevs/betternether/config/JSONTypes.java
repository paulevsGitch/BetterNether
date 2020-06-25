package paulevs.betternether.config;

import com.google.gson.JsonElement;

public class JSONTypes
{
	public static enum ElementType
	{
		BOOLEAN,
		STRING,
		NUMBER,
		NONE;
	}
	
	public static ElementType getType(JsonElement element)
	{
		if (element.isJsonNull())
			return ElementType.NONE;

		if (element.isJsonPrimitive())
		{
			if (element.getAsJsonPrimitive().isBoolean())
				return ElementType.BOOLEAN;
			if (element.getAsJsonPrimitive().isString())
				return ElementType.STRING;
			if (element.getAsJsonPrimitive().isNumber())
			{
				return ElementType.NUMBER;
			}
		}
		
		return ElementType.NONE;
	}
}
