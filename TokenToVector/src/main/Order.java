package main;

import java.util.ArrayList;
import java.util.List;

public class Order {
	
	/**
	 * Give a list (vector) it reorders its items according to the order given by order
	 * @param vector
	 * @param order
	 */
	public static List<Object> reorder(List<Object> vector, List<String> order) {
		List<Object> newList = new ArrayList<Object>();
		
		for(String itemOrder : order){
			int pos = Integer.parseInt(itemOrder);
			newList.add(vector.get(pos-1));
		}
		
		return newList;
	}
	
}
