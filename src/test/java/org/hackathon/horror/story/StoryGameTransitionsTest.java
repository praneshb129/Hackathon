package org.hackathon.horror.story;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StoryGameTransitionsTest {
    @Test
    void everySceneIsReachableAndChoicesLeadToHandledScenes() {
        FakeStoryView view = new FakeStoryView();
        StoryGame game = new StoryGame(view, () -> 0.75);
        Set<Scene> visited = EnumSet.noneOf(Scene.class);
        Deque<Scene> pending = new ArrayDeque<>();
        pending.add(Scene.START);

        while (!pending.isEmpty()) {
            Scene scene = pending.removeFirst();
            if (!visited.add(scene)) {
                continue;
            }

            game.showScene(scene);
            assertNotNull(view.scene, "Scene " + scene + " did not produce a display");
            visited.add(view.scene);
            assertFalse(view.choices.isEmpty(), "Scene " + scene + " has no outgoing choice");
            for (StoryPanel.Choice choice : view.choices) {
                assertNotNull(choice.getNextScene(), "A choice has no destination in " + scene);
                pending.addLast(choice.getNextScene());
            }
        }

        assertEquals(EnumSet.allOf(Scene.class), visited);
    }

    @Test
    void everyEndingOffersAStartOverTransition() {
        FakeStoryView view = new FakeStoryView();
        StoryGame game = new StoryGame(view, () -> 0.75);
        Set<Scene> endings = EnumSet.of(
                Scene.CELLAR,
                Scene.CELLAR_CAUGHT,
                Scene.GRAB_SECOND_OBJECT,
                Scene.HATCHET_FIGHT,
                Scene.KEEP_SPRINTING,
                Scene.FOREST_LEAVE_NOW,
                Scene.FOREST_BREAK_RUN,
                Scene.CROSS_STREET,
                Scene.MINESHAFT_ENTER,
                Scene.MINESHAFT_IGNORE);

        for (Scene ending : endings) {
            game.showScene(ending);
            assertRestartChoice(view.choices);
        }
    }

    @Test
    void cellarRandomBranchEndsAndOffersRestart() {
        FakeStoryView view = new FakeStoryView();
        StoryGame game = new StoryGame(view, () -> 0.25);

        game.showScene(Scene.DIM_ROOM);

        assertEquals(Scene.CELLAR, view.scene);
        assertTrue(view.text.contains("stumble into a pile of boxes"));
        assertRestartChoice(view.choices);
    }

    @Test
    void restartingClearsTheLampState() {
        FakeStoryView view = new FakeStoryView();
        StoryGame game = new StoryGame(view, () -> 0.75);

        game.showScene(Scene.PICK_UP_OBJECT);
        assertEquals(Scene.DARK_ROOM_WITH_LAMP, view.scene);

        game.showScene(Scene.HATCHET_FIGHT);
        assertRestartChoice(view.choices);
        game.showScene(view.choices.get(0).getNextScene());
        assertEquals(Scene.START, view.scene);

        game.showScene(Scene.CREATURE_ROOM);
        assertTrue(view.text.contains("The object glows beside the sleeping creature."));
        assertFalse(view.text.contains("The object you carry reacts strongly to this one."));
    }

    private void assertRestartChoice(List<StoryPanel.Choice> choices) {
        assertEquals(1, choices.size());
        assertEquals("Start over", choices.get(0).getLabel());
        assertEquals(Scene.START, choices.get(0).getNextScene());
    }

    private static class FakeStoryView implements StoryView {
        private String text;
        private Scene scene;
        private List<StoryPanel.Choice> choices = List.of();

        @Override
        public void displayScene(String text, Scene scene, List<StoryPanel.Choice> choices) {
            this.text = text;
            this.scene = scene;
            this.choices = choices;
        }

        @Override
        public void setVisible(boolean visible) {
        }
    }
}
