package io.github.lmikoto.railgun.utils;

import io.github.lmikoto.railgun.model.Field;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.MessageDigest;
import java.util.*;

public class BeanUtil {

    /**
     * Bean --> Map
     * @param obj
     * @return
     */
    public static Map<String, Object> transBean2Map(Object obj) {
        if(obj == null){
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                // 过滤class属性
                if (!"class".equals(key)) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }
        return map;
    }


    /**
     * 计算默认的 serialVersionUID
     *
     * @see java.io.ObjectStreamClass#lookup(Class)
     * @see java.io.ObjectStreamClass#computeDefaultSUID(Class)
     */
    public static long computeDefaultSUID(String className, List<Field> fields) {
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            DataOutputStream dout = new DataOutputStream(bout);

            // simple class name
            dout.writeUTF(className);
            int classMods = Modifier.PUBLIC & (Modifier.PUBLIC | Modifier.FINAL | Modifier.INTERFACE | Modifier.ABSTRACT);
            dout.writeInt(classMods);

            // interface name
            dout.writeUTF("java.io.Serializable");

            // fields
            // fields.sort(Comparator.comparing(Field::getField));
            for (Field field : fields) {
                int mods = Modifier.PRIVATE &
                        (Modifier.PUBLIC | Modifier.PRIVATE | Modifier.PROTECTED |
                                Modifier.STATIC | Modifier.FINAL | Modifier.VOLATILE |
                                Modifier.TRANSIENT);
                if (((mods & Modifier.PRIVATE) == 0) ||
                        ((mods & (Modifier.STATIC | Modifier.TRANSIENT)) == 0)) {
                    dout.writeUTF(field.getName());
                    dout.writeInt(mods);
                    dout.writeUTF(field.getFieldType());
                }
            }

            // method ignore
            dout.flush();

            MessageDigest md = MessageDigest.getInstance("SHA");
            byte[] hashBytes = md.digest(bout.toByteArray());
            long hash = 0;
            for (int i = Math.min(hashBytes.length, 8) - 1; i >= 0; i--) {
                hash = (hash << 8) | (hashBytes[i] & 0xFF);
            }
            return hash;
        } catch (Exception e) {
            // ignore
        }
        return 1;
    }

    public static <T> T nullOr(T str,T def){
        return str == null ? def : str;
    }
}
