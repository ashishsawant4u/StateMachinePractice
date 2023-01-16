package com.devx.sm.actions;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
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
@Component("souringFailureHandlerAction")
public class SouringFailureHandlerAction implements Action<FulfillmentStates, FulfillmentEvents> 
{
	@Override
	public void execute(StateContext<FulfillmentStates, FulfillmentEvents> context) 
	{
		log.info("(SouringFailureHandlerAction)");

		OrderModel orderModel = (OrderModel)context.getMessage().getHeaders().get("order");
		if(null!=orderModel)
		{
			log.info("SOURCING FAILURE FOR ORDER ==> "+orderModel.getOrderid());
			
			//publishing event INIT_SOURCINGFAIL so that order goes for re-sourcing
			Message<FulfillmentEvents> sourcingFailMsg =  MessageBuilder.withPayload(FulfillmentEvents.INIT_SOURCINGFAIL).setHeader("order",orderModel).build();
			context.getStateMachine().sendEvent(sourcingFailMsg);	
		}


	}
}