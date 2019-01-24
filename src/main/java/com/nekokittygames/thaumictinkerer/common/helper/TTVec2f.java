package com.nekokittygames.thaumictinkerer.common.helper;


public class TTVec2f {

    public static final TTVec2f ZERO = new TTVec2f(0.0F, 0.0F);
    public static final TTVec2f ONE = new TTVec2f(1.0F, 1.0F);
    public static final TTVec2f UNIT_X = new TTVec2f(1.0F, 0.0F);
    public static final TTVec2f NEGATIVE_UNIT_X = new TTVec2f(-1.0F, 0.0F);
    public static final TTVec2f UNIT_Y = new TTVec2f(0.0F, 1.0F);
    public static final TTVec2f NEGATIVE_UNIT_Y = new TTVec2f(0.0F, -1.0F);
    public static final TTVec2f MAX = new TTVec2f(3.4028235E38F, 3.4028235E38F);
    public static final TTVec2f MIN = new TTVec2f(1.4E-45F, 1.4E-45F);
    public final float x;
    public final float y;

    public TTVec2f(float xIn, float yIn) {
        this.x = xIn;
        this.y = yIn;
    }
}
