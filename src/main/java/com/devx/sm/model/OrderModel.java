package com.devx.sm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 @author Ashish S
*/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderModel 
{	
	public long orderid;
	
	public String userId;
	
	public String orderStatus;
}
