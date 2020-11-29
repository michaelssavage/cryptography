# Cryptography

### GCD

The Euclidean Algorithm is a technique for quickly finding the Greatest Common Divisor of two integers.

For example, gcd(24, 36) = 12

---

### Miller-Rabin primality test 

This was created by Gary Miller and Michael O. Rabin and also makes use of Fermat’s Little Theorem. To test an odd number **n** for primality, we let **2^k** be the largest power of **2** dividing **n-1**. Thus we have  **n-1 = 2^k(m)** for some odd number **m**. 

![Miller-Rabin primality test](img/mrabin.jpg)

---

### Modular exponentiation 

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
### Chinese Remainder Theorem

The Chinese Remainder Theorem (CRT) allows us to replace a modular computation using a large composite modulus with smaller more efficient computations using the factors of the modulus instead. 

![Chinese Remainder Theorem](img/crt.jpg)
