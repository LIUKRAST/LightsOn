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
            setting.render(guiGraphics, leftPos, topPos+y+13);
            y+=12+setting.getHeight();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if(save == null) return;
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
        for(BlockNetSetting<?> setting : settings) {
            setting.save(tag);
        }
        return tag;
    }
}
