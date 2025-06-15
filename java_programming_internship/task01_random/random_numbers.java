
import java.util.Random;
import java.util.Scanner;

public class random_numbers {

    public static void main(String args[]) {
        Random r = new Random();
        System.out.println("Welcome to the Number Guessing Game!");
        
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        while (flag) {
            int n1 = r.nextInt(1, 100 + 1);
            //System.out.println("Random number between 1 and 100: " + n1);
            int chances = 10;
            int attempt = 1;
            System.out.println("You have " + chances + " chances to guess the random number genertaed between 1 and 100.");
            do {
                System.out.print("Enter your guess" + (attempt) + ": ");
                int guess = sc.nextInt();
                if (guess < 1 || guess > 100) {
                    System.out.println("Please enter a number between 1 and 100.");
                    continue;
                }
                if (guess < 0 || guess > 100) {
                    System.out.println("Enter a valid guess within a range(0-100).");
                }
                if (guess == n1) {
                    System.out.println("Congratulations! You guessed the number correctly.");
                    break;
                } else if (guess < n1) {
                    System.out.println("Your guess is too low. Try again.");
                } else {
                    System.out.println("Your guess is too high. Try again.");
                }
                attempt++;
            } while (attempt <= chances);
            if (attempt > chances) {
                System.out.println("Sorry, you've used all your chances. The number was: " + n1);
            }
            System.out.println("Do you want to play again? (yes/no)");
            String playAgain = sc.next();
            if (playAgain.equalsIgnoreCase("no")) {
                flag = false;
                System.out.println("Thank you for playing! Goodbye!");
            } else if (!playAgain.equalsIgnoreCase("yes")) {
                System.out.println("Invalid input. Exiting the game.");
                flag = false;
            }

        }
    }
}
