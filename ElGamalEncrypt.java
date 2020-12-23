import java.util.Scanner;
import java.math.BigInteger;

public class ElGamalEncrypt {
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        
        System.out.print("\nInput values g, x, and p: ");           // example: 3 7 29
        BigInteger g = new BigInteger(in.next());
        BigInteger x = new BigInteger(in.next());
        BigInteger p = new BigInteger(in.next());

        BigInteger y = ModExpo.modExpNoPrint(g, x, p);
        System.out.println("-----------------\ny = " + g + "^" + x + " mod " + p+ "\n-----------------\n");

        System.out.print("Input values k and m: ");               // example: 5 19
        BigInteger k = new BigInteger(in.next());
        BigInteger m = new BigInteger(in.next());

        BigInteger c1 = ModExpo.modExpNoPrint(g, k, p);
        
        BigInteger temp = ModExpo.modExpNoPrint(y, k, p);
        BigInteger temp1 = ModExpo.modExpNoPrint(m, BigInteger.ONE, p);
        BigInteger c2 = ModExpo.modExpNoPrint(temp.multiply(temp1), BigInteger.ONE, p);

        System.out.println("-----------------\nc1 = " + g + "^" + k + " mod " + p);
        System.out.println("c2 = " + m + " * (" + y + "^" + k + ") mod " + p + "\n-----------------\n");
        System.out.println("public key y = " + y);
        System.out.println("c1||c2 = " + c1 + "||" + c2 + "\n");

        in.close();
    }
}