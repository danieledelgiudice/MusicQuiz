/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mq.rsa;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Daniele
 */
public class RSA {
    public static BigInteger[] generateKeys()
    {
        BigInteger p = BigInteger.probablePrime( 1024, new SecureRandom() ); //Nuovo numero primo a 512 bit
        BigInteger q = BigInteger.probablePrime( 1024, new SecureRandom() ); //Nuovo numero primo a 512 bit

        BigInteger n = p.multiply( q ); // n = p*q

        BigInteger one = BigInteger.ONE;

        BigInteger pMin1 = p.subtract( one ); // p-1
        BigInteger qMin1 = q.subtract( one ); // q-1

        BigInteger fi = pMin1.multiply( qMin1 );

        BigInteger e = p.nextProbablePrime();
        BigInteger d = e.modInverse( fi ); //Esegue (1/e) % ((p-1)-(q-1))

        return new BigInteger[] {d, e, n};
    }
}
