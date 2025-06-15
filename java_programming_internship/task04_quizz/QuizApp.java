import java.util.*;

public class QuizApp {
    static class Question {
        String question;
        String[] options;
        char correctAnswer;

        public Question(String question, String[] options, char correctAnswer) {
            this.question = question;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }
    }

    static List<Question> questions = new ArrayList<>();
    static int score = 0;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadQuestions();

        for (Question q : questions) {
            askQuestionWithTimer(q, 15); // 15 seconds per question
        }

        System.out.println("\nQuiz Completed!");
        System.out.println("Your Score: " + score + " out of " + questions.size());
    }

    static void loadQuestions() {
        questions.add(new Question(
            "1. What is the time complexity of binary search?",
            new String[]{"a) O(n)", "b) O(n log n)", "c) O(log n)", "d) O(1)"},
            'c'
        ));

        questions.add(new Question(
            "2. Which data structure uses FIFO order?",
            new String[]{"a) Stack", "b) Queue", "c) Tree", "d) Graph"},
            'b'
        ));

        questions.add(new Question(
            "3. What is the worst-case time complexity of quicksort?",
            new String[]{"a) O(n)", "b) O(n log n)", "c) O(n^2)", "d) O(log n)"},
            'c'
        ));

        questions.add(new Question(
            "4. Which of the following is not a linear data structure?",
            new String[]{"a) Array", "b) Linked List", "c) Stack", "d) Tree"},
            'd'
        ));

        questions.add(new Question(
            "5. What is the height of a balanced binary tree with n nodes?",
            new String[]{"a) log n", "b) n", "c) sqrt(n)", "d) n log n"},
            'a'
        ));

        questions.add(new Question(
            "6. Which traversal method visits nodes in ascending order in a BST?",
            new String[]{"a) Preorder", "b) Inorder", "c) Postorder", "d) Level order"},
            'b'
        ));

        questions.add(new Question(
            "7. Which algorithm is used for finding the shortest path in a graph?",
            new String[]{"a) Dijkstra", "b) DFS", "c) BFS", "d) Kruskal"},
            'a'
        ));
    }

    static void askQuestionWithTimer(Question q, int seconds) {
        printBorder();
        System.out.println(q.question);
        for (String option : q.options) {
            System.out.println(option);
        }
        printBorder();

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("\nTime's up! Moving to next question.");
                System.exit(0); // End the quiz if time runs out
            }
        };

        timer.schedule(task, seconds * 1000);
        System.out.print("Enter your answer (a/b/c/d): ");
        String answer = scanner.nextLine().toLowerCase();

        timer.cancel(); // Stop the timer if answered in time

        if (answer.length() > 0 && answer.charAt(0) == q.correctAnswer) {
            System.out.println("Correct!");
            score++;
        } else {
            System.out.println("Incorrect. Correct answer: " + q.correctAnswer);
        }
    }

    static void printBorder() {
        System.out.println("****************************************");
    }
}
