package ru.diasoft.micro.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Table(name = "sms_verification")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmsVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sms_verification_seq")
    @SequenceGenerator(name = "sms_verification_seq", sequenceName = "sms_verification_seq", allocationSize = 1)
    @Column(name = "verificationid")
    private Long verificationId;

    @Column(name = "processguid")
    private String processGuid;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "secretcode")
    private String secretCode;

    @Column(name = "status")
    private String status;
}

