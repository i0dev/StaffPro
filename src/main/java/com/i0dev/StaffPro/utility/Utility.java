package com.i0dev.StaffPro.utility;


import com.i0dev.StaffPro.managers.MessageManager;
import com.massivecraft.massivecore.xlib.gson.JsonElement;
import com.massivecraft.massivecore.xlib.gson.JsonObject;
import com.massivecraft.massivecore.xlib.gson.JsonParser;
import lombok.*;
import me.clip.placeholderapi.libs.kyori.adventure.util.Index;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Utility {

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> color(List<String> ss) {
        List<String> ret = new ArrayList<>();
        ss.forEach(s -> ret.add(color(s)));
        return ret;
    }

    public static ItemStack makeItem(Material material, int amount, short data, String name, List<String> lore, boolean glow) {
        if (lore == null)
            lore = new ArrayList<>();
        ItemStack item = new ItemStack(material, amount, data);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(color(name));
        meta.setLore(color(lore));
        if (glow) meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(meta);
        return item;
    }

    @SafeVarargs
    public static String pair(String msg, MessageManager.Pair<String, String>... pairs) {
        for (MessageManager.Pair<String, String> pair : pairs) {
            msg = msg.replace(pair.getKey(), pair.getValue());
        }
        return msg;
    }

    @SafeVarargs
    public static ItemStack makeItem(ConfigItem c, MessageManager.Pair<String, String>... pairs) {
        List<String> lore = new ArrayList<>();
        c.lore.forEach(s -> lore.add(pair(s, pairs)));
        String displayName = pair(c.displayName, pairs);
        ItemStack item = makeItem(Material.getMaterial(c.material), c.amount, c.data, displayName, lore, c.glow);
        if (c instanceof ColorConfigItem) {
            LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
            ColorConfigItem cc = (ColorConfigItem) c;
            meta.setColor(Color.fromRGB(cc.red, cc.blue, cc.green));
            item.setItemMeta(meta);
        }
        return item;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class ConfigItem {

        public String displayName = "";
        public int amount = 0;
        public short data = 0;
        public String material = "";
        public List<String> lore = new ArrayList<>();
        public boolean glow = true;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Getter
    @Setter
    public static class IndexableConfigItem extends ConfigItem {

        public int index = 0;

        public IndexableConfigItem(String displayName, int amount, short data, String material, List<String> lore, boolean glow, int index) {
            this.displayName = displayName;
            this.amount = amount;
            this.data = data;
            this.material = material;
            this.lore = lore;
            this.glow = glow;
            this.index = index;
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Getter
    @Setter
    public static class ColorConfigItem extends ConfigItem {

        public int red = 0;
        public int blue = 0;
        public int green = 0;

        public ColorConfigItem(String displayName, int amount, short data, String material, List<String> lore, boolean glow, int red, int blue, int green) {
            this.displayName = displayName;
            this.amount = amount;
            this.data = data;
            this.material = material;
            this.lore = lore;
            this.glow = glow;
            this.red = red;
            this.blue = blue;
            this.green = green;
        }

    }


    public static Integer getInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception ignored) {
            return null;
        }
    }

    public static void sendActionText(Player player, String message) {
        try {
            message = color(message);
            Object entity = getOBCClass("entity.CraftPlayer").cast(player);
            Object handle = entity.getClass().getMethod("getHandle").invoke(entity);
            Object connection = handle.getClass().getField("playerConnection").get(handle);
            Object chatText = getNMSClass("ChatComponentText").getConstructor(String.class).newInstance(message);
            Object packet;
            try {
                packet = getNMSClass("PacketPlayOutChat").getConstructor(getNMSClass("IChatBaseComponent"), byte.class).newInstance(chatText, (byte) 2);
            } catch (Exception ex2) {
                packet = getNMSClass("PacketPlayOutChat").getConstructor(getNMSClass("IChatBaseComponent"), getNMSClass("ChatMessageType")).newInstance(chatText, Enum.valueOf((Class<? extends Enum>) getNMSClass("ChatMessageType"), "GAME_INFO"));
            }
            connection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(connection, packet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Class<?> getOBCClass(String name) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static Class<?> getNMSClass(String name, String def) {
        return getNMSClass(name) != null ? getNMSClass(name) : getNMSClass(def.split("\\.")[0]).getDeclaredClasses()[0];
    }

    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + "." + name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public static ItemStack itemFromBase64(String base64) {
        ItemStack item = getPlayerSkullItem();
        return itemWithBase64(item, base64);
    }

    public static ItemStack itemWithBase64(ItemStack item, String base64) {
        notNull(item, "item");
        notNull(base64, "base64");

        UUID hashAsId = new UUID(base64.hashCode(), base64.hashCode());
        return Bukkit.getUnsafe().modifyItemStack(
                item,
                "{SkullOwner:{Id:\"" + hashAsId + "\",Properties:{textures:[{Value:\"" + base64 + "\"}]}}}"
        );
    }

    private static boolean newerApi() {
        try {
            Material.valueOf("PLAYER_HEAD");
            return true;
        } catch (IllegalArgumentException e) { // If PLAYER_HEAD doesn't exist
            return false;
        }
    }

    public static ItemStack getPlayerSkullItem() {
        if (newerApi()) {
            return new ItemStack(Material.valueOf("PLAYER_HEAD"));
        } else {
            return new ItemStack(Material.valueOf("SKULL_ITEM"), 1, (byte) 3);
        }
    }

    private static void notNull(Object o, String name) {
        if (o == null) {
            throw new NullPointerException(name + " should not be null!");
        }
    }

    public static String getDataFromName(String name) {
        try {
            URL url_0 = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            InputStreamReader reader_0 = new InputStreamReader(url_0.openStream());

            JsonElement jsonElement;

            if ((jsonElement = new JsonParser().parse(reader_0)) == null) {
                return "";
            }

            if (jsonElement.isJsonNull()) {
                return "";
            }

            String uuid = jsonElement.getAsJsonObject().get("id").getAsString();

            URL url_1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            InputStreamReader reader_1 = new InputStreamReader(url_1.openStream());
            JsonObject textureProperty = new JsonParser().parse(reader_1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String texture = textureProperty.get("value").getAsString();

            if (texture == null || texture.isEmpty()) {
                texture = "";
            }

            return texture;
        } catch (IOException e) {
            System.err.println("Could not get skin data from session servers!");
            e.printStackTrace();
            return null;
        }
    }

    public static ItemStack[] decrypt(String data) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];
            for (int i = 0; i < items.length; i++) items[i] = (ItemStack) dataInput.readObject();
            dataInput.close();
            return items;
        } catch (Exception e) {
            e.printStackTrace();
            return new ItemStack[0];
        }
    }

    public static String encrypt(ItemStack[] items) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeInt(items.length);
            for (ItemStack item : items) dataOutput.writeObject(item);
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public static int getMaxInventorySize(int number) {
        if (number >= 0 && number <= 9) {
            return 9;
        } else if (number >= 10 && number <= 18) {
            return 18;
        } else if (number >= 19 && number <= 27) {
            return 27;
        } else if (number >= 28 && number <= 36) {
            return 36;
        } else if (number >= 37 && number <= 45) {
            return 45;
        } else {
            return 54;
        }
    }
}
