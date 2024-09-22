package net.frozenblock.lightsOn.screen;

import net.frozenblock.lightsOn.block.LightBeamBlockEntity;
import net.frozenblock.lightsOn.packet.LightBeamUpdatePacket;
import net.frozenblock.lightsOn.platform.Services;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class LightBeamSetupScreen extends Screen {

    private static final Component COLOR_LABEL = Component.translatable("light_beam.color");
    private static final Component PITCH_LABEL = Component.translatable("light_beam.pitch");
    private static final Component YAW_LABEL = Component.translatable("light_beam.yaw");
    private static final Component DURATION_LABEL = Component.translatable("light_beam.duration");
    private static final Component SIZE_LABEL = Component.translatable("light_beam.size");
    private static final Component LENGTH_LABEL = Component.translatable("light_beam.length");

    private final LightBeamBlockEntity entity;
    private ColorInput type = ColorInput.RGB;
    private EditBox colorChannelA;
    private EditBox colorChannelB;
    private EditBox colorChannelC;
    private EditBox pitchEdit;
    private EditBox yawEdit;
    private EditBox durationEdit;
    private EditBox sizeEdit;
    private EditBox lengthEdit;
    private Button startButton;

    public LightBeamSetupScreen(LightBeamBlockEntity entity) {
        super(GameNarrator.NO_TITLE);
        this.entity = entity;
    }

    /* TODO: Check this!
    @Override
    public void tick() {
        this.colorChannelA.tick();
        this.colorChannelB.tick();
        this.colorChannelC.tick();
        this.pitchEdit.tick();
        this.yawEdit.tick();
        this.durationEdit.tick();
        sizeEdit.tick();
        lengthEdit.tick();
    }*/

    private void onDone(Button ignored) {
        this.sendToServer();
        assert this.minecraft != null;
        this.minecraft.setScreen(null);
    }

    private void onCancel(Button ignored) {
        assert this.minecraft != null;
        this.minecraft.setScreen(null);
    }

    private void sendToServer() {
        Services.PACKET_HELPER.send2S(new LightBeamUpdatePacket(entity.getBlockPos(), packData()));
    }

    private CompoundTag packData() {
        final CompoundTag tag = new CompoundTag();

        int rgb = getColor();
        tag.putInt("Color", rgb);
        tag.putFloat("Pitch", Float.parseFloat(pitchEdit.getValue()));
        tag.putFloat("Yaw", Float.parseFloat(yawEdit.getValue()));
        tag.putFloat("Duration", Float.parseFloat(durationEdit.getValue()));
        tag.putFloat("Size", Float.parseFloat(sizeEdit.getValue()));
        tag.putFloat("Length", Float.parseFloat(lengthEdit.getValue()));
        return tag;
    }

    @Override
    public void onClose() {
        this.onCancel(null);
    }

    @Override
    protected void init() {
        //if(minecraft != null) this.minecraft.keyboardHandler.setSendRepeatsToGui(true); TODO: Check this

        @SuppressWarnings("unused")
        CycleButton<ColorInput> colorButton = this.addRenderableWidget(CycleButton.builder(ColorInput::getName).withValues(ColorInput.values()).withInitialValue(ColorInput.RGB).displayOnlyValue()
                .create(this.width / 2 - 153, 20, 30, 20, COLOR_LABEL, this::reloadColor));

        this.colorChannelA = new EditBox(this.font, this.width/2 - 112, 20, 80, 20, Component.translatable("light_beam.color"));
        this.colorChannelA.setMaxLength(128);
        this.colorChannelA.setValue(String.valueOf(ColorInput.int2rgb(entity.calculateColor(1))[0]));
        this.colorChannelA.setResponder((a) -> this.updateValidity());
        this.addWidget(this.colorChannelA);

        this.colorChannelB = new EditBox(this.font, this.width/2 - 112 + 90, 20, 80, 20, Component.translatable("light_beam.color"));
        this.colorChannelB.setMaxLength(128);
        this.colorChannelB.setValue(String.valueOf(ColorInput.int2rgb(entity.calculateColor(1))[1]));
        this.colorChannelB.setResponder((a) -> this.updateValidity());
        this.addWidget(this.colorChannelB);

        this.colorChannelC = new EditBox(this.font, this.width/2 - 112 + 180, 20, 80, 20, Component.translatable("light_beam.color"));
        this.colorChannelC.setMaxLength(128);
        this.colorChannelC.setValue(String.valueOf(ColorInput.int2rgb(entity.calculateColor(1))[2]));
        this.colorChannelC.setResponder((a) -> this.updateValidity());
        this.addWidget(this.colorChannelC);

        this.pitchEdit = new EditBox(this.font, this.width/2 - 152, 55, 140, 20, Component.translatable("light_beam.pitch"));
        this.pitchEdit.setMaxLength(128);
        this.pitchEdit.setValue(String.valueOf(entity.calculatePitch(1)));
        this.pitchEdit.setResponder((a) -> this.updateValidity());
        this.addWidget(this.pitchEdit);
        this.yawEdit = new EditBox(this.font, this.width / 2 + 8, 55, 140, 20, Component.translatable("light_beam.yaw"));
        this.yawEdit.setMaxLength(128);
        this.yawEdit.setValue(String.valueOf(entity.calculateYaw(1)));
        this.yawEdit.setResponder(a -> this.updateValidity());
        this.addWidget(this.yawEdit);

        this.sizeEdit = new EditBox(this.font, this.width / 2 - 152, 90, 140, 20, Component.translatable("light_beam.size"));
        this.sizeEdit.setMaxLength(128);
        this.sizeEdit.setValue(String.valueOf(entity.calculateSize(1)));
        this.sizeEdit.setResponder(a -> this.updateValidity());
        this.addWidget(sizeEdit);
        this.lengthEdit = new EditBox(this.font, this.width / 2 + 8, 90, 140, 20, Component.translatable("light_beam.length"));
        this.lengthEdit.setMaxLength(128);
        this.lengthEdit.setValue(String.valueOf(entity.calculateLength(1)));
        this.lengthEdit.setResponder(a -> this.updateValidity());
        this.addWidget(lengthEdit);
        this.durationEdit = new EditBox(this.font, this.width / 2 - 152, 125, 300, 20, Component.translatable("light_beam.duration"));
        this.durationEdit.setMaxLength(128);
        this.durationEdit.setValue(String.valueOf(entity.getDuration()));
        this.durationEdit.setResponder(a -> this.updateValidity());
        this.addWidget(this.durationEdit);
        this.startButton = this.addRenderableWidget(Button
                .builder(Component.translatable("light_beam.start"), this::onDone)
                .bounds(this.width / 2 - 154, 210, 150, 20)
                .build());
        this.addRenderableWidget(Button
                .builder(CommonComponents.GUI_CANCEL, this::onCancel)
                .bounds(this.width / 2 + 4, 210, 150, 20)
                .build());
        this.setInitialFocus(this.colorChannelA);
        this.updateValidity();
    }

    private void reloadColor(CycleButton<ColorInput> unused, ColorInput b) {
        try {
            if (type == ColorInput.RGB) {
                int[] hsl = ColorInput.int2hsl(getColor());
                this.colorChannelA.setValue(String.valueOf(hsl[0]));
                this.colorChannelB.setValue(String.valueOf(hsl[1]));
                this.colorChannelC.setValue(String.valueOf(hsl[2]));
            } else {
                int[] rgb = ColorInput.int2rgb(getColor());
                this.colorChannelA.setValue(String.valueOf(rgb[0]));
                this.colorChannelB.setValue(String.valueOf(rgb[1]));
                this.colorChannelC.setValue(String.valueOf(rgb[2]));
            }
        } catch (Exception ignored) {}
        this.type = b;
    }

    @Override
    public void resize(@NotNull Minecraft minecraft, int w, int h) {
        String r = this.colorChannelA.getValue();
        String g = this.colorChannelB.getValue();
        String b = this.colorChannelC.getValue();
        String p = this.pitchEdit.getValue();
        String y = this.yawEdit.getValue();
        String d = this.durationEdit.getValue();
        String s = this.sizeEdit.getValue();
        String l = this.lengthEdit.getValue();
        this.init(minecraft, w, h);

        this.colorChannelA.setValue(r);
        this.colorChannelB.setValue(g);
        this.colorChannelC.setValue(b);

        this.pitchEdit.setValue(p);
        this.yawEdit.setValue(y);
        this.durationEdit.setValue(d);
        this.sizeEdit.setValue(s);
        this.lengthEdit.setValue(l);
    }

    @Override
    public void removed() {
        //if(minecraft != null) this.minecraft.keyboardHandler.setSendRepeatsToGui(false); TODO: Check this!
    }

    private void updateValidity() {
        boolean flag = true;
        try {
            getColor();
            float pitch = Float.parseFloat(this.pitchEdit.getValue());
            if(pitch < 0 || pitch > 180) flag = false;
            Float.parseFloat(this.yawEdit.getValue());
            int d = Integer.parseInt(this.durationEdit.getValue());
            if(d < 0) flag = false;
            float size = Float.parseFloat(this.sizeEdit.getValue());
            if(size < 0 || size > 100) flag = false;
            float length = Float.parseFloat(this.lengthEdit.getValue());
            if(length < 0 || length > 100) flag = false;
        } catch (NumberFormatException e) {
            flag = false;
        }
        this.startButton.active = flag;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(super.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        } else if(!this.startButton.active || keyCode != 257 && keyCode != 335) {
            return false;
        } else {
            this.onDone(null);
            return true;
        }
    }

    private int getColor() {
        return type.parse(Integer.parseInt(colorChannelA.getValue()), Integer.parseInt(colorChannelB.getValue()),Integer.parseInt(colorChannelC.getValue()));
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        //this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        int color;
        try {
            color = getColor();
        } catch (Exception e) {
            color = 10526880;
        }
        guiGraphics.drawString(this.font, COLOR_LABEL, this.width / 2 - 153, 10, color);
        this.colorChannelA.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.colorChannelB.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.colorChannelC.render(guiGraphics, mouseX, mouseY, partialTicks);
        guiGraphics.drawString(this.font, PITCH_LABEL, this.width / 2 - 153, 45, 10526880);
        this.pitchEdit.render(guiGraphics, mouseX, mouseY, partialTicks);
        guiGraphics.drawString(this.font, YAW_LABEL, this.width / 2 + 7, 45, 10526880);
        this.yawEdit.render(guiGraphics, mouseX, mouseY, partialTicks);
        guiGraphics.drawString(this.font, SIZE_LABEL, this.width / 2 - 153, 80, 10526880);
        this.sizeEdit.render(guiGraphics, mouseX, mouseY, partialTicks);
        guiGraphics.drawString(this.font, LENGTH_LABEL, this.width / 2 + 7, 80, 10526880);
        this.lengthEdit.render(guiGraphics, mouseX, mouseY, partialTicks);
        guiGraphics.drawString(this.font, DURATION_LABEL, this.width / 2 - 153, 115, 10526880);
        this.durationEdit.render(guiGraphics, mouseX, mouseY, partialTicks);
    }
}
