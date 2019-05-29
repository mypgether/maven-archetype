package ${package}.web.config.databind;

import org.apache.commons.lang3.StringUtils;

/**
 * mvc框架的databind，如果不使用mvc框架可以去除
 *
 * @author myp
 * @date 2019/5/29 10:14 AM
 */
public class FiledKeyRequestParameterUtil {

  public static String convertSnakeToCamel(String snake) {
    if (snake == null) {
      return null;
    }

    if (snake.indexOf("_") < 0) {
      return snake;
    }
    String result = "";

    String[] split = StringUtils.split(snake, "_");
    int index = 0;
    for (String s : split) {
      if (index == 0) {
        result += s.toLowerCase();
      } else {
        result += capitalize(s);
      }
      index++;
    }

    return result;
  }

  private static String capitalize(String s) {
    if (s == null) {
      return null;
    }
    if (s.length() == 1) {
      return s.toUpperCase();
    }
    return s.substring(0, 1).toUpperCase() + s.substring(1);
  }
}