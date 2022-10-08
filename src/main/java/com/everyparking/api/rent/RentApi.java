package com.everyparking.api.rent;

/**
 * @author Taewoo
 */


import com.everyparking.api.dto.AddRentDto;
import com.everyparking.data.rent.service.RentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/rent")
@RequiredArgsConstructor
@Slf4j
public class RentApi {

    private final RentService rentService;

    @GetMapping("")
    public ResponseEntity<?> getAllRent() {
        return null;
    }

    @PostMapping("")
    public ResponseEntity<?> addRent(
            @RequestHeader String authorization,
            @Valid @RequestBody AddRentDto addRentDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(rentService.addRent(authorization, addRentDto));
    }
}














