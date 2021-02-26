package org.ucsccaa.mms.services;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.ucsccaa.mms.MembershipManagementSystemApplication;
import org.ucsccaa.mms.domains.Member;
import org.ucsccaa.mms.domains.Orders;
import org.ucsccaa.mms.repositories.MemberRepository;
import org.ucsccaa.mms.repositories.OrderRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MembershipManagementSystemApplication.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private OrderService service;

    @Test
    public void createOrderTest() {
        Orders expectedOrder = new Orders();
        expectedOrder.setId((long)1);
        Member member = new Member();
        member.setId((long)1);
        expectedOrder.setMember(member);
        when(memberRepository.existsById(anyLong())).thenReturn(true);
        when(orderRepository.save(any())).thenReturn(expectedOrder);
        Long id = service.createOrder(expectedOrder);
        assertEquals(expectedOrder.getId(), id);
    }

    @Test(expected = RuntimeException.class)
    public void createOrderMemberIdNotExistsTest() {
        Orders order = new Orders();
        Member member = new Member();
        member.setId((long)1);
        order.setMember(member);
        service.createOrder(order);
    }

    @Test(expected = RuntimeException.class)
    public void createOrderMemberIdNullTest() {
        Orders order = new Orders();
        order.setMember(new Member());
        service.createOrder(order);
    }

    @Test
    public void updateOrderTest() {
        Orders expectedOrder = new Orders();
        expectedOrder.setId((long)1);
        expectedOrder.setStatus("shipped");
        Member member = new Member();
        member.setId((long)1);
        expectedOrder.setMember(member);

        when(orderRepository.existsById(anyLong())).thenReturn(true);
        when(memberRepository.existsById(anyLong())).thenReturn(true);
        when(orderRepository.save(any())).thenReturn(expectedOrder);
        Orders order = service.updateOrder(expectedOrder);
        assertNotNull(order);
        assertEquals(expectedOrder.getId(), order.getId());
        assertEquals(expectedOrder.getStatus(), order.getStatus());
    }

    @Test
    public void getOrderByIdTest() {
        Orders expectOrder = new Orders();
        expectOrder.setId((long)1);

        when(orderRepository.findById(expectOrder.getId())).thenReturn(Optional.of(expectOrder));
        Orders item = service.getOrderById(expectOrder.getId());
        assertEquals(expectOrder.getId(), item.getId());
    }

    @Test(expected = RuntimeException.class)
    public void getOrderByIdNullTest() {
        service.getOrderById(null);
    }

    @Test
    public void deleteByIdTest() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(new Orders()));
        boolean result = service.deleteById((long)1);
        assertEquals(true, result);
    }

    @Test
    public void deleteByIdNotFoundTest() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
        boolean result = service.deleteById((long)1);
        assertEquals(false, result);
    }

    @Test(expected = RuntimeException.class)
    public void deleteByIdNullTest() {
        service.deleteById(null);
    }
}
