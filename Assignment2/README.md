# Digital Signature Using RSA

The aim of this assignment is to implement a digital signature using RSA. Before the digital signature can be implemented, you will need to set up an appropriate public/private RSA key pair. This should be done as follows:

1. Generate two distinct 512-bit probable primes p and q
2. Calculate the product of these two primes n = pq
3. Calculate the Euler totient function phi(n)
4. You will be using an encryption exponent e = 65537, so you will need to ensure that this is relatively prime to phi(n). If it is not, go back to step 1 and generate new values for p and q
5. Compute the value for the decryption exponent d, which is the multiplicative inverse of e (mod phi(n)). This should use your own implementation of the extended Euclidean GCD algorithm to calculate the inverse rather than using a library method for this purpose.

You should then write code to implement a decryption method which calculates h(m)d (mod n) for message digest h(m). You should use your own implementation of the Chinese Remainder Theorem to calculate this more efficiently; this can also make use of your multiplicative inverse implementation.
