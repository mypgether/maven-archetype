package ${package}.web.config.databind;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletRequest;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

/**
 * mvc框架的databind，如果不使用mvc框架可以去除
 * @author myp
 * @date 2019/5/29 10:14 AM
 */
public class FiledKeyRequestDataBinder extends ExtendedServletRequestDataBinder {

  public FiledKeyRequestDataBinder(Object target, String objectName) {
    super(target, objectName);
  }

  @Override
  protected void addBindValues(MutablePropertyValues mpvs, ServletRequest request) {
    super.addBindValues(mpvs, request);

    //处理JsonProperty注释的对象
    Class<?> targetClass = getTarget().getClass();
    Field[] fields = targetClass.getDeclaredFields();
    for (Field field : fields) {
      JsonProperty jsonPropertyAnnotation = field.getAnnotation(JsonProperty.class);
      if (jsonPropertyAnnotation != null && mpvs.contains(jsonPropertyAnnotation.value())) {
        if (!mpvs.contains(field.getName())) {
          mpvs.add(field.getName(),
              mpvs.getPropertyValue(jsonPropertyAnnotation.value()).getValue());
        }
      }
    }

    List<PropertyValue> covertValues = new ArrayList<PropertyValue>();
    for (PropertyValue propertyValue : mpvs.getPropertyValueList()) {
      if (propertyValue.getName().contains("_")) {
        String camelName = FiledKeyRequestParameterUtil
            .convertSnakeToCamel(propertyValue.getName());
        if (!mpvs.contains(camelName)) {
          covertValues.add(new PropertyValue(camelName, propertyValue.getValue()));
        }
      }
    }
    mpvs.getPropertyValueList().addAll(covertValues);
  }
}