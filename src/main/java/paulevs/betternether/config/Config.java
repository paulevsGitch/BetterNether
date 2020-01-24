package paulevs.betternether.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import paulevs.betternether.BetterNether;

public class Config
{
	private static JsonObject config;
	private static boolean rewrite = false;

	public static void load()
	{
		File file = getFile();
		if (file.exists())
		{
			Gson gson = new Gson();
			try
			{
				Reader reader = new FileReader(file);
				config = gson.fromJson(reader, JsonObject.class);
				if (config == null)
				{
					config = new JsonObject();
					rewrite = true;
				}
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
				config = new JsonObject();
				rewrite = true;
			}
		}
		else
		{
			config = new JsonObject();
			rewrite = true;
		}
	}

	public static void save()
	{
		if (rewrite)
		{
			File file = getFile();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			try
			{
				FileWriter writer = new FileWriter(file);
				String gstring = gson.toJson(config);
				writer.write(gstring);
				writer.flush();
		        writer.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	private static File getFile()
	{
		return new File(String.format("./config/%s.json", BetterNether.MOD_ID));
	}

	public static boolean getBoolean(String group, String name, boolean def)
	{
		JsonObject jGroup = config.getAsJsonObject(group);
		if (jGroup != null)
		{
			JsonElement element = jGroup.get(name);
			if (element != null)
			{
				return element.getAsBoolean();
			}
			else
			{
				jGroup.addProperty(name, def);
				rewrite = true;
				return def;
			}
		}
		else
		{
			JsonObject newGroup = new JsonObject();
			newGroup.addProperty(name, def);
			config.add(group, newGroup);
			rewrite = true;
			return def;
		}
	}
	
	public static float getFloat(String group, String name, float def)
	{
		JsonObject jGroup = config.getAsJsonObject(group);
		if (jGroup != null)
		{
			JsonElement element = jGroup.get(name);
			if (element != null)
			{
				return element.getAsFloat();
			}
			else
			{
				jGroup.addProperty(name, def);
				rewrite = true;
				return def;
			}
		}
		else
		{
			JsonObject newGroup = new JsonObject();
			newGroup.addProperty(name, def);
			config.add(group, newGroup);
			rewrite = true;
			return def;
		}
	}
	
	public static int getInt(String group, String name, int def)
	{
		JsonObject jGroup = config.getAsJsonObject(group);
		if (jGroup != null)
		{
			JsonElement element = jGroup.get(name);
			if (element != null)
			{
				return element.getAsInt();
			}
			else
			{
				jGroup.addProperty(name, def);
				rewrite = true;
				return def;
			}
		}
		else
		{
			JsonObject newGroup = new JsonObject();
			newGroup.addProperty(name, def);
			config.add(group, newGroup);
			rewrite = true;
			return def;
		}
	}
	
	public static String[] getStringArray(String group, String name, String[] def)
	{
		JsonObject jGroup = config.getAsJsonObject(group);
		if (jGroup != null)
		{
			JsonElement element = jGroup.get(name);
			if (element != null)
			{
				return toStringArray(element.getAsJsonArray());
			}
			else
			{
				jGroup.add(name, toJsonArray(def));
				rewrite = true;
				return def;
			}
		}
		else
		{
			JsonObject newGroup = new JsonObject();
			newGroup.add(name, toJsonArray(def));
			config.add(group, newGroup);
			rewrite = true;
			return def;
		}
	}
	
	private static String[] toStringArray(JsonArray array)
	{
		String[] result = new String[array.size()];
		for (int i = 0; i < array.size(); i++)
			result[i] = array.get(i).getAsString();
		return result;
	}
	
	private static JsonArray toJsonArray(String[] array)
	{
		JsonArray result = new JsonArray();
		for (String s: array)
			result.add(s);
		return result;
	}
	
	public static JsonArray getJsonArray(String group, String name, JsonArray def)
	{
		JsonObject jGroup = config.getAsJsonObject(group);
		if (jGroup != null)
		{
			JsonElement element = jGroup.get(name);
			if (element != null)
			{
				return element.getAsJsonArray();
			}
			else
			{
				jGroup.add(name, def);
				rewrite = true;
				return def;
			}
		}
		else
		{
			JsonObject newGroup = new JsonObject();
			newGroup.add(name, def);
			config.add(group, newGroup);
			rewrite = true;
			return def;
		}
	}
}
