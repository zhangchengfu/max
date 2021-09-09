package com.laozhang.max_interview;

import com.google.zxing.BarcodeFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BarCodeInfo {
    /**
     * 条形码数值
     */
    private String value;

    /**
     * 条形码横向坐标
     */
    private Integer x;

    /**
     * 条形码垂直坐标
     */
    private Integer y;

    /**
     * 条形码宽度
     */
    private Integer width;

    /**
     * 条形码长度
     */
    private Integer height;

    /**
     * 条形码类型
     */
    private BarcodeFormat format = BarcodeFormat.CODE_128;


}
