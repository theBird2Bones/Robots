package gui.visualizers;

import objects.tiles.DirtTile;
import objects.tiles.PrecipiceTile;
import objects.tiles.StoneTile;
import objects.tiles.Tile;
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

    public void repaintAll(Graphics2D g2d) {
        updateBackground();
        paint(g2d);
    }

    public void updateBackground() {
        createBackground();
    }

    public void paint(Graphics2D g2d) {
        BufferedImage backgroundImage;

        synchronized (this) {
            backgroundImage = copyBufferedImage(this.backgroundImage);
        }
        var bgG2d = backgroundImage.createGraphics();
        paintPlayer(bgG2d);

        paintOnPanel(g2d, backgroundImage);
    }

    private static BufferedImage copyBufferedImage(BufferedImage bi) {
        var colorModel = bi.getColorModel();
        return new BufferedImage(
                colorModel,
                bi.copyData(null),
                colorModel.isAlphaPremultiplied(),
                null
        );
    }

    private void paintPlayer(Graphics2D g2d) {
        var point = PointExtends.mult(gamePanel.getPlayer().getPosition(), tileWidth, tileHeight);
        g2d.setColor(Color.red);
        g2d.drawOval(point.x, point.y, tileWidth / 2, tileHeight / 2);
    }

    private void paintOnPanel(Graphics2D g2d, BufferedImage backgroundImage) {
        var arenaSize = toPhysics(gamePanel.getMapSize());
        var panelSize = gamePanel.getSize();
        var imagePosition = new Point(
                panelSize.width / 2 - arenaSize.width / 2,
                panelSize.height / 2 - arenaSize.height / 2
        );

        g2d.drawImage(
                backgroundImage, imagePosition.x, imagePosition.y,
                arenaSize.width, arenaSize.height,
                gamePanel
        );
    }

    private Dimension toPhysics(Dimension logical){
        return new Dimension(logical.width * tileWidth, logical.height * tileHeight);
    }

    private void createBackground() {
        var arenaSize = gamePanel.getMapSize();
        var pointToRectangle = gamePanel.getPointToRectangle();
        Graphics2D g2d;

        synchronized (this) {
            backgroundImage = new BufferedImage(
                    arenaSize.width * tileWidth,
                    arenaSize.height * tileHeight,
                    BufferedImage.TYPE_INT_RGB
            );
            g2d = backgroundImage.createGraphics();
        }


        for (int x = 0; x < arenaSize.width; x++) {
            for (int y = 0; y < arenaSize.height; y++) {
                var rec = pointToRectangle.get(new Point(x, y));
                var color = chooseColor(gamePanel.getMap()[x][y]);

                drawFillRect(g2d, color, rec);
            }
        }
    }

    private Color chooseColor(Tile tile) {
        return switch (tile) {
            case StoneTile e -> Color.darkGray;
            case DirtTile e -> new Color(180, 87, 43);
            case PrecipiceTile e -> Color.black;
            default -> Color.gray;
        };
    }

    private void drawFillRect(Graphics2D g2d, Color color, Rectangle rec) {
        g2d.setColor(color);
        g2d.fillRect(rec.x, rec.y, rec.width, rec.height);
    }
}
