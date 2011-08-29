package com.shaka.validation

import com.sun.jmx.remote.util.Service;

import grails.test.*

class TextValidationServiceTests extends GrailsUnitTestCase {

    def textValidationService

    protected void setUp() {
        super.setUp()
        textValidationService = new TextValidationService()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testHasTextInHtml() {
        assertFalse(textValidationService.hasTextInHtml(null))

        def htmlText = "<html><body><br /> </body></html>"
        assertFalse(textValidationService.hasTextInHtml(htmlText))

        htmlText = "<p>&nbsp;</p> <p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>"
        assertFalse(textValidationService.hasTextInHtml(htmlText))

        htmlText = "&lt;p&gt;&nbsp;&lt;/p> <p&gt;&nbsp;</p>&lt;p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>"
        assertFalse(textValidationService.hasTextInHtml(htmlText))

        htmlText = "&lt;p&gt;&nbsp;&lt;/p> <p&gt;&nbsp;ola mundo</p>&lt;p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>"
        assertTrue(textValidationService.hasTextInHtml(htmlText))

        htmlText = "<html><body><br /> teste</body></html>"
        assertTrue(textValidationService.hasTextInHtml(htmlText))

        htmlText = "<p>&nbsp;</p> <p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;teste</p>"
        assertTrue(textValidationService.hasTextInHtml(htmlText))
     }

    void testHasJSCodeinHtml(){
        assertFalse(textValidationService.hasJSCodeinHtml(null))

        def htmlText = "<html><body><br /> </body></html> <script > <script />"
        assertTrue(textValidationService.hasJSCodeinHtml(htmlText))

        htmlText = "<html><body><br /> </body></html> <scriPt > <script />"
        assertTrue(textValidationService.hasJSCodeinHtml(htmlText))

        htmlText = "<html><body><br /> </body></html> <scriPt > "
        assertTrue(textValidationService.hasJSCodeinHtml(htmlText))

        htmlText = "<html><body><br /> </body></html> &lt;scriPt > "
        assertTrue(textValidationService.hasJSCodeinHtml(htmlText))

        htmlText = "<html><body><br /> </body></html> "
        assertFalse(textValidationService.hasJSCodeinHtml(htmlText))

    }
}
