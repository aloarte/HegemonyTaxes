package com.p4r4d0x.hegemonytaxes.model

import com.p4r4d0x.hegemonytaxes.domain_data.model.CapitalistClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.MiddleClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.StateClassInputs
import com.p4r4d0x.hegemonytaxes.domain_data.model.WorkingClassInputs
import org.junit.Assert
import org.junit.Test

class RoleInputsTest {

    @Test
    fun `GIVEN WorkingClassInputs WHEN properties, copy and methods called THEN return correct data`() {
        // GIVEN
        val original = WorkingClassInputs(population = 5)
        val copy = original.copy()

        // WHEN & THEN
        Assert.assertEquals(5, original.population)
        Assert.assertEquals(5, original.component1())
        Assert.assertEquals(original, copy)
        Assert.assertEquals(original.hashCode(), copy.hashCode())
        Assert.assertTrue(original.toString().contains("5"))
    }

    @Test
    fun `GIVEN MiddleClassInputs WHEN properties, copy and methods called THEN return correct data`() {
        // GIVEN
        val original = MiddleClassInputs(externalCompaniesWithWorkers = 2, ownCompanies = 3)
        val copy = original.copy()

        // WHEN & THEN
        Assert.assertEquals(2, original.externalCompaniesWithWorkers)
        Assert.assertEquals(3, original.ownCompanies)
        Assert.assertEquals(2, original.component1())
        Assert.assertEquals(3, original.component2())
        Assert.assertEquals(original, copy)
        Assert.assertEquals(original.hashCode(), copy.hashCode())
    }

    @Test
    fun `GIVEN CapitalistClassInputs WHEN properties, copy and methods called THEN return correct data`() {
        // GIVEN
        val original = CapitalistClassInputs(companies = 4, profit = 150)
        val copy = original.copy()

        // WHEN & THEN
        Assert.assertEquals(4, original.companies)
        Assert.assertEquals(150, original.profit)
        Assert.assertEquals(4, original.component1())
        Assert.assertEquals(150, original.component2())
        Assert.assertEquals(original, copy)
        Assert.assertEquals(original.hashCode(), copy.hashCode())
    }

    @Test
    fun `GIVEN StateClassInputs WHEN properties, copy and methods called THEN return correct data`() {
        // GIVEN
        val original = StateClassInputs(
            wcPopulation = 1,
            mcExternalCompaniesWithWorkers = 2,
            mcOwnCompanies = 3,
            ccCompanies = 4,
            ccProfit = 50
        )
        val copy = original.copy()

        // WHEN & THEN
        Assert.assertEquals(1, original.wcPopulation)
        Assert.assertEquals(2, original.mcExternalCompaniesWithWorkers)
        Assert.assertEquals(3, original.mcOwnCompanies)
        Assert.assertEquals(4, original.ccCompanies)
        Assert.assertEquals(50, original.ccProfit)
        Assert.assertEquals(1, original.component1())
        Assert.assertEquals(2, original.component2())
        Assert.assertEquals(3, original.component3())
        Assert.assertEquals(4, original.component4())
        Assert.assertEquals(50, original.component5())
        Assert.assertEquals(original, copy)
        Assert.assertEquals(original.hashCode(), copy.hashCode())
    }
}
