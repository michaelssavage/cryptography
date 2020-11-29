# Symmetric Encryption Using Diffie-Hellman Key Agreement

The aim of this assignment is to perform symmetric encryption using the block cipher AES. Before this encryption can be done, a key must be exchanged with the receiver of the message; this will be done using Diffie-Hellman key agreement. 

The prime modulus **p** is given as a 1024-bit prime in hexadecimal.

The generator **g** is given in hexadecimal.

The public shared value A for the Diffie-Hellman key change is given by g^a (mod p) where a is the secret value.

## In order to perform the Diffie-Hellman key exchange, you should do the following:

1. Generate a random 1023-bit integer; this will be your secret value b.
2. Generate your public shared value B given by g^b (mod p)
3. Calculate the shared secret s given by A^b (mod p)

You can use the shared secret s for your AES encryption. However, it is too large (1024 bits) to be used directly as the AES key. You should therefore use SHA-256 to produce a 256-bit digest from the shared secret s, giving a 256-bit AES key k.
You will then encrypt an input binary file using AES in CBC mode with the 256-bit key k and a block size of 128-bits. 
The IV for this encryption will be a randomly generated 128-bit value. 
You will use the following padding scheme (as given in lectures): 
- if the final part of the message is less than the block size, append a 1-bit and fill the rest of the block with 0-bits; 
- if the final part of the message is equal to the block size, then create an extra block starting with a 1-bit and fill the rest of the block with 0-bits. 
