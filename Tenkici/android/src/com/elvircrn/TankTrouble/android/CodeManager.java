package com.elvircrn.TankTrouble.android;

public class CodeManager {
    public static final int ServerTankIndex = 0;
    public static final int ClientTankIndex = 1;

    public static final byte RequestNewGame     = 1;
    public static final byte NewGameResponse    = 2;
    public static final byte Seed               = 3;
    public static final byte FrameClient        = 4;
    public static final byte FramerServer       = 5;
    public static final byte TankClientPosition = 6;
}