package com.everyparking.data.borrow.domain;

/**
 * @author Taewoo
 */


import com.everyparking.data.car.domain.Car;
import com.everyparking.data.rent.domain.Rent;
import com.everyparking.data.user.domain.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TABLE_BORROW")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "BORROWER_ID")
    private User borrower;

    @OneToOne(fetch = FetchType.EAGER)
    private Rent rent;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    @OneToOne
    @JoinColumn(name = "BORROWER_CAR_ID")
    private Car car;

}
