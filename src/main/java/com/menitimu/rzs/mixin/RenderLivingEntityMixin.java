package com.menitimu.rzs.mixin;

import com.menitimu.rzs.config.RZSConfig;
import com.menitimu.rzs.stuff.BetterVisibility;
import com.menitimu.rzs.util.RandomStuff;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RendererLivingEntity.class)
public class RenderLivingEntityMixin <T extends EntityLivingBase>{
    @Inject(method = "setDoRenderBrightness", at = @At("HEAD"), cancellable = true)
    public void setDoRenderBrightnessMixin(T entityLivingBaseIn, float partialTicks, CallbackInfoReturnable<Boolean> cir){
        if(entityLivingBaseIn instanceof EntityZombie && RandomStuff.calcDistance(entityLivingBaseIn) < RZSConfig.enemyVisDistance && !BetterVisibility.shouldRenderEnemy)
            cir.setReturnValue(false);
    }
}
