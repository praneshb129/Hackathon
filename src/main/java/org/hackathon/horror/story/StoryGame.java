package org.hackathon.horror.story;

import java.util.List;

public class StoryGame {
    private final StoryPanel view = new StoryPanel(this::showScene);
    private boolean hasLamp;

    public void start() {
        showScene(Scene.START);
        view.setVisible(true);
    }

    private void showScene(Scene scene) {
        if (scene == Scene.START) {
            hasLamp = false;
        }
        if (scene == Scene.PICK_UP_OBJECT || scene == Scene.IGNORE_OBJECT) {
            hasLamp = scene == Scene.PICK_UP_OBJECT;
            showDarkRoom();
            return;
        }
        if (scene == Scene.DIM_ROOM) {
            showDimRoom();
            return;
        }

        String text;
        List<StoryPanel.Choice> choices;

        switch (scene) {
            case START:
                text = "You're lost on a trail, with only the wind to accompany you. "
                        + "You walk forward until the trail splits. Which path do you take?";
                choices = List.of(
                        new StoryPanel.Choice("Go left", Scene.LEFT_ROAD),
                        new StoryPanel.Choice("Go right", Scene.GATES));
                break;
            case GATES:
                text = "You take the right path and find yourself at the foot of large iron gates. "
                        + "Do you push them open or turn around and try the other path?";
                choices = List.of(
                        new StoryPanel.Choice("Push open the gates", Scene.MANOR_DOOR),
                        new StoryPanel.Choice("Turn around and go left", Scene.LEFT_ROAD));
                break;
            case MANOR_DOOR:
                text = "Beyond the gates is a manor covered in spiderwebs. Rats scratch inside the walls. "
                        + "You climb the steps to an old wooden door. Do you slowly open it?";
                choices = List.of(
                        new StoryPanel.Choice("Yes, open the door", Scene.ENTER_MANOR),
                        new StoryPanel.Choice("Kick the door open", Scene.DOOR_KICKED));
                break;
            case DOOR_KICKED:
                text = "You kick the door open. It crashes to the floor, and you step into the manor. "
                        + "A glowing object brightly illuminates the chamber. Do you pick it up or leave it be?";
                choices = List.of(
                        new StoryPanel.Choice("Pick up the object", Scene.PICK_UP_OBJECT),
                        new StoryPanel.Choice("Ignore it", Scene.IGNORE_OBJECT));
                break;
            case ENTER_MANOR:
                text = "You slowly open the door, peek inside, and step into the manor. "
                        + "A glowing object brightly illuminates the chamber. Do you pick it up or leave it be?";
                choices = List.of(
                        new StoryPanel.Choice("Pick up the object", Scene.PICK_UP_OBJECT),
                        new StoryPanel.Choice("Ignore it", Scene.IGNORE_OBJECT));
                break;
            case STEP_OVER_SHARDS:
                text = "You try to step over the shattered pieces, but still land on a fragment. You wince "
                        + "in pain, but manage to get over all the pieces.";
                choices = List.of(new StoryPanel.Choice("Continue", Scene.DIM_ROOM));
                break;
            case WALK_AROUND_SHARDS:
                text = "You carefully walk around the shattered pieces without injuring yourself.";
                choices = List.of(new StoryPanel.Choice("Continue", Scene.DIM_ROOM));
                break;
            case BOXES_CHOICE:
                text = "You round a corner and stop in front of a pile of boxes. Do you move them "
                        + "and continue this path, or turn around?";
                choices = List.of(
                        new StoryPanel.Choice("Move the boxes", Scene.CELLAR),
                        new StoryPanel.Choice("Turn around", Scene.VENT_DISCOVERY));
                break;
            case CELLAR:
                text = "You stumble into the pile of boxes and end up in a cellar. A man is chained "
                        + "to the walls. He appears to be asleep.";
                choices = List.of(new StoryPanel.Choice("Start over", Scene.START));
                break;
            case VENT_DISCOVERY:
                text = "You turn around, not wanting to move the boxes, and notice a vent hidden "
                        + "underneath the dust. Strange... it wasn't there the first time. Do you enter it?";
                choices = List.of(
                        new StoryPanel.Choice("Enter the vent", Scene.VENT_CREATURE_CHOICE),
                        new StoryPanel.Choice("Stay in the room", Scene.CELLAR));
                break;
            case VENT_CREATURE_CHOICE:
                text = "You stomp the vent open and crawl through. It leads to a room where a horrible "
                        + "tree-like creature is sleeping. Do you climb back up or enter the room?";
                choices = List.of(
                        new StoryPanel.Choice("Go back up", Scene.CELLAR),
                        new StoryPanel.Choice("Enter the room", Scene.CREATURE_ROOM));
                break;
            case CREATURE_ROOM:
                text = "You carefully enter without waking the beast. There is nothing here but the "
                        + "creature and a second glowing object beside it. "
                        + (hasLamp
                        ? "The object you carry reacts strongly to this one. "
                        : "The object glows beside the sleeping creature. ")
                        + "Do you leave or try to grab it?";
                choices = List.of(
                        new StoryPanel.Choice("Leave", Scene.CELLAR),
                        new StoryPanel.Choice("Grab the second object", Scene.GRAB_SECOND_OBJECT));
                break;
            case GRAB_SECOND_OBJECT:
                text = "You slowly advance toward the second glowing object...";
                choices = List.of(new StoryPanel.Choice("Start over", Scene.START));
                break;
            case LEAVE_MANOR:
                text = "You decide not to open the door. As you step away, the door creaks open behind you...";
                choices = List.of(new StoryPanel.Choice("Start over", Scene.START));
                break;
            case LEFT_ROAD:
                text = "You take the left path and reach an empty street. The sun disappears behind "
                        + "the horizon. Headlights approach. Do you start running?";
                choices = List.of(
                        new StoryPanel.Choice("Run", Scene.RUN),
                        new StoryPanel.Choice("Keep walking", Scene.WALK));
                break;
            case RUN:
                text = "You pick up your pace and run down the road. A strange snarl rises behind you. "
                        + "Among the trash, you spot a broken hatchet. Do you grab it and fight the creature, "
                        + "or keep running?";
                choices = List.of(
                        new StoryPanel.Choice("Fight it with the hatchet", Scene.HATCHET_FIGHT),
                        new StoryPanel.Choice("Keep running", Scene.KEEP_RUNNING));
                break;
            case HATCHET_FIGHT:
                text = "You grab the hatchet and whirl around, fear and determination rushing through you. "
                        + "Your swing strikes the creature, and it shrieks. It grabs you and drags you into "
                        + "the darkness. A scream echoes across the empty street. The End.";
                choices = List.of(new StoryPanel.Choice("Start over", Scene.START));
                break;
            case KEEP_RUNNING:
                text = "You push yourself to run as fast as you can and spot a path leading into the woods. "
                        + "Do you take the path? Choose Yes or No.";
                choices = List.of(
                        new StoryPanel.Choice("Yes", Scene.FOREST_DASH),
                        new StoryPanel.Choice("No", Scene.WOODS_PATH_CHOICE));
                break;
            case WOODS_PATH_CHOICE:
                text = "You continue sprinting. Both you and the creature begin to slow down, but you "
                        + "are tiring much faster. Do you tough it out and keep sprinting, or try your luck "
                        + "with the path into the woods?";
                choices = List.of(
                        new StoryPanel.Choice("Keep sprinting", Scene.KEEP_SPRINTING),
                        new StoryPanel.Choice("Try the path into the woods", Scene.FOREST_DASH));
                break;
            case KEEP_SPRINTING:
                text = "You tough it out and accelerate to a speed you never thought possible. The monster "
                        + "falls farther behind until its cries fade away. Exhausted, you collapse. When you "
                        + "try to stand, you fall back down. Blood runs from an invisible scar on your back. "
                        + "As your vision fades, a figure and the tree-beast carry you away. The End.";
                choices = List.of(new StoryPanel.Choice("Start over", Scene.START));
                break;
            case FOREST_DASH:
                text = "You slow down, pretending to tire, then dash right when the creature is close enough "
                        + "for you to smell its breath. You vanish into the foliage. After howling in "
                        + "frustration, the creature stalks away. Do you leave now or wait longer?";
                choices = List.of(
                        new StoryPanel.Choice("Leave immediately", Scene.FOREST_LEAVE_NOW),
                        new StoryPanel.Choice("Wait a little longer", Scene.FOREST_WAIT));
                break;
            case FOREST_LEAVE_NOW:
                text = "You leave, uneasy, but fail to notice the monster waiting in the trees above. "
                        + "It descends in a terrible fury and tears into you. You wish it would end quickly. "
                        + "It does not. The End.";
                choices = List.of(new StoryPanel.Choice("Start over", Scene.START));
                break;
            case FOREST_WAIT:
                text = "You sit quietly, suspecting the monster may be waiting for you. After several tense "
                        + "minutes, it descends from the trees and leaves. Do you leave quietly or make a break for it?";
                choices = List.of(
                        new StoryPanel.Choice("Leave quietly", Scene.FOREST_LEAVE_QUIETLY),
                        new StoryPanel.Choice("Make a break for it", Scene.FOREST_BREAK_RUN));
                break;
            case FOREST_LEAVE_QUIETLY:
                text = "You slowly walk through the forest, aware that anything could come at you. "
                        + "You find a mineshaft with a strange light deep inside. Do you enter or ignore it?";
                choices = List.of(
                        new StoryPanel.Choice("Enter the mineshaft", Scene.MINESHAFT_ENTER),
                        new StoryPanel.Choice("Ignore it", Scene.MINESHAFT_IGNORE));
                break;
            case FOREST_BREAK_RUN:
                text = "You burst from your hiding place and sprint through the trees. Branches claw at "
                        + "your clothes as the creature's roar follows close behind. The End.";
                choices = List.of(new StoryPanel.Choice("Start over", Scene.START));
                break;
            case CROSS_STREET:
                text = "You sprint across the empty street. The creature crashes into the road behind you, "
                        + "and you disappear into the darkness. The End.";
                choices = List.of(new StoryPanel.Choice("Start over", Scene.START));
                break;
            case MINESHAFT_ENTER:
                text = "You enter the mineshaft and discover a surprising supply of applesauce. "
                        + "You live out the rest of your life there. The End.";
                choices = List.of(new StoryPanel.Choice("Start over", Scene.START));
                break;
            case MINESHAFT_IGNORE:
                text = "You leave the strange light alone. With no food or shelter, you eventually "
                        + "succumb to starvation. The End.";
                choices = List.of(new StoryPanel.Choice("Start over", Scene.START));
                break;
            case WALK:
                text = "You continue calmly along the road, assuming the car is only passing by. Then it "
                        + "makes noises no car should make. You turn and see a giant tree-like creature "
                        + "with flashing eyes charging toward you. Do you run into the forest or across "
                        + "the empty street?";
                choices = List.of(
                        new StoryPanel.Choice("Run into the forest", Scene.FOREST_DASH),
                        new StoryPanel.Choice("Run across the street", Scene.CROSS_STREET));
                break;
            default:
                throw new IllegalStateException("Unhandled story scene: " + scene);
        }

        view.displayScene(text, scene, choices);
    }

    private void showDarkRoom() {
        String text;
        Scene illustrationScene;
        List<StoryPanel.Choice> choices;

        if (hasLamp) {
            text = "You pick up the glowing object and hold it close to your chest. Its warmth is comforting. "
                    + "You enter the next room, but it is pitch black. The object provides little light "
                    + "as you walk through the room.";
            illustrationScene = Scene.DARK_ROOM_WITH_LAMP;
            choices = List.of(new StoryPanel.Choice("Continue", Scene.DIM_ROOM));
        } else {
            text = "You ignore the glowing object and enter the next room. It is pitch black, and you "
                    + "cannot see what is ahead. You trace the wall with your fingers as you walk, "
                    + "but bump into something. It falls and shatters at your feet. Do you step over "
                    + "the pieces or walk around them?";
            illustrationScene = Scene.DARK_ROOM_WITHOUT_LAMP;
            choices = List.of(
                    new StoryPanel.Choice("Step over the pieces", Scene.STEP_OVER_SHARDS),
                    new StoryPanel.Choice("Walk around the pieces", Scene.WALK_AROUND_SHARDS));
        }

        view.displayScene(text, illustrationScene, choices);
    }

    private void showDimRoom() {
        if (Math.random() * 100 < 50) {
            showScene(Scene.CELLAR);
        } else {
            String text = "You make it out of the dark hallway and enter a dimly lit room. "
                    + "The windows are boarded up, and water drips faintly onto the floor. "
                    + "You wander aimlessly, unsure what you're looking for. Rounding a corner...";
            view.displayScene(text, Scene.DIM_ROOM, List.of(
                    new StoryPanel.Choice("Continue", Scene.BOXES_CHOICE)));
        }
    }
}
