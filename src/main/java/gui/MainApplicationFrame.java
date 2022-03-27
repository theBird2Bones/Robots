package gui;

import localizer.Localizer;
import log.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class MainApplicationFrame extends JFrame {
    private final JDesktopPane desktopPane = new JDesktopPane();

    public MainApplicationFrame(Localizer localizer) {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.

        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);

        setContentPane(desktopPane);

        GameWindow gameWindow = new GameWindow(localizer);
        gameWindow.setSize(400, 400);
        addWindow(gameWindow);
//        addWindow(createLogWindow(localizer));

        setJMenuBar(generateMenuBar(localizer));

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CloseFrame.closeApp(MainApplicationFrame.this, localizer);
            }
        });
    }

/*
    protected LogWindow createLogWindow(Localizer localizer) {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource(), localizer);

        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();

        Logger.debug("Протокол работает");
        return logWindow;
    }
*/

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    private JMenuBar generateMenuBar(Localizer localizer) {
        JMenuBar menuBar = new JMenuBar();

        var configMenu = new JMenu(localizer.getConfigLocalizer().getConfigMenuName());
        var exitButton = new JMenuItem(localizer.getConfigLocalizer().getExitButtonName());

        exitButton.addActionListener(l -> CloseFrame.closeApp(this, localizer));

        configMenu.add(createLookAndFeelMenu(localizer));
        configMenu.add(exitButton);

        menuBar.add(configMenu);
        menuBar.add(createTestMenu(localizer));

        return menuBar;
    }

    private JMenu createLookAndFeelMenu(Localizer localizer) {
        JMenu lookAndFeelMenu =
                new JMenu(localizer.getConfigLocalizer().getViewModeMenuLocalizer().getViewModeMenuName());
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        addSystemLookAndFeel(lookAndFeelMenu, localizer);
        addCrossplatformLookAndFeel(lookAndFeelMenu, localizer);

        return lookAndFeelMenu;
    }

    private JMenu createTestMenu(Localizer localizer) {
        JMenu testMenu = new JMenu(localizer.getTestMenuName());
        testMenu.setMnemonic(KeyEvent.VK_T);
        addLogMessageItem(testMenu);

        return testMenu;
    }

    private void addSystemLookAndFeel(JMenu lookAndFeelMenu, Localizer localizer) {
        JMenuItem systemLookAndFeel =
                new JMenuItem(
                        localizer.getConfigLocalizer().getViewModeMenuLocalizer().getSystemSchemeName(),
                        KeyEvent.VK_S);
        systemLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.invalidate();
        });

        lookAndFeelMenu.add(systemLookAndFeel);
    }

    private void addCrossplatformLookAndFeel(JMenu lookAndFeelMenu, Localizer localizer) {
        JMenuItem crossplatformLookAndFeel =
                new JMenuItem(
                        localizer.getConfigLocalizer().getViewModeMenuLocalizer().getUniversalSchemeName(),
                        KeyEvent.VK_S);
        crossplatformLookAndFeel.addActionListener((event) -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            this.invalidate();
        });

        lookAndFeelMenu.add(crossplatformLookAndFeel);
    }

    private void addLogMessageItem(JMenu testMenu) {
        JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
        addLogMessageItem.addActionListener((event) -> Logger.debug("Новая строка"));

        testMenu.add(addLogMessageItem);
    }

    private void setLookAndFeel(String className) {
        try {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (ClassNotFoundException
                | InstantiationException
                | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            System.out.println("crushed. setLookAndFeel");
        }
    }
}
