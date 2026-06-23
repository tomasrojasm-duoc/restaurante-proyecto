package com.order.tbrm.restaurante.order.service.impl;
import com.order.tbrm.restaurante.order.dto.*;
import com.order.tbrm.restaurante.order.model.Order;
import com.order.tbrm.restaurante.order.model.OrderDetail;
import com.order.tbrm.restaurante.order.repository.RestaurantOrderRepository;
import com.order.tbrm.restaurante.order.service.apis.DinerClient;
import com.order.tbrm.restaurante.order.service.apis.MenuClient;
import com.order.tbrm.restaurante.order.service.apis.TableClient;
import com.order.tbrm.restaurante.order.service.apis.WaiterClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private RestaurantOrderRepository repository;

    @Mock
    private DinerClient dinerClient;

    @Mock
    private TableClient tableClient;

    @Mock
    private WaiterClient waiterClient;

    @Mock
    private MenuClient menuClient;

    @InjectMocks
    private OrderServiceImpl service;

    @Test
    void create_shouldCreateOrderWhenAllDataIsValid() {
        OrderDetailRequestDto detailRequest = new OrderDetailRequestDto(
                10L,
                2
        );

        OrderRequestDto request = new OrderRequestDto(
                null,
                1L,
                2L,
                3L,
                "2026-06-22",
                null,
                List.of(detailRequest)
        );

        DinerResponseDto dinerResponse = new DinerResponseDto();
        dinerResponse.setId(1L);

        TableResponseDto tableResponse = new TableResponseDto();
        tableResponse.setId(2L);

        WaiterResponseDto waiterResponse = new WaiterResponseDto();
        waiterResponse.setId(3L);

        MenuItemResponseDto menuItemResponse = new MenuItemResponseDto();
        menuItemResponse.setId(10L);
        menuItemResponse.setName("Pizza");
        menuItemResponse.setPrice(5000);
        menuItemResponse.setStatus("AVAILABLE");

        Order savedOrder = new Order();
        savedOrder.setId(1L);
        savedOrder.setDinerId(1L);
        savedOrder.setTableId(2L);
        savedOrder.setWaiterId(3L);
        savedOrder.setDate("2026-06-22");
        savedOrder.setStatus("CREATED");
        savedOrder.setTotal(10000);

        OrderDetail savedDetail = new OrderDetail();
        savedDetail.setId(1L);
        savedDetail.setMenuItemId(10L);
        savedDetail.setQuantity(2);
        savedDetail.setUnitPrice(5000);
        savedDetail.setSubtotal(10000);
        savedDetail.setOrder(savedOrder);

        savedOrder.setDetails(List.of(savedDetail));

        when(dinerClient.findById(1L)).thenReturn(dinerResponse);
        when(tableClient.findById(2L)).thenReturn(tableResponse);
        when(waiterClient.findById(3L)).thenReturn(waiterResponse);
        when(menuClient.findById(10L)).thenReturn(menuItemResponse);
        when(repository.save(any(Order.class))).thenReturn(savedOrder);

        OrderResponseDto response = service.create(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getDinerId());
        assertEquals(2L, response.getTableId());
        assertEquals(3L, response.getWaiterId());
        assertEquals("2026-06-22", response.getDate());
        assertEquals("CREATED", response.getStatus());
        assertEquals(10000, response.getTotal());

        assertNotNull(response.getDetails());
        assertEquals(1, response.getDetails().size());
        assertEquals(10L, response.getDetails().get(0).getMenuItemId());
        assertEquals(2, response.getDetails().get(0).getQuantity());
        assertEquals(5000, response.getDetails().get(0).getUnitPrice());
        assertEquals(10000, response.getDetails().get(0).getSubtotal());

        verify(dinerClient).findById(1L);
        verify(tableClient).findById(2L);
        verify(waiterClient).findById(3L);
        verify(menuClient).findById(10L);
        verify(repository).save(any(Order.class));
    }

    @Test
    void create_shouldReturnNullWhenDinerDoesNotExist() {
        OrderDetailRequestDto detailRequest = new OrderDetailRequestDto(
                10L,
                2
        );

        OrderRequestDto request = new OrderRequestDto(
                null,
                99L,
                2L,
                3L,
                "2026-06-22",
                null,
                List.of(detailRequest)
        );

        when(dinerClient.findById(99L)).thenReturn(null);

        OrderResponseDto response = service.create(request);

        assertNull(response);

        verify(dinerClient).findById(99L);
        verify(tableClient, never()).findById(anyLong());
        verify(waiterClient, never()).findById(anyLong());
        verify(menuClient, never()).findById(anyLong());
        verify(repository, never()).save(any(Order.class));
    }

    @Test
    void create_shouldReturnNullWhenTableDoesNotExist() {
        OrderDetailRequestDto detailRequest = new OrderDetailRequestDto(
                10L,
                2
        );

        OrderRequestDto request = new OrderRequestDto(
                null,
                1L,
                99L,
                3L,
                "2026-06-22",
                null,
                List.of(detailRequest)
        );

        DinerResponseDto dinerResponse = new DinerResponseDto();
        dinerResponse.setId(1L);

        when(dinerClient.findById(1L)).thenReturn(dinerResponse);
        when(tableClient.findById(99L)).thenReturn(null);

        OrderResponseDto response = service.create(request);

        assertNull(response);

        verify(dinerClient).findById(1L);
        verify(tableClient).findById(99L);
        verify(waiterClient, never()).findById(anyLong());
        verify(menuClient, never()).findById(anyLong());
        verify(repository, never()).save(any(Order.class));
    }

    @Test
    void create_shouldReturnNullWhenWaiterDoesNotExist() {
        OrderDetailRequestDto detailRequest = new OrderDetailRequestDto(
                10L,
                2
        );

        OrderRequestDto request = new OrderRequestDto(
                null,
                1L,
                2L,
                99L,
                "2026-06-22",
                null,
                List.of(detailRequest)
        );

        DinerResponseDto dinerResponse = new DinerResponseDto();
        dinerResponse.setId(1L);

        TableResponseDto tableResponse = new TableResponseDto();
        tableResponse.setId(2L);

        when(dinerClient.findById(1L)).thenReturn(dinerResponse);
        when(tableClient.findById(2L)).thenReturn(tableResponse);
        when(waiterClient.findById(99L)).thenReturn(null);

        OrderResponseDto response = service.create(request);

        assertNull(response);

        verify(dinerClient).findById(1L);
        verify(tableClient).findById(2L);
        verify(waiterClient).findById(99L);
        verify(menuClient, never()).findById(anyLong());
        verify(repository, never()).save(any(Order.class));
    }

    @Test
    void create_shouldReturnNullWhenMenuItemDoesNotExist() {
        OrderDetailRequestDto detailRequest = new OrderDetailRequestDto(
                99L,
                2
        );

        OrderRequestDto request = new OrderRequestDto(
                null,
                1L,
                2L,
                3L,
                "2026-06-22",
                null,
                List.of(detailRequest)
        );

        DinerResponseDto dinerResponse = new DinerResponseDto();
        dinerResponse.setId(1L);

        TableResponseDto tableResponse = new TableResponseDto();
        tableResponse.setId(2L);

        WaiterResponseDto waiterResponse = new WaiterResponseDto();
        waiterResponse.setId(3L);

        when(dinerClient.findById(1L)).thenReturn(dinerResponse);
        when(tableClient.findById(2L)).thenReturn(tableResponse);
        when(waiterClient.findById(3L)).thenReturn(waiterResponse);
        when(menuClient.findById(99L)).thenReturn(null);

        OrderResponseDto response = service.create(request);

        assertNull(response);

        verify(dinerClient).findById(1L);
        verify(tableClient).findById(2L);
        verify(waiterClient).findById(3L);
        verify(menuClient).findById(99L);
        verify(repository, never()).save(any(Order.class));
    }

    @Test
    void create_shouldReturnNullWhenMenuItemIsNotAvailable() {
        OrderDetailRequestDto detailRequest = new OrderDetailRequestDto(
                10L,
                2
        );

        OrderRequestDto request = new OrderRequestDto(
                null,
                1L,
                2L,
                3L,
                "2026-06-22",
                null,
                List.of(detailRequest)
        );

        DinerResponseDto dinerResponse = new DinerResponseDto();
        dinerResponse.setId(1L);

        TableResponseDto tableResponse = new TableResponseDto();
        tableResponse.setId(2L);

        WaiterResponseDto waiterResponse = new WaiterResponseDto();
        waiterResponse.setId(3L);

        MenuItemResponseDto menuItemResponse = new MenuItemResponseDto();
        menuItemResponse.setId(10L);
        menuItemResponse.setName("Pizza");
        menuItemResponse.setPrice(5000);
        menuItemResponse.setStatus("UNAVAILABLE");

        when(dinerClient.findById(1L)).thenReturn(dinerResponse);
        when(tableClient.findById(2L)).thenReturn(tableResponse);
        when(waiterClient.findById(3L)).thenReturn(waiterResponse);
        when(menuClient.findById(10L)).thenReturn(menuItemResponse);

        OrderResponseDto response = service.create(request);

        assertNull(response);

        verify(dinerClient).findById(1L);
        verify(tableClient).findById(2L);
        verify(waiterClient).findById(3L);
        verify(menuClient).findById(10L);
        verify(repository, never()).save(any(Order.class));
    }

    @Test
    void findById_shouldReturnOrderWhenExists() {
        Order order = new Order();
        order.setId(1L);
        order.setDinerId(1L);
        order.setTableId(2L);
        order.setWaiterId(3L);
        order.setDate("2026-06-22");
        order.setStatus("CREATED");
        order.setTotal(10000);

        OrderDetail detail = new OrderDetail();
        detail.setId(1L);
        detail.setMenuItemId(10L);
        detail.setQuantity(2);
        detail.setUnitPrice(5000);
        detail.setSubtotal(10000);
        detail.setOrder(order);

        order.setDetails(List.of(detail));

        when(repository.findById(1L)).thenReturn(Optional.of(order));

        OrderResponseDto response = service.findById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("CREATED", response.getStatus());
        assertEquals(10000, response.getTotal());
        assertEquals(1, response.getDetails().size());

        verify(repository).findById(1L);
    }

    @Test
    void findById_shouldReturnNullWhenOrderDoesNotExist() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        OrderResponseDto response = service.findById(99L);

        assertNull(response);

        verify(repository).findById(99L);
    }

    @Test
    void updateStatus_shouldUpdateStatusWhenOrderExists() {
        Order order = new Order();
        order.setId(1L);
        order.setDinerId(1L);
        order.setTableId(2L);
        order.setWaiterId(3L);
        order.setDate("2026-06-22");
        order.setStatus("CREATED");
        order.setTotal(10000);
        order.setDetails(List.of());

        Order savedOrder = new Order();
        savedOrder.setId(1L);
        savedOrder.setDinerId(1L);
        savedOrder.setTableId(2L);
        savedOrder.setWaiterId(3L);
        savedOrder.setDate("2026-06-22");
        savedOrder.setStatus("DELIVERED");
        savedOrder.setTotal(10000);
        savedOrder.setDetails(List.of());

        when(repository.findById(1L)).thenReturn(Optional.of(order));
        when(repository.save(any(Order.class))).thenReturn(savedOrder);

        OrderResponseDto response = service.updateStatus(1L, "DELIVERED");

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("DELIVERED", response.getStatus());

        verify(repository).findById(1L);
        verify(repository).save(any(Order.class));
    }

    @Test
    void updateStatus_shouldReturnNullWhenOrderDoesNotExist() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        OrderResponseDto response = service.updateStatus(99L, "DELIVERED");

        assertNull(response);

        verify(repository).findById(99L);
        verify(repository, never()).save(any(Order.class));
    }

    @Test
    void delete_shouldReturnTrueWhenOrderExists() {
        Order order = new Order();
        order.setId(1L);
        order.setDinerId(1L);
        order.setTableId(2L);
        order.setWaiterId(3L);
        order.setDate("2026-06-22");
        order.setStatus("CREATED");
        order.setTotal(10000);
        order.setDetails(List.of());

        when(repository.findById(1L)).thenReturn(Optional.of(order));
        doNothing().when(repository).deleteById(1L);

        boolean response = service.delete(1L);

        assertTrue(response);

        verify(repository).findById(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void delete_shouldReturnFalseWhenOrderDoesNotExist() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        boolean response = service.delete(99L);

        assertFalse(response);

        verify(repository).findById(99L);
        verify(repository, never()).deleteById(99L);
    }
}