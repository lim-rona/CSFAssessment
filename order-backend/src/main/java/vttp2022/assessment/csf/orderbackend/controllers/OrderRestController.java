package vttp2022.assessment.csf.orderbackend.controllers;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import vttp2022.assessment.csf.orderbackend.models.Order;
import vttp2022.assessment.csf.orderbackend.models.OrderResponse;
import vttp2022.assessment.csf.orderbackend.models.OrderSummary;
import vttp2022.assessment.csf.orderbackend.services.OrderService;

@RestController
public class OrderRestController {

    @Autowired
    OrderService orderSvc;

    @PostMapping(path="/api/order",produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postContact(@RequestBody String payload){

        Order order = new Order();
        
        //Read the payload 
        try{
            order = Order.create(payload);
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("Exception occured when reading payload");
        }
        //Save to database
        
        orderSvc.createOrder(order);
        

        return ResponseEntity.ok(Json.createObjectBuilder().add("success","200").build().toString());
    }

    @GetMapping(path="/api/order/{email}/all", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getContacts(@PathVariable String email){

        List<OrderSummary> summaryList = orderSvc.getOrdersByEmail(email);
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for(OrderSummary summary: summaryList){
            arrayBuilder.add(summary.toJson());
        }
        return ResponseEntity.ok(arrayBuilder.build().toString());

    }
}
