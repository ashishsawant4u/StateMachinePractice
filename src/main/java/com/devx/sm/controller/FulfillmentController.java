package com.devx.sm.controller;

import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.devx.sm.model.OrderModel;
import com.devx.sm.oms.FulfillmentEvents;
import com.devx.sm.oms.FulfillmentStateMachineHandler;
import com.devx.sm.oms.FulfillmentStates;

import jakarta.annotation.Resource;

/**
 @author Ashish S
*/
@Controller
@RequestMapping("/oms")
public class FulfillmentController 
{
	@Resource(name = "fulfillmentStateMachineHandler")
	FulfillmentStateMachineHandler fulfillmentStateMachineHandler;
	
	@RequestMapping("/fulfill")
	public ResponseEntity<String> fullfillOrder()
	{
		OrderModel orderModel = getOrderModel();
		initStateMachine(orderModel);
		return new ResponseEntity<String>("OK",HttpStatus.OK);
	}
	
	private void initStateMachine(OrderModel orderModel) 
	{
		StateMachine<FulfillmentStates, FulfillmentEvents> stateMachine = fulfillmentStateMachineHandler.getInstnce(FulfillmentStates.READY_FOR_SOURCING);
		Message<FulfillmentEvents> sourcingMsg =  MessageBuilder.withPayload(FulfillmentEvents.INIT_SOURCING).setHeader("order",orderModel).build();
	    
		stateMachine.getExtendedState().getVariables().put("someRandomVariable", "someRandomValue");
		stateMachine.getExtendedState().getVariables().put("someRandomBooleanVariable", false);
		
	    stateMachine.sendEvent(sourcingMsg);
	}
	
	private OrderModel getOrderModel() 
	{
		OrderModel orderModel = new OrderModel();
		
		orderModel.setOrderid(new Random().nextLong());
		orderModel.setUserId("rohitsharma@gmail.com");
		orderModel.setOrderStatus(FulfillmentStates.READY_FOR_SOURCING.name());
		
		return orderModel;
	}
	
}
