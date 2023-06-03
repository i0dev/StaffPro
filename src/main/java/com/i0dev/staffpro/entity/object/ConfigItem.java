package com.i0dev.staffpro.entity.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

import java.util.List;

@AllArgsConstructor
@Getter
public class ConfigItem {
    public String displayName;
    public int amount;
    public int slot;
    Material material;
    public List<String> lore;
    public boolean glow;

}

