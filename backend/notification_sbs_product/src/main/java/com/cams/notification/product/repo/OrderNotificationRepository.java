package com.cams.notification.product.repo;
import com.cams.notification.product.model.OrderNotification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import java.util.Map;
@Repository
public class OrderNotificationRepository {
  private final SimpleJdbcInsert insert;
  public OrderNotificationRepository(JdbcTemplate jdbc){
    this.insert=new SimpleJdbcInsert(jdbc).withTableName("order_notifications").usingGeneratedKeyColumns("id");
  }
  public long save(OrderNotification o){
    SqlParameterSource p=new MapSqlParameterSource(Map.of(
      "customer_name",o.getCustomerName(),
      "email",o.getEmail(),
      "order_id",o.getOrderId(),
      "product_name",o.getProductName(),
      "quantity",o.getQuantity(),
      "price",o.getPrice(),
      "total",o.getTotal()
    ));
    Number key=insert.executeAndReturnKey(p);
    return key.longValue();
  }
}
