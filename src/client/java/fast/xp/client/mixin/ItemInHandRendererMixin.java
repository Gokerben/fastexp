package fast.xp.client.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.item.ExperienceBottleItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

	@Shadow private ItemStack mainHandItem;
	@Shadow private ItemStack offHandItem;

	@Inject(at = @At("HEAD"), method = "tick")
	private void onTick(CallbackInfo info) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.player != null) {
			ItemStack currentMain = mc.player.getMainHandItem();
			// If we are holding an exp bottle, pretend the item hasn't changed to stop the bobbing animation.
			if (currentMain.getItem() instanceof ExperienceBottleItem && this.mainHandItem.getItem() instanceof ExperienceBottleItem) {
				this.mainHandItem = currentMain;
			}

			ItemStack currentOff = mc.player.getOffhandItem();
			if (currentOff.getItem() instanceof ExperienceBottleItem && this.offHandItem.getItem() instanceof ExperienceBottleItem) {
				this.offHandItem = currentOff;
			}
		}
	}
}
