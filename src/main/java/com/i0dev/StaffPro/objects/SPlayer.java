package com.i0dev.StaffPro.objects;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class SPlayer {

    // Identifier

    public UUID uuid;

    // States

    public boolean vanished = false;
    public boolean modmode = false;
    public boolean frozen = false;
    public boolean soft_frozen = false;

    // Mod Mode Storage
    public String savedInventoryContents = "";
    public String savedArmorContents = "";
    public double savedLocationX = 0;
    public double savedLocationY = 0;
    public double savedLocationZ = 0;
    public float savedLocationYaw = 0;
    public float savedLocationPitch = 0;
    public String savedLocationWorld = "";
    public String savedGameMode = "";
    public int savedHunger = 0;
    public double savedHealth = 0;
    public float savedEXP = 0;
    public boolean flying = false;
    public boolean allowFlight = false;

    public SPlayer(UUID uuid) {
        this.uuid = uuid;
    }
}
