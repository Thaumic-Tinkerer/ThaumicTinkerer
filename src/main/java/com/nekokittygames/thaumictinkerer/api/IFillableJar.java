/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.api;

import thaumcraft.api.aspects.Aspect;

/**
 * Fillable jar interface
 */
public interface IFillableJar {
    /**
     * Gets the max capacity of the jar
     *
     * @param aspect Aspect querying
     * @return amount of aspect jar is able to handle
     */
    int getMaxCapacity(Aspect aspect);
}
