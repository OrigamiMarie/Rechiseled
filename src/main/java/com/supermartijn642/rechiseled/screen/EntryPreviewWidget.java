package com.supermartijn642.rechiseled.screen;

import com.supermartijn642.core.gui.widget.BaseWidget;
import com.supermartijn642.core.gui.widget.WidgetRenderContext;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

/**
 * Created 25/12/2021 by SuperMartijn642
 */
public class EntryPreviewWidget extends BaseWidget {

    private static final int ROTATION_TIME = 10000;

    private final Supplier<Item> item;
    private final Supplier<Integer> previewMode;

    private float yaw = 0.35f, pitch = 30;
    private long lastRotationTime;
    private boolean dragging = false;
    private int mouseStartX, mouseStartY;

    public EntryPreviewWidget(int x, int y, int width, int height,
                              Supplier<Item> item,
                              Supplier<Integer> previewMode){
        super(x, y, width, height);
        this.item = item;
        this.previewMode = previewMode;
        this.lastRotationTime = System.currentTimeMillis();
    }

    @Override
    protected void addWidgets(){
        this.addWidget(new ToggleRotationButton(this.x, this.y, 11, 11));
        super.addWidgets();
    }

    @Override
    public Component getNarrationMessage(){
        return null;
    }

    @Override
    public void render(WidgetRenderContext context, int mouseX, int mouseY){
        long now = System.currentTimeMillis();

        Item item = this.item.get();
        int previewMode = this.previewMode.get();
        if(item != null && previewMode >= 0 && previewMode <= 2){
            // Update the rotation
            if(this.dragging){
                this.yaw += (mouseX - this.mouseStartX) / 100d * 360;
                this.pitch += (mouseY - this.mouseStartY) / 100d * 360;
                this.mouseStartX = mouseX;
                this.mouseStartY = mouseY;
            }else if(ToggleRotationButton.rotate)
                this.yaw += (double)(now - this.lastRotationTime) / ROTATION_TIME * 360;

            // Render the item or block
            if(item instanceof BlockItem){
                // Render block
                Block block = ((BlockItem)item).getBlock();
                BlockCapture capture;
                if(previewMode == 0)
                    capture = new BlockCapture(block);
                else if(previewMode == 1){
                    capture = new BlockCapture(block);
                    capture.putBlock(new BlockPos(-1, 0, 0), block);
                    capture.putBlock(new BlockPos(1, 0, 0), block);
                }else{
                    capture = new BlockCapture();
                    for(int i = 0; i < 9; i++)
                        capture.putBlock(new BlockPos(i / 3 - 1, i % 3 - 1, 0), block);
                }
                ScreenBlockRenderer.drawBlock(context.poseStack(), capture, this.x + this.width / 2d, this.y + this.height / 2d, this.width, this.yaw, this.pitch, true);
            }else{
                // Render item
                ScreenItemRender.drawItem(context.poseStack(), item, this.x + this.width / 2d, this.y + this.height / 2d, this.width, this.yaw, this.pitch, true);
            }
        }

        this.lastRotationTime = now;

        super.render(context, mouseX, mouseY);
    }

    @Override
    public boolean mousePressed(int mouseX, int mouseY, int button, boolean hasBeenHandled){
        hasBeenHandled |= super.mousePressed(mouseX, mouseY, button, hasBeenHandled);
        if(!hasBeenHandled && mouseX >= this.x && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height){
            this.dragging = true;
            this.mouseStartX = mouseX;
            this.mouseStartY = mouseY;
            return true;
        }
        return hasBeenHandled;
    }

    @Override
    public boolean mouseReleased(int mouseX, int mouseY, int button, boolean hasBeenHandled){
        this.dragging = false;
        return super.mouseReleased(mouseX, mouseY, button, hasBeenHandled);
    }
}
