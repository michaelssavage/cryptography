import java.util.Scanner;
import java.math.BigInteger;
 
public class GCD
{
    // Read a String from the standard input using Scanner
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        
        System.out.println("Enter two numbers: ");

        // input a, x and n only
        BigInteger a = new BigInteger(in.next());
        BigInteger b = new BigInteger(in.next());

        BigInteger result = gcd(a,b);
        System.out.println("GCD = " + result);
        
        in.close();
    }
    // get the greatest common divisor
    private static BigInteger gcd(BigInteger a, BigInteger b){
        
        // While the number is positive.                            Example: gcd(24,36)
        while(b.compareTo(BigInteger.ZERO) > 0){  // -------------------------------------------------
            BigInteger tmp = a.mod(b);            // | loop 1:       | loop 2:       | loop 3:       |
            a = b;                                // | tmp = 24 % 36 | tmp = 36 % 24 | tmp = 24 % 12 |
            b = tmp;                              // | a = 36        | a = 24        | phi = 12      |
        }                                         // | b = 24        | b = 12        | e = 0         |
        return a;                                 // -------------------------------------------------
    }
}
