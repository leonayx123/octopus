package com.sdyc.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Created with IntelliJ IDEA.
 * User: lyp
 * Date: 2014/8/27
 * Time: 17:07
 * </pre>
 *
 * @author lyp
 */
public class DButil {
    static private Log log = LogFactory.getLog(DButil.class);


    public static String getUUId(){
        return  java.util.UUID.randomUUID().toString();
    }

    /**
     * 替换DTO对应的数据库名
     * @param s
     * @return
     */
    public static String replaceDataBaseName(String s) {
        s = replaceOneLatterLower(s);
        String[] reps = {"DTO", "Dto","dto"};
        for (String rep : reps) {
            s = s.replace(rep, "");
        }
        s = replaceCapital(s);
        return s;
    }

    public static void main(String[] args) {
        String x = "BvcMessageDTO";
        System.out.println(replaceDataBaseName(x));
    }
    //替换大写形式的字段命名
    public static String replaceCapital(String s){
        char[] chars = s.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            char ichar = chars[i];
            if (ichar >= 'A' && ichar <= 'Z') {
                sb.append("_").append(String.valueOf(ichar).toLowerCase());
            }else{
                sb.append(ichar);
            }
        }
        return sb.toString();
    }

    //判断是否有忽略掉的字段
    public static boolean replaceIgnores(String s,String [] slist) {
        for (String s1 : slist) {
            if (s.equals(s1)) {
                return true;
            }
            if (replaceCapital(s).equals(s1)) {
                return true;
            }
        }
        return false;
    }
    //把字符串的第一个字符转换成大写
    public static String replaceOneLatterUpper(String s){
        char[] chars = s.toCharArray();
        StringBuffer sb = new StringBuffer();
        chars[0] = Character.toUpperCase(chars[0]);
        return String.valueOf(chars);
    }
    //把字符串的第一个字符转换成小写
    public static String replaceOneLatterLower(String s){
        char[] chars = s.toCharArray();
        StringBuffer sb = new StringBuffer();
        chars[0] = Character.toLowerCase(chars[0]);
        return String.valueOf(chars);
    }

    /**
     * 查询列表
     * @param clazz
     * @param jdbcTemplate
     * @param whereSql 查询条件（where field=value）
     * @param fields 查询字段
     * @param args 参数列表
     * @param <T>
     * @return
     */
    public static <T> List<T> list(Class<T> clazz,JdbcTemplate jdbcTemplate,String whereSql,List<String> fields,List args){
        StringBuffer selectSql = new StringBuffer("select ").append(StringUtils.join(fields, ",")).append(" FROM ");
        selectSql.append(replaceDataBaseName(replaceDataBaseName(clazz.getSimpleName())));
        selectSql.append(" ").append(whereSql);
        log.debug(selectSql);
        return jdbcTemplate.query(selectSql.toString(), args.toArray(), new BeanPropertyRowMapper<T>(clazz));
    }




    /**
     *查询列表s
     * @param clazz
     * @param jdbcTemplate
     * @param whereSql 查询条件（where field=value）
     * @param args 参数列表
     * @param <T>
     * @return
     */
    public static <T> List<T> list(Class<T> clazz,JdbcTemplate jdbcTemplate,String whereSql,List args){
        StringBuffer selectSql = new StringBuffer("select * from ");
        selectSql.append(replaceDataBaseName(replaceDataBaseName(clazz.getSimpleName())));
        selectSql.append(" ").append(whereSql);
        log.debug(selectSql);
        return jdbcTemplate.query(selectSql.toString(), args.toArray(), new BeanPropertyRowMapper<T>(clazz));
    }
    /**
     * 查询
     * @param clazz
     * @param jdbcTemplate
     * @param whereSql
     * @return
     */
    public static <T> List<T> listNoParam(Class<T> clazz, JdbcTemplate jdbcTemplate,String whereSql) {
        StringBuffer selectSql = new StringBuffer("select * from ");
        selectSql.append(replaceDataBaseName(replaceDataBaseName(clazz.getSimpleName())));
        selectSql.append(" ").append(whereSql);
        log.debug(selectSql);
        return jdbcTemplate.query(selectSql.toString(),new BeanPropertyRowMapper<T>(clazz));
    }

    /**
     * 查询
     * @param clazz
     * @param jdbcTemplate
     * @param whereSql
     * @return
     */
    public static <T> List<T> list(String tableName,Class<T> clazz, JdbcTemplate jdbcTemplate,String whereSql,Object[] args) {
        StringBuffer selectSql = new StringBuffer("select * from ");
        selectSql.append(tableName);
        selectSql.append(" ").append(whereSql);
        return jdbcTemplate.query(selectSql.toString(),args,new BeanPropertyRowMapper<T>(clazz));
    }
    /**
     * 查询一个
     * @param clazz
     * @param jdbcTemplate
     * @param whereSql
     * @return
     */
    private static <T>T getObj(Class<T> clazz, JdbcTemplate jdbcTemplate,String whereSql) {
        StringBuffer selectSql = new StringBuffer("select * from ");
        selectSql.append(replaceDataBaseName(replaceDataBaseName(clazz.getSimpleName())));
        selectSql.append(" ").append(whereSql);
        List<T> query = jdbcTemplate.query(selectSql.toString(), new BeanPropertyRowMapper<T>(clazz));
        T t = null;
        if (query != null && query.size() > 0) {
            t = query.get(0);
        }
        return t;
    }

    /**
     * 查询一个
     * @param clazz
     * @param jdbcTemplate
     * @param whereSql
     * @return
     */
    public static <T>T getObj(Class<T> clazz, JdbcTemplate jdbcTemplate,String whereSql,Object[]args) {
        StringBuffer selectSql = new StringBuffer("select * from ");
        selectSql.append(replaceDataBaseName(replaceDataBaseName(clazz.getSimpleName())));
        selectSql.append(" ").append(whereSql);
        List<T> query = jdbcTemplate.query(selectSql.toString(),args, new BeanPropertyRowMapper<T>(clazz));
        T t = null;
        if (query != null && query.size() > 0) {
            t = query.get(0);
        }
        return t;
    }
    /**
     * 查询
     * @param clazz
     * @param jdbcTemplate
     * @param whereSql
     * @return
     */
    private static <T> List<T> list(Class<T> clazz, JdbcTemplate jdbcTemplate,String whereSql) {
        StringBuffer selectSql = new StringBuffer("select * from ");
        selectSql.append(replaceDataBaseName(replaceDataBaseName(clazz.getSimpleName())));
        selectSql.append(" ").append(whereSql);
        long start = System.currentTimeMillis();
        List<T> query = jdbcTemplate.query(selectSql.toString(), new BeanPropertyRowMapper<T>(clazz));
        log.debug(selectSql + "--------->" + (System.currentTimeMillis() - start) + "ms");
        return query;
    }

    /**
     * 查询
     * @param clazz
     * @param jdbcTemplate
     * @param whereSql
     * @return
     */
    public static <T> List<T> list(Class<T> clazz, JdbcTemplate jdbcTemplate,String whereSql,Object[] args) {
        StringBuffer selectSql = new StringBuffer("select * from ");
        selectSql.append(replaceDataBaseName(replaceDataBaseName(clazz.getSimpleName())));

        if(StringUtils.isNotBlank(whereSql)){
            selectSql.append(" ").append(whereSql);
        }
        long start = System.currentTimeMillis();
        List<T> query = jdbcTemplate.query(selectSql.toString(),args, new BeanPropertyRowMapper<T>(clazz));
        log.debug(selectSql + "--------->" + (System.currentTimeMillis() - start) + "ms");
        return query;
    }

    /**
     * 查询
     * @param clazz
     * @param jdbcTemplate
     * @return
     */
    public static <T> List<T> list(Class<T> clazz, JdbcTemplate jdbcTemplate) {
        StringBuffer selectSql = new StringBuffer("select * from ");
        selectSql.append(replaceDataBaseName(replaceDataBaseName(clazz.getSimpleName())));
        //jdbcTemplate.query(selectSql.toString(), new BeanPropertyRowMapper<T>(clazz));
        return jdbcTemplate.query(selectSql.toString(),new BeanPropertyRowMapper<T>(clazz));
    }

    /**
     * 查询
     * @param clazz
     * @param jdbcTemplate
     * @return
     */
    public static <T> List<T> list(Class<T> clazz,String filterSql, JdbcTemplate jdbcTemplate) {
        StringBuffer selectSql = new StringBuffer("select * from ");
        selectSql.append(replaceDataBaseName(replaceDataBaseName(clazz.getSimpleName())));
        if(StringUtils.isNotBlank(  filterSql)){
            selectSql.append(" where ").append(filterSql);
        }
        return jdbcTemplate.query(selectSql.toString(),new BeanPropertyRowMapper<T>(clazz));
    }

    /**
     * 查询
     * @param clazz
     * @param jdbcTemplate
     * @return
     */
    public static <T> List<T> list(Class<T> clazz,String filterSql,Object[]args, JdbcTemplate jdbcTemplate) {
        StringBuffer selectSql = new StringBuffer("select * from ");
        selectSql.append(replaceDataBaseName(replaceDataBaseName(clazz.getSimpleName())));
        if(StringUtils.isNotBlank(  filterSql)){
            selectSql.append(" where ").append(filterSql);
        }
        return jdbcTemplate.query(selectSql.toString(),new BeanPropertyRowMapper<T>(clazz));
    }
    /**
     * 根据字段查询
     * @param clazz
     * @param jdbcTemplate
     * @return
     */
    public static <T> List<T> list(Class<T> clazz, JdbcTemplate jdbcTemplate,String ... arg) {
        StringBuffer selectSql = new StringBuffer();
        if (arg.length > 0) {
            selectSql.append("select ");
            selectSql.append(StringUtils.join(arg,","));
            selectSql.append(" from ");
        }
        selectSql.append(replaceDataBaseName(clazz.getSimpleName()));
        log.debug(selectSql);
        return jdbcTemplate.query(selectSql.toString(), new BeanPropertyRowMapper<T>(clazz));
    }
    /**
     * 根据字段查询
     * @param clazz
     * @param jdbcTemplate
     * @return
     */
    public static <T> List<T> list(Class<T> clazz, JdbcTemplate jdbcTemplate,String whereSql,String... arg) {
        StringBuffer selectSql = new StringBuffer();
        if (arg.length > 0) {
            selectSql.append("select ");
            selectSql.append(StringUtils.join(arg,","));
            selectSql.append(" from ");
        }
        selectSql.append(replaceDataBaseName(clazz.getSimpleName()));
        if (StringUtils.isNotBlank(whereSql)) {
            selectSql.append(" ").append(whereSql);
        }
        log.debug(selectSql);
        return jdbcTemplate.query(selectSql.toString(), new BeanPropertyRowMapper<T>(clazz));
    }
    /**
     *
     * @param clazz
     * @param jdbcTemplate
     * @param start
     * @param end
     * @param sidx 排序字段
     * @param sord 排序方式
     * @param <T>
     * @return
     */
    public static <T> List<T> listBylimit(Class<T> clazz, JdbcTemplate jdbcTemplate,Integer start,Integer end,String sidx,String sord) {
        StringBuffer selectSql = new StringBuffer("select * from ");
        selectSql.append(replaceDataBaseName(replaceDataBaseName(clazz.getSimpleName())));
        if (StringUtils.isNotBlank(sidx)) {
            selectSql.append(" order by ").append(replaceCapital(sidx));
        }
        if (StringUtils.isNotBlank(sidx)&&StringUtils.isNotBlank(sord)) {
            selectSql.append(" ").append(sord);
        }
        selectSql.append(" limit ").append(start).append(",").append(end);
        return jdbcTemplate.query(selectSql.toString(),new BeanPropertyRowMapper<T>(clazz));
    }

    /**
     * 分页模糊查询,返回总数
     * @param clazz
     * @param searchObj
     * @param jdbcTemplate
     * @param start
     * @param end
     * @param sidx 排序字段
     * @param sord 排序方式
     * @param <T>
     * @return
     */
    public static <T> List<T> listBylimit(Class<T> clazz,Object searchObj, JdbcTemplate jdbcTemplate,Integer start,Integer end,String sidx,String sord,String wheresql,StringBuffer count) {
        StringBuffer selectSql = new StringBuffer("select * from ");
        selectSql.append(replaceDataBaseName(replaceDataBaseName(clazz.getSimpleName())));
        Class<?> sclass = searchObj.getClass();
        Field[] sfields = sclass.getDeclaredFields();

        try {
            StringBuffer where = new StringBuffer("");
            where.append(" where 1=1 ");
            for (Field sfield : sfields) {
                String sname = sfield.getName();
                Method method = clazz.getMethod("get" + replaceOneLatterUpper(sname));
                Object invoke = method.invoke(searchObj);
                if (invoke == null || StringUtils.isBlank(invoke.toString())) {
                    continue;
                }
                sname = replaceCapital(sname);
                where.append(" and ").append(sname).append(" like \"%" + invoke + "%\" ");
            }
            where.append(wheresql);
            selectSql.append(where);
            count.append(getCount(clazz, jdbcTemplate, where.toString()+""));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (StringUtils.isNotBlank(sidx)) {
            selectSql.append(" order by ").append(replaceCapital(sidx));
        }
        if (StringUtils.isNotBlank(sidx)&&StringUtils.isNotBlank(sord)) {
            selectSql.append(" ").append(sord);
        }
        selectSql.append(" limit ").append(start).append(",").append(end);
        log.debug(selectSql);
        return jdbcTemplate.query(selectSql.toString(),new BeanPropertyRowMapper<T>(clazz));
    }




    /**
     * 分页模糊查询
     *
     * @param clazz
     * @param searchObj
     * @param jdbcTemplate
     * @param start
     * @param end
     * @param sidx         排序字段
     * @param sord         排序方式
     * @param <T>
     * @return
     */
    public static <T> List<T> listBylimit(Class<T> clazz, Object searchObj, JdbcTemplate jdbcTemplate, Integer start, Integer end, String sidx, String sord, String wheresql) {
        StringBuffer selectSql = new StringBuffer("select * from ");
        selectSql.append(replaceDataBaseName(replaceDataBaseName(clazz.getSimpleName())));
        Class<?> sclass = searchObj.getClass();
        Field[] sfields = sclass.getDeclaredFields();

        try {
            selectSql.append(" where 1=1 ");
            for (Field sfield : sfields) {
                String sname = sfield.getName();
                Method method = clazz.getMethod("get" + replaceOneLatterUpper(sname));
                Object invoke = method.invoke(searchObj);
                if (invoke == null || StringUtils.isBlank(invoke.toString())) {
                    continue;
                }
                sname = replaceCapital(sname);
                selectSql.append(" and ").append(sname).append(" like \"%" + invoke + "%\" ");
            }
            selectSql.append(wheresql);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (StringUtils.isNotBlank(sidx)) {
            selectSql.append(" order by ").append(replaceCapital(sidx));
        }
        if (StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(sord)) {
            selectSql.append(" ").append(sord);
        }
        selectSql.append(" limit ").append(start).append(",").append(end);
        log.debug(selectSql);
        return jdbcTemplate.query(selectSql.toString(), new BeanPropertyRowMapper<T>(clazz));
    }

    /**
     * 分页查询
     *
     * @param clazz
     * @param jdbcTemplate
     * @param start
     * @param end
     * @param sidx         排序字段
     * @param sord         排序方式
     * @param <T>
     * @return
     */
    public static <T> List<T> Pagelist(Class<T> clazz, JdbcTemplate jdbcTemplate, Integer start, Integer end, String sidx, String sord,String whereSql) {
        StringBuffer selectSql = new StringBuffer("select * from ");
        selectSql.append(replaceDataBaseName(replaceDataBaseName(clazz.getSimpleName())));
        selectSql.append(whereSql);
        if (StringUtils.isNotBlank(sidx)) {
            selectSql.append(" order by ").append(replaceCapital(sidx));
        }
        if (StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(sord)) {
            selectSql.append(" ").append(sord);
        }
        selectSql.append(" limit ").append(start).append(",").append(end);
        log.debug(selectSql);
        return jdbcTemplate.query(selectSql.toString(), new BeanPropertyRowMapper<T>(clazz));
    }

    /**
     * 分页查询 条件
     *
     * @param clazz
     * @param searchObj
     * @param jdbcTemplate
     * @param start
     * @param end
     * @param sidx         排序字段
     * @param sord         排序方式
     * @param <T>
     * @return
     */
    public static <T> List<T> pageListByCondition(Class<T> clazz, Object searchObj, JdbcTemplate jdbcTemplate, Integer start, Integer end, String sidx, String sord) {
        StringBuffer selectSql = new StringBuffer("select * from ");
        selectSql.append(replaceDataBaseName(replaceDataBaseName(clazz.getSimpleName())));
        Class<?> sclass = searchObj.getClass();
        Field[] sfields = sclass.getDeclaredFields();

        try {
            selectSql.append(" where 1=1 ");
            for (Field sfield : sfields) {
                String sname = sfield.getName();
                Method method = clazz.getMethod("get" + replaceOneLatterUpper(sname));
                Object invoke = method.invoke(searchObj);
               if (invoke == null || StringUtils.isBlank(invoke.toString())) {
                    continue;
                }
                /* sname = replaceCapital(sname);
                selectSql.append(" and ").append(sname).append(" = " + invoke);*/

               /* selectSql.append(" and ").append(sname).append(" like \"%" + invoke + "%\" ");*/
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        if (StringUtils.isNotBlank(sidx)) {
            selectSql.append(" order by ").append(replaceCapital(sidx));
        }
        if (StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(sord)) {
            selectSql.append(" ").append(sord);
        }
        selectSql.append(" limit ").append(start).append(",").append(end);
        log.debug(selectSql);
        return jdbcTemplate.query(selectSql.toString(), new BeanPropertyRowMapper<T>(clazz));
    }




    /**
     * 获得总数
      * @param clazz
     * @param jdbcTemplate
     * @param <T>
     * @return
     */
    public static <T>Integer getCount(Class<T> clazz, JdbcTemplate jdbcTemplate) {
        Integer count = 0;
        StringBuffer selectSql = new StringBuffer("select count(0) as c from ");
        selectSql.append(replaceDataBaseName(replaceDataBaseName(clazz.getSimpleName())));
        final List<Map<String, Object>> list = jdbcTemplate.queryForList(selectSql.toString());
        if (list != null && list.size() > 0) {
            count = Integer.parseInt(list.get(0).get("c") + "");
        }
        return count;
    }

    /**
     * 获得总数
     * @param clazz
     * @param jdbcTemplate
     * @param whereSql
     * @param <T>
     * @return
     */
    private static <T>Integer getCount(String tableName,Class<T> clazz, JdbcTemplate jdbcTemplate,String whereSql) {
        Integer count = 0;
        StringBuffer selectSql = new StringBuffer("select count(0) as c from ");
        selectSql.append(tableName);
        selectSql.append(" ").append(whereSql);
        final List<Map<String, Object>> list = jdbcTemplate.queryForList(selectSql.toString());
        if (list != null && list.size() > 0) {
            count = Integer.parseInt(list.get(0).get("c") + "");
        }
        return count;
    }



    /**
     * 获得总数
     * @param clazz
     * @param jdbcTemplate
     * @param whereSql
     * @param <T>
     * @return
     */
    public static <T>Integer getCount(String tableName,Class<T> clazz, JdbcTemplate jdbcTemplate,String whereSql,Object[] args) {
        Integer count = 0;
        StringBuffer selectSql = new StringBuffer("select count(0) as c from ");
        selectSql.append(tableName);
        selectSql.append(" ").append(whereSql);
        final List<Map<String, Object>> list = jdbcTemplate.queryForList(selectSql.toString(),args);
        if (list != null && list.size() > 0) {
            count = Integer.parseInt(list.get(0).get("c") + "");
        }
        return count;
    }

    /**
     * 获得总数
     * @param clazz
     * @param jdbcTemplate
     * @param whereSql
     * @param <T>
     * @return
     */
    private static <T>Integer getCount(Class<T> clazz, JdbcTemplate jdbcTemplate,String whereSql) {
        Integer count = 0;
        StringBuffer selectSql = new StringBuffer("select count(0) as c from ");
        selectSql.append(replaceDataBaseName(replaceDataBaseName(clazz.getSimpleName())));
        selectSql.append(" ").append(whereSql);
        final List<Map<String, Object>> list = jdbcTemplate.queryForList(selectSql.toString());
        if (list != null && list.size() > 0) {
            count = Integer.parseInt(list.get(0).get("c") + "");
        }
        return count;
    }

    /**
     * 获得总数
     * @param clazz
     * @param jdbcTemplate
     * @param whereSql
     * @param <T>
     * @return
     */
    public static <T>Integer getCount(Class<T> clazz, JdbcTemplate jdbcTemplate,String whereSql,Object[] args) {
        Integer count = 0;
        StringBuffer selectSql = new StringBuffer("select count(0) as c from ");
        selectSql.append(replaceDataBaseName(replaceDataBaseName(clazz.getSimpleName())));
        selectSql.append(" ").append(whereSql);
        final List<Map<String, Object>> list = jdbcTemplate.queryForList(selectSql.toString(),args);
        if (list != null && list.size() > 0) {
            count = Integer.parseInt(list.get(0).get("c") + "");
        }
        return count;
    }

    /**
     * 插入
     * @param o
     * @param jdbcTemplate
     * @return
     */
    public static boolean insert(Object o, JdbcTemplate jdbcTemplate,String whereSql){
        ArrayList<Object> objectArray = new ArrayList<Object>();
        try {
            Class<?> clazz = o.getClass();
            StringBuffer insertSql = new StringBuffer("INSERT INTO ");
            StringBuffer valuesSql = new StringBuffer("(");
            String name = clazz.getSimpleName();
            insertSql.append(replaceDataBaseName(name)).append(" ( ");
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                String name1 = declaredField.getName();
                //如果值为NULL则跳过
                Method method = clazz.getMethod("get"+ replaceOneLatterUpper(name1));
                Object invoke = method.invoke(o);
                if(invoke==null){
                    continue;
                }
                objectArray.add(invoke);
                name1 = replaceCapital(name1);
                valuesSql.append("?,");
                insertSql.append(name1).append(",");
            }
            insertSql = insertSql.delete(insertSql.length() - 1, insertSql.length());
            valuesSql = valuesSql.delete(valuesSql.length() - 1, valuesSql.length()).append(")");
            insertSql.append(")").append(" values ").append(valuesSql.toString());
            if (StringUtils.isNotBlank(whereSql)) {
                insertSql.append(" ").append(whereSql);
            }
            log.debug(insertSql.toString());
            if(objectArray.size()<=0) {
                return false;
            }
            jdbcTemplate.execute("set names 'utf8mb4'");
            jdbcTemplate.update(insertSql.toString(),objectArray.toArray());

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 插入数据并获得主键生成器
     * @param o
     * @param jdbcTemplate
     * @return
     */
    public static KeyHolder insertReturnPk(String tableName, Object o, JdbcTemplate jdbcTemplate){
        final ArrayList<Object> objectArray = new ArrayList<Object>();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Class<?> clazz = o.getClass();
        StringBuffer insertSql = new StringBuffer("INSERT INTO ");
        try {
            StringBuffer valuesSql = new StringBuffer("(");
            String name = clazz.getSimpleName();
            insertSql.append(replaceDataBaseName(name)).append(" ( ");
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                String name1 = declaredField.getName();
                //如果值为NULL则跳过
                Method method = clazz.getMethod("get"+ replaceOneLatterUpper(name1));
                Object invoke = method.invoke(o);
                if(invoke==null){
                    continue;
                }
                objectArray.add(invoke);
                name1 = replaceCapital(name1);
                valuesSql.append("?,");
                insertSql.append(name1).append(",");
            }
            insertSql = insertSql.delete(insertSql.length() - 1, insertSql.length());
            valuesSql = valuesSql.delete(valuesSql.length() - 1, valuesSql.length()).append(")");
            insertSql.append(")").append(" values ").append(valuesSql.toString());

            log.debug(insertSql.toString());
            if(objectArray.size()<=0) {
                return null;
            }
            final String sql = insertSql.toString();
            jdbcTemplate.execute("set names 'utf8mb4'");
            jdbcTemplate.update(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
                    for (int i = 0; i < objectArray.size(); i++) {
                        ps.setObject(i + 1, objectArray.get(i));
                    }
                    return ps;
                }
            },keyHolder);

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return keyHolder;
    }
    /**
     * 插入
     * @param o
     * @param jdbcTemplate
     * @return
     */
    public static boolean insert(Object o, JdbcTemplate jdbcTemplate){
        try {
            Class<?> clazz = o.getClass();
            StringBuffer insertSql = new StringBuffer("INSERT INTO ");
            StringBuffer valuesSql = new StringBuffer("(");
            ArrayList<Object> objectArray = new ArrayList<Object>();
            String name = clazz.getSimpleName();
            insertSql.append(replaceDataBaseName(name)).append(" ( ");
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                String name1 = declaredField.getName();
                //如果值为NULL则跳过
                if(name1.equalsIgnoreCase("serialVersionUID")) {
                    continue;
                }
                Method method = clazz.getMethod("get"+ replaceOneLatterUpper(name1));
                Object invoke = method.invoke(o);
                if(invoke==null){
                    continue;
                }
                objectArray.add(invoke);
                name1 = replaceCapital(name1);
                valuesSql.append("?,");
                insertSql.append(name1).append(",");
            }
            insertSql = insertSql.delete(insertSql.length() - 1, insertSql.length());
            valuesSql = valuesSql.delete(valuesSql.length() - 1, valuesSql.length()).append(")");
            insertSql.append(")").append(" values ").append(valuesSql.toString());
            log.debug(insertSql.toString());
            if(objectArray.size()<=0) {
                return false;
            }
            jdbcTemplate.execute("set names 'utf8mb4'");
            jdbcTemplate.update(insertSql.toString(),objectArray.toArray());

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 插入
     * @param o
     * @param jdbcTemplate
     * @return
     */
    public static boolean insert(String tableName,Object o, JdbcTemplate jdbcTemplate){
        try {
            Class<?> clazz = o.getClass();
            StringBuffer insertSql = new StringBuffer("INSERT INTO ");
            StringBuffer valuesSql = new StringBuffer("(");
            ArrayList<Object> objectArray = new ArrayList<Object>();
            insertSql.append(tableName).append(" ( ");
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                String name1 = declaredField.getName();
                //如果值为NULL则跳过
                if(name1.equalsIgnoreCase("serialVersionUID")) {
                    continue;
                }
                //如果值为NULL则跳过
                Method method = clazz.getMethod("get"+ replaceOneLatterUpper(name1));
                Object invoke = method.invoke(o);
                if(invoke==null){
                    continue;
                }
                objectArray.add(invoke);
                name1 = replaceCapital(name1);
                valuesSql.append("?,");
                insertSql.append(name1).append(",");
            }
            insertSql = insertSql.delete(insertSql.length() - 1, insertSql.length());
            valuesSql = valuesSql.delete(valuesSql.length() - 1, valuesSql.length()).append(")");
            insertSql.append(")").append(" values ").append(valuesSql.toString());
            System.out.println(insertSql.toString());
            if(objectArray.size()<=0) {
                return false;
            }
            jdbcTemplate.execute("set names 'utf8mb4'");
            jdbcTemplate.update(insertSql.toString(),objectArray.toArray());

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }
    /**
     * 更新
     * @param o
     * @param jdbcTemplate
     * @return
     */
    public static boolean update(Object o,JdbcTemplate jdbcTemplate) {
        Class<?> clazz = o.getClass();
        StringBuffer updateSql = new StringBuffer(" update ");
        ArrayList<Object> objectArrayList = new ArrayList<Object>();
        try {
            updateSql.append(replaceDataBaseName(clazz.getSimpleName())).append(" set ");
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                //如果值为NULL则跳过
                if(fieldName.equalsIgnoreCase("serialVersionUID")) {
                    continue;
                }
                Method method = clazz.getMethod("get"+ replaceOneLatterUpper(fieldName));
                Object invoke = method.invoke(o);
                if(invoke==null) {
                    continue;
                }
                updateSql.append(replaceCapital(fieldName) + "=?,");
                objectArrayList.add(invoke);
            }
            updateSql.delete(updateSql.length() - 1, updateSql.length());
            log.debug(updateSql.toString());
            if(objectArrayList.size()<=0) {
                return false;
            }
            jdbcTemplate.execute("set names 'utf8mb4'");
            jdbcTemplate.update(updateSql.toString(), objectArrayList.toArray());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 更新之后返回主键
     * @param o
     * @param jdbcTemplate
     * @return
     */
    public KeyHolder updateReturnPk(Object o, JdbcTemplate jdbcTemplate) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        Class<?> clazz = o.getClass();
        StringBuffer updateSql = new StringBuffer(" update ");
        ArrayList<Object> objectArrayList = new ArrayList<Object>();
        try {
            updateSql.append(replaceDataBaseName(clazz.getSimpleName())).append(" set ");
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                if(fieldName.equalsIgnoreCase("serialVersionUID")) {
                    continue;
                }
                Method method = clazz.getMethod("get" + replaceOneLatterUpper(fieldName));
                Object invoke = method.invoke(o);
                if (invoke == null) {
                    continue;
                }
                updateSql.append(replaceCapital(fieldName) + "=?,");
                objectArrayList.add(invoke);
            }

            updateSql.delete(updateSql.length() - 1, updateSql.length());
            log.debug(updateSql.toString());
            if (objectArrayList.size() <= 0) {
                return null;
            }
            jdbcTemplate.execute("set names 'utf8mb4'");
            jdbcTemplate.update(updateSql.toString(), objectArrayList.toArray(),keyHolder
            );
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return keyHolder;
    }

    /**
     * 更新
     * @param o
     * @param jdbcTemplate
     * @param whereSql
     * @return
     */
    private static boolean update(Object o,JdbcTemplate jdbcTemplate,String whereSql) {
        Class<?> clazz = o.getClass();
        StringBuffer updateSql = new StringBuffer(" update ");
        ArrayList<Object> objectArrayList = new ArrayList<Object>();
        try {
            updateSql.append(replaceDataBaseName(clazz.getSimpleName())).append(" set ");
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                if(fieldName.equalsIgnoreCase("serialVersionUID")) {
                    continue;
                }
                Method method = clazz.getMethod("get"+ replaceOneLatterUpper(fieldName));
                Object invoke = method.invoke(o);
                if(invoke==null) {
                    continue;
                }
                updateSql.append(replaceCapital(fieldName) + "=?,");
                objectArrayList.add(invoke);
            }
            updateSql.delete(updateSql.length() - 1, updateSql.length());
            if(StringUtils.isNotBlank(whereSql)) {
                updateSql.append(" ").append(whereSql);
            }
            log.debug(updateSql.toString());
            if(objectArrayList.size()<=0) {
                return false;
            }
            jdbcTemplate.execute("set names 'utf8mb4'");
            jdbcTemplate.update(updateSql.toString(), objectArrayList.toArray());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 更新
     * @param o
     * @param jdbcTemplate
     * @param whereSql
     * @return
     */
    public static boolean update(Object o,JdbcTemplate jdbcTemplate,String whereSql,Object[]args) {
        Class<?> clazz = o.getClass();
        StringBuffer updateSql = new StringBuffer(" update ");
        ArrayList<Object> objectArrayList = new ArrayList<Object>();
        try {
            updateSql.append(replaceDataBaseName(clazz.getSimpleName())).append(" set ");
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                if(fieldName.equalsIgnoreCase("serialVersionUID")) {
                    continue;
                }
                Method method = clazz.getMethod("get"+ replaceOneLatterUpper(fieldName));
                Object invoke = method.invoke(o);
                if(invoke==null) {
                    continue;
                }
                updateSql.append(replaceCapital(fieldName) + "=?,");
                objectArrayList.add(invoke);
            }
            updateSql.delete(updateSql.length() - 1, updateSql.length());
            if(StringUtils.isNotBlank(whereSql)) {
                updateSql.append(" ").append(whereSql);
            }
            log.debug(updateSql.toString());
            if(objectArrayList.size()<=0) {
                return false;
            }
            jdbcTemplate.execute("set names 'utf8mb4'");
            if( args!=null&&args.length>0){
                for(Object obj:args){
                    objectArrayList.add(obj);
                }
            }

            jdbcTemplate.update(updateSql.toString(), objectArrayList.toArray());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }
    /**
     * 更新
     * @param o
     * @param jdbcTemplate
     * @param whereSql
     * @return
     */
    private static boolean update(String tableName,Object o,JdbcTemplate jdbcTemplate,String whereSql) {
        Class<?> clazz = o.getClass();
        StringBuffer updateSql = new StringBuffer(" update ");
        ArrayList<Object> objectArrayList = new ArrayList<Object>();
        try {
            updateSql.append(tableName).append(" set ");
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                if(fieldName.equalsIgnoreCase("serialVersionUID")) {
                    continue;
                }
                Method method = clazz.getMethod("get"+ replaceOneLatterUpper(fieldName));
                Object invoke = method.invoke(o);
                if(invoke==null) {
                    continue;
                }
                updateSql.append(replaceCapital(fieldName) + "=?,");
                objectArrayList.add(invoke);
            }
            updateSql.delete(updateSql.length() - 1, updateSql.length());
            if(StringUtils.isNotBlank(whereSql)) {
                updateSql.append(" ").append(whereSql);
            }
            log.debug(updateSql.toString());
            if(objectArrayList.size()<=0) {
                return false;
            }
            jdbcTemplate.execute("set names 'utf8mb4'");
            jdbcTemplate.update(updateSql.toString(), objectArrayList.toArray());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 更新
     * @param o
     * @param jdbcTemplate
     * @param whereSql
     * @return
     */
    public static boolean update(String tableName,Object o,JdbcTemplate jdbcTemplate,String whereSql,Object[]args) {
        Class<?> clazz = o.getClass();
        StringBuffer updateSql = new StringBuffer(" update ");
        ArrayList<Object> objectArrayList = new ArrayList<Object>();
        try {
            updateSql.append(tableName).append(" set ");
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                if(fieldName.equalsIgnoreCase("serialVersionUID")) {
                    continue;
                }
                Method method = clazz.getMethod("get"+ replaceOneLatterUpper(fieldName));
                Object invoke = method.invoke(o);
                if(invoke==null) {
                    continue;
                }
                updateSql.append(replaceCapital(fieldName) + "=?,");
                objectArrayList.add(invoke);
            }
            updateSql.delete(updateSql.length() - 1, updateSql.length());
            if(StringUtils.isNotBlank(whereSql)) {
                updateSql.append(" ").append(whereSql);
            }
            log.debug(updateSql.toString());
            if(objectArrayList.size()<=0) {
                return false;
            }
            jdbcTemplate.execute("set names 'utf8mb4'");
            jdbcTemplate.update(updateSql.toString(),args, objectArrayList.toArray());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 删除
     * @param clazz
     * @param jdbcTemplate
     * @param whereSql
     * @return
     */
    private static boolean delete(Class<?> clazz , JdbcTemplate jdbcTemplate, String whereSql) {
        if(StringUtils.isBlank(whereSql)){return  false;}
        StringBuffer deleteSql = new StringBuffer("delete from ");
        try {
            String dbname = clazz.getSimpleName();
            deleteSql.append(replaceDataBaseName(dbname));
            deleteSql.append(" ").append(whereSql);
            jdbcTemplate.update(deleteSql.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    /**
     * 删除
     * @param clazz
     * @param jdbcTemplate
     * @param whereSql
     * @return
     */
    public static boolean delete(Class<?> clazz , JdbcTemplate jdbcTemplate, String whereSql,Object args) {
        if(StringUtils.isBlank(whereSql)){return  false;}
        StringBuffer deleteSql = new StringBuffer("delete from ");
        try {
            String dbname = clazz.getSimpleName();
            deleteSql.append(replaceDataBaseName(dbname));
            deleteSql.append(" ").append(whereSql);
            jdbcTemplate.update(deleteSql.toString(),args);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    /**
     * 删除
     * @param tableName
     * @param jdbcTemplate
     * @param whereSql
     * @return
     */
    private static boolean delete(String tableName, JdbcTemplate jdbcTemplate, String whereSql) {
        if(StringUtils.isBlank(whereSql)){return  false;}
        StringBuffer deleteSql = new StringBuffer("delete from ");
        try {

            deleteSql.append(tableName);
            deleteSql.append(" ").append(whereSql);
            jdbcTemplate.update(deleteSql.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    /**
     * 删除
     * @param tableName
     * @param jdbcTemplate
     * @param whereSql
     * @return
     */
    public static boolean delete(String tableName, JdbcTemplate jdbcTemplate, String whereSql,Object[]args) {
        if(StringUtils.isBlank(whereSql)){return  false;}
        StringBuffer deleteSql = new StringBuffer("delete from ");
        try {

            deleteSql.append(tableName);
            deleteSql.append(" ").append(whereSql);
            jdbcTemplate.update(deleteSql.toString(),args);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }


    public static <T> T  getMembere( JdbcTemplate jdbcTemplate,Class<T> t,String sql , Object[] o) {
        try {
            return jdbcTemplate.queryForObject(sql,t,o);
        } catch (Exception e) {
            return null;
        }
    }

}
