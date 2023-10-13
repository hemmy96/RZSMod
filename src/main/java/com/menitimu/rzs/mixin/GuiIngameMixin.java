package com.menitimu.rzs.mixin;

import com.menitimu.rzs.config.RZSConfig;
import com.menitimu.rzs.stuff.RELODIN;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public class GuiIngameMixin {
    @Inject(method = "renderTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;tryBlendFuncSeparate(IIII)V", shift = At.Shift.AFTER))
    public void renderIcon(ScaledResolution sr, float partialTicks, CallbackInfo ci){
        RELODIN.onRenderSlot();
    }
}
