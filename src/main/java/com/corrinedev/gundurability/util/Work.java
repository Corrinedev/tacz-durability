package com.corrinedev.gundurability.util;

import net.minecraft.world.entity.Entity;

public abstract class Work<T extends Entity> implements Runnable {
    public final T entity;
    public int tick;

    public Work(T entity, int ticks) {
        this.entity = entity;
        tick = ticks;
    }
    public void tick() {}
}
