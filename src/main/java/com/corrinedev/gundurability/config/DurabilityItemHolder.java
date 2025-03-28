package com.corrinedev.gundurability.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

public record DurabilityItemHolder(String id, float durability, int uses, Slots slot, float maxDurability, float minDurability, JsonObject gunTag) {
    public enum Slots {BARREL, BOLT, SPRING, MISC}
    public static DurabilityItemHolder BRASS_BRUSH = new DurabilityItemHolder("gundurability:brass_brush", 2.5f, 16, Slots.MISC, 80, 50, null);
    public static DurabilityItemHolder WD40 = new DurabilityItemHolder("gundurability:wd_40", 4f, 8, Slots.MISC, 60, 30, null);
    public static DurabilityItemHolder RECOIL_SPRING = new DurabilityItemHolder("gundurability:recoil_spring", 30f, 1, Slots.SPRING, 90, 10, null);
    public static DurabilityItemHolder GUN_BOLT = new DurabilityItemHolder("gundurability:gun_bolt", 25f, 1, Slots.BOLT, 80, 0, null);
    public static DurabilityItemHolder GUN_BARREL = new DurabilityItemHolder("gundurability:gun_barrel", 17.5f, 1, Slots.BARREL, 100, 0, null);
    public static JsonObject AKTAGOBJ = new Gson().fromJson("""
            {
                "tagName": "AK Barrels",
                "gunIds": [
                    "tacz:qbz_95",
                    "tacz:rpk",
                    "tacz:type_81",
                    "tacz:sks_tactical",
                    "tacz:ak47"
                ]
            }""", JsonObject.class);
    public static DurabilityItemHolder AK_BARREL = new DurabilityItemHolder("gundurability:ak_barrel", 25f, 1, Slots.BARREL, 100, 0, AKTAGOBJ);
}

