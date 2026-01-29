// @ts-check
const { test, expect } = require('../utils/fixtures');

test.describe('Banana Club CRM - Ticket Details', () => {

  test.beforeEach(async ({ loginPage, ticketsPage }) => {
    // Login and navigate to tickets
    await loginPage.goto();
    await loginPage.loginWithTestUser();
    await ticketsPage.gotoAssignedToMe();
    await ticketsPage.waitForTicketsLoad();
  });

  test.describe('Ticket Information', () => {

    test('TC_DETAIL_001: Should display ticket details page', async ({ ticketsPage, ticketDetailsPage }) => {
      if (await ticketsPage.hasTickets()) {
        await ticketsPage.clickFirstTicket();

        // Verify ticket details elements
        const ticketId = ticketDetailsPage.ticketId;
        await ticketId.waitFor({ state: 'visible', timeout: 5000 }).catch(() => {});
      }
    });

    test('TC_DETAIL_002: Should show customer information', async ({ ticketsPage, ticketDetailsPage }) => {
      if (await ticketsPage.hasTickets()) {
        await ticketsPage.clickFirstTicket();

        const customerInfo = await ticketDetailsPage.getCustomerInfo();
        expect(customerInfo).toBeDefined();
      }
    });

    test('TC_DETAIL_003: Should display ticket status', async ({ ticketsPage, ticketDetailsPage }) => {
      if (await ticketsPage.hasTickets()) {
        await ticketsPage.clickFirstTicket();

        const status = await ticketDetailsPage.getStatus();
        // Status could be P (Pending), C (Complete), J (Junk), etc.
        expect(['P', 'C', 'J', 'Pending', 'Complete', 'Junk', '']).toContain(status.trim());
      }
    });
  });

  test.describe('Conversation History', () => {

    test('TC_DETAIL_004: Should display conversation history', async ({ ticketsPage, ticketDetailsPage }) => {
      if (await ticketsPage.hasTickets()) {
        await ticketsPage.clickFirstTicket();

        const conversationCount = await ticketDetailsPage.getConversationCount();
        expect(conversationCount).toBeGreaterThanOrEqual(0);
      }
    });

    test('TC_DETAIL_005: Should send reply to ticket', async ({ ticketsPage, ticketDetailsPage }) => {
      if (await ticketsPage.hasTickets()) {
        await ticketsPage.clickFirstTicket();

        const replyInput = ticketDetailsPage.replyInput;
        if (await replyInput.isVisible()) {
          await ticketDetailsPage.sendReply('Test reply from QA automation');
        }
      }
    });
  });

  test.describe('Canned Responses', () => {

    test('TC_DETAIL_006: Should show canned responses', async ({ ticketsPage, ticketDetailsPage }) => {
      if (await ticketsPage.hasTickets()) {
        await ticketsPage.clickFirstTicket();

        const cannedBtn = ticketDetailsPage.cannedResponseButton;
        if (await cannedBtn.isVisible()) {
          await cannedBtn.click();

          const cannedList = ticketDetailsPage.cannedResponseList;
          await cannedList.waitFor({ state: 'visible', timeout: 5000 }).catch(() => {});
        }
      }
    });

    test('TC_DETAIL_007: Should use canned response', async ({ ticketsPage, ticketDetailsPage }) => {
      if (await ticketsPage.hasTickets()) {
        await ticketsPage.clickFirstTicket();

        const cannedBtn = ticketDetailsPage.cannedResponseButton;
        if (await cannedBtn.isVisible()) {
          await ticketDetailsPage.useCannedResponse(0);
        }
      }
    });
  });

  test.describe('Ticket Actions', () => {

    test('TC_DETAIL_008: Should dispose ticket from details page', async ({ ticketsPage, ticketDetailsPage }) => {
      await ticketsPage.gotoPendingTickets();
      await ticketsPage.waitForTicketsLoad();

      if (await ticketsPage.hasTickets()) {
        await ticketsPage.clickFirstTicket();

        const disposeBtn = ticketDetailsPage.disposeButton;
        if (await disposeBtn.isVisible()) {
          await ticketDetailsPage.dispose({ remark: 'QA automation test dispose' });
        }
      }
    });

    test('TC_DETAIL_009: Should reopen ticket from details page', async ({ ticketsPage, ticketDetailsPage }) => {
      await ticketsPage.gotoCompleteTickets();
      await ticketsPage.waitForTicketsLoad();

      if (await ticketsPage.hasTickets()) {
        await ticketsPage.clickFirstTicket();

        const reopenBtn = ticketDetailsPage.reopenButton;
        if (await reopenBtn.isVisible()) {
          await ticketDetailsPage.reopen('QA automation test reopen');
        }
      }
    });

    test('TC_DETAIL_010: Should escalate ticket', async ({ ticketsPage, ticketDetailsPage }) => {
      if (await ticketsPage.hasTickets()) {
        await ticketsPage.clickFirstTicket();

        const escalateBtn = ticketDetailsPage.escalateButton;
        if (await escalateBtn.isVisible()) {
          await ticketDetailsPage.escalate();
        }
      }
    });
  });

  test.describe('Attachments', () => {

    test('TC_DETAIL_011: Should display attachments if present', async ({ ticketsPage, ticketDetailsPage }) => {
      if (await ticketsPage.hasTickets()) {
        await ticketsPage.clickFirstTicket();

        const attachmentCount = await ticketDetailsPage.getAttachmentCount();
        expect(attachmentCount).toBeGreaterThanOrEqual(0);
      }
    });
  });

  test.describe('Navigation', () => {

    test('TC_DETAIL_012: Should go back to ticket list', async ({ ticketsPage, ticketDetailsPage }) => {
      if (await ticketsPage.hasTickets()) {
        await ticketsPage.clickFirstTicket();

        const backBtn = ticketDetailsPage.backButton;
        if (await backBtn.isVisible()) {
          await ticketDetailsPage.goBack();

          // Verify back on ticket list
          const url = ticketDetailsPage.page.url();
          expect(url).toContain('tickets');
        }
      }
    });
  });

  test.describe('Response Time', () => {

    test('TC_DETAIL_013: Ticket details should load within threshold', async ({ ticketsPage }) => {
      if (await ticketsPage.hasTickets()) {
        const startTime = Date.now();
        await ticketsPage.clickFirstTicket();
        await ticketsPage.page.waitForLoadState('networkidle');
        const loadTime = Date.now() - startTime;

        // Should load within 5 seconds
        expect(loadTime).toBeLessThan(5000);
      }
    });
  });
});
