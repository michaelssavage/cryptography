import java.util.Scanner;
import java.math.BigInteger;

public class ElGamalDecrypt {
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        
        System.out.print("\nInput values x and p: ");           // 3, 7 and 29
        BigInteger x = new BigInteger(in.next());
        BigInteger p = new BigInteger(in.next());


        System.out.print("Input values c1||c2: ");               // 5 and 19
        BigInteger c1 = new BigInteger(in.next());
        BigInteger c2 = new BigInteger(in.next());

        BigInteger power = p.subtract(BigInteger.ONE).subtract(x);
        System.out.println("----------------------\n|      |  a   |  y   |\n----------------------");
        BigInteger temp = ModExpo.modExp(c1,power, p);
        System.out.println("----------------------\n\n");

        BigInteger message = temp.multiply(c2).mod(p);

        System.out.println("-----------------\n"
        +"temp = c1 ^ p-1-x (mod p)" + " ---> " + c1 + " ^ " +power + " (mod " + p + ") = " + temp
        +"\nmessage = temp * c2 (mod p)" + " ---> " + temp + " * " + c2 + " (mod " + p + ") = " + message
        + "\n-----------------\n");


        System.out.println("decrypted message = " + message);


        in.close();
    }
}