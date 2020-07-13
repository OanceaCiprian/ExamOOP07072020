package delivery;

public class Order {
	
	private String[] dishNames;
	private int[] quantities;
	private String customerName;
	private String restaurantName;
	private int deliveryTimme;
	private int deliveryDistance;
	private boolean isAssigned = false;
	private int orderNumber = 0;

	public Order(String[] dishNames, int[] quantities, String customerName, String restaurantName, int deliveryTimme,
			int deliveryDistance) {
		super();
		this.dishNames = dishNames;
		this.quantities = quantities;
		this.customerName = customerName;
		this.restaurantName = restaurantName;
		this.deliveryTimme = deliveryTimme;
		this.deliveryDistance = deliveryDistance;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String[] getDishNames() {
		return dishNames;
	}

	public void setDishNames(String[] dishNames) {
		this.dishNames = dishNames;
	}

	public int[] getQuantities() {
		return quantities;
	}

	public void setQuantities(int[] quantities) {
		this.quantities = quantities;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public int getDeliveryTimme() {
		return deliveryTimme;
	}

	public void setDeliveryTimme(int deliveryTimme) {
		this.deliveryTimme = deliveryTimme;
	}

	public int getDeliveryDistance() {
		return deliveryDistance;
	}

	public void setDeliveryDistance(int deliveryDistance) {
		this.deliveryDistance = deliveryDistance;
	}
	
	public boolean isAssigned() {
		return isAssigned;
	}

	public void setAssigned(boolean isAssigned) {
		this.isAssigned = isAssigned;
	}
	
}
