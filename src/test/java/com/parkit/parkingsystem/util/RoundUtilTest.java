package com.parkit.parkingsystem.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoundUtilTest {

    @Test
    void threeDigitRenderLow() {
        assertEquals(0.333, RoundUtil.threeDigitRender(0.33333333333));
    }

    @Test
    void threeDigitRenderHigh() {
        assertEquals(1.0, RoundUtil.threeDigitRender(0.9999999999));
    }
}