/**
 *
 *         My First Finals Project 
 * [First Year, 1st Semester A.Y. 2023-2024]
 * 
 * VIBESWIFT: A CONCERT SEAT RESERVATIONS
 *
 * 
 * This is my first final project, a console-based Concert Seat Reservation System 
 * I created in my first semester. It allows users to reserve seats for concerts 
 * and features an ASCII drawing of a stadium for visualization. 
 *
 */


import java.util.Scanner;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;



public class Main {
    
    // initialize class variables (accessible to all methods)
    private static final Scanner scan = new Scanner(System.in);
    private static final Random random = new Random();
    private static HashMap<String, Map<String, Map<String, Integer>>> seatBlocks = new HashMap<>();
   
    public static void main(String[] args) {
        
        // initialize variables 
        String clientName;
        initializeHashMap(seatBlocks);        

        // ask for the name of the user
        do {
            System.out.print("Hi! What is your name?\n> ");
            clientName = scan.nextLine();       
            space(3);
        } while (clientName.isEmpty());

        
        // display VibeSwift ASCII Art and concert seat plan
        displayVibeSwift();
        displaySeatPlan();
        System.out.printf(" Hello, %s! Welcome to VibeSwift Concert Reservations%n", clientName);       
        
        // STARTING POINT
        mainMenu();
    }
    
    
    
    // METHODS FOR INITIALIZATION AND PREDETERMINED VALUES
    
    public static void initializeHashMap(HashMap seatBlocks) {
        seatBlocks.put("A", seatBlockValue(15, 4500));
        seatBlocks.put("B", seatBlockValue(15, 8500));
        seatBlocks.put("C", seatBlockValue(15, 4500));
        seatBlocks.put("D", seatBlockValue(15, 8500));
        seatBlocks.put("E", seatBlockValue(15, 8500));
        seatBlocks.put("F", seatBlockValue(15, 4500));
        seatBlocks.put("G", seatBlockValue(15, 8500));
        seatBlocks.put("H", seatBlockValue(15, 4500));
        seatBlocks.put("I", seatBlockValue(15, 4500));
        seatBlocks.put("J", seatBlockValue(15, 8500));
        seatBlocks.put("K", seatBlockValue(15, 8500));
        seatBlocks.put("L", seatBlockValue(15, 4500));
        seatBlocks.put("M", seatBlockValue(15, 4500));
        seatBlocks.put("N", seatBlockValue(15, 4500));
        seatBlocks.put("VIP", seatBlockValue(20, 12500));

    }

    public static Map<String, Map<String, Integer>> seatBlockValue(int capacity, int price) {
        Map<String, Map<String, Integer>> value = new HashMap<>();

        Map<String, Integer> info = new HashMap<>();
        info.put("Capacity", capacity);
        info.put("Price", price);
        
        // randomized seats whether available or reserved to simulate real life situations
        Map<String, Integer> seats = new HashMap<>();
        for (int seatNum=1; seatNum<capacity+1; seatNum++) {
            String strnum = String.valueOf(seatNum);
            double probability = 0.4;
            if (random.nextDouble() < probability) {
                seats.put(strnum, 1);
            } else {
                seats.put(strnum, 0);
            }    
        }
        value.put("Seats", seats);
        value.put("Info", info);
        return value;
    }
    

    
    // THE FOLLOWING METHODS ARE RESPONSIBLE FOR PROGRAM FLOW OF DIFFERENT MENU
    // 
    // This is the flow/structure of my program input menu:
    //    [1]  Select a Block
    //         - [1]  Reserve a Seat
    //         - [2]  View Available Seats
    //         - [3]  View Reserved Seats
    //         - [0]  Back
    //
    //    [2]  Show Pricing
    //    [3]  View Concert Information
    //    [0]  Exit VibeSwift
    //
    //  Managed by using methods and while loop as checkpoints
    //  This will allow me to handle different menus and logic well

    
    public static void mainMenu() {
        /**
         * @parent: MainMenu()
         * [1]  Select a Block
         * [2]  Show Pricing
         * [3]  View Concert Information
         * [0]  Back
         */
        while (true)  { // main menu checkpoint
            displayMainMenu();
            
            // main menu input, also validate
            int mainMenuInput = -1;
            boolean gettingInput = true;
            while (gettingInput) {
                try {
                    System.out.print("\t> ");
                    mainMenuInput = scan.nextInt();
                    scan.nextLine(); // consume next line
                    gettingInput = false;
                } catch (Exception e) {
                    System.out.println("\t Invalid Input. Please choose from 1 to 3.\n");
                    scan.nextLine(); // consume invalid input
                }
            }
            
            // switch statement of menus redirecting user to their selected option
            switch (mainMenuInput) {
                case 1 -> selectBlockMenu();
                case 2 -> showPricings();
                case 3 -> viewConcertInformation();   
                case 0 -> System.exit(0);
            }
            
            System.out.println("TEST: MAIN MENU");
            
        }        
    }

    
    
    // [1] Select a Block
    public static void selectBlockMenu() {
        /**
         * @parent: MainMenu()
         * [1]  Reserve Seat
         * [2]  View Available Seats
         * [3]  View Reserved Seats
         * [0]  Back
         */
        space(3);
        displayVibeSwift();
        displaySeatPlan();
        
        // get input and validate selected block first
        
        System.out.println();
        String block = null;
        boolean gettingInput = true;
            while (gettingInput) {
                try {
                    System.out.print("\t Choose among the labeled letters: \n");
                    System.out.print("\t> ");
                    block = scan.nextLine().toUpperCase();
                    if (!seatBlocks.containsKey(block)) {
                        System.out.println("\t Invalid Input. Please choose among the labeled capital letters.\n");
                        continue;
                    }
                    gettingInput = false;
                } catch (Exception e) {
                    System.out.println("\t Invalid Input. Please choose among the labeled capital letters.\n");
                }
            }
        // print options for this menu
        displaySeats(block);
        
        loop: // label to establish a checkpoint
        while (true)  { 
            // display menu options
            displayReserveSeatMenu();
            
            // select block menu input, also validate
            int blockMenuInput = -1;
            gettingInput = true;
            while (gettingInput) {
                try {
                    System.out.print("\t> ");
                    blockMenuInput = scan.nextInt();
                    scan.nextLine(); // consume next line
                    gettingInput = false;
                } catch (Exception e) {
                    System.out.println("\t Invalid Input. Please choose from 1 to 3.\n");
                    scan.nextLine(); // consume invalid input
                }
            }

            // switch statement of blockMenuInput
            switch (blockMenuInput) {
                case 1 -> {reserveThisSeat(block); break loop;}
                case 2 -> viewAvailableSeats(block);
                case 3 -> viewReservedSeats(block);
                case 0 -> {
                    // exit current menu, go back to parent menu
                    break loop; 
                }
            }
        }
    }
    
        
    
    // [1-1]Reserve this Seat
    public static void reserveThisSeat(String block) {
        // INPUT: ask for which seat to reserve
        System.out.println();
        int cap = seatBlocks.get(block).get("Info").get("Capacity");
        Map<String, Integer> seatList = seatBlocks.get(block).get("Seats");
        int seatnum = -1;

        // ask for the seat, invalid if seat is already reserved
        boolean gettingInput = true;
            while (gettingInput) {
                try {
                    System.out.print("\t Enter seat number (1-"+cap+")? \n");
                    System.out.print("\t> ");
                    seatnum = scan.nextInt();
                    scan.nextLine(); // consume new line
                    if (seatnum < 1 || seatnum > cap) {
                        System.out.println("\t Invalid Input. Please choose from 1 to"+cap+".\n");
                        continue;
                    }
                    // if seat is not available, provide alternative available seats
                    if (seatList.get(String.valueOf(seatnum)) == 1) {
                        System.out.println("\n\t Unfortunately, the seat "+block+"-"+seatnum+" has been already reserved.");
                        System.out.println("\t These are the available seats in Block-"+block+"\n");
                        viewAvailableSeats(block);
                        continue;
                    } 
                    gettingInput = false;
                } catch (Exception e) {
                    System.out.println("\t Invalid Input. Please choose among the labeled capital letters.\n");
                    scan.nextLine(); // consume invalid input
                }
            }

        // confirm or cancel, to proceed to payment
        int confirm = -1;
        gettingInput = true;
        while (gettingInput) {
            try {
                System.out.print("\n\n\n\t[1] Confirm\n");
                System.out.print("\t[0] Cancel\n");
                System.out.print("\t> ");
                confirm = scan.nextInt();
                if (confirm < 0 || confirm > 1) {
                    continue;
                }
                scan.nextLine(); // consume next line
                gettingInput = false;
            } catch (Exception e) {
                scan.nextLine(); // consume invalid input

            }
        }
        
        if (confirm == 0) {
            System.out.println("\tTransaction cancelled.");
            mainMenu();
        } else if (confirm == 1) {
            System.out.println("Press enter to proceed to payment:");
            scan.nextLine();
            payment(block, seatnum);
        }
    }

        
    
    // Payment
    public static void payment(String block, int seatnum) {
        /**
         * Final payment and transaction after reservation
         */
        Map<String, Integer> seatList = seatBlocks.get(block).get("Seats");
        int price = seatBlocks.get(block).get("Info").get("Price");

        // print order summary
        System.out.printf("\tTicket: %s-%02d%n\tPrice: %d", block, seatnum, price);

        // INPUT: validate payment
                int clientPay = 0;
        boolean processing = true;
        while (processing) {
        try {
            System.out.print("\n\tEnter Payment:  ");
            clientPay = scan.nextInt();
            scan.nextLine(); // consume next line
            if (clientPay < price) {
                System.out.println("\tInsufficient funds, try again.");
                continue;
            }
            processing = false;
        } catch (Exception e) {
            System.out.println("\tInvalid Transaction. Try again.");
            scan.nextLine(); // consume invalid input
        }
  
        // change the availability of bought seat to reserved
        seatList.put(String.valueOf(seatnum), 1);
        
        // print to summarize the final transaction details
        int change = clientPay - price;
        System.out.printf("\tChange: %d%n", change);
        
        System.out.println("=======================================================");
        System.out.println("");
        System.out.println("                 CONGRATULATIONS!");
        System.out.println("You've successfully purchased a ticket with VibeSwift!\n");
        System.out.printf("Ticket Details: %s%02d%n", block, seatnum);
        System.out.printf("Your Payment: %d%n", clientPay);
        System.out.printf("Your Change: %d", change);
        System.out.println("");
        System.out.println("=======================================================");
        System.exit(0);
        }
    }
    
    
        
    // [1-2] View Available Seats
    public static void viewAvailableSeats(String block) {
        System.out.println("\t\tAVAILABLE SEATS");
        int cap = seatBlocks.get(block).get("Info").get("Capacity");
        Map<String, Integer> seatList = seatBlocks.get(block).get("Seats");
        System.out.print("\n\t\t");
        for (int s=1; s<cap+1; s++) {
            if (seatList.get(String.valueOf(s)) == 1) {
                System.out.printf("-- ");
            } else {
            System.out.printf("%02d ", s);
            }
            
            if (s%5==0) {
                System.out.print("\n\n\t\t");
            }
        }
        space(2);

    }

    
    // [1-3] View Available Seats
    public static void viewReservedSeats(String block) {
        System.out.println("\t\tRESERVED SEATS");
        int cap = seatBlocks.get(block).get("Info").get("Capacity");
        Map<String, Integer> seatList = seatBlocks.get(block).get("Seats");
        System.out.print("\n\n\t\t");
        for (int s=1; s<cap+1; s++) {
            if (seatList.get(String.valueOf(s)) == 0) {
                System.out.printf("-- ");
            } else {
            System.out.printf("%02d ", s);
            }
            
            if (s%5==0) {
                System.out.print("\n\n\t\t");
            }
        }
        space(2);
    }
        

    
    // [2] Show Pricings
    public static void showPricings() {
        System.out.println("\n\n\tLOWER BOX - P8500");
        System.out.println("\t[B, D, J, K, G, E]\n");
        System.out.println("\tUPPER BOX - P4500");
        System.out.println("\t[A, C, I, M, N, L, H, F]\n");
        System.out.println("\tVIP SEATED - P12500\n\n");
        
    }
    
    
    // [3] View Concert Information
    public static void viewConcertInformation() {
        System.out.println("\n\n\t_____________________");
        System.out.println("\t|=== PERFORMERS ===|");
        System.out.println("\t|   Taylor Swift   |");
        System.out.println("\t|    The Weeknd    |");
        System.out.println("\t|      Ben&Ben     |");
        System.out.println("\t|     Blackpink    |");
        System.out.println("\t|    Arthur Nery   |");
        System.out.println("\t|___________________");
        System.out.println("\tBook Your Tickets Now!\n\n");
        
    }
    
    
    
    // THESE METHODS ARE RESPONSIBLE FOR DISPLAYS AND ASCII ARTS

    public static void space(int n) {
        // just a shortcut for printing "\n" character n times
        for (int i=0; i<n; i++) {
            System.out.print("\n");
        }
    }
    
    public static void displaySeats(String block) {
        int cap = seatBlocks.get(block).get("Info").get("Capacity");
        System.out.print("\n\n\t\t");
        for (int s=1; s<cap+1; s++) {
            System.out.printf("%02d ", s);
            if (s%5==0) {
                System.out.print("\n\n\t\t");
            }
        }
        space(2);
    }
        
    public static void displayMainMenu() {
        // method to display main menu options
        System.out.println("\n\t - - - -   MAIN MENU   - - - -");
        System.out.println("\t[1]  Select a Block (A, B, C...)");
        System.out.println("\t[2]  Show Pricings");
        System.out.println("\t[3]  View Concert Information");
        System.out.println("\t[0]  Exit VibeSwift\n");
    }
    
    public static void displayReserveSeatMenu() {
        System.out.println("\n\t - - -   SEAT SELECTION MENU   - - -");
        System.out.println("\t[1]  Reserve a Seat");
        System.out.println("\t[2]  View Available Seats");
        System.out.println("\t[3]  View Reserved Seats");
        System.out.println("\t[0]  Back\n");
    }
    
    public static void displayMyReservationsMenu() {
        // method to display main menu options
        System.out.println("\n\t - - -   RESERVATION MENU   - - -");
        System.out.println("\t[1]  View a Reservation (ex:  A-01, B-02..)");
        System.out.println("\t[2]  Cancel a Reservation (ex:  A-01, B-02..)");
        System.out.println("\t[3]  Proceed to Payment");
        System.out.println("\t[0]  Back\n");
    }
    
    public static void displayVibeSwift() {
        // method to draw the VibeSwift ASCII Art
        System.out.println("         __   ___ _         ___        _  __ _   ");
        System.out.println("         \\ \\ / (_) |__  ___/ __|_ __ _(_)/ _| |_ ");
        System.out.println("          \\ V /| | '_ \\/ -_)__ \\ V  V / |  _|  _|");
        System.out.println("           \\_/ |_|_.__/\\___|___/\\_/\\_/|_|_|  \\__|\n\n");
    }
    
    public static void displaySeatPlan() {
        // method to draw the concert seat plan for user's visualization
        // this took me hours to complete, worth it for the finals though
        System.out.println("        ===========================================");
        System.out.println("                     YOUR SEAT, YOUR VIBE!                           ");
        System.out.println("                   [RESERVE YOUR SEATS NOW!]         ");                
        System.out.println("                                                   ");
        System.out.println("        ===========================================");
        System.out.println("                                                   ");
        System.out.println("                                                   ");
        System.out.println("                       _____________               ");
        System.out.println("       /|¯¯¯¯¯|¯¯¯¯¯|  |¯¯¯¯¯¯¯¯¯¯¯|  |¯¯¯¯¯|¯¯¯¯¯|\\");
        System.out.println("       ||     |     |  |   STAGE   |  |     |     ||");
        System.out.println("       ||  A  |  B  |   \\_________/   |  E  |  F  ||");
        System.out.println("       ||     |     |                 |     |     ||");
        System.out.println("       ||_____|_____|   ...........   |_____|_____||");
        System.out.println("       ||     |     |   ...........   |     |     ||");
        System.out.println("       ||     |     |   ....VIP....   |     |     ||");
        System.out.println("       ||  C  |  D  |   ...........   |  G  |  H  ||");
        System.out.println("       ||     |     |   ...........   |     |     ||");
        System.out.println("       ||_____|_____|                 |_____|_____||");
        System.out.println("        \\\\     \\    /\\_______________/\\    /     //");
        System.out.println("         \\\\  I  \\  /    J    |   K     \\  /  L  //");
        System.out.println("          \\\\     \\/__________|__________\\/     //");
        System.out.println("           \\\\   /      M     |    N      \\    //");
        System.out.println("            \\\\_/_____________|____________\\__//");
        System.out.println("              ¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯     \n\n\n");
    }         
}
