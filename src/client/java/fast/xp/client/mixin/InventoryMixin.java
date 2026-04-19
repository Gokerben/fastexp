package fast.xp.client.mixin;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ExperienceBottleItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Inventory.class)
public class InventoryMixin {
    @Inject(method = "setItem", at = @At("HEAD"), cancellable = true)
    private void onSetItem(int slot, ItemStack stack, CallbackInfo ci) {
        Inventory inv = (Inventory) (Object) this;
        ItemStack existing = inv.getItem(slot);
        
        // Prevent the UI hotbar from doing the "pop" animation when throwing exp bottles.
        // We do this by updating the count of the existing stack instead of replacing it with a new instance.
        if (existing != null && stack != null && !existing.isEmpty() && !stack.isEmpty() 
            && existing.getItem() instanceof ExperienceBottleItem 
            && stack.getItem() instanceof ExperienceBottleItem) {
            
            existing.setCount(stack.getCount());
            ci.cancel();
        }
    }
}
