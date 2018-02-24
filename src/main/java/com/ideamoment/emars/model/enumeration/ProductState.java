/**
 * 
 */
package com.ideamoment.emars.model.enumeration;


/**
 * @author Chinakite
 *
 */
public class ProductState {
    
    public static final String DRAFT = "0";  //
    public static final String DRAFT_TEXT = "草稿";
    
    public static final String APPROVE_WAITING = "1";
    public static final String APPROVE_WAITING_TEXT = "待内容审核";
    
    public static final String APPROVE_REJECT = "101";
    public static final String APPROVE_REJECT_TEXT = "审核不过";
    
    public static final String EVALUATE_WAITING = "2";
    public static final String EVALUATE_WAITING_TEXT = "待评价";
    
    public static final String EVALUATED = "3";
    public static final String EVALUATED_TEXT = "已评价";
    
    public static final String EVALUATE_FINISH = "4";
    public static final String EVALUATE_FINISH_TEXT = "评价完成";
    
    public static final String CP_CONTRACT_INFLOW = "5";
    public static final String CP_CONTRACT_INFLOW_TEXT = "签约流程中";
    
    public static final String CP_CONTRACT_FINISH = "6";
    public static final String CP_CONTRACT_FINISH_TEXT = "版权合同完成";
    
    public static final String RESERVED = "7";
    public static final String RESERVED_TEXT = "已预订";
    
    public static final String CPFILE_APPROVE_WAITING = "8";
    public static final String CPFILE_APPROVE_WAITING_TEXT = "待权属审核";
    
    public static final String MK_WAITING = "10";
    public static final String MK_WAITING_TEXT = "待制作";
    
    public static final String MK_CONTRACT = "11";
    public static final String MK_CONTRACT_TEXT = "制作合同签订";
    
    public static final String MK = "12";
    public static final String MK_TEXT = "制作中";

    public static final String MK_FINISH = "13";
    public static final String MK_FINISH_TEXT = "制作完成";
    
    public static final String SALED = "14";
    public static final String SALED_TEXT = "已销售";
    
    public static final String NEW_WITHOUT_EVA = "50";
    public static final String NEW_WITHOUT_EVA_TEXT = "未签约";
}
