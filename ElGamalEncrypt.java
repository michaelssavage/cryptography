import java.util.Scanner;
import java.math.BigInteger;

public class ElGamalEncrypt {
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);

        System.out.print("\nEnter 'e'for encryption, 'd' for decryption, or 's' for signature: "); // example encrypt m = 2
        String path = in.next();
        if(path.equals("e")){
        
            System.out.print("\nInput values g, x, and p: ");           // example: 3 7 29
            BigInteger g = new BigInteger(in.next());
            BigInteger x = new BigInteger(in.next());
            BigInteger p = new BigInteger(in.next());

            BigInteger y = ModExpo.modExpNoPrint(g, x, p);
            System.out.println("-----------------\ny = " + g + "^" + x + " mod " + p+ " = " + y + "\n-----------------\n");

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
        }
        else if(path.equals("d")){
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
        }
        else if(path.equals("s")){
            System.out.print("\nInput values g, k, m, x, and p: ");           // example: 3 7 29
            BigInteger g = new BigInteger(in.next());
            BigInteger k = new BigInteger(in.next());
            BigInteger m = new BigInteger(in.next());
            BigInteger x = new BigInteger(in.next());
            BigInteger p = new BigInteger(in.next());

            BigInteger s1 = ModExpo.modExpNoPrint(g, k, p);
            BigInteger temp = ModInv.modInvNoPrint(k, p.subtract(BigInteger.ONE));
            BigInteger message = m.subtract(x.multiply(s1));
            BigInteger s2 = temp.multiply(message).mod(p.subtract(BigInteger.ONE));

            System.out.println("Signed message = " + s1 + "||" + s2);

        } else {
            System.out.println("\nError: Type 'e', 'd' or 's'.");
        }

        in.close();
    }
}
