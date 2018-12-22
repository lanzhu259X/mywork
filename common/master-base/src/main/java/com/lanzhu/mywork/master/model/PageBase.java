package com.lanzhu.mywork.master.model;

import com.lanzhu.mywork.master.commons.ToString;
import lombok.Getter;
import lombok.Setter;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-23
 */
@Setter
@Getter
public class PageBase extends ToString {
    private static final long serialVersionUID = -5655047564155101209L;

    private Integer page;

    private Integer rows;

    public int getRows() {
        if (rows == null || rows <= 0) {
            return 10;
        }
        return rows;
    }

    /**
     * 当page是空的时候，考虑不分页
     * @return
     */
    public Integer getOffset() {
        if (page == null) {
            return null;
        }
        if (page <=0) {
            page = 1;
        }
        rows = getRows();
        return (page - 1) * rows;
    }


}
