package yj.capstone.aerofarm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import yj.capstone.aerofarm.controller.dto.UserDetailsImpl;
import yj.capstone.aerofarm.controller.form.OrderForm;
import yj.capstone.aerofarm.service.OrderService;


@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @ResponseBody
    @PostMapping("/order/createOrder")
    @PreAuthorize("hasAnyAuthority('GUEST')")
    public Long createOrder(@RequestBody OrderForm orderForm, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return orderService.createOrder(userDetails.getMember(), orderForm).getId();
    }

    @GetMapping("/order/create")
    @PreAuthorize("hasAnyAuthority('GUEST')")
    public String createOrder() {
        return "/order/orderPage";
    }
    /*@ResponseBody
    @GetMapping("/order/create")
    @PreAuthorize("hasAnyAuthority('GUEST')")
    public OrderForm createOrder() {
        OrderForm orderForm = new OrderForm();
        orderForm.setReceiver("QQC");
        orderForm.setAddressInfo(new AddressInfo("seoul", "gangnam", "12345"));
        orderForm.setPaymentType(PaymentType.CREDIT_CARD);

        List<OrderLineDto> orderLineDtoList = new ArrayList<>();
        orderLineDtoList.add(new OrderLineDto(new ProductDto(1L,"Apple", 1000), 5, 5000));
        orderLineDtoList.add(new OrderLineDto(new ProductDto(1L,"Pear", 3000), 3, 9000));
        orderLineDtoList.add(new OrderLineDto(new ProductDto(1L,"Banana", 1000), 1, 1000));

        orderForm.setOrderLineDtos(orderLineDtoList);
        return orderForm;
    }*/
}
