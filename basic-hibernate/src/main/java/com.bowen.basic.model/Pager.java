package com.bowen.basic.model;

import java.util.List;

/**
* 分页对象
 * @AUTHOR BOWEN
 * @PARAM <T>
* */
public class Pager<T> {
    /**
    * 分页的大小
    * */
    private int size;

    /**分页起始页*/
    private int offset;
    /**分页总大小*/
    private int total;

    /**分页的数据*/
    private List<T> datas;


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}
