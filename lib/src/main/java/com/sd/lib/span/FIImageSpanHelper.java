package com.sd.lib.span;

/**
 * Created by zhengjun on 2017/7/10.
 */

public interface FIImageSpanHelper
{
    /**
     * 设置宽度
     *
     * @param width
     */
    void setWidth(int width);

    /**
     * 设置左边间距
     *
     * @param marginLeft
     */
    void setMarginLeft(int marginLeft);

    /**
     * 设置右边间距
     *
     * @param marginRight
     */
    void setMarginRight(int marginRight);

    /**
     * 设置底部边距
     *
     * @param marginBottom
     */
    void setMarginBottom(int marginBottom);

    /**
     * 设置竖直方向对齐方式
     *
     * @param verticalAlignType
     */
    void setVerticalAlignType(VerticalAlignType verticalAlignType);

    enum VerticalAlignType
    {
        /**
         * 对齐字体的底部
         */
        ALIGN_BOTTOM,
        /**
         * 对齐字体的基准线
         */
        ALIGN_BASELINE,
    }
}
