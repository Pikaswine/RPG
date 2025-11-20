import java.util.*;


//prisca fixed
public class cohesionQuest {


    static class Player {
        final String name;
        int health = 10;
        final Set<String> inventory = new HashSet<>();
        boolean gameOver = false;

        Player(String name) {
            this.name = name;
        }

        void addItem(String item) {
            inventory.add(item.toLowerCase());
        }

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
        int health;
        int damage;

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
            String[] foeTypes = {"Sandy Virus", "Deep Sea Worm", "Microscopic Shark"};

            System.out.println("You wake up alone at the bottom of the ocean.");
            System.out.println("There is nothing around you except for a shimmering bubble.");
            String bubbleChoice = game.makeChoice("Pop the bubble?", new String[]{"y", "n"});

            if (bubbleChoice.equals("y")) {
                System.out.println("Inside the bubble was the Bubble Blade!");
                p.addItem("bubble");
                System.out.println("The pop has disturbed a creature underneath the sand.");
                game.encounterEnemy("Sandy Virus");
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

        final List<Scene> availableScenes = Arrays.asList(
                new Ocean()
        );
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
            while (enemy.health > 0 && !p.gameOver) {
                String action = makeChoice("Do you want to fight or run?", new String[]{"f", "r"});
                if (action.equals("f")) {
                    if (p.has("bubble")) {
                        System.out.println("You slash the " + enemy.kind + " with the power of burning hot bubbles!");
                        System.out.println("The " + enemy.kind + " runs away!\n");
                        break;
                    } else {
                        System.out.println("You tried to fight, but you didn't have a weapon!");
                        System.out.println("The " + enemy.kind + " attacks you as it flees!");
                        p.losehp(2);
                        break;
                    }
                } else {
                    System.out.println("It was close, but you got away safely!\n");
                    break;
                }
            }
        }

        void play() {
            while (true) {
                startGame();

                List<Scene> path = new ArrayList<>(availableScenes);
                Collections.shuffle(path, rng);

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