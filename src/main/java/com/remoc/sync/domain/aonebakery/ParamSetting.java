package com.remoc.sync.domain.aonebakery;


import javax.persistence.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Entity
public class ParamSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;        //主键id
    /**
     * 参数名称
     */
    private String name;

    /**
     * 值
     */
    private String value;

    /**
     * 十二种甜点的颜色
     */
    private String rgb;

    /**
     * 参数设置的parentId
     */
    @Column(name = "parent_id")
    private Integer parentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRgb() {
        return rgb;
    }

    public void setRgb(String rgb) {
        this.rgb = rgb;
    }

    public Integer getParentType() {
        return parentType;
    }

    public void setParentType(Integer parentType) {
        this.parentType = parentType;
    }
}
