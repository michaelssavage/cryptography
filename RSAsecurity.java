import java.util.Scanner;
import java.math.BigInteger;

public class RSAsecurity {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.print("\033[H\033[2J");  
        System.out.flush();
        System.out.println("Enter 'n' if using the same public modulus \nor 'e' if using the same exponent: ");


        String path = in.next();
        if(path.equals("n")){
            System.out.println("Calculate original message sent to two different users."
            +" Enter the 5 values needed: e1, e2, c1, c2, and N.");

            // e1 = 11
            // e2 = 5
            // c1 = 1_514
            // c2 = 8_189
            // N = 18_923
                                                                        
            BigInteger e1 = BigInteger.valueOf(11); // new BigInteger(in.next());
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
        }
        else if (path.equals("e")){            
            BigInteger c1 = BigInteger.valueOf(31); //new BigInteger(in.next());
            BigInteger n1 = BigInteger.valueOf(33); //new BigInteger(in.next());

            BigInteger c2 = BigInteger.valueOf(29); //new BigInteger(in.next());
            BigInteger n2 = BigInteger.valueOf(35); //new BigInteger(in.next());

            BigInteger c3 = BigInteger.valueOf(25); //new BigInteger(in.next());
            BigInteger n3 = BigInteger.valueOf(39); //new BigInteger(in.next());

            BigInteger n;

            if(n1.gcd(n2).equals(BigInteger.ONE)){
                n = n1.multiply(n2);
                BigInteger m1 = message(n, n1, c1);
                BigInteger m2 = message(n, n2, c2);
                System.out.println("\n------------------"
                +"\nGCD of n1 and n2 = " +n1.gcd(n2)
                +"\nm1 = " + m1
                +"\nm2 = " + m2
                + "\nThe message = " +m1.add(m2).mod(n) 
                + "\n------------------\n");
            }
            if(n2.gcd(n3).equals(BigInteger.ONE)){
                n = n2.multiply(n3);
                BigInteger m2 = message(n, n2, c2);
                BigInteger m3 = message(n, n3, c3);
                System.out.println("\n------------------"
                +"\nGCD of n2 and n3 = " +n2.gcd(n3)
                +"\nm2 = " + m2
                +"\nm3 = " + m3
                +"\nThe message = " +m2.add(m3).mod(n)
                + "\n------------------\n");
            }
            if(n1.gcd(n3).equals(BigInteger.ONE)){
                n = n1.multiply(n3);
                BigInteger m1 = message(n, n1, c1);
                BigInteger m3 = message(n, n3, c3);
                System.out.println("\n------------------"
                +"\nGCD of n1 and n3 = " +n1.gcd(n3)
                +"\nm1 = " + m1
                +"\nm3 = " + m3
                +"\nThe message = " + m1.add(m3).mod(n)
                + "\n------------------\n");
            }
        }
        else {
            System.out.println("Error: Input 'n' or 'e' only.");
        }
        in.close();
    }
    public static BigInteger message(BigInteger n, BigInteger n1, BigInteger c){
        BigInteger ni = n.divide(n1);
        BigInteger y = ModInv.modInvNoPrint(ni, n1);

        System.out.println("result = " + c + "*" + ni + "*" + y);
        return c.multiply(ni).multiply(y);
    }
}
