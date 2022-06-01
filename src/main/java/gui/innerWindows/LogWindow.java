package gui.innerWindows;

import gui.JInternalFrameExtended;
import localizer.ChangingLanguage;
import localizer.LocalizationKey;
import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class LogWindow extends JInternalFrameExtended
    implements LogChangeListener, ChangingLanguage {
  protected final LogWindowSource logSource;
  protected final TextArea logContent;

  public LogWindow(LogWindowSource logSource, ResourceBundle bundle) {
    super(
        bundle, bundle.getString(LocalizationKey.LOG_WINDOW_NAME.value()), true, true, true, true);
    this.logSource = logSource;
    this.logSource.registerListener(this);
    logContent = new TextArea("");
    logContent.setSize(getSize());

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(logContent, BorderLayout.CENTER);
    getContentPane().add(panel);

    pack();
    updateLogContent();
  }

  protected void updateLogContent() {
    StringBuilder content = new StringBuilder();
    for (LogEntry entry : logSource.all()) {
      content.append(entry.getMessage()).append("\n");
    }
    logContent.setText(content.toString());
    logContent.invalidate();
  }

  @Override
  public void onLogChanged() {
    EventQueue.invokeLater(this::updateLogContent);
  }

  @Override
  public void changeLanguageWith(ResourceBundle bundle) {
    this.title = bundle.getString(LocalizationKey.COORDINATING_WINDOW_NAME.value());
  }
}
