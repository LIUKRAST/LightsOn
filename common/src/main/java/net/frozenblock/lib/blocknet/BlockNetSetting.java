package net.frozenblock.lib.blocknet;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.Supplier;

/**
 * Defines a setting modifiable through BlockNet config screens.
 * @since 1.0
 * @author LiukRast
 * */
public abstract class BlockNetSetting<T> {
    private final String key;
    private final Supplier<T> getter;
    private Component title = null;
    private T value;
    private boolean isValid = true;
    /**
     * The constructor must pass a key,
     * and a supplier which is usually the original getter method from the block entity class
     * @param key The string key, used for data export and GUI language key.
     * @param getter The getter method which is used to get the value of the variable we're modifying through this setting.
     *               It's used in case the value has to be reset, and so we invoke and get it directly from the block entity
     * @since 1.0
     * @author LiukRast
     * */
    public BlockNetSetting(String key, Supplier<T> getter) {
        this.key = key;
        this.getter = getter;
        this.value = getter.get();
    }
    /**
     * Invoked when {@code init(...)} is invoked in gui.
     * @param widgets A widget list where we can add widgets required for our GUI.
     *                See an example in {@link net.frozenblock.lib.blocknet.setting.RangedBlockNetSetting}
     * @param leftPos The leftPosition of the screen, where you should start placing your widgets
     * @param topPos The topPosition of the screen, where you should start placing your widgets
     * @since 1.0
     * @author LiukRast
     * */
    public void initGui(List<AbstractWidget> widgets, int leftPos, int topPos) {}
    /**
     * @return a title tip for the user to understand the limits of the setting.
     * An example can be seen in {@link net.frozenblock.lib.blocknet.setting.RangedBlockNetSetting}
     * @since 1.0
     * @author LiukRast
     * */
    public abstract String getTitleTip();
    /**
     * The method involved for data save. Always save your data using the {@link #getKey()}
     * @param tag the tag where you should write your values.
     * @since 1.0
     * @author LiukRast
     * */
    public abstract void save(CompoundTag tag);
    /**
     * @return The height required for this setting. Each setting is stacked on top of another, so this can be changed.
     * @since 1.0
     * @author LiukRast
     * */
    public int getHeight() {
        return 16;
    }
    /**
     * Invoked ONLY if the setting has been clicked in the GUI.
     * @param mouseX the X of the mouse, relative to the setting area
     * @param mouseY the Y of the mouse, relative to the setting area
     * @param topPos The top position of the screen
     * @param leftPos The left position of the screen
     * @return {@code true} if the action succeeded
     * @since 1.0
     * @author LiukRast
     * */
    public boolean mouseClicked(double mouseX, double mouseY, int leftPos, int topPos) {
        return false;
    }
    /**
     * Invoked every time the mouse has been released in the screen this is rendered in
     * @param mouseX the X of the mouse, relative to the setting area
     * @param mouseY the Y of the mouse, relative to the setting area
     * @param topPos The top position of the screen
     * @param leftPos The left position of the screen
     * @return {@code true} if the action succeeded
     * @since 1.0
     * @author LiukRast
     * */
    public boolean mouseReleased(double mouseX, double mouseY, int leftPos, int topPos) {
        return false;
    }
    /**
     * Invoked every time the mouse has been scrolled in the screen this is rendered in
     * @param mouseX the X of the mouse, relative to the setting area
     * @param mouseY the Y of the mouse, relative to the setting area
     * @param topPos The top position of the screen
     * @param leftPos The left position of the screen
     * @return {@code true} if the action succeeded
     * @since 1.0
     * @author LiukRast
     * */
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY, int leftPos, int topPos) {
        return false;
    }
    /**
     * Invoked every time the mouse has been moved in the screen this is rendered in
     * @param mouseX the X of the mouse, relative to the setting area
     * @param mouseY the Y of the mouse, relative to the setting area
     * @param topPos The top position of the screen
     * @param leftPos The left position of the screen
     * @since 1.0
     * @author LiukRast
     * */
    public void mouseMoved(double mouseX, double mouseY, int leftPos, int topPos) {}
    /**
     * Invoked when rendering the screen. Allows rendering custom things
     * @param graphics The GuiGraphics instance
     * @param mouseX the X of the mouse, relative to the setting area
     * @param mouseY the Y of the mouse, relative to the setting area
     * @param topPos The top position of the screen
     * @param leftPos The left position of the screen
     * @since 1.0
     * @author LiukRast
     * */
    public void render(GuiGraphics graphics, int leftPos, int topPos, int mouseX, int mouseY) {}
    /**
     * @return {@code true} if the setting is in a valid state and so it's ready to be saved
     * @since 1.0
     * @author LiukRast
     * */
    public boolean isValid() {
        return this.isValid;
    }
    /**
     * Sets the valid state
     * @param valid the state
     * @since 1.0
     * @author LiukRast
     * */
    public void setValid(boolean valid) {
        isValid = valid;
    }
    /**
     * Resets the value from the original getter method
     * @since 1.0
     * @author LiukRast
     * */
    public void resetValue() {
        this.value = getter.get();
    }
    /**
     * @return The setting current value, unsaved
     * @since 1.0
     * @author LiukRast
     * */
    public T getValue() {
        return value;
    }
    /**
     * @param value The value to be set
     * @since 1.0
     * @author LiukRast
     * */
    public void setValue(T value) {
        this.value = value;
    }
    /**
     * @return The setting Key
     * @since 1.0
     * @author LiukRast
     * */
    public String getKey() {
        return key;
    }
    /**
     * @return Two settings with the same key are considered equal in the Setting context,
     * since you cannot register two settings with the same key.
     * @param obj The object to be compared
     * @since 1.0
     * @author LiukRast
     * */
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof BlockNetSetting<?> other)) return false;
        return other.key.equals(this.key);
    }
    /**
     * @return The title for display purpose in the Screen.
     * Usually its generated automatically, and there's no need to override it.
     * Note that you should include {@code blocknet.settingType.YOURKEY} in your lang files
     * */
    public Component getTitle() {
        if(title == null) title = Component.translatable("blocknet.settingType." + key).append(net.minecraft.network.chat.Component.literal(" " + getTitleTip()).withStyle(ChatFormatting.GRAY));
        return title;
    }
}
