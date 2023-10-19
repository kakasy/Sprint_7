package api.model;

import java.util.List;

public class OrdersList {
    private List<Order> orders;
    private Page pageInfo;
    private List<Station> availableStations;

    public OrdersList() {}

    public OrdersList(List<Order> orders, Page pageInfo, List<Station> availableStations) {
        this.orders = orders;
        this.pageInfo = pageInfo;
        this.availableStations = availableStations;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Page getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(Page pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<Station> getAvailableStations() {
        return availableStations;
    }

    public void setAvailableStations(List<Station> availableStations) {
        this.availableStations = availableStations;
    }
}
