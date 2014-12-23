package com.github.MitI_7;

import com.intellij.openapi.editor.impl.EditorComponentImpl;
import javax.swing.border.Border;
import java.awt.*;


public class WallPaper implements Border {
    private EditorSetting editorSetting;
    private Image scaledImage;

    public WallPaper(Image image, EditorSetting editorSetting) {
        this.editorSetting = editorSetting;

        // 表示サイズの計算
        int imageWidth = (int)(image.getWidth(null) * editorSetting.imageSize);
        int imageHeight = (int)(image.getHeight(null) * editorSetting.imageSize);

        if (imageWidth == 0 || imageHeight == 0) {
            this.scaledImage = null;
        }
        else {
            this.scaledImage = image.getScaledInstance(imageWidth, imageHeight, Image.SCALE_DEFAULT);
        }
    }

    public void paintBorder(Component component, Graphics graphics, int x, int y, int width, int height) {
        if (scaledImage == null) { return;}

        Graphics2D graphics2 = (Graphics2D) graphics;
        graphics2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.editorSetting.imageOpacity));

        Point p = calculate_position(component, scaledImage.getWidth(null), scaledImage.getHeight(null));
        graphics2.drawImage(scaledImage, p.x, p.y, component);
    }

    // 表示位置の計算
    private Point calculate_position(Component component, int imageWidth, int imageHeight) {
        Point p = new Point();

        Rectangle rect = ((EditorComponentImpl) component).getVisibleRect();
        int position_x = 0;
        switch (this.editorSetting.imageHorizonPositionNo) {
            // 右に表示
            case 0:
                position_x = rect.x;
                break;
            // 中央に表示
            case 1:
                position_x = rect.x + (rect.width - imageWidth) / 2;
                break;
            // 左に表示
            case 2:
                position_x = rect.x + rect.width - imageWidth;
                break;
        }
        int position_y = 0;
        switch (this.editorSetting.imageVerticalPositionNo) {
            // 上に表示
            case 0:
                position_y = rect.y;
                break;
            // 中央に表示
            case 1:
                position_y = rect.y + (rect.height - imageHeight) / 2;
                break;
            // 下に表示
            case 2:
                position_y = rect.y + rect.height - imageHeight;
                break;
        }

        p.setLocation(position_x, position_y);

        return p;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(0, 0, 0, 0);
    }

    public boolean isBorderOpaque() {
        return true;
    }
}
