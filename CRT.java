import java.util.Scanner;
import java.math.BigInteger;

public class CRT {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        
        System.out.println("Chinese Remainder Theorem helps us compute large modular exponentiation"
        +"\n\na = ai * ni * yi (mod n)"
        +"\nmod n is split to pq"
        +"\nEnter base, power, p and q: ");

        // inputs
        BigInteger base = new BigInteger(in.next());
        BigInteger power = new BigInteger(in.next());
        BigInteger p = new BigInteger(in.next());
        BigInteger q = new BigInteger(in.next());

        BigInteger n = p.multiply(q);
        BigInteger result = crt(base,power,p,q,n);
        System.out.println("\nThe modular exponentiation = " + result);
        in.close();
        
    }
    // decrypt message using hashMessage, decrypt, prime P and Q and N
    private static BigInteger crt(BigInteger a, BigInteger e, BigInteger p, BigInteger q, BigInteger n){

        // a = c^d mod n where a = c^d mod p and a = c^d mod q
        //Using the chinese remainder theorem, 
        //a = SIGMA(i=1;i<=k;i+=1) ai * ni * yi (mod n)                     (27^37) % 55 where p = 11 and q = 5                                           
        BigInteger a1 = ModExpo.modExp(a.mod(p), e, p);              // a1 = (27 % 11)^37 % 11 = 3
        BigInteger n1 = n.divide(p);                                        // n1 = 55 / 11 = 5
        BigInteger y1 = ModInv.modInv(n1,p);                         // y1 = (5^-1) % 11 = 9

        BigInteger a2 = ModExpo.modExp(a.mod(q), e, q);              // a2 = (27 % 5)^37 % 5 = 2
        BigInteger n2 = n.divide(q);                                        // n2 = 55 / 5 = 11
        BigInteger y2 = ModInv.modInv(n2,q);                         // y2 = (11^-1) % 5 = 1
        
        BigInteger m1 = a1.multiply(n1).multiply(y1);                       // m1 = 3 * 5 * 9 = 135
        BigInteger m2 = a2.multiply(n2).multiply(y2);                       // m2 = 2 * 11 * 1 = 22

        return (m1.add(m2)).mod(n);                                         // answer = (135 + 22) % 55 = 47
    }
}

// Example 27 37 11 5
