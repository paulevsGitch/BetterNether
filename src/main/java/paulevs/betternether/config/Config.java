package paulevs.betternether.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import paulevs.betternether.BetterNether;

public class Config
{
	private static JsonObject config;
	private static boolean rewrite = false;

	private static void load()
	{
		if (config == null)
		{
			File file = getFolder();
			if (!file.exists())
				file.mkdirs();
			file = getFile();
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
					else
					{
						boolean rewrite = getBooleanLoad("config", "rewrite_on_version_update", true);
						if (rewrite)
						{
							ModContainer mod = FabricLoader.getInstance().getModContainer(BetterNether.MOD_ID).get();
							String versionActual = mod.getMetadata().getVersion().getFriendlyString();
							String version = getStringLoad("config", "mod_version");
							if (!version.equals(versionActual) || version.equals("${version}"))
							{
								config = new JsonObject();
								rewrite = true;
								setStringLoad("config", "mod_version", versionActual);
							}
						}
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
	}

	public static void save()
	{
		if (rewrite)
		{
			File file = getFolder();
			if (!file.exists())
				file.mkdirs();
			file = getFile();
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
	
	private static File getFolder()
	{
		return new File("./config/");
	}

	public static boolean getBoolean(String groups, String name, boolean def)
	{
		load();
		name += "[def: " + def + "]";
		
		JsonObject group = getGroup(groups);
		JsonElement element = group.get(name);
		
		if (element != null)
		{
			return element.getAsBoolean();
		}
		else
		{
			group.addProperty(name, def);
			rewrite = true;
			return def;
		}
	}
	
	private static boolean getBooleanLoad(String groups, String name, boolean def)
	{
		name += "[def: " + def + "]";
		
		JsonObject group = getGroup(groups);
		JsonElement element = group.get(name);
		
		if (element != null)
		{
			return element.getAsBoolean();
		}
		else
		{
			group.addProperty(name, def);
			rewrite = true;
			return def;
		}
	}
	
	private static String getStringLoad(String groups, String name)
	{
		JsonObject group = getGroup(groups);
		JsonElement element = group.get(name);
		
		if (element != null)
		{
			return element.getAsString();
		}
		else
		{
			return "";
		}
	}
	
	public static void setBoolean(String groups, String name, boolean def, boolean value)
	{
		name += "[def: " + def + "]";
		
		JsonObject group = getGroup(groups);
		group.addProperty(name, value);
		
		rewrite = true;
	}
	
	public static float getFloat(String groups, String name, float def)
	{
		load();
		name += "[def: " + def + "]";
		
		JsonObject group = getGroup(groups);
		JsonElement element = group.get(name);
		
		if (element != null)
		{
			return element.getAsFloat();
		}
		else
		{
			group.addProperty(name, def);
			rewrite = true;
			return def;
		}
	}
	
	public static void setFloat(String groups, String name, float def, float value)
	{
		name += "[def: " + def + "]";
		
		JsonObject group = getGroup(groups);
		group.addProperty(name, value);
		
		rewrite = true;
	}
	
	public static int getInt(String groups, String name, int def)
	{
		load();
		name += "[def: " + def + "]";
		
		JsonObject group = getGroup(groups);
		JsonElement element = group.get(name);
		
		if (element != null)
		{
			return element.getAsInt();
		}
		else
		{
			group.addProperty(name, def);
			rewrite = true;
			return def;
		}
	}
	
	public static String getString(String groups, String name, String def)
	{
		load();
		name += "[def: " + def + "]";
		
		JsonObject group = getGroup(groups);
		JsonElement element = group.get(name);
		
		if (element != null)
		{
			return element.getAsString();
		}
		else
		{
			group.addProperty(name, def);
			rewrite = true;
			return def;
		}
	}
	
	public static void setInt(String groups, String name, int def, int value)
	{
		name += "[def: " + def + "]";
		
		JsonObject group = getGroup(groups);
		group.addProperty(name, value);
		
		rewrite = true;
	}
	
	public static void setStringLoad(String groups, String name, String value)
	{
		JsonObject group = getGroup(groups);
		group.addProperty(name, value);
	}
	
	public static String[] getStringArray(String groups, String name, String[] def)
	{
		load();
		
		JsonObject group = getGroup(groups);
		JsonElement element = group.get(name);
		
		if (element != null)
		{
			return toStringArray(element.getAsJsonArray());
		}
		else
		{
			group.add(name, toJsonArray(def));
			rewrite = true;
			return def;
		}
	}
	
	private static String[] toStringArray(JsonArray array)
	{
		load();
		String[] result = new String[array.size()];
		for (int i = 0; i < array.size(); i++)
			result[i] = array.get(i).getAsString();
		return result;
	}
	
	private static JsonArray toJsonArray(String[] array)
	{
		load();
		JsonArray result = new JsonArray();
		for (String s: array)
			result.add(s);
		return result;
	}
	
	public static JsonObject getGroup(String groups)
	{
		JsonObject obj = config;
		String[] groupsArr = groups.split("\\.");
		for (String group: groupsArr)
		{
			JsonObject jGroup = obj.getAsJsonObject(group);
			if (jGroup == null)
			{
				jGroup = new JsonObject();
				obj.add(group, jGroup);
			}
			obj = jGroup;
		}
		return obj;
	}

	public static List<String> getBaseGroups()
	{
		List<String> groups = new ArrayList<String>();
		Iterator<Entry<String, JsonElement>> iterator = config.entrySet().iterator();
		iterator.forEachRemaining((element) -> { groups.add(element.getKey()); });
		return groups;
	}
	
	public static List<Entry<String, JsonElement>> getGroupMembers(JsonObject group)
	{
		List<Entry<String, JsonElement>> result = new ArrayList<Entry<String, JsonElement>>();
		result.addAll(group.entrySet());
		return result;
	}

	public static void markToSave()
	{
		rewrite = true;
	}
}
