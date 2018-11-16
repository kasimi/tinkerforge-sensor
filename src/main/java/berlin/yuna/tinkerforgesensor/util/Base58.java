package berlin.yuna.tinkerforgesensor.util;

import java.math.BigInteger;

/**
 * A custom form of base58 is used to encode Bitcoin addresses. Note that this is not the same base58 as used by
 * Flickr, which you may see reference to around the internet.<p>
 * <p>
 * Satoshi says: why base-58 instead of standard base-64 encoding?<p>
 *
 * <ul>
 * <li>Don't want 0OIl characters that look the same in some fonts and
 * could be used to create visually identical looking account numbers.</li>
 * <li>A string with non-alphanumeric characters is not as easily accepted as an account number.</li>
 * <li>E-mail usually won't line-break if there's no punctuation to break at.</li>
 * <li>Double-clicking selects the whole number as one word if it's all alphanumeric.</li>
 * </ul>
 *
 * @author The bitcoinj developers, modified by Jonathan Coe
 */
public class Base58 {
    private static final String ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
    private static final BigInteger BASE = BigInteger.valueOf(58);

    private Base58() {
        // The constructor of this class is private in order to prevent the class being instantiated
    }

    public static String encode(byte[] input) {
        // This could be a lot more efficient.
        BigInteger bi = new BigInteger(1, input);
        StringBuffer s = new StringBuffer();
        while (bi.compareTo(BASE) >= 0) {
            BigInteger mod = bi.mod(BASE);
            s.insert(0, ALPHABET.charAt(mod.intValue()));
            bi = bi.subtract(mod).divide(BASE);
        }
        s.insert(0, ALPHABET.charAt(bi.intValue()));
        // Convert leading zeros too.
        for (byte anInput : input) {
            if (anInput == 0)
                s.insert(0, ALPHABET.charAt(0));
            else
                break;
        }
        return s.toString();
    }

    public static byte[] decode(String input) {
        byte[] bytes = decodeToBigInteger(input).toByteArray();
        // We may have got one more byte than we wanted, if the high bit of the next-to-last byte was not zero. This
        // is because BigIntegers are represented with twos-compliment notation, thus if the high bit of the last
        // byte happens to be 1 another 8 zero bits will be added to ensure the number parses as positive. Detect
        // that case here and chop it off.
        boolean stripSignByte = bytes.length > 1 && bytes[0] == 0 && bytes[1] < 0;
        // Count the leading zeros, if any.
        int leadingZeros = 0;
        for (int i = 0; input.charAt(i) == ALPHABET.charAt(0); i++) {
            leadingZeros++;
        }
        // Now cut/pad correctly. Java 6 has a convenience for this, but Android can't use it.
        byte[] tmp = new byte[bytes.length - (stripSignByte ? 1 : 0) + leadingZeros];
        System.arraycopy(bytes, stripSignByte ? 1 : 0, tmp, leadingZeros, tmp.length - leadingZeros);
        return tmp;
    }

    private static BigInteger decodeToBigInteger(String input) {
        BigInteger bi = BigInteger.valueOf(0);

        // Work backwards through the string.
        for (int i = input.length() - 1; i >= 0; i--) {
            int alphaIndex = ALPHABET.indexOf(input.charAt(i));
            if (alphaIndex == -1) {
                throw new IllegalArgumentException("In Base58.decodeToBigInteger(), Illegal character " + input.charAt(i) + " at index " + i + ". Throwing new IlleglArgumentException.");
            }
            bi = bi.add(BigInteger.valueOf(alphaIndex).multiply(BASE.pow(input.length() - 1 - i)));
        }

        return bi;
    }
}