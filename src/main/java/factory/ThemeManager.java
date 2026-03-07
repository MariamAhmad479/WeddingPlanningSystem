/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package factory;

public class ThemeManager {
    private static GUIFactory currentFactory = new LightGUIFactory();

    public static GUIFactory getFactory() {
        return currentFactory;
    }

    public static void setLightTheme() {
        currentFactory = new LightGUIFactory();
    }

    public static void setDarkTheme() {
        currentFactory = new DarkGUIFactory();
    }

    public static boolean isDarkTheme() {
        return currentFactory instanceof DarkGUIFactory;
    }
}
