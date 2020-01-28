package org.example.dao;

import org.example.utils.DbUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author yhChen
 * @Description
 * @Date 2020/1/28
 */
public class BaseDao<T> {
    private Class<?> clazz;
    public BaseDao() {
        clazz = (Class) ((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * sql查询将查询结果直接放入ResultSet中
     * @param sql sql语句
     * @param params 参数数组，若没有参数则为null
     * @return 结果集
     */
    private ResultSet excuteQuery(String sql,Object[] params) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            //获得链接
            Connection conn = DbUtil.getConn();
            pst = conn.prepareStatement(sql);
            //参数赋值
            if (params != null) {
                for (int i = 0; i<params.length;i++) {
                    pst.setObject(i + 1,params[i]);
                }
            }
            rs = pst.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    public List<T> resultSetToList(ResultSet rs) {
        List<T> list = new ArrayList<T>();
        try {
            while (rs.next()) {
                Object object = null;
                try {
                    object = clazz.getDeclaredConstructor().newInstance();
                    for (Method m : clazz.getMethods()) {
                        String methodName = m.getName();
                        //set方法
                        if (methodName.startsWith("set")) {
                            //获取字段名
                            String fileName = methodName.substring(3);
                            //获取数据并通过反射赋值
                            m.invoke(object,rs.getObject(fileName));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                list.add((T) object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<T> selectAll(String sql,Object[] params) {
        ResultSet resultSet = excuteQuery(sql, params);
        return resultSetToList(resultSet);
    }
}
