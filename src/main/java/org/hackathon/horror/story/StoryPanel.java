package org.hackathon.horror.story;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.util.function.Consumer;

public class StoryPanel extends JFrame implements StoryView {
    private static final Color BACKGROUND = new Color(19, 22, 31);
    private static final Color SURFACE = new Color(34, 39, 52);
    private static final Color STORY_BACKGROUND = new Color(39, 44, 57);
    private static final Color STORY_FOREGROUND = new Color(237, 233, 225);
    private static final Color BORDER_COLOR = new Color(65, 73, 89);
    private static final Color BUTTON_BACKGROUND = new Color(145, 52, 70);
    private static final Color BUTTON_HOVER = new Color(177, 64, 82);
    private static final Color BUTTON_PRESSED = new Color(111, 39, 56);
    private static final Font BODY_FONT = new Font("SansSerif", Font.PLAIN, 18);
    private static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 15);
    private static final int CHOICE_GAP = 14;
    private static final int BUTTON_HEIGHT = 62;
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
        setSize(1080, 700);
        setMinimumSize(new Dimension(720, 600));

        storyText.setEditable(false);
        storyText.setLineWrap(true);
        storyText.setWrapStyleWord(true);
        storyText.setRows(5);
        storyText.setFont(BODY_FONT);
        storyText.setForeground(STORY_FOREGROUND);
        storyText.setBackground(STORY_BACKGROUND);
        storyText.setMargin(new Insets(24, 28, 24, 28));
        storyText.setCaretColor(STORY_FOREGROUND);
        storyText.setSelectionColor(BUTTON_HOVER);
        storyText.setSelectedTextColor(Color.WHITE);

        JScrollPane storyScrollPane = new JScrollPane(storyText);
        storyScrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        storyScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        storyScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        storyScrollPane.getVerticalScrollBar().setUnitIncrement(18);

        illustration.setPreferredSize(new Dimension(900, 250));
        illustration.setMinimumSize(new Dimension(480, 180));
        illustration.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));

        choicesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, CHOICE_GAP, 4));
        choicesPanel.setBackground(SURFACE);
        choicesPanel.setOpaque(true);
        choicesPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR),
                BorderFactory.createEmptyBorder(10, 8, 0, 8)));
        choicesPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent event) {
                updateChoiceLayout();
            }
        });

        JPanel content = new JPanel(new BorderLayout(0, 12));
        content.setBackground(BACKGROUND);
        content.setBorder(BorderFactory.createEmptyBorder(16, 20, 12, 20));
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
            buttonWidth = Math.max(buttonWidth, metrics.stringWidth(choice.getLabel()) + 44);
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
        button.setForeground(Color.WHITE);
        button.setBackground(BUTTON_BACKGROUND);
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
            Graphics2D g = (Graphics2D) graphics.create();
            try {
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                Color fill = getModel().isPressed() && getModel().isArmed()
                        ? BUTTON_PRESSED
                        : getModel().isRollover() ? BUTTON_HOVER : BUTTON_BACKGROUND;
                int inset = 4;
                g.setColor(fill);
                g.fillRoundRect(inset, inset, getWidth() - inset * 2,
                        getHeight() - inset * 2, 20, 20);
                g.setColor(hasFocus() ? new Color(255, 236, 239, 230)
                        : new Color(255, 255, 255, 45));
                g.setStroke(new BasicStroke(hasFocus() ? 2f : 1f));
                g.drawRoundRect(inset, inset, getWidth() - inset * 2 - 1,
                        getHeight() - inset * 2 - 1, 20, 20);
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
