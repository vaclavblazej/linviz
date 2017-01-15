package spacegame.view;

import spacegame.Settings;
import spacegame.controller.Controller;
import spacegame.model.Model;
import spacegame.model.Player;
import spacegame.view.component.CustomCheckbox;
import spacegame.view.component.CustomSlider;
import sun.swing.DefaultLookup;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author Václav Blažej
 */
public class EvolutionView extends JPanel implements ActionListener {

    private final JLabel turn = new JLabel();
    private final Timer refreshGraphics = new Timer(200, this);
    private final DefaultListModel<Player> playersModel;
    private Controller controller;
    private View view;
    private Model model;
    private Settings settings;
    private Integer lastEpoch = 0;

    public EvolutionView(View view, Model model, Controller controller, Settings settings) {
        this.view = view;
        this.model = model;
        this.controller = controller;
        this.settings = settings;
        this.setPreferredSize(new Dimension(400, 600));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.playersModel = new DefaultListModel<>();

        this.add(turn);
        this.add(createPlayerList());
        this.add(graphicsControls());
        this.add(createSimulationControls());
        JTabbedPane tabs = new JTabbedPane();
        tabs.setPreferredSize(new Dimension(220, 200));
        tabs.addTab("Genetic", createGeneticTab());
//        tabs.addTab("Differential", createDifferentialTab());
        this.add(tabs);
        refreshGraphics.start();
    }

    private JComponent graphicsControls() {
        JPanel res = new JPanel();
        res.setLayout(new BoxLayout(res, BoxLayout.Y_AXIS));
        res.add(new JLabel("Graphics settings"));
        res.add(getCheckboxWithLabel("Show rays",
                new CustomCheckbox("on", "off", false),
                e -> view.setShowPoints(e.getStateChange() == ItemEvent.SELECTED)));
        res.add(getCheckboxWithLabel("Show graphics",
                new CustomCheckbox("on", "off", true),
                e -> controller.setGRAPHICS(e.getStateChange() == ItemEvent.SELECTED)));
        res.setBorder(new BevelBorder(BevelBorder.LOWERED));
        return res;
    }

    private JPanel getCheckboxWithLabel(String text, CustomCheckbox checkbox, ItemListener listener) {
        final JPanel panel = new JPanel();
        panel.add(new JLabel(text + ":"));
        checkbox.addItemListener(listener);
        panel.add(checkbox);
        return panel;
    }

    private JComponent createSimulationControls() {
        JPanel res = new JPanel();
        res.setLayout(new BoxLayout(res, BoxLayout.Y_AXIS));
        res.add(new JLabel("Simulation settings"));
        final JSlider speedSlider = new CustomSlider("Speed", 2, 1000, 150);
        speedSlider.addChangeListener(evt -> controller.setSimulationDelay(speedSlider.getValue()));
        res.add(speedSlider);
//        final CustomCheckbox runButton = new CustomCheckbox("run", "stop", false);
//        runButton.addItemListener(e -> {
//            if (e.getStateChange() == ItemEvent.SELECTED) {
//                controller.startSimulationInThread();
//            } else {
//                controller.endSimulation();
//            }
//        });
//        res.add(runButton);
        res.setBorder(new BevelBorder(BevelBorder.LOWERED));
        res.setPreferredSize(new Dimension(220, 200));
        return res;
    }

    private JComponent createGeneticTab() {
        JPanel res = new JPanel(new BorderLayout());
        res.setBorder(new BevelBorder(BevelBorder.LOWERED));
        return res;
    }

    private JComponent createPlayerList() {
        final JList<Player> list = new JList<>(playersModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        JScrollPane scroll = new JScrollPane(list);
        scroll.setPreferredSize(new Dimension(280, 120));
        list.setCellRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(
                    JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {
                Color color = ((Player) value).getColor();
                setComponentOrientation(list.getComponentOrientation());

                Color bg = null;
                Color fg = null;

                JList.DropLocation dropLocation = list.getDropLocation();
                if (dropLocation != null
                        && !dropLocation.isInsert()
                        && dropLocation.getIndex() == index) {

                    bg = DefaultLookup.getColor(this, ui, "List.dropCellBackground");
                    fg = DefaultLookup.getColor(this, ui, "List.dropCellForeground");
                    isSelected = true;
                }
                fg = color;

                if (isSelected) {
                    setBackground(bg == null ? list.getSelectionBackground() : bg);
                    setForeground(fg == null ? list.getSelectionForeground() : fg);
                } else {
                    setBackground(list.getBackground());
                    setForeground(fg == null ? list.getSelectionForeground() : fg);
                }

                if (value instanceof Icon) {
                    setIcon((Icon) value);
                    setText("");
                } else {
                    setIcon(null);
                    setText((value == null) ? "" : value.toString());
                }

                setEnabled(list.isEnabled());
                setFont(list.getFont());

                Border border = null;
                if (cellHasFocus) {
                    if (isSelected) {
                        border = DefaultLookup.getBorder(this, ui, "List.focusSelectedCellHighlightBorder");
                    }
                    if (border == null) {
                        border = DefaultLookup.getBorder(this, ui, "List.focusCellHighlightBorder");
                    }
                } else {
                    border = new EmptyBorder(1, 1, 1, 1);
                }
                setBorder(border);

                return this;
            }
        });
        return scroll;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
