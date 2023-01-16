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
@Component("performSouringAction")
public class PerformSouringAction implements Action<FulfillmentStates, FulfillmentEvents> 
{
	@Override
	public void execute(StateContext<FulfillmentStates, FulfillmentEvents> context) 
	{
		log.info("(PerformSouringAction)");
		
		log.info("EXTENDED VARIBALES "+context.getStateMachine().getExtendedState().getVariables());
		
		OrderModel orderModel = (OrderModel)context.getMessage().getHeaders().get("order");
		if(null!=orderModel)
		{
			log.info("ORDER IN ACTION ==> "+orderModel.getOrderid());
			
			if((boolean)context.getStateMachine().getExtendedState().getVariables().get("failSourcingAction"))
			{
				int x = 100/0;  //throwing some error during sourcing action this should call SouringFailureHandlerAction
			}
			
			orderModel.setOrderStatus(FulfillmentStates.SOURCED.name());
		}
		
		
	}
}
