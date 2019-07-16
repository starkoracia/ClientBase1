package application.util.converters;

public class MobileStringConverter {

    public static final String MOBILE_PATTERN = "+38(___)__-__-___";
    private final char[] patternOnChar = MOBILE_PATTERN.toCharArray();
    StringBuffer patternBuffer;

    public String toString(String object) {
        patternBuffer = new StringBuffer(MOBILE_PATTERN);
        char[] strMass = object.toCharArray();
        StringBuffer outBuffer = new StringBuffer();

        for (int i = 0; i < patternBuffer.length(); i++) {
            if (patternBuffer.charAt(i) == '_') {
                if (strMass[i] != '_') {
                    outBuffer.append(strMass[i]);
                }
            }
        }

        return outBuffer.toString();
    }

    public String addMobileDigitsToPattern(String oldValue, String digits) {
        if (oldValue == null || oldValue.equals("")) {
            oldValue = MOBILE_PATTERN;
        }
        if (!digits.matches("[\\d]*")) {
            return oldValue;
        }
        patternBuffer = new StringBuffer(oldValue);

        for (int i = 0; i < digits.length() && i <= 9; i++) {
            int index = patternBuffer.indexOf("_");
            if(index == -1) {
                break;
            }
            patternBuffer.deleteCharAt(index).insert(index, digits.charAt(i));
        }
        return patternBuffer.toString();
    }

}
