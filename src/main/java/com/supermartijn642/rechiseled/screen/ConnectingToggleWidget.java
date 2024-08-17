package com.supermartijn642.rechiseled.screen;

import com.supermartijn642.core.TextComponents;
import com.supermartijn642.core.gui.ScreenUtils;
import com.supermartijn642.core.gui.widget.WidgetRenderContext;
import com.supermartijn642.core.gui.widget.premade.AbstractButtonWidget;
import com.supermartijn642.core.util.Holder;
import com.supermartijn642.rechiseled.chiseling.ChiselingEntry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created 26/12/2021 by SuperMartijn642
 */
public class ConnectingToggleWidget extends AbstractButtonWidget {

    private static final ResourceLocation GREY_BUTTONS = ResourceLocation.fromNamespaceAndPath("rechiseled", "textures/screen/grey_buttons.png");
    private static final ResourceLocation ICON_CONNECTED_ON = ResourceLocation.fromNamespaceAndPath("rechiseled", "textures/screen/icon_connecting_true.png");
    private static final ResourceLocation ICON_CONNECTED_OFF = ResourceLocation.fromNamespaceAndPath("rechiseled", "textures/screen/icon_connecting_false.png");

    private final Supplier<Boolean> connecting;
    private final Supplier<ChiselingEntry> currentEntry;

    public ConnectingToggleWidget(int x, int y, int width, int height, Supplier<Boolean> connecting, Supplier<ChiselingEntry> currentEntry, Runnable onPress){
        super(x, y, width, height, onPress);
        this.connecting = connecting;
        this.currentEntry = currentEntry;
    }

    @Override
    public Component getNarrationMessage(){
        Holder<Component> message = new Holder<>();
        this.getTooltips(message::set);
        return message.get();
    }

    @Override
    protected void getTooltips(Consumer<Component> tooltips){
        boolean connecting = this.connecting.get();
        ChiselingEntry currentEntry = this.currentEntry.get();
        if(currentEntry != null && (connecting ? currentEntry.hasRegularItem() : currentEntry.hasConnectingItem()))
            tooltips.accept(TextComponents.translation("rechiseled.chiseling.connecting", TextComponents.translation("rechiseled.chiseling.connecting." + (connecting ? "on" : "off")).color(ChatFormatting.GOLD).get()).get());
    }

    @Override
    public void render(WidgetRenderContext context, int mouseX, int mouseY){
        boolean connecting = this.connecting.get();
        ChiselingEntry currentEntry = this.currentEntry.get();
        boolean canSwitch = currentEntry != null && (connecting ? currentEntry.hasRegularItem() : currentEntry.hasConnectingItem());

        ScreenUtils.bindTexture(GREY_BUTTONS);
        ScreenUtils.drawTexture(context.poseStack(), this.x, this.y, this.width, this.height, 0, (canSwitch ? this.isFocused() ? 2 : 0 : 1) / 3f, 1, 1 / 3f);

        ScreenUtils.bindTexture(this.connecting.get() ? ICON_CONNECTED_ON : ICON_CONNECTED_OFF);
        ScreenUtils.drawTexture(context.poseStack(), this.x + 1, this.y + 2, this.width - 2, this.height - 4);
    }
}
