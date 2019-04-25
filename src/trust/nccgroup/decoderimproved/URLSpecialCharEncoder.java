package trust.nccgroup.decoderimproved;

import java.io.UnsupportedEncodingException;

/**
 * Created by j on 12/6/16.
 */
public class URLSpecialCharEncoder extends ByteModifier {
    public URLSpecialCharEncoder() {
        super("URL Special Characters");
    }

    // URL Encode the bytes
    public byte[] modifyBytes(byte[] input) {
        try {
            String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String numbers = "0123456789";
            String specialChars = "$-_.+!*'(),,";
            byte[] whitelist = (letters + numbers + specialChars).getBytes("UTF-8");

            String output = "";
            for (byte b : input) {
                if (!Utils.contains(whitelist, b)) {
                    output += "%";
                    output += String.format("%02X", (0xFF & (int) b));
                } else {
                    output += (char)b;
                }
            }
            return output.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new byte[0];
        }
    }
}

public class URLSpecialCharEncoder extends URLSpecialCharEncoder {
    public URLSpecialCharEncoder() {
        super("URL Double Encode");
    }

    // URL Encode the bytes
    public byte[] modifyBytes(byte[] input) {
        try {
            String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String numbers = "0123456789";
            String specialChars = "$-_.+!*'(),,";
            byte[] whitelist = (letters + numbers + specialChars).getBytes("UTF-8");

            String output = "";
            for (byte b : input) {
                if (!Utils.contains(whitelist, b)) {
                    output += "%";
                    output += String.format("%02X", (0xFF & (int) b));
                } else {
                    output += (char)b;
                }
            }
            // Double Encode
            String output2 = "";
            for (byte b : output) {
                if (!Utils.contains(whitelist, b)) {
                    output2 += "%";
                    output2 += String.format("%02X", (0xFF & (int) b));
                } else {
                    output2 += (char)b;
                }
            }

            return output2.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return new byte[0];
        }
    }
}