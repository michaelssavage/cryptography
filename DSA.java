import java.util.Scanner;
import java.math.BigInteger;

public class DSA {      // Digital Signature Algorithm
    public static void main(String[] args){
        System.out.print("Enter the values p, q, g, and x: "); 
                                                                       
        Scanner in = new Scanner(System.in);
        
        BigInteger p = new BigInteger(in.next());
        BigInteger q = new BigInteger(in.next()); 
        BigInteger g = new BigInteger(in.next());
        BigInteger x = new BigInteger(in.next());

        BigInteger y = ModExpo.modExpNoPrint(g, x, p);

        System.out.print("y = " + y
        +"\nEnter 's' for signature or 'v' for verification: "); // example encrypt m = 2
        String path = in.next();
        if(path.equals("s")){
            System.out.print("Enter the values k and h(m): ");
            BigInteger k = new BigInteger(in.next());
            BigInteger m = new BigInteger(in.next());

            BigInteger s1 = ModExpo.modExpNoPrint(g, k, p);
            s1 = s1.mod(q);

            BigInteger temp = ModInv.modInvNoPrint(k, q);
            BigInteger message = m.add(x.multiply(s1)).mod(q);
            BigInteger s2 = temp.multiply(message).mod(q);

            System.out.println("Signed message = " + s1 + "||" + s2);
        }
        else if(path.equals("v")){
            System.out.print("Enter the values s1||s2 and h(m): ");
            BigInteger s1 = new BigInteger(in.next());
            BigInteger s2 = new BigInteger(in.next());
            BigInteger m = new BigInteger(in.next());

            BigInteger w = ModInv.modInvNoPrint(s2, q);
            BigInteger u1 = m.multiply(w).mod(q);
            BigInteger u2 = s1.multiply(w).mod(q);

            BigInteger t1 = ModExpo.modExpNoPrint(g, u1, p);
            BigInteger t2 = ModExpo.modExpNoPrint(y, u2, p);
            BigInteger v = t1.multiply(t2).mod(p).mod(q);

            System.out.println(v + " = " + s1);
        }
        else {
            System.out.println("Error: Input 's' or 'v' only.");
        }
        in.close();
    }
}

// Example: 23 11 4 10
// y = 6

// signature ==> 3, 19
// s1||s2 = 7||4