package com.lksoft.nyaage.desktop;

/**
 * Created by lake on 14/12/21.
 */
public class NyaTexturePacker {
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        NyaGamePackager.packageResources("../resources/", "../android/assets/");
    }
}
