package net.fabricmc.example.mixin;

import btw.block.blocks.BedBlockBase;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemAppleGold.class)
public abstract class GoldenAppleMixin extends ItemFood  {

    public GoldenAppleMixin(int par1, int par2, float par3, boolean par4) {
        super(par1, par2, par3, par4);
    }

    @Redirect(method = "onFoodEaten", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EntityPlayer;addPotionEffect(Lnet/minecraft/src/PotionEffect;)V",ordinal = 0))
    public void onFoodEaten(EntityPlayer entityPlayer, PotionEffect potionEffect) {
        World world = entityPlayer.getEntityWorld();
        if (!world.isRemote) {
            ChatMessageComponent chatMessageComponent = new ChatMessageComponent();
            chatMessageComponent.setColor(EnumChatFormatting.DARK_RED);
            chatMessageComponent.addText("<Better Than Wolves> Too bad, even the Notch apple can't dispel your sins...");
            MinecraftServer.getServer().getConfigurationManager().sendChatMsg(chatMessageComponent);
            world.playSoundEffect(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, "ambient.weather.thunder", 10000.0F, 0.8F + entityPlayer.rand.nextFloat() * 0.2F);
            world.playSoundEffect(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, "random.explode", 2.0F, 0.5F + entityPlayer.rand.nextFloat() * 0.2F);
        }
    }


}