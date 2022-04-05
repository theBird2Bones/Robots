package gui.visualizers;

import objects.tiles.DirtTile;
import objects.tiles.PrecipiceTile;
import objects.tiles.StoneTile;
import utility.consts.GlobalConst;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Map;

public class ArenaPainter {
    private final int tileWidth = GlobalConst.TILE_SIZE;
    private final int tileHeight = GlobalConst.TILE_SIZE;

    private GamePanel gamePanel;
    private BufferedImage _backgroundImage;

    public ArenaPainter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        createBackground();
    }

    public void update(Graphics2D g2d){
        createBackground();
        paint(g2d);
    }

    public void updateBackground(){
        createBackground();
    }

    public void paint(Graphics2D g2d) {
        var arenaSize = gamePanel.getSize();
        g2d.drawImage(
                _backgroundImage, 0, 0,
                arenaSize.width * tileWidth, arenaSize.height * tileHeight,
                gamePanel
        );
    }

    private void createBackground() {
        var arenaSize = gamePanel.getSize();
        var pointToRectangle = gamePanel.getPointToRectangle();
        _backgroundImage = new BufferedImage(arenaSize.width * tileWidth, arenaSize.height * tileHeight, BufferedImage.TYPE_INT_RGB);
        var g2d = _backgroundImage.createGraphics();

        for (int x = 0; x < arenaSize.width; x++) {
            for (int y = 0; y < arenaSize.height; y++) {
                var rec = pointToRectangle.get(new Point(x, y));
                g2d.setColor(Color.black);
                g2d.drawRect(rec.x, rec.y, rec.width, rec.height);

                var color = switch (gamePanel.getMap()[x][y]) {
                    case StoneTile e -> Color.darkGray;
                    case DirtTile e -> Color.orange;
                    case PrecipiceTile e -> Color.black;
                    default -> Color.gray;
                };

                drawFillRect(g2d, color, rec);
            }
        }
    }

    private void drawFillRect(Graphics2D g2d, Color color, Rectangle rec) {
        g2d.setColor(color);
        g2d.fillRect(rec.x, rec.y, rec.width, rec.height);
    }
}
