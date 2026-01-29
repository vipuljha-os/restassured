// @ts-check

class OrdersPage {
  /**
   * @param {import('@playwright/test').Page} page
   */
  constructor(page) {
    this.page = page;

    // Order List Locators
    this.orderList = page.locator('.order-list, [class*="order-list"]');
    this.orderRows = page.locator('.order-row, .order-item, tr[class*="order"]');
    this.orderId = page.locator('.order-id, [class*="order-id"]');
    this.orderStatus = page.locator('.order-status, [class*="order-status"]');
    this.orderDate = page.locator('.order-date, [class*="order-date"]');
    this.orderTotal = page.locator('.order-total, [class*="order-total"]');
    this.noOrdersMessage = page.locator('.no-orders, .empty-state, :has-text("No orders")');

    // Order Details
    this.orderDetailPanel = page.locator('.order-detail, [class*="order-detail"]');
    this.customerInfo = page.locator('.customer-info, [class*="customer"]');
    this.shippingAddress = page.locator('.shipping-address, [class*="address"]');
    this.orderItems = page.locator('.order-items, .line-items');
    this.itemRows = page.locator('.item-row, .line-item');

    // Search and Filters
    this.searchInput = page.locator('input[placeholder*="Search"], input[name="order_search"]');
    this.searchButton = page.locator('button:has-text("Search"), .search-btn');
    this.statusFilter = page.locator('select[name="order_status"], .order-status-filter');
    this.dateFromFilter = page.locator('input[name="date_from"], .date-from');
    this.dateToFilter = page.locator('input[name="date_to"], .date-to');
    this.applyFilterButton = page.locator('button:has-text("Apply"), .apply-filter');

    // Actions
    this.viewDetailsButton = page.locator('button:has-text("View"), .view-details');
    this.refreshButton = page.locator('button:has-text("Refresh"), .refresh-btn');

    // Pagination
    this.nextPage = page.locator('button:has-text("Next"), .next-page');
    this.prevPage = page.locator('button:has-text("Previous"), .prev-page');
  }

  /**
   * Navigate to orders page
   */
  async goto() {
    await this.page.goto('/nui/orders');
    await this.page.waitForLoadState('networkidle');
  }

  /**
   * Wait for orders to load
   */
  async waitForOrdersLoad() {
    await this.page.waitForLoadState('networkidle');
    await Promise.race([
      this.orderRows.first().waitFor({ state: 'visible', timeout: 10000 }).catch(() => {}),
      this.noOrdersMessage.waitFor({ state: 'visible', timeout: 10000 }).catch(() => {})
    ]);
  }

  /**
   * Get order count
   * @returns {Promise<number>}
   */
  async getOrderCount() {
    await this.waitForOrdersLoad();
    return await this.orderRows.count();
  }

  /**
   * Check if orders exist
   * @returns {Promise<boolean>}
   */
  async hasOrders() {
    const count = await this.getOrderCount();
    return count > 0;
  }

  /**
   * Search for an order
   * @param {string} query - Order ID or search term
   */
  async searchOrder(query) {
    await this.searchInput.fill(query);
    await this.searchButton.click();
    await this.waitForOrdersLoad();
  }

  /**
   * Filter orders by status
   * @param {string} status - Order status
   */
  async filterByStatus(status) {
    await this.statusFilter.selectOption(status);
    await this.applyFilterButton.click();
    await this.waitForOrdersLoad();
  }

  /**
   * Filter orders by date range
   * @param {string} fromDate - Start date (YYYY-MM-DD)
   * @param {string} toDate - End date (YYYY-MM-DD)
   */
  async filterByDateRange(fromDate, toDate) {
    await this.dateFromFilter.fill(fromDate);
    await this.dateToFilter.fill(toDate);
    await this.applyFilterButton.click();
    await this.waitForOrdersLoad();
  }

  /**
   * Click on first order
   */
  async clickFirstOrder() {
    await this.orderRows.first().click();
    await this.page.waitForLoadState('networkidle');
  }

  /**
   * Click on order by index
   * @param {number} index
   */
  async clickOrderByIndex(index) {
    await this.orderRows.nth(index).click();
    await this.page.waitForLoadState('networkidle');
  }

  /**
   * Get all order IDs
   * @returns {Promise<string[]>}
   */
  async getAllOrderIds() {
    const ids = [];
    const count = await this.orderId.count();
    for (let i = 0; i < count; i++) {
      const id = await this.orderId.nth(i).textContent();
      if (id) ids.push(id.trim());
    }
    return ids;
  }

  /**
   * Get order details from the detail panel
   * @returns {Promise<object>}
   */
  async getOrderDetails() {
    return {
      id: await this.orderId.first().textContent().catch(() => ''),
      status: await this.orderStatus.first().textContent().catch(() => ''),
      date: await this.orderDate.first().textContent().catch(() => ''),
      total: await this.orderTotal.first().textContent().catch(() => '')
    };
  }

  /**
   * Get item count in an order
   * @returns {Promise<number>}
   */
  async getOrderItemCount() {
    return await this.itemRows.count();
  }

  /**
   * Refresh order list
   */
  async refresh() {
    await this.refreshButton.click();
    await this.waitForOrdersLoad();
  }

  /**
   * Navigate to next page
   * @returns {Promise<boolean>}
   */
  async goToNextPage() {
    if (await this.nextPage.isEnabled()) {
      await this.nextPage.click();
      await this.waitForOrdersLoad();
      return true;
    }
    return false;
  }
}

module.exports = { OrdersPage };
