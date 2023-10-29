package com.p4r4d0x.hegemonytaxes.datasource

import com.p4r4d0x.hegemonytaxes.domain_data.datasource.PoliciesDatasource
import com.p4r4d0x.hegemonytaxes.domain_data.datasource.impl.PoliciesDatasourceImpl
import com.p4r4d0x.hegemonytaxes.domain_data.exceptions.TaxException
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyState
import com.p4r4d0x.hegemonytaxes.domain_data.model.PolicyType
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.BASE_TAX_INCREMENT_A
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.BASE_TAX_INCREMENT_B
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.BASE_TAX_INCREMENT_C
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.WELFARE_TAX_INCREMENT_A
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.WELFARE_TAX_INCREMENT_B
import com.p4r4d0x.hegemonytaxes.domain_data.utils.Constants.WELFARE_TAX_INCREMENT_C
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class PoliciesDatasourceTest {

    private lateinit var datasource: PoliciesDatasource

    @Before
    fun setUp() {
        datasource = PoliciesDatasourceImpl()
    }

    @Test
    fun `test get policies data`() {
        val listResult = datasource.getPoliciesData()

        Assert.assertTrue(listResult.any { it.type == PolicyType.FiscalPolicy })
        Assert.assertTrue(listResult.any { it.type == PolicyType.LaborMarket })
        Assert.assertTrue(listResult.any { it.type == PolicyType.Taxation })
        Assert.assertTrue(listResult.any { it.type == PolicyType.WEHealthcare })
        Assert.assertTrue(listResult.any { it.type == PolicyType.WEEducation })
        Assert.assertTrue(listResult.any { it.type == PolicyType.ForeignTrade })
        Assert.assertTrue(listResult.any { it.type == PolicyType.Immigration })
    }

    @Test
    fun `test get base tax increment`() {
        Assert.assertEquals(BASE_TAX_INCREMENT_A, datasource.getBaseTaxIncrement(PolicyState.A))
        Assert.assertEquals(BASE_TAX_INCREMENT_B, datasource.getBaseTaxIncrement(PolicyState.B))
        Assert.assertEquals(BASE_TAX_INCREMENT_C, datasource.getBaseTaxIncrement(PolicyState.C))
        Assert.assertThrows(TaxException::class.java) {
            datasource.getBaseTaxIncrement(PolicyState.Unknown)
        }
    }

    @Test
    fun `test get welfare tax increment`() {
        Assert.assertEquals(WELFARE_TAX_INCREMENT_A, datasource.getWelfareIncrement(PolicyState.A))
        Assert.assertEquals(WELFARE_TAX_INCREMENT_B, datasource.getWelfareIncrement(PolicyState.B))
        Assert.assertEquals(WELFARE_TAX_INCREMENT_C, datasource.getWelfareIncrement(PolicyState.C))
        Assert.assertThrows(TaxException::class.java) {
            datasource.getWelfareIncrement(PolicyState.Unknown)
        }
    }

}