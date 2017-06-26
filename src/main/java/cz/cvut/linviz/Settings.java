package cz.cvut.linviz;

import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Václav Blažej
 */
public final class Settings implements Serializable {

    private static final AffineTransform BASE_TRANSFORM = AffineTransform.getScaleInstance(1, -1);
    public static DecimalFormat twoDecimalOutputFormat = new DecimalFormat("#.00");
    public Integer dotSize = 10;
    private volatile List<SettingsListener> listeners = new ArrayList<>();
    private boolean showInfo;
    private AffineTransform viewTransform;

    public Settings() {
        this.listeners = new ArrayList<>();
        this.showInfo = true;
        viewTransform = getBaseTransform();
    }

    public AffineTransform getBaseTransform() {
        return new AffineTransform(BASE_TRANSFORM);
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
