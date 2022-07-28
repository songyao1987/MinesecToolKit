package com.minesec.android.toolkit.receipt;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @author eric.song
 * @since 2022/7/28 9:59
 */
@Getter
@Builder
@ToString
public class PrintingCheck {
    private String receiptContent;

    private List<String> printLines;

    private CheckDetail detail;

    @Getter
    @Builder
    @ToString
    public static class CheckDetail {

        private String tableNo;

        private String checkNo;

        private String checkOpenEmpId;

        private String checkOpenTime;

        private String checkCloseTime;

        private String checkOpenStationCode;

        private Integer guests;

        private BigDecimal totalAmt;

        private BigDecimal svcTotalAmt;

        private BigDecimal surchargeAmt;

        private BigDecimal amt;

        private BigDecimal paidTotalAmt;

        private PrintingRvc rvcInfo;

        private List<PrintingItem> items;

        private List<PrintingTax> taxes;

        private List<PrintingPayment> payments;

    }

    @Getter
    @Builder
    @ToString
    public static class PrintingRvc {

        private String posMerId;

        private String posMerName;

        private String posRvcId;

        private String posRvcName;

        private String address1;

        private String info;

        private String phone;

        private String fax;

        private String website;

    }

    @Getter
    @Builder
    @ToString
    public static class PrintingItem {

        private String name;

        private Double qty;

        private BigDecimal price;

        private BigDecimal amount;

    }

    @Getter
    @Builder
    @ToString
    public static class PrintingTax {

        private String name;

        private BigDecimal amount;

    }

    @Getter
    @Builder
    @ToString
    public static class PrintingPayment {

        private String name;

        private BigDecimal totalAmt;

        private BigDecimal txnAmt;

        private BigDecimal tipAmt;

        private BigDecimal surchargeAmt;

    }

}
