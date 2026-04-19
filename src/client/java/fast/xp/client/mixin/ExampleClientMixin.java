package fast.xp.client.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ExperienceBottleItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class ExampleClientMixin {

	@Shadow private int rightClickDelay;

	private long lastUseTime = 0;

	@Inject(at = @At("HEAD"), method = "tick")
	private void onTick(CallbackInfo info) {
		Minecraft mc = (Minecraft) (Object) this;
		if (mc.player != null) {
			if (mc.player.getMainHandItem().getItem() instanceof ExperienceBottleItem ||
				mc.player.getOffhandItem().getItem() instanceof ExperienceBottleItem) {
				
				// Locked to 20 TPS (1 per tick) to prevent GrimAC PacketOrderN flags.
				this.rightClickDelay = 0;
			}
		}
	}
}