import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Comparator;
class Inventory{
    static final int SIZE = 20;
    static int lastindex = 0;
    static String inventory[] = new String[SIZE];

    Inventory(){
        for(int i=0;i<SIZE;i++)
        {inventory[i] = "Empty";}
        resortInventory();

    }//End of Inventory constactor
    public void printInventory(){
        resortInventory();
        System.out.println("");
        System.out.print(" ║ \uD83C\uDF92 Inventory:( ");
        //System.out.println("lastindex: "+ lastindex);
        for (int i = 0; i < lastindex; i++) {
            System.out.print(inventory[i]);
            if (i < lastindex - 1) {
                System.out.print(" | ");
            }
        }
        System.out.print(" )\n");
    }


    static public void resortInventory() {
        // Custom comparator to sort "Empty" to the end
        Comparator<String> customComparator = (a, b) -> {
            if (a.equals("Empty") && b.equals("Empty")) {
                return 0; // Leave elements with "Empty" values in their original order
            } else if (a.equals("Empty")) {
                return 1; // Move "Empty" values to the end
            } else if (b.equals("Empty")) {
                return -1; // Keep non-"Empty" values first
            } else {
                return a.compareTo(b); // Compare non-"Empty" values
            }
        };

        // Sort the array using Arrays.sort and the custom comparator
        Arrays.sort(inventory, customComparator);

        // Update lastindex to the last non-empty index
        lastindex = SIZE - 1;
        while (lastindex >= 0 && inventory[lastindex].equals("Empty")) {
            lastindex--;
        }
        lastindex++;
    }

    public static boolean isEmpty(){
        for (String element : inventory) {
            if (!element.equals("Empty")) {
                return false; // If any element is not "Empty", return false
            }
        }
        return true; // All elements are "Empty"
    }


}//End of inv
class Player extends Inventory{

    static final int equipmentBuildSize = 5;
    static String EquipmentBuild[] = new String[5];
    public static String userName = "Unknown User";
    public static int level = 1;
    private static int exp = 0;
    private static int health = 100;
    private static int gold = 30;
    private static int attack = 0;
    static int equipmentLastIndex = 0;

    public Player(){
        for(int i=0;i<EquipmentBuild.length;i++)
        {EquipmentBuild[i] = "Empty";}
    }

    //Cacluate total attack
    static public void resortEquipment(){
        /*int emptyIndex = equipmentLastIndex;
        for (int i = equipmentLastIndex - 1; i >= 0; i--) {
            if (EquipmentBuild[i].equals("Empty")) {
                // Swap elements to move "Empty" to the last position
                String temp = EquipmentBuild[i];
                EquipmentBuild[i] = EquipmentBuild[emptyIndex - 1];
                EquipmentBuild[emptyIndex - 1] = temp;

                emptyIndex--;
            }*/
        int tempAttack = 0;
        for(int i=0;i < EquipmentBuild.length;i++)
        {
            if(EquipmentBuild[i] == Guild.Equipment_TYPES[i])
            {
                tempAttack += Guild.Equipment_ATTACK_STATS[i];
            }
        }
        //System.out.println("Attack from reEq: "+tempAttack); //Error check
        attack = (tempAttack)*level;


        /*equipmentLastIndex = equipmentBuildSize - 1;  // Set lastindex to the last index of the array
        for (int i = 0; i < equipmentBuildSize; i++) {
            if (EquipmentBuild[i] != "Empty") {
                continue;
            }else{equipmentLastIndex = i;}  // Update lastindex to the last empty index
        }*/

    }
    public void printPanel(){
        updatePlayer();
        System.out.println("\n \033[1;36m╔════════════════════════════════════════════════════════════════════════════════════════════════════╗\033[0m");
        System.out.println(" \033[1;36m║                                      Player Panel                                                  ║\033[0m");
        System.out.println(" \033[1;36m║════════════════════════════════════════════════════════════════════════════════════════════════════║\033[0m");
        System.out.println("\033[1;36m ║\033[0m \uD83E\uDDB8\u200D♂\uFE0F Hero: \033[1;37m" + userName + "\033[0m");
        System.out.println("\033[1;36m ║\033[0m \033[1;34mPROGRESS\033[0m"); // Blue color for PROGRESS
        System.out.println("\033[1;36m ║\033[0m \uD83C\uDFC5 Level: \033[1;37m" + level +"\033[0m");
        System.out.print("\033[1;36m ║\033[0m ✨ Experience: \033[1;37m" + exp + "/"+  (level*100)+ "\033[0m [\033[1;32m");

        for (int i = 0; i < 20; i++) {
            if (i < (exp * 20) / (level * 100)) {
                System.out.print("█"); // Filled part of the bar
            } else {
                System.out.print("░"); // Empty part of the bar
            }
        }
        System.out.print("\033[0m]");

        System.out.println("\n\033[1;36m ║ \033[1;32mSTATS\033[0m"); // Green color for STATS
        System.out.println("\033[1;36m ║\033[0m ❤\uFE0F Health: \033[1;31m" + health + "/100\033[0m");
        System.out.println("\033[1;36m ║\033[0m \uD83D\uDDE1\uFE0F Attack: \033[1;37m" + attack + "\033[0m");
        System.out.println("\033[1;36m ║\033[0m \uD83D\uDCB0 Gold: \033[1;33m" + gold + "\033[0m"); // Yellow color for Gold

        System.out.print("\033[1;36m ║ \033[1;35mArmor\033[0m: ");
        for (int i = 0; i < EquipmentBuild.length; i++) {
            System.out.print((i + 1) + ".\033[1;35m" + EquipmentBuild[i] + "\033[0m ");
        }
        printInventory();

        System.out.println("\033[1;36m ╚════════════════════════════════════════════════════════════════════════════════════════════════════╝\033[0m");



    }//End of panel
    public void useLifeFountain()
    {
        final int LIFEFOUNTAINCOST = 25; // cost for using the Life Fountain

        System.out.println("\n ╔════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        if (gold < LIFEFOUNTAINCOST){System.out.println(" ║ Not enough gold to use the Life Fountain. It costs " + LIFEFOUNTAINCOST + " gold.                                        ║");}
        else{
            if (health == 100){System.out.println(" ║ Health is at maximum ('" + health + "'), so you cannot use the Life Fountain!                                 ║");}
            else if (health >= 80)
            {gold -= LIFEFOUNTAINCOST; health = 100;
                System.out.println(" ║ Used the Life Fountain. Health increased by 20 points.");
                System.out.println(" ║ Cost: " + LIFEFOUNTAINCOST + " gold. Remaining Gold: " + gold);
            }
            else{gold -= LIFEFOUNTAINCOST; health += 20;
                System.out.println(" ║ Used the Life Fountain. Health increased by 20 points.");
                System.out.println(" ║ Cost: " + LIFEFOUNTAINCOST + " gold. Remaining Gold: " + gold);}
        }
        System.out.println(" ╚════════════════════════════════════════════════════════════════════════════════════════════════════╝");
    }//end of life fountain

    //getters and setters
    public int getLevel(){return level;}
    public int getGold(){return gold;}
    public int getExp(){return exp;}
    public int getAttack(){return attack;}
    public int getHealth(){return health;}
    public void setGold(int goldy){this.gold = goldy;}
    public void setExp(int expy){this.exp = expy;}
    public void setHealth(int healthy){this.health = healthy;}
    public void setName(String name){ this.userName = name;
        System.out.println("Player name set to: " + this.userName);} //End of getters and setters T^-^
    public void putEquipment(){
        Scanner input = new Scanner(System.in);
        int choice = 0;
        String temp = " ";
        int size = inventory.length;

        //Check if lastindex != 0 so that the user wouldn't be stuck having an empty Inventory, forced to delete an item from his build
        resortInventory();
        if(lastindex != 0) {
            //System.out.println("Lastindex: "+lastindex);
            //Print the used equipment
            System.out.println("╔═════════════════════ The Equipments you are currently using ═════════════════════╗");
            for(int i = 0; i < EquipmentBuild.length; i++)
            {System.out.println("║"+(i+1)+". Element: " + EquipmentBuild[i]);}
            System.out.println("║════════════════════════ The Equipments in Your Inventory ════════════════════════╝");
            resortInventory();

            System.out.print("║(");
            for (int i = 0; i < lastindex; i++) {
                System.out.print("" + (i + 1) + ". Element: " + inventory[i]);
                if (i < lastindex - 1) {
                    System.out.print(" ║ ");
                }
            }
            System.out.print(") "); //System.out.print("lastindex: " + lastindex);
            System.out.println("\n╚══════════════════════════════════════════════════════════════════════════════════╝");

            if((EquipmentBuild[0] == "Empty" || EquipmentBuild[1] == "Empty" || EquipmentBuild[2] == "Empty" || EquipmentBuild[3] == "Empty" || EquipmentBuild[4] == "Empty")){
                //Take user input choice-1
                System.out.print("\nChoose the equipment you wish to equip to you build: ");
                choice = input.nextInt();
                choice--;

                System.out.print("\nChoose the part you wish to add it to: ");
                int placement = input.nextInt();
                placement--;

                // Check if the choice is within the valid range (Add check to not add empty)
                if (choice < 0 || choice >= inventory.length || choice > lastindex) {
                    System.out.println("\n[Invalid choice: Please enter a valid index]");
                    return;  // Exit the method if the choice is invalid
                }else if (inventory[choice] == "Empty") {
                    System.out.println("\n[Invalid choice: index is Empty]");
                    return;  // Exit the method if the choice is invalid
                }

                // Set input to Empty and add the equipment to inventory lastindex
                temp = inventory[choice];

                if( EquipmentBuild[placement] == "Empty"){
                    // Check if the inventory has space to add the equipment
                    if (equipmentLastIndex < EquipmentBuild.length) {
                        inventory[choice] = "Empty";
                        EquipmentBuild[placement] = temp;
                        equipmentLastIndex++;
                        System.out.println("\n[Equipment added and removed from inventory successfully]");
                    } else {
                        System.out.println("\n[Equipment is full. Cannot add equipment. Remove some Equipment first]");

                    }
                }else{System.out.println("\n[Invalid choice: Please enter a valid index]");}

            }//End of check empty
            else{System.out.println("\n[Your equipment inventory is at full capacity. To add a new item, consider selling one of your existing pieces or unequip one first.]"); return;}



        }//end of if(index != 0) a check statment
        else{System.out.println("\n[You inventory is empty. By equipment from the shop first]");}

    }//End of put eq
    public void editProperties(String passwordd) {

        Scanner input = new Scanner(System.in);
        String password = "Wrong";
        System.out.println("\nEnter the Admin's Password: ");
        password = input.nextLine();

        if ("Turki".equals(password) || "turki".equals(password) || "t".equals(password)) {

            System.out.print("\n╔══════════════════════ Admin Panel ══════════════════════╗");
            System.out.print("\n║Set username: ");
            this.userName = input.nextLine();

            System.out.print("║Set level: ");
            this.level = input.nextInt();

            System.out.print("║Set gold: ");
            this.gold = input.nextInt();

            System.out.println("║[Properties updated successfully]                        ║");
            System.out.print("╚═════════════════════════════════════════════════════════╝");
        }else { System.out.println("\n[You don't have access!!]");}
    }
    //Admin method
    public void removeEquipment()
    {
        Scanner input = new Scanner(System.in);
        int choice = 0;
        String temp = " ";
        int size = inventory.length;

        //Print the used equipment
        System.out.println("\n╔═══════════════════════ The Equipments you are using ═══════════════════════╗");
        for(int i = 0; i < EquipmentBuild.length; i++)
        {System.out.println("║"+(i+1)+": Element: " + EquipmentBuild[i]);}
        System.out.println("╚══════════════════════════════════════════════════════════════════════════════╝\n");

        //Take user input choice-1
        System.out.print("\nChoose the equipment you wish to remove: ");
        choice = input.nextInt();
        choice--;
        // Check if the choice is within the valid range
        if (choice < 0 || choice >= EquipmentBuild.length) {
            System.out.println("\n[Invalid choice: Please enter a valid index]");
            return;  // Exit the method if the choice is invalid
        }
        else if(EquipmentBuild[choice].equals("Empty")){
            System.out.println("\n[Invalid choice: index is Empty]");
            return;  // Exit the method if the choice is invalid
        }

        // Set input to Empty and add the equipment to inventory lastindex
        temp = EquipmentBuild[choice];


        // Check if the inventory has space to add the equipment
        if (lastindex < inventory.length) {
            EquipmentBuild[choice] = "Empty";
            inventory[lastindex] = temp;
            lastindex++;
            System.out.println("\n[Equipment removed and added to inventory successfully]");
        } else {
            System.out.println("\n[Inventory is full. Cannot add equipment. Sell some items to make space]");

        }

    }

    public void updatePlayer(){

        if (health <= 0) {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println("\u001B[31m╔══════════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║ Attention, Courageous Adventurer! It pains me to inform you that your health has reached zero,   ║");
            System.out.println("║ bringing our grand journey to an unexpected end. May the winds of fortune guide you to new       ║");
            System.out.println("║ realms of daring exploits. Until we meet again, farewell!                                        ║");
            System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════════════╝");
            System.out.println("\n[Your Health reached "+health+"] ");
            System.exit(0);
        }

        int levelUpExp = this.level * 100;
        if(this.exp >= levelUpExp)
        {
            this.exp -= levelUpExp;
            this.level++;
            health = 100; //when you level up your health is set to 100
            System.out.println("\n\u001B[32m[Congratulations! You leveled up to level " + level + " and your health is set to 100.]\u001B[0m");
        }
        this.attack = (10 + equipmentStats()) * this.level * 2;


    }
    private int equipmentStats() {
        int totalStats = 0;
        for (int i = 0; i < lastindex; i++) {
            if(Guild.Equipment_TYPES[i] == EquipmentBuild[i])
                totalStats += Guild.Equipment_ATTACK_STATS[i];
        }
        return totalStats;
    }


}
class Guild{

    //Equipment info
    protected static final int Equipment_SIZE = 10;
    protected static final String[] Equipment_TYPES = {
            "Sword ⚔️",
            "Shield 🛡️",
            "Bow 🏹",
            "Helmet \uD83C\uDFA9",
            "Armor \uD83E\uDDE5️",
            "Gloves 🧤",
            "Boots 👢",
            "Ring 💍",
            "Amulet 📿",
            "Magic Staff \uD83E\uDDD9"
    };
    protected static final int[] Equipment_PRICE = {10, 50, 80, 30, 120, 45, 60, 25, 55, 150};
    protected static final int[] Equipment_ATTACK_STATS = {15, 25, 40, 10, 35, 20, 18, 12, 30, 50};

    //End

    private Player player;

    public Guild(Player player) {
        this.player = player;
    }



    public void goOnMission() {
        player.updatePlayer();
        Scanner input = new Scanner(System.in);
        Random random = new Random();
        Monster monster = new Monster(player);
        Boss_Monster boss = new Boss_Monster(player);
        Herbs herb = new Herbs();
        int temporaryHP = 0;

        int choice;

        do {
            random = new Random();
            monster = new Monster(player);
            boss = new Boss_Monster(player);
            herb = new Herbs();

            System.out.print(
                    "\n"+
                            "╔══════════════════════════════════════╗\n"+
                            "║        Choose your missions          ║\n"+
                            "║══════════════════════════════════════║\n"+
                            "║1- Hunt                               ║\n"+
                            "║2- Raid                               ║\n"+
                            "║3- Gather                             ║\n"+
                            "║4- Go Back                            ║\n"+
                            "╚══════════════════════════════════════╝\n"+
                            "|Choice: "
            );

            choice = input.nextInt();

            switch (choice) {
                case 1:
                    //monster hunt
                    player.updatePlayer();
                    temporaryHP = monster.monsterAttack;
                    player.setHealth(player.getHealth() - temporaryHP);
                    player.updatePlayer();


                    System.out.println();
                    System.out.println("╔══════════════════════════════════════════════════════════════════════════════╗");
                    System.out.println("║ "+ player.userName + " found and killed a " + monster.monsterType);
                    System.out.println("║ Earned " + monster.monsterPrice + " Gold and earned " + monster.monsterXp + " XP");
                    player.setGold(player.getGold() + monster.monsterPrice);
                    player.setExp(player.getExp() + monster.monsterXp);
                    System.out.println("║ Lost " + temporaryHP + " HP, remaining HP is " + player.getHealth() + "/100");
                    System.out.println("╚══════════════════════════════════════════════════════════════════════════════╝\n");

                    player.updatePlayer();
                    break;
                case 2:
                    //Boss monster raid
                    player.updatePlayer();
                    temporaryHP = monster.monsterAttack;
                    player.setHealth(player.getHealth() - temporaryHP);
                    player.updatePlayer();

                    System.out.println();
                    System.out.println("╔══════════════════════════════════════════════════════════════════════════════╗");
                    System.out.println("║ "+ player.userName + " found and killed a " + boss.bossType);
                    System.out.println("║ Earned " + boss.monsterPrice + " Gold and earned " + boss.monsterXp + " XP");
                    player.setGold(player.getGold() + boss.monsterPrice);
                    player.setExp(player.getExp() + boss.monsterXp);
                    System.out.println("║ Lost " + temporaryHP + " HP, remaining HP is " + player.getHealth() + "/100");
                    System.out.println("╚══════════════════════════════════════════════════════════════════════════════╝\n");

                    player.updatePlayer();
                    break;
                case 3:
                    //Herb gathering
                    player.updatePlayer();

                    System.out.println();
                    System.out.println("╔══════════════════════════════════════════════════════════════════════════════╗");
                    System.out.println("║ "+ player.userName + " found and gathered a "+ herb.herbType);
                    int herbGold = random.nextInt(49) + 50;
                    System.out.println("║ Earned " + herbGold + " Gold ");
                    player.setGold(player.getGold() + herbGold);
                    System.out.println("╚══════════════════════════════════════════════════════════════════════════════╝\n");

                    player.updatePlayer();
                    break;
                case 4:
                    player.updatePlayer();
                    System.out.println("Going back to the guild.");
                    player.updatePlayer();
                    break;
                default:
                    player.updatePlayer();
                    System.out.println("You entered a wrong number.");
                    player.updatePlayer();
                    // Returns back to the guild
                    break;
            }
        } while (choice != 4);
    }
    public void buyEquipment(){
        Equipment.printShop();
        int valid = 1;
        Scanner input = new Scanner(System.in);
        int choice = 0;

        //System.out.println("║ You have: " + player.getGold() + " Piece of Gold!!║");
        int goldAmount = player.getGold();
        String goldMessage = (goldAmount == 1) ? "gold coin" : "gold coins";

        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║    Congratulations, Adventurer!        ║");
        System.out.println("║ You currently possess: " + goldAmount + " " + goldMessage + ". ");
        System.out.println("║ Use it wisely in your journey!         ║");
        System.out.println("╚════════════════════════════════════════╝");


        do {
            System.out.print("Enter the number of the equipment you want to buy (Enter 999 to exit): ");
            valid = 1;
            choice = input.nextInt();
            choice--;
            if(choice == 998){return;}
            // Check if the choice is within the valid range
            if (choice >= 0 && choice <= Equipment_TYPES.length - 1){

                //System.out.println("\nindex: "+choice);
                // Check if the player has enough gold to buy the selected equipment
                if (player.getGold() < Equipment_PRICE[choice]) {
                    System.out.println("\n[Not enough gold to buy this equipment]");
                    valid = -1;
                }

                // Check if the player's inventory is full
                if (player.lastindex >= Inventory.SIZE - 1) {
                    System.out.println("\n[Inventory is full. Sell some items to make space]"); valid = -1;
                }

                if (valid > 0) {
                    // Subtract the gold and add the equipment to the player's inventory
                    player.setGold(player.getGold() - Equipment_PRICE[choice]);
                    player.inventory[player.lastindex++] = Equipment_TYPES[choice];

                    System.out.println("\n[Equipment purchased successfully]");
                    break;
                }//end of first if
            }
            // Check if the choice is within the valid range
            if (choice < 0 || choice > Equipment_TYPES.length - 1) {
                System.out.println("\n[Invalid index: Please enter a valid index]");
                valid = -1;
            }

        } while (choice != 999 || valid < 1);

        Inventory.resortInventory();
    }
    public void sellEquipment() {

        if(Inventory.isEmpty()){System.out.println("\nYour inventory is empty come back when you have something to sell!!"); return;}

        Scanner input = new Scanner(System.in);
        int size = player.inventory.length;

        System.out.print("\n╔════════════ The Equipments you have in your Inventory ═══════════════╗\n║");
        for (int i = 0; i < size; i++) {
            for(int j = 0; j < Equipment_TYPES.length; j++) {

                if (player.inventory[i].equals(Equipment_TYPES[j])) {System.out.print("\n║"+(i+1)+". "+Guild.Equipment_TYPES[i]+" [Price: "+Guild.Equipment_PRICE[i]+"]");}
            }
        }
        System.out.println("\n╚══════════════════════════════════════════════════════════════════════╝\n");

        int choice = 0; // Declare choice outside the do-while loop
        do{
            System.out.print("\nEnter the index of the equipment you want to sell (Enter 999 to exit): ");
            choice = input.nextInt();
            if(choice == 999){return;}
            choice--;
            // Check if the choice is within the valid range
            if (choice < 0 || choice >= size) {
                System.out.println("\n[Invalid index: Please enter a valid index]");
                continue;  // Skip the rest of the loop and prompt the user again
            }

            // Check if the selected slot is empty
            if (player.inventory[choice].equals("Empty")) {
                System.out.println("\n[Invalid choice: The selected slot is already empty]");
            }
        } while (choice >= size || choice < 0 || player.inventory[choice].equals("Empty"));

        // Update player's gold & set index to Empty
        player.inventory[choice] = "Empty";
        //Function to resort the array pushing the Empty element to last and updating the lastindex empty index
        player.lastindex--;
        player.setGold(player.getGold() + Equipment_PRICE[choice]);
        Inventory.resortInventory(); //resort inv
    }

}
class Equipment{
    public static void printShop(){
        System.out.print("\n╔═══════════════════  -== Equipments Shop ==-  ═══════════════════╗");
        for (int i = 0; i < Guild.Equipment_TYPES.length; i++)
        {System.out.print("\n║"+(i+1)+". "+Guild.Equipment_TYPES[i]+" [Price: "+Guild.Equipment_PRICE[i]+"]");}
        System.out.println("\n╚═════════════════════════════════════════════════════════════════╝\n");
    }
}
class Herbs{
    private static final String[] HERBS_TYPES = {
            "Moonblossom 🌸",
            "Starroot 🌱",
            "Dreamvine 🌿"
    };

    public int herblength = HERBS_TYPES.length;
    protected String herbType = "Unknown Herb";
    //Herbs herb = new Herbs();
    protected int herbPrice = 0;

    protected static final Random random = new Random();
    public Herbs() {
        // Set a random monster type
        this.herbType = HERBS_TYPES[random.nextInt(herblength)];

        // Set a random price from 50-300 (50-1+1)
        this.herbPrice = random.nextInt(249) + 50;
    }
    public void print_herb(){
        System.out.println("Herb: "+this.herbType+" Price: "+this.herbPrice);
    }
    public String getHerbType() {
        return herbType;
    }
}
class Monster {
    protected static final Random random = new Random();
    protected static final String[] MONSTER_TYPES = {
            "Slime 🟢",
            "Skeleton 💀",
            "Goblin 👺",
            "Zombie 🧟‍♂️",
            "Werewolf 🐺"
    };
    protected int monsterLength = MONSTER_TYPES.length;
    protected String monsterType = "Unknown Monster";
    protected int monsterHealth = 100;
    protected int monsterAttack = 100;
    protected int monsterPrice = 100;
    protected int monsterXp = 100;

    public Monster(Player player) {
        // Set a random monster type
        this.monsterType = MONSTER_TYPES[random.nextInt(monsterLength)];

        // Set a random health from 25-50 (50-1+25) + the user level
        this.monsterAttack = (random.nextInt(50) + 25) * player.getLevel();
        this.monsterHealth = (random.nextInt(50) + 25) * player.getLevel();
        this.monsterPrice = (random.nextInt(50) + 25) * player.getLevel();
        this.monsterXp = (random.nextInt(50) + 25) * player.getLevel();
    }



    public void print_monster() {
        System.out.println("Monster: "+this.monsterType+" Health: "+this.monsterHealth);
    }

}

class Boss_Monster extends Monster{
    private static final String[] BOSS_MONSTER_TYPES = {
            "Hobgoblin 🛡️",
            "Progenitor Slime 🌐",
            "Dark Knight ⚔️",
            "Dragon 🐉",
            "Sorceress Queen 👑"
    };
    String bossType = " ";
    Boss_Monster(Player player){
        super(player);
        // Set a random boss monster type
        this.setBossMonster();
    }
    private void setBossMonster(){
        bossType = BOSS_MONSTER_TYPES[random.nextInt(monsterLength)];

        // Set a random health multiplier from 1-3 (2-1+1) -> monster + 25 + 1-3 random multiplier
        this.monsterHealth = this.monsterHealth+25;
        this.monsterHealth = (int) (monsterHealth*(random.nextDouble(3) + 1));

        this.monsterAttack = this.monsterAttack+25;
        this.monsterAttack = (int) (monsterAttack*(random.nextDouble(3) + 1));

        this.monsterPrice = this.monsterPrice+45;
        this.monsterPrice = (int) (monsterPrice*(random.nextDouble(3) + 1));

        this.monsterXp = this.monsterXp+30;
        this.monsterXp = (int) (monsterXp*(random.nextDouble(3) + 1));
    }

}

public class Main {
    public static void main(String[] args) {
        // ANSI escape codes for text colors
        final String RESET = "\u001B[0m";
        final String RED = "\u001B[31m";
        final String GREEN = "\u001B[32m";
        final String YELLOW = "\u001B[33m";
        final String BLUE = "\u001B[34m";

        // ANSI escape codes for background colors
        final String BLACK_BACKGROUND = "\u001B[40m";
        final String RED_BACKGROUND = "\u001B[41m";
        final String GREEN_BACKGROUND = "\u001B[42m";
        final String YELLOW_BACKGROUND = "\u001B[43m";
        final String BLUE_BACKGROUND = "\u001B[44m";
        final String CYAN_BOLD = "\u001B[1;36m";  // Bold Cyan

        System.out.println(CYAN_BOLD + "\n" +
                "╔═══════════════════════════════════════════════════════════════════════════════════════════╗\n" +
                "║          -== Welcome to the world of Adventures of the New Cosmos Adventurer ==-          ║\n" +
                "╚═══════════════════════════════════════════════════════════════════════════════════════════╝\n " +
                RESET
        );

        Player player = new Player();
        Guild guild = new Guild(player);
        int choice = 0;

        //Set character username:
        System.out.print("Hello, adventurer. May I inquire about your name? \u001B[31m");
        Scanner user = new Scanner(System.in);
        player.userName = user.nextLine();

        System.out.println("\u001B[0m╔════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ Well, it's an honor to have you, " + player.userName + ".               ");
        System.out.println("║ New Cosmos has been crowded with monsters lately, and many citizens    ║");
        System.out.println("║ are injured. With your help, we hope to restore normalcy in due time.  ║");
        System.out.println("║                                                                        ║");
        System.out.println("║\u001B[31m A word of caution before you go: be mindful when choosing 'Exit',      \u001B[0m║");
        System.out.println("║\u001B[31m as it will return you to your world with no way of coming back.        \u001B[0m║");
        System.out.println("║\u001B[31m Oh, I almost forgot: if your health reaches zero, then you die forever!\u001B[0m║");
        System.out.println("║\u001B[34m So, let the blue fountain of life be your friend.                      \u001B[0m║");
        System.out.println("║\u001B[0m                                                                        ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════╝");

        System.out.println("Press any KEY to start: ");
        String Junk = user.nextLine();


        player.resortEquipment();

        do {
            displayMainMenu();
            System.out.print("Enter your choice: ");
            Scanner input = new Scanner(System.in);
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    // Guild Menu
                    int guildChoice;
                    do {
                        displayGuildMenu();
                        System.out.print("Enter your choice: ");
                        guildChoice = input.nextInt();
                        player.resortEquipment(); //For testing phase
                        switch (guildChoice) {
                            case 1:
                                guild.goOnMission();
                                break;
                            case 2:
                                break; //Go to main
                            case 0:
                                System.out.println("Exiting the guild menu.");
                                System.out.println("\n" +
                                        "╔═══════════════════════════════════════════════════════════════════════════════════════════╗\n" +
                                        "║Exiting the game, we hope that you have enjoyed your experience in the world of New Cosmos!║\n" +
                                        "╚═══════════════════════════════════════════════════════════════════════════════════════════╝\n");
                                System.exit(0);
                            default:
                                System.out.println("Invalid choice. Please enter a valid option for the guild menu.");
                        }

                    } while (guildChoice != 0 && guildChoice != 2);
                    break;

                case 2:
                    // Player Menu
                    int playerChoice;
                    do {
                        displayPlayerMenu();
                        System.out.print("Enter your choice: ");
                        playerChoice = input.nextInt();
                        //ClearScreenExample();

                        switch (playerChoice) {
                            case 1:
                                player.printPanel();
                                break;
                            case 2:
                                player.putEquipment();
                                break;
                            case 3:
                                player.removeEquipment();
                                break;
                            case 4:
                                break; //Go to main
                            case 0:
                                System.out.println("Exiting the player menu.");
                                System.out.println("\n" +
                                        "╔═══════════════════════════════════════════════════════════════════════════════════════════╗\n" +
                                        "║Exiting the game, we hope that you have enjoyed your experience in the world of New Cosmos!║\n" +
                                        "╚═══════════════════════════════════════════════════════════════════════════════════════════╝\n");
                                System.exit(0);
                            default:
                                System.out.println("Invalid choice. Please enter a valid option for the player menu.");
                        }

                    } while (playerChoice != 0 && playerChoice != 4);
                    break;

                case 3:
                    // Town Menu
                    int townChoice;
                    do {
                        displayTownMenu();
                        System.out.print("Enter your choice: ");
                        townChoice = input.nextInt();

                        switch (townChoice) {
                            case 1:
                                player.useLifeFountain();
                                break;
                            case 2:
                                guild.sellEquipment();
                                break;
                            case 3:
                                guild.buyEquipment();
                                break;
                            case 4:
                                break; //Go to main
                            case 0:
                                System.out.println("Exiting the town menu.");
                                System.out.println("\n" +
                                        "╔═══════════════════════════════════════════════════════════════════════════════════════════╗\n" +
                                        "║Exiting the game, we hope that you have enjoyed your experience in the world of New Cosmos!║\n" +
                                        "╚═══════════════════════════════════════════════════════════════════════════════════════════╝\n");
                                System.exit(0);
                            default:
                                System.out.println("Invalid choice. Please enter a valid option for the town menu.");
                        }

                    } while (townChoice != 0 && townChoice != 4);
                    break;

                case 4:
                    // Admin Panel
                    player.editProperties("Admin");
                    break;

                case 0:
                    System.out.println("\n" +
                            "╔═══════════════════════════════════════════════════════════════════════════════════════════╗\n" +
                            "║Exiting the game, we hope that you have enjoyed your experience in the world of New Cosmos!║\n" +
                            "╚═══════════════════════════════════════════════════════════════════════════════════════════╝\n");
                    System.exit(0);
                    //break;

                default:
                    System.out.println("Invalid choice. Please enter a valid option for the main menu.");
            }

        } while (choice != 0);




    }// End of main method

    private static void displayMainMenu() {
        System.out.println("\u001B[34m"+
                "                                                      \n" +
                " ╔═══════════════════ Main Menu ═══════════════════╗\n" +
                " ║ 1. Guild Menu                                   ║\n" +
                " ║ 2. Player Menu                                  ║\n" +
                " ║ 3. Town Menu                                    ║\n" +
                " ║ 4. Admin Panel                                  ║\n" +
                " ║ 0. Exit                                         ║\n" +
                " ╚═════════════════════════════════════════════════╝" +
                "\u001B[0m"
        );
    }

    private static void displayGuildMenu() {
        System.out.println("\u001B[35m"+
                " ╔══════════════════ Guild Menu ═══════════════════╗\n" +
                " ║ 1. Go on Mission                                ║\n" +
                " ║ 2. Main Menu                                    ║\n" +
                " ║ 0. Exit                                         ║\n" +
                " ╚═════════════════════════════════════════════════╝" +
                "\u001B[0m"
        );
    }

    private static void displayPlayerMenu() {
        System.out.println(
                "\u001B[33m"+
                        " ╔══════════════════ Player Menu ══════════════════╗\n" +
                        " ║ 1. Print Player Panel                           ║\n" +
                        " ║ 2. Put Equipment                                ║\n" +
                        " ║ 3. Remove Equipment                             ║\n" +
                        " ║ 4. Main Menu                                    ║\n" +
                        " ║ 0. Exit                                         ║\n" +
                        " ╚═════════════════════════════════════════════════╝" +
                        "\u001B[0m"
        );
    }

    private static void displayTownMenu(){
        System.out.println(
                "\u001B[32m"+
                        " ╔════════════════════ Town Menu ════════════════════╗\n" +
                        " ║ 1. Use Life Fountain                              ║\n" +
                        " ║ 2. Sell Equipment                                 ║\n" +
                        " ║ 3. Buy Equipment                                  ║\n" +
                        " ║ 4. Main Menu                                      ║\n" +
                        " ║ 0. Exit                                           ║\n" +
                        " ╚═══════════════════════════════════════════════════╝" +
                        "\u001B[0m"
        );
    }
    public static void ClearScreenExample(){
        // Prints 50 newline characters to "clear" the screen
        System.out.println("Press any KEY to start: ");
        Scanner user = new Scanner(System.in);
        String Junk = user.nextLine();
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

}//End of code