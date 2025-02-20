package com.corrinedev.gundurability.config;

import com.google.gson.JsonObject;

import java.util.List;

public record DurabilityItemHolder(String id, float durability, int uses, Slots slot, float maxDurability, float minDurability, JsonObject gunTag) {
    public enum Slots {BARREL, BOLT, SPRING, MISC}
}

