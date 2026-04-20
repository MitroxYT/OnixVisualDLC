package onix.dev.util.config;

import com.google.gson.*;
import lombok.SneakyThrows;
import onix.dev.Onixvisual;
import onix.dev.module.api.Function;
import onix.dev.module.setting.api.Setting;
import onix.dev.module.setting.impl.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import static onix.dev.util.wrapper.Wrapper.mc;

public class ConfigSystem {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static void saveConfig(String filename) {
        JsonObject root = new JsonObject();

        for (Function module : Onixvisual.getInstance().getFunctionManager().getModules()) {
            JsonObject moduleData = new JsonObject();
            moduleData.addProperty("enabled", module.isState());
            moduleData.addProperty("key", module.getKey());

            JsonObject settingsObj = new JsonObject();
            for (Setting setting : module.getSettings()) {
                if (setting instanceof BooleanSetting s) {
                    settingsObj.addProperty(s.getName(), s.isVisible());
                }else if (setting instanceof ColorSetting) {
                    //ColorSetting s = (ColorSetting) setting;
                  //  settingsObj.addProperty(s.getName(), s.getColor());
                } else if (setting instanceof ModeSetting s) {
                    settingsObj.addProperty(s.getName(), s.getSelected());
                } else if (setting instanceof NumberSetting s) {
                    settingsObj.addProperty(s.getName(), s.getValue());
                } else if (setting instanceof StringSetting s) {
                    settingsObj.addProperty(s.getName(),s.getValue());
                }
            }

            moduleData.add("settings", settingsObj);
            root.add(module.getName(), moduleData);
        }

        try {
            try (Writer writer = new FileWriter(getConfigFile(filename))) {
                String cryptedjson = gson.toJson(root);
                String json = Base64.getEncoder().encodeToString(cryptedjson.getBytes(StandardCharsets.UTF_8));// gson.toJson(root);
                writer.write(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try (FileWriter writer = new FileWriter(getConfigFile(filename))) {
//            for (Module m : ModuleManager.modules) {
//                writer.write(m.getName() + " {\n");
//                writer.write("  binding=" + m.getBinding() + "\n");
//                writer.write("  enable=" + m.isEnable() + "\n");
//                writer.write("}\n\n");
//            }
//        } catch (IOException e) {
//        }
    }
    public static String getJsonConfig() {
        JsonObject root = new JsonObject();
        JsonObject data = new JsonObject();
        root.addProperty("version",1.0);
        for (Function module : Onixvisual.getInstance().getFunctionManager().getModules()) {
            JsonObject moduleData = new JsonObject();
            moduleData.addProperty("name",module.getName());
            moduleData.addProperty("enabled", module.isState());
            data.add(module.getName(),moduleData);
        }
        root.add("modules",data);
//
//        for (Function module : Onixvisual.getInstance().getFunctionManager().getModules()) {
//            JsonObject moduleData = new JsonObject();
//            moduleData.addProperty("enabled", module.isState());
//            moduleData.addProperty("key", module.getKey());
//
//            JsonObject settingsObj = new JsonObject();
//            for (Setting setting : module.getSettings()) {
//                if (setting instanceof BooleanSetting s) {
//                    settingsObj.addProperty(s.getName(), s.isVisible());
//                }else if (setting instanceof ColorSetting) {
//                    //ColorSetting s = (ColorSetting) setting;
//                    //  settingsObj.addProperty(s.getName(), s.getColor());
//                } else if (setting instanceof ModeSetting s) {
//                    settingsObj.addProperty(s.getName(), s.getSelected());
//                } else if (setting instanceof NumberSetting s) {
//                    settingsObj.addProperty(s.getName(), s.getValue());
//                } else if (setting instanceof StringSetting s) {
//                    settingsObj.addProperty(s.getName(),s.getValue());
//                }
//            }
//
//            moduleData.add("settings", settingsObj);
//            root.add(module.getName(), moduleData);
//        }

        return gson.toJson(root);
    }
    @SneakyThrows
    public static void loadConfig(String filename) {
        File file = getConfigFile(filename);
        if (!file.exists()) return;
        //   try (Reader reader = new FileReader(file)) {
        JsonParser aa = new JsonParser();
        String configString;
        try (FileReader reader = new FileReader(file)) {
            StringBuilder content = new StringBuilder();
            char[] buffer = new char[1024];
            int bytesRead;
            while ((bytesRead = reader.read(buffer)) != -1) {
                content.append(buffer, 0, bytesRead);
            }
            configString = content.toString();
            String decripted = new String(Base64.getDecoder().decode(configString));


            JsonObject root = aa.parse(decripted).getAsJsonObject();

            for (Map.Entry<String, JsonElement> entry : root.entrySet()) {
                Function module = Onixvisual.getInstance().getFunctionManager().get(entry.getKey());
                if (module == null) continue;

                JsonObject moduleData = entry.getValue().getAsJsonObject();
                if (moduleData.has("key")) {
                    module.setKey(moduleData.get("key").getAsInt());
                }
                if (moduleData.has("enabled")) {
                    module.setState(moduleData.get("enabled").getAsBoolean());
                }

                JsonObject settings = moduleData.getAsJsonObject("settings");
                for (Setting setting : module.getSettings()) {
                    if (!settings.has(setting.getName())) continue;

                    JsonElement settingElement = settings.get(setting.getName());

                    if (setting instanceof BooleanSetting s) {
                        s.setValue(settingElement.getAsBoolean());
                    }  else if (setting instanceof ColorSetting) {
                        ColorSetting s = (ColorSetting) setting;
//                        s.setColor(settingElement.getAsInt());
                    } else if (setting instanceof ModeSetting s) {
                        s.setValue(settingElement.getAsString());
                    } else if (setting instanceof NumberSetting s) {
                        s.setValue(settingElement.getAsDouble());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
//            String line;
//            Module currentModule = null;
//            while ((line = reader.readLine()) != null) {
//                if (line.trim().isEmpty() || line.startsWith("//")) {
//                    continue;
//                }
//                if (line.endsWith(" {")) {
//                    String moduleName = line.substring(0, line.length() - 2);
//                    for (Module m : ModuleManager.modules) {
//                        if (m.getName().equals(moduleName)) {
//                            currentModule = m;
//                            break;
//                        }
//                    }
//                    if (currentModule == null) {
//                        continue;
//                    }
//                } else if (currentModule != null) {
//                    String[] parts = line.split("=");
//                    if (parts.length == 2) {
//                        String key = parts[0].trim();
//                        String value = parts[1].trim();
//                        if (key.equals("binding")) {
//                            currentModule.setBinding(Integer.parseInt(value));
//                        } else if (key.equals("enable")) {
//                            currentModule.setEnable(Boolean.parseBoolean(value));
//                        }
//                    }
//                }
//            }
//        } catch (IOException e) {
//        }
    }


    private static File getConfigFile(String filename) {
        File azensDir = new File(mc.gameDirectory, "vein");
        if (!azensDir.exists()) {
            azensDir.mkdirs();
        }
        return new File(azensDir,filename + ".venvs");
    }

}
