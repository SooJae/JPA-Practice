package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

  @Id @GeneratedValue
  @Column(name = "order_id")
  private Long id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "member_id")
  private Member member;


  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<OrderItem> orderItems = new ArrayList<>();

  @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "delivery_id")
  private Delivery delivery;

  private LocalDateTime orderDate; // 주문 시간

  @Enumerated(EnumType.STRING)
  private OrderStatus status; // 주문 상태 [ORDER, CANCEL]


//  == 연관관계 편의 메서드 //

  public void setMember(Member member){
    this.member = member;
    member.getOrders().add(this);
  }

  public void addOrderItem(OrderItem orderItem){
    orderItems.add(orderItem);
    orderItem.setOrder(this);
  }

  public void setDelivery(Delivery delivery){
    this.delivery = delivery;
    delivery.setOrder(this);
  }


//  public static void main(String[] args) {
//    Member member = new Member();
//    Order order = new Order();
//
//    member.getOrders().add(order); // member.getOrders().add(this);로 인해 이 코드는 사실 필요 없어짐
//    order.setMember(member);
//  }
}
