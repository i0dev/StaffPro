package com.i0dev.staffpro.util;

import com.i0dev.staffpro.Perm;
import com.i0dev.staffpro.StaffProPlugin;
import com.i0dev.staffpro.entity.MLang;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.permissions.Permissible;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {

    public static String prefixAndColor(String message, Pair<String, String>... pairs) {
        return color(pair(message.replace("%prefix%", MLang.get().prefix), pairs));
    }

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String[] prefixColorFormat(List<String> in, Pair<String, String>... pairs) {

        String[] msg = new String[in.size()];
        for (int i = 0; i < in.size(); i++) {
            msg[i] = prefixAndColor(in.get(i));
        }

        return msg;
    }

    @SafeVarargs
    public static String pair(String msg, Pair<String, String>... pairs) {
        for (Pair<String, String> pair : pairs) {
            msg = msg.replace(pair.getKey(), pair.getValue());
        }
        return msg;
    }

    public static void messageAllStaffExcludes(String msg, Player... excludes) {
        if (excludes == null) {
            Bukkit.getOnlinePlayers().stream()
                    .filter(Perm.STAFF::has)
                    .forEach(player -> player.sendMessage(msg));
            return;
        }
        Bukkit.getOnlinePlayers().stream()
                .filter(Perm.STAFF::has)
                .filter(player -> !Stream.of(excludes).map(Player::getUniqueId).toList().contains(player.getUniqueId()))
                .forEach(player -> player.sendMessage(msg));
    }

    public static NamespacedKey key(String key) {
        return new NamespacedKey(StaffProPlugin.get(), key);
    }

    public static ItemStack setDataValue(ItemStack itemStack, String key, String value) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(key(key), PersistentDataType.STRING, value);
        itemStack.setItemMeta(meta);
        return itemStack;
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

}
