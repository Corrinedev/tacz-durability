package com.corrinedev.gundurability.config;

public record DurabilityItemHolder(String id, int durability, int uses, Slots slot) {
    public enum Slots {BARREL, BOLT, SPRING, MISC}
}

