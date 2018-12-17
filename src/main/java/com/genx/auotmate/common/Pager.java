package com.genx.auotmate.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 翻页包装类
 *
 * @author: genx
 * @date: 2018/12/13 22:43
 */
public class Pager<T> {
    /**
     * 页码
     */
    private int pageNo;
    /**
     * 页记录数
     */
    private int pageSize;
    /**
     * 总记录数
     */
    private int total;
    /**
     * 查询结果
     */
    private List<T> resultList;
    /**
     * 开始ID
     **/
    private int sinceId = -1;
    /**
     * 最大Id
     **/
    private int maxId = -1;
    /**
     * 页面路径
     */
    private String pageUrl;
    /**
     * 页面参数
     */
    private String params;


    /**
     * 存放统计数据，  例如总金额、总人次
     */
    private Map<String, Object> tabulateData = new HashMap();


    private Pager(int pageNo, int pageSize, int total) {
        if (pageNo <= 0) {
            pageNo = 1;
        }
        if (pageSize <= 0) {
            pageSize = 20;
        }

        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.total = total;
    }

    public static Pager empty() {
        Pager pager = new Pager(1, 20, 0);
        pager.setResultList(new ArrayList<>());
        return pager;
    }

    public static Pager of(int pageNo, int pageSize, int total) {
       return new Pager(pageNo, pageSize, total);
    }

    public int getPageCount() {
        int num = total / pageSize + (total % pageSize == 0 ? 0 : 1);
        // 判断没有记录的时候
        return total == 0 ? 1 : num;
    }

    public int getPageNo() {
        return pageNo;
    }

    /**
     * 反回下一个页面号
     *
     * @return
     * @author lujun
     */
    public int getNextPage() {
        if (this.pageNo < getPageCount()) {
            return this.pageNo + 1;
        } else {
            return 1;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    /**
     * 取第一个记录数
     *
     * @return
     * @author lujun
     */
    public int getIndexOf() {
        int num = (pageNo - 1) * pageSize;
        return num;
    }

    /**
     * 获取当前记录 sql 的
     *
     * @return
     * @author lujun
     */
    public int getLastIndex() {
        int num = (pageNo * pageSize);
        return Math.min(num, total);
    }


    public int getSinceId() {
        return sinceId;
    }

    public void setSinceId(int sinceId) {
        this.sinceId = sinceId;
    }

    public int getMaxId() {
        return maxId;
    }

    public void setMaxId(int maxId) {
        this.maxId = maxId;
    }

    public List<?> getResultList() {
        return resultList;
    }


    public int getTotal() {
        return total;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Map<String, Object> getTabulateData() {
        return tabulateData;
    }

    public void addTabulateData(String key, Object val) {
        if (key != null) {
            tabulateData.put(key, val);
        }
    }
}
