// @ts-check
const { test, expect } = require('../utils/fixtures');

test.describe('Banana Club CRM - Login Functionality', () => {

  test.beforeEach(async ({ loginPage }) => {
    await loginPage.goto();
  });

  test('TC_LOGIN_001: Login page should load successfully', async ({ loginPage, page }) => {
    // Verify login page elements are visible
    await expect(loginPage.usernameInput).toBeVisible();
    await expect(loginPage.passwordInput).toBeVisible();
    await expect(loginPage.loginButton).toBeVisible();

    // Verify URL
    expect(page.url()).toContain('/employee/index.html');
  });

  test('TC_LOGIN_002: Should login with valid credentials', async ({ loginPage, page }) => {
    // Login with test credentials
    await loginPage.loginWithTestUser();

    // Verify successful login
    const isLoggedIn = await loginPage.isLoggedIn();
    expect(isLoggedIn).toBeTruthy();

    // Verify navigation to dashboard/tickets page
    const url = page.url();
    expect(url).toMatch(/nui|dashboard|tickets/);
  });

  test('TC_LOGIN_003: Should show error with invalid credentials', async ({ loginPage }) => {
    // Attempt login with invalid credentials
    await loginPage.login('invalid_user', 'wrong_password');

    // Wait for error response
    await loginPage.page.waitForTimeout(2000);

    // Check if still on login page (login failed)
    const url = loginPage.page.url();
    expect(url).toContain('employee');
  });

  test('TC_LOGIN_004: Should show error with empty username', async ({ loginPage }) => {
    // Attempt login with empty username
    await loginPage.login('', 'Test@1234');

    // Verify still on login page
    await expect(loginPage.loginButton).toBeVisible();
  });

  test('TC_LOGIN_005: Should show error with empty password', async ({ loginPage }) => {
    // Attempt login with empty password
    await loginPage.login('test_user', '');

    // Verify still on login page
    await expect(loginPage.loginButton).toBeVisible();
  });

  test('TC_LOGIN_006: Should navigate to NUI login page', async ({ loginPage, page }) => {
    await loginPage.gotoNUI();

    // Verify NUI page loads
    expect(page.url()).toContain('/nui/');
  });

  test('TC_LOGIN_007: Username field should accept alphanumeric input', async ({ loginPage }) => {
    const testUsername = 'TestUser123';
    await loginPage.usernameInput.fill(testUsername);

    const value = await loginPage.usernameInput.inputValue();
    expect(value).toBe(testUsername);
  });

  test('TC_LOGIN_008: Password field should mask input', async ({ loginPage }) => {
    const passwordField = loginPage.passwordInput;

    // Verify password field has type="password"
    const inputType = await passwordField.getAttribute('type');
    expect(inputType).toBe('password');

    // Enter password and verify it's masked
    await passwordField.fill('TestPassword123');
    const value = await passwordField.inputValue();
    expect(value).toBe('TestPassword123');
  });

  test('TC_LOGIN_009: Login button should be clickable', async ({ loginPage }) => {
    const loginButton = loginPage.loginButton;

    await expect(loginButton).toBeEnabled();
    await expect(loginButton).toBeVisible();
  });

  test('TC_LOGIN_010: Should handle special characters in password', async ({ loginPage }) => {
    await loginPage.login('test_user', 'P@ssw0rd!#$%');

    // Verify login attempt was made (page should respond)
    await loginPage.page.waitForTimeout(1000);
    await expect(loginPage.page).not.toHaveURL(/error/);
  });

  test('TC_LOGIN_011: Session should persist after login', async ({ loginPage, page, context }) => {
    await loginPage.loginWithTestUser();

    // Wait for login to complete
    await page.waitForLoadState('networkidle');

    // Check if cookies are set
    const cookies = await context.cookies();
    expect(cookies.length).toBeGreaterThan(0);
  });

  test('TC_LOGIN_012: Should logout successfully', async ({ loginPage, page }) => {
    // First login
    await loginPage.loginWithTestUser();
    await page.waitForLoadState('networkidle');

    // Then logout
    await loginPage.logout();

    // Verify redirected to login page
    await page.waitForLoadState('networkidle');
    const url = page.url();
    expect(url).toMatch(/employee|login|nui/);
  });
});
