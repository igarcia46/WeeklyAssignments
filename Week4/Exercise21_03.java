import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Exercise21_03 {

    // java keywords from LiveExample 21.7
    private static final Set<String> KEYWORDS = new HashSet<>(Arrays.asList(
            "abstract","assert","boolean","break","byte","case","catch","char","class","const",
            "continue","default","do","double","else","enum","extends","final","finally","float",
            "for","goto","if","implements","import","instanceof","int","interface","long","native",
            "new","package","private","protected","public","return","short","static","strictfp",
            "super","switch","synchronized","this","throw","throws","transient","try","void",
            "volatile","while","true","false","null"
    ));

    public static void main(String[] args) throws Exception {
        Scanner consoleInput = new Scanner(System.in);
        System.out.print("Enter location of Java source file: ");
        String fileName = consoleInput.nextLine();

        File sourceFile = new File(fileName);
        // handle logic in countKeywords() method
        if (sourceFile.exists()) {
            int keywordCount = countKeywords(sourceFile);
            System.out.println("The number of keywords in " + fileName + " is " + keywordCount);
        } else {
            System.out.println("File " + fileName + " does not exist");
        }
    }

    public static int countKeywords(File sourceFile) throws Exception {
        int keywordCount = 0;
        boolean insideLineComment = false;
        boolean insideBlockComment = false;
        boolean insideStringLiteral = false;

        StringBuilder currentToken = new StringBuilder();

        BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
        int characterCode;
        // set previousCharacter to null at start
        char previousCharacter = '\0';

        while ((characterCode = reader.read()) != -1) {
            char currentCharacter = (char) characterCode;

            // end of line ends a // comment
            if (currentCharacter == '\n') {
                insideLineComment = false;
                keywordCount += finalizeToken(currentToken);
                previousCharacter = currentCharacter;
                continue;
            }

            // skip everything inside a line comment
            if (insideLineComment) {
                previousCharacter = currentCharacter;
                continue;
            }

            // skip everything inside a block comment
            if (insideBlockComment) {
                if (previousCharacter == '*' && currentCharacter == '/') {
                    insideBlockComment = false;
                }
                previousCharacter = currentCharacter;
                continue;
            }

            // skip everything inside a string literal
            if (insideStringLiteral) {
                if (currentCharacter == '"' && previousCharacter != '\\') {
                    insideStringLiteral = false;
                }
                previousCharacter = currentCharacter;
                continue;
            }

            // detect start of line comment //
            if (previousCharacter == '/' && currentCharacter == '/') {

                // remove the '/' that may have been added to the token
                if (currentToken.length() > 0 &&
                        currentToken.charAt(currentToken.length() - 1) == '/') {
                    currentToken.deleteCharAt(currentToken.length() - 1);
                }

                keywordCount += finalizeToken(currentToken);
                insideLineComment = true;
                previousCharacter = currentCharacter;
                continue;
            }

            // detect start of block comment /*
            if (previousCharacter == '/' && currentCharacter == '*') {
                if (currentToken.length() > 0 &&
                        currentToken.charAt(currentToken.length() - 1) == '/') {
                    currentToken.deleteCharAt(currentToken.length() - 1);
                }

                keywordCount += finalizeToken(currentToken);
                insideBlockComment = true;
                previousCharacter = currentCharacter;
                continue;
            }

            // detect start of string literal
            if (currentCharacter == '"') {
                keywordCount += finalizeToken(currentToken);
                insideStringLiteral = true;
                previousCharacter = currentCharacter;
                continue;
            }

            // build token from valid identifier characters
            if (isIdentifierCharacter(currentCharacter)) {
                currentToken.append(currentCharacter);
            } else {
                keywordCount += finalizeToken(currentToken);
            }

            previousCharacter = currentCharacter;
        }

        // final token at end of file
        keywordCount += finalizeToken(currentToken);
        reader.close();
        return keywordCount;
    }

    // return true if the character can be part of a java identifier
    private static boolean isIdentifierCharacter(char character) {
        return Character.isLetterOrDigit(character)
                || character == '_'
                || character == '$';
    }

    private static int finalizeToken(StringBuilder currentToken) {
        if (currentToken.length() == 0) {
            return 0;
        }

        String word = currentToken.toString();
        currentToken.setLength(0);
        return KEYWORDS.contains(word) ? 1 : 0;
    }
}
