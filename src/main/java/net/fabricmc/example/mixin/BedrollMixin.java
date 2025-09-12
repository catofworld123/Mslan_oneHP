package net.fabricmc.example.mixin;

import btw.block.blocks.BedBlockBase;
import btw.block.blocks.BedrollBlock;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BedrollBlock.class)
public abstract class BedrollMixin extends BedBlockBase {

    public BedrollMixin(int iBlockID) {
        super(iBlockID);
    }
    @Redirect(method = "registerIcons", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/IconRegister;registerIcon(Ljava/lang/String;)Lnet/minecraft/src/Icon;"))
    public Icon registerIcons(IconRegister instance, String string) {
        this.blockIcon = instance.registerIcon("bedroll");
        return null;
    }

}
