package com.supermartijn642.rechiseled;

import com.supermartijn642.core.CommonUtils;
import com.supermartijn642.core.item.BaseItem;
import com.supermartijn642.core.item.ItemProperties;
import com.supermartijn642.rechiseled.screen.ChiselContainer;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * Created 25/12/2021 by SuperMartijn642
 */
public class ChiselItem extends BaseItem {
    public static final DataComponentType<ItemStack> CHISEL_STACK = register("chisel_stack",
            (builder) -> builder.persistent(ItemStack.CODEC).networkSynchronized(ItemStack.STREAM_CODEC).cacheEncoding());

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
        Optional<TypedDataComponent<?>> stack = chisel.getComponents().filter(dct -> dct == CHISEL_STACK).stream().findFirst();
        if (stack.isPresent()) {
            return (ItemStack)stack.get().value();
        } else {
            return ItemStack.EMPTY;
        }
    }

    public static void setStoredStack(ItemStack chisel, ItemStack stack){
        DataComponentMap dataComponents = chisel.getComponents();
        if(stack == null || stack.isEmpty()) {
            chisel.applyComponents(dataComponents.filter(dct -> dct != CHISEL_STACK));
        } else {
            chisel.applyComponents(DataComponentMap.composite(dataComponents, DataComponentMap.builder().set(CHISEL_STACK, stack).build()));
        }
    }

    private static <T> DataComponentType<T> register(String string, UnaryOperator<DataComponentType.Builder<T>> unaryOperator) {
        DataComponentType<T> dataComponentType = ((DataComponentType.Builder)unaryOperator.apply(DataComponentType.builder())).build();
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, string, dataComponentType);
    }
}
