package com.shell.webapplication.utils.app;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

@Component
public class DefaultProfileImageGenerator {

    public byte[] generateProfileImage(String userName) throws IOException {
        int width = 100;
        int height = 100;

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics2d = bufferedImage.createGraphics();
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2d.setColor(getRandomColor());
        graphics2d.fillRect(0, 0, width, height);
        graphics2d.setColor(Color.WHITE);
        graphics2d.setFont(new Font("Arial", Font.BOLD, 50));

        FontMetrics fontMetrics = graphics2d.getFontMetrics();
        String userNameFirstLetter = userName.substring(0, 1).toUpperCase();
        int x = (width - fontMetrics.stringWidth(userNameFirstLetter)) / 2;
        int y = ((height - fontMetrics.getHeight()) / 2) + fontMetrics.getAscent();

        graphics2d.drawString(userNameFirstLetter, x, y);
        graphics2d.dispose();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }

    public Color getRandomColor() {
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        return new Color(red, green, blue);
    }

}
