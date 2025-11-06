import java.util.*;

public class NucleusQuest {
    static class Player {
        final String name;
        int health = 10;
        final Set<String> inventory = new HashSet<>();
        boolean gameOver = false;

        Player(String name) {this.name = name;}

        void addItem(String item) {inventory.add(item.toLowerCase());}

        void has(String item) {return inventory.contains(item.toLowerCase( )); }

        void losehp(int amount) {
            health -= amount;
            System.out.println(amount + " health points have been lost. Health remaining remaining: " + health);
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

        Enemy(String kind) {this(kind, 3, 2)};

        Enemy(String kind, int health, int damage) {
            this.kind = kind;
            this.health = health;
            this.damage = damage;

        }

        void Attack(Player player) {
            System.out.println("The " + type + "attacks!");
            player.losehp(damage);
        }
    }

    static abstract class Scene {
        final String name;
        Scene(String name) { this.name = name;}
        abstract void enter(Game game);
    }

    static class Ocean extends Scene {
        Ocean() { super("Ocean");}

        @Override
        void enter(Game game) {
            Player p = game.player;
            String[] foeTypes = {"Sandy Virus", "Deep Sea Worm", "Microscopic Shark"};
            System.out.println("You wake up alone at the bottom the ocean.");
            System.out.println("There is nothing around you except for a shimmering bubble.");
            String choice = game.makeChoice("Pop the bubble?", new String[]{"y", "n"});
            if (choice.equals("y")) {
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
            String choice = game.makeChoice("Enter the shelter?", new String[]{"y", "n"});
            if (choice.equals("y")) {
                System.out.println("You enter the shelter and see an archea cell sitting on a rock surrounded by rocky decorations and furniture.");
                System.out.println("[???] Hello! You must be tired from wandering around these horrible wastelands.");
                System.out.println("[???] Please, stop here and rest. It is very lonely living this deep and I could use some company.");
                System.out.println("[Archie] My name is Archie. I have lived here for a long time. Just me and my rocks.");
                System.out.println("[Archie] While I get us something to eat, do you have any questions?");
                String choice = game.makeChoice("a. Could you tell me more about yourself? b. What is this place?", new String[]{"a", "b"});
                if (choice.equals("a")) {
                    System.out.println("[Archie] Well, I am apart of the ancient Archea Clan.");
                    System.out.println("[Archie] Archea cells are some of the oldest cells around and we have a rich culture.");
                    System.out.println("[Archie] Our ancestors, protocells, came before any cell.");
                    System.out.println("[Archie] ");
                }

            }
        }

        static class Game {
            final Scanner in = new Scanner(System.in);
            final Random rng = new Random();
            Player player;
            final List<Scene> availableScenes = Arrays.asList(new Ocean());

            String makeChoice(String prompt, String[] options) {
                List<String> opts = new ArrayList<>();
                for (String o : options) opts.add(o.toLowerCase());
                String optsDisplay = String.join("/", options);

                while(true) {
                    System.out.println(prompt + " (" + optsDisplay + ") ");
                    String ans = in.nextLine().trim().toLowerCase();
                    if (opts.contains(ans)) return ans;
                    System.out.println("Please choose one of: " + String.join(", ", opts));
                }
            }

            void startGame() {
                System.out.println("\nWelcome to Nucleus Quest!");
                System.out.print("What is your name? ");
                String name = in.nextLine().trim();
                if (name.isEmpty()) name = "Kary";
                player = new Player(name);
                System.out.println("Hello, " + player.name + "! Your adventure begins now...\n");
            }

            void encounterEnemy(String kind) {
                Player p = player;
                Enemy enemy = new Enemy(kind);
                System.out.println("Oh no! A " + enemy.kind + " attacks you!");
                while (enemy.health > 0 && !p.gameOver) {
                    String action = makeChoice("Do you want to fight or run?", new String[]{"f", "r"});
                    if (action.equals("f")) {
                        if (p.has("bubble")) {
                            System.out.println("You slash the " + enemy.kind + " with the power of burning hot bubbles!");
                            System.out.println("The " + enemy.kind + " runs away!\n");
                            break;
                        } else {
                            System.out.println("You tried to fight, but you didn't have a weapon!");
                            System.out.println("The " + enemy.kind + " runs away!");
                            p.losehp(2);
                            break;
                        }
                    } else {
                        System.out.println("It was close, but you got away safely!\n");
                        break;
                    }
                }
            }
        }
}
}

// 1. Archaebacteria "The Archean Ocean" 2. Eubacteria 3. Protista 4. Fungi 5. Plantae 6. Animalia
// bubble -> "Bubble Blade"