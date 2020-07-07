package delivery;

public class Rating {
	
	private String restaurantName;
	private int raitng;
	
	public Rating(String restaurantName, int raitng) {
		super();
		this.restaurantName = restaurantName;
		this.raitng = raitng;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public int getRaitng() {
		return raitng;
	}

	public void setRaitng(int raitng) {
		this.raitng = raitng;
	}
	
}
