package com.p4r4d0x.hegemonytaxes.model

import com.p4r4d0x.hegemonytaxes.domain_data.model.HegemonyRole
import org.junit.Assert
import org.junit.Test

class HegemonyRoleTest {

    @Test
    fun `GIVEN HegemonyRole entries WHEN value property accessed THEN return correct string value`() {
        // GIVEN, WHEN & THEN
        Assert.assertEquals("Working class", HegemonyRole.WorkingClass.value)
        Assert.assertEquals("Middle class", HegemonyRole.MiddleClass.value)
        Assert.assertEquals("Capitalist class", HegemonyRole.CapitalistClass.value)
        Assert.assertEquals("State", HegemonyRole.State.value)
    }

    @Test
    fun `GIVEN string name WHEN valueOf called THEN return corresponding HegemonyRole`() {
        // GIVEN, WHEN & THEN
        Assert.assertEquals(HegemonyRole.WorkingClass, HegemonyRole.valueOf("WorkingClass"))
        Assert.assertEquals(HegemonyRole.MiddleClass, HegemonyRole.valueOf("MiddleClass"))
        Assert.assertEquals(HegemonyRole.CapitalistClass, HegemonyRole.valueOf("CapitalistClass"))
        Assert.assertEquals(HegemonyRole.State, HegemonyRole.valueOf("State"))
    }

    @Test
    fun `GIVEN HegemonyRole enum WHEN values called THEN return all enum entries`() {
        // GIVEN, WHEN & THEN
        val allRoles = HegemonyRole.values()
        Assert.assertEquals(4, allRoles.size)
        Assert.assertTrue(allRoles.contains(HegemonyRole.WorkingClass))
        Assert.assertTrue(allRoles.contains(HegemonyRole.MiddleClass))
        Assert.assertTrue(allRoles.contains(HegemonyRole.CapitalistClass))
        Assert.assertTrue(allRoles.contains(HegemonyRole.State))
    }
}
