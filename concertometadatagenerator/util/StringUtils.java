package concertometadatagenerator.util;

import java.util.*;
import java.lang.*;

public class StringUtils {

  public static String getStringAfter(String str, String delim) {
    int index = str.indexOf(delim);
    String prefix = str.substring(index + 1, str.length());
    return prefix;
  }

  public static String getStringBefore(String str, String delim) {
    int index = str.indexOf(delim);
    String prefix = str.substring(0, index);
    return prefix;
  }

}