package spacegame;

import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Václav Blažej
 */
public final class Settings implements Serializable {

    private volatile List<SettingsListener> listeners = new ArrayList<>();
    private boolean showInfo;
    private AffineTransform viewTransform;

    public Settings() {
        this.listeners = new ArrayList<>();
        this.showInfo = true;
        viewTransform = new AffineTransform();
    }

    public void fireSettingsChange() {
        for (SettingsListener listener : listeners) {
            listener.settingsChanged();
        }
    }

    public boolean isShowInfo() {
        return showInfo;
    }

    public void setShowInfo(boolean showInfo) {
        this.showInfo = showInfo;
        fireSettingsChange();
    }

    public AffineTransform getViewTransform() {
        return viewTransform;
    }

    public void setViewTransform(AffineTransform viewTransform) {
        this.viewTransform = viewTransform;
    }
}
