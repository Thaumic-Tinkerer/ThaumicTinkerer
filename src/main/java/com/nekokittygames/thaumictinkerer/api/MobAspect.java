/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.api;

import thaumcraft.api.aspects.AspectList;

public class MobAspect {
    private Class<?> entityClass;
    private AspectList aspects;
    private float scale;
    private float offset;
    private String prefix="minecraft";

    public MobAspect(Class<?> entityClass, AspectList aspects, float scale, float offset) {
        this.entityClass = entityClass;
        this.aspects = aspects;
        this.scale = scale;
        this.offset = offset;
    }

    public MobAspect(Class<?> entityClass, AspectList aspects) {
        this.entityClass = entityClass;
        this.aspects = aspects;
        this.scale = 1.0f;
        this.offset = 0.0f;
    }


    public String getPrefix() {
        return prefix;
    }

    public MobAspect setPrefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public MobAspect setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
        return this;
    }

    public AspectList getAspects() {
        return aspects;
    }

    public MobAspect setAspects(AspectList aspects) {
        this.aspects = aspects;
        return this;
    }

    public float getScale() {
        return scale;
    }

    public MobAspect setScale(float scale) {
        this.scale = scale;
        return this;
    }

    public float getOffset() {
        return offset;
    }

    public MobAspect setOffset(float offset) {
        this.offset = offset;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MobAspect{");
        sb.append("entityClass=").append(entityClass);
        sb.append(", aspects=").append(aspects);
        sb.append(", scale=").append(scale);
        sb.append(", offset=").append(offset);
        sb.append(", prefix='").append(prefix).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
