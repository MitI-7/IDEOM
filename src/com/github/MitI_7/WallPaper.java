package com.github.MitI_7;

import com.intellij.openapi.editor.impl.EditorComponentImpl;
import javax.swing.border.Border;
import java.awt.*;

public class WallPaper implements Border {
    private Image image;
    private Setting setting;

    public WallPaper(Image image, Setting setting) {
        this.image = image;
        this.setting = setting;
    }

    public void paintBorder(Component component, Graphics graphics, int x, int y, int width, int height) {
        Graphics2D graphics2 = (Graphics2D) graphics;
        graphics2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.setting.imageOpacity));

        // 表示位置の計算
        Rectangle rect = ((EditorComponentImpl) component).getVisibleRect();
        int position_x = 0;
        switch (this.setting.imageHorizonPositionNo) {
            // 右に表示
            case 0:
                position_x = rect.x;
                break;
            // 中央に表示
            case 1:
                position_x = rect.x + (rect.width - image.getWidth(null)) / 2;
                break;
            // 左に表示
            case 2:
                position_x = rect.x + rect.width - image.getWidth(null);
                break;
        }
        int position_y = 0;
        switch (this.setting.imageVerticalPositionNo) {
            // 上に表示
            case 0:
                position_y = rect.y;
                break;
            // 中央に表示
            case 1:
                position_y = rect.y + (rect.height - image.getHeight(null)) / 2;
                break;
            // 下に表示
            case 2:
                position_y = rect.y + rect.height - image.getHeight(null);
                break;
        }
        graphics2.drawImage(image, Math.max(0, position_x), Math.max(0, position_y), component);
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(0, 0, 0, 0);
    }

    public boolean isBorderOpaque() {
        return true;
    }
}
