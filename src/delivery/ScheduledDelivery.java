package delivery;

public class ScheduledDelivery {
	
	private int deliveryTime;
	private int maxDistance;
	private int maxOrders;
	
	public ScheduledDelivery(int deliveryTime, int maxDistance, int maxOrders) {
		super();
		this.deliveryTime = deliveryTime;
		this.maxDistance = maxDistance;
		this.maxOrders = maxOrders;
	}

	public int getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(int deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public int getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(int maxDistance) {
		this.maxDistance = maxDistance;
	}

	public int getMaxOrders() {
		return maxOrders;
	}

	public void setMaxOrders(int maxOrders) {
		this.maxOrders = maxOrders;
	}
	
}
