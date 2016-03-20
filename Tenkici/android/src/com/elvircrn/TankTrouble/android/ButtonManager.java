package com.elvircrn.TankTrouble.android;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Vedo on 05.03.2016..
 */
public class ButtonManager {
    public static Texture[] textures;

    public static void load(int textureCount) {
        textures = new Texture[textureCount];
        textures [0] = Tenkici.manager.get("data/wall.png", Texture.class);
        textures [1] = Tenkici.manager.get("OptionsData/musicOn.png",Texture.class);
        textures [2] = Tenkici.manager.get("OptionsData/musicOff.png", Texture.class);
        textures [3] = Tenkici.manager.get("MainMenuData/soundOn.png", Texture.class);
        textures [4] = Tenkici.manager.get("MainMenuData/soundOff.png", Texture.class);
        textures [5] = Tenkici.manager.get("OptionsData/plus.png",Texture.class);
        textures [6] = Tenkici.manager.get("OptionsData/minus.png", Texture.class);
        textures [7] = Tenkici.manager.get("OptionsData/vibrateUp.png",Texture.class);
        textures [8] = Tenkici.manager.get("OptionsData/vibrateDown.png",Texture.class);
        textures [9] = Tenkici.manager.get("OptionsData/back.png",Texture.class);
        textures [10] = Tenkici.manager.get("OptionsData/vibrateUp.png",Texture.class);
        textures [11] = Tenkici.manager.get("OptionsData/vibrateDown.png",Texture.class);
        textures [12] = Tenkici.manager.get("OptionsData/back.png",Texture.class);
        textures [13] = Tenkici.manager.get("MainMenuData/spUp.png",Texture.class);
        textures [14] = Tenkici.manager.get("MainMenuData/spDn.png",Texture.class);
        textures [15] = Tenkici.manager.get("MainMenuData/mpUp.png",Texture.class);
        textures [16] = Tenkici.manager.get("MainMenuData/mpDn.png",Texture.class);
        textures [17] = Tenkici.manager.get("MainMenuData/gearUp.png",Texture.class);
        textures [18] = Tenkici.manager.get("MainMenuData/gearDn.png",Texture.class);
        textures [19] = Tenkici.manager.get("VictoryData/mainmenubtn.png", Texture.class);
        textures [20] = Tenkici.manager.get("VictoryData/rematchbtn.png", Texture.class);
        textures [21] = Tenkici.manager.get("MPMenuData/localUp.png", Texture.class);
        textures [22] = Tenkici.manager.get("MPMenuData/localDn.png", Texture.class);
        textures [23] = Tenkici.manager.get("MPMenuData/joinUp.png", Texture.class);
        textures [24] = Tenkici.manager.get("MPMenuData/joinDn.png", Texture.class);
        textures [25] = Tenkici.manager.get("MPMenuData/hostUp.png", Texture.class);
        textures [26] = Tenkici.manager.get("MPMenuData/hostDn.png", Texture.class);

    }
}
