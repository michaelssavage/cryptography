# Cryptography

### GCD

The Euclidean Algorithm is a technique for quickly finding the Greatest Common Divisor of two integers.

For example, gcd(24, 36) = 12

---

### Miller-Rabin primality test 

This was created by Gary Miller and Michael O. Rabin and also makes use of Fermat’s Little Theorem. To test an odd number **n** for primality, we let **2^k** be the largest power of **2** dividing **n-1**. Thus we have  **n-1 = 2^k(m)** for some odd number **m**. 

![Miller-Rabin primality test](img/mrabin.jpg)

---

### Modular Exponentiation 

This is widely used in public key cryptography. The modular exponentiation **a^x(mod n)** is just repeated multiplication (**a** multiplied by itself **x** times). 

The right to left variant of this algorithm for calculating a^x (mod n) where the exponent x is k bits long is as follows: 

For example, 123 ^ 5 mod 511 = 359

![Modular exponentiation](img/modexpo.jpg)

---

### Modular Inverse

Using the Euclidean algorithm, we can determine if a has an inverse modulo n which will be the case when gcd(a, n) = 1.

To do this, we can use an extended version of the Euclidean algorithm. 

For example, 67^-1 mod 119 = 93

![Modular Inverse](img/inverse.jpg)

---

### Modular Division

Dividing an A/B mod N is the same as calculating A * (B ^-1) mod N. 
So using the modular inverse function, we get: 

- A * (modInv(B, N)) .mod(N)

![Modular Division](img/modDivision.jpg)

### Modular Square Roots

To find the square roots of a number A modulo N, N must be prime or have prime factors. For example, 59 is prime. 77 is not prime but has prime factors 7 and 11.
![Modular Square roots](img/squareroots.jpg)

### ElGamal Encryption

The ElGamal public key encryption technique was first published in 1985 by Taher ElGamal: "A Public Key Cryptosystem and a Signature Scheme Based on Discrete Logarithms". 
The public key is represented by the values (p,g,y) where prime p > 512 bits, and y = g^x (mod p) with 0 < x < p-1 and g of the multiplicative group Z*p.

Need to create to cipherkeys with the message m and a key, 0 < k < p-1.

- c1 = g^k (mod p)
- c2 = my^k (mod p)

To decrypt a message, you solve c1^ (p-1-x) mod p which is c1^-x.
This is used to solve c1^-x * c2 mod p

![ElGamal](img/elgamal.jpg)


### Chinese Remainder Theorem

The Chinese Remainder Theorem (CRT) allows us to replace a modular computation using a large composite modulus with smaller more efficient computations using the factors of the modulus instead. 

![Chinese Remainder Theorem](img/crt.jpg)
