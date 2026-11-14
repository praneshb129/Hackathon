package org.hackathon.horror.story;

import javax.swing.SwingUtilities;

public class HackathonApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StoryGame().start());
    }
}
