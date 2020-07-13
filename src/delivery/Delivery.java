package delivery;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Delivery {
	
	private int order_id = 1;
	private List<String> categories = new ArrayList<>();
	private Map<String, Restaurant> restaurants = new HashMap<>();
	private Map<String, Dish> dishes = new HashMap<>();
	private Map<String, List<String>> dishesForRestaurants = new HashMap<>();
	private List<Order> orders = new ArrayList<>();
	private List<Rating> ratings = new ArrayList<>();
	private List<Integer> scheduledDeliveries = new ArrayList<>();
	
	// R1
	
    /**
     * adds one category to the list of categories managed by the service.
     * 
     * @param category name of the category
     * @throws DeliveryException if the category is already available.
     */
	public void addCategory (String category) throws DeliveryException {
		if(categories.contains(category)) {
			throw new DeliveryException("The category is already available");
		}
		categories.add(category);
	}
	
	/**
	 * retrieves the list of defined categories.
	 * 
	 * @return list of category names
	 */
	public List<String> getCategories() {
		return categories;
	}
	
	/**
	 * register a new restaurant to the service with a related category
	 * 
	 * @param name     name of the restaurant
	 * @param category category of the restaurant
	 * @throws DeliveryException if the category is not defined.
	 */
	public void addRestaurant (String name, String category) throws DeliveryException {
		if(!categories.contains(category)) {
			throw new DeliveryException("Category undefined");
		}
		
		Restaurant restaurant = new Restaurant(name, category);
		
		restaurants.put(name, restaurant);
	}
	
	/**
	 * retrieves an ordered list by name of the restaurants of a given category. 
	 * It returns an empty list in there are no restaurants in the selected category 
	 * or the category does not exist.
	 * 
	 * @param category name of the category
	 * @return sorted list of restaurant names
	 */
	public List<String> getRestaurantsForCategory(String category) {
		List<String> restaurantsForCategory = new ArrayList<>();
		
		for(Restaurant restaurant : restaurants.values()) {
			if(restaurant.getCategory().contains(category)) {
				restaurantsForCategory.add(restaurant.getName());
			}
		}
		
        return restaurantsForCategory.stream()
        		.sorted()
        		.collect(Collectors.toList());
	}
	
	// R2
	
	/**
	 * adds a dish to the list of dishes of a restaurant. 
	 * Every dish has a given price.
	 * 
	 * @param name             name of the dish
	 * @param restaurantName   name of the restaurant
	 * @param price            price of the dish
	 * @throws DeliveryException if the dish name already exists
	 */
	public void addDish(String name, String restaurantName, float price) throws DeliveryException {
	
		Dish dish = new Dish(name, restaurantName, price);
		
		if(dishes.containsKey(name)) {
			throw new DeliveryException("This dish already exists");
		}
		
		dishes.put(name, dish);
	
	}
	
	/**
	 * returns a map associating the name of each restaurant with the 
	 * list of dish names whose price is in the provided range of price (limits included). 
	 * If the restaurant has no dishes in the range, it does not appear in the map.
	 * 
	 * @param minPrice  minimum price (included)
	 * @param maxPrice  maximum price (included)
	 * @return map restaurant -> dishes
	 */
	public Map<String,List<String>> getDishesByPrice(float minPrice, float maxPrice) {
        
		for(Restaurant restaurant : restaurants.values()) {
			List<String> dishesInRange = new ArrayList<>();
			for(Dish dish : dishes.values()) {
				if(restaurant.getName().equals(dish.getRestaurantName())) {
					if(dish.getPrice() >= minPrice && dish.getPrice() <= maxPrice) {
						dishesInRange.add(dish.getName());
					}
				}
			}
			if(dishesInRange.size() != 0) {
				dishesForRestaurants.put(restaurant.getName(), dishesInRange);
			}
		}
		
		return dishesForRestaurants;
	}
	
	/**
	 * retrieve the ordered list of the names of dishes sold by a restaurant. 
	 * If the restaurant does not exist or does not sell any dishes 
	 * the method must return an empty list.
	 *  
	 * @param restaurantName   name of the restaurant
	 * @return alphabetically sorted list of dish names 
	 */
	public List<String> getDishesForRestaurant(String restaurantName) {
		List<String> dishesForRestaurant = new ArrayList<>();
		
		for(Dish dish : dishes.values()) {
			if(dish.getRestaurantName().equals(restaurantName)) {
				dishesForRestaurant.add(dish.getName());				
			}
		}
		
        return dishesForRestaurant.stream()
        		.sorted()
        		.collect(Collectors.toList());
	}
	
	/**
	 * retrieves the list of all dishes sold by all restaurants belonging to the given category. 
	 * If the category is not defined or there are no dishes in the category 
	 * the method must return and empty list.
	 *  
	 * @param category     the category
	 * @return 
	 */
	public List<String> getDishesByCategory(String category) {
		List<String> dishesByCategory = new ArrayList<>();
		
		for(Dish dish : dishes.values()) {
			for(Restaurant restaurant : restaurants.values()) {
				if(restaurant.getCategory().equals(category) && dish.getRestaurantName().equals(restaurant.getName())) {
					dishesByCategory.add(dish.getName());
				}
			}
		}
		
        return dishesByCategory;
	}
	
	//R3
	
	/**
	 * creates a delivery order. 
	 * Each order may contain more than one product with the related quantity. 
	 * The delivery time is indicated with a number in the range 8 to 23. 
	 * The delivery distance is expressed in kilometers. 
	 * Each order is assigned a progressive order ID, the first order has number 1.
	 * 
	 * @param dishNames        names of the dishes
	 * @param quantities       relative quantity of dishes
	 * @param customerName     name of the customer
	 * @param restaurantName   name of the restaurant
	 * @param deliveryTime     time of delivery (8-23)
	 * @param deliveryDistance distance of delivery
	 * 
	 * @return order ID
	 */
	public int addOrder(String dishNames[], int quantities[], String customerName, String restaurantName, int deliveryTime, int deliveryDistance) {
		Order order = new Order(dishNames, quantities, customerName, restaurantName, deliveryTime, deliveryDistance);
		order.setOrderNumber(this.orders.size() + 1);
		orders.add(order);
		
		return order_id++;
	}
	
	/**
	 * retrieves the IDs of the orders that satisfy the given constraints.
	 * Only the  first {@code maxOrders} (according to the orders arrival time) are returned
	 * they must be scheduled to be delivered at {@code deliveryTime} 
	 * whose {@code deliveryDistance} is lower or equal that {@code maxDistance}. 
	 * Once returned by the method the orders must be marked as assigned 
	 * so that they will not be considered if the method is called again. 
	 * The method returns an empty list if there are no orders (not yet assigned) 
	 * that meet the requirements.
	 * 
	 * @param deliveryTime required time of delivery 
	 * @param maxDistance  maximum delivery distance
	 * @param maxOrders    maximum number of orders to retrieve
	 * @return list of order IDs
	 */
	public List<Integer> scheduleDelivery(int deliveryTime, int maxDistance, int maxOrders) {
		List<Order> scheduledOrders = this.orders
				.stream()
				.filter(order -> !order.isAssigned() && order.getDeliveryTimme() == deliveryTime && order.getDeliveryDistance() <= maxDistance)
				.limit(maxOrders).collect(Collectors.toList());
			scheduledOrders.forEach(order -> order.setAssigned(true));
			List<Integer> scheduledDeliveries = scheduledOrders.stream()
													.map(order -> order.getOrderNumber())
													.collect(Collectors.toList());
		
		return scheduledDeliveries;
	}
	
	/**
	 * retrieves the number of orders that still need to be assigned
	 * @return the unassigned orders count
	 */
	public int getPendingOrders() {
		return (int)this.orders.stream().filter(order -> !order.isAssigned()).count();
	}
	
	// R4
	/**
	 * records a rating (a number between 0 and 5) of a restaurant.
	 * Ratings outside the valid range are discarded.
	 * 
	 * @param restaurantName   name of the restaurant
	 * @param rating           rating
	 */
	public void setRatingForRestaurant(String restaurantName, int rating) {
		if(rating >= 0 || rating <= 5) {
			Rating r = new Rating(restaurantName, rating);
			ratings.add(r);
		}
	}
	
	/**
	 * retrieves the ordered list of restaurant. 
	 * 
	 * The restaurant must be ordered by decreasing average rating. 
	 * The average rating of a restaurant is the sum of all rating divided by the number of ratings.
	 * 
	 * @return ordered list of restaurant names
	 */
	public List<String> restaurantsAverageRating() {
		List<String> restaurantsByRating = new ArrayList<>();
		for(Restaurant restaurant : restaurants.values()) {
			double result = 0.0;
			int counter = 0;
			for(Rating rating : ratings) {
				if(rating.getRestaurantName().equals(restaurant.getName())) {
					result += rating.getRaitng();
					counter++;
				}
			}
		}
		
		Map<String, Double> averageRatings =
        		this.ratings.stream()
        		.collect(Collectors.groupingBy(rating->rating.getRestaurantName(), Collectors.averagingDouble(rating->rating.getRaitng())));
        
        List<String> restaurantsOrderedByRating =   
        averageRatings
		.entrySet()
		.stream()
		.sorted(Map.Entry.comparingByValue())
		.collect(Collectors.toList())
		.stream()
		.map(entry -> entry.getKey())
        .collect(Collectors.toList());
        Collections.reverse(restaurantsOrderedByRating);
        return restaurantsOrderedByRating;
	}
	
	//R5
	/**
	 * returns a map associating each category to the number of orders placed to any restaurant in that category. 
	 * Also categories whose restaurants have not received any order must be included in the result.
	 * 
	 * @return map category -> order count
	 */
	public Map<String,Long> ordersPerCategory() {
		return this.orders.stream()
				.map(order -> this.restaurants.get(order.getRestaurantName()).getCategory())
				.collect(Collectors.toList())
				.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		}
	
	/**
	 * retrieves the name of the restaurant that has received the higher average rating.
	 * 
	 * @return restaurant name
	 */
	public String bestRestaurant() {
			List<String> restaurantsByAverageRating = this.restaurantsAverageRating();
	        if(restaurantsByAverageRating.isEmpty()) {
	        	return null;
	        }
	        return restaurantsByAverageRating.get(0);
        }
}
