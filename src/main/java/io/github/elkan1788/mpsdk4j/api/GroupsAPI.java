package io.github.elkan1788.mpsdk4j.api;

import java.util.Collection;
import java.util.List;

import io.github.elkan1788.mpsdk4j.vo.api.Groups;

/**
 * 微信用户组管理接口
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public interface GroupsAPI {

    /**
     * 创建分组地址
     */
    String create_groups = "/groups/create?access_token=";

    /**
     * 查询所有分组地址
     */
    String get_groups = "/groups/get?access_token=";

    /**
     * 查询用户所在分组地址
     */
    String get_member_group = "/groups/getid?access_token=";

    /**
     * 修改分组名称地址
     */
    String update_group = "/groups/update?access_token=";

    /**
     * 移动用户分组地址
     */
    String update_member_group = "/groups/members/update?access_token=";

    /**
     * 批量移动用户分组地址
     */
    String update_members_group = "/groups/members/batchupdate?access_token=";

    /**
     * 删除分组地址
     */
    String delete_groups = "/groups/delete?access_token=";

    /**
     * 创建用户分组
     * 
     * <pre/>
     * 一个公众账号,最多支持创建100个分组
     * 
     * @param name
     *            分组名字(30个字符以内)
     * @return 分组Id
     */
    int createGroup(String name);

    /**
     * 查询所有分组
     * 
     * @return 分组集合{@link Groups}
     */
    List<Groups> getGroups();

    /**
     * 查询用户所在分组
     * 
     * @param openId
     *            用户openId
     * @return 分组Id
     */
    int getGroup(String openId);

    /**
     * 修改分组名称
     * 
     * @param id
     *            分组Id
     * @param name
     *            新的分组名称
     * @return true 或 false
     */
    boolean renGroups(int id, String name);

    /**
     * 移动用户所在分组
     * 
     * @param openId
     *            用户openId
     * @param groupId
     *            分组Id
     * @return true 或 false
     */
    boolean move2Group(String openId, int groupId);

    /**
     * 批量移动用户分组
     * 
     * @param openIds
     *            用户openId集合
     * @param groupId
     *            分组Id
     * @return true 或 false
     */
    boolean batchMove2Group(Collection<String> openIds, int groupId);

    /**
     * 删除分组
     * 
     * @param id
     *            分组Id
     * @return true 或 false
     */
    boolean delGroup(int id);
}
