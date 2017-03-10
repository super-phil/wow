package com.magic.wow.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhaoxf on 2017/3/6.
 */
@Data
@NoArgsConstructor
public class DTRequest implements Serializable {

    private static final long serialVersionUID = 1056725809206873758L;
    private int draw;
    private int start;//开始
    private int length;//长度
    private DTSearch search;//查询参数
    private List<DTOrder> order;
    private List<DTColumn> columns;

    /**
     * 查询参数
     *
     * @return the search
     */
    public String getQ() {
        return search.getValue();
    }

    /**
     * 模糊查询
     *
     * @return the search
     */
    public String getFullike() {
        return "%" + search.getValue() + "%";
    }

    /**
     * 左模糊查询
     *
     * @return the search
     */
    public String getLeftLike() {
        return "%" + search.getValue();
    }

    /**
     * 右模糊查询
     *
     * @return the search
     */
    public String getRightLike() {
        return search.getValue() + "%";
    }

    /**
     * 排序
     *
     * @return the string
     */
    public String getOrder() {
        DTOrder dtOrder = order.get(0);//默认列排序
        return columns.get(dtOrder.getColumn()).getData() + " " + dtOrder.getDir();
    }


}
