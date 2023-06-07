class CareersPage {
    //getOpenPositions return UI locator for Job title
    getOpenPositions(){
        return cy.get('.title-wrapper-container-holder')
    }

}
export default CareersPage