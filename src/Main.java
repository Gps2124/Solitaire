import java.util.*;
import java.util.List;



public class Main {

    //list of standard deck of cards

    static List<String> cards = Arrays.asList(
            "AS", "2S", "3S", "4S", "5S", "6S", "7S", "8S", "9S", "XS", "JS", "QS", "KS",
            "AD", "2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D", "XD", "JD", "QD", "KD",
            "AC", "2C", "3C", "4C", "5C", "6C", "7C", "8C", "9C", "XC", "JC", "QC", "KC",
            "AH", "2H", "3H", "4H", "5H", "6H", "7H", "8H", "9H", "XH", "JH", "QH", "KH"
    );

    static List<String> locations = Arrays.asList("T1", "T2", "T3", "T4", "T5", "T6", "T7", "F1", "F2", "F3", "F4", "WASTE"); //list of locations to move
    static ArrayList<String> tableau1 = new ArrayList<String>(); //tableau 1
    static ArrayList<String> tableau2 = new ArrayList<String>(); //tableau 2
    static ArrayList<String> tableau3 = new ArrayList<String>(); //tableau 3
    static ArrayList<String> tableau4 = new ArrayList<String>(); //tableau 4
    static ArrayList<String> tableau5 = new ArrayList<String>(); //tableau 5
    static ArrayList<String> tableau6 = new ArrayList<String>(); //tableau 6
    static ArrayList<String> tableau7 = new ArrayList<String>(); //tableau 7
    static LinkedHashMap<String, String> colors = new LinkedHashMap<>(); //assigning the card colors to each card
    static LinkedHashMap<String, String> suits = new LinkedHashMap<>(); //assigning the suits to each card
    static LinkedHashMap<String, Boolean> isFaceUp = new LinkedHashMap<>(); //making the cards face down
    static TreeMap<String, Integer> values = new TreeMap<>(); //assigning a value to each card
    static ArrayList<String> cards2 = new ArrayList<String>();
    static Stack<String> stock = new Stack<String>(); //Stock to draw from after dealing all cards
    static Stack<String> waste = new Stack<String>(); //waste stack
    static Stack<String> buffer = new Stack<String>(); //buffer stack to help transfer cards between tableaux
    static Stack<String> f1 = new Stack<String>(); //foundation 1
    static Stack<String> f2 = new Stack<String>(); //foundation 2
    static Stack<String> f3 = new Stack<String>(); //foundation 3
    static Stack<String> f4 = new Stack<String>(); //foundation 4
    public static String card1;
    public static String location1;
    public static final String RED = "\u001B[31m";
    public static final String BLACK = "\u001B[30m";
    public static final String RESET = "\u001B[0m";
//    public static

    //rules of the game, generates at the start of running the program
    public static void rules() {
        System.out.println("\nThe objective is to move cards around the board to create complete build piles.\n" +
                "You create a build pile by layering cards of opposite color on top of each other in descending\n" +
                "order. You are allowed to move around the top facing cards on the 7 building decks as well as cards that\n" +
                "are flipped from the deck. For example, in the image above the black nine of spades can be\n" +
                "moved onto the red 10 of hearts to begin the consolidation process. If you move a top facing\n" +
                "card onto another top facing card on the 7 building decks, you can then go back a flip over the underlying\n" +
                "card from the column in which you moved from.\n" +
                "The card input will be taken as the value of the card and then the first leader of the suit\n" +
                "for example ace of hearts would be AH or a 7 of spades would be 7S.\n"
        );
    }


    public static void main(String[] args) {
        shuffle();
    }
    public static void shuffle() {
        //assigns each card its suit, color, and making sure that it is face down
        for (int j = 0; j < 13; j++) {
            colors.put(cards.get(j), "black");
            suits.put(cards.get(j), BLACK + "S" + RESET);
            isFaceUp.put(cards.get(j), false);
        }
        for (int j = 13; j < 26; j++) {
            colors.put(cards.get(j), "red");
            suits.put(cards.get(j), RED + "D" + RESET);
            isFaceUp.put(cards.get(j), false);
        }
        for (int j = 26; j < 39; j++) {
            colors.put(cards.get(j), "black");
            suits.put(cards.get(j), BLACK + "C" + RESET);
            isFaceUp.put(cards.get(j), false);
        }
        for (int j = 39; j <= 51; j++) {
            colors.put(cards.get(j), "red");
            suits.put(cards.get(j), RED + "H" + RESET);
            isFaceUp.put(cards.get(j), false);
        }


        //assigning values for each number
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                values.put(cards.get(j + (13 * i)), j + 1);
            }
        }
        cards2.addAll(cards); //transfers all cards in deck to a new arrayList

        Collections.shuffle(cards2); //shuffles the deck

        //set up tableau1
        tableau1.add(cards2.remove(0));
        isFaceUp.put(tableau1.get(0), true);

        //set up tableau2
        for (int i = 0; i < 2; i++) {
            tableau2.add(cards2.remove(i));
            if (i == 1)
                isFaceUp.put(tableau2.get(i), true);
        }

        //set up tableau3
        for (int f = 0; f < 3; f++) {
            tableau3.add(cards2.remove(f));
            if (f == 2)
                isFaceUp.put(tableau3.get(f), true);
        }

        //set up tableau4
        for (int g = 0; g < 4; g++) {
            tableau4.add(cards2.remove(g));
            if (g == 3)
                isFaceUp.put(tableau4.get(g), true);
        }

        //set up tableau5
        for (int j = 0; j < 5; j++) {
            tableau5.add(cards2.remove(j));
            if (j == 4)
                isFaceUp.put(tableau5.get(j), true);
        }

        //set up tableau6
        for (int j = 0; j < 6; j++) {
            tableau6.add(cards2.remove(j));
            if (j == 5)
                isFaceUp.put(tableau6.get(j), true);
        }

        //set up tableau7
        for (int j = 0; j < 7; j++) {
            tableau7.add(cards2.remove(j));
            if (j == 6)
                isFaceUp.put(tableau7.get(j), true);
        }

        while (cards2.size() != 0) {
            stock.add(cards2.remove(0));
        }
        //prints the rules at top of console
        rules();

        //prints graphics into console
        print();

        input();

    }


    //prompts the player what type of move they would like to make
    //checks to make sure that the move is legal or not
    public static void input() {

        Scanner scanner = new Scanner(System.in);
        List<String> actions = Arrays.asList("STOCK", "MOVE");
        System.out.println("What actions would you like to take? \nYou may move a card by typing 'move' \nOr access the stock by typing 'stock'.\n" +
                "Or type 'Oops' to reset selection. \n" +
                "Type 'quit' to quit the game");
        String action = scanner.next().toUpperCase();
        if (action.equals("OOPS"))
            input();
        else if (action.equals("QUIT")) {
            System.out.println("Thanks for playing! Have a nice day!");
            System.exit(0);

        } else if (action.equals("WIN")) {
            System.out.println("Congratulations! You were smart enough to not waste hours playing this game.\n" +
                    "Please play again to honor the time it took to make this game.");
            System.exit(0);
        }


        else {
            while (!actions.contains(action)) {
                System.out.println("Not a valid action. Try again\nOr type 'Oops' to reset selection.");
                action = scanner.next().toUpperCase();
                if (action.equals("OOPS"))
                    input();
            }
            if (action.equals("MOVE")) {

                //prompts the player where they would like to move their selected card and from where and checks if its legal
                System.out.println("Where would you like to move a card from?\n" +
                        "Type 'T1' through 'T7' for the Tableaux\n" +
                        "Type 'F1' through 'F4' for the Foundations\n" +
                        "Type 'waste' to move a card from the Waste.\n" +
                        "Or type 'Oops' to reset selection.");
                String move = scanner.next().toUpperCase();
                if (move.equals("OOPS"))
                    input();

                while (!locations.contains(move)) {
                    System.out.println("Not a valid location. Try again\nOr type 'Oops' to reset selection.");
                    move = scanner.next().toUpperCase();
                    if (move.equals("OOPS"))
                        input();
                }
                card1 = cardInput();
                System.out.println("\nYou've selected " + card1 + ".");
                System.out.println("Where would you like to move it?\n" +
                        "Or type 'Oops' to reset selection.");
                location1 = locationInput();

                //if the move is illegal, gives an error, otherwise asks you to move another card
                if (!(isLegal(card1, location1))) {
                    System.out.println("Illegal move. Try again");
                    input();
                } else {
                    updateGame(card1, move, location1);
                    System.out.println("\nMoving " + card1 + " to " + location1 + ". Nice work.\nLet's move another card.\n");
                    input();
                }

            } else { //moves a single card from the stock into the waste face up and turns the previous card in the waste face down
                if (stock.isEmpty() && waste.isEmpty()) {
                    System.out.println("No cards to interact with. Try moving a card!");
                    input();
                } else if (stock.isEmpty()) {
                    int size = waste.size();
                    for (int i = 0; i < size; i++) {
                        stock.push(waste.pop());
                    }
                    print();
                    input();
                } else if (waste.isEmpty()) {
                    waste.push(stock.pop());
                    isFaceUp.put(waste.peek(), true);
                    print();
                    input();
                } else {
                    String move = stock.pop();
                    String move2 = waste.peek();
                    isFaceUp.put(move2, false);
                    isFaceUp.put(move, true);
                    waste.push(move);
                    print();
                    input();
                }


            }
        }
    }


    //prompts the player to select a card and checks to make sure that the card can be played
    public static String cardInput() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Which card would you like to move: ");
        String card1 = scanner.next().toUpperCase();
        while (!(cards.contains(card1)) || !isFaceUp.get(card1)) {
            System.out.println("Not a valid card. Try again.\nOr type 'Oops' to reset selection.\"");
            card1 = scanner.next().toUpperCase();
            if (card1.equals("OOPS"))
                input();
        }
        return card1;
    }

    //passes the card from its previous spot, to a new spot
    public static void updateGame(String card, String location, String location2) {
        String temp = "";
        ArrayList<String> t = new ArrayList<String>();
        Stack<String> f = new Stack<String>();

        if (location.equals("T1")) {
            temp = "tableau";
            t = tableau1;
        } else if (location.equals("T2")) {
            temp = "tableau";
            t = tableau2;
        } else if (location.equals("T3")) {
            temp = "tableau";
            t = tableau3;
        } else if (location.equals("T4")) {
            t = tableau4;
            temp = "tableau";
        } else if (location.equals("T5")) {
            t = tableau5;
            temp = "tableau";
        } else if (location.equals("T6")) {
            t = tableau6;
            temp = "tableau";
        } else if (location.equals("T7")) {
            t = tableau7;
            temp = "tableau";
        } else if (location.equals("F1")) {
            f = f1;
            temp = "foundation";
        } else if (location.equals("F2")) {
            f = f2;
            temp = "foundation";
        } else if (location.equals("F3")) {
            f = f3;
            temp = "foundation";
        } else if (location.equals("F4")) {
            f = f4;
            temp = "foundation";
        } else if (location.equals("WASTE")) {
            f = waste;
            temp = "waste";
        }
        String temp2 = "";
        ArrayList<String> t2 = new ArrayList<String>(); //passes the card from its previous spot, to a new spot
        Stack<String> f_2 = new Stack<String>();

        if (location2.equals("T1")) {
            temp2 = "tableau";
            t2 = tableau1;
        } else if (location2.equals("T2")) {
            temp2 = "tableau";
            t2 = tableau2;
        } else if (location2.equals("T3")) {
            temp2 = "tableau";
            t2 = tableau3;
        } else if (location2.equals("T4")) {
            t2 = tableau4;
            temp2 = "tableau";
        } else if (location2.equals("T5")) {
            t2 = tableau5;
            temp2 = "tableau";
        } else if (location2.equals("T6")) {
            t2 = tableau6;
            temp2 = "tableau";
        } else if (location2.equals("T7")) {
            t2 = tableau7;
            temp2 = "tableau";
        } else if (location2.equals("F1")) {
            f_2 = f1;
            temp2 = "foundation";
        } else if (location2.equals("F2")) {
            f_2 = f2;
            temp2 = "foundation";
        } else if (location2.equals("F3")) {
            f_2 = f3;
            temp2 = "foundation";
        } else if (location2.equals("F4")) {
            f_2 = f4;
            temp2 = "foundation";
        } else if (location2.equals("WASTE")) {
            f_2 = waste;
            temp2 = "waste";
        }

        //waste or foundation to elsewhere
        if ((temp.equals("waste")) || (temp.equals("foundation"))) {

            if (temp2.equals("foundation")) {
                f_2.push(f.pop());
//
            } else if (temp2.equals("tableau")) {
                t2.add(f.pop());
//
            }
        } else {
            if (t.indexOf(card) == t.size() - 1) {
                //card is at end of array
                if (temp2.equals("foundation")) {

                    f_2.push(t.remove(t.size() - 1));
                    //previous array card face up
                    if (!temp2.isEmpty()) {
                        isFaceUp.put(t.get(t.size() - 1), true);
                    }
                } else if (temp2.equals("tableau")) {
                    t2.add(t.remove(t.size() - 1));
                    //previous array card face up

                    if (!(t.isEmpty()))
                        isFaceUp.put(t.get(t.size() - 1), true);
                }


            } else {
                //moving multiple cards between tableaux
                //moves cards from one tableau into a buffer array from back to front
                //moves cards from buffer array into targeted tableau
                int size = t.size() - 1;
                int loop = size - t.indexOf(card);


                for (int i = size; i >= loop; i--) {

                    buffer.push(t.remove(i));

                }
                while (!buffer.isEmpty()) {
                    t2.add(buffer.pop());
                }
            }
        }

        print();


    }

    //updating the game board
    public static void print() {
        Stack<String> waste_1 = new Stack<String>();


        int size1 = tableau1.size();
        int size2 = tableau2.size();
        int size3 = tableau3.size();
        int size4 = tableau4.size();
        int size5 = tableau5.size();
        int size6 = tableau6.size();
        int size7 = tableau7.size();

        ArrayList<String> tableau_1 = new ArrayList<String>();
        for (int i = 0; i < size1; i++) {
            tableau_1.add(tableau1.get(i));
        }
        ArrayList<String> tableau_2 = new ArrayList<String>();
        for (int i = 0; i < size2; i++) {
            tableau_2.add(tableau2.get(i));
        }
        ArrayList<String> tableau_3 = new ArrayList<String>();
        for (int i = 0; i < size3; i++) {
            tableau_3.add(tableau3.get(i));
        }
        ArrayList<String> tableau_4 = new ArrayList<String>();
        for (int i = 0; i < size4; i++) {
            tableau_4.add(tableau4.get(i));
        }
        ArrayList<String> tableau_5 = new ArrayList<String>();
        for (int i = 0; i < size5; i++) {
            tableau_5.add(tableau5.get(i));
        }
        ArrayList<String> tableau_6 = new ArrayList<String>();
        for (int i = 0; i < size6; i++) {
            tableau_6.add(tableau6.get(i));
        }
        ArrayList<String> tableau_7 = new ArrayList<String>();
        for (int i = 0; i < size7; i++) {
            tableau_7.add(tableau7.get(i));
        }


        //hide all face down cards in tableaux
        for (int i = 0; i < size1; i++) {
            if (!(isFaceUp.get(tableau1.get(i))))
                tableau_1.set(i, "??");
        }
        for (int o = 0; o < size2; o++) {
            if (!(isFaceUp.get(tableau2.get(o))))
                tableau_2.set(o, "??");
        }
        for (int p = 0; p < size3; p++) {
            if (!isFaceUp.get(tableau3.get(p)))
                tableau_3.set(p, "??");
        }
        for (int k = 0; k < size4; k++) {
            if (!isFaceUp.get(tableau4.get(k)))
                tableau_4.set(k, "??");
        }
        for (int k = 0; k < size5; k++) {
            if (!isFaceUp.get(tableau5.get(k)))
                tableau_5.set(k, "??");
        }
        for (int l = 0; l < size6; l++) {
            if (!isFaceUp.get(tableau6.get(l)))
                tableau_6.set(l, "??");
        }
        for (int i = 0; i < size7; i++) {
            if (!isFaceUp.get(tableau7.get(i)))
                tableau_7.set(i, "??");
        }

        for (int l = 0; l < waste.size() - 1; l++)
            waste_1.push("??");

        if (!waste.isEmpty())
            waste_1.push(waste.peek());

        System.out.println("Available Cards:\n");

        System.out.print("Stock: " + "[" + "??" + "]" + " ");
        System.out.println("Waste: " + waste_1);
        System.out.println("Tableau 1: " + tableau_1);
        System.out.println("Tableau 2: " + tableau_2);
        System.out.println("Tableau 3: " + tableau_3);
        System.out.println("Tableau 4: " + tableau_4);
        System.out.println("Tableau 5: " + tableau_5);
        System.out.println("Tableau 6: " + tableau_6);
        System.out.println("Tableau 7: " + tableau_7);

        graphics();
    }


    //method to check if a move is legal
    public static Boolean isLegal(String card, String location) {
        String temp = "";
        ArrayList<String> t = new ArrayList<String>();
        Stack<String> f = new Stack<String>();


        if (location.equals("T1")) {
            temp = "tableau";
            t = tableau1;
        } else if (location.equals("T2")) {
            temp = "tableau";
            t = tableau2;
        } else if (location.equals("T3")) {
            temp = "tableau";
            t = tableau3;
        } else if (location.equals("T4")) {
            t = tableau4;
            temp = "tableau";
        } else if (location.equals("T5")) {
            t = tableau5;
            temp = "tableau";
        } else if (location.equals("T6")) {
            t = tableau6;
            temp = "tableau";
        } else if (location.equals("T7")) {
            t = tableau7;
            temp = "tableau";
        } else if (location.equals("F1")) {
            f = f1;
            temp = "foundation";
        } else if (location.equals("F2")) {
            f = f2;
            temp = "foundation";
        } else if (location.equals("F3")) {
            f = f3;
            temp = "foundation";
        } else if (location.equals("F4")) {
            f = f4;
            temp = "foundation";
        }
        //check if the location your going to is the waste pile
        //if location is the waste pile it will return false
        if (location.equals("waste")) {
            return false;
        }

        if (temp.equals("tableau")) {
            if (t.isEmpty()) {
                int value3 = values.get(card);
                if (value3 == 13)
                    return true;
            }
            String card_2 = t.get(t.size() - 1);
            int value = values.get(card);
            int value_2 = values.get(card_2);


            if (colors.get(card) == colors.get(card_2)) { //determines if a card is of a lesser value when going into a new tableau and of an opposite color
                return false;
            } else if (value + 1 != value_2) {
                return false;
            } else {
                return true;
            }


        } else {//determines if the first card in a foundation is an Ace, and then checks if each following card is of a higher value and the same suit


            if (f.isEmpty()) {
                int value = values.get(card);
                if (value == 1) {
                    return true;
                } else {
                    return false;
                }
            } else {
                String card_2 = f.peek();
                int value = values.get(card);
                int value_2 = values.get(card_2);
                String suit = suits.get(card);
                String suit_2 = suits.get(card_2);

                if ((value - value_2 != 1) && (suit != suit_2))
                    return false;
                else
                    return true;
            }
        }


    }

    //scans the location and determines if it'll be valid
    public static String locationInput() {
        Scanner scanner = new Scanner(System.in);
        String location = scanner.next().toUpperCase();
        if (location.equals("OOPS")) {
            print();
            input();
        } // end of game allows user to play another game or quit
        if (location.equals("QUIT")) {
            System.out.println("No more moves \n Please try again\n Write Yes to try again No to finish");
            if (location.equals("YES")) {
                shuffle();
            } else {
                System.out.println("Have a nice day");
            }
        }

        //Panel.secondDeckCard1(Graphics, card1);
        while (!locations.contains(location)) {
            System.out.println("Not a valid location. Try again\nOr type 'Oops' to reset selection.");
            location = scanner.next().toUpperCase();
            if (location.equals("OOPS"))
                input();
        }

        return location;
    }

    //new and improved graphics courtesy of Abbey!
    public static void graphics() {

        int size1 = tableau1.size();
        int size2 = tableau2.size();
        int size3 = tableau3.size();
        int size4 = tableau4.size();
        int size5 = tableau5.size();
        int size6 = tableau6.size();
        int size7 = tableau7.size();

        ArrayList<String> tableau_1 = new ArrayList<String>();
        for (int i = 0; i < size1; i++) {
            tableau_1.add(tableau1.get(i));
        }
        ArrayList<String> tableau_2 = new ArrayList<String>();
        for (int i = 0; i < size2; i++) {
            tableau_2.add(tableau2.get(i));
        }
        ArrayList<String> tableau_3 = new ArrayList<String>();
        for (int i = 0; i < size3; i++) {
            tableau_3.add(tableau3.get(i));
        }
        ArrayList<String> tableau_4 = new ArrayList<String>();
        for (int i = 0; i < size4; i++) {
            tableau_4.add(tableau4.get(i));
        }
        ArrayList<String> tableau_5 = new ArrayList<String>();
        for (int i = 0; i < size5; i++) {
            tableau_5.add(tableau5.get(i));
        }
        ArrayList<String> tableau_6 = new ArrayList<String>();
        for (int i = 0; i < size6; i++) {
            tableau_6.add(tableau6.get(i));
        }
        ArrayList<String> tableau_7 = new ArrayList<String>();
        for (int i = 0; i < size7; i++) {
            tableau_7.add(tableau7.get(i));
        }

        if (stock.isEmpty()) {
            System.out.print("       ");
        } else if (!stock.isEmpty()) {
            System.out.print("-------");
        }
        if (waste.isEmpty()) {
            System.out.print("       ");
        } else if (!waste.isEmpty()) {
            System.out.print("-------");
        }
        if (f1.isEmpty()) {
            System.out.print("       ");
        } else if (!f1.empty()) {
            System.out.print("-------");
        }
        if (f2.isEmpty()) {
            System.out.print("       ");
        } else if (!f2.isEmpty()) {
            System.out.print("-------");
        }
        if (f3.isEmpty()) {
            System.out.print("       ");
        } else if (!f3.isEmpty()) {
            System.out.print("-------");
        }
        if (f4.isEmpty()) {
            System.out.println("       ");
        } else if (!f4.isEmpty()) {
            System.out.println("-------");
        }
        if (stock.isEmpty()) {
            System.out.print("       ");
        } else if (!stock.isEmpty()) {
            System.out.print("|     |");
        }
        if (waste.isEmpty()) {
            System.out.print("       ");

        } else if (!waste.isEmpty()) {
            System.out.print("|  " + waste.peek().charAt(0) + "  |");
        }
        if (f1.isEmpty()) {
            System.out.print("       ");
        } else if (!f1.empty()) {
            System.out.print("|  " + f1.peek().charAt(0) + "  |");
        }
        if (f2.isEmpty()) {
            System.out.print("       ");
        } else if (!f2.isEmpty()) {
            System.out.print("|  " + f2.peek().charAt(0) + "  |");
        }
        if (f3.isEmpty()) {
            System.out.print("       ");
        } else if (!f3.isEmpty()) {
            System.out.print("|  " + f3.peek().charAt(0) + "  |");
        }
        if (f4.isEmpty()) {
            System.out.println("       ");
        } else if (!f4.isEmpty()) {
            System.out.println("|  " + f4.peek().charAt(0) + "  |");
        }
        if (stock.isEmpty()) {
            System.out.print("       ");
        } else if (!stock.isEmpty()) {
            System.out.print("|     |");
        }
        if (waste.isEmpty()) {
            System.out.print("       ");
        } else if (!waste.isEmpty()) {
            System.out.print("|  " + suits.get(waste.peek()) + "  |");
        }
        if (f1.isEmpty()) {
            System.out.print("       ");
        } else if (!f1.empty()) {
            System.out.print("|  " + suits.get(f1.peek()) + "  |");
        }
        if (f2.isEmpty()) {
            System.out.print("       ");
        } else if (!f2.isEmpty()) {
            System.out.print("|  " + suits.get(f2.peek()) + "  |");
        }
        if (f3.isEmpty()) {
            System.out.print("       ");
        } else if (!f3.isEmpty()) {
            System.out.print("|  " + suits.get(f3.peek()) + "  |");
        }
        if (f4.isEmpty()) {
            System.out.println("       ");
        } else if (!f4.isEmpty()) {
            System.out.println("|  " + suits.get(f4.peek()) + "  |");
        }
        if (stock.isEmpty()) {
            System.out.print("       ");
        } else if (!stock.isEmpty()) {
            System.out.print("-------");
        }
        if (waste.isEmpty()) {
            System.out.print("       ");
        } else if (!waste.isEmpty()) {
            System.out.print("-------");
        }
        if (f1.isEmpty()) {
            System.out.print("       ");
        } else if (!f1.empty()) {
            System.out.print("-------");
        }
        if (f2.isEmpty()) {
            System.out.print("       ");
        } else if (!f2.isEmpty()) {
            System.out.print("-------");
        }
        if (f3.isEmpty()) {
            System.out.print("       ");
        } else if (!f3.isEmpty()) {
            System.out.print("-------");
        }
        if (f4.isEmpty()) {
            System.out.println("       ");
        } else if (!f4.isEmpty()) {
            System.out.println("-------");
        }
        if (stock.isEmpty()) {
            System.out.print(" Stock ");
        } else if (!stock.isEmpty()) {
            System.out.print(" Stock ");
        }
        if (waste.isEmpty()) {
            System.out.print(" waste ");
        } else if (!waste.isEmpty()) {
            System.out.print(" waste ");
        }
        if (f1.isEmpty()) {
            System.out.print("  f1  ");
        } else if (!f1.empty()) {
            System.out.print("  f1  ");
        }
        if (f2.isEmpty()) {
            System.out.print("  f2  ");
        } else if (!f2.isEmpty()) {
            System.out.print("  f2  ");
        }
        if (f3.isEmpty()) {
            System.out.print("  f3  ");
        } else if (!f3.isEmpty()) {
            System.out.print("  f3  ");
        }
        if (f4.isEmpty()) {
            System.out.println("  f4  ");
        } else if (!f4.isEmpty()) {
            System.out.println("  f4  ");
        }

        if (tableau1.isEmpty()) {
            System.out.println("  t1  ");
            System.out.println("-------");

            System.out.println("     ");
            System.out.println("-------");
        } else if (tableau1.size() > 0) {

            System.out.println("  t1  ");
            for (int y = 0; y < tableau1.size(); y++) {
                System.out.print("------");
            }
            System.out.println();

            for (int y = 0; y < tableau1.size(); y++) {
                if (!isFaceUp.get(tableau1.get(y)))
                    System.out.print("  ?? |");
                else
                    System.out.print("  " + tableau1.get(y).charAt(0) + suits.get(tableau1.get(y)) + " |");
            }
            System.out.println();

            for (int h = 0; h < tableau1.size(); h++) {
                System.out.print("------");
            }
            System.out.println();
        }
        if (tableau2.isEmpty()) {
            System.out.println("  t2  ");
            System.out.println("-------");

            System.out.println("     ");
            System.out.println("-------");
        } else if (tableau2.size() > 0) {
            System.out.println("  t2  ");
            for (int y = 0; y < tableau2.size(); y++) {
                System.out.print("------");
            }
            System.out.println();

            for (int y = 0; y < tableau2.size(); y++) {
                if (!isFaceUp.get(tableau2.get(y)))
                    System.out.print("  ?? |");
                else
                    System.out.print("  " + tableau2.get(y).charAt(0) + suits.get(tableau2.get(y)) + " |");
            }
            System.out.println();

            for (int h = 0; h < tableau2.size(); h++) {
                System.out.print("------");
            }
            System.out.println();
        }
        if (tableau3.isEmpty()) {
            System.out.println("  t3  ");
            System.out.println("-------");

            System.out.println("     ");
            System.out.println("-------");
        } else if (tableau3.size() > 0) {
            System.out.println("  t3  ");
            for (int y = 0; y < tableau3.size(); y++) {
                System.out.print("------");
            }
            System.out.println();

            for (int y = 0; y < tableau3.size(); y++) {
                if (!isFaceUp.get(tableau3.get(y)))
                    System.out.print("  ?? |");
                else
                    System.out.print("  " + tableau3.get(y).charAt(0) + suits.get(tableau3.get(y)) + " |");
            }
            System.out.println();

            for (int h = 0; h < tableau3.size(); h++) {
                System.out.print("------");
            }
            System.out.println();
        }
        if (tableau4.isEmpty()) {
            System.out.println("  t4  ");
            System.out.println("-------");
            System.out.println("     ");
            System.out.println("-------");
        } else if (tableau4.size() > 0) {
            System.out.println("  t4  ");
            for (int y = 0; y < tableau4.size(); y++) {
                System.out.print("------");
            }
            System.out.println();

            for (int y = 0; y < tableau4.size(); y++) {
                if (!isFaceUp.get(tableau4.get(y)))
                    System.out.print("  ?? |");
                else
                    System.out.print("  " + tableau4.get(y).charAt(0) + suits.get(tableau4.get(y)) + " |");
            }
            System.out.println();

            for (int h = 0; h < tableau4.size(); h++) {
                System.out.print("------");
            }
            System.out.println();

        }
        if (tableau5.isEmpty()) {
            System.out.println("  t5  ");
            System.out.println("-------");

            System.out.println("     ");
            System.out.println("-------");
        } else if (tableau5.size() > 0) {
            System.out.println("  t5  ");
            for (int y = 0; y < tableau5.size(); y++) {
                System.out.print("------");
            }
            System.out.println();

            for (int y = 0; y < tableau5.size(); y++) {
                if (!isFaceUp.get(tableau5.get(y)))
                    System.out.print("  ?? |");
                else
                    System.out.print("  " + tableau5.get(y).charAt(0) + suits.get(tableau5.get(y)) + " |");
            }
            System.out.println();

            for (int h = 0; h < tableau5.size(); h++) {
                System.out.print("------");
            }
            System.out.println();

        }
        if (tableau6.isEmpty()) {
            System.out.println("  t6  ");
            System.out.println("-------");

            System.out.println("     ");
            System.out.println("-------");
        } else if (tableau6.size() > 0) {
            System.out.println("  t6  ");
            for (int y = 0; y < tableau6.size(); y++) {
                System.out.print("------");
            }
            System.out.println();

            for (int y = 0; y < tableau6.size(); y++) {
                if (!isFaceUp.get(tableau6.get(y)))
                    System.out.print("  ?? |");
                else
                    System.out.print("  " + tableau6.get(y).charAt(0) + suits.get(tableau6.get(y)) + " |");
            }
            System.out.println();

            for (int h = 0; h < tableau6.size(); h++) {
                System.out.print("------");
            }
            System.out.println();

        }
        if (tableau7.isEmpty()) {
            System.out.println("  t7  ");
            System.out.println("-------");

            System.out.println("     ");
            System.out.println("-------");
        } else if (tableau7.size() > 0) {
            System.out.println("  t7  ");
            for (int y = 0; y < tableau7.size(); y++) {
                System.out.print("------");
            }
            System.out.println();

            for (int y = 0; y < tableau7.size(); y++) {
                if (!isFaceUp.get(tableau7.get(y)))
                    System.out.print("  ?? |");
                else
                    System.out.print("  " + tableau7.get(y).charAt(0) + suits.get(tableau7.get(y)) + " |");
            }
            System.out.println();

            for (int h = 0; h < tableau7.size(); h++) {
                System.out.print("------");
            }
            System.out.println();

        }



    }
}
