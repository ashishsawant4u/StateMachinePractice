package com.devx.sm.actions;

import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import com.devx.sm.model.OrderModel;
import com.devx.sm.oms.FulfillmentEvents;
import com.devx.sm.oms.FulfillmentStates;

import lombok.extern.slf4j.Slf4j;

/**
 @author Ashish S
*/
@Slf4j
@Component("performResourcingAction")
public class PerformResourcingAction implements Action<FulfillmentStates, FulfillmentEvents>
{
	@Override
	public void execute(StateContext<FulfillmentStates, FulfillmentEvents> context) 
	{
		log.info("(PerformResourcingAction)");
		
		OrderModel orderModel = (OrderModel)context.getMessage().getHeaders().get("order");
		if(null!=orderModel)
		{
			log.info("ORDER IN ACTION ==> "+orderModel.getOrderid());
			orderModel.setOrderStatus(FulfillmentStates.SOURICNG_FAIL.name());
			context.getMessage().getHeaders().put("order", orderModel);
		}
		
	}
}
