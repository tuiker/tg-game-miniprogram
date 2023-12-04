package com.hou_tai.common.util;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;

import javax.xml.datatype.XMLGregorianCalendar;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: BeanUtil
 * @Description: 实体转换
 * @Author: Sam
 * @Date: 2023-11-04 16:33
 * @Version: 1.0
 **/
@Slf4j
public class BeanUtil extends org.springframework.beans.BeanUtils {

        /**
         * 对象赋值
         *
         * @param source 源对象
         * @param target 目标对象
         * @throws BeansException
         */
        public static void copyProperties(Object source, Object target) throws BeansException {
            Assert.notNull(source, "Source must not be null");
            Assert.notNull(target, "Target must not be null");
            Class<?> actualEditable = target.getClass();
            PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
            for (PropertyDescriptor targetPd : targetPds) {
                if (targetPd.getWriteMethod() != null) {
                    PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                    if (sourcePd != null && sourcePd.getReadMethod() != null) {
                        try {
                            Method readMethod = sourcePd.getReadMethod();
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            // 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
                            if (value != null) {
                                Method writeMethod = targetPd.getWriteMethod();
                                Type targetParameterType = writeMethod.getGenericParameterTypes()[0];
                                // 特殊类型不再执行copy XMLGregorianCalendar
                                if (!(targetParameterType.equals(XMLGregorianCalendar.class))) {
                                    if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                        writeMethod.setAccessible(true);
                                    }
                                    writeMethod.invoke(target, value);
                                }
                            }
                        } catch (Throwable ex) {
                            log.error(ex.getMessage());
                            throw new FatalBeanException("Could not copy properties from source to target", ex);
                        }
                    }
                }
            }
        }

        /**
         * 集合对象转化赋值
         * @param sources 源集合对象
         * @param voClass vo类型
         * @param <T>
         * @return
         */
        public static <T> List<T> copyListProperties(List<? extends Object> sources, final Class<T> voClass) {
            Assert.isTrue(!CollectionUtils.isEmpty(sources), "Source must not be null");
            List<T> targets = new ArrayList<>();
            sources.forEach(source -> {
                try {
                    T target = voClass.newInstance();
                    copyProperties(source, target);
                    targets.add(target);
                } catch (InstantiationException | IllegalAccessException e) {
                    log.error(e.getMessage());
                }
            });
            return targets;
        }

    }


