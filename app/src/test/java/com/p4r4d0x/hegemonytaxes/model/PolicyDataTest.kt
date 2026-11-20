package com.p4r4d0x.hegemonytaxes.model

import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyData
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyState
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyType
import org.junit.Assert
import org.junit.Test

class PolicyDataTest {

    @Test
    fun `GIVEN PolicyData instance WHEN properties, copy and methods called THEN return correct values`() {
        // GIVEN
        val original = PolicyData(number = 1, type = PolicyType.FiscalPolicy, state = PolicyState.A)
        val copy = original.copy(state = PolicyState.B)

        // WHEN & THEN
        Assert.assertEquals(1, original.number)
        Assert.assertEquals(PolicyType.FiscalPolicy, original.type)
        Assert.assertEquals(PolicyState.A, original.state)
        Assert.assertEquals(1, original.component1())
        Assert.assertEquals(PolicyType.FiscalPolicy, original.component2())
        Assert.assertEquals(PolicyState.A, original.component3())
        Assert.assertEquals(PolicyState.B, copy.state)
        Assert.assertEquals(original, original.copy())
        Assert.assertEquals(original.hashCode(), original.copy().hashCode())
        Assert.assertTrue(original.toString().contains("FiscalPolicy"))
    }
}
