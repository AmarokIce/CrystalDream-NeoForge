package club.someoneice.crystaldream.init;

import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ImageHelper {
    public static BufferedImage cache;

    public static BufferedImage getImage() {
        if (cache != null) {
            return cache;
        }

        var inputStream = MissingTextureAtlasSprite.class.getClassLoader().getResourceAsStream("assets/crystaldream/missingno.png");
        if (inputStream == null) {
            return null;
        }

        try {
            BufferedImage image = ImageIO.read(inputStream);
            inputStream.close();
            cache = image;
        } catch (Exception ignored) {}
        return cache;
    }
}
