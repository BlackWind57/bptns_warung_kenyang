package com.darryl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.darryl.repository.DrinkItemRepository;
import com.darryl.repository.FoodItemRepository;
import com.darryl.requestbean.MenuItem;

@Service
@Component
public class MenuItemService {

	@Autowired
	private FoodItemRepository foodRepository;
	
	@Autowired
	private DrinkItemRepository drinkRepository;	
	
	public com.darryl.bean.MenuItem getMenuItem ( MenuItem menuItem ) {
		com.darryl.bean.MenuItem item = null;
		
		if ( "food".equals(menuItem.getType()) ) {
			item = foodRepository.findByName( menuItem.getName() );
		}
		else if ( "drink".equals(menuItem.getType()) ) {
			item = drinkRepository.findByName( menuItem.getName() );
		}
		
		if ( item == null )
			throw new ResponseStatusException (
					HttpStatus.BAD_REQUEST, "Invalid menu item");
		
		return item;
	}

}
