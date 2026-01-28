public class Exercise12_7 {
    public static void main(String[] args) {
        // expect exception to be thrown on "1021" and "10a01"
        String[] binaryStrings = {"1011", "1021", "0000", "111111", "10a01"};
        System.out.println("Binary -> Decimal");
        for (String input : binaryStrings) {
            try {
                int dec = bin2Dec(input);
                System.out.println(input + " -> " + dec);
            } catch (BinaryFormatException e) {
                System.out.println("Error with \"" + input + "\": " + e.getMessage());
            }
        }
    }

    // bin2Dec converts a binary string to a decimal and throws BinaryFormatException
    public static int bin2Dec(String binaryString) throws BinaryFormatException {
        if (binaryString == null || binaryString.length() == 0) {
            throw new BinaryFormatException("empty string");
        }

        int value = 0;

        for (int i = 0; i < binaryString.length(); i++) {
            char currentChar = binaryString.charAt(i);

            // if not 0 or 1, then not binary format
            if (currentChar != '0' && currentChar != '1') {
                throw new BinaryFormatException("not binary(found '" + currentChar + "')");
            }

            value = value * 2 + (currentChar - '0');
        }
        return value;
    }

    // custom exception class for bad binary strings
    static class BinaryFormatException extends Exception {
        public BinaryFormatException(String message) {
            super(message);
        }
    }
}
