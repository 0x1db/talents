package com.wangyu.talents.common.base;

import com.wangyu.talents.common.model.ResponseCode;
import com.wangyu.talents.common.model.ResponseModel;
import com.wangyu.talents.entity.SystemUserEntity;
import com.wangyu.talents.service.SystemUserService;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Stack;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 控制器基类疯转
 *
 * @author wangyu
 * @Date 2019/3/9 17:30
 * @Version 1.0
 **/
public class BaseController {

  /**
   * 日志
   */
  public final Logger LOG = LoggerFactory.getLogger(BaseController.class);

  @Autowired
  private SystemUserService systemUserService;

  /**
   * 当异常状况时，使用该方法构造返回值
   *
   * @param e 错误的异常对象描述
   * @return 组装好的异常结果
   */
  protected ResponseModel buildHttpResultForException(Exception e) {
    String errorMsg = "";
    if (e != null) {
      errorMsg = e.getMessage();
    }

    ResponseModel result = new ResponseModel(new Date().getTime(), null, ResponseCode._501,
        errorMsg);
    return result;
  }

  /**
   * 构造http返回信息
   *
   * @see #filterProperties(BaseEntity, String...)
   */
  protected <T extends BaseEntity> ResponseModel buildHttpResult(Iterable<T> page,
      String... properties) {
    ResponseModel result = new ResponseModel(new Date().getTime(), null, ResponseCode._200, null);

    if (page == null || properties == null) {
      result.setData(page);
      return result;
    }

    page.forEach(item -> {
      filterProperties(item, properties);
    });

    result.setData(page);
    return result;
  }

  /**
   * 构造http返回信息
   *
   * @see #filterProperties(BaseEntity, String...)
   */
  protected <T extends BaseEntity> ResponseModel buildHttpResult(Collection<T> entities,
      String... properties) {
    ResponseModel result = new ResponseModel(new Date().getTime(), null, ResponseCode._200, null);

    if (entities == null || entities.isEmpty()
        || properties == null) {
      result.setData(entities);
      return result;
    }

    for (T entity : entities) {
      filterProperties(entity, properties);
    }

    result.setData(entities);
    return result;
  }

  /**
   * 构造http返回信息
   *
   * @param entity 返回的业务处理结果
   * @param properties 要去除的属性
   */
  protected <T extends BaseEntity> ResponseModel buildHttpResult(T entity, String... properties) {
    ResponseModel result = new ResponseModel(new Date().getTime(), null, ResponseCode._200, null);
    if (entity == null || properties == null) {
      return result;
    }

    // 过滤
    this.filterProperties(entity, properties);

    result.setData(entity);
    return result;
  }

  /**
   * 该方法不返回任何信息，只是告诉调用者，调用业务成功了。
   */
  protected ResponseModel buildHttpResult() {
    return new ResponseModel(new Date().getTime(), null, ResponseCode._200, null);
  }

  /**
   * TODO filterProperties方法只是进行了简单测试，还没有进行深入测试
   *
   * @see #filterProperties(BaseEntity, String...)
   */
  protected <T extends BaseEntity> Collection<T> filterProperties(Collection<T> entities,
      String... properties) {
    if (entities == null || entities.isEmpty()
        || properties == null) {
      return entities;
    }

    for (T entity : entities) {
      filterProperties(entity, properties);
    }

    return entities;
  }

  /**
   * 该工具用来去除实体对象中的属性关联。支持集合内部的对象属性去除。<br> 注意，进行属性引用去除的对象必须是UuidEntity的子类示例对象
   *
   * @param entity 目标对象
   * @param properties 要去除的属性<br>
   * <pre>
   * <example>
   *   // 以下代码可以去除currentRole中直接引用的5个属相
   *   this.filterProperty
   *     (currentRole, new String[]{"agents","competences","creator","merchants","modifier"});
   *     <br/>
   *   // 以下代码可以去除currentRole中直接引用的3个对象，以及merchants属性集合中的modifier属性和creator属性
   *   this.filterProperty
   *     (currentRole, new String[]{"agents","competences","creator","merchants.modifier","merchants.creator"});
   * </example>
   * </pre>
   */
  protected <T extends BaseEntity> void filterProperties(T entity, String... properties) {
    if (entity == null || properties == null) {
      return;
    }

    /*
     * 首先要对初始输入的属性列表进行初步处理：
     * 1、排序
     * 2、确定这个属性是在第几层对象中
     * */
    // 1、排序
    Arrays.sort(properties);

    // 2、递归排除指定的属性
    Stack<Class<?>> stackClasses = new Stack<>();
    try {
      for (String property : properties) {
        stackClasses.push(entity.getClass());
        filterProperty(property, entity, stackClasses);
        stackClasses.pop();
      }
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
    }
  }

  /**
   * 过滤相关属性
   */
  private void filterProperty(String property, Object currentObject, Stack<Class<?>> stackClasses)
      throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    // 如果条件成立，说明还要进入下一级对象，否则就是操作本级对象
    int nodeIndex;
    if ((nodeIndex = property.indexOf(".")) != -1) {
      String currentFieldName = property.substring(0, nodeIndex);
      String nextFieldName = property.substring(nodeIndex + 1);
      Field currentField = null;
      Class<?> fieldClass = null;
      Class<?> currentClass = null;
      try {
        currentField = this.findField(currentFieldName, currentObject.getClass());
        if (currentField == null) {
          return;
        }
        fieldClass = currentField.getType();
        currentClass = currentObject.getClass();
      } catch (NoSuchFieldException | SecurityException e) {
        throw new IllegalArgumentException(
            "not found property: " + currentFieldName + " in object " + currentObject.getClass()
                .getName());
      }

      // 取得下一级对象
      char[] chars = currentFieldName.toCharArray();
      chars[0] -= 32;
      Method getMethod = currentClass.getMethod("get" + String.valueOf(chars));
      Object nextObject = getMethod.invoke(currentObject);

      /*
       * 那么是不是进入内部呢？还要以以下判断条件为准:
       * 1、这个属性必须是UuidEntity的子类
       * 2、这个属性本来不为null
       * 3、这个属性所对应的类没有在已进入的递归列表中
       * */
      // 如果条件成立，说明是单一对象
      if (nextObject != null && nextObject instanceof BaseEntity
          && !stackClasses.contains(fieldClass)) {
        stackClasses.push(fieldClass);
        filterProperty(nextFieldName, nextObject, stackClasses);
        stackClasses.pop();
      }
      // 如果条件成立，说明这个属性是一个集合
      else if (nextObject != null && nextObject instanceof Collection) {
        Collection<?> collections = (Collection<?>) nextObject;
        for (Object propertyObject : collections) {
          Class<?> propertyClass = propertyObject.getClass();
          if (!(propertyObject instanceof BaseEntity)) {
            break;
          }
          stackClasses.push(propertyClass);
          filterProperty(nextFieldName, propertyObject, stackClasses);
          stackClasses.pop();
        }
      }
    }
    // 就在本级对象进行属性排除
    else {
      String currentFieldName = property;
      Field currentField = null;
      Class<?> fieldClass = null;
      Class<?> currentClass = null;
      try {
        currentField = this.findField(currentFieldName, currentObject.getClass());
        if (currentField == null) {
          return;
        }
        fieldClass = currentField.getType();
        currentClass = currentObject.getClass();
      } catch (NoSuchFieldException | SecurityException e) {
        throw new IllegalArgumentException(
            "not found property: " + currentFieldName + " in object " + currentObject.getClass()
                .getName());
      }

      // 如果执行到这里，就可以将属性设置为null了
      char[] chars = currentFieldName.toCharArray();
      chars[0] -= 32;
      Method getMethod = currentClass.getMethod("set" + String.valueOf(chars), fieldClass);
      getMethod.invoke(currentObject, new Object[]{null});
    }
  }

  /**
   * 该私有方法查询指定类中的指定字段名
   */
  private Field findField(String currentFieldName, Class<?> targetClass)
      throws NoSuchFieldException {
    Field currentField = null;
    try {
      currentField = targetClass.getDeclaredField(currentFieldName);
    } catch (NoSuchFieldException | SecurityException e) {

    }

    if (currentField == null) {
      Class<?> superClass = targetClass.getSuperclass();
      if (superClass != null) {
        return this.findField(currentFieldName, superClass);
      } else {
        throw new NoSuchFieldException(
            "not found property " + currentFieldName + " in class " + targetClass.getSimpleName());
      }
    }

    return currentField;
  }

  protected <T> ResponseModel buildHttpResult(T entity) {
    ResponseModel result = new ResponseModel(new Date().getTime(), null, ResponseCode._200, null);
    if (entity == null) {
      return result;
    }
    result.setData(entity);
    return result;
  }

  protected ResponseModel buildHttpResultForValidate(ResponseCode code, String errorMsg) {
    return new ResponseModel(new Date().getTime(), null, code, errorMsg);
  }

  /**
   * 验证操作者是否登陆，是否是代理商
   *
   * @return 登陆的代理商
   */
  protected SystemUserEntity verifyLogin(Principal operator) {
    String account = getPrincipalAccount(operator);

    if (StringUtils.isNotEmpty(account)) {
      // 验证是否是代理商用户
      SystemUserEntity opUser = this.systemUserService.findByUsernameAndStatus(account, "1");
      return opUser;
    }

    return null;
  }

  /**
   * 获取登陆者账号
   */
  protected String getPrincipalAccount(Principal operator) {
    // 通过operator确定当前的登录者信息
    if (operator != null) {
      String account = operator.getName();
      return account;
    }

    return null;
  }
}
