package trust.nccgroup.decoderimproved;

/**
 * Created by k on 25/5/19.
 */

import java.util.ArrayList;
import java.util.Arrays;

public class URLRecursiveDecoder extends ByteModifier {
    public URLRecursiveDecoder() {
        super("URL Recursive");
    }

    // Recursively URL Decode the bytes
    public byte[] modifyBytes(byte[] input) {
        int encoded = 1;
        ArrayList<Byte> output = new ArrayList<>();
        
        while(encoded == 1){
            encoded = 0;
            output = new ArrayList<>();
            for (int i = 0; i < input.length; i++ ) {
                // If the loop is within the last two characters it can't be a url encoded character
                if (i >= input.length-2) {
                    output.add(input[i]);
                } else {
                    // url encoded chars start with a %
                    if (input[i] == '%') {
                        // If the next two chars aren't valid hex chars, it isn't url encoded
                        if (Utils.isHexDigit((char) input[i + 1]) && Utils.isHexDigit((char) input[i + 2])) {
                            // Check if the next two chars after the % are digits
                            // Take in the next two bytes
                            encoded = 1;
                            output.add((byte) (Integer.parseInt(new String(Arrays.copyOfRange(input, i + 1, i + 3)), 16) & 0xFF));
                            // Need to skip over the next two characters that were just decoded
                            i += 2;
                        }
                    } else {
                        // Add the value of the char
                        output.add(input[i]);
                    }
                }
            }
            input = Utils.convertByteArrayListToByteArray(output);
        }

        return input;
    }
}

