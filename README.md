import java.util.Scanner;

 

public class MyProgram
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Do you want a tutorial? (yes/no)");
        String yN = input.nextLine();
        
        //tutorial
        if(yN.equalsIgnoreCase("yes") || yN.equalsIgnoreCase("y"))
        {
            System.out.println("This is a story game, where you can respond with simple one letter answers. \nEach path will lead you down a different path.");
            System.out.println();
            System.out.println("TUTORIAL STORY LOADING. . .");
            System.out.println();
            System.out.println("You find yourself trapped in your burning house.");
            System.out.println("To your left you see a gap large enough to fit you, however the path to it is swallowed in flames.");
            System.out.println("To you right you see a broken window, but its much further away.");
            System.out.println("Do you take the left path or right path?");
            
            
            yN = input.nextLine();
            while(!(yN.equalsIgnoreCase("left")||yN.equalsIgnoreCase("l") || yN.equalsIgnoreCase("right")||yN.equalsIgnoreCase("r")))
            {
                System.out.println("um hello excuse me? type left or right please please please");
                yN=input.nextLine();
            }
            if(yN.equalsIgnoreCase("left")||yN.equalsIgnoreCase("l"))
            {
                System.out.println("You sprint across the room, fire licking your whole body.");
                System.out.println("You attempt to leap through, however instead you slam into the wall at full speed.");
                System.out.println("Conciousness rapidly exits your body, as you are claimed by the flames.");
            }
            else if(yN.equalsIgnoreCase("right")||yN.equalsIgnoreCase("r"))
            {
                System.out.println("You sprint full speed, trying to get to the window before the flames claim it.");
                System.out.println("You leap through bracing yourself. Although you are left with many bad cuts from the glass.");
                System.out.println("You have successfully escaped.");
            }
            System.out.println("\nTutorial complete. . .");
            
        }
        
        
        //acutal :)
        System.out.println("Press any key to continue:");
        input.nextLine();
        clearScreen();
        System.out.println("LOADING STORY. . .");
        System.out.println("You're lost on a trail with only the wind to accompany you.");
        System.out.println("You find yourself aimlessly walking forward until you are met with two different paths.");
        System.out.println("Do you wish to head right or left?");
        String lr = input.nextLine();
        
        clearScreen();
        
        //left
        if(lr.equals("left"))
        {
            goLeft();
        }
        else
        {
            System.out.println("right im not finished");
        }
        /*
        if(lr.equals("right"))
        {
            goRight();
        }
        */
        
        
        
        
        
        
        //so we want options (Text adventure)
        //in the story we can choose 'l' or 'r'
        
    }
    //go left #1 :)
    public static void goLeft()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("You end up going left, where you find yourself on the side of an empty street.");
        System.out.println("You walk aimlessly down the road, watching the sun disappear behind the horizon.");
        System.out.println("You see the headlights of a car approaching you. Do you start running? (y/n)");
        
        //if user doesnt choose yes or no 
        String run = input.nextLine().trim();
        while (!(run.equalsIgnoreCase("yes") || run.equalsIgnoreCase("y") ||
                 run.equalsIgnoreCase("no") || run.equalsIgnoreCase("n")))
        {
            System.out.println("??????\nPlease input y or n");
            run = input.nextLine().trim();
        }
        
        //if user says yes
        if (run.equalsIgnoreCase("yes") || run.equalsIgnoreCase("y"))
        {
            clearScreen();
            System.out.println("You start to pick up your pace, before you find yourself running down the road.");
            System.out.println("You now hear a strange snarling behind you. As you run, you find a broken hatchet on the ground,");
            System.out.println("along with the other trash along the road.");
            System.out.println("Do you try to fight off the monster behind you, or keep running? (fight/run)");

            String action = input.nextLine().trim();
            while (!(action.equalsIgnoreCase("fight") || action.equalsIgnoreCase("f") ||
                     action.equalsIgnoreCase("run") || action.equalsIgnoreCase("r")))
            {
                System.out.println("??????\nPlease input fight or run");
                action = input.nextLine().trim();
            }

            if (action.equalsIgnoreCase("fight") || action.equalsIgnoreCase("f"))
            {
                clearScreen();
                System.out.println("You grab the hatchet and whip around, fear and determination rushing through your head.");
                System.out.println("You get a good hit on the creature's head, gashing it open. The beast shrieks in pain.");
                System.out.println("It grabs you by the arms and legs and pulls.");
                System.out.println("A scream echoes across the street, followed by noisy eating.");
                System.out.println("GAME OVER. . .");
            }
            else
            {
                clearScreen();
                System.out.println("You push yourself to run as fast as you possibly can.");
                System.out.println("You notice a path leading into the woods. Do you take the path? (y/n)");

                String path = input.nextLine().trim();
                while (!(path.equalsIgnoreCase("yes") || path.equalsIgnoreCase("y") ||
                         path.equalsIgnoreCase("no") || path.equalsIgnoreCase("n")))
                {
                    System.out.println("??????\nPlease input y or n");
                    path = input.nextLine().trim();
                }

                if (path.equalsIgnoreCase("yes") || path.equalsIgnoreCase("y"))
                {
                    // TODO: write what happens when they take the path
                    System.out.println("You take the path into the woods...");
                }
                else
                {
                    // TODO: write what happens when they stay on the road
                    System.out.println("You keep running down the road...");
                }
            }
        }
        
        else
        {
            // TODO: write what happens when they don't run
            clearScreen();
            System.out.println("You continue to calmly walk on the road, thinking the car is just someone passing by.");
            System.out.println("However, as you keep walking, you notice that the car making noises a car absolutely should not be making.");
            System.out.println("You whip around and see a giant wooden creature with bright flashing eyes barreling towards you.");
            System.out.println("Do you run into the forest on the right of the road, or across the empty street? (right/across)");
            
            String rightAcross = input.nextLine().trim();
            
            while (!(rightAcross.equalsIgnoreCase("right") || rightAcross.equalsIgnoreCase("r") ||
                     rightAcross.equalsIgnoreCase("across") || rightAcross.equalsIgnoreCase("a")))
            {
                System.out.println("??????\nPlease provide the right input.");
                rightAcross = input.nextLine().trim();
            }

            if (rightAcross.equalsIgnoreCase("right") || rightAcross.equalsIgnoreCase("r"))
            {
                clearScreen();
                // TODO: what happens when they run into the forest
                System.out.println("You begin to slow down, intending to feint and move into the forest.");
                System.out.println("When the creature is so close you can smell its disgusting breath, you swiftly dash to the right and dissapear into the forest foliage.");
                System.out.println("After much howling from frustration of losing you and not being able to find you, it stalks away.");
                System.out.println("Do you leave immediately, or wait a little longer? (l/w)");
                
                String leW = input.nextLine().trim();
                while(!(leW.equalsIgnoreCase("leave") || leW.equalsIgnoreCase("l") || leW.equalsIgnoreCase("wait") || leW.equalsIgnoreCase("w")))
                {
                    System.out.println("PLEASE ENTER LEAVE OR WAIT! (preferably now.)");
                }
                if(leW.equalsIgnoreCase("leave") || leW.equalsIgnoreCase("l"))
                {
                    System.out.println("You exit, feeling uneasy.");
                    System.out.println("Unfortunately, you fail to notice the monster waiting in the trees above.");
                    System.out.println("It descends with a terrible fury, ripping you open.");
                    System.out.println("You wish the monster would end you quickly.");
                    System.out.println("It does not.");
                    System.out.println("GAME OVER . . .");
                }
                else
                {
                    System.out.println("You sit quietly, thinking the monster may be waiting for you to come out.");
                    System.out.println("After a few tense minutes, it descends from the trees and leaves.");
                    System.out.println("You slowly walk through the forest, aware that anything could come at you.");
                    System.out.println("You come across a mineshaft with a strange light deep in it.");
                    System.out.println("Do you go in, or ignore it? (go/ignore)");
                    leW = input.nextLine().trim();
                    while(!(leW.equalsIgnoreCase("go") || leW.equalsIgnoreCase("g") || leW.equalsIgnoreCase("ignore") || leW.equalsIgnoreCase("i")))
                    {
                        System.out.println("Enter right input :) Go/ignore");
                        leW = input.nextLine().trim();
                    }
                    if(leW.equalsIgnoreCase("go") || leW.equalsIgnoreCase("g"))
                    {
                        System.out.println("you find lotsa applesauce. You live the rest of your life there :)");
                        System.out.println("Victory!");
                    }
                    else
                    {
                        System.out.println("The hunger creeps in and you slowly starve to death.");
                        System.out.println("GAME OVER . . .");
                    }
                    
                }
                
                /*
                die? from starvation idk what a loser imagine not going
                        into a mysterious minshaft that you could very clearly die in (end)
            */
                
            }
            else if(rightAcross.equalsIgnoreCase("across") || rightAcross.equalsIgnoreCase("a"))
            {
                clearScreen();
                System.out.println("You run aross the empty road but you get hit by bazooka.");
                System.out.println("Fortunately, you survive (somehow), but the next second, you are swept off your feet.");
                System.out.println("The monster grabs you and eats you.");
                System.out.println("GAME OVER . . .");
                // add what happenswhen running across
            }
            
            
            //another split
            
        }
    }  
    
    public static void goRight()
    {
        Scanner input = new Scanner(System.in);
        System.out.print("You end up going right, and find yourself in front of large iron gates.");
        System.out.println("Do you push open the gates, or do you turn around and try another path?(open/turn");
        String choice = input.nextLine();
        if(choice.equalsIgnoreCase("open"))
        {
            System.out.println("You pass through the gates and find a manor, covered in spiderwebs and the sounds of rats climbing the walls.");
            System.out.println("You walk up the old concrete steps to the wooden door.");
            System.out.println("You knock once with no answer, you and decide to go in because the wind is picking up, do you slowly open the door?");
            
        }
        else if(choice.equalsIgnoreCase("turn"))
        {
            goLeft();
        }
    }

    //new method so we dont have to keep doing the thing
    
    
    //clearing screen:
    public static void clearScreen() 
    {
    System.out.print("\033[H\033[2J");
    System.out.flush();
    }
    
    
    
}


/*
Tutorial Story: You find yourself trapped in your burning house. To your left you see a gap large enough 
 to fit you, however the path to it is swallowed in flames. To you right you see a broken window, but its much further away. 
do you take the left path or right path?(choice)

Going Left: you sprint across the room, fire licking your whole body. You attempt to leap through, however instead
you slam into the wall at full speed. Conciousness rapidly exits your body, as you are claimed by the flames.

Going right: You sprint full speed, trying to get to the window before the flames claim it. you leap through
bracing yourself. Although you are left with many bad cuts from the glass, you have successfully escaped. 


Horror story
Your lost on a trail with only the wind to accompany you
you find yourself aimlessly walking forward, leaves crunching with each step you take.
You continue walking until you find yourself with two diferent paths(choice left or right)

Going right
    You end up going right, and find yourself in front of large iron gates.
    Do you push open the gates, or do you turn around and try the other path?(choice)

    Through the gates:
        You pass through the gates and find a manor, covered in spiderwebs and the sounds of rats climbing the walls
        You walk up the old concrete steps to the wooden door. You knock once with no answer, you and decide to go in because the wind is picking up, do you slowly open the door?
        Yes(slowly open the door):
            You slowly open the door peeking inside before you enter the manor, its creaking echoing
            in the room.
        No(Don't open slowly):
            You kick open the door watching the door split in two as it hit the ground. You overestimated how strong the door would be.
        
        You walk in to find a glowing object brightly illuminating the chamber.
        do you pick it up, or leave it be?(choice) 
            
            Yes(Pick up the object):
                You pick up the object, holding it close to your chest and find its warmth to be comforting.
                
            No(Ignore it):
                You ignore it, not thinking it as anything important.
                
            You enter the next room, only that you can't see whats ahead of you. The room is pitch black.
            (if you took the object):
                The glowing objects provides little light for you as you walk through the room.
            (Use an else statement\ if you didn't take the object):
                You place your hands on the wall, traceing your fingers across as you walk through the room,
                only to still bump into somethibg that falls and shatters at your feet. Do you try to step over it, or do you try to walk it?
                Step Over:
                    You try to step over the shattered pieces only to still land a foot on a fragment. You wince in pain, however
                    you manage to get over all the pieces
                Walk Around: 
                    You are able to walk around the shattered pieces without injurying yourself. 
            
            You make it out of the dark hallway and end up in a dimly litten room with the windows 
            boarded up and and the faint sound of water dripping onto the floor. You aimlessly wander
            the room, not sure what you're looking for. Rounding the corner, (Math.random for chances)
                (< 50)
                    you stumble into a pile of boxes and end up in a celler. In the celler is what seems like a man. He's asleep, yet you notice he is chained to the walls.
                (> 50)
                    you stop right in front of a pile of boxes. Do you move them and continue this path or turn around?
                        Move boxes:
                            You decide to move the boxes and continue this path. You find yourself in a celler, where a man is chained to the walls. Yet thankfully, he seems to be asleep. 
                        Turn Around:
                            You turn around, not wanting to bother moving all the boxes. You find a vent hidden underneath the dust.
                            strange. That wasn't there the first time. Do you enter it?
                                Yes: you stomp the vent open, then crawl into it. the vent leads into a room with somekind of 
                                horrible treelike creature in there sleeping. Do you go back up or enter the room?
                                    Go back up: (stumble into the boxes, end up in a cellar, blah blah blah)
                                    Enter the room: you enter the room, careful not to wake up the beast. You quickly realize 
                                    there is nothing to be found here other than the creature and a second glowing object next to it.
                                    the one you hold reacts strongly to the second one. Do you leave, or try to grab the other one?
                                        Leave:(stumble into boxes)
                                        Grab: you slowly advance 
            
                
    
        
    Turn Around:
    (going to the left path) 

Going left
    You end up going left, where you find yourself on the side of an empty street. 
    You walk aimlessy down the road, watching the sun disappear behind the horizone. 
    You see the headlights of a car approaching you, do you start running?(choice)

    Yes: //done
        You start to pick up your pace, before you find yourself running down the road.
        you now hear a strange snarling behind you. As you run, you find a broken hatchet on the ground,
        along with the other trash along the road. Do you try to fight off the monster behind you,
        or keep running? (choice)
    
        Fight off: //done
             You grab the hatchet and whip around, fear and determination rushing through your head.
            You get a good hit on the creature's head, gashing it open. The beast shrieks
            in pain. It grabs you by the arms and legs and pulls. A scream echoes across the street,
             followed by noisy eating. (end)
             
        Keep running:You push yourself to run as fast as you possibly can. You notice a path leading into
        the woods. Do you take the path?(choice)
        
            Yes:
            (run into the forest)
            
            No: You continue to sprint as fast as you can. However, both you and the creature began to
            slow down, you much quicker. do you tough it out and keep sprinting, or
            try your luck with the path?
            
                Keep running: You tough it out and accelerate to a speed you never thought you could acheive.
                The monster quickly falls further and further behind you, untill you can no longer hear its cries.
                Exhausted, you collapse on the ground, panting heavily. When you feel strong enough to get up,
                you rise, only to fall back down. You feel something flowing down you rback and reach down to find blood spurting
                from an invisible scar.The creature must've gotten  ahit while you ere running. As your vision fades,
                you see a figure, along with the tree beast, pick you up and carry you off. (end)
                
                Try the path:
                (run into the forest)
    No:
        You continue to calmly walk on the road, thinking the car to just be someone passing by.
        However, as you keep walking, you notice that car is making noises a car absolutely should not be making.
        You whip around and see a giant tree-like creature with bright flashing eyes barreling towards you.
        Do you run into the forest on the right of the road, or across the empty street? (choice)

            Run into the forest(yes): You begin to slow down, intending to feint and move into the forest.
            When the creature is so close you can smell its disgusting breath, you swiftly
            dash to the right and dissapear into the forest foliage. After much howling from frsutration of losing
            you and not being able to find you, it stalks away. Do you leave immediately, or wait a little longer?
            
                Leave(yes): You exit, feeling uneasy. Unfortunately, you fail to notice the monster waiting in the trees above.
                it descends with a terrible fury, ripping you open. you wish the monster would end you quickly.
                It does not. (end)
                
                Wait(no): You sit quietly, thinking the monster may be waiting for you to come out.
                After a few tense minutes, it descends from the trees and leaves. Do you leave
                quietly or make a break for it?
                
                    Leave quietly: you slowly walk throught the forest, aware that anything could come at you.
                    You come across a mineshaft with a strange light deep in it. do you go in, or ignore it?
                    
                        Go in: you find lotsa applesauce. You live the rest of your life there :) (end)
                        
                        ignore it: die? from starvation idk what a loser imagine not going
                        into a mysterious minshaft that you could very clearly die in (end)
            
            

*/
