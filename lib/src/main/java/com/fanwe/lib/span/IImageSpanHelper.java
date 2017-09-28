/*
 * Copyright (C) 2017 zhengjun, fanwe (http://www.fanwe.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fanwe.lib.span;

/**
 * Created by Administrator on 2017/7/10.
 */

public interface IImageSpanHelper
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
