package vttp2022.assessment.csf.orderbackend.models;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

// IMPORTANT: You can add to this class, but you cannot delete its original content

public class Order {

	private Integer orderId;
	private String name;
	private String email;
	private Integer size;
	private String sauce;
	private Boolean thickCrust;
	private List<String> toppings = new LinkedList<>();
	private String comments;

	public void setOrderId(Integer orderId) { this.orderId = orderId; }
	public Integer getOrderId() { return this.orderId; }

	public void setName(String name) { this.name = name; }
	public String getName() { return this.name; }

	public void setEmail(String email) { this.email = email; }
	public String getEmail() { return this.email; }

	public void setSize(Integer size) { this.size = size; }
	public Integer getSize() { return this.size; }

	public void setSauce(String sauce) { this.sauce = sauce; }
	public String getSauce() { return this.sauce; }

	public void setThickCrust(Boolean thickCrust) { this.thickCrust = thickCrust; }
	public Boolean isThickCrust() { return this.thickCrust; }

	public void setToppings(List<String> toppings) { this.toppings = toppings; }
	public List<String> getToppings() { return this.toppings; }
	public void addTopping(String topping) { this.toppings.add(topping); }

	public void setComments(String comments) { this.comments = comments; }
	public String getComments() { return this.comments; }

	//Take in a json string and return an Order object
    public static Order create(String json){
		List<String> toppingsList = new ArrayList<>();
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject data = reader.readObject();

		final Order order = new Order();
		order.setName(data.getString("name"));
		order.setEmail(data.getString("email"));
		order.setSize(data.getInt("size"));
		order.setSauce(data.getString("sauce"));
		if(data.getString("base")=="thin"){
			order.setThickCrust(false);
		}else{
			order.setThickCrust(true);
		}
		JsonArray arr = data.getJsonArray("toppings");
		for(JsonValue topping:arr){
			toppingsList.add(topping.toString().replaceAll("\"", ""));
			
		}
		order.setToppings(toppingsList);

		if(data.containsKey("comments")){
            order.setComments(data.getString("comments"));
        }else{
            order.setComments("");
        }
		

        return order;

    }
}
