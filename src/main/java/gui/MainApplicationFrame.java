package gui;

import localizer.LocalizationKey;
import log.Logger;
import utility.JMenuItemBundled;
import utility.ObservableLanguage;

import static localizer.LocalizationKey.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.Locale;
import java.util.ResourceBundle;


public class MainApplicationFrame extends JFrameWithCustomClose {
    private final JDesktopPane desktopPane = new JDesktopPane();
    private final LogWindow logWindow;

    public MainApplicationFrame(ResourceBundle bundle) {
        super(bundle);
        //Make the big window be indented 50 pixels from each edge
        //of the screen.

        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);

        setContentPane(desktopPane);

        logWindow = createLogWindow(bundle);

        addWindow(logWindow);
        addWindow(createGameWindow(bundle));

        setJMenuBar(generateMenuBar(bundle));

        logWindow.setVisible(false);
    }

    protected void addWindow(JInternalFrame frame) {
        desktopPane.add(frame);
        frame.setVisible(true);
    }

    protected LogWindow createLogWindow(ResourceBundle bundle) {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource(), bundle);

        logWindow.setLocation(10, 10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();

        Logger.debug("Протокол работает");
        return logWindow;
    }

    protected GameWindow createGameWindow(ResourceBundle bundle) {
        GameWindow gameWindow = new GameWindow(bundle, new Dimension(500, 500));

        gameWindow.setLocation(10, 10);
        gameWindow.setMinimumSize(gameWindow.getSize());

        return gameWindow;
    }

    private JMenuBar generateMenuBar(ResourceBundle bundle) {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(createConfigMenu(bundle));
        menuBar.add(createLogMenu(bundle));

        return menuBar;
    }



    private JMenuItem createConfigMenu(ResourceBundle bundle) {
        var config = JMenuItemBundled.of(new JMenu(), bundle, CONFIG_MENU_NAME);

        var languageMenu = JMenuItemBundled.of(new JMenu(), bundle, LANGUAGE_MENU_TITLE);
        var englishButton = JMenuItemBundled.of(new JMenuItem(), bundle, LANGUAGE_MENU_ENGLISH);
        englishButton.getItem().addActionListener(l ->
                ObservableLanguage.instance().changeLocale(Locale.ENGLISH) );

        var russianButton = JMenuItemBundled.of(new JMenuItem(), bundle, LANGUAGE_MENU_RUSSIAN);
        russianButton.getItem().addActionListener(l ->
                ObservableLanguage.instance().changeLocale(new Locale("ru")));

        languageMenu.add(englishButton).add(russianButton);

        var exitButton = JMenuItemBundled.of(new JMenuItem(),bundle, EXIT_BUTTON_NAME);
        exitButton.getItem().addActionListener(l -> onCloseAppEvent(this, bundle));

        config.getItem().add(createLookAndFeelMenu(bundle));
        config.add(languageMenu);
        config.add(exitButton);

        ObservableLanguage.instance().setListeners(config, languageMenu, englishButton, russianButton, exitButton);

        return config.getItem();
    }

    private JMenuItem createLookAndFeelMenu(ResourceBundle bundle) {
        var lookAndFeelMenu = JMenuItemBundled.of(new JMenu(), bundle, VIEW_MODE_MENU_NAME);

        lookAndFeelMenu.getItem().setMnemonic(KeyEvent.VK_V);
        addSystemLookAndFeel(lookAndFeelMenu.getItem(), bundle);
        addCrossplatformLookAndFeel(lookAndFeelMenu.getItem(), bundle);

        ObservableLanguage.instance().setListener(lookAndFeelMenu);
        return lookAndFeelMenu.getItem();
    }

    private void addSystemLookAndFeel(JMenuItem lookAndFeelMenu, ResourceBundle bundle) {
        var systemLookAndFeel = JMenuItemBundled.of(new JMenuItem(), bundle, SYSTEM_SCHEME_NAME);

        systemLookAndFeel.getItem().addActionListener((event) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.invalidate();
        });

        ObservableLanguage.instance().setListener(systemLookAndFeel);
        lookAndFeelMenu.add(systemLookAndFeel.getItem());
    }

    private void addCrossplatformLookAndFeel(JMenuItem lookAndFeelMenu, ResourceBundle bundle) {
        var crossplatformLookAndFeel = JMenuItemBundled.of(new JMenuItem(), bundle, UNIVERSAL_SCHEME_NAME);
        crossplatformLookAndFeel.getItem().addActionListener((event) -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            this.invalidate();
        });

        ObservableLanguage.instance().setListener(crossplatformLookAndFeel);
        lookAndFeelMenu.add(crossplatformLookAndFeel.getItem());
    }

    private JMenuItem createLogMenu(ResourceBundle bundle) {
        var testMenu = JMenuItemBundled.of(new JMenu(), bundle, TEST_MENU_NAME);
        testMenu.getItem().setMnemonic(KeyEvent.VK_T);

        var addLogMessageItem = JMenuItemBundled.of(new JMenuItem(), bundle, TEST_LOG_BUTTON_NAME);
        addLogMessageItem.getItem().addActionListener((event) -> Logger.debug("Новая строка"));

        var switchLogMenuVisibleItem = JMenuItemBundled.of(new JMenuItem(), bundle, SWITCHER_NAME);
        switchLogMenuVisibleItem.getItem().addActionListener((event) -> logWindow.setVisible(!logWindow.isVisible()));

        testMenu.add(switchLogMenuVisibleItem);
        testMenu.add(addLogMessageItem);

        ObservableLanguage.instance().setListeners(testMenu, switchLogMenuVisibleItem, addLogMessageItem);

        return testMenu.getItem();
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
