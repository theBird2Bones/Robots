package gui;

import localizer.ChangingLanguage;
import localizer.LocalizationKey;

import javax.swing.*;
import java.util.ResourceBundle;

public class JMenuBundled extends JMenu implements ChangingLanguage {
  private final LocalizationKey key;

  public JMenuBundled(ResourceBundle bundle, LocalizationKey key) {
    this.key = key;
    changeLanguageWith(bundle);
  }

  @Override
  public void changeLanguageWith(ResourceBundle bundle) {
    this.setText(bundle.getString(key.value()));
  }
}
