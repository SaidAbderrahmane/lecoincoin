package fr.bihar.lecoincoin

import grails.testing.web.interceptor.InterceptorUnitTest
import spock.lang.Specification

class SaleAdInterceptorSpec extends Specification implements InterceptorUnitTest<SaleAdInterceptor> {

    def setup() {
    }

    def cleanup() {

    }

    void "Test saleAd interceptor matching"() {
        when:"A request matches the interceptor"
            withRequest(controller:"saleAd")

        then:"The interceptor does match"
            interceptor.doesMatch()
    }
}
