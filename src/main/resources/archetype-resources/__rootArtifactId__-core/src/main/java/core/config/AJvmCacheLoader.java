package ${package}.core.config;

import com.netflix.config.DynamicBooleanProperty;
import com.netflix.config.DynamicContextualProperty;
import com.netflix.config.DynamicFloatProperty;
import com.netflix.config.DynamicIntProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import org.apache.commons.lang.StringUtils;

/**
 * 数据热更新加载抽象类
 *
 * @author myp
 * @date 2019/5/29 10:14 AM
 */
public abstract class AJvmCacheLoader<T> implements JvmCacheLoader<T> {

  public final static String DEFAULT_VALUE_STRING = "";
  public final static int DEFAULT_VALUE_INT = -100;
  public final static boolean DEFAULT_VALUE_BOOLEAN = false;
  public final static float DEFAULT_VALUE_FLOAT = -100f;
  public final static long DEFAULT_VALUE_LONG = -100L;

  public int getValueInt(String name) {
    DynamicIntProperty prop = DynamicPropertyFactory.getInstance()
        .getIntProperty(name, DEFAULT_VALUE_INT);
    return prop.get();
  }

  public String getValueString(String name) {
    DynamicStringProperty prop = DynamicPropertyFactory.getInstance()
        .getStringProperty(name, DEFAULT_VALUE_STRING);
    return prop.get();
  }

  public boolean getValueBoolean(String name) {
    DynamicBooleanProperty prop = DynamicPropertyFactory.getInstance()
        .getBooleanProperty(name, DEFAULT_VALUE_BOOLEAN);
    return prop.get();
  }

  public boolean getValueBoolean(String name, boolean defaultValue) {
    DynamicBooleanProperty prop = DynamicPropertyFactory.getInstance()
        .getBooleanProperty(name, defaultValue);
    return prop.get();
  }

  public float getValueFloat(String name) {
    DynamicFloatProperty prop = DynamicPropertyFactory.getInstance()
        .getFloatProperty(name, DEFAULT_VALUE_FLOAT);
    return prop.get();
  }

  /**
   * 获取数据值
   */
  public T getValue(String uniqueKey) {
    DynamicContextualProperty<T> prop = DynamicPropertyFactory.getInstance()
        .getContextualProperty(getCacheKey(uniqueKey), null);
    return prop.getValue();
  }

  /**
   * 获取缓存中的key
   */
  protected String getCacheKey(String uniqueKey) {
    return String.format("%s%s", getKeyPrefix(), uniqueKey);
  }

  /**
   * 获取原始的key
   */
  protected String getRawKey(String cacheKey) {
    return StringUtils.replaceOnce(cacheKey, getKeyPrefix(), "");
  }

  @Override
  public void setPara(T param) {
  }

  @Override
  public void remove(String uniqueKey) {
  }
}