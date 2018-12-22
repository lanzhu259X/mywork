package com.lanzhu.mywork.master.model;

import com.lanzhu.mywork.master.commons.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * description:
 *
 * @author lanzhu259X
 * @date 2018-12-23
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SearchResult<T> extends ToString {
    private static final long serialVersionUID = 1477416875336109453L;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 记录列表
     */
    private List<T> rows;

}
