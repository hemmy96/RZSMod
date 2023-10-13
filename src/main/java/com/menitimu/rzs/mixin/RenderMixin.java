package com.menitimu.rzs.mixin;

import com.menitimu.rzs.config.RZSConfig;
import com.menitimu.rzs.stuff.BetterVisibility;
import com.menitimu.rzs.util.RandomStuff;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Render.class)
public class RenderMixin {
    @Inject(method = "renderEntityOnFire", at = @At("HEAD"), cancellable = true)
    public void onRenderEntityOnFire(Entity entity, double x, double y, double z, float partialTicks, CallbackInfo ci){
        if(entity instanceof EntityPlayer && entity != Minecraft.getMinecraft().thePlayer && RandomStuff.calcDistance(entity) < RZSConfig.playerVisDistance && !RZSConfig.renderFire && !BetterVisibility.shouldRender)
            ci.cancel();
    }
    @Inject(method = "renderEntityOnFire", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V", shift = At.Shift.AFTER))
    public void renderEntityOnFire(Entity entity, double x, double y, double z, float partialTicks, CallbackInfo ci){
        if(entity instanceof EntityZombie && !((EntityZombie) entity).isChild() && RandomStuff.calcDistance(entity) < RZSConfig.enemyVisDistance && !BetterVisibility.shouldRenderEnemy){
            GlStateManager.color(1.0F, 1.0F, 1.0F, RZSConfig.enemyOpacity);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.alphaFunc(516, 0.00001F);
        }
    }
}
