package com.github.MitI_7;

import com.intellij.openapi.editor.impl.EditorComponentImpl;
import javax.swing.border.Border;
import java.awt.*;

public class WallPaper implements Border {
    private Image image;
    private float imageOpacity;
    private int imagePositionNo;

    public WallPaper(Image image, float imageOpacity, int imagePositionNo) {
        this.image = image;
        this.imageOpacity = imageOpacity;
        this.imagePositionNo = imagePositionNo;
    }

    public void paintBorder(Component component, Graphics graphics, int x, int y, int width, int height) {
        Graphics2D graphics2 = (Graphics2D) graphics;
        graphics2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.imageOpacity));

        // 表示位置の計算
        Rectangle rect = ((EditorComponentImpl) component).getVisibleRect();
        int position_x = 0;
        // 右に表示
        if (imagePositionNo == 0) {
            position_x = rect.x;
        }
        // 中央に表示
        if (imagePositionNo == 1) {
            position_x = rect.x + (rect.width - image.getWidth(null)) / 2;
        }
        // 左に表示
        else if (imagePositionNo == 2) {
            position_x = rect.x + rect.width - image.getWidth(null);
        }

        graphics2.drawImage(image, Math.max(0, position_x), rect.y, component);
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(0, 0, 0, 0);
    }

    public boolean isBorderOpaque() {
        return true;
    }
}
