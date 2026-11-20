package com.p4r4d0x.hegemonytaxes.model

import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyState
import org.junit.Assert
import org.junit.Test

class PolicyStateTest {

    @Test
    fun `GIVEN float values WHEN getState THEN return corresponding PolicyState`() {
        // GIVEN, WHEN & THEN
        Assert.assertEquals(PolicyState.A, PolicyState.getState(1f))
        Assert.assertEquals(PolicyState.B, PolicyState.getState(2f))
        Assert.assertEquals(PolicyState.C, PolicyState.getState(3f))
        Assert.assertEquals(PolicyState.Unknown, PolicyState.getState(-1f))
        Assert.assertEquals(PolicyState.Unknown, PolicyState.getState(99f))
    }

    @Test
    fun `GIVEN PolicyState WHEN fromState THEN return corresponding float value`() {
        // GIVEN, WHEN & THEN
        Assert.assertEquals(1f, PolicyState.fromState(PolicyState.A), 0.0f)
        Assert.assertEquals(2f, PolicyState.fromState(PolicyState.B), 0.0f)
        Assert.assertEquals(3f, PolicyState.fromState(PolicyState.C), 0.0f)
        Assert.assertEquals(-1f, PolicyState.fromState(PolicyState.Unknown), 0.0f)
    }
}
