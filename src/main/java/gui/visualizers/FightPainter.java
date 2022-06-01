package gui.visualizers;

import objects.entities.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FightPainter {
    private final int entitySize = 20;

    private final FightPanel fightPanel;
    private BufferedImage backgroundImage;

    public FightPainter(FightPanel fightPanel) {
        this.fightPanel = fightPanel;
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
        paintEnemies(bgG2d);

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
        var panelSize = fightPanel.getSize();
        var position = new Point(panelSize.width / 4, panelSize.height / 2);

        g2d.setColor(Color.red);
        g2d.fillOval(position.x, position.y, entitySize, entitySize);
        paintHealBar(g2d, fightPanel.getPlayer().getAcquire(), position);
    }

    private void paintEnemies(Graphics2D g2d){
        var enemies = fightPanel.getEnemies();
        var panelSize = fightPanel.getSize();

        int yShift = panelSize.height / (enemies.size() + 1);
        for(var i = 0; i < enemies.size(); i++){
            var position = new Point(panelSize.width / 4 * 3, yShift * (i + 1));

            g2d.setColor(Color.green);
            g2d.fillOval(position.x, position.y, entitySize, entitySize);
            paintHealBar(g2d, enemies.get(i).getAcquire(), position);
        }
    }

    private void paintHealBar(Graphics2D g2d, Entity entity, Point entPosition){
        var position = new Point(entPosition.x - 10, entPosition.y - 20);
        var size = new Dimension(entitySize * 3, 10);
        var health = (int)((entity.getHealth() / entity.getMAX_HEALTH()) * size.width);

        g2d.setColor(Color.red);
        g2d.fillRect(position.x, position.y, health, size.height);
        g2d.setColor(Color.white);
        g2d.drawRect(position.x, position.y, size.width, size.height);
    }

    private void paintOnPanel(Graphics2D g2d, BufferedImage backgroundImage) {
        var arenaSize = fightPanel.getSize();

        g2d.drawImage(
                backgroundImage, 0, 0,
                arenaSize.width, arenaSize.height,
                fightPanel
        );
    }

    private void createBackground() {
        var arenaSize = fightPanel.getSize();

        synchronized (this) {
            backgroundImage = new BufferedImage(
                    arenaSize.width,
                    arenaSize.height,
                    BufferedImage.TYPE_INT_RGB
            );
        }
    }
}
