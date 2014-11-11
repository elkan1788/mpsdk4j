package org.elkan1788.osc.weixin.mp.vo;

/**
 * 微信分组信息
 *
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2014/11/10
 * @version 1.0.0
 */
public class Group {

    private int id;
    private String name;
    private int count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
