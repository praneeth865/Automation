import CareersPage from "../support/PageObjects/CareersPage.js";
import HomePage from "../support/PageObjects/HomePage.js";
//Visit Dave website and on click of Careers page, verify Read more link exists
describe('Visit Dave Careers Page', () => {
    let data;
    beforeEach(() => {
        // run these tests as if in a desktop
        // browser with a 720p monitor
        cy.viewport(1280, 720)
        //Read Test data role from example.json
            cy.fixture('example').then((testData) => {
            data = testData
        })
    })
    it("Verify Read More Link", function () {
        //Create an object for homePage
        const homePage = new HomePage();
        homePage.navigate();
        homePage.careers();
        //Create an object for Careers Page
        const careersPage = new CareersPage();
        //Iterating through UI elements with job role
        careersPage.getOpenPositions().each(($ele) => {
            //Selecting an element that has job opening specified in example.json
            if ($ele.text() === data.role) {
                expect($ele.text()).equal(data.role);
                cy.get($ele).parent().then(($div) => {
                    //Traversing through DOM elements of job desired role finding an anchor tag
                    cy.get($div).children('.description-wrapper').find('a').then(($link) => {
                        cy.get($link).should('have.attr', 'href');
                        cy.get($link).focus();
                        expect($link.text()).equal('Read more');
                        //This checks the existence of link. Request() calls link as an HTTP request
                        cy.request($link.prop('href'))
                            .its('status')
                            .should('eq', 200);
                    })
                })
                return false;
            }
        })
    })
})
