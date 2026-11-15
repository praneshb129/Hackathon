package org.hackathon.horror.story;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.function.Consumer;

public class StoryPanel extends JFrame implements StoryView {
    private static final Color BACKGROUND = new Color(13, 12, 11);
    private static final Color STORY_FOREGROUND = new Color(232, 226, 212);
    private static final Color MUTED_FOREGROUND = new Color(138, 131, 120);
    private static final Color ACCENT = new Color(217, 164, 65);
    private static final Color BORDER_COLOR = new Color(42, 38, 34);
    private static final Color BUTTON_BORDER = new Color(74, 68, 60);
    private static final Font BODY_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 16);
    private static final Font BUTTON_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 14);
    private static final int CHOICE_GAP = 14;
    private static final int BUTTON_HEIGHT = 48;
    private static final int MIN_BUTTON_WIDTH = 180;

    private final JTextArea storyText = new JTextArea();
    private final JPanel choicesPanel = new JPanel();
    private final SceneIllustration illustration = new SceneIllustration();
    private final Consumer<Scene> sceneSelected;
    private boolean choicesStacked;

    public StoryPanel(Consumer<Scene> sceneSelected) {
        super("Lost on the Trail");
        this.sceneSelected = sceneSelected;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(784, 700);
        setMinimumSize(new Dimension(720, 640));

        storyText.setEditable(false);
        storyText.setLineWrap(true);
        storyText.setWrapStyleWord(true);
        storyText.setRows(5);
        storyText.setFont(BODY_FONT);
        storyText.setForeground(STORY_FOREGROUND);
        storyText.setOpaque(false);
        storyText.setMargin(new Insets(16, 18, 16, 18));
        storyText.setCaretColor(ACCENT);
        storyText.setSelectionColor(new Color(217, 164, 65, 90));
        storyText.setSelectedTextColor(STORY_FOREGROUND);

        JScrollPane storyScrollPane = new JScrollPane(storyText);
        storyScrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        storyScrollPane.setOpaque(false);
        storyScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        storyScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        storyScrollPane.getVerticalScrollBar().setUnitIncrement(18);
        storyScrollPane.getViewport().setOpaque(false);
        storyScrollPane.getVerticalScrollBar().setBackground(BACKGROUND);
        storyScrollPane.getVerticalScrollBar().setForeground(MUTED_FOREGROUND);

        illustration.setPreferredSize(new Dimension(720, 360));
        illustration.setMinimumSize(new Dimension(480, 180));
        illustration.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));

        choicesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, CHOICE_GAP, 4));
        choicesPanel.setOpaque(false);
        choicesPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR),
                BorderFactory.createEmptyBorder(10, 8, 0, 8)));
        choicesPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent event) {
                updateChoiceLayout();
            }
        });

        JPanel content = new JPanel(new BorderLayout(0, 20)) {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                Graphics2D g = (Graphics2D) graphics.create();
                try {
                    float radius = Math.max(getWidth(), getHeight()) * 0.85f;
                    g.setPaint(new RadialGradientPaint(
                            new Point2D.Float(getWidth() / 2f, getHeight() * 0.3f),
                            Math.max(1f, radius),
                            new float[]{0f, 1f},
                            new Color[]{new Color(28, 24, 21), BACKGROUND}));
                    g.fillRect(0, 0, getWidth(), getHeight());
                } finally {
                    g.dispose();
                }
            }
        };
        content.setBackground(BACKGROUND);
        content.setBorder(BorderFactory.createEmptyBorder(20, 24, 16, 24));
        content.add(illustration, BorderLayout.NORTH);
        content.add(storyScrollPane, BorderLayout.CENTER);
        content.add(choicesPanel, BorderLayout.SOUTH);
        setContentPane(content);

        setLocationRelativeTo(null);
    }

    public void displayScene(String text, Scene scene, List<Choice> choices) {
        storyText.setText(text);
        storyText.setCaretPosition(0);
        illustration.showScene(scene);

        choicesPanel.removeAll();
        int buttonWidth = MIN_BUTTON_WIDTH;
        FontMetrics metrics = choicesPanel.getFontMetrics(BUTTON_FONT);
        for (Choice choice : choices) {
            buttonWidth = Math.max(buttonWidth, metrics.stringWidth(choice.getLabel()) + 40);
        }
        Dimension buttonSize = new Dimension(buttonWidth, BUTTON_HEIGHT);
        for (Choice choice : choices) {
            ChoiceButton button = createChoiceButton(choice, buttonSize);
            choicesPanel.add(button);
        }

        choicesStacked = false;
        choicesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, CHOICE_GAP, 4));
        updateChoiceLayout();
        choicesPanel.revalidate();
        choicesPanel.repaint();
    }

    private ChoiceButton createChoiceButton(Choice choice, Dimension size) {
        ChoiceButton button = new ChoiceButton(choice.getLabel());
        button.setFont(BUTTON_FONT);
        button.setForeground(STORY_FOREGROUND);
        button.setBackground(BACKGROUND);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setRolloverEnabled(true);
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size);
        button.addActionListener(event -> sceneSelected.accept(choice.getNextScene()));
        return button;
    }

    private void updateChoiceLayout() {
        Component[] buttons = choicesPanel.getComponents();
        int requiredWidth = Math.max(0, buttons.length - 1) * CHOICE_GAP;
        for (Component button : buttons) {
            requiredWidth += button.getPreferredSize().width;
        }

        Insets insets = choicesPanel.getInsets();
        int availableWidth = choicesPanel.getWidth() - insets.left - insets.right - 2 * CHOICE_GAP;
        boolean shouldStack = buttons.length > 1 && requiredWidth > availableWidth;
        if (shouldStack == choicesStacked) {
            return;
        }

        choicesStacked = shouldStack;
        if (shouldStack) {
            choicesPanel.setLayout(new BoxLayout(choicesPanel, BoxLayout.Y_AXIS));
            for (Component button : buttons) {
                if (button instanceof JComponent swingButton) {
                    swingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                }
            }
        } else {
            choicesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, CHOICE_GAP, 4));
        }
        choicesPanel.revalidate();
    }

    private static class ChoiceButton extends JButton {
        private ChoiceButton(String label) {
            super(label);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            boolean highlighted = getModel().isRollover() || hasFocus();
            setForeground(highlighted ? ACCENT : STORY_FOREGROUND);
            Graphics2D g = (Graphics2D) graphics.create();
            try {
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                boolean pressed = getModel().isPressed() && getModel().isArmed();
                g.setColor(pressed ? new Color(217, 164, 65, 34)
                        : highlighted ? new Color(217, 164, 65, 16) : new Color(0, 0, 0, 0));
                g.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
                g.setColor(highlighted || pressed ? ACCENT : BUTTON_BORDER);
                g.setStroke(new BasicStroke(1f));
                g.drawRect(1, 1, getWidth() - 3, getHeight() - 3);
            } finally {
                g.dispose();
            }
            super.paintComponent(graphics);
        }
    }

    public static class Choice {
        private final String label;
        private final Scene nextScene;

        public Choice(String label, Scene nextScene) {
            this.label = label;
            this.nextScene = nextScene;
        }

        public String getLabel() {
            return label;
        }

        public Scene getNextScene() {
            return nextScene;
        }
    }
}
