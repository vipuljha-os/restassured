// @ts-check

class TicketDetailsPage {
  /**
   * @param {import('@playwright/test').Page} page
   */
  constructor(page) {
    this.page = page;

    // Ticket Info Locators
    this.ticketId = page.locator('.ticket-id, [class*="ticket-id"], #ticket-id');
    this.ticketStatus = page.locator('.ticket-status, [class*="status-badge"], .status');
    this.ticketTitle = page.locator('.ticket-title, h1, h2');
    this.ticketDescription = page.locator('.ticket-description, .description, .ticket-detail');
    this.ticketPriority = page.locator('.ticket-priority, [class*="priority"]');
    this.ticketCreatedDate = page.locator('.created-date, [class*="created"]');
    this.ticketAssignee = page.locator('.assignee, [class*="assigned-to"]');

    // Customer Info
    this.customerName = page.locator('.customer-name, [class*="cname"]');
    this.customerEmail = page.locator('.customer-email, [class*="email"]');
    this.customerPhone = page.locator('.customer-phone, [class*="phone"]');
    this.customerCode = page.locator('.customer-code, [class*="ccode"]');

    // Order Info
    this.orderId = page.locator('.order-id, [class*="order-id"]');
    this.orderStatus = page.locator('.order-status, [class*="order-status"]');

    // Conversation/History
    this.conversationList = page.locator('.conversation-list, .chat-history, [class*="conversation"]');
    this.conversationItems = page.locator('.conversation-item, .chat-message');
    this.replyInput = page.locator('textarea[name="reply"], .reply-input, #reply-text');
    this.sendReplyButton = page.locator('button:has-text("Send"), button:has-text("Reply"), .send-btn');

    // Attachments
    this.attachments = page.locator('.attachments, [class*="attachment"]');
    this.attachmentItems = page.locator('.attachment-item, .file-item');

    // Actions
    this.editButton = page.locator('button:has-text("Edit"), .edit-btn');
    this.disposeButton = page.locator('button:has-text("Dispose"), .dispose-btn');
    this.reopenButton = page.locator('button:has-text("Reopen"), .reopen-btn');
    this.escalateButton = page.locator('button:has-text("Escalate"), .escalate-btn');
    this.transferButton = page.locator('button:has-text("Transfer"), .transfer-btn');
    this.closeButton = page.locator('button:has-text("Close"), .close-btn');

    // Canned Responses
    this.cannedResponseButton = page.locator('button:has-text("Canned"), .canned-response-btn');
    this.cannedResponseList = page.locator('.canned-response-list, [class*="canned"]');

    // Back Navigation
    this.backButton = page.locator('button:has-text("Back"), a:has-text("Back"), .back-btn');
  }

  /**
   * Navigate to ticket details by ID
   * @param {string} ticketId
   */
  async goto(ticketId) {
    await this.page.goto(`/nui/ticket/${ticketId}`);
    await this.page.waitForLoadState('networkidle');
  }

  /**
   * Get ticket ID
   * @returns {Promise<string>}
   */
  async getTicketId() {
    return await this.ticketId.textContent() || '';
  }

  /**
   * Get ticket status
   * @returns {Promise<string>}
   */
  async getStatus() {
    return await this.ticketStatus.textContent() || '';
  }

  /**
   * Get customer information
   * @returns {Promise<object>}
   */
  async getCustomerInfo() {
    return {
      name: await this.customerName.textContent().catch(() => ''),
      email: await this.customerEmail.textContent().catch(() => ''),
      phone: await this.customerPhone.textContent().catch(() => ''),
      code: await this.customerCode.textContent().catch(() => '')
    };
  }

  /**
   * Send a reply
   * @param {string} message
   */
  async sendReply(message) {
    await this.replyInput.fill(message);
    await this.sendReplyButton.click();
    await this.page.waitForLoadState('networkidle');
  }

  /**
   * Get conversation count
   * @returns {Promise<number>}
   */
  async getConversationCount() {
    return await this.conversationItems.count();
  }

  /**
   * Use canned response
   * @param {number} index - Index of canned response to use
   */
  async useCannedResponse(index = 0) {
    await this.cannedResponseButton.click();
    await this.cannedResponseList.locator('li, .canned-item').nth(index).click();
  }

  /**
   * Dispose ticket from details page
   * @param {object} options
   */
  async dispose(options = {}) {
    await this.disposeButton.click();
    // Handle dispose form
    if (options.folder) {
      await this.page.locator('select[name="folder"], .folder-select').selectOption(options.folder);
    }
    if (options.remark) {
      await this.page.locator('textarea[name="remark"], .remark-input').fill(options.remark);
    }
    await this.page.locator('button:has-text("Submit"), button:has-text("Confirm")').click();
    await this.page.waitForLoadState('networkidle');
  }

  /**
   * Reopen ticket
   * @param {string} reason
   */
  async reopen(reason = 'Test reopen') {
    await this.reopenButton.click();
    const reasonInput = this.page.locator('textarea[name="reason"], .reopen-reason');
    if (await reasonInput.isVisible()) {
      await reasonInput.fill(reason);
    }
    await this.page.locator('button:has-text("Submit"), button:has-text("Confirm")').click();
    await this.page.waitForLoadState('networkidle');
  }

  /**
   * Escalate ticket
   */
  async escalate() {
    await this.escalateButton.click();
    await this.page.locator('button:has-text("Confirm"), button:has-text("Yes")').click();
    await this.page.waitForLoadState('networkidle');
  }

  /**
   * Transfer ticket to another agent
   * @param {string} agentId
   */
  async transfer(agentId) {
    await this.transferButton.click();
    await this.page.locator('select[name="agent"], input[name="agent"]').fill(agentId);
    await this.page.locator('button:has-text("Transfer"), button:has-text("Confirm")').click();
    await this.page.waitForLoadState('networkidle');
  }

  /**
   * Get attachment count
   * @returns {Promise<number>}
   */
  async getAttachmentCount() {
    return await this.attachmentItems.count();
  }

  /**
   * Go back to ticket list
   */
  async goBack() {
    await this.backButton.click();
    await this.page.waitForLoadState('networkidle');
  }
}

module.exports = { TicketDetailsPage };
