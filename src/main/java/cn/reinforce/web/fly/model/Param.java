package cn.reinforce.web.fly.model;

import java.io.Serializable;

/**
 * 用于存放一些系统参数
 * @author 幻幻Fate
 * @create 2017/3/22
 */
public class Param implements Serializable{

    public static final int TYPE_TEXT = 1;

    public static final int TYPE_INT = 2;
    private static final long serialVersionUID = -7949394889871566596L;

    private int id;

    private int intValue;

    private String key;

    private int type;

    private String textValue;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key == null ? null : key.trim();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue == null ? null : textValue.trim();
    }
}