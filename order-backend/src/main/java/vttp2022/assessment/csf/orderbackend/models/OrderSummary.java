package vttp2022.assessment.csf.orderbackend.models;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp2022.assessment.csf.orderbackend.services.PricingService;

// IMPORTANT: You can add to this class, but you cannot delete its original content


public class OrderSummary {

	@Autowired
	PricingService pricingSvc;
	
	private Integer orderId;
	private String name;
	private String email;
	private Float amount;

	public void setOrderId(Integer orderId) { this.orderId = orderId; }
	public Integer getOrderId() { return this.orderId; }

	public void setName(String name) { this.name = name; }
	public String getName() { return this.name; }

	public void setEmail(String email) { this.email = email; }
	public String getEmail() { return this.email; }

	public void setAmount(Float amount) { this.amount = amount; }
	public Float getAmount() { return this.amount; }

	public static OrderSummary create(SqlRowSet rs){
		OrderSummary o = new OrderSummary();
		o.setOrderId(rs.getInt("order_id"));
		o.setName(rs.getString("name"));	
		o.setEmail(rs.getString("email"));
		
		//To get total cost: 
		PricingService price = new PricingService();
		String toppings = rs.getString("toppings");
		String[] topping = toppings.split("[,]",0);
		float total = 0;
		for(String top:topping){
			top.replaceAll("[^a-zA-Z]+","");

			total += price.topping(top);

		}
		if(rs.getBoolean("thick_crust")==true){
			total +=price.thickCrust();
		}else{
			total +=price.thinCrust();
		}

		total += price.size(rs.getInt("pizza_size")) + price.sauce(rs.getString("sauce"));

		o.setAmount(total);

		return o;

	}

	public JsonObject toJson(){
		return Json.createObjectBuilder()
			.add("orderId",orderId)
			.add("name",name)
			.add("email",email)
			.add("amount",amount)
			.build();
	}

}
