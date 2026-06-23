package com.reservation.tbrm.restaurante.reservation.service.impl;

import com.reservation.tbrm.restaurante.reservation.dto.DinerResponseDto;
import com.reservation.tbrm.restaurante.reservation.dto.ReservationRequestDto;
import com.reservation.tbrm.restaurante.reservation.dto.ReservationResponseDto;
import com.reservation.tbrm.restaurante.reservation.dto.TableResponseDto;
import com.reservation.tbrm.restaurante.reservation.model.Reservation;
import com.reservation.tbrm.restaurante.reservation.repository.ReservationRepository;
import com.reservation.tbrm.restaurante.reservation.service.apis.DinerClient;
import com.reservation.tbrm.restaurante.reservation.service.apis.TableClient;
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
class ReservationServiceImplTest {

    @Mock
    private ReservationRepository repository;

    @Mock
    private DinerClient dinerClient;

    @Mock
    private TableClient tableClient;

    @InjectMocks
    private ReservationServiceImpl service;

    @Test
    void findById_shouldReturnReservationWhenExists() {
        Reservation reservation = new Reservation(
                1L,
                1L,
                2L,
                "2026-06-22"
        );

        when(repository.findById(1L)).thenReturn(Optional.of(reservation));

        ReservationResponseDto response = service.findById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getDinerId());
        assertEquals(2L, response.getTableId());
        assertEquals("2026-06-22", response.getDate());

        verify(repository).findById(1L);
    }

    @Test
    void findById_shouldReturnNullWhenReservationDoesNotExist() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        ReservationResponseDto response = service.findById(99L);

        assertNull(response);

        verify(repository).findById(99L);
    }

    @Test
    void create_shouldCreateReservationWhenDinerAndTableExist() {
        ReservationRequestDto request = new ReservationRequestDto(
                null,
                1L,
                2L,
                "2026-06-22"
        );

        Reservation savedReservation = new Reservation(
                1L,
                1L,
                2L,
                "2026-06-22"
        );

        when(dinerClient.findById(1L)).thenReturn(mock(DinerResponseDto.class));
        when(tableClient.findById(2L)).thenReturn(mock(TableResponseDto.class));
        when(repository.save(any(Reservation.class))).thenReturn(savedReservation);

        ReservationResponseDto response = service.create(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getDinerId());
        assertEquals(2L, response.getTableId());
        assertEquals("2026-06-22", response.getDate());

        verify(dinerClient).findById(1L);
        verify(tableClient).findById(2L);
        verify(repository).save(any(Reservation.class));
    }

    @Test
    void create_shouldReturnNullWhenDinerDoesNotExist() {
        ReservationRequestDto request = new ReservationRequestDto(
                null,
                99L,
                2L,
                "2026-06-22"
        );

        when(dinerClient.findById(99L)).thenReturn(null);

        ReservationResponseDto response = service.create(request);

        assertNull(response);

        verify(dinerClient).findById(99L);
        verify(tableClient, never()).findById(anyLong());
        verify(repository, never()).save(any(Reservation.class));
    }

    @Test
    void create_shouldReturnNullWhenTableDoesNotExist() {
        ReservationRequestDto request = new ReservationRequestDto(
                null,
                1L,
                99L,
                "2026-06-22"
        );

        when(dinerClient.findById(1L)).thenReturn(mock(DinerResponseDto.class));
        when(tableClient.findById(99L)).thenReturn(null);

        ReservationResponseDto response = service.create(request);

        assertNull(response);

        verify(dinerClient).findById(1L);
        verify(tableClient).findById(99L);
        verify(repository, never()).save(any(Reservation.class));
    }

    @Test
    void findByDinerId_shouldReturnReservationsWhenDinerExists() {
        Reservation reservationOne = new Reservation(
                1L,
                1L,
                2L,
                "2026-06-22"
        );

        Reservation reservationTwo = new Reservation(
                2L,
                1L,
                3L,
                "2026-06-23"
        );

        when(dinerClient.findById(1L)).thenReturn(mock(DinerResponseDto.class));
        when(repository.findByDinerId(1L)).thenReturn(List.of(reservationOne, reservationTwo));

        List<ReservationResponseDto> response = service.findByDinerId(1L);

        assertNotNull(response);
        assertEquals(2, response.size());

        assertEquals(1L, response.get(0).getDinerId());
        assertEquals(1L, response.get(1).getDinerId());

        verify(dinerClient).findById(1L);
        verify(repository).findByDinerId(1L);
    }

    @Test
    void findByDinerId_shouldReturnNullWhenDinerDoesNotExist() {
        when(dinerClient.findById(99L)).thenReturn(null);

        List<ReservationResponseDto> response = service.findByDinerId(99L);

        assertNull(response);

        verify(dinerClient).findById(99L);
        verify(repository, never()).findByDinerId(anyLong());
    }

    @Test
    void findByTableId_shouldReturnReservationsWhenTableExists() {
        Reservation reservationOne = new Reservation(
                1L,
                1L,
                2L,
                "2026-06-22"
        );

        Reservation reservationTwo = new Reservation(
                2L,
                3L,
                2L,
                "2026-06-23"
        );

        when(tableClient.findById(2L)).thenReturn(mock(TableResponseDto.class));
        when(repository.findByTableId(2L)).thenReturn(List.of(reservationOne, reservationTwo));

        List<ReservationResponseDto> response = service.findByTableId(2L);

        assertNotNull(response);
        assertEquals(2, response.size());

        assertEquals(2L, response.get(0).getTableId());
        assertEquals(2L, response.get(1).getTableId());

        verify(tableClient).findById(2L);
        verify(repository).findByTableId(2L);
    }

    @Test
    void findByTableId_shouldReturnNullWhenTableDoesNotExist() {
        when(tableClient.findById(99L)).thenReturn(null);

        List<ReservationResponseDto> response = service.findByTableId(99L);

        assertNull(response);

        verify(tableClient).findById(99L);
        verify(repository, never()).findByTableId(anyLong());
    }

    @Test
    void update_shouldUpdateReservationWhenExists() {
        ReservationRequestDto request = new ReservationRequestDto(
                1L,
                1L,
                2L,
                "2026-06-24"
        );

        Reservation existingReservation = new Reservation(
                1L,
                1L,
                2L,
                "2026-06-22"
        );

        Reservation updatedReservation = new Reservation(
                1L,
                1L,
                2L,
                "2026-06-24"
        );

        when(repository.findById(1L)).thenReturn(Optional.of(existingReservation));
        when(repository.save(any(Reservation.class))).thenReturn(updatedReservation);

        ReservationResponseDto response = service.update(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("2026-06-24", response.getDate());

        verify(repository).findById(1L);
        verify(repository).save(any(Reservation.class));
    }

    @Test
    void update_shouldReturnNullWhenIdIsNull() {
        ReservationRequestDto request = new ReservationRequestDto(
                null,
                1L,
                2L,
                "2026-06-24"
        );

        ReservationResponseDto response = service.update(request);

        assertNull(response);

        verify(repository, never()).findById(anyLong());
        verify(repository, never()).save(any(Reservation.class));
    }

    @Test
    void update_shouldReturnNullWhenReservationDoesNotExist() {
        ReservationRequestDto request = new ReservationRequestDto(
                99L,
                1L,
                2L,
                "2026-06-24"
        );

        when(repository.findById(99L)).thenReturn(Optional.empty());

        ReservationResponseDto response = service.update(request);

        assertNull(response);

        verify(repository).findById(99L);
        verify(repository, never()).save(any(Reservation.class));
    }

    @Test
    void delete_shouldReturnTrueWhenReservationExists() {
        Reservation reservation = new Reservation(
                1L,
                1L,
                2L,
                "2026-06-22"
        );

        when(repository.findById(1L)).thenReturn(Optional.of(reservation));
        doNothing().when(repository).deleteById(1L);

        boolean response = service.delete(1L);

        assertTrue(response);

        verify(repository).findById(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void delete_shouldReturnFalseWhenReservationDoesNotExist() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        boolean response = service.delete(99L);

        assertFalse(response);

        verify(repository).findById(99L);
        verify(repository, never()).deleteById(99L);
    }
}