package paulevs.betternether.config;

import com.google.common.collect.Lists;
import com.google.gson.*;
import paulevs.betternether.BetterNether;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public final class Config {
	private static final List<Config> ALL = Lists.newArrayList();
	private JsonObject config;
	private boolean rewrite = false;
	private final String name;
	
	public Config(String name) {
		this.name = name;
		ALL.add(this);
	}

	private void load() {
		if (config == null) {
			File file = getFolder();
			if (!file.exists())
				file.mkdirs();
			file = getFile();
			if (file.exists()) {
				Gson gson = new Gson();
				try {
					Reader reader = new FileReader(file);
					config = gson.fromJson(reader, JsonObject.class);
					if (config == null) {
						config = new JsonObject();
						rewrite = true;
					}
					else {
						rewrite = false;
					}
				}
				catch (FileNotFoundException e) {
					e.printStackTrace();
					config = new JsonObject();
					rewrite = true;
				}
			}
			else {
				config = new JsonObject();
				rewrite = true;
			}
		}
	}

	public static void save() {
		ALL.forEach((config) -> {
			if (config.rewrite) {
				File file = config.getFolder();
				if (!file.exists())
					file.mkdirs();
				file = config.getFile();
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				try {
					FileWriter writer = new FileWriter(file);
					String gstring = gson.toJson(config.config);
					writer.write(gstring);
					writer.flush();
					writer.close();
					config.rewrite = false;
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private File getFile() {
		return new File(String.format("./config/%s/%s.json", BetterNether.MOD_ID, name));
	}

	private File getFolder() {
		return new File("./config/" + BetterNether.MOD_ID + "/");
	}

	public boolean getBoolean(String groups, String name, boolean def) {
		load();
		name += "[def: " + def + "]";

		JsonObject group = getGroup(groups);
		JsonElement element = group.get(name);

		if (element != null) {
			return element.getAsBoolean();
		}
		else {
			group.addProperty(name, def);
			rewrite = true;
			return def;
		}
	}

	public void setBoolean(String groups, String name, boolean def, boolean value) {
		name += "[def: " + def + "]";

		JsonObject group = getGroup(groups);
		group.addProperty(name, value);

		rewrite = true;
	}

	public float getFloat(String groups, String name, float def) {
		load();
		name += "[def: " + def + "]";

		JsonObject group = getGroup(groups);
		JsonElement element = group.get(name);

		if (element != null) {
			return element.getAsFloat();
		}
		else {
			group.addProperty(name, def);
			rewrite = true;
			return def;
		}
	}

	public void setFloat(String groups, String name, float def, float value) {
		name += "[def: " + def + "]";

		JsonObject group = getGroup(groups);
		group.addProperty(name, value);

		rewrite = true;
	}

	public int getInt(String groups, String name, int def) {
		load();
		name += "[def: " + def + "]";

		JsonObject group = getGroup(groups);
		JsonElement element = group.get(name);

		if (element != null) {
			return element.getAsInt();
		}
		else {
			group.addProperty(name, def);
			rewrite = true;
			return def;
		}
	}

	public String getString(String groups, String name, String def) {
		load();
		name += "[def: " + def + "]";

		JsonObject group = getGroup(groups);
		JsonElement element = group.get(name);

		if (element != null) {
			return element.getAsString();
		}
		else {
			group.addProperty(name, def);
			rewrite = true;
			return def;
		}
	}

	public void setInt(String groups, String name, int def, int value) {
		name += "[def: " + def + "]";

		JsonObject group = getGroup(groups);
		group.addProperty(name, value);

		rewrite = true;
	}

	public void setStringLoad(String groups, String name, String value) {
		JsonObject group = getGroup(groups);
		group.addProperty(name, value);
	}

	public String[] getStringArray(String groups, String name, String[] def) {
		load();

		JsonObject group = getGroup(groups);
		JsonElement element = group.get(name);

		if (element != null) {
			return toStringArray(element.getAsJsonArray());
		}
		else {
			group.add(name, toJsonArray(def));
			rewrite = true;
			return def;
		}
	}

	private String[] toStringArray(JsonArray array) {
		load();
		String[] result = new String[array.size()];
		for (int i = 0; i < array.size(); i++)
			result[i] = array.get(i).getAsString();
		return result;
	}

	private JsonArray toJsonArray(String[] array) {
		load();
		JsonArray result = new JsonArray();
		for (String s : array)
			result.add(s);
		return result;
	}

	public JsonObject getGroup(String groups) {
		JsonObject obj = config;
		String[] groupsArr = groups.split("\\.");
		for (String group : groupsArr) {
			JsonObject jGroup = obj.getAsJsonObject(group);
			if (jGroup == null) {
				jGroup = new JsonObject();
				obj.add(group, jGroup);
			}
			obj = jGroup;
		}
		return obj;
	}

	public List<String> getBaseGroups() {
		List<String> groups = new ArrayList<String>();
		Iterator<Entry<String, JsonElement>> iterator = config.entrySet().iterator();
		iterator.forEachRemaining((element) -> {
			groups.add(element.getKey());
		});
		return groups;
	}

	public List<Entry<String, JsonElement>> getGroupMembers(JsonObject group) {
		List<Entry<String, JsonElement>> result = new ArrayList<Entry<String, JsonElement>>();
		result.addAll(group.entrySet());
		return result;
	}

	public void markToSave() {
		rewrite = true;
	}
}
