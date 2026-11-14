package org.hackathon.horror.story;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class StoryPanel extends JFrame {
    private final JTextArea storyText = new JTextArea();
    private final JPanel choicesPanel = new JPanel(new FlowLayout());
    private final SceneIllustration illustration = new SceneIllustration();
    private final Consumer<Scene> sceneSelected;

    public StoryPanel(Consumer<Scene> sceneSelected) {
        super("Lost on the Trail");
        this.sceneSelected = sceneSelected;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        storyText.setEditable(false);
        storyText.setLineWrap(true);
        storyText.setWrapStyleWord(true);
        storyText.setFont(new Font("SansSerif", Font.PLAIN, 16));
        storyText.setMargin(new Insets(16, 16, 16, 16));

        JPanel scenePanel = new JPanel(new BorderLayout());
        scenePanel.add(storyText, BorderLayout.NORTH);
        scenePanel.add(illustration, BorderLayout.CENTER);

        add(scenePanel, BorderLayout.CENTER);
        add(choicesPanel, BorderLayout.SOUTH);
    }

    public void displayScene(String text, Scene scene, List<Choice> choices) {
        storyText.setText(text);
        storyText.setCaretPosition(0);
        illustration.showScene(scene);

        choicesPanel.removeAll();
        for (Choice choice : choices) {
            JButton button = new JButton(choice.getLabel());
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
