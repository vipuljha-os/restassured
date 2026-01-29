// @ts-check

class TicketsPage {
  /**
   * @param {import('@playwright/test').Page} page
   */
  constructor(page) {
    this.page = page;

    // Navigation Locators
    this.pendingTicketsTab = page.locator('a:has-text("Pending"), [data-status="pending"]');
    this.completeTicketsTab = page.locator('a:has-text("Complete"), [data-status="complete"]');
    this.junkTicketsTab = page.locator('a:has-text("Junk"), [data-status="junk"]');
    this.assignedToMeTab = page.locator('a:has-text("Assigned to Me"), [data-filter="assigned"]');
    this.createdByMeTab = page.locator('a:has-text("Created by Me"), [data-filter="created"]');
    this.unassignedTab = page.locator('a:has-text("Unassigned"), [data-filter="unassigned"]');

    // Ticket List Locators
    this.ticketList = page.locator('.ticket-list, .tickets-container, [class*="ticket-list"]');
    this.ticketRows = page.locator('.ticket-row, .ticket-item, tr[class*="ticket"]');
    this.ticketId = page.locator('.ticket-id, [class*="ticket-id"]');
    this.noTicketsMessage = page.locator('.no-tickets, .empty-state, :has-text("No tickets")');

    // Ticket Actions
    this.disposeButton = page.locator('button:has-text("Dispose"), .dispose-btn');
    this.reopenButton = page.locator('button:has-text("Reopen"), .reopen-btn');
    this.junkButton = page.locator('button:has-text("Junk"), .junk-btn');
    this.addTicketButton = page.locator('button:has-text("Add Ticket"), .add-ticket-btn, a:has-text("Add Ticket")');

    // Search
    this.searchInput = page.locator('input[placeholder*="Search"], input[type="search"], .search-input');
    this.searchButton = page.locator('button:has-text("Search"), .search-btn');

    // Filters
    this.statusFilter = page.locator('select[name="status"], .status-filter');
    this.dateFilter = page.locator('input[type="date"], .date-filter');

    // Pagination
    this.nextPage = page.locator('button:has-text("Next"), .next-page, a:has-text("Next")');
    this.prevPage = page.locator('button:has-text("Previous"), .prev-page, a:has-text("Previous")');
    this.pageInfo = page.locator('.pagination-info, .page-info');

    // Loading
    this.loadingSpinner = page.locator('.loading, .spinner, [class*="loading"]');
  }

  /**
   * Navigate to tickets page
   * @param {string} filter - pending, complete, junk, assigned_to_me, etc.
   */
  async goto(filter = 'assigned_to_me') {
    await this.page.goto(`/nui/tickets/${filter}/5/-1/0`);
    await this.page.waitForLoadState('networkidle');
  }

  /**
   * Navigate to pending tickets
   */
  async gotoPendingTickets() {
    await this.goto('pending');
  }

  /**
   * Navigate to complete tickets
   */
  async gotoCompleteTickets() {
    await this.goto('complete');
  }

  /**
   * Navigate to junk tickets
   */
  async gotoJunkTickets() {
    await this.goto('junk');
  }

  /**
   * Navigate to assigned tickets
   */
  async gotoAssignedToMe() {
    await this.goto('assigned_to_me');
  }

  /**
   * Wait for tickets to load
   */
  async waitForTicketsLoad() {
    await this.page.waitForLoadState('networkidle');
    // Wait for either tickets to appear or no tickets message
    await Promise.race([
      this.ticketRows.first().waitFor({ state: 'visible', timeout: 10000 }).catch(() => {}),
      this.noTicketsMessage.waitFor({ state: 'visible', timeout: 10000 }).catch(() => {})
    ]);
  }

  /**
   * Get total ticket count
   * @returns {Promise<number>}
   */
  async getTicketCount() {
    await this.waitForTicketsLoad();
    return await this.ticketRows.count();
  }

  /**
   * Check if tickets are displayed
   * @returns {Promise<boolean>}
   */
  async hasTickets() {
    const count = await this.getTicketCount();
    return count > 0;
  }

  /**
   * Search for a ticket
   * @param {string} query
   */
  async searchTicket(query) {
    await this.searchInput.fill(query);
    await this.searchButton.click();
    await this.waitForTicketsLoad();
  }

  /**
   * Click on first ticket in the list
   */
  async clickFirstTicket() {
    await this.ticketRows.first().click();
    await this.page.waitForLoadState('networkidle');
  }

  /**
   * Click on ticket by index
   * @param {number} index
   */
  async clickTicketByIndex(index) {
    await this.ticketRows.nth(index).click();
    await this.page.waitForLoadState('networkidle');
  }

  /**
   * Dispose a ticket
   * @param {object} options - Dispose options
   */
  async disposeTicket(options = {}) {
    await this.disposeButton.click();
    // Handle dispose modal/form if present
    const disposeModal = this.page.locator('.dispose-modal, [class*="dispose-form"]');
    if (await disposeModal.isVisible()) {
      // Fill dispose form fields if needed
      if (options.remark) {
        await this.page.locator('textarea[name="remark"], .remark-input').fill(options.remark);
      }
      await this.page.locator('button:has-text("Submit"), button:has-text("Confirm")').click();
    }
    await this.page.waitForLoadState('networkidle');
  }

  /**
   * Reopen a ticket
   * @param {string} reason
   */
  async reopenTicket(reason = 'Test reopen') {
    await this.reopenButton.click();
    const reasonInput = this.page.locator('textarea[name="reason"], input[name="reason"], .reopen-reason');
    if (await reasonInput.isVisible()) {
      await reasonInput.fill(reason);
    }
    await this.page.locator('button:has-text("Submit"), button:has-text("Confirm")').click();
    await this.page.waitForLoadState('networkidle');
  }

  /**
   * Mark ticket as junk
   */
  async markAsJunk() {
    await this.junkButton.click();
    await this.page.locator('button:has-text("Confirm"), button:has-text("Yes")').click();
    await this.page.waitForLoadState('networkidle');
  }

  /**
   * Navigate to add ticket page
   */
  async gotoAddTicket() {
    await this.addTicketButton.click();
    await this.page.waitForLoadState('networkidle');
  }

  /**
   * Get all ticket IDs from current view
   * @returns {Promise<string[]>}
   */
  async getAllTicketIds() {
    const ids = [];
    const count = await this.ticketId.count();
    for (let i = 0; i < count; i++) {
      const id = await this.ticketId.nth(i).textContent();
      if (id) ids.push(id.trim());
    }
    return ids;
  }

  /**
   * Navigate to next page
   * @returns {Promise<boolean>} - Returns true if navigation was successful
   */
  async goToNextPage() {
    if (await this.nextPage.isEnabled()) {
      await this.nextPage.click();
      await this.waitForTicketsLoad();
      return true;
    }
    return false;
  }

  /**
   * Navigate to previous page
   * @returns {Promise<boolean>} - Returns true if navigation was successful
   */
  async goToPreviousPage() {
    if (await this.prevPage.isEnabled()) {
      await this.prevPage.click();
      await this.waitForTicketsLoad();
      return true;
    }
    return false;
  }
}

module.exports = { TicketsPage };
