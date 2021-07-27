package com.darryl.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.darryl.bean.Orders;
import com.darryl.repository.DrinkItemRepository;
import com.darryl.repository.FoodItemRepository;
import com.darryl.repository.OrderRepository;
import com.darryl.requestbean.Order;
import com.darryl.requestbean.OrderItem;
import com.darryl.responsebean.OrderResponse;

@Service
@Component
public class OrderService {
	
	private Logger logger
		= LoggerFactory.getLogger(OrderService.class);

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private FoodItemRepository foodRepository;
	
	@Autowired
	private DrinkItemRepository drinkRepository;	
	
	public List<OrderResponse> getAllOrders() {
		return orderRepository.findAll()
				.stream()
				.map(OrderResponse::new)
				.collect(Collectors.toList());
	}
	
	@Transactional
	public Orders addOrders( Order reqOrder ) throws ResponseStatusException {
		Orders order = new Orders( reqOrder.getTableNumber() );
			
		List<com.darryl.bean.OrderItem> orderItems = new ArrayList<>();
		
		for ( OrderItem reqOrderItem : reqOrder.getOrderItems() ) {
			reqOrderItem.getQuantity();
			
			String name = reqOrderItem.getMenuItem().getName();
			String type = reqOrderItem.getMenuItem().getType();
			com.darryl.bean.MenuItem item;
			
			if ( "food".equals(type) ) {
				item = foodRepository.findByName( name );
			}
			else if ( "drink".equals(type) ) {
				item = drinkRepository.findByName( name );
			}
			else {
				throw new ResponseStatusException ( 
						HttpStatus.BAD_REQUEST, 
						"Contain missing request fields");
			}
			
			//logger.info(item.toString());
			
			com.darryl.bean.OrderItem orderItem = 
					new com.darryl.bean.OrderItem ( 
							reqOrderItem.getQuantity(), 
							item, 
							order);
			
			//logger.info(orderItem.toString());
			orderItems.add( orderItem );
		}
		//logger.info( "Final order items: \n"+ Arrays.toString( orderItems.toArray() ) );

		order.setOrderItems(orderItems);
		
		return order;
	}
	
	@Transactional
	public Orders saveOrder ( Order order ) throws ResponseStatusException {
		Orders createdOrder = addOrders ( order );
		return orderRepository.saveAndFlush(createdOrder);
	}

}
