// @ts-check
const { test: base } = require('@playwright/test');
const { LoginPage, TicketsPage, TicketDetailsPage, OrdersPage } = require('../pages');

/**
 * Extended test fixtures with page objects
 */
const test = base.extend({
  // Page Objects as fixtures
  loginPage: async ({ page }, use) => {
    const loginPage = new LoginPage(page);
    await use(loginPage);
  },

  ticketsPage: async ({ page }, use) => {
    const ticketsPage = new TicketsPage(page);
    await use(ticketsPage);
  },

  ticketDetailsPage: async ({ page }, use) => {
    const ticketDetailsPage = new TicketDetailsPage(page);
    await use(ticketDetailsPage);
  },

  ordersPage: async ({ page }, use) => {
    const ordersPage = new OrdersPage(page);
    await use(ordersPage);
  },

  // Authenticated page fixture - logs in before each test
  authenticatedPage: async ({ page }, use) => {
    const loginPage = new LoginPage(page);
    await loginPage.goto();
    await loginPage.loginWithTestUser();
    await use(page);
  },

  // Authenticated with tickets page ready
  authenticatedTicketsPage: async ({ page }, use) => {
    const loginPage = new LoginPage(page);
    const ticketsPage = new TicketsPage(page);

    await loginPage.goto();
    await loginPage.loginWithTestUser();
    await ticketsPage.gotoAssignedToMe();

    await use(ticketsPage);
  }
});

const { expect } = require('@playwright/test');

module.exports = { test, expect };
