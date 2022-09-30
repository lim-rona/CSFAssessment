package vttp2022.assessment.csf.orderbackend.repositories;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.assessment.csf.orderbackend.models.Order;
import vttp2022.assessment.csf.orderbackend.models.OrderSummary;

@Repository
public class OrderRepository {

    private static final String SQL_INSERT_ORDER="insert into orders(name,email,pizza_size,thick_crust,sauce,toppings,comments) values (?, ?, ?,?,?,?,?)";

	private static final String SQL_GET_ORDERS_BY_EMAIL = "select * from orders where email = ?";


    
    @Autowired
    JdbcTemplate template;



    public void insertOrder(Order order){
        String top="";

        for(String topping:order.getToppings()){
            top +=topping + ",";
        }
        template.update(SQL_INSERT_ORDER, order.getName(),order.getEmail(),order.getSize(),order.isThickCrust(),order.getSauce(),top,order.getComments());
    }

    public List<OrderSummary> getOrdersByEmail(String email){
        List<OrderSummary> summaries = new LinkedList<>();

        SqlRowSet rs = template.queryForRowSet(SQL_GET_ORDERS_BY_EMAIL, email);
        while(rs.next()){
            OrderSummary summary = OrderSummary.create(rs);
            summaries.add(summary);
        }

        return summaries;
    }
}
