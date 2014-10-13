package de.alextape.sonicshop.methods;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The Class MD5.
 */
public class MD5 {
    /*
     * cryptographic hash function that produces a 128-bit (16-byte) hash value
     * for all user passwords
     */
    /**
     * Md5.
     *
     * @param input
     *            the input
     * @return the string
     */
    public String md5(String input) {

        // is there any pasword that is worth the salt?? ;-)
        String salt = "$1s#th3r3@nyp455w0rd%th4t&1s^w0r1h-th3~s4lt*??";
        String md5 = null;
        if (null == input) {
            return null;
        } else {
            // salt it
            input = salt + input;
        }

        try {
            // create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");
            // update input string in message digest
            digest.update(input.getBytes(), 0, input.length());
            // converts message digest value in base 16 (hex)
            md5 = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }
        return md5;
    }
}
