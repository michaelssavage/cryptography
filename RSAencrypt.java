import java.util.Scanner;
import java.math.BigInteger;

public class RSAencrypt {
    public static void main(String[] args){
        System.out.println("Given p, q and e, find the private key."); 
                                                                       
        Scanner in = new Scanner(System.in);
        
        BigInteger p = new BigInteger(in.next());              // example input = 5 11 17
        BigInteger q = new BigInteger(in.next());              // n = 55, p = 5 and q = 11
        BigInteger e = new BigInteger(in.next());

        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        // e^-1 mod(phi(n))
        BigInteger d = ModInv.modInv(e, phi);
        System.out.println("\ne^-1 mod(phi(n))\nprivate key d = " + d);

        System.out.print("\nEnter 'e'for encryption, 'd' for decryption, or 's' for signature: "); // example encrypt m = 2
        String path = in.next();
        if(path.equals("e")){
            System.out.print("\nEnter the message to be encrypted: ");
            BigInteger m = new BigInteger(in.next());
            System.out.println("\nc = m^e (mod n)\nencrypted message  = " + ModExpo.modExp(m, e, p.multiply(q)));
        } else if(path.equals("d")){
            System.out.print("\nEnter the message to be decrypted: ");
            BigInteger c = new BigInteger(in.next());
            System.out.println("\nm = c^d (mod n) \ndecryted message  = " + ModExpo.modExp(c, d, p.multiply(q)));
        } else if(path.equals("s")){
            System.out.print("\nEnter the hash message to be signed: ");
            BigInteger s = new BigInteger(in.next());
            System.out.println("\ns = h(m)^d (mod n) \nsignature  = " + ModExpo.modExp(s, d, p.multiply(q)));
        } else {
            System.out.println("\nError: Type 'e', 'd' or 's'.");
        }
        System.out.println();
        in.close();
    }
}
