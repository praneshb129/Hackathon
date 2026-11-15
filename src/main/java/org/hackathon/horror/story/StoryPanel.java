package org.hackathon.horror.story;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class StoryPanel extends JFrame {
    private static final Color BACKGROUND = new Color(24, 28, 40);
    private static final Color SURFACE = new Color(35, 40, 54);
    private static final Color STORY_BACKGROUND = new Color(242, 238, 226);
    private static final Color STORY_FOREGROUND = new Color(42, 43, 48);
    private static final Color BUTTON_BACKGROUND = new Color(137, 53, 69);
    private static final Color BUTTON_HOVER = new Color(164, 68, 82);
    private static final Font BODY_FONT = new Font("SansSerif", Font.PLAIN, 16);
    private static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 14);

    private final JTextArea storyText = new JTextArea();
    private final JPanel choicesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 12));
    private final SceneIllustration illustration = new SceneIllustration();
    private final Consumer<Scene> sceneSelected;

    public StoryPanel(Consumer<Scene> sceneSelected) {
        super("Lost on the Trail");
        this.sceneSelected = sceneSelected;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(760, 600);
        setMinimumSize(new Dimension(620, 500));
        setLayout(new BorderLayout(12, 12));
        getContentPane().setBackground(BACKGROUND);

        storyText.setEditable(false);
        storyText.setLineWrap(true);
        storyText.setWrapStyleWord(true);
        storyText.setFont(BODY_FONT);
        storyText.setForeground(STORY_FOREGROUND);
        storyText.setBackground(STORY_BACKGROUND);
        storyText.setMargin(new Insets(16, 18, 16, 18));
        storyText.setCaretColor(STORY_FOREGROUND);

        JScrollPane storyScrollPane = new JScrollPane(storyText);
        storyScrollPane.setBorder(BorderFactory.createLineBorder(new Color(205, 198, 180)));
        storyScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        storyScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPanel scenePanel = new JPanel(new BorderLayout(0, 12));
        scenePanel.setBackground(BACKGROUND);
        scenePanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 4, 16));
        scenePanel.add(storyScrollPane, BorderLayout.CENTER);
        scenePanel.add(illustration, BorderLayout.SOUTH);

        add(scenePanel, BorderLayout.CENTER);
        choicesPanel.setBackground(SURFACE);
        choicesPanel.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
        add(choicesPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(null);
    }

    public void displayScene(String text, Scene scene, List<Choice> choices) {
        storyText.setText(text);
        storyText.setCaretPosition(0);
        illustration.showScene(scene);

        choicesPanel.removeAll();
        for (Choice choice : choices) {
            JButton button = new JButton(choice.getLabel());
            button.setFont(BUTTON_FONT);
            button.setForeground(Color.WHITE);
            button.setBackground(BUTTON_BACKGROUND);
            button.setOpaque(true);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            button.setRolloverEnabled(true);
            button.getModel().addChangeListener(event ->
                    button.setBackground(button.getModel().isRollover()
                            ? BUTTON_HOVER : BUTTON_BACKGROUND));
            button.addActionListener(event -> sceneSelected.accept(choice.getNextScene()));
            choicesPanel.add(button);
        }

        choicesPanel.revalidate();
        choicesPanel.repaint();
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
