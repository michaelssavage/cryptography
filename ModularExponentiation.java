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

        System.out.println("\n"+ x + " = " + x.toString(2));
        System.out.println("----------------------\n|      |  a   |  y   |\n----------------------");
        BigInteger result = modExp(a,x,n);
        System.out.println("----------------------\n" +"The result is: " + result + "\n");
        
        in.close();
    }
    // y = (a^x) % n, modular exponentiation function
    public static BigInteger modExp(BigInteger a, BigInteger x, BigInteger n) {

        // the square and multiply algorithm, right to left variant.
        // init result (y) = 1
        BigInteger y = BigInteger.ONE;
        int bitValue;
        
        for(int i = 0; i < x.bitLength(); i++){
            // check if exponent bit is odd
            if (x.testBit(i)){
                //result = (result * base) % modulus
                y = (y.multiply(a)).mod(n);
                bitValue = 1;
            }
            else{
                bitValue = 0;
            }
            //base = (base^2) % modulus
            a = (a.multiply(a)).mod(n);

            System.out.println("| x" + i + "="+ bitValue + " | " + String.format("%-4s", a) +  " | " + String.format("%-4s", y) +  " |");
            }
        return y;
    }
}

// Example:(123^5) % 511 = 359
