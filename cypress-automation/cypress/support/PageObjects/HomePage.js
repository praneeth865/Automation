class HomePage {
    //navigate method is used to land on dave website
    navigate() {
        cy.visit('https://dave.com')
    }
    //careers method has ui locator for Careers links on navigation menu
    careers() {
        cy.get('ul').find('>li').filter(':contains("Careers")').first().click()
    }
}
export default HomePage