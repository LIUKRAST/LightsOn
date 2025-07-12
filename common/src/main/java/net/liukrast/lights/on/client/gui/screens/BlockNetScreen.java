package net.liukrast.lights.on.client.gui.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.liukrast.lights.LightsOnConstants;
import net.liukrast.lights.on.network.protocol.game.BlockNetDataSlotChangePacket;
import net.liukrast.lights.on.platform.Services;
import net.liukrast.lights.on.project.BlockNetProject;
import net.liukrast.lights.on.world.inventory.BlockNetMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.NonnullDefault;

import java.util.HashSet;
import java.util.Set;

@NonnullDefault
public class BlockNetScreen extends Screen implements MenuAccess<BlockNetMenu> {
    private static final ResourceLocation TEXTURE = LightsOnConstants.id("textures/gui/blocknet_interface.png");
    private final BlockNetMenu menu;
    private float scale = 20;
    private Vec2 pos = new Vec2(0,0);
    private Vec2 angle = new Vec2(-30, 45);
    private final Set<BlockPos> selected = new HashSet<>();

    /* Block entity synced data */
    private final Set<BlockPos> validPositions = new HashSet<>();
    @Nullable
    private BlockPos origin;
    private final BlockNetProject project = new BlockNetProject();
    private EditBox timeBox;

    public BlockNetScreen(BlockNetMenu menu, Inventory ignored, Component title) {
        super(title);
        this.menu = menu;
    }


    @Override
    protected void init() {
        super.init();
        timeBox = new EditBox(this.font, width/2+128, height-20, 40, 12, Component.empty());
        timeBox.insertText(String.valueOf(menu.rawTimeSlot.get()));
        timeBox.setResponder(s -> {
            try {
                Services.PACKET_HELPER.send2S(new BlockNetDataSlotChangePacket(2, (int) Long.parseLong(s)));
            } catch (NumberFormatException ignored) {}
        });
        addRenderableWidget(timeBox);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        if (minecraft == null || minecraft.level == null || minecraft.player == null) return;

        BlockRenderDispatcher blockRenderDispatcher = minecraft.getBlockRenderer();
        BlockEntityRenderDispatcher entityRenderDispatcher = minecraft.getBlockEntityRenderDispatcher();

        var pose = guiGraphics.pose();
        pose.pushPose();

        pose.translate((float) width/2, (float) height /2, 200);
        pose.mulPose(Axis.XP.rotationDegrees(angle.x));
        pose.translate(pos.x, pos.y, 0);
        pose.mulPose(Axis.YP.rotationDegrees(angle.y));
        pose.scale(scale, -scale, scale);


        pose.translate(-0.5, 0, -0.5);
        var bniPos = origin == null ? new BlockPos(0,0,0) : origin;
        pose.translate(-bniPos.getX(), -bniPos.getY(), -bniPos.getZ());

        pose.pushPose();
        pose.translate(bniPos.getX(), bniPos.getY(), bniPos.getZ());
        blockRenderDispatcher.renderSingleBlock(minecraft.level.getBlockState(bniPos), guiGraphics.pose(), guiGraphics.bufferSource(), LightTexture.pack(minecraft.level.getBrightness(LightLayer.BLOCK, bniPos), minecraft.level.getBrightness(LightLayer.SKY, bniPos)), OverlayTexture.NO_OVERLAY);
        pose.popPose();

        for(BlockPos pos : validPositions) {
            for(int x = -1; x < 2; x++) {
                for(int y = -1; y < 2; y++) {
                    for(int z = -1; z < 2; z++) {
                        var fPos = pos.offset(x, y, z);
                        pose.pushPose();
                        pose.translate(fPos.getX(), fPos.getY(), fPos.getZ());
                        if(selected.contains(fPos)) {
                            VoxelShape shape = minecraft.level.getBlockState(fPos).getShape(minecraft.level, fPos);
                            if (!shape.isEmpty()) {
                                LevelRenderer.renderVoxelShape(pose, guiGraphics.bufferSource().getBuffer(RenderType.lines()), shape,
                                        0,0,0, 1, 1, 1,1,true);
                            }
                        }
                        blockRenderDispatcher.renderSingleBlock(minecraft.level.getBlockState(fPos), guiGraphics.pose(), guiGraphics.bufferSource(), LightTexture.pack(minecraft.level.getBrightness(LightLayer.BLOCK, fPos), minecraft.level.getBrightness(LightLayer.SKY, fPos)), OverlayTexture.NO_OVERLAY);
                        var be = minecraft.level.getBlockEntity(fPos);
                        if(be != null) entityRenderDispatcher.render(be, 0, pose, guiGraphics.bufferSource());
                        pose.popPose();
                    }
                }
            }
        }

        pose.popPose();
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        guiGraphics.blit(TEXTURE, (width-256)>>1, height-20, 0, 0, 256, 9);
        guiGraphics.blit(TEXTURE, (width-256)>>1, height-18, 0, 9, menu.timeSlot.get(), 5);

        // Play buttons
        boolean hoverPlay = mouseX >= (width-5)>>1 && mouseX < ((width-5)>>1)+9 && mouseY >= height-30 && mouseY < height-21;
        boolean hoverReset = mouseX >= ((width-5)>>1)-10 && mouseX < ((width-5)>>1)-1 && mouseY >= height-30 && mouseY < height-21;
        boolean hoverEnd = mouseX >= ((width-5)>>1)+10 && mouseX < ((width-5)>>1)+19 && mouseY >= height-30 && mouseY < height-21;
        guiGraphics.blit(TEXTURE, (width-5)>>1, height-30, menu.playingSlot.get() > 0 ? 9 : 0, 14 + (hoverPlay ? 9 : 0), 9, 9);
        guiGraphics.blit(TEXTURE, ((width-5)>>1)-10, height-30, 27, 14 + (hoverReset?9:0), 9, 9);
        guiGraphics.blit(TEXTURE, ((width-5)>>1)+10, height-30, 18, 14 + (hoverEnd?9:0), 9, 9);

        for(long frame : project.keySet()) {
            int dur = (int)(frame/(float)menu.durationSlot.get() * 256);
            guiGraphics.blit(TEXTURE, ((width-256)>>1) + dur, height-18, 36, 14, 1, 9);
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        scale = (float) Mth.clamp(scale + scrollY, 1, 100);
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(mouseX >= (width-5)>>1 && mouseX < ((width-5)>>1)+9 && mouseY >= height-30 && mouseY < height-21)
            Services.PACKET_HELPER.send2S(new BlockNetDataSlotChangePacket(1, menu.playingSlot.get() > 0 ? 0 : 1));
        if(mouseX >= ((width-5)>>1)-10 && mouseX < ((width-5)>>1)-1 && mouseY >= height-30 && mouseY < height-21)
            Services.PACKET_HELPER.send2S(new BlockNetDataSlotChangePacket(0, 0));
        if(mouseX >= ((width-5)>>1)+10 && mouseX < ((width-5)>>1)+19 && mouseY >= height-30 && mouseY < height-21)
            Services.PACKET_HELPER.send2S(new BlockNetDataSlotChangePacket(0, 256));
        if(mouseX >= (width-256)>>1 && mouseX < ((width-256)>>1)+256 && mouseY >= height-20 && mouseY < height-11)
            Services.PACKET_HELPER.send2S(new BlockNetDataSlotChangePacket(0, (int) (mouseX - ((width-256)>>1))));

        if (minecraft == null || minecraft.level == null /*|| bni == null*/) return false;

        // Ricrea la stessa matrice che usi per renderizzare
        PoseStack pose = new PoseStack();
        pose.translate((float) width/2, (float) height /2, 0);
        pose.mulPose(Axis.XP.rotationDegrees(angle.x));
        pose.translate(pos.x, pos.y, 0);
        pose.mulPose(Axis.YP.rotationDegrees(angle.y));
        pose.scale(scale, -scale, scale);
        pose.translate(-0.5, 0, -0.5);
        if(origin != null) pose.translate(-origin.getX(), -origin.getY(), -origin.getZ());

        Matrix4f matrix = pose.last().pose();

        BlockPos clicked = null;
        double minDistance = 9999;

        for (BlockPos blockPos : validPositions) {
            // Proietta il centro del blocco in 2D
            Vector4f screenPos = projectToScreen(new Vector3f(blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f), matrix);

            // Calcola distanza 2D dal mouse
            double dx = screenPos.x() - mouseX;
            double dy = screenPos.y() - mouseY;
            double distSq = dx * dx + dy * dy;

            if (distSq < 100) { // threshold: 10 pixel (puoi cambiarlo)
                if (distSq < minDistance) {
                    clicked = blockPos;
                    minDistance = distSq;
                }
            }
        }

        if (clicked != null) {
            if(!Screen.hasControlDown()) selected.clear();
            selected.add(clicked);
            return true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if(mouseX >= (width-256)>>1 && mouseX < ((width-256)>>1)+256 && mouseY >= height-20 && mouseY < height-11) return false;
        if(button == 1) pos = pos.add(new Vec2((float) dragX, (float) dragY));
        else if(button == 0) angle = new Vec2((float) (Mth.clamp(angle.x + dragY, -90, 90)), (float) (angle.y + dragX));
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    public void update(CompoundTag tag) {
        project.loadAdditional(tag.getCompound("ProjectData"));
        origin = new BlockPos(tag.getInt("x"), tag.getInt("y"), tag.getInt("z"));
        validPositions.clear();
        for(Tag iat : tag.getList("NetworkPoles", Tag.TAG_INT_ARRAY)) {
            final var asList = ((IntArrayTag)iat);
            validPositions.add(new BlockPos(
                    asList.getFirst().getAsInt(),
                    asList.get(1).getAsInt(),
                    asList.get(2).getAsInt()
            ));
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private Vector4f projectToScreen(Vector3f pos, Matrix4f transform) {
        return transform.transform(new Vector4f(pos, 1.0f));
    }

    @Override
    public BlockNetMenu getMenu() {
        return menu;
    }

    @Override
    public void onClose() {
        if(this.minecraft == null || this.minecraft.player == null) return;
        this.minecraft.player.closeContainer();
        super.onClose();
    }
}
