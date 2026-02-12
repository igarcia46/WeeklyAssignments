import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class Exercise20_11 {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Error: No source file specified");
            return;
        }

        // make sure file exists
        File file = new File(args[0]);
        if (!file.exists()) {
            System.out.println("File not found: " + args[0]);
            return;
        }

        boolean ok = hasCorrectGroupingPairs(file);

        if (ok) {
            System.out.println("Correct grouping pairs");
        } else {
            System.out.println("Incorrect grouping pairs");
        }
    }

    private static boolean hasCorrectGroupingPairs(File file) {
        Stack<Character> stack = new Stack<>();

        // already checked if exists but new Scanner() throws a checked exception that needs to be handled
        try (Scanner input = new Scanner(file)) {
            while (input.hasNextLine()) {
                String line = input.nextLine();

                for (int i = 0; i < line.length(); i++) {
                    char currentCharacter = line.charAt(i);

                    // opening symbols get pushed first, if not an opening symbol, then invalid grouping by default
                    if (currentCharacter == '(' || currentCharacter == '{' || currentCharacter == '[') {
                        stack.push(currentCharacter);
                    } else if (currentCharacter == ')' || currentCharacter == '}' || currentCharacter == ']') {
                        if (stack.isEmpty()) return false;

                        char top = stack.pop();
                        if (!matches(top, currentCharacter)) return false;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            return false;
        }

        // if stack is empty, means all groupings were correct
        return stack.isEmpty();
    }

    private static boolean matches(char open, char close) {
        return (open == '(' && close == ')')
                || (open == '{' && close == '}')
                || (open == '[' && close == ']');
    }
}
