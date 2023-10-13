package com.menitimu.rzs.mixin;

import com.menitimu.rzs.config.RZSConfig;
import com.menitimu.rzs.stuff.BetterVisibility;
import com.menitimu.rzs.util.RandomStuff;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelBiped.class)
public class ModelBipedMixin {
    @Inject(method = "render", at = @At("HEAD"))
    public void renderMixin(Entity entityIn, float f, float g, float h, float i, float j, float scale, CallbackInfo ci){
        if(entityIn instanceof EntityZombie && !((EntityZombie) entityIn).isChild()) {
            if (RandomStuff.calcDistance(entityIn) < RZSConfig.enemyVisDistance && !BetterVisibility.shouldRenderEnemy) {
                GlStateManager.color(1.0F, 1.0F, 1.0F, RZSConfig.enemyOpacity);
                GlStateManager.depthMask(false);
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(770, 771);
                GlStateManager.alphaFunc(516, 0.00001F);
            }
        }
    }
}
