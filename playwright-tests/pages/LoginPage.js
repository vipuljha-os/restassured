// @ts-check

class LoginPage {
  /**
   * @param {import('@playwright/test').Page} page
   */
  constructor(page) {
    this.page = page;

    // Locators
    this.usernameInput = page.locator('input#username_, input[name="username"], input[type="text"]').first();
    this.passwordInput = page.locator('input[type="password"], input[name="password"]').first();
    this.loginButton = page.locator('button[value="Login"], button:has-text("Login"), input[type="submit"]').first();
    this.errorMessage = page.locator('.error-message, .alert-danger, p:has-text("Oops")');
    this.dashboard = page.locator('.dashboard, .home-page, [class*="dashboard"]');
  }

  /**
   * Navigate to login page
   */
  async goto() {
    await this.page.goto('/employee/index.html');
    await this.page.waitForLoadState('domcontentloaded');
  }

  /**
   * Navigate to NUI login page
   */
  async gotoNUI() {
    await this.page.goto('/nui/');
    await this.page.waitForLoadState('domcontentloaded');
  }

  /**
   * Login with credentials
   * @param {string} username
   * @param {string} password
   */
  async login(username, password) {
    await this.usernameInput.fill(username);
    await this.passwordInput.fill(password);
    await this.loginButton.click();
    await this.page.waitForLoadState('networkidle');
  }

  /**
   * Login with default test credentials
   */
  async loginWithTestUser() {
    const username = process.env.TEST_USERNAME || 'test_user';
    const password = process.env.TEST_PASSWORD || 'Test@1234';
    await this.login(username, password);
  }

  /**
   * Check if login was successful
   * @returns {Promise<boolean>}
   */
  async isLoggedIn() {
    try {
      // Check for common dashboard elements or URL patterns
      const url = this.page.url();
      return url.includes('/nui/') || url.includes('/dashboard') || url.includes('/tickets');
    } catch {
      return false;
    }
  }

  /**
   * Check if error message is displayed
   * @returns {Promise<boolean>}
   */
  async hasError() {
    return await this.errorMessage.isVisible();
  }

  /**
   * Get error message text
   * @returns {Promise<string>}
   */
  async getErrorMessage() {
    if (await this.hasError()) {
      return await this.errorMessage.textContent() || '';
    }
    return '';
  }

  /**
   * Logout from the application
   */
  async logout() {
    const logoutBtn = this.page.locator('a:has-text("Logout"), button:has-text("Logout"), .logout-btn');
    if (await logoutBtn.isVisible()) {
      await logoutBtn.click();
      await this.page.waitForLoadState('networkidle');
    }
  }
}

module.exports = { LoginPage };
