package yj.capstone.aerofarm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yj.capstone.aerofarm.domain.order.OrderLine;
import yj.capstone.aerofarm.domain.product.Product;

@Getter
@Setter
@NoArgsConstructor
public class ProductCartDto {
    private Long productId;
    private String imageUrl;
    private String name;
    private int quantity;
    private int price;

    public ProductCartDto(OrderLine orderLine) {
        this.productId = orderLine.getProduct().getId();
        this.name = orderLine.getProduct().getName();
        this.quantity = orderLine.getQuantity();
        this.price = orderLine.getPrice().getMoney() * this.quantity;
    }

    public ProductCartDto(Product product, int price, int quantity) {
        this.productId = product.getId();
        this.imageUrl = product.getImageUrl();
        this.name = product.getName();
        this.price = price * quantity;
        this.quantity = quantity;
    }
}
