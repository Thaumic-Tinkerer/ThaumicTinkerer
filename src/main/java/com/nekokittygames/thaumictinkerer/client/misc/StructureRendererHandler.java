/*
 * Copyright (c) 2020. Katrina Knight
 */

package com.nekokittygames.thaumictinkerer.client.misc;

public class StructureRendererHandler {
/*    static float radialHudScale = 0.0F;
    long lastTime = 0L;
    boolean lastState = false;
    public void handleStructureRadial(Minecraft mc, long time, RenderGameOverlayEvent event) {
        if ((TTKeys.isStructureRadialActive()) || (radialHudScale > 0.0F)) {
            if (TTKeys.isStructureRadialActive()) {
                if (mc.currentScreen != null) {
                    //KeyHandler.radialActive = false;
                    //KeyHandler.radialLock = true;
                    mc.setIngameFocus();
                    mc.setIngameNotInFocus();
                    return;
                }
                if (radialHudScale == 0.0F) {
                    getFociInfo(mc);
                    if ((this.foci.size() > 0) && (mc.inGameHasFocus)) {
                        mc.inGameHasFocus = false;
                        mc.mouseHelper.ungrabMouseCursor();
                    }

                }
            } else if ((mc.currentScreen == null) && (this.lastState)) {
                if (org.lwjgl.opengl.Display.isActive()) {
                    if (!mc.inGameHasFocus) {
                        mc.inGameHasFocus = true;
                        mc.mouseHelper.grabMouseCursor();
                    }
                }
                this.lastState = false;
            }


            renderFocusRadialHUD(event.getResolution().getScaledWidth_double(), event.getResolution().getScaledHeight_double(), time, event.getPartialTicks());
            if (time > this.lastTime) {
                for (String key : this.fociHover.keySet()) {
                    if (((Boolean) this.fociHover.get(key)).booleanValue()) {
                        if ((!KeyHandler.radialActive) && (!KeyHandler.radialLock)) {
                            PacketHandler.INSTANCE.sendToServer(new PacketFocusChangeToServer(key));
                            KeyHandler.radialLock = true;
                        }
                        if (((Float) this.fociScale.get(key)).floatValue() < 1.3F) {
                            this.fociScale.put(key, Float.valueOf(Math.min(1.3F, ((Float) this.fociScale.get(key)).floatValue() + getRadialChange(time, this.lastTime, 150L))));
                        }
                    } else if (((Float) this.fociScale.get(key)).floatValue() > 1.0F) {
                        this.fociScale.put(key, Float.valueOf(Math.max(1.0F, ((Float) this.fociScale.get(key)).floatValue() - getRadialChange(time, this.lastTime, 250L))));
                    }
                }


                if (!KeyHandler.radialActive) {
                    radialHudScale -= getRadialChange(time, this.lastTime, 150L);
                }
                else if ((KeyHandler.radialActive) && (radialHudScale < 1.0F)) {
                    radialHudScale += getRadialChange(time, this.lastTime, 150L);
                }
                if (radialHudScale > 1.0F) radialHudScale = 1.0F;
                if (radialHudScale < 0.0F) {
                    radialHudScale = 0.0F;
                    KeyHandler.radialLock = false;
                }
                this.lastState = KeyHandler.radialActive;
            }
        }

        this.lastTime = time;
    }*/
}
