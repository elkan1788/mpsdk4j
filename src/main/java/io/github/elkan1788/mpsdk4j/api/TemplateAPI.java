package io.github.elkan1788.mpsdk4j.api;

/**
 * 模板消息接口
 * 
 * @author 凡梦星尘(elkan1788@gmail.com)
 * @since 2.0
 */
public interface TemplateAPI {

    /**
     * 设置所属行业地址
     */
    String set_industry = "/template/api_set_industry?access_token=";

    /**
     * 获得模板ID
     */
    String add_template = "/template/api_add_template?access_token=";

    /**
     * 设置所属行业
     * 
     * @param id1
     *            模板消息所属行业编号
     * @param id2
     *            模板消息所属行业编号
     * @return true 或 false
     */
    boolean setIndustry(int id1, int id2);

    /**
     * 获得模板ID
     * 
     * @param tmlShortId
     *            模板库中模板的编号,有"TM**"和"OPENTMTM**"等形式
     * @return 模板Id
     */
    String getTemplateId(String tmlShortId);
}
