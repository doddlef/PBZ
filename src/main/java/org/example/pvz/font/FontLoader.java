package org.example.pvz.font;

import javafx.scene.text.Font;

import java.io.IOException;

public class FontLoader {
    public static Font getFont(int size) {
        String currentFontFile = "/org/example/font/Fleftex_M.ttf";
        try(var in = FontLoader.class.getResourceAsStream(currentFontFile)){
            Font ret = Font.loadFont(in, size);
            return ret;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
