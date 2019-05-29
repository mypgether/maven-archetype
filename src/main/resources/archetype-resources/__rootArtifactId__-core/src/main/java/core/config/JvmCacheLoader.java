package ${package}.core.config;

import java.util.Map;

/**
 * 数据热更新加载接口
 *
 * @author myp
 * @date 2019/5/29 10:14 AM
 */
public interface JvmCacheLoader<T> {

  /**
   * 是否需要初始化loader
   */
  public default boolean init() {
    return true;
  }


  /**
   * 获取给定cache的key，主要用于匹配loader，替换key
   */
  public String getKeyPrefix();

  /**
   * 设置参数，新增或者修改
   */
  public void setPara(T param);

  /**
   * 删除参数
   */
  public void remove(String uniqueKey);

  /**
   * 获取全量的数据
   */
  public Map<String, T> getAllData();
}