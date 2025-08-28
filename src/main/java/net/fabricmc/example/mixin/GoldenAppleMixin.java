package net.fabricmc.example.mixin;

import btw.block.blocks.BedBlockBase;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemAppleGold.class)
public abstract class GoldenAppleMixin extends ItemFood  {

    public GoldenAppleMixin(int par1, int par2, float par3, boolean par4) {
        super(par1, par2, par3, par4);
    }

    @Override
    public void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
        if (!world.isRemote) {
            ChatMessageComponent chatMessageComponent = new ChatMessageComponent();
            chatMessageComponent.setColor(EnumChatFormatting.DARK_RED);
            chatMessageComponent.addText("<Better Than Wolves> Too bad, even the Notch apple can't dispel your sins...");
            MinecraftServer.getServer().getConfigurationManager().sendChatMsg(chatMessageComponent);
            world.playSoundEffect(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, "ambient.weather.thunder", 10000.0F, 0.8F + entityPlayer.rand.nextFloat() * 0.2F);
            world.playSoundEffect(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, "random.explode", 2.0F, 0.5F + entityPlayer.rand.nextFloat() * 0.2F);
        }

        if (itemStack.getItemDamage() > 0) {
            if (!world.isRemote) {
                entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.id, 600, 4));
                entityPlayer.addPotionEffect(new PotionEffect(Potion.resistance.id, 6000, 0));
                entityPlayer.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 6000, 0));
            }
        } else {
            super.onFoodEaten(itemStack, world, entityPlayer);
        }

    }


}