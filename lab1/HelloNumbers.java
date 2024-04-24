public class HelloNumbers {
    public static void main(String args[]) {
        int sum = 0, x = 0;
        while (x < 9) {
            sum += x;
            System.out.print(sum + " ");
            x += 1;
        }
        sum += 9;
        System.out.println(sum);
    }
}