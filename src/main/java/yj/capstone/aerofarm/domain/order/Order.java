package yj.capstone.aerofarm.domain.order;

import lombok.*;
import yj.capstone.aerofarm.controller.form.OrderForm;
import yj.capstone.aerofarm.domain.AddressInfo;
import yj.capstone.aerofarm.domain.BaseEntity;
import yj.capstone.aerofarm.domain.member.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String receiver;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderLine> orderLines = new ArrayList<>();

    @Embedded
    private Money totalPrice;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member orderer;

    // TODO 추후 컬럼 이름 바꿀 수 있으면 바꾸기
    @Embedded
    private AddressInfo addressInfo;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    protected Order(){} // NoArgsConstructor

    // TODO 대충 만든 임시 빌더 추후 검토 필요
    @Builder(builderClassName = "OrderBuilder", builderMethodName = "orderBuilder")
    public Order(OrderForm orderForm, Member orderer) {
        this.receiver = orderForm.getReceiver();
        this.paymentType = orderForm.getPaymentType();
        this.addressInfo = orderForm.getAddressInfo();
        this.orderer = orderer;
        if (this.paymentType == PaymentType.MOOTONGJANG) {
            this.deliveryStatus = DeliveryStatus.PAYMENT_WAITING;
        } else {
            this.deliveryStatus = DeliveryStatus.PAYMENT_OK;
        }
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        for (OrderLine orderLine : orderLines) {
            orderLine.setOrder(this);
        }
        this.orderLines.addAll(orderLines);
        calculateTotalPrice();
    }

    private void calculateTotalPrice() {
        int sum = orderLines.stream()
                .mapToInt(OrderLine::getOrderPrice)
                .sum();
        this.totalPrice = new Money(sum);
    }

    public DeliveryStatus cancel() {
        DeliveryStatus deliveryStatus = this.deliveryStatus;

        if (this.deliveryStatus == DeliveryStatus.PAYMENT_WAITING || this.deliveryStatus == DeliveryStatus.PAYMENT_OK) {
            this.deliveryStatus = DeliveryStatus.CANCELED;
            return deliveryStatus;
        } else {
            throw new IllegalArgumentException("배송 시작 후에는 취소가 불가능 합니다."); // TODO 예외 이름 변경
        }
    }
}
