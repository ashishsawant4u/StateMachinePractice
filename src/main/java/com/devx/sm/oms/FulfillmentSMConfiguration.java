package com.devx.sm.oms;

import java.util.EnumSet;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import com.devx.sm.actions.PerformPackaginAction;
import com.devx.sm.actions.PerformQCAction;
import com.devx.sm.actions.PerformResourcingAction;
import com.devx.sm.actions.PerformSouringAction;
import com.devx.sm.actions.SouringFailureHandlerAction;

import jakarta.annotation.Resource;

/**
@author Ashish S
*/
@Configuration
@EnableStateMachineFactory(name = "fulfillmentSMConfigurationRef")
public class FulfillmentSMConfiguration extends StateMachineConfigurerAdapter<FulfillmentStates, FulfillmentEvents> 
{
	@Resource(name = "performSouringAction")
	PerformSouringAction performSouringAction;
	
	@Resource(name = "performQCAction")
	PerformQCAction performQCAction;
	
	@Resource(name = "performPackaginAction")
	PerformPackaginAction performPackaginAction;
	
	@Resource(name = "performResourcingAction")
	PerformResourcingAction performResourcingAction;
	
	@Resource(name = "souringFailureHandlerAction")
	SouringFailureHandlerAction SouringFailureHandlerAction;
	
	@Override
	public void configure(StateMachineStateConfigurer<FulfillmentStates, FulfillmentEvents> states) throws Exception 
	{
		states.withStates()
			.initial(FulfillmentStates.READY_FOR_SOURCING)
			.states(EnumSet.allOf(FulfillmentStates.class))
			.end(FulfillmentStates.PACKED)
			.end(FulfillmentStates.SOURICNG_FAIL)
			.end(FulfillmentStates.QC_FAIL);
	}
	
	@Override
	public void configure(StateMachineTransitionConfigurer<FulfillmentStates, FulfillmentEvents> transitions)
			throws Exception 
	{
		transitions.withExternal()
				  .source(FulfillmentStates.READY_FOR_SOURCING)
				  .target(FulfillmentStates.SOURCED)
				  .event(FulfillmentEvents.INIT_SOURCING)
				  .action(performSouringAction,SouringFailureHandlerAction);
		
		transitions.withExternal()
			  .source(FulfillmentStates.SOURCED)
			  .target(FulfillmentStates.QC_PASS)
			  .event(FulfillmentEvents.INIT_QC)
			  .action(performQCAction);
		
		transitions.withExternal()
			  .source(FulfillmentStates.QC_PASS)
			  .target(FulfillmentStates.PACKED)
			  .event(FulfillmentEvents.INIT_PACKAGING)
			  .action(performPackaginAction);
		
		transitions.withExternal()
			  .source(FulfillmentStates.READY_FOR_SOURCING)
			  .target(FulfillmentStates.SOURICNG_FAIL)
			  .event(FulfillmentEvents.INIT_SOURCINGFAIL);
		
		transitions.withExternal()
			  .source(FulfillmentStates.SOURCED)
			  .target(FulfillmentStates.QC_FAIL)
			  .event(FulfillmentEvents.INIT_QCFAIL);
		
	}
}
