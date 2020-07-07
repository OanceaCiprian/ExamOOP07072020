import delivery.Delivery;
import delivery.DeliveryException;

public class Test {

	public static void main(String[] args) throws DeliveryException {
		Delivery d = new Delivery();
		
		d.addCategory("test");
		d.addCategory("test1");
		
	}

}
