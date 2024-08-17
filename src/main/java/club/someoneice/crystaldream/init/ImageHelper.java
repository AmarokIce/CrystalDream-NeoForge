package club.someoneice.crystaldream.init;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import org.apache.commons.io.output.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageHelper {
    static NativeImage cache;

    public static NativeImage getImage(int width, int height) {
        if (cache != null) {
            return cache;
        }

        var inputStream = MissingTextureAtlasSprite.class.getClassLoader().getResourceAsStream("assets/crystaldream/textures/missingno.png");
        if (inputStream == null) {
            return null;
        }

        try {
            BufferedImage image = ImageIO.read(inputStream);

            BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            newImg.getGraphics().drawImage(image.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(newImg, "png", output);
            byte[] bytes = output.toByteArray();

            output.close();
            inputStream.close();

            cache = NativeImage.read(bytes);
        } catch (Exception ignored) {}
        return cache;
    }
}
