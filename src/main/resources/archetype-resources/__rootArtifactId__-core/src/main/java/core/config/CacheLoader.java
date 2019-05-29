package ${package}.core.config;

import com.netflix.config.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据热更新加载，通过netflix框架进行数据加载
 *
 * @author myp
 * @date 2019/5/29 10:14 AM
 */
@Service("cacheLoader")
@Slf4j
public class CacheLoader implements InitializingBean, ApplicationContextAware {

  private ApplicationContext applicationContext;

  static {
    System.setProperty(ConfigurationManager.DISABLE_DEFAULT_SYS_CONFIG, "true");
    System.setProperty(ConfigurationManager.DISABLE_DEFAULT_ENV_CONFIG, "true");
    System.setProperty(DynamicPropertyFactory.DISABLE_DEFAULT_CONFIG, "true");
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    ConfigurationSource source = new ConfigurationSource();
    FixedDelayPollingScheduler scheduler = new FixedDelayPollingScheduler(0, 30 * 1000, false);
    DynamicConfiguration configuration = new DynamicConfiguration(source, scheduler);
    PropertyChangeHandler changeHandler = new PropertyChangeHandler();
    configuration.addConfigurationListener(new ConfigurationListener() {
      //执行两次，一个before, 一个after，这里只在after后相关的操作
      @Override
      public void configurationChanged(ConfigurationEvent event) {
        if (!event.isBeforeUpdate()) {
          changeHandler.handle(event);
        }
      }
    });
    ConfigurationManager.install(configuration);
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }


  private class ConfigurationSource implements PolledConfigurationSource {

    public ConfigurationSource() {
    }

    @Override
    public PollResult poll(boolean initial, Object checkPoint) throws Exception {
      Map<String, Object> map = new ConcurrentHashMap<String, Object>();
      Map<String, AJvmCacheLoader> beanMap = applicationContext
          .getBeansOfType(AJvmCacheLoader.class);
      for (Map.Entry<String, AJvmCacheLoader> entry : beanMap.entrySet()) {
        if (entry.getValue().init()) {
          Map<String, Object> all = entry.getValue().getAllData();
          all.forEach((key, value) -> map.put(entry.getValue().getCacheKey(key), value));
        }
      }
      return PollResult.createFull(map);
    }
  }

  private class PropertyChangeHandler {

    private String ACTION_ADDSET = "addSet";
    private String ACTION_CLEAR = "clear";

    public void handle(ConfigurationEvent event) {
      Object valueObj = event.getPropertyValue();
      String key = event.getPropertyName();
      if (AbstractConfiguration.EVENT_ADD_PROPERTY == event.getType()
          || AbstractConfiguration.EVENT_SET_PROPERTY == event.getType()) {
        doAction(key, valueObj, ACTION_ADDSET);
      } else if (AbstractConfiguration.EVENT_CLEAR_PROPERTY == event.getType()) {
        doAction(key, valueObj, ACTION_CLEAR);
      } else {
      }
    }

    private void doAction(String key, Object valueObj, String action) {
      try {
        Map<String, AJvmCacheLoader> beanMap = applicationContext
            .getBeansOfType(AJvmCacheLoader.class);
        for (Map.Entry<String, AJvmCacheLoader> entry : beanMap.entrySet()) {
          AJvmCacheLoader cacheLoader = entry.getValue();
          if (cacheLoader.init() && StringUtils.startsWith(key, cacheLoader.getKeyPrefix())) {
            if (ACTION_ADDSET.equals(action)) {
              cacheLoader.setPara(valueObj);
            } else if (ACTION_CLEAR.equals(action)) {
              cacheLoader.remove(cacheLoader.getRawKey(key));
            }
          }
        }
      } catch (Exception e) {
        log.error("{} doAction error,", this.getClass().getSimpleName(), e);
      }
    }
  }
}