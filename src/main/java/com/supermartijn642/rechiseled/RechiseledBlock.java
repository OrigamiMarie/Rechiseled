package com.supermartijn642.rechiseled;

import com.supermartijn642.core.TextComponents;
import com.supermartijn642.core.block.BaseBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;

import javax.annotation.Nullable;
import java.util.function.Consumer;

/**
 * Created 22/12/2021 by SuperMartijn642
 */
public class RechiseledBlock extends BaseBlock {

    public final boolean connecting;

    public RechiseledBlock(boolean connecting, Properties properties){
        super(false, properties);
        this.connecting = connecting;
    }

    @Override
    protected void appendItemInformation(ItemStack stack, @Nullable BlockGetter level, Consumer<Component> info, boolean advanced){
        if(this.connecting)
            info.accept(TextComponents.translation("rechiseled.tooltip.connecting").color(ChatFormatting.GRAY).get());
    }
}
