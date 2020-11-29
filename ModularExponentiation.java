import java.util.Scanner;
import java.math.BigInteger;

public class ModExpo {

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        
        System.out.println("a^x (mod n)\nEnter a, x, and n: ");

        // input a, x and n only
        BigInteger a = new BigInteger(in.next());
        BigInteger x = new BigInteger(in.next());
        BigInteger n = new BigInteger(in.next());

        BigInteger result = modExp(a,x,n);
        System.out.println("The result is: " + result);
        
        in.close();
    }
    // y = (a^x) % n, modular exponentiation function
    private static BigInteger modExp(BigInteger a, BigInteger x, BigInteger n) {

        // the square and multiply algorithm, right to left variant.

        // init result (y) = 1                                         Example:(123^5) % 511
        BigInteger y = BigInteger.ONE;                              // --------------------
        for(int i = 0; i < x.bitLength(); i++){                     // |      |  a  |  y  |
            // check if exponent bit is odd                            --------------------
            if (x.testBit(i)){                                      // | init | 123 |  1  |
                //result = (result * base) % modulus                   | x0=1 | 310 | 123 |
                y = (y.multiply(a)).mod(n);                         // | x1=0 | 32  | 123 |
            }                                                       // | x2=1 |  2  | 359 |
            //base = (base^2) % modulus                                --------------------
            a = (a.multiply(a)).mod(n);                             //     answer = 359
            }
        return y;
    }
}
