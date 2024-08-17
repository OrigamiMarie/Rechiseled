package com.supermartijn642.rechiseled;

import com.supermartijn642.core.CommonUtils;
import com.supermartijn642.core.item.BaseItem;
import com.supermartijn642.core.item.ItemProperties;
import com.supermartijn642.rechiseled.screen.ChiselContainer;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Created 25/12/2021 by SuperMartijn642
 */
public class ChiselItem extends BaseItem {

    public static final DataComponentType<ItemStack> HELD_STACK = DataComponentType.<ItemStack>builder().persistent(ItemStack.OPTIONAL_CODEC).networkSynchronized(ItemStack.OPTIONAL_STREAM_CODEC).build();

    public ChiselItem(){
        super(ItemProperties.create().maxStackSize(1).group(Rechiseled.GROUP));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand){
        ItemStack stack = player.getItemInHand(hand);
        if(!world.isClientSide)
            CommonUtils.openContainer(new ChiselContainer(player, hand));
        return InteractionResultHolder.sidedSuccess(stack, world.isClientSide);
    }

    public static ItemStack getStoredStack(ItemStack chisel){
        return chisel.has(HELD_STACK) ? chisel.get(HELD_STACK) : ItemStack.EMPTY;
    }

    public static void setStoredStack(ItemStack chisel, ItemStack stack){
        if(stack == null || stack.isEmpty())
            chisel.remove(HELD_STACK);
        else
            chisel.set(HELD_STACK, stack);
    }
}
