package com.mycompany.cw2.storage;

import com.mycompany.cw2.model.Order;
import java.util.*;

public class OrderDataStore {
    private static final Map<Integer, List<Order>> orderStore = new HashMap<>();
    private static int currentOrderId = 1;

    public static Order createOrder(int customerId, List<Integer> bookIds) {
        Order newOrder = new Order(currentOrderId++, customerId, new ArrayList<>(bookIds));
        orderStore.computeIfAbsent(customerId, k -> new ArrayList<>()).add(newOrder);
        return newOrder;
    }

    public static List<Order> getAllOrdersForCustomer(int customerId) {
        return orderStore.get(customerId);
    }

    public static Order getOrderById(int customerId, int orderId) {
        List<Order> orders = orderStore.get(customerId);
        if (orders != null) {
            for (Order order : orders) {
                if (order.getId() == orderId) {
                    return order;
                }
            }
        }
        return null;
    }
}
