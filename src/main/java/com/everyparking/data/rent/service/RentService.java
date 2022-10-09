package com.everyparking.data.rent.service;

/**
 * @author Taewoo
 */


import com.everyparking.api.dto.AddRentDto;
import com.everyparking.api.dto.DefaultResponseDtoEntity;
import com.everyparking.data.place.domain.Place;
import com.everyparking.data.place.repository.PlaceRepository;
import com.everyparking.data.rent.domain.Rent;
import com.everyparking.data.rent.repository.RentRepository;
import com.everyparking.data.user.service.valid.JwtTokenUtils;
import com.everyparking.exception.PlaceNotFoundException;
import com.everyparking.exception.RentTimeInvalidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RentService {

    private final PlaceRepository placeRepository;
    private final RentRepository rentRepository;
    private final JwtTokenUtils jwtTokenUtils;

    @Transactional(readOnly = true)
    public DefaultResponseDtoEntity getMyPlace(String authorization) {

        var user = jwtTokenUtils.getUserByToken(authorization);

        List<Place> places = placeRepository.findPlacesByUser(user);

        return DefaultResponseDtoEntity.ok("성공", places);
    }

    @Transactional
    public DefaultResponseDtoEntity addRent(String authorization, AddRentDto addRentDto) {

        log.info(addRentDto.getEndTime().toString());

        var place = placeRepository.findById(addRentDto.getPlaceId())
                .orElseThrow(PlaceNotFoundException::new);

        var user = jwtTokenUtils.getUserByToken(authorization);

        if (LocalDateTime.now().isAfter(addRentDto.getStartTime())
                || LocalDateTime.now().isAfter(addRentDto.getEndTime())
                || addRentDto.getStartTime().isAfter(addRentDto.getEndTime()))
            throw new RentTimeInvalidException("대여 시간이 정확하지 않습니다.");

        var rent = Rent.dtoToEntity(
                place,
                addRentDto.getCost(),
                addRentDto.getMessage(),
                false,
                addRentDto.getStartTime().minusHours(3).plusSeconds(49),
                addRentDto.getEndTime().minusHours(3).plusSeconds(49)
        );

        place.setBorrow(true);
        placeRepository.save(place);

        return DefaultResponseDtoEntity.of(HttpStatus.CREATED, "렌트 등록 성공", rentRepository.save(rent));
    }
}
