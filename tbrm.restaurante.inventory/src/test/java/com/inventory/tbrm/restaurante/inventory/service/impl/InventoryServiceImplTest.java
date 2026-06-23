package com.inventory.tbrm.restaurante.inventory.service.impl;

import com.inventory.tbrm.restaurante.inventory.dto.InventoryRequestDto;
import com.inventory.tbrm.restaurante.inventory.dto.InventoryResponseDto;
import com.inventory.tbrm.restaurante.inventory.model.Inventory;
import com.inventory.tbrm.restaurante.inventory.repository.InventoryRepository;
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
class InventoryServiceImplTest {

    @Mock
    private InventoryRepository repository;

    @InjectMocks
    private InventoryServiceImpl service;

    @Test
    void findById_shouldReturnInventoryWhenExists() {
        Inventory item = new Inventory(
                1L,
                "Tomate",
                20,
                5,
                "KG",
                "AVAILABLE"
        );

        when(repository.findById(1L)).thenReturn(Optional.of(item));

        InventoryResponseDto response = service.findById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Tomate", response.getName());
        assertEquals(20, response.getStock());
        assertEquals(5, response.getMinimumStock());
        assertEquals("KG", response.getUnit());
        assertEquals("AVAILABLE", response.getStatus());

        verify(repository).findById(1L);
    }

    @Test
    void findById_shouldReturnNullWhenInventoryDoesNotExist() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        InventoryResponseDto response = service.findById(99L);

        assertNull(response);

        verify(repository).findById(99L);
    }

    @Test
    void create_shouldCreateInventoryWithAvailableStatus() {
        InventoryRequestDto request = new InventoryRequestDto(
                null,
                "Palta",
                50,
                10,
                "KG",
                "AVAILABLE"
        );

        Inventory savedItem = new Inventory(
                1L,
                "Palta",
                50,
                10,
                "KG",
                "AVAILABLE"
        );

        when(repository.findByName("Palta")).thenReturn(null);
        when(repository.save(any(Inventory.class))).thenReturn(savedItem);

        InventoryResponseDto response = service.create(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Palta", response.getName());
        assertEquals(50, response.getStock());
        assertEquals(10, response.getMinimumStock());
        assertEquals("KG", response.getUnit());
        assertEquals("AVAILABLE", response.getStatus());

        verify(repository).findByName("Palta");
        verify(repository).save(any(Inventory.class));
    }

    @Test
    void create_shouldCreateInventoryWithLowStockStatus() {
        InventoryRequestDto request = new InventoryRequestDto(
                null,
                "Queso",
                3,
                5,
                "KG",
                "AVAILABLE"
        );

        Inventory savedItem = new Inventory(
                1L,
                "Queso",
                3,
                5,
                "KG",
                "LOW_STOCK"
        );

        when(repository.findByName("Queso")).thenReturn(null);
        when(repository.save(any(Inventory.class))).thenReturn(savedItem);

        InventoryResponseDto response = service.create(request);

        assertNotNull(response);
        assertEquals("Queso", response.getName());
        assertEquals(3, response.getStock());
        assertEquals(5, response.getMinimumStock());
        assertEquals("LOW_STOCK", response.getStatus());

        verify(repository).findByName("Queso");
        verify(repository).save(any(Inventory.class));
    }

    @Test
    void create_shouldCreateInventoryWithOutOfStockStatus() {
        InventoryRequestDto request = new InventoryRequestDto(
                null,
                "Pan",
                0,
                5,
                "UN",
                "AVAILABLE"
        );

        Inventory savedItem = new Inventory(
                1L,
                "Pan",
                0,
                5,
                "UN",
                "OUT_OF_STOCK"
        );

        when(repository.findByName("Pan")).thenReturn(null);
        when(repository.save(any(Inventory.class))).thenReturn(savedItem);

        InventoryResponseDto response = service.create(request);

        assertNotNull(response);
        assertEquals("Pan", response.getName());
        assertEquals(0, response.getStock());
        assertEquals(5, response.getMinimumStock());
        assertEquals("OUT_OF_STOCK", response.getStatus());

        verify(repository).findByName("Pan");
        verify(repository).save(any(Inventory.class));
    }

    @Test
    void create_shouldReturnNullWhenInventoryNameAlreadyExists() {
        InventoryRequestDto request = new InventoryRequestDto(
                null,
                "Tomate",
                20,
                5,
                "KG",
                "AVAILABLE"
        );

        Inventory existingItem = new Inventory(
                1L,
                "Tomate",
                20,
                5,
                "KG",
                "AVAILABLE"
        );

        when(repository.findByName("Tomate")).thenReturn(existingItem);

        InventoryResponseDto response = service.create(request);

        assertNull(response);

        verify(repository).findByName("Tomate");
        verify(repository, never()).save(any(Inventory.class));
    }

    @Test
    void updateStock_shouldUpdateStockAndSetLowStockStatus() {
        Inventory item = new Inventory(
                1L,
                "Lechuga",
                20,
                5,
                "UN",
                "AVAILABLE"
        );

        Inventory savedItem = new Inventory(
                1L,
                "Lechuga",
                3,
                5,
                "UN",
                "LOW_STOCK"
        );

        when(repository.findById(1L)).thenReturn(Optional.of(item));
        when(repository.save(any(Inventory.class))).thenReturn(savedItem);

        InventoryResponseDto response = service.updateStock(1L, 3);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Lechuga", response.getName());
        assertEquals(3, response.getStock());
        assertEquals(5, response.getMinimumStock());
        assertEquals("LOW_STOCK", response.getStatus());

        verify(repository).findById(1L);
        verify(repository).save(any(Inventory.class));
    }

    @Test
    void updateStock_shouldReturnNullWhenInventoryDoesNotExist() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        InventoryResponseDto response = service.updateStock(99L, 10);

        assertNull(response);

        verify(repository).findById(99L);
        verify(repository, never()).save(any(Inventory.class));
    }

    @Test
    void findLowStock_shouldReturnOnlyLowStockItems() {
        Inventory lowStockItem = new Inventory(
                1L,
                "Queso",
                3,
                5,
                "KG",
                "LOW_STOCK"
        );

        Inventory availableItem = new Inventory(
                2L,
                "Palta",
                50,
                10,
                "KG",
                "AVAILABLE"
        );

        Inventory outOfStockItem = new Inventory(
                3L,
                "Pan",
                0,
                5,
                "UN",
                "OUT_OF_STOCK"
        );

        when(repository.findAll()).thenReturn(List.of(lowStockItem, availableItem, outOfStockItem));

        List<InventoryResponseDto> response = service.findLowStock();

        assertNotNull(response);
        assertEquals(2, response.size());

        assertEquals("Queso", response.get(0).getName());
        assertEquals("Pan", response.get(1).getName());

        verify(repository).findAll();
    }

    @Test
    void delete_shouldReturnTrueWhenInventoryExists() {
        Inventory item = new Inventory(
                1L,
                "Tomate",
                20,
                5,
                "KG",
                "AVAILABLE"
        );

        when(repository.findById(1L)).thenReturn(Optional.of(item));
        doNothing().when(repository).deleteById(1L);

        boolean response = service.delete(1L);

        assertTrue(response);

        verify(repository).findById(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void delete_shouldReturnFalseWhenInventoryDoesNotExist() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        boolean response = service.delete(99L);

        assertFalse(response);

        verify(repository).findById(99L);
        verify(repository, never()).deleteById(99L);
    }
}