import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

public class Baseball {
    public static void main(String[] args) throws Exception {
        playGame();
    }

    public static void playGame() throws Exception {
        int x, y, z;
        int[] com = new int[3];
        Random r = new Random();

        x = Math.abs(r.nextInt() % 9) + 1;
        do {
            y = Math.abs(r.nextInt() % 9) + 1;
        } while (x == y);
        do {
            z = Math.abs(r.nextInt() % 9) + 1;
        } while (x == z || y == z);
        System.out.println("Generated Numbers (For debugging): " + x + " " + y + " " + z);

        com[0] = x;
        com[1] = y;
        com[2] = z;

        int count = 0;
        int strike = 0;
        int ball = 0;
        int[] usr = new int[3];

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        while (strike < 3) {
            System.out.println("첫 번째 변수:");
            usr[0] = Integer.parseInt(in.readLine());

            System.out.println("두 번째 변수:");
            usr[1] = Integer.parseInt(in.readLine());

            System.out.println("셋 번재 변수:");
            usr[2] = Integer.parseInt(in.readLine());

            strike = ball = 0;

            if (usr[0] == com[0]) strike++;
            if (usr[1] == com[1]) strike++;
            if (usr[2] == com[2]) strike++;

            if (usr[0] == com[1]) ball++;
            if (usr[0] == com[2]) ball++;
            if (usr[1] == com[0]) ball++;
            if (usr[1] == com[2]) ball++;
            if (usr[2] == com[0]) ball++;
            if (usr[2] == com[1]) ball++;

            System.out.println("strike: " + strike + " ball: " + ball);
            count++;
        }

        displayResult(count);
    }

    public static void displayResult(int result) {
        if (result <= 2) {
            System.out.println("참 잘했어요!");
        } else if (result <= 5) {
            System.out.println("잘했어요!");
        } else if (result <= 9) {
            System.out.println("보통이네요!");
        } else {
            System.out.println("분발하세요!");
        }
    }

}