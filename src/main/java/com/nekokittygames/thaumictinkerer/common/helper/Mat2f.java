package com.nekokittygames.thaumictinkerer.common.helper;

public class Mat2f {
    public float m00 = 0.0f;
    public float m01 = 0.0f;
    public float m10 = 0.0f;
    public float m11 = 0.0f;

    public Mat2f setIdentity() {
        this.m00 = 1.0f;
        this.m01 = 0.0f;
        this.m10 = 0.0f;
        this.m11 = 1.0f;
        return this;
    }
}
