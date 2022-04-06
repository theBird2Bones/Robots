package gui.visualizers;

import objects.tiles.DirtTile;
import objects.tiles.PrecipiceTile;
import objects.tiles.StoneTile;
import utility.PointExtends;
import utility.consts.GlobalConst;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ArenaPainter {
    private final int tileWidth = GlobalConst.TILE_SIZE;
    private final int tileHeight = GlobalConst.TILE_SIZE;

    private GamePanel gamePanel;
    private BufferedImage backgroundImage;

    public ArenaPainter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        createBackground();
    }

    public void repaintAll(Graphics2D g2d){
        updateBackground();
        paint(g2d);
    }

    public void updateBackground(){
        createBackground();
    }

    public void paint(Graphics2D g2d){
        var arenaSize = gamePanel.getSize();
        BufferedImage backgroundImage;

        synchronized (this) {
            backgroundImage = copyBufferedImage(this.backgroundImage);
        }
        var bgG2d = backgroundImage.createGraphics();
        paintPlayer(bgG2d);

        g2d.drawImage(
                backgroundImage, 0, 0,
                arenaSize.width * tileWidth, arenaSize.height * tileHeight,
                gamePanel
        );
    }

    private static BufferedImage copyBufferedImage(BufferedImage bi){
        var colorModel = bi.getColorModel();
        return new BufferedImage(
                colorModel,
                bi.copyData(null),
                colorModel.isAlphaPremultiplied(),
                null
        );
    }

    private void paintPlayer(Graphics2D g2d){
        var point = PointExtends.mult(gamePanel.getPlayer().getPosition(), tileWidth, tileHeight);
        g2d.setColor(Color.red);
        g2d.drawOval(point.x, point.y, tileWidth / 2, tileHeight / 2);
    }

    private void createBackground() {
        var arenaSize = gamePanel.getSize();
        var pointToRectangle = gamePanel.getPointToRectangle();

        synchronized (this){
            backgroundImage = new BufferedImage(
                    arenaSize.width * tileWidth,
                    arenaSize.height * tileHeight,
                    BufferedImage.TYPE_INT_RGB
            );
        }
        var g2d = backgroundImage.createGraphics();

        for (int x = 0; x < arenaSize.width; x++) {
            for (int y = 0; y < arenaSize.height; y++) {
                var rec = pointToRectangle.get(new Point(x, y));
                g2d.setColor(Color.black);
                g2d.drawRect(rec.x, rec.y, rec.width, rec.height);

                var color = switch (gamePanel.getMap()[x][y]) {
                    case StoneTile e -> Color.darkGray;
                    case DirtTile e -> new Color(180, 87, 43);
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
