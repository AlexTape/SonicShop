package de.alextape.sonicshop.methods;

import javax.servlet.http.HttpServletRequest;

/*
 * some evergreen helpers needed more then once in the project
 */
/**
 * The Class Methods.
 */
public class Methods {

    /*
     * this method defines the lenght of the article name of articles came from
     * the taglibs
     */
    /**
     * Fit length.
     *
     * @param literal
     *            the literal
     * @return the string
     */
    synchronized public static String fitLength(String literal) {
        if (literal.length() > 22) {
            literal = literal.substring(0, 22) + "..";
        }
        return literal;
    }

    /**
     * Gets the after comma.
     *
     * @param digit
     *            the digit
     * @return the after comma
     */
    synchronized public static int getAfterComma(double digit) {
        return (int) (digit * 100) % 100;
    }

    /**
     * Gets the before comma.
     *
     * @param d
     *            the d
     * @return the before comma
     */
    synchronized public static int getBeforeComma(double d) {
        return (int) Math.abs(d);
    }

    /*
     * get the full url of the requested target
     */
    /**
     * Gets the full url.
     *
     * @param request
     *            the request
     * @return the full url
     */
    synchronized public static String getFullUrl(HttpServletRequest request) {
        String uri = null;
        if (request.getQueryString() != null) {
            uri = request.getRequestURL().append("?")
                    .append(request.getQueryString()).toString();
        } else {
            uri = request.getRequestURL().toString();
        }
        return uri;
    }

    /**
     * Checks if is integer.
     *
     * @param str
     *            the str
     * @return true, if is integer
     */
    synchronized public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c <= '/' || c >= ':') {
                return false;
            }
        }
        return true;
    }

}
