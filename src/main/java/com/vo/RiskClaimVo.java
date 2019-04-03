package com.vo;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RiskClaimVo{
	//案例名称
	private String claimName; 
	//产品名称
	private String riskCname;
	//出险年度
	private String claimYear;
	//最低赔款金额
	private BigDecimal claimAmountLow;
	//最高赔款金额
	private BigDecimal claimAmountHigh;
	//险种
	private String [] riskNames;
	//行业
	private String [] professions;
	//案件来源
	private String [] senders;
	//出险原因
	private String [] claimReasons;
	//审核状态
	private String [] validStatus;
	
	private String [] geometrys;
	
	
	
	
}
