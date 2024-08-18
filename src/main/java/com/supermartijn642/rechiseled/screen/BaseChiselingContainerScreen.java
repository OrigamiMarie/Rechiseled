package com.supermartijn642.rechiseled.screen;

import com.supermartijn642.core.ClientUtils;
import com.supermartijn642.core.gui.ScreenUtils;
import com.supermartijn642.core.gui.widget.BaseContainerWidget;
import com.supermartijn642.core.gui.widget.WidgetRenderContext;
import com.supermartijn642.rechiseled.Rechiseled;
import com.supermartijn642.rechiseled.chiseling.ChiselingEntry;
import com.supermartijn642.rechiseled.chiseling.ChiselingRecipe;
import com.supermartijn642.rechiseled.packet.PacketChiselAll;
import com.supermartijn642.rechiseled.packet.PacketSelectEntry;
import com.supermartijn642.rechiseled.packet.PacketToggleConnecting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

/**
 * Created 23/12/2021 by SuperMartijn642
 */
public class BaseChiselingContainerScreen<T extends BaseChiselingContainer> extends BaseContainerWidget<T> {

    private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath("rechiseled", "textures/screen/chiseling_background.png");

    /**
     * 0 - 1 block
     * 1 - row of 3 blocks
     * 2 - 3x3 blocks
     */
    public static int previewMode = 0;

    private final Component title;
    private ChiselAllWidget chiselAllWidget;

    public BaseChiselingContainerScreen(Component title){
        super(0, 0, 222, 226);
        this.title = title;
    }

    @Override
    protected void addWidgets(){
        for(int row = 0; row < 5; row++){
            for(int column = 0; column < 5; column++){
                int index = row * 5 + column;
                int x = 9 + 20 * column;
                int y = 17 + 22 * row;
                this.addWidget(new EntryButtonWidget(x, y, 20, 22,
                    () -> this.getEntry(index),
                    () -> this.container.currentEntry,
                    () -> this.selectEntry(index),
                    () -> this.container.connecting));
            }
        }

        this.addWidget(new EntryPreviewWidget(117, 17, 68, 69, () -> {
            ChiselingEntry entry = this.container.currentEntry;
            if(entry == null)
                return null;
            return (this.container.connecting && entry.hasConnectingItem()) || !entry.hasRegularItem() ? entry.getConnectingItem() : entry.getRegularItem();
        }, () -> previewMode));
        Supplier<Boolean> enablePreviewButtons = () -> {
            ChiselingEntry entry = this.container.currentEntry;
            if(entry == null)
                return false;
            Item currentItem = (this.container.connecting && entry.hasConnectingItem()) || !entry.hasRegularItem() ? entry.getConnectingItem() : entry.getRegularItem();
            return currentItem instanceof BlockItem;
        };
        this.addWidget(new PreviewModeButtonWidget(193, 18, 19, 21, 2, () -> previewMode, enablePreviewButtons, () -> previewMode = 2));
        this.addWidget(new PreviewModeButtonWidget(193, 41, 19, 21, 1, () -> previewMode, enablePreviewButtons, () -> previewMode = 1));
        this.addWidget(new PreviewModeButtonWidget(193, 64, 19, 21, 0, () -> previewMode, enablePreviewButtons, () -> previewMode = 0));
        this.addWidget(new ConnectingToggleWidget(193, 99, 19, 21, () -> this.container.connecting, () -> this.container.currentEntry, this::toggleConnecting));
        this.chiselAllWidget = this.addWidget(new ChiselAllWidget(127, 99, 19, 21, () -> this.container.currentEntry, this::chiselAll));
    }

    @Override
    public Component getNarrationMessage(){
        return this.title;
    }

    @Override
    public void renderBackground(WidgetRenderContext context, int mouseX, int mouseY){
        ScreenUtils.bindTexture(BACKGROUND);
        ScreenUtils.drawTexture(context.poseStack(), 0, 0, this.width, this.height);
        super.renderBackground(context, mouseX, mouseY);
    }

    @Override
    public void renderForeground(WidgetRenderContext context, int mouseX, int mouseY){
        // Render chisel all slot overlays
        if(this.container.currentRecipe != null && this.chiselAllWidget != null && this.chiselAllWidget.isFocused()){
            for(int index = 1; index < this.container.slots.size(); index++){
                Slot slot = this.container.getSlot(index);
                ItemStack stack = slot.getItem();

                for(ChiselingEntry entry : this.container.currentRecipe.getEntries()){
                    if(stack.getComponentsPatch().isEmpty()
                        && ((entry.hasConnectingItem() && stack.getItem() == entry.getConnectingItem())
                        || (entry.hasRegularItem() && stack.getItem() == entry.getRegularItem()))){
                        ScreenUtils.fillRect(context.poseStack(), slot.x, slot.y, 16, 16, 0, 20, 100, 0.5f);
                    }
                }
            }
        }

        super.renderForeground(context, mouseX, mouseY);
        ScreenUtils.drawString(context.poseStack(), ClientUtils.getPlayer().getInventory().getName(), 31, 133);
    }

    private ChiselingEntry getEntry(int index){
        ChiselingRecipe recipe = this.container.currentRecipe;
        if(recipe == null)
            return null;
        return index >= 0 && index < recipe.getEntries().size() ? recipe.getEntries().get(index) : null;
    }

    private void selectEntry(int index){
        Rechiseled.CHANNEL.sendToServer(new PacketSelectEntry(index));
    }

    private void toggleConnecting(){
        Rechiseled.CHANNEL.sendToServer(new PacketToggleConnecting());
    }

    private void chiselAll(){
        Rechiseled.CHANNEL.sendToServer(new PacketChiselAll());
    }
}
