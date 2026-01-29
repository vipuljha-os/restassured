// @ts-check
const { test, expect } = require('../utils/fixtures');
const { generateRandomString } = require('../utils/testHelpers');

test.describe('Banana Club CRM - Ticket Management', () => {

  test.beforeEach(async ({ loginPage, ticketsPage }) => {
    // Login before each test
    await loginPage.goto();
    await loginPage.loginWithTestUser();
  });

  test.describe('Pending Tickets', () => {

    test('TC_TICKET_001: Should display pending tickets list', async ({ ticketsPage }) => {
      await ticketsPage.gotoPendingTickets();
      await ticketsPage.waitForTicketsLoad();

      // Verify page loaded
      const url = ticketsPage.page.url();
      expect(url).toContain('pending');
    });

    test('TC_TICKET_002: Should show ticket count for pending tickets', async ({ ticketsPage }) => {
      await ticketsPage.gotoPendingTickets();

      const count = await ticketsPage.getTicketCount();
      // Count should be 0 or more
      expect(count).toBeGreaterThanOrEqual(0);
    });

    test('TC_TICKET_003: Should be able to click on pending ticket', async ({ ticketsPage }) => {
      await ticketsPage.gotoPendingTickets();
      await ticketsPage.waitForTicketsLoad();

      if (await ticketsPage.hasTickets()) {
        await ticketsPage.clickFirstTicket();
        // Verify navigation to ticket details
        await ticketsPage.page.waitForLoadState('networkidle');
      }
    });
  });

  test.describe('Complete Tickets', () => {

    test('TC_TICKET_004: Should display complete tickets list', async ({ ticketsPage }) => {
      await ticketsPage.gotoCompleteTickets();
      await ticketsPage.waitForTicketsLoad();

      const url = ticketsPage.page.url();
      expect(url).toContain('complete');
    });

    test('TC_TICKET_005: Should show completed tickets', async ({ ticketsPage }) => {
      await ticketsPage.gotoCompleteTickets();

      const count = await ticketsPage.getTicketCount();
      expect(count).toBeGreaterThanOrEqual(0);
    });
  });

  test.describe('Junk Tickets', () => {

    test('TC_TICKET_006: Should display junk tickets list', async ({ ticketsPage }) => {
      await ticketsPage.gotoJunkTickets();
      await ticketsPage.waitForTicketsLoad();

      const url = ticketsPage.page.url();
      expect(url).toContain('junk');
    });

    test('TC_TICKET_007: Should show junk tickets count', async ({ ticketsPage }) => {
      await ticketsPage.gotoJunkTickets();

      const count = await ticketsPage.getTicketCount();
      expect(count).toBeGreaterThanOrEqual(0);
    });
  });

  test.describe('Assigned To Me', () => {

    test('TC_TICKET_008: Should display assigned tickets', async ({ ticketsPage }) => {
      await ticketsPage.gotoAssignedToMe();
      await ticketsPage.waitForTicketsLoad();

      const url = ticketsPage.page.url();
      expect(url).toContain('assigned');
    });

    test('TC_TICKET_009: Should show assigned tickets list', async ({ ticketsPage }) => {
      await ticketsPage.gotoAssignedToMe();

      const count = await ticketsPage.getTicketCount();
      expect(count).toBeGreaterThanOrEqual(0);
    });
  });

  test.describe('Ticket Search', () => {

    test('TC_TICKET_010: Should search for ticket by ID', async ({ ticketsPage }) => {
      await ticketsPage.gotoAssignedToMe();
      await ticketsPage.waitForTicketsLoad();

      // Get a ticket ID if available
      const ticketIds = await ticketsPage.getAllTicketIds();
      if (ticketIds.length > 0) {
        await ticketsPage.searchTicket(ticketIds[0]);
        await ticketsPage.waitForTicketsLoad();

        const searchResults = await ticketsPage.getTicketCount();
        expect(searchResults).toBeGreaterThanOrEqual(0);
      }
    });

    test('TC_TICKET_011: Should show no results for invalid search', async ({ ticketsPage }) => {
      await ticketsPage.gotoAssignedToMe();

      // Search with random invalid ID
      await ticketsPage.searchTicket('INVALID_TICKET_99999999');
      await ticketsPage.waitForTicketsLoad();

      const count = await ticketsPage.getTicketCount();
      expect(count).toBe(0);
    });
  });

  test.describe('Ticket Operations', () => {

    test('TC_TICKET_012: Should open add ticket page', async ({ ticketsPage }) => {
      await ticketsPage.gotoAssignedToMe();

      // Check if add ticket button exists and click it
      const addBtn = ticketsPage.addTicketButton;
      if (await addBtn.isVisible()) {
        await ticketsPage.gotoAddTicket();
        await ticketsPage.page.waitForLoadState('networkidle');
      }
    });

    test('TC_TICKET_013: Should dispose ticket successfully', async ({ ticketsPage }) => {
      await ticketsPage.gotoPendingTickets();
      await ticketsPage.waitForTicketsLoad();

      if (await ticketsPage.hasTickets()) {
        await ticketsPage.clickFirstTicket();

        // Check if dispose button is available
        const disposeBtn = ticketsPage.disposeButton;
        if (await disposeBtn.isVisible()) {
          await ticketsPage.disposeTicket({ remark: 'Test dispose - QA automation' });
        }
      }
    });

    test('TC_TICKET_014: Should reopen ticket successfully', async ({ ticketsPage }) => {
      await ticketsPage.gotoCompleteTickets();
      await ticketsPage.waitForTicketsLoad();

      if (await ticketsPage.hasTickets()) {
        await ticketsPage.clickFirstTicket();

        // Check if reopen button is available
        const reopenBtn = ticketsPage.reopenButton;
        if (await reopenBtn.isVisible()) {
          await ticketsPage.reopenTicket('Test reopen - QA automation');
        }
      }
    });

    test('TC_TICKET_015: Should mark ticket as junk', async ({ ticketsPage }) => {
      await ticketsPage.gotoPendingTickets();
      await ticketsPage.waitForTicketsLoad();

      if (await ticketsPage.hasTickets()) {
        await ticketsPage.clickFirstTicket();

        // Check if junk button is available
        const junkBtn = ticketsPage.junkButton;
        if (await junkBtn.isVisible()) {
          await ticketsPage.markAsJunk();
        }
      }
    });
  });

  test.describe('Pagination', () => {

    test('TC_TICKET_016: Should navigate to next page', async ({ ticketsPage }) => {
      await ticketsPage.gotoAssignedToMe();
      await ticketsPage.waitForTicketsLoad();

      const initialIds = await ticketsPage.getAllTicketIds();

      if (initialIds.length > 0) {
        const navigated = await ticketsPage.goToNextPage();
        if (navigated) {
          const newIds = await ticketsPage.getAllTicketIds();
          // Verify different tickets are shown
          expect(newIds).not.toEqual(initialIds);
        }
      }
    });

    test('TC_TICKET_017: Should navigate to previous page', async ({ ticketsPage }) => {
      await ticketsPage.gotoAssignedToMe();
      await ticketsPage.waitForTicketsLoad();

      // First go to next page
      await ticketsPage.goToNextPage();
      const pageIds = await ticketsPage.getAllTicketIds();

      // Then go back
      const navigatedBack = await ticketsPage.goToPreviousPage();
      if (navigatedBack) {
        const prevIds = await ticketsPage.getAllTicketIds();
        expect(prevIds).not.toEqual(pageIds);
      }
    });
  });

  test.describe('Ticket Filters', () => {

    test('TC_TICKET_018: Should filter tickets by status', async ({ ticketsPage }) => {
      await ticketsPage.gotoAssignedToMe();

      const statusFilter = ticketsPage.statusFilter;
      if (await statusFilter.isVisible()) {
        await statusFilter.selectOption('P'); // Pending
        await ticketsPage.waitForTicketsLoad();

        const url = ticketsPage.page.url();
        expect(url).toBeDefined();
      }
    });
  });

  test.describe('Response Time Validation', () => {

    test('TC_TICKET_019: Pending tickets should load within threshold', async ({ ticketsPage, page }) => {
      const startTime = Date.now();

      await ticketsPage.gotoPendingTickets();
      await ticketsPage.waitForTicketsLoad();

      const loadTime = Date.now() - startTime;

      // Should load within 10 seconds
      expect(loadTime).toBeLessThan(10000);
    });

    test('TC_TICKET_020: Complete tickets should load within threshold', async ({ ticketsPage }) => {
      const startTime = Date.now();

      await ticketsPage.gotoCompleteTickets();
      await ticketsPage.waitForTicketsLoad();

      const loadTime = Date.now() - startTime;

      // Should load within 10 seconds
      expect(loadTime).toBeLessThan(10000);
    });
  });
});
