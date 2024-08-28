package club.someoneice.crystaldream.mixin;

import club.someoneice.crystaldream.init.ImageHelper;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.image.BufferedImage;

/**
 * tO mY dEaR BeclR0Ck
 * hEre ArE U'eR LoVeLy "BlAcK wItH pUrPlE" tExTuRe WiThOuT mIsSiNgNo!
 * MuCh ThAnKs U mY fRiEnD!
 */
@Mixin(MissingTextureAtlasSprite.class)
public abstract class MissingTextureAtlasSpriteMixin {

    @Inject(method = "generateMissingImage", at = @At("HEAD"), cancellable = true)
    private static void generatorMissingNo(int width, int height, CallbackInfoReturnable<NativeImage> cir) {
        BufferedImage buffered = ImageHelper.getImage();
        if (buffered == null) return;
        NativeImage nativeimage = new NativeImage(width, height, false);
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                nativeimage.setPixelRGBA(x, y, buffered.getRGB(x, y));
            }
        }

        cir.setReturnValue(nativeimage);
    }
}
