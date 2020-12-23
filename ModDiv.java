import java.util.Scanner;
import java.math.BigInteger;

public class ModDiv {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        // 67/ 97 mod 157
        System.out.print("\nInput values a, b and n: "); // 67 97 157
        BigInteger a = new BigInteger(in.next());
        BigInteger b = new BigInteger(in.next());
        BigInteger n = new BigInteger(in.next());

        System.out.println("(" + a + " / " + b + ") mod " + n + " ----> " + a + " * (" + b + " ^-1) mod " + n);

        BigInteger result = a.multiply(ModInv.modInv(b, n)).mod(n);
        System.out.println("result = " + result);
        in.close();
    }
}
