/**
 * Created by temp2015 on 1/12/2016.
 */
public class printInt {
    public static void printInt(int[] input) {
        int length = input.length;

        System.out.println("*****");

        for (int i = 0; i < length; i++) {
            System.out.println(input[i]);
        }
        System.out.println(input.length);
    }
}
