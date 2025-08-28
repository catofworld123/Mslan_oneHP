package net.fabricmc.example.mixin;

import btw.block.blocks.BedBlockBase;
import btw.block.blocks.BedrollBlock;
import net.minecraft.src.IconRegister;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BedrollBlock.class)
public abstract class BedrollMixin extends BedBlockBase {

    public BedrollMixin(int iBlockID) {
        super(iBlockID);
    }
    @Override
    public void registerIcons(IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon("bedroll");
    }
}
