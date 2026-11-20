package com.p4r4d0x.hegemonytaxes.model

import com.p4r4d0x.hegemonytaxes.domain_data.model.CapitalistClassTaxes
import com.p4r4d0x.hegemonytaxes.domain_data.model.MiddleClassTaxes
import com.p4r4d0x.hegemonytaxes.domain_data.model.StateClassTaxes
import com.p4r4d0x.hegemonytaxes.domain_data.model.TaxesNotCalculated
import com.p4r4d0x.hegemonytaxes.domain_data.model.WorkingClassTaxes
import org.junit.Assert
import org.junit.Test

class ResultTaxesTest {

    @Test
    fun `GIVEN TaxesNotCalculated object WHEN accessed THEN verify instance and equality`() {
        // GIVEN & WHEN
        val taxes = TaxesNotCalculated

        // THEN
        Assert.assertNotNull(taxes)
        Assert.assertEquals(TaxesNotCalculated, taxes)
        Assert.assertEquals(TaxesNotCalculated.hashCode(), taxes.hashCode())
        Assert.assertNotNull(taxes.toString())
    }

    @Test
    fun `GIVEN WorkingClassTaxes instance WHEN properties, copy and methods called THEN return correct data`() {
        // GIVEN
        val original = WorkingClassTaxes(incomeTaxResult = 12)
        val copy = original.copy(incomeTaxResult = 12)

        // WHEN & THEN
        Assert.assertEquals(12, original.incomeTaxResult)
        Assert.assertEquals(12, original.component1())
        Assert.assertEquals(original, copy)
        Assert.assertEquals(original.hashCode(), copy.hashCode())
        Assert.assertTrue(original.toString().contains("12"))
    }

    @Test
    fun `GIVEN MiddleClassTaxes instance WHEN properties, copy and methods called THEN return correct data`() {
        // GIVEN
        val original = MiddleClassTaxes(incomeTaxResult = 10, employmentTaxResult = 20, totalTaxes = 30)
        val copy = original.copy()

        // WHEN & THEN
        Assert.assertEquals(10, original.incomeTaxResult)
        Assert.assertEquals(20, original.employmentTaxResult)
        Assert.assertEquals(30, original.totalTaxes)
        Assert.assertEquals(10, original.component1())
        Assert.assertEquals(20, original.component2())
        Assert.assertEquals(30, original.component3())
        Assert.assertEquals(original, copy)
        Assert.assertEquals(original.hashCode(), copy.hashCode())
        Assert.assertTrue(original.toString().contains("30"))
    }

    @Test
    fun `GIVEN CapitalistClassTaxes instance WHEN properties, default arguments, copy and methods called THEN return correct data`() {
        // GIVEN
        val defaultCapitalist = CapitalistClassTaxes(employmentTaxResult = 15, corporateTaxResult = 25, totalTaxes = 40)
        val customCapitalist = CapitalistClassTaxes(employmentTaxResult = 15, corporateTaxResult = 25, reducedFromRevenue = 100, totalTaxes = 40)
        val copy = customCapitalist.copy()

        // WHEN & THEN
        Assert.assertEquals(0, defaultCapitalist.reducedFromRevenue)
        Assert.assertEquals(100, customCapitalist.reducedFromRevenue)
        Assert.assertEquals(15, customCapitalist.employmentTaxResult)
        Assert.assertEquals(25, customCapitalist.corporateTaxResult)
        Assert.assertEquals(40, customCapitalist.totalTaxes)
        Assert.assertEquals(15, customCapitalist.component1())
        Assert.assertEquals(25, customCapitalist.component2())
        Assert.assertEquals(100, customCapitalist.component3())
        Assert.assertEquals(40, customCapitalist.component4())
        Assert.assertEquals(customCapitalist, copy)
        Assert.assertEquals(customCapitalist.hashCode(), copy.hashCode())
        Assert.assertTrue(customCapitalist.toString().contains("100"))
    }

    @Test
    fun `GIVEN StateClassTaxes instance WHEN properties, copy and methods called THEN return correct data`() {
        // GIVEN
        val original = StateClassTaxes(wcTaxes = 10, mcTaxes = 20, ccTaxes = 30, totalTaxes = 60)
        val copy = original.copy()

        // WHEN & THEN
        Assert.assertEquals(10, original.wcTaxes)
        Assert.assertEquals(20, original.mcTaxes)
        Assert.assertEquals(30, original.ccTaxes)
        Assert.assertEquals(60, original.totalTaxes)
        Assert.assertEquals(10, original.component1())
        Assert.assertEquals(20, original.component2())
        Assert.assertEquals(30, original.component3())
        Assert.assertEquals(60, original.component4())
        Assert.assertEquals(original, copy)
        Assert.assertEquals(original.hashCode(), copy.hashCode())
        Assert.assertTrue(original.toString().contains("60"))
    }
}
