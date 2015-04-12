package edu.carleton.boswells;

/**
 * Created by stonemanm on 4/11/15.
 */
public class EncodingHelper {
    /**
     * Prints the help info.
     */
    public void useInformation() {
        String info = "java EncodingHelper [-i type] [-o type] <data>\n";
        info += "    [--help/-h] displays usage options\n";
        info += "   [--input/-i] types: string, utf8, codepoint\n";
        info += "                default: string\n";
        info += "  [--output/-o] types: summary, string, utf8, codepoint\n";
        info += "                default: summary";
        System.out.println(info);
    }

    public EncodingHelper() {

    }

    /**
     * FOR STRING INPUT
     * Takes a given string and creates an EncodingHelperChar from each of the
     * chars in the string.
     *
     * @return An array of EncodingHelperChar objects.
     */
    public EncodingHelperChar[] readFromString(String s) {
        EncodingHelperChar[] codepoints = new EncodingHelperChar[s.length()];
        for (int i = 0; i < s.length(); i++) {
            codepoints[i] = EncodingHelperChar(s.charAt(i));
        }
        return codepoints;
    }

    /**
     * FOR BYTE INPUT
     * Takes a string of bytes, sorts and splits them appropriately, and creates
     * an EncodingHelperChar from each of the byte arrays.
     *
     * @return An array of EncodingHelperChar objects.
     */
    public EncodingHelperChar[] readFromUTF8(String stringOfBytes) {
        // This one will be tougher. We have to make a byte array from the
        // formatted string first, and that'll be a pain. Then we have to group
        // the bytes. We can group them by iterating through, looking at the
        // first couple bits, and then removing a corresponding number of bytes,
        // but error checking will be a bitch. I'm not excited for it.
        //
        // Anyway, at the end of that, we have an array of bytearrays...
        // for (bytearray in bytearrayarray)
        //    array[i] = the char-based constructor
        // return array

        return null;
    }

    /**
     * FOR CODEPOINT INPUT
     * Takes a string of bytes, sorts and splits them appropriately, and creates
     * an EncodingHelperChar from each of the byte arrays.
     *
     * @return An array of EncodingHelperChar objects.
     */
    public EncodingHelperChar[] readFromCodepoints(String[] codepoints) {
        // This might take a little thinking about.
        // There's two ways to input the codepoints – as separate args, or as a
        // string. We could have two separate constructors, but it seems easier
        // to write a method that parses them into the same form.
        //
        // The optimal form is an array of strings, where each string is of the
        // form U+####. Then
        //
        // for (string in String[])
        //     string = string.substring(2) <= deletes the first 2 chars (U+)
        //     turn it into an int somehow
        //     call the constructor
        //     add it to the output
        //
        // How do we get it to this form? If the input type is codepoint, we
        // take the relevant args and make an array of them – or, if there's
        // only one, we split the string? I'm not entirely sure. I'd love input.
        // Either way, I think we need to do it in main.

        return null;
    }

    /**
     * FOR STRING OUTPUT
     * Takes an array of EncodingHelperChar objects and constructs a
     * corresponding string, then returns it.
     *
     * @return A string corresponding to the EncodingHelperChar[].
     */
    public String writeToString(EncodingHelperChar[] codepoints) {
        String output = "";
        // Do we have a way to get a char from a codepoint?
        // Regardless, we iterate through codepoints and construct a string:
        //
        //for thing in thing
        //    make it a char
        //    output = output + that char

        return output;
    }

    /**
     * FOR BYTE OUTPUT
     * Takes an array of EncodingHelperChar objects and constructs a
     * corresponding string of UTF-8 byte representations, then returns it.
     *
     * @return A string of bytes corresponding to the EncodingHelperChar[].
     */
    public String writeToUTF8(EncodingHelperChar[] codepoints) {
        String output = "";
        for (EncodingHelperChar codepoint : codepoints) {
            output += codepoint.toUtf8String();
        }
        return output;
    }

    /**
     * FOR CODEPOINT OUTPUT
     * Takes an array of EncodingHelperChar objects and constructs a
     * corresponding string of codepoints, then returns it.
     *
     * @return A string of codepoints corresponding to the EncodingHelperChar[].
     */
    public String writeToCodepoints(EncodingHelperChar[] codepoints) {
        String output = "";
        for (EncodingHelperChar codepoint : codepoints) {
            output += " " + codepoint.toUtf8String();
        }
        return output.substring(1); //cuts the first space
    }

    /**
     * Our main function. It needs to determine the type of input based on the
     * tags and call functions as a result.
     * The default input is a string. The default output is a summary.
     *
     * For convenience, methods have been named readFromX() or writeToX(), where
     * X is "String", "UTF8", or "Codepoints".
     * All writeToX() methods take an array of EncodingHelperChar objects.
     * readFromString() and readFromUTF8() take a string as an argument.
     * readFromCodepoints() takes a String[], and is unique. See its comments.
     *
     * I didn't write a method for Summary. It can be compiled from the others.
     */
    public static void main(String[] args) {
        EncodingHelper EH = new EncodingHelper();
        String inputType = "string";
        String outputType = "summary";
        int startingPoint = 0;
        if (args.length == 0 || args[0].compareTo("-h") == 0 || args[0].compareTo("--help") == 0) {
            EH.useInformation();
            System.exit(1);
            //Run the help function and quit.
        }
        int i = 0;
        while (i < args.length) {
            if (args[i].compareTo("-i") = 0 || args[i].compareTo("--input") = 0) {
                i++;
                inputType = args[i];
                i++;
                startingPoint += 2;
            } else if (args[i].compareTo("-o") = 0 || args[i].compareTo("--output") = 0) {
                i++;
                outputType = args[i];
                i++;
                startingPoint += 2;
            }
        }
        

        // I think the best way to read the args is to iterate through them and
        // search for an "-i", "--input", "-o", or "--output".
        // If any are found, we can save the next value and work with it – for
        // instance, if args[2] = "-i", then we run inputType = args[3].
        // If the tags aren't found, or if it fails to parse the input well, we
        // run the default case.
        //
        // To be honest, this shouldn't be terrible. Construct an object for
        // each char/byte/etc, using the appropriate constructor, and then give
        // the appropriate output.
        //
        // Oh, and if the args are empty, we give the usage case. We should also
        // display it on a -h or --help, which means it should be a separate
        // method.


        // I think that's it for the methods we need! If you see any more, add
        // them. If not, would you mind coming up with the tests for them? I
        // figure after we have tests, we can meet tomorrow or work individually
        // and actually write the methods out.

        // One more thing to note. I'm bad at knowing what should be static and
        // what needs a constructor. If you see something that looks wrong on
        // that front, don't hesitate to change it.
        //
        // Best of luck!
        // -Michael
    }
}
