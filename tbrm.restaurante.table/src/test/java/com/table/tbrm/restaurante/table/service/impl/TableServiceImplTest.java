package com.table.tbrm.restaurante.table.service.impl;

import com.table.tbrm.restaurante.table.dto.TableRequestDto;
import com.table.tbrm.restaurante.table.dto.TableResponseDto;
import com.table.tbrm.restaurante.table.model.TableRestaurant;
import com.table.tbrm.restaurante.table.repository.TableRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TableServiceImplTest {

    @Mock
    private TableRepository repository;

    @InjectMocks
    private TableServiceImpl service;

    @Test
    void findById_shouldReturnTableWhenExists() {
        TableRestaurant table = new TableRestaurant(
                1L,
                10,
                4,
                "AVAILABLE"
        );

        when(repository.findById(1L)).thenReturn(Optional.of(table));

        TableResponseDto response = service.findById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(10, response.getTableNumber());
        assertEquals(4, response.getCapacity());
        assertEquals("AVAILABLE", response.getStatus());

        verify(repository).findById(1L);
    }

    @Test
    void findById_shouldReturnNullWhenTableDoesNotExist() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        TableResponseDto response = service.findById(99L);

        assertNull(response);

        verify(repository).findById(99L);
    }

    @Test
    void create_shouldCreateTableWhenTableNumberDoesNotExist() {
        TableRequestDto request = new TableRequestDto(
                null,
                15,
                6,
                "AVAILABLE"
        );

        TableRestaurant savedTable = new TableRestaurant(
                1L,
                15,
                6,
                "AVAILABLE"
        );

        when(repository.findByTableNumber(15)).thenReturn(null);
        when(repository.save(any(TableRestaurant.class))).thenReturn(savedTable);

        TableResponseDto response = service.create(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(15, response.getTableNumber());
        assertEquals(6, response.getCapacity());
        assertEquals("AVAILABLE", response.getStatus());

        verify(repository).findByTableNumber(15);
        verify(repository).save(any(TableRestaurant.class));
    }

    @Test
    void create_shouldReturnNullWhenTableNumberAlreadyExists() {
        TableRequestDto request = new TableRequestDto(
                null,
                10,
                4,
                "AVAILABLE"
        );

        TableRestaurant existingTable = new TableRestaurant(
                1L,
                10,
                4,
                "AVAILABLE"
        );

        when(repository.findByTableNumber(10)).thenReturn(existingTable);

        TableResponseDto response = service.create(request);

        assertNull(response);

        verify(repository).findByTableNumber(10);
        verify(repository, never()).save(any(TableRestaurant.class));
    }

    @Test
    void delete_shouldReturnTrueWhenTableExists() {
        TableRestaurant table = new TableRestaurant(
                1L,
                10,
                4,
                "AVAILABLE"
        );

        when(repository.findById(1L)).thenReturn(Optional.of(table));
        doNothing().when(repository).deleteById(1L);

        boolean response = service.delete(1L);

        assertTrue(response);

        verify(repository).findById(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void delete_shouldReturnFalseWhenTableDoesNotExist() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        boolean response = service.delete(99L);

        assertFalse(response);

        verify(repository).findById(99L);
        verify(repository, never()).deleteById(99L);
    }
}