import java.util.Scanner;
import java.math.BigInteger;

public class RSAsecurity {
    public static void main(String[] args){
        System.out.println("Calculate original message sent to two different users."
        +" Enter the 5 values needed: e1, e2, c1, c2, and N."); 
                                                                       
        Scanner in = new Scanner(System.in);
        BigInteger e1 = BigInteger.valueOf(11); // new BigInteger(in.next());              // example input = 11 5 1514 8189 18923
        BigInteger e2 = BigInteger.valueOf(5); //new BigInteger(in.next());
        BigInteger c1 = BigInteger.valueOf(1514); //new BigInteger(in.next());
        BigInteger c2 = BigInteger.valueOf(8189); //new BigInteger(in.next());
        BigInteger n = BigInteger.valueOf(18923); //new BigInteger(in.next());

        BigInteger t1 = ModInv.modInvNoPrint(e1, e2);
        BigInteger t2 = (t1.multiply(e1).subtract(BigInteger.ONE)).divide(e2);

        BigInteger m1 = ModExpo.modExpNoPrint(c1, t1, n);

        BigInteger inv = ModInv.modInvNoPrint(c2, n);
        BigInteger m2 = ModExpo.modExpNoPrint(inv, t2, n);

        System.out.println("\n----------------------------"
        + "\n1. t1 = e1^-1 (mod e2) = " + t1 
        + "\n\n2. t2 = (t1*e1)-1 / e2 = " + t2 
        + "\n\n3. m1 = c1 ^ t1 (mod n) = " + m1
        + "\n\n4. m2 = c2^(-1)(t2) mod n:"
        + "\n   -- Inv = c2^-1 (mod n) = " + inv 
        + "\n   -- m2 = inv^ t2 (mod n) = " + m2);
        System.out.println("\nmessage = " + m1.multiply(m2).mod(n) + "\n----------------------------");
        in.close();
    }
}
// e1 = 11
// e2 = 5
// c1 = 1_514
// c2 = 8_189
// N = 18_923