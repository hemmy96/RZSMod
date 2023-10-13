package com.menitimu.rzs.mixin;

import com.menitimu.rzs.config.RZSConfig;
import com.menitimu.rzs.stuff.BetterVisibility;
import com.menitimu.rzs.util.RandomStuff;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LayerCustomHead.class)
public class LayerCustomHeadMixin {
    @Inject(method = "doRenderLayer", at = @At("HEAD"), cancellable = true)
    public void onRenderLayer(EntityLivingBase entitylivingbaseIn, float f, float g, float partialTicks, float h, float i, float j, float scale, CallbackInfo ci){
        if(entitylivingbaseIn instanceof EntityZombie && !entitylivingbaseIn.isChild() && !entitylivingbaseIn.isInvisible() && RandomStuff.calcDistance(entitylivingbaseIn) < RZSConfig.enemyVisDistance && !BetterVisibility.shouldRenderEnemy)
            ci.cancel();
    }
}
