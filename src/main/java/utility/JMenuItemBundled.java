package utility;

import localizer.LocalizationKey;
import lombok.Getter;

import javax.swing.*;
import java.util.ResourceBundle;

public class JMenuItemBundled implements ChangingLanguage{
    @Getter
    private JMenuItem item;
    private LocalizationKey key;

    private JMenuItemBundled(){}
    private JMenuItemBundled(JMenuItem inst, ResourceBundle bundle, LocalizationKey key){
        item = inst;
        this.key = key;
        update(bundle);
    }
    public static JMenuItemBundled of(JMenuItem inst, ResourceBundle bundle, LocalizationKey key){
        return new JMenuItemBundled(inst, bundle, key);
    }
    @Override
    public void update(ResourceBundle bundle) {
        item.setText(bundle.getString(key.value()));
    }

    public JMenuItemBundled add(JMenuItemBundled bundled){
        item.add(bundled.getItem());
        return this;
    }
}
