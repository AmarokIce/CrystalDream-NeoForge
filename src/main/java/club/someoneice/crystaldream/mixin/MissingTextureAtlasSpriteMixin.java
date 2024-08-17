package club.someoneice.crystaldream.mixin;

import club.someoneice.crystaldream.init.ImageHelper;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.server.packs.resources.ResourceMetadata;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.io.IOException;

/**
 * tO mY dEaR BeclR0Ck
 * hEre ArE U'eR LoVeLy "BlAcK wItH pUrPlE" tExTuRe WiThOuT mIsSiNgNo!
 * MuCh ThAnKs U mY fRiEnD!
 */
@Mixin(MissingTextureAtlasSprite.class)
public abstract class MissingTextureAtlasSpriteMixin {
    @Shadow(remap = false) @Nullable private static DynamicTexture missingTexture;

    @Shadow(remap = false)
    private static NativeImage generateMissingImage(int width, int height) {
        return null;
    }

    @Shadow(remap = false) @Final private static ResourceMetadata SPRITE_METADATA;

    @Inject(method = "create", at = @At("HEAD"), remap = false, cancellable = true)
    private static void onCreateTexture(CallbackInfoReturnable<SpriteContents> cir) {
        NativeImage nativeimage = ImageHelper.getImage(16, 16);
        nativeimage = nativeimage == null ? generateMissingImage(16, 16) : nativeimage;
        var sc = new SpriteContents(MissingTextureAtlasSprite.getLocation(), new FrameSize(16, 16), nativeimage, SPRITE_METADATA);
        cir.setReturnValue(sc);
    }

    @Inject(method = "getTexture", at = @At("HEAD"), remap = false, cancellable = true)
    private static void onGetTexture(CallbackInfoReturnable<DynamicTexture> cir) throws IOException {
        if (missingTexture == null) {
            NativeImage nativeimage = ImageHelper.getImage(16, 16);
            nativeimage = nativeimage == null ? generateMissingImage(16, 16) : nativeimage;
            nativeimage.untrack();
            missingTexture = new DynamicTexture(nativeimage);
            Minecraft.getInstance().getTextureManager().register(MissingTextureAtlasSprite.getLocation(), missingTexture);
        }

        cir.setReturnValue(missingTexture);
    }
}
