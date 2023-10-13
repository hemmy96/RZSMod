package com.menitimu.rzs.mixin;

import com.menitimu.rzs.stuff.RoundStuff;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.network.play.server.S0FPacketSpawnMob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class NetHandlerPlayClientMixin {
    @Inject(method = "handleSpawnMob", at = @At("RETURN"))
    public void onSpawnMob(S0FPacketSpawnMob packetIn, CallbackInfo ci){
        if(packetIn.getEntityType() == 61 && RoundStuff.isBlaze) {
            EntityBlaze blaze = (EntityBlaze) Minecraft.getMinecraft().theWorld.getEntityByID(packetIn.getEntityID());
            try {
                blaze.getDataWatcher().addObject(12, (byte)0);
            }catch (IllegalArgumentException ignored){
            }
        }
    }
}
