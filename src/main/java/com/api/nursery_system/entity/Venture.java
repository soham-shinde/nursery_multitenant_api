package com.api.nursery_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "venture_detail_table")
public class Venture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "venture_name")
    private String ventureName;

    private String address;
    private String village;
    private String taluka;
    private String district;
    private String state;

    @Column(name = "gstin_no")
    private String gstinNo;

    @Column(name = "gst_per")
    private Double gstPer;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "account_holder_name")
    private String accountHolderName;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "upi_id")
    private String upiId;

    @Column(name = "user_id")
    private Long userId;
}
