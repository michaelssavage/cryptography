import java.util.Scanner;
import java.math.BigInteger;

public class ModularExponentiation {

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        
        System.out.println("a^x (mod n)\nEnter a, x, and n: ");

        // input a, x and n only
        BigInteger a = new BigInteger(in.next());
        BigInteger x = new BigInteger(in.next());
        BigInteger n = new BigInteger(in.next());

        BigInteger result = modularExponent(a,x,n);
        System.out.println("The result is: " + result);
        
        in.close();
    }
    // y = a ^ x (mod n)
    private static BigInteger modularExponent(BigInteger base, BigInteger power, BigInteger modulo) {
        //init y = 1
        BigInteger result = BigInteger.ONE;
        for(int i = 0; i < power.bitLength(); i++){
            // check if exponent bit is odd
            if (power.testBit(i)){
                //y = (y * a) % n
                result = (result.multiply(base)).mod(modulo);
            }
            //a = (a * a) % n
            base = (base.multiply(base)).mod(modulo);
            }
        return result;
    }
}
