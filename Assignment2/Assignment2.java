import java.math.BigInteger;
import java.security.*;
import javax.crypto.*;
import java.nio.file.*;
import java.io.*;
import java.util.Scanner;

public class Assignment2{

    private static SecureRandom randNum = new SecureRandom();
    private static final String MODULUS_FILE = "Modulus.txt";
    private static final BigInteger EXPONENT = BigInteger.valueOf(65537);
    private static final int BIT_LENGTH = 512;
    public static void main(String[] args) throws Exception {
        
        // get input file and make sure it is not a negative number.
        byte[] inputFile = getEncryptedFile(args);
        BigInteger hashMessage = byteToInt(inputFile);
        
        BigInteger[] primes = getPrimeValues(); // p = primes[0], q = primes[1]
        BigInteger n = primes[0].multiply(primes[1]);
        writeToFile(n.toString(16), MODULUS_FILE);
        
        BigInteger phi = primes[2];
        BigInteger d = modInv(EXPONENT, phi);  // e^-1 mod phi

        BigInteger s = signMessage(hashMessage, d, primes[0], primes[1], n);   // c^d mod n
        System.out.println(s.toString(16));

        // BigInteger hashMessage = new BigInteger("35716170779396691039740081549532864396647397333964275440879179063560675964966");
        // BigInteger p = new BigInteger("10079147682482877959036648892208304726846078394868564971308497738804039340185534330289911625819913466054851961563538181763706253315623939498513441288459529");
        // BigInteger q = new BigInteger("10283463764479044062838778305010596847880632351283934473952932053604713332016514567624254351742602329395402602890177098664863407016870407517915893836315961");
        // BigInteger n = new BigInteger("103648549969645608897956309743026503251343150576717426279022022005081479247295925785097210336977436948102267189666787646539010496098215767994030104619379393888557692309352289422013127219902440414467735991980096638111566242358868749189680161938710366698873481563671961842065522978252050979017278969506205242369");
        // BigInteger phi = new BigInteger("103648549969645608897956309743026503251343150576717426279022022005081479247295925785097210336977436948102267189666787646539010496098215767994030104619379373525946245347430267546585930001000865687756989839480651376681773833606196547140782247772732804183078031309107508126785094408591718484670262540171080466880");
        // BigInteger d = new BigInteger("94784089854293004447446748485879188746196769769196465992559131831004519801775171645825198131066904972361644219465491512159176908496853188388516597190116188018325684363980511689610265297770479002680194623796548488751271333684583845205629822444045690530543549024481303340929311352300515315367779644437999825153");
        // BigInteger m = new BigInteger("35578592762993278508760525544995033914540999717118353459168867674481272712315284389069134104667447926338160840433486634137064055457624346587336126687606071759812083900569549975864646648550609946647081420585097798554823332740556843524579486555622039008656155584621242404002255163448885780956223496176566625581");

        // writeToFile(n.toString(16), MODULUS_FILE);
        // // modulusfile --> "9399b61469132d09f829efd78e93d82db9e69c12398256d4d10b835bbb6391b575efafa3ef3dddc360a6aa561930dc7db995c3a24ad477c64952b5fea8322ccc7d840a899e2a27975eb0454ac49623124a5142de5dc0c51c189c82a8d546e2b76cb58d50dab7a8f10aa0385c44ccd2dc6d7cfa0e8ef6b99139f8bca47e0b9401";
        // System.out.println(m.toString(16));
        // //signed h(m) --> "32aa649f50a61d4c80ce4982b783bdbdea76e4be98d3e308d851abc88d54be646f8365669af3b5a4ba6bfb549b66a60ad871730dea1ac48106f8f08ad8a17ed5cf50b2c10c29cad82a03bb5957f8edaedab68b1e95f0971e115bd16e334922bab1d6c4bfc6e1a0e1ff81dbc6b78da8729cb20b8715566d77d9d531ae62d9652d";
    }

    // get bytes and digitally sign it.
    private static byte[] getEncryptedFile(String[] args) throws IOException, NoSuchAlgorithmException{
        Scanner in = new Scanner(System.in);
        String file = null;

        //get bytes from Assignment2.class
        if(args.length >0)
            file = args[0];
        else{
            System.err.print("Please enter the input file: ");
            file = in.next();
        }
        in.close();
        Path fileLocation = Paths.get(file);
        byte[] inputFile = Files.readAllBytes(fileLocation);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(inputFile);
        return md.digest();
    }

    // get rid of any possible negative number by reversing
    private static BigInteger byteToInt(byte[] arr) {
        byte[] rev = new byte[arr.length + 1];
        for (int i = 0, j = arr.length; j > 0; i++, j--)
            rev[j] = arr[i];
        return new BigInteger(rev);
    }
    
    // create primes, p and q, and make sure that they're not equal and the gcd = 1
    private static BigInteger[] getPrimeValues(){
        BigInteger p = BigInteger.probablePrime(BIT_LENGTH, randNum); //bit length = 512
        BigInteger q = BigInteger.probablePrime(BIT_LENGTH, randNum);

        // phi(n) = (p-1)(q-1)
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        
        //p == q, repeat or if gcd != 1, repeat
        if(p.equals(q) || !gcd(phi, EXPONENT).equals(BigInteger.ONE))          
            getPrimeValues();
            
        return new BigInteger[] {p, q, phi};
    }

    // get the greatest common divisor
    private static BigInteger gcd(BigInteger a, BigInteger b){
        
        // While the number is positive.                            Example: gcd(24,36)
        while(b.compareTo(BigInteger.ZERO) > 0){  // -------------------------------------------------
            BigInteger tmp = a.mod(b);            // | loop 1:       | loop 2:       | loop 3:       |
            a = b;                                // | tmp = 24 % 36 | tmp = 36 % 24 | tmp = 24 % 12 |
            b = tmp;                              // | a = 36        | a = 24        | phi = 12      |
        }                                         // | b = 24        | b = 12        | e = 0         |
        return a;                                 // -------------------------------------------------
    }

    //Write the modulus to the file.
    private static void writeToFile(String modulus, String file) throws IOException{
        PrintWriter out = new PrintWriter(file);
        out.write(modulus);
        out.close();
    }

    // Extended Euclidean Algorithm where e(x) + n(y) = 1
    private static BigInteger modInv(BigInteger e, BigInteger n) {
		
		// Set up all initial values.                               Example: 5^-1 mod 11
		BigInteger n0 = n;                                          // n0 = 11
		BigInteger t0 = BigInteger.ZERO;                            // t0 = 0
		BigInteger t = BigInteger.ONE;                              // t = 1
		BigInteger q = n.divide(e);                                 // q = 11 // 5 = 2
		BigInteger r = n0.subtract(q.multiply(e));                  // r = 11 - (2 * 5) = 1
		
		while (r.compareTo(BigInteger.ZERO) > 0) {                  // while r > 0
			
			// Simulate the subtraction step in EEA
			BigInteger temp = t0.subtract(q.multiply(t));           // temp = 0 - (2*1) = -2
			
			// Adjust temp if it's negative.
			if (temp.compareTo(BigInteger.ZERO) > 0)
			    temp = temp.mod(n);
			if (temp.compareTo(BigInteger.ZERO) < 0)                // temp < 0
			    temp = n.subtract(temp.negate().mod(n));            // temp = 11 - (2 % 11) = 9
			
			// Update each variable as necessary.
			t0 = t;                                                 // t0 = 1  
			t = temp;                                               // t = 9
			n0 = e;                                                 // n0 = 5
			e = r;                                                  // e = 1
			q = n0.divide(e);                                       // q = 5 / 1 = 5
			r = n0.subtract(q.multiply(e));                         // r = 5 - (5 * 1) = 0
		}
		if (!e.equals(BigInteger.ONE))
			return BigInteger.ONE;
		else                                                        //e == 1
		 	return t.mod(n);                                        //answer = 9 % 11 = 9
    }

    // Get the signed message using Chinese Remainder Theorem
    private static BigInteger signMessage(BigInteger c, BigInteger d, BigInteger p, BigInteger q, BigInteger n){

        // a = c^d mod n where a = c^d mod p and a = c^d mod q
        //a = SIGMA(i=1;i<=k;i+=1) ai * ni * yi (mod n)             (27^37) % 55 where p = 11 and q = 5                                           
        BigInteger a1 = modExp(c.mod(p), d, p);                     // a1 = (27 % 11)^37 % 11 = 3
        BigInteger n1 = n.divide(p);                                // n1 = 55 / 11 = 5
        BigInteger y1 = modInv(n1,p);                               // y1 = (5^-1) % 11 = 9

        BigInteger a2 = modExp(c.mod(q), d, q);                     // a2 = (27 % 5)^37 % 5 = 2
        BigInteger n2 = n.divide(q);                                // n2 = 55 / 5 = 11
        BigInteger y2 = modInv(n2,q);                               // y2 = (11^-1) % 5 = 1
        
        BigInteger m1 = a1.multiply(n1).multiply(y1);               // m1 = 3 * 5 * 9 = 135
        BigInteger m2 = a2.multiply(n2).multiply(y2);               // m2 = 2 * 11 * 1 = 22

        return (m1.add(m2)).mod(n);                                 // answer = (135 + 22) % 55 = 47
    }

    // y = (a^x) % n, modular exponentiation function
    private static BigInteger modExp(BigInteger a, BigInteger x, BigInteger n) {

        // the square and multiply algorithm, right to left variant.

        // init result (y) = 1                                         Example:(123^5) % 511
        BigInteger y = BigInteger.ONE;                              // --------------------
        for(int i = 0; i < x.bitLength(); i++){                     // |      |  a  |  y  |
            // check if exponent bit is odd                            --------------------
            if (x.testBit(i)){                                      // | init | 123 |  1  |
                //result = (result * base) % modulus                   | x0=1 | 310 | 123 |
                y = (y.multiply(a)).mod(n);                         // | x1=0 | 32  | 123 |
            }                                                       // | x2=1 |  2  | 359 |
            //base = (base^2) % modulus                                --------------------
            a = (a.multiply(a)).mod(n);                             //     answer = 359
            }
        return y;
    }
}