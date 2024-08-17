package com.supermartijn642.rechiseled.screen;

import com.supermartijn642.core.gui.ScreenUtils;
import com.supermartijn642.core.gui.widget.WidgetRenderContext;
import com.supermartijn642.core.gui.widget.premade.AbstractButtonWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * Created 20/04/2023 by SuperMartijn642
 */
public class ToggleRotationButton extends AbstractButtonWidget {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("rechiseled", "textures/screen/rotation_icon.png");

    public static boolean rotate = true;

    public ToggleRotationButton(int x, int y, int width, int height){
        super(x, y, width, height, () -> rotate = !rotate);
    }

    @Override
    public void render(WidgetRenderContext context, int mouseX, int mouseY){
        ScreenUtils.bindTexture(TEXTURE);
        ScreenUtils.drawTexture(context.poseStack(), this.x, this.y, this.width, this.height, rotate ? 0 : 0.5f, this.isFocused() ? 0.5f : 0, 0.5f, 0.5f);
        super.render(context, mouseX, mouseY);
    }

    @Override
    public Component getNarrationMessage(){
        return null;
    }
}
