package fr.bihar.lecoincoin

import grails.testing.web.interceptor.InterceptorUnitTest
import spock.lang.Specification

class CategoryInterceptorSpec extends Specification implements InterceptorUnitTest<CategoryInterceptor> {

    def setup() {
    }

    def cleanup() {

    }

    void "Test category interceptor matching"() {
        when:"A request matches the interceptor"
            withRequest(controller:"category")

        then:"The interceptor does match"
            interceptor.doesMatch()
    }
}
