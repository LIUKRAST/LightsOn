package net.frozenblock.lightsOn.screen;

import net.frozenblock.lib.blocknet.BlockNetConfigurable;
import net.frozenblock.lib.blocknet.BlockNetSetting;
import net.frozenblock.lib.blocknet.BlockNetSettingBuilder;
import net.frozenblock.lightsOn.LightsOnConstants;
import net.frozenblock.lightsOn.packet.BlockNetConfigUpdatePacket;
import net.frozenblock.lightsOn.platform.Services;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Set;

public class BlockNetConfigScreen extends Screen {
    private static final ResourceLocation TEXTURE = LightsOnConstants.id("textures/gui/blocknet_config.png");

    private final Set<BlockNetSetting<?>> settings;
    private final BlockPos pos;
    private Button save = null;
    private boolean valid = true;
    private int interpolation = 0;

    public BlockNetConfigScreen(BlockNetConfigurable configurable, BlockPos pos) {
        super(GameNarrator.NO_TITLE);
        var builder = new BlockNetSettingBuilder();
        configurable.defineSettings(builder);
        this.settings = builder.getSettings();
        this.pos = pos; //TODO: CLOSE SCREEN WHEN BLOCK IS BROKEN
    }

    @Override
    protected void init() {
        super.init();
        int leftPos = (this.width-224)>>1;
        int topPos = (this.height-144)>>1;
        int rightPos = (this.width+224)>>1;
        int bottomPos = (this.height+144)>>1;
        ArrayList<AbstractWidget> list = new ArrayList<>();
        int y = 0;
        for(BlockNetSetting<?> setting : settings) {
            setting.initGui(list, leftPos+4, topPos+13+y, this.font);
            y+=12+setting.getHeight();
        }
        list.forEach(this::addRenderableWidget);
        this.save = Button.builder(Component.translatable("blockNet.configScreen.save"), this::onDone)
                .bounds(rightPos-44, bottomPos-20, 40, 16)
                .build();
        this.addRenderableWidget(save);
        var interpolation = new EditBox(this.font,rightPos-44, bottomPos-38, 40, 16, Component.literal("Time"));
        interpolation.setMaxLength(128);
        interpolation.setResponder(s -> {
            try {
                this.interpolation = Integer.parseInt(s);
                this.valid = true;
            } catch (NumberFormatException e) {
                this.valid = false;
            }
        });
        interpolation.setValue(String.valueOf(this.interpolation));
        this.addRenderableWidget(interpolation);
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        int leftPos = (this.width-224)>>1;
        int topPos = (this.height-144)>>1;
        guiGraphics.blit(TEXTURE, leftPos, topPos, 0, 0, 224, 144);
        int y = 0;
        for(BlockNetSetting<?> setting : settings) {
            guiGraphics.drawString(this.font, setting.getTitle(), leftPos + 4, topPos + y + 4, -1);
            setting.render(guiGraphics, leftPos, topPos+y+13, mouseX, mouseY-y-13-topPos);
            y+=12+setting.getHeight();
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int y = 0;
        int leftPos = (this.width-224)>>1;
        int topPos = (this.height-144)>>1;
        for(BlockNetSetting<?> setting : settings) {
            var fMouseY = mouseY-y-13-topPos;
            boolean hovered = fMouseY >= 0 && fMouseY < 11 && mouseX >= leftPos+4 && mouseX < leftPos+132;
            if(hovered && setting.mouseClicked(mouseX, fMouseY, leftPos, topPos)) return true;
            y+=12+setting.getHeight();
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        int y = 0;
        int leftPos = (this.width-224)>>1;
        int topPos = (this.height-144)>>1;
        for(BlockNetSetting<?> setting : settings) {
            var fMouseY = mouseY-y-13-topPos;
            boolean hovered = fMouseY >= 0 && fMouseY < 11 && mouseX >= leftPos+4 && mouseX < leftPos+132;
            if(hovered && setting.mouseScrolled(mouseX, fMouseY, scrollX, scrollY, leftPos, topPos+13+y)) return true;
            y+=12+setting.getHeight();
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        int y = 0;
        int leftPos = (this.width-224)>>1;
        int topPos = (this.height-144)>>1;
        for(BlockNetSetting<?> setting : settings) {
            var fMouseY = mouseY-y-13-topPos;
            if(setting.mouseReleased(mouseX, fMouseY, leftPos, topPos)) return true;
            y+=12+setting.getHeight();
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        int y = 0;
        int leftPos = (this.width-224)>>1;
        int topPos = (this.height-144)>>1;
        for(BlockNetSetting<?> setting : settings) {
            var fMouseY = mouseY-y-13-topPos;
            setting.mouseMoved(mouseX, fMouseY, leftPos, topPos);
            y+=12+setting.getHeight();
        }
        super.mouseMoved(mouseX, mouseY);
    }

    @Override
    public void tick() {
        super.tick();
        if(save == null) return;
        if(!this.valid) {
            save.active = false;
            return;
        }
        for(BlockNetSetting<?> setting : settings) {
            if(!setting.isValid()) {
                save.active = false;
                return;
            }
        }
        save.active = true;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void onDone(Button ignored) {
        Services.PACKET_HELPER.send2S(new BlockNetConfigUpdatePacket(pos, packData()));
        this.onClose();
    }

    private CompoundTag packData() {
        final CompoundTag tag = new CompoundTag();
        tag.putInt("duration", interpolation);
        for(BlockNetSetting<?> setting : settings) {
            setting.save(tag);
        }
        return tag;
    }
}
