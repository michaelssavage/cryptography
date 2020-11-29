import java.io.*;
import java.math.BigInteger;
import java.nio.file.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.util.Scanner;

public class Assignment1 {

    // You have private key a, and public key A.
    // I have private key b, and public key B.
    // We share generator g, and prime P.
    // The shared secret key is s.
    private static final BigInteger PRIME = new BigInteger("b59dd79568817b4b9f6789822d22594f376e6a9abc0241846de"
    +"426e5dd8f6eddef00b465f38f509b2b18351064704fe75f012fa346c5e2c442d7c99eac79b2bc8a202c98327b96816cb8042698ed"
    +"3734643c4c05164e739cb72fba24f6156b6f47a7300ef778c378ea301e1141a6b25d48f1924268c62ee8dd3134745cdf7323",16);

    private static final BigInteger GENERATOR = new BigInteger("44ec9d52c8f9189e49cd7c70253c2eb3154dd4f08467a64"
    +"a0267c9defe4119f2e373388cfa350a4e66e432d638ccdc58eb703e31d4c84e50398f9f91677e88641a2d2f6157e2f4ec538088dc"
    +"f5940b053c622e53bab0b4e84b1465f5738f549664bd7430961d3e5a2e7bceb62418db747386a58ff267a9939833beefb7a6fd68",16);

    private static final BigInteger PUBLIC_A = new BigInteger("5af3e806e0fa466dc75de60186760516792b70fdcd72a5b6"
    +"238e6f6b76ece1f1b38ba4e210f61a2b84ef1b5dc4151e799485b2171fcf318f86d42616b8fd8111d59552e4b5f228ee838d535b4"
    +"b987f1eaf3e5de3ea0c403a6c38002b49eade15171cb861b367732460e3a9842b532761c16218c4fea51be8ea0248385f6bac0d",16);

    private static SecureRandom randNum = new SecureRandom();
    private static final String PUBLIC_FILE = "DH.txt";         //output file for the hex value of my public key.
    private static final String IV_FILE = "IV.txt";             //output file for the hex value of IV.
    private static final int BIT_LENGTH = 1023;                 //1023 bits
    private static final int IV_SIZE = 16;                      //16 bytes = 128 bits
    private static final String SHA256 = "SHA-256";
    private static final String CHARSET_TYPE = "UTF-8";
    private static final String BLOCKCIPHER = "AES/CBC/NoPadding";
    private static final String AES = "AES";

    public static void main(String[] args) throws Exception {

        Scanner in = new Scanner(System.in);
        byte[] inputFile = null;
        
        //get bytes from Assignment1.class
        if(args.length >0){
            inputFile = getFileInfo(args[0]);
        }
        else{
            System.err.print("Please enter the input file: ");
            inputFile = getFileInfo(in.next());
            in.close();
        }
        // my secret value b = 1023 bits
        BigInteger privateb = BigInteger.probablePrime(BIT_LENGTH, randNum);

        // my public shared value B = g^b (mod p)
        BigInteger publicB = modularExponent(GENERATOR, privateb, PRIME);
        String publicHex = publicB.toString(16);
        writeKeyToFile(publicHex,PUBLIC_FILE);

        //Intialisation Vector with 128 bits
        byte[] iv = generateIV(randNum, IV_SIZE, IV_FILE);

        // shared secret value s = A^b (mod p)
        BigInteger secretKey = modularExponent(PUBLIC_A, privateb, PRIME);

        // generate 256-bit AES key k
        byte[] aesKey = getSHA256(secretKey);

        //Encrypt file using the aesKey and the IV
        encryptFile(inputFile, aesKey, iv);
    }

      // get class name from args, return bytes
      public static byte[] getFileInfo(String args) throws IOException {
        Path fileLocation = Paths.get(args);
        return Files.readAllBytes(fileLocation);
    }

    // y = a ^ x (mod n)
    private static BigInteger modularExponent(BigInteger base, BigInteger power, BigInteger modulo) {
        //init y = 1
        BigInteger result = BigInteger.ONE;
        for(int i = 0; i < power.bitLength(); i++){
            // check if exponent bit is odd
            if (power.testBit(i)){
                //y = (y * a) % n
                result = (result.multiply(base)).mod(modulo);
            }
            //a = (a * a) % n
            base = (base.multiply(base)).mod(modulo);
            }
        return result;
    }

    //Generate IV and write it to the IV.txt file, then return it
    private static byte[] generateIV(SecureRandom randNum, int ivSize, String ivFile) throws IOException {
        //IV Size = 16
        byte[] ivBytes = new byte[ivSize];
        randNum.nextBytes(ivBytes);
        String ivHex = byteToHex(ivBytes);
        writeKeyToFile(ivHex,ivFile);
        return ivBytes;
    }

    //get a Hex String from a byte array
    private static String byteToHex(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for(byte b: bytes){
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

    //Store a generated key to the appropriate file.
    private static void writeKeyToFile(String hexString, String file) throws IOException{
        PrintWriter out = new PrintWriter(file);
        out.write(hexString);                       //simplest way to write strings to files
        out.close();
    }

    //Get the SHA256 Digest of a BigInteger using UTF-8
    public static byte[] getSHA256(BigInteger key) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String secretKey = key.toString();
        MessageDigest md = MessageDigest.getInstance(SHA256);
        byte[] bytes = md.digest(secretKey.getBytes(CHARSET_TYPE));
        return bytes;
    }

    //Encrypted file needs to be padded.
    public static void encryptFile(byte[] file, byte[] key, byte[] iv)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        
        SecretKeySpec secretKey = new SecretKeySpec(key, AES);
        IvParameterSpec ivKey = new IvParameterSpec(iv);

        Cipher cipher = Cipher.getInstance(BLOCKCIPHER);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivKey);

        int fileLength = file.length;
        int padding = IV_SIZE - (fileLength % IV_SIZE);    //IV Size = 16
        byte[] paddedFile = new byte[fileLength + padding];

        //create copy of byte file with extra bytes.
        System.arraycopy(file, 0, paddedFile, 0, fileLength);

        //First new byte is 1 and remaining bytes will be 0.
        paddedFile[fileLength] = (byte) 1;
        for (int i = fileLength + 1; i < paddedFile.length; i++) {
			paddedFile[i] = (byte) 0;
		}
        byte[] encryptedFile = cipher.doFinal(paddedFile);

        String encryption = byteToHex(encryptedFile);
        System.out.print(encryption);
    }
}
