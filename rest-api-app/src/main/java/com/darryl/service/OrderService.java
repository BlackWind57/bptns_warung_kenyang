package com.darryl.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.darryl.bean.Orders;
import com.darryl.repository.OrderRepository;
import com.darryl.requestbean.Order;
import com.darryl.requestbean.OrderItem;
import com.darryl.responsebean.OrderResponse;

@Service
@Component
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired MenuItemService menuItemService;
	
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
			
			com.darryl.bean.MenuItem item = 
					menuItemService.getMenuItem(reqOrderItem.getMenuItem());			
			
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
