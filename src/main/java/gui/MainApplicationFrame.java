package gui;

import localizer.LocalizationKey;
import log.Logger;
import utility.InternalFramesManager;
import utility.JMenuItemBundled;
import utility.ObservableLocalization;

import static localizer.LocalizationKey.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.Locale;
import java.util.ResourceBundle;


public class MainApplicationFrame extends JFrameExtended {
    private final JDesktopPane desktopPane = new JDesktopPane();
//    private final LogWindow logWindow;

  public MainApplicationFrame(ResourceBundle bundle) {
    super(bundle);
    // Make the big window be indented 50 pixels from each edge
    // of the screen.

    int inset = 50;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    setBounds(inset, inset, screenSize.width - inset * 2, screenSize.height - inset * 2);

    setContentPane(desktopPane);

        var logWindow = createLogWindow(bundle);
        InternalFramesManager.instance().registerFrame(logWindow);

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
    var config = new JMenuBundled(bundle, CONFIG_MENU_NAME);

    var languageMenu = new JMenuBundled(bundle, LANGUAGE_MENU_TITLE);

    var englishButton = new JMenuItemBundled(bundle, LANGUAGE_MENU_ENGLISH);
    englishButton.addActionListener(l -> ObservableLocalization.instance().changeLocale(Locale.ENGLISH));

    var russianButton = new JMenuItemBundled(bundle, LANGUAGE_MENU_RUSSIAN);
    russianButton .addActionListener(l -> ObservableLocalization.instance().changeLocale(new Locale("ru")));

    languageMenu.add(englishButton);
    languageMenu.add(russianButton);

    var exitButton = new JMenuItemBundled(bundle, EXIT_BUTTON_NAME);
    exitButton.addActionListener(l -> onCloseAppEvent(this));

    config.add(createLookAndFeelMenu(bundle));
    config.add(languageMenu);
    config.add(exitButton);

    ObservableLocalization.instance()
        .addListeners(config, languageMenu, englishButton, russianButton, exitButton);

    return config;
  }

  private JMenuItem createLookAndFeelMenu(ResourceBundle bundle) {
    var lookAndFeelMenu = new JMenuBundled(bundle, VIEW_MODE_MENU_NAME);

    lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
    addSystemLookAndFeel(lookAndFeelMenu, bundle);
    addCrossplatformLookAndFeel(lookAndFeelMenu, bundle);

    ObservableLocalization.instance().addListener(lookAndFeelMenu);
    return lookAndFeelMenu;
  }

  private void addSystemLookAndFeel(JMenuItem lookAndFeelMenu, ResourceBundle bundle) {
    var systemLookAndFeel = new JMenuItemBundled(bundle, SYSTEM_SCHEME_NAME);

    systemLookAndFeel
        .addActionListener(
            (event) -> {
              setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
              this.invalidate();
            });

    ObservableLocalization.instance().addListener(systemLookAndFeel);
    lookAndFeelMenu.add(systemLookAndFeel);
  }

  private void addCrossplatformLookAndFeel(JMenuItem lookAndFeelMenu, ResourceBundle bundle) {
    var crossplatformLookAndFeel =
        new JMenuItemBundled(bundle, UNIVERSAL_SCHEME_NAME);
    crossplatformLookAndFeel
        .addActionListener(
            (event) -> {
              setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
              this.invalidate();
            });

    ObservableLocalization.instance().addListener(crossplatformLookAndFeel);
    lookAndFeelMenu.add(crossplatformLookAndFeel);
  }

  private JMenuItem createLogMenu(ResourceBundle bundle) {
    var testMenu = new JMenuBundled(bundle, TEST_MENU_NAME);
    testMenu.setMnemonic(KeyEvent.VK_T);

    var addLogMessageItem = new JMenuItemBundled(bundle, TEST_LOG_BUTTON_NAME);
    addLogMessageItem.addActionListener((event) -> Logger.debug("Новая строка"));

        var switchLogMenuVisibleItem = JMenuItemBundled.of(new JMenuItem(), bundle, SWITCHER_NAME);
        switchLogMenuVisibleItem.getItem().addActionListener((event) -> {
            var logWindow = InternalFramesManager.instance().getFrameInstance(LogWindow.class);
            logWindow.setVisible(!logWindow.isVisible());
        });

    testMenu.add(switchLogMenuVisibleItem);
    testMenu.add(addLogMessageItem);

    ObservableLocalization.instance()
        .addListeners(testMenu, switchLogMenuVisibleItem, addLogMessageItem);

    return testMenu;
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
