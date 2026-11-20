package com.p4r4d0x.hegemonytaxes.model

import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyType
import org.junit.Assert
import org.junit.Test

class PolicyTypeTest {

    @Test
    fun `GIVEN string name WHEN valueOf called THEN return corresponding PolicyType`() {
        // GIVEN, WHEN & THEN
        Assert.assertEquals(PolicyType.FiscalPolicy, PolicyType.valueOf("FiscalPolicy"))
        Assert.assertEquals(PolicyType.LaborMarket, PolicyType.valueOf("LaborMarket"))
        Assert.assertEquals(PolicyType.Taxation, PolicyType.valueOf("Taxation"))
        Assert.assertEquals(PolicyType.WEHealthcare, PolicyType.valueOf("WEHealthcare"))
        Assert.assertEquals(PolicyType.WEEducation, PolicyType.valueOf("WEEducation"))
        Assert.assertEquals(PolicyType.ForeignTrade, PolicyType.valueOf("ForeignTrade"))
        Assert.assertEquals(PolicyType.Immigration, PolicyType.valueOf("Immigration"))
    }

    @Test
    fun `GIVEN PolicyType enum WHEN values called THEN return all enum entries`() {
        // GIVEN, WHEN & THEN
        val allTypes = PolicyType.values()
        Assert.assertEquals(7, allTypes.size)
    }
}
