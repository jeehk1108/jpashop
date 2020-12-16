package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;     // 주문 가격
    private int count;          // 주문 수량

    //JPA에서 new OrderItem 에 의한 생성막는방법  protected 설정
    // 참고로 상단 어노테이션 @NoArgsConstructor(access = AccessLevel.PROTECTED) 로 변경 가능
//    protected OrderItem() {
//    }

    //== 생성 메서드 ==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        //주문 생성될때 재고 감소시켜줘야함
        item.removeStock(count);
        return orderItem;
    }


    //== 비지니스 로직 ==//
    /**
     * 재고 다시 원복
     */
    public void cancel() {
        getItem().addStock(count);
    }


    //== 조회 로직 ==//
    /**
     * 주문상품 전체 가격 조회
     * @return
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}