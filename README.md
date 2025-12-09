import java.util.*;
import java.util.Scanner;


public class cohesionQuest {


    static class Player {
        final String name;
        int health = 10;
        final Set<String> inventory = new HashSet<>();
        final Set<String> weapons = new HashSet<>();
        boolean gameOver = false;

        Player(String name) {
            this.name = name;
        }

        void addItem(String item) {
            inventory.add(item.toLowerCase());
        }

        void addWeapon(String weapon) {weapons.add(weapon);}

        boolean has(String item) {
            return inventory.contains(item.toLowerCase());
        }

        void losehp(int amount) {
            health -= amount;
            System.out.println(amount + " health points have been lost. Health remaining: " + health);
            if (health <= 0) {
                System.out.println("You ran out of health...");
                gameOver = true;
            }
        }
    }


    static class Enemy {
        final String kind;
        int health = 3;
        int damage = 2;

        Enemy(String kind) {
            this(kind, 3, 2);
        }

        Enemy(String kind, int health, int damage) {
            this.kind = kind;
            this.health = health;
            this.damage = damage;
        }

        void attack(Player player) {
            System.out.println("The " + kind + " attacks!");
            player.losehp(damage);
        }
    }


    static abstract class Scene {
        final String name;

        Scene(String name) {
            this.name = name;
        }

        abstract void enter(Game game);
    }


    static class Ocean extends Scene {
        Ocean() {
            super("Ocean");
        }

        @Override
        void enter(Game game) {
            Player p = game.player;
            String[] foeTypes = {"Gloomy Virus" , "Deep Sea Worm", "Microscopic Shark"};

            System.out.println("You wake up alone at the bottom of the ocean.");
            System.out.println("There is nothing around you except for a shimmering bubble.");
            String bubbleChoice = game.makeChoice("Pop the bubble?", new String[]{"y", "n"});

            if (bubbleChoice.equals("y")) {
                System.out.println("Inside the bubble was the Bubble Blade!");
                p.addItem("bubble");
                p.addWeapon("Bubble Blade");
                System.out.println("The pop has disturbed a creature underneath the sand.");
                game.encounterEnemy("Gloomy Virus");
                System.out.println("After that surprise, you go searching for something interesting.");
            } else {
                System.out.println("You walk away from the bubble and go searching for something more interesting.");
            }

            System.out.println("After a while of traveling, you see a giant cloud in the distance.");
            System.out.println("Approaching it, you can see that there is a giant mountain spewing out hot water and minerals from the Earth.");
            System.out.println("A creature comes out of a hole in the mountain and dashes towards you!");
            game.encounterEnemy(foeTypes[game.rng.nextInt(foeTypes.length)]);

            System.out.println("A little shelter is in a crack in the rocks.");
            String shelterChoice = game.makeChoice("Enter the shelter?", new String[]{"y", "n"});

            if (shelterChoice.equals("y")) {
                System.out.println("You enter the shelter and see an archaea cell sitting on a rock surrounded by rocky decorations and furniture.");
                System.out.println("[???] Hello! You must be tired from wandering around these horrible wastelands.");
                System.out.println("[???] Please, stop here and rest. It is very lonely living this deep and I could use some company.");
                System.out.println("[Archie] My name is Archie. I have lived here for a long time. Just me and my rocks.");
                System.out.println("[Archie] While I get us something to eat, do you have any questions?");

                String questionChoice = game.makeChoice(
                        "a. Could you tell me more about yourself? b. What is this place?",
                        new String[]{"a", "b"}
                );

                if (questionChoice.equals("a")) {
                    System.out.println("[Archie] Well, I am apart of the ancient Archaea Clan.");
                    System.out.println("[Archie] Archaea cells are some of the oldest cells around and we have a rich culture.");
                    System.out.println("[Archie] We live in extreme environments and can survive in places most cells cannot.");
                    System.out.println("[Archie] Our membranes and enzymes are specially adapted to high heat and harsh chemicals.");
                } else {
                    System.out.println("[Archie] This giant mountain is a hydrothermal vent.");
                    System.out.println("[Archie] It shoots out minerals and heat from the earth for cells like me to eat.");
                    System.out.println("[Archie] That is how we survive out here.");
                    System.out.println("[Archie] Long ago, this place was full of life until a giant organism came.");
                    System.out.println("[Archie] Our community tried to fight it off, but it was no use.");
                    System.out.println("[Archie] It was not of any kingdom I've ever seen.");
                    System.out.println("[Archie] Maybe you can find that out.");
                }

                System.out.println("Bing!");
                System.out.println("[Archie] Oh! Here is the food.");
                System.out.println("Archie gives you a bowl of sludge.");
                p.addItem("Sludge");
                System.out.println("[Archie] How did you land up in the ocean?");
                System.out.println("You tell Archie that you don't know.");
                System.out.println("[Archie] The only way back to the surface is through the hydrothermal vent.");
                System.out.println("[Archie] If you catch its current there is a chance you will make it to shore.");
                System.out.println("[Archie] There is a passageway that goes to the bottom of the vent and you can rise up from there.");
                System.out.println("[Archie] However, a cell lives there.");
                // heterotophic and autothropic?? help
                System.out.println("[Archie] His name is Chaeakin (pronounced Ch-aye-uh-kin). He went down into the vent when our old community was attacked.");
                System.out.println("[Archie] He has been consuming the minerals directly and is now more outside molecule than himself.");
                System.out.println("[Archie] If you want to leave, then you must defeat him.");
                System.out.println("[Archie] I will give you an artifact from my protocell ancestors.");
                p.addItem("Protocell Artifact");
                System.out.println("[Archie] Good luck, " + p.name + "!");
                System.out.println("Chaekin descends down towards you...\n");
                game.encounterEnemy("Chaekin");
            }
        }
    }

    static class Shore extends Scene {
        Shore() {
            super("Shore");
        }

        @Override
        void enter(Game game) {
            Player p = game.player;
            String[] foeTypes = {"Sandy Virus", "Microplastic Monster", "Trash Heap", "Rabid Bacteria", "Grain o' Sand"};
            boolean shoreChallenge = false;
            System.out.println("The currents carry you to shore.");
            System.out.println("You wake up on the warm sand and surrounded by trash.");
            System.out.println("Out of nowhere, a bacteria cell jumps down towards you from the top of a sand castle.");
            System.out.println("[???] Aye! What are you doin' here?");
            System.out.println("[Bacteria Guard] This is the land of the bacteria, and I can't let you through.");
            String shoreChoice = game.makeChoice("a. Explain who you are. b. Walk away.", new String[]{"a", "b"});
            if (shoreChoice.equals("a")) {
                System.out.println("[Bacteria Guard] So's you saying that your an adventurer?");
                System.out.println("[Bacteria Guard] That's great! The Queen has been wantin' someone like you to clean this place up.");
                System.out.println("[Bacteria Guard] We love the trash 'ere. It's yummy.");
                System.out.println("[Bacteria Guard] But problem is that there's been some creatures crawling around and making everything all bad.");
                System.out.println("[Bacteria Guard] I'll take you to the Queen right away!");
                System.out.println("The bacteria guard turns around and tells someone inside the castle to open the gate.");
                System.out.println("A shell lowers down, making a bridge over a stream of deadly garbage water that surrounds the castle.");
                System.out.println("After going through many halls, you reach the Queen.");
                System.out.println("[Queen Eubacteria] Hello, who is this? You're very ugly. I don't like you. Get them away from me, guards.");
                System.out.println("[Bacteria Guard] My Queen, this is a strong adventurer!");
                System.out.println("[Bacteria Guard] Their name is " + p.name + " and they have traveled far and wide just to meet you.");
                System.out.println("You don't really know what he's talking about but you go with it.");
                System.out.println("[Queen Eubacteria] Oh! Forget what I said, guards!");
                System.out.println("[Queen Eubacteria] You, " + p.name + "! Come over here.");
                System.out.println("[Queen Eubacteria] Recently, some nasty things have been infesting our wonderful garbage!");
                System.out.println("[Queen Eubacteria] We need adventurers like you to them them.");
                System.out.println("[Queen Eubacteria] If you navigate that garbage labyrinth and manage to defeat 10 of those creature, you will be given a great reward.");
                String queenChoice = game.makeChoice("Accept or refuse?", new String[]{"a", "r"});
                if (queenChoice.equals("a")) {
                    shoreChallenge = true;

                    System.out.println("[Queen Eubacteria] Splendid! Guard 12, give our new friend one of our own hand crafted Garbage Swords.");
                    p.addItem("garbage");
                    p.addWeapon("Garbage Sword");
                    System.out.println("[Queen Eubacteria] Now guards, escort the adventurer to the garbage wastes!\n");
                } else {
                    System.out.println("[Queen Eubacteria] Then get out! Guards take them away!");
                    System.out.println("[Bacteria Guard] How are you so stupid?\n");
                }
            }
            System.out.println("After walking around for a while you see a giant wall of garbage.");
            System.out.println("You enter the garbage labyrinth.");
            int shoreFoes = 10;
            while (true) {
                String garbageLabyrinth = game.makeChoice("Turn left or turn right?", new String[]{"l", "r"});
                if  (garbageLabyrinth.equals("l")) {
                    if (game.rng.nextDouble() < 0.5) {
                        game.encounterEnemy(foeTypes[game.rng.nextInt(foeTypes.length)]);
                        if (shoreChallenge) {
                            shoreFoes = shoreFoes - 1;
                            System.out.println("Creatures needed to be defeated: " + shoreFoes);
                            if (shoreFoes == 0);
                            System.out.println("You return back to the castle and tell a guard of your accomplishment.");
                            System.out.println("They let you in and bring you to the Queen.");
                            System.out.println("[Queen Eubacteria] Very good! Very good! Guards, bring in the reward.");
                            System.out.println("[Queen Eubacteria] Here is the Shell Scythe.");
                            p.addItem("shell");
                            p.addWeapon("Shell Scythe");
                            System.out.println("[Queen Eubacteria] It was created by my father, King Eubacteria.");
                            System.out.println("[Queen Eubacteria] He used it to fight off horrible creatures in the Shore War.");
                            System.out.println("[Queen Eubacteria] They could fly around and dodge almost anything.");
                            System.out.println("[Queen Eubacteria] We call them flies.");
                            System.out.println("[Queen Eubacteria] The flies almost ate all of our trash before my father and his army defeated them.");
                            System.out.println("[Queen Eubacteria] No one knows much about flies. They surely aren't bacteria.");
                            System.out.println("[Queen Eubacteria] I'm sure you can figure that out, " + p.name + ".");
                            System.out.println("[Queen Eubacteria] Now leave.");
                            System.out.println("The Queen points to the exit and you leave.");
                            System.out.println("You return back to the garbage wastes and adventure more.");
                        }
                    } else {
                        if (!p.has("key")) {
                            System.out.println("You see a rusty key in a pile of trash and pick it up.");
                            p.addItem("key");
                        } else {
                            System.out.println("There is a plastic spoon on the ground.");
                            System.out.println("You greet the plastic spoon.");
                        }

                    }

                } else {
                    if (game.rng.nextDouble() < 0.5) {
                        game.encounterEnemy(foeTypes[game.rng.nextInt(foeTypes.length)]);
                        if (shoreChallenge) {
                            shoreFoes = shoreFoes - 1;
                            System.out.println("Creatures needed to be defeated: " + shoreFoes);
                            if (shoreFoes == 0);
                            System.out.println("You return back to the castle and tell a guard of your accomplishment.");
                            System.out.println("They let you in and bring you to the Queen.");
                            System.out.println("[Queen Eubacteria] Very good! Very good! Guards, bring in the reward.");
                            System.out.println("[Queen Eubacteria] Here is the Shell Scythe.");
                            p.addItem("shell");
                            p.addWeapon("Shell Scythe");
                            System.out.println("[Queen Eubacteria] It was created by my father, King Eubacteria.");
                            System.out.println("[Queen Eubacteria] He used it to fight off horrible creatures in the Shore War.");
                            System.out.println("[Queen Eubacteria] They could fly around and dodge almost anything.");
                            System.out.println("[Queen Eubacteria] We call them flies.");
                            System.out.println("[Queen Eubacteria] The flies almost ate all of our trash before my father and his army defeated them.");
                            System.out.println("[Queen Eubacteria] No one knows much about flies. They surely aren't bacteria.");
                            System.out.println("[Queen Eubacteria] I'm sure you can figure that out, " + p.name + ".");
                            System.out.println("[Queen Eubacteria] Now leave.");
                            System.out.println("The Queen points to the exit and you leave.");
                            System.out.println("You return back to the garbage wastes and adventure more.");
                        }
                    } else {
                        if (game.rng.nextDouble() < 0.5) {
                            System.out.println("You see a giant wall of garbage towering above you.");
                            System.out.println("There is a small keyhole in the trash.");
                            if (p.has("key")) {
                                String keyUse = game.makeChoice("Do you want to use the key you found? You may not be able to turn back.", new String[]{"y", "n"});
                                if (keyUse.equals("y")) {
                                    System.out.println("You put the key in the keyhole and turn it.");
                                    System.out.println("A small opening in the garbage wall opens and you step through it.");
                                    System.out.println("When you get to the other side, it closes back up.");
                                    break;
                                } else {System.out.println("You don't use the key.");}
                            } else {System.out.println("If only you had a key...");}
                        } else {
                            if (!p.has("garbage")) {
                                System.out.println("In a heap of garbage, you see something shimmering.");
                                System.out.println("After digging though the trash, you find the Plastic Pike!");
                                p.addItem("plastic");
                                p.addWeapon("Plastic Pike");
                            } else {
                                System.out.println("There is a plastic fork on the ground.");
                                System.out.println("You greet the plastic fork.");}
                        }
                    }
                }

            }

        }
    }

    static class Mountain extends Scene {
        Mountain() {
            super("Mountain");
        }

        @Override
        void enter(Game game) {
            Player p = game.player;
            String[] foeTypes = {"Fungi-Like Fighter", "Plant-Like Puncher", "Animal-Like Attacker", };
            System.out.println("After going through the trashy gate, the first thing you see is a mountain.");
            System.out.println("This mountain isn't like the one you saw in the ocean, it is a real, huge, big, massive, gigantic mountain.");
            System.out.println("The only way to continue your journey is to make it over the mountain.");
            System.out.println("3 protista cells appear from behind a rock and jump towards you!");
            System.out.println("[Fungi-like Protist Protector] Welcome");
            System.out.println("[Plant-like Protist Protector] To");
            System.out.println("[Animal-like Protist Protector] The");
            System.out.println("[Fungi-like Protist Protector] Protista");
            System.out.println("[Plant-like Protist Protector] Mountain!");
            System.out.println("[Animal-like Protist Protector] If");
            System.out.println("[Fungi-like Protist Protector] You");
            System.out.println("[Plant-like Protist Protector] Can");
            System.out.println("[Animal-like Protist Protector] Reach");
            System.out.println("[Fungi-like Protist Protector] The");
            System.out.println("[Plant-like Protist Protector] Protista");
            System.out.println("[Animal-like Protist Protector] Peak,");
            System.out.println("[Fungi-like Protist Protector] Then-");
            System.out.println("[Plant-like Protist Protector] Wait, guys. This isn't working.");
            System.out.println("[Plant-like Protist Protector] I'll just explain this myself.");
            System.out.println("[Plant-like Protist Protector] We are the three Protista Protectors.");
            System.out.println("[Plant-like Protist Protector] We protect all of the eukaryotes beyond the Protista Peak.");
            System.out.println("[Plant-like Protist Protector] We will let you meet them, but only if you are worthy.");
            System.out.println("[Plant-like Protist Protector] Be warned that we are not going to let you pass easily, for we value the complexer cells more than our own lives.");
            System.out.println("[Plant-like Protist Protector] You will be tested of your...");
            System.out.println("[Fungi-like Protist Protector] Intelligence!");
            System.out.println("[Plant-like Protist Protector] Logic!");
            System.out.println("[Animal-like Protist Protector] And Perseverance!");
            System.out.println("[All Protist Protectors] Bye!");
            System.out.println("The three scurry back behind the rock, but when you go to look for them, they are gone.\n");
            System.out.println("After only a few minutes of walking, a cell attacks!");
            game.encounterEnemy(foeTypes[game.rng.nextInt(foeTypes.length)]);
            System.out.println("As you ascend the mountain, you find a dead leaf on the ground.");
            String deadLeafChoice = game.makeChoice("a. Talk to the leaf. b. Step on the stupid, ugly, dumb leaf.", new String[]{"a", "b"});
            if (deadLeafChoice.equals("a")) {
                System.out.println("You ask the leaf if it is alright.");
                System.out.println("[???] Oh. Thank you for asking. I'm fine.");
                System.out.println("[Chlorm] My name is Chlorm.");
                System.out.println("[Chlorm] One day, a fuzzy creature came up on to my tree and plucked me off.");
                System.out.println("[Chlorm] And the wind carried me here.");
                String leafOriginChoice = game.makeChoice("a. Could you tell me more about the creature? b. Could you tell me more about where you came from?", new String[]{"a", "b"});
                if (leafOriginChoice.equals("a")) {
                    System.out.println("[Chlorm] That fuzzy creature...");
                    System.out.println("[Chlorm] It was not a plant or fungus.");
                    System.out.println("[Chlorm] I have trouble remembering, but I know its cells were quite complex.");
                    System.out.println("[Chlorm] They didn't have chloroplasts or even cell walls.");
                    String leafOriginAChoice = game.makeChoice("a. What are chloroplasts? b. Are there more of those creatures?", new String[]{"a", "b"});
                    if (leafOriginAChoice.equals("a")) {
                        System.out.println("[Chlorm] Ah! Chloroplasts!");
                        System.out.println("[Chlorm] Chloroplasts are the organelles that allow us plants to get energy.");
                        System.out.println("[Chlorm] They are very important to plant culture.");
                        System.out.println("[Chlorm] Historians say that chloroplasts were once their own cells.");
                        System.out.println("[Chlorm] Then, other cells absorbed them, but they weren't destroyed.");
                        System.out.println("[Chlorm] The chloroplasts lived WITH the outer cell.");
                        System.out.println("[Chlorm] Eventually, there were plant-like protists.");
                        System.out.println("[Chlorm] Now, there are wonderful plants.");
                        System.out.println("[Chlorm] If you ever reach my home, you can learn about it more.");
                    } else {
                        System.out.println("[Chlorm] Yes, I know that.");
                        System.out.println("[Chlorm] An entire kingdom, in fact.");
                        System.out.println("[Chlorm] They aren't all fuzzy, but they can move around on their own.");
                        System.out.println("[Chlorm] They breathe in oxygen and exhale carbon dioxide.");
                        System.out.println("[Chlorm] So weird...");
                        System.out.println("[Chlorm] Maybe you can find that fuzzy creature along your journey.");
                        System.out.println("[Chlorm] It was called a... um... ");
                        System.out.println("[Chlorm] Squash?");
                    }
                } else {
                    System.out.println("[Chlorm] Oh, my home was wonderful.");
                    System.out.println("[Chlorm] I don't remember much about it, though.");
                    System.out.println("[Chlorm] It was very beautiful and calm.");
                    System.out.println("[Chlorm] There were many streams with clean running water.");
                    System.out.println("[Chlorm] Our scientists worked very hard to find ways to get energy.");
                    System.out.println("[Chlorm] That energy was used to power many things in our village.");
                    System.out.println("[Chlorm] I remember water was transported everywhere using that energy.");
                    System.out.println("[Chlorm] The water was nice and cool.");
                    System.out.println("[Chlorm] It was important to us, but it's hard to remember why.");
                    System.out.println("[Chlorm] Had something to do with getting energy for ourselves.");
                    System.out.println("[Chlorm] I don't know the exact details.");
                    String leafOriginBChoice = game.makeChoice("a. Where did the village's energy come from? b. Is there a way for you to return home?", new String[]{"a", "b"});
                    if (leafOriginBChoice.equals("a")) {
                        System.out.println("[Chlorm] It was in the center of the village.");
                        System.out.println("[Chlorm] In the main lab.");
                        System.out.println("[Chlorm] It was called the Great Chloroplast.");
                        System.out.println("[Chlorm] The scientists fed it materials and it would turn it in to energy.");
                        System.out.println("[Chlorm] I don't understand it.");
                    } else {
                        System.out.println("[Chlorm] I don't think so, friend.");
                        System.out.println("[Chlorm] I'm a dying leaf.");
                        System.out.println("[Chlorm] There isn't much time for me left, being seperated from the entire organism.");
                        System.out.println("[Chlorm] My friend, Algreeg, lives in the village.");
                        System.out.println("[Chlorm] He is very wise, but he isn't a plant!");
                        System.out.println("[Chlorm] Algreeg was actually from this mountain, in some pond.");
                        System.out.println("[Chlorm] He is a multicellular plant-like protist.");
                        System.out.println("[Chlorm] Maybe if you find him, he can help me, but I'm not too sure.");
                    }
                    System.out.println("[Chlorm] Anyways, thank you for speaking to me.");
                    System.out.println("[Chlorm] I know most would just leave a dying leaf like me alone, but you're different.");
                    System.out.println("[Chlorm] As my thanks, have this Sprout Slicer.");
                    p.addItem("sprout");
                    p.addWeapon("Sprout Slicer");
                    System.out.println("[Chlorm] It's a common tool in my village.");
                    System.out.println("[Chlorm] I'm sure it can aid you in your journey.\n");
                }
            } else {
                System.out.println("[???] Hey!");
                System.out.println("[???] That's so rude!");
                System.out.println("[???] Why would you step on a poor, dying leaf?");
                System.out.println("[???] That's not cool, man.");
                System.out.println("[???] I don't even know your name.");
                System.out.println("[Chlorm] My name is Chlorm by the way. Nice to meet you.");
                System.out.println("[Chlorm] Actually- No!");
                System.out.println("[Chlorm] It's not nice! You're not nice!");
                System.out.println("[Chlorm] Kids like you need to learn some respect...");
                game.encounterEnemy("Angry Chlorm");
            }
            System.out.println("After that leafy interaction, you keep on climbing.");
            System.out.println("You come across a statue of a mushroom. It is faintly glowing.");
            System.out.println("You touch the statue and a little door on the mushroom's stalk opens up.");
            System.out.println("The Fungi-Like Protist Protector comes out of the statue.");
            System.out.println("[Fungi-like Protist Protector] Hmmmmm...");
            System.out.println("[Fungi-like Protist Protector] So it seems that you have reached the first trial!");
            System.out.println("[Fungi-like Protist Protector] Here is your first question:");
            System.out.println("[Fungi-like Protist Protector] What two kingdoms DON'T have organelles?");
            String fungiQuestionOne = game.makeChoice("a. eubacteria and protista b. archaea and eubacteria c. archaea and protista ", new String[]{"a", "b", "c"});
            System.out.println("[Fungi-like Protist Protector] Alright... Now on for question two!!!");
            System.out.println("[Fungi-like Protist Protector] Which of these is found in every cell?");
            String fungiQuestionTwo = game.makeChoice("a. cell wall b. nucleus c. cytoplasm ", new String[]{"a", "b", "c"});
            System.out.println("[Fungi-like Protist Protector] Finally...");
            System.out.println("[Fungi-like Protist Protector] What you have all been waiting for...");
            System.out.println("[Fungi-like Protist Protector] The third question!!!");
            System.out.println("A group of fungi-like protists come out of the statue and do a short dance.");
            System.out.println("[Fungi-like Protist Protector] What is my favorite food?");
            String fungiQuestionThree = game.makeChoice("a. cookies b. pizza c. pasta ", new String[]{"a", "b", "c"});
            System.out.println("[Fungi-like Protist Protector] Okay... now to reveal if you passed the trial.");
            System.out.println("The fungi-like protists lean in closer, ready for the results.");
            System.out.println("[Fungi-like Protist Protector] Oooh. Too bad, buddy.");
            System.out.println("[Fungi-like Protist Protector] You didn't get all of the questions correct.");
            System.out.println("[Fungi-like Protist Protector] Maybe you can search around the mountain for some answers.");
            System.out.println("The fungi-like protists all go back inside the statue and close the door.");
            int protogeeKick = 0;
            boolean favoriteFood = false;
            while (true) {
                String fungiLoopChoice = game.makeChoice("What do you want to do? a. Retry the trial. b. Look around for answers. ", new String[]{"a", "b"});
                if (fungiLoopChoice.equals("a")) {
                    System.out.println("The Fungi-Like Protist Protector comes out of the statue.");
                    System.out.println("[Fungi-like Protist Protector] Let's do this!");
                    System.out.println("[Fungi-like Protist Protector] Here is your first question:");
                    System.out.println("[Fungi-like Protist Protector] What two kingdoms DON'T have organelles?");
                    fungiQuestionOne = game.makeChoice("a. eubacteria and protista b. archaea and eubacteria c. archaea and protista ", new String[]{"a", "b", "c"});
                    System.out.println("[Fungi-like Protist Protector] Alright... Now on for question two!!!");
                    System.out.println("[Fungi-like Protist Protector] Which of these is found in every cell?");
                    fungiQuestionTwo = game.makeChoice("a. cell wall b. nucleus c. cytoplasm ", new String[]{"a", "b", "c"});
                    System.out.println("[Fungi-like Protist Protector] Finally...");
                    System.out.println("[Fungi-like Protist Protector] What you have all been waiting for...");
                    System.out.println("[Fungi-like Protist Protector] The third question!!!");
                    System.out.println("A group of fungi-like protists come out of the statue and do a short dance.");
                    System.out.println("[Fungi-like Protist Protector] What is my favorite food?");
                    if (favoriteFood) {
                        fungiQuestionThree = game.makeChoice("a. cookies b. pizza c. pasta d. homemade bark-bites", new String[]{"a", "b", "c", "d"});
                    } else {
                        fungiQuestionThree = game.makeChoice("a. cookies b. pizza c. pasta ", new String[]{"a", "b", "c"});
                    }
                    System.out.println("[Fungi-like Protist Protector] Okay... now to reveal if you passed the trial.");
                    System.out.println("The fungi-like protists lean in closer, ready for the results.");
                    if (fungiQuestionOne.equals("b") && fungiQuestionTwo.equals("c") && fungiQuestionThree.equals("d")) {
                        System.out.println("[Fungi-like Protist Protector] Yes! You passed!");
                        break;
                    } else {
                        System.out.println("[Fungi-like Protist Protector] Oooh. Too bad, buddy.");
                        System.out.println("[Fungi-like Protist Protector] You didn't get all of the questions correct, but don't give up!");
                        System.out.println("[Fungi-like Protist Protector] Maybe you can search around the mountain for some answers.");
                        System.out.println("The fungi-like protists all go back inside the statue and close the door.");
                    }
                } else {
                    System.out.println("You look around and see two cells roaming around.");
                    String fungiInvestigateChoice = game.makeChoice("a. Talk to fungi-like protist b. Talk to animal-like protist ", new String[]{"a", "b"});
                    if (fungiInvestigateChoice.equals("a")) {
                        System.out.println("You go up to the old looking fungi-like protist.");
                        System.out.println("[Old Protee] Hello little youngin'. I'm Old Protee.");
                        System.out.println("[Old Protee] I am very old.");
                        System.out.println("[Old Protee] My son works at that statue over there.");
                        System.out.println("[Old Protee] Do you know him?");
                        System.out.println("[Old Protee] He always talks about the questions he likes asking.");
                        System.out.println("[Old Protee] Always with those questions.");
                        System.out.println("[Old Protee] He will come to my house and talk for hours about his new questions.");
                        System.out.println("[Old Protee] The little cell gets so hungry by talking so much, so I make him his favorite meal:");
                        System.out.println("[Old Protee] My homemade rotting bark-bites!");
                        System.out.println("[Old Protee] Here, you can have one if you like.");
                        p.addItem("bark-bite");
                        System.out.println("[Old Protee] Bye-Bye!");
                        favoriteFood = true;

                    } else {
                        System.out.println("You go up to the smart looking animal-like protist.");
                        if (protogeeKick == 0) {
                            System.out.println("[Lil' Protogee] Greetings! I'm Lil' Protogee.");
                            System.out.println("[Lil' Protogee] Some could say that I am above the average protist's intelligence.");
                            System.out.println("[Lil' Protogee] Although I lack physical strength, I pride myself in my intellect.");
                            System.out.println("[Lil' Protogee] I shall warn you...");
                            System.out.println("[Lil' Protogee] Don't mistake me for one of my animal-like protist siblings, for they are brutish compared to me.");
                            System.out.println("[Lil' Protogee] They are like those achaea and eubacteria cells while I am a true animalia.");
                            System.out.println("[Lil' Protogee] Total prokaryotes...");
                            System.out.println("[Lil' Protogee] One day, a cell came up to me.");
                            System.out.println("[Lil' Protogee] He said, 'Protogee, your are like a modern Leonardo Cellvinci.'");
                            System.out.println("[Lil' Protogee] It's true!");
                            System.out.println("[Lil' Protogee] While all cells have cytoplasm not all of them have organelles.");
                            System.out.println("[Lil' Protogee] It's sad, really.");
                            System.out.println("[Lil' Protogee] To have the medium without the brush.");
                            System.out.println("[Lil' Protogee] For my entire life, my superior intellect has alienated me from my peers.");
                            System.out.println("[Lil' Protogee] I was just too smart for them to handle, I suppose.");
                            System.out.println("[Lil' Protogee] No one could match me.");
                            System.out.println("[Lil' Protogee] Sometimes, I wish I was dumb, like you.");
                            System.out.println("[Lil' Protogee] To be freed from my gargantuan mind.");
                            System.out.println("[Lil' Protogee] I speak of metaphors that no one will understand.");
                            System.out.println("[Lil' Protogee] I ponder thoughts no other cell can even comprehend.");
                            System.out.println("[Lil' Protogee] I believe that one day, the more complex cells beyond this mountain will recognize me and take me along.");
                            System.out.println("[Lil' Protogee] To live among the greats.");
                            System.out.println("[Lil' Protogee] I know that is what I am destined for.");
                            System.out.println("[Lil' Protogee] Maybe they have forsaken me here as a test of willpower.");
                            System.out.println("[Lil' Protogee] Now, simpleton, leave me be.");
                            System.out.println("[Lil' Protogee] I can feel my mind shinking because of your presence.");
                            System.out.println("[Lil' Protogee] Salutations!");
                            String kickHimChoice = game.makeChoice("What do you want to do? a. Kick Lil' Protogee b. Don't ", new String[]{"a", "b"});
                            if (kickHimChoice.equals("a")) {
                                System.out.println("[Lil' Protogee] Hey!");
                                System.out.println("[Lil' Protogee] You better leave before I get angry!");
                                System.out.println("[Lil' Protogee] GrrRrRrrRRRrRrrRrr!");
                                protogeeKick = 1;
                            } else {
                                System.out.println("[Lil' Protogee] Wait.");
                                System.out.println("[Lil' Protogee] I just want to say something...");
                                System.out.println("[Lil' Protogee] I'm smarter than you.");
                            }
                        } else if (protogeeKick == 1) {
                            System.out.println("[Lil' Protogee] Hey!");
                            System.out.println("[Lil' Protogee] It's you!");
                            System.out.println("[Lil' Protogee] I'm sure you've learned you lesson by now.");
                            System.out.println("[Lil' Protogee] My friends say that when I get angry, I turn red.");
                            System.out.println("[Lil' Protogee] Actually, that's a lie.");
                            System.out.println("[Lil' Protogee] I don't have any friends...");
                            System.out.println("[Lil' Protogee] But I've heard that lying is a sign of intelligence.");
                            System.out.println("[Lil' Protogee] So, check-mate!");
                            System.out.println("[Lil' Protogee] Yeah, I play chess.");
                            System.out.println("[Lil' Protogee] I even made my own opening.");
                            System.out.println("[Lil' Protogee] I call it the West Siberian Photoautotrophic Gambit.");
                            System.out.println("[Lil' Protogee] It starts with moving the pawn in front of your queen forward.");
                            System.out.println("[Lil' Protogee] Then- here's the interesting part- you move another pawn forward.");
                            System.out.println("[Lil' Protogee] Any pawn!");
                            System.out.println("[Lil' Protogee] And that's it.");
                            System.out.println("[Lil' Protogee] Pretty ingenious, right?");
                            System.out.println("[Lil' Protogee] I know someone like you couldn't think of it.");
                            System.out.println("[Lil' Protogee] Oh? You want to know about prokaryotes and cell parts?");
                            System.out.println("[Lil' Protogee] How simple!");
                            System.out.println("[Lil' Protogee] I can hardly believe that someone doesn't know that.");
                            System.out.println("[Lil' Protogee] If someone told me that you existed, I wouldn't believe them.");
                            System.out.println("[Lil' Protogee] But I guess you're living evidence to my hypothesis.");
                            System.out.println("[Lil' Protogee] Indubitably!");
                            System.out.println("[Lil' Protogee] Wow.");
                            System.out.println("[Lil' Protogee] I can't stand living on this mountain for even another second.");
                            System.out.println("[Lil' Protogee] My intelligence is like air filling the small ballon that is this place.");
                            System.out.println("[Lil' Protogee] I have had enough of you.");
                            System.out.println("[Lil' Protogee] Begone.");
                            String kickNowChoice = game.makeChoice("What do you want to do? a. Push Lil' Protogee b. Shake Lil' Protogee ", new String[]{"a", "b"});
                            if(kickNowChoice.equals("a")) {System.out.println("You push Lil' Protogee to the ground."); }
                            else {System.out.println("You shake Lil' Protogee."); }
                            System.out.println("[Lil' Protogee] Oh, you've really done it now!");
                            System.out.println("[Lil' Protogee] Prepare to face my wrath and intellectual superiority!");
                            game.encounterEnemy("Lil' Protogee");
                            System.out.println("Lil' Protogee dropped his Bowtie of Annoyance");
                            p.addItem("bowtie");
                            protogeeKick = 2;
                        } else {
                            System.out.println("[Lil' Protogee] Get away from me, you barbarian!");
                        }
                    }

                }
            }
            System.out.println("[Fungi-like Protist Protector] Great job!");
            System.out.println("All of the fungi-like protists start jumping and cheering for you.");
            System.out.println("[Fungi-like Protist Protector] I'll go tell the other Protist Protectors that you passed.");
            System.out.println("[Fungi-like Protist Protector] Good luck!\n");
            System.out.println("You are now half way up the mountain!");
            System.out.println("You hear something in a hole and go check.");
            game.encounterEnemy(foeTypes[game.rng.nextInt(foeTypes.length)]);
            System.out.println("After that fight you see yet another hole.");
            String holesChoice = game.makeChoice("a. Keep on walking b. Look in the hole ", new String[]{"a", "b"});
            if(holesChoice.equals("a")) {System.out.println("You keep on walking, but you have the feeling that a powerful tool was in that hole.");}
            else {
                System.out.println("You look in the hole, but something attacks you! You will never learn...");
                game.encounterEnemy(foeTypes[game.rng.nextInt(foeTypes.length)]);
            }
            System.out.println("After seeing a lot of rocks, you see another statue!");
            System.out.println("It is in the shape of a flower. It is has a little glow.");
            System.out.println("When you touch it, the top of the flower starts to slowly spin.");
            System.out.println("The part of the flower that contains the polled starts to retract in to the flower stem.");
            System.out.println("The Plant-Like Protist Protector comes out of the top.");
            System.out.println("[Plant-Like Protist Protector] I heard from the other Protector that you passed his trial.");
            System.out.println("[Plant-Like Protist Protector] But I expected that.");
            System.out.println("[Plant-Like Protist Protector] In my trial, you have to only make one choice.");
            System.out.println("Two plant-like protists get launched out from the flower.");
            System.out.println("[Protist A] I'm Protist A!");
            System.out.println("[Protist B] I'm Protist B!");
            System.out.println("[Plant-Like Protist Protector] One of these cells must die!");
            System.out.println("[Plant-Like Protist Protector] You will be the one to chose who dies.");
            System.out.println("[Plant-Like Protist Protector] If you pick the correct option, you will pass the trial.");
            System.out.println("[Plant-Like Protist Protector] These cells will tell their accounts of the same day.");
            System.out.println("[Plant-Like Protist Protector] However, one of these cells will tell ONE lie and it is your job to figure out which one is lying.");
            System.out.println("[Plant-Like Protist Protector] In plant culture, lying is VERY bad.");
            System.out.println("[Plant-Like Protist Protector] It's worse than murder.");
            System.out.println("[Plant-Like Protist Protector] If you choose incorrectly, you will be killed along with your chosen cell.");
            System.out.println("[Plant-Like Protist Protector] Make sure to look for inconsistencies between the stories.");
            System.out.println("[Plant-Like Protist Protector] Anyways, let the cells tell their stories.");
            System.out.println("[Protist A] I woke up at 8:37 am. I know, I sleep in.");
            System.out.println("[Protist A] Then, I went to C.E.L. to get some milk for my cereal, because I was out of milk.");
            System.out.println("[Protist A] I got home at 10:12 am and ate my cereal.");
            System.out.println("[Protist A] At 1:25 pm, Protist B came over to my house in his cell-car.");
            System.out.println("[Protist A] Together we went to the cell-arcade, but midway through the journey, his car was out of gas!");
            System.out.println("[Protist A] After fixing that problem, we finally got to enjoy the cell-arcade.");
            System.out.println("[Protist A] It was so fun for me, but I don't think Protist B liked it as much.");
            System.out.println("[Protist A] We started driving back at 4:32 pm.");
            System.out.println("[Protist A] Luckily, his cell-car didn't stop this time!");
            System.out.println("[Protist A] When I got home at 5:02 pm, I watched cellovision until I fell asleep.");
            System.out.println("[Protist A] There was an ad for Celloyta, the electic cell-car company that made my friend's car.");
            System.out.println("[Plant-Like Protist Protector] Now on to Protist B!");
            System.out.println("[Protist B] I woke up at 7:00 am.");
            System.out.println("[Protist B] Next, I went on my morning walk. It ended at 8:48 am.");
            System.out.println("[Protist B] I got hungry so I ordered cell-pizza. It arrived at 10:02 am.");
            System.out.println("[Protist B] The delivery guy had an electric cell-car just like me.");
            System.out.println("[Protist B] I scrolled on CellTok for 3 hours.");
            System.out.println("[Protist B] Then, I went to pick up Protist A to go the the cell-arcade.");
            System.out.println("[Protist B] Unfortunately, we had to go and charge my car for 20 minutes!");
            System.out.println("[Protist B] I lost at the arcade games a lot, so I didn't have that good of a time.");
            System.out.println("[Protist B] I got home at 5:32 pm.");
            System.out.println("[Protist B] I got bored so I watched Celltopia 2 on Cellney+.");
            System.out.println("[Protist B] I finished it at 7:21 pm.");
            System.out.println("[Protist B] I scrolled on CellTok until I fell asleep.");
            System.out.println("[Plant-Like Protist Protector] You've heard their stories, so who do you think is the liar?");
            String executeChoice = game.makeChoice("What do you want execute? a. Protist A b. Protist B ", new String[]{"a", "b"});
            System.out.println("[Plant-Like Protist Protector] Very well!");
            if (executeChoice.equals("a")) {
                System.out.println("The flower statue turns towards Protist A and starts to shake...");
                System.out.println("[Protist A] Wait! Wait! I'm sorry!");
                System.out.println("The flower statue shoots out a rock at Protist A.");
                System.out.println("[Plant-Like Protist Protector] You have chosen correctly!");
                System.out.println("[Plant-Like Protist Protector] You put a horrible cell to justice.");
                System.out.println("[Plant-Like Protist Protector] Good job.");
                System.out.println("[Plant-Like Protist Protector] You have passed the trial.");
                System.out.println("[Plant-Like Protist Protector] Also, Protist A is fine!");
                System.out.println("[Plant-Like Protist Protector] Right, Protist A?");
                System.out.println("[Protist A] Where am I?");
                System.out.println("[Plant-Like Protist Protector] I will tell the last Protist Protector that you have passed my trial!");
                System.out.println("[Plant-Like Protist Protector] Good luck.\n");
            } else {
                System.out.println("The flower statue turns towards Protist B and starts to shake...");
                System.out.println("[Protist B] Wait! Wait! I didn't do it!");
                System.out.println("The flower statue shoots out a rock at Protist B.");
                System.out.println("[Plant-Like Protist Protector] You have chosen incorrectly!");
                System.out.println("[Plant-Like Protist Protector] A poor cell was killed because of you.");
                System.out.println("The flower statue turns towards you and starts to shake...");
                System.out.println("[Plant-Like Protist Protector] Protist B is fine, by the way.");
                System.out.println("[Plant-Like Protist Protector] That rock was fake.");
                System.out.println("[Plant-Like Protist Protector] But this one is real!");
                System.out.println("The flower statue shoots out a rock at you.");
                p.losehp(57);
            }
            System.out.println("You are close to the Protist Peak.");
            System.out.println("It is starting to get cold.");
            System.out.println("You see steam in the distance and run towards it.");
            System.out.println("It is coming from a warm pond which you hop in to.");
            System.out.println("The pond's warmth is very nice.");
            System.out.println("[???] Hey! Get out of our pond you animal!");
            System.out.println("[???] Wait. You aren't an animal-like protist.");
            System.out.println("[Albert] I'm so sorry, traveler. My name is Albert.");
            System.out.println("[Albert] Me and my family have been living in this pond for generations and those animal-like protists have been swimming in our pond recently.");
            System.out.println("[Albert] The animal-like protist trial used to be on the other side of the mountain, but they moved it to the top.");
            System.out.println("[Albert] It's hard to blame them, I guess.");
            System.out.println("[Albert] It's so cold at the Protista Peak.");
            System.out.println("[Albert] I apologize for what I said earlier.");
            System.out.println("[Albert] You are a guest here.");
            System.out.println("[Albert] Us algae should never be disrespectful.");
            System.out.println("[Albert] You should meet my family.");
            System.out.println("[Albert] This is my wife Tista.");
            System.out.println("[Tista] Hello. Many adventurers have come by here in the past.");
            System.out.println("[Tista] I never get the chance to ask them where they are going, though.");
            System.out.println("[Tista] What are you adventuring for?");
            String tistaChoice = game.makeChoice("a. For glory b. For fun ", new String[]{"a", "b"});
            if (tistaChoice.equals("a")) {
                System.out.println("[Tista] Wow. I assume you are trying to find some terrible monster and defeat it.");
                System.out.println("[Tista] Or save some people.");
                System.out.println("[Tista] What a respectable motive!");
            } else {
                System.out.println("[Tista] So you don't do anything?");
                System.out.println("[Tista] You just walk around and stuff?");
                System.out.println("[Tista] You don't help people?");
                System.out.println("[Tista] Alright...");
            }
            System.out.println("[Albert] And this is my daughter Algania.");
            System.out.println("[Algania] Hello adventurer!");
            System.out.println("[Algania] Have you seen my brother?");
            System.out.println("[Algania] His name is Algreeg.");
            System.out.println("[Algania] He left the Algae Pond to adventure like you.");
            System.out.println("[Algania] Algreeg said he was going to pass the trials and go to see real plants.");
            System.out.println("[Algania] I bet where real plants live is beautiful!");
            System.out.println("[Algania] Have you seen him during your adventure?");
            System.out.println("You tell her that you have not seen Algreeg.");
            System.out.println("[Algania] Well, I bet that you'll see him soon, then!");
            System.out.println("[Albert] If you see my son, tell him that we hope he comes back soon.");
            System.out.println("[Albert] Oh! I have something you can give to him.");
            System.out.println("Albert gives you a green bubble.");
            System.out.println("[Albert] This is an Algae Bubble.");
            System.out.println("[Albert] It uses algae power to contain sound.");
            System.out.println("[Albert] When you pop it, you can hear the message put in to it.");
            System.out.println("[Albert] Thank you. Good luck on your journey!");
            System.out.println("When you get out of the water, the cold shocks you.");
            System.out.println("Something falls from the sky and attacks you!");
            game.encounterEnemy(foeTypes[game.rng.nextInt(foeTypes.length)]);
            System.out.println("You keep on climbing...");
            System.out.println("At last, you have reached the Protista Peak.");
            System.out.println("In the distance you can see the Archaean Ocean, Bacteria Shore, and a vast field of mushrooms.");
            System.out.println("In front of you is a large statue of a chicken. It is glowing brightly.");
            System.out.println("You go up to it and touch the statue.");
            System.out.println("The beak starts to open and the Animal-Like Protist Protector jumps comes out.");
            System.out.println("[Animal-like Protist Protector] You have proven that you are very strong already.");
            System.out.println("[Animal-like Protist Protector] Now we will see if you are worthy.");
            System.out.println("[Animal-like Protist Protector] This is a trial of perseverance.");
            System.out.println("[Animal-like Protist Protector] I have seen many travelers get to here, but very few pass it.");
            System.out.println("[Animal-like Protist Protector] You must defeat 5 protists in a row.");
            System.out.println("[Animal-like Protist Protector] Defeating one is easy, but 5...");
            System.out.println("[Animal-like Protist Protector] It takes perseverance.");
            System.out.println("[Animal-like Protist Protector] Let us begin.");
            System.out.println("5 protists jump out of the chicken's beak.");
            game.encounterEnemy(foeTypes[game.rng.nextInt(foeTypes.length)]);
            System.out.println("[Animal-like Protist Protector] A good start...");
            game.encounterEnemy(foeTypes[game.rng.nextInt(foeTypes.length)]);
            System.out.println("[Animal-like Protist Protector] Keep the good work going...");
            game.encounterEnemy(foeTypes[game.rng.nextInt(foeTypes.length)]);
            System.out.println("[Animal-like Protist Protector] Over half way done...");
            game.encounterEnemy(foeTypes[game.rng.nextInt(foeTypes.length)]);
            System.out.println("[Animal-like Protist Protector] Don't give up...");
            game.encounterEnemy(foeTypes[game.rng.nextInt(foeTypes.length)]);
            System.out.println("[Animal-like Protist Protector] Very good!");
            System.out.println("[Animal-like Protist Protector] You are certainly worthy.");
            System.out.println("[Animal-like Protist Protector] The complexer eukaryotes will welcome you.");
            System.out.println("[Animal-like Protist Protector] Now, follow me.");
            System.out.println("You both go inside the beak of the chicken statue.");
            System.out.println("Inside, there is a staircase going down.");
            System.out.println("[Animal-like Protist Protector] At the bottom of these stairs, there is an elevator.");
            System.out.println("[Animal-like Protist Protector] A protist fighter will lower you down to the bottom of the mountain.");
            System.out.println("[Animal-like Protist Protector] From there, you will be on the other side of the mountain.");
            System.out.println("[Animal-like Protist Protector] You will reach the Fungi Fields.");
            System.out.println("All of the Protist Protectors come up to you.");
            System.out.println("[All Protist Protectors] Good luck!");
            System.out.println("You go down the stairs and hop inside the elevator.");
            System.out.println("You descend to the bottom of the mountain.");
            System.out.println("At the bottom, you see a hole in the inner mountain. It is very bright.");
            System.out.println("You walk through it in to the Fungi Fields.");





        }
    }

    static class Field extends Scene {
        Field() {
            super("Field");
        }

        @Override
        void enter(Game game) {
            Player p = game.player;
            String[] foeTypes = {"Shroomy Virus" , "Spore Cloud", "Mycelium Monster", "Moldy Monster"};

            System.out.println("After walking for a while, you realize that you are lost.");
            System.out.println("All of the mushrooms look the same!");
            System.out.println("Then, from behind a shroom, you see something...");
            game.encounterEnemy(foeTypes[game.rng.nextInt(foeTypes.length)]);
            System.out.println("That was unlucky.");
            System.out.println("Then, from behind another shroom, you see someone...");
            System.out.println("[Mushy] Hi, friend! My name is Mushy!");
            System.out.println("[Mushy] Are you lost? You seem very lost.");
            System.out.println("[Mushy] Don't worry, for I know this place inside and out!");
            System.out.println("Mushy starts guiding the way and you follow.");
            System.out.println("[Mushy] You clearly aren't a fungi cell like me.");
            System.out.println("[Mushy] I bet you came from that mountain over there.");
            System.out.println("[Mushy] They're pretty dumb with their trials and all.");
            System.out.println("[Mushy] I don't see why they need them.");
            System.out.println("[Mushy] Anyways, I'm bringing you to my home.");
            System.out.println("[Mushy] Most fungi don't have homes. We just eat stuff and wander around through the fields.");
            System.out.println("[Mushy] The place we're going to is the Mushroom Market.");
            System.out.println("[Mushy] I spend a lot of time there.");
            System.out.println("[Mushy] If you're trying to find adventure, it's the place.");
            System.out.println("[Mushy] Wait what's that?");
            game.encounterEnemy(foeTypes[game.rng.nextInt(foeTypes.length)]);
            System.out.println("[Mushy] You saved me!");
            System.out.println("[Mushy] For doing that, here's 5 Sporebucks.");
            System.out.println("[Mushy] You don't know what Sporebucks are?");
            System.out.println("[Mushy] Sporebucks is the currency in the Mushroom Market.");
            System.out.println("[Mushy] There are games you can play to get more.");
            System.out.println("[Mushy] With Sporebucks you can get a lot of things.");
            System.out.println("[Mushy] The biggest prize is a ticket in to the Grand Shroom Chamber.");
            System.out.println("[Mushy] Inside there is the Grand Shroom.");
            System.out.println("[Mushy] He is the oldest mushroom around.");
            System.out.println("[Mushy] He has connection to like everywhere through mycelium!");
            System.out.println("[Mushy] Maybe he can help you with your journey.");
            System.out.println("You and Mushy eventually arrive at the Mushroom Market.");
            int sporebucks = 5;
            System.out.println("So there was going to be like games and stuff but I cou;d pn;y get one done so its gone now");
            System.out.println("Pretend you got enough Sporebucks to meet that bug mushroom guy and get transported to the plant area.");
            System.out.println("Bye");
            
                     
        }
    }
    static class Stump extends Scene {
        Stump() {
            super("Stump");
        }

        @Override
        void enter(Game game) {
            Player p = game.player;
            System.out.println("You wake up in a small village.");
            System.out.println("There are plants everywhere and the village is surrounded by a wooden wall.");
            System.out.println("You go in to town and meet a plant scientist.");
            System.out.println("[Petalologist] Quick! Quick! You need to help us!");
            System.out.println("[Petalologist] An animal has attacked our plant village and destroyed the Great Chloroplast!");
            System.out.println("[Petalologist] Without it, the village has no power.");
            System.out.println("[Petalologist] We won't survive!");
            System.out.println("He takes you to the Great Nucleus.");
            System.out.println("There, the head scientist explains the problem.");
            System.out.println("[Head Scientist Leafbo] Hello, I am Head Scientist Leafbo.");
            System.out.println("[Head Scientist Leafbo] Last night, a horrible creature known as a rabbit crashed in to the village. ");
            System.out.println("[Head Scientist Leafbo] The Great Nucleus consists of two parts: the Chlorometer and the Nucloizer 2000.");
            System.out.println("[Head Scientist Leafbo] The Chlorometer acts like a chloroplast and takes in carbon dioxide and water and turns it into glucose and oxygen.");
            System.out.println("[Head Scientist Leafbo] The Nucloizer 200 acts like a nucleus and takes in glucose and oxygen and turns it in to water, carbon dioxide and ATP energy.");
            System.out.println("[Head Scientist Leafbo] Unfortunately, the materials got messed up and no one remembers how to construct them!");
            System.out.println("[Head Scientist Leafbo] I bet you know, though.");
            System.out.println("[Head Scientist Leafbo] We will start the machine and you just tell us what to put in.");
            while (true) {
                System.out.println("[Head Scientist Leafbo] First, let's make a single carbon dioxide molecule.");
                String greatNucleus1 = game.makeChoice("a. C2O b. CO2 c. H2O ", new String[]{"a", "b", "c"});
                if  (greatNucleus1.equals("b")) {
                    System.out.println("[Head Scientist Leafbo] That's it! CO2 is running.");
                    break;
                }
                else {System.out.println("[Head Scientist Leafbo] Oh that's not it! We need to start again.");}
            }
            while (true) {
                System.out.println("[Head Scientist Leafbo] Next, let's make a single water molecule.");
                String greatNucleus2 = game.makeChoice("a. HO2 b. O2 c. H2O ", new String[]{"a", "b", "c"});
                if  (greatNucleus2.equals("c")) {
                    System.out.println("[Head Scientist Leafbo] That's it! H20 is running.");
                    break;
                }
                else {System.out.println("[Head Scientist Leafbo] Oh that's not it! We need to start again.");}
            }
            while (true) {
                System.out.println("[Head Scientist Leafbo] Next, let's make a single glucose molecule.");
                String greatNucleus3 = game.makeChoice("a. C6H12O6 b. C12H6O6 c. C6H6O12 ", new String[]{"a", "b", "c"});
                if  (greatNucleus3.equals("a")) {
                    System.out.println("[Head Scientist Leafbo] That's it! C6H12O6 is running.");
                    break;
                }
                else {System.out.println("[Head Scientist Leafbo] Oh that's not it! We need to start again.");}
            }
            while (true) {
                System.out.println("[Head Scientist Leafbo] Finally, let's make a single oxygen molecule.");
                String greatNucleus4 = game.makeChoice("a. O2 b. 2O c. O6 ", new String[]{"a", "b", "c"});
                if  (greatNucleus4.equals("a")) {
                    System.out.println("[Head Scientist Leafbo] That's it! O2 is running.");
                    break;
                }
                else {System.out.println("[Head Scientist Leafbo] Oh that's not it! We need to start again.");}
            }
            while (true) {
                System.out.println("[Head Scientist Leafbo] Now, how many water molecules go in to the Chlorometer?");
                String greatNucleus5 = game.makeChoice("a. 1 b. 12 c. 6 ", new String[]{"a", "b", "c"});
                if  (greatNucleus5.equals("c")) {
                    System.out.println("[Head Scientist Leafbo] That's it! Sending in 6.");
                    break;
                }
                else {System.out.println("[Head Scientist Leafbo] Oh that's not it! We need to start again.");}
            }
            while (true) {
                System.out.println("[Head Scientist Leafbo] Now, how many carbon dioxide molecules go in to the Chlorometer?");
                String greatNucleus6 = game.makeChoice("a. 6 b. 3 c. 2 ", new String[]{"a", "b", "c"});
                if  (greatNucleus6.equals("a")) {
                    System.out.println("[Head Scientist Leafbo] That's it! Sending in 6.");
                    break;
                }
                else {System.out.println("[Head Scientist Leafbo] Oh that's not it! We need to start again.");}
            }
            while (true) {
                System.out.println("[Head Scientist Leafbo] Now, how many glucose molecules go in to the Nucloizer 2000?");
                String greatNucleus7 = game.makeChoice("a. 6 b. 1 c. 12 ", new String[]{"a", "b", "c"});
                if  (greatNucleus7.equals("b")) {
                    System.out.println("[Head Scientist Leafbo] That's it! Sending in 1.");
                    break;
                }
                else {System.out.println("[Head Scientist Leafbo] Oh that's not it! We need to start again.");}
            }
            while (true) {
                System.out.println("[Head Scientist Leafbo] Finally, how many oxygen molecules go in to the Nucloizer 2000?");
                String greatNucleus8 = game.makeChoice("a. 6 b. 2 c. 4 ", new String[]{"a", "b", "c"});
                if  (greatNucleus8.equals("a")) {
                    System.out.println("[Head Scientist Leafbo] That's it! Sending in 6.");
                    break;
                }
                else {System.out.println("[Head Scientist Leafbo] Oh that's not it! We need to start again.");}
            }
            System.out.println("[Head Scientist Leafbo] We did it! The cycle is flowing again.");
            System.out.println("[Head Scientist Leafbo] I will call the mayor to get here right away!");
            System.out.println("The mayor is in water in a cart. He is being moved in my plantae agents.");
            System.out.println("[Mayor Algreeg] I am mayor Algreeg and I want to thank you for you efforts.");
            System.out.println("You tell Algreeg about his family.");
            System.out.println("[Mayor Algreeg] I thought they were following me! I will send someone to get them right away!");
            if (p.has("slicer")) {
                System.out.println("Pretend you got enough Sporebucks to meet that bug mushroom guy and get transported to the plant area.");
            }

        }
    }

    static class Nucleus extends Scene {
        Nucleus() {
            super("Nucleus");
        }

        @Override
        void enter(Game game) {
            Player p = game.player;
            System.out.println("You reach the Nucleus Membrane.");
            if (p.has("Sludge")) {
                System.out.println("The Nucleus Membrane notices the sludge and lets it into the Nucleus.");
                System.out.println("You enter the Nucleus and see the cool DNA.");
                System.out.println("After your long journey, you are very hungry and you eat the DNA like spaghetti.");
                System.out.println("You win.");
            } else {
                System.out.println("The Nucleus Membrane does not recognize you and blocks your path.");
                System.out.println("You are pushed back into the cytoplasm and drift away...");
                System.out.println("You die.");
                p.gameOver = true;
            }
        }
    }


    static class Game {
        final Scanner in = new Scanner(System.in);
        final Random rng = new Random();
        Player player;

        final List<Scene> availableScenes = Arrays.asList(new Ocean(), new Shore(), new Mountain());
        final Scene finalScene = new Nucleus();

        String makeChoice(String prompt, String[] options) {
            List<String> opts = new ArrayList<>();
            for (String o : options) {
                opts.add(o.toLowerCase());
            }
            String optsDisplay = String.join("/", options);

            while (true) {
                System.out.println(prompt + " (" + optsDisplay + ") ");
                String ans = in.nextLine().trim().toLowerCase();
                if (opts.contains(ans)) {
                    return ans;
                }
                System.out.println("Please choose one of: " + String.join(", ", opts));
            }
        }

        void startGame() {
            System.out.println("\nWelcome to Nucleus Quest!");
            System.out.print("What is your name? ");
            String name = in.nextLine().trim();
            if (name.isEmpty()) {
                name = "Kary";
            }
            player = new Player(name);
            System.out.println("Hello, " + player.name + "! Your adventure begins now...\n");
        }

        void encounterEnemy(String kind) {
            Player p = player;
            Enemy enemy = new Enemy(kind);
            System.out.println("Oh no! A " + enemy.kind + " appears!");
            System.out.println("Your weapons: " + p.weapons);
            int foeHealth = 10;

            while (!p.gameOver) {
                String action = makeChoice("Do you want to fight or protect?", new String[]{"f", "p"});
                if (action.equals("f")) {
                    if (p.has("bubble")) {
                        System.out.println("You slash the " + enemy.kind + " with the power of burning hot bubbles!");
                        break;

                    } else if (p.has("garbage")) {
                        System.out.println("You slash the " + enemy.kind + " with the power of pungent garbage!");
                        break;

                    } else if (p.has("plastic")) {
                        System.out.println("You slash the " + enemy.kind + " with the power of sharp plastic!");
                        break;
                    } else {
                        System.out.println("You tried to fight, but you didn't have a weapon!");
                        System.out.println("The " + enemy.kind + " attacks you as it flees!");
                        p.losehp(2);
                        break;
                    }
                } else {
                    System.out.println("The " + enemy.kind + " flees!");

                    break;

                }
            }
        }

        void play() {
            while (true) {
                startGame();

                List<Scene> path = new ArrayList<>(availableScenes);


                for (Scene scene : path) {
                    scene.enter(this);
                    if (player.gameOver) {
                        break;
                    }
                }

                if (!player.gameOver) {
                    finalScene.enter(this);
                }

                String again = makeChoice("Do you want to play again?", new String[]{"y", "n"});
                if (again.equals("n")) {
                    System.out.println("Thank you for playing!");
                    break;
                }
            }
        }
    }


    public static void main(String[] args) {
        new Game().play();
    }
}
// 1. Archaebacteria "The Archean Ocean" 2. Eubacteria "Beach" 3. Protista 4. Fungi 5. Plantae 6. Animalia "Tree"
// bubble -> "Bubble Blade"
