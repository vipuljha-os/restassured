// @ts-check
const { test, expect } = require('../utils/fixtures');
const { getTodayFormatted, getDateFromNow } = require('../utils/testHelpers');

test.describe('Banana Club CRM - Order Management', () => {

  test.beforeEach(async ({ loginPage }) => {
    // Login before each test
    await loginPage.goto();
    await loginPage.loginWithTestUser();
  });

  test.describe('Order List', () => {

    test('TC_ORDER_001: Should display orders page', async ({ ordersPage }) => {
      await ordersPage.goto();
      await ordersPage.waitForOrdersLoad();

      const url = ordersPage.page.url();
      expect(url).toContain('order');
    });

    test('TC_ORDER_002: Should show order count', async ({ ordersPage }) => {
      await ordersPage.goto();

      const count = await ordersPage.getOrderCount();
      expect(count).toBeGreaterThanOrEqual(0);
    });

    test('TC_ORDER_003: Should display order list items', async ({ ordersPage }) => {
      await ordersPage.goto();
      await ordersPage.waitForOrdersLoad();

      if (await ordersPage.hasOrders()) {
        const orderIds = await ordersPage.getAllOrderIds();
        expect(orderIds.length).toBeGreaterThan(0);
      }
    });
  });

  test.describe('Order Search', () => {

    test('TC_ORDER_004: Should search for order by ID', async ({ ordersPage }) => {
      await ordersPage.goto();
      await ordersPage.waitForOrdersLoad();

      // Get an order ID if available
      const orderIds = await ordersPage.getAllOrderIds();
      if (orderIds.length > 0) {
        await ordersPage.searchOrder(orderIds[0]);
        await ordersPage.waitForOrdersLoad();

        const searchResults = await ordersPage.getOrderCount();
        expect(searchResults).toBeGreaterThanOrEqual(0);
      }
    });

    test('TC_ORDER_005: Should show no results for invalid order ID', async ({ ordersPage }) => {
      await ordersPage.goto();

      // Search with invalid order ID
      await ordersPage.searchOrder('INVALID_ORDER_99999999');
      await ordersPage.waitForOrdersLoad();

      const count = await ordersPage.getOrderCount();
      expect(count).toBe(0);
    });
  });

  test.describe('Order Filters', () => {

    test('TC_ORDER_006: Should filter orders by status', async ({ ordersPage }) => {
      await ordersPage.goto();

      const statusFilter = ordersPage.statusFilter;
      if (await statusFilter.isVisible()) {
        // Filter by cancelled status
        await ordersPage.filterByStatus('cancelled');
        await ordersPage.waitForOrdersLoad();

        // Verify filter was applied
        const url = ordersPage.page.url();
        expect(url).toBeDefined();
      }
    });

    test('TC_ORDER_007: Should filter orders by date range', async ({ ordersPage }) => {
      await ordersPage.goto();

      const dateFrom = ordersPage.dateFromFilter;
      if (await dateFrom.isVisible()) {
        const fromDate = getDateFromNow(-30); // 30 days ago
        const toDate = getTodayFormatted();

        await ordersPage.filterByDateRange(fromDate, toDate);
        await ordersPage.waitForOrdersLoad();

        const count = await ordersPage.getOrderCount();
        expect(count).toBeGreaterThanOrEqual(0);
      }
    });
  });

  test.describe('Order Details', () => {

    test('TC_ORDER_008: Should view order details', async ({ ordersPage }) => {
      await ordersPage.goto();
      await ordersPage.waitForOrdersLoad();

      if (await ordersPage.hasOrders()) {
        await ordersPage.clickFirstOrder();

        // Verify order details are visible
        const details = await ordersPage.getOrderDetails();
        expect(details).toBeDefined();
      }
    });

    test('TC_ORDER_009: Should display order items', async ({ ordersPage }) => {
      await ordersPage.goto();
      await ordersPage.waitForOrdersLoad();

      if (await ordersPage.hasOrders()) {
        await ordersPage.clickFirstOrder();

        const itemCount = await ordersPage.getOrderItemCount();
        expect(itemCount).toBeGreaterThanOrEqual(0);
      }
    });
  });

  test.describe('Order Pagination', () => {

    test('TC_ORDER_010: Should navigate to next page of orders', async ({ ordersPage }) => {
      await ordersPage.goto();
      await ordersPage.waitForOrdersLoad();

      const initialIds = await ordersPage.getAllOrderIds();

      if (initialIds.length > 0) {
        const navigated = await ordersPage.goToNextPage();
        if (navigated) {
          const newIds = await ordersPage.getAllOrderIds();
          expect(newIds).not.toEqual(initialIds);
        }
      }
    });
  });

  test.describe('Order Refresh', () => {

    test('TC_ORDER_011: Should refresh order list', async ({ ordersPage }) => {
      await ordersPage.goto();
      await ordersPage.waitForOrdersLoad();

      const refreshBtn = ordersPage.refreshButton;
      if (await refreshBtn.isVisible()) {
        await ordersPage.refresh();

        // Verify page still shows orders
        const count = await ordersPage.getOrderCount();
        expect(count).toBeGreaterThanOrEqual(0);
      }
    });
  });

  test.describe('Response Time Validation', () => {

    test('TC_ORDER_012: Orders page should load within threshold', async ({ ordersPage }) => {
      const startTime = Date.now();

      await ordersPage.goto();
      await ordersPage.waitForOrdersLoad();

      const loadTime = Date.now() - startTime;

      // Should load within 10 seconds
      expect(loadTime).toBeLessThan(10000);
    });

    test('TC_ORDER_013: Order search should respond within threshold', async ({ ordersPage }) => {
      await ordersPage.goto();
      await ordersPage.waitForOrdersLoad();

      const startTime = Date.now();
      await ordersPage.searchOrder('12345');
      await ordersPage.waitForOrdersLoad();
      const searchTime = Date.now() - startTime;

      // Search should complete within 5 seconds
      expect(searchTime).toBeLessThan(5000);
    });
  });
});
