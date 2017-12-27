import java.util.Scanner;

public class Input {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String line;

        while (sc.hasNextLine()) {

            line = sc.nextLine();

            if (!line.isEmpty() && !line.matches("^#.*")) {

                if (line.split(" ").length != 2) {
                    System.out.println("Bad input");
                    System.exit(1);
                }

                int width = Integer.parseInt(line.split(" ")[0]);
                int length = Integer.parseInt(line.split(" ")[1]);

                if ((length >= 1 && length <= 600) && (width >= 1) && ((length * width) % 4 == 0)) {
                    // The input is ok. Run program.

                } else {
                    System.err.println("Invalid input");
                }

                System.out.println(width);
                System.out.println(length);
            }
        }

    }

}
