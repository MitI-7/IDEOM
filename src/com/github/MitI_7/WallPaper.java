package com.github.MitI_7;

import javax.swing.border.Border;
import java.awt.*;

public class WallPaper implements Border {
    private Image image;
    private float imageOpacity;

    public WallPaper(Image image, float imageOpacity) {
        this.image = image;
        this.imageOpacity = imageOpacity;
    }

    public void paintBorder(Component component, Graphics graphics, int x, int y, int width, int height) {
        Graphics2D graphics2 = (Graphics2D) graphics;
        graphics2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.imageOpacity));

        graphics2.drawImage(image, -1 * component.getX(), -1 * component.getY(), component);
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(0, 0, 0, 0);
    }

    public boolean isBorderOpaque() {
        return true;
    }
}
