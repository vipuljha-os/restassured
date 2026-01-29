// @ts-check
const { expect } = require('@playwright/test');

/**
 * Test helper utilities for Banana Club CRM testing
 */

/**
 * Wait for API response with specific status
 * @param {import('@playwright/test').Page} page
 * @param {string} urlPattern - URL pattern to match
 * @param {number} expectedStatus - Expected HTTP status code
 */
async function waitForApiResponse(page, urlPattern, expectedStatus = 200) {
  const response = await page.waitForResponse(
    (response) => response.url().includes(urlPattern) && response.status() === expectedStatus
  );
  return response;
}

/**
 * Wait for API response and get JSON body
 * @param {import('@playwright/test').Page} page
 * @param {string} urlPattern
 */
async function waitForApiJson(page, urlPattern) {
  const response = await page.waitForResponse(
    (response) => response.url().includes(urlPattern)
  );
  return await response.json();
}

/**
 * Take screenshot with timestamp
 * @param {import('@playwright/test').Page} page
 * @param {string} name
 */
async function takeTimestampedScreenshot(page, name) {
  const timestamp = new Date().toISOString().replace(/[:.]/g, '-');
  await page.screenshot({ path: `./screenshots/${name}-${timestamp}.png`, fullPage: true });
}

/**
 * Generate random string for test data
 * @param {number} length
 * @returns {string}
 */
function generateRandomString(length = 10) {
  const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
  let result = '';
  for (let i = 0; i < length; i++) {
    result += chars.charAt(Math.floor(Math.random() * chars.length));
  }
  return result;
}

/**
 * Generate random email for testing
 * @returns {string}
 */
function generateRandomEmail() {
  return `test_${generateRandomString(8)}@example.com`;
}

/**
 * Generate random phone number
 * @returns {string}
 */
function generateRandomPhone() {
  return `9${Math.floor(Math.random() * 900000000 + 100000000)}`;
}

/**
 * Format date for form input
 * @param {Date} date
 * @returns {string}
 */
function formatDateForInput(date) {
  return date.toISOString().split('T')[0];
}

/**
 * Get today's date formatted for input
 * @returns {string}
 */
function getTodayFormatted() {
  return formatDateForInput(new Date());
}

/**
 * Get date X days from now
 * @param {number} days
 * @returns {string}
 */
function getDateFromNow(days) {
  const date = new Date();
  date.setDate(date.getDate() + days);
  return formatDateForInput(date);
}

/**
 * Retry action with exponential backoff
 * @param {Function} action
 * @param {number} maxRetries
 * @param {number} initialDelay
 */
async function retryWithBackoff(action, maxRetries = 3, initialDelay = 1000) {
  let lastError;
  for (let i = 0; i < maxRetries; i++) {
    try {
      return await action();
    } catch (error) {
      lastError = error;
      if (i < maxRetries - 1) {
        const delay = initialDelay * Math.pow(2, i);
        await new Promise(resolve => setTimeout(resolve, delay));
      }
    }
  }
  throw lastError;
}

/**
 * Check if element is visible and enabled
 * @param {import('@playwright/test').Locator} locator
 * @returns {Promise<boolean>}
 */
async function isInteractable(locator) {
  try {
    const isVisible = await locator.isVisible();
    const isEnabled = await locator.isEnabled();
    return isVisible && isEnabled;
  } catch {
    return false;
  }
}

/**
 * Validate response time is within threshold
 * @param {number} responseTime
 * @param {number} threshold
 */
function validateResponseTime(responseTime, threshold = 3000) {
  expect(responseTime).toBeLessThan(threshold);
}

/**
 * Extract numbers from string
 * @param {string} text
 * @returns {number[]}
 */
function extractNumbers(text) {
  const matches = text.match(/\d+/g);
  return matches ? matches.map(Number) : [];
}

/**
 * Scroll element into view
 * @param {import('@playwright/test').Page} page
 * @param {string} selector
 */
async function scrollIntoView(page, selector) {
  await page.evaluate((sel) => {
    const element = document.querySelector(sel);
    if (element) {
      element.scrollIntoView({ behavior: 'smooth', block: 'center' });
    }
  }, selector);
}

/**
 * Wait for network idle with custom timeout
 * @param {import('@playwright/test').Page} page
 * @param {number} timeout
 */
async function waitForNetworkIdle(page, timeout = 30000) {
  await page.waitForLoadState('networkidle', { timeout });
}

/**
 * Clear all cookies
 * @param {import('@playwright/test').BrowserContext} context
 */
async function clearCookies(context) {
  await context.clearCookies();
}

/**
 * Get all cookies
 * @param {import('@playwright/test').BrowserContext} context
 */
async function getAllCookies(context) {
  return await context.cookies();
}

module.exports = {
  waitForApiResponse,
  waitForApiJson,
  takeTimestampedScreenshot,
  generateRandomString,
  generateRandomEmail,
  generateRandomPhone,
  formatDateForInput,
  getTodayFormatted,
  getDateFromNow,
  retryWithBackoff,
  isInteractable,
  validateResponseTime,
  extractNumbers,
  scrollIntoView,
  waitForNetworkIdle,
  clearCookies,
  getAllCookies
};
