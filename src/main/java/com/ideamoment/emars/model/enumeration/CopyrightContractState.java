/**
 * 
 */
package com.ideamoment.emars.model.enumeration;


/**
 * @author Chinakite
 *
 */
public class CopyrightContractState {
    
    public static final String DRAFT = "0";    //草稿
    public static final String DRAFT_TEXT = "草稿";
    
    public static final String DIRECTOR_AUDIT = "1";    //主管审批 
    public static final String DIRECTOR_AUDIT_TEXT = "待主管审批";     
        
    public static final String MANAGER_AUDIT = "2";     //经理审批
    public static final String MANAGER_AUDIT_TEXT = "待经理审批";     
    
    public static final String CEO_AUDIT = "3";     //总经理审批
    public static final String CEO_AUDIT_TEXT = "待总经理审批";     
    
    public static final String AUDIT_FINISH = "4";  //审批完成
    public static final String AUDIT_FINISH_TEXT = "审批完成";  
    
    public static final String LAWYER_AUDIT = "5";    //法务审批
    public static final String LAWYER_AUDIT_TEXT = "待法务审批";    
    
    public static final String LAWYER_CONFIRM = "6";    //法务确认
    public static final String LAWYER_CONFIRM_TEXT = "法务确认";
    
    public static final String FINISH_CONFIRM = "7";    //法务确认
    public static final String FINISH_CONFIRM_TEXT = "待最终确认";
    
    public static final String REJECTED = "51";
    public static final String REJECTED_TEXT = "已拒绝";
    
    public static final String FINISH = "99";        //完成
    public static final String FINISH_TEXT = "完成";        //完成
}
